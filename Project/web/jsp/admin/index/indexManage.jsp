<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="../jsp/admin/index/indexManage.js"></script>

<%
%>

<html>
<head>
    <title>index manage</title>
</head>
<body>


<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form action="indexManage">
            <input type="button" style="margin-left: 10px;" onclick="rebuildIndex()" value="重建歌曲索引"/>
            <input type="button" style="margin-left: 10px;" onclick="rebuildAlbumIndex()" value="重建专辑索引"/>
            <input type="button" style="margin-left: 10px;" onclick="rebuildArtistIndex()" value="重建艺人索引"/>
        </form>
    </div>
    <%@include file="../bottom.jsp"%>
</div>

<%@include file="../../popMessage.jsp"%>

</body>
</html>
