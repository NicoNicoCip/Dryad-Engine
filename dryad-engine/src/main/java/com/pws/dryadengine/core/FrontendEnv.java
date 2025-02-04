package main.java.com.pws.dryadengine.core;

import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;
import main.java.com.pws.dryadengine.func.Debug;
import main.java.com.pws.dryadengine.func.SLD.Input;
import main.java.com.pws.dryadengine.func.SLD.Window;
import main.java.com.pws.dryadengine.types.drawing.Color;

import static io.github.libsdl4j.api.video.SDL_WindowFlags.*;
import static io.github.libsdl4j.api.render.SDL_RendererFlags.*;
import static io.github.libsdl4j.api.render.SdlRender.*;

public class FrontendEnv implements Runnable {
    private static float hue = 0;

    public static Color getNextColor() {
        hue = (hue + 0.00001f) % 1.0f;
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    public static final void testCode() {
        Window.createSDLEnviorment();
        SDL_Window window = Window.createWindow("Test Window", 200, 200, 1280, 720, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        SDL_Renderer renderer = Window.createRenderer(window, -1, SDL_RENDERER_ACCELERATED);
        // Set color of renderer to green
        SDL_SetRenderDrawColor(renderer, (byte) 0, (byte) 255, (byte) 0, (byte) 255);

        // Clear the window and make it all red
        SDL_RenderClear(renderer);

        // Render the changes above ( which up until now had just happened behind the scenes )
        SDL_RenderPresent(renderer);

        while (!App.finnishExecution) {
            Window.checkForEndOfProgram();

            Color c = getNextColor();
            SDL_SetRenderDrawColor(renderer, c.getR(), c.getG(), c.getB(), (byte)255);
            SDL_RenderClear(renderer);
            SDL_RenderPresent(renderer);

            if(Input.isPressed(Input._space)) 
                Debug.println("Space has been pressed!!!");
        }
    }

    @Override
    public void run() {
        testCode();
    }
}
