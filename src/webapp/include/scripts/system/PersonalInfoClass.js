var PersonalInfoClass = function (token) {

    function initAll() {
        var loginUserId = loginUser.getId();


        var fromPersonalInfoId = "baseEditForm" + token;
        var url = WEB_ROOT + "/system/User_load.action?user.id=" + loginUserId;
        fw.post(url, null, function (data) {
            fw.formLoad(fromPersonalInfoId, data);
            $("#upassword" + token).val("");
            $("#upassword2" + token).val("");
        }, null);



        var fromPasswordId = "passwordEditForm" + token;
        var urlUpdate = WEB_ROOT + "/system/User_updateSelfPassword.action?user.id=" + loginUserId;
        fw.bindOnClick('btnPasswordEditSubmit' + token, function (process) {
            if(
                $("#upassword" + token).val()==""  ||
            $("#upassword2" + token).val("")==""){
                fw.alert('提示','请输入密码！');
                return;
            }
            fw.bindOnSubmitForm(fromPasswordId, urlUpdate, function () {
                process.beforeClick();
            }, function () {
                //alert('done');
                //修改成功清空文本框
                $("#theOldpassword" + token).val("");
                $("#upassword" + token).val("");
                $("#upassword2" + token).val("");
                if(token == null){
                   var a = fw.confirm('提示','密码修改成功！重新登录', function(){
                        window.location.href="login.jsp";
                    }, null);
                } else {
                    fw.alert('提示','密码修改成功！');
                    fw.windowClose("personalInfoWindow");
                }

                process.afterClick();
            }, function () {
                process.afterClick();
            });
        });
    }

    return {
        initModule: function () {
            // 初始化token
            return initAll();
        }
    };


};