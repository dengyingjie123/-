<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
         language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><%=Config.APP_NAME%>
    </title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/default.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/buttons.css">
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
    <script type="text/javascript"
            src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/system/ConfigClass.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/boot.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/treeplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
    <style type="text/css">
        <!--
        body {
            background: url("include/images/bglogin1.jpg") no-repeat;
            margin: 0px auto;
            padding: 0;
            z-index: -1;
            font-family: 'Microsoft YaHei', 'WenQuanYi Micro Hei', sans-serif;
        }

        #logo_hw {
            background: url("include/images/icon_kepler.png") no-repeat;
            width: 258px;
            height: 380px;
            margin: -40px auto -80px auto;
        }

        #loginbg {
            margin: 0px auto;;
            background: url("include/images/panel_login_kepler.png");
            width: 529px;
            height: 418px;;
            z-index: 1;
        }

        #panel-login {
            position: relative;
        }

        #panel-login span {
            font-size: 15px;
            word-spacing: 10px;;
        }

        #panel-login input {
            width: 267px;
            height: 34px;;
            border: 1px solid #ccc;
            border-radius: 8px;;
            outline: none;
            font: 18px;
            padding-left: 20px;

        }

        #panel-login .login_btn {
            cursor: pointer;
            position: absolute;
            top: 219px;
            left: 50px;
            width: 109px;
            height: 38px;
            background: url("include/images/icon_login.png") -1px;
            font-size: 15px;
            border: 0px;
            text-align: center;
             padding: 0;
        }

        #panel-login .cancel_btn {
            text-align: center;
            cursor: pointer;
            position: absolute;
            top: 219px;
            left: 170px;
            width: 109px;
            height: 38px;
            background: url("include/images/icon_quit.png") -1px;
            font-size: 15px;
            border: 0px;
            padding: 0;
        }

        #panel-login .captcha_btn {
            text-align: center;
            cursor: pointer;
            position: absolute;
            top: 219px;
            left: 290px;
            width: 109px;
            height: 38px;
            background: url("include/images/icon_quit.png") -1px;
            font-size: 15px;
            border: 0px;
            padding: 0;
        }

        #panel-login .reset_btn {
            text-align: center;
            cursor: pointer;
            position: absolute;
            top: 219px;
            left: 410px;
            width: 109px;
            height: 38px;
            background: url("include/images/icon_quit.png") -1px;
            font-size: 15px;
            border: 0px;
            padding: 0;
        }

        .login_tip {
	position: absolute;
	top: 289px;
	left: 78px;
	width:424px;
	height: 29px;
        }

        .login_tip span {
            width: 110px;
            display: inline-block;
        }

        .login_tip .tip-1px {
            border-right: 1px solid #000000;
            font-size: 10px;
        }

        .login_tip .tip-right {
            text-align: right;
        }

        .login_tip .tip-1px .a_doc {
            color: #ff8200;
        }

        .login_tip span a {
            font-size: 14px;
            text-decoration: none;;
            color: #000000;
        }

        #loginForm {

        }

        .login_user {
            position: absolute;
            top: 40px;
            left: 90px;
            width: 368px;
            padding: 15px 10px;
        }

        .login_password {
            position: absolute;
            top: 80px;
            left: 90px;
            width: 368px;
            padding: 15px 10px;
        }

        .login_mobile {
            position: absolute;
            top: 120px;
            left: 90px;
            width: 368px;
            padding: 15px 10px;
        }

        .login_captcha {
            position: absolute;
            top: 160px;
            left: 90px;
            width: 368px;
            padding: 15px 10px;
        }

        #divLoginform {
            position: absolute;
            text-align: left;
            top: -40px;
            left: -35px;
            width: 230px;
        }

        #tableTitle {
            font-size: 45px;
            color: seagreen;
        }

        -->
    </style>
    <script language="javascript">
        $(document).ready(function () {

            $('#operatorId').keyup(function (e) {
                if (e.keyCode == 13) {
                    Login_DoLogin();
                }
            });

            $('#password').keyup(function (e) {
                if (e.keyCode == 13) {
                    Login_DoLogin();
                }
            });
            showMsg();

        });
        function Login_DoLogin() {
            var operatorId = fw.sqlReplace($('#operatorId').val());
            var password = fw.getMD5($('#password').val());

            if (fw.checkIsTextEmpty(operatorId) || fw.checkIsTextEmpty($('#password').val())) {
                fw.alert("失败", "请输入用户名和登录密码!");
                return;
            }

            $('#password').val(password)

            $('#btnLogin').attr('disabled', 'disabled');
            $('#btnLogin').attr('value', '正在登录');
            $('#loginForm').submit();
        }
        function showMsg() {
            var message = "<%=request.getAttribute("Msg")%>";
            if (message != "null") {
                fw.alert("失败", message);
            }
        }

        function Get_Captcha() {
            var mobile = $('#moible').val()
            if (fw.checkIsTextEmpty(mobile)) {
                fw.alert("失败", "请输入电话号码!");
                return;
            };
            var pattern = /^1[34578]\d{9}$/;
            if(!pattern.test(mobile)){
                fw.alert("失败", "请输入11位电话号码!");
                return;
            };

            $('#btnCaptcha').val('正在发送验证码');
            $('#loginForm').submit();

        }

    </script>
</head>

<body>
<div id="logo_hw"></div>
<div id="loginbg">
    <form id="loginForm" name="form1" method="post" action="<%=Config.getWebRoot()%>/login_execute">
        <div id="panel-login">
            <div class="login_user"><span>用户名：</span><input type="text" name="user.name" id="operatorId" value="4"/></div>
            <div class="login_password"><span>密&nbsp;码：</span><input type="password" name="user.password" id="password" value="admin"/></div>

            <div class="login_mobile"><span>电话号码：</span><input type="text" name="user.mobile" id="mobile"/></div>
            <div class="login_captcha"><span>验证码：</span><input type="password" name="user.captcha" id="captcha"/></div>



            <input type="button" class="login_btn" name="button" id="btnLogin" value="登录" onclick="Login_DoLogin()"/>
            <input type="reset" class="cancel_btn" name="button2" id="btnReset" value="重置"/>
            <input type="reset" class="captcha_btn" name="button3" id="btnCaptcha" value="获得验证码" onclick="Get_Captcha()"/>
            <input type="reset" class="reset_btn" name="button3" id="btnByCaptcha" value="修改密码" onclick="Get_Captcha()"/>


            <div class="login_tip">
                <a class="a_doc" style="font-size: 12px" href="http://www.miitbeian.gov.cn" target="_blank">粤ICP备16072634号</a>
                &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;深圳开普乐信息服务有限公司 版权所有
                <a class="a_doc" style="font-size: 12px" href="http://dev.youngbook.net/forum/forum.php?mod=viewthread&tid=1&page=1&extra=#pid1" target="_blank">帮助</a>
            </div>

        </div>

    </form>
</div>
</body>
</html>