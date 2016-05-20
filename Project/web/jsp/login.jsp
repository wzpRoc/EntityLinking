<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<head>
    <link href="css/base.css" rel="stylesheet" type="text/css" />
    <link href="css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="clearfix" id="container">
    <div class="clearfix" id="content">
        <div class="main">
            <div id="modal-head">
                <h1>Welcome to NERL !</h1>
            </div>
            <div id="message" style="display:none"></div>
            <div class="padding">
                <form class="default" name="loginForm" action="/el/login" method="post">
                    <ul>
                        <li class="row">
                            <div class="text-box">
                                <label id="lblName" style="display: block;" class="prompted" for="username">用户名</label>
                                <input class="text" id="username" name="username" size="30" type="text" />
                            </div>
                        </li>
                        <li class="row">
                            <div class="text-box">
                                <label id="lblPass" style="display: block;" class="prompted" for="password">密&nbsp;码</label>
                                <input class="text" id="password" name="password" size="30" type="password" />
                            </div>
                        </li>

                        <li class="row">
                            <input id="chkSave" type="checkbox" name="save" onclick="this.value='false';if(this.checked)this.value='true';" checked value="true"/>
                            <label for="chkSave" style="font-size: 13px;">记住我</label>
                        </li>

                        <li>
                            <input class="submit" id="person_session_submit" name="commit"
                                   value="登陆" type="submit" />
                            &nbsp;
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>