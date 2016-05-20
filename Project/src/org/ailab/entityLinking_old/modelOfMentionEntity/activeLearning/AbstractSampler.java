package org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning;

import org.apache.log4j.Logger;


/**
 * User: Lu Tingming
 * Date: 15-5-19
 * Time: 上午1:42
 * Desc: 根据主动学习采用
 */
public abstract class AbstractSampler implements ISampler {
    protected Logger logger = Logger.getLogger(this.getClass());

    public String getName() {
        String className = this.getClass().getName();
        int idxOfLastDot = className.lastIndexOf(".");
        return className.substring(idxOfLastDot+1).replace("Sampler", "");
    }
}
