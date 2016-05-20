<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.shop.commodity.Commodity" %>
<%@ page import="org.ailab.tem.wim.shop.commodity.CompareItem" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/shop/commodity/commodity.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>
<%
    List<CompareItem> compareItemList = (List<CompareItem>) request.getAttribute("compareItemList");
    int commodityNum = (Integer) request.getAttribute("commodityNum");
%>
<html>
<head>
    <title>商品对比</title>
</head>
<body>
<div id="fullContent">
    <%@include file="../../../header.jsp" %>
    <%@include file="../../../leftBar.jsp" %>
    <div id="mainContent">
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

        <table class="table table-bordered">
            <%
                CompareItem idItem = compareItemList.get(0);
                CompareItem nameItem = compareItemList.get(1);
                CompareItem photoItem = compareItemList.get(2);
            %>
            <tr class="commodityPhoto">
                <td width="10%">商品图片</td>
                <td width="30%">
                    <a href="<%=idItem.getFirstValue()%>">
                        <div class="photo"><img src="<%=photoItem.getFirstValue()%>"></div>
                        <p><%=nameItem.getFirstValue()%>
                        </p>
                    </a>
                </td>
                <td width="30%">
                    <a href="<%=idItem.getSecondValue()%>">
                        <p class="photo"><img src="<%=photoItem.getSecondValue()%>"></p>

                        <p><%=nameItem.getSecondValue()%>
                        </p>
                    </a>
                </td>
                <td width="30%">
                    <% if (commodityNum >= 3) { %>
                    <a href="<%=idItem.getThirdValue()%>">
                        <p class="photo"><img src="<%=photoItem.getThirdValue()%>"></p>

                        <p><%=nameItem.getThirdValue()%>
                        </p>
                    </a>
                    <%} else {%>
                    <a href="<%=idItem.getThirdValue()%>">
                        <p class="photo">暂无对比项目</p>

                        <p>添加
                        </p>
                    </a>
                    <%}%>
                </td>
            </tr>
            <%--表格内容--%>
            <%
                for (int i = 3; i < compareItemList.size(); i++) {
                    CompareItem item = compareItemList.get(i);

            %>
            <tr class="tbody <%--<%if(i %2==0){%>grayTD<%}%>--%>">
                <%--<td>--%>
                <%--<img width="50px" src="<%=item.getFirstValue()%>">--%>
                <%--</td>--%>
                <td>
                    <%=item.getName()%>
                </td>
                <td>
                    <%=StringUtil.replaceNullWithHorizontal(item.getFirstValue())%>
                </td>
                <td>
                    <%=StringUtil.replaceNullWithHorizontal(item.getSecondValue())%>
                </td>
                <td>
                    <%if (commodityNum >= 3) {%>
                    <%=StringUtil.replaceNullWithHorizontal(item.getThirdValue())%>
                    <%}%>
                </td>
            </tr>
            <%}%>
        </table>


    </div>
    <%@include file="../../../footer.jsp" %>
</div>

</body>
</html>