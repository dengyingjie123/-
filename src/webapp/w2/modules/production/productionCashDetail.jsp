
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.dao.MySQLDao" %>
<%@ page import="com.youngbook.entity.po.cms.ArticlePO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerMoneyPO" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.entity.wvo.production.ProductionWVO" %>
<%@ page import="com.youngbook.entity.po.production.OrderPO" %>
<%@ page import="com.youngbook.service.web.AdImageService" %>
<%@ page import="com.youngbook.entity.vo.web.AdImageVO" %>
<%@ page import="com.youngbook.entity.vo.Sale.PaymentPlanVO" %>

<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.vo.production.OrderVO" %>
<%@ page import="com.youngbook.common.utils.MoneyUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="../../info.jsp" %>
<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    }
%>
<%
    ProductionWVO productionWVO = (ProductionWVO) request.getAttribute("productionWVO");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
<title>点金派 - 产品详情</title>

<link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
<link href="<%=Config.getWebRoot()%>/w2/css/production/productionCashDetail.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>

    <link rel="stylesheet" href="<%=Config.getWebRoot()%>/w2/css/style.css">

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/include/extensions/frameworkplus.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/production/productionCashDetail.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>

    <script type="text/javascript">
        function formatMoney() {
            var investMoney = $('#investMeney').val();
            if (parseInt(investMoney) > 0 || parseInt(investMoney) < 0) {
                $('#investMeney').val(parseInt(investMoney));
            } else {
                $('#investMeney').val(0);
            }
            jsInvest();
        }

        function investMoney() {
            var verifyMoney = document.getElementById("verifyMoney");
            verifyMoney.style.visibility = "hidden";
        }

        function invest(minMoney, currentMoney) {
            if (jsInvest()) {
                var productionWVOId = $('#productionWVOId').val();
                var investMeney = $('#investMeney').val();
                if (parseInt(investMeney) < minMoney) {
                    popStatus(4, "金额小于起投金额", 2);
                    return;
                }
                if (currentMoney < minMoney) {
                    var rechargeUrl = "/core/w2/modules/customer/recharge.jsp";
                    popStatus(1, "可用资金不足，正在进入充值页面...", 2, rechargeUrl);
                    return;
                }
                $('#investForm').submit();
            }
        }
        function jsInvest() {
            var investMoney = $('#investMeney').val();
            var availableMoney = $('#AvailableMoney').val();
            var productionWVOSizeStart = $('#productionWVOSizeStart').val();
            var productionWVOSizeStop = $('#productionWVOSizeStop').val();
            var verifyMoney = document.getElementById("verifyMoney");
            if (parseInt(availableMoney) < parseInt(investMoney)) {
                verifyMoney.style.visibility = "visible";
                verifyMoney.innerHTML = "您的资金不足";
                //return false;
            }

            if (parseInt(investMoney) < parseInt(productionWVOSizeStart)) {
                verifyMoney.style.visibility = "visible";
                verifyMoney.innerHTML = "金额小于起投金额";
                return false;
            } else if (investMoney == '' || investMoney == null) {
                $('#investMeney').val(0);
                return false;
            }
            else if (parseInt(investMoney) > parseInt(productionWVOSizeStop)) {
                verifyMoney.style.visibility = "visible";
                verifyMoney.innerHTML = "金额超过最大投资金额";
                return false;
            } else {
                return true;
            }
        }

    </script>
</head>

<body>

<jsp:include page="/w2/top.jsp"/>
<div style="width: 100%;background: #fafafa;" >
    <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item" style="color:#d28d2a">投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" >我的账户</a>
        </span>
    </div>
</div>


    <div id="position" class="w1200">

        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a> &gt; <a href="<%=Config.getWebRoot()%>/w2/customer/IndexShow">我的账户</a>&gt; <a href="#">投资产品</a>
    </div>

    <div   class="container w1200">
        <span  class="hts-h3">产品信息</span>

        <div id="detail" class="container-table">

            <table cellpadding="0" cellspacing="0">
                <thead>
                <tr style="height: 35px;">

                    <td width="210" align="center" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">产品</td>
                    <td width="190" align="center" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">收益率</td>
                    <td width="200" align="center" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">期限</td>
                    <td width="200" align="center" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">起息日</td>
                    <td width="200" align="center" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">产品进度</td>
                </tr>
                </thead>

                <tr>
                    <td   align="center"><%=productionWVO.getWebsiteDisplayName()%></td>
                    <%--<td   align="center"><%=productionWVO.getSize()%>元</td>--%>
                    <td    align="center"><%
                        if (productionWVO.getMaxExpectedYield() != Double.MAX_VALUE) {
                            //最大收益率是否与最小收益率相同
                            if (productionWVO.getMaxExpectedYield() == productionWVO.getMinExpectedYield()) {
                                out.print("<strong class='fz-primary'>" + productionWVO.getMaxExpectedYield() + "%</strong>");
                            } else {
                                out.print("<strong class='fz-primary'>" + productionWVO.getMinExpectedYield() + "%</strong><strong> - </strong><strong class='fz-primary'>" + productionWVO.getMaxExpectedYield() + "%</strong>");
                            }
                        }

                    %></td>
                    <td   align="center"><%=productionWVO.getInvestTermView()%></td>
                    <td   align="center"><%=productionWVO.getValueDate()%> </td>
                    <td   align="center"><%

                        if (productionWVO.getSaleMoney() != Double.MAX_VALUE) {
                            double v = productionWVO.getSaleMoney() / productionWVO.getSize() * 100;

                            out.println("<strong>" + MoneyUtils.format2String(v) + "%</strong>");
                        }
                    %>
                    </td>
                </tr>
            </table>
         </div>
    </div>


<%--<div   class="container container-item">--%>
    <%--<div  class="hts-h3">订单信息</div>--%>

    <%--<div  class="container-table">--%>

        <%--<table cellpadding="0" cellspacing="0">--%>
            <%--<thead>--%>
            <%--<tr style="height: 35px;">--%>
                <%--<td width="100"  style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">订单号</td>--%>
                <%--<td width="110"  style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">客户</td>--%>
                <%--<td width="150"  style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">购买金额(元)</td>--%>
                <%--<td width="100"  style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">当前状态</td>--%>
                <%--<td width="100"  style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">客户属性</td>--%>
                <%--<td width="100"  style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">推荐人</td>--%>
                <%--<td width="200"  style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">订单时间</td>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody id="productionCashList">--%>
            <%--<!-- 订单信息 -->--%>
            <%--</tbody>--%>
        <%--</table>--%>
    <%--</div>--%>
<%--</div>--%>

<div   class="container container-item">
    <span  class="hts-h3">兑付信息</span>

    <div class="container-table">

        <table cellpadding="0" cellspacing="0">
            <thead>
            <tr style="height: 35px;">
                <td width="230" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">客户名称</td>
                <td width="160" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">兑付类型</td>
                <td width="200" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">订单号</td>
                <td width="170" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">兑付日期</td>
                <td width="150" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">兑付总期数</td>
                <td width="200" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">当前兑付期数</td>
                <td width="150" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">当前兑付状态</td>
                <td width="150" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">兑付本金</td>
                <td width="150" style="font-size: 14px; background: url(<%=Config.getWebRoot()%>/w2/img/bg_tableHead.png); height: 35px;">兑付收益</td>

            </tr>
            </thead>
            <tbody id="paymentplanOrderList">
            <!-- 订单信息 -->
            </tbody>
        </table>
    </div>
</div>




<jsp:include page="/w2/bottom.jsp"/>

</body>
</html>
