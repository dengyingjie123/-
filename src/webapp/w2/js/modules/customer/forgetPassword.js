var flag = false;

// 验证手机号码的正则表达式
function VerificationMobile(str) {
    if (/^(13|14|15|17|18)\d{9}$/.test(str)) {
        return true;
    }
    return false;
}
// 检查手机号码调用的事件
function checkUserMobile() {
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
// 检查验证码有没有输入
function checkCode() {
    flag = true;
    var code = $('#captcha').val()
    if (code == "" || code == null) {
        popStatus(2, "请输入验证码", 1, null, null);
        flag = false;
    }
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

// 提交修改
function nextSubmit4ForGetPS() {
    var mobile = $('#mobile').val();
    if (mobile == '' || mobile.length == 0 || mobile == null || mobile == undefined) {
        popStatus(2, "请输入手机号码", 1, null, null);
        return;
    }
    var code = $('#captcha').val()
    if (code == "" || code == null) {
        popStatus(2, "请输入验证码", 1, null, null);
        return;
    }
    $('#formForGetPassword').submit();
}
function  resetRefresh(){
    var codeId= document.getElementById("codeId");
    onClickRefresh(codeId,1);
}