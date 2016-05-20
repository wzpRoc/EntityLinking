package org.ailab.tem.wim.menu;

import org.ailab.wimfra.core.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：菜单
 */
public class Menu extends BaseDTO {
	// ID
	protected int id;

	// 上级ID
	protected int parentId;

	// 名称
	protected String name;

	// 链接
	protected String link;

	// 模块
	protected String moduleName;

	// 方法名
	protected String dowhat;

	// 级别
	protected int level;

	// 备注
	protected String description;

    protected int siblingSeq;

    protected List<Menu> children;

    // 用户组是否有此菜单的权限
    private boolean havePermission;

    /**
	 * 得到ID
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置ID
	 * @param id ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 得到上级ID
	 * @return 上级ID
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * 设置上级ID
	 * @param parentId 上级ID
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * 得到名称
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 得到链接
	 * @return 链接
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 设置链接
	 * @param link 链接
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 得到模块
	 * @return 模块
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * 设置模块
	 * @param moduleName 模块
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * 得到方法名
	 * @return 方法名
	 */
	public String getDowhat() {
		return dowhat;
	}

	/**
	 * 设置方法名
	 * @param dowhat 方法名
	 */
	public void setDowhat(String dowhat) {
		this.dowhat = dowhat;
	}

	/**
	 * 得到级别
	 * @return 级别
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 设置级别
	 * @param level 级别
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 得到备注
	 * @return 备注
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置备注
	 * @param description 备注
	 */
	public void setDescription(String description) {
		this.description = description;
	}

    public int getSiblingSeq() {
        return siblingSeq;
    }

    public void setSiblingSeq(int siblingSeq) {
        this.siblingSeq = siblingSeq;
    }

    public void addChild(Menu menu) {
        if(children == null) {
            children = new ArrayList<Menu>();
        }
        for(int i=0; i<children.size(); i++) {
            if(children.get(i).getSiblingSeq() > menu.getSiblingSeq()) {
                children.add(i, menu);
                return;
            }
        }
        children.add(menu);
    }

    public String toString() {
        return name;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public boolean isHavePermission() {
        return havePermission;
    }

    public void setHavePermission(boolean havePermission) {
        this.havePermission = havePermission;
    }
}
