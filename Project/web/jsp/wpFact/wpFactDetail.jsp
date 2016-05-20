<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.wpFact.WPFact" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/wpFact/wpFact.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/wpFact/wpFact.js"></script>

<%
    WPFact wpFact = (WPFact) request.getAttribute("dto");
%>

<html>
<head>
    <title>维基百科事实详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="wpFactDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                维基百科事实详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='wpFactList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
                <tr>
					<td class="label">ID:</td>
					<td class="value"><s:textfield name="dto.id"/></td>
				</tr>
				<tr>
					<td class="label">entityId:</td>
					<td class="value"><s:textfield name="dto.entityId"/></td>
				</tr>
				<tr>
					<td class="label">predicateId:</td>
					<td class="value"><s:textfield name="dto.predicateId"/></td>
				</tr>
				<tr>
					<td class="label">objectId:</td>
					<td class="value"><s:textfield name="dto.objectId"/></td>
				</tr>
				<tr>
					<td class="label">entityTitle:</td>
					<td class="value"><s:textfield name="dto.entityTitle"/></td>
				</tr>
				<tr>
					<td class="label">predicateName:</td>
					<td class="value"><s:textfield name="dto.predicateName"/></td>
				</tr>
				<tr>
					<td class="label">objectName:</td>
					<td class="value"><s:textfield name="dto.objectName"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
