/**
 * 框架工具类
 * see:  http://c.youngbook.net:96/display/SHEL/frameworkplus
 */
var fw = (function () {

    var debug_mode = false;
    var isDebug = 1;

    /**
     * 关闭窗口
     * @param windowId 窗口id
     */
    function windowClose(windowId) {
        var obj = fw.getObjectFromId(windowId);
        $(obj).window('close');
        $(obj).remove();
    }

    function setFormValueText(formId, value, defaultValue) {

        if (debug_mode) {
            if (fw.checkIsNullObject(value)) {
                alert("Form [" + formId + "] value:[" + value + "] error.");
            }
        }

        var setValue = defaultValue;
        if (!fw.checkIsTextEmpty(value)) {
            setValue = value;
        }
        //alert("Form " + $("#"+formId).length);
        //$("#"+formId).attr("value",setValue);
        $("#" + formId).val(setValue);
    }

    function setFormValueNumberBox(formId, value, defaultValue) {

        if (debug_mode) {
            if (fw.checkIsNullObject(value)) {
                alert("Form [" + formId + "] value:[" + value + "] error.");
            }
        }

        if (!fw.checkIsTextNumber(defaultValue)) {
            alert("Form [" + formId + "]'s default is not a NUMBER!");
        }
        var setValue = defaultValue;
        if (!fw.checkIsTextEmpty(value) && fw.checkIsTextNumber(value)) {
            setValue = value;
        }
        //alert(setValue);
        $('#' + formId).numberbox("setValue", setValue);
    }

    /**
     * 从KV中构建combotree
     * @param id
     * @param groupName
     * @param orderBy
     * @param selectedId
     * @param isWithCheck 是否需要checkbox
     */
    function getComboTreeFromKV(id, groupName, orderBy, selectedId, isWithCheck) {
        id = fw.getObjectFromId(id);

        if (fw.checkIsNullObject(selectedId)) {
            selectedId = '-2';
        }
        if (typeof orderBy == 'undefined' || orderBy == 'undefined') {
            orderBy = '';
        }
        var url = WEB_ROOT + "/system/KV_listAll4Tree.action?kv.GroupName=" + groupName + "&OrderBy=" + orderBy;
        //alert(url);
        fw.combotreeOnload(id, url, function (data) {
            // alert(JSON.stringify(data));
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                // fw.alertReturnValue(data.rows);
                // treeData = data.rows;
                treeData = data.rows;
            }
            catch (e) {
                alert(e);
            }
            return treeData;
        }, selectedId);

        if (isWithCheck) {
            $(id).combotree({
                multiple: true,
                separator: ",",
                onCheck: function () {
                    var text = $(id).combo('getText');
                    //alert(text.length);
                    var size = (10 * text.length) > 200 ? (10 * text.length + 30) : 180;
                    $(id).combo('resize', size);
                }
            });
        }

    }


//取得cookie
    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');    //把cookie分割成组
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];                      //取得字符串
            while (c.charAt(0) == ' ') {          //判断一下字符串有没有前导空格
                c = c.substring(1, c.length);      //有的话，从第二位开始取
            }
            if (c.indexOf(nameEQ) == 0) {       //如果含有我们要的name
                return unescape(c.substring(nameEQ.length, c.length));    //解码并截取我们要值
            }
        }
        return false;
    }

    function setFormDateBox(formId, value, defaultValue) {
        //alert("form:["+formId+"] value:["+value+"] default:["+defaultValue+"]");

        if (debug_mode) {
            if (fw.checkIsNullObject(value)) {
                alert("Form [" + formId + "] value:[" + value + "] error.");
            }
        }

        var setValue = "";
        if (fw.checkIsTextEmpty(defaultValue)) {
            if (fw.type_date_now == defaultValue) {
                var now = new Date();
                setValue = getDateText(now);
            }
            else {
                setValue = defaultValue;
            }
        }

        if (!fw.checkIsTextEmpty(value)) {
            if (value == '1900-01-01') {
                setValue = "";
            }
            else {
                setValue = value;
            }

        }


        if (!fw.checkIsTextEmpty(setValue) && !fw.checkIsTextEmpty(formId)) {
            $('#' + formId).datebox('setValue', setValue);
            $('#' + formId).combo('setText', setValue);
        }

        if (fw.checkIsTextEmpty(setValue)) {
            $('#' + formId).combo('clear');
        }
    }

    function setFormDateTimeBox(formId, value, defaultValue) {
        //alert("form:["+formId+"] value:["+value+"] default:["+defaultValue+"]");

        if (debug_mode) {
            if (fw.checkIsNullObject(value)) {
                alert("Form [" + formId + "] value:[" + value + "] error.");
            }
        }

        var setValue = "";
        if (!fw.checkIsTextEmpty(defaultValue)) {
            if (fw.type_datetime_now == defaultValue) {
                var now = new Date();
                setValue = getDateTimeText(now);
            }
            else {
                setValue = defaultValue;
            }
        }

        if (!fw.checkIsTextEmpty(value)) {
            if (value == "1900-01-01 00:00:00") {
                setValue = "";
            }
            else {
                setValue = value;
            }

        }

        if (!fw.checkIsTextEmpty(setValue) && !fw.checkIsTextEmpty(formId)) {
            $('#' + formId).datetimebox('setValue', setValue);
            $('#' + formId).combo('setText', setValue);
        }

        if (fw.checkIsTextEmpty(setValue)) {
            $('#' + formId).combo('clear');
        }
    }

    function setFormValueComboTree(formId, value, defaultValue) {

        if (debug_mode) {
            if (fw.checkIsNullObject(value)) {
                alert("Form [" + formId + "] value:[" + value + "] error.");
            }
        }

        if (!fw.checkIsNullObject(value) && !fw.checkIsNullObject(value.length) && value.length == 2) {
            var setValue1 = defaultValue[0];
            var setValue2 = defaultValue[1];

            if (!fw.checkIsTextEmpty(value[0])) {
                setValue1 = value[0];
            }

            if (!fw.checkIsTextEmpty(value[1])) {
                setValue2 = value[1];
            }

            if (debug_mode) {
                alert("fw.setFromValueComboTree(): value1:[" + setValue1 + "]");
                alert("fw.setFromValueComboTree(): value2:[" + setValue2 + "]");
            }

            if (!fw.checkIsTextEmpty(formId) && !fw.checkIsTextEmpty(setValue1)) {
                $('#' + formId).combotree('setValue', setValue1);
                /*
                 var tree = $('#'+formId).combotree('tree');
                 var node = $(tree).tree('find', setValue1);
                 if (!fw.checkIsNullObject(node) && node.length>0 ) {
                 $('#'+formId).combotree('setValue',setValue1);
                 }
                 else {
                 $('#'+formId).combotree('clear');
                 }
                 */
            }

            if (!fw.checkIsTextEmpty(formId) && !fw.checkIsTextEmpty(setValue2)) {
                $('#' + formId).combotree('setText', setValue2);
            }

            if (fw.checkIsTextEmpty(setValue1) && fw.checkIsTextEmpty(setValue2)) {
                $('#' + formId).combotree('clear');
            }
        }
        else {
            alert("Form [" + formId + "]'s values must be an Array");
        }
    }


    function getDateTimeText(date) {
        var Year = 0;
        var Month = 0;
        var Day = 0;
        var Hour = 0;
        var Minute = 0;
        var Second = 0;
        var CurrentDate = "";

        //初始化时间
        Year = date.getFullYear();
        Month = date.getMonth() + 1;
        Day = date.getDate();
        Hour = date.getHours();
        Minute = date.getMinutes();
        Second = date.getSeconds();


        CurrentDate = Year + "-";
        if (Month >= 10) {
            CurrentDate = CurrentDate + Month + "-";
        }
        else {
            CurrentDate = CurrentDate + "0" + Month + "-";
        }
        if (Day >= 10) {
            CurrentDate = CurrentDate + Day;
        }
        else {
            CurrentDate = CurrentDate + "0" + Day;
        }

        if (Hour >= 10) {
            CurrentDate = CurrentDate + " " + Hour;
        }
        else {
            CurrentDate = CurrentDate + " 0" + Hour;
        }
        if (Minute >= 10) {
            CurrentDate = CurrentDate + ":" + Minute;
        }
        else {
            CurrentDate = CurrentDate + ":0" + Minute;
        }

        if (Second >= 10) {
            CurrentDate = CurrentDate + ":" + Second;
        }
        else {
            CurrentDate = CurrentDate + ":0" + Second;
        }
        return CurrentDate;
    }

    function getRandomNumber() {
        var today = new Date();
        var seed = today.getTime();
        seed = (seed * 9301 + 49297) % 233280;
        seed = seed / (233280.0);
        var number = seed * 5 + " ";
        number = number.replace(".", "").replace(" ", "");
        return number;
    }

    function getDateText(date) {
        var Year = 0;
        var Month = 0;
        var Day = 0;
        var CurrentDate = "";

        //初始化时间
        Year = date.getFullYear();
        Month = date.getMonth() + 1;
        Day = date.getDate();


        CurrentDate = Year + "-";
        if (Month >= 10) {
            CurrentDate = CurrentDate + Month + "-";
        }
        else {
            CurrentDate = CurrentDate + "0" + Month + "-";
        }
        if (Day >= 10) {
            CurrentDate = CurrentDate + Day;
        }
        else {
            CurrentDate = CurrentDate + "0" + Day;
        }
        return CurrentDate;
    }

    return {
        type_form_text: "type_form_text",
        type_form_hidden: "type_form_hidden",
        type_form_numberbox: "type_form_numberbox",
        type_form_combotree: "type_form_combotree",
        type_form_combobox: "type_form_combobox",
        type_form_datebox: "type_form_datebox",
        type_form_datetimebox: "type_form_datetimebox",

        type_date_now: "type_date_now",
        type_datetime_now: "type_datetime_now",

        type_get_text: "type_get_text",
        type_get_value: "type_get_value",
        color_green: "#8FDAB5",
        color_red: "#FFC1C1",
        color_gray: "#C8C8C8",
        color_blue: "#A8C6ED",


        initCKEditor: function (elementName) {
            using(SCRIPTS_ROOT + '/ckeditor/4.4.3/ckeditor.js', function () {
                CKEDITOR.replace(elementName);
            });
        },
        getCKEditorData: function (elementName, callback) {
            using(SCRIPTS_ROOT + '/ckeditor/4.4.3/ckeditor.js', function () {
                var data = CKEDITOR.instances[elementName].getData();

                if (fw.checkIsFunction(callback)) {
                    callback(data);
                }
            });
        },
        /**
         * 格式化input文本框，使之显示金额类型
         *
         * 千分位逗号分割
         * @param id
         *
         * @author leevits
         * 时间：2015年6月24日 15:08:04
         */
        textFormatCurrency: function (id) {
            id = fw.getObjectFromId(id);
            using('../extensions/jquery.formatCurrency.js', function () {
                $(id).formatCurrency();
                $(id).change(function () {
                    $(id).formatCurrency();
                })
            });
        },
        /**
         * 格式化input文本框，使显示金额类型转为浮点型
         *
         * 传入文本框id
         * @param id
         *
         * @author 姚章鹏
         * 时间：2015年6月24日 16:08:04
         *
         * 修改人：周海鸿
         * 修改时间：2015-7-7
         * 内容：判断根据id获取的值是否为有值
         */
        CurrencyFormatText: function (id) {
            id = fw.getObjectFromId(id);
            var sizestring = $(id).val();
            if (sizestring == "" || sizestring == null) {
                return;
            }
            var money = sizestring.replace(/,/g, '');
            $(id).val(parseFloat(money));
        },
        /**
         * 去除金额文本框的格式 返回值。
         *
         * 调用 fw.getCurrencyFormatValue(id);
         *
         * 修改人：周海鸿
         * 修改时间:2015-7-1
         *
         * @param id 文本框ID
         * @returns {XML|string|void|*}
         */
        getCurrencyFormatValue: function (id) {
            id = fw.getObjectFromId(id);
            var sizestring = $(id).val();
            return sizestring.replace(/,/g, '');
        },

        addMenuButton: function (id, name, url, permission, token) {
            // 添加空格
            var length = (6 - name.length) * 2;
            for (i = 0; i < length; i++) {
                name = "&nbsp;" + name + "&nbsp;";
            }
            var html = "<p><a id='" + id + "' href=\"javascript:void(0);\" class=\"menuButton\">" + name + "</a></p>";

            $('#menubar').append(html);

            $('#' + id).bind('click', function () {
                loadWorkSpace(url, name, permission, '', token);
            });
        },
        alert: function (title, message) {
            $.messager.alert(title, message);
        },
        debugFormValue:function(formId){
            formId = fw.getObjectFromId(formId);
            var formValue = $(formId).serialize();
            fw.alert('formValue', formValue);
        },
        getObjectStartWith: function (idValue) {
            return $("[id^='" + idValue + "']");
        },


        treeDataConvert: function (rows) {
            function exists(rows, parentId) {
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].id == parentId) return true;
                }
                return false;
            }

            var nodes = [];
            // get the top level nodes
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                if (!fw.checkIsNullObject(row.attributes.parentId) && !exists(rows, row.attributes.parentId)) {
                    nodes.push({
                        id: row.id,
                        text: row.text,
                        iconCls: row.iconCls,
                        attributes: {
                            url: row.attributes.url,
                            moduleName: row.attributes.moduleName,
                            permissionName: row.attributes.permissionName
                        }
                    });
                }
            }

            var toDo = [];
            for (var i = 0; i < nodes.length; i++) {
                toDo.push(nodes[i]);
            }
            while (toDo.length) {
                var node = toDo.shift();    // the parent node
                // get the children nodes
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    if (!fw.checkIsNullObject(row.attributes.parentId) &&
                        row.attributes.parentId == node.id) {
                        var child = {
                            id: row.id,
                            text: row.text,
                            iconCls: row.iconCls,
                            attributes: {
                                url: row.attributes.url,
                                moduleName: row.attributes.moduleName,
                                permissionName: row.attributes.permissionName
                            }
                        };
                        if (node.children) {
                            node.children.push(child);
                        } else {
                            node.children = [child];
                        }
                        toDo.push(child);
                    }
                }
            }
            return nodes;
        },


        setFormValue: function (formId, value, type, defaultValue) {
            /**
             * 测试开始
             */
            //alert("form:["+formId+"] value:["+value+"] type:["+type+"] default:["+defaultValue+"]");


            if (fw.checkIsTextEmpty(formId) || $('#' + formId).length == 0) {
                if (debug_mode) {
                    alert("FW.setFormValue错误，formId:[" + formId + "]");
                    return null;
                }
            }

            if (fw.checkIsTextEmpty(type)) {
                alert("FW.setFormValue错误，type:[" + type + "]");
                return null;
            }

            if (fw.checkIsNullObject(defaultValue)) {
                alert("FW.setFormValue错误，defaultValue:[" + defaultValue + "]");
                return null;
            }

            if (fw.type_form_text == type || fw.type_form_hidden == type) {
                setFormValueText(formId, value, defaultValue);
            }
            else if (fw.type_form_numberbox == type) {
                setFormValueNumberBox(formId, value, defaultValue);
            }
            else if (fw.type_form_combotree == type) {
                setFormValueComboTree(formId, value, defaultValue);
            }
            else if (fw.type_form_datebox == type) {
                setFormDateBox(formId, value, defaultValue);
            }
            else if (fw.type_form_datetimebox == type) {
                setFormDateTimeBox(formId, value, defaultValue);
            }

        },
        getFormValue: function (formId, formType, getType) {
            formId = fw.getObjectFromId(formId);
            var textValue = "";
            var obj = $(formId);
            if (fw.checkIsNullObject(obj) || obj.length == 0) {
                if (debug_mode) {
                    alert("Error: form [" + formId + "] is null");
                }
                return null;
            }

            if (fw.type_form_text == formType) {
                textValue = $(formId).attr('value');
            }
            else if (fw.type_form_combotree == formType) {
                if (fw.type_get_value == getType) {
                    textValue = $(formId).combotree("getValue");
                }
                else if (fw.type_get_text == getType) {
                    textValue = $(formId).combotree("getText");
                }
            }
            else if (fw.type_form_datebox == formType) {
                //alert("Data:" + $( "#"+formId ).combotree("getValue"));
                if ($(formId).combotree("getValue") == '1900-01-01') {
                    textValue = "";
                }
                else {
                    textValue = $(formId).combotree("getValue");
                }
            }
            else if (fw.type_form_numberbox == formType) {
                textValue = $(formId).numberbox("getValue");
            }
            else if (fw.type_form_combobox == formType) {
                textValue = $(formId).combobox("getValue");
            }
            else if (fw.type_form_datetimebox == formType) {
                textValue = $(formId).datetimebox("getValue");
            }

            // SQL敏感词过滤
            textValue = fw.sqlReplace(textValue);
            return textValue;
        },

        getRandomNumber: function () {
            return getRandomNumber();
        },

        getFloat: function (text) {
            if (fw.checkIsTextEmpty(text)) {
                return 0.00;
            }
            else {
                var f = 0.00;
                try {
                    f = parseFloat(text);
                    return f;
                }
                catch (e) {
                    return f;
                }
            }
        },
        /**
         * 获得JSON对象的属性值
         * @param obj JSON对象
         * @param memverName 属性名称
         * @returns {*}
         */
        getMemberValue: function (obj, memverName) {
            if (!fw.checkIsNullObject(obj) && !fw.checkIsNullObject(obj[memverName])) {
                return obj[memverName];
            }
            return null;
        },
        checkIsJsonEmpty: function (json) {
            return $.isEmptyObject(json);
        },
        checkIsJsonObject:function (obj) {
            var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
            return isjson;
        },
        // 判断text是否为空，text取值为null, ""或者undefined，都将认为是空
        checkIsTextEmpty: function (text) {
            var value = $.trim(text);
            if (value != null && value != "undefined" && value != "") {
                return false;
            }
            return true;
        },
        checkIsTextNumber: function (text) {
            if (!fw.checkIsTextEmpty(text)) {
                return /^\d+(\.\d+)?$/.test(text);
            }
            return false;
        },
        checkIsTextNumberBiggerThenZero: function (text) {
            if (!fw.checkIsTextEmpty(text)) {
                return /^\d+(\.\d+)?$/.test(text) && parseFloat(text) > 0;
            }
            return false;
        },
        /**
         * 判断是否可以编辑或不可以显示
         * @param id
         * @returns 0 可以编辑   1 不可以编辑 2 不显示 {boolean}
         */
        checkWfEditable: function (id) {
            id = fw.getObjectFromId(id);

            if ($(id).attr('wf') == '0') {
                return true;
            }
            return false;
        },
        sqlReplace: function (text) {
            var newText = "";
            if (!fw.checkIsTextEmpty(text)) {
                newText = text.replace("'", "''").replace("\"", "").replace("!", "！").replace("~", "").replace("$", "").replace("%", "").replace("^", "").replace("&", "").replace("*", "").replace("|", "").replace("--", "").replace("<", "").replace(">", "").replace("//", "").replace("delete", "").replace("drop", "").replace("select", "").replace("where", "").replace("from", "").replace("exec", "");
            }
            return newText;
        },
        stringSubString:function(text, start, end) {

            if (!fw.checkIsTextEmpty(text)) {
                return text.substring(start, end);
            }

            return "";
        },
        setDefaultValue: function (json, name, defaultValue) {
            try {
                if (fw.checkIsNullObject(json)) {
                    return {};
                }

                if (fw.checkIsTextEmpty(json[name])) {
                    json[name] = defaultValue;
                }
            }
            catch (e) {

            }

            return json;
        },
        checkIsObject:function(obj){
            return typeof(obj) == "object";
        },
        checkIsNullObject: function (obj) {
            if (obj != null && obj != "undefined") {
                return false;
            }
            return true;
        },
        treeGetSelected: function (treeId, success, errorMessage) {
            //fw.testObject(treeId);

            treeId = fw.getObjectFromId(treeId);

            if (fw.checkIsTextEmpty(errorMessage)) {
                errorMessage = "请选择树形数据";
            }

            var selected = $(treeId).tree('getSelected');
            if (selected == null) {
                fw.alert('提示', errorMessage);
                return;
            }

            if (fw.checkIsFunction(success)) {
                success(selected);
            }
        },
        /**
         *
         * @param treeId
         * @param splitString
         * @param quote id前后的字符，一般设置为单引号
         * @returns {string}
         */
        treeGetCheckedIds: function (treeId, splitString, quote) {
            treeId = fw.getObjectFromId(treeId);
            if (fw.checkIsTextEmpty(splitString)) {
                splitString = ",";
            }

            if (fw.checkIsTextEmpty(quote)) {
                quote = "";
            }

            var tree = fw.getObjectFromId(treeId);
            var nodes = $(tree).tree('getChecked');
            var ids = "";
            //fw.alertReturnValue(nodes);
            for (i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                ids += quote + node.id + quote + splitString;
            }
            ids = fw.removeLastLetters(ids, splitString);
            return ids;
        },
        testObject: function (obj) {
            var o = fw.getObjectFromId(obj);
            alert(obj + ' Length:' + $(o).length);
        },
        treeClear: function (tree) {
            tree = fw.getObjectFromId(tree);
            var roots = $(tree).tree('getRoots');
            $(roots).each(function () {
                $(tree).tree('remove', this.target);
            });
        },
        databoxClear:function(id){
            id = fw.getObjectFromId(id);
            $(id).datebox('clear');
        },
        combotreeClear: function (combotreeId) {
            try {
                combotreeId = fw.getObjectFromId(combotreeId);
                $(combotreeId).combotree('clear');

                // 取消选中
                var tree = $(combotreeId).combotree('tree');
                $(tree).tree('getChecked').each(function () {
                    fw.alertReturnValue(this);
                    $(tree).tree('uncheck', this.target);
                });

            }
            catch (e) {

            }
        },
        convert2Json: function (text) {
            try {
                //alert("t1:"+text);
                // 如果本身就是json对象，则直接返回
                if (typeof(text) == "object") {
                    return text;
                }

                if (fw.checkIsTextEmpty(text)) {
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
        convert2String:function(json){
            try {
                if (fw.checkIsObject(json)) {
                    return JSON.stringify(json);
                }
            }
            catch(e) {
                alert("fw.convert2String()异常:Text【" + json + "】" + e);
            }
            return json;
        },
        /**
         * 将传入参数转换为数字
         *
         * 如果传入参数不是数字，则返回0
         *
         * @author leevits
         * 时间：2015年6月17日 11:52:19
         * @param text
         * @returns {*}
         */
        convert2Number: function (text) {
            if (!fw.checkIsTextNumber(text)) {
                return 0;
            }
            return parseFloat(text);
        },
        windowClose: function (windowId) {
            var ids = fw.getObjectStartWith(windowId);
            var i = 0;
            try {
                for (i = 0; i < ids.length; i++) {
                    var obj = ids[i];
                    //alert("Reload " + i);
                    $(obj).window("close");
                    $(obj).remove();
                }
            }
            catch (e) {

            }
        },
        /**
         * 将逗号分隔的字符串组织成JSON格式
         * @param text
         * @param parameterName
         * @returns {{}}
         */
        getJsonParameters: function (text, parameterName) {
            var idsarray = text.split(",");
            var idsJson = {};
            var i = 0;
            $(idsarray).each(function () {
                if (!fw.checkIsTextEmpty(this)) {
                    idsJson[parameterName + "[" + i + "]"] = this;
                    i++;
                }
            });
            //fw.alertReturnValue(idsJson);
            return idsJson;
        },
        getFormValueByName:function(name){
            return $("input[name='"+name+"']").val();
        },
        getMD5: function (text) {
            return fnGetMD5(text);
        },
        showMessage: function (title, message) {
            $.messager.show({
                title: title,
                msg: message,
                timeout: 3000,
                showType: 'show'
            });
        },
        showError: function (title, message) {
            $.messager.show({
                title: title,
                msg: message,
                timeout: 0,
                showType: 'show'
            });
        },
        showMessageDone: function () {
            fw.showMessage('提示', '操作成功');
        },
        hideWithIdPrefix:function(arrayOfIdPrefix) {
            for (var index in arrayOfIdPrefix) {
                var id = arrayOfIdPrefix[index]
                $('#'+id).css("display", "none");
            }
        },
        getTimeYearString:function(){
            var now = new Date();
            var year = now.getFullYear();
            return year + "";
        },
        getTimeMonthString:function(){
            var now = new Date();
            var month = now.getMonth() + 1;

            return month > 9 ? month + "" : "0" + month;
        },
        getTimeNow: function () {
            var dt = new Date();
            var month = (dt.getMonth() + 1) < 10 ? '0' + (dt.getMonth() + 1) : (dt.getMonth() + 1);
            var date = dt.getDate() < 10 ? '0' + dt.getDate() : dt.getDate();
            var today = dt.getFullYear() + "-" + month + "-" + date + " " + dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
            return today;
        },
        getTimeToday: function () {
            var dt = new Date();
            var month = (dt.getMonth() + 1) < 10 ? '0' + (dt.getMonth() + 1) : (dt.getMonth() + 1);
            var date = dt.getDate() < 10 ? '0' + dt.getDate() : dt.getDate();
            var today = dt.getFullYear() + "-" + month + "-" + date;
            //alert("fw.getTimeToday(): " + today);
            return today;
        },
        getTimeYesterday: function () {
            var now = new Date();
            var yesterday = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
            //var yestaday = dt.getFullYear() + "-" + (dt.getMonth()+1) + "-" + (dt.getDate()-1);
            var month = (yesterday.getMonth() + 1) < 10 ? '0' + (yesterday.getMonth() + 1) : (yesterday.getMonth() + 1);
            var date = yesterday.getDate() < 10 ? '0' + yesterday.getDate() : yesterday.getDate();
            return yesterday.getFullYear() + "-" + month + "-" + date;
        },
        /**
         * 动态加载js代码
         * 这里的请求地址可以是一个包含js代码的jsp页面，或者是action，这样可以做到更加动态的获取js内容
         * 可以取代jeasyui的using方法
         * @param url js代码的请求地址
         * @param callback 加载成功以后运行的方法
         */
        loadScript: function (url, callback) {
            $.getScript(url, function () {
                if (fw.checkIsFunction(callback)) {
                    callback();
                }
            });
        },
        dealReturnObject: function (data) {
            //fw.alertReturnValue(data);
            data = fw.convert2Json(data);
            // TODO 判断data的值是否合法
            if (fw.checkIsNullObject(data)) {

            }
            else {
                if (data.code == 100) {
                    //fw.showMessage('成功', data.message);
                    return fw.convert2Json(data.returnValue);
                }
                else if (data.code == 200) {
                    fw.alert('失败', data.message);
                    throw new Error("数据库无法连接");
                }
                else if (data.code == 201) {
                    //fw.alert('失败','请重新登录');
                    fw.confirm('提示', '请重新登录', function () {
                        //alert(WEB_ROOT + "/login.jsp");
                        window.location = WEB_ROOT + "/login.jsp";
                    }, function () {
                    });
                    //throw new Error("数据库无法连接");
                }
                else if (data.code == 202) {
                    fw.alert('失败', data.message);
                    throw new Error("没有操作权限，请与管理员联系。");
                }
                else if (data.code == 301) {
                    fw.alert('失败', '数据库事务异常，请检查数据是否正确');
                    // throw new Error("数据库事务异常，请检查数据是否正确");
                }
                else {
                    fw.alert('失败', '错误编码：【'+ data.code + '】，错误说明：【'+data.message+'】');
                    throw new Error("错误：" + data.message);
                }
            }
        },
        dateboxClear: function (id) {
            id = fw.getObjectFromId(id);
            $(id).combobox('clear');

        },
        datetimeboxSetReadOnly: function (id) {
            id = fw.getObjectFromId(id);
            $(id).datetimebox({editable: false, disabled:true});
        },
        dateboxSetReadOnly: function (id) {
            id = fw.getObjectFromId(id);
            $(id).datebox({editable: false, disabled:true});
        },
        datetimeboxSetValue:function(id,value){
            id = fw.getObjectFromId(id);
            $(id).datetimebox({value: value});
        },
        datetimeboxSetEnable: function (id) {
            id = fw.getObjectFromId(id);
            $(id).datetimebox({editable: true, disabled:false});
        },
        dateboxSetEnable: function (id) {
            id = fw.getObjectFromId(id);
            $(id).datebox({editable: true, disabled:false});
        },
        dealException: function (e) {
            fw.alert('异常', e);
        },

        datagrid:function(definition) {
            var usedHeight = definition['usedHeight'];

            if (fw.checkIsNullObject(usedHeight)) {
                usedHeight = 250;
            }

            var strTableId = definition['id'];
            var url = definition['url'];
            var frozenColumns = definition['frozenColumns'];
            var columns = definition['columns'];
            var onLoadSuccess = definition['onLoadSuccess'];
            var onClickCell = definition['onClickCell'];
            var pagination = definition['pagination'];
            if (fw.checkIsNullObject(pagination)) {
                pagination = true;
            }

            var pageSize = Math.round(getHeight(usedHeight) / 30) - 1;
            var pageList = [pageSize, pageSize * 2, pageSize * 3, 100, 200];

            //fw.alertReturnValue(definition);

            $('#' + strTableId).datagrid({
                url: url,
                queryParams: {
                    // 此处可定义默认的查询条件
                },
                loadMsg: '数据正在加载，请稍后……',
                //width:getWidth(0.838),
                height:getHeight(usedHeight),
                fitColumns: false,
                singleSelect: true,
                pageList: pageList,
                pageSize: pageSize,
                pagination:pagination,
                rownumbers: true,
                loadFilter: function (data) {
                    try {
                        data = fw.dealReturnObject(data);
                        return data;
                    }
                    catch (e) {
                    }
                },
                frozenColumns: frozenColumns,
                columns:columns,
                onLoadSuccess: function () {
                    if (fw.checkIsFunction(onLoadSuccess)) {
                        onLoadSuccess();
                    }
                },
                onClickCell:function(index,field,value){
                    if (fw.checkIsFunction(onClickCell)) {
                        onClickCell(index,field,value);
                    }
                }
            });
        },
        datagridGetSelected: function (tableId, success) {
            var selected = $('#' + tableId).datagrid('getSelected');
            if (selected != null) {
                if (fw.checkIsFunction(success)) {
                    success(selected);
                }
            }
            else {
                fw.alert('警告', '请选择数据');
            }
        },
        datagridGetSelections: function (tableId, success) {
            var sections = $('#' + tableId).datagrid('getSelections');
            if (sections.length > 0) {
                if (fw.checkIsFunction(success)) {
                    success(sections);
                }
            }
            else {
                fw.alert('警告', '请选择数据');
            }
        },
        datagridReload: function (tableId) {
            $('#' + tableId).datagrid('reload');
        },

        bindOnClick4Any:function(id, onClick){
            id = fw.getObjectFromId(id);

            // 清除已有的绑定事件
            $(id).unbind("click");

            $(id).bind('click',function(){
                if (fw.checkIsFunction(onClick)) {
                    onClick(id);
                }
            });
        },
        /**
         * 绑定点击事件
         * @param buttonId 按钮ID
         * @param onClick 点击响应的事件
         */
        bindOnClick: function (buttonId, onClick) {
            buttonId = fw.getObjectFromId(buttonId);


            // 如果没有此按钮，则退出
            if (fw.checkIsNullObject(buttonId) || $(buttonId).length == 0) {
                return;
            }

            //alert(buttonId);
            //alert($(buttonId).length);
            // 清除已有的绑定事件
            $(buttonId).unbind("click");


            var process = {};
            try {
                if (!fw.checkIsNullObject($(buttonId).linkbutton('options'))) {


                    var text = $(buttonId).linkbutton('options').text;

                    // process类定义了三个方法，将在执行点击时使用，用于处理异步情况
                    process = {
                        //改变按钮的文本
                        beforeClick: function () {
                            //alert(1);
                            $(buttonId).linkbutton({text: '正在处理', disabled: true});
                        },
                        afterClick: function () {
                            $(buttonId).linkbutton({text: text, disabled: false});
                        },
                        errorClick: function () {
                            $(buttonId).linkbutton({text: text, disabled: false});
                        }
                    };
                }
            }
            catch (e) {
                alert(e.message);
            }




            $(buttonId).bind('click', function () {
                //alert(1);
                //var text = $(buttonId).linkbutton('options').text;
                //$(buttonId).linkbutton({text: '正在处理', disabled: true});
                //alert(2);
                try {
                    // 如果按钮已被禁用，则直接返回
                    if (!fw.checkIsNullObject($(buttonId).linkbutton('options'))) {
                        var isDisabled = $(buttonId).linkbutton('options').disabled;
                        if (isDisabled) {
                            return;
                        }
                        onClick(process);
                    }

                }
                catch (e) {

                }
                finally {
                    //$(buttonId).linkbutton({text: text, disabled: false});
                }
            });

        },
        bindOnSubmitForm: function (formId, url, onSubmit, success, error) {

            $("#" + formId).form('submit', {
                url: url,
                onSubmit: function () {
                    if (!$(this).form('validate')) {
                        return false;
                    }
                    else {
                        if (fw.checkIsFunction(onSubmit)) {
                            onSubmit();
                        }
                    }
                },
                success: function (data) {
                    try {
                        // fw.alertReturnValue(data);
                        data = fw.dealReturnObject(data);

                        if (fw.checkIsFunction(success)) {
                            success(data);
                        }
                    }
                    catch (e) {
                        if (fw.checkIsFunction(error)) {
                            error();
                        }
                    }
                }
            });
        },
        debug: function (text) {
            //if (isDebug == 1) {
            //    alert(text);
            //}
            fw.debug(text, 900000);
        },
        debug:function(text, number) {
            var textValue = text;
            var isObject = fw.checkIsJsonObject(text);
            if (isObject) {
                textValue = JSON.stringify(text);
            }
            fw.alert('debug ' +  number, textValue);
        },
        getObjectFromId: function (id) {
            //alert(typeof id + " : " + id);

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
        getTextValue: function (id) {
            id = fw.getObjectFromId(id);
            return $(id).val();
        },
        removeLastLetters: function (text, letters) {
            if (fw.checkIsLastWith(text, letters)) {
                //alert('letters.length:'+letters.length);
                return text.substring(0, text.length - letters.length)
            }
            return text;
        },
        removeLastLettersCount: function (text, count) {
            return text.substring(0, text.length - count);
        },
        checkIsLastWith: function (text, letters) {
            //alert('text:' + text + ' letters:' + letters);
            var lastIndex = text.lastIndexOf(letters);
            //alert(lastIndex);
            if (lastIndex != -1) {
                var lastLetters = text.substring(lastIndex, text.length);
                //alert('lastLetters:' + lastLetters + ' letters:' + letters + 'result:'+ (lastLetters == letters));
                return lastLetters == letters;
            }
            return false;
        },
        treeLoad: function (treeId, url, parameters, success, error) {

            var loadingMessage = "正在加载，请稍候……";
            var tree = fw.getObjectFromId(treeId);

            //alert(tree);

            fw.treeClear(tree);
            $(tree).tree('loadData', [{'id': '', 'text': loadingMessage}]);

            fw.post(url, parameters, function (data) {
                //fw.alertReturnValue(data);
                fw.treeClear(tree);
                $(tree).tree({
                    data: data
                });
                if (fw.checkIsFunction(success)) {
                    success(data);
                }

            }, function () {
                fw.treeClear(tree);
                $(tree).tree('loadData', [{'id': '', 'text': '加载失败'}]);

                if (fw.checkIsFunction(error)) {
                    error();
                }
            });

        },
        /**
         *
         * @param combotreeId
         * @param splitString
         * @param quote id前后的字符，一般设置为单引号
         * @returns {string}
         */
        combotreeGetCheckedIds: function (combotreeId, splitString, quote) {
            combotreeId = fw.getObjectFromId(combotreeId);
            if (fw.checkIsTextEmpty(splitString)) {
                splitString = ",";
            }


            var tree = $(combotreeId).combotree('tree');
            return fw.treeGetCheckedIds(tree, splitString, quote);
        },
        combotreeLoadWithCheck: function (combotreeId, url, parameters, success, error) {

            var loadingMessage = "正在加载，请稍候……";
            var combotree = fw.getObjectFromId(combotreeId);
            var tree = $(combotree).combotree('tree');
            //alert(tree);

            fw.treeClear(tree);
            $(tree).tree('loadData', [{'id': '', 'text': loadingMessage}]);

            fw.post(url, parameters, function (data) {
                //fw.alertReturnValue(data);
                fw.treeClear(tree);
                $(tree).tree({
                    data: data,
                    checkbox: true
                });
                $(combotree).combotree({
                    multiple: true,
                    separator: ",",
                    onCheck: function () {
                        var text = $(combotree).combo('getText');
                        //alert(text.length);
                        var size = (10 * text.length) > 200 ? (10 * text.length + 30) : 180;
                        $(combotreeId).combo('resize', size);

                    }
                });
                if (fw.checkIsFunction(success)) {
                    success(data);
                }

            }, function () {
                fw.treeClear(tree);
                $(tree).tree('loadData', [{'id': '', 'text': '加载失败'}]);

                if (fw.checkIsFunction(error)) {
                    error();
                }
            });

        },
        getComboTreeFromKV: function (id, groupName, orderBy, selectedId) {

            getComboTreeFromKV(id, groupName, orderBy, selectedId, false);

        },
        getComboTreeValue:function(id) {
            id = fw.getObjectFromId(id);

            return fw.getFormValue(id, fw.type_form_combotree, fw.type_get_value);
        },
        getComboTreeText:function(id) {
            id = fw.getObjectFromId(id);

            return fw.getFormValue(id, fw.type_form_combotree, fw.type_get_text);
        },
        getComboTreeFromKVWithCheck: function (id, groupName, orderBy, selectedId) {

            getComboTreeFromKV(id, groupName, orderBy, selectedId, true);

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
        /**
         *
         * @param url 提交路径
         * @param params 参数
         * @param success 成功时执行的方法
         * @param error 失败时执行的方法
         */
        post: function (url, params, success, error, buttonId) {
            $.ajax({
                type: "POST",
                url: url,
                processData: true,
                data: params,
                dataType: 'json',
                success: function (data) {

                    try {
                        try {
                            var d = fw.dealReturnObject(data);
                        } catch (e) {
                            var text = $(buttonId).linkbutton('options').text;
                            $("#" + buttonId).linkbutton({text: text, disabled: false});
                        }
                        if (fw.checkIsFunction(success)) {
                            success(d);
                        }

                        fw.showMessageDone();
                    }
                    catch (e) {
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var message = textStatus + " " + errorThrown;
                    fw.showError('未知错误[L:001]', message);
                    if (fw.checkIsFunction(error)) {
                        error(message);
                    }
                }
            });
        },
        /**
         * 专门给Oa提交的Post
         * @param url 提交路径
         * @param params 参数
         * @param success 成功时执行的方法
         * @param error 失败时执行的方法
         */
        OaPost: function (url, params, success, error, buttonId) {
            $.ajax({
                type: "POST",
                url: url,
                processData: true,
                data: params,
                dataType: 'json',
                success: function (data) {

                    data = fw.convert2Json(data);
                    if(data.code==502){
                        fw.alert("警告",data.message);
                        if (fw.checkIsFunction(error)) {
                            error();
                        }
                        return ;
                    }
                    try {
                        try {
                            var d = fw.dealReturnObject(data);
                        } catch (e) {

                        }
                        if (fw.checkIsFunction(success)) {
                            success(d);
                        }

                        fw.showMessageDone();
                    }
                    catch (e) {
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var message = textStatus + " " + errorThrown;
                    fw.showError('未知错误[L:001]', message);
                    if (fw.checkIsFunction(error)) {
                        error(message);
                    }
                }
            });
        }, /**
         *
         * @param url 提交路径
         * @param params 参数
         * @param success 成功时执行的方法
         * @param error 失败时执行的方法
         */
        post: function (url, params, success, error) {
            $.ajax({
                type: "POST",
                url: url,
                processData: true,
                data: params,
                dataType: 'json',
                success: function (data) {

                    try {
                        try {
                            var d = fw.dealReturnObject(data);

                            if (fw.checkIsFunction(success)) {
                                success(d);
                            }

                        } catch (e) {
                            // fw.alertReturnValue(e);
                        }


                        fw.showMessageDone();
                    }
                    catch (e) {
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var message = textStatus + " " + errorThrown;
                    fw.showError('未知错误[L:001]', message);
                    if (fw.checkIsFunction(error)) {
                        error(message);
                    }
                }
            });
        },
        postOrginal: function (url, params, success, error) {
            $.ajax({
                type: "POST",
                url: url,
                processData: true,
                data: params,
                dataType: 'text',
                success: function (data) {
                    // alert("fw.postOrginal(): " + data);
                    try {
                        if (fw.checkIsFunction(success)) {
                            success(data);
                        }

                    }
                    catch (e) {
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var message = textStatus + " " + errorThrown;
                    alert(message);
                    if (fw.checkIsFunction(error)) {
                        error(message);
                    }
                }
            });
        },
//        post:function(url,params,success,error){
//            $.ajax({
//                url:url,
//                type:"POST",
//                processData:true,
//                data:params,
//                dataType:'json',
//                success:function(data){
//                    try{
//                        var d = fw.dealReturnObject(data);
//                        if(fw.checkIsFunction(success)){
//                            return success(d);
//                        }
//                        fw.showMessageDone();
//                    }catch(e){
//
//                    }
//                },
//                error:function(XMLHttpRequest , textStatus , errorThrown){
//                    var message = textStatus + " " + errorThrown;
//                    fw.showError('未知错误[L:001]',message);
//                    if(fw.checkIsFunction(error)){
//                        error(message);
//                    }
//                }
//            });
//        },
        /**
         *
         * @param url 提交路径
         * @param params 参数
         * @param success 成功时执行的方法
         * @param error 失败时执行的方法
         */
        get: function (url, params, success, error) {
            $.ajax({
                type: "GET",
                url: url,
                processData: true,
                data: params,
                dataType: 'json',
                success: success,
                error: error
            });
        },
        getQueryString: function (jsonObject) {
            var string = "";
            $.each(jsonObject, function (key, value) {
                //alert(key);
                string += key + "=" + value + "&";
            });
            return fw.removeLastLetters(string, '&');
        },
        buildUrlParameters:function(json) {
            var parameters = "";
            if (fw.checkIsJsonObject(json)) {
                var keys = Object.keys(json);
                for (i = 0; i < keys.length; i++) {
                    var k = keys[i];
                    var v = json[k];

                    parameters +=  k + "=" + v + "&";
                }
            }

            return parameters;
        },
        alertReturnValue: function (data) {
            alert(JSON.stringify(data));
        },
        chartPie1: function (containerId, title, description, data) {
            //fw.alertReturnValue(data);
            containerId = fw.getObjectFromId(containerId);
            $(containerId).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: title
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}% ({point.y})</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f}% ({point.y})',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: description,
                    data: data
                }]
            });
        },
        formatMoney:function(m){
            return accounting.formatMoney(m, "", 2);
        },
        checkIsFunction: function (functionName) {
            //alert('fw.checkIsFunction(): ' + typeof(functionName));
            if (typeof(functionName) == 'function') {
                return true;
            }
            return false;
        },
        formLoad: function (formId, data) {
            $('#' + formId).form('clear');
            $('#' + formId).form('load', data);
        },
        formSubmit: function (formId, url, buttonId, onSubmit, success) {
            $("#" + formId).form("submit", {
                url: url,
                onSubmit: function () {
                    if (!fw.checkIsNullObject(buttonId)) {
                        $('#' + buttonId).linkbutton({text: '正在提交...', disabled: true});
                    }
                    if (!$(this).form('validate')) {
                        if (!fw.checkIsNullObject(buttonId)) {
                            $('#' + buttonId).linkbutton({text: '确定', disabled: false});
                        }
                        return false;
                    }
                    if (fw.checkIsFunction(onSubmit)) {
                        onSubmit();
                    }
                },
                success: function (data) {
                    if (fw.checkIsFunction(success)) {
                        success(data);
                    }
                    if (!fw.checkIsNullObject(buttonId)) {
                        $('#' + buttonId).linkbutton({text: '确定', disabled: false});
                    }
                }
            });
        },
        urlEncode: function (str) {
            var ret = "";
            var strSpecial = "!\"#$%&’()*+,/:;<=>?[]^`{|}~%";
            var tt = "";
            for (var i = 0; i < str.length; i++) {
                var chr = str.charAt(i);
                var c = str2asc(chr);
                tt += chr + ":" + c + "n";
                if (parseInt("0x" + c) > 0x7f) {
                    ret += "%" + c.slice(0, 2) + "%" + c.slice(-2);
                }
                else {
                    if (chr == " ")
                        ret += "+";
                    else if (strSpecial.indexOf(chr) != -1)
                        ret += "%" + c.toString(16);
                    else
                        ret += chr;
                }
            }

            return ret;
        },
        window: function (id, title, width, height, url, onLoad, onClose) {

            if ($('#' + id).length == 0) {
                $("#windowsArea").append("<div id='" + id + "'></div> ");
            }
            $('#' + id).window({
                title: title,
                width: width,
                height: height,
                modal: true,
                cache: false,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                closable: true,
                resizable: false,
                loadingMessage: '正在加载，请稍候……',
                href: url,
                closed: false,
                onLoad: function () {
                    if (fw.checkIsFunction(onLoad)) {
                        onLoad();
                    }
                },
                onClose: function () {
                    if (fw.checkIsFunction(onClose)) {
                        onClose();
                    }
                }
            });
        },
        customerWindow: function (id, helpWindowId, title, width, height, url, url2, onLoad, onClose) {
            if ($('#' + id).length == 0) {
                $("#windowsArea").append("<div id= '" + id + "'></div> ");
            }
            $('#' + id).window({
                title: title,
                width: width,
                height: height,
                modal: true,
                cache: false,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                closable: true,
                resizable: false,
                loadingMessage: '正在加载，请稍候……',
                href: url,
                closed: false,
                tools: [{
                    iconCls: 'icon-help',
                    handler: function () {

                        fw.window(helpWindowId, '帮助提示', 380, 180, url2, null, null);
                    }
                }],
                onLoad: function () {
                    if (fw.checkIsFunction(onLoad)) {
                        onLoad();
                    }
                },
                onClose: function () {
                    if (fw.checkIsFunction(onClose)) {
                        onClose();
                    }
                }
            });
        },
        //重载onload方法带参数
        window: function (id, title, width, height, url, onLoad, onClose, Param) {

            //var json = JSON.stringify(Param)
            //alert("fw.window(): " + json);

            if ($('#' + id).length == 0) {
                $("#windowsArea").append("<div id='" + id + "'></div> ");
            }
            $('#' + id).window({
                title: title,
                width: width,
                height: height,
                modal: true,
                cache: false,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                closable: true,
                resizable: false,
                loadingMessage: '正在加载，请稍候……',
                href: url,
                closed: false,
                onLoad: function () {
                    if (fw.checkIsFunction(onLoad)) {
                        onLoad(Param);
                    }
                },
                onClose: function () {
                    if (fw.checkIsFunction(onClose)) {
                        onClose();
                    }
                }
            });

        },
        /*
         * 作者：周海鸿
         * 时间：2015-8-7
         * 内容：实现二级公司部门下拉列表
         **/
        /**
         *
         * @param treeId1 公司下拉id
         * @param treeId2 部门下拉id
         * @param selected1 选中的项
         * @param selected2
         * @param isDisplay true 禁用、false 启用
         */
        doubleDepartmentTrees: function (treeId1, treeId2, selected1, selected2, isDisplay) {
            var controlString1Url = WEB_ROOT + "/system/Department_getDepartmentTrees.action";//公司的URL
            fw.combotreeLoad(treeId1, controlString1Url, selected1);
            $("#" + treeId1).combotree({
                onSelect: function (node) {
                    var controlString2Url = WEB_ROOT + "/system/Department_getDepartmentTrees2.action?department.parentId=" + node.id;//部门选择的URL
                    fw.combotreeLoad(treeId2, controlString2Url, selected2);

                    //禁止使用
                    if (isDisplay) {
                        $("#" + treeId1).combotree('disable');
                    }


                }
            });
            //预留方法用啦处理部门的选中事件
            //禁止使用
            $("#" + treeId2).combotree({
                onSelect: function (node) {
                    //禁用下了列表
                    if (isDisplay) {
                        $("#" + treeId2).combotree('disable');
                    }
                }
            });

        },
        confirm: function (title, message, yes, no) {
            $.messager.confirm(title, message, function (r) {
                if (r) {
                    if (!fw.checkIsNullObject(yes)) {
                        yes();
                    }
                }
                else {
                    if (!fw.checkIsNullObject(no)) {
                        no();
                    }
                }
            });
        },
        checkboxGetChecked:function(id){
            id = fw.getObjectFromId(id);
            return $(id).is(':checked');
        },
        checkboxSetChecked:function(id, defaultChecked, onChecked, onUnchecked){
            id = fw.getObjectFromId(id);
            $(id).prop('checked', defaultChecked);

            if (defaultChecked) {
                if (fw.checkIsFunction(onChecked)) {
                    onChecked(id);
                }
            }
            else {
                if (fw.checkIsFunction(onUnchecked)) {
                    onUnchecked(id);
                }
            }

            fw.bindOnClick4Any(id, function(id){
                $(id).prop('checked', function (i, checked) {
                    if (checked) {
                        // checked..
                        if (fw.checkIsFunction(onChecked)) {
                            onChecked(id);
                        }
                    }
                    else {
                        // unchecked...
                        if (fw.checkIsFunction(onUnchecked)) {
                            onUnchecked(id);
                        }
                    }
                });
            });
        },
        textGetValue:function(id){
            id = fw.getObjectFromId(id);
            return $(id).val();
        },
        textSetValue:function(id, value){
            id = fw.getObjectFromId(id);
            $(id).val(value);
        },
        textReadOnly:function(id, isReadOnly) {
            id = fw.getObjectFromId(id);
            $(id).attr("readonly", isReadOnly);
        },
        combotreeSetReadOnly: function (id) {
            id = fw.getObjectFromId(id);
            $(id).combotree({readonly: true});
        },
        combotreeSetEditable: function (id) {
            id = fw.getObjectFromId(id);
            $(id).combotree({readonly: false});
        },

        /**
         * 选中combotree中传入参数对应的选项
         * @param id combotree id
         * @param selectSuccessId 选中的选项值
         */
        combotreeSetSelected: function (id, selectSuccessId) {
            //alert('do');
            //var t = $('#cc').combotree('tree');
            // alert("fw.comboteeSetSelected(): " + selectSuccessId);
            var tree = $(id).combotree("tree");
            //alert(typeof(tree));
            if (!fw.checkIsNullObject(selectSuccessId)) {
                if (selectSuccessId == '-1') {
                    var root = $(tree).tree('getRoot');
                    $(tree).tree('select', root.target);
                    $(id).combotree("setValue", root.id);
                }
                // 不选择任何节点
                else if (selectSuccessId == '-2') {
                    // do nothing
                }
                else {
                    var node = $(tree).tree('find', selectSuccessId);
                    if (!fw.checkIsNullObject(node)) {
                        $(tree).tree('select', node.target);
                        $(id).combotree("setValue", node.id);
                    }
                }
            }
        },

        /**
         * 预加载combotree
         * @param id combotree id
         * @param url ajax加载url
         * @param success 成功以后的处理
         * @param selectSuccessId 成功以后默认选中的id值，'-1'：则表示选择根节点，‘-2'：不选择任何节点
         */
        combotreeOnload: function (id, url, success, selectSuccessId) {
            id = fw.getObjectFromId(id);
            $.ajax({
                type: "GET",
                url: url,
                processData: true,
                data: '',
                dataType: 'json',
                success: function (data) {
                    try {
                        if (data.code == 100) {
                            //alert(JSON.stringify(data));
                            $(id).combotree({
                                onLoadSuccess: function () {
                                    fw.combotreeSetSelected(id, selectSuccessId);
                                }
                            });
                            var combotreeValue = success(data);
                            //alert(JSON.stringify(combotreeValue));
                            // fw.debug(combotreeValue, 323234);
                            $(id).combotree('loadData', combotreeValue);
                            if (selectSuccessId == '-2') {
                                fw.combotreeClear(id);
                            }
                        }
                        else if (data.code == 200) {
                            fw.alert('错误', data.message);
                        }
                    }
                    catch (e) {
                        fw.alert("错误", "加载" + id + "失败" + e);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    fw.alert('错误', textStatus);
                }
            });
        },
        /**
         * 加载树形节点
         */
        combotreeLoad: function (combotreeId, URL, selectIndexId) {
            // fw.debug('开始执行', 8783);
            fw.combotreeOnload(combotreeId, URL, function (data) {
                var treeData = [];
                try {
                    // fw.debug(data, '加载树1234');
                    data = fw.dealReturnObject(data);
                    treeData = data;
                }
                catch (e) {
                    fw.alert('fw.combotreeLoad', e.message);
                }
                return treeData;
            }, selectIndexId);
        },
        combotreeLoadFromClass: function (combotreeId, className, enumTypeId, selectedId) {
            var url = WEB_ROOT + "/include/framework/ComboTreeJS.jsp?className=" + className + "&id=" + enumTypeId;

            combotreeId = fw.getObjectFromId(combotreeId);
            //alert(combotreeId);

            fw.postOrginal(url, null, function (data) {
                data = fw.convert2Json(data);
                $(combotreeId).combotree('loadData', data);

                fw.combotreeSetSelected(combotreeId, selectedId);

            }, null);

        },
        //获取系统时间 格式0000-00-00
        getDate: function () {
            var now = new Date();
            return getDateText(now);
        },
        /**
         * 获取格式 0000-00-00 00:00:00
         * @returns {*}
         */
        getDateTime: function () {
            var now = new Date();
            return getDateTimeText(now);
        },
        /**
         * 文件上传类，（弹出文件管理窗口）
         * @param moduleId 所属模块，
         * @param moduleDescription 模块的描述
         */
        /*修改：周海鸿
         * 时间：2015-7-13
         * 内容：增加一参数实现在用章管理的时候是有申请人才能修改用章上传的文件
         * "remove" 为移除添加、删除、修改 上传按钮。其他 不操作*/
        uploadFiles: function (token, moduleId, btnStatus, Bizid) {
            var URL = WEB_ROOT + "/modules/system/FilesUpload_Main.jsp?token=" + token;
            var windowId = "FilesUploadWindow" + token;
            fw.window(windowId, "文件上传列表", 900, 500, URL, function () {
                $(".panel-tool").empty();
                using(SCRIPTS_ROOT + "/system/FilesClass.js", function () {
                    var filesClass = new FilesClass(token, moduleId, btnStatus, Bizid);
                    filesClass.initModule();
                });
            }, null);
        },
        /**
         * 初始化附件上传
         * @author 邓超
         * @param bizid         业务 ID，或理解为行为 ID，如添加了一条记录，那么取该记录的 ID
         * @param moduleId      模块 ID，在 KV 定义好的模块 ID
         * @param buttonId      HTML 代码的上传按钮 ID
         * @param token         你懂的
         *
         * 修改：李昕骏
         * 时间：2015年6月12日 14:29:57
         * 内容：
         * 增加验证bizId的有效性，如果没有bizId，则不能上传
         * 增加传入参数验证，增加注释
         *
         *
         */
        /*修改：周海鸿
         * 时间：2015-7-13
         * 内容：增加一参数实现在用章管理的时候是有申请人才能修改用章上传的文件
         *
         * "remove" 为移除添加、删除、修改 上传按钮。其他 不操作*/
        initFileUpload: function (bizId, moduleId, buttonId, btnStatus, token) {

            if (fw.checkIsTextEmpty(moduleId)) {
                fw.alert('警告', '附件上传，传入模块编号数据异常，请与管理员联系！');
                return;
            }

            if (fw.checkIsTextEmpty(buttonId)) {
                fw.alert('警告', '附件上传，传入模块按钮数据异常，请与管理员联系！');
                return;
            }

            buttonId = fw.getObjectFromId(buttonId);


            if (bizId != "") {
                var URl = WEB_ROOT + "/system/Files_getBiZidCounts.action?files.moduleId=" + moduleId + "&files.bizid=" + bizId;
                fw.post(URl, null, function (data) {
                    $(buttonId).linkbutton({text: "已有" + data['bizids'] + "个附件，点击上传", disabled: false});
                }, null);
            }

            fw.bindOnClick(buttonId, function (process) {

                if (fw.checkIsTextEmpty(bizId)) {
                    fw.alert('警告', '业务编号为空，请保存当前页面内容或检查数据有效性！');
                    return;
                }

                process.beforeClick();
                fw.uploadFiles(token, moduleId, btnStatus, bizId);
                process.afterClick();
            });
        },

        /**
         * 生成一个 UUID
         * @author 邓超
         * @returns {string}
         */
        genUUID: function () {
            var s = [];
            var hexDigits = "0123456789abcdef";
            for (var i = 0; i < 36; i++) {
                s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
            }
            s[14] = "4";
            s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
            s[8] = s[13] = s[18] = s[23] = "-";
            var uuid = s.join("");
            return uuid;
        },
        numberFloatFormat: function (number, fixed) {

            if (fw.checkIsTextNumber(number)) {
                number = fw.convert2Number(number);
            }

            try {
                number = number.toFixed(fixed);
            }
            catch (e) {
                // alert(e);
            }

            return number;
        },
        /**
         *
         * 周海鸿
         * 初始化弹出业务审批窗口
         * 用法
         *
         * fw.onClickBizSubmit(token,buttonId,YWID,workflowID,routeListId,currentNodeId,tableId,windowId);
         *修改：周海鸿
         * 时间2015-7-30
         * 内容：YWID,workflowID, routeListId, currentNodeId 将原来测参数改为以json数组的方式传入
         *
         * @param token
         * @param buttonId 按钮ID
         * @param YWID  业务ID
         * @param workflowID  工作流编号
         * @param routeListId  节点编号
         * @param currentNodeId  路由编号
         * @param tableId 需要重新加载的列表编号
         * @param tableId2 需要重新加载的列表编号
         * @param tableId3 需要重新加载的列表编号
         * @param windowId 需要 关闭的窗口编号
         */
        onClickBizSubmit: function (token, buttonId, YWData, tableId, tableId2, tableId3, windowId, newWorkflow) {

            fw.bindOnClick(buttonId, function (process) {
                process.beforeClick();
                using('../wf/script/BizRouteClass.js', function () {
                    var bizRoute = new BizRouteClass(token, YWData, tableId, tableId2, tableId3, windowId, newWorkflow);
                    bizRoute.initModule();
                    process.afterClick();
                });

            });
        },
        /**
         * 周海鸿
         * 用来获取选中的数据
         * @param id
         * @param callback
         */
        buildCompanyCombotree: function (id, selected, callback) {
            //初始化部门下拉列表
            var url = WEB_ROOT + "/system/Department_list.action";
            fw.combotreeLoad(id, url, selected);
            $('#' + id).combotree({
                onSelect: function (node) {
                    callback(node);
                }
            });
        },
        combotreeBuild4FortuneCenter: function (id, selected, callback) {
            //初始化部门下拉列表
            var url = WEB_ROOT + "/system/Department_getDepartments4FortuneCenter.action";
            fw.combotreeLoad(id, url, selected);
            $('#' + id).combotree({
                onSelect: function (node) {
                    callback(node);
                }
            });
        },
        buildJson: function (k, v) {
            var json = "{'" + k + "':'" + v + "'}";
            json = fw.convert2Json(json);
            return json;
        },
        /**
         * 用来提交申请的方法
         * @param data
         * 格式为：{'WorkflowID':业务流编号,'id':id,'routeListId':'路由编号',currentNodeId:"当前节点编号"
         *               ，'nextNode':下一个节点编号，controlString1：公司，controlString2：部门，controlString3:数据标识
         *              ，serviceClassName:'主表逻辑操作类'}
         * @returns {{initModule: Function}}
         */
        onclickworkflowApplay: function (data, process, tableId1, tableId2, tableId3, windowId1) {
            var bizRouteworkflowId = "bizRoute.workflowId=" + data["workflowID"] + "&";
            var WorkflowID = "WorkflowID=" + data["workflowID"] + "&";
            var YWID = "YWID=" + data["id"] + "&bizRoute.id_ywid=" + data["id"] + "&";
            var RouteListID = "RouteListID=" + data["routeListId"] + "&";
            var CurrentNode = "CurrentNode=" + data["currentNodeId"] + "&";
            var TargetURL = "TargetURL=//wf//Done.jsp&";
            var ServiceType = "ServiceType=SaveForward&";
            var BizDaoName = "BizDaoName=com.youngbook.entity.po.wf.BizRoutePO&";
            var JsonPrefix = "JsonPrefix=bizRoute&";
            var NextNode = "NextNode=" + data["nextNode"] + "&";
            var Participant = "Participant=" + loginUser.getId() + "&";
            //用来在流结束时操作主表的逻辑类
            var classname = data["serviceClassName"] == "undefined" ? "" : data["serviceClassName"];
            var serviceClassName = "bizRoute.serviceClassName=" + classname + "&";

            //设置操作人
            var applicationName = "bizRoute.applicantName=" + loginUser.getName() + "&";
            //设置操作时间
            var applicationTime = "bizRoute.applicantTime=" + fw.getDateTime() + "&";
            //设置操作编号
            var applicationId = "bizRoute.applicantId=" + loginUser.getId() + "&";

            //设置申请人
            var submitterId = "bizRoute.submitterId=" + loginUser.getId() + "&";
            //设置申请时间
            var submitterTime = "bizRoute.submitterTime=" + fw.getDateTime() + "&";
            //设置申请编号
            var submitterName = "bizRoute.submitterName=" + loginUser.getName() + "&";

            //公司
            var controlString1 = '';
            if (data["controlString1"]) {
                controlString1 = data["controlString1"];
            }
            //部门
            var controlString2 = '';
            if (data["controlString2"]) {
                controlString2 = data["controlString2"];
            }
            //数据标识号
            var controlString3 = '';
            if (data["controlString3"]) {
                controlString3 = data["controlString3"];
            }
            //将所有审批状态都改成0
            var status = "bizRoute.departmentLeaderStatus=0&bizRoute.generalManagerStatus=0&bizRoute.accountingStatus=0&" +
                "bizRoute.financeDirectorStatus=0&bizRoute.chargeLeaderStatus=0&bizRoute.executiveDirectorStatus=0&" +
                "bizRoute.cashierStatus=0&bizRoute.status1=0&bizRoute.status2=0&bizRoute.status3=0&" +
                "bizRoute.status4=0&bizRoute.status5=0&";

            controlString1 = "bizRoute.controlString1=" + controlString1 + "&";
            controlString2 = "bizRoute.controlString2=" + controlString2 + "&";
            controlString3 = "bizRoute.controlString3=" + controlString3 + "&";
            var urls = bizRouteworkflowId + WorkflowID + YWID + RouteListID + CurrentNode + NextNode + serviceClassName + TargetURL + ServiceType + BizDaoName + JsonPrefix + Participant + applicationName + applicationTime + applicationId + submitterId + submitterTime + submitterName + controlString1 + controlString2 + controlString3 + status;
            //设置业务提交URL
            var url = WEB_ROOT + "/wf/Workflow_service.action?" + urls;
            fw.confirm("通知", "此申请将直接进入流转审批，是否确认?",function(){
                fw.OaPost(url, null, function (data) {
                    if (data != 1) {
                        fw.alert("通知", "申请失败，请联系管理员");
                    }
                    $("#" + tableId1).datagrid('load');
                    $("#" + tableId2).datagrid('load');
                    $("#" + tableId3).datagrid('load');
                    fw.windowClose(windowId1);
                    process.afterClick();
                },function(){/*
                    $("#"+tableId1).datagrid('load');
                    $("#"+tableId2).datagrid('load');
                    $("#"+tableId3).datagrid('load');
                    fw.windowClose(windowId1);*/
                    process.afterClick();
                });
            });
        }
        , getContractRouteCombotreeDate: function () {
            //流转状态
            var ContractRouteStatus = [
                {"id": "1", "text": "合同申请"},
                {"id": "2", "text": "审批通过"},
                {"id": "3", "text": "审批失败"},
                {"id": "4", "text": "等待调配"},
                {"id": "5", "text": "合同寄送"},
                {"id": "6", "text": "合同签收"},
                {"id": "7", "text": "合同领用"},
                {"id": "8", "text": "合同签约"},
                {"id": "9", "text": "合同异常"},
                {"id": "10", "text": "移交调配管理员"},
                {"id": "11", "text": "合同状态确认"},
                {"id": "12", "text": "合同归档状态确认"},
                {"id": "13", "text": "合同归档"},
                {"id": "14", "text": "空白合同管理员"},
                {"id": "15", "text": "异常合同管理员"},
                {"id": "16", "text": "签约合同管理员"},
                {"id": "17", "text": "归档合同管理员"},
                {"id": "18", "text": "合同正常"},
                {"id": "19", "text": "销售合同取消签约"},
                {"id": "20", "text": "移交总部修改财富中心"}
            ];
            return ContractRouteStatus;
        }
    };
})
();

function fwCloseWindow(windowId) {
    var ids = fw.getObjectStartWith(windowId);
    var i = 0;
    try {
        for (i = 0; i < ids.length; i++) {
            var obj = ids[i];
            //alert("Reload " + i);
            $(obj).window("close");
            $(obj).remove();
        }
    }
    catch (e) {

    }
}

function checkMobile(mobile) {


}