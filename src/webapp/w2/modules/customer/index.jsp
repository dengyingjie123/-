<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerMoneyPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.common.Pager" %>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="/w2/home.jsp" %>

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
    CustomerMoneyPO customerMoney = (CustomerMoneyPO) request.getSession().getAttribute("customerMoney");
    Integer sum = Integer.parseInt(request.getSession().getAttribute("sum").toString());
%>

<!DOCTYPE html><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>我的账户</title>
    <link href="<%=Config.getWebRoot()%>/w2/dist/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/dist/css/bootstrap-theme.min.css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/index.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script language="javascript"
            src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>



    <script src="<%=Config.getWebRoot()%>/w2/dist/html5/excanvas.compiled.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/getCustomerMoney.js"></script>

</head>

<body>


<%--top--%>
<jsp:include page="/w2/top.jsp"/>

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

<%--top 结束--%>
<div id="position" class="w1200">
    <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a> &gt;
    <a href="<%=Config.getWebRoot()%>/w2/customer/IndexShow">我的账户</a>
</div>

<div id="container" class="w1200">
    <div id="menu">
        <ul>
            <li  style="background: url(<%=Config.getWebRoot()%>/w2/img/menu_current.png) no-repeat;">
                <a href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" class="font20" style="color: white;">我的账户</a>
            </li>

            <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/investment.jsp" class="font20">投资产品</a></li>
            <%--<li><a href="#" class="font20">参加计划</a></li>--%>
            <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/money.jsp" class="font20">资金记录</a></li>
            <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/account.jsp"  class="font20" >账户管理</a></li>

        </ul>
    </div>
    <div id="detail">
        <div id="customerInfo">
            <div id="flag"><img src="<%=Config.getWebRoot()%>/w2/img/customer_flag.png" style="height: 80px;width: 80px"/></div>
            <div id="accountInfo" style="width: 430px;">
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <td><span style="font-size: 26px; color: #ff6900;">
                            <%=loginUser.getLoginName()%></span>&nbsp;&nbsp;
                            <span style="font-size:18px;"  id="sayHello"><%--问候--%></span></td>
                    </tr>

                        <tr>
                            <td align="left" style="font-size:16px;color: #545454 ">账户安全级别：


                            <span style="color:#ff3c3c;">
                            <% if (sum <= 30) {  out.write("低"); } else if (sum > 30 && sum <= 70) {out.write("中");  } else if (sum > 70) { out.write("高");}%>
                            </span>&nbsp;&nbsp;
                            <span>
                            <a href="<%=Config.getWebRoot()%>/w2/modules/customer/account.jsp" style="color:#ff6900;text-decoration: none">[提升]</a>
                            </span>
                            </td>
                        </tr>
                        <tr>
                            <td align="left" style="font-size:16px;color: #545454 ">上次登录时间： <label id="lastLoginTime"> <!-- 上次登录时间 --> </label></td>
                        </tr>
                    <tr>
                        <td>
                            <div id="customerAuthStatus" >
                                <%--VIP--%>
                            </div>
                        </td>
                    </tr>
                    </table>

            </div>
            <div id="split"><img src="<%=Config.getWebRoot()%>/w2/img/customer_split.png" style="height: 250px"/></div>
            <div id="moneyInfo">
                <table cellpadding="5" cellspacing="0">
                <tr>
                    <td><span style="font-size:20px;">投资总额：</span>
                            <span style="font-size: 26px; color: #ff3f3f;" class="money">
                                <span id="allMoney"><%--总资产--%></span></span>&nbsp;&nbsp;<span
                                style="font-size: 26px; color: #ff3f3f;">元</span></td>
                </tr>
                <tr>
                    <td><span style="font-size:20px;">预期总收益：</span>
                            <span style="font-size: 26px; color: #ff3f3f;" class="money">
                                <span id="profitMoney"><%--预期总收益--%></span></span>&nbsp;&nbsp;<span
                                style="font-size: 26px; color: #ff3f3f;">元</span></td>
                </tr>
                    <tr>
                        <td><span style="font-size:20px;">总资产：</span>
                            <span style="font-size: 26px; color: #ff3f3f;" class="money">
                                <span id="totalMoney"><%--投资加上收益--%></span></span>&nbsp;&nbsp;<span
                                    style="font-size: 26px; color: #ff3f3f;">元</span></td>
                    </tr>

                </table>

            </div>
        </div>

        <div id="productions">

            <div id="tableTitle"><h2 style="display: inline; font-weight: normal">推荐列表</h2></div>
            <div id="tableDetail">
                <table cellpadding="0" cellspacing="0">
                    <tr id="firstLine">
                        <td>产品详情</td>
                        <td>预期年化收益率</td>
                        <td>产品总金额（元）</td>
                        <td>投资期限</td>
                        <td>购买进度</td>
                        <td>剩余时间</td>
                        <td>操作</td>
                    </tr>
                <tbody id="productionList">
                <tr><td colspan='7' class='otherLine'>
                    <div id="load">
                        <div class="icon_load"></div>
                        <label class="load_title">加载中</label>
                    </div>
                </td>
                </tr>
                </table>
            </div>
        </div>
      <%--  <div >
            <nav>
                <ul class="pagination" id="pagination">
                    <!-- 分页内容 -->
                </ul>
            </nav>
        </div>--%>
    </div>
</div>


<jsp:include page="/w2/bottom.jsp"/>

</body>

<script language="javascript">
    // 项目进度转圈动画
    $(function () {
        //create instance
        $('.chart').easyPieChart({
            size: 50,
            lineWidth: 3,
            barColor: '#c5acd2',
            trackColor: '#dddddd',
            scaleColor: '#ffffff',
            onStep: function (from, to, percent) {
                $(this.el).find('.percent').text(Math.round(percent));
            }
        });
    });
</script>
<script src="<%=Config.getWebRoot() %>/w2/js/modules/customer/index.js"></script>
<script language="JavaScript">
    $('.money').formatCurrency();
</script>
</html>
