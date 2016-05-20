<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.order.Order" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.ailab.tem.wim.shop.orderProduct.OrderProduct" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page import="org.ailab.tem.wim.shop.OrderStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../../commonJS.jsp"%>

<link href="./css/base.css" rel="stylesheet" type="text/css"/>
<link href="./css/style.css" rel="stylesheet" type="text/css"/>
<link href="./css/list.css" rel="stylesheet" type="text/css"/>
<link href="./css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>
<link href="./jsp/common/shop/order/order.css" rel="stylesheet" type="text/css"/>
<link href="./css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>




<%
    Order order = (Order) request.getAttribute("dto");
    ArrayList<OrderProduct> orderProductList = (ArrayList<OrderProduct>) request.getAttribute("orderProductList");
%>

<html>
<head>
    <title>订单详情</title>
</head>
<body>


<div id="fullContent">
    <%@include file="../../../header.jsp" %>
    <%@include file="../../../leftBar.jsp" %>
    <div id="mainContent">
        <%
            if ("view".equals(dowhat)) {
        %>
        <div class="panel-heading prescNo">
            <a href="productList">首页/</a>
            <a href="orderList">我的订单/</a>
            <span>订单详情</span>
        </div>

        <%
        } else {
        %>
        <div class="panel-heading prescNo">确认订单信息</div>
        <%
            }
        %>
        <div class="panel panel-default" id="addressInfo">
            <div class="panel-heading subtitle">订单状态</div>
            <div class="panel-body" id="state">
                <ul>
                    <li <%if (order.getState() >= 1) {%> style="color: #009148" <%}%> >1 拍下商品</li>
                    <li <%if (order.getState() >= 2) {%> style="color: #009148" <%}%> >2 付款到支付宝</li>
                    <li <%if (order.getState() >= 3) {%> style="color: #009148" <%}%> >3 卖家发货</li>
                    <li <%if (order.getState() >= 4) {%> style="color: #009148" <%}%> >4 确认收货</li>
                </ul>
            </div>
        </div>


        <form name="mainForm" class="form-horizontal" role="form" id="mainForm" method="post"
              action="jsp/common/shop/alipay/alipayapi.jsp">
            <input type="hidden" name="saveTag" value="1"/>
            <s:hidden name="pageType" value="detail"/>
            <s:hidden name="dowhat" id="dowhat"/>
            <s:hidden name="dto.state" id="state"/>
            <s:hidden name="dto.id"/>
            <s:hidden name="dto.orderId" id="orderId"/>
            <s:hidden name="dto.userId"/>
            <s:hidden name="dto.createDate"/>
            <s:hidden name="dto.createTime"/>
            <s:hidden name="dto.quantity"/>
            <s:hidden name="dto.amount"/>
            <s:hidden name="dto.payType"/>
            <s:hidden name="dto.alipayNo"/>
            <s:hidden name="dto.payTime"/>
            <s:hidden name="dto.deliverId"/>
            <s:hidden name="dto.deliverName"/>
            <s:hidden name="dto.deliverTime"/>
            <s:hidden name="dto.logisticsCompanyId"/>
            <s:hidden name="dto.logisticsCompanyName"/>
            <s:hidden name="dto.logisticsInfo"/>
            <s:hidden name="dto.consignTime"/>
            <s:hidden name="dto.state"/>


            <!--支付宝需要的数据-->
            <s:hidden name="WIDseller_email" value="true_e2013@163.com"/>


            <div class="panel panel-default" id="addressInfo">
                <div class="panel-heading subtitle">收货地址</div>
                <div class="panel-body">
                    <div class="form-group">
                        <label for="consignee" class="control-label  Clearfix">收货人</label>

                        <div class="col-sm-10">
                            <input type="number" name="dto.consignee"
                                   value="<%=StringUtil.replaceNullWithBlank(order.getConsignee())%>"
                                   class="form-control" id="consignee"
                                   placeholder="收货人">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="mobilephone" class="control-label  Clearfix">电话</label>

                        <div class="col-sm-10">
                            <input type="number" name="dto.mobilephone"
                                   value="<%=StringUtil.replaceNullWithBlank(order.getMobilephone())%>"
                                   class="form-control" id="mobilephone"
                                   placeholder="请务必填写有效电话">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="address" class="control-label">地址</label>

                        <div class="col-sm-10">
                            <input type="text" name="dto.address"
                                   value="<%=StringUtil.replaceNullWithBlank(order.getAddress())%>"
                                   class="form-control"
                                   id="address" placeholder="如：XX省 XXX市 XXX区 XXX路 XXX小区 XXX栋 XXX单元 XXX号">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="zipCode" class="control-label">邮编</label>

                        <div class="col-sm-10">
                            <input type="text" name="dto.zipCode"
                                   value="<%=StringUtil.replaceNullWithBlank(order.getZipCode())%>" id="zipCode"
                                   class="form-control"
                                   placeholder="邮编">
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel panel-default" id="productInfo">
                <div class="panel-heading subtitle">商品信息</div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <%--表头--%>
                        <tr class="thead" style="text-align: center">
                            <th width="20%">
                                宝贝
                            </th>
                            <th width="10%">
                                商品型号
                            </th>
                            <th width="10%">
                                单价
                            </th>
                            <th width="10%">
                                数量
                            </th>
                            <th width="10%">
                                金额
                            </th>
                        </tr>

                        <%--表格内容--%>
                        <%
                            for (Object dto : orderProductList) {
                                OrderProduct orderProduct = (OrderProduct) dto;
                        %>
                        <tr class="tbody">
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
                            <td class="productAmount">
                                <%=orderProduct.getAmount()%>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </div>
            </div>

            <div id="amount">
                总额：<span>￥<%=order.getAmount()%></span>元
            </div>

            <%--功能按钮区--%>
            <div id="buttonArea" class=" Clearfix">
                <button type="button" class="btn btn-default btn-sm" onclick="window.location='orderList'">我的订单
                </button>
                <%if (order.getState() <= OrderStatus.WAIT_BUYER_PAY.getValue()) { %>
                <button class="btn btn-default btn-lg" id="submitOrderBtn" onclick="submitOrder(); return false;">
                    去付款
                </button>
                <% } else if (order.getState() == OrderStatus.WAIT_BUYER_CONFIRM_GOODS.getValue()) {%>
                <button class="btn btn-default btn-lg" id="submitOrderBtn" onclick="confirmReceipt(<%=order.getId()%>); return false;">
                    确认收货
                </button>
                <%}%>
                <button class="btn btn-default btn-lg" id="submitOrderBtn" onclick="confirmReceipt(<%=order.getId()%>); return false;">
                    确认收货
                </button>

            </div>


        </form>
    </div>
</div>

<script language="javascript" src="./js/wimfra.js"></script>
<script language="javascript" src="./js/load.js"></script>
<script language="javascript" src="./jsp/common/shop/order/order.js"></script>
</body>
</html>

<%@ include file="/jsp/msg.jsp" %>

