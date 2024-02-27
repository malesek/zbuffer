package rasterops;

import linalg.Lerp;
import objectdata.Vertex;
import rasterdata.ZBuffer;

public class LineRasterizer {
//            v1 = v2;
//            v2 = temp;
//        }
//
//        //for y in v1.y v2.y
//        for(int y = (int)(v1.getPosition().getY() + 1); y <= v2.getPosition().getX(); y++){
//            //      compute tA and tB
//            double tA = (y - v1.getPosition().getY()) / (v2.getPosition().getY() - v1.getPosition().getY());
//            double tB = (y - v1.getPosition().getY()) / (v2.getPosition().getY());
//            //      compute values x, z, color?, ... in a and b
//            Vertex vA = Lerp.compute(tA, v1, v2);
//            Vertex vB = Lerp.compute(tB, v1, v2);
//
//            //      switch values if needed
//            if(vB.getPosition().getX() < vA.getPosition().getX()){
//                Vertex temp = vA;
//                vA = vB;
//                vB = temp;
//            }
//            //      for x in xA xB
//            for(int x = (int)(vA.getPosition().getX() + 1); x <= vB.getPosition().getX(); x++){
//                //          compute t
//                double t = (x - vA.getPosition().getX()) / (vB.getPosition().getX() - vA.getPosition().getX());
//                //          compute value
//                Vertex v = Lerp.compute(t, vA, vB);
//                //          setPixel(x,y,value)
//                zBuffer.setPixel(x, y, v.getColor(), v.getPosition().getZ());
//            }
//        }
    }
}
