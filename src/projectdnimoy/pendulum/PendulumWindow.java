/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.pendulum;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import projectdnimoy.others.AbstractWindow;
import projectdnimoy.others.Vector2;

/**
 *
 * @author cstuser
 */
public class PendulumWindow extends AbstractWindow {

    private double omega, length;
    private int startTheta;
    private final Line thread = new Line();
    private final Circle mass = new Circle(20);
     
    public PendulumWindow() {
        super(TOPICS[WAVES_ID][1]);
        thread.setStartX(paneWidth/2);
        thread.setStartY(paneHeight/2);
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
        Vector2 v = Vector2.fromPolar(length*Math.min(paneHeight, paneWidth)/9-10, Math.toRadians(getTheta()+90));
        thread.setEndX(thread.getStartX()+v.getX());
        thread.setEndY(thread.getStartY()+v.getY());
        mass.setCenterX(thread.getEndX());
        mass.setCenterY(thread.getEndY());
    }
    
    @Override
    public void resetVariables() {
        startTheta = r.nextInt(340)-170;
        length = (r.nextDouble()+0.5)*3;
        omega = Math.sqrt(G/length);
        update();
    }

    @Override
    public String helpMessage() {
        return "This section simulates a simple harmonic motion pendulum. \nWhile the program plays " + 
                "the pendulum will continuously oscillate without losing any energy. \nOn clicking reset, the pendulum will restart from a new starting angle and length of pendulum.";
    }

    @Override
    public Pane initAnimationPane() {
        Pane result = new Pane(thread, mass);
        result.setPrefSize(paneWidth, paneHeight);
        return result;
    }

    @Override
    public void onPlayClick() {}
    
}
