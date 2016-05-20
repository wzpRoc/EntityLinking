package lutm.ml;

import lutm.ml.distribution.Distribution;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午6:12
 * 随机变量
 */
public class RandomVariable {
    protected Distribution distribution;

    public double getProbability(Number value) {
        return distribution.getProbability(value);
    }


    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }
}
