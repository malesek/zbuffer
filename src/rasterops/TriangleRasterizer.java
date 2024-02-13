package rasterops;

import linalg.Lerp;
import rasterdata.ZBuffer;
import transforms.Point3D;

public class TriangleRasterizer {
    public static void rasterize(ZBuffer zBuffer, Point3D v1, Point3D v2, Point3D v3, int color){
        //sort v1, v2, v3
        if(v1.getY() > v2.getY()){
            Point3D temp = new Point3D(v1);
            v1 = new Point3D(v2);
            v2 = new Point3D(temp);
        }
        if(v2.getY() > v3.getY()){
            Point3D temp = new Point3D(v2);
            v2 = new Point3D(v3);
            v3 = new Point3D(temp);
        }
        if(v1.getY() > v3.getY()){
            Point3D temp = new Point3D(v1);
            v1 = new Point3D(v3);
            v3 = new Point3D(temp);
        }
        //for y in v1.y v2.y
        for(int y = (int)(v1.getY() + 1); y <= v2.getY(); y++){
            //      compute tA and tB
            double tA = (y - v1.getY()) / (v2.getY() - v1.getY());
            double tB = (y - v1.getY()) / (v3.getY() - v1.getY());
            //      compute values x, z, color?, ... in a and b
            double xA = Lerp.computeDouble(tA, v1.getX(), v2.getX());
            double xB = Lerp.computeDouble(tB, v1.getX(), v3.getX());
            if(xB < xA){
                double temp = xA;
                xA = xB;
                xB = temp;
            }
            //      for x in xA xB
            for(int x = (int)(xA + 1); x <= xB; x++){
                //          compute t
                double t = (x - xA) / (xB - xA);
                //          compute value

                //          setPixel(x,y,value)
                zBuffer.setPixel(x, y, color);
            }
        }
        for(int y = (int)(v2.getY() + 1); y <= v3.getY(); y++){
            //      compute tA and tB
            double tA = (y - v2.getY()) / (v3.getY() - v2.getY());
            double tB = (y - v1.getY()) / (v3.getY() - v1.getY());
            //      compute values x, z, color?, ... in a and b
            double xA = Lerp.computeDouble(tA, v2.getX(), v3.getX());
            double xB = Lerp.computeDouble(tB, v1.getX(), v3.getX());
            if(xB < xA){
                double temp = xA;
                xA = xB;
                xB = temp;
            }
            //      for x in xA xB
            for(int x = (int)(xA + 1); x <= xB; x++){
                //          compute t
                double t = (x - xA) / (xB - xA);
                //          compute value

                //          setPixel(x,y,value)
                zBuffer.setPixel(x, y, color);
            }
        }
    }
}
