<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.order.Order" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>


<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>订单列表</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../left.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="orderList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <div id="titleArea">
                <h1>
                    订单列表
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    订单号
                </div>
                <div class="value">
                    <s:textfield name="condition.orderId" style="width:80px"/>
                </div>
                <div class="submitButton">
                    <button type="submit">查询</button>
                    <button type="button" onclick="window.location='orderDetail?dowhat=add'">新增</button>
                    <button type="button" onclick="deleteFromList()">删除</button>
                </div>
            </div>

            <%--查询结果--%>
            <div id="dataArea">
                <table id="dataTable" cellSpacing="0">
                    <%--表头--%>
                    <tr class="thead">
                        <th class="td_checkbox" style="text-align: center">
                            <input type="checkbox" id="id_head" name="checkbox_id_head"
                                   onclick="on_checkbox_id_head_clicked(this)"/>
                        </th>

                        <th width="10%">
                            订单号
                        </th>
                        <th width="10%">
                            创建日期
                        </th>
                        <th width="5%">
                            金额
                        </th>
                        <th width="10%">
                            状态
                        </th>
                        <th width="10%">
                            操作
                        </th>
                    <tr>
                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        Order order = (Order) dto;
                        int id = order.getId();

                %>
                    <tr class="tbody">
                        <td style="text-align: center">
                            <input type="checkbox" value="<%=order.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <td>
                            <a href="orderDetail?dowhat=edit&name=<%=id%>">
                                <%=order.getOrderId()%>
                            </a>
                        </td>
                        <td>
                            <%=order.getCreateDate()%>
                        </td>
                        <td>
                            <%=order.getAmount()%>
                        </td>
                        <td>
                            <%=StringUtil.replaceNullWithBlank(order.getStateDesc())%>
                        </td>
                        <td>
                            <%
                                if (order.getState() == Order.WAIT_SELLER_SEND_GOODS) {
                            %>
                            <a href="orderDetail?dowhat=sendGoods&name=<%=id%>">发货</a>
                            <%} else{ %>
                            <a href="orderDetail?dowhat=edit&name=<%=id%>">订单详情</a>
                            <%}%>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
                <br/>
            </div>
        </form>
    </div>
    <%@include file="../../bottom.jsp" %>
</div>




</body>
</html>
