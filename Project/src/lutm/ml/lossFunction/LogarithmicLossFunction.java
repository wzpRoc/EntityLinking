package lutm.ml.lossFunction;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-6
 * Time: 下午4:06
 * 绝对损失函数
 */
public class LogarithmicLossFunction extends LossFunction {
    /**
     * @param y 真实值
     * @param fx 预测值
     * @return 损失
     */
    public double evaluate(double y, double fx) {
        return -Math.log(y - fx);
    }
}
