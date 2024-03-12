package objectdata;

import linalg.Vectorizable;
import transforms.Point3D;
import transforms.Vec2D;

import java.util.Optional;

public class Vertex implements Vectorizable<Vertex> {
    private final Point3D position;
    private final int color;
    private final Vec2D textureCoord;

    public Vertex(Point3D _position, int _color){
        position = _position;
        color = _color;
        textureCoord = null;
    }

    public Vertex(Point3D _position, int _color, Vec2D _textureCoord){
        position = _position;
        color = _color;
        textureCoord = _textureCoord;
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

        if(textureCoord != null && other.textureCoord != null) return new Vertex(position.add(other.position), color, textureCoord.add(other.textureCoord));

        else return new Vertex(position.add(other.position), color);

    }

    public Vertex mul(double t) {
        final int red = (int) (((color >> 16) & 0xFF) * t);
        final int blue = (int) (((color >> 8) & 0xFF) * t);
        final int green = (int) ((color & 0xFF) * t);
        final int color = (red) << 16 | (green) << 8 | (blue);
        if(textureCoord != null) return new Vertex(position.mul(t), color, textureCoord.mul(t));
        else return new Vertex(position.mul(t), color);
    }

    public Optional<Vec2D> getTextureCoord() {
        return  Optional.ofNullable(textureCoord);
    }
}
