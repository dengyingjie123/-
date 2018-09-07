/**
 * Created by Jepson on 2015/7/3.
 */

var ReceiverSmsClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化表格
//        alert(obj);
        initTableSenderSmsTable();
    }

    /**
     * 初始化表格
     */
    function initTableSenderSmsTable(obj) {
        var id = obj;
        var strTableId = 'SmsReceiverTable' + token;
        var url = WEB_ROOT +  '/oa/sms/Sms_list.action?type=ReceiveType&loginId='+loginUser.getId();

        $('#' + strTableId).datagrid({
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [10, 15, 20],
            pageSize: 10,
            rownumbers: true,
            loadFilter: function (data) {
                try {
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
                    { field: 'sid', title: 'sid', hidden: true, sortable: true},
                    { field: 'id', title: 'id', hidden: true, sortable: true},
                    { field: 'state', title: 'state', hidden: true, sortable: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'subject', title: '主题', hidden: false, sortable: true},
                    { field: 'content', title: '内容', hidden: false, sortable: true},
                    { field: 'type', title: '类型', hidden: true, sortable: true},
                    { field: 'typeName', title: '类型', hidden: false, sortable: true},
                    { field: 'senderId', title: '发送者编号', hidden: true, sortable: true},
                    { field: 'senderName', title: '发送者名称', hidden: false, sortable: true},
                    { field: 'senderMobile', title: '发送者手机', hidden: false, sortable: true},
                    { field: 'receiveTime', title: '接收时间', hidden: false, sortable: true},
                    { field: 'feedbackStatus', title: '反馈状态', hidden: false, sortable: true},
                    { field: 'feedbackTime', title: '反馈时间', hidden: false, sortable: true},
                    { field: 'feedbackContent', title: '反馈内容', hidden: false, sortable: true}
                ]
            ],
            toolbar: [

                {
                    id: 'btnSmsAdd' + token,
                    text: '写信',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSmsEdit' + token,
                    text: '查看',
                    iconCls: 'icon-search'
                },
                {
                    id: 'btnSmsDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                onClickSmsAdd();
                onClickSmsEdit();
                onClickSmsDelete();
            }
        });
    }


    /**
     * 添加事件
     */
    function onClickSmsAdd() {
        var buttonId = 'btnSmsAdd' + token;
        fw.bindOnClick(buttonId, function(process) {
            initEditSmsWindow({});
        });
    }
    function initEditSmsWindow(data){
        data['sms.operatorId'] = loginUser.getId();
        data['sms.operatorName'] = loginUser.getName();
        data['sms.senderId']=loginUser.getId();
        data['sms.senderName']=loginUser.getName();
        data['sms.senderMobile']=loginUser.getMobile();
        var url = WEB_ROOT + '/modules/oa/sms/Sms_Save.jsp?token=' + token;
        var windowId = 'smsWindow' + token;
        fw.window(windowId, '写信', 650, 350, url, function () {
            fw.getComboTreeFromKV('type'+token, 'System_SmsType', 'k', fw.getMemberValue(data, 'sms.type'));
            //选择用户
            initUserName();
            //提交事件
            onClickSmsSubmit();
            // 加载数据
            fw.formLoad('formSms' + token, data);

        });
    }

    /**
     * 查看事件
     */
    function onClickSmsEdit() {
        var buttonId = 'btnSmsEdit' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('SmsReceiverTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/sms/Sms_load.action?sms.id='+selected.id;
                fw.post(url, null, function(data){
                    initSmsWindow(data);
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
    function onClickSmsDelete() {
        var buttonId = 'btnSmsDelete' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('SmsReceiverTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + '/oa/sms/Sms_delete.action?sms.sid='+selected.sid;
                    fw.post(url, null, function(data) {
                        process.afterClick();
                        fw.datagridReload('SmsReceiverTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }
    /**
     * 初始化弹出窗口
     * @param data
     */
    function initSmsWindow(data) {

        // fw.alertReturnValue(data);

        data['sms.operatorId'] = loginUser.getId();
        data['sms.operatorName'] = loginUser.getName();
        var url = WEB_ROOT + '/modules/oa/sms/SmsReceive_Save.jsp?token=' + token;
        var windowId = 'smsWindow' + token;
        fw.window(windowId, '信息', 650, 350, url, function () {
            fw.getComboTreeFromKV('type'+token, 'System_SmsType', 'k', fw.getMemberValue(data, 'sms.type'));
            // 加载数据
            fw.formLoad('formSms' + token, data);

        });
    }
    /**
     *
     初始化选择用户
     */
    function initUserName() {
        //设置按钮ID
        var textID = "#receiverName" + token;
        //按钮点击事件
        $(textID).bind('click', function () {
            //加载用户选择脚本
            using(SCRIPTS_ROOT + '/system/UserClass(Discard).js', function () {
                var userClass = new UserClass(token);
                userClass.windowOpenUserSelection(true, token, function (row) {
                    //获取选择的用户名字 ，ID
                    $("#receiverName" + token).val(row[0].name);
                    $("#receiverId" + token).val(row[0].id);
                    $("#receiverMobile" + token).val(row[0].mobile);
                });

            })
        });
    }
    /**
     * 数据提交事件
     */
    function onClickSmsSubmit() {
        var buttonId = 'btnSmsSubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = 'formSms' + token;
            var url = WEB_ROOT + '/oa/sms/Sms_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('SmsReceiverTable'+token);
                fw.windowClose('smsWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }
    return{
        /**
         * boot.js 加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };

}

