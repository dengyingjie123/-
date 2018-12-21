/**
 * Created by Lee on 10/21/2017.
 */
var timeoutSecond = 60;
function onClickGetMobileCode(btnId, formId) {

    console.log(btnId);
    console.log(formId);

    $$('#'+btnId).on('click',function(){

        if ($$('#'+btnId).hasClass('color-gray')) {
            return;
        }

        var formData = myApp.formToJSON('#'+formId);

        console.log(formData);

        var mobile = formData['mobile'];
        var _s = formData['_s'];

        if (!fm.checkIsMobile(mobile)) {
            fm.f7_alert(myApp, '请输入正确的手机号', null);
            return;
        }


        var url = WEB_ROOT + "/system/Token_getMobileCode?mobile="+mobile+"&_s="+_s;
        console.log(mobile);
        $$.post(url, "", function (data) {
            // console.log(data);
            data = fm.convert2Json(data);
            data = data['returnValue'];
            console.log(data);
            formData['checkCode'] = data['checkCode'];
            myApp.formFromJSON('#'+formId, formData);

            formData = myApp.formToJSON('#'+formId);

            console.log(formData);


            // $$(btnId).text('已发送（）');
            $$('#'+btnId).toggleClass('color-gray');
            var tt = setInterval(function(){
                // alert(timeoutSecond--);
                $$('#'+btnId).text('已发送（'+(timeoutSecond--)+'）');
                if (timeoutSecond < 0) {
                    window.clearInterval(tt);
                    $$('#'+btnId).toggleClass('color-gray');
                    timeoutSecond = 60;
                    $$('#'+btnId).text('获取验证码');
                }
            }, 1000);
        });


    });
}