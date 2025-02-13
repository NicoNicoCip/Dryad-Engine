package main.java.com.pws.dryadengine.types;

import main.java.com.pws.dryadengine.func.Window;

public class Camera extends Node {
    public float zoom = 1.0f;
    public Vector2 offset = new Vector2();
    public boolean center;
    public boolean follow;
    private Node root;

    private Vector2 getViewOffset() {
        if(center) offset = new Vector2(-Window.scale.x/2 + this.parent.size.x / 2, -Window.scale.y/2 + this.parent.size.y / 2);
        if(follow) position = this.parent.position;

        return new Vector2(-position.x - offset.x, -position.y - offset.y);
    }

    public void update() {
        if(root == null) root = this.getRoot();
        root.position = getViewOffset();
    }

    public void updateLerped(float amount) {
        if(root == null) root = this.getRoot();
        root.position = root.position.follow( getViewOffset(), amount);
    }
}

