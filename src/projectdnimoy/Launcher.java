package projectdnimoy;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projectdnimoy.others.AbstractWindow;

public class Launcher extends Application implements ConstantsInterface {
    
    @Override
    public void start(Stage primaryStage) {
        
        HBox root = new HBox();
        root.getChildren().add(getTopicPane(MECH_ID));
        root.getChildren().add(getTopicPane(WAVES_ID));
        root.getChildren().add(getTopicPane(ENM_ID));
        
        Scene scene = new Scene(root, width, height);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox getTopicPane(int sub) {
        VBox out = new VBox();
        out.setPadding(new Insets(10));
        out.setPrefWidth(width/4);
        Text title = new Text(SUBJECTS[sub]);
        out.getChildren().add(title);
        VBox.setMargin(title, new Insets(height/2-100, 10, 10, 10)); //needs more precision
        Button b;
        for(int i : new int[]{0, 1, 2}) {
            b = getTopicButton(sub, i);
            out.getChildren().add(b);
            VBox.setMargin(b, new Insets(10));
        }
        out.setBackground(new Background(new BackgroundFill(SUBJECT_COLORS[sub], CornerRadii.EMPTY, Insets.EMPTY)));
        return out;
    }
    
    private Button getTopicButton(int sub, int num) {
        Button b;
        if(num!=2) {
            b = new Button(TOPICS[sub][num]);
            b.setOnAction((ActionEvent e)->launchWindow(sub, num));
        } else {
            b = new Button(IN_CONS_TITLE);
            b.setOnAction((ActionEvent e)->JOptionPane.showMessageDialog(null, IN_CONS_MESSAGE, IN_CONS_TITLE, JOptionPane.INFORMATION_MESSAGE));
        }
        b.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
        b.setBackground(new Background(new BackgroundFill(SUBJECT_COLORS[sub], CornerRadii.EMPTY, Insets.EMPTY)));
        return b;
    }
    
    private static void launchWindow(int subject, int choice) {
        AbstractWindow w = new AbstractWindow(subject, choice) {

            @Override
            public void resetVariables() {
                
            }

            @Override
            public Pane initAnimationPane() {
                return new StackPane();
            }

            @Override
            public LineChart initChart() {
                return null;
            }
        };
        try {
            w.start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
