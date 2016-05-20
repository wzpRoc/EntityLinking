package org.ailab.tool;

import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntityBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.wimfra.database.DBUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 15-12-23
 * Time: 下午10:42
 * 生成候选实体
 */
public class MentionCandidateEntityGenerator {
    static Logger logger = Logger.getLogger(MentionCandidateEntityGenerator.class);

    public static void main(String[] args) throws Exception {
        // load mentions
        List<Mention> mentionList = (new MentionBL()).getList();
        logger.info(mentionList.size() + " mentions found");

        // create candidate entity list
        List<CandidateEntity> candidateEntityList = new ArrayList<CandidateEntity>();

        double minProbOfNameToEntity = 0.001;
        EntityNameMap entityNameMap = new EntityNameMap(minProbOfNameToEntity);

        for (int i=0; i<mentionList.size(); i++) {
            logger.debug("processing mention "+(i+1));
            Mention mention = mentionList.get(i);
            Map<Integer, Double> entityIdToProbMap = entityNameMap.getEntityIdToProbMap(mention.getName());
            if(entityIdToProbMap == null) {
                logger.warn("can not find a candidate entity for mention ["+mention.getName()+"]");
                continue;
            }
            for (Map.Entry<Integer, Double> entityIdNProb : entityIdToProbMap.entrySet()) {
                CandidateEntity candidateEntity = new CandidateEntity();
                candidateEntity.setMention(mention);
                candidateEntity.setEntityId(entityIdNProb.getKey());
                candidateEntity.setProbOfNameToEntity(entityIdNProb.getValue());

                candidateEntityList.add(candidateEntity);
            }
        }
        logger.info(candidateEntityList.size() + " candidate entities found");
        logger.info("average: " + (candidateEntityList.size() + mentionList.size()));

        new CandidateEntityBL().batchInsert(candidateEntityList);
    }

}
