package com.pws.dryadengine.types;

public class Euler {
    private float x, y, z; // Euler angles in radians

    public Euler() {
        this(0, 0, 0);
    }

    public Euler(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Apply Euler rotation to a given vector
    public Vector3 rotateVector(Vector3 v) {
        float s1 = (float) Math.sin(x);
        float s2 = (float) Math.sin(y);
        float s3 = (float) Math.sin(z);
        float c1 = (float) Math.cos(x);
        float c2 = (float) Math.cos(y);
        float c3 = (float) Math.cos(z);

        float newX = (c1 * c3 - c2 * s1 * s3) * v.x + (-c1 * s3 - c2 * c3 * s1) * v.y + (s1 * s2) * v.z;
        float newY = (c3 * s1 + c1 * c2 * s3) * v.x + (c1 * c2 * c3 - s1 * s3) * v.y + (-c1 * s2) * v.z;
        float newZ = (s2 * s3) * v.x + (s2 * c3) * v.y + c2 * v.z;

        return new Vector3(newX, newY, newZ);
    }

    // Convert Euler angles to degrees
    public Vector3 toAngle() {
        return new Vector3((x * 180.0f) / (float) Math.PI, (y * 180.0f) / (float) Math.PI, (z * 180.0f) / (float) Math.PI);
    }

    // Getters and setters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
}
