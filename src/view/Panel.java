package view;

import rasterdata.ColorRaster;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private ColorRaster raster;

    public ColorRaster getRaster() {
        return raster;
    }

    public static final int WIDTH = 800, HEIGHT = 600;

    Panel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        raster = new ColorRaster(WIDTH, HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        raster.present(g);
    }

    public void clear(Integer pixelValue) {
        raster.clear(pixelValue);
    }
}
