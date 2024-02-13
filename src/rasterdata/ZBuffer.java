package rasterdata;

import java.util.Optional;

public class ZBuffer implements Raster<Integer>{
    private final ColorRaster colorRaster;
    private final DepthRaster depthRaster;

    public ZBuffer(ColorRaster colorRaster){
        this.colorRaster = colorRaster;
        depthRaster = new DepthRaster(colorRaster.getWidth(), colorRaster.getHeight());
    }
    @Override
    public int getWidth() {
        return colorRaster.getWidth();
    }

    @Override
    public int getHeight() {
        return colorRaster.getHeight();
    }

    @Override
    public Optional<Integer> getPixel(int c, int r) {
        return colorRaster.getPixel(c,r);
    }

    @Override
    public boolean setPixel(int c, int r, Integer pixelValue) {
        return colorRaster.setPixel(c,r,pixelValue);
    }

    public boolean setPixel(int c, int r, Integer pixelValue, float depth){
        //ZTest
        //check value of old depth
        if(depthRaster.getPixel(c, r).isPresent())
            if(depthRaster.getPixel(c, r).get() > depth){
                colorRaster.setPixel(c,r,pixelValue);
                depthRaster.setPixel(c,r,depth);
                return true;
            }
        return false;
    }

    @Override
    public void clear(Integer pixelValue) {
        colorRaster.clear(pixelValue);
        depthRaster.clear(1F);
    }
}
