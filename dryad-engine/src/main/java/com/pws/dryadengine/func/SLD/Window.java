package com.pws.dryadengine.func.SLD;

import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;
import com.pws.dryadengine.core.App;
import com.pws.dryadengine.func.LogsManager;
import com.pws.dryadengine.types.Color;

import static io.github.libsdl4j.api.Sdl.SDL_Init;
import static io.github.libsdl4j.api.error.SdlError.SDL_GetError;
import static io.github.libsdl4j.api.event.SdlEvents.SDL_PollEvent;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_CreateWindow;

import com.pws.dryadengine.types.Vector2;

import static io.github.libsdl4j.api.event.SDL_EventType.*;
import static io.github.libsdl4j.api.render.SdlRender.*;

public class Window {
    public static final SDL_Event evt = new SDL_Event();

    public static final void createSDLEnviorment() {
        int result = SDL_Init(62001);
        if (result != 0) {
            LogsManager.logErrors(new IllegalStateException("Unable to initialize SDL library (Error code " + result + "): " + SDL_GetError()));
        }
    }

    public static final SDL_Window createWindow(String title, int x, int y, int w, int h, int flags) {
        //SDL_Window window = SDL_CreateWindow("Demo SDL2", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, 1024, 768, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        SDL_Window window = SDL_CreateWindow(title, x, y, w, h, flags);
        if (window == null) {
            LogsManager.logErrors( new IllegalStateException("Unable to create SDL window: " + SDL_GetError()));
        }
        return window;
    }

    public static final SDL_Renderer createRenderer(SDL_Window window, int index, int flags) {
        //SDL_Renderer renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
        SDL_Renderer renderer = SDL_CreateRenderer(window, index, flags);
        if (renderer == null) {
            LogsManager.logErrors( new IllegalStateException("Unable to create SDL renderer: " + SDL_GetError()));
        }
        return renderer;
    }

    public static final SDL_Rect createRect(Vector2 pos, Vector2 size) {
        SDL_Rect rect = new SDL_Rect();
        rect.x = pos.getX().intValue();
        rect.y = pos.getY().intValue();
        rect.w = size.getX().intValue();
        rect.h = size.getX().intValue();
        return rect;
    }

    public static final void checkForEndOfProgram() {
        while (SDL_PollEvent(evt) != 0) {
            if(evt.type == SDL_QUIT) {
                App.finnishExecution = true;
                break;
            }
        }
    }
}
