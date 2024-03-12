package objectdata.objects;

import objectdata.Part;
import objectdata.Solid;
import objectdata.Topology;
import objectdata.Vertex;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;

public class Axis extends Solid {

    public Axis() {
        super(List.of(
                new Vertex(new Point3D(0, 0, 0), 0xFFFF00), // 0
                new Vertex(new Point3D(0.8, 0, 0), 0x0000FF), // 1
                new Vertex(new Point3D(0, 0.8, 0), 0xFF0000), // 2
                new Vertex(new Point3D(0, 0, 0.8), 0x00FF00), // 3
                new Vertex(new Point3D(0.8, 0.05, -0.05), 0x00FFFF), // 4
                new Vertex(new Point3D(0.8, -0.05, -0.05), 0x00FFFF), // 5
                new Vertex(new Point3D(0.85, 0.00, 0), 0xFFFFFF), // 6
                new Vertex(new Point3D(0.8, 0.05, 0.05), 0x00FFFF), // 7
                new Vertex(new Point3D(0.8, -0.05, 0.05), 0x00FFFF), // 8
                new Vertex(new Point3D(0.05, 0.8, 0.05), 0x0000FF), // 9
                new Vertex(new Point3D(-0.05, 0.8, 0.05), 0x0000FF), // 10
                new Vertex(new Point3D(0, 0.85, 0), 0xFFFFFF), // 11
                new Vertex(new Point3D(0.05, 0.8, -0.05), 0x0000FF), // 12
                new Vertex(new Point3D(-0.05, 0.8, -0.05), 0x0000FF), // 13
                new Vertex(new Point3D(-0.05, -0.05, 0.80), 0x00FF00), // 14
                new Vertex(new Point3D(0.05, -0.05, 0.80), 0x00FF00), // 15
                new Vertex(new Point3D(0, 0, 0.85), 0xFFFFFF), // 16
                new Vertex(new Point3D(-0.05, 0.05, 0.80), 0x00FF00), // 17
                new Vertex(new Point3D(0.05, 0.05, 0.80), 0x00FF00) // 18
        ), List.of(
                4, 5, 6,
                6, 7, 8,
                6, 7, 4,
                5, 6, 8,
                9, 10, 11,
                13, 12, 11,
                10, 13, 11,
                11, 12, 9,
                14, 15, 16,
                16, 17, 18,
                14, 16, 17,
                15, 16, 18,
                0, 1,
                0, 2,
                0, 3
        ), List.of(
                new Part(0, 12, Topology.TRIANGLE_LIST),
                new Part(36, 3, Topology.LINE_LIST)
        ), new Mat4Identity());
    }
}
