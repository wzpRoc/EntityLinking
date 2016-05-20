package org.ailab.wimfra.frontend;

import com.sun.deploy.net.HttpRequest;
import org.ailab.wimfra.util.StringUtil;
import org.htmlparser.parserapplications.filterbuilder.layouts.NullLayoutManager;

import javax.servlet.http.HttpServletRequest;

/**
 * User: ZhangQiang
 * Date: 13-12-11
 * Time: 下午7:58
 * Desc:
 */
public class MenuUtil {
    public static boolean isActive(HttpServletRequest request, String moduleNameOfMenu) {
        return isActive(request, moduleNameOfMenu, null);
    }

    public static boolean isActive(HttpServletRequest request, String moduleNameOfMenu, String dowhatOfMenu) {
        if(moduleNameOfMenu == null) {
            return false;
        }

        String moduleName = (String) request.getAttribute("moduleName");
        if(moduleName!=null && moduleName.startsWith("/")) {
            moduleName = moduleName.substring(1);
        }
        if(moduleNameOfMenu.equals(moduleName)) {
            if(dowhatOfMenu == null) {
                return true;
            }
            String dowhat = (String) request.getAttribute("dowhat");
            String dowhatInList = (String) request.getAttribute("dowhatInList");
            if(StringUtil.containsWord(dowhatOfMenu, dowhatInList, "|")
                    || StringUtil.containsWord(dowhatOfMenu, dowhat, "|")
                    ) {
                return true;
            }
        }

        return false;
    }

    public static boolean isActive(String moduleName, String dowhat, String dowhatInList, String moduleNameOfMenu, String dowhatOfMenu) {
        return moduleNameOfMenu.equals(moduleName)&&(dowhatOfMenu.equals(dowhatInList) || dowhatOfMenu.equals(dowhat));
    }

}
