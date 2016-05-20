<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="org.ailab.tem.wim.user.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script language="javascript" src="js/jquery-1.11.0.js"></script>
<%
    User user = (User) request.getAttribute("user");
    boolean isLogin = (user != null);
%>
<div id="header">
    <div id="logo">
        <a class="brand" href="index.html">
            <img src="img/front/logo.png" alt="logo">
        </a>
    </div>

    <div id="buttons" class=" Clearfix">
        <div id="language">
            <span id="Chinese">
                <img src="img/front/Chinese.png" alt="中文">
            </span>
            <span id="English">
                <img src="img/front/English.png" alt="英文">
            </span>
        </div>

        <div id="loginAndRegister">
            <span id="login">
                <a href="#" data-toggle="modal" data-target="#loginModal" id="loginBut">
                    <img src="img/front/login.png" alt="登录">
                </a>
            </span>
            <span id="loginAndRegisterMiddle">
                <img src="img/front/loginAndRegisterMiddle.png" alt="">
            </span>
            <span id="register">
                <a href="#" data-toggle="modal" data-target="#registerModal">
                    <img src="img/front/register.png" alt="登录">
                </a>
            </span>
        </div>

        <div id="search">
            <input id="searchInput"><img src="img/front/seacher.png" alt="登录">
        </div>
    </div>
</div>



<div class="modal fade" style="width:300px; display: none;" id="loginModal" tabindex="-1" role="dialog"
     aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="loginModalLabel"><strong>用户登录</strong></h4>

                <div id="message" class="control-label" style="color:red"></div>
            </div>
            <div class="modal-body">

                <div>
                    <form id="formForLogin" class="form-horizontal" method="post" onSubmit="return login();">
                        <div class="form-group row">
                            <div class="col-sm-offset-1 col-sm-9">
                                <input id="usernameForLogin" autofocus="autofocus" class="form-control"
                                       placeholder="输入用户名或邮箱"
                                       name="usernameForLogin" type="text"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-offset-1 col-sm-9">
                                <input id="passwordForLogin" class="form-control" placeholder="输入密码"
                                       name="passwordForLogin"
                                       type="password"/>
                            </div>
                        </div>

                        <p class="col-sm-offset-1">
                            <label style="color:black; font-weight: lighter" for="chkSave"
                                   class="checkbox" contenteditable="false">
                                <input id="chkSave"
                                       type="checkbox"
                                       onclick="this.value='false';if(this.checked) this.value='true'"/>
                                记住我 </label></p>

                        <div class="row form-group">
                            <button type="submit" class="btn btn-primary col-sm-offset-3 col-sm-3">登录</button>
                            <button type="reset" class="btn col-sm-3">重置</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal" id="leaveBut">离开</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade" style="display: none" id="registerModal" tabindex="-1" role="dialog"
     aria-labelledby="registerModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                        onclick="$('#result').text('');">&times;</button>
                <h4 class="modal-title" id="registerModalLabel"><strong>用户注册</strong></h4>
                <span id="result" style="color:red"></span>
            </div>
            <div class="modal-body">

                <div>
                    <form id="formForRegister" action="" class="form-horizontal" method="post"
                          onSubmit="return register();">
                        <div class="form-group row">
                            <tab for="username" class="col-sm-2 control-label">用户名<span
                                    class="glyphicon-asterisk"></span></tab>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="username" id="username"
                                       data-validate="username" data-describedby="usernameMessages"
                                       data-description="username"/>
                            </div>
                            <tab name="usernameMessages" class="col-sm-5"></tab>
                        </div>

                        <div class="form-group row">
                            <tab for="nicName" class="col-sm-2 control-label"> 昵称<span
                                    class="glyphicon-asterisk"></span></tab>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="nicName" id="nicName"
                                       data-validate="nicName"
                                       data-describedby="nicNameMessages" data-description="nicName"/>
                            </div>
                            <tab name="nicNameMessages" class="col-sm-5"></tab>
                        </div>

                        <div class="form-group row">
                            <tab for="realName" class="col-sm-2 control-label"> 真实姓名<span
                                    class="glyphicon-asterisk"></span></tab>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="realName" id="realName"
                                       data-validate="realName"
                                       data-describedby="realNameeMessages" data-description="realName"/>
                            </div>
                            <tab name="nicNameMessages" class="col-sm-5"></tab>
                        </div>

                        <%--<div class="form-group row">
                            <tab class="col-sm-2 control-label"> 性别<span class="glyphicon-asterisk"></span></tab>
                            &nbsp;&nbsp;
                            <div id="label-switch" class="make-switch" data-on-label="男生" data-off-label="女生"
                                 data-off="danger">
                                <input type="checkbox" checked>
                            </div>
                        </div>--%>

                        <div class="form-group row">
                            <tab for="email" class="col-sm-2 control-label"> 邮箱地址<span
                                    class="glyphicon-asterisk"></span></tab>

                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="email" id="email" data-validate="email"
                                       data-describedby="emailMessages" data-description="email"/>
                            </div>
                            <tab name="emailMessages" class="col-sm-5"></tab>
                        </div>

                        <div class="form-group row">
                            <tab for="password" class="col-sm-2 control-label"> 输入密码<span
                                    class="glyphicon-asterisk"></span></tab>

                            <div class="col-sm-5">
                                <input type="password" class="form-control" name="password" id="password"
                                       data-validate="password"
                                       data-describedby="passwordMessages" data-description="password"/>
                            </div>
                            <tab name="passwordMessages" class="col-sm-5"></tab>
                        </div>

                        <div class="form-group row">
                            <tab for="confirm" class="col-sm-2 control-label">
                                确认密码<span class="glyphicon-asterisk"></span></tab>

                            <div class="col-sm-5">
                                <input type="password" class="form-control" name="confirm" id="confirm"
                                       data-validate="confirm"
                                       data-describedby="confirmMessages" data-description="confirm"/>
                            </div>
                            <tab name="confirmMessages" class="col-sm-5"></tab>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="auth" id="auth"
                                       Onfocus="createCode()" data-validate="auth" data-describedby="authMessages"
                                       data-description="auth">
                            </div>
                            <div class="col-sm-3" readonly="readonly">
                                <input type="text" class="form-control" onclick="createCode()" disabled="disabled"
                                       readonly
                                       id="checkCode" value="验证码"
                                       style="width:80px"/>
                            </div>
                            <tab name="authMessages" class="col-sm-5"></tab>
                        </div>
                        <input type="hidden" id="hiddenArea1" value=""/>
                        <input type="hidden" id="hiddenArea2" value=""/>

                        <div class="row form-group">
                            <button type="submit" class="btn btn-primary col-sm-offset-3 col-sm-3">提交</button>
                            <button type="reset" class="btn col-sm-3" onclick="$('#result').text('');">重置</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="close" class="btn btn-info" data-dismiss="modal">离开</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade" style="display: none" id="retakePasswordModal" tabindex="-1" role="dialog"
     aria-labelledby="registerModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="retakePasswordModalLabel"><strong>找回密码</strong></h4>
            </div>
            <div class="modal-body">
                <div class="span12">
                    <div class="alert alert-info">
                        <button type="button" class="close" data-dismiss="alert">×</button>
                        <h4>
                            <strong>提示!</strong>
                        </h4> 请注意你的个人隐私安全.
                    </div>
                    <form class="form-inline">
                        <fieldset>
                            <div class="row form-group col-sm-12">
                                <label class="control-label col-sm-offset-1">发送至邮箱</label></br></br>
                                <div class="col-sm-offset-1 col-sm-6">
                                    <input type="text" id="emailAddress" autofocus="autofocus" class="form-control"
                                           placeholder="Type mailbox"
                                           data-required data-validate="email0" data-describedby="emailMessages0"
                                           data-description="email0"/>
                                </div>
                                <tab name="emailMessages0" class="col-sm-4"></tab>
                            </div>
                            <div class="col-sm-offset-1 col-sm-6"><span class="help-block" id="timeInfo"
                                                                        style="display:none">若未收到邮件，30秒后可选在重发。</span>
                            </div>
                            </br></br>
                            <div class="row form-group col-sm-12">
                                <div class="col-sm-offset-1">
                                    <input class="btn btn-info" id="sendPwd" type="submit" onclick="send()" value="发送"/>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-info" data-dismiss="modal">离开</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
</div>

<script language="javascript">

    $(document).ready(function () {
        <%if(isLogin){%>
        $("#login").empty().html('<a href="userInfo"><%=user.getNicName()%></a>');
        $("#register").empty().html('<a id="logoutBut" onclick="logout()">退出</a>');
        <%}%>
    });

    var isError;
    function send() {
        var address = document.getElementById("emailAddress");
        if (isError == "false" && address != null) {
            var times = 30;//倒计时时间
            var spanObj = document.getElementById("timeInfo");
            spanObj.style.display = "block";
            document.getElementById("sendPwd").disabled = true;
            spanObj.innerHTML = "若未收到邮件，" + times + "秒后可选在重发。";

            var t = setTimeout(function countTime() {
                if (times == 1) {//最后一秒后隐藏
                    if (typeof(t) != "undefined")//停止计时器
                        clearTimeout(t);
                    spanObj.style.display = "none";
                    document.getElementById("sendPwd").disabled = false;
                } else {
                    times--;
                    spanObj.innerHTML = "若未收到邮件，" + times + "秒后可选在重发。";
                    t = setTimeout(countTime, 1000);
                }
            }, 1000);
        }
    }

    $('form').validate({
        onKeyup: true,
        sendForm: false,
        onSubmit: false,
        onChange: true,
        eachValidField: function () {
            isError = "false";
            $(this).closest('div').removeClass('has-error').addClass('has-success');
        },
        eachInvalidField: function () {
            isError = "true";
            $(this).closest('div').removeClass('has-success').addClass('has-error');
        },
        description: {
            email0: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">不符合邮箱格式</span >',
                conditional: '<span  class="alert-error form-control" style="background-color: #f2dede">该邮箱无对应用户</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值合法</span>'
            }
        }
    });
    $.validateExtend({
        email0: {
            required: true,
            pattern: /\w+([-+.]\w+)*@\w+([-.]w+)*.\w+([-.\w]*)/,
            conditional: function (value) {
                checkValid(2, '#emailAddress');
                return value == $("#hiddenArea2").val();
            }
        }
    });

    var code;
    function createCode() {
        code = "";
        var codeLength = 6;
        var checkCode = document.getElementById("checkCode");
        var selectChar = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');//???к???????????????????????????

        for (var i = 0; i < codeLength; i++) {
            var charIndex = Math.floor(Math.random() * 36);
            code += selectChar[charIndex];
        }

        if (checkCode) {
            checkCode.className = "code";
            checkCode.value = code;
        }

    }
    ;

    function validateCode() {
        var inputCode = document.getElementById("auth").value;
        code = document.getElementById("checkCode").value;
        if (inputCode != code) {
            return false;
        }
        else {
            return true;
        }
    }
    ;

    $('#formForRegister').validate({
        onKeyup: true,
        sendForm: false,
        onChange: true,
        eachValidField: function () {

            $(this).closest('div').removeClass('has-error').addClass('has-success');
        },
        eachInvalidField: function () {

            $(this).closest('div').removeClass('has-success').addClass('has-error');
        },
        description: {
            username: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度须小于20,禁止含非法字符</span >',
                conditional: '<span  class="alert-error form-control" style="background-color: #f2dede">该用户名已被注册</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值合法</span>'
            },
            email: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">不符合邮箱格式</span >',
                conditional: '<span  class="alert-error form-control" style="background-color: #f2dede">该邮箱已被注册</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值合法</span>'
            },
            nicName: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度须小于20,禁止含非法字符</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值合法</span>'
            },
            realName: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度须小于20,禁止含非法字符</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值合法</span>'
            },
            password: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度应在6-16位的数字或字母</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值合法</span>'
            },
            confirm: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                pattern: '<span  class="alert-error form-control" style="background-color: #f2dede">长度应在6-16位的数字或字母</span >',
                conditional: '<span  class="alert-error form-control" style="background-color: #f2dede">验证不匹配，请重新输入</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入值合法</span>'
            },
            auth: {
                required: '<span class="alert-error form-control" style="background-color: #f2dede">必填字段</span>',
                conditional: '<span  class="alert-error form-control" style="background-color: #f2dede">输入验证码不正确</span >',
                valid: '<span class="alert-success form-control" style="background-color: #dff0d8">输入正确</span>'
            }
        }
    });

    $.validateExtend({ username: {
        required: true,
        pattern: /^[\u4e00-\u9fa5\w]{1,20}$/,
        conditional: function (value) {
            checkValid(1, null);
            return value == $("#hiddenArea1").val();
        }
    },
        nicname: {
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
                checkValid(2, '#email');
                return value == $("#hiddenArea2").val();
            }
        },
        password: {
            required: true,
            pattern: /^[a-zA-Z0-9]{6,16}$/
        },
        confirm: {
            required: true,
            pattern: /^[a-zA-Z0-9]{6,16}$/,
            conditional: function (value) {
                return value == $("#password").val();
            }
        },
        auth: {
            required: true,
            conditional: function () {
                return validateCode();
            }
        }
    });
</script>

<script language="javascript">
    /**
     * 增加方法到“onload”事件上
     * @param f_func
     */
    function addOnload(f_func) {
        if (document.addEventListener) {
            window.addEventListener('load', f_func, false);
        }
        else if (window.attachEvent) {
            window.attachEvent('onload', f_func);
        }
        else {
            var f_onLoad = window.onload;
            if (typeof window.onload != 'function') {
                window.onload = f_func;
            }
            else {
                window.onload = function () {
                    f_onLoad();
                    f_func();
                }
            }
        }
    }

    <% if(!isLogin && request.getParameter("requestLogin")!=null) { %>
    addOnload(showLoginFrame);
    <% } %>
</script>