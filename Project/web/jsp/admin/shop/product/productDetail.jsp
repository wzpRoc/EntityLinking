<%@ page import="org.ailab.tem.wim.shop.product.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.ailab.tem.wim.shop.productParam.ProductParam" %>
<%@ page import="org.ailab.tem.wim.shop.productComment.ProductComment" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/shop/product/product.css" rel="stylesheet" type="text/css"/>


<%
    String path = request.getContextPath();
    Product product = (Product) request.getAttribute("dto");
    ArrayList<ProductParam> productParamList = (ArrayList<ProductParam>) request.getAttribute("productParamList");
    ArrayList<ProductComment> productCommentList = (ArrayList<ProductComment>) request.getAttribute("productCommentList");
    ArrayList<ProductComment> productConsultList = (ArrayList<ProductComment>) request.getAttribute("productConsultList");
%>
<script>
    var path = "<%=path%>";
</script>


<html>
<head>
    <title>商品信息</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../left.jsp" %>
    <div id="mainContent">


        <div id="titleArea">
            <h1>
                商品信息
            </h1>
        </div>

        <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">

                <button type="button" onclick="window.location='productList'">返回</button>
            </div>
        </div>

        <div id="product-detail">

            <div class="subTitle" onclick="toggleHideData(this)">商品详情</div>
            <%--数据表格区--%>
            <div id="dataArea" class="subData">
                <form name="mainForm" id="mainForm" method="post" action="productDetail">
                    <input type="hidden" name="saveTag" value="1"/>
                    <s:hidden name="dowhat"/>
                    <s:hidden name="dto.id" id="productId"/>
                    <table id="dataTable">
                        <tr>
                            <td class="label">商品名称:</td>
                            <td class="value"><s:textfield name="dto.name"/></td>
                        </tr>
                        <tr>
                            <td class="label">商品型号:</td>
                            <td class="value"><s:textfield name="dto.classificationModel"/></td>
                        </tr>
                        <tr>
                            <td class="label">原价:</td>
                            <td class="value"><s:textfield name="dto.originalPrice"/></td>
                        </tr>
                        <tr>
                            <td class="label">现价:</td>
                            <td class="value"><s:textfield name="dto.currentPrice"/></td>
                        </tr>
                        <tr>
                            <td class="label">折扣率:</td>
                            <td class="value"><s:textfield name="dto.discount"/></td>
                        </tr>
                        <tr>
                            <td class="label">运费:</td>
                            <td class="value"><s:textfield name="dto.freight"/></td>
                        </tr>
                        <tr>
                            <td class="label">库存量:</td>
                            <td class="value"><s:textfield name="dto.inventory"/></td>
                        </tr>
                        <%-- <tr>
                             <td class="label">类别:</td>
                             <td class="value"><s:textfield name="dto.category"/></td>
                         </tr>--%>
                        <tr>
                            <td class="label">配件清单:</td>
                            <td class="value"><s:textfield name="dto.accessories"/></td>
                        </tr>
                        <tr>
                            <td class="label">描述信息:</td>
                            <td class="value"><s:textfield name="dto.descriptionHtml"/></td>
                        </tr>
                        <tr>
                            <td class="label">状态:</td>
                            <td class="value">
                                <w:select name="dto.state" dataId="productState"></w:select>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">照片链接:</td>
                            <td class="value">
                                <s:textfield name="dto.photoUrl" id="photoUrl" readonly="true"/>
                            </td>
                        </tr>
                    </table>
                    <button type="submit">保存</button>
                </form>
            </div>


            <div id="product-info">
                <div class="subTitle" onclick="toggleSubData(this)">商品参数</div>
                <div class="subData">
                    <ul id="productParam" class="clearfix">
                        <%
                            if (productParamList != null) {
                                for (ProductParam productParam : productParamList) {
                                    String name = productParam.getName();
                                    String value = productParam.getValue();
                                    int id = productParam.getId();
                        %>
                        <li id="<%=id%>"><%=name%>:<%=value%>
                            <span class="deleteParam" onclick="deleteProductParam('<%=id%>', this)">删除</span>
                        </li>
                        <%
                                }
                            }
                        %>
                    </ul>
                    <button onclick="addProductParam()">新增参数</button>
                </div>
            </div>


            <div id="product-comment" class=" Clearfix">
                <div class="subTitle" onclick="toggleSubData(this)">商品评论</div>
                <div class="subData">
                    <ul id="commentList">
                        <% if (productCommentList != null) {
                            for (ProductComment productComment : productCommentList) {
                                int id = productComment.getId();
                                String userName = productComment.getUserName();
                                String comment = productComment.getComment();
                                String reply = productComment.getReply();
                                String time = productComment.getOpTime();

                        %>
                        <li>
                            <div class="commentUser"><%=userName%>
                                <span class="deleteComment" onclick="deleteProductComment(this, <%=id%>)">删除此评价</span>
                            </div>
                            <div class="commentContent">
                                <%=comment%>
                            </div>

                            <div class="commentReply" onclick="editReply(this)">
                                <span class="oldReply"><%=reply%></span>
                            </div>

                            <div class="commentReply editReply">
                                <textarea class="newReply"><%=reply%>
                                </textarea>
                                <button onclick="updateProductReply(this, <%=id%>)">保存</button>
                            </div>
                            <div class="commentTime"><%=time%>
                            </div>
                        </li>
                        <%
                                }
                            }
                        %>
                    </ul>
                    <textarea class="form-control" rows="3" id="commentArea"></textarea>
                    <button type="submit" class="btn btn-default" id="commentBtn" onclick="addProductComment()">新增评论
                    </button>
                </div>
            </div>

            <div id="productConsult" class=" Clearfix">
                <div class="subTitle" onclick="toggleSubData(this)">商品咨询</div>
                <div class="subData">
                    <ul id="consultList">
                        <% if (productConsultList != null) {
                            for (ProductComment productComment : productConsultList) {
                                int id = productComment.getId();
                                String userName = productComment.getUserName();
                                String comment = productComment.getComment();
                                String reply = productComment.getReply();
                                String time = productComment.getOpTime();
                        %>
                        <li>
                            <div class="commentUser"><%=userName%>
                                <span class="deleteComment" onclick="deleteProductComment(this, <%=id%>)">删除此问题</span>
                            </div>
                            <div class="commentContent">
                                <%=comment%>
                            </div>

                            <div class="commentReply" onclick="editReply(this)">
                                <span class="oldReply"><%=reply%></span>
                            </div>


                            <div class="commentReply editReply">
                                <textarea class="newReply"><%=reply%></textarea>
                                <button onclick="updateProductReply(this, <%=id%>)">保存</button>
                            </div>

                            <div class="commentTime"><%=time%>
                            </div>
                        </li>
                        <%
                                }
                            }
                        %>
                    </ul>
                    <textarea class="form-control" rows="3" id="consultArea"></textarea>
                    <button type="submit" class="btn btn-default" id="consultBtn" onclick="addProductConsult()">新增咨询
                    </button>
                </div>
            </div>
        </div>

        <!--用于提交图片的表单 -->
        <form id="picForm" enctype="multipart/form-data" class=" Clearfix">
            更改照片：
            <input id="fileupload" type="file" name="fileupload" onchange="uploadImage();"/>
            <!--用于显示图片的表单 -->
            <div id="showImage">
                <img src="<%=path%>/<%=product.getPhotoRelUrl()%>">
            </div>
        </form>
    </div>
    <%@include file="../../bottom.jsp" %>
</div>

<script language="javascript" src="../js/jquery.form.js"></script>
<script language="javascript" src="../js/uploadImage.js"></script>
<script language="javascript" src="../jsp/admin/shop/product/product.js"></script>
</body>
</html>
<%@ include file="/jsp/msg.jsp" %>
