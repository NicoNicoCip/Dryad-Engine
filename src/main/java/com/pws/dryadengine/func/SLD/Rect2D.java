package main.java.com.pws.dryadengine.func.SLD;

import main.java.com.pws.dryadengine.types.Color;
import main.java.com.pws.dryadengine.types.Vector2;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderDrawRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_SetRenderDrawColor;

import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;

public class Rect2D {
    public SDL_Rect rect;
    public Vector2 position;
    public Vector2 scale;
    public int zIndex = 0;
    public Color color;

    public Rect2D(SDL_Renderer renderer) {
        rect = new SDL_Rect();
        SDL_RenderDrawRect(renderer, rect);
    }

    public Rect2D(SDL_Renderer renderer, float x, float y, float w, float h, Color col) {
        this.rect = new SDL_Rect();
        this.rect.x = (int)x;
        this.rect.y = (int)y;
        this.rect.w = (int)w;
        this.rect.h = (int)h;

        this.position = new Vector2(x, y);
        this.scale = new Vector2(w, h);
        this.color = col;
        SDL_RenderDrawRect(renderer, rect);
    }

    public void updateRect(SDL_Renderer renderer) {
        this.rect.x = (int)position.x;
        this.rect.y = (int)position.y;
        this.rect.w = (int)scale.x;
        this.rect.h = (int)scale.y;

        SDL_SetRenderDrawColor(renderer, color.r, color.g, color.b, color.a);
        SDL_RenderFillRect(renderer, rect);
    }
}
