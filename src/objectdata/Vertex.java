package objectdata;

import linalg.Vectorizable;
import transforms.Point3D;

public class Vertex implements Vectorizable<Vertex> {
    private final Point3D position;
    private final int color;

    public Vertex(Point3D _position, int _color){
        position = _position;
        color = _color;
    }

    public int getColor() {
        return color;
    }

    public Point3D getPosition() {
        return position;
    }

    public Vertex add(Vertex other){
        final int red = (color >> 16) & 0xFF;
        final int blue = (color >> 8) & 0xFF;
        final int green = color & 0xFF;
        final int redOther = (other.color >> 16) & 0xFF;
        final int blueOther = (other.color >> 8) & 0xFF;
        final int greenOther = other.color & 0xFF;
        final int color = (red+redOther) << 16 | (green + greenOther) << 8 | (blue+blueOther);
        return new Vertex(position.add(other.position), color);

    }

    public Vertex mul(double t) {
        final int red = (int) (((color >> 16) & 0xFF) * t);
        final int blue = (int) (((color >> 8) & 0xFF) * t);
        final int green = (int) ((color & 0xFF) * t);
        final int color = (red) << 16 | (green) << 8 | (blue);
        return new Vertex(position.mul(t), color);
    }
}
