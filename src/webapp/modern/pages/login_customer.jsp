<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%
    String userInfoId = "";
    if (!StringUtils.isEmpty(request.getParameter("userInfoId"))) {
        userInfoId = request.getParameter("userInfoId");
    }

    // userType
    String userType = "";
    if (!StringUtils.isEmpty(request.getParameter("userType"))) {
        userType = request.getParameter("userType");
    }
%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">  
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>德合在线</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/mobile.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/color.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot()%>/modern/css/public.css">
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/mobiscroll.custom-2.17.1.min.js" ></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="application/javascript">
        $(function () {

            $('#demo').mobiscroll().calendar({
                theme: 'mobiscroll',
                display: 'bottom',
                dateFormat: 'yyyy-mm-dd',
                lang:'zh'
            });

            $('#show').click(function () {
                $('#demo').mobiscroll('show');
                return false;
            });

            $('#clear').click(function () {
                $('#demo').mobiscroll('clear');
                return false;
            });

        });

    </script>
    <script type="text/javascript">

        $(document).ready(function(){
            var userInfoId = '<%=userInfoId%>';


            if (!fm.checkIsTextEmpty(userInfoId) && userInfoId != 'null') {
                $('#wechatLoginButton').hide();
                $('#otherChannel').hide();
                $('#userTypeDiv').hide();
            }
        });

        function wechatLogin() {
            var userType = 'customer';

            var state = "{\"userType\":\""+userType+"\"}";
            state = fm.urlEncode(state);


            var url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0cf3490b0e5c79b4&redirect_uri=http%3a%2f%2fwww.dianjinpai.com%2fcore%2fweixindone.jsp&response_type=code&scope=snsapi_userinfo&state="+state+"#wechat_redirect";

            // alert(url);
            fm.goto(url);
        }

        function login() {
            var messageId = 'loginMessage';
            var userName = $('#userName').val();
            var password = $('#password').val();

            if (fm.checkIsTextEmpty(userName)) {
                fm.message('警告', '请输入正确的用户名');
                return;
            }

            if (fm.checkIsTextEmpty(password)) {
                fm.message('警告', '请输入正确的密码');
                return;
            }


            var userType = 'customer';
            var parameters = {'loginName' : userName, 'password' : fm.getMD5(password), 'userType' : userType, 'userInfoId':'<%=userInfoId%>'};

            // alert(JSON.stringify(parameters));


            var url = "<%=Config.getWebRoot()%>/modern/common/system/loginUniversal";


            fm.post(url, parameters, function(data) {
//                var s = JSON.stringify(data);
//                alert(s);
                if (data['userType'] == 'customer') {
                    fm.goto('<%=Config.getModernCustomerManagementPages()%>/production/home.jsp');
                }
                else {
                    fm.goto('<%=Config.getModernSaleManagementPages()%>/production/home.jsp');
                }
            }, null);
        }
    </script>
    <script>
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            $scope.cannotLogin = function(){
                fm.goto("<%=Config.getWebRoot()%>/modern/pages/common/login/user_type.jsp");
            }
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
    <div>
        <div class="rgt-hd">
            <img alt="logo" src="<%=Config.getWebRoot()%>/modern/images/logo.png">
            <h3 class="register-name">投资者登录</h3>
        </div>
        <div style="padding:0 10px">
            <div style="text-align:center; padding: 10px;">
                <div><img src="<%=Config.getModernRoot()%>/images/login_wechat.png" width="50"/></div>
                <a href="#" id="wechatLoginButton" class="easyui-linkbutton c1" plain="true" outline="true" style="width:98%;height:35px;font-size:.5rem" onClick="wechatLogin()"><span style="font-size:16px;">微信登录</span></a>
            </div>
            <div style="text-align:center; padding: 10px;">
                <div><img src="<%=Config.getModernRoot()%>/images/login_mobile.png" width="50"/></div>
                <input id="userName" name="user.ame" class="easyui-textbox" data-options="prompt:'手机号',iconCls:'icon-man'" style="width:100%;height:38px" type="number" pattern="[0-9]*" inputmode="numeric" value="13888939712">
                <input id="password" name="user.password" class="easyui-passwordbox" data-options="prompt:'密码'" style="width:100%;height:38px" type="letter" value="admin">
                <a href="javascript:login();" class="easyui-linkbutton c2" style="width:98%;height:35px;font-size: .5rem "><span style="font-size:16px">登录</span></a>
            </div>
            <div id="otherChannel" class="login-lookaround">
                <a href="javascript:void(0)" onClick="fm.goto('<%=Config.getWebRoot()%>/modern/pages/common/register/user_type.jsp');">立即注册</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0)" onClick="fm.goto('<%=Config.getWebRoot()%>/modern/pages/common/login/user_type.jsp');">无法登录？</a>
            </div>
        </div>
    </div>

    <div id="modern-common-area"></div>
    <div id="loginMessage"></div>
</body>    
</html>