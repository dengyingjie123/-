<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.config.Config" %>
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
//    Object money = request.getAttribute("money");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<title>银行卡认证</title>

<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/customer/bankCardAuth.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/reset.css">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/style.css">

    <!--[if lte IE 9]>
    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/respond.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/html5.js"></script>
    <![endif]-->

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/bank.js"></script>



</head>

<body>



<%--top--%>
<jsp:include page="/w2/top.jsp"/>
<%--top 结束--%>

    <div id="position" class="w1000">
    </div>
    
    <div id="container" class="w1000">

		<div id="title">银行卡认证</div>
        <div id="content" style="vertical-align:middle;">
            <form class="form-horizontal" id="bindCardform"
                  action="<%=Config.getWebRoot()%>/w2/customer/checkBankCardMoney" method="post">
        	<table>
            	<tr>
                	<td align="right" style="font-weight: bold; font-size:12px;" colspan="2">为验证银行卡的有限性，已将验证金额转到银行卡上，请 1 至 5 分钟内查看转账结果。</td>
                </tr>
            	<tr>
                	<td align="right">金额</td><td style="padding: 0px 0px 0px 30px;">
                    <input type="text" class="bankCardNum"  id="bankNumber" placeholder="请输入收到的金额（单位：元）" maxlength="19"
                                                                                           name="verifyMoney" />
                     </td>
                </tr>

                <tr>
                    <td/>
                <td style="padding: 0px 0px 0px 30px;">
                    <input type="button" class="btn-cardAuth" id="bankCardSubmmitButton" onclick="bankCardSubmit()"  >
                </td>
                </tr>
            </table>
             </form>
        </div>
 
    </div>

<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
