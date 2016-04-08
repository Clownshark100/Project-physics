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
import projectdnimoy.others.Vector2;


public class CannonWindow extends AbstractWindow {
    CannonBall ball = new CannonBall();
    CannonBody can = new CannonBody();
    CannonSmoke s = new CannonSmoke();
    boolean isFirst = false;
    double tet=0, v=0;
    
    public CannonWindow(){
        super(TOPICS[MECH_ID][0], TIME_AXIS, HEIGHT_AXIS);
        setHelpMessage("Click and drag the cannon to give it initial velocity and angle of shot. "
                + "Press start and observe the cannonball's trajectory. "
                + "The farther you drag, the stronger the shot!");
        resetVariables();
    }
    
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
        can.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (isFirst) {
                    tet = Math.toDegrees(Math.atan2((can.getY()-e.getY()),(e.getX()-can.getX())));
                    can.setRotate(-tet);
                }
            }
        });
        can.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (isFirst) {
                    Vector2 launch = new Vector2(Math.max(0,e.getSceneX()-can.getX()), Math.max(0,can.getY()-e.getSceneY()));
                    can.setRotate(Math.toDegrees(-launch.getDirection()));
                    tet = -can.getRotate();
                    v=launch.getMagnitude()/4;
                }
            }
        });
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
    protected void addPoint() {
        addPoint(getRunningTime(), calculateHeight()+16);
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
    
    private boolean outOfWindow() {
        return ball.getX()<0 || ball.getX()>paneWidth || ball.getY()<0 || (ball.getY()+16)>paneHeight;
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
}
