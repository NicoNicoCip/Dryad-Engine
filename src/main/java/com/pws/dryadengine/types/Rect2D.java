package com.pws.dryadengine.types;

import java.util.ArrayList;
import java.util.List;

public class Rect2D extends Node {
    public Color color;
    private List<Vertex> verts = new ArrayList<>();
    private Mesh mesh = new Mesh();

    public Rect2D() {
        super();
    }

    public Rect2D(float x, float y, float w, float h, Color col) {
        this.position = new Vector3(x, y, 0);
        this.scale = new Vector3(w, h, 0);
        this.color = col;
        
        float halfW = w / 2;
        float halfH = h / 2;
    
        Vertex v1 = new Vertex(new Vector3(-halfW, -halfH, 0), col);
        Vertex v2 = new Vertex(new Vector3(halfW, -halfH, 0), col);
        Vertex v3 = new Vertex(new Vector3(-halfW, halfH, 0), col);
        Vertex v4 = new Vertex(new Vector3(halfW, halfH, 0), col);
        
        verts.add(v1);
        verts.add(v3);
        verts.add(v4);
        verts.add(v1);
        verts.add(v4);
        verts.add(v2);
        
        this.addChild(mesh);
    }

    public void updateRect() {
        Vector3 parentOffset = new Vector3();
        if(parent != null) {
            parentOffset = parent.position;
        }
    
        mesh.position = this.position.add(parentOffset);
        mesh.rotation = this.rotation;  // Add this line to pass rotation
        mesh.updateMesh(verts);
    }    
}
