var fm = (function () {

    function dialog(id, title, message, buttons) {

        if (!fm.checkIsNullObject(buttons)) {
            var btn = {};
            $(buttons).each(function(){
                var text = this['text'];
                var callback = this['callback'];
                fw.jsonJoin(btn, {'text': text}, true);
                fw.jsonJoin(btn, {'callback': callback}, true);

                buttons.push(btn);
            });
        }

    }

    function dialog(id, title, message, buttonText1, buttonText1Callback, buttonText2, buttonText2Callback) {
        id = fm.getObjectFromId(id);

        var btn1Id = 'btn1' + id.substr(1);
        var btn2Id = 'btn2' + id.substr(1);

        $(id).width('80%');
        //$(id).height('300px');
        // $(id).attr('style', 'font-size:15px');
        $(id).html('<br><div style="font-size: 15px; text-align: center">'+message+'</div><br>');


        var buttons = [];
        if (!fm.checkIsNullObject(buttonText1)) {
            var btn1 = {
                id: btn1Id,
                text: buttonText1,
                handler: function () {
                    if (fm.checkIsFunction(buttonText1Callback)) {
                        buttonText1Callback();
                    }
                }
            };

            buttons.push(btn1);
        }

        if (!fm.checkIsNullObject(buttonText2)) {
            var btn2 = {
                id: btn2Id,
                text: buttonText2,
                handler: function () {
                    if (fm.checkIsFunction(buttonText2Callback)) {
                        buttonText2Callback();
                    }
                }
            };

            buttons.push(btn2);
        }

        $(id).dialog({
            title: title,
            closed: false,
            cache: false,
            modal: true,
            buttons:buttons
        });

        var percent = '90%';

        if (buttons.length > 1) {
            percent = '45%';
        }

        $('#' + btn1Id).width(percent);
        $('#' + btn1Id).height('35px');
        $('#' + btn1Id).css({'textAlign':'center'});
        $('#' + btn1Id).linkbutton({size: 'large'});
        $('#'+btn1Id+' span span').attr('style', 'font-size:15px');

        $('#' + btn2Id).width(percent);
        $('#' + btn2Id).height('35px');
        //$('#' + btn2Id).height('0.8rem');
        $('#' + btn2Id).css({'margin':'5px','textAlign':'center'});
        $('#' + btn2Id).linkbutton({size: 'large'});
        $('#'+btn2Id+' span span').attr('style', 'font-size:15px');
    }

    return {
        alert: function (title, message) {
            fm.confirm(title, message, null);
        },
        bindOnSubmitForm: function (formId, url, onSubmit, success, error) {

            $("#" + formId).form('submit', {
                url: url,
                onSubmit: function () {
                    if (!$(this).form('validate')) {
                        return false;
                    }
                    else {
                        if (fm.checkIsFunction(onSubmit)) {

                            onSubmit();
                        }
                    }
                },
                success: function (data) {
                    try {
                        //fw.alertReturnValue(data);
                        fm.dealReturnObject(data);

                        if (fm.checkIsFunction(success)) {
                            success();
                        }
                    }
                    catch (e) {
                        if (fm.checkIsFunction(error)) {
                            error();
                        }
                    }
                }
            });
        },
        checkIsFunction: function (functionName) {
            //alert('fw.checkIsFunction(): ' + typeof(functionName));
            if (typeof(functionName) == 'function') {
                return true;
            }
            return false;
        },
        checkIsMobile:function(mobile){
            if (/^(13|14|15|17|18)\d{9}$/.test(mobile)) {
                return true;
            }
            return false;
        },
        checkIsNullObject: function (obj) {
            if (obj != null && obj != "undefined") {
                return false;
            }
            return true;
        },
        formSetValue:function(formId, name, value){
            $('#' + formId + ' input[name='+name+']').val(value);
        },
        urlEncode:function(text){
            return encodeURIComponent(text);
        },
        checkIsTextEmpty: function (text) {
            var value = $.trim(text);
            if (value != null && value != "undefined" && value != "") {
                return false;
            }
            return true;
        },
        checkIsTextNumber: function (text) {
            if (!fm.checkIsTextEmpty(text)) {
                return /^\d+(\.\d+)?$/.test(text);
            }
            return false;
        },
        replaceAll:function(text, replace, replaceTo) {
            if (fm.checkIsTextEmpty(text)) {
                return text;
            }

            if (text.indexOf(replace) != -1) {
                return fm.replaceAll(text, replace, replaceTo);
            }

            return text;
        },
        contains:function(text, contain) {

            if (!fm.checkIsTextEmpty(text) && text.indexOf(contain) != -1) {
                return true;
            }

            return false;
        },
        checkIsBankNumber:function(text){


            if (!fm.checkIsTextNumber(text)) {
                return false;
            }

            /*
            if (text.length != 16 && text.length != 19) {
                return false;
            }
            */

            return true;
        },
        confirm:function(title, message, fn){
            fm.message(title, message, fn);
        },
        convert2Json: function (text) {
            try {
                //alert("t1:"+text);
                // 如果本身就是json对象，则直接返回
                if (typeof(text) == "object") {
                    return text;
                }

                if (fm.checkIsTextEmpty(text)) {
                    return {};
                }

                var json = eval("(" + text + ")");
                return json;
            }
            catch (e) {
                alert("fw.convert2Json()异常:Text【" + text + "】" + e);
                return {};
            }
        },
        buildJSON:function(key, value, json){
            if (fm.checkIsNullObject(json)) {
                json = {};
            }
            json[key] = value;
            return json;
        },
        getFormValueToJSON:function(formId, jsonName, json) {
            var data = fm.getFormValueById(formId);
            if (fm.checkIsNullObject(json)) {
                json = {};
            }
            json = fm.buildJSON(jsonName, data, json);
            return json;
        },
        convert2String: function(json){
            if (!fm.checkIsNullObject(json)) {
                return JSON.stringify(json);
            }
            return  "";
        },
        dialogWithYesNo:function(id, title, content, yesCallback) {
            dialog(id, title, content, '是', yesCallback, '否', function() {
                fm.dialogClose(id);
            });
        },
        message:function(title, content, fn) {
            var id = "_message_id";
            if ($("#"+id).length == 0) {
                $("body").append("<div id=\""+id+"\"></div>");
            }
            dialog(id, title, content, null, null, '确定', function() {
                if (fm.checkIsFunction(fn)) {
                    fn();
                }
                fm.dialogClose(id);
            });
        },
        dealReturnObject: function (data) {
            //fw.alertReturnValue(data);
            data = fm.convert2Json(data);
            // TODO 判断data的值是否合法
            if (fm.checkIsNullObject(data)) {

            }
            else {
                if (data.code == 100) {
                    //fw.showMessage('成功', data.message);
                    return fm.convert2Json(data.returnValue);
                }
                else if (data.code == 200) {
                    fm.alert('失败', data.message);
                    throw new Error("数据库无法连接");
                }
                else if (data.code == 201) {
                    //fw.alert('失败','请重新登录');
                    fm.confirm('提示', '请重新登录', function () {
                        //alert(WEB_ROOT + "/login.jsp");
                        window.location = WEB_ROOT + "/login.jsp";
                    }, function () {
                    });
                    //throw new Error("数据库无法连接");
                }
                else if (data.code == 6855) {
                    fm.alert('失败', data.message);
                    // throw new Error("没有系统邮箱，请与管理员联系。");
                }
                else if (data.code == 202) {
                    fm.alert('失败', data.message);
                    // throw new Error("没有操作权限，请与管理员联系。");
                }
                else if (data.code == 301) {
                    fm.alert('失败', '数据库事务异常，请检查数据是否正确');
                    // throw new Error("数据库事务异常，请检查数据是否正确");
                }
                else if (data.code == 32537) {
                    fm.alert('失败', '请选择归属为是！');
                    // throw new Error("数据库事务异常，请检查数据是否正确");
                }
                //2015 -6 -17 周海鸿
                // 获取用户默认部门 该用户没有默认部门异常处理
                else if (data.code == 2015617) {
                    fm.alert('失败', '该用户没有默认部门');
                    //  throw new Error("检查用户数据，该用户没有默认部门");
                }
                else {
                    fm.alert('失败', data.message);
                    throw new Error("错误：" + data.message);
                }
            }
        },
        dialogClose:function(id){
            id = fm.getObjectFromId(id);
            $(id).dialog('close');
        },
        getObjectFromId:function(id) {
            if (typeof id == 'object') {
                return id;
            }

            if (typeof id == 'string' && id.indexOf('#') != -1) {
                return id;
            }

            if (id.indexOf('#') == -1) {
                id = '#' + id;
            }
            //alert(id);
            return id;
        },
        formatMoney:function(m){
            return accounting.formatMoney(m, "", 2);
        },
        formatMoneys:function(m){
            return m + "元";
        },
        getFormValueByName:function(name){
            return $("input[name='"+name+"']").val();
        },
        getFormValueById:function(id){
            return $("#"+id).val();
        },
        getMD5: function (text) {
            return fnGetMD5(text);
        },
        goto:function(url){
            window.location = url;
        },
        post: function (url, params, success, error) {
            $.ajax({
                type: "POST",
                url: url,
                processData: true,
                data: params,
                dataType: 'text',
                success: function (data) {
                    // alert(JSON.stringify(data));
                    try {
                        var d = fm.dealReturnObject(data);

                        if (fm.checkIsFunction(success)) {
                            success(d);
                        }
                    }
                    catch (e) {
                        // alert(e);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var message = textStatus + " " + errorThrown;
                    alert(message);
                    if (fm.checkIsFunction(error)) {
                        error(message);
                    }
                }
            });
        },
        urlEncode:function(url){
            if (!fm.checkIsTextEmpty(url)) {
                return encodeURIComponent(url);
            }
            return null;
        },
        urlDecode:function (text) {
            if (!fm.checkIsTextEmpty(text)) {
                return decodeURIComponent(text);
            }
            return null;
        },
        jsonJoin: function (des, src, override) {
            if (src instanceof Array) {
                for (var i = 0, len = src.length; i < len; i++)
                    fw.jsonJoin(des, src[i], override);
            }
            for (var i in src) {
                if (override || !(i in des)) {
                    des[i] = src[i];
                }
            }
            return des;
        },
        log:function(id, log){
            console.log(id);
            console.log(log);
        },
        getParametersFormUrl:function(url){
            var index = url.indexOf("?");

            if (index != -1) {
                index++;
                var parameters = url.substr(index);
                return parameters;
            }

            return '';
        },
        getParametersJsonFormUrl:function(url){
            var index = url.indexOf("?");

            if (index != -1) {
                index++;
                var parameters = url.substr(index);


                var json = {};

                if (!fm.checkIsTextEmpty(parameters)) {
                    var psArray = parameters.split("&");

                    for (i = 0; i < psArray.length; i++) {
                        var ps = psArray[i].split("=");

                        fm.buildJSON(ps[0], ps[1], json);
                    }

                    return json;
                }

                return {};

            }

            return {};
        },
        checkIsAndroid:function() {
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1;
            return isAndroid;
        },
        checkIsiOS:function() {
            var u = navigator.userAgent;
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
            return isiOS;
        },
        checkIsWX:function() {
            var u = navigator.userAgent;
            var isWX = u.indexOf('MicroMessenger') > -1;
            return isWX;
        },
        f7_alert:function(app, message, callback){
            if (fm.checkIsFunction(callback)) {
                app.alert(message, callback);
            }
            else {
                app.alert(message);
            }
        },
        f7_confirm:function(app, message, callback){
            if (fm.checkIsFunction(callback)) {
                app.confirm(message, callback);
            }
            else {
                app.confirm(message, null);
            }
        },
        f7_bind:function(btnId, onClick){
            $$(btnId).on('click', onClick);
        },
        f7_getCurrentView:function(){
            return $$('.view.active')[0].f7View;
        },
        f7_bindWidthClass:function(className, onClick){
            $$('.' + className).on('click', onClick);
        },
        f7_url:function(url) {
            if (!fm.checkIsTextEmpty(url)) {
                if (fm.contains(url, "?")) {
                     return url + "&r=" + Math.random();
                }
                return url + "?r=" + Math.random();
            }
            return "";
        },
        f7_post:function(app, url, data, success, error){
            $$.post(url, data, function(data, status, xhr){

                console.log(data);
                data = fm.convert2Json(data);

                if (data['code'] != 100) {
                    var message = "未知异常，请稍后再试";
                    if (!fm.checkIsTextEmpty(data['message'])) {
                        message = data['message'];
                    }

                    fm.f7_alert(app, message);
                    return;
                }

                if (fm.checkIsFunction(success)) {
                    success(data);
                }
            }, function(xhr, status){
                if (fm.checkIsFunction(error)) {
                    error();
                }
            });
        },
        f7_getFormToJSON:function(app, formId){
            return app.formToJSON('#'+formId);
        },
        f7_getJSON:function(url, data, success, error){
            $$.getJSON(url, data, success, error);
        }
    };
})
();