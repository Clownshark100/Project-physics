package projectdnimoy.lenses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import projectdnimoy.others.AbstractWindow;
import static projectdnimoy.others.ConstantsInterface.TOPICS;

/**
 *
 * @author Daniel Nahum
 */
public class LensWindow extends AbstractWindow {
    
    Witch image = new Witch();
    ConvLens convLens = new ConvLens();
    Human object = new Human();
    public LensWindow(){
        super(TOPICS[WAVES_ID][2]);
        resetVariables();
    }
    double f = 50;
    double o = 70;
    
    private double calculateImage() {
        return 1/(1/f - 1/o);  
    }
    
    public void resetVariables() {
     convLens.setX(250);
     convLens.setY(75);
     object.setX(250-70);
     object.setY(50);
     image.setX(calculateImage());
     image.setY(100);
     
      update();
    }

    

    @Override
    public String helpMessage() {
        return "Press start and observe the change of image over time.";
    }

    

    @Override
    public void update() {
       image.setPosition(calculateImage(), 100);
    }

    @Override
    protected void addPoint() {
       addPoint(getRunningTime(), calculateImage());
    }
    @Override
    public Pane initAnimationPane() {
        Pane anim = new Pane(object, convLens, image);
       anim.setPrefSize(paneWidth, paneHeight);
       return anim;
    }

 
    public void onPlayClick() {
        
        
    }

    private class Human extends ImageView {

         Human() {
            super(new Image("projectdnimoy/images/human.png"));
        }
    }

    private class ConvLens extends ImageView {

        ConvLens() {
         super(new Image("projectdnimoy/images/converLens.jpg"));
        }
        
        public void setPosition(double x,double y){
            setX(x);
            setY(y);
        }
        
       
    }

    private class Witch extends ImageView {
       
       Witch() {
           super(new Image("projectdnimoy/images/demon.png"));
       }
       
       public void setPosition(double x, double y) {
           setX(x);
           setY(y);
       }
    }   
}
