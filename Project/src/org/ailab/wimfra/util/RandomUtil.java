package org.ailab.wimfra.util;

import java.util.*;

/**
 * User: Lu Tingming
 * Time: 2010-11-11 20:53:54
 * Desc: 随机数生成工具
 */
public class RandomUtil {
    public static Random random;

    /**
     * 得到随机数，在基数的基础上可以浮动
     * @return
     */
    public static int getRandom(int base, double floatRate) {
        Random random = new Random(System.currentTimeMillis());
        int floatRange = (int) (base * floatRate);
        if(floatRange<1) floatRange=1;
        int rdm = random.nextInt(floatRange * 2);
        return base - floatRange + rdm;
    }

    /**
     * 得到 min - max 之间的一个随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomBetween(int min, int max) {
        Random random = new Random(System.currentTimeMillis());
        int rdm = random.nextInt(max - min + 1);
        return rdm + min;
    }

    /**
     * 打乱顺序
     * @param srcAs
     * @return
     */
    public static String[] disSort(String[] srcAs) {
        if (srcAs == null) return null;

        HashMap<Integer, Integer> randomMap = genRandomMap(srcAs.length, srcAs.length);

        String[] destAs = new String[srcAs.length];

        for (int i = 0; i < srcAs.length; i++) {
            int r = randomMap.get(i);
            destAs[i] = srcAs[r];
            //System.out.println(r+"\t->\t"+i);
        }

        return destAs;
    }

    /**
     * 从 [0, totalSize-1] 中随机选择出 selectSize 个数
     * 这些数存储在Map的键值中
     *
     * @param totalSize
     * @param selectSize
     * @return
     */
    public static HashMap<Integer, Integer> genRandomMap(int totalSize, int selectSize) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        // todo
        //Random random = new Random(Calendar.getInstance().getTimeInMillis());
        Random random = new Random(0);
        for (int i = 0; i < selectSize;) {
            int i_rdm = random.nextInt(totalSize);
            if (map.containsKey(i_rdm)) {

            } else {
                map.put(i_rdm, i);
                i++;
            }
        }
        return map;
    }

    /**
     * 从 [0, totalSize-1] 中随机选择出 selectSize 个数
     *
     * @param totalSize
     * @param selectSize
     * @return
     */
    public static ArrayList<Integer> randomSelect(int totalSize, int selectSize, boolean keepOrder) {
        ArrayList<Integer> list = new ArrayList<Integer>(selectSize);
        Set<Integer> set = new HashSet<Integer>(selectSize);
        // todo
        //Random random = new Random(Calendar.getInstance().getTimeInMillis());
        Random random = new Random(0);
        for (int i = 0; i < selectSize;) {
            int i_rdm = random.nextInt(totalSize);
            if (set.contains(i_rdm)) {

            } else {
                set.add(i_rdm);
                if (keepOrder) {
                    //todo
                    list.add(i_rdm);
                } else {
                    list.add(i_rdm);
                }
                i++;
            }
        }

        return list;
    }


    /**
     * 从 list 中随机选择出 selectSize 个元素，存入新的 selectionList，作为返回结果
     *
     * @param collection
     * @param selectionSize
     * @return
     */
    public static Set randomSelectFromCollection(Collection collection, int selectionSize) {
        int totalSize = collection.size();
        List<Integer> selectionIdxList = randomSelect(totalSize, selectionSize, false);
        Set<Integer> selectionIdxSet = new HashSet<Integer>(selectionIdxList);

        Set<Object> selectionSet = new HashSet<Object>(selectionSize);
        int idx = 0;
        for(Object element : collection) {
            if(selectionIdxSet.contains(idx)) {
                selectionSet.add(element);
                selectionIdxSet.remove(idx);
            }
            idx++;
        }

        return selectionSet;
    }


    /**
     * 从 list 中随机选择出 selectSize 个元素，存入新的 selectionList，作为返回结果
     *
     * @param list
     * @param selectionSize
     * @return
     */
    public static List randomSelect(List list, int selectionSize) {
        int totalSize = list.size();
        ArrayList<Integer> selectionIdxList = randomSelect(totalSize, selectionSize, false);

        ArrayList selectionList = new ArrayList(selectionSize);
        for (Integer i : selectionIdxList) {
            selectionList.add(list.get(i));
        }

        return selectionList;
    }


    /**
     * 按照概率，从列表中选择一个
     * @param ap
     * @return
     */
    public static int randomSelectByProbability(double[] ap) {
        double randomDouble = getRandomDouble();
        double sum = 0;
        for(int i=0; i<ap.length; i++){
            sum += ap[i];
            if(randomDouble<sum){
                return i;
            }
        }

        System.err.println("error occurred in randomSelect(double[] ap)");

        return -1;
    }


    /**
     * 按照概率，从列表中选择一个
     * @param ap
     * @return
     */
    public static int randomSelectByProbability(List<Double> ap) {
        double randomDouble = getRandomDouble();
        double sum = 0;
        for(int i=0; i<ap.size(); i++){
            sum += ap.get(i);
            if(randomDouble<sum){
                return i;
            }
        }

        System.err.println("error occurred in randomSelect(double[] ap)");

        return -1;
    }


    /**
     * 返回一个随机double，范围在0-1
     * @return
     */
    public static double getRandomDouble() {
        if(random == null) {
            // random = new Random(System.currentTimeMillis());
            random = new Random(0);
        }
        return random.nextDouble();
    }
}
