//客户支付脚本

var CustomerPaymentClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        //初始化绑定下拉框
        initFormCustomerPaymentSearch();
        // 初始化查询事件
        onClickCustomerPaymentSearch();
        // 初始化查询重置事件
        onClickCustomerPaymentSearchReset();
        // 初始化表格
        initCustomerPaymentTable();
    }

    //初始化判定下拉框
    function initFormCustomerPaymentSearch() {
        //绑定方式下拉列表
        fw.getComboTreeFromKV('search_typeName' + token, 'customer_customerPayment_Type', 'k', '-2');

        //绑定状态下拉列表
        fw.getComboTreeFromKV('search_status' + token, 'customer_customerPayment_status', 'k', '-2');

        //绑定支付方式下拉列表
        fw.getComboTreeFromKV('search_paymentTypeName' + token, 'customer_customerPayment_PaymentType', 'k', '-2')

    }

    // 构造初始化表格脚本
    function initCustomerPaymentTable() {
        var strTableId = 'CustomerPaymentTable' + token;

        var url = WEB_ROOT + "/customer/CustomerPayment_list.action";
        $('#' + strTableId).datagrid({
            title: '客户支付',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
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
                    { field: 'sid', hidden: true, title: 'sid', width: 30 },
                    { field: 'id', hidden: true, title: 'id', width: 30 },
                    { field: 'operatorName', title: '操作员', width: 30 },
                    { field: 'operateTime', title: '操作时间', width: 30 },
                    { field: 'money', title: '金额', width: 30 },
                    { field: 'feeRate', title: '费率', width: 30 },
                    { field: 'typeName', title: '方式', width: 30 },
                    { field: 'statusName', title: '状态', width: 30 },
                    { field: 'time', title: '添加时间', width: 30 },
                    { field: 'iP', title: 'IP', width: 30 },
                    { field: 'bizId', title: '传输业务编号', width: 30 },
                    { field: 'paymentAccount', title: '支付银行', width: 30 },
                    { field: 'paymentTypeName', title: '支付方式', width: 30 },
                    { field: 'paymentInfo', title: '凭证信息', width: 30 }
                ]
            ],
            toolbar: [
                {
                    id: 'btnCustomerPaymentEdit' + token,
                    text: '数据详细',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                //更新弹出详情事件
                onClickCustomerPaymentEdit();
                //删除事件
                //onClickCustomerPaymentDelete();
            }
        });
    }
    //修改事件
    function onClickCustomerPaymentEdit() {
        var buttonId = "btnCustomerPaymentEdit" + token;

        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerPaymentTable'+token, function(selected){
                //更改按钮文字。
                process.beforeClick();
                //获取ID
                var id = selected.id;
                //设置URL
                var url = WEB_ROOT + "/customer/CustomerPayment_load.action?customerPayment.id="+selected.id;
                //查询
                fw.post(url, null, function(data){
                   data["customerPayment.typeName"]=selected.typeName;
                   data["customerPayment.statusName"]=selected.statusName;
                   data["customerPayment.paymentTypeName"]=selected.paymentTypeName;

                    //初始话窗口
                    initWindowCustomerPaymentWindow(data,null);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }
    //初始化查询事件
    function onClickCustomerPaymentSearch() {
        //获取查询按钮
        var buttonId = "btnSearchCustomerPayment" + token;

        //绑定点击事件
        fw.bindOnClick(buttonId, function (process) {

            //获取datagrid表格对象
            var strTableId = "CustomerPaymentTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            //获取查询条件
            //获取方式
            params['customerPaymentVO.type'] = fw.getFormValue('search_typeName' + token, fw.type_form_combotree, fw.type_get_value);
            //获取状态
            params["customerPaymentVO.status"] = fw.getFormValue('search_status' + token, fw.type_form_combotree, fw.type_get_value);
            //获取操作员名称
            params['customerPaymentVO.operatorName'] = $("#search_operatorName" + token).val();
            //支付方式
            params['customerPaymentVO.paymentType'] = fw.getFormValue('search_paymentTypeName' + token, fw.type_form_combotree, fw.type_get_value);
            //装载到datagrid中

            $('#' + strTableId).datagrid('load');
        });

    }

    /**
     * 删除记录数据的事件
     */
    function onClickCustomerPaymentDelete(){

        //获取按钮对象
        var buttonId = "btnCustomerPaymentDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerPaymentTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
            
                    var url = WEB_ROOT + "/customer/CustomerPayment_delete.action?customerPayment.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('CustomerPaymentTable'+token);
                    }, null);
                }, null);
            });
        });
    }
    /**
     * 查询重置初始化
     */
    function onClickCustomerPaymentSearchReset() {
        var buttonId = "btnCustomerPaymentSearchReset" + token;
        fw.bindOnClick(buttonId, function (process) {

            //清空下拉列表
            fw.combotreeClear('search_typeName' + token);
            fw.combotreeClear('search_status' + token);
            fw.combotreeClear('search_paymentTypeName' + token);

            //清空文本看
            $("#search_operatorName" + token).val('');
        });
    }
    //初始话弹出窗口事件
   function initWindowCustomerPaymentWindow(data,sid){
       //设置操作员ID
       data["customerPayment.operatorId"]=loginUser.getId();
       //设置操作员姓名
       data["customerPayment.operatorName"]=loginUser.getName();

       //设置URL
       var url=WEB_ROOT+"/modules/customer/CustomerPayment_Save.jsp?token="+token;

       //设置windoID
       var windowId = "CustomerPaymentWindow"+token;

       //弹出窗口
       fw.window(windowId,"客户支付",400,400,url,function(){

           fw.formLoad('formcustomerPayment'+token, data);
       },null);

   }
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
};