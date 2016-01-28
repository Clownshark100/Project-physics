/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.others;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import projectdnimoy.ConstantsInterface;

/**
 *
 * @author cstuser
 */
public abstract class AbstractWindow extends Application implements ConstantsInterface {
    public abstract void resetVariables();
    String title;
    
    public AbstractWindow(int topic, int choice) {
        title = TOPICS[topic][choice];
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.add(initAnimationPane(), 0, 0, 2, 1);
        root.add(initButtonPane(), 0, 1);
        root.add(initChart(), 1, 1);
        
        Scene scene = new Scene(root, width, height);
        
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public abstract Pane initAnimationPane();
    public abstract LineChart initChart();
    
    GridPane initButtonPane() {
        GridPane result = new GridPane();
        Button playPause = new Button(PLAY_TEXT), done = new Button(DONE_TEXT), reset = new Button(RESET_TEXT),
                help = new Button(HELP_TEXT);
        result.add(playPause, 0, 0);
        result.add(reset, 1, 0);
        result.add(help, 2, 0);
        result.add(done, 3 , 0);
        return result;
    }
    
}
