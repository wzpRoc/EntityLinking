<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.candidateEntity.CandidateEntity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/candidateEntity/candidateEntity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/candidateEntity/candidateEntity.js"></script>

<%
    CandidateEntity candidateEntity = (CandidateEntity) request.getAttribute("dto");
%>

<html>
<head>
    <title>指称的候选实体详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="candidateEntityDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                指称的候选实体详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='candidateEntityList'">返回</button>
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
					<td class="label">文档ID:</td>
					<td class="value"><s:textfield name="dto.docId"/></td>
				</tr>
				<tr>
					<td class="label">文档内部指称序号:</td>
					<td class="value"><s:textfield name="dto.seqInDoc"/></td>
				</tr>
				<tr>
					<td class="label">指称ID:</td>
					<td class="value"><s:textfield name="dto.mentionId"/></td>
				</tr>
				<tr>
					<td class="label">实体名称:</td>
					<td class="value"><s:textfield name="dto.mentionName"/></td>
				</tr>
				<tr>
					<td class="label">候选实体ID:</td>
					<td class="value"><s:textfield name="dto.entityId"/></td>
				</tr>
				<tr>
					<td class="label">候选实体维基标题:</td>
					<td class="value"><s:textfield name="dto.wikiTitle"/></td>
				</tr>
				<tr>
					<td class="label">指称到实体的概率:</td>
					<td class="value"><s:textfield name="dto.probOfMentionToEntity"/></td>
				</tr>
				<tr>
					<td class="label">指称名字到实体的概率:</td>
					<td class="value"><s:textfield name="dto.probOfNameToEntity"/></td>
				</tr>
				<tr>
					<td class="label">指称到实体的概率排序:</td>
					<td class="value"><s:textfield name="dto.rankByProbOfNameToEntity"/></td>
				</tr>
				<tr>
					<td class="label">指称名字到实体的概率排序:</td>
					<td class="value"><s:textfield name="dto.rankByProbOfMentionToEntity"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
