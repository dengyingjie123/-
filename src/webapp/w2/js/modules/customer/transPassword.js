/**
 * Created by 邓超
 * Date 2015-5-26
 */

var isSend = false;
function getMobiAuthCode() {
    var url = '/core/w2/customer/getMobiAuthCode.action?mobile='+$('#mobile').val();
    fweb.post(url, null, function (data) {
        if (data.code == 100 && data.message == 'OK') {
            popStatus(1, '动态码已发送', 1, null, null);
            isSend = true;
        } else {
            isSend = false;
        }
    });
}

/**
 * 提交认证
 */
function validateSubmit4TPassWord() {


 // $('#transpasswordSubmmitButton').val("正在提交");
    $('#transpasswordSubmmitButton').attr('disabled', "disabled");

    var transPassword = $('#transPassword').val();
    var transPasswordAgain = $('#transPasswordAgain').val();
    //验证文本框是否填写完整
    if ( transPassword == '' || transPasswordAgain == '') {
        popStatus(4, '请将信息输入完整', 5);
      //
        $('#transpasswordSubmmitButton').removeAttr('disabled');
        return false;
    }
    //判断输入的值是否都为数字

    if(transPassword.length < 6 || transPassword.length < 6){
        popStatus(4, '请设置六位交易密码', 3);
      //
        $('#transpasswordSubmmitButton').removeAttr('disabled');
        return false;
    }

    if (transPassword != transPasswordAgain) {
        popStatus(4, '请检查两次密码是否一致', 3);
      //
        $('#transpasswordSubmmitButton').removeAttr('disabled');
        return fasle;
    }
    if (!/^[0-9]*$/.test(transPassword)) {

        popStatus(4, '交易密码只能是数字', 3);
      //
        $('#transpasswordSubmmitButton').removeAttr('disabled');
        return false;
    }
    //var oldTransPassword = document.getElementById("oldTransPassword");
    //if (oldTransPassword != null) {
    //    if (oldTransPassword.value == '') {
    //        popStatus(4, '请输入旧的交易密码', 3);
    //      //
    //        $('#transpasswordSubmmitButton').removeAttr('disabled');
    //        return false;
    //    }
    //}
    popStatus(3, '请您稍候', 3000);
    $('#updatepassword').submit();
}
//判断是否都为数字
function VerificationFirst(id,str) {
    if(""== str || null == str){
        $('#'+id).text('交易密码只能是6位数字');
        return  false;
    }
     
    if (/^[0-9]*$/.test(str)) {
        $('#'+id).text('');
        return true;
    }
    $('#'+id).text('交易密码只能是6位数字');
  //  
    $('#transpasswordSubmmitButton').removeAttr('disabled');
    return false;
}
/*检查两次密码是否相同*/
function isDoublePassword(){

    var transPassword = $('#transPassword').val();
    var transPasswordAgain = $('#transPasswordAgain').val();
    if (transPassword != transPasswordAgain) {
        popStatus(4, '请检查两次密码是否一致', 3);
      //  
        $('#transpasswordSubmmitButton').removeAttr('disabled');
        return;
    }
}

//跳转到首页
function gotoHome(){
    window.location=''
}