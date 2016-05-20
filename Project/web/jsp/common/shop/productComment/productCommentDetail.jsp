<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.productComment.ProductComment" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../../commonJS.jsp"%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/detail.css" rel="stylesheet" type="text/css"/>
<link href="jsp/productComment/productComment.css" rel="stylesheet" type="text/css"/>


<script language="javascript" src="js/tcal.js"></script>
<script language="javascript" src="js/string.js"></script>
<script language="javascript" src="js/time.js"></script>

<script language="javascript" src="jsp/productComment/productComment.js"></script>

<%
    ProductComment productComment = (ProductComment) request.getAttribute("dto");
%>

<html>
<head>
    <title>商品评论详情</title>
</head>
<body>

<div id="mainContent">
    <form name="mainForm" id="mainForm" method="post" action="productCommentDetail">
        <input type="hidden" name="saveTag" value="1"/>
        <s:hidden name="dowhat"/>
        <s:hidden name="dto.id"/>

        <div id="titleArea">
            <h1>
                商品评论详情
            </h1>
        </div>

    <%--功能按钮区--%>
        <div id="topArea">
            &nbsp;
            <div id="buttonArea">
                <button type="submit">保存</button>
                <button type="button" onclick="window.location='productCommentList'">返回</button>
            </div>
        </div>

        <%--数据表格区--%>
        <div id="dataArea">
            <table id="dataTable">
                <tr>
					<td class="label">ID:</td>
					<td class="value"><s:textfield name="dto.id"/></td>
				</tr>
				<tr>
					<td class="label">祖先ID:</td>
					<td class="value"><s:textfield name="dto.ancestorId"/></td>
				</tr>
				<tr>
					<td class="label">上级ID:</td>
					<td class="value"><s:textfield name="dto.parentId"/></td>
				</tr>
				<tr>
					<td class="label">商品ID:</td>
					<td class="value"><s:textfield name="dto.productId"/></td>
				</tr>
				<tr>
					<td class="label">用户ID:</td>
					<td class="value"><s:textfield name="dto.userId"/></td>
				</tr>
				<tr>
					<td class="label">评论内容:</td>
					<td class="value"><s:textfield name="dto.comment"/></td>
				</tr>
				<tr>
					<td class="label">答复内容:</td>
					<td class="value"><s:textfield name="dto.reply"/></td>
				</tr>
				<tr>
					<td class="label">操作日期:</td>
					<td class="value"><s:textfield name="dto.opDate"/></td>
				</tr>
				<tr>
					<td class="label">操作时间:</td>
					<td class="value"><s:textfield name="dto.opTime"/></td>
				</tr>
				<tr>
					<td class="label">楼层:</td>
					<td class="value"><s:textfield name="dto.depth"/></td>
				</tr>
				<tr>
					<td class="label">类别（1评论 2问题）:</td>
					<td class="value"><s:textfield name="dto.type"/></td>
				</tr>
				<tr>
					<td class="label">评分:</td>
					<td class="value"><s:textfield name="dto.rate"/></td>
				</tr>
				<tr>
					<td class="label">是否已读:</td>
					<td class="value"><s:textfield name="dto.haveRead"/></td>
				</tr>

            </table>
        </div>
    </form>
</div>

</body>
</html>

<%@ include file="../msg.jsp" %>
