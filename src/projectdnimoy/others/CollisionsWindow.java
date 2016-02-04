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
        int mass = 1;
        
        public Ball(int radius) {
            this(new Random().nextInt(width), new Random().nextInt(height), radius);
        }
        
        public Ball(int centerX, int centerY, int radius) {
            super(centerX, centerY, radius, Color.BLACK);
        }
        
        public void setPosition(double x, double y) {
            setCenterX(x);
            setCenterY(y);
        }
        
        public void setRandomPosition() {
            setCenterX(new Random().nextInt(width));
            setCenterY(new Random().nextInt(height));
        }

	public void transferMomentum(Ball firstBall, Ball secondBall){
		double dx = firstBall.getCenterX()-secondBall.getCenterX(), dy = firstBall.getCenterY()-secondBall.getCenterY();
		double theta = Math.atan2(dy, dx);
		double mag1 = firstBall.vel.getMagnitude(), mag2 = secondBall.vel.getMagnitude();
		double dir1 = firstBall.vel.getDirection(), dir2 = secondBall.vel.getDirection();
		double new_x1 = mag1*Math.cos(dir1-theta);
		double new_y1 = mag1*Math.sin(dir1-theta);
		double new_x2 = mag2*Math.cos(dir2-theta);
		double new_y2 = mag2*Math.sin(dir2-theta);
		double final_x1 = ((firstBall.mass-secondBall.mass)*new_x1+(secondBall.mass+secondBall.mass)*new_x2)/(firstBall.mass+secondBall.mass);
		double final_x2 = ((secondBall.mass-firstBall.mass)*new_x2+(firstBall.mass+firstBall.mass)*new_x1)/(firstBall.mass+secondBall.mass);
		firstBall.vel.setX(Math.cos(theta)*final_x1+Math.cos(theta+Math.PI/2)*new_y1);
		firstBall.vel.setY(Math.sin(theta)*final_x1+Math.sin(theta+Math.PI/2)*new_y1);
		secondBall.vel.setX(Math.cos(theta)*final_x2+Math.cos(theta+Math.PI/2)*new_y2);
		secondBall.vel.setY(Math.sin(theta)*final_x2+Math.sin(theta+Math.PI/2)*new_y2);
	}
    }
    Ball[] samples = new Ball[3];
    Ball ctrlBall;
    
    public CollisionsWindow() {
        setTitle("Collisions Simulation");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                
            }
        }, 1000, 25);
        ctrlBall = new Ball(width/2, height/2, 10);
        for(int i = 0; i<3; i++) samples[i] = new Ball(10);
    }
    
    @Override
    public void resetVariables() {
        ctrlBall.setPosition(width/2, height/2);
        for(int i = 0; i<3; i++) {
            samples[i].setRandomPosition();
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
        return new Pane(ctrlBall, samples[0], samples[1], samples[2]);
    }
    
}
