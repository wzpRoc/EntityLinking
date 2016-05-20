<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.mention.Mention" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/mention/mention.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/mention/mention.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>mentionList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="mentionList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                指称列表
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
                <button type="button" onclick="window.location='mentionDetail?dowhat=add'">新增</button>
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
                        文档内部序号
                    </th>
                    <th width="10%">
                        开始位置
                    </th>
                    <th width="10%">
                        结束位置
                    </th>
                    <th width="10%">
                        字符串
                    </th>
                    <th width="10%">
                        实体ID
                    </th>
                    <th width="10%">
                        维基标题
                    </th>
                    <th width="10%">
                        初始权重
                    </th>
                    <th width="10%">
                        tf
                    </th>
                    <th width="10%">
                        idf
                    </th>
                    <th width="10%">
                        tfidf
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        Mention mention = (Mention) dto;
                        int id = mention.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=mention.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="mentionDetail?dowhat=edit&id=<%=id%>">
                            mention<%=mention.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=mention.getId()%>
                    </td>
                    <td>
                        <%=mention.getDocId()%>
                    </td>
                    <td>
                        <%=mention.getSeq()%>
                    </td>
                    <td>
                        <%=mention.getStartIdx()%>
                    </td>
                    <td>
                        <%=mention.getEndIdx()%>
                    </td>
                    <td>
                        <%=mention.getName()%>
                    </td>
                    <td>
                        <%=mention.getEntityId()%>
                    </td>
                    <td>
                        <%=mention.getWikiTitle()%>
                    </td>
                    <td>
                        <%=mention.getInitialWeightInDoc()%>
                    </td>
                    <td>
                        <%=mention.getTf()%>
                    </td>
                    <td>
                        <%=mention.getIdf()%>
                    </td>
                    <td>
                        <%=mention.getTfidf()%>
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
