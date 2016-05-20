<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.album.Album" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="jsp/album/album.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>




<script language="javascript" src="jsp/album/album.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <title>albumList</title>
</head>
<body>
<div id="db-global-nav" class="global-nav">
    <div class="bd">
        <div class="top-nav-info">
            <a href="#">登录</a>
            <a href="#">注册</a>
        </div>
        <div class="global-nav-items">
            <ul>
                <li>
                    <a href="#" >超毅音乐True-e</a>
                </li>
                <li>
                    <a href="#" >首页</a>
                </li>e
                <li>
                    <a href="#" >商城</a>
                </li>
                <li>
                    <a href="#" >产品</a>
                </li>
                <li class="on">
                    <a href="#">音乐</a>
                </li>
                <li>
                    <a href="#" >论坛</a>
                </li>
                <li>
                    <a href="#" >联系</a>
                </li>
            </ul>
        </div>
    </div>
</div>

<div id="music-nav" class="nav">
    <div class="nav-content">
        <div class="nav-main">
            <div class="nav-logo">
                <a>超毅音乐</a>
            </div>
            <div class="nav-search">
                <form name="mainForm" id="mainForm" method="get" action="songList">
                    <s:hidden name="clearSession"/>
                    <s:hidden name="submitTag"/>
                    <s:hidden name="dowhat"/>
                    <input type="hidden" name="fuzzyMatch" value="true"/>
                    <fieldset>
                        <legend>搜索：</legend>
                        <%--<label for="inp-query">唱片名、表演者、条码、ISRC</label>--%>
                        <div class="inp"><input id="inp-query" name="condition.name" size="22" maxlength="60" value=""></div>
                        <div class="inp-btn"><input type="submit"></div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
    <div class="nav-secondary">
        <div class="nav-items nav-anon">
            <ul>
                <li><a href="http://music.douban.com/artists/">音乐人</a></li>
                <li><a href="http://music.douban.com/chart">排行榜</a></li>
                <li><a href="http://music.douban.com/tag/">分类浏览</a></li>
                <li><a href="http://douban.fm/">豆瓣FM</a></li>
            </ul>
        </div>

    </div>
</div>


</body>
</html>
