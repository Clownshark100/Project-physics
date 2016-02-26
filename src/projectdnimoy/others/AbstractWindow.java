/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.others;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
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
    
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final ScatterChart chart;
    private final XYChart.Series mainSeries;
    private Stage mainMenu;
    private boolean running;
    private long lastNanoTime;
    private double runningTime = 0;
    protected Timer t = new Timer();
    protected Random r = new Random();
    private Button playPause;
    
    public AbstractWindow(String title) {
        this.running = false;
        chart = new ScatterChart(new NumberAxis(), new NumberAxis());
        chart.setPrefHeight(200);
        mainSeries = new XYChart.Series();
        chart.getData().add(mainSeries);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isRunning()) {
                    update();
                    nextFrame();
                }
            }
        }, 1000, 25);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isRunning()) {
                    Platform.runLater(()->addPoint());
                }
            }
        }, 1000, 166);
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
        primaryStage.setOnCloseRequest(e -> {Platform.exit(); System.exit(0);});
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    GridPane initButtonPane(Stage primaryStage) {
        GridPane result = new GridPane();
        Button done = new Button(DONE_TEXT), reset = new Button(RESET_TEXT),
                help = new Button(HELP_TEXT);
                playPause = new Button(PLAY_TEXT);
        done.setOnAction((ActionEvent e)->{
            running = false;
            mainMenu.show();
            primaryStage.hide();
        });
        reset.setOnAction((ActionEvent e)->{
            resetVariables();
            mainSeries.getData().clear();
            runningTime = 0;
        });
        help.setOnAction((ActionEvent e)->{
            playPause.fire();
            JOptionPane.showMessageDialog(null, helpMessage(), HELP_TEXT + " - " + getTitle(), JOptionPane.INFORMATION_MESSAGE);
            playPause.fire();
        });
        playPause.setOnAction((ActionEvent e)->{
            switch (playPause.getText()) {
                case PLAY_TEXT:
                    playPause.setText(PAUSE_TEXT);
                    lastNanoTime = System.nanoTime();
                    running = true;
                    break;
                case PAUSE_TEXT:
                    playPause.setText(PLAY_TEXT);
                    running = false;
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
        mainSeries.getData().add(new XYChart.Data(time, value));
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void pauseRunning() {
        if (playPause.getText().equals(PAUSE_TEXT))
            playPause.fire();
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
    
    protected abstract void addPoint();
    public abstract void resetVariables();
    public abstract String helpMessage();
    public abstract Pane initAnimationPane();
    public abstract void update();
}
