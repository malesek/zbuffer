import rasterdata.ColorRaster;
import rasterdata.ZBuffer;
import rasterops.TriangleRasterizer;
import transforms.Point3D;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class CanvasAll {

    JFrame frame;
    JPanel panel;
    ColorRaster img;
    ZBuffer zBuffer;

    public CanvasAll(int width, int height) {
        img = new ColorRaster(width, height);
        zBuffer = new ZBuffer(img);

        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("PGRF 2 - Task 1");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Serial
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
        clear(zBuffer);
        Point3D v1;
        Point3D v2;
        Point3D v3;
        v1 = new Point3D(100, 400, 0.9, 1);
        v2 = new Point3D(0, 300, 0.8, 1);
        v3 = new Point3D(250, 50, 0.1, 1);
        TriangleRasterizer.rasterize(zBuffer, v1, v2, v3, 0x00ff00);
        v1 = new Point3D(50, 400, 0.98, 1);
        v2 = new Point3D(150, 400, 0.98, 1);
        v3 = new Point3D(100, 200, 0.45, 1);
        TriangleRasterizer.rasterize(zBuffer, v1, v2, v3, 0x0000ff);
        panel.repaint();
    }

    static void clear(ZBuffer zBuffer) {
        zBuffer.clear(0xffffff);
        Point3D v1 = new Point3D(150, 0, 0.5, 1);
        Point3D v2 = new Point3D(0, 200, 0.5, 1);
        Point3D v3 = new Point3D(300, 400, 0.5, 1);
        TriangleRasterizer.rasterize(zBuffer, v1, v2, v3, 0xff0000);
    }

    public void present(Graphics g) {
        img.present(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasAll(300, 400).start());
    }

}

