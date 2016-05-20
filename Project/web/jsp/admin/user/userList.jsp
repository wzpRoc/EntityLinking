<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.user.User" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/user/user.css" rel="stylesheet" type="text/css"/>
<script language="javascript" src="../jsp/admin/user/user.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>userList</title>
</head>
<body>
<div id="fullContent">
<%@include file="../header.jsp"%>
<%@include file="../left.jsp"%>
<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="userList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                用户列表
            </h1>
        </div>

        <%--查询条件--%>
        <div id="conditionArea">
            <div class="label">
                姓名：
            </div>
            <div class="value">
                <s:textfield name="condition.name" style="width:80px"/>
            </div>
            <div class="label">
                ID：
            </div>
            <div class="value">
                <s:textfield name="condition.id" style="width:80px"/>
            </div>
            <div class="submitButton">
                <button type="submit">查询</button>
                <button type="button" onclick="window.location='userDetail?dowhat=add'">新增</button>
                <button type="button" onclick="deleteFromList()">删除</button>
            </div>
        </div>

        <%--查询结果--%>
        <div id="dataArea">
            <table id="dataTable" cellSpacing="0">
                <%--表头--%>
                <tr class="thead">
                    <th class="td_checkbox">
                        <input type="checkbox" id="id_head" name="checkbox_id_head"
                               onclick="on_checkbox_id_head_clicked(this)"/>
                    </th>

                    <th width="10%">
                        ID
                    </th>
                    <th width="10%">
                        登录用户名
                    </th>
                    <th width="10%">
                        昵称
                    </th>
                    <th width="10%">
                        角色
                    </th>
                    <th width="10%">
                        客户类别
                    </th>
                    <th width="10%">
                        积分余额
                    </th>
                    <th width="10%">
                        等级
                    </th>
                    <th width="10%">
                        最近登录
                    </th>
                    <th width="10%">
                        状态
                    </th>


                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        User user = (User) dto;
                        int id = user.getId();

                %>
                <tr class="tbody">
                    <td>
                        <input type="checkbox" value="<%=user.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="userDetail?dowhat=edit&id=<%=id%>">
                            user<%=user.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=user.getId()%>
                    </td>
                    <td>
                        <a href="userDetail?name=<%=user.getId()%>">
                            <%=user.getUsername()%>
                        </a>
                    </td>
                    <td>
                        <%=user.getNicName()%>
                    </td>
                    <td>
                        <%=user.getRoleStr()%>
                    </td>
                    <td>
                        <%=user.getCustomerTypeName()%>
                    </td>
                    <td>
                        <%=user.getPoints()%>
                    </td>
                    <td>
                        <%=user.getGradeStr()%>
                    </td>
                    <td>
                        <%=user.getLastLoginTime()%>
                    </td>
                    <td>
                        <%=user.getStateStr()%>
                    </td>

                </tr>
                 <%
                     }
                 %>
            </table>
            <br/>
        </div>
        <%@include file="../../pageNavigation.jsp"%>
    </form>
</div>
<%@include file="../bottom.jsp"%>
</div>
</body>
</html>
