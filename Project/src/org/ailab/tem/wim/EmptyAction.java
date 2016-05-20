package org.ailab.tem.wim;

import org.ailab.common.config.ConfigBL;
import org.ailab.common.config.ConfigListCondition;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.core.BaseListAction;
import org.apache.struts2.ServletActionContext;

import javax.servlet.Servlet;

/**
 * User: Lu Tingming
 * Date: 2012-06-17
 */
public class EmptyAction extends BaseAction {
    @Override
    public String myExecute() throws Exception {
        return SUCCESS;
    }
}