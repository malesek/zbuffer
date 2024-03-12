package shader;

import objectdata.Vertex;

public class SmoothColor implements FragmentShader {
    public int getColor(Vertex v){
        return v.getColor();
    }
}
