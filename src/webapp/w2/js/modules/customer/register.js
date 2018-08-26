/**
 * Created by 方斌杰
 * Date 2015-9-28
 */

// 检查密码调用的事件
var flag = false;

// 检查密码的基本
function checkPassWord() {
    $('#password').text("");

    var pwd = $("#pwd").val();
    if (pwd.length < 6) {
        $('#password').attr("class","notice_bar_error");
        flag = false;
    }
    else if (pwd.length > 18) {
        $('#password').attr("class","notice_bar_error");
        flag = false;
    }
    else if (VerificationPassWordStrong(pwd) == 1) {
        $('#password').attr("class","notice_bar_error");
        flag = false;
    }
    else if (VerificationPassWordStrong(pwd) == 2) {
        $('#password').attr("class","notice_bar_success");
        flag = true;
    }
    else if (VerificationPassWordStrong(pwd) == 3 || VerificationPassWordStrong(pwd) == 4) {
        $('#password').attr("class","notice_bar_success");
        flag = true;
    }
    return flag;
}

// 检查密码两次密码是否相同调用的事件
function checkPwdAgain() {
    $('#passwordTip').text("");
    var pwd = $("#pwd").val();
    var pwdAgain = $("#pwdAgain").val();
    if (pwd != pwdAgain || pwdAgain == "") {
        $('#passwordTip').attr("class", "notice_bar_error");
        flag = false;
    }
    if(flag == true) {
        if (pwd == pwdAgain) {
            $('#passwordTip').attr("class","notice_bar_success");
            flag = true;
        } else {
            $('#passwordTip').attr("class", "notice_bar_error");
            flag = false;
        }
    }

    return flag;
}

// 检查手机号码调用的事件
function checkMobile4Register() {
    $('#mobileTip').text("");
    var mobile = $('#mobile').val();
    if (mobile.length != 11 || !VerificationMobile(mobile)) {
        $('#mobileTip').attr("class","notice_bar_error");
        return false;
    } else {
        $('#mobileTip').attr("class","notice_bar_success");
        return true;
    }
    return true;
}

// 勾选会员协议调用的事件
function onClickConsentAgreement() {
    if (!document.getElementById("agree").checked) {
        $('#agreementService').css("color", "#e74c3c");
        $('#agreementService').text("您需要同意《会员服务协议》");
        flag = false;
    }
    else {
        $('#agreementService').text("");
        flag = true;
    }
    return flag;
}

// 限制输入数字
function inputNumber() {
    var val = $('#mobile').val();
    if (parseInt(val) < 0 || parseInt(val) > 0) {
        $('#mobile').val(parseInt(val));
    }
    else {
        $('#mobile').val("");
    }
}

// 验证手机号码的正则表达式
function VerificationMobile(str) {
    if (/^(13|14|15|17|18)\d{9}$/.test(str)) {
        return true;
    }
    return false;
}

// 检测密码强度的正则表达式
function VerificationPassWordStrong(str) {
    var modes = 0;
    //正则表达式验证符合要求的
    if (str.length < 1) return modes;
    if (/\d/.test(str)) modes++; //数字
    if (/[a-z]/.test(str)) modes++; //小写
    if (/[A-Z]/.test(str)) modes++; //大写
    if (/\W/.test(str)) modes++; //特殊字符
    //逻辑处理
    switch (modes) {
        case 1:
            return 1; // 1代表密码强度弱
            break;
        case 2:
            return 2; // 2代表密码强度中
        case 3: // 3代表密码强度强
        case 4: // 4代表密码强度非常好
            return str.length < 12 ? 3 : 4
            break;
    }
}

var isClick = false;

/**
 * 提交
 */
function submitRegisterForm() {
    var isAgree = onClickConsentAgreement();
    if (isAgree) {
        flag = false;
        checkPassWord();
        checkPwdAgain();
        //判读是否填写验证码
        var  mobiValidateCode=  $("#mobiValidateCode").val();
        if(!mobiValidateCode){
            popStatus(2, "请填写动态码",2);
            flag = false
            return;
        }
        if (flag) {
            $('#mobileTip').text("");
            var mobile = $('#mobile').val();
            if (mobile.length != 11 || !VerificationMobile(mobile)) {
                $('#mobileTip').css("color", "#e74c3c");
                $('#mobileTip').text("手机号码格式有误");
                flag = false;
                return;
            }


            // 提交
            isClick = false;
            var url = "/core/w2/customer/mobileRegister";
            var params = $("#RegisterForm").serializeArray();
            var pwd = $('#pwd').val();
            var pwdAgain = $('#pwdAgain').val();
            $("#mobileRegisterForm").attr("action", url);
            $('#pwd').val(fnGetMD5(pwd));
            $('#pwdAgain').val(fnGetMD5(pwdAgain));
            $("#mobileRegisterForm").submit();
        }
    }
}

// 获取手机动态码
function mobileRegisterCode(id) {
    var mobile = $('#mobile').val();
    var mobiValidateCode = $('#mobiValidateCode').val('');
    $('#mobileCode').val('');
    if (mobile == undefined || mobile == null || mobile == "" || mobile.length == 0) {
        $('#mobileTip').attr("class","notice_bar_error");

        return;
    }
    if (mobile.length != 11 || !VerificationMobile(mobile)) {
        $('#mobileTip').attr("class","notice_bar_error");
        return;
    }
    else {
        var params = {
            'mobile': $('#mobile').val()
        }
        var url = '/core/w2/customer/getMobileRegisterCode';
        fweb.post(url, params, function (data) {
            if (data.code == 100 && data.message == 'OK') {
                if (data.returnValue != "") {
                    $("#" + id).val(data.returnValue);
                }
                isSend = true;
                setTime();
            } else {
                isSend = false;
            }
        });
    }
}

// 手机动态码倒计时
var countdown = 60;
function setTime() {
    if (countdown == 0) {
        countdown = 60;
        $('#code').removeAttr("disabled");
        $('#code').css('font-size',"15px");
        $('#code').text("获取动态码");
        return;
    } else {
        $('#code').attr('disabled', "true");
        $('#code').css('font-size',"10px");
        $('#code').text("(" + countdown + ")重新发送");
        countdown--;
    }
    setTimeout(function () {
        setTime()
    }, 1000)
}

// 验证动态码是否正确
function verificationCode(mobileID, codeID) {
    var mobile = $('#' + mobileID).val();
    var validateCode = $('#' + codeID).val();
    if(validateCode == '') {
        $('#mobileCode').attr("class","notice_bar_error");
        return false;
    } else {
        $('#mobileCode').text('');
        var params = {
            'mobile': $('#' + mobileID).val(),
            'mobileValidateCode': $('#' + codeID).val()
        }
        // 发起请求查询动态码是否正确
        // $('#mobileCode').attr("class","notice_bar_success");
        //var url = '/core/w2/customer/verificationMobileCode';
        //fweb.post(url, params, function (data) {
        //    var dataValue = data.returnValue;
        //    if(dataValue == ''||dataValue == undefined) {
        //        $('#mobileCode').attr("class","notice_bar_success");
        //        return false;
        //    }
        //    else {
        //        $('#mobileCode').attr("class","notice_bar_error");
        //
        //    }
        //}, null)
    }
}