package render;

import linalg.Lerp;
import objectdata.Part;
import objectdata.Scene;
import objectdata.Solid;
import objectdata.Vertex;
import rasterdata.ZBuffer;
import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import objectdata.Topology;

import static rasterops.LineRasterizer.rasterize;
import static rasterops.TriangleRasterizer.rasterizeVertex;

public class Renderer3D {
    public void renderScene(Scene scene, Mat4 viewMat, Mat4 projMat, ZBuffer img) {
        for (Solid solid : scene.getSolids()) {
            Mat4 transMatrix = solid.getModelMat().mul(viewMat).mul(projMat);
            //Mat4 transMatrix = projMat.mul(viewMat).mul(solid.getModelMat());
            renderSolid(solid, transMatrix, img);
        }
    }
   private void renderSolid(Solid solid, Mat4 transMatrix, ZBuffer img){
       List<Vertex> tempVB = new ArrayList<>();
        //transformation of all vertices from vertexBuffer
       for(Vertex vertex : solid.getVertexBuffer()){
           vertex = new Vertex(vertex.getPosition().mul(transMatrix), vertex.getColor());
           tempVB.add(vertex);
       }

       for (Part part : solid.getPartBuffer()) {
           switch (part.getTopology()) {
               //primitive assembly (lines, triangles)
               case LINE_LIST -> {
                   //for each primitive
                   for(int i = part.getStartIndex(); i < part.getStartIndex() + part.getCount() * 2; i+=2){
                       //for each vertex
                       Vertex v1 = tempVB.get(solid.getIndexBuffer().get(i));
                       Vertex v2 = tempVB.get(solid.getIndexBuffer().get(i+1));
                       //if isInViewSpace
                       if(isInViewSpace(List.of(v1,v2))){
                           //dehomog
                           v1 = dehomog(v1);
                           v2 = dehomog(v2);
                           //transform toViewPort
                           Vertex viewportV1 = toViewport(v1, img.getWidth(), img.getHeight());
                           Vertex viewportV2 = toViewport(v2, img.getWidth(), img.getHeight());
                           //rasterize
                           rasterize(img, viewportV1, viewportV2);
                       }
                       else {
                           //clip for dehomog
                           List<Vertex> clipped = clipZ(v1,v2);
                           v1 = clipped.get(0);
                           v2 = clipped.get(1);
                           //dehomog
                           v1 = dehomog(v1);
                           v2 = dehomog(v2);
                           //transform toViewPort
                           Vertex viewportV1 = toViewport(v1, img.getWidth(), img.getHeight());
                           Vertex viewportV2 = toViewport(v2, img.getWidth(), img.getHeight());
                           //rasterize
                           rasterize(img, v1, v2);
                       }
                   }
               }
               case TRIANGLE_LIST -> {
                   for(int i = part.getStartIndex(); i < part.getStartIndex() + part.getCount() * 3; i+=3){
                       Vertex v1 = tempVB.get(solid.getIndexBuffer().get(i));
                       Vertex v2 = tempVB.get(solid.getIndexBuffer().get(i+1));
                       Vertex v3 = tempVB.get(solid.getIndexBuffer().get(i+2));

                       if(isInViewSpace(List.of(v1,v2,v3))){
                           //dehomog
                           v1 = dehomog(v1);
                           v2 = dehomog(v2);
                           v3 = dehomog(v3);
                           //transform toViewPort
                           Vertex viewportV1 = toViewport(v1, img.getWidth(), img.getHeight());
                           Vertex viewportV2 = toViewport(v2, img.getWidth(), img.getHeight());
                           Vertex viewportV3 = toViewport(v3, img.getWidth(), img.getHeight());
                           //rasterize
                           rasterizeVertex(img, viewportV1, viewportV2, viewportV3);
                       }
                       else {
                           //clip for dehomog
                           List<Vertex> clipped = clipZ(v1,v2,v3);
                           //clip can create 2 triangles (6 vertices)
                           for(int j = 0; j < clipped.size(); j+=3){
                               v1 = clipped.get(j);
                               v2 = clipped.get(j+1);
                               v3 = clipped.get(j+2);
                               //dehomog
                               v1 = dehomog(v1);
                               v2 = dehomog(v2);
                               v3 = dehomog(v3);
                               //transform toViewPort
                               Vertex viewportV1 = toViewport(v1, img.getWidth(), img.getHeight());
                               Vertex viewportV2 = toViewport(v2, img.getWidth(), img.getHeight());
                               Vertex viewportV3 = toViewport(v3, img.getWidth(), img.getHeight());
                               //rasterize
                               rasterizeVertex(img, viewportV1, viewportV2, viewportV3);
                           }
                       }

                   }

               }
           }
       }
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

    public Vertex dehomog(Vertex vertex) {
        double w = vertex.getPosition().getW();
        if (w != 0) {
            Point3D position = new Point3D(vertex.getPosition().getX() / w,
                    vertex.getPosition().getY() / w,
                    vertex.getPosition().getZ() / w);
            return new Vertex(position, vertex.getColor());
        } else {
            return vertex;
        }
    }
    private Vertex toViewport(Vertex vertex, int width, int height) {
        // Transform the vertex coordinates to viewport space
        double x = ((vertex.getPosition().getX() + 1) * (width-1)) / 2;
        double y = ((1 - vertex.getPosition().getY()) * (height-1)) / 2;
        double z = vertex.getPosition().getZ(); // No transformation on z-axis in viewport
        return new Vertex(new Point3D(x, y, z), vertex.getColor());
    }
}
