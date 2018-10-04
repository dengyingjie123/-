var AllinpayCircleQueryCashShareClass = function (token) {
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
        openWindow:function(customerId){
            var url = WEB_ROOT + "/modules/allinpayCircle/allinpay_circle_query_cash_share.jsp";

            fw.window('allinpay_circle_query_cash_share_window' + token, '个人资产', 600, 400, url, function () {

                var queryUrl = WEB_ROOT + "/pay/AllinpayCircle_queryCashShare?customerId=" + customerId;

                fw.post(queryUrl, null, function (data) {

                    // fw.alertReturnValue(data);

                    $('#table-2').createTable(data['qur_rst'], {});

                }, null);


            }, null);
        }
    };
}
