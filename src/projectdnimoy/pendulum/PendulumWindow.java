package projectdnimoy.pendulum;

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

    private double omega, length, startTheta;
    private boolean isFirst = true;
    private final Line thread = new Line();
    private final Circle mass = new Circle(20);
    private final short MAX_ANGLE = 100, LENGTH_MOD = 200;
     
    public PendulumWindow() {
        super(TOPICS[WAVES_ID][1]);
        setHelpMessage("This section simulates a simple harmonic motion pendulum. \nWhile the program plays " + 
                "the pendulum will continuously oscillate without losing any energy. \nOn clicking reset, the pendulum will restart from a new starting angle and length of pendulum.");
        thread.setStartX(paneWidth/2);
        thread.setStartY(paneHeight/4);
        mass.setCursor(Cursor.HAND);
        resetVariables();
    }
    
    @Override
    protected void addPoint() {
        addPoint(getRunningTime(), getTheta());
    }
    
    private double getTheta() {
        return startTheta*Math.cos(getRunningTime()*omega);
    }
    
    @Override
    public void update() {
        Vector2 v = Vector2.fromPolar(length, Math.toRadians(getTheta()+90));
        thread.setEndX(thread.getStartX()+v.getX());
        thread.setEndY(thread.getStartY()+v.getY());
        mass.setCenterX(thread.getEndX());
        mass.setCenterY(thread.getEndY());
    }
    
    @Override
    public void resetVariables() {     
        startTheta = r.nextInt(MAX_ANGLE*2)-MAX_ANGLE;
        length = (r.nextDouble()+0.5)*LENGTH_MOD;
        omega = 10*Math.sqrt(G/length);
        
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
                startTheta = -Math.toDegrees(Math.atan2((thread.getEndX()-paneWidth/2), (thread.getEndY()-thread.getStartY())));
                length = (Math.sqrt(Math.pow(thread.getEndX()-thread.getStartX(),2)+Math.pow(thread.getEndY()-thread.getStartY(),2)));
                omega = 10*Math.sqrt(G/length);
                System.out.println(length);
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
}
