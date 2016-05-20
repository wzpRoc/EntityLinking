package org.ailab.tem.wim.menu;

import org.ailab.tem.wim.menu.Menu;
import org.ailab.tem.wim.menu.MenuBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：菜单业务逻辑
 */


public class MenuDetailAction extends BaseDetailAction {
    public MenuDetailAction() {
        dto = new Menu();
        bl = new MenuBL();
    }
}
