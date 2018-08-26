<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="/w2/home.jsp" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.system.CodeType" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>找回密码</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/forgetPassword.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/style.css">

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/include/extensions/frameworkplus.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/forgetPassword.js"></script>

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
        <form class="form-horizontal" id="formForGetPassword"
              action="<%=Config.getWebRoot()%>/w2/customer/resetPassword4Mobile4W"
              method="post">

            <table>
                <tr>
                    <td align="right">手机号码</td>
                    <td style="padding: 0px 0px 0px 30px;">
                        <input type="text" class="form-control" onblur="checkUserMobile()"
                               id="mobile" placeholder="请输入手机号码"
                               name="mobile" onkeyup="inputNumber()" maxlength="11"
                               style="background:#fafafa;width:200px;"/>

                    </td>
                    <td><span id="mobileTip" style="font-weight: bold"></span></td>
                </tr>
                <tr>
                    <td align="right">验证码</td>
                    <td style="padding: 0px 0px 0px 30px;">
                        <input type="text" class="form-control"
                               placeholder="验证码" id="captcha" name="captcha" onblur="checkCode()"
                               style="background:#fafafa;width:150px;"/>
                    </td>
                    <td>
                        <a href="javascript:void(0);" onclick="javascript:refreshImg('#codeId', <%=CodeType.FIND_PASSWORD%>);">
                            <img id="codeId"
                                 src="<%=Config.getWebRoot()%>/w2/system/getCaptcha.action?u=<%=CodeType.FIND_PASSWORD%>" width="93" height="37">
                            换一张</a><span  id="captchaTip" style="font-weight: bold"></span>
                    </td>
                </tr>

                <tr>
                    <td>                    <input type="hidden" id="use" name="u" value="<%=CodeType.FIND_PASSWORD%>"/>
                    </td>
                    <td style="padding: 0px 0px 0px 30px;">
                        <input type="button" id="nextForGetPasswordSubmit" value="下一步"
                               class="btn btnsprimary" onclick="nextSubmit4ForGetPS()"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</div>


<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
