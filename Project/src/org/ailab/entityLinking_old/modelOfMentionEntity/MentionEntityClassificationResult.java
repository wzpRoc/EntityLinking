package org.ailab.entityLinking_old.modelOfMentionEntity;

import org.ailab.entityLinking_old.wim.entity.Entity;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 上午1:20
 * Desc:
 */
public class MentionEntityClassificationResult implements Comparable<MentionEntityClassificationResult> {
    public Entity candidateEntity;
    public double probability;

    public MentionEntityClassificationResult(Entity candidateEntity, double probability) {
        this.candidateEntity = candidateEntity;
        this.probability = probability;
    }

    @Override
    public int compareTo(MentionEntityClassificationResult o) {
        double diff = this.probability - o.probability;
        if(diff<0) {
            return -1;
        } else if(diff==0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return candidateEntity.toString()+" "+probability;
    }
}
