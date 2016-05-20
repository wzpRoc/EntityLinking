package org.ailab.wimfra.frontend.graph;

import flexjson.JSONSerializer;
import org.ailab.wimfra.util.CollectionUtil;
import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 20:54:14
 * Desc:
 */
public class MultiLineGraphData {
    public boolean isMultiLine = true;
    public List<String> keyList;
    public LabelList labelList;
    public LabelList commentList;
    public MultiLineData multiLineData;
    public List<String> idList;
    public List<String> dateBeginList;
    public List<String> dateEndList;
    public List<String> tooltipsList;
    public String minX;
    public String maxX;
    public DBRowList dbRowList;

    public double minY;
    public double maxY;

    /**
     * 从行数据转换为多线条图数据
     *
     * @return
     */
    public static MultiLineGraphData fromSql(String sql, boolean isMultiLine, int numSize, Object... params) throws Exception {
        final DBRowList dbRowList = (new DBFetcher()).getDBRowList(sql, numSize, params);
        return fromDBRowList(dbRowList, isMultiLine);
    }


    /**
     * 从行数据转换为多线条图数据
     *
     * @return
     */
    public static MultiLineGraphData fromSql(String dbConfigName, String sql, boolean isMultiLine, int numSize, Object... params) throws Exception {
        final DBFetcher dbFetcher = new DBFetcher();
        dbFetcher.setDbConfigName(dbConfigName);
        final DBRowList dbRowList = dbFetcher.getDBRowList(sql, numSize, params);
        return fromDBRowList(dbRowList, isMultiLine);
    }


    /**
     * 从行数据转换为多线条图数据
     *
     * @return
     */
    public static MultiLineGraphData fromSql(String dbConfigName, String sql, int numSize, Object... params) throws Exception {
        final DBFetcher dbFetcher = new DBFetcher();
        dbFetcher.setDbConfigName(dbConfigName);
        final DBRowList dbRowList = dbFetcher.getDBRowList(sql, numSize, params);
        return fromDBRowList(dbRowList, true);
    }


    /**
     * 从行数据转换为多线条图数据
     *
     * @return
     */
    public static MultiLineGraphData fromSql(String sql, int numSize, Object... params) throws Exception {
        final DBRowList dbRowList = (new DBFetcher()).getDBRowList(sql, numSize, params);
        return fromDBRowList(dbRowList, true);
    }


    /**
     * 从行数据转换为多线条图数据
     *
     * @param dbRowList
     * @return
     */
    public static MultiLineGraphData fromDBRowList(DBRowList dbRowList, boolean isMultiLine) {
        MultiLineGraphData multiLineGraphData = new MultiLineGraphData();

        multiLineGraphData.dbRowList = dbRowList;
        multiLineGraphData.labelList = LabelList.fromDBRowList(dbRowList);
        multiLineGraphData.multiLineData = MultiLineData.fromDBRowList(dbRowList);
        multiLineGraphData.multiLineData.isMultiLine = multiLineGraphData.isMultiLine = isMultiLine;
        multiLineGraphData.idList = constructIdList(dbRowList);
        multiLineGraphData.tooltipsList = constructTooltipsListList(dbRowList);
        multiLineGraphData.dateBeginList = constructDateBeginList(dbRowList);
        multiLineGraphData.dateEndList = constructDateEndList(dbRowList);

        if (NumberUtil.approximatelyEqual(multiLineGraphData.multiLineData.minY, Double.MAX_VALUE)) {
            multiLineGraphData.minY = 0;
        } else {
            multiLineGraphData.minY = multiLineGraphData.multiLineData.minY;
        }
        if (NumberUtil.approximatelyEqual(multiLineGraphData.multiLineData.maxY, Double.MIN_VALUE)) {
            multiLineGraphData.maxY = 1;
        } else {
            multiLineGraphData.maxY = multiLineGraphData.multiLineData.maxY;
        }

        return multiLineGraphData;
    }


    public static List<String> constructTooltipsListList(DBRowList dbRowList) {
        List<String> tooltipsList = new ArrayList<String>();
        // 以线条为第一级
        // 以横轴（数据行）为第二级
        for (int i_line = 0; i_line < dbRowList.numSize; i_line++) {
            for (DBRow dbRow : dbRowList) {
                // 数据行往下排，取数据行中第i_x个tooltips
                if (dbRow.toolTipsList == null || dbRow.toolTipsList.size() == 0) {
                    break;
                }
                final String s = dbRow.toolTipsList.get(i_line);
                tooltipsList.add(s);
            }
        }

        return tooltipsList;
    }

    public static List<String> constructIdList(DBRowList dbRowList) {
        List<String> id = new ArrayList<String>();
        // 以横轴（数据行）为
        for (DBRow dbRow : dbRowList) {
            id.add(dbRow.id);
        }

        return id;
    }


    public static List<String> constructDateBeginList(DBRowList dbRowList) {
        List<String> tooltipsList = new ArrayList<String>();
        // 以横轴（数据行）为
        for (DBRow dbRow : dbRowList) {
            tooltipsList.add(dbRow.dateBegin);
        }

        return tooltipsList;
    }


    public static List<String> constructDateEndList(DBRowList dbRowList) {
        List<String> tooltipsList = new ArrayList<String>();
        // 以横轴（数据行）为
        for (DBRow dbRow : dbRowList) {
            tooltipsList.add(dbRow.dateEnd);
        }

        return tooltipsList;
    }


    public LabelList getLabelList() {
        return labelList;
    }

    public void setLabelList(LabelList labelList) {
        this.labelList = labelList;
    }

    public MultiLineData getMultiLineData() {
        return multiLineData;
    }

    public void setMultiLineData(MultiLineData multiLineData) {
        this.multiLineData = multiLineData;
    }

    public String getLabelsJs() {
        return labelList.toJs();
    }

    public String getLabelsJs_ge5() {
        if(multiLineData == null || multiLineData.size()==0) {
            return null;
        }
        LineData lineData = multiLineData.get(0);
        lineData.calcSumAndProportion();

        // 小于5%以后的不加入
        int i;
        for(i = 0; i<lineData.size(); i++) {
            double proportion = lineData.proportionList.get(i);
            if(proportion<0.05) {
                break;
            }
        }
        return labelList.toJs(i);
    }

    public String getTop5LabelsJs() {
        return labelList.toJs(5);
    }

    public String getTop4LabelsJs() {
        return labelList.toJs(4);
    }

    public String getLineDataJs() {
        return multiLineData.getJs();
    }

    public String getLineIdJs() {
        return multiLineData.toMultiLineIdJs();
    }

    public String getTooltipsJs() {
        if (tooltipsList.size() == 1) {
            return new JSONSerializer().exclude("*.class").serialize(tooltipsList.get(0));
        } else {
            return new JSONSerializer().exclude("*.class").serialize(tooltipsList);
        }
    }

    public String getMinX() {
        return minX;
    }

    public void setMinX(String minX) {
        this.minX = minX;
    }

    public String getMaxX() {
        return maxX;
    }

    public void setMaxX(String maxX) {
        this.maxX = maxX;
    }

    public DBRowList getDbRowList() {
        return dbRowList;
    }

    public void setDbRowList(DBRowList dbRowList) {
        this.dbRowList = dbRowList;
    }

    public void addKey(String key) {
        if (keyList == null) {
            keyList = new ArrayList<String>();
        }
        keyList.add(key);
    }


    public String getKeysJs() {
        return
                "['" +
                        StringUtil.collectionToString(keyList, "', '") +
                        "']";
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


    public String getBarDataJs() {
        StringBuffer sb = new StringBuffer();

        if (isMultiLine) {
            sb.append('[');
        }

        boolean needComma = false;
        for (DBRow dbRow : dbRowList) {
            if (needComma) {
                sb.append(',');
            }

            sb.append('[');
            for (int i = 0; i < dbRowList.numSize; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(dbRow.numList.get(i));
            }
            sb.append(']');

            if (!needComma) {
                needComma = true;
            }
        }

        if (isMultiLine) {
            sb.append(']');
        }

        return sb.toString();
    }


    public String getDateBeginListJs() {
        return new JSONSerializer().exclude("*.class").serialize(dateBeginList);
    }


    public String getDateEndListJs() {
        return new JSONSerializer().exclude("*.class").serialize(dateEndList);
    }


    public List<String> getColorList() {
        return ColorUtil.colorList;
    }

    public String getColorListJs() {
        return ColorUtil.colorListJs;
    }

    public List<String> getIdList() {
        return idList;
    }

    public String getLabelPercentListJs() {
        if(multiLineData == null || multiLineData.size()==0) {
            return null;
        }

        LineData lineData = multiLineData.get(0);
        lineData.calcSumAndProportion();
        List<String> percentList = lineData.percentList;
        List<String> labelPercentList = new ArrayList<String>();
        for(int i=0; i<labelList.size(); i++) {
            String label = labelList.get(i);
            String percent = percentList.get(i);
            labelPercentList.add(label+" "+percent);
        }

        return (new JSONSerializer()).exclude("class").serialize(labelPercentList);
    }


    public boolean isEmpty() {
        return CollectionUtil.isEmpty(getLabelList());
    }
}
