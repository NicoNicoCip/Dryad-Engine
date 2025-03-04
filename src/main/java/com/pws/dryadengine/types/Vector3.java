package com.pws.dryadengine.types;

public class Vector3 {
    public float x, y, z;

    public Vector3() {}

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

    // Set values from another vector
    public void set(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    // To String representation
    @Override
    public String toString() {
        return "{" + " x='" + this.x + "', y='" + this.y + "', z='" + this.z + "'}";
    }

    // Data representation for debugging or logging
    public String toData() {
        return "(" + this.x + "," + this.y + "," + this.z + ")";
    }

    // Calculate distance to another point
    public float distanceTo(Vector3 point) {
        float dx = this.x - point.x;
        float dy = this.y - point.y;
        float dz = this.z - point.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    // Addition
    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Subtraction
    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Scalar multiplication
    public Vector3 scalar(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    // Scalar multiplication
    public Vector3 multiply(Vector3 other) {
        return new Vector3(this.x * other.x, this.y * other.z, this.z * other.z);
    }

    // Dot product
    public float dot(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Cross product
    public Vector3 cross(Vector3 other) {
        return new Vector3(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }

    // Length (magnitude)
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    // Normalize the vector (make it a unit vector)
    public Vector3 normalize() {
        float len = this.length();
        if (len == 0) {
            return new Vector3(0, 0, 0);
        }
        return new Vector3(this.x / len, this.y / len, this.z / len);
    }

    // Calculate angle between two vectors (in radians)
    public float angle(Vector3 other) {
        float dotProduct = this.dot(other);
        float lengthsMultiplied = this.length() * other.length();
        if (lengthsMultiplied == 0) {
            return 0;
        }
        float cosTheta = dotProduct / lengthsMultiplied;
        return (float) Math.acos(Math.max(-1, Math.min(1, cosTheta))); // Clamp for precision errors
    }

    // Return a new Vector3 that is 'percentage' percent between this vector and target
    public Vector3 follow(Vector3 target, float percentage) {
        percentage = Math.max(0, Math.min(1, percentage));
        float newX = this.x + (target.x - this.x) * percentage;
        float newY = this.y + (target.y - this.y) * percentage;
        float newZ = this.z + (target.z - this.z) * percentage;
        return new Vector3(newX, newY, newZ);
    }

    public Vector2 toVector2() {
        return new Vector2(x, y);
    }
}
