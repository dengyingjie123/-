var viewHome;
var viewCustomer;
var viewCircle;
var viewMine;
var viewMarket;
var $$ = Dom7;
// Export selectors engine

// Initialize your app
var myApp = new Framework7({
    modalTitle: '理财圈',
    modalButtonOk:'确定',
    modalButtonCancel:'取消',
    modalPreloaderTitle:'加载中……',
    material: true,
    animateNavBackIcon: true,
    precompileTemplates: true,
    template7Pages: true,
    cache:false,
    preroute: function (view, options) {

    },
    preprocess: function (content, url, next) {


        for(var i = 0; i++ < pagePool.size; pagePool.next()) {
            var myPage = pagePool.value();

            // console.log(myPage);

            if (!fm.checkIsNullObject(myPage) && url.indexOf(myPage['url']) != -1) {
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

    console.log('check login:' + loginUser);

    if (loginUser != undefined && loginUser != '') {
        initApp();
        return true;
    }
    else {
        window.location = 'login.jsp';
        return false;
    }
}

function logout() {


    var url = WEB_ROOT + '/modern/common/system/loginUniversal';
    $$.post(url, '', function(data){
        loginUser = '';
        window.location = 'login.jsp';

    });


    return false;
}



function initApp() {

    // Add views
    viewHome = myApp.addView('#view-1',{
        main:true
    });

    viewHome.router.loadPage('home/home_list.jsp?columnId=16D15C9FA5FB4702B161E1A951E810D0&r='+Math.random());


    viewMarket = myApp.addView('#view-market', {
        dynamicNavbar: true
    });
    viewMarket.router.loadPage('market/production_list.jsp?r='+Math.random());

    viewCustomer = myApp.addView('#view-customer',{
        dynamicNavbar: true
    });
    viewCustomer.router.loadPage('customer/customer_list.jsp?r='+Math.random());

    viewCircle = myApp.addView('#view-4');
    viewCircle.router.loadPage('circle/circle_list.jsp?r='+Math.random());

    viewMine = myApp.addView('#view-5');
    // viewMine.router.loadPage('mine/mine_list.jsp?r='+Math.random());
    viewMine.router.loadPage(WEB_ROOT + "/ph/loadPage_ph_mine_list");


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


        if (id == 'btn-view-1') {
            viewHome.router.loadPage('home/home_list.jsp?columnId=16D15C9FA5FB4702B161E1A951E810D0&r='+Math.random());
            viewHome.router.refreshPage();
        }

        if (id == 'btn-view-2') {
            viewMarket.router.loadPage('market/production_list.jsp?r='+Math.random());
            viewMarket.router.refreshPage();
        }


        if (id == 'btn-view-3') {
            viewCustomer.router.loadPage('customer/customer_list.jsp?r='+Math.random());
            viewCustomer.router.refreshPage();
        }

        if (id == 'btn-view-4') {
            viewCircle.router.loadPage('circle/circle_list.jsp?r='+Math.random());
            viewCircle.router.refreshPage();
        }

        //

        if (id == 'btn-view-5') {
            // viewMine.router.loadPage('mine/mine_list.jsp?r='+Math.random());
            viewMine.router.loadPage(WEB_ROOT + "/ph/loadPage_ph_mine_list");
            viewMine.router.refreshPage();
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
    console.log('checkCode ' + checkCode);
    console.log('checkCodeIn ' + checkCodeIn);

    if (checkCode != checkCodeIn) {
        fm.f7_alert(myApp, '请输入正确的短信验证码');
        return;
    }

    /**
     * 执行登录
     */

    var url = WEB_ROOT + "/system/Login_loginUserWithMobileCode";
    fm.f7_post(myApp, url, formData, function (data) {
        console.log(data);

        loginUser = data;
        window.location = '../index.jsp';
    },null);


});


$$('#btn-login-register').on('click',function (e) {
    var url = WEB_ROOT + "/system/User_register";
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
            window.location = WEB_ROOT + "/modern2/index.jsp?r="+Math.random();
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

        loginUser = data;
        window.location = 'index.jsp';
    },null);

});

function androidCallback(jsId, data){
    if(jsId == 'mine_change_avatar') {
        viewMine.router.loadPage('mine/more_list.jsp');
        viewMine.router.refreshPage();
    }

}