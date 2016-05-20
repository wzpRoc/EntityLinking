package lutm.ml;

import lutm.math.IFunction;
import lutm.ml.lossFunction.LossFunction;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午7:20
 * 结构风险
 */
public class StructuralRisk implements IFunction {
    ComplexityEvaluator complexityEvaluator;

    @Override
    public Object evaluate(Object... input) {
        return evaluate((TrainingSet) input[0], (IFunction) input[1], (LossFunction) input[2]);
    }

    /**
     * 结构风险=经验风险+模型复杂度
     */
    public double evaluate(TrainingSet trainingSet, IFunction predicateFunction, LossFunction lossFunction, double lambda) {
        return new EmpiricalRisk().evaluate(trainingSet, predicateFunction, lossFunction)
                + lambda * complexityEvaluator.evaluate(predicateFunction);
    }
}
