package shader;

import objectdata.Vertex;

public interface FragmentShader {
    int getColor(Vertex v);
}
