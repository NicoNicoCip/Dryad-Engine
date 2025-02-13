package main.java.com.pws.dryadengine.types;

import java.util.ArrayList;
import java.util.List;

import main.java.com.pws.dryadengine.func.Debug;

public class Node {
    public Vector2 position = new Vector2();
    public float rotation = 0.0f; // Rotation is unimplemented for now
    public float scale = 1.0f;
    public Vector2 size;
    
    public Node parent = null;
    public List<Node> children = new ArrayList<>();

    public void addChild(Node child) {
        if (child.parent != null) {
            child.parent.removeChild(child);
        }
        child.parent = this;
        children.add(child);
    }

    public void removeChild(Node child) {
        children.remove(child);
        child.parent = null;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getRoot() {
        return this.parent == null ? this : this.parent.getRoot();
    }
    
}
