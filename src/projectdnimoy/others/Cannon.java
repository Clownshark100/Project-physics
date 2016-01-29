package projectdnimoy.others;

import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Olex Yasinovskyy
 */
public class Cannon extends AbstractWindow {
    
    
    
    public Cannon(){
        setTitle(TOPICS[MECH_ID][0]);
    }
    
    double h= 0, d=0, tet=0, v=0;
    double time = 0; //in abstract
    final double G = 9.80665, MIN_H = 0, MIN_D =0;
    
    private double calculateHeight(double tet, double v, double t) {
       h = MIN_H + Math.sin(Math.toRadians(tet))*v*t - G*0.5*Math.pow(t, 2);
        return h;
    }
    
    private double calculateDistance(double tet, double v, double t) {
        d = MIN_D + v*t*Math.cos(Math.toRadians(tet));
        return d;
    }
        
    @Override
    public void pauseAnimations() {
        
    }
    
    @Override
    public void playAnimations() {
        
    }    
    
    @Override
    public void resetVariables() {
        
    }

    @Override
    public Pane initAnimationPane() {
       GridPane anim = new GridPane();
       anim.setHgap(10);
       anim.setVgap(10);
       anim.setPadding(new Insets(400));
       
       
       
       return anim; 
    }

    @Override
    public String helpMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] a){
       launch(a);
    }

    private class cannonClass {
       Image image = new Image("projectDNIMOY/others/cannon.png");
       StackPane view;
       ImageView iv;
       RotateTransition trans = new RotateTransition();
    }   
}
