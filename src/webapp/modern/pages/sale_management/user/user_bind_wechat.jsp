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
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/framework-modern.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/swiper.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/modern/js/mobiscroll.custom-2.17.1.min.js" ></script>
    <script>
        function goto(type) {
            var url = "";

            if (type == 0) {
                url = "<%=Config.getWebRoot()%>/modern/pages/common/login/customer_login_mobile_code.jsp";
				url += "?userInfoId=<%=userInfoId%>";
            }
            else if (type == 1) {
                url = "<%=Config.getWebRoot()%>/modern/pages/login_salesman.jsp?userType=user";
				url += "&userInfoId=<%=userInfoId%>";
            }
            else if (type == 2) {
                url = "<%=Config.getModernCustomerManagementPages()%>/customer/customer_register.jsp";
				url += "?userInfoId=<%=userInfoId%>";
            }

            fm.goto(url);
        }
    </script>
</head>
<body>
    <div>
        <div class="rgt-hd">
            <img alt="logo" src="<%=Config.getWebRoot()%>/modern/images/logo.png">
            <h3 class="register-name">销售账户绑定</h3>
        </div>
        <section class="customer-details">
            <div class="home-recommend-ct">
                <h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>请绑定账户</h3>
                <ul class="cutmer-data lbBox nth-childtwo pd25">
                    <li onClick="goto(1)">
                        <i class="wait-ico1 cstmer lineBlock"></i>
                        <em class="myitem-txt lineBlock">通过验证查询密码绑定</em>
                        <i class="icon-arrow"></i>
                    </li>
                </ul>
            </div>
            <div class="home-recommend-ct">
                <%--<h3 class="tit"><i class="sjx-ico1"></i><i class="triangle-right"></i>操作</h3>--%>
                <div class="pro-hights pd25">
                    <%--<img class="collect" src="images/collection-ico.png">--%>
                    <div>
                        <button type="button" class="logout" onClick="history.go(-1);">返回</button>
                    </div>
                </div>
            </div>
        </section>
    </div>


    <div id="modern-common-area"></div>
    <div id="loginMessage"></div>
</body>    
</html>