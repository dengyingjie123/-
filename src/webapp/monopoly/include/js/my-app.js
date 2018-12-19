var viewHome;
var viewCustomer;
var viewMine;
var viewProduction;
var viewMore;
var $$ = Dom7;
// Export selectors engine

// Initialize your app
var myApp = new Framework7({
    modalTitle: '大富翁',
    modalButtonOk:'确定',
    modalButtonCancel:'取消',
    modalPreloaderTitle:'加载中……',
    material: true,
    animateNavBackIcon: true,
    precompileTemplates: true,
    template7Pages: true,
    cache:false,
    preroute: function (view, options) {

        console.log("preroute")
        console.log(loginCustomer);

        var url = WEB_ROOT + "/system/checkLoginCustomer";
        fm.f7_post(myApp, url, null, function (data) {
            if (data['returnValue'] == '0') {

                var url = WEB_ROOT + "/monopoly/login/login_mobile_code.jsp";

                /**
                 * 通过统一认证登录方式登录
                 */
                if (!fm.checkIsTextEmpty(loginToken)) {
                    url = WEB_ROOT +  "/system/loginWithToken?loginToken="+loginToken+"&success_page=login_success_monopoly&fail_page=login_mobile_code_monopoly";
                    window.location = url;
                }
                else {
                    fm.f7_alert(myApp, '请重新登录', function(){
                        window.location = url;
                        return false;
                    });
                }
            }
        }, null);

    },
    preprocess: function (content, url, next) {


        for(var i = 0; i++ < pagePool.size; pagePool.next()) {
            var myPage = pagePool.value();

            // console.log(myPage);

            console.log("load: " + url);

            if (!fm.checkIsNullObject(myPage) && !fm.checkIsTextEmpty(url) && url.indexOf(myPage['url']) != -1) {
                myPage.preprocess(content, url, next);

                return;
            }
        }

        next(content);

    },
    onPageBeforeInit:function(app, page){

    },
    onPageInit: function (app, page) {
        var myPage = pagePool.get(page.name);
        if (!fm.checkIsNullObject(myPage)) {
            myPage.onPageInit(app, page);
        }
    }
});


myApp.onPageInit('login-screen-embedded', function (page) {
    $$('#mainToolbar').hide();
    $$(page.container).find('.button').on('click', function () {


        var username = $$(page.container).find('input[name="username"]').val();
        var password = $$(page.container).find('input[name="password"]').val();
        myApp.alert('Username: ' + username + ', password: ' + password, function () {
            // mainView.router.back();
        });
    });
});


function checkLogin() {

    console.log('check login:' + loginCustomer);

    if (loginCustomer != undefined && loginCustomer != '') {
        initApp();
        return true;
    }
    else {
        window.location = 'login.jsp';
        return false;
    }
}

function logout() {


    var url = WEB_ROOT + '/system/logoutCustomer';
    $$.post(url, '', function(data){
        loginCustomer = null;
        window.location = WEB_ROOT + '/monopoly/login.jsp';

    });


    return false;
}



function initApp() {



    // viewHome = myApp.addView('#view-1', {
    //     main:true,
    //     domCache:false,
    //     dynamicNavbar: true
    // });
    // viewHome.router.loadPage('more/customer_list.jsp?r='+Math.random());

    if (customerStatus_monopoly == 3) {
        viewProduction = myApp.addView('#view-production', {
            domCache:false
        });
        viewProduction.router.loadPage(WEB_ROOT + "/api/monopoly/loadPage_production_list");
    }


    viewCustomer = myApp.addView('#view-customer',{
        domCache:false,
        dynamicNavbar: true
    });
    // viewCustomer.router.loadPage('mine/mine_list.jsp?r='+Math.random());
    viewCustomer.router.loadPage(WEB_ROOT + "/monopoly/loadPage_monopoly_mine_list");


    viewMore = myApp.addView('#view-more',{
        domCache:false,
        dynamicNavbar: true
    });
    // viewCustomer.router.loadPage('mine/mine_list.jsp?r='+Math.random());
    viewMore.router.loadPage(WEB_ROOT + "/api/monopoly/loadPage_more_list");

    // viewMine = myApp.addView('#view-5',{
    //     domCache:false
    // });
    // viewMine.router.loadPage('more/customer_list.jsp?r='+Math.random());

    viewHome = myApp.addView('#view-home',{
        main:true,
        domCache:false
    });
    viewHome.router.loadPage(WEB_ROOT + '/api/monopoly/loadPage_home_list');

    $$(document).on('navbarInit', function (e) {
        var navbar = e.detail.navbar;
        var page = e.detail.page

        console.log("页面切换");
        console.log(page);
    });

    $$('a').on('click', function (e) {
        //console.log('clicked');
        //console.log(e);

        var id = e.target.id;

        if (id == '') {
            id = e.target.parentElement.id;
        }

        console.log("事件监听["+id+"]");


        if (id == 'btn-view-home') {
            viewHome.router.loadPage(WEB_ROOT + '/api/monopoly/loadPage_home_list');
        }

        if (id == 'btn-view-production') {
            viewProduction.router.loadPage(WEB_ROOT + "/api/monopoly/loadPage_production_list?r"+Math.random());
        }


        if (id == 'btn-view-3') {
            // viewCustomer.router.loadPage('mine/mine_list.jsp?r='+Math.random());
            viewCustomer.router.loadPage(WEB_ROOT + "/monopoly/loadPage_monopoly_mine_list");
        }


        //

        if (id == 'btn-view-more') {
            viewMore.router.loadPage(WEB_ROOT + "/api/monopoly/loadPage_more_list");
        }



        //

        // if (id == 'btn-view-5') {
        //     viewMine.router.loadPage('more/customer_list.jsp?r='+Math.random());
        //     viewMine.router.refreshPage();
        // }

    });

}



function getParameters(url) {

    var index = url.indexOf("?");

    if (index != -1) {
        index++;
        var parameters = url.substr(index);
        return parameters;
    }

    return '';

}

$$('#btn-login-mobile-code').on('click',function (e) {

    var formData = myApp.formToJSON('#form-login-mobile-code');

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

    /**
     * 执行登录
     */

    var url = WEB_ROOT + "/system/Login_customerRegisterAndLogin";
    fm.f7_post(myApp, url, formData, function (data) {
        console.log(data);
        // alert(JSON.stringify(data));
        /**
         * 登录成功
         */

        loginCustomer = fm.convert2Json(data['returnValue']);

        loginToken = data['token'];

        console.log("token:" + loginToken);
        console.log("customerId:" + loginCustomer['id']);

        var parameters = {};
        parameters['cmd'] = '0001';
        parameters['customerId'] = loginCustomer['id'];
        parameters['loginToken'] = loginToken;

        if (fm.checkIsAndroid()) {
            try {
                window.android.loginAndroid(loginCustomer['id'], loginToken);
            }
            catch (e) {

            }

        }
        else if (fm.checkIsiOS()) {
            try {
                window.webkit.messageHandlers.invokeiOS.postMessage(parameters);
            }
            catch (e) {

            }
        }

        window.location = WEB_ROOT + '/monopoly/index.jsp';
    },null);


});


$$('#btn-login-register').on('click',function (e) {
    var url = WEB_ROOT + "/customer/CustomerPersonal_register";
    // var data = $('#login-register-form').serialize();

    var formData = myApp.formToJSON('#login-register-form');

    var checkCode =  formData['checkCode']
    var checkCodeIn = formData['mobileCode'] + formData['mobile'];
    checkCodeIn = fnGetMD5(checkCodeIn);
    // console.log('checkCode ' + checkCode);
    // console.log('checkCodeIn ' + checkCodeIn);
    //
    // return;

    if (checkCode != checkCodeIn) {
        fm.f7_alert(myApp, '请输入正确的短信验证码');
        return;
    }

    var name = formData['name'];
    if (fm.checkIsTextEmpty(name)) {
        fm.f7_alert(myApp, '请输入姓名');
        return;
    }

    var password = formData['password'];
    if (fm.checkIsTextEmpty(password)) {
        fm.f7_alert(myApp, '请输入密码');
        return;
    }

    // console.log(data);
    $$.post(url, formData, function (data) {
        // console.log(data);
        data = fm.convert2Json(data);

        if (data.code != 100) {
            fm.f7_alert(myApp, data.message);
            return;
        }

        if (data.code == 100) {
            window.location = WEB_ROOT + "/monopoly/index.jsp?r="+Math.random();
        }

    },function () {
        fm.f7_alert(myApp, '注册失败');
    });
});



$$('#btn-login-password').on('click', function(){

    console.log('btn-login-password');

    var url = WEB_ROOT + '/modern/common/system/loginUniversal';

    var mobile = $$('#txt-login-mobile').val();
    var password = $$('#txt-login-password').val();
    var data = {'loginName':mobile,'password':password,'userType':'customer'};

    console.log(data);


    fm.f7_post(myApp, url, data, function (data) {
        console.log(data);

        loginCustomer = data;
        window.location = 'index.jsp';
    },null);

});

function androidCallback(jsId, data){
    if(jsId == 'mine_change_avatar') {
        viewMine.router.loadPage('mine/customer_list.jsp');
        viewMine.router.refreshPage();
    }
}

function relogin() {

    var url = WEB_ROOT + "/monopoly/login/login/login_mobile_code.jsp"
    if (!fm.checkIsTextEmpty(loginToken)) {
        url = WEB_ROOT + "/system/loginWithToken?loginToken="+loginToken+"&success_page=login_success_monopoly&fail_page=login_mobile_code_monopoly";
    }

    window.location = url;
}