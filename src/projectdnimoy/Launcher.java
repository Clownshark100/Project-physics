package projectdnimoy;

import projectdnimoy.others.ConstantsInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projectdnimoy.others.AbstractWindow;
import projectdnimoy.balistics.Cannon;
import projectdnimoy.collisions.CollisionsWindow;
import projectdnimoy.pendulum.PendulumWindow;

public class Launcher extends Application implements ConstantsInterface {
    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Pane root = new Pane();
        root.getChildren().add(getTopicPane(MECH_ID));
        root.getChildren().add(getTopicPane(WAVES_ID));
        root.getChildren().add(getTopicPane(ENM_ID));
        Image i = new Image("projectdnimoy/Menu.jpg");
        root.setBackground(new Background(new BackgroundImage(i
                , BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT
                , BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        
        Button exit = new Button("  EXIT  ");
        exit.setLayoutX(407);
        exit.setLayoutY(430);
        exit.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderStroke.DEFAULT_WIDTHS)));
        exit.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        exit.setTextFill(Color.WHITE);
        exit.setFont(Font.font(20));
        exit.setOnAction((ActionEvent event) -> {
            System.exit(0);
        }) ;
        root.getChildren().add(exit);
        
        Scene scene = new Scene(root, width, height);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private VBox getTopicPane(int sub) {
        VBox out = new VBox();
        out.setPadding(new Insets(20));
        out.setPrefWidth(width/4);
        Text title = new Text(SUBJECTS[sub]);
        title.setFill(Color.WHITE);
        title.setFont(Font.font(14));
        out.setAlignment(Pos.CENTER);
        out.setLayoutX(xSubPosition[sub]);
        out.setLayoutY(ySubPosition[sub]);
        out.getChildren().add(title);
        
        
        Button b;
        for(int i : new int[]{0, 1, 2}) {
            b = getTopicButton(sub, i);
            out.getChildren().add(b);
            VBox.setMargin(b, new Insets(7));
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
        b.setBorder(new Border(new BorderStroke(Color.STEELBLUE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderStroke.DEFAULT_WIDTHS)));
        b.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        b.setTextAlignment(TextAlignment.CENTER);
        b.setPrefSize(200, 50);
        b.setTextFill(Color.WHITE);
        b.setFont(Font.font(14));
        return b;
    }
    
    private void launchWindow(int subject, int choice) {
        AbstractWindow w = null;
        switch(subject) {
        case MECH_ID:if(choice == 1) w = new CollisionsWindow();
                     else w = new Cannon(); break;
        case WAVES_ID:if(choice == 1) w=new PendulumWindow(); break;
        }
        try {
            w.setMainMenu(primaryStage);
            w.start(new Stage());
            primaryStage.hide();
        } catch (Exception ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
