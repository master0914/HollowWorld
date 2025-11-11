package Engine.Math;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Vector{

    // (Zx)
    // (Zy)
    public double x;
    public double y;
    public double absoluteValue;

    AffineTransform tx;
    Polygon arrowHead;


    public Vector(double Zx, double Zy){
        this.x = Zx;
        this.y = Zy;
        calculateAbsoluteValue();
    }


    private void calculateAbsoluteValue(){
        absoluteValue = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public Vector getUnitVector(){
        return multiply(1/absoluteValue);
    }

    public Vector add(Vector v){
        this.x += v.x;
        this.y += v.y;
        calculateAbsoluteValue();
        return this;
    }

    public Vector multiply(double a){
        return new Vector(a*x, a*y);
    }

    public void printVector(){
        System.out.println("V:   x: " + x + " y: " + y + " |V|: " + absoluteValue);
    }

    public IVector getIVector(){
        return new IVector((int)Math.round(x),(int)Math.round(y));
    }
}
