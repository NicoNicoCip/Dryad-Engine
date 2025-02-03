import org.junit.Test;

import main.java.pws.types.drawing.Mesh;
import main.java.pws.types.drawing.Vertex;
import main.java.pws.types.math.Vector3;

public class TestBench {
    @Test
    public void createMesh() {
        Mesh m = new Mesh(new Vertex[] {
            new Vertex(new Vector3(0, 1, 1)), 
            new Vertex(new Vector3(1, 0, 1)), 
            new Vertex(new Vector3(1, 1, 0))
        }
        );
        System.out.println(m.toData());
    }
}
