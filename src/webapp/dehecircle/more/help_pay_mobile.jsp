<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginCustomerId = Config.getLoginCustomerInSession(request).getId();
%>

<div data-page="help_pay" class="page">
    <div class="navbar">
        <div class="navbar-inner navbar-ph">
            <div class="left"><a href="#" class="back link"> <i class="icon-white icon-back"></i><span></span></a></div>
            <div class="center sliding">手机支付</div>
            <div class="right">
            </div>
        </div>
    </div>

    <div class="page-content hide-bars-on-scroll">

        <div class="content-block">
            &nbsp;&nbsp;&nbsp;&nbsp;
            1. 在“产品列表”选择产品，单击“详情”查看
            <br>
            <img src="<%=Config.getWebPH()%>/include/img/help003.jpg" style="width:100%; height:100%" />
            <br><br>
            &nbsp;&nbsp;&nbsp;&nbsp;
            2. 确认产品信息无误，单击“投资”按钮
            <br><br>
            &nbsp;&nbsp;&nbsp;&nbsp;
            3. 填写订单信息<br>
            	投资人（必填）<br>
            	银行名称（必填）<br>
            	支付方式（必填）<br>
            支付方式有：<br>
            &nbsp;&nbsp;&nbsp;&nbsp;	手机支付<br>
            &nbsp;&nbsp;&nbsp;&nbsp;	PC网银支付<br>
            	投资金额（必填）
            投资金额说明：<br>
            &nbsp;&nbsp;&nbsp;&nbsp;	当“投资金额”≤50000时 使用“手机支付”<br>
            &nbsp;&nbsp;&nbsp;&nbsp;	当“投资金额”>50000、或选择PC网银支付时，请参考《PC网银支付帮助》<br>
            	身份证号码（必填）<br>
            	推荐码（选填）<br>

            <br><br>
            <img src="<%=Config.getWebPH()%>/include/img/help005.jpg" style="width:100%; height:100%" />
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>
</div>