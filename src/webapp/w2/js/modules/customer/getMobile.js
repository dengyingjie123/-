/**
 * Created by 张舜清 on 7/23/2015.
 */
var url = "/core/w2/customer/getMobile";
fweb.post(url, null, function (data) {
//    fw.alertReturnValue(data);
    var data = data.returnValue;
    if(data != null){
        $('#mobile').val(data);
    }
    else{
        $('#mobile').val("手机号码未验证");
    }
}, null);
