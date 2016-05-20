<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.orderCommodity.OrderCommodity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/shop/orderCommodity/orderCommodity.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>


<html>
<head>
    <title>音乐清单</title>
</head>
<body>
<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <script>
        var userName = "<%=user == null ? "" : user.getNicName()%>";
        var userId = "<%=user==null?"":user.getId() %>"
    </script>
    <%@include file="../../leftBar.jsp" %>

    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="get" action="orderCommodityList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <div id="titleArea">
                <h5>
                    我的音乐清单
                </h5>
            </div>

            <%--查询结果--%>
            <div id="dataArea">
                <%
                    for (Object dto : list) {
                        OrderCommodity orderCommodity = (OrderCommodity) dto;
                        int id = orderCommodity.getId();

                %>
                <div class="commodity">
                    <div class="checkbox">
                        <td style="text-align: center">
                            <input type="checkbox" value="<%=orderCommodity.getId()%>" id="<%=id%>"
                                   name="checkbox_id">
                        </td>
                    </div>
                    <div class="photo">
                        <img src="<%=orderCommodity.getPhotoUrl()%>">
                    </div>
                    <div class="parameter">
                        <ul class="commodityName">
                            <li>产品名称：<%=orderCommodity.getCommodityName()%>
                            </li>
                            <li>产品型号：<%=orderCommodity.getCommodityName()%>
                            </li>
                        </ul>
                        <ul class="commodityInfo">
                            <li>评价：</li>
                            <li>单价：<%=orderCommodity.getCurrentPrice()%>
                            </li>
                            <li>库存：<%=orderCommodity.getQuantity()%>
                            </li>
                            <li>赠送：</li>
                        </ul>
                    </div>
                    <div class="options">
                        <ul>
                            <li>放入购物车</li>
                            <li>添加时间:<%=orderCommodity.getCreateDate()%>
                            </li>
                            <li>删除</li>
                        </ul>
                    </div>
                </div>
                <%
                    }
                %>
                <br/>


            </div>
            <%@include file="../../queryNavigation.jsp" %>
        </form>
    </div>
    <%@include file="../../footer.jsp" %>

</div>
<%@include file="../../commonJS.jsp" %>
<script language="javascript" src="jsp/common/shop/orderCommodity/orderCommodity.js"></script>
</body>
</html>
