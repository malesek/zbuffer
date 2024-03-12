package shader;

import objectdata.Vertex;
import rasterdata.Raster;
import transforms.Vec2D;

public class TextureColor implements FragmentShader{
    private final Raster<Integer> texture;

    public TextureColor(Raster<Integer> texture) {
        this.texture = texture;
    }

    @Override
    public int getColor(Vertex vertex) {
        if(vertex.getTextureCoord().isPresent()){
            final Vec2D textureCoord = vertex.getTextureCoord().get();
            int u = (int) (textureCoord.getX() * (texture.getWidth()-1));
            int v = (int) (textureCoord.getY() * (texture.getHeight()-1));
            return texture.getPixel(u,v).orElse(vertex.getColor());
        }
        return vertex.getColor();
    }
}
