package org.ailab.entityLinking_old.experiment;

import org.apache.log4j.Logger;


import java.util.*;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午7:50
 * Desc:
 */
public class ExperimentContext {
    protected final Logger logger = Logger.getLogger(this.getClass());
    protected TrainingCorpusContext trainingCorpusContext;
    protected TestingCorpusContext testingCorpusContext;

    public ExperimentContext(Set<Integer> trainingDocGroupIdSet, Set<Integer> testingDocGroupIdSet) {
        trainingCorpusContext = new TrainingCorpusContext(trainingDocGroupIdSet);
        testingCorpusContext = new TestingCorpusContext(testingDocGroupIdSet);
    }

    public TrainingCorpusContext getTrainingCorpusContext() {
        return trainingCorpusContext;
    }

    public TestingCorpusContext getTestingCorpusContext() {
        return testingCorpusContext;
    }
}
