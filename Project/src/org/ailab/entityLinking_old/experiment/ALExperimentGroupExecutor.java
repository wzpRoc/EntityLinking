package org.ailab.entityLinking_old.experiment;

import org.ailab.entityLinking_old.model.LinkModelType;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature.IDocFeatureGenerator;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature.MentionEntityPairAvgScoreDFG;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature.PermutationAvgScoreDFG;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature.CategoryJaccardDistanceEEFG;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature.InlinkJaccardDistanceEEFG;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature.SimilarityByWikiAbstEEFG;
import org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning.*;
import org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature.*;
import org.ailab.tem.DBConfig;
import org.ailab.wimfra.database.DBUtilInstance;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.Stopwatch;
import org.ailab.wimfra.util.StringUtil;
import org.ailab.wimfra.util.time.TimeUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午9:55
 * Desc: 执行一组实验
 */
public class ALExperimentGroupExecutor extends ExperimentGroupExecutor {
    static Logger logger = Logger.getLogger(ALExperimentGroupExecutor.class);

    protected final int initialTrainingDocSize;
    protected final int stepDocSize;
    protected final int steps;
    protected final int testingDocSize;
    protected DBUtilInstance dbi = new DBUtilInstance(DBConfig.docDB.getName());
    protected ALExperimentGroup experimentGroup;
    protected Set<Integer> initialTrainingDocIdSet;
    protected int allSampleTrainingDocSize;
    // 已抽样的文章ID集合
    protected Set<Integer> sampledTrainingDocIdSet;
    // 剩余可抽样的文章ID集合
    protected Set<Integer> unsampledTrainingDocIdSet;
    // 用于测试的文章ID集合
    protected Set<Integer> testingDocIdSet;

    protected String annoStates = "2,9";
    protected String docFilter = "1=1";

    public ALExperimentGroupExecutor(ALExperimentGroup experimentGroup) {
        super(experimentGroup);
        this.experimentGroup = experimentGroup;

        docFilter = experimentGroup.docFilter;
        initialTrainingDocSize = experimentGroup.initialTrainingDocSize;
        stepDocSize = experimentGroup.stepDocSize;
        steps = experimentGroup.steps;
        allSampleTrainingDocSize = stepDocSize * steps;
        testingDocSize = experimentGroup.testingDocSize;

        loadInitialTrainingDocIds();
        sampledTrainingDocIdSet = new HashSet<Integer>(initialTrainingDocIdSet);
        loadLeftSampleTrainingDocIdSet();
        loadTestingDocIdSet();
    }

    protected void loadInitialTrainingDocIds() {
        initialTrainingDocIdSet = dbi.getIntegerSet(
                "SELECT DISTINCT d.id\n" +
                        "FROM doc d, docEntity de\n" +
                        "WHERE d.annoState in (" + annoStates + ")\n" +
                        "AND " + docFilter+"\n" +
                        "AND d.id=de.docId\n" +
                        "ORDER BY id\n" +
                        "LIMIT " + experimentGroup.initialTrainingDocSize);
    }

    protected void loadLeftSampleTrainingDocIdSet() {
        unsampledTrainingDocIdSet = dbi.getIntegerSet(
                "SELECT DISTINCT d.id\n" +
                        "FROM doc d, docEntity de\n" +
                        "WHERE d.annoState in (" + annoStates + ")\n" +
                        "AND " + docFilter+"\n" +
                        "AND d.id=de.docId\n" +
                        "ORDER BY id\n" +
                        "LIMIT " + experimentGroup.initialTrainingDocSize + ", " + allSampleTrainingDocSize);
    }

    protected void loadTestingDocIdSet() {
        testingDocIdSet = dbi.getIntegerSet(
                "SELECT DISTINCT d.id\n" +
                        "FROM doc d, docEntity de\n" +
                        "WHERE d.annoState in (" + annoStates + ")\n" +
                        "AND " + docFilter+"\n" +
                        "AND d.id=de.docId\n" +
                        "ORDER BY id\n" +
                        "LIMIT " + (initialTrainingDocSize + allSampleTrainingDocSize) + ", " + testingDocSize);
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    protected void sampleTrainingDoc(int stepIdx) {
        // 第0步不需要抽样，直接使用初始训练数据
        if (stepIdx == 0) {
            return;
        }

        Set sampleSet = experimentGroup.sampler.sample(
                experimentGroup.linkModelType,
                experimentGroup.classificationModel,
                experimentGroup.featureGenerators,
                sampledTrainingDocIdSet, unsampledTrainingDocIdSet, stepDocSize,
                getFeatureVectorsLogPath(stepIdx));
        logger.info("stepIdx=" + stepIdx + ", ids=" + StringUtil.collectionToString(sampleSet, ","));

        sampledTrainingDocIdSet.addAll(sampleSet);
        unsampledTrainingDocIdSet.removeAll(sampleSet);
    }

    private String getFeatureVectorsLogPath(int stepIdx) {
        return "f:/logs/" +
                experimentGroup.batchStartTime.replace(':', '.')
                + " " + experimentGroup.linkModelType.name
                + " " + experimentGroup.sampler.getName()
                + " " + NumberUtil.format(stepIdx, 3, 0)
                + "sample"
                + ".log";
    }


    public void execute() {
        for (int stepIdx = 0; stepIdx <= steps; stepIdx++) {
            logger.info("sample " + (stepIdx + 1) + "/" + steps);
            // 抽样
            sampleTrainingDoc(stepIdx);

            // 创建实验对象
            String stepName = NumberUtil.format(stepIdx, 2)
                    + "." + NumberUtil.format(sampledTrainingDocIdSet.size(), 3, '0');
            String experimentName = experimentGroup.sampler.getName()
                    + ".step" + stepName;
            Experiment experiment = new Experiment(
                    experimentGroup.batchName,
                    experimentGroup.batchStartTime,
                    experimentGroup.name,
                    experimentGroup.sampler.getName(),
                    stepName,
                    TimeUtil.getYyyy_mm_dd_hh_mm_ss(),
                    experimentGroup.featureDesc,
                    experimentName,
                    experimentGroup.linkModelType,
                    experimentGroup.classificationModel,
                    experimentGroup.featureGenerators,
                    sampledTrainingDocIdSet,
                    testingDocIdSet);

            // 执行
            logger.info("execute experiment " + (stepIdx + 1) + "/" + steps + "'" + experiment.name + "'");
            new ExperimentExecutor(experiment).execute();
        }
    }


    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();

        LinkModelType[] linkModelTypes = {LinkModelType.docGlobal, LinkModelType.mentionEntity};
        String[] classificationModels = {"libsvm"};
//        String[] classificationModels = {"liblinear"};
        AbstractSampler[] samplers = {new RandomSampler(), new MinProbabilityDiffRatioSampler(), new MinMaxProbabilitySampler()};

        // loop 1: batch/linkModelType
        for(int batchIdx = 0; batchIdx < linkModelTypes.length; batchIdx++) {
            LinkModelType linkModelType = linkModelTypes[batchIdx];

           // 特征
            Object[] docFeatureGenerators = getDocFeatureGenerators(linkModelType);
            String featureDesc = StringUtil.arrayToString(docFeatureGenerators, ";");
            Batch batch = new Batch(
                    "global_al_multiNonNilAnno_cacheCE",
                    "nonNilCount>=2",
                    linkModelType,
                    samplers,
                    classificationModels,
                    docFeatureGenerators
            );
            batch.batchStartTime = TimeUtil.getYyyy_mm_dd_hh_mm_ss();

            // loop 2: classificationModel
            for (String classificationModel : batch.classificationModels) {
                // loop 3: sampler
                for (int i_sampler = 0; i_sampler < batch.samplerList.length; i_sampler++) {
                    AbstractSampler sampler = batch.samplerList[i_sampler];
                    String groupName = sampler.getName();
                    logger.info("executing group " + (i_sampler + 1) + "/" + batch.samplerList.length + " " + sampler.getName());
                    // loop 4: step
                    ALExperimentGroup group = new ALExperimentGroup(batch.batchName, batch.batchStartTime, groupName, null, linkModelType, classificationModel, featureDesc, docFeatureGenerators, sampler);
    //                group.setDocSize(600, 0, 0, 182);
    //                group.setDocSize(50, 50, 11, 182);
    //                group.setDocSize(100, 100, 5, 182);
                    group.setDocSize(batch.docFilter, 50, 50, 7, 104);    // multi-nonNilAnno 504
                    new ALExperimentGroupExecutor(group).execute();
                }
            }

            logger.info("InlinkJaccardDistanceEEFG.generationCount="+InlinkJaccardDistanceEEFG.generationCount);
            logger.info("InlinkJaccardDistanceEEFG.generationDuration="+InlinkJaccardDistanceEEFG.generationDuration);
            logger.info("InlinkJaccardDistanceEEFG.cacheHitCount="+InlinkJaccardDistanceEEFG.cacheHitCount);
            logger.info("InlinkJaccardDistanceEEFG.ididToDistanceMap.size()="+InlinkJaccardDistanceEEFG.ididToDistanceMap.size());

            logger.info("CategoryJaccardDistanceEEFG.generationCount="+CategoryJaccardDistanceEEFG.generationCount);
            logger.info("CategoryJaccardDistanceEEFG.generationDuration="+CategoryJaccardDistanceEEFG.generationDuration);

            System.out.println("total duration: " + stopwatch.stopAndGetFormattedTime());
        }
    }

    private static Object[] getDocFeatureGenerators(LinkModelType linkModelType) {
        Object[] docFeatureGenerators;
        if(linkModelType == LinkModelType.docGlobal) {
            docFeatureGenerators = new IDocFeatureGenerator[]{
                    // mention-entity features
                    new MentionEntityPairAvgScoreDFG(new EntityPopularityInTrainingCorpusMEFG()),
                    new MentionEntityPairAvgScoreDFG(new EntityPopularityInWikiMEFG()),
                    new MentionEntityPairAvgScoreDFG(new ProbOfEGivenMInTrainingCorpusMEFG()),
                    new MentionEntityPairAvgScoreDFG(new SimilarityByTrainingCorpusMEFG()),
                    new MentionEntityPairAvgScoreDFG(new SimilarityByWikiAbstMEFG()),
                    // global features
                    new PermutationAvgScoreDFG(new SimilarityByWikiAbstEEFG()),
                    new PermutationAvgScoreDFG(new InlinkJaccardDistanceEEFG()),
                    new PermutationAvgScoreDFG(new CategoryJaccardDistanceEEFG())
            };
        } else {
            docFeatureGenerators = new IMentionEntityFeatureGenerator[]{
                    // mention-entity features
                    new EntityPopularityInTrainingCorpusMEFG(),
                    new EntityPopularityInWikiMEFG(),
                    new ProbOfEGivenMInTrainingCorpusMEFG(),
                    new SimilarityByTrainingCorpusMEFG(),
                    new SimilarityByWikiAbstMEFG()
            };
        }
        return docFeatureGenerators;
    }
}
