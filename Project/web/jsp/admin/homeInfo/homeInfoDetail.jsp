<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.homeInfo.HomeInfo" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../adminJS.jsp" %>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/detail.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/homeInfo/homeInfo.css" rel="stylesheet" type="text/css"/>
<script language="javascript" src="../js/jquery.form.js"></script>


<script language="javascript" src="../jsp/admin/homeInfo/homeInfo.js"></script>

<%
    HomeInfo homeInfo = (HomeInfo) request.getAttribute("dto");
    String path = request.getContextPath();
    String ctx = path + '/';
%>
<script>
    var path = "<%=path%>";
</script>

<html>
<head>
    <title>首页信息详情</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../header.jsp" %>
    <%@include file="../left.jsp" %>
    <div id="mainContent">
        <form name="mainForm" id="mainForm" method="post" action="homeInfoDetail">
            <input type="hidden" name="saveTag" value="1"/>
            <s:hidden name="dowhat"/>
            <s:hidden name="dto.id"/>
            <s:hidden id="pic_small" name="dto.pic"/>
            <s:hidden id="pic_big" name="dto.pic_big"/>

            <div id="titleArea">
                <h1>
                    首页信息详情
                </h1>
            </div>

            <%--功能按钮区--%>
            <div id="topArea">
                &nbsp;
                <div id="buttonArea">
                    <button type="submit">保存</button>
                    <button type="button" onclick="window.location='homeInfoList'">返回</button>
                </div>
            </div>
        </form>

        <%--数据表格区--%>
        <div id="dataArea">
            <form id="pic_small_form" enctype="multipart/form-data">
                <div class="picFrom">
                    小图更改：
                    <input class="small_img_input" type="file" name="fileupload"
                           onchange="uploadSmallImage();return false;">
                    <!--用于显示图片的表单 -->
                    <div class="showImage_small" id="showImage_small">
                        <img src="<%=homeInfo.getCtxPhoto(ctx, false)%>">
                    </div>
                </div>
            </form>

            <form id="pic_big_form" enctype="multipart/form-data">
                <div class="picFrom">
                    大图更改：
                    <input class="small_img_input" type="file" name="fileupload" onchange="uploadBigImage();">
                    <!--用于显示图片的表单 -->
                    <div class="showImage_big" id="showImage_big">
                        <img src="<%=homeInfo.getCtxPhoto(ctx, true)%>">
                    </div>
                </div>
            </form>
            <%--<table id="dataTable">--%>
            <%--<tr>--%>
            <%--<td class="label">ID:</td>--%>
            <%--<td class="value"><s:textfield name="dto.id"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td class="label">小图:</td>--%>
            <%--<td class="value"><s:textfield name="dto.pic"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td class="label">大图:</td>--%>
            <%--<td class="value"><s:textfield name="dto.pic_big"/></td>--%>
            <%--</tr>--%>

            <%--</table>--%>
        </div>

    </div>
    <%@include file="../bottom.jsp" %>
</div>


</body>
</html>

<%@ include file="/jsp/msg.jsp" %>
