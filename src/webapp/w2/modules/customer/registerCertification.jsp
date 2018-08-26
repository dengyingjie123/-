<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    //获取不到用户的信息直接跳转
    if (loginUser == null) {
        String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;
    }
    //获取请求中的mark
    //默认显示设置交易密码
    String mark = "";
    if (request.getAttribute("mark") != null) {
        mark = (String) request.getAttribute("mark");
    }

    if (StringUtils.isEmpty(mark)) {
        mark = "";
    }

    String accountName = (String)request.getAttribute("accountName");
    String idCardNumber = (String)request.getAttribute("idCardNumber");

%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title></title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/registerCertification.css" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/style.css">

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/transPassword.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/getMobile.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/mobileValidate.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/realAuth.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/bank.js"></script>

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
<div id="position" class="w1200">
</div>

<div id="container" class="w1180">
    <div class="u-tabs1 fn-clear" style="pointer-events:none">
        <%
            if (mark.equals("password")) {
        %>
        <a href="javascript:void(0);" id="transactionPasswordID" class="current c1">①：交易密码</a>
        &nbsp;&nbsp;&nbsp;&nbsp;②：实名认证
        <%
        } else {%>

        &nbsp;&nbsp;&nbsp;&nbsp;①：交易密码
        <a href="javascript:void(0);" id="registerCertificatioinID" class="current c1">②：实名认证</a>
        <%
            }
        %>
    </div>


    <div class="tabs-wrap">
        <div class="item" style="display:block">
            <%
                if (mark.equals("password")) {
            %>
            <div class="item" style="display:block">
                <%}else{%>
                <div class="item"  style="display:none">
                <%}%>
                    <div class="pay-pass">
                        <div class="content" style="padding-left: 123px;">

                            <form class="form-horizontal" id="updatepassword"
                                  action="<%=Config.getWebRoot()%>/w2/customer/transPasswordDo_2"
                                  method="post">
                                <table>
                                    <tr>

                                        <td align="right">设置交易密码</td>
                                        <td/>
                                    </tr>
                                    <%--<tr>--%>
                                        <%--<td align="right">手机号码</td>--%>
                                        <%--<td style="padding: 0px 0px 0px 30px;">--%>
                                            <%--<input type="text" class="input_size myinput-control" disabled="disabled"--%>
                                                   <%--placeholder="请输入手机号码" id="mobile"><span id="mobileTip"--%>
                                                                                           <%--style="font-weight: bold"></span>--%>
                                        <%--</td>--%>
                                        <%--<td/>--%>
                                    <%--</tr>--%>
                                    <%--<tr>
                                        <td align="right">手机动态码</td>
                                        <td style="padding: 0px 0px 0px 30px;">

                                            <input type="text" class="bankCardNum" name="mobiValidateCode"
                                                   id="mobiValidateCode" placeholder="请输入动态码">
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="button" id="code" value="获取动态码"
                                                   style="color: #d28d2a;text-decoration: none;"
                                                   onclick="getMobiCode('mobicodevalue')"/> <span id="mobicodevalue"
                                                                                                  style="font-weight: bold"></span>
                                        </td>
                                    </tr>--%>

                                    <%
                                        if (hasTransactionPassword != null && hasTransactionPassword.equals("1")) {
                                    %>
                                    <tr>
                                        <td align="right">旧的交易密码</td>
                                        <td style="padding: 0px 0px 0px 30px;">
                                            ><input type="password" maxlength="6" class="input_size myinput-control"
                                                    name="oldTransPassword"
                                                    id="oldTransPassword"
                                                    onblur="VerificationFirst('oldTransPasswordTip',this.value)"
                                                    placeholder="请输入旧的交易密码"> <span id="oldTransPasswordTip"
                                                                                   style="font-weight: bold"></span>
                                        </td>
                                        <td/>
                                    </tr>
                                    <%
                                        }
                                    %>
                                    <tr>
                                        <td align="right">设置新的交易密码</td>
                                        <td style="padding: 0px 0px 0px 30px;">
                                            <input type="password" maxlength="6" class="input_size myinput-control"
                                                   name="transPassword"
                                                   id="transPassword"
                                                   onblur="VerificationFirst('transPasswordTip',this.value)"
                                                   placeholder="请输入新的交易密码">
                                        </td>
                                        <td><span id="transPasswordTip" style="font-weight: bold;color:#008000;font-size: 14px;">交易密码只能是6位数字</span></td>
                                    </tr>
                                    <tr>
                                        <td align="right">确认新的交易密码</td>
                                        <td style="padding: 0px 0px 0px 30px;">

                                            <input type="password" maxlength="6" class="input_size myinput-control"
                                                   name="transPasswordAgain"
                                                   id="transPasswordAgain" onBlur="isDoublePassword()"
                                                   placeholder="请输入重复的交易密码"><span id="transPasswordAgainTip"
                                                                                  style="font-weight: bold"></span></td>
                                        <td/>
                                    </tr>
                                    <tr>
                                        <td/>

                                        <td style="padding: 0px 0px 0px 30px;">
                                            <input type="button" class="btn_next mybtns btns-myprimary"
                                                   id="transpasswordSubmmitButton"  value="下一步"
                                                   onclick="javascript:validateSubmit4TPassWord()"/> &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="button" class="btn_next mybtns btns-myprimary" value="忽略"
                                                   onclick="window.location.href='<%=Config.getWebRoot()%>/w2/home.jsp'"/>
                                        </td>
                                        <td/>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                    if (!mark.equals("password")) {
                %>

                <div class="item"  style="display:block" >
                  <%}else {%>
                    <div class="item"  style="display:none">
                    <%}%>

                        <div class="pay-pass">
                            <div class="content" style="padding-left: 123px;">
                                <form class="form-horizontal" id="coedform" action="<%=Config.getWebRoot()%>/w2/customer/verifyRealNameAndBindingBankCard" method="post">
                                <table>

                                    <tr>
                                        <td align="right">持卡人</td><td style="padding: 0px 0px 0px 30px;">
                                        <input type="text" class="myinput-control myinput-controlWH"   onblur="checkCardHolder()" id="cardHolder" placeholder="请输入姓名" name="customerAccount.name" style="width:300px" /></td>
                                        <td><span class="m-tip" id="cardHolderTip" style="font-weight: bold"></span></td>
                                    </tr>
                                    <tr>
                                        <td align="right">身份证</td><td style="padding: 0px 0px 0px 30px;">
                                        <input type="text" class="myinput-control myinput-controlWH" name="realId" maxlength="18"  onblur="identityCardCheck()" placeholder="请输入身份证" id="realId" style="width:300px">
                                    </td>
                                        <td><span class="m-tip" id="realTip" style="font-weight: bold"></span></td>
                                    </tr>

                                    <tr>
                                        <td align="right">卡号</td><td style="padding: 0px 0px 0px 30px;">
                                        <input type="text" class="myinput-control myinput-controlWH" onBlur="checkBankNumber()"
                                               id="bankNumber" placeholder="请输入卡号" maxlength="19"  name="customerAccount.number" style="width:300px">
                                    </td>
                                        <td><span class="m-tip" id="bankNumberTip" style="font-weight: bold"></span></td>
                                    </tr>
                                    <tr>
                                        <td align="right">银行</td><td style="padding: 0px 0px 0px 30px;">
                                        <select class="myinput-control" name="customerAccount.bankCode" id="bankList" style="width:300px">
                                            <option value="0">请选择</option>
                                        </select>
                                    </td>
                                        <td><span class="m-tip" id="bankAreaTip" style="font-weight: bold"></span></td>
                                    </tr>
                                    <tr>
                                        <td/>
                                        <td style="padding: 0px 0px 0px 30px;">
                                            <input type="button" class="btns-mynext" style="margin-top: 10px;" id="bankCardSubmmitButton" onClick="customerAccountSubmit()"  value="下一步" >
                                        </td>
                                        <td/>
                                    </tr>

                                    <tr style="display: none">
                                        <td align="right">开户行</td><td style="padding: 0px 0px 0px 30px;">

                                        <select class="banks"  id="bankOption" name="customerAccount.bankBranchName"  onblur="checkBankOption()"></select>
                                    </td>
                                        <td><span class="m-tip" id="bankOptionTip" style="font-weight: bold" ></span></td>
                                    </tr>
                                </table>
                            </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/w2/bottom.jsp"/>
</body>
</html>
