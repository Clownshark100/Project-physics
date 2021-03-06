/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdnimoy.others;

/**
 *
 * @author cstuser
 */
public class Vector2 {
    private double x, y;
    
    public Vector2() {
        this(0, 0);
    }
    
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    
    public Vector2 add(Vector2 v) {
        this.x+=v.x;
        this.y+=v.y;
        return this;
    }
    
    public Vector2 sub(Vector2 v) {
        this.x-=v.x;
        this.y-=v.y;
        return this;
    }
    
    public Vector2 scale(double scale) {
        this.x*=scale;
        this.y*=scale;
        return this;
    }

    public double getMagnitude(){
	return Math.sqrt(x*x+y*y);
    }

    public double getDirection(){
	return Math.atan2(y,x);
    }
    
    public static Vector2 fromPolar(double mag, double dir) {
        return new Vector2(mag*Math.cos(dir), mag*Math.sin(dir));
    }
    
    @Override
    public String toString() {
        return String.format("Vector2[%.3f, %.3f]", x, y);
    }
}
