var CKEditorClass = function (token) {

    var _ckeditor_id;
    var _callback_function = undefined;

    function initWindowCKEditorWindow(id, content) {
        _ckeditor_id = id;
        var url = WEB_ROOT + "/modules/system/CKEditor_Save.jsp?token=" + token;
        var windowId = "CKEditorWindow" + token;
        fw.window(windowId, '富文本编辑器', 780, 500, url, function () {
            fw.initCKEditor(id);
            onClickCKEditorSubmit();
        }, null);
    }

    function onClickCKEditorSubmit() {
        var buttonId = "btnCKEditorSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            // fw.debug('btnCKEditorSubmit');
            // initWindowOrderWindow({}, Action_Appointment);

            fw.getCKEditorData(_ckeditor_id, function(data) {
                if (fw.checkIsFunction(_callback_function)) {
                    _callback_function(data);
                }
            });

            fwCloseWindow('CKEditorWindow'+token);
        });
    }

    function setCallbackFunction(fun) {
        _callback_function = fun;
    }

    return {
        init:function(id, content, callback) {
            initWindowCKEditorWindow(id + token);
            setCallbackFunction(callback);
        }
    }
};