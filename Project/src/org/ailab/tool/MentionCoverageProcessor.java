package org.ailab.tool;

import org.ailab.entityLinking.wim.entityName.EntityName;
import org.ailab.entityLinking.wim.entityName.EntityNameBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.wimfra.database.DBUtil;

import java.util.*;

/**
 * User: lutingming
 * Date: 15-12-23
 * Time: 下午10:42
 */
public class MentionCoverageProcessor {
    private static List<Mention> mentionList;
    private static MentionBL mentionBL;

    public static void main(String[] args) throws Exception {
        loadMentions();
        calcCoverage();
    }

    private static void loadMentions() {
        mentionBL = new MentionBL();
        mentionList = mentionBL.getList();

        System.out.println("mentionList.size = " + mentionList.size());
    }

    /**
     * 计算覆盖率
     * @throws Exception
     */
    private static void process() throws Exception {

    }


    /**
     * 计算覆盖率
     * @throws Exception
     */
    private static void calcCoverage() throws Exception {
        List<Vector> valueVectorList = new ArrayList<Vector>(mentionList.size());
        int totalCandidateEntities = 0;
        for(Mention mention : mentionList) {
            int existsNameInEN;
            int exactNameNEntityInEN;
            Map<Integer, Double> entityIdToProbMap = EntityNameMap.getInstance().getEntityIdToProbMap(mention.getName());
            if(entityIdToProbMap == null) {
                existsNameInEN = 0;
                exactNameNEntityInEN = 0;
            } else {
                totalCandidateEntities += entityIdToProbMap.size();
                existsNameInEN = 1;
                if(entityIdToProbMap.containsKey(mention.getEntityId())) {
                    exactNameNEntityInEN = 1;
                } else {
                    exactNameNEntityInEN = 0;
                }
            }
            Vector vector = new Vector();
            vector.add(existsNameInEN);
            vector.add(exactNameNEntityInEN);
            vector.add(mention.getId());
            valueVectorList.add(vector);
        }

        System.out.println("total mentions : " + mentionList.size());
        System.out.println("total candidate entities : " + totalCandidateEntities);
        System.out.println("average : " + (totalCandidateEntities/mentionList.size()));

        DBUtil.batchUpdate(
                "update mention set " +
                        "existsNameInEN=?, " +
                        "exactMatchWithEN=? " +
                        "where id=?",
                valueVectorList
        );
    }

}
