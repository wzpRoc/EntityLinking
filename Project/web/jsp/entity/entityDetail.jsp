<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.entity.Entity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/entity/entity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/entity/entity.js"></script>

<%
    Entity entity = (Entity) request.getAttribute("dto");
%>

<html>
<head>
    <title>实体详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="entityDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                实体详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='entityList'">返回</button>
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
					<td class="label">维基百科页面ID:</td>
					<td class="value"><s:textfield name="dto.wikiPageId"/></td>
				</tr>
				<tr>
					<td class="label">维基百科标题:</td>
					<td class="value"><s:textfield name="dto.wikiTitle"/></td>
				</tr>
				<tr>
					<td class="label">摘要:</td>
					<td class="value"><s:textfield name="dto.abst"/></td>
				</tr>
				<tr>
					<td class="label">出度:</td>
					<td class="value"><s:textfield name="dto.outlinkCount"/></td>
				</tr>
				<tr>
					<td class="label">入度:</td>
					<td class="value"><s:textfield name="dto.inlinkCount"/></td>
				</tr>
				<tr>
					<td class="label">流行度:</td>
					<td class="value"><s:textfield name="dto.popularity"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
