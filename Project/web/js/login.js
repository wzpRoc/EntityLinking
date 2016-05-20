/*******************************************************************************
 * 名称：login.js
 * 功能：实现用户权限认证，用户登录、登出
 * 开发者：彭程
 * 时间：2011-10-13
 * 长度：94
 * 更新日志:
 * *********************************************************************************************************************************************
 */
function login() {
    // 封装用户登录信息
    var user = new Object();
    user.username = $('#usernameForLogin').val();
    user.password = $('#passwordForLogin').val();
    var save = $('#chkSave').val() == "true" ? true : false;


    // 提交用户信息登录
    AjaxLogin.doLogin(user, save, function (isLogin) {
        if (!isLogin) {
            $('#login').addClass("has-error");
            $('#message').text('用户名或密码错误！');
            $('#message').show();
            $('#passwordForLogin').val('');
            return false;
        } else {
            hideLoginFrame();
            // 执行成功，调用回调函数
            if(loginCallback) {
                loginCallback();
                loginCallback = null;
            }else{
                //刷新页面
                window.location.reload();
            }

            return true;
        }
       // location = "musicHome";      //userInfo
    });

//    alert("end AjaxLogin.doLogin");
    return false;
}

function logout(p_callback){
    var callback;
    if(typeof(p_callback) == "function") {
        callback = p_callback;
    } else {
        callback = function(result) {
            if(result) {
                $("#login").empty().html('<a href="#" data-toggle="modal" data-target="#loginModal"><img src="img/front/login.png" alt="登录"></a>');
                $("#register").empty().html('<a href="#" data-toggle="modal" data-target="#registerModal"><img src="img/front/register.png" alt="登录"></a>');
                $("#register").empty().html('<a href="#" data-toggle="modal" data-target="#registerModal"><img src="img/front/register.png" alt="登录"></a>');

                return true;
            }
        }
    }

    AjaxLogin.logout(callback);
}

function gotoHome() {
    window.location = '../';
}

function adminLogout() {
    logout(gotoHome);
}

function checkValid(type, element){
    if(type == 1) {
        var username = $('#username').val();
        AjaxLogin.checkValid("username", username,function(returnValue){
            if(returnValue)
                $('#hiddenArea1').val(username);
            else
                $('#hiddenArea1').val("");
        });
    }
    else if(type == 2){
        var email = $(element).val();
        AjaxLogin.checkValid("email", email,function(returnValue){
            if(returnValue && element=="#email")
                $('#hiddenArea2').val(email);
            else if(!returnValue && element!="#email")
                $('#hiddenArea2').val(email);
            else
                $('#hiddenArea2').val("");
        });
    }
    else if(type == 3){
        var mobilephone = $('#mobilephone').val();
        AjaxLogin.checkValid("mobilephone", mobilephone,function(returnValue){
            if(returnValue)
                $('#hiddenArea3').val(mobilephone);
            else
                $('#hiddenArea3').val("");
        });
    }
}

function register() {
    // 封装用户登录信息
    var user = new Object();
    user.username = $('#username').val();
    user.password = $('#password').val();
    user.email = $('#email').val();
    user.nicName = $('#nicName').val();
    user.realName = $('#realName').val();

   /* var index = $('#label-switch').children(0).attr("class").indexOf("switch-on");
    if (index > 0)
        user.sex = 1;
    else*/
        user.sex = 3;


    AjaxLogin.register(user, function(returnValue){
        $('#result').text("");
        if(returnValue == -1){
            $('#result').text("注册信息提交失败！请重试！");
            return false;
        }
        AjaxLogin.doLogin(user, false);
        $('#result').text("注册成功！进入个人中心...");
        setTimeout("location = 'userInfo'"  , 3000);

    });
    return false;
}

var loginCallback;

/**
 * 弹出登录框
 */
function showLoginFrame(){
    var loginBtn = document.getElementById("loginBut");
    if(!loginBtn) {
        alert("登录按钮不存在");
    }
    loginBtn.click();
}

/**
 * 隐藏登录框
 */
function hideLoginFrame(){
    document.getElementById("leaveBut").click();
}