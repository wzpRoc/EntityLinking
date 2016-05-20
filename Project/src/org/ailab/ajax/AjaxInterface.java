package org.ailab.ajax;

import flexjson.JSONSerializer;
import org.ailab.entityLinking_old.candidateEntityGenerate.CandidateEntityGenerator;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.AnnoState;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.doc.DocBL;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.entityLinking_old.wim.entity.EntityBL;
import org.ailab.tem.wim.userGroup.UserGroup;
import org.ailab.tem.wim.userGroup.UserGroupBL;
import org.ailab.tem.wim.userGroupMenu.UserGroupMenu;
import org.ailab.tem.wim.userGroupMenu.UserGroupMenuBL;
import org.ailab.tem.wim.userGroupPrivilege.UserGroupPrivilege;
import org.ailab.tem.wim.userGroupPrivilege.UserGroupPrivilegeBL;
import org.ailab.tem.wim.userGroupUser.UserGroupUser;
import org.ailab.tem.wim.userGroupUser.UserGroupUserBL;
import org.ailab.wimfra.core.BLMessage;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;

import java.sql.SQLException;
import java.util.*;

/**
 * 异步Ajax方法类
 *
 * @author playcoin
 */
public class AjaxInterface {
    Logger logger = Logger.getLogger(this.getClass());


    private static CandidateEntityGenerator candidateEntityGenerator;

    static {
        Set<Integer> docIdSet = (new DocBL()).getDocIdSetByAnnoState("2");
        TrainingCorpusContext trainingCorpusContext = new TrainingCorpusContext(docIdSet);
        candidateEntityGenerator = new CandidateEntityGenerator(trainingCorpusContext);
        candidateEntityGenerator.setIncludeNIL(false);
    }

    /**
     * 候选项查询
     *
     * @param name   查询文本
     * @param fuzzy  模糊查询的标志位
     * @return
     */
    public String getCandidateList(String name, boolean fuzzy) throws SQLException {
        List<Entity> entityList;
        if(!fuzzy) {
            entityList = candidateEntityGenerator.generate(name);
        } else {
            EntityBL abl = new EntityBL();
            entityList = abl.queryEntity(name, fuzzy);
        }

        // 将list转化成JSON串
        JSONSerializer serializer = new JSONSerializer();
        return serializer.exclude("*.class").serialize(entityList);
    }


    public String saveAnno(Doc doc, List<DocEntity> docEntityList, String updaterId) throws Exception {
        (new DocBL()).saveAnno(doc, docEntityList, Integer.parseInt(updaterId), AnnoState.manuallyAnnotated.getValue());
        return "Saved successfully :)";
    }



    /**
     * 更新用户组中的人员信息
     *
     * @param userGroupId
     * @param userIds
     * @return
     */
    public BLMessage updateUserGroupUser(int userGroupId, String userIds) {
        try {
            // 1 删除原来的人员组信息
            UserGroupUserBL userGroupUserBL = new UserGroupUserBL();
            userGroupUserBL.deleteByGroupId(userGroupId);

            //2 增加新的人员组信息
            String userIdArray[] = convertIdsToIdArray(userIds);
            for (String userId : userIdArray) {
                UserGroupUser userGroupUser = new UserGroupUser(Integer.parseInt(userId), userGroupId);
                userGroupUserBL.insert(userGroupUser);
            }

            //3 更新用户组中的人员总数
            UserGroupBL userGroupBL = new UserGroupBL();
            UserGroup userGroup = (UserGroup) userGroupBL.get(userGroupId);
            userGroup.setUserCount(userIdArray.length);
            userGroupBL.update(userGroup);

            return new BLMessage(true,"保存成功", userGroup.getUserCount());
        } catch (Exception e) {
            e.printStackTrace();
            return new BLMessage(false, "抱歉，保存失败" + e);

        }
    }



    /**
     * 将js中拼接的字符串转化为数组
     */
    private String[] convertIdsToIdArray(String ids) {
        if (StringUtil.isEmpty(ids)) {
            return null;
        }
        if (ids.charAt(ids.length() - 1) == ',') {
            ids = ids.substring(0, ids.length() - 1);
        }
        String idArray[] = ids.split(",");
        return idArray;
    }



    /**
     * 更新用户拥有的菜单信息
     *
     * @param userGroupId
     * @param menuIds
     * @return
     */
    public BLMessage updateUserGroupMenu(int userGroupId, String menuIds) {
        try {
            // 1 删除原来的菜单信息
            UserGroupMenuBL userGroupMenuBL = new UserGroupMenuBL();
            userGroupMenuBL.deleteByGroupId(userGroupId);
            //2 增加新的菜单信息
            String menuIdArray[] = convertIdsToIdArray(menuIds);
            for (String menuId : menuIdArray) {
                UserGroupMenu userGroupMenu = new UserGroupMenu(userGroupId, Integer.valueOf(menuId));
                userGroupMenuBL.insert(userGroupMenu);
            }
            return new BLMessage(true, "保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new BLMessage(false, "抱歉，保存失败" + e);
        }

    }

    /**
     * 更新用户组权限信息
     * @param userGroupId
     * @param privilegeIds
     * @return
     */
    public BLMessage updateUserGroupPrivilege(int userGroupId, String privilegeIds) {
        try {
            // 1 删除原来的权限信息
            UserGroupPrivilegeBL userGroupPrivilegeBL = new UserGroupPrivilegeBL();
            userGroupPrivilegeBL.deleteByGroupId(userGroupId);

            //2 增加新的权限信息
            String privilegeIdArray[] = convertIdsToIdArray(privilegeIds);
            for (String privilegeId : privilegeIdArray) {
                UserGroupPrivilege userGroupPrivilege = new UserGroupPrivilege(userGroupId, Integer.valueOf(privilegeId));
                userGroupPrivilegeBL.insert(userGroupPrivilege);
            }
            return new BLMessage(true, "保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new BLMessage(false, "抱歉，保存失败" + e);
        }
    }

    /********************************************* 用户权限菜单 结束 **************************************************/


}
