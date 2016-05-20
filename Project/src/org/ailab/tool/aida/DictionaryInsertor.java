package org.ailab.tool.aida;

import org.ailab.entityLinking.wim.entityName.EntityName;
import org.ailab.entityLinking.wim.entityName.EntityNameBL;
import org.ailab.tool.WikiTitleAndPageIdMap;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: robby
 * Date: 15-12-23
 * Time: 下午3:40
 * Desc: 读取词典文件，插入到entityName表
 */
public class DictionaryInsertor {

    public static void main(String args[]) throws Exception {
        String path = "D:\\Datasets_of_EL\\GoogleCrossWikiDictionary\\compressedDictionary.txt";
        List<String> lines = FileUtil.readLines(path);
        List<EntityName> entityNameList = new ArrayList<EntityName>();
        for (int i=0; i<lines.size(); i++)
        {
            EntityName entityName = new EntityName();
            String strArray[] = lines.get(i).split("\t");
            if (strArray.length >= 2)
            {
                String name = strArray[0];
                if(name.endsWith("_(disambiguation)")
                        || name.startsWith("List_of_")) {
                    continue;
                }
                entityName.setName(name);
//                entityName.setName(strArray[0].toLowerCase());
                String titleAndProb[] = strArray[1].split(" ");
                String prob;
                if (titleAndProb.length >= 2)
                {
                    prob = titleAndProb[0];
                    entityName.setProbOfNameToEntity(Float.parseFloat(prob));
                    String title_dict = titleAndProb[1];
                    entityName.setWikiTitle(title_dict);
                    // 如果指向重定向，那么直接返回重定向目标页的ID
                    int entity_id = WikiTitleAndPageIdMap.getPageIdOrRedirect(title_dict);
                    entityName.setEntityId(entity_id);
                }
            }
            entityNameList.add(entityName);
        }

        DBUtil.executeUpdate("truncate table el.entityName");
        (new EntityNameBL()).batchInsert(entityNameList);
    }
}