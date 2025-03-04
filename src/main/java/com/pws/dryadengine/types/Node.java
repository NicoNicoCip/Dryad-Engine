package com.pws.dryadengine.types;

import java.util.ArrayList;
import java.util.List;

public class Node{
    public Vector3 position = new Vector3(0, 0, 0);
    public Vector3 origin = new Vector3(0, 0, 0);
    public Euler rotation = new Euler(0, 0, 0);
    public Vector3 scale = new Vector3(1, 1, 1);

    public Node parent = null;
    public List<Node> children = new ArrayList<>();

    public Node() {}

    public Node(Vector3 position, Euler rotation, Vector3 scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void addChild(Node child)  {
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

    // Get global position (relative to root)
    public Vector3 getGlobalPosition() {
        if (parent == null) return position;
        Vector3 parentPos = parent.getGlobalPosition();
        return new Vector3(parentPos.x + position.x, parentPos.y + position.y, parentPos.z + position.z);
    }

    // Get global rotation (relative to root)
    public Euler getGlobalRotation() {
        if (parent == null) return rotation;
        Euler parentRot = parent.getGlobalRotation();
        return new Euler(parentRot.getX() + rotation.getX(), parentRot.getY() + rotation.getY(), parentRot.getZ() + rotation.getZ());
    }

    // Get global scale (relative to root)
    public Vector3 getGlobalScale() {
        if (parent == null) return scale;
        Vector3 parentScale = parent.getGlobalScale();
        return new Vector3(parentScale.x * scale.x, parentScale.y * scale.y, parentScale.z * scale.z);
    }

    @Override
    public String toString() {
        return "TransformNode(Position: " + position + ", Rotation: " + rotation + ", Scale: " + scale + ")";
    }
}
