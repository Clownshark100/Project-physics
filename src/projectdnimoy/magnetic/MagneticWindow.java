package projectdnimoy.magnetic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import projectdnimoy.others.AbstractWindow;
import projectdnimoy.others.Vector2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ivan Miloslavov
 */
public class MagneticWindow extends AbstractWindow {
    final int wireX = 20, maxVel = 50;
    Charge c;

    public MagneticWindow() {
        super(TOPICS[ENM_ID][1]);
        setHelpMessage("This simulation displays the effect of a magnetic field coming from a straight wire with a constant current on an electric charge.\n" + 
                "Click play to watch the trajectory of the particle, repeated in graph form.\nClicking reset will place a new particle at a new starting location and with a new starting velocity vector.");
        c = new Charge(5);
    }

    @Override
    protected void addPoint() {
        addPoint(c.getCenterX()-c.start.getX(), c.start.getY()-c.getCenterY());
    }

    @Override
    public void resetVariables() {
        c.reset();
    }

    @Override
    public Pane initAnimationPane() {
        Pane result = new Pane(c);
        result.setPrefSize(paneWidth, paneHeight);
        return result;
    }

    @Override
    public void update() {
        c.update();
        if(c.vel.getMagnitude()>maxVel) pauseRunning();
    }
    
    private class Charge extends Circle {
        int charge = 500, current = 1, mass = 1;
        Vector2 vel = new Vector2(), start = new Vector2();
        
        Charge(int radius) {
            super(radius, Color.BLACK);
            reset();
        }
        
        public void reset() {
            setCenterX(r.nextInt(paneWidth-(int)getRadius()*2)+(int)getRadius());
            setCenterY(r.nextInt(paneHeight-(int)getRadius()*2)+(int)getRadius());
            start.setX(getCenterX());
            start.setY(getCenterY());
            vel.setY(r.nextDouble()*2-1);
            vel.setX(r.nextDouble()*2-1);
        }
        
        public void update() {
            vel.add(accAtPos().scale(deltaTime()));
            setCenterX(getCenterX()+vel.getX());
            setCenterY(getCenterY()+vel.getY());
        }
        
        public Vector2 accAtPos() {
            Vector2 result = new Vector2(-vel.getY(), vel.getX());
            result.scale(charge*current/mass*vel.getMagnitude()/(getCenterX()-wireX));
            return result;
        }
    }

    @Override
    public void onPlayClick() {}
}
