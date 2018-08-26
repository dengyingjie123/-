<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.dao.MySQLDao" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerMoneyPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.wvo.sale.InvestmentPlanWVO" %>
<%@ page import="com.youngbook.common.ReturnObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="../../info.jsp" %>
<%
    String code = "";
    String email = "";
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");

        ReturnObject returnObject = (ReturnObject) request.getAttribute("returnObject");
        if (returnObject != null) {
            code = returnObject.getMessage().split(",")[0];
            email = returnObject.getMessage().split(",")[1];
        }
    } else {
        String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;
    }
%>
<!DOCTYPE html>
    <html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>验证邮箱</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/emailAuth.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>


    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/style.css">
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/emailAuthMsg.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>

</head>

<body>


<%--top--%>
<jsp:include page="/w2/top.jsp"/>
<%--top 结束--%>


<div id="position" class="w1000">
</div>

<div id="container" class="w1000">

    <div id="title">验证邮箱</div>
    <div id="content">
        <form action="" method="post">
            <table>
                <tr>
                    <td>我们向您的 <strong><%=email%>
                    </strong> 邮箱发送了一封邮件，请及时查收并验证。
                    </td>
                </tr>
                <tr>
                    <td>如您长时间没有收到邮件：
                        <br/>1、请检查您的邮箱是否正确，或 <a href="javascript:void(0);" onclick="changeMail();">[更换邮箱]</a>
                        <br/>2、因网络原因，邮件发送延迟，或 <a href="javascript:void(0);" onclick="resend('<%=email%>');">[重新发送]</a>
                        <br/>生成好测试的邮箱地址：<a href="/core/w2/customer/validateEmailCallback?code=<%=code%>"
                                             target="_blank">/core/w2/customer/validateEmailCallback?code=<%=code%></a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</div>


<jsp:include page="/w2/bottom.jsp"/>
</body>
</html>
