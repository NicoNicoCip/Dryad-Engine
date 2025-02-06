package com.pws.dryadengine.func.SLD;

import io.github.libsdl4j.api.SdlSubSystemConst;
import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;

import com.pws.dryadengine.core.App;
import com.pws.dryadengine.func.LogsManager;

import static io.github.libsdl4j.api.Sdl.SDL_Init;
import static io.github.libsdl4j.api.error.SdlError.SDL_GetError;
import static io.github.libsdl4j.api.event.SdlEvents.SDL_PollEvent;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_CreateWindow;

import com.pws.dryadengine.types.Vector2;

import static io.github.libsdl4j.api.event.SDL_EventType.*;
import static io.github.libsdl4j.api.render.SdlRender.*;

public class Window {
    public static SDL_Event evt = new SDL_Event();
    public static void createSDLEnvironment() {
        int result = SDL_Init(SdlSubSystemConst.SDL_INIT_EVERYTHING);
        if (result != 0) {
            LogsManager.logErrors(new IllegalStateException("Unable to initialize SDL library (Error code " + result + "): " + SDL_GetError()));
        }
    }

    public static SDL_Window createWindow(String title, int x, int y, int w, int h, int flags) {
        //SDL_Window window = SDL_CreateWindow("Demo SDL2", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, 1024, 768, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        SDL_Window window = SDL_CreateWindow(title, x, y, w, h, flags);
        if (window == null) {
            LogsManager.logErrors( new IllegalStateException("Unable to create SDL window: " + SDL_GetError()));
        }
        return window;
    }

    public static SDL_Renderer createRenderer(SDL_Window window, int index, int flags) {
        //SDL_Renderer renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
        SDL_Renderer renderer = SDL_CreateRenderer(window, index, flags);
        if (renderer == null) {
            LogsManager.logErrors( new IllegalStateException("Unable to create SDL renderer: " + SDL_GetError()));
        }
        return renderer;
    }

    public static SDL_Rect createRect(Vector2 pos, Vector2 size) {
        SDL_Rect rect = new SDL_Rect();
        rect.x = (int)pos.x;
        rect.y = (int)pos.y;
        rect.w = (int)size.x;
        rect.h = (int)size.y;
        return rect;
    }

    public static void changeRectData(SDL_Rect rect,Vector2 pos, Vector2 size) {
        rect.x = (int)pos.x;
        rect.y = (int)pos.y;
        rect.w = (int)size.x;
        rect.h = (int)size.y;
    }

    public static void runEvents() {


        while (SDL_PollEvent(evt) != 0) {
            switch (evt.type) {
                case SDL_QUIT:
                    App.finnishExecution = true;
                    break;
                case SDL_KEYDOWN:
                    if(Input.keys.containsKey(Window.evt.key.keysym.sym) == false)
                        Input.addKey(Window.evt.key.keysym.sym, Input.State.pressed);
                    break;

                case SDL_KEYUP:
                    if(Input.checkKey(Window.evt.key.keysym.sym, Input.State.held))
                        Input.addKey(Window.evt.key.keysym.sym, Input.State.released);
                    break;

                default:
                    break;
            }
        }
    }
}
