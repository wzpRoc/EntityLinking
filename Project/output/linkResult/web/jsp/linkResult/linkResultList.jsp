<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.linkResult.LinkResult" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/linkResult/linkResult.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/linkResult/linkResult.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>linkResultList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="linkResultList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                连接结果列表
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
                <button type="button" onclick="window.location='linkResultDetail?dowhat=add'">新增</button>
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
                        文档ID
                    </th>
                    <th width="10%">
                        日期
                    </th>
                    <th width="10%">
                        实体类型
                    </th>
                    <th width="10%">
                        实体ID
                    </th>
                    <th width="10%">
                        实体名称
                    </th>
                    <th width="10%">
                        实体值
                    </th>
                    <th width="10%">
                        开始位置
                    </th>
                    <th width="10%">
                        结束位置
                    </th>
                    <th width="10%">
                        实验ID
                    </th>
                    <th width="10%">
                        最大概率
                    </th>
                    <th width="10%">
                        最大概率实体ID
                    </th>
                    <th width="10%">
                        最大概率实体标题
                    </th>
                    <th width="10%">
                        次大概率
                    </th>
                    <th width="10%">
                        次大概率实体ID
                    </th>
                    <th width="10%">
                        次大概率实体标题
                    </th>
                    <th width="10%">
                        最大与次大概率之差
                    </th>
                    <th width="10%">
                        是否正确
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        LinkResult linkResult = (LinkResult) dto;
                        int id = linkResult.getnull();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=linkResult.getnull()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="linkResultDetail?dowhat=edit&id=<%=id%>">
                            linkResult<%=linkResult.getnull()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=linkResult.getDocId()%>
                    </td>
                    <td>
                        <%=linkResult.getDate()%>
                    </td>
                    <td>
                        <%=linkResult.getEntityType()%>
                    </td>
                    <td>
                        <%=linkResult.getEntityId()%>
                    </td>
                    <td>
                        <%=linkResult.getEntityName()%>
                    </td>
                    <td>
                        <%=linkResult.getEntityValue()%>
                    </td>
                    <td>
                        <%=linkResult.getStartPosition()%>
                    </td>
                    <td>
                        <%=linkResult.getEndPosition()%>
                    </td>
                    <td>
                        <%=linkResult.getExperimentId()%>
                    </td>
                    <td>
                        <%=linkResult.getProbability0()%>
                    </td>
                    <td>
                        <%=linkResult.getEntityId0()%>
                    </td>
                    <td>
                        <%=linkResult.getEntityTitle0()%>
                    </td>
                    <td>
                        <%=linkResult.getProbability1()%>
                    </td>
                    <td>
                        <%=linkResult.getEntityId1()%>
                    </td>
                    <td>
                        <%=linkResult.getEntityTitle1()%>
                    </td>
                    <td>
                        <%=linkResult.getProbabilityDiff()%>
                    </td>
                    <td>
                        <%=linkResult.getCorrect()%>
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
