package org.ailab.entityLinking_old.model;

import org.ailab.entityLinking_old.modelOfDocGlobal.CandidateEntityPermutation;
import org.ailab.entityLinking_old.modelOfDocGlobal.CandidateEntityPermutationClassificationResult;
import org.ailab.entityLinking_old.experiment.Experiment;
import org.ailab.entityLinking_old.wim.doc.Doc;
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
 * Desc: 一篇文档的链接结果
 */
public class DocLinkResultByPermutation extends DocLinkResult {
    public List<DocEntity> mentionList;
    public List<CandidateEntityPermutationClassificationResult> permutationClassificationResultList;
    public CandidateEntityPermutation permutation0;
    public CandidateEntityPermutation permutation1;
    // 是否正确
    public boolean correct;


    public DocLinkResultByPermutation(Doc doc, List<DocEntity> mentionList) {
        super(doc);
        this.mentionList = mentionList;
        permutationClassificationResultList = new ArrayList<CandidateEntityPermutationClassificationResult>();
    }


    public void addClassificationResult(CandidateEntityPermutationClassificationResult permutationClassificationResult) {
        permutationClassificationResultList.add(permutationClassificationResult);
    }


    public void processAfterClassification() {
        // 从大到小排序
        //noinspection unchecked
        permutationClassificationResultList = SortUtil.sort(permutationClassificationResultList, false);

        if (permutationClassificationResultList.size() > 0) {
            CandidateEntityPermutationClassificationResult result0 = permutationClassificationResultList.get(0);
            permutation0 = result0.candidateEntityPermutation;
            probability0 = result0.probability;
            correct = permutation0.isCorrect(mentionList);
        } else {
            probability0 = 0;
            correct = false;
        }

        if (permutationClassificationResultList.size() > 1) {
            CandidateEntityPermutationClassificationResult result1 = permutationClassificationResultList.get(1);
            permutation1 = result1.candidateEntityPermutation;
            probability1 = result1.probability;
            probabilityDiff = probability0 - probability1;
        } else {
            permutation1 = null;
            probability1 = 0;
        }
    }


    public List<LinkResult> toLinkResult(Experiment experiment) {
        List<LinkResult> linkResultList = new ArrayList<LinkResult>(mentionList.size());
        for(int mentionIdx=0; mentionIdx<mentionList.size(); mentionIdx++) {
            LinkResult linkResult = toLinkResult(mentionIdx, experiment);
            linkResultList.add(linkResult);
        }

        return linkResultList;
    }


    public LinkResult toLinkResult(int mentionIdx, Experiment experiment) {
        DocEntity mention = mentionList.get(mentionIdx);

        LinkResult linkResult = new LinkResult();
        linkResult.setDocId(mention.getDocId());
        linkResult.setPubdate(mention.getPubdate());
        linkResult.setEntityType(mention.getEntityType());
        linkResult.setEntityId(mention.getEntityId());
        linkResult.setEntityTitle(mention.getEntityTitle());
        linkResult.setMention(mention.getMention());
        linkResult.setStartIdx(mention.getStartIdx());
        linkResult.setEndIdx(mention.getEndIdx());

        linkResult.setBatchName(experiment.batchName);
        linkResult.setBatchStartTime(experiment.batchStartTime);
        linkResult.setGroupName(experiment.groupName);
        linkResult.setSamplerName(experiment.samplerName);
        linkResult.setStepName(experiment.stepName);
        linkResult.setFeatureDesc(experiment.featureDesc);
        linkResult.setExperimentName(experiment.name);
        linkResult.setClassificationModel(experiment.classificationModel);
        linkResult.setStartTime(experiment.startTime);

        linkResult.setProbability0(probability0);
        Entity candidateEntity0 = permutation0.get(mentionIdx);
        linkResult.setEntityId0(candidateEntity0.getId());
        linkResult.setEntityTitle0(candidateEntity0.getTitle());

        if(permutation1 !=null) {
            linkResult.setProbability1(probability1);
            Entity candidateEntity1 = permutation1.get(mentionIdx);
            linkResult.setEntityId1(candidateEntity1.getId());
            linkResult.setEntityTitle1(candidateEntity1.getTitle());
        }
        linkResult.setProbabilityDiff(probabilityDiff);
        linkResult.setCorrect(mentionList.get(mentionIdx).getEntityId() == permutation0.get(mentionIdx).getId() ? 1 : 0);
        linkResult.setHasCorrectCandidate(-1);

        return linkResult;
    }
}
