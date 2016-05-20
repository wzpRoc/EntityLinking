package org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature;

import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.ml.WordVector;
import org.ailab.nlp.tfidf.IDFDict;
import org.ailab.nlp.tfidf.IDFDictFactory;
import org.ailab.nlp.tfidf.TFIDFProcessor;
import org.ailab.nlp.tfidf.TFProcessor;
import org.ailab.wimfra.util.NumberUtil;

import java.util.*;

/**
 * User: Lu Tingming
 * Date: 15-5-29
 * Time: 下午10:47
 * Desc: 两个实体的inlink的Jaccard距离
 */
public class SimilarityByWikiAbstEEFG extends EntityEntityFeatureGenerator {
    public static boolean debug = false;
    public static long generationCount = 0;
    public static long cacheHitCount = 0;
    public static long generationDuration = 0;

    public static Map<String, Double> ididToDistanceMap = new HashMap<String, Double>();

    protected TFIDFProcessor tfidfProcessor;
    private static IDFDict idfDict;

    @Override
    public double generate(Entity entity0, Entity entity1) {
        if(tfidfProcessor == null) {
            initTFIDFProcessor();
        }

        String key = entity0.getId() < entity1.getId()
                ? entity0.getId() + " " + entity1.getId()
                : entity1.getId() + " " + entity0.getId();
        Double similarityObj = ididToDistanceMap.get(key);
        double similarity;
        if (similarityObj == null) {
            WordVector vectorByEntity0 = tfidfProcessor.getWordTFIDFVector(entity0.getAbst());
            WordVector vectorByEntity1 = tfidfProcessor.getWordTFIDFVector(entity1.getAbst());
            if(debug) {
                System.out.println("-------------------------------- 0");
                System.out.println(entity0.getTitle());
                System.out.println(entity0.getAbst());
                System.out.println(vectorByEntity0);
                System.out.println("-------------------------------- 1");
                System.out.println(entity1.getTitle());
                System.out.println(entity1.getAbst());
                System.out.println(vectorByEntity1);
            }
            similarity = calcSimilarity(vectorByEntity0, vectorByEntity1);
            ididToDistanceMap.put(key, similarity);
        } else {
            similarity = similarityObj;
        }

        return similarity;
    }

    protected void initTFIDFProcessor() {
        if(idfDict == null) {
            idfDict = IDFDictFactory.wikiIDFDict;
        }
        this.tfidfProcessor = new TFIDFProcessor(idfDict, TFProcessor.getInstance());
    }

    protected double calcSimilarity(WordVector v1, WordVector v2) {
        if(v1.size() == 0 || v2.size() == 0) {
            return 0;
        }
        if(debug) {
            System.out.println("-----------------------------------");
            System.out.println("v1*v2="+NumberUtil.format(multiply(v1, v2), 5, 5));
            System.out.println("v1*v1="+NumberUtil.format(multiply(v1, v1), 5, 5));
            System.out.println("v2*v2="+NumberUtil.format(multiply(v2, v2), 5, 5));
            System.out.println("simi ="+multiply(v1, v2) / (Math.sqrt(multiply(v1, v1)*multiply(v2, v2))));
        }
        return multiply(v1, v2) / (Math.sqrt(multiply(v1, v1)*multiply(v2, v2)));
    }

    protected double multiply(WordVector v1, WordVector v2) {
        double sum = 0;
        for(Map.Entry<String, Double> entry : v1.entrySet()) {
            String word = entry.getKey();
            double value1 = entry.getValue();
            Double value2 = v2.get(word);
            if(value2 == null) {
                // continue;
            } else {
                sum += value1*value2;
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String[]> listOfEntityTitles = new ArrayList<String[]>();
//        listOfEntityTitles.add(new String[]{"东南大学", "东南大学"});
//        listOfEntityTitles.add(new String[]{"东南大学", "南京市"});
//        listOfEntityTitles.add(new String[]{"东南大学", "南京大学"});
//        listOfEntityTitles.add(new String[]{"东南大学", "东京大学"});
//        listOfEntityTitles.add(new String[]{"东南大学", "北京市"});
//        listOfEntityTitles.add(new String[]{"东南大学", "中华人民共和国"});
//        listOfEntityTitles.add(new String[]{"东南大学", "鲁廷明"});

//        listOfEntityTitles.add(new String[]{"东南大学", "南京市"});
//        listOfEntityTitles.add(new String[]{"东北大学", "南京市"});
//        listOfEntityTitles.add(new String[]{"东南大学", "沈阳市"});
//        listOfEntityTitles.add(new String[]{"东北大学", "沈阳市"});

        listOfEntityTitles.add(new String[]{"郭金龙", "天主教北京总教区"});
        listOfEntityTitles.add(new String[]{"郭金龙", "北京市"});

        SimilarityByWikiAbstEEFG similarityByWikiAbstEEFG = new SimilarityByWikiAbstEEFG();
        similarityByWikiAbstEEFG.debug = true;

        List<Double> distanceList = new ArrayList<Double>(listOfEntityTitles.size());
        for (String[] entityTitles : listOfEntityTitles) {
            String t0 = entityTitles[0];
            String t1 = entityTitles[1];
            double d = similarityByWikiAbstEEFG.generate(t0, t1);
            distanceList.add(d);
        }

        for (int i = 0; i < listOfEntityTitles.size(); i++) {
            String[] entityTitles = listOfEntityTitles.get(i);
            String t0 = entityTitles[0];
            String t1 = entityTitles[1];
            double d = distanceList.get(i);
            System.out.println(t0 + "\t" + t1 + "\t" + NumberUtil.format(d, 5, 5));
        }
    }


    @Override
    public String getFeatureShortName() {
        return "Sim(e,e)";
    }
}
