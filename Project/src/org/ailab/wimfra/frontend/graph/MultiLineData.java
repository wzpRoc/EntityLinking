package org.ailab.wimfra.frontend.graph;

import flexjson.JSONSerializer;
import org.ailab.wimfra.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 20:52:13
 * Desc: 多线条数据
 */
public class MultiLineData extends ArrayList<LineData> {
    public List<LineId> multiLineId;
    public double minY;
    public double maxY;
    public boolean isMultiLine = true;

    public MultiLineData() {
    }

    public MultiLineData(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 从行数据转换为多线条数据
     *
     * @param dbRowList
     * @return
     */
    public static MultiLineData fromDBRowList(DBRowList dbRowList) {
        // 线条数 = 数据行的数字数量
        final int nrLine = dbRowList.numSize;
        // 每个线条中的数据 = 数据行数
        final int nrDataInLine = dbRowList.size();

        MultiLineData multiLineData = new MultiLineData(nrLine);
        multiLineData.multiLineId = new ArrayList<LineId>(nrLine);
        multiLineData.minY = Double.MAX_VALUE;
        multiLineData.maxY = Double.MIN_VALUE;

        // 转换
        for (int i_line = 0; i_line < nrLine; i_line++) {
            // 创建线条
            LineData lineData = new LineData(nrDataInLine);
            LineId lineId = new LineId(nrDataInLine);
            multiLineData.add(lineData);
            multiLineData.multiLineId.add(lineId);

            lineData.minY = Double.MAX_VALUE;
            lineData.maxY = Double.MIN_VALUE;

            // 填充线条中的数值
            for (int i_dataInLine = 0; i_dataInLine < nrDataInLine; i_dataInLine++) {
                final Double num = dbRowList.get(i_dataInLine).numList.get(i_line);
                if (lineData.minY > num) {
                    lineData.minY = num;
                }
                if (lineData.maxY < num) {
                    lineData.maxY = num;
                }
                lineData.add(num);
                lineId.add(dbRowList.get(i_dataInLine).id);
            }

            if (multiLineData.minY > lineData.minY) {
                multiLineData.minY = lineData.minY;
            }
            if (multiLineData.maxY < lineData.maxY) {
                multiLineData.maxY = lineData.maxY;
            }
        }

        return multiLineData;
    }


    /**
     * 转换成javascript
     *
     * @return
     */
    public String toJs() {
        StringBuffer sb = new StringBuffer();

        if (isMultiLine) {
            sb.append('[');
        }

        boolean needComma = false;
        for (LineData lineData : this) {
            if (needComma) {
                sb.append(',');
            }
            sb.append(lineData.toJs());
            if (!needComma) {
                needComma = true;
            }
        }

        if (isMultiLine) {
            sb.append(']');
        }

        return sb.toString();
    }


    public String getPercent0ListJs() {
        double sum0;
        List<Double> percent0List = new ArrayList<Double>();

        sum0 = 0;
        for(LineData lineData : this) {
            sum0 += lineData.get(0);
        }

        if(sum0 == 0) {
            return null;
        }

        for(LineData lineData : this) {
            double v = lineData.get(0) / sum0;
            String s = NumberUtil.format(v * 100, 2, 0);
            percent0List.add(v);
        }

        return (new JSONSerializer()).exclude("class").serialize(percent0List);
    }



    /**
     * 转换成javascript
     *
     * @return
     */
    public String toMultiLineIdJs() {
        StringBuffer sb = new StringBuffer();

        if (isMultiLine) {
            sb.append('[');
        }

        boolean needComma = false;
        for (LineId lineId : this.multiLineId) {
            if (needComma) {
                sb.append(',');
            }
            sb.append(lineId.toJs());
            if (!needComma) {
                needComma = true;
            }
        }

        if (isMultiLine) {
            sb.append(']');
        }

        return sb.toString();
    }


    public String getJs() {
        return toJs();
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
}
