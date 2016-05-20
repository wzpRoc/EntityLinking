package org.ailab.entityLinking_old.experiment;

import org.ailab.entityLinking_old.feature.DocFeatureVectorAssembler;
import org.ailab.entityLinking_old.feature.DocFeatureVectorAssemblerFactory;
import org.ailab.entityLinking_old.model.DocLinkResult;
import org.ailab.entityLinking_old.model.Tester;
import org.ailab.entityLinking_old.model.Trainer;
import org.ailab.entityLinking_old.wim.linkResult.LinkResult;
import org.ailab.entityLinking_old.wim.linkResult.LinkResultBL;
import org.ailab.tem.DBConfig;
import org.ailab.wimfra.database.DBUtilInstance;
import weka.classifiers.Classifier;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午9:55
 * Desc:
 */
public class ExperimentExecutor {
    protected Experiment experiment;

    public ExperimentExecutor(Experiment experiment) {
        this.experiment = experiment;
    }

    public void execute() {
        // 0. 准备工作
        TrainingCorpusContext trainingCorpusContext = new TrainingCorpusContext(experiment.trainingDocIdSet);
        DocFeatureVectorAssembler docFeatureVectorAssembler;
        docFeatureVectorAssembler = DocFeatureVectorAssemblerFactory.create(
                experiment.linkModelType,
                trainingCorpusContext,
                experiment.featureGenerators);

        // 1. 训练
        Classifier classifier = new Trainer(experiment.classificationModel, trainingCorpusContext, docFeatureVectorAssembler).train();

        // 2. 测试
        TestingCorpusContext testingCorpusContext = new TestingCorpusContext(experiment.testingDocIdSet);
        String featureVectorsLogPath = getTestFeatureVectorsLogPath();
        List<DocLinkResult> docLinkResultList = new Tester(featureVectorsLogPath, classifier, testingCorpusContext, docFeatureVectorAssembler).test();

        // 3. 保存测试结果
        saveResult(docLinkResultList);

        // 4. 统计测试结果
        printStat();
    }

    private String getTestFeatureVectorsLogPath() {
        return getFeatureVectorsLogPath(false);
    }

    private String getTrainingFeatureVectorsLogPath() {
        return getFeatureVectorsLogPath(true);
    }

    private String getFeatureVectorsLogPath(boolean isTraining) {
        return "f:/logs/" + experiment.batchStartTime.replace(':', '.') + " " + experiment.name + "."
                + (isTraining ? "train" : "wzp/newsML/test")
                + ".log";
    }

    private void printStat() {
        DBUtilInstance dbi = new DBUtilInstance(DBConfig.docDB.getName());
        List<String[]> stringsList = dbi.getStringsList("\n" +
                "SELECT t0.groupName, t0.startTime, t0.featureDesc, t0.experimentName, t0.classificationModel, t0.stepName,\n" +
                "\tt0.accuracy 'Random', \n" +
                "\tt1.accuracy 'MinMaxProbability', \tt1.accuracy-t0.accuracy d10,\n" +
                "\tt2.accuracy 'MinProbabilityDiff',  \tt2.accuracy-t0.accuracy d20,\n" +
                "\tt3.accuracy 'MinProbabilityDiffRatio', \tt3.accuracy-t0.accuracy d30\n" +
                "FROM eldoc.linkResultStat t0\n" +
                "LEFT JOIN eldoc.linkResultStat t1 ON (t0.batchName = t1.batchName AND t0.batchStartTime = t1.batchStartTime AND t0.featureDesc = t1.featureDesc AND t0.stepName = t1.stepName AND t0.classificationModel = t1.classificationModel AND t1.samplerName='MinMaxProbability')\n" +
                "LEFT JOIN eldoc.linkResultStat t2 ON (t0.batchName = t2.batchName AND t0.batchStartTime = t2.batchStartTime AND t0.featureDesc = t2.featureDesc AND t0.stepName = t2.stepName AND t0.classificationModel = t2.classificationModel AND t2.samplerName='MinProbabilityDiff')\n" +
                "LEFT JOIN eldoc.linkResultStat t3 ON (t0.batchName = t3.batchName AND t0.batchStartTime = t3.batchStartTime AND t0.featureDesc = t3.featureDesc AND t0.stepName = t3.stepName AND t0.classificationModel = t3.classificationModel AND t3.samplerName='MinProbabilityDiffRatio')\n" +
                "WHERE t0.samplerName='Random'\n" +
                "AND   t0.batchName IN('" + experiment.batchName + "')\n" +
                "AND   t0.batchStartTime = '" + experiment.batchStartTime + "'\n" +
                "ORDER BY 1,2,3,4,t0.startTime");
        for (String[] strings : stringsList) {
            for (String string : strings) {
                System.out.print(string + "\t");
            }
            System.out.println();
        }
    }

    public void saveResult(List<DocLinkResult> docLinkResultList) {
        List<LinkResult> linkResultList = toLinkResult(docLinkResultList);
        new LinkResultBL().insert(linkResultList);
    }

    public List<LinkResult> toLinkResult(List<DocLinkResult> docLinkResultList) {
        List<LinkResult> linkResultList = new ArrayList<LinkResult>();
        for (DocLinkResult docLinkResult : docLinkResultList) {
            linkResultList.addAll(docLinkResult.toLinkResult(experiment));
        }

        return linkResultList;
    }
}
