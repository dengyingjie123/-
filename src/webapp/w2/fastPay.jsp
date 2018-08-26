<%@ page import="com.youngbook.common.config.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>快速支付</title>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>

</head>
<body>

    <form id="fastPay" name="fastPay" method="post">

       
        姓名：<input name="name" type="text" id="name" value="深圳公达资产管理有限公司" size="50" /><br />
        产品名：<input name="production" type="text" id="production" value="点金派资金兑付" size="50" /><br />
        金额（元）：<input name="money" type="text" id="money" value="" size="50" /><br />

        <input id="doFastPay" type="button" value="提交" onClick="doRequest()" />
        
        <input type="hidden" id="allinpayUrl" name="allinpayUrl" value="<%=Config.getSystemConfig("bank.pay.allinpay.order.submit")%>" />
        <input type="hidden" id="inputCharset" name="inputCharset"/>
        <input type="hidden" id="pickupUrl" name="pickupUrl"/>
        <input type="hidden" id="receiveUrl" name="receiveUrl"/>
        <input type="hidden" id="version" name="version"/>
        <input type="hidden" id="language" name="language"/>
        <input type="hidden" id="signType" name="signType"/>
        <input type="hidden" id="merchantId" name="merchantId"/>
        <input type="hidden" id="payerName" name="payerName"/>
        <input type="hidden" id="orderNo" name="orderNo"/>
        <input type="hidden" id="orderAmount" name="orderAmount"/>
        <input type="hidden" id="orderCurrency" name="orderCurrency"/>
        <input type="hidden" id="orderDatetime" name="orderDatetime"/>
        <input type="hidden" id="productName" name="productName"/>
        <input type="hidden" id="payType" name="payType"/>
        <input type="hidden" id="signMsg" name="signMsg"/>

    </form>

</body>

<script charset="UTF-8">

    function doRequest() {

        // 获取参数
        var name = $('#name').val();
        var production = $('#production').val();
        var money = $('#money').val();
        var allinpayUrl = $('#allinpayUrl').val();
        var backEndUrl = "/core/w2/production/fastPay";

        var params = { 'name': name, 'production': production, 'money': money };

        // 发起后台请求
        fweb.post(backEndUrl, params, function (data) {

            var response = data.returnValue;
            if (response == undefined) {
                alert("请求失败，请联系管理员。");
                return ;
            }

            $('#inputCharset').val(response.inputCharset);
            $('#pickupUrl').val(response.pickupUrl);
            $('#receiveUrl').val(response.receiveUrl);
            $('#version').val(response.version);
            $('#language').val(response.language);
            $('#signType').val(response.signType);
            $('#merchantId').val(response.merchantId);
            $('#payerName').val(response.payerName);
            $('#orderNo').val(response.orderNo);
            $('#orderAmount').val(response.orderAmount);
            $('#orderCurrency').val(response.orderCurrency);
            $('#orderDatetime').val(response.orderDatetime);
            $('#productName').val(response.productName);
            $('#payType').val(response.payType);
            $('#signMsg').val(response.signMsg);

            $('#fastPay').attr("action", allinpayUrl);
            $('#fastPay').attr("target", "_blank");
            $('#fastPay').submit();

        }, function (data) {

        })

    }

</script>

</html>
