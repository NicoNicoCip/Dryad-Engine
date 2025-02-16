package main.java.com.pws.dryadengine.types;

public class Vector2 {
    public float x;
    public float y;

    // Constructors
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

    // Set the values of this vector from another vector
    public void set(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    // To String representation
    @Override
    public String toString() {
        return "{" +
            " x='" + this.x + "'" +
            ", y='" + this.y + "'" +
            "}";
    }

    // Data representation for debugging or logging
    public String toData(){
        return "(" + this.x + "," + this.y + ")";
    }

    // Calculate the distance to another point
    public float distanceTo(Vector2 point) {
        float a = Math.abs(this.x - point.x);
        float b = Math.abs(this.y - point.y);
        return (float)Math.sqrt((a*a) + (b*b));
    }

    // Addition of two vectors
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    // Subtraction of two vectors
    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    // Scalar multiplication
    public Vector2 scalar(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    // Scalar multiplication
    public Vector2 multiply(Vector3 other) {
        return new Vector2(this.x * other.x, this.y * other.z);
    }


    // Dot product of two vectors
    public float dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    // Length (magnitude) of the vector
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    // Normalize the vector (make it a unit vector)
    public Vector2 normalize() {
        float len = this.length();
        if (len == 0) {
            return new Vector2(0, 0); // Avoid division by zero
        }
        return new Vector2(this.x / len, this.y / len);
    }

    // Return a vector perpendicular to this one
    public Vector2 perpendicular() {
        return new Vector2(-this.y, this.x); // 90-degree rotation
    }

    // Calculate the angle between two vectors (in radians)
    public float angle(Vector2 other) {
        float dotProduct = this.dot(other);
        float lengthsMultiplied = this.length() * other.length();
        if (lengthsMultiplied == 0) {
            return 0; // Avoid division by zero
        }
        float cosTheta = dotProduct / lengthsMultiplied;
        return (float) Math.acos(Math.max(-1, Math.min(1, cosTheta))); // Clamp value for precision errors
    }

    // Return a new Vector2 that is 'percentage' percent between this vector and target
    public Vector2 follow(Vector2 target, float percentage) {
        percentage = Math.max(0, Math.min(1, percentage)); // Clamp percentage between 0 and 1
        float newX = this.x + (target.x - this.x) * percentage;
        float newY = this.y + (target.y - this.y) * percentage;
        return new Vector2(newX, newY);
    }

    public Vector3 toVector3() {
        return new Vector3(x, y, 0);
    }
}
