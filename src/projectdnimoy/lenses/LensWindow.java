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
    
    Im image = new Im();
    ConvLens convLens = new ConvLens();
    Obj object = new Obj();
    public LensWindow(){
        super(TOPICS[WAVES_ID][0]);
        resetVariables();
    }
    double f = 30, p = 80;
    
    private double calculateImageDistance() {
        return 1/(1/f - 1/p);  
    }
    private double calculateImageSize() {
        return (calculateImageDistance()*object.getFitHeight())/p;  
    }
    
    public void resetVariables() {   
     p-= 20;
     convLens.setFitHeight(280);
     convLens.setPreserveRatio(true);
     convLens.setX(250);
     convLens.setY(100);
     object.setX((390-p + object.getFitWidth())*(1/1.5));
     object.setY(120);
     object.setSize(120);
     object.isPreserveRatio();
     image.setX((calculateImageDistance()+ convLens.getX()+140 - image.getFitWidth())*1.5);
     image.setY(240);
     image.setSize(calculateImageSize());
     image.isPreserveRatio();
     image.setRotate(180);
     
      update();
    }

    

    @Override
    public String helpMessage() {
        return "Press start and observe the change of image over time.";
    }

    

    @Override
    public void update() {
       image.setPosition((calculateImageDistance()+ convLens.getX()+140)*1.5,240);
       image.setSize(calculateImageSize());
    }

    @Override
    protected void addPoint() {
       addPoint(p, calculateImageDistance());
    }
    @Override
    public Pane initAnimationPane() {
        Pane anim = new Pane(object, convLens, image);
       anim.setPrefSize(paneWidth, paneHeight);
       return anim;
    }

 
    public void onPlayClick() {
       
        
        
    }

    private class Obj extends ImageView {

         Obj() {
            super(new Image("projectdnimoy/images/human.png"));
        }
         public void setSize(double h){
           setFitHeight(h);
           setPreserveRatio(true);
       }
    }

    private class ConvLens extends ImageView {

        ConvLens() {
         super(new Image("projectdnimoy/images/converLens.jpg"));
        }
    }

    private class Im extends ImageView {
       
       Im() {
           super(new Image("projectdnimoy/images/demon.png"));
       }
       
       public void setPosition(double x, double y) {
           setX(x);
           setY(y);
       }
       public void setSize(double h){
           setFitHeight(h);
           setPreserveRatio(true);
       }
    }   
}
