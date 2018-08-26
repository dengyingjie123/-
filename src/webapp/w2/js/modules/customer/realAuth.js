/**
 * Created by 邓超
 * Date 2015-5-26
 */

/**
 * 提交实名认证
 */
function cardSubmit() {


   //// $('#cardSubmmitButton').val("正在提交");
    $('#cardSubmmitButton').attr('disabled', "disabled");
    if($('#realName').val() == '' || $('#realId').val() == '') {
        popStatus(4, '请检查格式是否正确或输入项是否为空', 5);
       // $('#cardSubmmitButton').val("下一步");
        $('#cardSubmmitButton').removeAttr('disabled');
        return ;
    } else {

        //验证身分证号码
        var bankNumber = $('#realId').val();
        var validateCard=validateIdCard(bankNumber)
        if (validateCard) {

            popStatus(3, '请您稍候', 3000);
            $('#realfrom').submit();
        }else
        {
            popStatus(4, '身份证号码输入有误，请您检查后再提交！', 5);
            $('#cardSubmmitButton').removeAttr('disabled');
            return ;
        }
    }
}



