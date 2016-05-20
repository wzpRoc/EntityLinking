<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.commodity.Commodity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.ailab.wimfra.core.Page" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/shop/product/product.css" rel="stylesheet" type="text/css"/>

<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>


<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
    Set<String> compareCommodityIdSet = (Set<String>) request.getAttribute("compareCommodityIdSet");
    String path = request.getContextPath();
%>

<html>
<head>
    <title>商品列表</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../../../header.jsp" %>
    <%@include file="../../../leftBar.jsp" %>
    <script>
        var path = "<%=path%>";
        var userName = "<%=user == null ? "" : user.getNicName()%>";
        var userId = "<%=user==null?"":user.getId() %>"
    </script>

    <div id="mainContent">

        <form name="mainForm" id="mainForm" method="post" action="commodityList">
            <%--二级导航栏--%>
            <div id="subNav">
                <ol>
                    <li>古典音乐解读</li>
                    <li>流行音乐解读</li>
                    <li>乡村音乐解读</li>
                    <li>爵士音乐解读</li>
                    <li>摇滚音乐解读</li>
                </ol>
            </div>

            <s:hidden name="clearSession"/>
            <input type="hidden" name="submitTag" value="1"/>
            <s:hidden name="dowhat"/>
            <s:hidden name="topCategoryId" id="topCategoryId"/>
            <s:hidden name="dto.photoAbsUrl" id="photoAbsUrl"/>

            <div id="searchArea">
                <span id="search">
                    <img src="./img/front/searchSmall.png">
                </span>
                <a id="shopCart" href="orderCommodityList?type=1" target="_blank">
                    <span id="cartProductsNum"></span>
                    <img src="./img/front/shoppingCart.png">
                </a>
                <a id="wish" href="orderCommodityList?type=2" target="_blank">
                    <span id="wishProductsNum"></span>
                    <img src="./img/front/wish.png">
                </a>

            </div>


            <%--查询结果--%>
            <div id="dataArea" class=" Clearfix">


                <%
                    for (Object dto : list) {
                        Commodity commodity = (Commodity) dto;
                        int id = commodity.getId();
                        String commodityId = String.valueOf(id);

                %>
                <div class="product" id="product<%=id%>">
                    <div class="productImg">
                        <a href="productDetail?dowhat=edit&name=<%=id%>">
                            <img src="<%=commodity.getPhotoRelUrl()%> ">
                        </a>
                    </div>
                    <div class="productCategory">
                        <%=commodity.getTopCategoryId() %>
                    </div>
                    <div class="productTitle">
                        <%= commodity.getDescription()%>
                    </div>
                    <div class="productPrice">
                        价格：¥<%=commodity.getCurrentPrice()%>
                    </div>
                    <div class="operation">
                        <% if (compareCommodityIdSet != null && compareCommodityIdSet.contains(commodityId)) {%>
                           <span class="compare"
                                 onclick="removeCommodity(<%=commodity.getId()%>,'<%=commodity.getTopCategoryId()%>')">
                            取消
                        <% }else{ %>
                            <span class="compare" onclick="compareCommodity(<%=commodity.getId()%>, this)">
                            对比
                        <% } %>
                            <%--<img src="img/front/compare.png">--%>
                        </span>
                        <span class="addToCart" onclick="addOrderCommodity(<%=commodity.getId()%>,1)">
                            <img src="img/front/addToCart.png">
                        </span>
                        <span class="addToWish">
                            <img src="img/front/addToWish.png" onclick="addOrderCommodity(<%=commodity.getId()%>,2)">
                        </span>
                    </div>
                </div>
                <%
                    }
                %>
                <br/>
            </div>
            <%@include file="../../../queryNavigation.jsp" %>
        </form>
    </div>
    <%@include file="../../../footer.jsp" %>
    <%@include file="commodityComparisonBar.jsp" %>
</div>
</body>

<%@include file="../../../commonJS.jsp" %>
<script language="javascript" src="jsp/common/shop/commodity/commodity.js"></script>
</html>

