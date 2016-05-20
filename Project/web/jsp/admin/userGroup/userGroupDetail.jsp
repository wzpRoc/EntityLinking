<%@ page import="org.ailab.tem.wim.userGroup.UserGroup" %>
<%@ page import="org.ailab.tem.wim.user.User" %>
<%@ page import="org.ailab.tem.wim.menu.Menu" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp" %>


<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/userGroup/userGroup.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="../jsp/admin/userGroup/userGroup.js"></script>

<%
    UserGroup userGroup = (UserGroup) request.getAttribute("dto");
    List<User> userList = (List<User>) request.getAttribute("allUserList");
    Menu rootMenuOfAllMenu = (Menu) request.getAttribute("rootMenuOfAllMenu");
//    String dowhat =  request.getParameter("dowhat");    todo 原因
%>
<html>
<head>
    <title>用户组详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp" %>
    <%@include file="../left.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="post" action="userGroupDetail">
            <input type="hidden" name="saveTag" value="1"/>
            <s:hidden name="dowhat" id="dowhat"/>
            <s:hidden name="dto.id" id="userGroupId"/>

            <div id="titleArea">
                <h1>
                    用户组详情
                </h1>
            </div>

            <%--功能按钮区--%>
            <div id="topArea">
                &nbsp;
                <div id="buttonArea">
                    <button type="button" onclick="window.location='userGroupList'">返回</button>
                </div>
            </div>

            <div id="userGroupDetail">
                <div class="subTitle" onclick="toggleHideData(this)">用户组详情</div>
                <%--数据表格区--%>
                <div id="dataArea" class="subData">
                    <table id="dataTable">
                        <tr>
                            <td class="label">名称:</td>
                            <td class="value"><s:textfield name="dto.name"/></td>
                        </tr>
                        <tr>
                            <td class="label">用户数:</td>
                            <td class="value"><s:textfield name="dto.userCount" readonly="true"
                                                           cssClass="readOnly" id="userCount"/></td>
                        </tr>
                        <tr>
                            <td class="label">描述:</td>
                            <td class="value"><s:textarea cssStyle="width:200px;" name="dto.description"/></td>
                        </tr>
                    </table>
                    <div style="text-align: center">
                        <button type="submit">保存</button>
                    </div>
                </div>
            </div>



            <div id="userGroupUserInfo">
                <div class="subTitle" onclick="toggleHideData(this)">用户组成员</div>
                <div class="subData">
                    <ul class="Clearfix" id="userList">
                        <% if (userList != null) {
                            for (User user : userList) { %>
                        <li>
                            <input type="checkbox" id="<%=user.getId()%>" <%if(user.isInGroup()){%>checked="true" <%}%>>
                            <%=user.getUsername()%>(<%=user.getRealName()%>)
                        </li>
                        <%
                                }
                            }
                        %>
                    </ul>
                    <br>

                    <div style="text-align: center">
                        <button onclick="updateUserGroupUser(); return false;">保存</button>
                    </div>
                </div>
            </div>
            <div id="userGroupMenuInfo">
                <div class="subTitle" onclick="toggleHideData(this)">用户组权限</div>

                <div class="subData ">
                    <ul>
                        <% if (rootMenuOfAllMenu != null) {
                            List<Menu> firstLevelMenuList = rootMenuOfAllMenu.getChildren();
                            for (Menu firstLevelMenu : firstLevelMenuList) {
                        %>
                        <li class="firstLevel">
                            <div class="firstLevelName">
                                <input type="checkbox" id="<%=firstLevelMenu.getId()%>" onclick="firstMenuClick(this)"
                                       <%if(firstLevelMenu.isHavePermission()){%>checked="true" <%}%>>
                                <%=firstLevelMenu.getName()%>
                            </div>
                            <ul>
                                <% List<Menu> secondLevelMenuList = firstLevelMenu.getChildren();
                                    for (Menu secondLevelMenu : secondLevelMenuList) {
                                %>
                                <li class="secondLevel">
                                    <div class="secondLevelName">
                                        <input type="checkbox" id="<%=secondLevelMenu.getId()%>"
                                               onclick="secondMenuClick(this)"
                                               <%if(secondLevelMenu.isHavePermission()){%>checked="true" <%}%>>
                                        <%=secondLevelMenu.getName()%>
                                    </div>
                                    <ul>
                                        <%
                                            List<Menu> thirdLevelMenuList = secondLevelMenu.getChildren();
                                            for (Menu thirdLevelMenu : thirdLevelMenuList) {
                                        %>
                                        <li class="thirdLevel">
                                            <div class="thirdLevelName">
                                                <input type="checkbox" id="<%=thirdLevelMenu.getId()%>"
                                                       onclick="thirdMenuClick(this);"
                                                       <%if(thirdLevelMenu.isHavePermission()){%>checked="true" <%}%>>
                                                <%=thirdLevelMenu.getName()%>
                                            </div>
                                        </li>
                                        <%
                                            }
                                        %>
                                    </ul>
                                </li>
                                <%
                                    }
                                %>
                            </ul>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                    <%
                        }%>
                    </ul>
                    <div style="text-align: center">
                        <button onclick="updateUserGroupMenu(); return false;">保存</button>
                    </div>
                </div>
            </div>


        </form>
    </div>
</div>
</body>
</html>

<%@ include file="../../msg.jsp" %>
