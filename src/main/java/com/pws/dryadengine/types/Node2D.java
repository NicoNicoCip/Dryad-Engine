package main.java.com.pws.dryadengine.types;

import java.util.ArrayList;
import java.util.List;

public class Node2D {
    public Vector2 position = new Vector2(0, 0);
    public float rotation = 0.0f; // Rotation is unimplemented for now
    
    private Node2D parent;
    private List<Node2D> children = new ArrayList<>();

    public Node2D() {}

    public Node2D(Vector2 position) {
        this.position = position;
    }

    public void addChild(Node2D child) {
        if (child.parent != null) {
            child.parent.removeChild(child);
        }
        child.parent = this;
        children.add(child);
    }

    public void removeChild(Node2D child) {
        children.remove(child);
        child.parent = null;
    }

    public Vector2 getGlobalPosition() {
        if (parent == null) {
            return position;
        }
        return new Vector2(parent.getGlobalPosition().x + position.x, parent.getGlobalPosition().y + position.y);
    }

    public Node2D getParent() {
        return parent;
    }

    public List<Node2D> getChildren() {
        return children;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
