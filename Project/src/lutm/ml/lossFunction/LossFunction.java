package lutm.ml.lossFunction;

import lutm.math.IFunction;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午4:06
 * 损失函数
 * 损失函数为非负实值函数
 */
public abstract class LossFunction implements IFunction {
    public Object evaluate(Object... input) {
        return evaluate((Double) input[0], (Double) input[1]);
    }

    /**
     * @param y 真实值
     * @param fx 预测值
     * @return 损失
     */
    public abstract double evaluate(double y, double fx);
}
