<%@ page import="com.youngbook.common.ReturnObject" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;

    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO)request.getSession().getAttribute("loginUser");
        ReturnObject returnObject = (ReturnObject)request.getAttribute("returnObject");
    } else {
        String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;
    }
%>
<!DOCTYPE html>
<html ng-app="accountApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<title>账户管理</title>

<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/customer/account.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/account.js"></script>
</head>

<body>

    <%--top--%>
    <jsp:include page="/w2/top.jsp"/>
    <%--top 结束--%>
    <%--top 结束--%>
    <div style="width: 100%;background: #fafafa;" >
        <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
            <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item">投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp">新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" style="color: #d28d2a;padding-bottom: 29px;border-bottom: 2px solid #d28d2a;">我的账户</a>
        </span>
        </div>
    </div>

    <div id="position" class="w1200">
    	<a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a> &gt; <a href="<%=Config.getWebRoot()%>/w2/modules/customer/account.jsp">账户管理</a>
    </div>
    
    <div id="container" class="w1200">
        <div id="menu">
            <ul>
                <li>
                    <a href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" class="font20">我的账户</a>
                </li>

                <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/investment.jsp" class="font20">投资产品</a></li>
                <%--<li><a href="#" class="font20">参加计划</a></li>--%>
                <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/money.jsp" class="font20">资金记录</a></li>
                <li style="background: url(<%=Config.getWebRoot()%>/w2/img/menu_current.png);"><a href="<%=Config.getWebRoot()%>/w2/modules/customer/account.jsp"  class="font20" style="color: white;">账户管理</a></li>

            </ul>
        </div>
        <div id="detail">
        
        	<div id="detailTitle">账户安全</div>
            <div id="detailTable">
            	<table cellpadding="0" cellspacing="0">
                	<tr>
                    	<td style="background:#fdf5ea; width:110px;" id="accountAuth">
                            <img src="<%=Config.getWebRoot()%>/w2/img/ui/loading.gif" width="20" height="20"/>
                        </td>
                        <td style="width:155px;color:#ff6900;text-align:left;padding:0px 0px 0px 20px;">实名认证与银行卡绑定</td>
                        <td style="text-align:left;color:#666666;">保障账户安全，确认投资身份</td>
                        <td style="width:150px;text-align:center;"></td>
                        <td style="width:80px;text-align:center;"></td>
                    </tr>
                	<tr>
                    	<td style="background:#fdf5ea;" id="transactionPassword">
                            <img src="<%=Config.getWebRoot()%>/w2/img/ui/loading.gif" width="20" height="20"/></td>
                        <td style="width: 155px;color:#ff6900;text-align:left;padding:0px 0px 0px 20px;">交易密码</td>
                        <td style="text-align:left;color:#666666;">保证资金安全，充值、取现投资筹集相关操作事使用</td>
                        <td style="width:150px;text-align:center;"></td>
                        <td style="width:80px;text-align:center;"></td>
                    </tr>
                	<tr>
                    	<td style="background:#fdf5ea;" id="questions">
                            <img src="<%=Config.getWebRoot()%>/w2/img/ui/loading.gif" width="20" height="20"/></td>
                        <td style="width: 155px;color:#ff6900;text-align:left;padding:0px 0px 0px 20px;">安全保护问题</td>
                        <td style="text-align:left;color:#666666;">保障个人隐私，修改个人信息等操作时使用</td>
                        <td style="width:150px;text-align:center;"></td>
                        <td style="width:80px;text-align:center;"></td>
                    </tr>
                	<tr>
                    	<td style="background:#fdf5ea;">
                            <img src="<%=Config.getWebRoot()%>/w2/img/ui/loading.gif" width="20" height="20"/></td>
                        <td style="width: 155px;color:#ff6900;text-align:left;padding:0px 0px 0px 20px;">手机认证</td>
                        <td style="text-align:left;color:#666666;">保障资金安全，是您重要的身份凭证，获取账户资金变动通知</td>
                        <td style="width:150px;text-align:center;" id="mobile" ></td>
                        <td style="width:80px;text-align:center;"></td>
                    </tr>
                	<tr>
                    	<td style="background:#fdf5ea;">已设置</td>
                        <td style="width: 155px;color:#ff6900;text-align:left;padding:0px 0px 0px 20px;">登录密码</td>
                        <td style="text-align:left;color:#666666;" id="lastLoginTime" ></td>
                        <td style="width:150px;text-align:center;"></td>
                        <td style="width:80px;text-align:center;"><a href="<%=Config.getWebRoot()%>/w2/modules/customer/changePassword.jsp">修改</a></td>
                    </tr>
                	<%--<tr>--%>
                    	<%--<td style="background:#fdf5ea;" >--%>
                            <%--<img src="<%=Config.getWebRoot()%>/w2/img/ui/loading.gif" width="20" height="20"/></td>--%>
                        <%--<td style="width: 125px;color:#d28d2a;text-align:left;padding:0px 0px 0px 20px;">电子邮箱</td>--%>
                        <%--<td style="text-align:left;">获取账户变动通知和投资讯息</td>--%>
                        <%--<td style="width:150px;text-align:center;" id="email"></td>--%>
                        <%--<td style="width:40px;text-align:left;"></td>--%>
                    <%--</tr>--%>
                	<%--<tr>--%>
                    	<%--<td style="background:#fdf5ea;" id="bankCard">--%>
                            <%--<img src="<%=Config.getWebRoot()%>/w2/img/ui/loading.gif" width="20" height="20"/></td>--%>
                        <%--<td style="width: 125px;color:#ff6900;text-align:left;padding:0px 0px 0px 20px;">银行卡</td>--%>
                        <%--<td style="text-align:left;color:#666666;">管理银行卡</td>--%>
                        <%--<td style="width:150px;text-align:center;"></td>--%>
                        <%--<td style="width:80px;text-align:center;"></td>--%>
                    <%--</tr>--%>
                </table>
            </div>
            
        </div>
    </div>

    <jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
