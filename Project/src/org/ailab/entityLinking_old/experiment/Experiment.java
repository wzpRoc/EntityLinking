package org.ailab.entityLinking_old.experiment;

import org.ailab.entityLinking_old.model.LinkModelType;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature.IDocFeatureGenerator;
import org.ailab.wimfra.util.time.TimeUtil;

import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午9:58
 * Desc:
 */
public class Experiment {
    public String batchName;
    public String batchStartTime;
    public String groupName;
    public String samplerName;
    public String stepName;
    public String featureDesc;
    public String name;
    public LinkModelType linkModelType;
    public String classificationModel;
    public String startTime;
    public Set<Integer> trainingDocIdSet;
    public Set<Integer> testingDocIdSet;
    public Object featureGenerators;
    public IDocFeatureGenerator[] docFeatureGenerators;

    public Experiment(String batchName, String batchStartTime, String groupName, String samplerName, String stepName, String startTime, String featureDesc, String name, LinkModelType linkModelType, String classificationModel, Object featureGenerators, Set<Integer> trainingDocIdSet, Set<Integer> testingDocIdSet) {
        this.batchName = batchName;
        this.batchStartTime = batchStartTime;
        this.groupName = groupName;
        this.samplerName = samplerName;
        this.stepName = stepName;
        this.startTime = startTime;
        this.featureDesc = featureDesc;
        this.name = name;
        this.linkModelType = linkModelType;
        this.classificationModel = classificationModel;
        this.featureGenerators = featureGenerators;
        this.startTime = TimeUtil.getYyyy_mm_dd_hh_mm_ss();
        this.trainingDocIdSet = trainingDocIdSet;
        this.testingDocIdSet = testingDocIdSet;
    }

    public Set<Integer> getTrainingDocIdSet() {
        return trainingDocIdSet;
    }

    public void setTrainingDocIdSet(Set<Integer> trainingDocIdSet) {
        this.trainingDocIdSet = trainingDocIdSet;
    }

    public Set<Integer> getTestingDocIdSet() {
        return testingDocIdSet;
    }

    public void setTestingDocIdSet(Set<Integer> testingDocIdSet) {
        this.testingDocIdSet = testingDocIdSet;
    }
}
