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
    double f = 30, p = 160, v = 30;
    
    public LensWindow(){
        super(TOPICS[WAVES_ID][0], DIST_AXIS, "Image Distance (px)");
        setHelpMessage("This simulation presents the effect of lenses on images.\nPress play to visualize the displacement of an image created" + 
                " by a convergent lens depending on the displcement of the original object.");
        resetVariables();
    }
    
    private double calculateObjectDistance(){
        return v*getRunningTime();
    }
    private double calculateIniImageDistance() {
        return 1/(1/f - 1/p);  
    }
    private double calculateImageDistance(){
        return 1/(1/f-1/(p-calculateObjectDistance()));
    }
    private double calculateIniImageSize() {
        return (calculateIniImageDistance()*object.getFitHeight())/p;  
    }
     private double calculateImageSize() {
        return (calculateImageDistance()*object.getFitHeight())/p;  
    }
    
    public void resetVariables() {
     p= 160;
     convLens.setFitHeight(250);
     convLens.setPreserveRatio(true);
     convLens.setX(275);
     convLens.setY(100);
     object.setX(convLens.getX()+52-2*p + object.getFitWidth());
     object.setY(120);
     object.setSize(120);
     object.isPreserveRatio();
     image.setX(calculateIniImageDistance()*2+ convLens.getX()+52 - image.getFitWidth());
     image.setY(240);
     image.setSize(calculateIniImageSize());
     image.isPreserveRatio();
     image.setRotate(180);
     
      update();
    }    

    @Override
    public void update() {
       object.setPosition(calculateObjectDistance(), 120);   
       image.setPosition(calculateImageDistance()*2+ convLens.getX()+52 - image.getFitWidth(),240);
       image.setSize(calculateImageSize());
       if(calculateObjectDistance()> 110 )pauseRunning();
    }    

    @Override
    protected void addPoint() {
       addPoint(p-calculateObjectDistance(), 
               calculateImageDistance());
    }
    @Override
    public Pane initAnimationPane() {
        Pane anim = new Pane(object, convLens, image);
       anim.setPrefSize(paneWidth, paneHeight);
       return anim;
    }
 
    @Override
    public void onPlayClick() {
        if(isForcedPause()){
            resetChart();
            resetVariables();
        }
    }

    private class Obj extends ImageView {

         Obj() {
            super(new Image("projectdnimoy/images/human.png"));
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
