var flag = true;


function appointmentOrPaySubmit() {
    // alert('appointmentOrPay4Web');


    fw_alertFormValues('productionPayForm');

    // 判断是否通过所有输入验证
    if (!checkIsPassInputs()) {
        return;
    }


    $('#productionPayForm').submit();

}

function checkIsPassInputs() {

    // 验证交易密码
    if ($('#businessPwd').val() == "" || $('#businessPwd').val().length != 6) {
        popStatus(2, "请输入6位数字交易密码", 2);
        return false;
    }

    // 验证验证码 captcha
    if ($('#captcha').val() == "" || $('#captcha').val().length != 5) {
        popStatus(2, "请输入5位数字交易密码", 2);
        return false;
    }

    // 确认本人身份
    if (!document.getElementById("identity").checked) {
        popStatus(2, "请确认投资者本人身份", 2);
        return false;
    }

    // 风险揭示书和服务协议
    if (document.getElementById("agree").checked != true) {
        popStatus(2, "您需要同意《风险提示书》和相关协议", 2);
        return false;
    }

    return true;
}

/**
 * 提交支付
 */
function paySubmit(payWay, identityMessage) {
    if (onClickIMAgreement() && onClickIdentity(identityMessage)) {
        flag = true;
        var url = '';
        // 验证交易密码
        validateTransferPassword();
        if (flag) {
            // 提交对按钮进行控制
            $('#PaymentButton').attr('disabled', 'disabled');
            $('#PaymentButton').val("正在支付");
            var params = {
                'order.productionId': $('#productionId').val(),
                'order.money': $('#money').val(),
                'order.productionCompositionId': $('#productionCompositionId').val(),
                'order.salemanId': $('#salemanId').val(),
                'order.accountId': $('#accountId').val(),
                'order.customerId': $('#customerId').val(),
                'order.operatorId': $('#operatorId').val(),
                'order.referralCode': $('#referralCode').val(),
                'order.status': 0,
                'captcha': $('#captcha').val(),
                'legalAgreement.name': $('#legalAgreementId').val(),
                'businessPwd': fnGetMD5($('#businessPwd').val()),
                'orderId': $('#orderId').val(),
                'u': $('#u').val()
            };
            // 提交到服务器
            popStatus(3, '正在跳转支付界面，请稍后······', 3);
            fweb.post("/core/w2/production/buildOrder", params, function (data) {
                // alert(JSON.stringify(data));

                if (data.code != 100) {

                    var response = data.returnValue;

                    if (response == "3") {
                        url = "/core/w2/customer/toBankCard";
                    }

                    popStatus(2, data.message, 3, url);
                    $('#PaymentButton').removeAttr('disabled');
                    $('#PaymentButton').val("支付");
                    return;
                }

                var response = data.returnValue;
                if (response == undefined) {
                    url = '/core/w2/customer/IndexShow';
                }

                alert(JSON.stringify(response));

                /*$('#inputCharset').val(response.inputCharset);
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
                $('#signMsg').val(response.signMsg);*/

                $("#mchnt_cd").val(response.merchantCode);
                $("#order_id").val(response.orderId);
                $("#order_amt").val(response.orderAmount);
                $("#order_pay_type").val(response.orderPayType);
                $("#page_notify_url").val(response.pageNotifyURL);
                $("#back_notify_url").val(response.backNotifyURL);
                $("#order_valid_time").val(response.orderValidTime);
                $("#goods_name").val(response.goodsName);
                $("#goods_display_url").val(response.goodsDisplayURL);
                $("#rem").val(response.remark);
                $("#ver").val(response.version);
                $("#md5").val(response.md5);






                var confirmStr;
                if(payWay == "offline"){
                    confirmStr = "是否确认预约？";
                }
                if(payWay == "online"){
                    confirmStr = "是否确认购买？";
                }
                layer.confirm(confirmStr, {
                    btn: ['是', '否']
                }, function () {
                    var str;
                    //线下打款
                    if(payWay == "offline"){
                        str = "showPayPage";
                    }
                    //线上支付
                    if(payWay == "online"){
                        str = "https://service.allinpay.com/gateway/index.do";
                    }
                    $('#productionPayForm').attr("action", str);
                    $('#productionPayForm').attr("target", "_blank");
                    $('#productionPayForm').submit();

                    var confirmPayStr;
                    if(payWay == "offline"){
                        confirmPayStr = "请问您是否已经成功预约？";
                    }
                    if(payWay == "online"){
                        confirmPayStr = "请问您是否已经成功支付？";
                    }


                    layer.confirm(confirmPayStr, {
                        btn: ['是', '否'],
                        shade: false
                    }, function () {
                        // 支付成功
                        window.location.href = '/core/w2/modules/customer/investment.jsp';
                        $('#PaymentButton').val("支付");
                        $('#PaymentButton').removeAttr('disabled');
                        isClick = false;
                    }, function () {
                        // 支付失败
                        window.location.href = '/core/w2/modules/customer/investment.jsp';
                        $('#PaymentButton').val("支付");
                        $('#PaymentButton').removeAttr('disabled');
                        isClick = false;
                    });

                }, function () {
                    // 错误移除对按钮的控制
                    $('#PaymentButton').removeAttr('disabled');
                    $('#PaymentButton').val("支付");
                });
            }, function (data) {
                // 错误移除对按钮的控制
                popStatus(4, '系统繁忙，请稍候再试', 3);
                $('#PaymentButton').removeAttr('disabled');
                $('#PaymentButton').val("支付");
            })
        }

    }
}