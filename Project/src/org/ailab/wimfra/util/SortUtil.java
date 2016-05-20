package org.ailab.wimfra.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-2-24
 * Time: 15:54:37
 * Desc:
 */
public class SortUtil {
    /**
     * 直接比较列表的元素
     * @return
     */
    public static List sort(Collection src, boolean asc) {
        List desList = new ArrayList(src.size());
        for(Object e : src) {
            boolean inserted = false;
            for(int i=0; i<desList.size(); i++) {
                int comp = ((Comparable)e).compareTo(desList.get(i));
                if(!asc){
                    comp = -comp;
                }
                if(comp<0) {
                    desList.add(i, e);
                    inserted = true;
                    break;
                } else if(comp ==0){
                    desList.add(i + 1, e);
                    inserted = true;
                    break;
                }
            }

            if(!inserted) {
                desList.add(e);
            }
        }

        return desList;
    }


    public static List sort(Collection srcCollection, Comparer comparer, boolean asc) {
        List desList = new ArrayList(srcCollection.size());
        for(Object srcElement : srcCollection) {
            boolean inserted = false;
            for(int i=0; i<desList.size(); i++) {
                int comp = comparer.compare(srcElement, desList.get(i));
                if(!asc){
                    comp = -comp;
                }
                if(comp<0) {
                    desList.add(i, srcElement);
                    inserted = true;
                    break;
                } else if(comp ==0){
                    desList.add(i + 1, srcElement);
                    inserted = true;
                    break;
                }
            }

            if(!inserted) {
                desList.add(srcElement);
            }
        }

        return desList;
    }
}
