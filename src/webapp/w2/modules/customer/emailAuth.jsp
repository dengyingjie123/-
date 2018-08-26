<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.dao.MySQLDao" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerMoneyPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.wvo.sale.InvestmentPlanWVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="../../info.jsp" %>
<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute(Config.SESSION_LOGINUSER_STRING) != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute(Config.SESSION_LOGINUSER_STRING);
    } else {
        String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;
    }
%>
<!DOCTYPE html><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<title>验证邮箱</title>

    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/style.css">
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/emailAuth.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/emailAuth.js"></script>

</head>

<body>


<%--top--%>
<jsp:include page="/w2/top.jsp"/>
<%--top 结束--%>


    
    <div id="position" class="w1000">
    </div>
    
    <div id="container" class="w1000">

		<%--<div id="title">验证邮箱（下一步进入的是 emailAuthMsg 页面，通知用户收邮件，原来会有一个测试的地址进行点击来验证邮箱，绑定数据后这段话删掉）</div>--%>
        <div id="content">
            <form class="form-horizontal" action="<%=Config.getWebRoot()%>/w2/customer/emailValidateDo" method="post">
        	<table>
            	<tr>
                	<td align="right">邮箱地址</td>
                    <td style="padding: 0px 0px 0px 30px;">
                        <input type="text" class="customer" name="address" id="address" value="<%=loginUser.getEmail()%>">
                    </td>
                </tr>
            	<tr>
                	<td></td><td style="padding: 0px 0px 0px 30px;">

                    <input class="btn_submit"   style="background:url(<%=Config.getWebRoot()%>/w2/img/btn_next.png);"
                             id="emailSubmmitButton" onclick="javascript:emailSubmit()" />
                    </td>
                </tr>
            </table>
            </form>
        </div>
 
    </div>

<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
