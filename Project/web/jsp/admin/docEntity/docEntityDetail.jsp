<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.docEntity.DocEntity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/docEntity/docEntity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/docEntity/docEntity.js"></script>

<%
    DocEntity docEntity = (DocEntity) request.getAttribute("dto");
%>

<html>
<head>
    <title>文档实体详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="docEntityDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                文档实体详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='docEntityList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
                <tr>
					<td class="label">文档ID:</td>
					<td class="value"><s:textfield name="dto.docId"/></td>
				</tr>
				<tr>
					<td class="label">日期:</td>
					<td class="value"><s:textfield name="dto.pubdate"/></td>
				</tr>
				<tr>
					<td class="label">实体类型:</td>
					<td class="value"><s:textfield name="dto.entityType"/></td>
				</tr>
				<tr>
					<td class="label">实体ID:</td>
					<td class="value"><s:textfield name="dto.entityId"/></td>
				</tr>
				<tr>
					<td class="label">实体名称:</td>
					<td class="value"><s:textfield name="dto.entityTitle"/></td>
				</tr>
				<tr>
					<td class="label">实体值:</td>
					<td class="value"><s:textfield name="dto.entityTitle"/></td>
				</tr>
				<tr>
					<td class="label">字段:</td>
					<td class="value"><s:textfield name="dto.field"/></td>
				</tr>
				<tr>
					<td class="label">开始位置:</td>
					<td class="value"><s:textfield name="dto.startIdx"/></td>
				</tr>
				<tr>
					<td class="label">结束位置:</td>
					<td class="value"><s:textfield name="dto.endIdx"/></td>
				</tr>
				<tr>
					<td class="label">国家ID:</td>
					<td class="value"><s:textfield name="dto.countryId"/></td>
				</tr>
				<tr>
					<td class="label">更新时间:</td>
					<td class="value"><s:textfield name="dto.updateTime"/></td>
				</tr>
				<tr>
					<td class="label">更新者:</td>
					<td class="value"><s:textfield name="dto.updaterName"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
