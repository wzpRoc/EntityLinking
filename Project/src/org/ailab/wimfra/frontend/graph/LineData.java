package org.ailab.wimfra.frontend.graph;

import org.ailab.wimfra.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 20:49:52
 * Desc:
 */
public class LineData extends ArrayList<Double> {
    public double minY;
    public double maxY;

    public List<Double> proportionList;
    public List<String> percentList;
    public double sum;

    public LineData(int initialCapacity) {
        super(initialCapacity);
    }


    /**
     * 转换成javascript
     * @return
     */
    public String toJs() {
        StringBuffer sb = new StringBuffer();

        sb.append('[');

        boolean needComma = false;
        for (Double num : this) {
            if (needComma) {
                sb.append(',');
            }
            sb.append(num);
            if (!needComma) {
                needComma = true;
            }
        }

        sb.append(']');

        return sb.toString();
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }


    public void calcSumAndProportion() {
        sum = 0;
        
        for(double d: this) {
            sum += d;
        }

        proportionList = new ArrayList<Double>(this.size());
        percentList = new ArrayList<String>(this.size());
        for(double d : this) {
            double proportion = d / sum;
            proportionList.add(proportion);
            percentList.add(NumberUtil.format(proportion * 100, 2, 0)+"%");
        }
    }
}
