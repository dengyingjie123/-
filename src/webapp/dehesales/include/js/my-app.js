var viewCustomer;
var viewMine;
var viewMarket;
var $$ = Dom7;
// Export selectors engine

// Initialize your app
var myApp = new Framework7({
    modalTitle: '德合CRM',
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

        var url = WEB_ROOT + "/system/checkLoginUser";
        fm.f7_post(myApp, url, null, function (data) {
            if (data['returnValue'] == '0') {
                fm.f7_alert(myApp, '请重新登录', function(){
                    window.location = 'login.jsp';
                    return false;
                });
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
        window.location = 'login.jsp';

    });


    return false;
}



function initApp() {
    viewMarket = myApp.addView('#view-market', {
        main:true,
        domCache:false,
        dynamicNavbar: true
    });
    viewMarket.router.loadPage('market/production_list.jsp?r='+Math.random());

    // viewMine = myApp.addView('#view-mine',{
    //     domCache:false,
    //     dynamicNavbar: true
    // });
    // // viewCustomer.router.loadPage('mine/mine_list.jsp?r='+Math.random());
    // viewMine.router.loadPage(WEB_ROOT + "/dehesales/loadPage_ph_mine_list");

    viewCustomer = myApp.addView('#view-customer',{
        domCache:false
    });
    viewCustomer.router.loadPage(WEB_ROOT + '/dehesales/loadPage_deheSales_customer_list?r='+Math.random());


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


        if (id == 'btn-view-2') {
            viewMarket.router.loadPage('market/production_list.jsp?r='+Math.random());
            viewMarket.router.refreshPage();
        }


        // if (id == 'btn-view-mine') {
        //     // viewCustomer.router.loadPage('mine/mine_list.jsp?r='+Math.random());
        //     viewMine.router.loadPage(WEB_ROOT + "/dehesales/loadPage_ph_mine_list");
        //     viewMine.router.refreshPage();
        // }

        //

        if (id == 'btn-view-customer') {
            viewCustomer.router.loadPage(WEB_ROOT + '/dehesales/loadPage_deheSales_customer_list?r='+Math.random());
            viewCustomer.router.refreshPage();
        }

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

        loginCustomer = data;
        window.location = '../index.jsp';
    },null);


});


$$('#btn-login-register').on('click',function (e) {
    var url = WEB_ROOT + "/customer/CustomerPersonal_register";
    // var data = $('#login-register-form').serialize();

    var formData = myApp.formToJSON('#login-register-form');

    var checkCode =  formData['checkCode']
    var checkCodeIn = formData['mobileCode'] + formData['mobile'];
    checkCodeIn = fnGetMD5(checkCodeIn);


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
            window.location = WEB_ROOT + "/ph/index.jsp?r="+Math.random();
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
    var data = {'loginName':mobile,'password':password,'userType':'user'};


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