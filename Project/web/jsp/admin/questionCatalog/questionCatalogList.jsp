<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.questionCatalog.QuestionCatalog" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>


<%@include file="../adminJS.jsp" %>


<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/questionCatalog/questionCatalog.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="../jsp/admin/questionCatalog/questionCatalog.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List catalogList = listPage.getList();
%>

<html>
<head>
    <title>常见问题</title>
</head>
<body>


<div id="fullContent">

    <%@include file="../header.jsp" %>
    <%@include file="../left.jsp" %>

    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="questionCatalogList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>
            <s:hidden name="catalogId" id="catalogId"/>

            <div id="sidebar">
                <div id="help-menu">
                    <div class="mod-prescNo">
                        问题分类
                    </div>
                    <div class="mod-content-np">
                        <ul id="qMenu">
                            <%--表格内容--%>
                            <%
                                for (Object dto : catalogList) {
                                    QuestionCatalog questionCatalog = (QuestionCatalog) dto;
                            %>
                            <li class="firstMenu"
                                onclick="showQuestionSecondMenu('<%=questionCatalog.getId()%>', '<%=questionCatalog.getName()%>',this); return false;">
                                <a id="<%=questionCatalog.getId()%>"><%=questionCatalog.getName()%>
                                </a>
                                <span class="deleteMenu">
                                    <img onclick="deleteQuestionMenu(<%=questionCatalog.getId()%>, this); return false;"
                                         src="../img/delete.png">
                                </span>
                                <ul class="qMenu2">
                                </ul>
                            </li>
                            <%}%>

                            <li class="edit firstMenu" onclick="addQuestionMenu(this);"><a style="font-style:oblique">增加一级菜单</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <%--查询结果--%>
            <div id="dataArea">
                <div id="subNav">
                    <span id="questionMenuNav">问题分类</span>
                    <span id="secondQuestionMenuNav"></span>
                </div>
                <ul id="questions">
                </ul>
            </div>
        </form>
    </div>
</div>

</body>
</html>
