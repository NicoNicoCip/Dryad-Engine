package main.java.com.pws.dryadengine.core;

import main.java.com.pws.dryadengine.func.SLD.CollisionManager2D;
import main.java.com.pws.dryadengine.func.SLD.Input;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;
import main.java.com.pws.dryadengine.func.SLD.Window;

import static io.github.libsdl4j.api.video.SDL_WindowFlags.*;

import main.java.com.pws.dryadengine.types.Color;
import main.java.com.pws.dryadengine.types.Node2D;
import main.java.com.pws.dryadengine.types.Rect2D;
import main.java.com.pws.dryadengine.types.Rect2DCollider;

import static io.github.libsdl4j.api.render.SDL_RendererFlags.*;
import static io.github.libsdl4j.api.render.SdlRender.*;

public class FrontendEnv implements Runnable {
    private static final float moveSpeed = 0.5f;

    public static void testCode() {
        
        Window.createSDLEnvironment();
        SDL_Window window = Window.createWindow("Test Window", 200, 200, 1280, 720, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        SDL_Renderer renderer = Window.createRenderer(window, -1, SDL_RENDERER_ACCELERATED);
        Window.setBackgroundColor(new Color(255, 255, 255, 0));

        Node2D root = new Node2D();

        Rect2D rect1 = new Rect2D(renderer,100, 200, 50, 50, new Color(0, 0, 255, 255));
        Rect2D rect2 = new Rect2D(renderer,400, 200, 50, 50, new Color(255, 0, 0, 255));

        Rect2DCollider collider1 = new Rect2DCollider(rect1, rect1.size, false);
        Rect2DCollider collider2 = new Rect2DCollider(rect2, rect2.size, true);
        
        root.addChild(rect1);
        root.addChild(rect2);
        rect1.addChild(collider1);
        rect2.addChild(collider2);

        CollisionManager2D.collisionChain.add(collider1);
        CollisionManager2D.collisionChain.add(collider2);

        while (!App.finnishExecution) {
            Window.runEvents();

            if (Input.checkKey(Input._w,Input.State.held))      { rect1.position.y-= moveSpeed;}
            if (Input.checkKey(Input._s,Input.State.held))      { rect1.position.y+= moveSpeed;}
            if (Input.checkKey(Input._a,Input.State.held))      { rect1.position.x-= moveSpeed;}
            if (Input.checkKey(Input._d,Input.State.held))      { rect1.position.x+= moveSpeed;}

            if (Input.checkKey(Input._up,   Input.State.held))  { rect2.position.y-= moveSpeed;}
            if (Input.checkKey(Input._down, Input.State.held))  { rect2.position.y+= moveSpeed;}
            if (Input.checkKey(Input._left, Input.State.held))  { rect2.position.x-= moveSpeed;}
            if (Input.checkKey(Input._right,Input.State.held))  { rect2.position.x+= moveSpeed;}

            //if (Input.checkKey(Input._i, Input.State.pressed))  {rect2.scale = new Vector2(rect2.scale.x + 0.1f, rect2.scale.y + 0.1f); }
            //if (Input.checkKey(Input._o, Input.State.pressed))  {rect2.scale = new Vector2(rect2.scale.x - 0.1f, rect2.scale.y - 0.1f); }

            SDL_RenderClear(renderer);

            rect1.updateRect(renderer);
            rect2.updateRect(renderer);

            CollisionManager2D.updateAllCollisions();

            Window.drawBackground(renderer);
            SDL_RenderPresent(renderer);
        }
    }

    @Override
    public void run() {
        testCode();
    }
}