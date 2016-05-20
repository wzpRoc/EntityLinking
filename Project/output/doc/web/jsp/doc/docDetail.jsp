<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.doc.Doc" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/doc/doc.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/doc/doc.js"></script>

<%
    Doc doc = (Doc) request.getAttribute("dto");
%>

<html>
<head>
    <title>文档详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="docDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                文档详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='docList'">返回</button>
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
					<td class="label">标题:</td>
					<td class="value"><s:textfield name="dto.title"/></td>
				</tr>
				<tr>
					<td class="label">内容:</td>
					<td class="value"><s:textfield name="dto.content"/></td>
				</tr>
				<tr>
					<td class="label">token列表:</td>
					<td class="value"><s:textfield name="dto.tokens"/></td>
				</tr>
				<tr>
					<td class="label">URL:</td>
					<td class="value"><s:textfield name="dto.url"/></td>
				</tr>
				<tr>
					<td class="label">训练/测试标记:</td>
					<td class="value"><s:textfield name="dto.toeTag"/></td>
				</tr>
				<tr>
					<td class="label">发布日期:</td>
					<td class="value"><s:textfield name="dto.pubDate"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
