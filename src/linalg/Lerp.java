package linalg;

import objectdata.Vertex;

public class Lerp {
    public static double computeDouble(double t, double minValue, double maxValue){
        return (1-t)*minValue+t*maxValue;
    }
    public static int computeInt(double t, int minValue, int maxValue){
        return (int)((1-t)*minValue+t*maxValue);
    }

//    public static Vertex computeVertex(double t, Vertex minValue, Vertex maxValue){
//        return minValue.mul(1-t).add(maxValue.mul(t));
//    }

    public static <T extends Vectorizable<T>> T compute(double t, T minValue, T maxValue){
        return minValue.mul(1-t).add(maxValue.mul(t));
    }
}
