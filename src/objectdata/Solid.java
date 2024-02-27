package objectdata;

import java.util.List;

public abstract class Solid {
    private final List<Vertex> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final List<Part> partBuffer;

    protected Solid(List<Vertex> vertexBuffer, List<Integer> indexBuffer, List<Part> partBuffer) {
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.partBuffer = partBuffer;
    }

    public List<Part> getPartBuffer() {
        return partBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }
}
