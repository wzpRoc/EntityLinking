<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.commodity.Commodity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%--<%@include file="../../commonJS.jsp"%>--%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/commodity/commodity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/commodity/commodity.js"></script>

<%
    Commodity commodity = (Commodity) request.getAttribute("dto");
%>

<html>
<head>
    <title>商品详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="commodityDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                商品详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='commodityList'">返回</button>
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
					<td class="label">商品名称:</td>
					<td class="value"><s:textfield name="dto.name"/></td>
				</tr>
				<tr>
					<td class="label">原价:</td>
					<td class="value"><s:textfield name="dto.originalPrice"/></td>
				</tr>
				<tr>
					<td class="label">现价:</td>
					<td class="value"><s:textfield name="dto.currentPrice"/></td>
				</tr>
				<tr>
					<td class="label">库存量:</td>
					<td class="value"><s:textfield name="dto.inventory"/></td>
				</tr>
				<tr>
					<td class="label">运费:</td>
					<td class="value"><s:textfield name="dto.freight"/></td>
				</tr>
				<tr>
					<td class="label">商品类别Id:</td>
					<td class="value"><s:textfield name="dto.productCategoryId"/></td>
				</tr>
				<tr>
					<td class="label">商品一级类别:</td>
					<td class="value"><s:textfield name="dto.topCategoryId"/></td>
				</tr>
				<tr>
					<td class="label">抽象产品Id:</td>
					<td class="value"><s:textfield name="dto.abstractProductId"/></td>
				</tr>
				<tr>
					<td class="label">照片URL:</td>
					<td class="value"><s:textfield name="dto.photoUrl"/></td>
				</tr>
				<tr>
					<td class="label">描述信息:</td>
					<td class="value"><s:textfield name="dto.description"/></td>
				</tr>
				<tr>
					<td class="label">状态:</td>
					<td class="value"><s:textfield name="dto.state"/></td>
				</tr>
				<tr>
					<td class="label">创建者id:</td>
					<td class="value"><s:textfield name="dto.creatorUserId"/></td>
				</tr>
				<tr>
					<td class="label">创建时间:</td>
					<td class="value"><s:textfield name="dto.createTime"/></td>
				</tr>
				<tr>
					<td class="label">创建者id:</td>
					<td class="value"><s:textfield name="dto.editorUserId"/></td>
				</tr>
				<tr>
					<td class="label">创建时间:</td>
					<td class="value"><s:textfield name="dto.lastEditTime"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%--<%@ include file="../msg.jsp" %>--%>
