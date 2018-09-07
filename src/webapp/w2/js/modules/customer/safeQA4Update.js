/**
 * Created by 邓超
 * Date 2015-5-26
 */


// 是否已发送短信
var isSend = false;

/**
 * 发送手机动态码
 */
function sendMobiCode() {
    fweb.post('/core/w2/customer/getMobiAuthCode', {}, function(){
        popStatus(1, '动态码发送成功', 5);
       // $('#QuestionSubmmitButton').val("下一步");
        $('#QuestionSubmmitButton').removeAttr('disabled');
        isSend = true;
        return;
    }, function(){
        popStatus(4, '系统繁忙，请稍后再试', 5);
        //$('#QuestionSubmmitButton').val("下一步");
        $('#QuestionSubmmitButton').removeAttr('disabled');
        isSend = false;
        return;
    });
}

/**
 * 提交认证邮箱地址
 */
function questionSubmit() {

   // $('#QuestionSubmmitButton').val("正在提交");
    $('#QuestionSubmmitButton').attr('disabled', "disabled");
    if(!isSend) {
        popStatus(4, '请先发送手机动态码', 5);
      //  $('#QuestionSubmmitButton').val("下一步");
        $('#QuestionSubmmitButton').removeAttr('disabled');
        return;
    }
    if ($('#mobiValidateCode').val() == '') {
        popStatus(4, '请输入发送到您手机上的动态码', 5);
      //  $('#QuestionSubmmitButton').val("下一步");
        $('#QuestionSubmmitButton').removeAttr('disabled');
        return;
    }
    if($('#answer1').val() == '' || $('#answer2').val() == '' || $('#answer3').val() == '') {
        popStatus(4, '请将答案输入完整', 5);
       // $('#QuestionSubmmitButton').val("下一步");
        $('#QuestionSubmmitButton').removeAttr('disabled');
        return;
    }
    popStatus(3, '请您稍候', 3000);
    $('#protectionQuestionForm').submit();
}