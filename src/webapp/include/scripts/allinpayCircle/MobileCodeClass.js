
var MobileCodeClass = function (token) {
    //var customerId = null;
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        alert('初始化页面成功');
    }



    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        openMobileCodeCheckWindow:function(accountId) {

            console.log('openMobileCodeCheckWindow: ' + accountId);

            var mobileCodeCheckUrl = WEB_ROOT + '/modules/customer/Allinpay_MobileCode_Save.jsp?token='+token;

            fw.window('MobileCodeCheckWindow' + token, '验证码', 500, 200, mobileCodeCheckUrl, function(){

                var mobileCodeInfoUrl = WEB_ROOT + "/pay/AllinpayCircle_getMobileCodeInfo4ChangeBankNumber?bizId="+accountId;

                fw.post(mobileCodeInfoUrl, null, function(data){

                    // fw.alertReturnValue(data);
                    var formId = 'formCustomerAccountMobileCodeCheck' + token;
                    fw.formLoad(formId, data);

                    var mobileCheckButtonId = 'btnCustomerAccountSubmit_AllinpayCircle_MobileCodeCheck' + token;
                    fw.bindOnClick(mobileCheckButtonId, function () {

                        var formSubmitUrl = WEB_ROOT + "/pay/AllinpayCircle_checkMobileCode";
                        fw.formSubmit(formId, formSubmitUrl, mobileCheckButtonId, function(){
                        },function(data){

                            try {
                                data = fw.dealReturnObject(data);

                                fw.alert('formSubmitUrl', data);
                            }
                            catch(e) {

                            }

                        });

                    });

                },null);


            }, null);
        }
    };
}