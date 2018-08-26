<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerMoneyPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="login.jsp"%>
<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO)request.getSession().getAttribute("loginUser");
    }
    else {
        String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;
    }
    CustomerMoneyPO customerMoney = (CustomerMoneyPO)request.getSession().getAttribute("customerMoney");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>资金记录</title>


    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/customer/money.css">
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/productionInvested.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/money.js"></script>

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
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item">投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" style="color:#d28d2a">我的账户</a>
        </span>
    </div>
</div>
<div id="position" class="w1200">
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a> &gt; <a href="#">资金记录</a>
</div>

<div id="container" class="w1200">
    <div id="menu">
        <ul>
            <li>
                <a href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" class="font20" >我的账户</a>
            </li>

            <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/investment.jsp" class="font20">投资产品</a></li>
            <%--<li><a href="#" class="font20">参加计划</a></li>--%>
            <li style="background: url(<%=Config.getWebRoot()%>/w2/img/menu_current.png);"><a href="<%=Config.getWebRoot()%>/w2/modules/customer/money.jsp" class="font20" style="color: white;">资金记录</a></li>
            <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/account.jsp"  class="font20" >账户管理</a></li>


        </ul>
    </div>
    <div id="detail">
        <table cellpadding="0" cellspacing="0">
            <thead>
            <tr style="height: 38px;">
                <td style="font-size: 16px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">时间</td>
                <td style="font-size: 16px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">类型</td>
                <td style="font-size: 16px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">详情</td>
                <td style="font-size: 16px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">状态</td>
            </tr>
            </thead>
            <tbody id="moneyLogList">
            <!-- 资金日志内容 -->
            <tr><td colspan='7' class='otherLine'>
                <div id="load">
                    <div class="icon_load">
                    </div>
                    <label class="load_title">加载中</label>
                </div>
            </td>
            </tr>
            </tbody>

        </table>

        <div class="pagination" id="pagination">
            <!-- 分页内容 -->
        </div>


    </div>
</div>


<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
