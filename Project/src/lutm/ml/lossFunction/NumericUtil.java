package lutm.ml.lossFunction;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午4:14
 */
public class NumericUtil {
    public static boolean equals(double d0, double d1) {
        return Math.abs(d0 - d1)<0.00000000001;
    }
}
