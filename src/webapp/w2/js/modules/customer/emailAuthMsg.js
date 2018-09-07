/**
 * Created by 邓超
 * Date 2015-5-26
 */

/**
 * 更换邮箱
 */
function changeMail() {
    history.go(-1);
}

/**
 * 重新发送邮件
 */
function resend(email) {
    fweb.post('/core/w2/customer/emailValidateDo', {'address':email}, function(){}, function(){});
    popStatus(1, '邮件已发送成功', 5);
}

