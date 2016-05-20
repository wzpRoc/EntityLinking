package org.ailab.tool.candidate;

import org.ailab.entityLinking.Constants;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntityBL;
import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.entityToEntityLink.EntityInlinkCountCache;
import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLinkBL;
import org.ailab.entityLinking.wim.entityToEntityLink.InlinkCache;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.tool.EntityToEntityLink.EERelatednessMap;
import org.ailab.wimfra.util.CollectionUtil;
import org.ailab.wimfra.util.Stopwatch;

import java.util.List;
import java.util.Set;

/**
 * User: lutingming
 * Date: 16-1-1
 * Time: 下午12:53
 * Desc: 候选实体对语义关联度计算器（对一篇文档）
 */
public class DocEERelatednessProcessor {
    private EERelatednessMap EERelatednessMap;
    private Doc doc;

    private final MentionBL mentionBL = (new MentionBL());
    private final CandidateEntityBL candidateEntityBL = new CandidateEntityBL();


    public DocEERelatednessProcessor(EERelatednessMap EERelatednessMap, Doc doc) {
        this.EERelatednessMap = EERelatednessMap;
        this.doc = doc;
    }


    public void processDoc() {
        List<Mention> mentionList = mentionBL.getDistinctByNameListByDocId(doc.getId());
        // 载入候选实体
        for(Mention mention : mentionList) {
            List<CandidateEntity> candidateList = candidateEntityBL.getListByMentionId(mention.getId());
            mention.setCandidateList(candidateList);
        }

        // 开始组合
        for(int i=0; i<mentionList.size()-1; i++) {
            Mention mention_i = mentionList.get(i);
            for(int j=0; j<mentionList.size(); j++) {
                Mention mention_j = mentionList.get(j);
                // 两个mention开始咔咔咔
                processMentionAndMention(mention_i, mention_j);
            }
        }
    }


    protected void processMentionAndMention(Mention mention_i, Mention mention_j) {
        for(CandidateEntity ce_i : mention_i.getCandidateList()) {
            for(CandidateEntity ce_j : mention_j.getCandidateList()) {
                int entityId_i = ce_i.getEntityId();
                int entityId_j = ce_j.getEntityId();
                if(entityId_i == entityId_j) {
                    // return 1
                } else {
                    if(EERelatednessMap.contains(entityId_i, entityId_j)) {
                        // ok
                    } else {
                        // 开始计算
                        double sr = calcSemanticRelatedness(entityId_i, entityId_j);
                        EERelatednessMap.put(entityId_i, entityId_j, sr);
                    }
                }
            }
        }
    }


    protected double calcSemanticRelatedness(int entityId0, int entityId1) {
        int inlinkCount0 = EntityInlinkCountCache.getInlinkCount(entityId0);
        if(inlinkCount0 == 0) {
            return 0;
        }

        int inlinkCount1 = EntityInlinkCountCache.getInlinkCount(entityId1);
        if(inlinkCount1 == 0) {
            return 0;
        }

        // 求交集数量
        int intersectionsSize = getIntersectionsSize(entityId0, entityId1);
        if(intersectionsSize == 0) {
            return 0;
        }

        int minInlinkCount = Math.min(inlinkCount0, inlinkCount1);
        int maxInlinkCount = Math.max(inlinkCount0, inlinkCount1);

        double numerator = Math.log(maxInlinkCount)-Math.log(intersectionsSize);
        double denominator = Constants.totalInlinks_log - Math.log(minInlinkCount);

        double result = 1 - (numerator / denominator);

        return result;
    }


    protected int getIntersectionsSize(int entityId0, int entityId1) {
        Set<Integer> inlinks0 = InlinkCache.getInlinkSet(entityId0);
        if(inlinks0 == null || inlinks0.size()==0) {
            return 0;
        }
        Set<Integer> inlinks1 = InlinkCache.getInlinkSet(entityId1);

        return CollectionUtil.getIntersectionSize(inlinks0, inlinks1);
    }
}
