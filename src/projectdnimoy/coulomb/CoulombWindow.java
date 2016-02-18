/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.coulomb;

import javafx.scene.layout.Pane;
import projectdnimoy.others.AbstractWindow;

/**
 *
 * @author cstuser
 */
public class CoulombWindow extends AbstractWindow {

    public CoulombWindow() {
        super(TOPICS[ENM_ID][0]);
        resetVariables();
    }

    @Override
    protected void addPoint() {
        
    }

    @Override
    public void resetVariables() {
        
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
       Pane anim = new Pane();
       anim.setPrefSize(paneWidth, paneHeight);
             
       return anim; 
    }

    @Override
    public void update() {
        
    }
    
}
