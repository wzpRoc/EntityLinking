package lutm.ml.expection;

import lutm.ml.IntegralEvaluator;
import lutm.ml.RandomVariable;
import lutm.ml.distribution.ContinuousDistribution;
import lutm.ml.distribution.DiscreteDistribution;
import lutm.ml.distribution.Distribution;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午4:32
 * 期望计算器
 */
public class ExpectationEvaluator {
    /**
     * 计算随机变量的期望
     */
    public static double evaluate(RandomVariable randomVariable) {
        return evaluate(randomVariable.getDistribution());
    }


    /**
     * 计算随机分布的期望
     */
    public static double evaluate(Distribution distribution) {
        if(distribution instanceof DiscreteDistribution) {
            return evaluate((DiscreteDistribution) distribution);
        } else {
            return evaluate((ContinuousDistribution) distribution);
        }
    }


    /**
     * 计算连续随机分布的期望
     */
    public static double evaluate(ContinuousDistribution distribution) {
        return IntegralEvaluator.evaluate(distribution);
    }


    /**
     * 计算离散随机分布的期望
     */
    public static double evaluate(DiscreteDistribution distribution) {
        double sum = 0;
        for(Map.Entry<Number, Double> entry : distribution.getValueToProbabilityMap().entrySet()) {
            sum += entry.getKey().doubleValue() * entry.getValue();
        }

        return sum;
    }
}
