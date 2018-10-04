/**
 *
 */
var AllinpayCircleQueryClass = function (token) {
    //var customerId = null;
    ///  初始化部分 开始  //////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {
        // alert('初始化页面成功');
        onClickAllinpayCircleQuerySubmit();
        onClickAllinpayCircleQueryReset();
    }

    function onClickAllinpayCircleQuerySubmit() {
        var btnId = 'btnAllinpayCircleQuerySubmit' + token;
        fw.bindOnClick(btnId, function() {
            var url = WEB_ROOT + '/pay/AllinpayCircle_queryWithOneOrder';

            fw.formSubmit('formAllinpayCircleQuery' + token, url, btnId, function(){

            },function (data) {
                // fw.alertReturnValue(data);
                try {
                    data = fw.dealReturnObject(data);

                    // fw.alertReturnValue(data);
                    var message = '返回值：【'+data['qur_rst']+'】<br>返回信息【'+data['qur_rst_msg']+'】';

                    fw.alert('查询结果', message);
                }
                catch (e) {

                }
            });
        });
    }

    function onClickAllinpayCircleQueryReset() {
        fw.bindOnClick('btnAllinpayCircleQueryReset' + token, function() {
            fw.textSetValue('bizId' + token, '');
        });
    }

    ///  事件定义 结束  ///////////////////////////////////////////
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
}
