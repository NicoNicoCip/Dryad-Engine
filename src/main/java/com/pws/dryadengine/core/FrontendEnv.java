package main.java.com.pws.dryadengine.core;

import main.java.com.pws.dryadengine.func.Input;
import main.java.com.pws.dryadengine.func.Window;

import main.java.com.pws.dryadengine.physics.CollisionManager;
import main.java.com.pws.dryadengine.physics.Rect2DCollider;
import main.java.com.pws.dryadengine.physics.Rect2DCollider.ColliderType;
import main.java.com.pws.dryadengine.types.Camera;
import main.java.com.pws.dryadengine.types.Color;
import main.java.com.pws.dryadengine.types.Node;
import main.java.com.pws.dryadengine.types.Rect2D;

public class FrontendEnv implements Runnable {
    private static final float moveSpeed = 0.5f;
    private static final float gravity = 2f;
    private static final float jumpForce = 2f;

    public static void testCode() {
        Window.create("Test Window", 200, 200, 1280, 720);
        Window.setBackgroundColor(new Color(255, 255, 255, 0));

        Node root = new Node();
        CollisionManager cm = new CollisionManager(Window.scale.x, Window.scale.y);

        Rect2D player = new Rect2D(100, 0, 50, 50, new Color(0, 0, 255, 255));
        Rect2D ground = new Rect2D(0, 400, 1000, 300, new Color(120, 255, 120, 255));
        Rect2D center = new Rect2D(Window.scale.x / 2, Window.scale.y / 2, 1,1,new Color(255, 0, 0, 100));

        Rect2DCollider collider1 = new Rect2DCollider(player, ColliderType.DYNAMIC);
        Rect2DCollider collider2 = new Rect2DCollider(ground, ColliderType.STATIC);
        
        root.addChild(player);
        root.addChild(ground);

        cm.addCollider(collider1);
        cm.addCollider(collider2);

        Camera cam = new Camera();
        cam.follow = true;
        cam.center = true;
        player.addChild(cam);
        cam.update();

        float gforce = 0;

        while (!App.finnishExecution) {
            Window.runEvents();
            
            if(!cm.isColliding(collider2) && gforce <= 0) {
                player.position.y -= gravity * gforce;
                gforce -= 0.005f;
            } else if(!cm.isColliding(collider2) && gforce <= 1) {
                player.position.y -= jumpForce * gforce;
                gforce -= 0.005f;
            } else {
                gforce = 0;
            }

            if (Input.checkKey(Input._space,Input.State.held) && cm.isColliding(collider2))      { gforce = 1;}
            if (Input.checkKey(Input._a,Input.State.held))      { player.position.x-= moveSpeed;}
            if (Input.checkKey(Input._d,Input.State.held))      { player.position.x+= moveSpeed;}

            cm.resolveCollisions();

            Window.clear();
            
            cam.updateLerped(0.01f);
            player.updateRect();
            ground.updateRect();
            center.updateRect();
            
            Window.drawBackground();
            Window.print();
        }
    }

    @Override
    public void run() {
        testCode();
    }
}