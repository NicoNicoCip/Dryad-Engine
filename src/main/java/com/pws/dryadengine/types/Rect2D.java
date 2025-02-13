package main.java.com.pws.dryadengine.types;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderDrawRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_SetRenderDrawColor;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import main.java.com.pws.dryadengine.func.Window;

public class Rect2D extends Node {
    public SDL_Rect rect;
    public int zIndex = 0; // TODO: object instancing and object layering
    public Color color;

    public Rect2D() {
        rect = new SDL_Rect();
        SDL_RenderDrawRect(Window.rd, rect);
    }

    public Rect2D(float x, float y, float w, float h, Color col) {
        this.rect = new SDL_Rect();
        this.position = new Vector2(x, y);
        this.rect.x = (int)position.x;
        this.rect.y = (int)position.y;

        this.size = new Vector2(w, h);
        this.rect.w = (int)size.x;
        this.rect.h = (int)size.y;

        this.color = col;
        SDL_RenderDrawRect(Window.rd, rect);
    }

    public void updateRect() {
        Vector2 parentOffset = new Vector2();
        float parentScalar = 0.0f;
        if(parent != null) {
            parentOffset = parent.position;
            parentScalar = parent.scale;
        }

        this.rect.x = (int)position.x + (int)parentOffset.x;
        this.rect.y = (int)position.y + (int)parentOffset.y;
        this.rect.w = (int)size.x + (int)parentScalar;
        this.rect.h = (int)size.y + (int)parentScalar;

        SDL_SetRenderDrawColor(Window.rd, color.r, color.g, color.b, color.a);
        SDL_RenderFillRect(Window.rd, rect);
    }
}
