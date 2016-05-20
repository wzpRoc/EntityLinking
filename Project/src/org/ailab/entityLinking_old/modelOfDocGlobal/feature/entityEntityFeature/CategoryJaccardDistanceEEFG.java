package org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature;

import org.ailab.entityLinking_old.cache.NLPCCCategoryCache;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.measure.JaccardDistance;
import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-29
 * Time: 下午10:47
 * Desc: 两个实体的category的Jaccard距离
 */
public class CategoryJaccardDistanceEEFG extends EntityEntityFeatureGenerator {
    public static long generationCount = 0;
    public static long generationDuration = 0;

    @Override
    public double generate(Entity entity0, Entity entity1) {
        generationCount++;
        Stopwatch sw = new Stopwatch();

        Set<String> categories0 = NLPCCCategoryCache.getCategories(entity0.getId());
        Set<String> categories1 = NLPCCCategoryCache.getCategories(entity1.getId());
        double distance = JaccardDistance.calculate(categories0, categories1);

        generationDuration+=sw.stopAndGetMilliSeconds();

        return distance;
    }


    @Override
    public String getFeatureShortName() {
        return "categoryJD";
    }


    public static void main(String[] args) {
        List<String[]> listOfEntityTitles = new ArrayList<String[]>();
        listOfEntityTitles.add(new String[]{"东南大学", "东南大学"});
        listOfEntityTitles.add(new String[]{"东南大学", "南京大学"});
        listOfEntityTitles.add(new String[]{"东南大学", "东京大学"});
        listOfEntityTitles.add(new String[]{"东南大学", "南京市"});
        listOfEntityTitles.add(new String[]{"东南大学", "北京市"});
        listOfEntityTitles.add(new String[]{"东南大学", "中华人民共和国"});
        listOfEntityTitles.add(new String[]{"东南大学", "鲁廷明"});

        listOfEntityTitles.add(new String[]{"东南大学", "南京市"});
        listOfEntityTitles.add(new String[]{"东北大学", "南京市"});
        listOfEntityTitles.add(new String[]{"东南大学", "沈阳市"});
        listOfEntityTitles.add(new String[]{"东北大学", "沈阳市"});

        List<Double> distanceList = new ArrayList<Double>(listOfEntityTitles.size());
        for(String[] entityTitles : listOfEntityTitles) {
            String t0 = entityTitles[0];
            String t1 = entityTitles[1];
            double d = new CategoryJaccardDistanceEEFG().generate(t0, t1);
            distanceList.add(d);
        }

        for(int i=0; i<listOfEntityTitles.size();i++) {
            String[] entityTitles = listOfEntityTitles.get(i);
            String t0 = entityTitles[0];
            String t1 = entityTitles[1];
            double d = distanceList.get(i);
            System.out.println(t0+"\t"+t1+"\t"+ NumberUtil.format(d, 5, 5));
        }
    }
}
