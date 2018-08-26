/**
 * Created by 邓超
 * Date 2015-6-15
 */
var countdown = 60;
function setTime() {
    if (countdown == 0) {
        countdown = 60;
        $('#code').removeAttr("disabled");
        $('#code').text("获取动态码");
        return;
    } else {
        $('#code').attr('disabled', "true");
        $('#code').text("(" + countdown + ")重新发送");
        countdown--;
    }
    setTimeout(function () {
        setTime()
    }, 1000)
}
// 验证动态码是否正确
function verificationCode(mobileID,codeID){
    var params = {
        'mobile':$('#'+mobileID).val(),
        'mobileValidateCode':$('#'+codeID).val()
    }
    // 发起请求查询动态码是否正确
    var url = '/core/w2/customer/verificationMobileCode';
    fweb.post(url,params,function(data){
        var data = data.returnValue;
        if(data != undefined){
            popStatus(2,data,1);
        }
    },null)
}

/*
 正常情况下的获取手机动态码
 */
var isSend = true;
function getMobiCode(id) {
    var params = {
        'mobile': $('#mobile').val()
    }
    var url = '/core/w2/customer/getMobileRegisterCode';
    fweb.post(url, params, function (data) {
        if (data.code == 100 && data.message == 'OK') {
            popStatus(1, '短信已发送', 1, null, null);
            if (data.returnValue != "") {
                $("#" + id).text(data.returnValue);
            }
            setTime();
            isSend = true;
        } else {
            isSend = false;
        }
    });
}

/*
 创建人：张舜清
 时间：7/16/2015
 内容：这是重载方法是找回密码手机验证调用
 */
function getMobileCode(id) {
    var params = {
        'mobile': $('#mobile').val()
    }
    var url = '/core/w2/customer/getMobileCode';
    fweb.post(url, params, function (data) {
        if (data.code == 100 && data.message == 'OK') {
            popStatus(1, '短信已发送', 1, null, null);
            if (data.returnValue != "") {
                $("#" + id).text(data.returnValue);
            }
            isSend = true;
            setTime();
        } else {
            isSend = false;
        }
    });
}
/**
 * 创建人：张舜清
 * 时间：7/29/2015
 * 内容：重载一个注册获取手机动态码的方法
 *
 * @param id
 */
function mobileRegisterCode(id) {
    var mobile = $('#mobile').val();
    if (mobile == undefined || mobile == null || mobile == "" || mobile.length == 0) {
        popStatus(2, "请先输入手机号码", 2, null, null);
        return;
    }
    if (mobile.length!=11 || !VerificationMobile(mobile)) {
        popStatus(2,"手机格式有误",2);
        return;
    }
    else {
        var params = {
            'mobile': $('#mobile').val()
        }
        var url = '/core/w2/customer/getMobileCode4ForgetPwd';
        fweb.post(url, params, function (data) {
            setTime();
            popStatus(1, data.message, 1, null, null);
        });
    }
}
// 验证手机号码的正则表达式
function VerificationMobile(str) {
    if (/^(13|14|15|17|18)\d{9}$/.test(str)) {
        return true;
    }
    return false;
}

function iponeSubmit() {

    $('#iponeSubmmtButton').val("正在提交");
    $('#iponeSubmmtButton').attr('disabled', "disabled");
    if (isSend) {
        if ($('#mobiValidateCode').val() == '') {
            popStatus(2, "请输入手机动态码", 1, null, null);
            $('#iponeSubmmtButton').val("下一步");
            $('#iponeSubmmtButton').removeAttr('disabled');
            return;
        }
        if ($('#mobile').val() == "") {
            popStatus(2, "请输入绑定的手机号码", 1, null, null);
            $('#iponeSubmmtButton').val("下一步");
            $('#iponeSubmmtButton').removeAttr('disabled');
            return;
        }
        if ($('#password').val() == null || $('#password').val() == "") {
            popStatus(2, "请输入登录密码", 1, null, null);
            $('#iponeSubmmtButton').val("下一步");
            $('#iponeSubmmtButton').removeAttr('disabled');
            return;
        }
        var password = $('#password').val();
        $('#password').val(fnGetMD5(password));
        $('.form-horizontal').submit();
    } else {
        popStatus(2, "请先发送手机动态码", 1, null, null);
        $('#iponeSubmmtButton').val("下一步");
        $('#iponeSubmmtButton').removeAttr('disabled');
    }
}

function iponeSubmitSecond() {

    $('#iponeSubmmtButton').val("正在提交");
    $('#iponeSubmmtButton').attr('disabled', "disabled");
    if (isSend) {
        if ($('#mobiValidateCode').val() == '') {
            popStatus(2, "请输入手机动态码", 1, null, null);
            $('#iponeSubmmtButton').val("下一步");
            $('#iponeSubmmtButton').removeAttr('disabled');
            return;
        }
        if ($('#mobile').val() == "") {
            popStatus(2, "请输入绑定的手机号码", 1, null, null);
            $('#iponeSubmmtButton').val("下一步");
            $('#iponeSubmmtButton').removeAttr('disabled');
            return;
        }
        $('.form-horizontal').submit();
    } else {
        popStatus(2, "请先发送手机动态码", 1, null, null);
        $('#iponeSubmmtButton').val("下一步");
        $('#iponeSubmmtButton').removeAttr('disabled');
    }
}

// 检查输入是否输入手机号码事件
//function verifyMobile() {
//    var flag = true;
//    $('#mobileTip').text("");
//    var mobile = $('#mobile').val();
//    if (mobile == null || mobile.length == 0) {
//        $('#mobileTip').text("请输入手机号码");
//        flag = false;
//    }
//    return flag;
//}
// 检查是否输入验证码事件
function checkVerifyCode() {
    var flag = true;
    $('#verifyMobileTip').text("");
    var code = $('#mobiValidateCode').val();
    if (code == null || code.length == 0) {
        $('#verifyMobileTip').text("请输入验证码");
        popStatus(2, "请输入验证码", 1, null, null);
        $('#iponeSubmmtButton').val("下一步");
        $('#iponeSubmmtButton').removeAttr('disabled');
        flag = false;
    }
    return flag;
}

function nextSubmit() {
    checkVerifyCode();
//    verifyMobile();
    if (checkVerifyCode()) {
        popStatus(3, '请勿刷新或关闭浏览器', 1, null, null);
        var params = {
            'mobiValidateCode': $('#mobiValidateCode').val(),
            'mobile' : $('#mobile').val()
        };
        var url = "/core/w2/customer/verifyMobile";
        fweb.post(url, params, function (data) {
            var data = data.returnValue;
            if (data == "0") {
                popStatus(2, "验证码失效，请重新获取", 1, null, null);
                $('#iponeSubmmtButton').val("下一步");
                $('#iponeSubmmtButton').removeAttr('disabled');
            }
            else if (data == "2") {
                var url = "/core/w2/modules/customer/forgetPassword.jsp";
                popStatus(4, "请重新输入用户名", 1, url, null);
                $('#iponeSubmmtButton').val("下一步");
                $('#iponeSubmmtButton').removeAttr('disabled');
            }
            else if (data == "1") {
                var url = "/core/w2/modules/customer/resetPassword.jsp";
                popStatus(1, "验证成功，即将转到重置密码页面", 1, url, null);
            }
            else if (data == "4") {
                var url = "/core/w";
                popStatus(4, "验证错误", 1, url, null);
            }
        }, null)
    }
    else {
        popStatus(4, '请输入验证码', 1, null, null);
        $('#iponeSubmmtButton').val("下一步");
        $('#iponeSubmmtButton').removeAttr('disabled');
    }
}