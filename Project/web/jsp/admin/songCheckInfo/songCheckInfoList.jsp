<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.songCheckInfo.SongCheckInfo" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/songCheckInfo/songCheckInfo.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/songCheckInfo/songCheckInfo.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>音乐审核列表</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="songCheckInfoList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    歌曲审核信息列表
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    歌曲名称：
                </div>
                <div class="value">
                    <s:textfield name="condition.name" style="width:80px"/>
                </div>
                <div class="label">
                    审核类型：
                </div>
                <div class="value">
                    <w:select name="condition.checkType" dataId="checkType" addSelectAll="true" onchange="document.getElementById('mainForm').submit()"/>
                </div>
                <div class="label">
                    分配人员：
                </div>
                <div class="value">
                    <w:select name="condition.assigner_id" dataId="annotationUser" addSelectAll="true" onchange="document.getElementById('mainForm').submit()"/>
                </div>
                <div class="label">
                    编辑人员：
                </div>
                <div class="value">
                    <w:select name="condition.editor_id" dataId="annotationUser" addSelectAll="true" onchange="document.getElementById('mainForm').submit()"/>
                </div>
                <div class="label">
                    当前状态：
                </div>
                <div class="value">
                    <w:select name="condition.current_status" dataId="checkStatus" addSelectAll="true" onchange="document.getElementById('mainForm').submit()"/>
                </div>
                <div class="submitButton">
                    <button type="submit">查询</button>
                    <button type="button" onclick="window.location='songCheckInfoDetail?dowhat=add'">新增</button>
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
                            歌曲名称
                        </th>
                        <th width="10%">
                            标注类型
                        </th>
                        <th width="10%">
                            分配者
                        </th>
                        <th width="10%">
                            编辑者
                        </th>
                        <th width="10%">
                            审核者
                        </th>
                        <th width="10%">
                            当前状态
                        </th>
                    <tr>

                        <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        SongCheckInfo songCheckInfo = (SongCheckInfo) dto;
                        int id = songCheckInfo.getId();

                %>
                    <tr class="tbody">
                        <td>
                            <input type="checkbox" value="<%=songCheckInfo.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <td>
                            <%=songCheckInfo.getSongName()%>
                        </td>
                        <td>
                            <%=songCheckInfo.getTypeName()%>
                        </td>
                        <td>
                            <%=songCheckInfo.getAssignerName()%>
                        </td>
                        <td>
                            <%=songCheckInfo.getEditorName()%>
                        </td>
                        <td>
                            <%=songCheckInfo.getCheckerName()%>
                        </td>
                        <td>
                            <%=songCheckInfo.getCurrentStatus()%>
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
