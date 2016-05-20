package lutm.ml.model.perceptron;

import lutm.ml.core.Instance;
import lutm.ml.core.Instances;

/**
 * User: Lu Tingming
 * Date: 15-6-24
 * Time: 下午11:07
 * Desc:
 */
public class PerceptronLearner {
    protected int maxAdjustTimes = 10000;
    protected double w0=0;
    protected double b0=0;
    protected double learningRate=1;
    protected Perceptron perceptron;


    public Perceptron train(Instances instances) {
        initPerceptron(instances);

        boolean finished = false;
        for(int i=0; i<maxAdjustTimes; i++) {
            System.out.println("----------------  adjust "+(i+1));
            int errors = adjust(instances);
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


    private void initPerceptron(Instances instances) {
        perceptron = new Perceptron();
        perceptron.weights = new double[instances.getInputDimensions()];
        for(int i = 0; i<perceptron.weights.length; i++) {
            perceptron.weights[i] = w0;
        }
        perceptron.bias = b0;
    }


    /**
     * 扫描一遍数据集，对每个实例，如果分类错误，那么调整参数，最后返回误分类次数
     * @param instances
     * @return
     */
    private int adjust(Instances instances) {
        int errors = 0;

        for(int i=0; i<instances.size(); i++) {
            Instance instance = instances.get(i);
            if(adjust(instance)) {
                //
            } else {
                errors++;
            }
        }

        return errors;
    }


    private boolean adjust(Instance instance) {
        boolean correct = perceptron.test(instance);
        if(correct) {
            //
        } else {
            for(int i=0; i<perceptron.weights.length; i++) {
                perceptron.weights[i] = perceptron.weights[i] + learningRate*instance.xs[i]*instance.y;
            }
            perceptron.bias = perceptron.bias+learningRate*instance.y;
        }

        System.out.println(perceptron);
        return correct;
    }
}
