package main.java.com.pws.dryadengine.types.drawing;

import main.java.com.pws.dryadengine.types.math.Vector3;

public class Vertex {
    private Vector3 vPos;

    public Vector3 getVPos() {
        return this.vPos;
    }

    public void setVPos(Vector3 vPos) {
        this.vPos = vPos;
    }

    public Vertex() {

    }

    public Vertex(Vector3 vPos) {
        this.vPos = vPos;
    }

    public String toData(){
        return vPos.toData();
    }
}
