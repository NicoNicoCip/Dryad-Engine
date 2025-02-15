package main.java.com.pws.dryadengine.physics;

import java.util.List;
import java.util.Set;

import main.java.com.pws.dryadengine.types.Rect2D;

import java.util.HashSet;

public class CollisionManager {
    private Set<Rect2DCollider> colliders = new HashSet<>();
    private QuadTree quadTree;

    public CollisionManager(float worldWidth, float worldHeight) {
        this.quadTree = new QuadTree(0, 0, 0, worldWidth, worldHeight);
    }

    public void addCollider(Rect2DCollider collider) {
        colliders.add(collider);
        quadTree.insert(collider);
    }

    public void removeCollider(Rect2DCollider collider) {
        colliders.remove(collider);
        quadTree.remove(collider);
    }

    public boolean isColliding(Rect2DCollider collider) {
        List<Rect2DCollider> possibleCollisions = quadTree.retrieve(collider);
        for (Rect2DCollider other : possibleCollisions) {
            if (other != collider && checkCollision(collider, other)) {
                return true;
            }
        }
        return false;
    }

    public void resolveCollisions() {
        quadTree.clear();
        for (Rect2DCollider collider : colliders) {
            quadTree.insert(collider);
        }
        
        for (Rect2DCollider collider : colliders) {
            if (!collider.isStatic()) {
                List<Rect2DCollider> possibleCollisions = quadTree.retrieve(collider);
                for (Rect2DCollider other : possibleCollisions) {
                    if (collider != other && checkCollision(collider, other)) {
                        separateColliders(collider, other);
                    }
                }
            }
        }
    }

    private boolean checkCollision(Rect2DCollider a, Rect2DCollider b) {
        Rect2D rectA = a.getRect();
        Rect2D rectB = b.getRect();
    
        return rectA.position.x < rectB.position.x + rectB.scale.x &&
               rectA.position.x + rectA.scale.x > rectB.position.x &&
               rectA.position.y < rectB.position.y + rectB.scale.y &&
               rectA.position.y + rectA.scale.y > rectB.position.y;
    }
    
    private void separateColliders(Rect2DCollider a, Rect2DCollider b) {
        if (a.isStatic()) {
            // Static colliders don't move, adjust only b
            pushOut(b, a);
        } else if (b.isStatic()) {
            // Static colliders don't move, adjust only a
            pushOut(a, b);
        } else {
            // Both are dynamic, separate them equally
            pushOutBoth(a, b);
        }
    }
    
    private void pushOut(Rect2DCollider dynamic, Rect2DCollider staticCollider) {
        Rect2D rectA = dynamic.getRect();
        Rect2D rectB = staticCollider.getRect();
    
        float overlapX = Math.min(rectA.position.x + rectA.scale.x - rectB.position.x,
                                  rectB.position.x + rectB.scale.x - rectA.position.x);
        float overlapY = Math.min(rectA.position.y + rectA.scale.y - rectB.position.y,
                                  rectB.position.y + rectB.scale.y - rectA.position.y);
    
        if (overlapX < overlapY) {
            if (rectA.position.x < rectB.position.x) {
                rectA.position.x -= overlapX;
            } else {
                rectA.position.x += overlapX;
            }
        } else {
            if (rectA.position.y < rectB.position.y) {
                rectA.position.y -= overlapY;
            } else {
                rectA.position.y += overlapY;
            }
        }
    }
    
    private void pushOutBoth(Rect2DCollider a, Rect2DCollider b) {
        Rect2D rectA = a.getRect();
        Rect2D rectB = b.getRect();
    
        float overlapX = Math.min(rectA.position.x + rectA.scale.x - rectB.position.x,
                                  rectB.position.x + rectB.scale.x - rectA.position.x);
        float overlapY = Math.min(rectA.position.y + rectA.scale.y - rectB.position.y,
                                  rectB.position.y + rectB.scale.y - rectA.position.y);
    
        if (overlapX < overlapY) {
            if (rectA.position.x < rectB.position.x) {
                rectA.position.x -= overlapX / 2;
                rectB.position.x += overlapX / 2;
            } else {
                rectA.position.x += overlapX / 2;
                rectB.position.x -= overlapX / 2;
            }
        } else {
            if (rectA.position.y < rectB.position.y) {
                rectA.position.y -= overlapY / 2;
                rectB.position.y += overlapY / 2;
            } else {
                rectA.position.y += overlapY / 2;
                rectB.position.y -= overlapY / 2;
            }
        }
    }
    
}
