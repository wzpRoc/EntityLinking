package org.ailab.wimfra.util;

import org.junit.Test;

import java.util.*;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 21:18:06
 * Desc:
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean notEmpty(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    public static List<String> stringToList(String s, String spliter) {
        String[] as = s.split(spliter);
        return Arrays.asList(as);
    }

    public static List newList(Object... params) {
        if (params == null) {
            return null;
        }

        List list = new ArrayList(params.length);
        for (Object obj : params) {
            list.add(obj);
        }

        return list;
    }

    public static void print(Collection collection) {
        if (collection == null) {
            System.out.println("null");
            return;
        }
        int i = 0;
        for (Object obj : collection) {
            System.out.println(i + "\t" + obj);
            i++;
        }
    }


    public static void addNew(Collection cToBeAdded, Collection cToAdd) {
        if (cToAdd == null) {
            return;
        }
        for (Object toAdd : cToAdd) {
            if (cToBeAdded.contains(toAdd)) {
                // do nothing
            } else {
                cToBeAdded.add(toAdd);
            }
        }
    }


    public static void deduplicate(List list) {
        Set set = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            Object ele = list.get(i);
            if (set.contains(ele)) {
                list.remove(i);
                i--;
            } else {
                set.add(ele);
            }
        }
    }


    @Test
    public void deduplicate_test() {
        List list = newList("a", "a", "b");
        print(list);

        System.out.println("--------------------------");

        deduplicate(list);
        print(list);
    }


    @SuppressWarnings("unchecked")
    public static List merge(List list0, List list1) {
        if (list0 == null) return list1;
        if (list1 == null) return list0;

        List resultList = new ArrayList();

        for (Object obj : list0) {
            if (resultList.contains(obj)) {
                // skip
            } else {
                resultList.add(obj);
            }
        }

        for (Object obj : list1) {
            if (resultList.contains(obj)) {
                // skip
            } else {
                resultList.add(obj);
            }
        }

        return resultList;
    }

    public static int getIntersectionSize(Set set0, Set set1) {
        if (set0 == null || set1 == null || set0.size() == 0 || set1.size() == 0) return 0;

        int count = 0;
        for (Object e0 : set0) {
            if (set1.contains(e0)) {
                //noinspection unchecked
                count++;
            }
        }

        return count;
    }

    public static int getUnionSize(Set set0, Set set1) {
        Set union = unite(set0, set1);
        return union == null?0:union.size();
    }

    public static Set intersect(Set set0, Set set1) {
        if (set0 == null || set1 == null || set0.size() == 0 || set1.size() == 0) return null;

        Set result = new HashSet();
        for (Object e0 : set0) {
            if (set1.contains(e0)) {
                //noinspection unchecked
                result.add(e0);
            }
        }

        return result;
    }

    public static Set unite(Set set0, Set set1) {
        if (set0 == null || set0.size() == 0) {
            return set1;
        }
        if (set1 == null || set1.size() == 0) {
            return set0;
        }

        Set result = new HashSet(set0);
        result.addAll(set1);

        return result;
    }
}
