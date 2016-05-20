package org.ailab.entityLinking_old.experiment;

import org.ailab.entityLinking_old.model.LinkModelType;
import org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning.AbstractSampler;

/**
 * User: Lu Tingming
 * Date: 15-6-6
 * Time: 上午3:59
 * Desc:
 */
public class Batch {
    // 实验组名、开始时间
    public String batchName;
    public String batchStartTime;
    public String docFilter;
    public LinkModelType linkModelType;
    // 分类模型
    public String[] classificationModels;
    public AbstractSampler[] samplerList;

    public Object[] docFeatureGenerators;

    public Batch(String batchName, String docFilter, LinkModelType linkModelType, AbstractSampler[] samplerList, String[] classificationModels, Object[] docFeatureGenerators) {
        this.batchName = batchName;
        this.docFilter = docFilter;
        this.linkModelType = linkModelType;
        this.classificationModels = classificationModels;
        this.samplerList = samplerList;
        this.docFeatureGenerators = docFeatureGenerators;
    }
}
