package lutm.ml.model.perceptron;

import lutm.ml.core.Instance;
import lutm.ml.core.Instances;

/**
 * User: Lu Tingming
 * Date: 15-6-24
 * Time: 下午10:44
 * Desc:
 */
public class PerceptronTester {
    Instances instances;

    public void initTrainingSet() {
        instances = new Instances();
        instances.add(new Instance(new double[]{3, 3}, 1));
        instances.add(new Instance(new double[]{4, 3}, 1));
        instances.add(new Instance(new double[]{1, 1}, -1));
    }


}
