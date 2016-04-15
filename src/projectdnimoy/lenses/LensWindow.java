package projectdnimoy.lenses;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import projectdnimoy.others.AbstractWindow;
import static projectdnimoy.others.ConstantsInterface.TOPICS;

public class LensWindow extends AbstractWindow {
    
    Im image = new Im();
    ConvLens convLens = new ConvLens();
    Obj object = new Obj();
    private DoubleProperty p = new SimpleDoubleProperty(160);
    
    public LensWindow(){
        super(TOPICS[WAVES_ID][0], DIST_AXIS, "Image Distance (px)");
        setHelpMessage("This simulation presents the effect of lenses on images.\nPress play to visualize the displacement of an image created" + 
                " by a convergent lens depending on the displacement of the original object.");
        resetVariables();
    }
    
    private double calculateObjectDistance(){
        return UPDATE_FRAMERATE*getRunningTime();
    }
    private double calculateIniImageDistance() {
        return 1/(1/f - 1/p.getValue());  
    }
    private double calculateImageDistance(){
        return 1/(1/f-1/(p.getValue()-calculateObjectDistance()));
    }
    private double calculateIniImageSize() {
        return (calculateIniImageDistance()*object.getFitHeight())/p.getValue();  
    }
     private double calculateImageSize() {
        return (calculateImageDistance()*object.getFitHeight())/p.getValue();  
    }
    
    public void resetVariables() {
     p.setValue(160);
     convLens.setFitHeight(250);
     convLens.setPreserveRatio(true);
     convLens.setX(275);
     convLens.setY(100);
     object.setX(convLens.getX()+52-2*p.getValue() + object.getFitWidth());
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
       addPoint(p.getValue()-calculateObjectDistance(), 
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
