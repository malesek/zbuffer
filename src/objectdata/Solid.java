package objectdata;

import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public abstract class Solid {
    public boolean picked = false;
    private final List<Vertex> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final List<Part> partBuffer;
    private Mat4 modelMat;

    protected Solid(List<Vertex> vertexBuffer, List<Integer> indexBuffer, List<Part> partBuffer, Mat4 modelMat) {
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.partBuffer = partBuffer;
        this.modelMat = modelMat;
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

    public Mat4 getModelMat() {
        return modelMat;
    }

    public void setModelMat(Mat4 model) {
        this.modelMat = model;
    }
}