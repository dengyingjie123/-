<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
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
    %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>投资产品</title>

    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/productionInvested.css" rel="stylesheet" type="text/css"/>

    <script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/layer/layer.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>

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
    <a href="#">首页</a> &gt; <a href="#">投资产品</a>
</div>

<div id="container" class="w1200">
    <div id="menu">
        <ul>

            <li>
                <a href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" class="font20">我的账户</a>
            </li>

            <li style="background: url(<%=Config.getWebRoot()%>/w2/img/menu_current.png);"><a href="<%=Config.getWebRoot()%>/w2/modules/customer/investment.jsp" class="font20" style="color: white;">投资产品</a></li>
            <%--<li><a href="#" class="font20">参加计划</a></li>--%>
            <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/money.jsp" class="font20">资金记录</a></li>
            <li><a href="<%=Config.getWebRoot()%>/w2/modules/customer/account.jsp"  class="font20" >账户管理</a></li>

        </ul>
    </div>
    <div id="detail">
        <table cellpadding="0" cellspacing="0">
            <tr style="height: 38px;font-size: 16px">
                <td style="background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">
                    产品
                </td>
                <td style="background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">
                    预期总收益
                </td>
                <td style="background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">
                    起息日
                </td>
                <td style="background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">
                    订单创建时间
                </td>
                <td style="background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">
                    购买金额
                </td>
                <td style="background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">
                    状态
                </td>
                <td style="background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png);">
                    查看
                </td>
            </tr>

            <tbody id="productionList">
            <!-- 购买的产品信息 -->
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
        <div>
            常见问题<br>
            Q1：我已经成功支付了订单，为什么还会一直显示【去支付】？<br>
            A1：由于银行间结算有可能存在一些延迟，请耐心等待，一般延时时间不会超过5分钟。如果长时间显示【去支付】状态，请拨打我们的客服热线<%=Config.getSystemVariable("common.phone.400")%>，谢谢。<br>
            Q2：我的订单怎么不见了？<br>
            A2：考虑到系统安全，平台会将超过5分钟未完成支付的订单取消。若要购买可重新下单。
        </div>
    </div>
</div>


<jsp:include page="/w2/bottom.jsp"/>

</body>
<script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/investment.js"></script>
</html>
