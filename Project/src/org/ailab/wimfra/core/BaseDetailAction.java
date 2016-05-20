package org.ailab.wimfra.core;

import org.ailab.wimfra.user.BaseUser;
import org.ailab.wimfra.util.StringUtil;

/**
 * 功能：处理一条记录的基Action
 * 开发人：鲁廷明
 */
public abstract class BaseDetailAction extends BaseAction {
    // id
    protected String id;

    protected BaseDTO dto;

    protected BaseBL bl;

    protected String dowhat;

    protected String saveTag;

    protected String dowhatInList;


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

        return SUCCESS;
    }


    /**
     * 保存一条记录到数据库
     */
    protected void saveDtoToDB() throws Exception {
        BaseUser user = (BaseUser) getUser();

        if ("add".equals(dowhat)) {
//            if(!user.isPermittedToAdd()){
//                throw new Exception("当前用户无权限执行增加操作");
//            }
            addDtoToDB();
            msg = "增加成功";
            dowhat = "edit";
        } else {
//            if(!user.isPermittedToEdit()){
//                throw new Exception("当前用户无权限执行修改操作");
//            }
            editDtoToDB();
            msg = "修改成功";
        }
    }


    /**
     * 获得一条记录
     */
    public void getDtoFromDB() throws Exception {
        dto = bl.get(id);
    }


    /**
     * 增加一条记录
     */
    public void addDtoToDB() throws Exception {
        if (dto.getId() > 0) {
            bl.insert(dto);
        } else {
            bl.insertAfterAssigningId(dto);
        }
    }


    /**
     * 修改一条记录
     */
    public void editDtoToDB() throws Exception {
        int affectedRowsCount = bl.update(dto);
        if (affectedRowsCount == 0) {
            throw new Exception("更新记录数为0");
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public BaseDTO getDto() {
        return dto;
    }

    public void setDto(BaseDTO dto) {
        this.dto = dto;
    }

    public String getDowhat() {
        return dowhat;
    }


    public void setDowhat(String dowhat) {
        this.dowhat = dowhat;
    }


    public String getDowhatInList() {
        return dowhatInList;
    }


    public void setDowhatInList(String dowhatInList) {
        this.dowhatInList = dowhatInList;
    }

    public String getSaveTag() {
        return saveTag;
    }

    public void setSaveTag(String saveTag) {
        this.saveTag = saveTag;
    }

    public String getModuleName() {
        String actionName = getActionName();
        return actionName.substring(0, actionName.length() - 6);
    }


}