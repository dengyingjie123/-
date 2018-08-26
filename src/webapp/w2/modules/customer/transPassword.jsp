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
    String hasTransactionPassword = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
        if (request.getAttribute("hasTransactionPassword") != null) {
            hasTransactionPassword = request.getAttribute("hasTransactionPassword").toString();
        }
    } else {
        String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>点金派</title>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/index/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/production/production.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/accountValidate.css" rel="stylesheet" type="text/css"/>

    <!--[if lte IE 9]>
    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/respond.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/html5.js"></script>

    <![endif]-->
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/transPassword.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/getMobile.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/mobileValidate.js"></script>

</head>

<body>
<div id="container">

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
                <table cellspacing="0"  cellpadding="6"  align="center" style="height:75px;">
                    <tr>
                        <td colspan="9">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_left.png" >
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_realauth.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_trade_light.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_safe.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_mobile.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_key.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_email_.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_bankcard.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_right.png" >
                        </td>
                    </tr>
                    <tr align="center" style="margin-top: 16px;">
                        <td style="width: 125px;"/>
                        <td >
                            实名认证
                        </td>
                        <td style="color: #ff6900">
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
                        <td>
                            银行卡
                        </td>
                        <td style="width: 135px;"/>
                    </tr>
                </table>
            </div>
        </div>

    <div class="r fn-clear" style="width: 1200px;">

        <%
            if (loginUser != null) {
        %>

        <div class="mobile-intro">
            <div class="ht">设置交易密码</div>
            <div class="content" style="height:334px;">

                <form class="form-horizontal" id="updatepassword" style="margin-top: -26px;margin-left: 20px;"
                      action="<%=Config.getWebRoot()%>/w2/customer/transPasswordDo" method="post">

                    <table cellpadding="8" style="font-size:16px; margin-left:30px; color:#000000">
                        <tr>
                            <td class="td-right">手机号</td>
                            <td colspan="2"><input type="text" placeholder="请输入手机号" class="form-control" id="mobile" name="mobile" readonly="readonly"
                                                   value=""  style="background:#f1f1f1">
                            </td>
                            <td/>
                        </tr>
                        <tr>
                            <td class="td-right">手机动态码</td>
                            <td colspan="2">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="mobiValidateCode"
                                           id="mobiValidateCode" placeholder="请输入动态码">
                                <span class="input-group-btn" >
                                    <input type="button" class="btn btn-default" id="code" value="获取动态码" style="margin-left:30px;width:100px;height:36px;;"
                                           onclick="getMobiCode('mobicodevalue')"/>
                                </span>
                                </div>
                                <!-- /input-group -->

                            </td>
                        </tr>


                        <%
                            if (hasTransactionPassword != null && hasTransactionPassword.equals("1")) {
                        %>
                        <%--<div class="form-group">--%>

                            <%--<tr>--%>
                                <%--<td class="td-right">旧的交易密码</td>--%>
                                <%--<td colspan="2"><input type="password" maxlength="6" name="oldTransPassword" id="oldTransPassword"--%>
                                                       <%--class="form-control" placeholder="确认新的交易密码"--%>
                                                       <%--onblur="VerificationFirst('oldTransPasswordTip',this.value)">--%>
                                <%--</td>--%>
                            <%--</tr>--%>
                        <%--</div>--%>
                        <%
                            }
                        %>


                        <tr>
                            <td class="td-right"> 设置新的交易密码</td>
                            <td><input type="password" maxlength="6" class="form-control" name="transPassword" id="transPassword"
                                       onblur="VerificationFirst('transPasswordTip',this.value)" placeholder="请输入交易密码">
                            </td>

                            <td align="left"><label style="font-size:12px; color:#ff3f3f; ">交易密码只能是6位数字</label></td>
                        </tr>
                        <tr>
                            <td class="td-right"> 确认新的交易密码</td>
                            <td colspan="2"><input type="password" maxlength="6" class="form-control" name="transPasswordAgain"
                                                   id="transPasswordAgain" onblur="isDoublePassword()"
                                                   placeholder="请输入重复的交易密码"></td>

                        </tr>
                        <tr>
                            <td/>

                            <td align="left"><input type="button"   class="btns-mynext" style="margin-top: 12px;"
                                                    value="下一步" id="transpasswordSubmmitButton"
                                                    onclick="javascript:validateSubmit4TPassWord()"></td>
                        </tr>
                    </table>

                </form>

            </div>

        </div>

        <%
        } else {
        %>
        <div class="content">
            <a href="<%=Config.getWebRoot()%>/w2/customer/LoginRequest">未登录</a>
        </div>
        <%
            }
        %>

    </div>

        <div style="height: 50px;"></div>

        <jsp:include page="/w2/bottom.jsp"/>
</div>


</body>
</html>