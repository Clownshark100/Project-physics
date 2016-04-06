package projectdnimoy.lenses;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
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
    double f = 30, p = 160;
    
    private double calculateImageDistance() {
        return 1/(1/f - 1/p);  
    }
    private double calculateImageSize() {
        return (calculateImageDistance()*object.getFitHeight())/p;  
    }
    
    public void resetVariables() {
    
     convLens.setFitHeight(250);
     convLens.setPreserveRatio(true);
     convLens.setX(275);
     convLens.setY(100);
     object.setX(convLens.getX()+52-2*p + object.getFitWidth());
     object.setY(120);
     object.setSize(120);
     object.isPreserveRatio();
     image.setX(calculateImageDistance()*2+ convLens.getX()+52 - image.getFitWidth());
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
       image.setPosition(calculateImageDistance()*2+ convLens.getX()+52,240);
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
        TranslateTransition trObj = new TranslateTransition(Duration.seconds(5), object);
        trObj.setToX(160);
        trObj.equals(-p*2 + object.getFitWidth() + convLens.getX()+52);
        TranslateTransition trIm =  new TranslateTransition(Duration.seconds(5), image);
        trIm.setByX(calculateImageDistance()*2);
        trIm.setByY(35);
        ScaleTransition scIm = new ScaleTransition(Duration.seconds(5), image);
        scIm.setByX(2.8);
        scIm.setByY(2.8);
        ParallelTransition prTransition = new ParallelTransition(trObj,trIm,scIm);
        prTransition.play();
        
        
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
         super(new Image("projectdnimoy/images/converLens.png"));
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
