<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/config/config.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="../jsp/admin/config/config.js"></script>

<%
    String pageTitle="配置列表";
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title><%=pageTitle%></title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>

    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="configList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    <%=pageTitle%>
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    模块：
                </div>
                <div class="value">
                    <s:textfield name="condition.module" style="width:80px"/>
                </div>
                <div class="submitButton">
                    <button type="submit">查询</button>
                    <button type="button" onclick="window.location='configDetail?dowhat=add'">新增</button>
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
                            模块
                        </th>
                        <th width="30%">
                            名称
                        </th>
                        <th width="30%">
                            值
                        </th>
                        <th width="30%">
                            说明
                        </th>
                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        Config cfg = (Config) dto;
                        int id = cfg.getId();

                %>
                    <tr class="tbody">
                        <td>
                            <input type="checkbox" value="<%=cfg.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <td>
                            <%=cfg.getModule()%>
                        </td>
                        <td>
                            <a href="configDetail?name=<%=cfg.getId()%>">
                                <%=cfg.getName()%>
                            </a>
                        </td>
                        <td>
                            <%=cfg.getValue()%>
                        </td>
                        <td>
                            <%=cfg.getDescription()%>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
                <br/>
            </div>
        </form>
        <%@include file="../../pageNavigation.jsp"%>
    </div>

    <%@include file="../bottom.jsp"%>
</div>

</body>
</html>

<%@ include file="../../msg.jsp" %>
