<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.config.Config4W" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="login.jsp" %>
<%
    // 判断用户是否有输入手机号码来找回，如果没有直接重定向到忘记密码界面
    CustomerPersonalPO customerPersonal = null;
    if (request.getSession().getAttribute(Config4W.TEMP_SESSION_LOGINUSER_STRING) != null) {
        customerPersonal = (CustomerPersonalPO) request.getSession().getAttribute(Config4W.TEMP_SESSION_LOGINUSER_STRING);
    } else {
        String loginURL = Config.getWebRoot() + "/w2/customer/forgetPassword";
        response.sendRedirect(loginURL);
        return;
    }
%>
<!DOCTYPE html><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>找回密码</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/forgetPassword.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>


    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/mobileValidate.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/forGetPasswordVerifyMobile.js"></script>
</head>

<body>


<%--top--%>
<jsp:include page="/w2/top.jsp"/>
<%--top 结束--%>
<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item" >投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" style="color:#d28d2a">我的账户</a>
        </span>
    </div>
</div>
<div id="position" class="w1000">
</div>

<div id="container" class="w1000">

    <div id="title">找回密码</div>
    <div id="content">

        <form class="form-horizontal" id="formVerifyMobileCode"
              action="<%=Config.getWebRoot()%>/w2/customer/resetPS4VerifyMobileCode"
              method="post">
        <table>
            <tr>
                <td align="right">手机号码</td>
                <td style="padding: 0px 0px 0px 30px;">

                    <input type="text" id="mobile"  name="personal.mobile" class="form-control"
                           placeholder="请输入手机号码" onkeyup="inputNumber()" disabled="disabled"
                           onblur="checkMobile()" maxlength="11" value="<%=customerPersonal.getMobile()%>" style="background:#fafafa;width:200px;"/></td>
                <td/>
            </tr>
            <tr>
                <td align="right">手机验证码</td>
                <td style="padding: 0px 0px 0px 30px;">
                    <input type="text" class="form-control" name="mobiValidateCode" placeholder="请输入验证码"
                           id="mobiValidateCode" onblur="checkVerification()"
                                                              style="background:#fafafa;width:150px;"/></td>
                </td>
                <td>
                    <input type="button" style="color: #d28d2a;
	    text-decoration: none;" id="code" value="获取动态码" onclick="mobileRegisterCode('mobiValidateCode')" />

                </td>
            </tr>

            <tr>
                <td></td>
                <td style="padding: 0px 0px 0px 30px;">
                    <input type="button" id="nextSubmitVerifyMobileCode" value="下一步" title="下一步"
                           class="btn btnsprimary"
                           onclick="nextSubmit4VerifyMobileCode()"
                                                              value="下一步"></td>
            </tr>
        </table>
            </form>
    </div>

</div>


<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
