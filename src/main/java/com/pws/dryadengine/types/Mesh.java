package main.java.com.pws.dryadengine.types;

import static io.github.libsdl4j.api.render.SdlRender.SDL_RenderGeometry;

import java.util.ArrayList;
import java.util.List;

import io.github.libsdl4j.api.render.SDL_Vertex;
import main.java.com.pws.dryadengine.func.Window;

public class Mesh extends Node{
    List<SDL_Vertex> sdlverts = new ArrayList<>();

    public Mesh() {
    }
    
    public void updateMesh(List<Vertex> vertices) {
        sdlverts.clear();
        
        for (Vertex vertex : vertices) {
            // First translate to origin point
            Vector3 centeredPos = vertex.position.subtract(origin);
            
            // Apply rotation around origin
            Vector3 rotatedPos = rotation.rotateVector(centeredPos);
            
            // Apply scale around origin
            Vector3 scaledPos = new Vector3(
                rotatedPos.x * scale.x,
                rotatedPos.y * scale.y,
                rotatedPos.z * scale.z
            );
            
            // Translate back from origin and add final position
            Vector3 finalPos = scaledPos.add(origin).add(position);
            
            sdlverts.add(new SDL_Vertex(
                finalPos.x,
                finalPos.y,
                vertex.color.r,
                vertex.color.g, 
                vertex.color.b, 
                vertex.color.a, 
                vertex.textureCoords.x, 
                vertex.textureCoords.y
            ));
        }
    
        SDL_RenderGeometry(Window.rd, null, sdlverts, null);
    }
}
