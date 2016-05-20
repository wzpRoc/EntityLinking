package org.ailab.wimfra.webServer;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.ailab.common.config.ConfigBL;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.user.BaseUser;
import org.apache.log4j.Logger;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: lutingming
 * Date: 13-11-24
 * Time: 下午4:04
 * Desc: 访问次数控制拦截器
 */
public class AccessCountInterceptor extends AbstractInterceptor {
    private static Logger logger = Logger.getLogger(AccessCountInterceptor.class);
    private static List<DailyAccessCounter> accessCounterList;
    private static DailyAccessCounter dailyAccessCounterByIP;
    private static DailyAccessCounter dailyAccessCounterOfGeneralUser;
    private static DailyAccessCounter dailyAccessCounterOfAnnotator;
    private static DailyAccessCounter dailyAccessCounterOfAnnotatorAdministrator;
    private final String ACCESS_COUNT_EXCEEDED = "accessCountExceeded";

    static {
        dailyAccessCounterByIP = new DailyAccessCounter("IP", "IP", ConfigBL.getIntValue("security", "maxAccessCountByIP", 1000));
        dailyAccessCounterOfGeneralUser = new DailyAccessCounter("普通用户", "用户", ConfigBL.getIntValue("security", "maxAccessCountOfGeneralUser", 1000));
        dailyAccessCounterOfAnnotator = new DailyAccessCounter("标注用户", "用户", ConfigBL.getIntValue("security", "maxAccessCountOfAnnotator", 1000));
        dailyAccessCounterOfAnnotatorAdministrator = new DailyAccessCounter("标注管理员", "用户", ConfigBL.getIntValue("security", "maxAccessCountOfAnnotatorAdministrator", 1000));

        accessCounterList = new ArrayList<DailyAccessCounter>();
        accessCounterList.add(dailyAccessCounterByIP);
        accessCounterList.add(dailyAccessCounterOfGeneralUser);
        accessCounterList.add(dailyAccessCounterOfAnnotator);
        accessCounterList.add(dailyAccessCounterOfAnnotatorAdministrator);
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        //获取当前要执行的Action
        BaseAction objAction = (BaseAction)invocation.getAction();
        BaseUser user = (BaseUser) objAction.getUser();

        HttpServletRequest request = ServletActionContext.getRequest();

        // determine whether or not this is a heart-beat message
        if("/heartBeat".equals(request.getServletPath())) {
            // heart-beat message should not be included
            return invocation.invoke();
        }

        //获取IP地址
        String ip = request.getRemoteAddr();

        DailyAccessCounter counter;
        String key;
        if (user == null) {
            // 未登录用户，按照IP统计访问次数
            key = ip;
            counter = dailyAccessCounterByIP;
        } else {
            // 已登录用户，按照用户统计访问次数
            key = String.valueOf(user.getId());
/*
            if(user.getRoleEnum() == UserRole.GENERAL) {
                counter = dailyAccessCounterOfGeneralUser;
            } else if(user.getRoleEnum() == UserRole.ANNOTATOR) {
                counter = dailyAccessCounterOfAnnotator;
            } else if(user.getRoleEnum() == UserRole.ANNOTATOR_ADMINISTRATOR) {
                counter = dailyAccessCounterOfAnnotatorAdministrator;
            } else {
                counter = null;
            }
*/
            counter = null;
        }

        if(counter!=null) {
            int exceedance = counter.increase(key);
            if(exceedance<0) {
                // 未超
            } else {
                if(exceedance<10) {
                    if("/login".equals(request.getServletPath())) {
                        // 还有十次登录机会
                    } else {
                        return ACCESS_COUNT_EXCEEDED;
                    }
                } else {
                    // 没有访问任何界面的机会了
                    return ACCESS_COUNT_EXCEEDED;
                }
            }
        }
        return invocation.invoke();
    }

    public static List<DailyAccessCounter> getAccessCounterList() {
        return accessCounterList;
    }
}
