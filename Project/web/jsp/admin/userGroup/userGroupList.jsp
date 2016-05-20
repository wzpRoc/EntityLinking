<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.userGroup.UserGroup" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../adminJS.jsp" %>


<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/userGroup/userGroup.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/userGroup/userGroup.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>userGroupList</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp" %>
    <%@include file="../left.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="userGroupList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    用户组列表
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    名称：
                </div>
                <div class="value">
                    <s:textfield name="condition.name" style="width:80px"/>
                </div>
                <div class="submitButton">
                    <button type="submit">查询</button>
                    <button type="button" onclick="window.location='userGroupDetail?dowhat=add'">新增</button>
                    <button type="button" onclick="deleteFromList()">删除</button>
                </div>
            </div>

            <%--查询结果--%>
            <div id="dataArea">
                <table id="dataTable" cellSpacing="0">
                    <%--表头--%>
                    <tr class="thead">
                        <th class="td_checkbox" style="text-align: center">
                            <input type="checkbox" id="id_head" name="checkbox_id_head"
                                   onclick="on_checkbox_id_head_clicked(this)"/>
                        </th>

                        <th width="10%">
                            名称
                        </th>
                        <th width="10%">
                            用户数
                        </th>
                        <th width="10%">
                            描述
                        </th>

                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        UserGroup userGroup = (UserGroup) dto;
                        int id = userGroup.getId();

                %>
                    <tr class="tbody">
                        <td style="text-align: center">
                            <input type="checkbox" value="<%=userGroup.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <td>
                            <a href="userGroupDetail?dowhat=edit&id=<%=id%>">
                                <%=userGroup.getName()%>
                            </a>
                        </td>
                        <td>
                            <%=userGroup.getUserCount()%>
                        </td>
                        <td>
                            <%=userGroup.getDescription()%>
                        </td>

                    </tr>
                    <%
                        }
                    %>
                </table>
                <br/>
            </div>
        </form>
    </div>
</div>
</body>
</html>
