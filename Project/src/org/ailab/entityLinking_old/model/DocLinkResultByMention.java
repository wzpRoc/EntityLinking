package org.ailab.entityLinking_old.model;

import org.ailab.entityLinking_old.modelOfMentionEntity.MentionLinkResult;
import org.ailab.entityLinking_old.experiment.Experiment;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.linkResult.LinkResult;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 上午1:20
 * Desc: 一篇文档的链接结果
 */
public class DocLinkResultByMention extends DocLinkResult {
    public List<MentionLinkResult> mentionLinkResultList;


    public DocLinkResultByMention(Doc doc) {
        super(doc);
        this.mentionLinkResultList = new ArrayList<MentionLinkResult>();
    }


    public void addClassificationResult(MentionLinkResult mentionLinkResult) {
        mentionLinkResultList.add(mentionLinkResult);
    }


    /**
     * 求得最大概率、次大概率、最大次大概率差
     */
    public void processAfterClassification() {
        if (mentionLinkResultList == null || mentionLinkResultList.size() == 0) {
            throw new RuntimeException("mentionLinkResultList is empty");
        }

        probability0 = 0;
        probability1 = 0;
        for (MentionLinkResult mentionLinkResult : mentionLinkResultList) {
            if (mentionLinkResult.maxProbability > probability0) {
                probability0 = mentionLinkResult.maxProbability;
                probability1 = mentionLinkResult.secondProbability;
            }
        }

        probabilityDiff = probability0 - probability1;
        maxProbabilityRatio = probability0 / (probability0 + probability1);
    }

    @Override
    public List<LinkResult> toLinkResult(Experiment experiment) {
        List<LinkResult> linkResultList = new ArrayList<LinkResult>();
            for(MentionLinkResult mentionLinkResult : mentionLinkResultList) {
                linkResultList.add(mentionLinkResult.toLinkResult(experiment));
            }

        return linkResultList;
    }
}
