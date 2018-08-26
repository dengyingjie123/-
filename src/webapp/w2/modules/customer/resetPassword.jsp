<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="/w2/home.jsp" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.config.Config" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>重置密码</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/forgetPassword.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/include/extensions/frameworkplus.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/changePassword.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/resetPassword.js"></script>

</head>

<body>


<%--top--%>
<jsp:include page="/w2/top.jsp"/>
<%--top 结束--%>

<div id="position" class="w1000">
</div>

<div id="container" class="w1000">

    <div id="title">重置密码</div>

    <div id="content">
        <form class="form-horizontal" id="resetPasswordForm" action="/core/w2/customer/resetPassword"
              method="post">
            <table>
                <tr>
                    <td align="right">设置新的密码</td>
                    <td style="padding: 0px 0px 0px 30px;">
                        <input type="password" class="form-control" name="password"
                               id="newPassword" placeholder="请输入新密码" onblur="checkNewPassword()">

                    </td>
                    <td><span id="newPasswordTip" style="font-weight: bold"></span></td>
                </tr>
                <tr>
                    <td align="right">确认新的密码</td>
                    <td style="padding: 0px 0px 0px 30px;">
                        <input type="password" class="form-control" name="newPasswordAgain"
                               id="newPasswordAgain" placeholder="请输入重复密码" onblur="checkNewPasswordAgain()">

                    </td>

                    <td><span id="newPasswordAgainTip" style="font-weight: bold"></span></td>
                </tr>

                <tr>
                    <td>
                    </td>
                    <td style="padding: 0px 0px 0px 30px;">
                        <input type="button" id="resetPasswordButton" value="重置"
                               class="btn btnsprimary" onclick="resetPWDSubmit()"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</div>


<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
