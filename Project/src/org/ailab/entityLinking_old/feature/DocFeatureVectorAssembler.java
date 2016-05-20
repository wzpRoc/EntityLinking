package org.ailab.entityLinking_old.feature;

import org.ailab.entityLinking_old.util.DocInfo;
import org.ailab.entityLinking_old.candidateEntityGenerate.CandidateEntityGenerator;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.model.DocLinkResult;
import org.apache.log4j.Logger;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 上午12:53
 * Desc: 以文档为单位，组装特征向量
 */
public abstract class DocFeatureVectorAssembler {
    protected Logger logger = Logger.getLogger(this.getClass());
    // 生成特征需要用到训练数据
    protected TrainingCorpusContext trainingCorpusContext;
    protected CandidateEntityGenerator candidateEntityGenerator;
    // 测试时创建数据集，用于指示schema
    protected Instances schemaDataset;

    public DocFeatureVectorAssembler(CandidateEntityGenerator candidateEntityGenerator) {
        this.candidateEntityGenerator = candidateEntityGenerator;
    }

    public Instances loadInstancesForTraining(List<DocInfo> docInfoList) {
        // 获得值列表
        List<Instance> instanceList = new ArrayList<Instance>();
        for (int i=0; i<docInfoList.size(); i++) {
            DocInfo docInfo = docInfoList.get(i);
            logger.debug("assembling feature vector for document "+(i+1)+"/"+docInfoList.size()+"\t"+docInfo.doc.getTitle());
            List<Instance> instanceListOfDoc = getFeatureVectorsFromDoc(i, docInfo, true);
            instanceList.addAll(instanceListOfDoc);
        }

        Instances instances = createDataSet(instanceList.size());

        for (Instance instance : instanceList) {
            instances.add(instance);
        }

        logger.info(instances.numInstances() + " instances loaded.");

        return instances;
    }


    /**
     * 根据输入的文档，返回特征向量列表
     */
    protected abstract List<Instance> getFeatureVectorsFromDoc(int docIdx, DocInfo docInfo, boolean isTraining);

    public Instances createDataSet(int capacity) {
        // 创建分类属性
        FastVector classValues = new FastVector(2);
        classValues.addElement("0");
        classValues.addElement("1");
        Attribute classAttribute = new Attribute("class", classValues);

        // 创建属性列表
        FastVector attributes = new FastVector();
        attributes.addElement(classAttribute);

        String[] featureNames = getFeatureNames();
        for (String featureName : featureNames) {
            attributes.addElement(new Attribute(featureName));
        }

        // 创建weka对象
        Instances instances = new Instances("entityLinking", attributes, capacity);
        instances.setClassIndex(0);

        return instances;
    }

    protected abstract String[] getFeatureNames();

    /**
     * 测试一篇文档时，并不能直接把文档交给分类器，需要转换为特征向量，交给分类器，并将分类结果转换为我们需要的结果
     * @param classifier
     * @param docInfo
     * @return
     */
    public abstract DocLinkResult testDoc(int docIdx, Classifier classifier, DocInfo docInfo, StringBuilder featureVectorLogSB) throws Exception;

}
