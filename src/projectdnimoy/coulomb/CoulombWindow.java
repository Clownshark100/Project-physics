/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.coulomb;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import projectdnimoy.others.*;

/**
 *
 * @author cstuser
 */
public class CoulombWindow extends AbstractWindow {
    Charge[] array = new Charge[4];
    Vector2 zeroVel = new Vector2(), zeroPos;
    
    public CoulombWindow() {
        super(TOPICS[ENM_ID][0]);
        for(int i = 0; i<4; i++) array[i] = new Charge(10);
        zeroPos = new Vector2(array[0].getCenterX(), array[0].getCenterY());
        array[0].setFill(Color.RED);
    }
    
    private Vector2 totalFieldAtZero() {
        Vector2 test = new Vector2(array[0].getCenterX(), array[0].getCenterY());
        Vector2 sum = new Vector2();
        for(int i = 1; i<array.length; i++) sum.add(array[i].fieldAt(test));
        return sum;
    }

    @Override
    protected void addPoint() {
        addPoint(getRunningTime(), zeroVel.getMagnitude());
    }

    @Override
    public void resetVariables() {
        for(Charge c : array) c.resetToRandom();
        zeroPos.setX(array[0].getCenterX());
        zeroPos.setY(array[0].getCenterY());
        zeroVel.setX(0);
        zeroVel.setY(0);
    }

    @Override
    public String helpMessage() {
        return "Help for Coulomb";
    }

    @Override
    public Pane initAnimationPane() {
       Pane anim = new Pane(array);
       anim.setPrefSize(paneWidth, paneHeight);
       return anim; 
    }

    @Override
    public void update() {
        if(outOfWindow()) {
            array[0].resetToRandom();
            zeroPos.setX(array[0].getCenterX());
            zeroPos.setY(array[0].getCenterY());
            zeroVel.setX(0);
            zeroVel.setY(0);
            resetChart();
        } else {
            Vector2 acc = totalFieldAtZero().scale(array[0].charge*deltaTime()); //divided by mass
            zeroVel.add(acc);
            zeroPos.add(zeroVel);
            array[0].setCenterX(zeroPos.getX());
            array[0].setCenterY(zeroPos.getY());
        }
    }
    
    private boolean outOfWindow() {
        return zeroPos.getX()<0 || zeroPos.getX()>paneWidth || zeroPos.getY()<0 || zeroPos.getY()>paneHeight;
    }

    @Override
    public void onPlayClick() {
        
    }
    
    private class Charge extends Circle {
        double charge;
        
        Charge(int radius) {
            super(radius, Color.BLACK);
            resetToRandom();
        }
        
        public void resetToRandom() {
            setCenterX(r.nextInt(paneWidth-(int)getRadius()*2)+(int)getRadius());
            setCenterY(r.nextInt(paneHeight-(int)getRadius()*2)+(int)getRadius());
            charge = r.nextDouble()*6 - 3;
        }
        
        public Vector2 fieldAt(Vector2 test) {
            Vector2 result = new Vector2(getCenterX(), getCenterY()).sub(test);
            result.scale(-K*charge*Math.pow(10, -6)/Math.pow(result.getMagnitude(), 3));
            return result;
        }
    }
}
