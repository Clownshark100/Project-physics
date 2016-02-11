/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.others;

import java.util.ArrayList;
import java.util.Timer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Ivan
 */
public abstract class AbstractWindow extends Application implements ConstantsInterface {
    public abstract void resetVariables();
    public abstract void pauseAnimations();
    public abstract void playAnimations();
    public abstract String helpMessage();
    public abstract Pane initAnimationPane();
    
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final ScatterChart chart;
    private final XYChart.Series mainSeries;
    private Stage mainMenu;
    private boolean running;
    private long lastNanoTime;
    private double runningTime = 0;
    protected Timer t = new Timer();
    private ArrayList<XYChart.Data> pointsList = new ArrayList<>();
    
    public AbstractWindow() {
        this.running = false;
        chart = new ScatterChart(new NumberAxis(), new NumberAxis());
        chart.setPrefHeight(200);
        mainSeries = new XYChart.Series();
        chart.getData().add(mainSeries);
    }
    
    public void setMainMenu(Stage mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.add(initAnimationPane(), 0, 0, 2, 1);
        root.add(initButtonPane(primaryStage), 0, 1);
        root.add(chart, 1, 1);
        
        Scene scene = new Scene(root, width, height);
        
        primaryStage.setTitle(getTitle());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    GridPane initButtonPane(Stage primaryStage) {
        GridPane result = new GridPane();
        Button playPause = new Button(PLAY_TEXT), done = new Button(DONE_TEXT), reset = new Button(RESET_TEXT),
                help = new Button(HELP_TEXT);
        done.setOnAction((ActionEvent e)->{
            pauseAnimations();
            mainMenu.show();
            primaryStage.hide();
        });
        reset.setOnAction((ActionEvent e)->{
            resetVariables();
            chart.getData().clear();
        });
        help.setOnAction((ActionEvent e)->JOptionPane.showMessageDialog(null, helpMessage(), 
                HELP_TEXT + " - " + getTitle(), JOptionPane.INFORMATION_MESSAGE));
        playPause.setOnAction((ActionEvent e)->{
            switch (playPause.getText()) {
                case PLAY_TEXT:
                    playPause.setText(PAUSE_TEXT);
                    lastNanoTime = System.nanoTime();
                    playAnimations();
                    running = true;
                    break;
                case PAUSE_TEXT:
                    playPause.setText(PLAY_TEXT);
                    running = false;
                    pauseAnimations();
                    mainSeries.getData().addAll(pointsList);
                    pointsList.clear();
                    break;
            }
        });
        result.add(playPause, 0, 0);
        result.add(reset, 0, 1);
        result.add(help, 1, 0);
        result.add(done, 1, 1);
        return result;
    }
    
    public void setTitle(String title) {
        this.title.set(title);
    }
    
    public String getTitle() {
        return title.get();
    }
    
    public StringProperty titleProperty() {
        return title;
    }
    
    public void setChartTitle(String name) {
        chart.setTitle(name);
    }
    
    public void addPoint(double time, double value) {
        pointsList.add(new XYChart.Data(time, value));
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public double getRunningTime() {
        return runningTime;
    }
    
    public double deltaTime() {
        double delta = (double)(System.nanoTime()-lastNanoTime)/Math.pow(10, 9);
        return delta;
    }
    
    public void nextFrame() {
        runningTime+=deltaTime();
        lastNanoTime = System.nanoTime();
    }
}
