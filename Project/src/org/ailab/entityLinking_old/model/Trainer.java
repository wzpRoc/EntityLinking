package org.ailab.entityLinking_old.model;

import org.ailab.entityLinking_old.feature.DocFeatureVectorAssembler;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.Stopwatch;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.SelectedTag;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午10:01
 * Desc:
 */
public class Trainer {
    Logger logger = Logger.getLogger(this.getClass());
    TrainingCorpusContext corpusContext;
    DocFeatureVectorAssembler docFeatureVectorAssembler;
    protected String model;

    public Trainer(String model, TrainingCorpusContext corpusContext, DocFeatureVectorAssembler docFeatureVectorAssembler) {
        this.model = model;
        this.corpusContext = corpusContext;
        this.docFeatureVectorAssembler = docFeatureVectorAssembler;
    }

    public Classifier train() {
        Instances instances = docFeatureVectorAssembler.loadInstancesForTraining(corpusContext.getDocInfoList());

        Classifier classifier = ClassifierFactory.create(model);
        if(classifier instanceof LibSVM) {
            logger.info("LibSVM.ProbabilityEstimates = "+((LibSVM) classifier).getProbabilityEstimates());
            ((LibSVM) classifier).setProbabilityEstimates(true);
            ((LibSVM) classifier).setKernelType(new SelectedTag(LibSVM.KERNELTYPE_LINEAR, LibSVM.TAGS_KERNELTYPE));
        }

        logger.info("start to buildClassifier");
        Stopwatch st = new Stopwatch();
        try {
            classifier.buildClassifier(instances);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("finished to buildClassifier, time cost=" + st.stopAndGetFormattedTime());

        return classifier;
    }
}
