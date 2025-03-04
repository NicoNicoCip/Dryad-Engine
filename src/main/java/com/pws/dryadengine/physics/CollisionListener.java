package com.pws.dryadengine.physics;

public interface CollisionListener {
    void onCollisionEnter(Rect2DCollider other);
    void onTriggerEnter(Rect2DCollider other);
}
