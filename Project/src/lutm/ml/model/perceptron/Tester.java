package lutm.ml.model.perceptron;

import lutm.ml.core.Instance;
import lutm.ml.core.Instances;

/**
 * User: Lu Tingming
 * Date: 15-6-24
 * Time: 下午10:44
 * Desc:
 */
public class Tester {
    static Instances instances;

    public static void initTrainingSet() {
        instances = new Instances();
        instances.add(new Instance(new double[]{3, 3}, 1));
        instances.add(new Instance(new double[]{4, 3}, 1));
        instances.add(new Instance(new double[]{1, 1}, -1));
    }


    public static void testPerceptronLearner() {
        new PerceptronLearner().train(instances);
    }

    public static void testPerceptronDualLearner() {
        new PerceptronDualLearner().train(instances);
    }


    public static void main(String[] args) {
        initTrainingSet();
        // testPerceptronLearner();
        testPerceptronDualLearner();
    }
}
