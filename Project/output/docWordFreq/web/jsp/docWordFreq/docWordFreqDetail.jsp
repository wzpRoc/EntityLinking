<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking.wim.docWordFreq.DocWordFreq" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/docWordFreq/docWordFreq.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/docWordFreq/docWordFreq.js"></script>

<%
    DocWordFreq docWordFreq = (DocWordFreq) request.getAttribute("dto");
%>

<html>
<head>
    <title>新闻数据中词出现的频次详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="docWordFreqDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                新闻数据中词出现的频次详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='docWordFreqList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
                <tr>
					<td class="label">词的名称:</td>
					<td class="value"><s:textfield name="dto.wordName"/></td>
				</tr>
				<tr>
					<td class="label">文档中出现的次数:</td>
					<td class="value"><s:textfield name="dto.freq"/></td>
				</tr>
				<tr>
					<td class="label">在语料库中的idf:</td>
					<td class="value"><s:textfield name="dto.idf"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
