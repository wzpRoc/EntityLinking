<%@ page import="org.ailab.tem.wim.user.User" %>
<META http-equiv="X-UA-Compatible" content="IE=9">
</META>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctx = request.getContextPath();
    String moduleName = (String) request.getAttribute("moduleName");
    String dowhat = (String) request.getAttribute("dowhat");
    String dowhatInList = (String) request.getAttribute("dowhatInList");
%>
<ul id="menu">
    <li class="topMenu">
        <a href="<%=ctx%>/home" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/home.png"></span>
            <span class="selected"><img src="img/front/icon/selected/home.png"></span>
            首页
        </a>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/productList" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/shop.png"></span>
            <span class="selected"><img src="img/front/icon/selected/shop.png"></span>
            商城
        </a>
        <ul class="subMenu">
            <li><a href="<%=ctx%>/commodityList?topCategoryId=001">数字唱机</a></li>
            <li><a href="<%=ctx%>/commodityList?topCategoryId=002">音乐</a></li>
            <li><a href="<%=ctx%>/commodityList?topCategoryId=003">配件</a></li>
        </ul>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/productList" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/product.png"></span>
            <span class="selected"><img src="img/front/icon/selected/product.png"></span>
            产品
        </a>
        <ul class="subMenu">
            <li><a>家用数字唱机</a></li>
            <li><a>车载数字唱机</a></li>
            <li><a>便携数字唱机</a></li>
        </ul>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/musicList" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/music.png"></span>
            <span class="selected"><img src="img/front/icon/selected/music.png"></span>
            音乐
        </a>
        <ul class="subMenu">
            <li><a href="<%=ctx%>/musicList">音乐欣赏解读</a></li>
            <li><a>数字音频文件</a></li>
            <li><a>精彩录音文件</a></li>
        </ul>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/forumTopicList" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/forum.png"></span>
            <span class="selected"><img src="img/front/icon/selected/forum.png"></span>
            论坛
        </a>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/forum" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/service.png"></span>
            <span class="selected"><img src="img/front/icon/selected/service.png"></span>
            服务
        </a>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/contact" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/contacts.png"></span>
            <span class="selected"><img src="img/front/icon/selected/contacts.png"></span>
            联系方式
        </a>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/contact" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/enterpriseInfo.png"></span>
            <span class="selected"><img src="img/front/icon/selected/enterpriseInfo.png"></span>
            企业信息
        </a>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/contact" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/news.png"></span>
            <span class="selected"><img src="img/front/icon/selected/news.png"></span>
            业界动态
        </a>
    </li>
    <li class="topMenu">
        <a href="<%=ctx%>/relatedLinksList" class="menuItem">
            <span class="normal"><img src="img/front/icon/normal/relatedLinks.png"></span>
            <span class="selected"><img src="img/front/icon/selected/relatedLinks.png"></span>
            相关链接
        </a>
    </li>
</ul>
<script language="javascript" src="js/jquery-1.11.0.js"></script>
<script language="javascript" src="js/newHome.js"></script>