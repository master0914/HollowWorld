package Engine.Math;

public class IVector {
    // (Zx)
    // (Zy)
    public int x;
    public int y;
    public double absoluteValue;

    public IVector(int Zx, int Zy){
        this.x = Zx;
        this.y = Zy;
        calculateAbsoluteValue();
    }


    private void calculateAbsoluteValue(){
        absoluteValue = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public IVector getUnitVector(){
        return multiply(1/absoluteValue);
    }

    public IVector add(IVector v){
        this.x += v.x;
        this.y += v.y;
        calculateAbsoluteValue();
        return this;
    }

    public IVector multiply(double a){
        return new IVector((int)(a*x), (int)(a*y));
    }

    public void printVector(){
        System.out.println("V:   x: " + x + " y: " + y + " |V|: " + absoluteValue);
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
