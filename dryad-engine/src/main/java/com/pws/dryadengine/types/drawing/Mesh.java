package main.java.com.pws.dryadengine.types.drawing;

public class Mesh {
    private Vertex[] vertices;

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public String toData() {
        String out = "[";
        for (Vertex vertex : vertices) {
            out += vertex.toData() + ",";
        }
        return out += "]";
    }

    public Mesh() {
    }

    public Mesh(Vertex[] vertices) {
        this.vertices = vertices;
    }
}
