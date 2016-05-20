package org.ailab.tool.entityName;

import org.ailab.entityLinking_old.wim.entityName.EntityName;
import org.ailab.entityLinking_old.wim.entityName.EntityNameBL;
import org.ailab.wimfra.util.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ailab_pc_1
 * Date: 15-5-13
 * Time: 上午12:39
 * Desc：处理NLPCC知识库中，名字用顿号隔开的情况，一条记录变成多条记录
 */
public class EntityNameCleaner {
    private List<EntityName> originalEntityNameList;
    private List<EntityName> cleanedEntityNameList;

    public void loadOriginalEntityNames() throws SQLException {
        EntityNameBL entityNameBL = new EntityNameBL();
        entityNameBL.setTableName("entityName_original");
        originalEntityNameList = entityNameBL.getList();
    }

    public void process() {
        cleanedEntityNameList = new ArrayList<EntityName>(originalEntityNameList.size());
        for(EntityName entityNameObj : originalEntityNameList) {
            String entityName = entityNameObj.getEntityName();
            if(entityName == null) {
                continue;
            }
            entityName = entityName.trim();
            if(StringUtil.isEmpty(entityName)) {
                continue;
            }

            if(entityName.contains("、")) {
                System.out.println(entityName);
                String[] splittedEntityNames = entityName.split("、");
                for(String splittedEntityName : splittedEntityNames) {
                    splittedEntityName = splittedEntityName.trim();
                    if(!"".equals(splittedEntityName)) {
                        EntityName newEntityName = new EntityName();
                        newEntityName.setEntityId(entityNameObj.getEntityId());
                        newEntityName.setEntityTitle(entityNameObj.getEntityTitle());
                        newEntityName.setPredicateName(entityNameObj.getPredicateName());
                        newEntityName.setEntityName(splittedEntityName);
                        cleanedEntityNameList.add(newEntityName);
                    }
                }
            } else {
                entityNameObj.setEntityName(entityName);
                cleanedEntityNameList.add(entityNameObj);
            }
        }
    }

    public void saveCleanedEntityNames() throws Exception {
        EntityNameBL entityNameBL = new EntityNameBL();
        entityNameBL.truncate();
        entityNameBL.batchInsert(cleanedEntityNameList);
    }

    public void execute() throws Exception {
        loadOriginalEntityNames();
        process();
        saveCleanedEntityNames();
    }

    public static void main(String[] args) throws Exception {
        new EntityNameCleaner().execute();
    }
}
