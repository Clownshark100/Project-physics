package projectdnimoy.balistics;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import projectdnimoy.others.AbstractWindow;

/**
 *
 * @author Olex Yasinovskyy
 * @author Ivan Miloslavov
 */
public class CannonWindow extends AbstractWindow {
    CannonBall c = new CannonBall();
    CannonBody b = new CannonBody();
    
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
    public void resetVariables() {
      tet = 20+r.nextDouble()*50;
      v = 40+r.nextDouble()*60;
      b.setX(0);
      b.setY(paneHeight-32);
      b.setRotate(-tet);
      c.setPosition(16, paneHeight-32);
    }

    @Override
    public Pane initAnimationPane() {
       Pane anim = new Pane(c, b);
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
        c.setPosition(calculateDistance()+16, paneHeight-32-calculateHeight());
        if(calculateHeight()<0) pauseRunning();
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
    
    private class CannonBody extends ImageView {
        CannonBody() {
            super(new Image("projectdnimoy/balistics/cannon.png"));
        }
    }
}
