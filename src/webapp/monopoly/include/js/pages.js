/**

 pagePool.put('customer', {
    id:'customer',
    name:'customer_save',
    url:'customer_save.jsp',
    preprocess:function(content, url, next){
        alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

 login-mobile-code
 mine-change-description
 circle-save


 */

var pagePool = new Map;


pagePool.put('system-list', {
    id:'system-list',
    name:'system_list',
    url:'system_list.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        $$('.btn-clear-cache').on('click', function(){
            if (fm.checkIsAndroid()) {
                window.android.webViewClearCache();
            }
        });

        // btn-check-version
        $$('.btn-check-version').on('click', function(){
            if (fm.checkIsAndroid()) {
                var localVersion = window.android.getLocalAndroidVersion();

                fm.f7_alert(myApp, '本机安装版本为:' + localVersion, null);
            }
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('info-detail', {
    id:'info-detail',
    name:'info-detail',
    url:'info-detail.jsp',
    preprocess:function(content, url, next){
        alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('mine-change-mobile-success', {
    id:'mine-change-mobile-success',
    name:'mine-change-mobile-success',
    url:'mine-change-mobile-success.jsp',
    preprocess:function(content, url, next){
       // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('mine-change-mobile', {
    id:'mine-change-mobile',
    name:'mine_change_mobile',
    url:'mine_change_mobile.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        // onClickGetMobileCode('按钮ID','表单ID');
        // 表单里需要包含mobile表单域
        onClickGetMobileCode('btn-change-mobile-code','form-mine-change-mobile');

        $$('#btn-customer-mobile-change').on('click',function(){
            var formData = myApp.formToJSON('#form-mine-change-mobile');

            console.log(formData);

            var checkCode = fm.getMD5(formData['mobileCode'] + formData['mobile']);

            // console.log("mobileCode " + formData['mobileCode']);
            // console.log("checkCode server " + formData['checkCode']);
            // console.log("checkCode " + checkCode);

            if (checkCode != formData['checkCode']) {
                fm.f7_alert(myApp, '手机号验证码有误，请重新输入', null);
                return;
            }

            fm.f7_post(myApp, WEB_ROOT + '/api/monopoly/loadPage_mine_change_mobile', formData, function(data){
                // console.log(data);
                viewCustomer.router.loadPage('mine/mine_change_mobile_success.jsp');
                viewCustomer.router.refreshPage();
            }, null);

        });



    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('score-use-success', {
    id:'score-use-success',
    name:'score_use_success',
    url:'score_use_success.jsp',
    preprocess:function(content, url, next){
        alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('score-use-error', {
    id:'score-use-error',
    name:'score_use_error',
    url:'score_use_error.jsp',
    preprocess:function(content, url, next){
        alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('goods-detail', {
    id:'goods-detail',
    name:'goods_detail',
    url:'goods_detail.jsp',
    preprocess:function(content, url, next){
        alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {

        fm.f7_bind('.btn-score-use',function(e){
            var articleId = e.target.dataset.id;
            var score = e.target.dataset.score;


            /**
             * 检查客户资料
             */
            var urlCustomer = WEB_ROOT + "/customer/CustomerScore_getCustomerScore?customerId=" + loginCustomer['id'] + "&r=" + Math.random();

            fm.f7_post(myApp, urlCustomer, "", function(customerData){
                console.log(customerData);

                var availableScore = customerData['returnValue']['availableScore'];

                if (score > availableScore) {

                    fm.f7_alert(myApp, '商品所需积分为：'+score+'，<br>当前可用积分为：'+availableScore+'，<br>积分不足，无法兑换', null);
                    return;
                }

                /**
                 * 进行积分兑换
                 */
                fm.f7_confirm(myApp, '商品所需积分为：'+score+'，<br>当前可用积分为：'+availableScore+'，<br>是否确认兑换？', function(){

                    viewHome.router.loadPage(WEB_ROOT + '/api/monopoly/loadPage_score_use?articleId='+articleId+'&r='+Math.random());
                    return;
                });


            },null);

        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('fdcg-customer-mobile-change', {
    id:'fdcg-customer-mobile-change',
    name:'customer_mobile_change',
    url:'customer_mobile_change.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        fm.f7_bindWidthClass('btn-fdcg-customer-mobile-change',function(){

            var newPhone = $('#newPhone').val();

            var url = WEB_ROOT + "/customer/CustomerPersonal_fdcgGetChangeMobileData";

            var params = {};
            fm.jsonJoin(params, {'customerId':loginCustomer['id']}, true);
            fm.jsonJoin(params, {'newPhone':newPhone}, true);

            fm.f7_post(myApp, url, params, function(data){

                console.log(data['returnValue']);

                $('#reqData').val(data['returnValue']);

                fm.f7_alert(myApp, '即将跳转到手机号修改界面',function(){
                    $('#form-customer-mobile-change').submit();
                });
            },null);



        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('mine-password-list', {
    id:'mine-password-list',
    name:'password_list',
    url:'password_list.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        fm.f7_bindWidthClass('btn-mine-password-trade-change',function(){


            var url = WEB_ROOT + "/customer/CustomerPersonal_fdcgGetChangePasswordData";

            var params = {};
            fm.jsonJoin(params, {'customerId':loginCustomer['id']}, true);

            fm.f7_post(myApp, url, params, function(data){

                console.log(data['returnValue']);

                $('#reqData').val(data['returnValue']);

                fm.f7_alert(myApp, '即将跳转到修改交易密码界面',function(){
                    $('#form-customer-password-trade-change').submit();
                });
            },null);



        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('mine-account-withdraw', {
    id:'mine-account-withdraw',
    name:'account_withdraw',
    url:'account_withdraw.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        fm.f7_bindWidthClass('btn-mine-account-withdraw',function(){


            var url = WEB_ROOT + "/customer/CustomerPersonal_fdcgGetAccountWithdrawdData";

            var params = {};
            fm.jsonJoin(params, {'customerId':loginCustomer['id']}, true);
            fm.jsonJoin(params, {'amount':$('#amount').val()}, true);

            fm.f7_post(myApp, url, params, function(data){

                console.log(data['returnValue']);

                $('#reqData').val(data['returnValue']);

                fm.f7_alert(myApp, '即将跳转到银行提现申请码界面',function(){
                    $('#form-customer-account-withdraw').submit();
                });
            },null);



        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('mine-account-recharge', {
    id:'mine-account-recharge',
    name:'account_recharge',
    url:'account_recharge.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        fm.f7_bindWidthClass('btn-mine-account-recharge',function(){


            var url = WEB_ROOT + "/customer/CustomerPersonal_fdcgGetAccountRechargedData";

            var params = {};
            fm.jsonJoin(params, {'customerId':loginCustomer['id']}, true);
            fm.jsonJoin(params, {'amount':$('#amount').val()}, true);

            fm.f7_post(myApp, url, params, function(data){

                console.log(data['returnValue']);

                $('#reqData').val(data['returnValue']);

                fm.f7_alert(myApp, '即将跳转到银行充值申请码界面',function(){
                    $('#form-customer-account-recharge').submit();
                });
            },null);



        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('mine-account-list', {
    id:'mine-account-list',
    name:'account_list',
    url:'account_list.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});



pagePool.put('fdcg-customer-register', {
    id:'fdcg-customer-register',
    name:'fdcg_customer_register',
    url:'fdcg_customer_register.jsp',
    preprocess:function(content, url, next){
        alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        fm.f7_bindWidthClass('btn-fdcg-customer-register',function(){

            var realName = $('#realName').val();
            var identityCode = $('#identityCode').val();
            var mobilePhone = $('#mobilePhone').val();

            var url = WEB_ROOT + "/customer/CustomerPersonal_fdcgGetRegisterData";

            var params = {};
            fm.jsonJoin(params, {'customerId':loginCustomer['id']}, true);
            fm.jsonJoin(params, {'realName':realName}, true);
            fm.jsonJoin(params, {'identityCode':identityCode}, true);
            fm.jsonJoin(params, {'mobilePhone':mobilePhone}, true);

            fm.f7_post(myApp, url, params, function(data){

                console.log(data['returnValue']);

                $('#reqData').val(data['returnValue']);

                fm.f7_alert(myApp, '即将跳转到银行注册界面',function(){
                    $('#form-customer-register').submit();
                });
            },null);



        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('score-list', {
    id:'score-list',
    name:'score_list',
    url:'score_list.jsp',
    preprocess:function(content, url, next){

        var parameters = fm.getParametersFormUrl(url);
        var compiledTemplate = Template7.compile(content);

        url = WEB_ROOT + "/customer/CustomerScore_getCustomerScore?"+parameters;
        fm.f7_post(myApp, url, null, function (data) {

            console.log(data);

            var result = data['returnValue'];
            next(compiledTemplate(result));

        }, null);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('password-save', {
    id:'password-save',
    name:'password_save',
    url:'password_save.jsp',
    preprocess:function(content, url, next){
        // alert('init:' + this.id);
        next(content);
    },
    onPageInit:function (app, page) {
        onClickGetMobileCode('btn-mine-change-password-get-code','form-mine-change-password');



        fm.f7_bindWidthClass("btn-mine-change-password", function(){

            var formData = fm.f7_getFormToJSON(myApp, "form-mine-change-password");

            console.log(formData);

            var checkCode =  formData['checkCode'];
            var checkCodeIn = formData['mobileCode'] + formData['mobile'];

            if (fm.checkIsTextEmpty(checkCodeIn)) {
                fm.f7_alert(myApp, '请输入正确的手机号或验证码');
                return;
            }

            checkCodeIn = fnGetMD5(checkCodeIn);
            // console.log('checkCode ' + checkCode);
            // console.log('checkCodeIn ' + checkCodeIn);

            if (checkCode != checkCodeIn) {
                fm.f7_alert(myApp, '请输入正确的短信验证码');
                return;
            }


            var password =  formData['personal.password'];
            if (fm.checkIsTextEmpty(password) || password.length < 6) {
                fm.f7_alert(myApp, '请输入6位以上登录密码');
                return;
            }

            var url = WEB_ROOT + "/customer/CustomerPersonal_passwordUpdate";

            fm.f7_post(myApp, url, formData, function(data){
                if (data["returnValue"] == "1") {
                    fm.f7_alert(myApp, "密码修改成功", function () {
                        var currentView = fm.f7_getCurrentView();
                        currentView.router.loadPage(WEB_ROOT + '/monopoly/loadPage_monopoly_mine_list');
                        currentView.router.refreshPage();
                });
                }
            },null);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('paper-instruction', {
    id:'paper-instruction',
    name:'paper_instruction',
    url:'paper_instruction.jsp',
    preprocess:function(content, url, next){
        var parameters = fm.getParametersFormUrl(url);
        var compiledTemplate = Template7.compile(content);

        url = WEB_ROOT + "/cms/Article_getListArticleVO?"+parameters;
        fm.f7_post(myApp, url, null, function (data) {

            console.log(data);

            var result = data;
            next(compiledTemplate(result));
        }, null);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('paper-investment', {
    id:'paper-investment',
    name:'paper_investment',
    url:'paper_investment.jsp',
    preprocess:function(content, url, next){

        var parameters = fm.getParametersFormUrl(url);
        var compiledTemplate = Template7.compile(content);

        // alert('init:' + this.id);

        if (fm.checkIsTextEmpty(parameters)) {

            var data = {};
            data['customerName'] = "XXX";
            data['customerCertificateNumber'] = "XXX";
            data['bankName'] = "XXX";
            data['bankNumber'] = "XXX";
            data['bankNumber'] = "XXX";
            data['productionName'] = "XXX";
            data['investTermView'] = "XXX";
            data['projectName'] = "XXX";
            data['productionCompositionName'] = "XXX";
            data['customerCertificateNumber'] = "XXX";
            data['payCode'] = "XXX";
            data['moneyString'] = "XXX";
            data['payDate'] = "XXX";
            data['valueDate'] = "XXX";

            next(compiledTemplate(data));

            return;
        }


        url = WEB_ROOT + "/production/Order_loadOrderVOByOrderId?"+parameters;
        fm.f7_post(myApp, url, null, function (data) {

            console.log(data);

            var result = data['returnValue'];
            next(compiledTemplate(result));
        }, null);

        // alert('init:' + this.id);
        // next(content);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('payment-list', {
    id:'payment-list',
    name:'payment_list',
    url:'payment_list.jsp',
    preprocess:function(content, url, next){
        var parameters = fm.getParametersFormUrl(url);
        var compiledTemplate = Template7.compile(content);

        var url = WEB_ROOT + "/sale/PaymentPlan_getListPaymentPlanVOByOrderId?"+parameters;
        fm.f7_post(myApp, url, null, function (data) {

            console.log(data);

            var result = data;
            next(compiledTemplate(result));
        }, null);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('order-pay-code', {
    id:'order-pay-code',
    name:'order_pay_code',
    url:'order_pay_code.jsp',
    preprocess:function(content, url, next){
        var parameters = fm.getParametersFormUrl(url);
        var compiledTemplate = Template7.compile(content);

        var url = WEB_ROOT + "/production/Order_loadOrderVOByOrderId?"+parameters;
        fm.f7_post(myApp, url, null, function (data) {

            console.log(data);

            var result = data['returnValue'];
            next(compiledTemplate(result));
        }, null);
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('order-list', {
    id:'order-list',
    name:'order_list',
    url:'order_list.jsp',
    preprocess:function(content, url, next){

        console.log(url);

        var title = "订单列表";

        if (url.indexOf("orderStatus=0") != -1) {
            title = "未支付订单";
        }
        else if (url.indexOf("orderStatus=1") != -1) {
            title = "已支付订单";
        }
        else if (url.indexOf("orderStatus=2") != -1) {
            title = "已兑付订单";
        }

        var parameters = fm.getParametersFormUrl(url);
        var compiledTemplate = Template7.compile(content);

        url = WEB_ROOT + "/production/Order_getListOrderVO?"+parameters;



        fm.f7_post(myApp, url, null, function (data) {

            // console.log(data);

            var result = {};
            result['title'] = title;
            result['data'] = data;

            console.log(result);
            next(compiledTemplate(result));
        }, null);

    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('card-list', {
    id:'card-list',
    name:'card_list',
    url:'card_list.jsp',
    preprocess:function(content, url, next){
        next(content);
    },
    onPageInit:function (app, page) {

        fm.f7_bindWidthClass('btn-fdcg-customer-account-bind',function(){
            console.log('btn-fdcg-customer-account-bind');

            var url = WEB_ROOT + "/customer/CustomerAccount_fdcgGetBindData";

            var params = {};
            fm.jsonJoin(params, {'customerId':loginCustomer['id']}, true);

            fm.f7_post(myApp, url, params, function(data){

                console.log(data['returnValue']);

                $('#reqDataBind').val(data['returnValue']);

                fm.f7_alert(myApp, '即将跳转到银行绑定界面',function(){
                    $('#form-customer-account-bind').submit();
                });
            },null);
        });

        fm.f7_bindWidthClass('btn-fdcg-customer-account-unbind',function(){
            console.log('btn-fdcg-customer-account-unbind');

            var url = WEB_ROOT + "/customer/CustomerAccount_fdcgGetUnbindData";

            var params = {};
            fm.jsonJoin(params, {'customerId':loginCustomer['id']}, true);

            fm.f7_post(myApp, url, params, function(data){

                console.log(data['returnValue']);

                $('#reqDataUnbind').val(data['returnValue']);

                fm.f7_alert(myApp, '即将跳转到银行解绑界面',function(){
                    $('#form-customer-account-unbind').submit();
                });
            },null);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('card-save', {
    id:'card-save',
    name:'card_save',
    url:'card_save.jsp',
    preprocess:function(content, url, next){
        next(content);
    },
    onPageInit:function (app, page) {
        fm.f7_bindWidthClass('btn-card-add', function(){
            // alert('add card');
            // var formData = fm.f7_getFormToJSON(app, 'form-card-save');
            //
            // console.log(formData);

            var bankCode = fm.getFormValueById('bankCode');
            var number = fm.getFormValueById('number');
            var bankBranchName = fm.getFormValueById('bankBranchName');
            var customerId = fm.getFormValueById('customerId');


            if (fm.contains(number, " ")) {
                fm.f7_alert(myApp, "银行卡号不能含有空格");
                return;
            }

            if (!fm.checkIsBankNumber(number)) {
                fm.f7_alert(myApp, "请检查银行卡号是否输入正确");
                return;
            }


            var data;
            data = fm.buildJSON("bankCode", bankCode, data);
            data = fm.buildJSON("number", number, data);
            data = fm.buildJSON("bankBranchName", bankBranchName, data);
            data = fm.buildJSON("customerId", customerId, data);

            // console.log(data);

            var url = WEB_ROOT + "/customer/CustomerAccount_insertOrUpdateForFuiou";
            fm.f7_post(app, url, data, function(data){

                var currentView = fm.f7_getCurrentView();
                // viewCustomer
                currentView.router.refreshPreviousPage();
                fm.f7_alert(myApp, '添加成功', function () {

                    currentView.router.back();

                });

            }, null);

        });


        fm.f7_bindWidthClass('btn-card-delete', function(){
            // alert('add card');
            // var formData = fm.f7_getFormToJSON(app, 'form-card-save');
            //
            // console.log(formData);

            var id = fm.getFormValueById('id');

            // console.log(data);

            var url = WEB_ROOT + "/customer/CustomerAccount_delete?id="+id;
            fm.f7_post(app, url, null, function(data){

                viewCustomer.router.refreshPreviousPage();
                fm.f7_alert(myApp, '删除成功', function () {

                    viewCustomer.router.back();

                });

            }, null);

        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('order-save', {
    id:'order-save',
    name:'order_save',
    url:'order_save.jsp',
    preprocess:function(content, url, next){


        var compiledTemplate = Template7.compile(content);
        var parameters = getParameters(url);
        url = WEB_ROOT + "/modern/s/production/Production_getProductionWVOById.action?" + parameters + "&r=" + Math.random();
        // console.log(url);
        var data = {};
        $$.getJSON(url, data, function (data) {

            var result;
            result = fm.buildJSON("production", data['returnValue'], result);

            // console.log(result);

            /**
             * 获得客户信息
             */
            var urlCustomer = WEB_ROOT + "/customer/CustomerPersonal_loadCustomerPersonalVOByCustomerPersonalId?customerId=" + loginCustomer['id'] + "&r=" + Math.random();

            // fm.f7_alert(myApp, urlCustomer);

            fm.f7_post(myApp, urlCustomer, "", function(customerData){

                result = fm.buildJSON("customer", customerData['returnValue'], result);



                if (fm.contains(result.production.production.name, "政信通")) {
                    result = fm.buildJSON("productionTypeName", "政信通", result);
                }
                else {
                    result = fm.buildJSON("productionTypeName", "中能", result);
                }

                console.log(result);

                // fm.f7_alert(myApp, result['customer']['accountVOs']);

                next(compiledTemplate(result));

            },null);


        });
    },
    onPageInit:function (app, page) {

        $$('.btn-investment').on('click', function(){
            // alert('btn-investment');
            var formId = "investment";
            var url = WEB_ROOT + "/pay/FuiouPay_buildMobilePayOrder";

            var formData = myApp.formToJSON('#'+formId);

            // formData = fm.getFormValueToJSON('accountId', 'accountId', formData);

            // console.log(formData);

            /*
            var money = formData['money'];
            if (!fm.checkIsTextNumber(money) || money < formData['sizeStart']) {
                fm.f7_alert(myApp, "起投金额为【"+formData['sizeStart']+"元】，请输入正确的投资金额");
                return;
            }

            var increase = formData['increase'];
            if (money % increase != 0) {
                fm.f7_alert(myApp, "投资金额必须是【"+increase+"】的倍数，请输入正确的投资金额");
                return;
            }
            */

            fm.f7_post(app, url, formData, function(data){

                // console.log(data);

                fm.formSetValue(formId, 'FM', data['returnValue']['FM']);

                formData = myApp.formToJSON('#'+formId);

                console.log(formData);


                if (formData['payMethod'] == 0) {
                    $('#investment').submit();
                }
                else {
                    var currentView = fm.f7_getCurrentView();
                    // viewCustomer
                    currentView.router.loadPage('mine/order_pay_code.jsp?orderId='+data['returnValue']['orderId']+'&r='+Math.random());
                }


            }, null);


        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});



pagePool.put('production-list', {
    id:'production-list',
    name:'production_list',
    url:'production_list.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        var url = WEB_ROOT + "/modern/s/production/Production_listProductionVO4modernGroupByIncomeType.action";
        var data = {};
        $$.getJSON(url, data, function(data){

            var result = data['returnValue'];

            console.log(result);

            // console.log(compiledTemplate(result));

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {

        $(function(){
            $(".lc_ct_dsp").css("display","none");
            $(".more_ing").click(function(){

                var s = $(".lc_ct_dsp").css("display") == 'block' ? 'none' : 'block';
                var text = $(".lc_ct_dsp").css("display") == 'block' ? '更多' : '收起';

                console.log($(".more_ing").val());
                $(".lc_ct_dsp").css("display",s);
                $("#btn_production_more").text(text);
            })
        });

        fm.f7_bind('.btn-test', function(){
            // console.log(123);
            //  viewMarket.router.loadPage('market/production_list.jsp?r='+Math.random());
            // viewMarket.router.loadPage('mine/card_list.jsp?r='+Math.random());
            // var currentView = $$('.view.active').f7View;
            // console.log($$('.view.active'));
            // console.log(currentView);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('production-list-jys', {
    id:'production-list-jys',
    name:'production_list',
    url:'production_list.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        var url = WEB_ROOT + "/modern/s/production/Production_listProductionVO4modernGroupByIncomeType.action";
        var data = {};
        $$.getJSON(url, data, function(data){

            var result = data['returnValue'];

            console.log(result);

            // console.log(compiledTemplate(result));

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {

        $(function(){
            $(".lc_ct_dsp").css("display","none");
            $(".more_ing").click(function(){

                var s = $(".lc_ct_dsp").css("display") == 'block' ? 'none' : 'block';
                var text = $(".lc_ct_dsp").css("display") == 'block' ? '更多' : '收起';

                console.log($(".more_ing").val());
                $(".lc_ct_dsp").css("display",s);
                $("#btn_production_more").text(text);
            })
        });

        fm.f7_bind('.btn-test', function(){
            // console.log(123);
            //  viewMarket.router.loadPage('market/production_list.jsp?r='+Math.random());
            // viewMarket.router.loadPage('mine/card_list.jsp?r='+Math.random());
            // var currentView = $$('.view.active').f7View;
            // console.log($$('.view.active'));
            // console.log(currentView);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('production-detail', {
    id:'production-detail',
    name:'production_detail',
    url:'production_detail.jsp',
    preprocess:function(content, url, next){
        next(content);
    },
    onPageInit:function (app, page) {

        fm.f7_bind('.btn-investment',function(e){
            var productionId = e.target.dataset.id;
            // console.log(productionId);


            /**
             * 检查是否开通了存管通道
             */
            var isFdcgChannelOpen = true;

            if (isFdcgChannelOpen) {
                var checkCustomerRegisterUrl = WEB_ROOT + "/fdcg/Customer_fdcgCheckRegister?customerId=" + loginCustomer['id'] + "&r=" + Math.random();

                fm.f7_post(myApp, checkCustomerRegisterUrl, "", function(data){
                    console.log(data);

                    if (data['returnValue'] == '0') {
                        fm.f7_alert(myApp, '您还没有开通存管账户，是否现在开通？', function(){
                            viewMarket.router.loadPage('thirdparty/fdcg/customer_register.jsp?r='+Math.random());
                        });
                    }
                }, null);
                return;
            }

            /**
             * 检查客户资料
             */
            var urlCustomer = WEB_ROOT + "/customer/CustomerPersonal_loadCustomerPersonalVOByCustomerPersonalId?customerId=" + loginCustomer['id'] + "&r=" + Math.random();


            fm.f7_post(myApp, urlCustomer, "", function(customerData){
                console.log(customerData);

                if (fm.checkIsNullObject(customerData['returnValue']['accountVOs']) || customerData['returnValue']['accountVOs'].length == 0) {
                    fm.f7_alert(myApp, "您还没绑定银行卡，请首先绑定银行卡",function () {
                        /**
                         * 绑定银行卡
                         */
                        viewMarket.router.loadPage('mine/card_list.jsp?r='+Math.random());
                        return;
                    });
                }
                else {
                    url = WEB_ROOT + '/monopoly/market/order_save.jsp?productionId=' + productionId;
                    //url = WEB_ROOT + '/ph/phGetPageOrderSave?productionId=' + productionId;
                    viewMarket.router.loadPage(fm.f7_url(url));
                }

            },null);

        });


        $$('#btn-production-share').on('click', function (e) {

            var productionId = e.target.dataset.id;
            var userId = e.target.dataset.userId;
            var title = e.target.dataset.title;
            var summaryText = e.target.dataset.summaryText;

            var url = "/monopoly/share/share_production.jsp?productionId="+productionId+"&userId="+userId;
            url = fm.urlEncode(url);

            myApp.actions([
                // First buttons group
                [
                    // Group Label
                    {
                        text: '请选择',
                        label: true
                    },
                    {
                        text: '分享到微信朋友圈',
                        onClick: function () {
                            // share2Weixin();
                            window.android.invokeAndroid('{jsId:"weixin_share_timeline",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'"}');
                        }
                    },
                    {
                        text: '分享到微信好友',
                        onClick: function () {
                            // share2Weixin();
                            window.android.invokeAndroid('{jsId:"weixin_share_session",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'"}');
                        }
                    }
                ],
                // Second group
                [
                    {
                        text: '取消'
                    }
                ]
            ]);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('mine-info', {
    id:'mine-info',
    name:'mine_info',
    url:'mine_info.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        // var parameters = getParameters(url);
        url = WEB_ROOT + "/customer/CustomerPersonal_loadCustomerPersonalVOByCustomerPersonalId?customerId=" + loginCustomer['id'];

        var data = {};
        $$.getJSON(url, data, function(data){

            var result = data['returnValue'];

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {

    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('mine-list', {
    id:'mine-list',
    name:'mine_list',
    url:'mine_list.jsp',
    preprocess:function(content, url, next){

        next(content);

        // var compiledTemplate = Template7.compile(content);
        // // var parameters = getParameters(url);
        // url = WEB_ROOT + "/sale/PaymentPlan_getCustomerPaymentPlanInfo4ph?customerId=" + loginCustomer['id'];
        //
        //
        // var result;
        // $$.getJSON(url, {}, function(data){
        //
        //
        //     result = data['returnValue'];
        //
        //     $$.getJSON(WEB_ROOT + "/customer/CustomerPersonal_loadCustomerPersonalVOByCustomerPersonalId?customerId=" + loginCustomer['id'], {}, function(data){
        //
        //         console.log(data['returnValue']);
        //
        //         result['name'] = '345';
        //         result['customerPersonalVO'] = data['returnValue'];
        //
        //         next(compiledTemplate(result));
        //
        //     });
        //
        // });

    },
    onPageInit:function (app, page) {
        $$('.btn-logout').on('click', function(){
            logout();
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});

pagePool.put('more-list', {
    id:'more-list',
    name:'more_list',
    url:'more_list.jsp',
    preprocess:function(content, url, next){
        next(content);
    },
    onPageInit:function (app, page) {
        // $$('.check-version').on('click', function(){
        //     // window.location = "youngbook://check-version/{'4':'5','cc':{'d':1}}"
        //     window.location = "youngbook://check-version/customer/88/age/18"
        // });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('mine-change-description', {
    id:'mine-change-description',
    name:'mine_change_description',
    url:'mine_change_description.jsp',
    preprocess:function(content, url, next){

        // console.log(content);
        // alert('init:' + this.id);

        var compiledTemplate = Template7.compile(content);
        var url = WEB_ROOT + "/system/User_load?user.id="+loginCustomer['id']+"&r=" + Math.random();
        var data = {};
        $$.getJSON(url, data, function(data){
            // console.log(url);
            // console.log(data);
            var result = data;

            next(compiledTemplate(result));
        },function(){
            fm.f7_alert(myApp, '加载失败');
        });
    },
    onPageInit:function (app, page) {

        $$('#mine-change-description-description').change();

        fm.f7_bind('#btn-mine-change-description', function () {
            // fm.f7_alert(myApp, '保存介绍2');

            var data = $('#form-mine-change-description').serialize();
            var url = WEB_ROOT + "/system/User_updateUserInfo";
            // console.log(data);

            $$.post(url, data, function(data, status, xhr){
                viewMine.router.loadPage('mine/customer_list.jsp?r='+Math.random());
                viewMine.router.refreshPage();

            },function(xhr, status){
                fm.f7_alert(myApp, '更新失败');
            });

            // fm.f7_post(url, data, function(data){
            //     console.log(data);
            // },function(){
            //     fm.f7_alert(myApp, '更新失败');
            // });
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


pagePool.put('customer-save', {
    id:'customer-save',
    name:'customer_save',
    url:'customer_save.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        next(content);
        var parameters = getParameters(url);

        if (parameters != '') {
            url = WEB_ROOT + "/modern/s/customer/CustomerPersonal_loadCustomerPersonalVOById?"+parameters;
            // console.log(url);
            var data = {};
            $$.getJSON(url, data, function(data){

                var formData = data['returnValue'];


                // console.log(typeof(formData));

                //alert(JSON.stringify(formData));

                console.log(formData);

                // console.log(compiledTemplate(result));
                myApp.formFromJSON('#customer-save-form', formData);


            });
        }
    },
    onPageInit:function (app, page) {
        $$('#btn-customer-save').on('click', function(){

            // alert($('#customer-save-form').length);
            // return;

            var formData = myApp.formToJSON('#customer-save-form');
            // alert(JSON.stringify(formData));
            var data = {
                'personal.id':formData['id'],
                'personal.name':formData['name'],
                'personal.mobile':formData['mobile'],
                'personal.sex':formData['sex'],
                'personal.remark':formData['remark'],
                'personal.birthday':formData['birthday']
            };

            var url = WEB_ROOT + '/modern/s/customer/CustomerPersonal_registerModernByUser.action?';

            if (!fm.checkIsTextEmpty(formData['id'])) {
                // updateModern
                url = WEB_ROOT + '/modern/s/customer/CustomerPersonal_updateModern.action?';
            }


            // console.log(url);
            console.log('form data:');
            console.log(formData);

            fm.f7_post(myApp, url, data, function(data){
                myApp.alert('保存成功');
                viewCustomer.router.loadPage('customer/customer_list.jsp?r='+Math.random());
                viewCustomer.router.refreshPreviousPage();
            },function(){
                myApp.alert('保存失败');
            });


        });

        $$('#btn-customer-delete').on('click',function(){
            var formData = myApp.formToJSON('#customer-save-form');
            // alert(JSON.stringify(formData));
            var data = {
                'personal.id':formData['id'],
                'personal.name':formData['name'],
                'personal.mobile':formData['mobile'],
                'personal.sex':formData['gender']=='男'?1:2,
                'personal.birthday':formData['birthday']
            };

            if (!fm.checkIsTextEmpty(formData['id'])) {
                var url = WEB_ROOT + '/modern/s/customer/CustomerPersonal_deleteModern.action?';

                fm.f7_confirm(myApp, '是否确认删除客户？', function () {
                    fm.f7_post(myApp, url, data, function(data){
                        myApp.alert('操作成功');
                        viewCustomer.router.loadPage('customer/customer_list.jsp?r='+Math.random());
                        viewCustomer.router.refreshPreviousPage();
                    },function(){
                        myApp.alert('保存失败');
                    });
                })


            }
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
});


var homeListPage = {
    id:'home-list',
    name:'home_list',
    url:'home_list.jsp',
    preprocess:function(content, url, next){
        var compiledTemplate = Template7.compile(content);
        var parameters = getParameters(url);
        url = WEB_ROOT + "/modern/s/cms/Article_getPagerArticleVO?" + parameters;
        console.log(url);
        var data = {};
        $$.getJSON(url, data, function (data) {

            var result = data['returnValue'];

            next(compiledTemplate(result));
        });
    },
    onPageInit:function (app, page) {

        var swiper = new Swiper('.swiper-container', {
            spaceBetween: 30,
            centeredSlides: true,
            pagination: {
                el: '.swiper-pagination',
                clickable: true
            }

        });


        // btn-production-detail

        fm.f7_bind('.btn-production-detail',function(e){
            var articleId = e.target.dataset.id;

            viewHome.router.loadPage( WEB_ROOT + '/api/monopoly/loadPage_info_detail?articleId='+articleId);
        });

        $$('.btn-home-share').on('click', function (e) {

            var articleId = e.target.dataset.id;
            var userId = e.target.dataset.userId;
            var title = e.target.dataset.title;
            var summaryText = e.target.dataset.summaryText;

            var url = "/monopoly/share/share_article.jsp?userId="+userId+"&articleId="+articleId+"&title="+title;
            url = fm.urlEncode(url);

            console.log(e);

            myApp.actions([
                // First buttons group
                [
                    // Group Label
                    {
                        text: '请选择',
                        label: true
                    },
                    // First button
                    {
                        text: '分享到微信朋友圈',
                        onClick: function () {
                            // share2Weixin();

                            window.android.invokeAndroid('{jsId:"weixin_share_timeline",articleId:"'+articleId+'",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'"}');
                            // /ph/share/share_article.jsp?userId="+userId+"&articleId="+articleId+"&title="+title;
                        }
                    },
                    {
                        text: '分享到微信好友',
                        onClick: function () {
                            // share2Weixin();
                            window.android.invokeAndroid('{jsId:"weixin_share_session",articleId:"'+articleId+'",userId:"'+userId+'",title:"'+title+'",summaryText:"'+summaryText+'",url:"'+url+'"}');
                        }
                    }
                ],
                // Second group
                [
                    {
                        text: '取消'
                    }
                ]
            ]);
        });

        $$('.btn-home-share-preview').on('click', function (e) {
            // console.log(e);
            var articleId = e.target.dataset.id;
            // console.log(articleId);
            // window.location = 'share/share_article.jsp?articleId='+articleId;
            viewHome.router.loadPage('share/share_article.jsp?articleId='+articleId);
        });
    },
    onReload:function () {
        alert('onReload:' + this.id);
    }
};
pagePool.put('home-list', homeListPage);

