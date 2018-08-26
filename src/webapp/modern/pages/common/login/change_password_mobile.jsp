<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%
//    CustomerPersonalPO customer = Config.getLoginCustomerInSession(request);
//    String gotoUrl = request.getParameter("gotoUrl");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>手机号找回密码</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/mobile.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getModernFrameworkRoot()%>/themes/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot()%>/modern/css/public.css">
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getModernFrameworkRoot()%>/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.service.js"></script>
    <script type="application/javascript">
        var times = 60;
        function waiting(){
            $('#btn-mobile-code').text("已发送（"+times+"）");
            var st =setTimeout("waiting()", 1000);
            times--;

            if (times == 0) {
                clearTimeout(st);
                times = 60;
                $('#btn-mobile-code').text("获取验证码");
                $('#btn-mobile-code').removeClass("btn-mobile-code-disable").addClass("btn-mobile-code");
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

                var mobile = fm.getFormValueByName("personal.mobile");

                if (fm.checkIsTextEmpty(mobile)) {
                    fm.message('提示','请输入手机号');
                    return;
                }


                var url = "<%=Config.getWebRoot()%>/modern/c/customer/getMobileRegisterCode";
                var parameters = "mobile="+fm.getFormValueByName("personal.mobile");
                fm.post(url, parameters, function(data){
                    $('#btn-mobile-code').text("已发送");
                    $('#btn-mobile-code').removeClass("btn-mobile-code").addClass("btn-mobile-code-disable");
                    waiting();
                },null);
            }

            $scope.submitForm = function() {

                var mobileCode = fm.getFormValueByName("personal.mobileCode");

                var password = fm.getFormValueByName("personal.password");
                var password2 = fm.getFormValueByName("personal.password2");



                if (fm.checkIsTextEmpty(mobileCode)) {
                    fm.message('提示','请输入验证码');
                    return;
                }

                if (fm.checkIsTextEmpty(password) || fm.checkIsTextEmpty(password2) || password != password2) {
                    fm.message('提示','新密码格式有误，请重新输入');
                    return;
                }

                password = fm.getMD5(password);

                var url = "<%=Config.getWebRoot()%>/modern/c/customer/changePasswordByMobieCode";
                var parameters = "mobile="+fm.getFormValueByName("personal.mobile")+"&mobileCode="+fm.getFormValueByName("personal.mobileCode") + "&password="+password;

                fm.post(url, parameters, function(data){
                    if (data != "1") {
                        fm.message("提示","密码修改失败");
                        return;
                    }

                    fm.message("提示","密码已修改，请牢记新密码");
                    fm.goto("<%=Config.getWebRoot()%>/modern/pages/login.jsp");

                },null);
            }
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <div class="header-all">
        <span class="hd-news" onclick="history.go(-1);"><img src="<%=Config.getWebRoot()%>/modern/images/allow-fx.png"></span>
        <h3>手机号找回密码</h3>
        <span class="hd-cfact-search">
            <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png"> --%>
        </span>
    </div>
    <form>
    <section class="mycustomer-info">
        <ul class="bind-ph-num">
            <li>
                <div class="box-ct">
                    <div class="rgttel-ico">手机号</div>
                    <i class="ct-hx"></i>
                    <input type="text" id="mobile" name="personal.mobile" placeholder="请输入电话" class="input-box" value="" />
                </div>
                <div class="error-tips-ct" style="display: none;">
                    <i class="sjx"></i>
                    <div class="error-tips">
                        <p>密码或帐号错误，请重新输入</p>
                    </div>
                </div>
            </li>
            <li>
                <div class="box-ct">
                    <div class="rgttel-ico">验证码</div>
                    <i class="ct-hx"></i>
                    <input type="text" id="mobileCode" name="personal.mobileCode" placeholder="" class="input-box-mobile-code" value="" />
                    <a href="javascript:void(0)" id="btn-mobile-code" class="btn-mobile-code" ng-click="getMobileCode()">获取验证码</a>
                </div>
                <div class="error-tips-ct" style="display: none;">
                    <i class="sjx"></i>
                    <div class="error-tips">
                        <p>密码或帐号错误，请重新输入</p>
                    </div>
                </div>
            </li>
            <li>
                <div class="box-ct">
                    <div class="rgttel-ico">新密码</div>
                    <i class="ct-hx"></i>
                    <input type="password" id="password" name="personal.password" placeholder="输入新密码" class="input-box" value="" />
                </div>
                <div class="error-tips-ct" style="display: none;">
                    <i class="sjx"></i>
                    <div class="error-tips">
                        <p>密码或帐号错误，请重新输入</p>
                    </div>
                </div>
            </li>
            <li>
                <div class="box-ct">
                    <div class="rgttel-ico">重复新密码</div>
                    <i class="ct-hx"></i>
                    <input type="password" id="password2" name="personal.password2" placeholder="重新输入新密码" class="input-box" value="" />
                </div>
                <div class="error-tips-ct" style="display: none;">
                    <i class="sjx"></i>
                    <div class="error-tips">
                        <p>密码或帐号错误，请重新输入</p>
                    </div>
                </div>
            </li>
        </ul>
        <a href="javascript:void(0)" class="btn-sure" ng-click="submitForm()">提交</a>
    </section>
    </form>

</div>
</body>
</html>