package shader;

import objectdata.Vertex;

public class FlatColor implements FragmentShader {
    private final int color;

    public FlatColor(int color) {
        this.color = color;
    }

    public int getColor(Vertex v){
        return color;
    }
}
