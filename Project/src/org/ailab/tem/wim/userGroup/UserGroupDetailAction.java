package org.ailab.tem.wim.userGroup;

import org.ailab.tem.wim.menu.Menu;
import org.ailab.tem.wim.menu.MenuBL;
import org.ailab.tem.wim.privilege.Privilege;
import org.ailab.tem.wim.privilege.PrivilegeBL;
import org.ailab.tem.wim.user.User;
import org.ailab.tem.wim.userGroupPrivilege.UserGroupPrivilege;
import org.ailab.tem.wim.userGroupPrivilege.UserGroupPrivilegeBL;
import org.ailab.wimfra.core.BaseDetailAction;
import org.ailab.wimfra.util.StringUtil;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组业务逻辑
 */


public class UserGroupDetailAction extends BaseDetailAction {
    // 用户组的人员
    private List<User> allUserList;

    // 用户组的菜单权限
    private Menu rootMenuOfAllMenu;

    // 用户组审核权限
    private List<Privilege> privilegeList;

    public UserGroupDetailAction() {
        dto = new UserGroup();
        bl = new UserGroupBL();
    }

    /**
     * 处理一条记录
     */
    public String myExecute() throws Exception {
        if (StringUtil.isEmpty(saveTag)) {
            if ("add".equals(dowhat)) {
                bl.initDto(dto);
            } else {
                getDtoFromDB();
            }
        } else {
            saveDtoToDB();
        }
        UserGroup userGroup = (UserGroup) dto;
        int groupId = userGroup.getId();
        allUserList = ((UserGroupBL) bl).getAllUserListByGroupId(groupId);
        List<Menu> allMenuList = ((UserGroupBL) bl).getAllMenuListByGroupId(groupId);
        rootMenuOfAllMenu = (new MenuBL()).getRootMenuWithOffspringWithMenuList(allMenuList);

        getPrivilegeInfoFromDB();

        return SUCCESS;
    }


    /**
     * 获得权限
     * @throws java.sql.SQLException
     */
    public void getPrivilegeInfoFromDB() throws SQLException {
        int groupId  = dto.getId();
        PrivilegeBL privilegeBL = new PrivilegeBL();
        UserGroupPrivilegeBL userGroupPrivilegeBL = new UserGroupPrivilegeBL();
        List<UserGroupPrivilege> userGroupPrivilegeList = userGroupPrivilegeBL.getListByCondition("userGroupId = ?", groupId);
        privilegeList = privilegeBL.getList();

        HashSet<Integer> privilegeIdSet = new HashSet<Integer>();
        for(UserGroupPrivilege userGroupPrivilege: userGroupPrivilegeList) {
            privilegeIdSet.add(userGroupPrivilege.getPrivilegeId());
        }

        for(Privilege privilege: privilegeList) {
            if(privilegeIdSet.contains(privilege.getId()))
                privilege.setInGroup(true);
            else
                privilege.setInGroup(false);
        }



    }

    /**
     * 获得一条记录
     */
    public void getDtoFromDB() throws Exception {
        dto = bl.get(id);
    }


    public List<User> getAllUserList() {
        return allUserList;
    }

    public void setAllUserList(List<User> allUserList) {
        this.allUserList = allUserList;
    }

    public Menu getRootMenuOfAllMenu() {
        return rootMenuOfAllMenu;
    }

    public void setRootMenuOfAllMenu(Menu rootMenuOfAllMenu) {
        this.rootMenuOfAllMenu = rootMenuOfAllMenu;
    }

    public String getDowhat() {
        return dowhat;
    }

    public void setDowhat(String dowhat) {
        this.dowhat = dowhat;
    }

    public List<Privilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<Privilege> privilegeList) {
        this.privilegeList = privilegeList;
    }
}
