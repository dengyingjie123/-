
var CustomerAccountClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll(obj,calendar) {
        // 初始化表格
//        alert(obj);
        initTableCustomerAccountTable(obj);
        initArea(obj,calendar);
    }
    function initArea(obj,calendar){
        //if(obj==null&&calendar==null){
        //    $("#btnCustomerAccountAdd"+token).remove();
        //    $("#btnCustomerAccountEdit"+token).remove();
        //    $("#btnCustomerAccountDelete"+token).remove();
        //}else if(obj!=null&&calendar!=null){
        //    $("#btnCustomerAccountAdd"+token).remove();
        //    $("#btnCustomerAccountEdit"+token).remove();
        //    $("#btnCustomerAccountDelete"+token).remove();
        //}
    }
    /**
     * 初始化表格
     */
    function initTableCustomerAccountTable(obj) {
        var id=obj;
        var strTableId = 'CustomerAccountTable'+token;
        var url = WEB_ROOT+"/customer/CustomerAccount_list.action?customerAccount.customerId="+id;

        $('#'+strTableId).datagrid({
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[3],
            pageSize:3,
            rownumbers:true,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch(e) {
                }
            },
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            columns: [[
                { field: 'sid', title: '序号', hidden:true, width: 30 },
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'customerId', title: '客户编号', hidden:true, width: 30 },
                { field: 'bank', title: '开户行'},
                { field: 'name', title: '账户名称'},
                { field: 'number', title: '账号'},
                { field: 'bankBranchName', title: '开户支行名称'},
                { field: 'bankCode', title: '银行代码'},
                { field: 'mobile', title: '银行预留手机号'},
                { field: 'cityCode', title: '城市代码'}
            ]],
            toolbar: [{
                id:'btnCustomerAccountAdd'+token,
                text:'添加',
                iconCls: 'icon-add'
            },{
                id:'btnCustomerAccountEdit'+token,
                text:'修改',
                iconCls: 'icon-edit'
            },{
                id:'btnCustomerAccountDelete'+token,
                text:'删除',
                iconCls: 'icon-remove'
            }],
            onLoadSuccess:function() {
                onClickCustomerAccountAdd(obj);
                onClickCustomerAccountDelete();
                onClickCustomerAccountEdit(obj);
            }
        });
    }



    function initTableCustomerAccountSelectTable(customerId) {

        var strTableId = 'CustomerAccountTable'+token;
        var url = WEB_ROOT+"/customer/CustomerAccount_list.action?customerAccount.customerId="+customerId;

        $('#'+strTableId).datagrid({
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[3],
            pageSize:3,
            rownumbers:true,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch(e) {
                }
            },
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            columns: [[
                { field: 'sid', title: '序号', hidden:true, width: 30 },
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'customerId', title: '客户编号', hidden:true, width: 30 },
                { field: 'bank', title: '开户行'},
                { field: 'name', title: '账户名称'},
                { field: 'number', title: '账号'},
                { field: 'bankBranchName', title: '开户支行名称'},
                { field: 'bankCode', title: '银行代码'},
                { field: 'cityCode', title: '城市代码'}
            ]],
            onLoadSuccess:function() {

            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowCustomerAccountWindow(data,obj) {

        data["customerAccount.OperatorId"] = loginUser.getId();

        var url =  WEB_ROOT + "/modules/customer/CustomerAccount_Save.jsp?token="+token;
        var windowId = "CustomerAccountWindow" + token;
        fw.window(windowId, '账户信息', 360, 300, url, function() {

            // 初始化表单提交事件
            onClickCustomerAccountSubmit();

            // 银行列表
            var URL = WEB_ROOT + "/customer/CustomerAccount_getBankList.action";
            fw.combotreeLoad('bank' + token, URL, data["customerAccount.bankCode"]);

            //加载数据
            fw.formLoad('formCustomerAccount'+token, data);
            //获取产品id
            $("#customerId"+token).val(obj);
        }, null);
    }

    function initWindowCustomerAccountSelectWindow(customerId, callback) {
        var url = WEB_ROOT + "/modules/customer/CustomerAccount_Select.jsp?token="+token;
        fw.window('customerAccountSelectWindow'+token, '选择账户信息', 600, 300, url, function(){

            initTableCustomerAccountSelectTable(customerId);

            fw.bindOnClick('btnSelectedCustomerAccount'+token, function(){

                fw.datagridGetSelected('CustomerAccountTable'+token, function(selected){
                    callback(selected);

                    fw.windowClose('customerAccountSelectWindow'+token);
                });

            });


        },null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickCustomerAccountAdd(obj) {

        var buttonId = "btnCustomerAccountAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowCustomerAccountWindow({},obj);
        });

    }

    /**
     * 删除事件
     */
    function onClickCustomerAccountDelete() {
        var buttonId = "btnCustomerAccountDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerAccountTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/customer/CustomerAccount_delete.action?id="+selected.id;
                    //alert(url);
                    fw.post(url, null, function(data) {
                        //fw.alertReturnValue(data);
                        fw.datagridReload('CustomerAccountTable'+token);
                    }, null);
                }, null);
            });
        });
    }
    /**
     * 修改事件
     */
    function onClickCustomerAccountEdit(obj) {
        var butttonId = "btnCustomerAccountEdit" + token;
        fw.bindOnClick(butttonId, function(process) {

            fw.datagridGetSelected('CustomerAccountTable'+token, function(selected){
                var sid = selected.sid;
                var url = WEB_ROOT + "/customer/CustomerAccount_load.action?customerAccount.sid="+sid;
                fw.post(url, null, function(data){
                    initWindowCustomerAccountWindow(data,obj);
                }, null);
            })

        });
    }

    /**
     * 数据提交事件
     */
    function onClickCustomerAccountSubmit() {
        var buttonId = "btnCustomerAccountSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formCustomerAccount" + token;
            var url = WEB_ROOT + "/customer/CustomerAccount_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("CustomerAccountTable"+token);
                fw.windowClose('CustomerAccountWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(obj,calendar){
            return initAll(obj,calendar);
        },
        initModuleWithSelect:function(customerId, callback) {
            initWindowCustomerAccountSelectWindow(customerId, callback);
        }
    };
}
