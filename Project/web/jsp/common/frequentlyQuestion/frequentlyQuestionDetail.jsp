<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.frequentlyQuestion.FrequentlyQuestion" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/frequentlyQuestion/frequentlyQuestion.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/frequentlyQuestion/frequentlyQuestion.js"></script>

<%
    FrequentlyQuestion frequentlyQuestion = (FrequentlyQuestion) request.getAttribute("dto");
%>

<html>
<head>
    <title>常见问题详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="frequentlyQuestionDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                常见问题详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='frequentlyQuestionList'">返回</button>
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
					<td class="label">所属目录Id:</td>
					<td class="value"><s:textfield name="dto.catalogId"/></td>
				</tr>
				<tr>
					<td class="label">问题:</td>
					<td class="value"><s:textfield name="dto.question"/></td>
				</tr>
				<tr>
					<td class="label">答案:</td>
					<td class="value"><s:textfield name="dto.answer"/></td>
				</tr>
				<tr>
					<td class="label">编辑人:</td>
					<td class="value"><s:textfield name="dto.editorName"/></td>
				</tr>
				<tr>
					<td class="label">编辑时间:</td>
					<td class="value"><s:textfield name="dto.editTime"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
