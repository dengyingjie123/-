/**
 * Created by 邓超
 * Date 2015-5-26
 */

/**
 * 提交认证邮箱地址
 */
function emailSubmit() {
/*
    $('#emailSubmmitButton').val("正在提交");*/
    $('#emailSubmmitButton').attr('disabled', "disabled");
    var value=$('#address').val();
    if( value== '') {
        popStatus(4, '邮箱地址为空', 5);/*
        $('#emailSubmmitButton').val("下一步");*/
        $('#emailSubmmitButton').removeAttr('disabled');
        return ;
    }else if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value)){

        popStatus(4, '邮箱地址格式不正确', 5);/*
         $('#emailSubmmitButton').val("下一步");*/
        $('#emailSubmmitButton').removeAttr('disabled');
    } else {
        popStatus(3, '请您稍候', 3000);
        $('.form-horizontal').submit();
    }
}