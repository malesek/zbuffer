package rasterops;

import linalg.Lerp;
import objectdata.Vertex;
import rasterdata.ZBuffer;

import java.awt.*;

public class LineRasterizer {
    public static void rasterize(ZBuffer zBuffer, Vertex v1, Vertex v2) {
        // Check if the line is horizontal or vertical
        if (v1.getPosition().getX() == v2.getPosition().getX()) {
            // Handle vertical line
            if (v1.getPosition().getY() > v2.getPosition().getY()) {
                Vertex temp = v1;
                v1 = v2;
                v2 = temp;
            }

            double dy = v2.getPosition().getY() - v1.getPosition().getY();
            int directionX = (int) Math.signum(v2.getPosition().getX() - v1.getPosition().getX());

            int x = (int) v1.getPosition().getX();
            int y = (int) v1.getPosition().getY();

            while (y <= v2.getPosition().getY()) {
                double t = (y - v1.getPosition().getY()) / dy;
                Vertex interpolatedVertex = Lerp.compute(t, v1, v2);

                zBuffer.setPixel(x, y, interpolatedVertex.getColor(), interpolatedVertex.getPosition().getZ());

                y++;
                x += directionX * Math.abs((int) dy);
            }
        } else {
            // Handle horizontal line
            if (v1.getPosition().getX() > v2.getPosition().getX()) {
                Vertex temp = v1;
                v1 = v2;
                v2 = temp;
            }

            double dx = v2.getPosition().getX() - v1.getPosition().getX();
            double dy = v2.getPosition().getY() - v1.getPosition().getY();
            double m = dy / dx;
            int directionY = (int) Math.signum(dy);

            int x = (int) v1.getPosition().getX();
            int y = (int) v1.getPosition().getY();

            while (x <= v2.getPosition().getX()) {
                double t = (x - v1.getPosition().getX()) / dx;
                Vertex interpolatedVertex = Lerp.compute(t, v1, v2);

                zBuffer.setPixel(x, y, interpolatedVertex.getColor(), interpolatedVertex.getPosition().getZ());

                x++;
                y += directionY * Math.abs((int) m);
            }
        }
    }

}
