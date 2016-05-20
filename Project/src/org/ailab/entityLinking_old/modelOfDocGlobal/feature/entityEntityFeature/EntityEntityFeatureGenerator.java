package org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature;

import org.ailab.entityLinking_old.cache.EntityCache;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.apache.log4j.Logger;


/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc: “提及-实体”特征生成器
 */
public abstract class EntityEntityFeatureGenerator implements IEntityEntityFeatureGenerator {
    protected final Logger logger = Logger.getLogger(this.getClass());
    // 特征生成是与训练数据相关的
    protected TrainingCorpusContext trainingCorpusContext;

    @Override
    public abstract double generate(Entity entity0, Entity entity1);

    public double generate(int entityId0, int entityId1) {
        Entity entity0 = EntityCache.getById(entityId0);
        Entity entity1 = EntityCache.getById(entityId1);
        return generate(entity0, entity1);
    }

    public double generate(String entityTitle0, String entityTitle1) {
        Entity entity0 = EntityCache.getByTitle(entityTitle0);
        Entity entity1 = EntityCache.getByTitle(entityTitle1);
        return generate(entity0, entity1);
    }

    public String getFeatureName() {
        return this.getClass().getName();
    }

    public String getFeatureShortName() {
        return this.getClass().getName();
    }

    public TrainingCorpusContext getTrainingCorpusContext() {
        return trainingCorpusContext;
    }

    public void setTrainingCorpusContext(TrainingCorpusContext trainingCorpusContext) {
        this.trainingCorpusContext = trainingCorpusContext;
    }
}
