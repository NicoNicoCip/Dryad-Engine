package com.pws.dryadengine.scripts;

import com.pws.dryadengine.core.App;
import com.pws.dryadengine.core.scripts.Script;
import com.pws.dryadengine.func.Input;
import com.pws.dryadengine.func.Window;
import com.pws.dryadengine.physics.CollisionManager_Rect2D;
import com.pws.dryadengine.physics.Rect2DCollider;
import com.pws.dryadengine.types.Camera;
import com.pws.dryadengine.types.Color;
import com.pws.dryadengine.types.Node;
import com.pws.dryadengine.types.Rect2D;

public class Platformer extends Script {

    @Override
    public void setPC() {
        this.pc = -1;
    }

    private final float moveSpeed = 0.5f;
    private final float gravity = 2f;
    private final float jumpForce = 2f;

    private float gforce = 0;

    private Rect2D player = new Rect2D(100, 0, 50, 50, new Color(0, 0, 255, 255));
    private Rect2D ground = new Rect2D(0, 200, 1000, 300, new Color(120, 255, 120, 255));
    private Rect2D center;

    private Rect2DCollider collider1 = new Rect2DCollider();
    private Rect2DCollider collider2 = new Rect2DCollider();

    private Camera cam = new Camera();

    @Override
    public void plant() {
        center = new Rect2D(0,0, 1,1,new Color(255, 0, 0, 100));

        App.root.addChild(player);
        App.root.addChild(ground);

        cam.follow = true;
        cam.center = true;
        player.addChild(cam);
        cam.update();
    }

    @Override
    public void grow() {
        if(!collider2.isColliding && gforce <= 0) {
            player.position.y -= gravity * gforce;
            gforce -= 0.005f;
        } else if(!collider2.isColliding && gforce <= 1) {
            player.position.y -= jumpForce * gforce;
            gforce -= 0.005f;
        } else {
            gforce = 0;
        }

        if (Input.checkKey(Input._space,Input.State.held) && collider2.isColliding)      { gforce = 1;}
        if (Input.checkKey(Input._a,Input.State.held))      { player.position.x-= moveSpeed;}
        if (Input.checkKey(Input._d,Input.State.held))      { player.position.x+= moveSpeed;}
        
        cam.updateLerped(0.01f);
        player.updateRect();
        ground.updateRect();
        center.updateRect();
    } 
}
