package org.ailab.entityLinking_old.experiment;

import org.ailab.entityLinking_old.model.LinkModelType;
import org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning.ISampler;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 下午8:48
 * Desc:
 */
public class ALExperimentGroup extends ExperimentGroup {
    protected int initialTrainingDocSize = 50;
    protected int stepDocSize = 50;
    protected int steps = 9;
    protected int testingDocSize = 200;

    public LinkModelType linkModelType;
    protected String classificationModel;
    public String featureDesc;
    public Object featureGenerators;

    public ISampler sampler;
    public String docFilter;


    public ALExperimentGroup(String batchName, String batchStartTime, String name, String startTime, LinkModelType linkModelType, String classificationModel, String featureDesc, Object featureGenerators, ISampler sampler) {
        super(batchName, batchStartTime, name, startTime);
        this.linkModelType = linkModelType;
        this.classificationModel = classificationModel;
        this.featureDesc = featureDesc;
        this.featureGenerators = featureGenerators;
        this.sampler = sampler;
    }

    public void setDocSize(String docFilter, int initialTrainingDocSize, int stepDocSize, int steps, int testingDocSize) {
        this.docFilter = docFilter;
        this.initialTrainingDocSize = initialTrainingDocSize;
        this.stepDocSize = stepDocSize;
        this.steps = steps;
        this.testingDocSize = testingDocSize;
    }
}
