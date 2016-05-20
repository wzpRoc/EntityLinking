package org.ailab.entityLinking.wim.candidateEntity;

import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntityBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：指称的候选实体业务逻辑
 */


public class CandidateEntityDetailAction extends BaseDetailAction {
    public CandidateEntityDetailAction() {
        dto = new CandidateEntity();
        bl = new CandidateEntityBL();
    }
}
