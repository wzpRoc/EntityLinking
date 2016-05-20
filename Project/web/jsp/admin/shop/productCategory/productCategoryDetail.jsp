<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.productCategory.ProductCategory" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/productCategory/productCategory.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/productCategory/productCategory.js"></script>

<%
    ProductCategory productCategory = (ProductCategory) request.getAttribute("dto");
%>

<html>
<head>
    <title>商品类别详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="productCategoryDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                商品类别详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='productCategoryList'">返回</button>
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
					<td class="label">上级ID:</td>
					<td class="value"><s:textfield name="dto.parentId"/></td>
				</tr>
				<tr>
					<td class="label">目录ID:</td>
					<td class="value"><s:textfield name="dto.categoryId"/></td>
				</tr>
				<tr>
					<td class="label">目录名称:</td>
					<td class="value"><s:textfield name="dto.categoryName"/></td>
				</tr>
				<tr>
					<td class="label">级别:</td>
					<td class="value"><s:textfield name="dto.level"/></td>
				</tr>
				<tr>
					<td class="label">备注:</td>
					<td class="value"><s:textfield name="dto.description"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
