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
    private DoubleProperty p = new SimpleDoubleProperty(pInitial);
    
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
        return ONE/(ONE/f - ONE/p.getValue());  
    }
    private double calculateImageDistance(){
        return ONE/(ONE/f-ONE/(p.getValue()-calculateObjectDistance()));
    }
    private double calculateIniImageSize() {
        return (calculateIniImageDistance()*object.getFitHeight())/p.getValue();  
    }
     private double calculateImageSize() {
        return (calculateImageDistance()*object.getFitHeight())/p.getValue();  
    }
    
    public void resetVariables() {
     p.setValue(pInitial);
     convLens.setFitHeight(lensHeight);
     convLens.isPreserveRatio();
     convLens.setX(lensPosX);
     convLens.setY(lensPosY);
     object.setX(convLens.getX()+52-HALF*p.getValue() + object.getFitWidth());
     object.setY(objPostY);
     object.setSize(objPostY);
     object.isPreserveRatio();
     image.setX(calculateIniImageDistance()*HALF+ convLens.getX()+52 - image.getFitWidth());
     image.setY(imPosY);
     image.setSize(calculateIniImageSize());
     image.isPreserveRatio();
     image.setRotate(inverted);
     
      update();
    }    

    @Override
    public void update() {
       object.setPosition(calculateObjectDistance(), objPostY);   
       image.setPosition(calculateImageDistance()*HALF+ convLens.getX()+52 - image.getFitWidth(),imPosY);
       image.setSize(calculateImageSize());
       if(calculateObjectDistance()> maxP )pauseRunning();
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
