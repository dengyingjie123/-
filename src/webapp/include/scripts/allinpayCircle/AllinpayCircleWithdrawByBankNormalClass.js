/**
 *
 */
var AllinpayCircleWithdrawByBankNormalClass = function (token) {
    //var customerId = null;
    ///  初始化部分 开始  //////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {
        alert('初始化页面成功');
    }

    ///  事件定义 结束  ///////////////////////////////////////////
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        openWindow:function(data){
            var url = WEB_ROOT + "/modules/allinpayCircle/allinpay_circle_withdraw_by_bank_normal.jsp?token=" + token;

            fw.window('allinpay_circle_withdraw_by_bank_normal_window' + token, '普通取现', 600, 400, url, function () {
                var formId = 'formAllinpayCircleWithdrawByBankNormal' + token;

                fw.formLoad(formId, data);

                var submitUrl = WEB_ROOT + "/pay/AllinpayCircle_withdrawalByBankNormal";
                var btnId = "btnAllinpayCircleWithdrawByBankNormalSubmit" + token;

                fw.bindOnClick(btnId, function () {
                    fw.formSubmit(formId, submitUrl, btnId,
                        function () {

                        },
                        function (data) {
                        try {
                            data = fw.dealReturnObject(data);

                            if (data == '1') {
                                fw.alert('提示', '取现申请已受理');
                            }
                        }
                        catch (e) {

                        }
                    });
                });

            }, null);
        }
    };
}
