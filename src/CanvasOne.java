import rasterdata.ColorRaster;
import rasterdata.ZBuffer;
import rasterops.TriangleRasterizer;
import transforms.Point3D;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;

public class CanvasOne {

    JFrame frame;
    JPanel panel;
    ColorRaster img;
    ZBuffer zBuffer;

    public CanvasOne(int width, int height) {
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
        CanvasAll.clear(zBuffer);
        panel.repaint();
    }

    public void present(Graphics g) {
        img.present(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasOne(300, 400).start());
    }

}
