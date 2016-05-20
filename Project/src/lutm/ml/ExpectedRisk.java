package lutm.ml;

import lutm.math.IFunction;
import lutm.ml.distribution.DiscreteDistribution;
import lutm.ml.lossFunction.LossFunction;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午7:20
 * 期望风险：函数在分布下的风险的期望
 */
public class ExpectedRisk implements IFunction {
    @Override
    public Object evaluate(Object... input) {
        int inputIdx = 0;
        return evaluate(
                (DiscreteDistribution) input[inputIdx++],
                (IFunction) input[inputIdx++],
                (IFunction) input[inputIdx++],
                (LossFunction) input[inputIdx]
        );
    }

    /**
     * 在训练数据集trainingSet上，按照lossFunction，计算函数function的平均损失
     */
    public double evaluate(DiscreteDistribution distribution, IFunction function, IFunction predictFunction, LossFunction lossFunction) {
        double sum = 0;
        for(Map.Entry<Number, Double> entry : distribution) {
            final Number key = entry.getKey();
            final Double probability = entry.getValue();
            sum += lossFunction.evaluate(
                    (Double) function.evaluate(key),
                    (Double) predictFunction.evaluate(key)
            ) * probability;
        }
        return sum;
    }
}
