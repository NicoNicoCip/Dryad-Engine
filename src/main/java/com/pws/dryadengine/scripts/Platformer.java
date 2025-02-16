package main.java.com.pws.dryadengine.scripts;

import main.java.com.pws.dryadengine.core.scripts.Script;
import main.java.com.pws.dryadengine.func.Input;
import main.java.com.pws.dryadengine.func.Window;
import main.java.com.pws.dryadengine.physics.CollisionManager;
import main.java.com.pws.dryadengine.physics.Rect2DCollider;
import main.java.com.pws.dryadengine.physics.Rect2DCollider.ColliderType;
import main.java.com.pws.dryadengine.types.Camera;
import main.java.com.pws.dryadengine.types.Color;
import main.java.com.pws.dryadengine.types.Node;
import main.java.com.pws.dryadengine.types.Rect2D;

public class Platformer extends Script {

    @Override
    public void setPC() {
        this.pc = 0;
    }

    private final float moveSpeed = 0.5f;
    private final float gravity = 2f;
    private final float jumpForce = 2f;

    private float gforce = 0;
    
    private Node root = new Node();
    private CollisionManager cm;

    private Rect2D player = new Rect2D(100, 0, 50, 50, new Color(0, 0, 255, 255));
    private Rect2D ground = new Rect2D(0, 400, 1000, 300, new Color(120, 255, 120, 255));
    private Rect2D center;

    private Rect2DCollider collider1 = new Rect2DCollider(player, ColliderType.DYNAMIC);
    private Rect2DCollider collider2 = new Rect2DCollider(ground, ColliderType.STATIC);

    private Camera cam = new Camera();

    @Override
    public void plant() {
        cm = new CollisionManager(Window.scale.x, Window.scale.y);
        center = new Rect2D(Window.scale.x / 2, Window.scale.y / 2, 1,1,new Color(255, 0, 0, 100));

        root.addChild(player);
        root.addChild(ground);
        
        cm.addCollider(collider1);
        cm.addCollider(collider2);

        cam.follow = true;
        cam.center = true;
        player.addChild(cam);
        cam.update();
    }

    @Override
    public void grow() {
        
        
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
        
        cam.updateLerped(0.01f);
        player.updateRect();
        ground.updateRect();
        center.updateRect(); 
        
    } 
}
