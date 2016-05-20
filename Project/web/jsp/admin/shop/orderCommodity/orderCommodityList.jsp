<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.orderCommodity.OrderCommodity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/orderCommodity/orderCommodity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/orderCommodity/orderCommodity.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>orderCommodityList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="orderCommodityList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                订单商品列表
            </h1>
        </div>

        <%--查询条件--%>
        <div id="conditionArea">
            <div class="label">
                姓名：
            </div>
            <div class="value">
                <s:textfield name="condition.name" style="width:80px"/>
            </div>
            <div class="submitButton">
                <button type="submit">查询</button>
                <button type="button" onclick="window.location='orderCommodityDetail?dowhat=add'">新增</button>
                <button type="button" onclick="deleteFromList()">删除</button>
            </div>
        </div>

        <%--查询结果--%>
        <div id="dataArea">
            <table id="dataTable" cellSpacing="0">
                <%--表头--%>
                <tr class="thead">
                    <th class="td_checkbox">
                        <input type="checkbox" id="id_head" name="checkbox_id_head"
                               onclick="on_checkbox_id_head_clicked(this)"/>
                    </th>

                    <th width="10%">
                        ID
                    </th>
                    <th width="10%">
                        订单ID
                    </th>
                    <th width="10%">
                        用户ID
                    </th>
                    <th width="10%">
                        创建日期
                    </th>
                    <th width="10%">
                        创建时间
                    </th>
                    <th width="10%">
                        商品ID
                    </th>
                    <th width="10%">
                        商品名称
                    </th>
                    <th width="10%">
                        原价
                    </th>
                    <th width="10%">
                        现价
                    </th>
                    <th width="10%">
                        运费
                    </th>
                    <th width="10%">
                        数量
                    </th>
                    <th width="10%">
                        金额
                    </th>
                    <th width="10%">
                        状态
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        OrderCommodity orderCommodity = (OrderCommodity) dto;
                        int id = orderCommodity.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=orderCommodity.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="orderCommodityDetail?dowhat=edit&id=<%=id%>">
                            orderCommodity<%=orderCommodity.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=orderCommodity.getId()%>
                    </td>
                    <td>
                        <%=orderCommodity.getOrderId()%>
                    </td>
                    <td>
                        <%=orderCommodity.getUserId()%>
                    </td>
                    <td>
                        <%=orderCommodity.getCreateDate()%>
                    </td>
                    <td>
                        <%=orderCommodity.getCreateTime()%>
                    </td>
                    <td>
                        <%=orderCommodity.getCommodityId()%>
                    </td>
                    <td>
                        <%=orderCommodity.getCommodityName()%>
                    </td>
                    <td>
                        <%=orderCommodity.getOriginalPrice()%>
                    </td>
                    <td>
                        <%=orderCommodity.getCurrentPrice()%>
                    </td>
                    <td>
                        <%=orderCommodity.getFreight()%>
                    </td>
                    <td>
                        <%=orderCommodity.getQuantity()%>
                    </td>
                    <td>
                        <%=orderCommodity.getAmount()%>
                    </td>
                    <td>
                        <%=orderCommodity.getState()%>
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

</body>
</html>
