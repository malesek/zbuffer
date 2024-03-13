package rasterops;

import objectdata.Vertex;
import rasterdata.ZBuffer;

public class LineRasterizer {
    //Changed to be simillar to PRGF1 project
    public static void rasterize(ZBuffer zBuffer, Vertex v1, Vertex v2){
        //Init helper variables
        int v1y = (int) v1.getPosition().getY();
        int v2y = (int) v2.getPosition().getY();
        int v1x = (int) v1.getPosition().getX();
        int v2x = (int) v2.getPosition().getX();
        float k = (v2y - v1y) / (float) (v2x - v1x);
        float q = v1y - k * v1x;

        if (Math.abs(v2y - v1y) < Math.abs(v2x - v1x)) {
            //switch x and vertexes if needed
            if (v2x < v1x) {
                int tempX = v1x;
                v1x = v2x;
                v2x = tempX;

                Vertex temp = v1;
                v1 = v2;
                v2 = temp;
            }
            for (int x = v1x; x <= v2x; x++) {
                //compute y
                int y = Math.round(k * (float) x + q);
                //compute t
                double t = (x - v1x) / (double) (v2x - v1x);
                //interpolate vertex
                Vertex v = v1.mul(1 - t).add(v2.mul(t));
                //setpixel
                zBuffer.setPixel(x, y, v.getColor(), v.getPosition().getZ());
            }
        }
        if (Math.abs(v2y - v1y) >= Math.abs(v2x - v1x)) {
            //switch y and vertexes if needed
            if (v2y < v1y) {
                int tempY = v1y;
                v1y = v2y;
                v2y = tempY;

                Vertex temp = v1;
                v1 = v2;
                v2 = temp;
            }
            for (int y = v1y; y <= v2y; y++) {
                //check if horizontal otherwise compute x
                int x;
                if(v2x == v1x){
                    x = v1x;
                }
                else {
                    x = Math.round((y-q)/k);
                }
                //interpolate vertex
                double t = (y - v1y) / (double) (v2y - v1y);
                Vertex v = v1.mul(1 - t).add(v2.mul(t));
                //setpixel
                zBuffer.setPixel(x, y, v.getColor(), v.getPosition().getZ());
            }
        }
    }

}
