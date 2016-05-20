<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.song.Song" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp"%>

<link href="../css/base.css" rel="stylesheet" type="text/css"/>
<link href="../css/style.css" rel="stylesheet" type="text/css"/>
<link href="../css/list.css" rel="stylesheet" type="text/css"/>
<link href="../css/admin/admin.css" rel="stylesheet" type="text/css"/>
<link href="../jsp/admin/song/song.css" rel="stylesheet" type="text/css"/>
<link href="../css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>




<script language="javascript" src="../jsp/admin/song/song.js"></script>

<%
    Page listPage = (Page) request.getAttribute("page");
    List list = listPage.getList();
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>超毅音乐</title>
    <meta name="description" content="music" />
    <link href="../jsp/common/music/musicHome.css" rel="stylesheet" type="text/css">
    <link href="../jsp/common/music/global.css" rel="stylesheet" type="text/css">
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


    <%
        if(list!=null) {
    %>



<div class="span9" style="width:950px;margin:0 auto;">
    <div id="content">
        <h4>超毅搜索：</h4>
        <div class="grid-16-8 clearfix">
            <div class="article">
                <%
                    for (Object dto : list) {
                        Song song = (Song) dto;
                        int id = song.getId();
                %>
                <p class="ul first"></p>
                <table width="100%">
                    <tbody>
                        <tr class="item">
                            <td width="100" valign="top">
                                <img src="../jsp/common/song/dahua.jpg" alt="赵季平 - 大话西游">
                            </td>
                            <td valign="top">
                                <div class="p12">
                                    <a><span style="font-size:12px;"><a href="songDetail?dowhat=query&name=<%=id%>"><%=song.getTitle()%></a></span></a>
                                    <p class="pl"><%=song.getGenre()%></p>
                                </div>
                                <div class="star clearfix">
                                    <span class="allstar50"></span>
                                    <span class="rating_nums"><%=song.getCommentCount()%></span>
                                    <span class="pl">(3896人评价)</span>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <%
                    }
                %>
            </div>
            <div class ="aside">
                <div class ="ad">guangao</div>

            </div>
        </div>

    </div>



</div>

    <%
        }
    %>


<div class="footer" style="text-align: center; border-top: 1px solid #ccc; margin-top: 30px; padding-top: 30px;  background-color: rgb(25, 24, 24)">
    <div class="container">
        <p>True-E, Inc.</p>

        <p>MIT Licence</p>

        <p>Designed by YaTan | Powered by <a name="foot" href="http://twitter.github.io/bootstrap">Bootstrap</a>
        </p>
    </div>
</div>
</body>
</html>
