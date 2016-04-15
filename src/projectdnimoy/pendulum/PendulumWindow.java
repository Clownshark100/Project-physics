package projectdnimoy.pendulum;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import projectdnimoy.others.AbstractWindow;
import static projectdnimoy.others.ConstantsInterface.paneWidth;
import projectdnimoy.others.Vector2;

public class PendulumWindow extends AbstractWindow {

    private double length, startTheta;
    private DoubleProperty omega = new SimpleDoubleProperty();
    private boolean isFirst = true;
    private final Line thread = new Line();
    private final Circle mass = new Circle(20);
     
    public PendulumWindow() {
        super(TOPICS[WAVES_ID][1], TIME_AXIS, ANGLE_AXIS);
        setHelpMessage("This section simulates a simple harmonic motion pendulum. \nWhile the program plays " + 
                "the pendulum will continuously oscillate without losing any energy. \nOn clicking reset, the pendulum will restart from a new starting angle and length of pendulum."
                +" \nClick on the bob and drag it to the length and angle you want. Note that there is a \nmaximum length and if you make it longer, the bob will automatically go to the selected maximum.");
        thread.setStartX(paneWidth/HALF);
        thread.setStartY(paneHeight/QUARTER);
        mass.setCursor(Cursor.HAND);
        resetVariables();
    }
    
    @Override
    protected void addPoint() {
        addPoint(getRunningTime(), getTheta());
    }
    
    private double getTheta() {
        return startTheta*Math.cos(getRunningTime()*omega.get());
    }
    
    @Override
    public void update() {
        Vector2 v = Vector2.fromPolar(length, Math.toRadians(getTheta()+STRAIGHT_ANGLE));
        thread.setEndX(thread.getStartX()+v.getX());
        thread.setEndY(thread.getStartY()+v.getY());
        mass.setCenterX(thread.getEndX());
        mass.setCenterY(thread.getEndY());
    }
    
    @Override
    public void resetVariables() {     
        startTheta = r.nextInt(MAX_ANGLE*HALF)-MAX_ANGLE;
        length = (r.nextDouble()+0.5)*LENGTH_MOD;
        omega.set(PENDULUM_FACTOR*Math.sqrt(G/length));
        
        mass.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
               if (isFirst) {
                thread.setEndX(e.getSceneX());
                thread.setEndY(e.getSceneY());
                mass.setCenterX(thread.getEndX());
                mass.setCenterY(thread.getEndY());
               }
             }
        }); 
        
        mass.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e) {
                if (isFirst) {
                thread.setEndX(e.getSceneX());
                thread.setEndY(e.getSceneY());
                mass.setCenterX(e.getSceneX());
                mass.setCenterY(e.getSceneY());
                startTheta = -Math.toDegrees(Math.atan2((thread.getEndX()-paneWidth/HALF), (thread.getEndY()-thread.getStartY())));
                length = Math.min(MAX_THREAD,(Math.sqrt(Math.pow(thread.getEndX()-thread.getStartX(),2)+Math.pow(thread.getEndY()-thread.getStartY(),2))));
                omega.set(PENDULUM_FACTOR*Math.sqrt(G/length));
                }
            }
        });
         update();
         isFirst = true;
    }

    @Override
    public Pane initAnimationPane() {
        Pane result = new Pane(thread, mass);
        result.setPrefSize(paneWidth, paneHeight);
        return result;
    }

    @Override
    public void onPlayClick() {
        isFirst = false;
    }
    
    public DoubleProperty omegaProperty() {
        return omega;
    }
}
