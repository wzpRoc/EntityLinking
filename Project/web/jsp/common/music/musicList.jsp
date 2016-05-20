<META http-equiv="X-UA-Compatible" content="IE=9">
</META>
<%@ page import="java.util.List" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.wimfra.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%--<%@include file="../../commonJS.jsp"%>--%>

<link href="css/base.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<link href="css/list.css" rel="stylesheet" type="text/css"/>
<link href="css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="jsp/common/music/musicList.css" rel="stylesheet" type="text/css"/>


<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();

%>

<html>
<head>
    <title>音乐列表</title>
</head>
<body>


<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../leftBar.jsp" %>

    <script>
        var userName = "<%=user == null ? "" : user.getNicName()%>";
        var userId = "<%=user==null?"":user.getId() %>"
    </script>
    <div id="mainContent" class="Clearfix">

        <form name="mainForm" id="mainForm" method="post" action="musicList">
            <s:hidden name="clearSession"/>
            <input type="hidden" name="submitTag" value="1"/>
            <s:hidden name="dowhat"/>

            <%--查询条件--%>
            <div id="searchArea">
                <span id="search">
                    <img src="./img/front/searchSmall.png">
                </span>
                <span id="shopCart">
                    <img src="./img/front/shoppingCart.png">
                </span>
                <span id="wish">
                    <img src="./img/front/wish.png">
                </span>
            </div>



            <%--查询结果--%>
            <div id="dataArea" class=" Clearfix" style="padding: 10px">
                <%
                    for (Object dto : list) {
                        Album album = (Album) dto;
                        int id = album.getId();

                %>
                <div class="album">
                    <div class="albumImg">
                        <a href="albumDetail?dowhat=edit&name=<%=id%>">
                            <img src="<%=StringUtil.ifEmpty(album.getPhotoRelUrl(), "img/productPic_Default.jpg")%>" onerror="this.src='img/productPic_Default.jpg'"/>
                        </a>
                    </div>
                    <div class="albumComment">
                        <p><%=album.getDescription()%></p>
                    </div>
                    <div class="favor_label">
                        <a onclick="addMusicCart(<%=id%>)">收藏</a>
                    </div>
                    <div class="enter_label">
                        <a>详细信息 》》》</a>
                    </div>
                </div>
                <%
                    }
                %>
                <br/>
            </div>
            <%@include file="../../queryNavigation.jsp" %>
        </form>
    </div>

    <%@include file="../../footer.jsp" %>
</div>
<%@include file="./musicCart.jsp" %>
</body>
</html>


<%@include file="../../commonJS.jsp" %>
<script type="text/javascript" src="js/jquery-validate.min.js"></script>
<%--<script type="text/javascript" src="js/jquery-validate.min.js"></script>--%>
<script type="text/javascript" src="js/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="jsp/common/music/musicList.js"></script>
