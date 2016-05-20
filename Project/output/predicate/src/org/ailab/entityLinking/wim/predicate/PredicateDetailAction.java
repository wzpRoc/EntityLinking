package org.ailab.entityLinking.wim.predicate;

import org.ailab.entityLinking.wim.predicate.Predicate;
import org.ailab.entityLinking.wim.predicate.PredicateBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：谓词业务逻辑
 */


public class PredicateDetailAction extends BaseDetailAction {
    public PredicateDetailAction() {
        dto = new Predicate();
        bl = new PredicateBL();
    }
}
