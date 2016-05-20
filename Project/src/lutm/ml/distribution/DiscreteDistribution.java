package lutm.ml.distribution;

import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午6:00
 * 离散概率分布
 */
public class DiscreteDistribution extends Distribution implements Iterable<Map.Entry<Number, Double>> {
    protected Map<Number, Double> valueToProbabilityMap;

    public Map<Number, Double> getValueToProbabilityMap() {
        return valueToProbabilityMap;
    }

    public void setValueToProbabilityMap(Map<Number, Double> valueToProbabilityMap) {
        this.valueToProbabilityMap = valueToProbabilityMap;
    }

    @Override
    public double getProbability(Number value) {
        return valueToProbabilityMap.get(value);
    }

    @Override
    public Iterator<Map.Entry<Number, Double>> iterator() {
        return valueToProbabilityMap.entrySet().iterator();
    }
}
