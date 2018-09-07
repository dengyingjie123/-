<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.info.LegalAgreementPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="login.jsp" %>

<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    }
    LegalAgreementPO legalAgreement = (LegalAgreementPO) request.getAttribute("legalAgreement");

%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>客户注册</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/register.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/member.css">

    <script language="javascript" type="text/javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="<%=Config.getWebRoot()%>/w2/js/modules/customer/register.js"></script>
    <script language="javascript" type="text/javascript" src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script language="javascript" type="text/javascript" src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/include/extensions/frameworkplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>

</head>

<body>



<!-- 顶部栏开始 -->
<jsp:include page="/w2/top.jsp"/>
<!-- 顶部栏结束 -->




<div id="mainArea">
    <form id="mobileRegisterForm" method="post">
        <div id="loginContainer" class="w1000">
            <div id="loginArea">
                <div class="loginArea1">
                    <div style="width:400px;">
                        <span class="loginTip">用户注册</span>
                        <label class="login-reg">
                            已有账户？<a href="<%=Config.getWebRoot()%>/w2/customer/LoginRequest" style="color:#ef890c;">立即登录</a>
                        </label>
                        <table>
                            <tr>
                                <td align="right">
                                    <div class="font-33px">手机号码</div>
                                </td>
                                <td><input type="text" class="reg-input mobile" id="mobile" name="personal.loginName"
                                           placeholder="请输入手机号码"
                                           onkeyup="inputNumber()" onblur="checkMobile4Register()" maxlength="11"/>

                                    <div class="font-bold-weight"></div>
                                </td>
                                <td>
                                    <div id="mobileTip"></div>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <div class="font-33px">手机动态码</div>
                                </td>
                                <td><input type="text" class="reg-input code" style="width: 158px;"
                                           id="mobiValidateCode" name="mobileCode" placeholder="请输入手机动态码"
                                           onblur="verificationCode('mobile',this.id)"/>
                                    <button type="button" class="btns-code btns-myprimary" id="code"
                                            style="width: 97px;"
                                            onclick="mobileRegisterCode('mobiValidateCode')">获取动态码
                                    </button>
                                    <div class="font-bold-weight"></div>
                                </td>
                                <td>
                                    <div id="mobileCode"></div>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <div class="font-33px">登录密码</div>
                                </td>
                                <td><input id="pwd" type="password" name="personal.password"
                                           placeholder="请输入密码" onblur="checkPassWord()" class="reg-input p"/>

                                    <div class="font-bold-weight" style="color: #ef890c"> 6-18位字符，至少包含数字、字母</div>
                                </td>
                                <td style="font-size: 12px;">
                                    <div id="password"></div>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <div class="font-33px">确认密码</div>
                                </td>
                                <td><input class="reg-input p-again" id="pwdAgain" type="password"
                                           placeholder="再次输入密码"
                                           onblur="checkPwdAgain()"/>

                                    <div class="font-bold-weight"></div>
                                </td>
                                <td style="font-size: 12px;">
                                    <div id="passwordTip"></div>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <div class="font-33px">推荐码</div>
                                </td>
                                <td><input class="reg-input referralCode" id="referralCode" placeholder="请输入推荐码（可选）" name="referralCode" type="text"/>

                                    <div class="font-bold-weight"></div>
                                </td>
                                <td style="font-size: 12px;">
                                    <div id="referralCodeTip"></div>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td style="font-size:12px;"><input type="checkbox" id="agree"
                                                                   style="vertical-align: middle;margin-left: 20px;"
                                                                   onclick="onClickConsentAgreement()"/> 我已阅读并同意
                                    <a href="<%=Config.getWebRoot()%>/w2/modules/article/agreement.jsp"
                                       target="_blank" id="consentAgreement"
                                       title="
点金派会员服务协议"
                                       style="text-decoration:none; text-decoration:none; color:#ef890c;">《点金派会员服务协议》</a>

                                    <div class="font-bold-weight" id="agreementService"></div>
                                    <input type="hidden" name="legalAgreement.name"
                                           value="<%=legalAgreement.getName()%>">
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><a href="#">

                                    <input type="button" id="login" class="btns-code btns-myprimary"
                                           style="width: 261px;height: 36px;margin-left: 20px;" id="RegisterButton"
                                           onclick="submitRegisterForm()" value="同意并注册"/>
                                </a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div class="loginArea2">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_five.png">
                </div>
                <div class="loginArea3">
                    <img src="<%=Config.getWebRoot()%>/w2/img/line_register.png">
                </div>

            </div>
        </div>
    </form>
</div>





<jsp:include page="/w2/bottom_login_register.jsp"/>

</body>
</html>
