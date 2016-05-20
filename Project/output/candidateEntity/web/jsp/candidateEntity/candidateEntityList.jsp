<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.candidateEntity.CandidateEntity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/candidateEntity/candidateEntity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/candidateEntity/candidateEntity.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>candidateEntityList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="candidateEntityList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                指称的候选实体列表
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
                <button type="button" onclick="window.location='candidateEntityDetail?dowhat=add'">新增</button>
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
                        文档ID
                    </th>
                    <th width="10%">
                        文档内部指称序号
                    </th>
                    <th width="10%">
                        指称ID
                    </th>
                    <th width="10%">
                        实体名称
                    </th>
                    <th width="10%">
                        候选实体ID
                    </th>
                    <th width="10%">
                        候选实体维基标题
                    </th>
                    <th width="10%">
                        指称到实体的概率
                    </th>
                    <th width="10%">
                        指称名字到实体的概率
                    </th>
                    <th width="10%">
                        指称到实体的概率排序
                    </th>
                    <th width="10%">
                        指称名字到实体的概率排序
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        CandidateEntity candidateEntity = (CandidateEntity) dto;
                        int id = candidateEntity.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=candidateEntity.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="candidateEntityDetail?dowhat=edit&id=<%=id%>">
                            candidateEntity<%=candidateEntity.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=candidateEntity.getId()%>
                    </td>
                    <td>
                        <%=candidateEntity.getDocId()%>
                    </td>
                    <td>
                        <%=candidateEntity.getSeqInDoc()%>
                    </td>
                    <td>
                        <%=candidateEntity.getMentionId()%>
                    </td>
                    <td>
                        <%=candidateEntity.getMentionName()%>
                    </td>
                    <td>
                        <%=candidateEntity.getEntityId()%>
                    </td>
                    <td>
                        <%=candidateEntity.getWikiTitle()%>
                    </td>
                    <td>
                        <%=candidateEntity.getProbOfMentionToEntity()%>
                    </td>
                    <td>
                        <%=candidateEntity.getProbOfNameToEntity()%>
                    </td>
                    <td>
                        <%=candidateEntity.getRankByProbOfNameToEntity()%>
                    </td>
                    <td>
                        <%=candidateEntity.getRankByProbOfMentionToEntity()%>
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
