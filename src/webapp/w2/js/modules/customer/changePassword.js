/**
 * Created by 邓超
 * Date 2015-5-30
 */

/**
 * 提交认证邮箱地址
 */
function nextNoteSubmit() {


    $('#nextNote').val("正在提交");
    $('#nextNote').attr('disabled', "disabled");

    var oldPassword = $('#oldPassword').val();

    var newPassword = $('#newPassword').val();
    var newPasswordAgain = $('#newPasswordAgain').val();
    if(oldPassword == '' || newPassword == '' || newPasswordAgain == '') {
        popStatus(4, '请将信息输入完整', 5);
        $('#nextNote').val("下一步");
        $('#nextNote').removeAttr('disabled');
        return ;
    }
    if(newPassword != newPasswordAgain) {
        popStatus(4, '请检查两次密码是否一致', 5);
        $('#nextNote').val("下一步");
        $('#nextNote').removeAttr('disabled');
        return ;
    }
    popStatus(3, '请您稍候', 3000);
    $('#oldPassword').val(fnGetMD5(oldPassword));
    $('#newPassword').val(fnGetMD5(newPassword));
    $('#newPasswordAgain').val(fnGetMD5(newPasswordAgain));
    $('.form-horizontal').submit();
}
