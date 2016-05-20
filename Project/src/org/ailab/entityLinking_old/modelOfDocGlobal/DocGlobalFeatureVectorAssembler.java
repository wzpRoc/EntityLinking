package org.ailab.entityLinking_old.modelOfDocGlobal;

import org.ailab.entityLinking_old.feature.DocFeatureVectorAssembler;
import org.ailab.entityLinking_old.model.DocLinkResultByPermutation;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.DocGlobalFeatureVectorGenerator;
import org.ailab.entityLinking_old.util.DocInfo;
import org.ailab.entityLinking_old.candidateEntityGenerate.CandidateEntityGenerator;
import org.ailab.entityLinking_old.model.DocLinkResult;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.wimfra.util.NumberUtil;
import weka.classifiers.Classifier;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-30
 * Time: 上午1:12
 * Desc: 文档级别的特征向量组装器
 */
public class DocGlobalFeatureVectorAssembler extends DocFeatureVectorAssembler {
    DocGlobalFeatureVectorGenerator featureVectorGenerator;

    public DocGlobalFeatureVectorAssembler(CandidateEntityGenerator candidateEntityGenerator, DocGlobalFeatureVectorGenerator featureVectorGenerator) {
        super(candidateEntityGenerator);
        this.featureVectorGenerator = featureVectorGenerator;
        this.schemaDataset = createDataSet(1);
    }

    protected List<Instance> getFeatureVectorsFromDoc(int docIdx, DocInfo docInfo, boolean isTraining) {
        Doc doc = docInfo.doc;
        List<DocEntity> docEntityList = docInfo.docEntityList;
        // 对候选实体排列组合，并计算该排列是否是正确的排列
        CandidateEntityPermutator permutator = new CandidateEntityPermutator(docEntityList, candidateEntityGenerator);
        CandidateEntityPermutationList permutationList = permutator.permutate(true);

        List<Instance> instanceListOfDoc = new ArrayList<Instance>();
        // 对所有排列循环，每个排列生成一个特征向量
        for(int i=0; i<permutationList.size(); i++) {
            CandidateEntityPermutation permutation = permutationList.get(i);
            Instance instance = featureVectorGenerator.generate(docIdx, doc, docEntityList, permutation);
            instanceListOfDoc.add(instance);
        }

        return instanceListOfDoc;
    }

    @Override
    public DocLinkResult testDoc(int docIdx, Classifier classifier, DocInfo docInfo, StringBuilder featureVectorLogSB) throws Exception {
        Doc doc = docInfo.doc;
        List<DocEntity> docEntityList = docInfo.docEntityList;

        // 对候选实体排列组合，并计算该排列是否是正确的排列
        CandidateEntityPermutator permutator = new CandidateEntityPermutator(docEntityList, candidateEntityGenerator);
        CandidateEntityPermutationList permutationList = permutator.permutate(true);

        DocLinkResultByPermutation docLinkResult = new DocLinkResultByPermutation(doc, docEntityList);
        double[] probabilities = new double[permutationList.size()];
        ArrayList<Instance> instanceList = new ArrayList<Instance>(permutationList.size());
        // 对所有排列循环，每个排列生成一个特征向量
        for(int i_permutation=0; i_permutation<permutationList.size(); i_permutation++) {
            CandidateEntityPermutation permutation = permutationList.get(i_permutation);
            Instance instance = featureVectorGenerator.generate(docIdx, doc, docEntityList, permutation);
            instance.setDataset(schemaDataset);
            double[] distributionForInstance = classifier.distributionForInstance(instance);
            // 取分类为1的概率
            // double probability0 = probabilities[0];
            double probability = distributionForInstance[1];
            CandidateEntityPermutationClassificationResult classificationResult = new CandidateEntityPermutationClassificationResult(permutation, instance, probability);
            docLinkResult.addClassificationResult(classificationResult);
            // 保留结果，为后面打印日志用
            instanceList.add(instance);
            probabilities[i_permutation] = probability;
        }

        // 排序、汇总
        docLinkResult.processAfterClassification();

        // 打印日志
        // here the docLinkResult.permutationClassificationResultList has been sorted
        String featureVectorLog = genPermutationLog(docIdx, doc, docEntityList, docLinkResult.permutationClassificationResultList);
        logger.debug(featureVectorLog);
        featureVectorLogSB.append(featureVectorLog).append('\n');

        return docLinkResult;
    }


    public String genPermutationLog(int docIdx, Doc doc, List<DocEntity> mentionList, List<CandidateEntityPermutationClassificationResult> candidateEntityPermutationList) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<candidateEntityPermutationList.size(); i++) {
            CandidateEntityPermutationClassificationResult classificationResult = candidateEntityPermutationList.get(i);
            Instance instance = classificationResult.instance;
            double probability = classificationResult.probability;
            sb.append(genPermutationLog(doc, docIdx, mentionList, classificationResult.candidateEntityPermutation, i, instance, probability)).append('\n');
        }
        return sb.toString();
    }


    private String genPermutationLog(Doc doc, int docIdx, List<DocEntity> mentionList, CandidateEntityPermutation candidateEntityPermutation, int permutationRank, Instance instance, Double probability) {
        StringBuilder sb = new StringBuilder();
        sb.append(NumberUtil.format(docIdx, 3, ' '));
        sb.append(" ").append(doc.getId());

        // rank
        sb.append(" ").append(NumberUtil.format(permutationRank, 3, ' '));

        // gold label
        int goldLabel = candidateEntityPermutation.label.getValue();
        // wzp.newsML.test label
        int testLabel = (int) instance.value(0);
        char operator = goldLabel == testLabel
                ? '='
                : '<';
        sb.append(" ").append(goldLabel).append(operator).append(testLabel);

        for(int i=1; i<instance.numAttributes(); i++) {
            sb.append("\t").append(NumberUtil.format(instance.value(i), 4, 4));
        }

        sb.append("\t").append(
                probability == null
                ?"                 N/A"
                :NumberUtil.format(probability, 20, 20));

        sb.append("\t");
        for(int i=0; i<mentionList.size(); i++) {
            if(i>0) {
                sb.append(" ");
            }
            sb.append(mentionList.get(i).getMention()).append("->").append(candidateEntityPermutation.get(i).getTitle());
        }

        return sb.toString();
    }


    @Override
    protected String[] getFeatureNames() {
        return featureVectorGenerator.getFeatureShortNames();
    }
}
