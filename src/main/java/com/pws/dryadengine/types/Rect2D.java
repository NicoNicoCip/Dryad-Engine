package main.java.com.pws.dryadengine.types;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderDrawRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderFillRect;
import static io.github.libsdl4j.api.render.SdlRender.SDL_SetRenderDrawColor;

import io.github.libsdl4j.api.rect.SDL_Rect;
import main.java.com.pws.dryadengine.func.Window;

public class Rect2D extends Node {
    public SDL_Rect rect;
    public int zIndex = 0; // TODO: object instancing and object layering
    public Color color;

    public Rect2D() {
        super();
        rect = new SDL_Rect();
        SDL_RenderDrawRect(Window.rd, rect);
    }

    public Rect2D(float x, float y, float w, float h, Color col) {
        this.rect = new SDL_Rect();
        this.position = new Vector3(x, y,0);
        this.rect.x = (int)position.x;
        this.rect.y = (int)position.y;

        this.scale = new Vector3(w, h, 0);
        this.rect.w = (int)scale.x;
        this.rect.h = (int)scale.y;

        this.color = col;
        SDL_RenderDrawRect(Window.rd, rect);
    }

    public void updateRect() {
        Vector3 parentOffset = new Vector3();
        Vector3 parentScalar = new Vector3();
        if(parent != null) {
            parentOffset = parent.position;
            parentScalar = parent.scale;
        }

        this.rect.x = (int)position.x + (int)parentOffset.x;
        this.rect.y = (int)position.y + (int)parentOffset.y;
        this.rect.w = (int)scale.x + (int)parentScalar.toVector2().x;
        this.rect.h = (int)scale.y + (int)parentScalar.toVector2().y;

        SDL_SetRenderDrawColor(Window.rd, color.r, color.g, color.b, color.a);
        SDL_RenderFillRect(Window.rd, rect);
    }
}
