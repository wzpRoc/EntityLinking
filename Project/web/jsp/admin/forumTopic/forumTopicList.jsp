<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.forumTopic.ForumTopic" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>


<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="jsp/forumTopic/forumTopic.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/forumTopic/forumTopic.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>论坛话题列表</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="forumTopicList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    论坛话题列表
                </h1>
            </div>

            <%--查询条件--%>
            <%--<div id="conditionArea">--%>
                <%--<div class="label">--%>
                    <%--话题类别：--%>
                <%--</div>--%>
                <%--<div class="value">--%>
                    <%--<w:select name="condition.categoryId" dataId="TopicCategory" addSelectAll="true" onchange="document.getElementById('mainForm').submit()"/>--%>
                <%--</div>--%>
                <%--<div class="label">--%>
                    <%--审核结果：--%>
                <%--</div>--%>
                <%--<div class="value">--%>
                    <%--<w:select name="condition.isVisable" dataId="ForumCheckType" addSelectAll="true" onchange="document.getElementById('mainForm').submit()"/>--%>
                <%--</div>--%>

                <%--<div class="submitButton">--%>
                    <%--<button type="submit">查询</button>--%>
                    <%--<button type="button" onclick="window.location='forumTopicDetail?dowhat=add'">新增</button>--%>
                    <%--<button type="button" onclick="deleteFromList()">删除</button>--%>
                <%--</div>--%>
            <%--</div>--%>

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
                            类别
                        </th>
                        <th width="10%">
                            发表用户
                        </th>
                        <th width="20%">
                            标题
                        </th>
                        <th width="10%">
                            总回复数
                        </th>
                        <th width="10%">
                            重要性
                        </th>
                        <th width="10%">
                            审核结果
                        </th>

                    <tr>

                        <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        ForumTopic forumTopic = (ForumTopic) dto;
                        int id = forumTopic.getId();

                %>
                    <tr class="tbody">
                        <td>
                            <input type="checkbox" value="<%=forumTopic.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <td>
                            <%=forumTopic.getCategoryName()%>
                        </td>
                        <td>
                            <%=forumTopic.getUserName()%>
                        </td>
                        <td>
                            <a href="forumTopicDetail?name=<%=id%>"><%=forumTopic.getTitle()%></a>
                        </td>
                        <td>
                            <%=forumTopic.getTotalReplyNum()%>
                        </td>
                        <td>
                            <%=forumTopic.getImportance()%>
                        </td>
                        <td>
                            <%=forumTopic.getCheckResult()%>
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

    <%@include file="../bottom.jsp"%>
</div>

</body>
</html>
