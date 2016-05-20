package org.ailab.entityLinking_old.modelOfDocGlobal.feature;

import org.ailab.entityLinking_old.modelOfDocGlobal.CandidateEntityPermutation;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature.IDocFeatureGenerator;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-30
 * Time: 上午1:12
 * Desc: 文档级别的特征向量生成器
 */
public class DocGlobalFeatureVectorGenerator {
    private static final int WEIGH = 1;
    protected List<IDocFeatureGenerator> featureGeneratorList;
    protected int featureCount;
    // 特征生成是与训练数据相关的
    protected TrainingCorpusContext trainingCorpusContext;


    public DocGlobalFeatureVectorGenerator(TrainingCorpusContext trainingCorpusContext, IDocFeatureGenerator... featureGenerators) {
        this.trainingCorpusContext = trainingCorpusContext;
        addFeatureGenerators(featureGenerators);
    }


    private void addFeatureGenerators(IDocFeatureGenerator... featureGenerators) {
        featureGeneratorList = new ArrayList<IDocFeatureGenerator>(featureGenerators.length);

        for (IDocFeatureGenerator featureGenerator : featureGenerators) {
            featureGeneratorList.add(featureGenerator);
            featureGenerator.setTrainingCorpusContext(trainingCorpusContext);
        }

        featureCount = featureGeneratorList.size();
    }


    public Instance generate(int docIdx, Doc doc, List<DocEntity> mentionList, CandidateEntityPermutation candidateEntityPermutation) {
        double[] values = new double[featureCount + 1];
        values[0] = candidateEntityPermutation.label.getValue();

        // 对特征循环，生成特征向量
        for (int i = 0; i < featureGeneratorList.size(); i++) {
            IDocFeatureGenerator fg = featureGeneratorList.get(i);
            double featureValue = fg.generate(doc, mentionList, candidateEntityPermutation);
            values[i + 1] = featureValue;
        }

        Instance instance = new Instance(WEIGH, values);

        return instance;
    }


    public String[] getFeatureShortNames() {
        String[] featureNames = new String[featureGeneratorList.size()];
        for (int i = 0; i < featureGeneratorList.size(); i++) {
            featureNames[i] = featureGeneratorList.get(i).getFeatureShortName();
        }

        return featureNames;
    }
}
