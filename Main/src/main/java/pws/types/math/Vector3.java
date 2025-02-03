package main.java.pws.types.math;

public class Vector3 {
    private Number x;
    private Number y;
    private Number z;

    public Number getX() {
        return x;
    }

    public Number getY() {
        return y;
    }

    public Number getZ() {
        return z;
    }
    
    public void setX(Number x) {
        this.x = x;
    }

    public void setY(Number y) {
        this.y = y;
    }

    public void setZ(Number z) {
        this.z = z;
    }

    public Vector3() {
    }

    public Vector3(Number x, Number y, Number z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public String toData(){
        return "(" + getX() + "," + getY() + "," + getZ() + ")";
    }
}
