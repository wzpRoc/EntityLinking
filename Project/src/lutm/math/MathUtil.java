package lutm.math;

/**
 * User: Lu Tingming
 * Date: 15-6-24
 * Time: 下午11:16
 * Desc:
 */
public class MathUtil {
    public static double innerProduct(double[] v1, double[] v2) {
        double sum = 0;
        for(int i=0; i<v1.length; i++) {
            sum+= v1[i] * v2[i];
        }

        return sum;
    }


    public static double[] product(double d, double[] v) {
        double[] result = new double[v.length];
        for(int i=0; i<v.length; i++) {
            result[i] = d * v[i];
        }

        return result;
    }
}
