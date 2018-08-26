/**
 * Created by zhouhaihong on 2015/10/9.
 */
var flag = true;
// 验证手机号码的正则表达式
function VerificationMobile(str) {
    if (/^(13|14|15|17|18)\d{9}$/.test(str)) {
        return true;
    }
    return false;
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
// 检查手机号码调用的事件
function checkMobile() {
    flag = true;
    $('#mobileTip').text("");
    var mobile = $('#mobile').val();
    if (!VerificationMobile(mobile)) {
        $('#mobileTip').css("color", "#e74c3c");
        $('#mobileTip').text("手机号码格式有误");
        flag = false;
    }
    else {
        flag = true;
    }
    return flag;
}
// 检查动态码
function checkVerification() {
    flag = true;
    var validateCode = $("#mobiValidateCode").val();
    if (validateCode == "" || validateCode.length == 0) {
        popStatus(2, "请输入动态码", 1, null, null);
        flag = false;
    }
    else {
        flag = true;
    }
    return flag;
}


// 提交下一步事件
function nextSubmit4VerifyMobileCode() {
    checkMobile();
    checkVerification();
    if (flag) {
        $('#nextSubmitVerifyMobileCode').val("正在提交");
        $('#nextSubmitVerifyMobileCode').attr("disabled",'disabled');
        $('#formVerifyMobileCode').submit();
    }
}