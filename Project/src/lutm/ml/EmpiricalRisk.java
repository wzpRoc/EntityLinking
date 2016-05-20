package lutm.ml;

import lutm.math.IFunction;
import lutm.ml.lossFunction.LossFunction;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午7:20
 * 经验风险：关于训练数据集的平均损失
 */
public class EmpiricalRisk implements IFunction {
    @Override
    public Object evaluate(Object... input) {
        return evaluate((TrainingSet) input[0], (IFunction) input[1], (LossFunction) input[2]);
    }

    /**
     * 在训练数据集trainingSet上，按照lossFunction，计算函数function的平均损失
     */
    public double evaluate(TrainingSet trainingSet, IFunction function, LossFunction lossFunction) {
        double sum = 0;
        for(InputOutput inputOutput : trainingSet) {
            sum += lossFunction.evaluate((Double) function.evaluate(inputOutput.input), (Double) inputOutput.output);
        }
        return sum/trainingSet.size();
    }
}
