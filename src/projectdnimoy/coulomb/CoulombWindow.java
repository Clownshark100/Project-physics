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
    final int DEFAULT_CHARGE = 10;
    
    public CoulombWindow() {
        super(TOPICS[ENM_ID][0]);
        setHelpMessage("The Coulomb's law, explaining the movement of electric charges in electric fields, is presented here.\n" + 
                "Click play and watch the dynamic red charge obey the electric fields made by the black static charges.\n" +
                "Clicking reset will reconfigure the positions and charge of all charges.\n" +
                "If the red charge is to leave the screen, the program will automatically pause. Clicking play after that will restart ONLY the red charge from a new location.");
        for(int i = ZERO; i<4; i++) array[i] = new Charge(DEFAULT_CHARGE);
        zeroPos = new Vector2(array[ZERO].getCenterX(), array[ZERO].getCenterY());
        array[ZERO].setFill(Color.RED);
    }
    
    private Vector2 totalFieldAtZero() {
        Vector2 test = new Vector2(array[ZERO].getCenterX(), array[ZERO].getCenterY());
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
        zeroPos.setX(array[ZERO].getCenterX());
        zeroPos.setY(array[ZERO].getCenterY());
        zeroVel.setX(ZERO);
        zeroVel.setY(ZERO);
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
            pauseRunning();
        } else {
            Vector2 acc = totalFieldAtZero().scale(array[0].charge*deltaTime()); //divided by mass
            zeroVel.add(acc);
            zeroPos.add(zeroVel);
            array[ZERO].setCenterX(zeroPos.getX());
            array[ZERO].setCenterY(zeroPos.getY());
        }
    }
    
    private boolean outOfWindow() {
        return zeroPos.getX()<ZERO || zeroPos.getX()>paneWidth || zeroPos.getY()<ZERO || zeroPos.getY()>paneHeight;
    }

    @Override
    public void onPlayClick() {
        if(isForcedPause()) {
            array[0].resetToRandom();
            zeroPos.setX(array[ZERO].getCenterX());
            zeroPos.setY(array[ZERO].getCenterY());
            zeroVel.setX(ZERO);
            zeroVel.setY(ZERO);
            resetChart();
        }
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
