package rasterdata;

import java.util.Optional;

public class DepthRaster implements Raster<Double>{

    private final double[][] img;

    public DepthRaster(int width, int height){
        img = new double[width][height];
        clear(1.0);
    }

    @Override
    public int getWidth() {
        return img.length;
    }

    @Override
    public int getHeight() {
        return img[0].length;
    }

    @Override
    public Optional<Double> getPixel(int c, int r) {
        if(isValidAddress(c,r))
            return Optional.of(img[c][r]);
        return Optional.empty();
    }

    @Override
    public boolean setPixel(int c, int r, Double pixelValue) {
        if(isValidAddress(c,r)){
            img[c][r] = pixelValue;
            return true;
        }
        return false;
    }

    @Override
    public void clear(Double pixelValue) {
        for (int i = 0; i < img[0].length; i++){
            for(int j = 0; j < img.length; j++){
                img[j][i] = pixelValue;
            }
        }
    }
}
