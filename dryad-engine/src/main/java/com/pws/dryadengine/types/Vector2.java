package com.pws.dryadengine.types;

public class Vector2 {
    public float x;
    public float y;

    public Vector2() {
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public String toData(){
        return "(" + this.x + "," + this.y + ")";
    }
}
