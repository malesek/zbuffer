package rasterops;

import linalg.Lerp;
import objectdata.Vertex;
import rasterdata.ZBuffer;
import shader.FragmentShader;
import transforms.Point3D;
import transforms.Vec2D;

public class TriangleRasterizer {
    public static void rasterize(ZBuffer zBuffer, Vertex v1, Vertex v2, Vertex v3, FragmentShader shader){
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
                zBuffer.setPixel(x, y, shader.getColor(v), v.getPosition().getZ());
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
                zBuffer.setPixel(x, y, shader.getColor(v), v.getPosition().getZ());
            }
        }
    }
}
