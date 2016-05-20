package org.ailab.tem.wim.user;

import org.ailab.tem.wim.menu.Menu;
import org.ailab.tem.wim.menu.UserMenu;
import org.ailab.wimfra.core.IValueAndLabel;
import org.ailab.wimfra.user.BaseUser;
import org.ailab.wimfra.util.StringUtil;

import java.util.List;
import java.util.Set;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-12-10
 * 功能描述：用户
 */
public class User extends BaseUser implements IValueAndLabel {
    // ID
    protected int id;

    // 登录用户名
    protected String username;

    // 密码
    protected String password;

    // 昵称
    protected String nicName;

    // 真实姓名
    protected String realName;

    // 角色
    protected int role;

    // 客户类别
    protected int customerType;

    // 积分余额
    protected int points;

    // 等级
    protected int grade;

    // 等级分数
    protected int gradePoint;

    // 注册时间
    protected String registerTime;

    // 最近登录时间
    protected String lastLoginTime;

    // 邮箱
    protected String email;

    // 移动电话
    protected String mobilephone;

    // 固定电话
    protected String telephone;

    // 头像URL
    protected String photoUrl;

    // 性别
    protected int sex;

    // 生日
    protected String birthday;

    // 通信地址
    protected String address;

    // 爱好
    protected String hobby;

    // 车型
    protected String carType;

    // 购车日期
    protected String carPurchaseDate;

    // 学历
    protected int degree;

    // 证件类别
    protected int certificateType;

    // 证件号码
    protected String certificateNo;

    // 设备标志
    protected int deviceTag;

    // 状态
    protected int state;

    // 发送验证时间
    protected String sendValidationTime;

    // 验证码
    protected String authenticode;

    private UserMenu userMenu;

    // 用户所属用户组ID集合
    protected Set<Integer> groupIdSet;

    // 用户拥有的一级菜单
    protected Menu rootMenu;

    // 用户是否在某一用户组中
    private boolean inGroup;

    // 标注用户类型
    protected int annotationType;

    // 标注用户
    protected boolean isLyricsAnnotation;

    protected boolean isIntroductionAnnotation;

    protected boolean isGDPAnnotation;

    protected boolean isSDPAnnotation;

    protected boolean isADPAnnotation;

    protected boolean isPropertyAnnotation;

    public User() {}

    public User(String username, String nicName, String realName, String telephone, String email, String password, String registerTime, String lastLoginTime) {
        this.username = username;
        this.nicName = nicName;
        this.realName = realName;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 得到ID
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 得到登录用户名
     *
     * @return 登录用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置登录用户名
     *
     * @param username 登录用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 得到密码
     *
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 得到昵称
     *
     * @return 昵称
     */
    public String getNicName() {
        return nicName;
    }

    /**
     * 设置昵称
     *
     * @param nicName 昵称
     */
    public void setNicName(String nicName) {
        this.nicName = nicName;
    }

    /**
     * 得到真实姓名
     *
     * @return 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 得到角色
     *
     * @return 角色
     */
    public int getRole() {
        return role;
    }

    public UserRole getRoleEnum() {
        return UserRole.getByValue(role);
    }

    /**
     * 设置角色
     *
     * @param role 角色
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * 得到客户类别
     *
     * @return 客户类别
     */
    public int getCustomerType() {
        return customerType;
    }

    public String getCustomerTypeName() {
        CustomerType customerTypeObj = CustomerType.getByValue(customerType);
        if (customerTypeObj == null) {
            return "unknown role:" + role;
        } else {
            return customerTypeObj.getName();
        }
    }

    /**
     * 设置客户类别
     *
     * @param customerType 客户类别
     */
    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    /**
     * 得到积分余额
     *
     * @return 积分余额
     */
    public int getPoints() {
        return points;
    }

    /**
     * 设置积分余额
     *
     * @param points 积分余额
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * 得到等级
     *
     * @return 等级
     */
    public int getGrade() {
        return grade;
    }

    /**
     * 设置等级
     *
     * @param grade 等级
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * 得到等级分数
     *
     * @return 等级分数
     */
    public int getGradePoint() {
        return gradePoint;
    }

    /**
     * 设置等级分数
     *
     * @param gradePoint 等级分数
     */
    public void setGradePoint(int gradePoint) {
        this.gradePoint = gradePoint;
    }

    /**
     * 得到注册时间
     *
     * @return 注册时间
     */
    public String getRegisterTime() {
        if (registerTime == null)
            return "";
        return registerTime;
    }

    /**
     * 设置注册时间
     *
     * @param registerTime 注册时间
     */
    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * 得到最近登录时间
     *
     * @return 最近登录时间
     */
    public String getLastLoginTime() {
        if (lastLoginTime == null)
            return " ";
        return lastLoginTime;
    }

    /**
     * 设置最近登录时间
     *
     * @param lastLoginTime 最近登录时间
     */
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 得到邮箱
     *
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 得到移动电话
     *
     * @return 移动电话
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * 设置移动电话
     *
     * @param mobilephone 移动电话
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    /**
     * 得到固定电话
     *
     * @return 固定电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置固定电话
     *
     * @param telephone 固定电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 得到头像URL
     *
     * @return 头像URL
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 设置头像URL
     *
     * @param photoUrl 头像URL
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 得到性别
     *
     * @return 性别
     */
    public int getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * 得到生日
     *
     * @return 生日
     */
    public String getBirthday() {
        if (birthday == null)
            return "";
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 得到通信地址
     *
     * @return 通信地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置通信地址
     *
     * @param address 通信地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 得到爱好
     *
     * @return 爱好
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * 设置爱好
     *
     * @param hobby 爱好
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * 得到车型
     *
     * @return 车型
     */
    public String getCarType() {
        return carType;
    }

    /**
     * 设置车型
     *
     * @param carType 车型
     */
    public void setCarType(String carType) {
        this.carType = carType;
    }

    /**
     * 得到购车日期
     *
     * @return 购车日期
     */
    public String getCarPurchaseDate() {
        if (carPurchaseDate == null)
            return "";
        return carPurchaseDate;
    }

    /**
     * 设置购车日期
     *
     * @param carPurchaseDate 购车日期
     */
    public void setCarPurchaseDate(String carPurchaseDate) {
        this.carPurchaseDate = carPurchaseDate;
    }

    /**
     * 得到学历
     *
     * @return 学历
     */
    public int getDegree() {
        return degree;
    }

    /**
     * 设置学历
     *
     * @param degree 学历
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    /**
     * 得到证件类别
     *
     * @return 证件类别
     */
    public int getCertificateType() {
        return certificateType;
    }

    /**
     * 设置证件类别
     *
     * @param certificateType 证件类别
     */
    public void setCertificateType(int certificateType) {
        this.certificateType = certificateType;
    }

    /**
     * 得到证件号码
     *
     * @return 证件号码
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * 设置证件号码
     *
     * @param certificateNo 证件号码
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    /**
     * 得到设备标志
     *
     * @return 设备标志
     */
    public int getDeviceTag() {
        return deviceTag;
    }

    /**
     * 设置设备标志
     *
     * @param deviceTag 设备标志
     */
    public void setDeviceTag(int deviceTag) {
        this.deviceTag = deviceTag;
    }

    /**
     * 得到状态
     *
     * @return 状态
     */
    public int getState() {
        return state;
    }

    /**
     * 设置状态
     *
     * @param state 状态
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * 得到发送验证时间
     *
     * @return 发送验证时间
     */
    public String getSendValidationTime() {
        if (sendValidationTime == null)
            return "";
        return sendValidationTime;
    }

    /**
     * 设置发送验证时间
     *
     * @param sendValidationTime 发送验证时间
     */
    public void setSendValidationTime(String sendValidationTime) {
        this.sendValidationTime = sendValidationTime;
    }

    /**
     * 得到验证码
     *
     * @return 验证码
     */
    public String getAuthenticode() {
        return authenticode;
    }

    /**
     * 设置验证码
     *
     * @param authenticode 验证码
     */
    public void setAuthenticode(String authenticode) {
        this.authenticode = authenticode;
    }

    // 返回相应类型对应的字符串
    public String getSexStr() {
        if (sex == 1)
            return "男";
        else if (sex == 2)
            return "女";
        else
            return "未知";
    }

    public String getGradeStr() {
        switch (grade) {
            case 0:
                return "草民";
            case 1:
                return "捕快";
            case 2:
                return "亭长";
            case 3:
                return "县令";
            case 4:
                return "巡抚";
            case 5:
                return "总督";
            case 6:
                return "封疆大臣";
            case 7:
                return "军机大臣";
            case 8:
                return "御前";
            default:
                return "无名氏";
        }
    }

    public String getDegreeStr() {
        switch (degree) {
            case 1:
                return "本科生";
            case 2:
                return "研究生";
            case 3:
                return "博士及以上";

            default:
                return "其他";
        }
    }

    public String getCertificateTypeStr() {
        switch (certificateType) {
            case 1:
                return "身份证";
            case 2:
                return "军官证";
            default:
                return "其他";
        }
    }

    public String getRoleStr() {
        UserRole userRoleObj = UserRole.getByValue(role);
        if (userRoleObj == null) {
            return "unknown role:" + role;
        } else {
            return userRoleObj.getName();
        }
    }

    public String getStateStr() {
        switch (state) {
            case 0:
                return "未验证";
            case 1:
                return "已验证";
            default:
                return "未知";
        }
    }

    @Override
    public String getValue() {
        return String.valueOf(id);
    }

    @Override
    public String getLabel() {
        return realName;
    }

    public boolean isPermittedToAnnotate() {
        return role >= UserRole.ANNOTATOR.getId();
    }

    public boolean isPermittedToAnnotateConfirm() {
        return role >= UserRole.ANNOTATOR_ADMINISTRATOR.getId();
    }

    public UserMenu getUserMenu() {
        return userMenu;
    }

    public void setUserMenu(UserMenu userMenu) {
        this.userMenu = userMenu;
    }

    public Set<Integer> getGroupIdSet() {
        return groupIdSet;
    }

    public void setGroupIdSet(Set<Integer> groupIdSet) {
        this.groupIdSet = groupIdSet;
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    public void setRootMenu(Menu rootMenu) {
        this.rootMenu = rootMenu;
    }

    public String toString() {
        return (StringUtil.notEmpty(realName)) ? realName : nicName;
    }

    public boolean isInGroup() {
        return inGroup;
    }

    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    public int getAnnotationType() {
        return annotationType;
    }

    public void setAnnotationType(int annotationType) {
        this.annotationType = annotationType;
    }

    public boolean isLyricsAnnotation() {
        return isLyricsAnnotation;
    }

    public void setLyricsAnnottaion(boolean lyricsAnnottaion) {
        isLyricsAnnotation = lyricsAnnottaion;
    }

    public boolean isIntroductionAnnotation() {
        return isIntroductionAnnotation;
    }

    public void setIntroductionAnnottaion(boolean introductionAnnottaion) {
        isIntroductionAnnotation = introductionAnnottaion;
    }

    public boolean isGDPAnnotation() {
        return isGDPAnnotation;
    }

    public void setGDPAnnottaion(boolean GDPAnnottaion) {
        isGDPAnnotation = GDPAnnottaion;
    }

    public boolean isSDPAnnotation() {
        return isSDPAnnotation;
    }

    public void setSDPAnnottaion(boolean SDPAnnottaion) {
        isSDPAnnotation = SDPAnnottaion;
    }

    public boolean isADPAnnotation() {
        return isADPAnnotation;
    }

    public void setADPAnnottaion(boolean ADPAnnottaion) {
        isADPAnnotation = ADPAnnottaion;
    }

    public boolean isPropertyAnnotation() {
        return isPropertyAnnotation;
    }

    public void setPlacementAnnottaion(boolean placementAnnottaion) {
        isPropertyAnnotation = placementAnnottaion;
    }

}
