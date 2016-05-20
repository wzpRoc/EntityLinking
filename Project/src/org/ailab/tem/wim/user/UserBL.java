package org.ailab.tem.wim.user;

import com.sun.xml.internal.stream.writers.UTF8OutputStreamWriter;
import org.ailab.tem.wim.menu.MenuBL;
import org.ailab.tem.wim.userGroupUser.UserGroupUser;
import org.ailab.tem.wim.userGroupUser.UserGroupUserBL;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.user.BaseUser;
import org.ailab.wimfra.util.*;
import org.ailab.wimfra.util.time.TimeUtil;

import java.sql.SQLException;
import java.util.*;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-12
 * 功能描述：用户业务逻辑
 */
public class UserBL extends BaseBL {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(UserBL.class);
    protected UserDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    public static boolean isCacheEnabled = true;
    public static List<User> dtoList;
    public static List<User> annotatorList;
    public static List<User> annotatorAdministratorList;
    public static List<User> administratorList;
    public static List<User> advancedUserList;
    public static Map<Integer, User> idToDtoMap;

    static {
        try {
            UserBL userBL = new UserBL();
            userBL.reloadCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新载入缓存
     */
    protected void reloadCache() {
        BaseBL.reloadCache(this);

        annotatorList = new ArrayList<User>();
        annotatorAdministratorList = new ArrayList<User>();
        administratorList = new ArrayList<User>();
        advancedUserList = new ArrayList<User>();

        for (User user : dtoList) {
            if (user.getRoleEnum() == UserRole.ANNOTATOR) {
                annotatorList.add(user);
                advancedUserList.add(user);
            } else if (user.getRoleEnum() == UserRole.ANNOTATOR_ADMINISTRATOR) {
                annotatorList.add(user);
                annotatorAdministratorList.add(user);
                advancedUserList.add(user);
            } else if (user.getRoleEnum() == UserRole.ADMINISTRATOR) {
                annotatorList.add(user);
                administratorList.add(user);
                advancedUserList.add(user);
            }
        }
    }


    public void reloadUserInfo() throws Exception {
        reloadCache();
    }


    /**
     * 从缓存中获得对象
     */
    public static User getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = UserBL.getByIdFromCache(UserBL.class, id);
        if (dto != null) {
            return ((User) dto);
        } else {
            return null;
        }
    }

    /**
     * 从缓存中获得昵称
     */
    public static String getUserNameFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = UserBL.getByIdFromCache(UserBL.class, id);
        if (dto != null) {
            return ((User) dto).getUsername();
        } else {
            return null;
        }
    }

    /**
     * 从缓存中获得昵称
     */
    public static String getNicNameFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = UserBL.getByIdFromCache(UserBL.class, id);
        if (dto != null) {
            return ((User) dto).getNicName();
        } else {
            return null;
        }
    }

    /**
     * 从缓存中获得真名
     */
    public static String getRealNameFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = UserBL.getByIdFromCache(UserBL.class, id);
        if (dto != null) {
            return ((User) dto).getRealName();
        } else {
            return null;
        }
    }
    ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public UserBL() {
        this.dao = new UserDAO();
        super.dao = this.dao;
    }

    /**
     * 初始化一条记录
     */
    public void initDto(BaseDTO dto) {
        User user = (User) dto;
        user.setBirthday("1970-01-01");
        user.setCarPurchaseDate(TimeUtil.getYyyy_mm_dd());
        user.setLastLoginTime("1970-01-01");
    }


    /**
     * 由用户名、密码得到user
     */
    public User get(String username, String password) throws SQLException {
        return (User) dao.getByUsernameAndPassword(username, password);
    }


    /**
     * 插入一条记录
     */
    public int insert(BaseDTO dto) {
        User user = (User) dto;

        // 检查是否加重了

        user.setId(getNextId());
        user.setRegisterTime(TimeUtil.getYyyy_mm_dd_hh_mm_ss());
        user.setLastLoginTime(TimeUtil.getYyyy_mm_dd_hh_mm_ss());
        final int effectedRowsCount = dao.insert(user);

        reloadCache();

        return effectedRowsCount;
    }

    /**
     * 用户登录
     */
    public BLMessage login(BaseUser baseUser) {
        BLMessage msg = new BLMessage();

        try {
            // 验证
            User user;
            List<Object> list = new ArrayList<Object>();
            list.add(baseUser.getUsername());
            list.add(baseUser.getUsername());
            list.add(baseUser.getPassword());
            user = (User) dao.getBySql(
                    "SELECT * FROM user " +
                            "WHERE (username = ? OR email = ?) " +
                            "AND password = ?",
                    list);

            if (user != null) {
                // 登陆成功
                if (user.getRole() > UserRole.GENERAL.getId()) {
                    // 读取用户组
                    UserGroupUserBL userGroupUserBL = new UserGroupUserBL();
                    user.setGroupIdSet(userGroupUserBL.getGroupIdSetByUserId(user.getId()));
                    // 读取菜单
                    MenuBL menuBL = new MenuBL();
                    user.setRootMenu(menuBL.getRootMenuWithOffspringByGroupIds(user.getGroupIdSet()));
                }

                msg.setSuccess(true);
                msg.setData(user);
                msg.setMessage("登陆成功");
            } else {
                msg.setSuccess(false);
                msg.setMessage("登陆失败");
            }
        } catch (SQLException e) {
            logger.error(e);
            msg.setSuccess(false);
            msg.setMessage(e.getMessage());
        }

        return msg;
    }

    /**
     * 根据userId刷新目录
     *
     * @param userId
     */
    public BLMessage reloadUserMenuByUserId(int userId) {
        try {
            User user = getFromCache(userId);
            if (user != null && user.getRole() > UserRole.GENERAL.getId()) {
                // 读取用户组
                UserGroupUserBL userGroupUserBL = new UserGroupUserBL();
                user.setGroupIdSet(userGroupUserBL.getGroupIdSetByUserId(user.getId()));
                // 读取菜单
                MenuBL menuBL = new MenuBL();
                user.setRootMenu(menuBL.getRootMenuWithOffspringByGroupIds(user.getGroupIdSet()));
                return new BLMessage(true, "刷新成功", user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BLMessage(false, "刷新失败");
    }


    /**
     * 用户注册检查唯一性
     *
     * @param para
     * @return BlMessage
     */
    public BLMessage isValid(String para, String val) {
        BLMessage msg = new BLMessage();

        // 验证
        Vector vector = new Vector();
        vector.add(val);
        User user = (User) dao.getBySql("select * from user where " + para + " = ?", vector);

        if (user != null) {
            msg.setSuccess(false);
            msg.setData(user);
            msg.setMessage("该参数已被使用！");
        } else {
            msg.setSuccess(true);
            msg.setMessage("该参数可以使用！");
        }

        return msg;
    }

    public List<User> getUserListByUserIdSet(Set<Integer> userIdSet) throws SQLException {
        if (userIdSet.isEmpty()) {
            return null;
        } else {
            return dao.getUserListByUserIds(StringUtil.collectionToString(userIdSet, ","));
        }
    }


    /**
     * 通过用户组Id得到该用户组中的所有用户
     *
     * @param groupId
     * @return
     * @throws java.sql.SQLException
     */
    public List<User> getUserListByGroupId(int groupId) throws SQLException {
        UserGroupUserBL userGroupUserBL = new UserGroupUserBL();
        List<UserGroupUser> userGroupUsers = userGroupUserBL.getListByCondition("userGroupId = ?", groupId);
        UserBL userBL = new UserBL();
        List<User> userList = new ArrayList<User>();
        if (userGroupUsers != null) {
            for (UserGroupUser userGroupUser : userGroupUsers) {
                User user = (User) userBL.get(userGroupUser.getUserId());
                userList.add(user);
            }
        }

        return userList;
    }

    public static void main(String[] args) throws SQLException {
        UserBL userBL = new UserBL();
        Set<Integer> userIdSet = new HashSet<Integer>();
        userIdSet.add(1);
        userIdSet.add(9);
        userIdSet.add(10);
        userIdSet.add(12);
        System.out.println(StringUtil.collectionToString(userIdSet, ","));
        List<User> userList = userBL.getUserListByUserIdSet(userIdSet);
        System.out.println(userList.size());
    }

}
