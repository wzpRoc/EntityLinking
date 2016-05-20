<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@include file="adminJS.jsp"%>
<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css">




<%
    String pageTitle = "管理界面";
%>

<html>
<head>
    <title><%=pageTitle%></title>
</head>
<body>

<div id="fullContent">
    <%@include file="header.jsp"%>
    <%@include file="left.jsp"%>

    <%--<div id="mainContent">--%>
        <%--<div id="titleArea">--%>
            <%--<h1>--%>
                <%--<%=pageTitle%>--%>
            <%--</h1>--%>
        <%--</div>--%>
    <%--</div>--%>

    <%@include file="bottom.jsp"%>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
