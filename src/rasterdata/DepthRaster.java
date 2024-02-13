package rasterdata;

import java.util.Optional;

public class DepthRaster implements Raster<Float>{

    private final float[][] img;

    public DepthRaster(int width, int height){
        img = new float[width][height];
        clear(1F);
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
    public Optional<Float> getPixel(int c, int r) {
        if(isValidAddress(c,r))
            return Optional.of(img[c][r]);
        return Optional.empty();
    }

    @Override
    public boolean setPixel(int c, int r, Float pixelValue) {
        if(isValidAddress(c,r)){
            img[c][r] = pixelValue;
            return true;
        }
        return false;
    }

    @Override
    public void clear(Float pixelValue) {
        for (int i = 0; i < img[0].length; i++){
            for(int j = 0; j < img.length; j++){
                img[j][i] = pixelValue;
            }
        }
    }
}
