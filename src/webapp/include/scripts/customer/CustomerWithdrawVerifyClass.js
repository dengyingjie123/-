/**
 * Created by 张舜清 on 7/19/2015.
 */
var CustomerWithdrawVerifyClass = function (token) {

    var ActionUrl = WEB_ROOT  + '/customer';
    var WebPageUrl = WEB_ROOT + '/modules/customer';
    var callback4Select = undefined;

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickCustomerWithdrawVerifySearch();
        // 初始化查询重置事件
        onClickCustomerWithdrawVerifySearchReset();

        // 初始化表格
        initCustomerWithdrawVerifyTable();

        // 初始化查询表单
        initCustomerWithdrawVerifySearchForm();

        // 初始化选择窗口按钮
        onClcikSelectDone()
    }

    /**
     * 初始化查询表单
     */
    function initCustomerWithdrawVerifySearchForm() {
    }


    function onClcikSelectDone() {
        fw.bindOnClick('btnSelect'+token, function(press) {
            var strTableId = 'CustomerWithdrawVerifyTable' + token;
            var data = $('#' + strTableId).datagrid('getSelected');
            if (fw.checkIsFunction(callback4Select)) {
                callback4Select(data);
            }
            fwCloseWindow('SelectWindow'+token);
        });
    }

    // 构造初始化表格脚本
    function initCustomerWithdrawVerifyTable() {
        var strTableId = 'CustomerWithdrawVerifyTable' + token;

        var url = WEB_ROOT + '/customer/CustomerWithdrawVerify_list.action';

        $('#' + strTableId).datagrid({
            title: '客户提现审查',
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
                    { field: 'sid', title: 'SID',hidden:true},
                    { field: 'id', title: 'ID',hidden:true},
                    { field: 'state', title: 'State',hidden:true},
                    { field: 'operatorId', title: 'OperatorID',hidden:true},
                    { field: 'operateTime', title: 'OperateTime',hidden:true},
                    { field: 'customerId', title: '客户编号',hidden:true},
                    { field: 'customerLoginName', title: '客户用户名',hidden:false},
                    { field: 'customerName', title: '客户姓名',hidden:false},
                    { field: 'withdrawMoney', title: '提现金额',hidden:false},
                    { field: 'examineUserId1', title: '审核人一编号',hidden:true},
                    { field: 'examineUserName1', title: '审核人一',hidden:false},
                    { field: 'examineUserId2', title: '审核人二编号',hidden:true},
                    { field: 'examineUserName2', title: '审核人二',hidden:false},
                    { field: 'examineUserId3', title: '审核人三编号',hidden:true},
                    { field: 'examineUserName3', title: '审核人三',hidden:false},
                    { field: 'remittanceUserId', title: '打款人编号',hidden:true},
                    { field: 'remittanceUserName', title: '打款人',hidden:false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnCustomerWithdrawVerifyAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnCustomerWithdrawVerifyEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnCustomerWithdrawVerifyDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickCustomerWithdrawVerifyAdd();
                onClickCustomerWithdrawVerifyEdit();
                onClickCustomerWithdrawVerifyDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickCustomerWithdrawVerifyAdd() {
        var buttonId = 'btnCustomerWithdrawVerifyAdd' + token;
        fw.bindOnClick(buttonId, function(process) {
            initCustomerWithdrawVerifyWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickCustomerWithdrawVerifyEdit() {
        var buttonId = 'btnCustomerWithdrawVerifyEdit' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerWithdrawVerifyTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id
                alert(id);

                var url = ActionUrl + '/CustomerWithdrawVerify_load.action?customerWithdrawVerify.id='+selected.id;
                fw.post(url, null, function(data){
                    data['customerLoginName'] = selected.customerLoginName;
                    data['customerName'] = selected.customerName;

                    initCustomerWithdrawVerifyWindow(data);
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
    function onClickCustomerWithdrawVerifyDelete() {
        var buttonId = 'btnCustomerWithdrawVerifyDelete' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerWithdrawVerifyTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){

                    var url = ActionUrl + '/CustomerWithdrawVerify_delete.action?customerWithdrawVerify.sid='+selected.sid;
                    fw.post(url, null, function(data) {
                        process.afterClick();
                        fw.datagridReload('CustomerWithdrawVerifyTable'+token);
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
    function initCustomerWithdrawVerifyWindow(data) {

//        fw.alertReturnValue(data);

        data['customerWithdrawVerify.operatorId'] = loginUser.getId();
        data['customerWithdrawVerify.operatorName'] = loginUser.getName();

        var url = WebPageUrl + '/CustomerWithdrawVerify_Save.jsp?token=' + token;
        var windowId = 'CustomerWithdrawVerifyWindow' + token;
        fw.window(windowId, '客户提现审查', 350, 300, url, function () {
            //提交事件
            onClickCustomerWithdrawVerifySubmit();


            // 加载数据
            fw.formLoad('formCustomerWithdrawVerify' + token, data);

        });
    }

    /**
     * 数据提交事件
     */
    function onClickCustomerWithdrawVerifySubmit() {
        var buttonId = 'btnCustomerWithdrawVerifySubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = 'formCustomerWithdrawVerify' + token;

            var url = ActionUrl + '/CustomerWithdrawVerify_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('CustomerWithdrawVerifyTable'+token);
                fw.windowClose('CustomerWithdrawVerifyWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickCustomerWithdrawVerifySearch() {
        var buttonId = 'btnCustomerWithdrawVerifySearchSubmit' + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = 'CustomerWithdrawVerifyTable'+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;




            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickCustomerWithdrawVerifySearchReset() {
        var buttonId = 'btnCustomerWithdrawVerifySearchReset' + token;
        fw.bindOnClick(buttonId, function(process) {
            // 清空时间文本框
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
            var url =  WebPageUrl + '/CustomerWithdrawVerify_Select.jsp?token='+token;
            var selectionWindowId = 'SelectWindow' + token;
            fw.window(selectionWindowId, '选择窗口',1000, 500, url, function() {
                callback4Select = callback4SelectIn;
                initAll();
                $('#CustomerWithdrawVerifyTable'+token).datagrid({toolbar:[]});
            }, null);
        }
    };
};