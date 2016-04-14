
package projectdnimoy.others;

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

public abstract class AbstractWindow extends Application implements ConstantsInterface {
    
    private final SimpleStringProperty title = new SimpleStringProperty(), helpMessage = new SimpleStringProperty();
    private final ScatterChart chart;
    private final XYChart.Series mainSeries;
    private Stage mainMenu;
    private boolean running, forcedPause;
    private long lastNanoTime;
    private double runningTime = ZERO;
    protected Timer t = new Timer();
    protected Random r = new Random();
    private Button playPause;
    
    public AbstractWindow(String title, String xAxisName, String yAxisName) {
        this.running = false;
        chart = new ScatterChart(new NumberAxis(), new NumberAxis());
        chart.setPrefHeight(chartHeight);
        chart.setLegendVisible(false);
        chart.getXAxis().setLabel(xAxisName);
        chart.getYAxis().setLabel(yAxisName);
        mainSeries = new XYChart.Series();
        chart.getData().add(mainSeries);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isRunning()) {
                    Platform.runLater(()->{
                        update();
                        nextFrame();
                    });
                }
            }
        }, 1000, UPDATE_FRAMERATE);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isRunning()) {
                    Platform.runLater(()->addPoint());
                }
            }
        }, 1000, CHART_FRAMERATE);
        setTitle(title);
    }
    
    public void setMainMenu(Stage mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.add(initAnimationPane(), ZERO, ZERO, 2, 1);
        root.add(initButtonPane(primaryStage), ZERO, 1);
        root.add(chart, 1, 1);
        
        Scene scene = new Scene(root, width, height);
        
        primaryStage.setTitle(getTitle());
        primaryStage.setOnCloseRequest(e -> {Platform.exit(); System.exit(0);});
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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
            if (playPause.getText().equals(PAUSE_TEXT)) playPause.fire();
            resetChart();
            resetVariables();
        });
        help.setOnAction((ActionEvent e)->{
            if (playPause.getText().equals(PAUSE_TEXT)) playPause.fire();
            JOptionPane.showMessageDialog(null, getHelpMessage(), HELP_TEXT + " - " + getTitle(), JOptionPane.INFORMATION_MESSAGE);
        });
        playPause.setOnAction((ActionEvent e)->{
            switch (playPause.getText()) {
                case PLAY_TEXT:
                    playPause.setText(PAUSE_TEXT);
                    lastNanoTime = System.nanoTime();
                    running = true;
                    onPlayClick();
                    forcedPause = false;
                    break;
                case PAUSE_TEXT:
                    playPause.setText(PLAY_TEXT);
                    running = false;
                    break;
            }
        });
        result.add(playPause, ZERO, ZERO);
        result.add(reset, ZERO, 1);
        result.add(help, 1, ZERO);
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
    
    public void setHelpMessage(String m) {
        helpMessage.set(m);
    }
    
    public String getHelpMessage() {
        return helpMessage.get();
    }
    
    public StringProperty helpMessageProperty() {
        return helpMessage;
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
    
    public boolean isForcedPause() {
        return forcedPause;
    }
    
    public void pauseRunning() {
        if (playPause.getText().equals(PAUSE_TEXT))
            playPause.fire();
        forcedPause = true;
    }
    
    public double getRunningTime() {
        return runningTime;
    }
    
    public double deltaTime() {
        double delta = (double)(System.nanoTime()-lastNanoTime)/Math.pow(10, 9);
        return delta;
    }
    
    private void nextFrame() {
        runningTime+=deltaTime();
        lastNanoTime = System.nanoTime();
    }
    
    public void resetChart() {
        mainSeries.getData().clear();
        runningTime = 0;
    }
    
    protected abstract void addPoint();
    public abstract void resetVariables();
    public abstract Pane initAnimationPane();
    public abstract void update();
    public abstract void onPlayClick();
}
