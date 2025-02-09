package main.java.com.pws.dryadengine.types;

public class Camera extends Node2D {
    public boolean focused = false;
    // position defines the position in of the camera in relation to the world
    // scale defines how zoomed in it is.
    public Node2D followNode;

    public Camera() {

    }

    public Camera(boolean focused) {
        this.focused = focused;
    }

    public Camera(boolean focused, Node2D followNode) {
        this.focused = focused;
        this.followNode = followNode;
    }

    public void updateCamera(Node2D root) {
        if(this.focused) {
            root.position.x = -this.position.x;
            root.position.y = -this.position.y;

            if(followNode != null) {
                followNode.position = new Vector2(this.getGlobalPosition());
            }
        }
    }
}
