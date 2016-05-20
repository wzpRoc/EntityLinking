package org.ailab.tem.wim.menu;

import java.util.List;

/**
 * Author: ZhangQiang
 * Date: 14-2-28
 * Time: 下午6:54
 * Desc: 用户菜单
 */
public class UserMenu {
    private List<Menu> firstLevelMenuList;
    private List<Menu> secondLevelMenuList;
    private List<Menu> thirdLevelMenuList;

    public UserMenu() {
    }

    public UserMenu(List<Menu> firstLevelMenuList, List<Menu> secondLevelMenuList, List<Menu> thirdLevelMenuList) {
        this.firstLevelMenuList = firstLevelMenuList;
        this.secondLevelMenuList = secondLevelMenuList;
        this.thirdLevelMenuList = thirdLevelMenuList;
    }

    public List<Menu> getFirstLevelMenuList() {
        return firstLevelMenuList;
    }

    public void setFirstLevelMenuList(List<Menu> firstLevelMenuList) {
        this.firstLevelMenuList = firstLevelMenuList;
    }

    public List<Menu> getSecondLevelMenuList() {
        return secondLevelMenuList;
    }

    public void setSecondLevelMenuList(List<Menu> secondLevelMenuList) {
        this.secondLevelMenuList = secondLevelMenuList;
    }

    public List<Menu> getThirdLevelMenuList() {
        return thirdLevelMenuList;
    }

    public void setThirdLevelMenuList(List<Menu> thirdLevelMenuList) {
        this.thirdLevelMenuList = thirdLevelMenuList;
    }
}
