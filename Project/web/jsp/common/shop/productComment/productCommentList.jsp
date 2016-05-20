<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.productComment.ProductComment" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/productComment/productComment.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="jsp/productComment/productComment.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>productCommentList</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="get" action="productCommentList">
        <s:hidden name="clearSession"/>
        <s:hidden name="submitTag"/>
        <s:hidden name="dowhat"/>

        <!--%@ include file="../navigation.jsp" %-->
        <div id="titleArea">
            <h1>
                商品评论列表
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
                <button type="button" onclick="window.location='productCommentDetail?dowhat=add'">新增</button>
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
                        祖先ID
                    </th>
                    <th width="10%">
                        上级ID
                    </th>
                    <th width="10%">
                        商品ID
                    </th>
                    <th width="10%">
                        用户ID
                    </th>
                    <th width="10%">
                        评论内容
                    </th>
                    <th width="10%">
                        答复内容
                    </th>
                    <th width="10%">
                        操作日期
                    </th>
                    <th width="10%">
                        操作时间
                    </th>
                    <th width="10%">
                        楼层
                    </th>
                    <th width="10%">
                        类别（1评论 2问题）
                    </th>
                    <th width="10%">
                        评分
                    </th>
                    <th width="10%">
                        是否已读
                    </th>

                <tr>

                <%--表格内容--%>
                <%
                    for (Object dto : list) {
                        ProductComment productComment = (ProductComment) dto;
                        int id = productComment.getId();

                %>
                <tr class="tbody">
                    <td style="text-align: center">
                        <input type="checkbox" value="<%=productComment.getId()%>" id="id1" name="checkbox_id">
                    </td>
<%--
                    <td>
                        <a href="productCommentDetail?dowhat=edit&id=<%=id%>">
                            productComment<%=productComment.getId()%>
                        </a>
                    </td>
--%>
                    <td>
                        <%=productComment.getId()%>
                    </td>
                    <td>
                        <%=productComment.getAncestorId()%>
                    </td>
                    <td>
                        <%=productComment.getParentId()%>
                    </td>
                    <td>
                        <%=productComment.getProductId()%>
                    </td>
                    <td>
                        <%=productComment.getUserId()%>
                    </td>
                    <td>
                        <%=productComment.getComment()%>
                    </td>
                    <td>
                        <%=productComment.getReply()%>
                    </td>
                    <td>
                        <%=productComment.getOpDate()%>
                    </td>
                    <td>
                        <%=productComment.getOpTime()%>
                    </td>
                    <td>
                        <%=productComment.getDepth()%>
                    </td>
                    <td>
                        <%=productComment.getType()%>
                    </td>
                    <td>
                        <%=productComment.getRate()%>
                    </td>
                    <td>
                        <%=productComment.getHaveRead()%>
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
