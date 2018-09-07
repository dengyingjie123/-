
var principalMoney, profitMoney;

// 组织请求url查询客户资金
var urlMoney = '/core/w2/customer/getCustomerMoney';
fweb.post(urlMoney, null, function (data) {
    var principalMoney = data.returnValue.availableMoney;
    if (data != null) {

        $("#allMoney").text(principalMoney);
        $("#allMoney").formatCurrency();

        //组织请求url查询客户预期收益
        var urlProfit = '/core/w2/customer/getCustomerProfit'
        fweb.post(urlProfit,null,function(data){
            profitMoney = data.returnValue;
            $('#profitMoney').text(profitMoney);
            $("#profitMoney").formatCurrency();

            //组织客户总资产
            var totalMoney = principalMoney * 1 + profitMoney * 1;
            $('#totalMoney').text(totalMoney);
            $("#totalMoney").formatCurrency();

        });

    }
    else {
        popStatus(2, "获取资金失败", 2, null, null);
    }
}, null);
