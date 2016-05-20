<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.relatedLinks.RelatedLinks" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/tcal.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/relatedLinks/relatedLinks.js"></script>

<%
    RelatedLinks relatedLinks = (RelatedLinks) request.getAttribute("dto");
%>

<html>
<head>
    <title>网站链接详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="post" action="relatedLinksDetail">
            <input type="hidden" name="saveTag" value="1"/>
            <s:hidden name="dowhat"/>
            <s:hidden name="dto.id"/>
            <s:hidden name="dto.categoryId"/>
            <s:hidden name="dto.categoryName"/>
            <s:hidden name="dto.pictureAddr"/>

            <div id="titleArea">
                <h1>
                    网站链接详情
                </h1>
            </div>

            <%--功能按钮区--%>
            <div id="topArea">
                &nbsp;
                <div id="buttonArea">
                    <button type="submit">保存</button>
                    <button type="button" onclick="window.location='relatedLinksList'">返回</button>
                </div>
            </div>

            <%--数据表格区--%>
            <div id="dataArea">
                <table id="dataTable">
                    <tr>
                        <td class="label">网站名称:</td>
                        <td class="value"><s:textfield name="dto.siteName"/></td>
                    </tr>
                    <tr>
                        <td class="label">网站链接:</td>
                        <td class="value"><s:textfield name="dto.siteUrl"/></td>
                    </tr>
                    <tr>
                        <td class="label">描述:</td>
                        <td class="value"><s:textfield name="dto.description"/></td>
                    </tr>

                </table>
            </div>
        </form>
    </div>
    <%@include file="../bottom.jsp"%>
</div>


</body>
</html>
<%@ include file="/jsp/msg.jsp" %>
