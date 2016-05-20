package org.ailab.entityLinking_old.modelOfMentionEntity;

import org.ailab.entityLinking_old.feature.DocFeatureVectorAssembler;
import org.ailab.entityLinking_old.model.DocLinkResultByMention;
import org.ailab.entityLinking_old.modelOfMentionEntity.feature.MentionEntityFeatureVectorGenerator;
import org.ailab.entityLinking_old.util.DocInfo;
import org.ailab.entityLinking_old.cache.EntityCache;
import org.ailab.entityLinking_old.candidateEntityGenerate.CandidateEntityGenerator;
import org.ailab.entityLinking_old.model.DocLinkResult;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.StringUtil;
import weka.classifiers.Classifier;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-30
 * Time: 上午1:12
 * Desc: 为“提及-实体 模型”组装特征向量，与具体的特征无关
 */
public class MentionEntityFeatureVectorAssembler extends DocFeatureVectorAssembler {
    protected MentionEntityFeatureVectorGenerator featureVectorGenerator;

    public MentionEntityFeatureVectorAssembler(CandidateEntityGenerator candidateEntityGenerator, MentionEntityFeatureVectorGenerator featureVectorGenerator) {
        super(candidateEntityGenerator);
        this.featureVectorGenerator = featureVectorGenerator;
        this.schemaDataset = createDataSet(1);
    }

    @Override
    protected List<Instance> getFeatureVectorsFromDoc(int docIdx, DocInfo docInfo, boolean isTraining) {
        Doc doc = docInfo.doc;
        List<DocEntity> docEntityList = docInfo.docEntityList;
        List<Instance> instanceListOfDoc = new ArrayList<Instance>();
        // 对所有mention循环
        for (DocEntity docEntity : docEntityList) {
            Integer correctEntityId;
            List<Entity> candidateEntityList = candidateEntityGenerator.generate(docEntity.getMention());
            if (isTraining) {
                correctEntityId = docEntity.getEntityId();
            } else {
                correctEntityId = null;
            }
            List<Instance> instanceListForMention = featureVectorGenerator.generate(doc, docEntity, correctEntityId, candidateEntityList);
            instanceListOfDoc.addAll(instanceListForMention);
        }
        return instanceListOfDoc;
    }

    @Override
    protected String[] getFeatureNames() {
        return featureVectorGenerator.getFeatureNames();
    }


    public DocLinkResult testDoc(int docIdx, Classifier classifier, DocInfo docInfo, StringBuilder featureVectorLogSB) throws Exception {
        Doc doc = docInfo.doc;
        List<DocEntity> docEntityList = docInfo.docEntityList;
        DocLinkResultByMention docLinkResult = new DocLinkResultByMention(doc);

        // 对所有mention循环
        for (DocEntity docEntity : docEntityList) {
            List<Entity> candidateEntityList = candidateEntityGenerator.generate(docEntity.getMention());

            MentionLinkResult mentionLinkResult = new MentionLinkResult(docEntity, candidateEntityList.size());
            docLinkResult.mentionLinkResultList.add(mentionLinkResult);

            for (int i = 0; i < candidateEntityList.size(); i++) {
                Entity candidateEntity = candidateEntityList.get(i);
                Instance instance = featureVectorGenerator.generate(doc, docEntity, null, candidateEntity);
                instance.setDataset(schemaDataset);
                double[] probabilities = classifier.distributionForInstance(instance);
                // 取分类为1的概率
                // double probability0 = probabilities[0];
                double probability = probabilities[1];
                MentionEntityClassificationResult mentionEntityClassificationResult = new MentionEntityClassificationResult(candidateEntity, probability);
                mentionLinkResult.addClassificationResult(mentionEntityClassificationResult);
            }

            mentionLinkResult.processAfterClassification();
        }

        // todo: sb 保存排序后的结果
        // printClassification(docEntity, candidateEntity, instance, probability);

        return docLinkResult;
    }

    public void printMention(DocEntity docEntity) {
        System.out.println();
        System.out.println(docEntity.toString());
        System.out.println("----------------------------------");
    }

    public void printClassification(DocEntity docEntity, Entity candidateEntity, Instance instance, double p) {
        String equal = docEntity.getEntityId() == candidateEntity.getId() ? "==" : "<>";
        System.out.print(
                docEntity.getMention() + " -> " + EntityCache.getById(docEntity.getEntityId())
                        + "\t" + equal
                        + "\t" + StringUtil.rightFill(candidateEntity.toString(), 25, ' ')
                        + "\t" + NumberUtil.format(p, 5, 5)
                        + "\t" + instance.value(0));
        for (int i = 1; i < instance.numAttributes(); i++) {
            System.out.print("\t" + NumberUtil.format(instance.value(i), 10, 10));
        }
        System.out.println();
    }


}
