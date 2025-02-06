package com.pws.dryadengine.core;

import com.pws.dryadengine.func.SLD.Input;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;
import com.pws.dryadengine.func.SLD.Window;

import static io.github.libsdl4j.api.video.SDL_WindowFlags.*;
import com.pws.dryadengine.types.Vector2;
import io.github.libsdl4j.api.rect.SDL_Rect;

import static io.github.libsdl4j.api.render.SDL_RendererFlags.*;
import static io.github.libsdl4j.api.render.SdlRender.*;

public class FrontendEnv implements Runnable {

    private static Vector2 pos1 = new Vector2(50,50);
    private static Vector2 pos2 = new Vector2(200,50);
    
    public static void testCode() {
        Window.createSDLEnvironment();
        SDL_Window window = Window.createWindow("Test Window", 200, 200, 1280, 720, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        SDL_Renderer renderer = Window.createRenderer(window, -1, SDL_RENDERER_ACCELERATED);

        SDL_Rect rect1 = Window.createRect(pos1, new Vector2(50, 50));
        SDL_Rect rect2 = Window.createRect(pos2, new Vector2(50, 50));
        SDL_RenderDrawRect(renderer, rect1);
        SDL_RenderDrawRect(renderer, rect2);
        while (!App.finnishExecution) {
            Window.runEvents();

            if (Input.checkKey(Input._w,Input.State.pressed))      { pos1.y-= 0.1f;}
            if (Input.checkKey(Input._s, Input.State.pressed))     { pos1.y+= 0.1f;}
            if (Input.checkKey(Input._a,Input.State.pressed))      { pos1.x-= 0.1f;}
            if (Input.checkKey(Input._d,Input.State.pressed))      { pos1.x+= 0.1f;}

            if (Input.checkKey(Input._up,Input.State.held))     { pos2.y-= 0.1f;}
            if (Input.checkKey(Input._down,Input.State.held))   { pos2.y+= 0.1f;}
            if (Input.checkKey(Input._left,Input.State.held))   { pos2.x-= 0.1f;}
            if (Input.checkKey(Input._right,Input.State.held))  { pos2.x+= 0.1f;}

            Window.changeRectData(rect1, pos1, new Vector2(50,50));
            Window.changeRectData(rect2, pos2, new Vector2(50,50));

            SDL_RenderClear(renderer);

            SDL_SetRenderDrawColor(renderer, (byte)100, (byte)100, (byte)255, (byte)255);
            SDL_RenderFillRect(renderer, rect1);

            SDL_SetRenderDrawColor(renderer, (byte)255, (byte)100, (byte)100, (byte)255);
            SDL_RenderFillRect(renderer, rect2);

            SDL_SetRenderDrawColor(renderer, (byte)120, (byte)240, (byte)120, (byte)255);

            SDL_RenderPresent(renderer);
        }
    }

    @Override
    public void run() {
        testCode();
    }
}