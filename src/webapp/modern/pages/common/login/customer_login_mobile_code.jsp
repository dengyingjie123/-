<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%
    String userInfoId = "";
    if (!StringUtils.isEmpty(request.getParameter("userInfoId"))) {
        userInfoId = request.getParameter("userInfoId");
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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="text/javascript">
        function login() {

            var mobile = fm.getFormValueByName("mobile");
            if (fm.checkIsTextEmpty(mobile)) {
                fm.message('提示','请输入手机号');
                return;
            }

            var mobileCode = fm.getFormValueByName("mobileCode");
            if (fm.checkIsTextEmpty(mobileCode)) {
                fm.message('提示','请输验证码');
                return;
            }

            // $('#personalPassword').passwordbox({'value' : fm.getMD5(password)});
            var parameters = $("form").serialize();


            var url = "<%=Config.getWebRoot()%>/modern/common/system/loginWithMobileCode.action";

            fm.post(url, parameters, function(data){


                // alert(JSON.stringify(data));

                // 需要修改密码
                if (data['status'] == "1") {
                    fm.message('提示','登录成功');
                    fm.goto("<%=Config.getModernCustomerManagementPages()%>/customer/customer_password_change_only.jsp?tokenString="+data['tokenString']);
                    return;
                }
                else {
                    fm.goto("<%=Config.getModernCustomerManagementPages()%>/production/production_list.jsp");
                    return;
                }
                $('#personalPassword').passwordbox({'value' : ""});
            },null);
        }

        var times = 60;
        function waiting(){
            $('#btn-mobile-code').linkbutton({text:"已发送（"+times+"）"});
            var st = setTimeout("waiting()", 1000);
            times--;

            if (times == 0) {
                clearTimeout(st);
                times = 60;
                $('#btn-mobile-code').linkbutton({text:"获取验证码"});
                $('#btn-mobile-code').removeClass("easyui-linkbutton c2").addClass("easyui-linkbutton c1");
            }
        }

    </script>
    <script type="application/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            $scope.getMobileCode = function() {
                if (times < 60) {
                    return;
                }

                var mobile = fm.getFormValueByName("mobile");

                if (fm.checkIsTextEmpty(mobile)) {
                    fm.message('提示','请输入手机号');
                    return;
                }


                var url = "<%=Config.getWebRoot()%>/modern/c/customer/getMobileRegisterCode";
                var parameters = "mobile="+fm.getFormValueByName("mobile");
                fm.post(url, parameters, function(data){
                    $('#btn-mobile-code').linkbutton({text:'已发送'});
                    $('#btn-mobile-code').removeClass("easyui-linkbutton c1").addClass("easyui-linkbutton c2");
                    waiting();
                },null);
            }
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
    <div class="easyui-navpanel">
        <form>
        <div class="rgt-hd">
            <img alt="logo" src="<%=Config.getWebRoot()%>/modern/images/logo.png">
            <h3 class="register-name">客户快速登录</h3>
        </div>
        <div>&nbsp;</div>
        <div style="padding:0 20px">
            <div style="margin-bottom:10px">
                <input name="mobile" class="easyui-textbox" data-options="prompt:'手机号（必填）',iconCls:'icon-man'" style="width:100%;height:38px" value="">
            </div>
            <div style="margin-bottom:10px">
                <input name="mobileCode" class="easyui-textbox" data-options="prompt:'验证码（必填）',iconCls:'icon-man'" style="width:58%;height:38px">
                <a id="btn-mobile-code" href="#" class="easyui-linkbutton c1" style="width:40%;height:35px" ng-click="getMobileCode()"><span style="font-size:12px">获取验证码</span></a>
            </div>
            <div style="text-align:center;margin-top:30px">
                <a href="#" class="easyui-linkbutton c1" style="width:60%;height:35px" onClick="login();"><span style="font-size:16px">提交</span></a>
                <a href="#" class="easyui-linkbutton c2" plain="true" outline="true" style="width:35%;height:35px" onClick="history.go(-1);"><span style="font-size:16px">返回</span></a>
            </div>
        </div>
            <input type="hidden" id="userInfoId" name="userInfoId" value="<%=userInfoId%>" />
    </form>
    </div>

</body>    
</html>