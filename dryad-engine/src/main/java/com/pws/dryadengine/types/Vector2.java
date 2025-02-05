package com.pws.dryadengine.types;

public class Vector2 {
    private Number x;
    private Number y;

    public Number getX() {
        return x;
    }

    public Number getY() {
        return y;
    }
    
    public void setX(Number x) {
        this.x = x;
    }

    public void setY(Number y) {
        this.y = y;
    }


    public Vector2() {
    }

    public Vector2(Number x, Number y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public String toData(){
        return "(" + getX() + "," + getY() + ")";
    }
}
