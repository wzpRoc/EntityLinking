package org.ailab.wimfra.frontend.graph;

import java.util.ArrayList;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 21:44:14
 * Desc:
 */
public class LabelList extends ArrayList<String> {
    public double eliminateNum = 0;

    public LabelList(int initialCapacity) {
        super(initialCapacity);
    }


    /**
     * 从行数据获得标签列表
     *
     * @param dbRowList
     * @return
     */
    public static LabelList fromDBRowList(DBRowList dbRowList) {
        // 标签数 = 数据行数
        final int nrLabel = dbRowList.size();

        LabelList labelList = new LabelList(nrLabel);
        // 转换
        for (int i_label = 0; i_label < nrLabel; i_label++) {
            labelList.add(dbRowList.get(i_label).label);
        }

        return labelList;
    }


    /**
     * 转换成javascript
     *
     * @return
     */
    public String toJs() {
        return toJs(Integer.MAX_VALUE);
    }

    public String toJs(int maxLabels) {
        StringBuffer sb = new StringBuffer();

        sb.append('[');

        for (int i=0; i<this.size() && i<maxLabels; i++) {
            String label = this.get(i);
            if (i>0) {
                sb.append(',');
            }
            sb.append('\'');
            if(eliminateNum==0 || Math.round(eliminateNum)==0 || i%Math.round(eliminateNum) == 0){
                sb.append(label);
            } else {
                sb.append("");
            }
            sb.append('\'');
        }

        sb.append(']');

        return sb.toString();
    }

    public String getJs(){
        return toJs();
    }
}
