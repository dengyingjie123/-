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
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/modern/css/mobiscroll.custom-2.17.1.min.css" />
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/modern/css/idangerous.swiper.css">
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/public.js"></script>
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

        function submitForm() {
            var sex = $("#isman").hasClass('sel-box2') ? "1" : "0";
            var url = "<%=Config.getWebRoot()%>/modern/s/customer/CustomerPersonal_insertOrUpdateCustomerPersonalOut.action";
            var parameters = $("form").serialize() + "&customerPersonalOut.sex="+sex;
//            alert(parameters);
        }

    </script>
    <script type="application/javascript">
        var app = angular.module('myApp', []);
        app.controller('myCtrl', function($scope, $http){
            var urlCustomer = "<%=Config.getWebRoot()%>/modern/s/customer/CustomerPersonal_loadCustomerPersonalVOByCustomerPersonalId?customerId=<%=customer.getId()%>";
            $http.get(urlCustomer).success(function(response){
                $scope.customer = response.returnValue;
            });
        });
    </script>
</head>
<body ng-app="myApp" ng-controller="myCtrl">
<div class="easyui-navpanel">
    <div class="header-all">
        <span class="hd-news" onclick="history.go(-1);"><img src="<%=Config.getWebRoot()%>/modern/images/allow-fx.png"></span>
        <h3>客户信息</h3>
        <span class="hd-cfact-search">
            <%--<img src="<%=Config.getWebRoot()%>/modern/images/add-btn.png"> --%>
        </span>
    </div>
    <form>
    <section class="mycustomer-info">
        <ul class="bind-ph-num">
            <li>
                <div class="box-ct">
                    <div class="rgttel-ico">姓名</div>
                    <i class="ct-hx"></i>
                    <input type="text" id="name" name="customerPersonalOut.name" placeholder="请输入用户名" class="input-box" value="<%=customer.getName()%>" />
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
                    <div class="rgttel-ico">性别</div>
                    <i class="ct-hx"></i>
                    <div class="sel-box">
                        <div class="sel-box2 selbox-ze" id="isman" >
                            <i></i>男</div>
                        <div class="sel-box1 selbox-ze" id="iswoman" >
                            <i></i>女</div>
                    </div>
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
                    <div class="rgttel-ico">电话</div>
                    <i class="ct-hx"></i>
                    <input type="text" id="mobile" name="customerPersonalOut.mobile" placeholder="请输入电话" class="input-box" value="{{customer.mobile}}" />
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
                    <div class="rgttel-ico">证件</div>
                    <i class="ct-hx"></i>
                    <input type="text" id="idCard" name="customerPersonalOut.idCard" placeholder="请输入身份证" class="input-box" value="{{customer.idCard}}" />
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
        <%--<a href="javascript:void(0)" class="btn-sure mar-bom cd-popup-trigger" onclick="submitForm()">提交</a>--%>
    </section>
    </form>
</div>
</body>
</html>