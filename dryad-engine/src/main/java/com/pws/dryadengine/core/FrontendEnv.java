package com.pws.dryadengine.core;

import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;
import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.func.SLD.Input;
import com.pws.dryadengine.func.SLD.Window;
import com.pws.dryadengine.types.Color;

import static io.github.libsdl4j.api.video.SDL_WindowFlags.*;

import com.pws.dryadengine.types.Vector2;

import io.github.libsdl4j.api.rect.SDL_Rect;

import static io.github.libsdl4j.api.render.SDL_RendererFlags.*;
import static io.github.libsdl4j.api.render.SdlRender.*;

public class FrontendEnv implements Runnable {

    private static Vector2 pos = new Vector2(50,50);

    public static void testCode() {
        Window.createSDLEnviorment();
        SDL_Window window = Window.createWindow("Test Window", 200, 200, 1280, 720, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        SDL_Renderer renderer = Window.createRenderer(window, -1, SDL_RENDERER_ACCELERATED);

        SDL_Rect rect = Window.createRect(pos, new Vector2(50, 50));
        SDL_RenderDrawRect(renderer, rect);

        while (!App.finnishExecution) {
            Window.checkForEndOfProgram();

            if(Input.isPressed(Input._space))
                Debug.println("W pressed");
            if(Input.isPressed(Input._down))
                pos.setY(pos.getY().floatValue() + 1f);
            if(Input.isPressed(Input._left))
                pos.setX(pos.getX().floatValue() - 1f);
            if(Input.isPressed(Input._right))
                pos.setX(pos.getX().floatValue() + 1f);


            SDL_RenderClear(renderer);

            SDL_SetRenderDrawColor(renderer, (byte)100, (byte)100, (byte)255, (byte)255);
            SDL_RenderFillRect(renderer, rect);

            SDL_SetRenderDrawColor(renderer, (byte)120, (byte)240, (byte)120, (byte)255);

            SDL_RenderPresent(renderer);
        }
    }

    @Override
    public void run() {
        testCode();
    }
}
