package main.java.com.pws.dryadengine.types;

import io.github.libsdl4j.api.render.SDL_Vertex;

public class Vertex {
    public Vector3 position = new Vector3();
    public Color color = new Color();
    public Vector2 textureCoords = new Vector2();

    public Vertex() {
    }

    public Vertex(Vector3 position, Color color, Vector2 textureCoords) {
        this.position = position;
        this.color = color;
        this.textureCoords = textureCoords;
    }

    public Vertex(Vector3 position, Color color) {
        this.position = position;
        this.color = color;
    }

    public SDL_Vertex toSdlVertex() {
        return new SDL_Vertex(position.x, position.y, color.r, color.g, color.b, color.a, textureCoords.x, textureCoords.y);
    }
}
