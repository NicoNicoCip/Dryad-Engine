package main.java.com.pws.dryadengine.physics;

import main.java.com.pws.dryadengine.types.*;

public class Rect2DCollider extends Node {
    public enum ColliderType { STATIC, DYNAMIC }
    private Rect2D rect;
    private ColliderType type;

    public Rect2DCollider(Rect2D rect, ColliderType type) {
        super();
        this.rect = rect;
        this.type = type;
    }

    public boolean isStatic() {
        return type == ColliderType.STATIC;
    }

    public Rect2D getRect() {
        return rect;
    }
}