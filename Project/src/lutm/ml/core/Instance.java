package lutm.ml.core;

/**
 * User: Lu Tingming
 * Date: 15-6-24
 * Time: 下午10:42
 * Desc:
 */
public class Instance {
    public double[] xs;
    public int y;

    public Instance(double[] xs, int y) {
        this.xs = xs;
        this.y = y;
    }
}
