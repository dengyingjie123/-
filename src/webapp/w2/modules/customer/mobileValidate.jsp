<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="../../info.jsp" %>
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
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<title>点金派</title>
<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/index/index.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/production/production.css" rel="stylesheet" type="text/css"/>
 <link href="<%=Config.getWebRoot()%>/w2/css/customer/accountValidate.css" rel="stylesheet" type="text/css"/>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/mobileValidate.js"></script>
    <script src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>

</head>

<body>
<div id="container">

    <%--top--%>
    <jsp:include page="/w2/top.jsp"/>
    <%--top 结束--%>
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
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_trade.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_safe.png" align="center">
                            <img src="<%=Config.getWebRoot()%>/w2/img/bg_line_middle.png">
                            <img src="<%=Config.getWebRoot()%>/w2/img/icon_mobile_light.png" align="center">
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
                        <td>
                            安全保护
                        </td>
                        <td  style="color: #ff6900">
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
  
   <div class="mobile-intro">
        <div class="ht">验证原手机号</div>
        <div class="content">

            <form class="form-horizontal" id="mobileForm" action="<%=Config.getWebRoot()%>/w2/customer/validateOldMobile"
                  method="post">
            <table cellpadding="8" style="font-size:16px; margin-left:100px; color:#000000">
                <tr>
                <td  class="td-right">原手机号</td>
                <td colspan="2"><input type="text"   placeholder="请输入原来的手机号"
                                       class="form-control"  name="mobile"
                                       id="mobile" value="<%=loginUser.getMobile()%>" readonly="readonly"></td>

                </tr>
                <tr>
                 <td  class="td-right">手机动态码</td>
                <td  colspan="2">
                
                 
                 <div class="input-group">
                            <input type="text" class="form-control" name="mobiValidateCode"
                                   id="mobiValidateCode" placeholder="请输入动态码" >
                            <span class="input-group-btn">
                                <input type="button" class="btn btn-default" id="code" value="获取动态码"
                                       style="width:100px;height:36px;margin-left:30px;"
                                       onclick="getMobiCode('mobcodevalue')" />
                            </span>
                        </div><!-- /input-group -->
                    <span  id="mobcodevalue" style="font-weight: bold"></span>
                </td>
                </tr>
                <tr>
                <td  class="td-right"> 登录密码 </td>
                <td colspan="2"><input type="password" class="form-control"   placeholder="请输入密码"
                                       id="password" name="personal.password"></td>
                </tr>
                
                 <tr>
                 <td/>
                 
                <td align="left"><input type="button"    class="btns-mynext"  id="iponeSubmmtButton" style="margin-top: 10px;"
                                        onclick="javascript:iponeSubmit()" value="下一步" ></td>
                </tr>
                </table>
             </form>
        </div>

    </div>
</div>



<div style="height: 70px;"></div>



        <jsp:include page="/w2/bottom.jsp"/>
  </div>
  
 
    </body>
    </html>