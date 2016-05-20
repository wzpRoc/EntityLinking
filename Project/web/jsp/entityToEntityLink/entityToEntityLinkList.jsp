<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLink" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/entityToEntityLink/entityToEntityLink.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/entityToEntityLink/entityToEntityLink.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>entityToEntityLinkList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="entityToEntityLinkList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                实体到实体的链接列表
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
            <div class="submitButton">
                <button type="submit">查询</button>
                <button type="button" onclick="window.location='entityToEntityLinkDetail?dowhat=add'">新增</button>
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
                        源实体ID
                    </th>
                    <th width="10%">
                        目的实体ID
                    </th>
                    <th width="10%">
                        目的实体维基标题
                    </th>
                    <th width="10%">
                        计数
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        EntityToEntityLink entityToEntityLink = (EntityToEntityLink) dto;
                        int id = entityToEntityLink.getnull();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=entityToEntityLink.getnull()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="entityToEntityLinkDetail?dowhat=edit&id=<%=id%>">
                            entityToEntityLink<%=entityToEntityLink.getnull()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=entityToEntityLink.getFromEntityId()%>
                    </td>
                    <td>
                        <%=entityToEntityLink.getToEntityId()%>
                    </td>
                    <td>
                        <%=entityToEntityLink.getToWikiTitle()%>
                    </td>
                    <td>
                        <%=entityToEntityLink.getCnt()%>
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

</body>
</html>
