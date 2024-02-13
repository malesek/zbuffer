import rasterdata.ColorRaster;
import rasterdata.Raster;
import rasterdata.ZBuffer;

public class ZBufferTest {

    public static void printImage(Raster<Integer> img) {
        int length = String.valueOf(img.getWidth()).length();
        String verticalSep = " ".repeat(length) + " " + "----".repeat(img.getHeight()) + "-";
        StringBuilder header = new StringBuilder(" ".repeat(length) + "  ");
        for (int i = 0; i < img.getHeight(); i++) {
            header.append(" ").append(i).append((i < 10) ? "  " : " ");
        }
        System.out.println(header);
        System.out.println(verticalSep);
        for (int i = 0; i < img.getWidth(); i++) {
            System.out.print(i + " ".repeat(length - String.valueOf(i).length() + 1) + "| ");
            for (int j = 0; j < img.getHeight(); j++) {
                if(img.getPixel(i,j).isPresent())
                    System.out.print(img.getPixel(i, j).get());
                if (j < img.getHeight() - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.print(" |");
            System.out.println();
            System.out.println(verticalSep);
        }
    }

    public static void drawSquare(ZBuffer img, int x, int y, int width, int height, int color, double depth) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                img.setPixel(i, j, color, (float) depth);
            }
        }
    }

    public static void main(String[] args) {
        ZBuffer zBuffer = new ZBuffer(new ColorRaster(5, 5));
        printImage(zBuffer);
        drawSquare(zBuffer, 0, 0, 3, 3, 1, 0.8);
        printImage(zBuffer);
        /*
            0   1   2   3   4
          ---------------------
        0 | 1 | 1 | 1 | 0 | 0 |
          ---------------------
        1 | 1 | 1 | 1 | 0 | 0 |
          ---------------------
        2 | 1 | 1 | 1 | 0 | 0 |
          ---------------------
        3 | 0 | 0 | 0 | 0 | 0 |
          ---------------------
        4 | 0 | 0 | 0 | 0 | 0 |
          ---------------------
         */
        drawSquare(zBuffer, 1, 1, 3, 3, 2, 0.5);
        printImage(zBuffer);
        /*
            0   1   2   3   4
          ---------------------
        0 | 1 | 1 | 1 | 0 | 0 |
          ---------------------
        1 | 1 | 2 | 2 | 2 | 0 |
          ---------------------
        2 | 1 | 2 | 2 | 2 | 0 |
          ---------------------
        3 | 0 | 2 | 2 | 2 | 0 |
          ---------------------
        4 | 0 | 0 | 0 | 0 | 0 |
          ---------------------
         */
        drawSquare(zBuffer, 2, 2, 3, 3, 3, 0.5);
        printImage(zBuffer);
        /*
            0   1   2   3   4
          ---------------------
        0 | 1 | 1 | 1 | 0 | 0 |
          ---------------------
        1 | 1 | 2 | 2 | 2 | 0 |
          ---------------------
        2 | 1 | 2 | 2 | 2 | 3 |
          ---------------------
        3 | 0 | 2 | 2 | 2 | 3 |
          ---------------------
        4 | 0 | 0 | 3 | 3 | 3 |
          ---------------------
         */
    }
}

