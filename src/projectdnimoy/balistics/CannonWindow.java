package projectdnimoy.balistics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import projectdnimoy.others.AbstractWindow;

/**
 *
 * @author Olex Yasinovskyy
 */
public class CannonWindow extends AbstractWindow {
    CannonBall c = new CannonBall();
    
    public CannonWindow(){
        super(TOPICS[MECH_ID][0]);
        resetVariables();
    }
    
    double tet=0, v=0;
    
    private double calculateHeight() {
        double t = getRunningTime();
       return Math.sin(Math.toRadians(tet))*v*t - G*0.5*Math.pow(t, 2);
    }
    
    private double calculateDistance() {
        return v*getRunningTime()*Math.cos(Math.toRadians(tet));
    }
        
    @Override
    public void pauseAnimations() {
        
    }
    
    @Override
    public void playAnimations() {
        
    }    
    
    @Override
    public void resetVariables() {
      tet = 20+r.nextDouble()*50;
      v = 40+r.nextDouble()*60;
      update();
    }

    @Override
    public Pane initAnimationPane() {
       Pane anim = new Pane(c);
       //anim.setPadding(new Insets(400)); <-- wow.
       anim.setPrefSize(paneWidth, paneHeight);
             
       return anim; 
    }

    @Override
    public String helpMessage() {
        return "Click and drag the cannon to give it initial velocity and angle of shot. "
                + "Press start and observe the cannonball's trajectory.";
    }

    @Override
    protected void addPoint() {
        addPoint(getRunningTime(), calculateHeight());
    }

    @Override
    public void update() {
        c.setPosition(calculateDistance()+16, paneHeight-16-calculateHeight());
    }

    private class CannonBall extends ImageView {
       
       CannonBall() {
           super(new Image("projectdnimoy/balistics/cannonBall.png"));
       }
       
       public void setPosition(double x, double y) {
           setX(x);
           setY(y);
       }
    }   
}