var flag = true;
// 重置密码页面检查新密码是否有输入事件
function checkNewPassword() {
    flag = true;
    $('#newPasswordTip').text("");
    $('#newPasswordTip').css("color", "#e74c3c");
    var password = $('#newPassword').val();
    if (password == null || password.length < 0 || password == "") {
        flag = false;
    }

    else if (password.length < 6) {
        $('#newPasswordTip').text("密码长度不能低于6位");
        $('#newPasswordAgain').attr("disabled","disabled");
        flag = false;
    }
    else if (VerificationPassWordStrong(password) == 1) {
        $('#newPasswordTip').text("强度：弱  密码过于简单，请至少包含一个字母");
        $('#newPasswordAgain').attr("disabled","disabled");
        flag = false;
    }
    else if (VerificationPassWordStrong(password) == 2) {
        $('#newPasswordTip').text("通过  强度：中");
        $('#newPasswordTip').css("color", "#008000");
        $('#newPasswordAgain').removeAttr('disabled');
    }
    else if (VerificationPassWordStrong(password) == 3 || VerificationPassWordStrong(password) == 4) {
        $('#newPasswordTip').text("通过  强度：强");
        $('#newPasswordTip').css("color", "#008000");
    }
    else {
        $('#newPasswordTip').text('通过');
        $('#newPasswordTip').css("color", "green");
        flag = true;
        $('#newPasswordAgain').removeAttr('disabled');
    }
    return flag;
}
// 重置密码页面检查重复密码是否有输事件
function checkNewPasswordAgain() {
    flag = true;
    $('#newPasswordAgainTip').text("");
    $('#newPasswordAgainTip').css("color", "#e74c3c");
    var password = $('#newPassword').val();
    var passwordAgain = $('#newPasswordAgain').val();
    if (passwordAgain == null || passwordAgain.length < 0 || passwordAgain == "") {
        flag = false;
    }
    else if (password != passwordAgain) {
        $('#newPasswordAgainTip').text("两次密码输入不一样");
        $('#newPasswordAgainTip').css("color", "#e74c3c");
        flag = false;
    }
    else {
        $('#newPasswordAgainTip').text('通过');
        $('#newPasswordAgainTip').css("color", "green");
    }
    return flag;
}

function resetPWDSubmit() {
    checkNewPassword();
    checkNewPasswordAgain();
    if (flag) {
        popStatus(3, '正在修改密码······', 2, null, null);
        var newPS = $('#newPassword').val();
        var againPS = $('#newPasswordAgain').val();
        $('#newPassword').val(fw.getMD5(newPS));
        $('#newPasswordAgain').val(fw.getMD5(againPS));
        $('#resetPasswordButton').text('正在提交');
        $('#resetPasswordButton').attr('disabled', 'disabled');
        $('#resetPasswordForm').submit();
    }
}


/*
 正则表达式验证
 */
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