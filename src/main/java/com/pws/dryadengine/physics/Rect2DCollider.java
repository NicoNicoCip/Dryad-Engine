package com.pws.dryadengine.physics;

import com.pws.dryadengine.types.*;

public class Rect2DCollider extends Node {
    public boolean isColliding = false;
    public boolean isStatic = false;
    public boolean isTrigger = false;
    private CollisionListener listener;

    public Rect2DCollider() {
        CollisionManager_Rect2D.getInstance().registerCollider(this);
    }
    
    public Vector2[] getCorners() {
        Vector2 size = getGlobalScale().toVector2();
        float halfW = size.x / 2;
        float halfH = size.y / 2;
        
        Vector3 globalPos = getGlobalPosition();
        Euler globalRot = getGlobalRotation();
        
        Vector2[] corners = new Vector2[4];
        Vector3[] unrotatedCorners = {
            new Vector3(-halfW, -halfH, 0),
            new Vector3(halfW, -halfH, 0),
            new Vector3(halfW, halfH, 0),
            new Vector3(-halfW, halfH, 0)
        };
        
        for (int i = 0; i < 4; i++) {
            Vector3 rotated = globalRot.rotateVector(unrotatedCorners[i]);
            corners[i] = new Vector2(
                rotated.x + globalPos.x,
                rotated.y + globalPos.y
            );
        }
        
        return corners;
    }
    
    public boolean intersects(Rect2DCollider other) {
        Vector2[] cornersA = this.getCorners();
        Vector2[] cornersB = other.getCorners();
        
        return checkSeparatingAxisTheorem(cornersA, cornersB);
    }
    
    private boolean checkSeparatingAxisTheorem(Vector2[] cornersA, Vector2[] cornersB) {
        Vector2[] axes = getAxes(cornersA, cornersB);
        
        for (Vector2 axis : axes) {
            float[] projectionA = projectOntoAxis(cornersA, axis);
            float[] projectionB = projectOntoAxis(cornersB, axis);
            
            if (!overlaps(projectionA, projectionB)) {
                return false;
            }
        }
        
        return true;
    }
    
    private Vector2[] getAxes(Vector2[] cornersA, Vector2[] cornersB) {
        Vector2[] axes = new Vector2[4];
        
        // Get the axes from rectangle A
        axes[0] = getNormal(cornersA[0], cornersA[1]);
        axes[1] = getNormal(cornersA[1], cornersA[2]);
        
        // Get the axes from rectangle B
        axes[2] = getNormal(cornersB[0], cornersB[1]);
        axes[3] = getNormal(cornersB[1], cornersB[2]);
        
        return axes;
    }
    
    private Vector2 getNormal(Vector2 p1, Vector2 p2) {
        Vector2 edge = new Vector2(p2.x - p1.x, p2.y - p1.y);
        Vector2 normal = new Vector2(-edge.y, edge.x);
        float length = (float)Math.sqrt(normal.x * normal.x + normal.y * normal.y);
        return new Vector2(normal.x / length, normal.y / length);
    }
    
    private float[] projectOntoAxis(Vector2[] corners, Vector2 axis) {
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;
        
        for (Vector2 corner : corners) {
            float projection = corner.x * axis.x + corner.y * axis.y;
            min = Math.min(min, projection);
            max = Math.max(max, projection);
        }
        
        return new float[]{min, max};
    }
    
    private boolean overlaps(float[] projectionA, float[] projectionB) {
        return !(projectionA[1] < projectionB[0] || projectionB[1] < projectionA[0]);
    }

    public void setCollisionListener(CollisionListener listener) {
        this.listener = listener;
    }
    
    public void onCollisionEnter(Rect2DCollider other) {
        if (listener != null) {
            listener.onCollisionEnter(other);
        }
    }
    
    public void onTriggerEnter(Rect2DCollider other) {
        if (listener != null) {
            listener.onTriggerEnter(other);
        }
    }
}
