/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.others;

import javafx.scene.chart.LineChart;
import javafx.scene.layout.Pane;

/**
 *
 * @author Olex Yasinovskyy
 */
public class Cannon extends AbstractWindow {

    Cannon(){
        setTitle(TOPICS[MECH_ID][0]);
    }
    
    double h= 0, d=0, teta=0, v=0;
    final double a = 9.80665;
    
    @Override
    public void pauseAnimations() {
        
    }
    
    @Override
    public void playAnimations() {
        
    }    
    
    @Override
    public void resetVariables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pane initAnimationPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LineChart initChart() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}