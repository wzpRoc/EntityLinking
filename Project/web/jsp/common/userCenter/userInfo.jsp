<%@ page import="java.util.List" %>
<%@ page import="org.ailab.tem.wim.user.User" %>
<%@ page import="org.ailab.wimfra.core.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ taglib prefix="w" uri="/wimfra-tags.tld" %>

<%@include file="../../commonJS.jsp" %>

<link rel="stylesheet" href="css/bootstrap/bootstrap-switch.css"/>
<link rel="stylesheet" href="css/bootstrap/flat-ui-fonts.css">
<link rel="stylesheet" href="css/bootstrap/bootstrap-new.css">
<link href="css/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="./css/common/header.css" rel="stylesheet" type="text/css"/>


<link href="css/style.css" rel="stylesheet" type="text/css"/>
<%--<link href="css/bootstrap/bootstrap-new.css" rel="stylesheet" type="text/css"/>--%>
<link href="css/bootstrap/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="css/globalFrame.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="js/user.js"></script>

<%--<%@include file="../../header_old.jsp" %>--%>

<html>
<head>
    <title>用户中心</title>
</head>
<body>

<div id="fullContent">
    <%@include file="../../header.jsp" %>
    <%@include file="../../leftBar.jsp" %>
    <div id="mainContent">

        <div class="container" id="userInfo" style="background-color: #FFFFFF; width: 800px">
            <legend>
                <h3>个人信息</h3>
            </legend>
            <div class="form-group row">
                <span class="col-sm-2 control-span">用户名</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.username" id="username"/>
                </div>

                <span class="col-sm-2 control-span">昵称</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.nicName" id="nicName"/>
                </div>
            </div>

            <div class="form-group row">
                <span for="name" class="col-sm-2 control-span">真实姓名</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.realName" id="realName"/>
                </div>

                <span class="col-sm-2 control-span">积分余额</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.points" id="points"/>
                </div>
            </div>

            <div class="form-group row">
                <span class="col-sm-2 control-span">等级</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.gradeStr" id="grade"/>
                </div>

                <span class="col-sm-2 control-span">等级分数</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.gradePoint" id="gradePoint"/>
                </div>
            </div>

            <div class="form-group row">
                <span class="col-sm-2 control-span">性别</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.sexStr" id="sex"/>
                </div>

                <span class="col-sm-2 control-span">生日</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.birthday" id="birthday"/>
                </div>
            </div>

            <div class="form-group row">
                <span class="col-sm-2 control-span">固定电话</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.telephone" id="telephone"/>
                </div>
                <span class="col-sm-2 control-span">移动电话</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.mobilephone" id="mobilephone"/>
                </div>
            </div>

            <div class="form-group row">
                <span class="col-sm-2 control-span">通信地址</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.address" id="address"/>
                </div>

                <span class="col-sm-2 control-span">邮箱</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.email" id="email"/>
                </div>
            </div>

            <div class="form-group row">
                <span class="col-sm-2 control-span">车型</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.carType" id="carType"/>
                </div>

                <span class="col-sm-2 control-span">购车日期</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.carPurchaseDate" id="carPurchaseDate"/>
                </div>
            </div>

            <div class="form-group row">
                <span class="col-sm-2 control-span">证件类型</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.certificateTypeStr" id="certificateType"/>
                </div>

                <span class="col-sm-2 control-span">证件号码</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" placeholder="其他用户无法看到" name="dto.certificateNo"
                             id="certificateNo"/>
                </div>
            </div>

            <div class="form-group row">
                <span class="col-sm-2 control-span">学历</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.degreeStr" id="degree"/>
                </div>
                <span class="col-sm-2 control-span">爱好</span>

                <div class="col-sm-3">
                    <s:label cssClass="control-label" name="dto.hobby" id="hobby"/>
                </div>
            </div>

            <div class="form-group row">
                <span class="col-sm-2 control-span">已绑定设备</span>
            </div>


        </div>
        <br><br>

        <div class="row">
            <button class="btn btn-primary col-sm-offset-2 col-sm-2" bype="button" onclick="location='userInfoEdit'">
                完善资料
            </button>
            <button bype="button" class="btn btn-default col-sm-2" onclick="location='index'">返回主页</button>
        </div>

    </div>

    <%@include file="../../footer.jsp" %>
    <script>
        $(document).ready(function () {
            addDeviceInfoToUserInfo();
        });

    </script>
</div>
</body>
</html>

