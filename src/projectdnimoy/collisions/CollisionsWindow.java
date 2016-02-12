/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.collisions;

import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import projectdnimoy.others.AbstractWindow;
import projectdnimoy.others.Vector2;

/**
 *
 * @author cstuser
 */
public class CollisionsWindow extends AbstractWindow {
    
    
    private class Ball extends Circle {
        Vector2 vel;
        int mass = 2;
        
        public Ball(int radius) {
            super(radius, Color.BLACK);
            resetToRandom();
        }
        
        public void update() {
            setPosition(getCenterX()+vel.getX()*deltaTime(), 
                    getCenterY()+vel.getY()*deltaTime());
            if(getCenterX()<getRadius() || getCenterX()>paneWidth-getRadius())
                vel.setX(-vel.getX());
            else if (getCenterY()<getRadius() || getCenterY()>paneHeight-getRadius())
                vel.setY(-vel.getY());
            for(Ball b : balls)
                if(b!=this)
                    if(intersects(b))
                        transferMomentum(b);
        }
        
        public void setPosition(double x, double y) {
            setCenterX(x);
            setCenterY(y);
        }
        
        public Vector2 getPosition() {
            return new Vector2(getCenterX(), getCenterY());
        }
        
        public void resetToRandom() {
            setCenterX(r.nextInt(paneWidth-(int)getRadius()*2)+(int)getRadius());
            setCenterY(r.nextInt(paneHeight-(int)getRadius()*2)+(int)getRadius());
            vel = new Vector2(r.nextInt(70)-35, r.nextInt(70)-35);
        }

	public void transferMomentum(Ball other){
		double dx = this.getCenterX()-other.getCenterX(), dy = this.getCenterY()-other.getCenterY();
		double theta = Math.atan2(dy, dx);
		double mag1 = this.vel.getMagnitude(), mag2 = other.vel.getMagnitude();
		double dir1 = this.vel.getDirection(), dir2 = other.vel.getDirection();
		double new_x1 = mag1*Math.cos(dir1-theta);
		double new_y1 = mag1*Math.sin(dir1-theta);
		double new_x2 = mag2*Math.cos(dir2-theta);
		double new_y2 = mag2*Math.sin(dir2-theta);
		double final_x1 = ((this.mass-other.mass)*new_x1+(other.mass+other.mass)*new_x2)/(this.mass+other.mass);
		double final_x2 = ((other.mass-this.mass)*new_x2+(this.mass+this.mass)*new_x1)/(this.mass+other.mass);
		this.vel.setX(Math.cos(theta)*final_x1+Math.cos(theta+Math.PI/2)*new_y1);
		this.vel.setY(Math.sin(theta)*final_x1+Math.sin(theta+Math.PI/2)*new_y1);
		other.vel.setX(Math.cos(theta)*final_x2+Math.cos(theta+Math.PI/2)*new_y2);
		other.vel.setY(Math.sin(theta)*final_x2+Math.sin(theta+Math.PI/2)*new_y2);
	}

        private boolean intersects(Ball b) {
            return b.getPosition().sub(getPosition()).getMagnitude()<=b.getRadius()+getRadius();
        }
    }
    private Ball[] balls = new Ball[10];
    
    public CollisionsWindow() {
        super("Collisions Simulation");
        for(int i = 0; i<balls.length; i++) balls[i] = new Ball(15);
        balls[0].setFill(Color.RED);
    }

    @Override
    protected void addPoint() {
        addPoint(getRunningTime(), balls[0].vel.getMagnitude());
    }
    
    @Override
    public void update() {
        for(Ball b : balls) b.update();
    }
    
    @Override
    public void resetVariables() {
        for (Ball sample : balls) {
            sample.resetToRandom();
        }
    }

    @Override
    public void pauseAnimations() {
        
    }

    @Override
    public void playAnimations() {
        
    }

    @Override
    public String helpMessage() {
        return "Collisions Help";
    }

    @Override
    public Pane initAnimationPane() {
        Pane result = new Pane(balls);
        result.setPrefSize(paneWidth, paneHeight);
        return result;
    }
    
}
