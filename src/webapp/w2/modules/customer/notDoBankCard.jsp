<%--
  Created by IntelliJ IDEA.
  User: 张舜清
  Date: 7/24/2015
  Time: 10:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.dao.MySQLDao" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerMoneyPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.wvo.sale.InvestmentPlanWVO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerAccountPO" %>
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
    String accountName =(String) request.getAttribute("accountName");
    String accountNo =(String) request.getAttribute("accountNo");


%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<title>银行卡认证</title>

<link href="<%=Config.getWebRoot()%>/w2/css/customer/bankCardAuth.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>



    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/style.css">

    <!--[if lte IE 9]>
    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/respond.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/html5.js"></script>
    <![endif]-->
    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/transPassword.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/getMobile.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/mobileValidate.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/layer/layer.js"></script>
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
        <div id="content">
            <form class="form-horizontal" id="doBankCardForm" action="" method="post">

        	<table>
                <tr>
                    <td align="right" style="font-weight: bold; font-size:12px;" colspan="2">未成功认证银行卡，请确认收到的金额或更改卡号</td>
                </tr>

            	<tr>
                	<td align="right">持卡人</td><td style="padding: 0px 0px 0px 30px;">
                    <input type="text" class="customer"  id="cardHolder" placeholder="请输入持卡人" name="customerAccount.name" value="<%=accountName%>" readonly="readonly" /></td>

                </tr>
            	<tr>
                	<td align="right">卡号</td><td style="padding: 0px 0px 0px 30px;">

                    <input type="text" class="bankCardNum"  readonly="readonly" value="<%=accountNo%>" readonly="readonly"
                           id="bankNumber"   maxlength="19">
                </td>

                </tr>

            	<tr>
                	<td/>
                    <td style="padding: 0px 0px 0px 30px;">
                        <input type="button" class="btns-bank btns-bankCard" id="goChangeBankCardSubimt" onclick="doChangeBankCard()" value="解绑">
                        <input type="button" class="btns-bank btns-bankCard" id="goAnthenticaBankCard" onclick="doAnthenticaBankCard()" value="去认证">
                    </td>
                    <td/>
                </tr>
            </table>
            </form>
        </div>
 
    </div>


<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
