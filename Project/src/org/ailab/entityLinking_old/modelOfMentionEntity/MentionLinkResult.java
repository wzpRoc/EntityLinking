package org.ailab.entityLinking_old.modelOfMentionEntity;

import org.ailab.entityLinking_old.experiment.Experiment;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.entityLinking_old.wim.linkResult.LinkResult;
import org.ailab.wimfra.util.SortUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 上午1:20
 * Desc: 一个mention链接到多个候选实体，保存这些信息
 */
public class MentionLinkResult {
    public DocEntity docEntity;
    public List<MentionEntityClassificationResult> mentionEntityClassificationResultList;

    // 最可能的实体
    public Entity mostProbableCandidateEntity;
    // 是否正确
    public boolean correct;
    // 最大的概率
    public double maxProbability;
    public double secondProbability;
    // 最大的概率与次大的概率的差值
    public double probabilityDiff;
    public MentionEntityClassificationResult secondProbableResult;

    public boolean hasCorrectCandidate;

    public MentionLinkResult(DocEntity docEntity, int candidateEntityCount) {
        this.docEntity = docEntity;
        mentionEntityClassificationResultList = new ArrayList<MentionEntityClassificationResult>(candidateEntityCount);
    }

    public void addClassificationResult(MentionEntityClassificationResult mentionEntityClassificationResult) {
        mentionEntityClassificationResultList.add(mentionEntityClassificationResult);
    }

    public void processAfterClassification() {
        // 从大到小排序
        //noinspection unchecked
        mentionEntityClassificationResultList = SortUtil.sort(mentionEntityClassificationResultList, false);

        if(mentionEntityClassificationResultList.size()>0) {
            MentionEntityClassificationResult mostProbableResult = mentionEntityClassificationResultList.get(0);
            mostProbableCandidateEntity = mostProbableResult.candidateEntity;
            maxProbability = mostProbableResult.probability;
            correct = mostProbableCandidateEntity.getId() == docEntity.getEntityId();
        } else {
            maxProbability = 0;
            correct = false;
        }

        if(mentionEntityClassificationResultList.size()>1) {
            secondProbableResult = mentionEntityClassificationResultList.get(1);
            probabilityDiff = maxProbability - secondProbableResult.probability;
        } else {
            secondProbableResult = null;
        }

        hasCorrectCandidate = false;
        if(mentionEntityClassificationResultList !=null) {
            for(MentionEntityClassificationResult mentionEntityClassificationResult : mentionEntityClassificationResultList) {
                if(mentionEntityClassificationResult.candidateEntity.getId() == docEntity.getEntityId()) {
                    hasCorrectCandidate = true;
                    break;
                }
            }
        }
    }


    public LinkResult toLinkResult(Experiment experiment) {
        LinkResult linkResult = new LinkResult();
        linkResult.setDocId(docEntity.getDocId());
        linkResult.setPubdate(docEntity.getPubdate());
        linkResult.setEntityType(docEntity.getEntityType());
        linkResult.setEntityId(docEntity.getEntityId());
        linkResult.setEntityTitle(docEntity.getEntityTitle());
        linkResult.setMention(docEntity.getMention());
        linkResult.setStartIdx(docEntity.getStartIdx());
        linkResult.setEndIdx(docEntity.getEndIdx());

        linkResult.setBatchName(experiment.batchName);
        linkResult.setBatchStartTime(experiment.batchStartTime);
        linkResult.setGroupName(experiment.groupName);
        linkResult.setSamplerName(experiment.samplerName);
        linkResult.setStepName(experiment.stepName);
        linkResult.setFeatureDesc(experiment.featureDesc);
        linkResult.setExperimentName(experiment.name);
        linkResult.setClassificationModel(experiment.classificationModel);
        linkResult.setStartTime(experiment.startTime);

        linkResult.setProbability0(maxProbability);
        linkResult.setEntityId0(mostProbableCandidateEntity == null? 0 : mostProbableCandidateEntity.getId());
        linkResult.setEntityTitle0(mostProbableCandidateEntity == null? null : mostProbableCandidateEntity.getTitle());

        if(secondProbableResult!=null) {
            secondProbability = secondProbableResult.probability;
            linkResult.setProbability1(maxProbability);
            linkResult.setEntityId1(secondProbableResult.candidateEntity.getId());
            linkResult.setEntityTitle1(secondProbableResult.candidateEntity.getTitle());
        } else {
            secondProbability = 0;
        }
        linkResult.setProbabilityDiff(probabilityDiff);
        linkResult.setCorrect(correct?1:0);
        linkResult.setHasCorrectCandidate(hasCorrectCandidate?1:0);

        return linkResult;
    }

    @Override
    public String toString() {
        return docEntity.getMention()+"->"+mostProbableCandidateEntity.toString()
                + " "+(correct?"TRUE":"FALSE");
    }


}
