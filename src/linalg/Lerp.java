package linalg;

public class Lerp {
    public static double computeDouble(double t, double minValue, double maxValue){
        return (1-t)*minValue+t*maxValue;
    }
}
