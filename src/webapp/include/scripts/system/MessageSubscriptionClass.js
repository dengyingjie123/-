/**
 * Created by Jepson on 2015/6/25.
 */
var MessageSubscriptionClass = function (token) {

    var callback4Select = undefined;

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickMessageSubscriptionSearch();
        // 初始化查询重置事件
        onClickMessageSubscriptionSearchReset();

        // 初始化表格
        initMessageSubscriptionTable();

        // 初始化查询表单
        initMessageSubscriptionSearchForm();

        // 初始化选择窗口按钮
        onClcikSelectDone()
    }

    /**
     * 初始化查询表单
     */
    function initMessageSubscriptionSearchForm() {
        fw.getComboTreeFromKV('search_isEmail'+token, 'Is_Avaliable', 'k','-2');
        fw.getComboTreeFromKV('search_isSms'+token, 'Is_Avaliable', 'k','-2');
        fw.getComboTreeFromKV('search_isTodoList'+token, 'Is_Avaliable', 'k','-2');
        fw.getComboTreeFromKV('search_messageTypeId'+token, 'System_MessageType', 'k','-2');
    }


    function onClcikSelectDone() {
        fw.bindOnClick('btnSelect'+token, function(press) {
            var strTableId = 'MessageSubscriptionTable' + token;
            var data = $('#' + strTableId).datagrid('getSelected');
            if (fw.checkIsFunction(callback4Select)) {
                callback4Select(data);
            }
            fwCloseWindow('SelectWindow'+token);
        });
    }

    // 构造初始化表格脚本
    function initMessageSubscriptionTable() {
        var strTableId = 'MessageSubscriptionTable' + token;
        var url = WEB_ROOT + '/system/MessageSubscription_list.action';

        $('#' + strTableId).datagrid({
            title: '消息订阅',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: 'sid',//排序的列
            loadFilter: function (data) {
                try {
                    //获取数据 返回的Value
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid',hidden:true},
                    { field: 'id', title: 'id',hidden:true},
                    { field: 'state', title: 'state',hidden:true},
                    { field: 'operatorId', title: 'operatorId',hidden:true},
                    { field: 'operateTime', title: 'operateTime',hidden:true},
                    { field: 'userId', title: '用户编号',hidden:false,width:200},
                    { field: 'messageTypeName', title: '消息类型编号',hidden:false,width:200},
                    { field: 'emailName', title: '邮件提醒',hidden:false,width:200},
                    { field: 'smsName', title: '短信提醒',hidden:false,width:200},
                    { field: 'todoListName', title: '系统代办提醒',hidden:false,width:200}
                ]
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickMessageSubscriptionAdd();
                onClickMessageSubscriptionEdit();
                onClickMessageSubscriptionDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickMessageSubscriptionAdd() {
        var buttonId = 'btnMessageSubscriptionAdd' + token;
        fw.bindOnClick(buttonId, function(process) {
            initMessageSubscriptionWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickMessageSubscriptionEdit() {
        var buttonId = 'btnMessageSubscriptionEdit' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('MessageSubscriptionTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/system/MessageSubscription_load.action?messageSubscription.id='+selected.id;
                fw.post(url, null, function(data){
                    initMessageSubscriptionWindow(data);
                    process.afterClick();
                }, function() {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 删除事件
     */
    function onClickMessageSubscriptionDelete() {
        var buttonId = 'btnMessageSubscriptionDelete' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('MessageSubscriptionTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + '/system/MessageSubscription_delete.action?messageSubscription.sid='+selected.sid;
                    fw.post(url, null, function(data) {
                        process.afterClick();
                        fw.datagridReload('MessageSubscriptionTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }


    /**
     *
     初始化选择用户编号
     */
    function initUserName() {
        //设置按钮ID
        var textID = "#userId" + token;
        //按钮点击事件
        $(textID).bind('click', function () {
            //加载用户选择脚本
            using(SCRIPTS_ROOT + '/system/UserClass(Discard).js', function () {
                var userClass = new UserClass(token);
                userClass.windowOpenUserSelection(true, token, function (row) {
                    //获取选择的用户名字 ，ID
                    $("#userId" + token).val(row[0].id);
                });

            })
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initMessageSubscriptionWindow(data) {

        // fw.alertReturnValue(data);

        data['messageSubscription.operatorId'] = loginUser.getId();
        data['messageSubscription.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/system/MessageSubscription_Save.jsp?token=' + token;
        var windowId = 'MessageSubscriptionWindow' + token;
        fw.window(windowId, '消息订阅', 350, 250, url, function () {

            fw.getComboTreeFromKV('isEmail'+token, 'Is_Avaliable', 'k', fw.getMemberValue(data, 'messageSubscription.isEmail'));
            fw.getComboTreeFromKV('isSms'+token, 'Is_Avaliable', 'k', fw.getMemberValue(data, 'messageSubscription.isSms'));
            fw.getComboTreeFromKV('isTodoList'+token, 'Is_Avaliable', 'k', fw.getMemberValue(data, 'messageSubscription.isTodoList'));
            fw.getComboTreeFromKV('messageTypeId'+token, 'System_MessageType', 'k', fw.getMemberValue(data, 'messageSubscription.messageTypeId'));
            initUserName();
            //提交事件
            onClickMessageSubscriptionSubmit();


            // 加载数据
            fw.formLoad('formMessageSubscription' + token, data);

        });
    }

    /**
     * 数据提交事件
     */
    function onClickMessageSubscriptionSubmit() {
        var buttonId = 'btnMessageSubscriptionSubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = 'formMessageSubscription' + token;
            var url = WEB_ROOT + '/system/MessageSubscription_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('MessageSubscriptionTable'+token);
                fw.windowClose('MessageSubscriptionWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickMessageSubscriptionSearch() {
        var buttonId = 'btnMessageSubscriptionSearchSubmit' + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = 'MessageSubscriptionTable'+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            params["messageSubscriptionVO.userId"] = $("#search_userId"+token).val();
            params["messageSubscriptionVO.messageTypeId"] = $("#search_messageTypeId"+token).val();
            params["messageSubscriptionVO.isEmail"] = fw.getFormValue('search_isEmail' + token, fw.type_form_combotree, fw.type_get_value);
            params["messageSubscriptionVO.isSms"] = fw.getFormValue('search_isSms' + token, fw.type_form_combotree, fw.type_get_value);
            params["messageSubscriptionVO.isTodoList"] = fw.getFormValue('search_isTodoList' + token, fw.type_form_combotree, fw.type_get_value);
            params["messageSubscriptionVO.messageTypeId"] = fw.getFormValue('search_messageTypeId' + token, fw.type_form_combotree, fw.type_get_value);

            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickMessageSubscriptionSearchReset() {
        var buttonId = 'btnMessageSubscriptionSearchReset' + token;
        fw.bindOnClick(buttonId, function(process) {
            // 清空时间文本框
            fw.combotreeClear('#search_isEmail'+token);
            fw.combotreeClear('#search_isSms'+token);
            fw.combotreeClear('#search_isTodoList'+token);
            fw.combotreeClear('#search_messageTypeId'+token);
            $("#search_userId"+token).val('');
            $("#search_messageTypeId"+token).val('');
        });
    }
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initModuleWithSelect:function(callback4SelectIn) {
            var url =  WEB_ROOT + '/modules/system/MessageSubscription_Select.jsp?token='+token;
            var selectionWindowId = 'SelectWindow' + token;
            fw.window(selectionWindowId, '选择窗口',1000, 500, url, function() {
                callback4Select = callback4SelectIn;
                initAll();
                $('#MessageSubscriptionTable'+token).datagrid({toolbar:[]});
            }, null);
        }
    };
};