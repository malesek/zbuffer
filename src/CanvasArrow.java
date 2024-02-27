import objectdata.ArrowTest;
import objectdata.Part;
import objectdata.Vertex;
import rasterdata.ColorRaster;
import rasterdata.ZBuffer;
import rasterops.LineRasterizer;
import rasterops.TriangleRasterizer;
import transforms.Point3D;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;

public class CanvasArrow {

    JFrame frame;
    JPanel panel;
    ColorRaster img;
    ZBuffer zBuffer;

    public CanvasArrow(int width, int height) {
        img = new ColorRaster(width, height);
        zBuffer = new ZBuffer(img);

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("PGRF 2 - Task 1");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void start() {
        zBuffer.clear(0xffffff);

        ArrowTest arrowTest = new ArrowTest();
        for(Part part: arrowTest.getPartBuffer()){
            switch (part.getTopology()){
                case LINE_LIST -> {
                    for(int i = part.getStartIndex(); i < part.getStartIndex() + part.getCount() * 2; i+=2){
                        Vertex v1 = arrowTest.getVertexBuffer().get(arrowTest.getIndexBuffer().get(i));
                        Vertex v2 = arrowTest.getVertexBuffer().get(arrowTest.getIndexBuffer().get(i+1));
                        LineRasterizer.rasterize(zBuffer, v1, v2);
                    }
                }
                case TRIANGLE_LIST -> {
                    for(int i = part.getStartIndex(); i < part.getStartIndex() + part.getCount() * 3; i+=3){
                        Vertex v1 = arrowTest.getVertexBuffer().get(arrowTest.getIndexBuffer().get(i));
                        Vertex v2 = arrowTest.getVertexBuffer().get(arrowTest.getIndexBuffer().get(i+1));
                        Vertex v3 = arrowTest.getVertexBuffer().get(arrowTest.getIndexBuffer().get(i+2));
                        TriangleRasterizer.rasterizeVertex(zBuffer, v1, v2, v3);
                    }
                }
            }
        }

        panel.repaint();
    }

    public void present(Graphics g) {
        img.present(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasArrow(400, 400).start());
    }

}
