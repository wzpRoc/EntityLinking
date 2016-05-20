package org.ailab.wimfra.database;

/**
 * User: Lu Tingming
 * Date: 2010-10-24
 * Time: 13:22:03
 * Desc: 表示数据库中的一行
 */
public class Row {
    String[] colValues;

    public Row(int colNum) {
        this.colValues = new String[colNum];
    }
}
