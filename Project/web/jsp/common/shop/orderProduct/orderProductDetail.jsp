<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.shop.orderProduct.OrderProduct" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/orderProduct/orderProduct.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/orderProduct/orderProduct.js"></script>

<%
    OrderProduct orderProduct = (OrderProduct) request.getAttribute("dto");
%>

<html>
<head>
    <title>订单商品明细详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="orderProductDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                订单商品明细详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='orderProductList'">返回</button>
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
					<td class="label">订单ID:</td>
					<td class="value"><s:textfield name="dto.orderId"/></td>
				</tr>
				<tr>
					<td class="label">用户ID:</td>
					<td class="value"><s:textfield name="dto.userId"/></td>
				</tr>
				<tr>
					<td class="label">创建日期:</td>
					<td class="value"><s:textfield name="dto.createDate"/></td>
				</tr>
				<tr>
					<td class="label">商品ID:</td>
					<td class="value"><s:textfield name="dto.productId"/></td>
				</tr>
				<tr>
					<td class="label">商品名称:</td>
					<td class="value"><s:textfield name="dto.productName"/></td>
				</tr>
				<tr>
					<td class="label">商品型号:</td>
					<td class="value"><s:textfield name="dto.productModel"/></td>
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
					<td class="label">折扣率:</td>
					<td class="value"><s:textfield name="dto.discount"/></td>
				</tr>
				<tr>
					<td class="label">数量:</td>
					<td class="value"><s:textfield name="dto.quantity"/></td>
				</tr>
				<tr>
					<td class="label">金额:</td>
					<td class="value"><s:textfield name="dto.amount"/></td>
				</tr>
				<tr>
					<td class="label">状态:</td>
					<td class="value"><s:textfield name="dto.state"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
