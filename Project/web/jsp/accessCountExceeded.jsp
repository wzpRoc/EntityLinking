<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>超毅音乐</title>
    <meta name="description" content="music"/>
</head>
<body>


<%@include file="header_old.jsp" %>

<link href="<%=ctx%>/css/base.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx%>/css/style.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx%>/css/common/header.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx%>/css/common/footer.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx%>/css/common/globalNav.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx%>/jsp/common/music/music.css" rel="stylesheet" type="text/css"/>

<div style="width: 100%; height:500px; text-align: center; vertical-align: middle; margin-right: auto; margin-left: auto; margin-top: 200px">
    <div style="font-size: 18px; color: #000000">
        <b>您的访问次数超过限制，请联系系统管理员！</b>
    </div>
</div>


<%@include file="footer_old.jsp" %>

</body>
</html>

<script type="text/javascript" src="js/jquery-validate.min.js"></script>
<script type="text/javascript" src="js/bootstrap-switch.min.js"></script>

<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/interface/AjaxLogin.js"></script>
<script type="text/javascript" src="js/login.js"></script>
