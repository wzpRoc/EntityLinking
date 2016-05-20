<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.doc.Doc" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/doc/doc.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="../jsp/admin/doc/doc.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>docList</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp" %>
    <%@include file="../left.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="docList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    Document List
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    标题：
                </div>
                <div class="value">
                    <s:textfield name="condition.title" style="width:80px"/>
                </div>
                <div class="label">
                    状态：
                </div>
                <div class="value">
                    <w:select dataId="annoState" name="condition.annoState" addSelectAll="true"/>
                </div>
                <div class="label">
                    标注人：
                </div>
                <div class="value">
                    <w:select dataId="user" name="condition.updaterId" addSelectAll="true"/>
                </div>
                <div class="submitButton">
                    <button type="submit">查询</button>
                    <button type="button" onclick="window.location='docDetail?dowhat=add'">新增</button>
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

                        <th width="5%">
                            ID
                        </th>
                        <th width="40%">
                            Title
                        </th>
                        <th width="8%">
                            Publish Date
                        </th>
                        <th width="15%">
                            Update Time
                        </th>
                        <th width="5%">
                            State
                        </th>
                        <th width="5%">
                            Annotator
                        </th>
                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        Doc doc = (Doc) dto;
                        int id = doc.getId();

                %>
                    <tr class="tbody">
                        <td style="text-align: center">
                            <input type="checkbox" value="<%=doc.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <td>
                            <%=doc.getId()%>
                        </td>
                        <td>
                            <a href="docAnno?dowhat=edit&id=<%=id%>" target="_blank">
                                <%=doc.getTitle()%>
                            </a>
                        </td>
                        <td>
                            <%=doc.getPubdate()%>
                        </td>
                        <td>
                            <%=doc.getUpdateShortTime()%>
                        </td>
                        <td>
                            <%=doc.getAnnoStateAbbr()%>
                        </td>
                        <td>
                            <%=StringUtil.replaceNullWithBlank(doc.getUpdaterName())%>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
                <br/>
            </div>
            <%@include file="../../pageNavigation.jsp" %>
        </form>
    </div>
    <%@include file="../bottom.jsp" %>
</div>
</body>
</html>
