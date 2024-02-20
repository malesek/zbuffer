package rasterops;

import linalg.Lerp;
import objectdata.Vertex;
import rasterdata.ZBuffer;
import transforms.Point3D;

public class TriangleRasterizer {
    public static void rasterizeFlat(ZBuffer zBuffer, Point3D v1, Point3D v2, Point3D v3, int color){
        //sort v1, v2, v3
        if(v1.getY() > v2.getY()){
            Point3D temp = v1;
            v1 = v2;
            v2 = temp;
        }
        if(v1.getY() > v3.getY()){
            Point3D temp = v1;
            v1 = v3;
            v3 = temp;
        }
        if(v2.getY() > v3.getY()){
            Point3D temp = v2;
            v2 = v3;
            v3 = temp;
        }

        //for y in v1.y v2.y
        for(int y = (int)(v1.getY() + 1); y <= v2.getY(); y++){
            //      compute tA and tB
            double tA = (y - v1.getY()) / (v2.getY() - v1.getY());
            double tB = (y - v1.getY()) / (v3.getY() - v1.getY());
            //      compute values x, z, color?, ... in a and b
            double xA = Lerp.computeDouble(tA, v1.getX(), v2.getX());
            double xB = Lerp.computeDouble(tB, v1.getX(), v3.getX());
            double zA = Lerp.computeDouble(tA, v1.getZ(), v2.getZ());
            double zB = Lerp.computeDouble(tB, v1.getZ(), v3.getZ());
            //      switch values if needed
            if(xB < xA){
                double temp = xA;
                xA = xB;
                xB = temp;
                temp = zA;
                zA = zB;
                zB = temp;
            }
            //      for x in xA xB
            for(int x = (int)(xA + 1); x <= xB; x++){
                //          compute t
                double t = (x - xA) / (xB - xA);
                //          compute value
                double z = Lerp.computeDouble(t, zA, zB);
                //          setPixel(x,y,value)
                zBuffer.setPixel(x, y, color, z);
            }
        }
        for(int y = (int)(v2.getY() + 1); y <= v3.getY(); y++){
            //      compute tA and tB
            double tA = (y - v2.getY()) / (v3.getY() - v2.getY());
            double tB = (y - v1.getY()) / (v3.getY() - v1.getY());
            //      compute values x, z, color?, ... in a and b
            double xA = Lerp.computeDouble(tA, v2.getX(), v3.getX());
            double xB = Lerp.computeDouble(tB, v1.getX(), v3.getX());
            double zA = Lerp.computeDouble(tA, v2.getZ(), v3.getZ());
            double zB = Lerp.computeDouble(tB, v1.getZ(), v3.getZ());
            //      switch values if needed
            if(xB < xA){
                double temp = xA;
                xA = xB;
                xB = temp;
                temp = zA;
                zA = zB;
                zB = temp;
            }
            //      for x in xA xB
            for(int x = (int)(xA + 1); x <= xB; x++){
                //          compute t
                double t = (x - xA) / (xB - xA);
                //          compute value
                double z = Lerp.computeDouble(t, zA, zB);
                //          setPixel(x,y,value)
                zBuffer.setPixel(x, y, color, z);
            }
        }
    }
    public static void rasterizeVertex(ZBuffer zBuffer, Vertex v1, Vertex v2, Vertex v3){
        //sort v1, v2, v3
        if(v1.getPosition().getY() > v2.getPosition().getY()){
            Vertex temp = v1;
            v1 = v2;
            v2 = temp;
        }
        if(v1.getPosition().getY() > v3.getPosition().getY()){
            Vertex temp = v1;
            v1 = v3;
            v3 = temp;
        }
        if(v2.getPosition().getY() > v3.getPosition().getY()){
            Vertex temp = v2;
            v2 = v3;
            v3 = temp;
        }

        //for y in v1.y v2.y
        for(int y = (int)(v1.getPosition().getY() + 1); y <= v2.getPosition().getY(); y++){
            //      compute tA and tB
            double tA = (y - v1.getPosition().getY()) / (v2.getPosition().getY() - v1.getPosition().getY());
            double tB = (y - v1.getPosition().getY()) / (v3.getPosition().getY() - v1.getPosition().getY());
            //      compute values x, z, color?, ... in a and b
            Vertex vA = Lerp.compute(tA, v1, v2);
            Vertex vB = Lerp.compute(tB, v1, v3);

            //      switch values if needed
            if(vB.getPosition().getX() < vA.getPosition().getX()){
                Vertex temp = vA;
                vA = vB;
                vB = temp;
            }
            //      for x in xA xB
            for(int x = (int)(vA.getPosition().getX() + 1); x <= vB.getPosition().getX(); x++){
                //          compute t
                double t = (x - vA.getPosition().getX()) / (vB.getPosition().getX() - vA.getPosition().getX());
                //          compute value
                Vertex v = Lerp.compute(t, vA, vB);
                //          setPixel(x,y,value)
                zBuffer.setPixel(x, y, v.getColor(), v.getPosition().getZ());
            }
        }
        for(int y = (int)(v2.getPosition().getY() + 1); y <= v3.getPosition().getY(); y++){
            //      compute tA and tB
            double tA = (y - v2.getPosition().getY()) / (v3.getPosition().getY() - v2.getPosition().getY());
            double tB = (y - v1.getPosition().getY()) / (v3.getPosition().getY() - v1.getPosition().getY());
            //      compute values x, z, color?, ... in a and b
            Vertex vA = Lerp.compute(tA, v2, v3);
            Vertex vB = Lerp.compute(tB, v1, v3);
            //      switch values if needed
            if(vB.getPosition().getX() < vA.getPosition().getX()){
                Vertex temp = vA;
                vA = vB;
                vB = temp;
            }
            //      for x in xA xB
            for(int x = (int)(vA.getPosition().getX() + 1); x <= vB.getPosition().getX(); x++){
                //          compute t
                double t = (x - vA.getPosition().getX()) / (vB.getPosition().getX() - vA.getPosition().getX());
                //          compute value
                Vertex v = Lerp.compute(t, vA, vB);
                //          setPixel(x,y,value)
                zBuffer.setPixel(x, y, v.getColor(), v.getPosition().getZ());
            }
        }
    }
}
