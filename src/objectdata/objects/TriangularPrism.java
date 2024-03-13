package objectdata.objects;

import objectdata.Part;
import objectdata.Solid;
import objectdata.Topology;
import objectdata.Vertex;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;
public class TriangularPrism extends Solid {

    public TriangularPrism() {
        super(List.of(
                new Vertex(new Point3D(0.8, 1.6, 0.5), 0x00FF00),
                new Vertex(new Point3D(0.8, 2, 0.5), 0x00FF00),
                new Vertex(new Point3D(1.2, 1.6, 0.5), 0x00FF00),
                new Vertex(new Point3D(1.2, 2, 0.5), 0x00FF00),
                new Vertex(new Point3D(1, 1.6, 1), 0xFF00FF),
                new Vertex(new Point3D(1, 2, 1), 0xFFFF00)
        ), List.of(
                1,3,5,
                0,2,4,
                0,1,3,
                0,2,3,
                2,3,5,
                2,4,5,
                0,1,5,
                0,4,5

        ), List.of(
                new Part(0, 8, Topology.TRIANGLE_LIST)
        ), new Mat4Identity());
    }
}