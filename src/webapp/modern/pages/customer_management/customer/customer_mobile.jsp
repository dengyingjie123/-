<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%
    if (!Config.checkLoginedUniversal(request)) {
        response.sendRedirect(Config.getWebRoot() + "/modern/pages/login.jsp");
        return;
    }

    // 如果是游客，必须绑定客户信息
    if (Config.checkIsGuest(request) == 1) {
        CustomerPersonalPO guest = Config.getLoginCustomerInSession(request);
        response.sendRedirect(Config.getModernCustomerManagementPages() + "/customer/customer_bind_wechat.jsp?userInfoId=" + guest.getNationId());
        return;
    }
%>
<%
    CustomerPersonalPO customer = Config.getLoginCustomerInSession(request);
    String gotoUrl = request.getParameter("gotoUrl");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>产品列表</title>
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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/angular.service.js"></script>
    <script type="application/javascript">
        var times = 60;
        function waiting(){
            $('#btn-mobile-code').text("已发送（"+times+"）");
            var st =setTimeout("waiting()", 1000);
            times--;

            if (times == 0) {
                clearTimeout(st);
                times == 60;
                $('#btn-mobile-code').text("获取验证码");
                $('#btn-mobile-code').removeClass("btn-mobile-code-disable").addClass("btn-mobile-code");
            }
        }
    </script>
    <script type="application/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            var urlCustomer = "<%=Config.getWebRoot()%>/modern/s/customer/CustomerPersonal_loadCustomerPersonalVOByCustomerPersonalId?customerId=<%=customer.getId()%>";
            $http.get(urlCustomer).success(function(response){
                $scope.customer = response.returnValue;
            });

            $scope.next = function() {

                var mobileCode = fm.getFormValueByName("personal.mobileCode");

                if (fm.checkIsTextEmpty(mobileCode)) {
                    fm.message('提示','请输入验证码');
                    return;
                }

                var url = "<%=Config.getWebRoot()%>/modern/c/customer/CustomerPersonal_changeCustomerMobileStep1";
                var parameters = "mobile="+fm.getFormValueByName("personal.mobile")+"&mobileCode="+fm.getFormValueByName("personal.mobileCode");

                fm.post(url, parameters, function(data){
                    if (data != "1") {
                        fm.message("提示","输入验证码有误");
                        return;
                    }
                    fm.goto("<%=Config.getModernCustomerManagementPages()%>/customer/customer_mobile_new.jsp");
                },null);


            }

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
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <div class="header-all">
        <span class="hd-news" onclick="history.go(-1);"><img src="<%=Config.getWebRoot()%>/modern/images/allow-fx.png"></span>
        <h3>修改电话号码</h3>
        <span class="hd-cfact-search">
            <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png"> --%>
        </span>
    </div>
    <form>
    <section class="mycustomer-info">

        <ul class="bind-ph-num">

            <li>
                <div class="box-ct">
                    <div class="rgttel-ico">原电话</div>
                    <i class="ct-hx"></i>
                    <input type="text" id="mobile" name="personal.mobile" placeholder="请输入电话" class="input-box" value="{{customer.mobile}}" readonly="true" />
                </div>
                <div class="error-tips-ct" style="display: none;">
                    <i class="sjx"></i>
                    <div class="error-tips">
                        <p>密码或帐号错误，请重新输入</p>
                    </div>
                </div>
            </li>
            <%--<li>--%>
                <%--<div class="box-ct">--%>
                    <%--<div class="rgttel-ico">生日</div>--%>
                    <%--<i class="ct-hx"></i>--%>
                    <%--<input id="demo" placeholder="请选择出生年月" />--%>
                <%--</div>--%>
                <%--<div class="error-tips-ct" style="display: none;">--%>
                    <%--<i class="sjx"></i>--%>
                    <%--<i class="ct-hx"></i>--%>
                    <%--<div class="error-tips">--%>
                        <%--<p>密码或帐号错误，请重新输入</p>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</li>--%>
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
            <%--<li>--%>
                <%--<div class="box-ct">--%>
                    <%--<div class="rgttel-ico">属性</div>--%>
                    <%--<i class="ct-hx"></i>--%>
                    <%--<input type="text" placeholder="请输入属性" class="input-box" />--%>
                <%--</div>--%>
                <%--<div class="error-tips-ct" style="display: none;">--%>
                    <%--<i class="sjx"></i>--%>
                    <%--<div class="error-tips">--%>
                        <%--<p>密码或帐号错误，请重新输入</p>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</li>--%>

        </ul>
        <input id="id" name="personal.id" type="hidden" value="<%=customer.getId()%>"/>
        <a href="javascript:void(0)" id="btn-next" class="btn-sure" ng-click="next()">下一步</a>

    </section>

    </form>

    <%=Config.getModernCustomerManagementFooter("0010")%>
</div>
</body>
</html>