package com.pws.dryadengine.scripts;

import java.util.ArrayList;
import java.util.List;

import com.pws.dryadengine.core.scripts.Script;
import com.pws.dryadengine.types.Color;
import com.pws.dryadengine.types.Mesh;
import com.pws.dryadengine.types.Vector3;
import com.pws.dryadengine.types.Vertex;

public class TriangleTest extends Script {
    List<Vertex> verts = new ArrayList<>();
    Mesh mesh = new Mesh();

    @Override
    public void setPC() {
        this.pc = -1;
    }

    @Override
    public void plant() {
        Vertex v1 = new Vertex(new Vector3(0,0,0), new Color(255, 0, 0, 255));
        Vertex v2 = new Vertex(new Vector3(100,0,0), new Color(255, 0, 0, 255));
        Vertex v3 = new Vertex(new Vector3(0,100,0), new Color(255, 0, 0, 255));
        Vertex v4 = new Vertex(new Vector3(100,100,0), new Color(255, 0, 0, 255));
        
        verts.add(v1);
        verts.add(v3);
        verts.add(v4);

        verts.add(v1);
        verts.add(v4);
        verts.add(v2);
    }

    @Override
    public void grow() {

        mesh.updateMesh(verts);
    }
}
