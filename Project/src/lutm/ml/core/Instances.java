package lutm.ml.core;

import java.util.ArrayList;

/**
 * User: Lu Tingming
 * Date: 15-6-24
 * Time: 下午10:42
 * Desc:
 */
public class Instances extends ArrayList<Instance> {
    private int inputDimensions = -1;

    public int getInputDimensions() {
        if(inputDimensions < 0) {
            inputDimensions = get(0).xs.length;
        }
        return inputDimensions;
    }
}
