package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntityBL;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.cache.Cache;

import java.util.List;

/**
 * User: lutingming
 * Date: 16-1-16
 * Time: 下午2:22
 * Desc: 维基页面摘要缓存测试类
 */
public class PageAbstCacheTester {
    public static void main(String[] args) {
        PageAbstCache cache = new PageAbstCache(100);
        CandidateEntityBL bl = new CandidateEntityBL();
        List<CandidateEntity> list = bl.getLimitedList(10000);
        for(CandidateEntity ce : list) {
            String abst = cache.get(ce.getEntityId());
            cache.printStat();
        }
    }
}
