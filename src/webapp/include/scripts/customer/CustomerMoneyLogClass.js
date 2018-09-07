var CustomerMoneyLogClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        initTypeTree(null,"-2");
        initStatusTree(null,"-2");
        onClickCustomerMoneyLogSearch();
        // 初始化查询重置事件
        onClickCustomerMoneyLogSearchReset();
        // 初始化表格
        initCustomerMoneyLogTable();
    }

    /**
     * 初始化下拉列表项
     * selectIndexId 为选中的项 -2 为什么都选
     */
    function initTypeTree(combotreeId,selectIndexId) {
        if(combotreeId==null){
            combotreeId="search_type" + token;
        }
        var URL = WEB_ROOT + "/customer/CustomerMoneyLog_TypeTree.action";
        fw.combotreeOnload(combotreeId,URL,function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {}
            return treeData;
        },selectIndexId);
    }
    function onClickCustomerMoneyLogSearch(){
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "CustomerMoneyLogTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["customerMoneyLogVO.content"] = $("#search_content"+token).val();
            params["customerMoneyLogVO.type"]=$('#search_type'+token).combotree('getText');
            params["customerMoneyLogVO.status"]=$('#search_status'+token).combotree('getText');


            //获取是减控件的值
            params["customerMoneyLogVO_operateTime_Start"] = fw.getFormValue('search_StartTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["customerMoneyLogVO_operateTime_End"] = fw.getFormValue('search_StartTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 初始化下拉列表项
     * selectIndexId 为选中的项 -2 为什么都选
     */
    function initStatusTree(combotreeId,selectIndexId) {
        if(combotreeId==null){
            combotreeId="search_status" + token;
        }
        var URL = WEB_ROOT + "/customer/CustomerMoneyLog_StatusTree.action";
        fw.combotreeOnload(combotreeId,URL,function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {}
            return treeData;
        },selectIndexId);
    }

    // 构造初始化表格脚本
    function initCustomerMoneyLogTable() {
        var strTableId = 'CustomerMoneyLogTable'+token;
        var url = WEB_ROOT+"/customer/CustomerMoneyLog_list.action";

        $('#'+strTableId).datagrid({
            title: '资金日志',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[15,30,60],
            pageSize: 15,
            rownumbers:true,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);

                    //alert(JSON.stringify(data));
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
                { field: 'sid', title: 'sid',hidden:true, width: 30 },
                { field: 'id', title: 'id',hidden:true, width: 30 },
                { field: 'state', title: 'state',hidden:true, width: 30 },
                { field: 'operatorId', title: 'operatorId',hidden:true, width: 30 },
                { field: 'operateTime', title: 'operateTime',hidden:true, width: 30 },
                { field: 'customerName', title: '客户名称', width: 30 },
                { field: 'type', title: '类型', width: 30 },
                { field: 'content', title: '内容', width: 30 },
                { field: 'status', title: '状态', width: 30 },
                { field: 'moduleId', title: '模块编号', width: 30 },
                { field: 'bizId', title: '业务编号', width: 30 }
            ]],
//            toolbar:[{
//                id:'btnCustomerMoneyLogAdd'+token,
//                text:'添加',
//                iconCls:'icon-add'
//            },{
//                id:'btnCustomerMoneyLogEdit'+token,
//                text:'修改',
//                iconCls:'icon-edit'
//            },{
//                id:'btnCustomerMoneyLogDelete'+token,
//                text:'删除',
//                iconCls:'icon-cut'
//            }],
            onLoadSuccess:function() {
                // 初始化事件
                onClickCustomerMoneyLogAdd();
                onClickCustomerMoneyLogEdit();
                onClickCustomerMoneyLogDelete();
            }
        });
    }
    function onClickCustomerMoneyLogAdd(){
        var buttonId = "btnCustomerMoneyLogAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initCustomerMoneyLogWindow({});
        });
    }
    /**
     * 初始化弹出窗口
     * @param data
     */
    function initCustomerMoneyLogWindow(data) {

        data["customerMoneyLog.operatorId"] = loginUser.getId();
        data["customerMoneyLog.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/customer/CustomerMoneyLog_Save.jsp?token="+token;
        var windowId = "CustomerMoneyLogWindow" + token;
        fw.window(windowId, '资金日志' +
            '窗口', 450, 360, url, function() {

            // 初始化控件

            // 初始化类型combotree
//             fw.getComboTreeFromKV('type'+token, 'Customer_MoneyLog_Type', 'k', fw.getMemberValue(data, 'customerMoneyLog.type'));
            initTypeTree("type"+token,data["customerMoneyLog.type"]);
            initStatusTree("status"+token,data["customerMoneyLog.status"]);

            // 初始化表单提交事件

            onClickCustomerMoneyLogSubmit();
            // 加载数据
            fw.formLoad('formCustomerMoneyLog'+token, data);
        }, null);
    }
    /**
     * 删除事件
     */
    function onClickCustomerMoneyLogDelete() {
        var buttonId = "btnCustomerMoneyLogDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerMoneyLogTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/customer/CustomerMoneyLog_delete.action?customerMoneyLog.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('CustomerMoneyLogTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 查询重置事件
     */
    function onClickCustomerMoneyLogSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 重置部分
            fw.combotreeClear("search_status" + token);
            fw.combotreeClear("search_type" + token);
            $('#search_StartTime_Start'+token).datebox("setValue", '');
            $('#search_StartTime_End'+token).datebox("setValue", '');
            $('#search_content'+token).datebox("setValue", '');

        });
    }
    /**
     * 修改事件
     */
    function onClickCustomerMoneyLogEdit() {
        var butttonId = "btnCustomerMoneyLogEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('CustomerMoneyLogTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerMoneyLog_load.action?customerMoneyLog.id="+selected.id;
                fw.post(url, null, function(data){
                    // data["oc.orderNum"]=selected.orderNum;
                    // fw.alertReturnValue(data);
                    initCustomerMoneyLogWindow(data,null);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });

            })
        });
    }

    /**
     * 数据提交事件
     */
    function onClickCustomerMoneyLogSubmit() {
        var buttonId = "btnCustomerMoneyLogSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formCustomerMoneyLog" + token;
            var url = WEB_ROOT + "/customer/CustomerMoneyLog_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("CustomerMoneyLogTable"+token);
                fw.windowClose('CustomerMoneyLogWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }



    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        }
    };
};
