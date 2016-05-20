<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.orderProduct.OrderProduct" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../../commonJS.jsp" %>

<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="./css/base.css" rel="stylesheet" type="text/css"/>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
<link href="./css/list.css" rel="stylesheet" type="text/css"/>
<link href="./css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="./jsp/common/shop/orderProduct/orderProduct.css" rel="stylesheet" type="text/css"/>

<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>


<%
    Page listPage = (Page) request.getAttribute("page");
    List<OrderProduct> orderProductList = (List<OrderProduct>) request.getAttribute("orderProductList");

%>

<html>
<head>
    <title>我的购物车</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../../../header.jsp" %>
    <%@include file="../../../leftBar.jsp" %>

    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="orderProductList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>


            <div class="panel-heading prescNo">
                <a href="productList">首页/</a>
                <span>我的购物车</span>
            </div>

            <%if (orderProductList != null && orderProductList.size() > 0) { %>
            <div class="panel panel-default  Clearfix" id="productInfo">
                <%--<div class="panel-heading subtitle">商品信息</div>--%>
                <div class="panel-body">
                    <table id="dataTable" class="table table-hover" style="border-bottom: 1px solid #DDDDDD;">
                        <%--表头--%>
                        <tr class="thead">
                            <th class="td_checkbox">
                                <input type="checkbox" class="orderProductId" name="checkbox_id_head"
                                       onclick="on_checkbox_id_head_clicked(this); calTotalPrice();"/>
                            </th>
                            <th width="40%">
                                商品
                            </th>
                            <th width="10%">
                                商品型号
                            </th>
                            <th width="5%">
                                单价
                            </th>
                            <th width="5%">
                                数量
                            </th>
                            <th width="5%">
                                运费
                            </th>
                            <th width="5%">
                                总价
                            </th>
                            <th width="15%">
                                创建日期
                            </th>
                            <th width="10%">
                                操作
                            </th>
                        <tr>
                            <%--表格内容--%>
                                <%
                    for (OrderProduct orderProduct : orderProductList) {
                    %>
                        <tr class="tbody" style="height: 50px">
                            <td style="text-align: center">
                                <input type="checkbox" value="<%=orderProduct.getId()%>" id="id1" name="checkbox_id"
                                       onclick="calTotalPrice();">
                            </td>
                            <td style="text-align: left">
                                <img width="50px" src="<%=orderProduct.getPhotoRelUrl()%>">
                                <%=orderProduct.getProductName()%>
                            </td>
                            <td>
                                <%=orderProduct.getProductModel()%>
                            </td>
                            <td>
                                <%=orderProduct.getCurrentPrice()%>
                            </td>
                            <td>
                                <%=orderProduct.getQuantity()%>
                            </td>
                            <td>
                                <%=orderProduct.getFreight()%>
                            </td>
                            <td class="productAmount">
                                <%=orderProduct.getAmount()%>
                            </td>
                            <td>
                                <%=orderProduct.getCreateDate()%>
                            </td>
                            <td>
                                <a onclick="removeOrderProduct(this, <%=orderProduct.getId()%>)">删除</a>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                    <br/>

                    <div id="amount">总计(含运费)：<span id="amountNum">0</span></div>
                </div>
                <a id="continueBtn" href="productList">继续购物</a>
                <a id="payBtn" onclick="payProducts(); return false;">去结算 </a>
            </div>
            <%} else { %>
            <div id="noOrders">
                <img src="img/sadface.png">
                <br><br>暂无宝贝，去挑选宝贝 <a href="productList">商城首页</a>
            </div>
            <%}%>

            <div/>
        </form>
    </div>
</div>
<%@include file="../../../footer.jsp" %>
<script language="javascript" src="./js/wimfra.js"></script>
<script language="javascript" src="./jsp/common/shop/orderProduct/orderProduct.js"></script>
</body>
</html>
