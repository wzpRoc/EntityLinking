<META http-equiv="X-UA-Compatible" content="IE=9">
</META>
<%@ page import="org.ailab.tem.wim.shop.product.Product" %>
<%@ page import="org.ailab.wimfra.core.Page" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/shop/product/product.css" rel="stylesheet" type="text/css"/>

<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
    //查找根目录，防止链接失效
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

    <div id="mainContent" class="Clearfix">

        <form name="mainForm" id="mainForm" method="post" action="productList">
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
            <s:hidden name="dto.photoAbsUrl" id="photoAbsUrl"/>

            <div id="searchArea">
                <div id="search">
                    <img src="./img/front/searchSmall.png">
                </div>
                <div id="shopCart">
                    <img src="./img/front/shoppingCart.png">
                </div>
                <%--<div id="shopCartProducts">--%>
                    <%--<ul>--%>
                        <%--<li class="products">物品1</li>--%>
                        <%--<li class="products">物品2</li>--%>
                        <%--<li class="products">物品3</li>--%>
                        <%--<li class="products">物品4</li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
                <div id="wish">
                    <img src="./img/front/wish.png">
                </div>

            </div>

            <%--&lt;%&ndash;查询条件&ndash;%&gt;--%>
            <%--<div id="conditionArea">--%>
            <%--<div class="shopLabel">--%>
            <%--商品名称:--%>
            <%--</div>--%>
            <%--<input type="text" name="condition.name" style="width:100px">--%>

            <%--<div class="shopLabel">--%>
            <%--价格:--%>
            <%--</div>--%>
            <%--<div class="value" style="margin-right: 0px">--%>
            <%--<s:textfield name="condition.startPrice" style="width:50px"/>--%>
            <%--</div>--%>
            <%--<div class="shopLabel" style="width: 10px">--%>
            <%-----%>
            <%--</div>--%>
            <%--<div class="value">--%>
            <%--<s:textfield name="condition.endPrice" style="width:50px"/>--%>
            <%--</div>--%>

            <%--<div class="submitButton">--%>
            <%--<button type="submit" class="btn btn-default">查询</button>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--查询结果--%>
            <div id="dataArea" class=" Clearfix">


                <%
                    for (Object dto : list) {
                        Product product = (Product) dto;
                        int id = product.getId();

                %>
                <div class="product">
                    <div class="productImg">
                        <a href="productDetail?dowhat=edit&name=<%=id%>">
                            <img src="<%=product.getPhotoRelUrl()%> ">
                        </a>
                    </div>

                    <div class="productTitle">
                        <%= product.getDescriptionHtml()%>
                    </div>
                    <div class="productPrice">
                        价格：¥<%=product.getCurrentPrice()%>
                    </div>
                    <div class="operation">
                        <div class="compare">
                            <img src="img/front/compare.png">
                        </div>
                        <div class="addToCart">
                            <img src="img/front/addToCart.png">
                        </div>
                        <div class="addToWish">
                            <img src="img/front/addToWish.png">
                        </div>
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
</div>
<%@include file="./shoppingCart.jsp" %>
<%@include file="../commodity/commodityComparisonBar.jsp"%>
</body>

<%@include file="../../../commonJS.jsp" %>
<script language="javascript" src="jsp/common/shop/product/product.js"></script>
</html>




