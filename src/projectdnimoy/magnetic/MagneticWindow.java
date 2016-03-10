package projectdnimoy.magnetic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import projectdnimoy.others.AbstractWindow;
import static projectdnimoy.others.ConstantsInterface.K;
import static projectdnimoy.others.ConstantsInterface.paneHeight;
import static projectdnimoy.others.ConstantsInterface.paneWidth;
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
    int wireX = 10;

    public MagneticWindow() {
        super(TOPICS[ENM_ID][1]);
    }

    @Override
    protected void addPoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetVariables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String helpMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pane initAnimationPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPlayClick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class Charge extends Circle {
        int charge = 1;
        Vector2 vel = new Vector2();
        
        Charge(int radius) {
            super(radius, Color.BLACK);
            reset();
        }
        
        public void reset() {
            setCenterX(r.nextInt(paneWidth-(int)getRadius()*2)+(int)getRadius());
            setCenterY(r.nextInt(paneHeight-(int)getRadius()*2)+(int)getRadius());
            vel.setY(1);
            vel.setX(0);
        }
        
        public void update() {
            
        }
        
        public Vector2 fieldAtPos() {
            Vector2 result = new Vector2(getCenterY(), -getCenterX()+wireX);
            result.scale(-K*charge*Math.pow(10, -6)/Math.pow(result.getMagnitude(), 3));
            return result;
        }
    }
}
