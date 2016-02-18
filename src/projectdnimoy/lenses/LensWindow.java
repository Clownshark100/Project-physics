package projectdnimoy.lenses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import projectdnimoy.others.AbstractWindow;
import static projectdnimoy.others.ConstantsInterface.TOPICS;

/**
 *
 * @author Olex Yasinovskyy
 */
public class LensWindow extends AbstractWindow {
    
    Witch object = new Witch();
    ConvLens convLens = new ConvLens();
    Human image = new Human();
    public LensWindow(){
        super(TOPICS[WAVES_ID][2]);
        resetVariables();
    }   
    
    @Override
    public void resetVariables() {
     
      update();
    }

    

    @Override
    public String helpMessage() {
        return "Press start and observe the change of image over time.";
    }

    

    @Override
    public void update() {
       
    }

    @Override
    protected void addPoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pane initAnimationPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Human extends ImageView {

         Human() {
            super(new Image(""));
        }
    }

    private class ConvLens extends ImageView {

        ConvLens() {
         super(new Image(""));
        }
        
        public void setPosition(double x){
            setX(x);
        }
    }

    private class Witch extends ImageView {
       
       Witch() {
           super(new Image(""));
       }
       
       public void setPosition(double x, double y) {
           setX(x);
           setY(y);
       }
    }   
}
