/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/13/15
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
var CustomerFeedbackClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        initFormCustomerFeedbackSearch();
        onClickCustomerFeedbackSearch();

        // 初始化查询重置事件
        onClickCustomerFeedbackSearchReset();

        // 初始化表格
        initTable();
    }

    var OPNAME_ADD = 0;
    /**
     * 修改事件
     */
    function onClickCustomerFeedbackAdd() {
        var butttonId = "btnAdd" + token;
        fw.bindOnClick(butttonId, function(process) {
            // alert(butttonId);
            var data = {};
            initWindowCustomerFeedbackWindow(data);
        });
    }

    var OPNAME_EDIT = 0;
    /**
     * 修改事件
     */
    function onClickCustomerFeedbackEdit() {
        var butttonId = "btnEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('Table'+token, function(selected){
                var id = selected.id;
                //访问url 更改传入ID
                var url = WEB_ROOT + "/customer/CustomerFeedback_loadSalesMan.action?customerFeedback.id="+selected.id;
                fw.post(url, null, function(data) {
                    // 其他传入参数
                    // data["oc.orderNum"]=selected.orderNum;
                    initWindowCustomerFeedbackWindow(data);
                }, null);
            })
        });
    }

    /**
     * 查询事件
     */
    function onClickCustomerFeedbackSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "Table"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            //alert(JSON.stringify(params));
            //alert($("#search_user_name"+token).val());
            // 补充初始化代码
            // params["customerFeedback.typeId"] = $("#search_typeId"+token).val();
            params["customerFeedback.typeId"] = fw.getFormValue('search_typeId'+token, fw.type_form_combotree, fw.type_get_value);
            params["customerFeedback.content"] = $("#search_content"+token).val();
            params["customerFeedback.time_Start"] = fw.getFormValue('search_time_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["customerFeedback.time_End"] = fw.getFormValue('search_time_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["customerFeedback.customerName"] = $("#search_customerName"+token).val();
            params["customerFeedback.feedbackManName"] = $("#search_saleManName"+token).val();
            // alert(JSON.stringify(params));

            $( '#' + strTableId).datagrid('load');


            fw.treeClear()
        });
    }

    /**
     * 数据提交事件
     */
    function onClickCustomerFeedbackSubmit() {
        var buttonId = "btnCustomerFeedbackSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            // alert("开始提交");
            var formId = "formCustomerFeedback" + token;
            // url
            var url = WEB_ROOT + "/customer/CustomerFeedback_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("Table"+token);
                fw.windowClose('CustomerFeedbackWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 删除事件
     */
    function onClickCustomerFeedbackDelete() {
        var buttonId = "btnDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('Table'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    // 更改url
                    var url = WEB_ROOT + "/customer/CustomerFeedback_delete.action?customerFeedback.id="+selected.id;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('Table'+token);
                    }, null);
                }, null);
            });
        });
    }

    function initFormCustomerFeedbackSearch() {
        // fw.getComboTreeFromKV('search_InOrOut'+token, 'OA_Finance_MoneyLog_InOrOut', 'k','-2');
        fw.getComboTreeFromKV('search_typeId'+token, 'CRM_CustomerFeedbackType', 'k', '-2');
    }

    /**
     * 查询重置事件
     */
    function onClickCustomerFeedbackSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_content"+token).val('');
            $('#search_time_Start'+token).datebox("setValue", '');
            $('#search_time_End'+token).datebox("setValue", '');
            $("#search_customerName"+token).val('');
            $("#search_saleManName"+token).val('');
            fw.combotreeClear('search_typeId'+token);
        });
    }


    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowCustomerFeedbackWindow(data) {

        data["customerFeedback.operatorId"] = loginUser.getId();
        data["customerFeedback.operatorName"] = loginUser.getName();
        data["customerFeedback.feedbackManName"] = loginUser.getName();


        // 初始化其他表单
        data = fw.setDefaultValue(data, "customerFeedback.time", fw.getTimeToday());
        data = fw.setDefaultValue(data, "customerFeedback.feedbackManId", loginUser.getId());

        var url =  WEB_ROOT + "/modules/customer/CustomerFeedback_Save.jsp?token="+token;
        var windowId = "CustomerFeedbackWindow" + token;

        fw.window(windowId, '我的日志', 450, 320, url, function() {

            // 初始化控件

            // 初始化类型combotree
            fw.getComboTreeFromKV('typeId'+token, 'CRM_CustomerFeedbackType', 'k', fw.getMemberValue(data, 'customerFeedback.typeId'));
            //初始化查询客户
            onClickCheckCustomer();

            // 初始化表单提交事件
            onClickCustomerFeedbackSubmit();

            // 加载数据
            fw.formLoad('formCustomerFeedback'+token, data);
        }, null);
    }

    function initTable() {
        var strTableId = 'Table'+token;
        var url = WEB_ROOT+"/customer/CustomerFeedback_list.action";

        $('#'+strTableId).datagrid({
            title: '我的日志',
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
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'typeName', title: '日志类型', width: 30 },
                { field: 'customerName', title: '客户', width: 30 },
                { field: 'content', title: '内容', width: 80 },
                { field: 'feedbackManName', title: '理财师', width: 30 },
                { field: 'time', title: '时间', width: 30 }
            ]],
            toolbar:[{
                id:'btnAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                onClickCustomerFeedbackAdd();
                onClickCustomerFeedbackEdit();
                onClickCustomerFeedbackDelete();
            }
        });
    }

    /**
     * 查询客户事件
     */
    function onClickCheckCustomer(){
        //根据ID绑定点击事件
        $('#cname' + token).bind('focus', function () {
            //获取客户列表的链接
            var url =  WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function() {
                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT+'/customer/CustomerListSelectClass.js', function () {
                    var obj = new CustomerFeedbackClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token, obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
    }
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        },//将返回的数据判定到文本框中
        loadCustomerInfo:function(customerId,customerName,accountId,accountName){
            //绑定文本框数据
            if(customerId!=''){
                $('#customerId'+token).val(customerId);
                $('#cname'+token).val(customerName);
            }
            if(accountId != ''){
                $('#accountId'+token).val(accountId);
                $('#accountName'+token).val(accountName);
            }
        }

    };
};