<META http-equiv="X-UA-Compatible" content="IE=9">
</META>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>超毅音乐</title>
    <meta name="description" content="music"/>
</head>
<body>

<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<%
    String ctx = request.getContextPath();
%>

<div id="fullContent">

    <div id="header">
        <div class="logo">
            <a class="brand" href="index.html">
                <img src="images/logo-text.gif" alt="logo" style="height:60%;margin-top: 10px;">
            </a>
        </div>
        <div id="buttons" class=" Clearfix">
            <div id="login" class="button">登录</div>
            <div id="register" class="button">注册</div>
            <div id="search"><input></div>
        </div>
    </div>

    <div id="navbar" class="navbar navbar-inverse">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav" id="menu">
                <li><a class="scroll" href="<%=ctx%>/home"><i class="icon-user"></i>首页<span
                        class="arrow-left"></span></a></li>
                <li><a class="scroll" href="<%=ctx%>/productList"><i class="icon-gear"></i>商城<span class="arrow-left"></span></a>
                </li>
                <li><a class="scroll" href="<%=ctx%>/home#product"><i class="icon-briefcase"></i>产品<span
                        class="arrow-left"></span></a>
                </li>
                <li><a class="scroll" href="<%=ctx%>/musicHall"><i class="icon-dollar"></i>音乐<span class="arrow-left"></span></a>
                </li>
                <li><a class="scroll" href="<%=ctx%>/forum"><i class="icon-comments"></i>论坛<span
                        class="arrow-left"></span></a></li>
                <li><a class="scroll"href="<%=ctx%>/home#contact"><i class="icon-plus"></i>联系方式<span class="arrow-left"></span></a>
                </li>
                <li><a class="scroll" href="#contact"><i class="icon-plus"></i>企业信息<span class="arrow-left"></span></a>
                </li>
                <li><a class="scroll" href="#contact"><i class="icon-plus"></i>业界动态<span class="arrow-left"></span></a>
                </li>
                <li><a class="scroll" href="#contact"><i class="icon-plus"></i>相关链接<span class="arrow-left"></span></a>
                </li>
            </ul>
        </div>
    </div>

    <div id="footer">
        <div id="slogan">
            创新科技，带来更多生活乐趣
        </div>
    </div>
</div>
<script language="javascript" src="js/jquery-1.11.0.js"></script>
<script language="javascript" src="js/newHome.js"></script>
</body>
