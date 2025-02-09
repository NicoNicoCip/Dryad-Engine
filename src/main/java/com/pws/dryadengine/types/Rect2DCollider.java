package main.java.com.pws.dryadengine.types;

public class Rect2DCollider extends Node2D {
    public Rect2D rect;
    public Vector2 size;
    public boolean isStatic;
    public Vector2 lastSafePosition;

    public Rect2DCollider() {}

    public Rect2DCollider(Rect2D rect, Vector2 size, boolean isStatic) {
        this.rect = rect;
        this.size = size;
        this.isStatic = isStatic;
        this.position = rect.position;
        this.lastSafePosition = new Vector2(position.x, position.y);
    }

    public boolean checkCollision(Rect2DCollider other) {
        return (this.position.x < other.position.x + other.size.x && this.position.x + this.size.x > other.position.x &&
                this.position.y < other.position.y + other.size.y && this.position.y + this.size.y > other.position.y);
    }
}
