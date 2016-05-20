package org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature;

import org.ailab.entityLinking_old.cache.WikiInlinkCache;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.measure.JaccardDistance;
import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.Stopwatch;

import java.util.*;

/**
 * User: Lu Tingming
 * Date: 15-5-29
 * Time: 下午10:47
 * Desc: 两个实体的inlink的Jaccard距离
 */
public class InlinkJaccardDistanceEEFG extends EntityEntityFeatureGenerator {
    public static long generationCount = 0;
    public static long cacheHitCount = 0;
    public static long generationDuration = 0;

    public static Map<String, Double> ididToDistanceMap = new HashMap<String, Double>();

    @Override
    public double generate(Entity entity0, Entity entity1) {
        generationCount++;
        Stopwatch sw = new Stopwatch();

        String key = entity0.getId() < entity1.getId()
                ? entity0.getId() + " " + entity1.getId()
                : entity1.getId() + " " + entity0.getId();
        Double distanceObj = ididToDistanceMap.get(key);
        double distance;
        if (distanceObj == null) {
            Set<String> inlinksToEntity0 = WikiInlinkCache.getInlinks(entity0.getTitle());
            Set<String> inlinksToEntity1 = WikiInlinkCache.getInlinks(entity1.getTitle());
            distance = JaccardDistance.calculate(inlinksToEntity0, inlinksToEntity1);
            ididToDistanceMap.put(key, distance);
        } else {
            cacheHitCount++;
            distance = distanceObj;
        }

        generationDuration += sw.stopAndGetMilliSeconds();

        return distance;
    }

    public static void main(String[] args) {
        List<String[]> listOfEntityTitles = new ArrayList<String[]>();
        listOfEntityTitles.add(new String[]{"东南大学", "东南大学"});
        listOfEntityTitles.add(new String[]{"东南大学", "南京市"});
        listOfEntityTitles.add(new String[]{"东南大学", "南京大学"});
        listOfEntityTitles.add(new String[]{"东南大学", "东京大学"});
        listOfEntityTitles.add(new String[]{"东南大学", "北京市"});
        listOfEntityTitles.add(new String[]{"东南大学", "中华人民共和国"});
        listOfEntityTitles.add(new String[]{"东南大学", "鲁廷明"});

        listOfEntityTitles.add(new String[]{"东南大学", "南京市"});
        listOfEntityTitles.add(new String[]{"东北大学", "南京市"});
        listOfEntityTitles.add(new String[]{"东南大学", "沈阳市"});
        listOfEntityTitles.add(new String[]{"东北大学", "沈阳市"});

        List<Double> distanceList = new ArrayList<Double>(listOfEntityTitles.size());
        for (String[] entityTitles : listOfEntityTitles) {
            String t0 = entityTitles[0];
            String t1 = entityTitles[1];
            double d = new InlinkJaccardDistanceEEFG().generate(t0, t1);
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
        return "inlinkJD";
    }
}
