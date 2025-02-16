package main.java.com.pws.dryadengine.scripts;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderDrawLines;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderPresent;
import static io.github.libsdl4j.api.render.SdlRender.SDL_SetRenderDrawColor;

import java.util.ArrayList;
import java.util.List;

import io.github.libsdl4j.api.rect.SDL_Point;
import main.java.com.pws.dryadengine.core.scripts.Script;
import main.java.com.pws.dryadengine.func.Window;

public class TriangleTest extends Script {
    List<SDL_Point> points = new ArrayList<>();

    @Override
    public void setPC() {
        this.pc = 0;
    }

    @Override
    public void plant() {
        points.add(new SDL_Point(400, 100)); // Top
        points.add(new SDL_Point(200, 500)); // Bottom left
        points.add(new SDL_Point(600, 500)); // Bottom right
        points.add(new SDL_Point(400, 100)); // Back to top to close the triangle
    }

    @Override
    public void grow() {
        SDL_RenderDrawLines(Window.rd,points);
    }
}
