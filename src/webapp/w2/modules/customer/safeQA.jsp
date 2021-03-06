<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.ProtectionQuestionWVO" %>
<%@ page import="java.util.List" %>

<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
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
    <title>设置安全保护问题</title>

    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w/css/reset.css">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w/css/style.css">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w/css/member.css">
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/safeQA.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/safeQA.js"></script>

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

<div class="nav-table">
    <div class="nav-table-div">
        <table cellspacing="0"  cellpadding="6"  align="center" style="height: 72px;">
            <tr>
                <td colspan="9">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_left.png" >
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_realauth.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_trade.png" align="center">
                    <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                    <img src="<%=Config.getWebRoot()%>/w2/img/icon_safe_light.png" align="center">
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
                <td>
                    交易密码
                </td>
                <td style="color: #ff6900">
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
                <td style="width: 145px;"/>
            </tr>
        </table>
    </div>
</div>

<div id="container" class="w1200">

    <div id="title">设置安全保护问题</div>

    <%
        if (loginUser != null) {
    %>

        <div id="content">
            <form id="protectionQuestionForm" action="<%=Config.getWebRoot()%>/w2/customer/saveProtectionQuestion" method="post">
                <table>
                    <tr>
                        <td align="right">问题一</td>
                        <td style="padding: 0px 0px 0px 30px;"><select class="banks" name="question1" id="question1" onchange="javascript:validateSelect(this)">
                            <%
                                List<ProtectionQuestionWVO> questions = (List<ProtectionQuestionWVO>)request.getAttribute("questions");
                                if(questions != null && questions.size() > 0) {
                                    for(ProtectionQuestionWVO question : questions) {
                            %>
                            <option value="<%=question.getQuestionId()%>"><%=question.getQuestionContent()%></option>
                            <%
                                    }
                                }
                            %>
                        </select></td>
                    </tr>
                    <tr>
                        <td align="right">答案</td>
                        <td style="padding: 0px 0px 0px 30px;"><input type="text"class="myinput-control" style="height: 20px;width: 273px;'"  name="answer1" id="answer1"/></td>
                    </tr>
                    <tr>
                    <tr>
                        <td align="right">问题二</td>
                        <td style="padding: 0px 0px 0px 30px;"><select class="banks" name="question2" id="question2" onchange="javascript:validateSelect(this)">
                            <%
                                List<ProtectionQuestionWVO> questions2 = (List<ProtectionQuestionWVO>)request.getAttribute("questions");
                                if(questions != null && questions.size() > 0) {
                                    for(ProtectionQuestionWVO question : questions2) {
                            %>
                            <option value="<%=question.getQuestionId()%>"><%=question.getQuestionContent()%></option>
                            <%
                                    }
                                }
                            %>
                        </select></td>
                    </tr>
                    <tr>
                        <td align="right">答案</td>
                        <td style="padding: 0px 0px 0px 30px;"><input type="text" class="myinput-control" style="height: 20px;width: 273px;'" name="answer2" id="answer2"/></td>
                    </tr>
                    <tr>
                        <td align="right">问题三</td>
                        <td style="padding: 0px 0px 0px 30px;"><select class="banks" name="question3" id="question3" onchange="javascript:validateSelect(this)">
                            <%
                                List<ProtectionQuestionWVO> questions3 = (List<ProtectionQuestionWVO>)request.getAttribute("questions");
                                if(questions != null && questions.size() > 0) {
                                    for(ProtectionQuestionWVO question : questions3) {
                            %>
                            <option value="<%=question.getQuestionId()%>"><%=question.getQuestionContent()%></option>
                            <%
                                    }
                                }
                            %>
                        </select></td>
                    </tr>
                    <tr>
                        <td align="right">答案</td>
                        <td style="padding: 0px 0px 0px 30px;"><input type="text" class="myinput-control" style="height: 20px;width: 273px;'" name="answer3" id="answer3"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="padding: 0px 0px 0px 30px;">
                            <input type="button"   class="btns-mynext" style="margin-top: 12px;" value="下一步"   onclick="javascript:submitSelect()">
                           </td>
                    </tr>
                </table>
            </form>
        </div>

    <%
    } else {
    %>

        <div id="content">
            <a href="<%=Config.getWebRoot()%>/w2/customer/LoginRequest">请先登录</a>
        </div>

    <%
        }
    %>

</div>


<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
