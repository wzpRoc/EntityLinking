<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLink" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/entityToEntityLink/entityToEntityLink.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/entityToEntityLink/entityToEntityLink.js"></script>

<%
    EntityToEntityLink entityToEntityLink = (EntityToEntityLink) request.getAttribute("dto");
%>

<html>
<head>
    <title>实体到实体的链接详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="entityToEntityLinkDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                实体到实体的链接详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='entityToEntityLinkList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
                <tr>
					<td class="label">源实体ID:</td>
					<td class="value"><s:textfield name="dto.fromEntityId"/></td>
				</tr>
				<tr>
					<td class="label">目的实体ID:</td>
					<td class="value"><s:textfield name="dto.toEntityId"/></td>
				</tr>
				<tr>
					<td class="label">目的实体维基标题:</td>
					<td class="value"><s:textfield name="dto.toWikiTitle"/></td>
				</tr>
				<tr>
					<td class="label">计数:</td>
					<td class="value"><s:textfield name="dto.cnt"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
