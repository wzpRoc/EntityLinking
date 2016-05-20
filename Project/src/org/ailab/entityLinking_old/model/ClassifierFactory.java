package org.ailab.entityLinking_old.model;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibLINEAR;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午10:01
 * Desc:
 */
public class ClassifierFactory {
    public static Classifier create(String model) {
        if("nb".equals(model)) {
            return new NaiveBayes();
        } else if("smo".equals(model)) {
            return new SMO();
        } else if("J48".equals(model)) {
            return new J48();
        } else if("libsvm".equals(model)) {
            return new LibSVM();
        } else if("liblinear".equals(model)) {
            return new LibLINEAR();
        } else {
            return null;
        }
    }
}
