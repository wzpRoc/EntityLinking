<%@ page import="org.ailab.wimfra.frontend.MenuUtil" %>
<%@ page import="org.ailab.tem.wim.user.User" %>
<%@ page import="org.ailab.tem.wim.menu.Menu" %>
<%@ page import="org.ailab.tem.wim.user.UserBL" %>
<%@ page import="org.ailab.wimfra.core.BLMessage" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String moduleName = (String) request.getAttribute("moduleName");
    String dowhat = (String) request.getAttribute("dowhat");
    String dowhatInList = (String) request.getAttribute("dowhatInList");
%>
<script language="javascript">
    var moduleName = "<%=moduleName%>";
    var dowhat = "<%=dowhat%>";
    var dowhatInList = "<%=dowhatInList==null?"":dowhatInList%>";
</script>

<%
    if (needOuterPart!=null && needOuterPart) {
%>
<div id="content_left">
    <%
        User advancedUser = (User) request.getAttribute("user");
        // 用于刷新用户的目录
        BLMessage blMessage = (new UserBL()).reloadUserMenuByUserId(advancedUser.getId());
        advancedUser = (User) blMessage.getData();
        Menu rootMenu = advancedUser.getRootMenu();
        if (rootMenu != null) {
            List<Menu> firstLevelMenuList = rootMenu.getChildren();
            if (firstLevelMenuList != null) {
                for (Menu firstLevelMenu : firstLevelMenuList) {
    %>
    <div class="outBox">
        <h1><%=firstLevelMenu.getName()%>
        </h1>
        <%
            List<Menu> secondLevelMenuList = firstLevelMenu.getChildren();
            if (secondLevelMenuList != null) {
                for (Menu secondLevelMenu : secondLevelMenuList) {
        %>
        <div class="box">
            <div class="box_head"></div>
            <div class="box_middle">
                <h2 onclick="return toggleTitleList(this)"><%=secondLevelMenu.getName()%>
                </h2>

                <div class="cbside">
                    <ul>
                        <%
                            List<Menu> thirdLevelMenuList = secondLevelMenu.getChildren();
                            for (Menu thirdLevelMenu : thirdLevelMenuList) {
                        %>
                        <li>
                            <a <%=MenuUtil.isActive(request, thirdLevelMenu.getModuleName(), thirdLevelMenu.getDowhat()) ? "class='focus'" : ""%>
                                    href="<%=thirdLevelMenu.getLink()%>"><%=thirdLevelMenu.getName()%>
                            </a></li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
    <%--outBox--%>
    <%
                }
            }
        }
    %>
</div>
<%--left--%>
<%
    }
%>




