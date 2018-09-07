<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.system.CodeType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="login.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>点金派</title>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/index/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/login.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>

    <script src="<%=Config.getWebRoot()%>/include/extensions/frameworkplus.js"></script>
    <script src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <%--登陆脚本--%>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/login.js"></script>

</head>

<body>


<%--top--%>
<jsp:include page="/w2/top.jsp"/>
<%--top 结束--%>

<div id="login_middle">
    <div id="loginContainer" class="w1000">
        <div id="loginArea">
            <form id="loginForm" name="loginForm" method="post"
                  class="form-horizontal">

                <div class="login_table_1">
                    <span class="loginTip" style="font-size: 26px;">用户登录</span>
                    <label class="login-reg">
                        没有账户？ <a
                            href="<%=Config.getWebRoot()%>/w2/customer/MobileRegisterRequest"
                            style="color:#ef890c;">立即注册</a>
                    </label>
                    <table id="" cellspacing="3" cellpadding="0" class="login-table">
                        <tr>
                        </tr>
                        <tr>
                            <td align="center">用&nbsp;&nbsp;户&nbsp;&nbsp;名&nbsp;&nbsp;</td>
                            <td colspan="3">
                                <input class="txt_user" type="text" id="personalName" onblur="checkLoginName()" maxlength="11" name="loginName" placeholder="用户名" style=" background:url(<%=Config.getWebRoot()%>/w2/img/login_name.gif);" value="13888939712">
                            </td>
                            <td style="width:100px;"><span id="personalNameTip"></span></td>
                        </tr>
                        <tr>
                            <td align="center">密&nbsp;&nbsp;码&nbsp;&nbsp;</td>
                            <td colspan="3"><input class="txt_user" type="password" id="personalPassword" name="password" placeholder="密码" onblur="checkPassword()" style="background:url(<%=Config.getWebRoot()%>/w2/img/login_password.gif);" value="123456y">

                            </td>
                            <td><span id="personalPasswordTip"></span></td>
                        </tr>
                        <tr>
                            <td align="center">验&nbsp;&nbsp;证&nbsp;&nbsp;码&nbsp;&nbsp;</td>
                            <td><input class="login_verify myinput-control" type="text" placeholder="验证码"
                                       name="captcha"
                                       id="captcha" onblur="checkCaptcha()"
                            ></td>
                            <td>
                                <img id="codeId" onclick="javascript:onClickRefresh(this,1)"
                                     src="<%=Config.getWebRoot()%>/w2/system/getCaptcha.action?u=<%=CodeType.LOGIN%>"
                                     style="background:url(<%=Config.getWebRoot()%>/w2/img/code.gif);width:100px;height:32px; border:0px;">
                                <input type="hidden" id="u" name="u" value="<%=CodeType.LOGIN%>"/>
                            </td>
                            <td><a href="javascript:void(0);" onclick="javascript:refreshImg('#codeId', <%=CodeType.LOGIN%>);">换一张</a>&nbsp;&nbsp;
                            </td>
                            <td><span
                                    id="codeTip" style="color: #e74c3c;"></span></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan=""><input type="checkbox"
                                                  style="background:url(<%=Config.getWebRoot()%>/w2/img/bg_remenberpassword.png.gif);vertical-align: middle">记住密码
                            </td>
                            <td align="right"><a
                                    href="<%=Config.getWebRoot()%>/w2/modules/customer/forgetPassword.jsp"
                                    style="color:#ff000c;">&nbsp;&nbsp;&nbsp;&nbsp; 忘记密码</a></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan="6" align="left">
                                <input type="button" id="login" onclick="submitForm()"
                                       class="btns-code btns-myprimary" style="width: 261px;height: 36px;margin-left: 0px;"
                                       value="立即登录"/>
                            </td>
                        </tr>
                    </table>

                </div>
                <div class="login_table_3">

                    <img src="<%=Config.getWebRoot()%>/w2/img/login-five.png" class="login_table_3_img">
                </div>
                <div class="login_table_2">
                    <img src="<%=Config.getWebRoot()%>/w2/img/login-tree-1.png" class="login_table_2_img">
                </div>


            </form>
        </div>
    </div>
</div>

<jsp:include page="/w2/bottom_login_register.jsp"/>

</body>
</html>
