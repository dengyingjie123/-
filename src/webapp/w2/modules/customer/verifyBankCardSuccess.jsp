<%--
  Created by IntelliJ IDEA.
  User: 张舜清
  Date: 7/24/2015
  Time: 10:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="../../../w2/info.jsp" %>
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

    <script src="<%=Config.getWebRoot()%>/w2/js/framework/layer/layer.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/bank.js"></script>

</head>

<body>



<%--top--%>
<jsp:include page="/w2/top.jsp"/>
<%--top 结束--%>
<%--导航栏--%>
<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item">投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" style="color:#d28d2a">我的账户</a>
        </span>
    </div>
</div>


<div class="nav-table">
    <div class="nav-table-div">
        <table cellspacing="0"  cellpadding="6"  align="center">
            <tr>
                <td colspan="9">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_left.png" >
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_realauth.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_trade.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_safe.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_mobile.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_key.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_email_.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_bankcard_light.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_right.png" >
                </td>
            </tr>
            <tr align="center" style="margin-top: 16px;">
                <td style="width: 125px;"/>
                <td>
                    实名认证
                </td>
                <td>
                    交易密码
                </td>
                <td>
                    安全保护
                </td>
                <td>
                    手机认证
                </td>
                <td>
                    登录密码
                </td>
                <td >
                    电子邮箱
                </td>
                <td style="color: #ff6900">
                    银行卡
                </td>
                <td style="width: 135px;"/>
            </tr>
        </table>
    </div>
</div>

<div id="position" class="w1200">
</div>
    <div id="container" class="w1200" style="margin-top: -42px;">
 
		<div id="title">银行卡认证</div>
        <div id="content">
            <form class="form-horizontal" id="coedform" action="<%=Config.getWebRoot()%>/w2/customer/authenticationindCard" method="post">
        	<table>
            	<tr>
                	<td align="right">持卡人</td><td style="padding: 0px 0px 0px 30px;">
                    <input type="text"  class="myinput-control" style="height: 20px;width: 273px;background: #f1f1f1"
                           id="cardHolder"  value="<%=accountName%>" readonly="readonly"/></td>
                <td><span class="m-tip" id="cardHolderTip" style="font-weight: bold"></span></td>
                </tr>
            	<tr>
                	<td align="right">卡号</td><td style="padding: 0px 0px 0px 30px;">

                    <input type="text" class="myinput-control" style="height: 20px;width: 273px;background: #f1f1f1"
                           id="bankNumber"   maxlength="19"  value="<%=accountNo%>"   readonly="readonly"/>
                </td>
                    <td><span class="m-tip" id="bankNumberTip" style="font-weight: bold"></span></td>
                </tr>

            	<tr>
                	<td/>
                    <td style="padding: 0px 0px 0px 30px;">

                        <input type="button" class="btns-mynext" style="margin-top: 10px;" id="unBindBankCardButton" onclick="unBindBankCard()" value="解绑">
                    </td>
                    <td/>
                </tr>
            </table>
            </form>
        </div>
 
    </div>
<div style="height: 80px;"></div>

<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
