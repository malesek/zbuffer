package objectdata.objects;

import objectdata.Part;
import objectdata.Solid;
import objectdata.Topology;
import objectdata.Vertex;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;

public class Plumbob extends Solid {

    public Plumbob() {
        super(List.of(
                new Vertex(new Point3D(0.8, 0.8, 0.5), 0x00FF00), // Red
                new Vertex(new Point3D(0.8, 1.2, 0.5), 0x00FF00), // Green
                new Vertex(new Point3D(1.2, 0.8, 0.5), 0x00FF00), // Magenta
                new Vertex(new Point3D(1.2, 1.2, 0.5), 0x00FF00), // Cyan
                new Vertex(new Point3D(1, 1, 1), 0xFF00FF), // Magenta
                new Vertex(new Point3D(1, 1, 0), 0x00FFFF) // Cyan
        ), List.of(
                //Top part
                0, 1, 4,
                0, 3, 4,
                2, 3, 4,
                1, 2, 4,
                //Bottom part
                0, 1, 5,
                0, 2, 5,
                2, 3, 5,
                1, 3, 5
        ), List.of(
                // Define a single part for the entire plumbob
                new Part(0, 8, Topology.TRIANGLE_LIST)
        ), new Mat4Identity());
    }
}
