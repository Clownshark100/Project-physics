package projectdnimoy;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Launcher extends Application implements ConstantsInterface {
    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        BorderPane root = new BorderPane();
        root.getChildren().add(getTopicPane(MECH_ID));
        root.getChildren().add(getTopicPane(WAVES_ID));
        root.getChildren().add(getTopicPane(ENM_ID));
        Image i = new Image("projectdnimoy/Menu.jpg");
        root.setBackground(new Background(new BackgroundImage(i
                , BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT
                , BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        
        // Exit button 
        Scene scene = new Scene(root, width, height);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private VBox getTopicPane(int sub) {
        VBox out = new VBox();
        out.setPadding(new Insets(10));
        out.setPrefWidth(width/4);
        Text title = new Text(SUBJECTS[sub]);
        title.setFill(Color.WHITE);
        title.setFont(Font.font(14));
       
        out.getChildren().add(title);
        //VBox.setMargin(title, new Insets(height/2-100, 10, 10, 10)); //needs more precision
        Button b;
        for(int i : new int[]{0, 1, 2}) {
            b = getTopicButton(sub, i);
            out.getChildren().add(b);
            VBox.setMargin(b, new Insets(10));
        }
        
       
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
        b.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        b.setTextFill(Color.WHITE);
        b.setFont(Font.font(14));
        return b;
    }
    
    private void launchWindow(int subject, int choice) {
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
