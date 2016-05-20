<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.wikiFact.WikiFact" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/wikiFact/wikiFact.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/wikiFact/wikiFact.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>wikiFactList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="wikiFactList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                维基百科事实列表
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
                <button type="button" onclick="window.location='wikiFactDetail?dowhat=add'">新增</button>
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
                        entityId
                    </th>
                    <th width="10%">
                        predicateId
                    </th>
                    <th width="10%">
                        objectId
                    </th>
                    <th width="10%">
                        entityTitle
                    </th>
                    <th width="10%">
                        predicateName
                    </th>
                    <th width="10%">
                        objectName
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        WikiFact wikiFact = (WikiFact) dto;
                        int id = wikiFact.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=wikiFact.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="wikiFactDetail?dowhat=edit&id=<%=id%>">
                            wikiFact<%=wikiFact.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=wikiFact.getId()%>
                    </td>
                    <td>
                        <%=wikiFact.getEntityId()%>
                    </td>
                    <td>
                        <%=wikiFact.getPredicateId()%>
                    </td>
                    <td>
                        <%=wikiFact.getObjectId()%>
                    </td>
                    <td>
                        <%=wikiFact.getEntityTitle()%>
                    </td>
                    <td>
                        <%=wikiFact.getPredicateName()%>
                    </td>
                    <td>
                        <%=wikiFact.getObjectName()%>
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
