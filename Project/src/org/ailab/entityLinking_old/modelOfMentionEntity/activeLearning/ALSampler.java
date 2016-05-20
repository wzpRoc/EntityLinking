package org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning;

import org.ailab.entityLinking_old.feature.DocFeatureVectorAssembler;
import org.ailab.entityLinking_old.feature.DocFeatureVectorAssemblerFactory;
import org.ailab.entityLinking_old.model.LinkModelType;
import org.ailab.entityLinking_old.experiment.TestingCorpusContext;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.model.DocLinkResult;
import org.ailab.entityLinking_old.model.Tester;
import org.ailab.entityLinking_old.model.Trainer;
import org.ailab.wimfra.util.Comparer;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.SortUtil;
import weka.classifiers.Classifier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-19
 * Time: 上午1:42
 * Desc: 根据主动学习采用
 */
public abstract class ALSampler extends AbstractSampler {
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 此段为重头戏
     * @return
     */
    @Override
    public Set<Integer> sample(LinkModelType linkModelType, String classificationModel, Object featureGenerators, Set<Integer> sampledTrainingDocIdSet, Set<Integer> unsampledTrainingDocIdSet, int sampleSize, String featureVectorsLogPath) {
        // 1. 使用已有的训练数据，重新训练模型
        // 1.1 合并已有的训练数据
        logger.info("availableTrainingData " + sampledTrainingDocIdSet.size());
        // 1.2 创建训练数据上下文
        TrainingCorpusContext trainingCorpusContext = new TrainingCorpusContext(sampledTrainingDocIdSet);
        // 1.3 创建特征向量生成器
        DocFeatureVectorAssembler featureVectorAssembler = DocFeatureVectorAssemblerFactory.create(
                linkModelType, trainingCorpusContext, featureGenerators
        );
        // 1.4 训练
        Classifier classifier = new Trainer(classificationModel, trainingCorpusContext, featureVectorAssembler).train();

        // 2. 对可以标注但尚未标注的数据，进行分类，并记录结果以被分析抽样
        TestingCorpusContext testingCorpusContext = new TestingCorpusContext(unsampledTrainingDocIdSet);
        List<DocLinkResult> docLinkResultList = new Tester(featureVectorsLogPath, classifier, testingCorpusContext, featureVectorAssembler).test();

        // 3. 分析结果
        return sample(docLinkResultList, sampleSize);
    }


    public Set<Integer> sample(List<DocLinkResult> docLinkResultList, int sampleSize) {
        @SuppressWarnings("unchecked")
        List<DocLinkResult> rankedDocLinkResultList = SortUtil.sort(docLinkResultList, getDocLinkResultComparer(), false);

        Set<Integer> sampledDocIdSet = new HashSet<Integer>(sampleSize);
        for(int i=0; i<sampleSize; i++) {
            DocLinkResult docLinkResult = rankedDocLinkResultList.get(i);
            sampledDocIdSet.add(docLinkResult.doc.getId());
        }

        return sampledDocIdSet;
    }

    protected abstract Comparer getDocLinkResultComparer();
}
