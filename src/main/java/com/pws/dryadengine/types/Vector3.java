package main.java.com.pws.dryadengine.types;

public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3() {
    }

    public Vector3(float x, float y, float z) {
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
        return "(" + this.x + "," + this.y + "," + this.z + ")";
    }
}
