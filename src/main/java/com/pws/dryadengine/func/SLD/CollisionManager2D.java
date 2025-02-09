package main.java.com.pws.dryadengine.func.SLD;

import java.util.ArrayList;
import java.util.List;
import main.java.com.pws.dryadengine.types.Rect2DCollider;
import main.java.com.pws.dryadengine.types.Vector2;

public class CollisionManager2D {
    public static List<Rect2DCollider> collisionChain = new ArrayList<>();
    private static KDTree2D kdTree = new KDTree2D();

    public static void updateAllCollisions() {
        // Build the KD-Tree with the current colliders
        kdTree.buildTree(collisionChain);

        for (Rect2DCollider collider : collisionChain) {
            // Save last known safe position (before resolving collisions)
            if (!isColliding(collider)) {
                collider.lastSafePosition.set(collider.position);
            }
        }

        // Now, for each collider, we query the KD-Tree for nearby colliders
        for (Rect2DCollider self : collisionChain) {
            // Query KD-Tree for colliders that are close to `self`
            List<Rect2DCollider> nearbyColliders = kdTree.query(self);

            for (Rect2DCollider other : nearbyColliders) {
                if (self != other && self.checkCollision(other)) {
                    // Save last known safe position for dynamic objects before resolving collisions
                    if (!self.isStatic) {
                        self.lastSafePosition.set(self.position);
                    }
                    if (!other.isStatic) {
                        other.lastSafePosition.set(other.position);
                    }

                    // Calculate the minimum translation vector (MTV)
                    float overlapX1 = (self.position.x + self.size.x) - other.position.x;
                    float overlapX2 = (other.position.x + other.size.x) - self.position.x;
                    float overlapY1 = (self.position.y + self.size.y) - other.position.y;
                    float overlapY2 = (other.position.y + other.size.y) - self.position.y;

                    float mtvX = (Math.abs(overlapX1) < Math.abs(overlapX2)) ? overlapX1 : -overlapX2;
                    float mtvY = (Math.abs(overlapY1) < Math.abs(overlapY2)) ? overlapY1 : -overlapY2;

                    Vector2 selfPos = self.position;
                    Vector2 otherPos = other.position;

                    // Resolve collision by adjusting positions
                    if (Math.abs(mtvX) < Math.abs(mtvY)) {
                        if (!self.isStatic && other.isStatic) {
                            selfPos.x -= mtvX; // Move non-static object
                        } else if (self.isStatic && !other.isStatic) {
                            otherPos.x += mtvX; // Move non-static object
                        } else if (!self.isStatic && !other.isStatic) {
                            selfPos.x -= mtvX;
                            otherPos.x += mtvX; // Both adjust
                        } else {
                            selfPos.x -= mtvX; // Move non-static object
                            otherPos.x -= mtvX; // Move non-static object
                        }
                    } else {
                        if (!self.isStatic && other.isStatic) {
                            selfPos.y -= mtvY;
                        } else if (self.isStatic && !other.isStatic) {
                            otherPos.y += mtvY;
                        } else if (!self.isStatic && !other.isStatic) {
                            selfPos.y -= mtvY;
                            otherPos.y += mtvY;
                        } else {
                            selfPos.y -= mtvY;
                            otherPos.y -= mtvY;
                        }
                    }

                    // **Check if objects are still stuck after resolution**
                    if (self.checkCollision(other)) {
                        // If they are still colliding, we need a more nuanced resolution.
                        // Instead of fully restoring to the last safe position, let's apply a threshold.

                        // Allow a small threshold of overlap (e.g., 1 pixel), as a tolerance for minor issues
                        float tolerance = 1.0f;
                        if (Math.abs(self.position.x - other.position.x) < tolerance && Math.abs(self.position.y - other.position.y) < tolerance) {
                            // If the objects are within the tolerance, let's leave them in place
                            // or move them back slightly, but not to the original safe position.
                            // You can decide if you want to apply a small adjustment.
                        } else {
                            // If objects are still colliding and it's not a minor overlap, revert to the safe position
                            selfPos.x = self.lastSafePosition.x;
                            selfPos.y = self.lastSafePosition.y;
                            otherPos.x = other.lastSafePosition.x;
                            otherPos.y = other.lastSafePosition.y;
                        }
                    }


                    self.setPosition(selfPos);
                    other.setPosition(otherPos);
                }
            }
        }
    }

    // This is no longer needed since the KD-Tree optimizes collision checks
    private static boolean isColliding(Rect2DCollider collider) {
        for (Rect2DCollider other : collisionChain) {
            if (collider != other && collider.checkCollision(other)) {
                return true;
            }
        }
         return false;
    }
}
