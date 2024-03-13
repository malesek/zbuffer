package objectdata.objects;

import objectdata.Part;
import objectdata.Solid;
import objectdata.Topology;
import objectdata.Vertex;
import transforms.Mat4Identity;
import transforms.Point3D;
import java.util.List;

public class Cube extends Solid {

    public Cube() {
        super(List.of(
                new Vertex(new Point3D(-0.5, -0.5, 0.5), 0xFF0000), // Red
                new Vertex(new Point3D(0.5, -0.5, 0.5), 0x00FF00), // Green
                new Vertex(new Point3D(0.5, 0.5, 0.5), 0x0000FF), // Blue
                new Vertex(new Point3D(-0.5, 0.5, 0.5), 0xFFFF00), // Yellow
                new Vertex(new Point3D(-0.5, -0.5, 1.5), 0xFF00FF), // Magenta
                new Vertex(new Point3D(0.5, -0.5, 1.5), 0x00FFFF), // Cyan
                new Vertex(new Point3D(0.5, 0.5, 1.5), 0xFFFFFF), // White
                new Vertex(new Point3D(-0.5, 0.5, 1.5), 0xCCCCCC) // Grey
        ), List.of(
                // Front face
                0, 1, 2,
                2, 3, 0,
                // Back face
                5, 4, 7,
                7, 6, 5,
                // Left face
                4, 0, 3,
                3, 7, 4,
                // Right face
                1, 5, 6,
                6, 2, 1,
                // Top face
                3, 2, 6,
                6, 7, 3,
                // Bottom face
                4, 5, 1,
                1, 0, 4
        ), List.of(
                new Part(0, 12, Topology.TRIANGLE_LIST)
        ), new Mat4Identity());
    }
}
