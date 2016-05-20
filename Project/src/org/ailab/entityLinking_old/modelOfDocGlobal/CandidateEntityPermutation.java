package org.ailab.entityLinking_old.modelOfDocGlobal;

import org.ailab.entityLinking_old.model.Label;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-30
 * Time: 上午1:18
 * Desc: 候选实体排列
 */
public class CandidateEntityPermutation extends ArrayList<Entity> {
    public Label label = Label.unknown;

    public CandidateEntityPermutation() {
    }

    public CandidateEntityPermutation(int initialCapacity) {
        super(initialCapacity);
    }

    public CandidateEntityPermutation(Collection<? extends Entity> c) {
        super(c);
    }

    public boolean isCorrect(List<DocEntity> correctDocEntityList) {
        for(int i=0; i<this.size(); i++) {
            Entity candidateEntity = this.get(i);
            DocEntity correctDocEntity = correctDocEntityList.get(i);
            if(candidateEntity.getId() != correctDocEntity.getEntityId()) {
                return false;
            }
        }

        return true;
    }
}
