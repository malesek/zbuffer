package rasterdata;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class ColorRaster implements Raster<Integer>{

    private final BufferedImage img;

    public ColorRaster(int width, int height){
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    @Override
    public Optional<Integer> getPixel(int c, int r) {
        if(isValidAddress(c,r))
            return Optional.of(img.getRGB(c, r) & 0xffffff);
        return Optional.empty();
    }

    @Override
    public boolean setPixel(int c, int r, Integer pixelValue) {
        if(isValidAddress(c,r)) {
            img.setRGB(c, r, pixelValue);
            return true;
        }
        return false;
    }

    @Override
    public void clear(Integer pixelValue) {
        Graphics g = img.getGraphics();
        g.setColor(new Color(pixelValue));
        g.drawRect(0,0,img.getWidth(),img.getHeight());
    }
}
