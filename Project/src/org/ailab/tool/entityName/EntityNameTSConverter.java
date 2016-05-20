package org.ailab.tool.entityName;

import org.ailab.entityLinking_old.TSTag;
import org.ailab.entityLinking_old.wim.entityName.EntityName;
import org.ailab.entityLinking_old.wim.entityName.EntityNameBL;
import org.ailab.nlp.TSChineseConverter;
import org.ailab.wimfra.util.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ailab_pc_1
 * Date: 15-5-13
 * Time: 上午12:39
 * Desc: 繁体转换为简体
 */
public class EntityNameTSConverter {
    private List<EntityName> originalEntityNameList;
    private List<EntityName> convertedEntityNameList;

    /**
     * 载入原有的名字列表
     * @throws SQLException
     */
    public void loadOriginalEntityNames() throws SQLException {
        EntityNameBL entityNameBL = new EntityNameBL();
        entityNameBL.setTableName("entityname_20150518_1714");
        //noinspection unchecked
        originalEntityNameList = entityNameBL.getList();
    }

    public void process() {
        convertedEntityNameList = new ArrayList<EntityName>(originalEntityNameList.size());
        for (EntityName originalEntityNameObj : originalEntityNameList) {
            String originalEntityName = originalEntityNameObj.getEntityName();
            if (originalEntityName == null) {
                continue;
            }
            originalEntityName = originalEntityName.trim();
            if (StringUtil.isEmpty(originalEntityName)) {
                continue;
            }

            convertedEntityNameList.add(originalEntityNameObj);
            String simplifiedEntityName = TSChineseConverter.toSimplifiedChinese(originalEntityName);
            String traditionalEntityName = TSChineseConverter.toTraditionalChinese(originalEntityName);
            if (originalEntityName.equals(simplifiedEntityName)) {
                // 原来的值与简体相同，直接使用
                if (originalEntityName.equals(traditionalEntityName)) {
                    originalEntityNameObj.setTsTag(TSTag.UNKNOWN.getValue());
                } else {
                    originalEntityNameObj.setTsTag(TSTag.SIMPLIFIED.getValue());
                }
            } else {
                // 原来的值与简体不相同，认为是繁体；需要增加一条，转换为简体
                originalEntityNameObj.setTsTag(TSTag.TRADITIONAL.getValue());
                EntityName convertedEntityNameObj = originalEntityNameObj.clone();
                convertedEntityNameObj.setEntityName(simplifiedEntityName);
                convertedEntityNameObj.setTsTag(TSTag.TRADITIONAL_TO_SIMPLIFIED.getValue());
                convertedEntityNameList.add(convertedEntityNameObj);
            }
        }
    }

    public void saveConvertedEntityNames() throws Exception {
        EntityNameBL entityNameBL = new EntityNameBL();
        entityNameBL.truncate();
        entityNameBL.batchInsert(convertedEntityNameList);
    }

    public void execute() throws Exception {
        loadOriginalEntityNames();
        process();
        saveConvertedEntityNames();
    }

    public static void main(String[] args) throws Exception {
        new EntityNameTSConverter().execute();
    }
}
