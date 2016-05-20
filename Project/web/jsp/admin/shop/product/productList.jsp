<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.product.Product" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../adminJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/shop/product/product.css" rel="stylesheet" type="text/css"/>



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
        <form name="mainForm" id="mainForm" method="get" action="productList">
            <s:hidden name="clearSession"/>
            <s:hidden name="submitTag"/>
            <s:hidden name="dowhat"/>

            <!--%@ include file="../navigation.jsp" %-->
            <div id="titleArea">
                <h1>
                    商品列表
                </h1>
            </div>

            <%--查询条件--%>
            <div id="conditionArea">
                <div class="label">
                    商品名称:
                </div>
                <div class="value">
                    <s:textfield name="condition.name" style="width:100px"/>
                </div>
                <div class="label">
                    状态:
                </div>
                <div class="value">
                    <w:select name="condition.state" addSelectAll="true" dataId="productState" onchange="document.getElementById('mainForm').submit()"/>
                </div>

                <div class="submitButton">
                    <button type="submit">查询</button>
                    <button type="button" onclick="window.location='productDetail?dowhat=add'">新增</button>
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
                            商品名称
                        </th>
                        <th width="10%">
                            商品型号
                        </th>
                        <th width="10%">
                            原价
                        </th>
                        <th width="10%">
                            现价
                        </th>
                        <th width="10%">
                            折扣率
                        </th>
                        <th width="10%">
                            库存量
                        </th>
                        <th width="10%">
                            照片URL
                        </th>
                        <th width="10%">
                            配件清单
                        </th>
                        <th width="10%">
                            状态
                        </th>
                    <tr>

                        <%--表格内容--%>
                            <%
                    for (Object dto : list) {
                        Product product = (Product) dto;
                        int id = product.getId();

                %>
                    <tr class="tbody">
                        <td style="text-align: center;">
                            <input type="checkbox" value="<%=product.getId()%>" id="id1" name="checkbox_id">
                        </td>
                        <td>
                            <a href="productDetail?dowhat=edit&name=<%=id%>">
                                <%=product.getName()%>
                            </a>
                        </td>
                        <td>
                            <%=product.getModel()%>
                        </td>
                        <td>
                            <%=product.getOriginalPrice()%>
                        </td>
                        <td>
                            <%=product.getCurrentPrice()%>
                        </td>
                        <td>
                            <%=product.getDiscount()%>
                        </td>
                        <td>
                            <%=product.getInventory()%>
                        </td>
                        <td>
                            <%=product.getPhotoUrl()%>
                        </td>
                        <td>
                            <%=product.getAccessories()%>
                        </td>
                        <td>
                            <%=product.getStateDesc()%>
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

<script language="javascript" src="../jsp/admin/shop/product/product.js"></script>
</body>
</html>
