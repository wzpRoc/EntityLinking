package lutm.ml.model.perceptron;

import lutm.math.MathUtil;
import lutm.ml.core.Instance;
import lutm.ml.core.Instances;
import org.ailab.wimfra.util.NumberUtil;

/**
 * User: Lu Tingming
 * Date: 15-6-24
 * Time: 下午10:29
 * Desc:
 */
public class Perceptron {
    protected double[] weights;
    protected double bias;

    public int test(double[] xs) {
        double score = MathUtil.innerProduct(weights, xs) + bias;
        return score >= 0 ? 1 : -1;
    }

    public boolean test(Instance instance) {
        return test(instance.xs) == instance.y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(double weight : weights) {
            sb.append(NumberUtil.format(weight, 4, 4)).append("\t");
        }
        sb.append(NumberUtil.format(bias, 4, 4));

        return sb.toString();
    }
}
