package main.java.com.pws.dryadengine.types;

import java.util.ArrayList;
import java.util.List;

import io.github.libsdl4j.api.render.SDL_Vertex;

public class Mesh extends Node {
    private List<Vector2> localVertices = new ArrayList<>();
    private List<SDL_Vertex> transformedVertices = new ArrayList<>();

    public Mesh() {}

    public void addVertex(Vector2 vertex, Color color) {
        localVertices.add(vertex);
        transformedVertices.add(new SDL_Vertex(vertex.x,vertex.y,color.r, color.g, color.b, color.a,0,0));
    }

    public List<SDL_Vertex> getTransformedVertices(int screenWidth, int screenHeight, Vector2 vertex, Color col) {
        transformedVertices.clear();
        Vector3 worldPos = getGlobalPosition();
        Euler worldRot = getGlobalRotation();
        Vector3 worldScale = getGlobalScale();
        
        for (Vector2 localVertex : localVertices) {
            float x = localVertex.x * worldScale.x;
            float y = localVertex.y * worldScale.y;

            float cosTheta = (float) Math.cos(worldRot.getZ());
            float sinTheta = (float) Math.sin(worldRot.getZ());
            float rotatedX = x * cosTheta - y * sinTheta;
            float rotatedY = x * sinTheta + y * cosTheta;

            float finalX = rotatedX + worldPos.x;
            float finalY = rotatedY + worldPos.y;

            float screenX = screenWidth / 2.0f + finalX;
            float screenY = screenHeight / 2.0f - finalY;

            transformedVertices.add(new SDL_Vertex(screenX, screenY, col.r, col.g, col.b, col.a,0,0));
        }

        return transformedVertices;
    }
}
