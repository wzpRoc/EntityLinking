<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>

<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<link href="jsp/common/relatedLinks/relatedLinks.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/relatedLinks/relatedLinks.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>相关链接</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../leftBar.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="relatedLinksList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <div id="dataArea" class=" Clearfix" style="padding: 10px">
                <%
                    for(int i = 0;i < 12; i++) {
                %>
                <div class="relatedLink"></div>
                <%
                    }
                %>
            </div>

        </form>
    </div>
    <%@include file="../../footer.jsp" %>
</div>



</body>
</html>
