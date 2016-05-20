package org.ailab.tool;

import org.ailab.entityLinking.wim.entityName.EntityName;
import org.ailab.entityLinking.wim.entityName.EntityNameBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.wimfra.util.SortUtil;

import java.util.*;

/**
 * User: lutingming
 * Date: 15-12-23
 * Time: 下午10:42
 * Desc: 根据mention.name处理entityName
 * 对每个mention.name处理
 * 1. 如果能精确查找到entityName，那么直接使用
 * 2. 否则，不区分大小写查找
 * 3. 对查找到的结果排序、编号
 * 4. 插入到entityName_new
 */
public class EntityNameProcessor {
    private static List<Mention> mentionList;
    private static Set<String> mentionNameSet;
    private static List<String> mentionNameList;
    private static MentionBL mentionBL;

    public static void main(String[] args) throws Exception {
        loadMentions();
        process();
    }

    private static void loadMentions() {
        mentionBL = new MentionBL();
        mentionList = mentionBL.getList();
        mentionNameSet = new HashSet<String>();
        mentionNameList = new ArrayList<String>();

        for (Mention mention : mentionList) {
            if(mentionNameSet.contains(mention.getName())) {
                // ok
            } else {
                mentionNameSet.add(mention.getName());
                mentionNameList.add(mention.getName());
            }
        }

        System.out.println("mentionNameSet.size = " + mentionNameSet.size());
        System.out.println("mentionNameList.size = " + mentionNameList.size());
    }

    /**
     * 计算覆盖率
     * @throws Exception
     */
    private static void process() throws Exception {
        List<EntityName> resultEntityNameList = new ArrayList<EntityName>();

        EntityNameBL entityNameBL = new EntityNameBL();
        entityNameBL.setTableName("entityName_old");

        for(int i=0; i<mentionNameList.size(); i++) {
            System.out.println("processing " + (i+1) + " mention");
            String mentionName = mentionNameList.get(i);
            // 区分大小写查找
            List<EntityName> entityNameListByNameCS = entityNameBL.getMaxListByName(mentionName);
            // 不区分大小写查找
            List<EntityName> entityNameListByNameCI = entityNameBL.getAverageListByLcName(mentionName);
            // 声明合并结果
            List<EntityName> mergedEntityNameList = new ArrayList<EntityName>();
            // 开始合并
            boolean hasCSResult = entityNameListByNameCS != null && entityNameListByNameCS.size() > 0;
            boolean hasCIResult = entityNameListByNameCI != null && entityNameListByNameCI.size() > 0;
            if(hasCSResult) {
                mergedEntityNameList.addAll(entityNameListByNameCS);
                if(hasCIResult) {
                    // 1. 不区分大小写的结果的概率，不应超过区分大小写的结果的最大概率
                    double maxCSProb = entityNameListByNameCS.get(0).getProbOfNameToEntity();
                    updateProb(entityNameListByNameCI, maxCSProb);
                    // 2. 如果有相同的候选实体，以CS为准
                    dedubplicate(entityNameListByNameCS, entityNameListByNameCI);
                    // 加入合并后的列表
                    mergedEntityNameList.addAll(entityNameListByNameCI);
                } else {
                    // do nothing
                }
            } else {
                // 没有区分大小写结果，直接使用不区分大小写结果
                if(hasCIResult) {
                    mergedEntityNameList.addAll(entityNameListByNameCI);
                } else {
                    // 啥都没有
                }
            }

            // 排序
            SortUtil.sort(mergedEntityNameList, new EntityNameComparer(), false);
            // 编号
            setSeq(mergedEntityNameList);
            resultEntityNameList.addAll(mergedEntityNameList);
        }

        save(resultEntityNameList);
    }


    private static int save(List<EntityName> resultEntityNameList) throws Exception {
        EntityNameBL entityNameBL = new EntityNameBL();
        entityNameBL.truncate();
        return entityNameBL.batchInsert(resultEntityNameList);
    }


    /**
     * 去重，如果有相同的，以list0为准
     */
    private static void dedubplicate(List<EntityName> list0, List<EntityName> list1) throws Exception {
        for(int i0=0; i0<list0.size(); i0++) {
            EntityName entityName0 = list0.get(i0);

            for(int j1=list1.size()-1; j1>=0; j1--) {
                EntityName entityName1 = list1.get(j1);
                if(entityName0.getEntityId() == entityName1.getEntityId()) {
                    list1.remove(j1);
                }
            }
        }
    }
    
    
    private static void updateProb(List<EntityName> entityNameList, double rate) {
        for(int i=0; i<entityNameList.size(); i++) {
            EntityName entityName = entityNameList.get(i);
            entityName.setProbOfNameToEntity(entityName.getProbOfNameToEntity()*rate);
        }
    }


    private static void setSeq(List<EntityName> entityNameList) {
        for(int i=0; i<entityNameList.size(); i++) {
            entityNameList.get(i).setSeq(i);
        }
    }
}
