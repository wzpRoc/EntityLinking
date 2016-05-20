package org.ailab.wimfra.frontend.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 21:03:13
 * Desc:
 */
public class DBRow {
    public String dateBegin;
    public String dateEnd;
    public String id;
    public String label;
    public List<String> toolTipsList;
    public List<Double> numList;

    public DBRow(int numSize) {
        numList = new ArrayList<Double>(numSize);
        toolTipsList = new ArrayList<String>(numSize);
    }

    public void addNum(double num){
        numList.add(num);
    }

    public void addNum(Double num){
        if(num == null){
            numList.add(0.0);
        } else {
            numList.add(num);
        }
    }

    public void addToolTips(String tooltips) {
        if(tooltips == null) {
            tooltips = " ";
        }
        toolTipsList.add(tooltips);
    }
}
