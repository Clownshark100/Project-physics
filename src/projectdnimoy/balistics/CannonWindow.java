package projectdnimoy.balistics;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import projectdnimoy.others.AbstractWindow;
import javafx.scene.input.MouseEvent;


/**
 * @author Olex Yasinovskyy
 * @author Ivan Miloslavov
 */
public class CannonWindow extends AbstractWindow {
    CannonBall c = new CannonBall();
    CannonBody b = new CannonBody();
    CannonSmoke s = new CannonSmoke();
    boolean isFirst = true;
    
    public CannonWindow(){
        super(TOPICS[MECH_ID][0]);
        resetVariables();
    }
   
    double tet=0, v=0;
    
    private double calculateHeight() {
        double t = getRunningTime()*2;
       return Math.sin(Math.toRadians(tet))*v*t - G*0.5*Math.pow(t, 2);
    }
    
    private double calculateDistance() {
        return v*getRunningTime()*Math.cos(Math.toRadians(tet))*2;
    }
    
    @Override
    public void resetVariables() {
      tet = 30+r.nextDouble()*40;
      v = 40+r.nextDouble()*60;
      b.setX(0);
      b.setY(paneHeight-32);
      b.setRotate(-tet);
      c.setPosition(16, paneHeight-32);
      s.setX(27);
      s.setY(paneHeight-80);
      isFirst = true;
    }

    @Override
    public Pane initAnimationPane() {
       Pane anim = new Pane(c, b, s);
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

    @Override
    public void onPlayClick() {
         if (isFirst) {
             FadeTransition ft = new FadeTransition(Duration.millis(200), s);
             s.setVisible(true);
             ft.setFromValue(0.0);
             ft.setToValue(1.0);
             ft.setCycleCount(2);
             ft.setAutoReverse(true);
            ft.play();
            isFirst = false; 
         }
    }

    private class CannonBall extends ImageView {
        
       CannonBall() {
           super(new Image("projectdnimoy/images/cannonBall.png"));
       }
       
       public void setPosition(double x, double y) {
           setX(x);
           setY(y);
       }
    }
    
    private class CannonBody extends ImageView {
        CannonBody() {
            super(new Image("projectdnimoy/images/cannon.png"));
        }
    }
    
    private class CannonSmoke extends ImageView {
        CannonSmoke () {
            super(new Image("projectdnimoy/images/cannonSmoke.png"));
            setVisible(false);
        }
    }
    
    EventHandler<MouseEvent> cannonPressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent e) {
           tet = Math.tan((b.getY()-e.getY())/(e.getX()-b.getX()));
            b.setRotate(-tet);
        }
    };
    
    EventHandler<MouseEvent> cannonDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent e) {
            v=40*Math.sqrt(Math.pow(b.getY()-e.getY(),2)+Math.pow((e.getX()-b.getX()), 2));
        }
    };
}
