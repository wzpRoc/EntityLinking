<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.commodity.Commodity" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/commodity/commodity.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/commodity/commodity.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>commodityList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="commodityList">
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
                姓名：
            </div>
            <div class="value">
                <s:textfield name="condition.name" style="width:80px"/>
            </div>
            <div class="submitButton">
                <button type="submit">查询</button>
                <button type="button" onclick="window.location='commodityDetail?dowhat=add'">新增</button>
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
                        商品名称
                    </th>
                    <th width="10%">
                        原价
                    </th>
                    <th width="10%">
                        现价
                    </th>
                    <th width="10%">
                        库存量
                    </th>
                    <th width="10%">
                        运费
                    </th>
                    <th width="10%">
                        商品类别Id
                    </th>
                    <th width="10%">
                        商品一级类别
                    </th>
                    <th width="10%">
                        抽象产品Id
                    </th>
                    <th width="10%">
                        照片URL
                    </th>
                    <th width="10%">
                        描述信息
                    </th>
                    <th width="10%">
                        状态
                    </th>
                    <th width="10%">
                        创建者id
                    </th>
                    <th width="10%">
                        创建时间
                    </th>
                    <th width="10%">
                        创建者id
                    </th>
                    <th width="10%">
                        创建时间
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        Commodity commodity = (Commodity) dto;
                        int id = commodity.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=commodity.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="commodityDetail?dowhat=edit&id=<%=id%>">
                            commodity<%=commodity.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=commodity.getId()%>
                    </td>
                    <td>
                        <%=commodity.getName()%>
                    </td>
                    <td>
                        <%=commodity.getOriginalPrice()%>
                    </td>
                    <td>
                        <%=commodity.getCurrentPrice()%>
                    </td>
                    <td>
                        <%=commodity.getInventory()%>
                    </td>
                    <td>
                        <%=commodity.getFreight()%>
                    </td>
                    <td>
                        <%=commodity.getProductCategoryId()%>
                    </td>
                    <td>
                        <%=commodity.getTopCategoryId()%>
                    </td>
                    <td>
                        <%=commodity.getAbstractProductId()%>
                    </td>
                    <td>
                        <%=commodity.getPhotoUrl()%>
                    </td>
                    <td>
                        <%=commodity.getDescription()%>
                    </td>
                    <td>
                        <%=commodity.getState()%>
                    </td>
                    <td>
                        <%=commodity.getCreatorUserId()%>
                    </td>
                    <td>
                        <%=commodity.getCreateTime()%>
                    </td>
                    <td>
                        <%=commodity.getEditorUserId()%>
                    </td>
                    <td>
                        <%=commodity.getLastEditTime()%>
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
