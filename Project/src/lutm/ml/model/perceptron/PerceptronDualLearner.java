package lutm.ml.model.perceptron;

import lutm.math.MathUtil;
import lutm.ml.core.Instance;
import lutm.ml.core.Instances;
import org.ailab.wimfra.util.NumberUtil;

/**
 * User: Lu Tingming
 * Date: 15-6-24
 * Time: 下午11:07
 * Desc:
 */
public class PerceptronDualLearner {
    protected int maxAdjustTimes = 10000;
    protected double w0=0;
    protected double b0=0;
    protected double learningRate=1;

    protected Instances instances;
    protected double[] alphas;
    protected double[][] gramMatrix;

    protected Perceptron perceptron;

    public Perceptron train(Instances instances) {
        init(instances);

        boolean finished = false;
        for(int i=0; i<maxAdjustTimes; i++) {
            System.out.println("----------------  adjust "+(i+1));
            int errors = adjust();
            System.out.println("errors="+errors);

            if(errors == 0) {
                finished = true;
                break;
            }
        }

        if(finished) {
            return perceptron;
        } else {
            return null;
        }
    }


    private void init(Instances instances) {
        this.instances = instances;

        perceptron = new Perceptron();
        alphas = new double[instances.size()];
        for(int i = 0; i<alphas.length; i++) {
            alphas[i] = w0;
        }
        perceptron.bias = b0;

        gramMatrix = new double[instances.size()][];
        for(int i = 0; i<instances.size(); i++) {
            gramMatrix[i] = new double[instances.size()];
            for(int j = 0; j<instances.size(); j++) {
                double temp = MathUtil.innerProduct(instances.get(i).xs, instances.get(j).xs);
                System.out.print(NumberUtil.format(temp, 4, 0)+"\t");
                gramMatrix[i][j] = temp;
            }
            System.out.println();
        }
    }


    /**
     * 扫描一遍数据集，对每个实例，如果分类错误，那么调整参数，最后返回误分类次数
     * @return
     */
    private int adjust() {
        int errors = 0;

        for(int i=0; i<instances.size(); i++) {
            Instance instance = instances.get(i);
            if(adjust(instance, i)) {
                //
            } else {
                errors++;
            }
        }

        return errors;
    }


    private boolean adjust(Instance instance_i, int idx) {
        boolean correct = test(instance_i);
        if(correct) {
            //
        } else {
            alphas[idx] += learningRate;
            perceptron.bias += learningRate*instance_i.y;
            print();
        }

        return correct;
    }


    private void print() {
        for(int i=0; i<alphas.length; i++) {
            System.out.print(alphas[i]+"\t");
        }
        System.out.println(perceptron.bias);
    }


    private boolean test(Instance instance_i) {
        double sum = 0;
        for(int j=0; j<instances.size(); j++) {
            Instance instance_j = instances.get(j);
            double[] v_j = MathUtil.product(alphas[j] * instance_j.y, instance_j.xs);
            sum += MathUtil.innerProduct(instance_i.xs, v_j);
        }

        return instance_i.y * (sum + perceptron.bias) > 0;
    }
}
