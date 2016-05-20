<%@ page import="org.ailab.tem.wim.shop.OrderStatus" %>
<%@ page import="org.ailab.tem.wim.shop.order.Order" %>
<%@ page import="org.ailab.tem.wim.shop.orderProduct.OrderProduct" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../../commonJS.jsp" %>

<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="./css/base.css" rel="stylesheet" type="text/css"/>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
<link href="./css/list.css" rel="stylesheet" type="text/css"/>
<link href="./jsp/common/shop/order/order.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>


<%
    List<Order> orderList = (List<Order>) request.getAttribute("orderList");
%>

<html>
<head>
    <title>我的订单</title>
</head>
<body>


<div id="fullContent">
    <%@include file="../../../header.jsp" %>
    <%@include file="../../../leftBar.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="orderList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>


            <div class="panel-heading prescNo">
                <a href="productList">首页/</a>
                <span>我的订单</span>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    订单号
                </div>
                <input type="text" name="condition.orderId">

                <div class="submitButton">
                    <button type="button" class="btn btn-default" onclick='$("#mainForm").submit()'>查询</button>
                </div>
            </div>


                <% if(orderList!=null && orderList.size()>0){  %>
            <table class="table table-bordered" id="orderList">
                <%--表头--%>
                <tr class="thead" style="text-align: center">
                    <th width="20%">
                        宝贝
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
                    <th width="15%">
                        订单号
                    </th>
                    <th width="5%">
                        总价
                    </th>
                    <th width="10%">
                        创建日期
                    </th>
                    <th width="10%">
                        交易状态
                    </th>
                    <th width="10%">
                        操作
                    </th>
                </tr>

                <%--表格内容--%>
                <%
                    for (Order order : orderList) {
                        int id = order.getId();
                        ArrayList<OrderProduct> orderProductList = (ArrayList<OrderProduct>) order.getOrderProductList();
                        OrderProduct orderProduct = orderProductList.get(0);
                        int productAmount = orderProductList.size();
                %>
                <tr class="tbody" orderId="<%=order.getId()%>">
                    <td>
                        <img width="50px" src="<%=orderProduct.getPhotoRelUrl()%>">
                        <%=orderProduct.getProductName()%>
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
                    <td rowspan="<%=productAmount%>">
                        <%=order.getOrderId()%>
                        <br>
                        <a href="orderDetail?dowhat=view&name=<%=id%>">
                            订单详情
                        </a>
                    </td>
                    <td rowspan="<%=productAmount%>">
                        <%=order.getAmount()%>
                    </td>
                    <td rowspan="<%=productAmount%>">
                        <%=order.getCreateDate()%>
                    </td>
                    <td rowspan="<%=productAmount%>" style="color: #009148">
                        <%=order.getStateDesc()%>
                    </td>
                    <td rowspan="<%=productAmount%>">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    style="padding: 2px 15px;">
                                操作 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <% if (order.getState() == OrderStatus.WAIT_BUYER_CONFIRM_GOODS.getValue()) { %>
                                <li><a onclick="confirmReceipt(<%=order.getId()%>); return false;">确认收货</a></li>
                                <% } %>
                                <li><a href="orderDetail?dowhat=view&name=<%=id%>">订单详情</a></li>
                                <li class="divider"></li>
                                <li><a onclick="removeOrder(<%=order.getId()%>, this); return false;">删除订单</a></li>
                            </ul>
                        </div>
                        <%--<a onclick="removeOrder(<%=order.getId()%>, this)">删除</a>--%>
                    </td>
                </tr>
                <%
                    //遍历其它商品信息
                    if (orderProductList.size() > 1) {
                        for (int i = 1; i < orderProductList.size(); i++) {
                            orderProduct = orderProductList.get(i);
                %>
                <tr class="tbody" orderId="<%=order.getId()%>">
                    <td>
                        <img width="50px" src="<%=orderProduct.getPhotoRelUrl()%>">
                        <%=orderProduct.getProductName()%>
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
                </tr>
                <%
                            }
                        }
                    }
                %>
            </table>
                <%}else{  %>
            <div id="noProducts">
                <img src="img/sadface.png">
                <br><br>暂无订单，去挑选宝贝 <a href="productList">商城首页</a>
            </div>
                <% }%>
            <br/>
    </div>
    </form>
    <%@include file="../../../footer.jsp" %>

</div>
</div>
<script language="javascript" src="./js/wimfra.js"></script>
<script language="javascript" src="./js/load.js"></script>
<script language="javascript" src="./jsp/common/shop/order/order.js"></script>

</body>
</html>
