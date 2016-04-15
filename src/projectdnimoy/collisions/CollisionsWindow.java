/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.collisions;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import projectdnimoy.others.AbstractWindow;
import projectdnimoy.others.Vector2;

public class CollisionsWindow extends AbstractWindow {
    private final int MAX_VEL = 35;
    private Ball[] balls = new Ball[10];
    private final int DEFAULT_RADIUS = 15;
    
    public CollisionsWindow() {
        super(TOPICS[MECH_ID][1], TIME_AXIS, "Momentum (kg*m/s)");
        setHelpMessage("This section displays how collisions affect the paths and momenta of particles. Click play to make all the particles move.\n" + 
                "Once any collide, they will transfer momentum between themselves. The red charge is the one whose momentum is displayed in the graph.");
        for(int i = ZERO; i<balls.length; i++) balls[i] = new Ball(DEFAULT_RADIUS);
        balls[ZERO].setFill(Color.RED);
    }

    @Override
    protected void addPoint() {
        addPoint(getRunningTime(), balls[ZERO].vel.getMagnitude());
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
    public Pane initAnimationPane() {
        Pane result = new Pane(balls);
        result.setPrefSize(paneWidth, paneHeight);
        return result;
    }

    @Override
    public void onPlayClick() {}
    
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
            vel = new Vector2(r.nextInt(MAX_VEL*2)-MAX_VEL, r.nextInt(MAX_VEL*2)-MAX_VEL);
        }

	public void transferMomentum(Ball other){
		double theta = Math.atan2(this.getCenterY()-other.getCenterY(), this.getCenterX()-other.getCenterX());
		double new_x1 = this.vel.getMagnitude()*Math.cos(this.vel.getDirection()-theta);
		double new_y1 = this.vel.getMagnitude()*Math.sin(this.vel.getDirection()-theta);
		double new_x2 = other.vel.getMagnitude()*Math.cos(other.vel.getDirection()-theta);
		double new_y2 = other.vel.getMagnitude()*Math.sin(other.vel.getDirection()-theta);
		double final_x1 = ((this.mass-other.mass)*new_x1+(2*other.mass)*new_x2)/(this.mass+other.mass);
		double final_x2 = ((other.mass-this.mass)*new_x2+(2*this.mass)*new_x1)/(this.mass+other.mass);
		this.vel.setX(Math.cos(theta)*final_x1-Math.sin(theta)*new_y1);
		this.vel.setY(Math.sin(theta)*final_x1+Math.cos(theta)*new_y1);
		other.vel.setX(Math.cos(theta)*final_x2-Math.sin(theta)*new_y2);
		other.vel.setY(Math.sin(theta)*final_x2+Math.cos(theta)*new_y2);
	}

        private boolean intersects(Ball b) {
            return b.getPosition().sub(getPosition()).getMagnitude()<=b.getRadius()+getRadius();
        }
    }
}
