package rasterdata;

import java.util.Optional;

public interface Raster<P> {
    int getWidth();
    int getHeight();
    Optional<P> getPixel(int c, int r);
    boolean setPixel(int c, int r, P pixelValue);
    void clear(P pixelValue);

    default boolean isValidAddress(int c, int r) {
        return c < getWidth() && c >= 0 && r < getHeight() && r >= 0;
    }
}
