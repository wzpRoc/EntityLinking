package org.ailab.tool;

import org.ailab.nlp.TSChineseConverter;
import org.ailab.tem.DBConfig;
import org.ailab.wimfra.database.DBUtilInstance;
import org.ailab.wimfra.util.KeyValue;

import java.sql.SQLException;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 下午4:25
 * Desc: 把entity表的title转换为繁体，存在另一个字段中
 */
public class EntityNameToTC {
    public static void main(String[] args) throws Exception {
        DBUtilInstance dbi = new DBUtilInstance(DBConfig.defaultDB.getName());
        List<KeyValue> idToTitleList = dbi.getKeyValueList("entity", "id", "title");

        for(KeyValue idTitle : idToTitleList) {
            String title = idTitle.getValue();
            String titleInTC = TSChineseConverter.toTraditionalChinese(title);
            idTitle.setValue(titleInTC);
        }

        dbi.updateByKeyValueList("entity", "id", "titleInTC", idToTitleList);
    }
}
