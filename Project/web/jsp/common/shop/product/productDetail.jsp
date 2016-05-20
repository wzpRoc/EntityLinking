<%@ page import="org.ailab.tem.wim.shop.product.Product" %>
<%@ page import="org.ailab.tem.wim.shop.productComment.ProductComment" %>
<%@ page import="org.ailab.tem.wim.shop.productParam.ProductParam" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page import="org.ailab.wimfra.util.time.TimeUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>


<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/shop/product/product.css" rel="stylesheet" type="text/css"/>

<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>


<%
    Product product = (Product) request.getAttribute("dto");
    ArrayList<ProductParam> productParamList = (ArrayList<ProductParam>) request.getAttribute("productParamList");
    ArrayList<ProductComment> productCommentList = (ArrayList<ProductComment>) request.getAttribute("productCommentList");
    ArrayList<ProductComment> productConsultList = (ArrayList<ProductComment>) request.getAttribute("productConsultList");
    String path = request.getContextPath();
%>
<html>
<head>
    <title>商品详情</title>
</head>
<body>
<div id="fullContent">
    <%@include file="../../../header.jsp" %>
    <%@include file="../../../leftBar.jsp" %>

    <div id="mainContent" class="Clearfix">
        <form name="mainForm" id="mainForm" method="post" action="productDetail">
            <input type="hidden" name="saveTag" value="1"/>
            <s:hidden name="dowhat"/>
            <s:hidden name="dto.id" id="productId"/>
            <s:hidden name="dto.inventory" id="inventory"/>

            <div id="product-intro" class="info-block block-margin">
                <div id="preview">
                    <img src="<%=product.getPhotoRelUrl()%> ">
                </div>

                <div id="summary">
                    <div id="name">
                        <h3><%=product.getName()%>
                        </h3>
                    </div>
                    <div id="descriptionHtml">
                        <%=product.getDescriptionHtml()%>
                    </div>
                    <div id="price">
                        <div id="currentPriceLabel" style="float: left">现价:</div>
                        <div id="currentPrice">
                            ¥<%=product.getCurrentPrice()%>
                        </div>
                        <div id="originalPriceLabel" style="float: left">原价:</div>
                        <div id="originalPrice">
                            ¥<%=product.getOriginalPrice()%>
                        </div>
                    </div>
                    <table>
                        <tr>
                            <td id="classificationModel">商品型号:</td>
                            <td class="value">
                                <%=product.getModel()%>
                            </td>
                        </tr>
                        <tr>
                            <td>配件清单:</td>
                            <td class="value">
                                <%=product.getAccessories()%>
                            </td>
                        </tr>
                        <tr>
                            <td>运费:</td>
                            <td class="value"><%=product.getFreight()%>
                            </td>
                        </tr>
                        <tr>
                            <td>库存量:</td>
                            <td class="value"><%=product.getInventory()%>
                            </td>
                        </tr>
                        <tr>
                            <td>购买数量:</td>
                            <td class="value">
                                <a id="but_decrease" onclick="decreaseQuantity(); return false;">-</a>
                                <s:textfield name="dto.quantity" id="quantity" value="1"/>
                                <a id="but_increase" onclick="increaseQuantity(); return false;">+</a>
                            </td>
                        </tr>
                        <tr id="quantityWarning" style="display: none;">
                            <td></td>
                            <td><span style="color: red">您所填写的商品数量超过库存!</span></td>
                        </tr>
                    </table>
                    <div id="choose">
                        <div id="btnBuy" onclick="buyProduct()">立刻购买</div>
                        <div id="btnAppend" onclick="addOrderProduct()">加入购物车</div>
                    </div>
                </div>
            </div>
        </form>

        <div id="product-info" class="info-block block-margin">
            <h4>商品参数</h4>
            <ul id="productParam" class="clearfix">
                <% for (ProductParam productParam : productParamList) {
                    String name = productParam.getName();
                    String value = productParam.getValue();
                %>
                <li><%=name%>:<%=value%>
                </li>
                <%
                    }
                %>
            </ul>
        </div>

        <div class="block-margin">
            <div class="subTitle">商品评论</div>
            <div id="product-comment" class="info-block">
                <ul id="commentList">
                    <% for (ProductComment productComment : productCommentList) {
                        String userName = productComment.getUserName();
                        String comment = productComment.getComment();
                        String reply = productComment.getReply();
                        String time = productComment.getOpTime();
                    %>
                    <li>
                        <div class="commentUser"><%=userName%>
                        </div>
                        <div class="commentContent">
                            <%=comment%>
                        </div>
                        <div class="commentReply"><%=reply%>
                        </div>
                        <div class="commentTime"><%=TimeUtil.formatTime(time)%>
                        </div>
                    </li>
                    <% } %>
                </ul>
            </div>
        </div>

        <div class="block-margin">
            <div class="subTitle">商品咨询</div>
            <div id="productConsult" class=" Clearfix info-block">
                <ul id="consultList">
                    <% for (ProductComment productComment : productConsultList) {
                        String userName = productComment.getUserName();
                        String comment = productComment.getComment();
                        String reply = productComment.getReply();
                        String time = productComment.getOpTime();
                    %>
                    <li>
                        <div class="commentUser"><%=userName%>
                        </div>
                        <div class="commentContent">
                            <%=comment%>
                        </div>
                        <%if (StringUtil.notEmpty(reply)) { %>
                        <div class="commentReply"><%=reply%>
                        </div>
                        <% } %>

                        <div class="commentTime"><%=TimeUtil.formatTime(time)%>
                        </div>
                    </li>
                    <% } %>
                </ul>
                <textarea class="form-control" rows="3" id="consultArea"></textarea>
                <button type="submit" class="btn btn-default" id="consultBtn" onclick="submitConsult()">提交</button>
            </div>
        </div>
    </div>
    <%@include file="../../../footer.jsp" %>
</div>
<%@include file="./shoppingCart.jsp" %>
<%@ include file="/jsp/msg.jsp" %>
<%@include file="../../../commonJS.jsp" %>
<script>
    var userName = "<%=user == null ? "" : user.getNicName()%>";
    var userId = "<%=user==null?"":user.getId() %>"
    var path = "<%=path%>";
</script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="jsp/common/shop/product/product.js"></script>
</body>
</html>


