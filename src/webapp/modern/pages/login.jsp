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

// $(".selbox-ze").on("touchend touchcancle",function(){
//      $(".selbox-ze").removeClass("sel-box1").addClass("sel-box2");
//      $(this).removeClass("sel-box2").addClass("sel-box1");
//  });
            $("#isman").click(function(){

                $("#iswoman").removeClass("sel-box2").addClass("sel-box1");
                $("#isman").removeClass("sel-box1").addClass("sel-box2");
            });
            $("#iswoman").click(function(){
                $("#isman").removeClass("sel-box2").addClass("sel-box1");
                $("#iswoman").removeClass("sel-box1").addClass("sel-box2");
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
            var userType = '<%=userType%>';

            if (fm.checkIsTextEmpty(userType)) {
                userType = $("#isman").hasClass('sel-box2') ? "customer" : "user";
            }



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


            var userType = '<%=userType%>';
            if (fm.checkIsTextEmpty(userType)) {
                userType = $("#isman").hasClass('sel-box2') ? "customer" : "user";
            }


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
            <h3 class="register-name">德合在线</h3>
        </div>
        <div style="padding:60px 20px ; text-align:center; background-image: url('../images/login_bg.png')">
            <table>
                <tr>
                    <td><a href="<%=Config.getWebRoot()%>/modern/pages/login_customer.jsp">
                        <img src="<%=Config.getWebRoot()%>/modern/images/login_customer.png" width="60%">
                    </a></td>
                    <td><a href="<%=Config.getWebRoot()%>/modern/pages/login_salesman.jsp">
                        <img src="<%=Config.getWebRoot()%>/modern/images/login_salesman.png" width="60%">
                    </a></td>
                </tr>
                <tr><td colspan="2">
                    <div style="font-size: 0.25rem; padding: 60px 5px 5px; text-align: left;">总部：深圳福田商报东路英龙商务大厦1708</div>
                    <div style="font-size: 0.25rem; padding: 5px; text-align: left;">昆明：昆明市西山区滇池路南亚风情第壹城B座20层</div>
                </td></tr>
                <tr><td colspan="2"><div style="font-size: .35rem; padding: 60px 0px 10px;">
                    <a href="javascript:void(0)" onClick="fm.goto('<%=Config.getModernCustomerManagementPages()%>/customer/customer_register.jsp');">投资者注册</a>
                </div></td></tr>
            </table>
        </div>
    </div>

    <div id="modern-common-area"></div>
    <div id="loginMessage"></div>
</body>    
</html>