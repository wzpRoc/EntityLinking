package org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature;

import org.ailab.entityLinking_old.wim.entity.Entity;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc: “实体-实体”特征生成器
 */
public interface IEntityEntityFeatureGenerator {
    public double generate(Entity entity0, Entity entity1);

    public String getFeatureName();

    public String getFeatureShortName();

}
