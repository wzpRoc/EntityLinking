<%--
  Created by IntelliJ IDEA.
  User: onechen
  Date: 13-12-11
  Time: 下午3:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
        超毅音乐
    </title>
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
                <a href="http://music.douban.com">超毅音乐</a>
            </div>
            <div class="nav-search">
                <form action="http://music.douban.com/subject_search" method="get">
                    <fieldset>
                        <legend>搜索：</legend>
                        <label for="inp-query">唱片名、表演者、条码、ISRC</label>
                        <div class="inp"><input id="inp-query" name="search_text" size="22" maxlength="60" value=""></div>
                        <div class="inp-btn"><input type="submit" value="搜索"></div>
                        <input type="hidden" name="cat" value="1003" />
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

