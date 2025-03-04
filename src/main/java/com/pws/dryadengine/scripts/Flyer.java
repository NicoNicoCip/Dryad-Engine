package com.pws.dryadengine.scripts;

import com.pws.dryadengine.core.App;
import com.pws.dryadengine.core.scripts.Script;
import com.pws.dryadengine.func.Input;
import com.pws.dryadengine.func.Window;
import com.pws.dryadengine.physics.Rect2DCollider;
import com.pws.dryadengine.types.Camera;
import com.pws.dryadengine.types.Color;
import com.pws.dryadengine.types.Euler;
import com.pws.dryadengine.types.Rect2D;
import com.pws.dryadengine.physics.CollisionListener;

public class Flyer extends Script {
    @Override
    public void setPC() {
        this.pc = 0;
    }
    
     private final float moveSpeed = 0.05f;
    private Rect2D player = new Rect2D(0, 0, 50, 50, new Color(0, 0, 255, 255));
    private Rect2D ground = new Rect2D(0, 200, 1000, 300, new Color(120, 255, 120, 255));
    private Rect2D center;
    private Rect2D origin = new Rect2D(0,0, 10, 10, new Color(0, 0, 255, 255));

    private Rect2DCollider collider1 = new Rect2DCollider();
    private Rect2DCollider collider2 = new Rect2DCollider();

    private Camera cam = new Camera();

    @Override
    public void plant() {

        center = new Rect2D(Window.scale.x / 2, Window.scale.y / 2, 5, 5, new Color(1, 1, 1, 10));

        App.root.addChild(player);
        App.root.addChild(ground);
        App.root.addChild(origin);

        player.addChild(collider1);
        player.position.z = 1.0f;
        player.rotation.setY(0.5f);
        ground.addChild(collider2);

        collider1.setCollisionListener(new CollisionListener() {
            @Override
            public void onCollisionEnter(Rect2DCollider other) {

            }

            @Override
            public void onTriggerEnter(Rect2DCollider other) {

            }
        });

        cam.follow = true;
        cam.center = true;
        player.addChild(cam);
        cam.update();
    }

    float spinner = 0;
    @Override
    public void grow() {
        if (Input.checkKey(Input._a,Input.State.held))      { player.position.x-= moveSpeed;}
        if (Input.checkKey(Input._d,Input.State.held))      { player.position.x+= moveSpeed;}
        if (Input.checkKey(Input._w,Input.State.held))      { player.position.y-= moveSpeed;}
        if (Input.checkKey(Input._s,Input.State.held))      { player.position.y+= moveSpeed;}
        
        cam.updateLerped(0.005f);
        player.updateRect();
        ground.updateRect();
        ground.rotation = new Euler(0, 0, 0.5f);
        center.updateRect(); 
        origin.updateRect();
        spinner+= 0.05f;
        center.rotation = new Euler(0, 0, spinner);
    } 
}
