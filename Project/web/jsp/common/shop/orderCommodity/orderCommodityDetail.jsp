<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.orderCommodity.OrderCommodity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/orderCommodity/orderCommodity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/orderCommodity/orderCommodity.js"></script>

<%
    OrderCommodity orderCommodity = (OrderCommodity) request.getAttribute("dto");
%>

<html>
<head>
    <title>订单商品详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="orderCommodityDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                订单商品详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='orderCommodityList'">返回</button>
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
					<td class="label">创建时间:</td>
					<td class="value"><s:textfield name="dto.createTime"/></td>
				</tr>
				<tr>
					<td class="label">商品ID:</td>
					<td class="value"><s:textfield name="dto.commodityId"/></td>
				</tr>
				<tr>
					<td class="label">商品名称:</td>
					<td class="value"><s:textfield name="dto.commodityName"/></td>
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
					<td class="label">运费:</td>
					<td class="value"><s:textfield name="dto.freight"/></td>
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
