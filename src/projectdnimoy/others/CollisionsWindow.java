/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.others;

import java.util.Random;
import java.util.TimerTask;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
            for(Ball b : samples)
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
            setCenterX(new Random().nextInt(paneWidth));
            setCenterY(new Random().nextInt(paneHeight));
            vel = new Vector2(new Random().nextInt(70)-35, new Random().nextInt(70)-35);
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
    Ball[] samples = new Ball[15];
    int paneWidth = width, paneHeight = height-200;
    
    public CollisionsWindow() {
        setTitle("Collisions Simulation");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isRunning()) {
                    for(Ball b : samples)
                        b.update();
                    addPoint(getRunningTime(), samples[0].vel.getMagnitude());
                    nextFrame();
                }
            }
        }, 1000, 25);
        for(int i = 0; i<samples.length; i++) samples[i] = new Ball(15);
        samples[0].setFill(Color.RED);
    }
    
    @Override
    public void resetVariables() {
        for (Ball sample : samples) {
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
        Pane result = new Pane(samples);
        result.setPrefSize(paneWidth, paneHeight);
        return result;
    }
    
}
