package org.ailab.tool.candidate;

import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntityBL;
import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.doc.DocBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: lutingming
 * Date: 16-1-1
 * Time: 下午12:53
 * Desc: 候选实体对分析
 */
public class CandidatePairAnalyser {
    private static Set<String> globalCandidatePairSet;
    private static Set<String> candidatePairSetInDoc;

    private static final DocBL docBL = new DocBL();
    private static final MentionBL mentionBL = (new MentionBL());
    private static CandidateEntityBL candidateEntityBL = new CandidateEntityBL();

    public static void main(String[] args) {
        globalCandidatePairSet = new HashSet<String>();

        // 对每篇文章逐个处理
        List<Doc> docList = docBL.getList();
        for(int i_doc=0; i_doc<docList.size(); i_doc++) {
            System.out.print("processing doc " + (i_doc+1) + "/" + docList.size());
            Doc doc = docList.get(i_doc);
            processDoc(doc);
        }
    }


    public static void processDoc(Doc doc) {
        List<Mention> mentionList = mentionBL.getDistinctByNameListByDocId(doc.getId());
        // 载入候选实体
        for(Mention mention : mentionList) {
            List<CandidateEntity> candidateList = candidateEntityBL.getListByMentionId(mention.getId(), 30);
            mention.setCandidateList(candidateList);
        }

        candidatePairSetInDoc = new HashSet<String>();
        // 开始组合
        for(int i=0; i<mentionList.size()-1; i++) {
            Mention mention_i = mentionList.get(i);
            for(int j=0; j<mentionList.size(); j++) {
                Mention mention_j = mentionList.get(j);
                // 两个mention开始咔咔咔
                processMentionAndMention(mention_i, mention_j);
            }
        }

        System.out.println("\t"+candidatePairSetInDoc.size() + "\t" + globalCandidatePairSet.size());
    }


    protected static void processMentionAndMention(Mention mention_i, Mention mention_j) {
        for(CandidateEntity ce_i : mention_i.getCandidateList()) {
            for(CandidateEntity ce_j : mention_j.getCandidateList()) {
                addToPairSet(ce_i.getEntityId(), ce_j.getEntityId());
            }
        }
    }


    private static void addToPairSet(int ceId0, int ceId1) {
        String key;
        if(ceId0 == ceId1) {
            return;
        } else if(ceId0 < ceId1) {
            key = ceId0 + " " + ceId1;
        } else {
            key = ceId1 + " " + ceId0;
        }
        if(candidatePairSetInDoc.add(key)) {
            globalCandidatePairSet.add(key);
        }
    }

}
