package main.java.com.pws.dryadengine.func;

import io.github.libsdl4j.api.SdlSubSystemConst;
import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;

import main.java.com.pws.dryadengine.core.App;

import static io.github.libsdl4j.api.Sdl.SDL_Init;
import static io.github.libsdl4j.api.Sdl.SDL_Quit;
import static io.github.libsdl4j.api.error.SdlError.SDL_GetError;
import static io.github.libsdl4j.api.event.SdlEvents.SDL_PollEvent;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_CreateWindow;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_DestroyWindow;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_GetWindowPosition;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_GetWindowSizeInPixels;

import com.sun.jna.ptr.*;

import main.java.com.pws.dryadengine.types.Color;
import main.java.com.pws.dryadengine.types.Vector2;

import static io.github.libsdl4j.api.event.SDL_EventType.*;
import static io.github.libsdl4j.api.render.SdlRender.*;

public class Window {
    public static SDL_Event evt = new SDL_Event();
    public static SDL_Renderer rd;
    private static SDL_Window window;

    public static String title = "Default Window";
    public static Vector2 position = new Vector2(200,200);
    public static Vector2 scale = new Vector2(640,480);

    private static Color backgroundColor = new Color(0, 0, 0, 0);

    static {
        createSDLEnvironment();
        updateWindow();
    }

    public static void updateWindow() {
        end();
        window = createWindow(title, (int)position.x, (int)position.y, (int)scale.x, (int)scale.y, 4 | 32);
        rd = createRenderer(window, 1, 2 | 8);
    }

    private static void createSDLEnvironment() {
        int result = SDL_Init(SdlSubSystemConst.SDL_INIT_EVERYTHING);
        if (result != 0) {
            LogsManager.logErrors(new IllegalStateException("Unable to initialize SDL library (Error code " + result + "): " + SDL_GetError()));
        }
    }

    private static SDL_Window createWindow(String title, int x, int y, int w, int h, int flags) {
        //SDL_Window window = SDL_CreateWindow("Demo SDL2", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, 1024, 768, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        SDL_Window window = SDL_CreateWindow(title, x, y, w, h, flags);
        if (window == null) {
            LogsManager.logErrors( new IllegalStateException("Unable to create SDL window: " + SDL_GetError()));
        }
        return window;
    }

    private static SDL_Renderer createRenderer(SDL_Window window, int index, int flags) {
        //SDL_Renderer renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
        SDL_Renderer renderer = SDL_CreateRenderer(window, index, flags);
        if (renderer == null) {
            LogsManager.logErrors( new IllegalStateException("Unable to create SDL renderer: " + SDL_GetError()));
        }
        return renderer;
    }

    public static void drawBackground() {
        SDL_SetRenderDrawColor(rd, backgroundColor.r, backgroundColor.g, backgroundColor.b,(byte) 255);
    }

    public static void drawRender(Color col) {
        SDL_SetRenderDrawColor(rd, col.r, col.g, col.b,col.a);
    }

    public static void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    public static void clear() {
        SDL_RenderClear(rd);
    }

    public static void print() {
        SDL_RenderPresent(rd);
    }

    public static void end() {
        SDL_DestroyRenderer(rd);
        SDL_DestroyWindow(window);
        SDL_Quit();
    }

    public static void runEvents() {
        while (SDL_PollEvent(evt) != 0) {
            switch (evt.type) {
                case SDL_QUIT:
                    App.finnishExecution = true;
                    break;
                case SDL_KEYDOWN:
                    if(!Input.keys.containsKey(Window.evt.key.keysym.sym))
                        Input.addKey(Window.evt.key.keysym.sym, Input.State.pressed);
                    break;

                case SDL_KEYUP:
                    if(Input.checkKey(Window.evt.key.keysym.sym, Input.State.held))
                        Input.addKey(Window.evt.key.keysym.sym, Input.State.released);
                    break;

                case SDL_WINDOWEVENT:
                    IntByReference x = new IntByReference();
                    IntByReference y = new IntByReference();

                    SDL_GetWindowSizeInPixels(window, x, y);
                    if(x.getValue() != scale.x) scale.x = x.getValue();
                    if(y.getValue() != scale.y) scale.y = y.getValue();

                    SDL_GetWindowPosition(window, x, y);
                    if(x.getValue() != position.x) position.x = x.getValue();
                    if(y.getValue() != position.y) position.y = y.getValue();
                    
                    break;
                default:
                    break;
            }
        }
    }
}
