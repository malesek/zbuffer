package objectdata;

import transforms.Mat4;
import transforms.Point3D;

import java.util.List;

public class ArrowTest extends Solid {

    public ArrowTest(){
        super(List.of(
                new Vertex(new Point3D(0, 199, 0.5, 1), 0x00ff00),
                new Vertex(new Point3D(199, 199, 0.5, 1), 0x00ff00),
                new Vertex(new Point3D(199, 99, 0.5, 1), 0xff0000),
                new Vertex(new Point3D(399, 199, 0.5, 1), 0xff0000),
                new Vertex(new Point3D(199, 299, 0.5, 1), 0xff0000)
        ), List.of(
                0,1,2,3,4
        ), List.of(
                new Part(0, 1, Topology.LINE_LIST),
                new Part(2, 1, Topology.TRIANGLE_LIST)
        ), new Mat4());
    }

}
