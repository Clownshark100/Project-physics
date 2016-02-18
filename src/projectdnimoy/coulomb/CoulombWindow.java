/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.coulomb;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import projectdnimoy.others.AbstractWindow;

/**
 *
 * @author cstuser
 */
public class CoulombWindow extends AbstractWindow {
    Charge[] array = new Charge[4];
    
    public CoulombWindow() {
        super(TOPICS[ENM_ID][0]);
        for(int i = 0; i<4; i++) array[i] = new Charge();
        resetVariables();
    }

    @Override
    protected void addPoint() {
        
    }

    @Override
    public void resetVariables() {
        for(Charge c : array) c.resetToRandom();
    }

    @Override
    public void pauseAnimations() {
        
    }

    @Override
    public void playAnimations() {
        
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
        
    }
    
    private class Charge extends Circle {
        public void resetToRandom() {
            
        }
    }
}
