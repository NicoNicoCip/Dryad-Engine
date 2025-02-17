package main.java.com.pws.dryadengine.physics;

import java.util.List;

import main.java.com.pws.dryadengine.func.Window;
import main.java.com.pws.dryadengine.types.Vector2;

import java.util.ArrayList;

public class CollisionManager_Rect2D {
    private static CollisionManager_Rect2D instance;
    private QuadTree quadTree;
    private List<Rect2DCollider> colliders;

    //TODO: add threading collision detection.

    private CollisionManager_Rect2D() {
        colliders = new ArrayList<>();
        quadTree = new QuadTree(0, 
            new Vector2(),
            Window.scale
        );
    }
    
    public static CollisionManager_Rect2D getInstance() {
        if (instance == null) {
            instance = new CollisionManager_Rect2D();
        }
        return instance;
    }
    
    public void registerCollider(Rect2DCollider collider) {
        colliders.add(collider);
    }
    
    public void update() {
        quadTree.clear();
        
        // Insert all colliders into quad tree
        for (Rect2DCollider collider : colliders) {
            quadTree.insert(collider);
        }
        
        // Check collisions
        List<Rect2DCollider> returnObjects = new ArrayList<>();
        for (Rect2DCollider collider : colliders) {
            collider.isColliding = false;
            returnObjects.clear();
            quadTree.retrieve(returnObjects, collider);
            
            for (Rect2DCollider other : returnObjects) {
                collider.isColliding = false;
                if (collider != other && collider.intersects(other)) {
                    handleCollision(collider, other);
                }
            }
        }
    }
    
    private void handleCollision(Rect2DCollider a, Rect2DCollider b) {
        a.isColliding = true;
        b.isColliding = true;
    
        // Skip collision resolution if either collider is a trigger
        if (a.isTrigger || b.isTrigger) {
            a.onTriggerEnter(b);
            b.onTriggerEnter(a);
            return;
        }
    
        // Skip if both objects are static
        if (a.isStatic && b.isStatic) return;
    
        Vector2 aPos = a.getGlobalPosition().toVector2();
        Vector2 bPos = b.getGlobalPosition().toVector2();
        Vector2 aSize = a.getGlobalScale().toVector2();
        Vector2 bSize = b.getGlobalScale().toVector2();
    
        // Calculate overlap on both axes
        float overlapX = Math.min(aPos.x + aSize.x, bPos.x + bSize.x) - Math.max(aPos.x, bPos.x);
        float overlapY = Math.min(aPos.y + aSize.y, bPos.y + bSize.y) - Math.max(aPos.y, bPos.y);
    
        // Resolve collision based on the smallest overlap
        if (overlapX < overlapY) {
            if (!a.isStatic) a.position.x += (aPos.x < bPos.x) ? -overlapX : overlapX;
            if (!b.isStatic) b.position.x += (bPos.x < aPos.x) ? -overlapX : overlapX;
        } else {
            if (!a.isStatic) a.position.y += (aPos.y < bPos.y) ? -overlapY : overlapY;
            if (!b.isStatic) b.position.y += (bPos.y < aPos.y) ? -overlapY : overlapY;
        }
    
        // Call collision callbacks
        a.onCollisionEnter(b);
        b.onCollisionEnter(a);
    }
}
