package org.ailab.entityLinking_old.model;

import org.ailab.entityLinking_old.feature.DocFeatureVectorAssembler;
import org.ailab.entityLinking_old.util.DocInfo;
import org.ailab.entityLinking_old.experiment.TestingCorpusContext;
import org.ailab.wimfra.util.FileUtil;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.Stopwatch;
import weka.classifiers.Classifier;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午10:01
 * Desc:
 */
public class Tester {
    Logger logger = Logger.getLogger(this.getClass());
    Classifier classifier;
    TestingCorpusContext corpusContext;
    DocFeatureVectorAssembler docFeatureVectorAssembler;
    protected String featureVectorsLogPath;

    public Tester(String featureVectorsLogPath, Classifier classifier, TestingCorpusContext corpusContext, DocFeatureVectorAssembler docFeatureVectorAssembler) {
        this.featureVectorsLogPath = featureVectorsLogPath;
        this.classifier = classifier;
        this.corpusContext = corpusContext;
        this.docFeatureVectorAssembler = docFeatureVectorAssembler;
    }

    public List<DocLinkResult> test() {
        logger.info("start to classify");
        Stopwatch st = new Stopwatch();
        try {
            List<DocInfo> docInfoList = corpusContext.getDocInfoList();
            List<DocLinkResult> docLinkResultList = new ArrayList<DocLinkResult>(docInfoList.size());
            StringBuilder featureVectorLogSB = new StringBuilder();

            // 对所有文档循环
            for (int i=0; i<docInfoList.size(); i++) {
                DocInfo docInfo = docInfoList.get(i);
                DocLinkResult docLinkResult = docFeatureVectorAssembler.testDoc(i, classifier, docInfo, featureVectorLogSB);
                docLinkResultList.add(docLinkResult);
            }

            logger.info("finished to classify, time cost=" + st.stopAndGetFormattedTime());

            saveFeatureVectors(featureVectorLogSB.toString());

            return docLinkResultList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void saveFeatureVectors(String featureVectorLog) {
        logger.debug("feature vectors are being saved to "+featureVectorsLogPath);
        FileUtil.write(featureVectorLog, featureVectorsLogPath);
    }
}
