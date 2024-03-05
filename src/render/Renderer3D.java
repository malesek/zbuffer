package render;

import linalg.Lerp;
import objectdata.Scene;
import objectdata.Solid;
import objectdata.Vertex;
import rasterdata.ZBuffer;
import transforms.Mat4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Renderer3D {
    public void renderScene(Scene scene, Mat4 viewMat, Mat4 projMat, ZBuffer img){

        //for each solid
            //call rendersolid with the appropriate transMatrix = modelMat * viewMat * projMat

    }

    private void renderSolid(Solid solid, Mat4 transMatrix){
        //transformation of all vertices from vertexBuffer
        //primitive assembly (lines, triangles)
        //for each primitive
            //for each vertex
                //if isInViewSpace
                    //continue
                    //dehomog
                    //transform toViewPort
                    //rasterize
    }

    private boolean isInViewSpace(List<Vertex> vertexList){
        boolean allTooLeft = vertexList.stream().allMatch(v -> v.getPosition().getX() > -v.getPosition().getW());
        boolean allTooRight = vertexList.stream().allMatch(v -> v.getPosition().getX() > v.getPosition().getW());
        boolean allTooClose = vertexList.stream().allMatch(v -> v.getPosition().getZ() > -v.getPosition().getW());
        boolean allTooFar = vertexList.stream().allMatch(v -> v.getPosition().getZ() > v.getPosition().getW());
        boolean allTooUp = vertexList.stream().allMatch(v -> v.getPosition().getY() > -v.getPosition().getW());
        boolean allTooDown = vertexList.stream().allMatch(v -> v.getPosition().getY() > v.getPosition().getW());

        return !(allTooLeft || allTooRight || allTooClose || allTooFar || allTooDown || allTooUp);
    }

    private List<Vertex> clipZ(Vertex v1, Vertex v2){ //method for line
        final Vertex min = v1.getPosition().getZ() < v2.getPosition().getZ() ? v1 : v2;
        final Vertex max = min == v1 ? v2 : v1;
        if(min.getPosition().getZ() < 0){
            double t = (- min.getPosition().getZ()) / (max.getPosition().getZ() - min.getPosition().getZ());
            final Vertex vertex = Lerp.compute(t, min, max);
            return List.of(vertex, max);
        }
        else {
            return List.of(v1,v2);
        }
    }

    private List<Vertex> clipZ(Vertex v1, Vertex v2, Vertex v3){ // method for triangle
        // sort v1, v2, v3 based on Z => min, mid. max
        List<Vertex> sortedVertices = new ArrayList<>(List.of(v1,v2,v3));
        sortedVertices.sort(Comparator.comparingDouble(a -> a.getPosition().getZ()));
        Vertex min = sortedVertices.get(0);
        Vertex mid = sortedVertices.get(1);
        Vertex max = sortedVertices.get(2);

        int count = verticesBelowZero(min, mid, max);
        if(count == 0) {
            return List.of(v1,v2,v3);
        }
        else if (count == 2) {
            double t = (- mid.getPosition().getZ()) / (max.getPosition().getZ() - mid.getPosition().getZ());
            final Vertex A = Lerp.compute(t, mid, max);
            t = (- min.getPosition().getZ()) / (max.getPosition().getZ() - min.getPosition().getZ());
            final Vertex B = Lerp.compute(t, min, max);
            return List.of(A, B, max);
        }
        else {
            double t = (- min.getPosition().getZ()) / (mid.getPosition().getZ() - min.getPosition().getZ());
            final Vertex A = Lerp.compute(t, min, mid);
            t = (- min.getPosition().getZ()) / (max.getPosition().getZ() - min.getPosition().getZ());
            final Vertex B = Lerp.compute(t, min, max);
            return List.of(max, mid, A, max, A, B);
        }
    }

    private int verticesBelowZero(Vertex v1, Vertex v2, Vertex v3) {
        return (int) Stream.of(v1, v2, v3).filter(v -> v.getPosition().getZ() < 0).count();
    }
}
