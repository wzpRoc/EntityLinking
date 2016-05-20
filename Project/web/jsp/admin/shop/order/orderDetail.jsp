<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.order.Order" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>


<%
    Order order = (Order) request.getAttribute("dto");
%>

<html>
<head>
    <title>订单详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../left.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="post" action="orderDetail">
            <input type="hidden" name="saveTag" value="1"/>
            <s:hidden name="pageType" value="detail"/>
            <s:hidden name="dowhat" id="dowhat"/>
            <s:hidden name="dto.id" id="id"/>
            <s:hidden name="dto.payType"/>

            <div id="titleArea">
                <h1>
                    订单详情
                </h1>
            </div>

            <%--功能按钮区--%>
            <div id="topArea">
                &nbsp;
                <div id="buttonArea">
                    <% if (!"sendGoods".equalsIgnoreCase(dowhat)) {%>
                    <button type="submit">保存</button>
                    <%}%>
                    <button type="button" onclick="window.location='orderList?condition.deleted=all'">返回</button>
                </div>
            </div>

            <%--数据表格区--%>
            <div id="dataArea">
                <table id="dataTable">

                    <tr>
                        <td class="label">订单号:</td>
                        <td class="value"><s:textfield name="dto.orderId" readonly="true" cssClass="readOnly"/></td>
                    </tr>
                    <% if (!"sendGoods".equalsIgnoreCase(dowhat)) {%>
                    <tr>
                        <td class="label">用户ID:</td>
                        <td class="value"><s:textfield name="dto.userId"/></td>
                    </tr>
                    <tr>
                        <td class="label">创建日期:</td>
                        <td class="value"><s:textfield name="dto.createDate" readonly="true" cssClass="readOnly"/></td>
                    </tr>
                    <tr>
                        <td class="label">创建时间:</td>
                        <td class="value"><s:textfield name="dto.createTime" readonly="true" cssClass="readOnly"/></td>
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
                        <td class="label">收货人:</td>
                        <td class="value"><s:textfield name="dto.consignee"/></td>
                    </tr>
                    <tr>
                        <td class="label">收货地址:</td>
                        <td class="value"><s:textfield name="dto.address"/></td>
                    </tr>
                    <tr>
                        <td class="label">手机号:</td>
                        <td class="value"><s:textfield name="dto.mobilephone"/></td>
                    </tr>
                    <tr class="state2">
                        <td class="label">支付宝交易号:</td>
                        <td class="value"><s:textfield name="dto.alipayNo"/></td>
                    </tr>
                    <tr class="state2">
                        <td class="label">付款时间:</td>
                        <td class="value"><s:textfield name="dto.payTime"/></td>
                    </tr>
                    <%}%>
                    <tr class="state3">
                        <td class="label">发货人姓名:</td>
                        <td class="value"><s:textfield name="dto.deliverName"/></td>
                    </tr>
                    <tr class="state3">
                        <td class="label">发货时间:</td>
                        <td class="value"><s:textfield name="dto.deliverTime" id="deliverTime" readonly="true" cssClass="readOnly"/></td>
                    </tr>
                    <tr class="state3">
                        <td class="label">物流公司名称:</td>
                        <td class="value"><s:textfield name="dto.logisticsCompanyName" id="logisticsCompanyName"/></td>
                    </tr>
                    <tr class="state3">
                        <td class="label">运单号:</td>
                        <td class="value"><s:textfield name="dto.logisticsId" id="logisticsId"/></td>
                    </tr>
                    <% if (!"sendGoods".equalsIgnoreCase(dowhat)) {%>
                    <tr class="state4">
                        <td class="label">收货时间:</td>
                        <td class="value"><s:textfield name="dto.consignTime"/></td>
                    </tr>
                    <tr>
                        <td class="label">状态:</td>
                        <td class="value">
                            <w:select name="dto.state" dataId="orderState"/>
                        </td>
                    </tr>
                    <%}%>
                </table>
                <% if ("sendGoods".equalsIgnoreCase(dowhat)) {%>
                <button onclick="sendGoods(); return false;">确认发货</button>
                <%}%>
            </div>
        </form>
    </div>
    <%@include file="../../bottom.jsp" %>
</div>
<script language="javascript" src="../jsp/admin/shop/order/order.js"></script>
</body>
</html>

<%@ include file="/jsp/msg.jsp" %>



