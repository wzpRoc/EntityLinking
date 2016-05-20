<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="s" uri="/struts-tags.tld" %>
<%@ page import="org.ailab.tem.wim.user.User" %>
<%@ page import="org.ailab.tem.wim.user.UserDetailAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) (new UserDetailAction()).getUser();
    String styleStrOfSex = "switch-off";
    if (user != null) {
        if (user.getSex() == 1)
            styleStrOfSex = "switch-on";
    }
%>

<%@include file="../../commonJS.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>欢迎加入</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<!-- Bootstrap -->
<link rel="stylesheet" href="css/bootstrap/bootstrap-switch.css"/>
<link rel="stylesheet" href="css/bootstrap/flat-ui-fonts.css">
<link rel="stylesheet" href="css/bootstrap/bootstrap-new.css">
<link href="css/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="js/user.js"></script>
<script type="text/javascript" src="js/jquery-validate.min.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</head>
<body>
<h3>编辑个人资料</h3>

<div class="container">
<form id="editForm" action="userInfo?saveTag=1" class="form-horizontal" method="post">
<s:hidden name="clearSession"/>
<s:hidden name="saveTag"/>
<s:hidden name="dowhat"/>

<div class="panel-group" id="accordion">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-prescNo">
                <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#BasicInfo">
                    基本信息
                </a>
            </h4>
        </div>
        <div id="BasicInfo" class="panel-collapse collapse in">
            <div class="panel-body">
                <div class="form-group row">
                    <tab for="username" class="col-sm-2 control-label">用户名<span
                            class="glyphicon-asterisk"></span></tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.username" id="username" data-validate="username" data-describedby="usernameMessages"
                                     data-description="username"/>
                    </div>

                    <tab for="nicname" class="col-sm-2 control-label">昵称<span
                            class="glyphicon-asterisk"></span></tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.nicName" id="nicName" data-validate="nicName" data-describedby="nicNameMessages"
                                     data-description="nicName"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label id="usernameMessages" class="col-sm-offset-2 col-sm-3"></label>
                    <label id="nicNameMessages" class="col-sm-offset-2 col-sm-3"></label>
                </div>

                <div class="form-group row">
                    <tab for="name" class="col-sm-2 control-label">真实姓名<span
                            class="glyphicon-asterisk"></span></tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.realName" id="realName" data-validate="nicName" data-describedby="realNameMessages"
                                     data-description="realName"/>
                    </div>

                    <tab class="col-sm-2 control-label">性别</tab>
                    <div class="col-sm-3">
                        <div id="label-switch" class="make-switch" data-on-label="男" data-off-label="女"
                             data-off="danger">
                            <input id="sexCheck" type="checkbox" class="form-control">
                            <s:hidden name="dto.sex" id="sex" />
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label id="realNameMessages" class="col-sm-offset-2 col-sm-3"></label>
                </div>


                <div class="form-group row">

                    <tab for="birthday" class="col-sm-2 control-label">生日</tab>
                    <div class="input-group pubdate form_date col-sm-3" data-pubdate="" data-pubdate-format="yyyy-mm-dd"
                         data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                        <s:textfield cssClass="form-control" size="16" id="birthday" name="dto.birthday"
                                     readonly='true'/>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>

                    <tab for="degree" class="col-sm-2 control-label">学历</tab>
                    <div class="col-sm-3">
                        <div class="input-group-btn dropdown">
                            <button id="degree" type="button" class="btn btn-primary dropdown-toggle"
                                    data-toggle="dropdown">选择学历<span
                                    class="caret"></span></button>
                            <ul class="dropdown-menu">
                                <li><a onclick="$('#degree').text('本科');$('#hiddenDegree').val('1');">本科</a></li>
                                <li><a onclick="$('#degree').text('研究生');$('#hiddenDegree').val('2');">研究生</a></li>
                                <li><a onclick="$('#degree').text('博士及以上');$('#hiddenDegree').val('3');">博士及以上</a></li>
                                <li><a onclick="$('#degree').text('其他');$('#hiddenDegree').val('4');">其他</a></li>
                            </ul>
                            <s:hidden name="dto.degree" id="hiddenDegree"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                </div>

                <div class="form-group row">
                    <tab for="telephone" class="col-sm-2 control-label">固定电话</tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.telephone" id="telephone"/>
                    </div>
                    <tab for="mobilephone" class="col-sm-2 control-label">移动电话</tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.mobilephone" id="mobilephone"/>
                    </div>
                </div>
                <div class="form-group row">
                </div>

                <div class="form-group row">
                    <tab for="address" class="col-sm-2 control-label">通信地址</tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.address" id="address"/>
                    </div>

                    <tab for="email" class="col-sm-2 control-label">邮箱<span
                            class="glyphicon-asterisk"></span></tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.email" id="email" data-validate="email" data-describedby="emailMessages"
                                     data-description="email"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label id="emailMessages" class="col-sm-offset-7 col-sm-3"></label>
                </div>

                <div class="form-group row">
                    <tab for="carType" class="col-sm-2 control-label">车型</tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.carType" id="carType"/>
                    </div>

                    <tab for="carPurchaseDate" class="col-sm-2 control-label">购车日期</tab>
                    <div class="input-group pubdate form_date col-sm-3" data-pubdate="" data-pubdate-format="yyyy-mm-dd"
                         data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                        <s:textfield cssClass="form-control" size="16" id="carPurchaseDate" name="dto.carPurchaseDate"
                                     readonly="true"/>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>
                </div>
                <div class="form-group row">
                </div>

                <div class="form-group row">
                    <tab for="certificateType" class="col-sm-2 control-label">证件类型</tab>
                    <div class="col-sm-3">
                        <div class="input-group-btn dropup">
                            <button id="certificateType" type="button" class="btn btn-primary dropdown-toggle"
                                    data-toggle="dropdown">
                                选择类型 <span class="caret"></span></button>
                            <ul class="dropdown-menu">
                                <li>
                                    <a onclick="$('#certificateType').text('身份证');$('#hiddenCertificateType').val('1');">身份证</a>
                                </li>
                                <li>
                                    <a onclick="$('#certificateType').text('军官证');$('#hiddenCertificateType').val('2');">军官证</a>
                                </li>
                            </ul>
                            <s:hidden name="dto.certificateType" id="hiddenCertificateType"/>
                        </div>
                    </div>

                    <tab for="certificateNo" class="col-sm-2 control-label">证件号码</tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" placeholder="其他用户无法看到" name="dto.certificateNo"
                                     id="certificateNo"/>
                    </div>
                </div>
                <div class="form-group row">
                </div>

                <div class="form-group row">
                    <tab for="hobby" class="col-sm-2 control-label">爱好</tab>
                    <div class="col-sm-3">
                        <s:textfield cssClass="form-control" name="dto.hobby" id="hobby"/>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-prescNo">
                <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#ExtendInfo">
                    绑定设备
                </a>
            </h4>
        </div>
        <div id="ExtendInfo" class="panel-collapse collapse">
            <div id="device" class="panel-body">
                <div class="row form-group">
                    <button type="button" class="btn btn-info col-sm-offset-1 col-sm-1" onclick="addDeviceBinding('', '', 0, '绑定')">添加绑定</button>
                    <div class="col-sm-3">
                        <span id="bindMessage" class="control-label" style="color:red"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-prescNo">
                <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#SecretInfo">
                    修改密码
                </a>
            </h4>
        </div>
        <div id="SecretInfo" class="panel-collapse collapse">
            <div class="panel-body">

                <div class="form-group row">
                    <tab for="password" class="col-sm-2 control-label"> 输入密码<span
                            class="glyphicon-asterisk"></span></tab>

                    <div class="col-sm-5">
                        <s:password cssClass="form-control" name="dto.password" id="password"
                               data-validate="password"  placeholder="置空则不修改密码"
                               data-describedby="passwordMessages" data-description="password"/>
                    </div>
                    <tab name="passwordMessages" class="col-sm-5"></tab>
                </div>

                <div class="form-group row">
                    <tab for="confirm" class="col-sm-2 control-label">
                        确认密码<span class="glyphicon-asterisk"></span></tab>

                    <div class="col-sm-5">
                        <s:password cssClass="form-control" name="confirm" id="confirm"
                               data-validate="confirm"  placeholder="确认修改密码"
                               data-describedby="confirmMessages" data-description="confirm"/>
                    </div>
                    <tab name="confirmMessages" class="col-sm-5"></tab>
                </div>

            </div>
        </div>
    </div>
</div>
<input type="hidden" id="hiddenArea1" value=""/>
<input type="hidden" id="hiddenArea2" value=""/>
<div class="row form-group">
    <button type="submit" class="btn btn-primary col-sm-offset-3 col-sm-3">保存</button>
    <button type="button" class="btn col-sm-3" onclick="location='userInfo'"> 返回</button>
</div>
</form>
</div>
<script>


    function changeBinding(element) {
        $parentNode = $(element).closest('div');
        var deviceNo = $parentNode.find(".deviceNo").val();
        var purchasingDate = $parentNode.find(".bindingDate").val();

        if ($(element).val() == '0') {
            bindDevice(element, deviceNo, purchasingDate, true);
        }
        else {
            bindDevice(element, deviceNo, purchasingDate, false);
        }
    }

    $(".form_date").on("click",function(){
        $(".form_date").datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        })
    }   );

    $('#label-switch').on('switch-change', function (e, data) {
        var index = $('#label-switch').children(0).attr("class").indexOf("switch-on");
        if (index == -1)
            $("#sex").val("2");
        else
            $("#sex").val("1");
    });

    $(document).ready(function () {
        $("#label-switch div:first-child").attr("class", "<%=styleStrOfSex%>");

        var certificateType = ["身份证", "军官证"];
        var certificate = <%=user.getCertificateType()%>;
        if (certificate > 0)
            $("#certificateType").text(certificateType[certificate - 1]);

        var degreeType = ["本科", "研究生", "博士生及以上", "其他"];
        degree = <%=user.getDegree()%>;
        if (degree > 0)
            $("#degree").text(degreeType[degree - 1]);

        setDeviceHtml();

    });

    $('#editForm').validate({
        onKeyup: true,
        sendForm: true,
        onChange: true,
        eachValidField: function () {

            $(this).closest('div').removeClass('has-error');
        },
        eachInvalidField: function () {

            $(this).closest('div').removeClass('has-success').addClass('has-error');
        },
        description: {
            username: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度须小于20,禁止含非法字符</span >',
                conditional: '<span  class="alert-error form-control" style="background-color: #f2dede">该用户名已被注册</span >',
                valid: ''
            },
            email: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">不符合邮箱格式</span >',
                conditional: '<span  class="alert-error form-control" style="background-color: #f2dede">该邮箱已被注册</span >',
                valid: ''
            },
            nicName: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度须小于20,禁止含非法字符</span >',
                valid: ''
            },
            realName: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度须小于20,禁止含非法字符</span >',
                valid: ''
            },
            password: {
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度应在6-16位的数字或字母</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值有效</span>'
            },
            confirm: {
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度应在6-16位的数字或字母</span >',
                conditional: '<span  class="alert-error form-control" style="background-color: #f2dede">验证不匹配，请重新输入</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值匹配</span>'
            }
        }
    });

    $.validateExtend({ username: {
        required: true,
        pattern: /^[\u4e00-\u9fa5\w]{1,20}$/,
        conditional: function (value) {
            if("<%=user.getUsername()%>" != value)
                checkValid(1, null);
            else
                return true;
            return value==$("#hiddenArea1").val();
        }
    },
        nicName: {
            required: true,
            pattern: /^[\u4e00-\u9fa5\w]{1,20}$/
        },
        realName: {
            required: true,
            pattern: /^[\u4e00-\u9fa5\w]{1,20}$/
        },
        email: {
            required: true,
            pattern: /\w+([-+.]\w+)*@\w+([-.]w+)*.\w+([-.\w]*)/,
            conditional: function (value) {
                if("<%=user.getEmail()%>" != value)
                    checkValid(2, "#email")
                else
                    return true;
                return value==$("#hiddenArea2").val();
            }
        },
        password: {
            pattern: /^[a-zA-Z0-9]{6,16}$/
        },
        confirm: {
            pattern: /^[a-zA-Z0-9]{6,16}$/,
            conditional: function (value) {
                return value == $("#password").val();
            }
        }
    });
</script>
</body>
</html>
