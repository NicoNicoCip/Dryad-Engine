package main.java.com.pws.dryadengine.types;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderDrawRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_SetRenderDrawColor;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;

public class Rect2D extends Node2D {
    public SDL_Rect rect;
    public Vector2 size;
    public int zIndex = 0; // TODO: object instancing and object layering
    public Color color;

    public Rect2D(SDL_Renderer renderer) {
        rect = new SDL_Rect();
        SDL_RenderDrawRect(renderer, rect);
    }

    public Rect2D(SDL_Renderer renderer, float x, float y, float w, float h, Color col) {
        this.rect = new SDL_Rect();
        this.position = new Vector2(x, y);
        this.rect.x = (int)position.x;
        this.rect.y = (int)position.y;

        this.size = new Vector2(w, h);
        this.rect.w = (int)size.x;
        this.rect.h = (int)size.y;

        this.color = col;
        SDL_RenderDrawRect(renderer, rect);
    }

    public void updateRect(SDL_Renderer renderer) {
        this.rect.x = (int)position.x;
        this.rect.y = (int)position.y;
        this.rect.w = (int)size.x;
        this.rect.h = (int)size.y;

        SDL_SetRenderDrawColor(renderer, color.r, color.g, color.b, color.a);
        SDL_RenderFillRect(renderer, rect);
    }
}
