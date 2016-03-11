package projectdnimoy.balistics;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import projectdnimoy.others.AbstractWindow;
import javafx.scene.input.MouseEvent;
import static projectdnimoy.others.ConstantsInterface.paneHeight;


/**
 * @author Olex Yasinovskyy
 * @author Ivan Miloslavov
 */
public class CannonWindow extends AbstractWindow {
    CannonBall ball = new CannonBall();
    CannonBody can = new CannonBody();
    CannonSmoke s = new CannonSmoke();
    boolean isFirst = false;
    
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
      can.setX(0);
      can.setY(paneHeight-32);
      can.setRotate(-tet);
      can.setCursor(Cursor.HAND);
      can.setOnMousePressed(cannonPressedEventHandler);
      can.setOnMouseDragged(cannonDraggedEventHandler);
      ball.setPosition(16, paneHeight-32);
      s.setX(27);
      s.setY(paneHeight-80);
      isFirst = true;
    }

    @Override
    public Pane initAnimationPane() {
       Pane anim = new Pane(ball, can, s);
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
        ball.setPosition(calculateDistance()+16, paneHeight-32-calculateHeight());
        if (outOfWindow()) pauseRunning();
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
           if (isFirst) {
           tet = Math.toDegrees(Math.atan2((can.getY()-e.getY()),(e.getX()-can.getX())));
            can.setRotate(-tet);
           }
        }
    };
    
    EventHandler<MouseEvent> cannonDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent e) {
            if (isFirst) {
            tet = Math.toDegrees(Math.atan2((can.getY()-e.getY()),(e.getX()-can.getX())));
            can.setRotate(-tet);
            v=Math.sqrt(Math.pow(can.getY()-e.getY(),2)+Math.pow((e.getX()-can.getX()), 2))/2;
            System.out.println("Y"+(can.getY()-e.getY()));
            System.out.println("X"+(e.getX()-can.getX()));
            }
        }
    };
    
    private boolean outOfWindow() {
        return ball.getX()<0 || ball.getX()>paneWidth || ball.getY()<0 || ball.getY()>paneHeight;
    }
}
