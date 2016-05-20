<%@ page import="java.util.List" %>
<%@ page import="org.ailab.entityLinking_old.wim.pageAbst.PageAbst" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/pageAbst/pageAbst.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/pageAbst/pageAbst.js"></script>

<%
    PageAbst pageAbst = (PageAbst) request.getAttribute("dto");
%>

<html>
<head>
    <title>维基页面摘要详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="pageAbstDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                维基页面摘要详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='pageAbstList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
                <tr>
					<td class="label">page_id:</td>
					<td class="value"><s:textfield name="dto.page_id"/></td>
				</tr>
				<tr>
					<td class="label">page_title:</td>
					<td class="value"><s:textfield name="dto.page_title"/></td>
				</tr>
				<tr>
					<td class="label">page_abst:</td>
					<td class="value"><s:textfield name="dto.page_abst"/></td>
				</tr>
				<tr>
					<td class="label">page_abst_len:</td>
					<td class="value"><s:textfield name="dto.page_abst_len"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
