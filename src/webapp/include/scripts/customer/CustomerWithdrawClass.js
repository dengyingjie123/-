var CustomerWithdrawClass = function (token, obj)  {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickCustomerWithdrawSearch();
        // 初始化查询重置事件
        onClickCustomerWithdrawSearchReset();
        // 初始化列表
        initTableCustomerWithdrawTable();
    }

    /**
     * 加载列表事件
     */
    function initTableCustomerWithdrawTable() {
        var strTableId = 'CustomerWithdrawTable'+token;
        var url = WEB_ROOT + "/customer/CustomerWithdraw_list.action";
        $('#'+strTableId).datagrid({
            title: '客户提现',
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
                { field: 'customerName', title: '客户名称', width: 15 },
                { field: 'money', title: '金额', width: 15 },
                { field: 'time', title: '提现日期', width: 30 },
                { field: 'status', title: '状态', formatter: function (value, row, index) {
                    //判断数据的格式
                    //未充值
                    if("0" ==  value){
                        return "未提现";
                    }else if("1" == value){
                        return "提现成功";
                    }else if("2" == value){
                        return "提现失败";
                    }
                }},
                { field: 'customerIP', title: '客户IP', width: 30 },
                { field: 'rate', title: '费率', width: 30 },
                { field: 'fee', title: '提现手续费', width: 30 },
                { field: 'feeModifed', title: '修改后的提现手续费', width: 30 },
                { field: 'moneyTransfer',title:'实际到账金额',width:30},
                { field: 'operatorName', title: '操作人', width: 30 }
            ]],
            toolbar:[{
                id:'btnCustomerWithdrawAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnCustomerWithdrawEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnCustomerWithdrawDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化添加事件
                onClickCustomerWithdrawAdd();
                // 初始化修改事件
                onClickCustomerWithdrawEdit();
                // 初始化删除事件
                onClickCustomerWithdrawDelete();
            }
        });
    }

    /**
     * 初始化弹窗
     * @param data
     */
    function initWindowCustomerWithdraw(data) {

        data["customerWithdraw.operatorId"] = loginUser.getId();
        data["customerWithdraw.operatorName"] = loginUser.getName();
        fw.textFormatCurrency('money'+token);
        fw.textFormatCurrency('rate'+token);
        fw.textFormatCurrency('fee'+token);
        fw.textFormatCurrency('moneyTransfer'+token);
        var url =  WEB_ROOT + "/modules/customer/CustomerWithdraw_Save.jsp?token="+token;
        var windowId = "CustomerWithdrawWindow" + token;
        fw.window(windowId, '客户提现信息', 450, 380, url, function() {
            // 初始化客户名称窗口
            onClickCheckCustomer();
            onClickInputCustomer();
            // 初始化表单提交事件
            onClickCustomerWithdrawSubmit();
            fw.formLoad('formCustomerWithdraw'+token, data);
        }, null);
    }

    /**
     * 删除事件
     */
    function onClickCustomerWithdrawDelete() {
        var buttonId = "btnCustomerWithdrawDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerWithdrawTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/customer/CustomerWithdraw_delete.action?customerWithdraw.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('CustomerWithdrawTable'+token);
                        process.afterClick();
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 添加事件
     */
    function onClickCustomerWithdrawAdd(){
        var buttonId = "btnCustomerWithdrawAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowCustomerWithdraw({});
        });
    }

    /**
     * 修改事件
     */
    function onClickCustomerWithdrawEdit() {
        var butttonId = "btnCustomerWithdrawEdit" + token;
        fw.bindOnClick(butttonId, function(process) {

            fw.datagridGetSelected('CustomerWithdrawTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerWithdraw_load.action?customerWithdraw.id="+id;
                fw.post(url, null, function(data){
                    data["customerWithdraw.customerName"] = selected.customerName;
                    data['customerWithdraw.customerId'] = selected.customerId;
                    initWindowCustomerWithdraw(data);
                    process.afterClick();
                }, function() {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 查询事件
     */
    function onClickCustomerWithdrawSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId,function(){
            var strTableId = "CustomerWithdrawTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            //获取客户名称
            params["customerWithdrawVO.customerName"] = $('#search_CustomerName'+token).val();

            //获取提现时间
            params["customerWithdrawVO_time_Start"] = fw.getFormValue('search_Time_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["customerWithdrawVO_time_End"] = fw.getFormValue('search_Time_End'+token, fw.type_form_datebox, fw.type_get_value);
            $( '#' + strTableId).datagrid('load');
        })
    }

    /**
     * 查询重置事件
     */
    function onClickCustomerWithdrawSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 重置客户名称参数
            $('#search_CustomerName' + token).val("");
            // 重置时间参数
            $('#search_Time_Start'+token).datebox("setValue", '');
            $('#search_Time_End'+token).datebox("setValue", '');
        });
    }

    /**
     * 数据提交事件
     */
    function onClickCustomerWithdrawSubmit() {
        var buttonId = "btnCustomerWithdrawSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formCustomerWithdraw" + token;
            var url = WEB_ROOT + "/customer/CustomerWithdraw_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                fw.CurrencyFormatText('money'+token);
                fw.CurrencyFormatText('rate'+token);
                fw.CurrencyFormatText('fee'+token);
                fw.CurrencyFormatText('moneyTransfer'+token);
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("CustomerWithdrawTable"+token);
                fw.windowClose('CustomerWithdrawWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }



    //客户名称文本框点击弹出客户列表事件
    function onClickInputCustomer() {
        $('#customerName' + token).bind('click', function () {
            //获取客户列表的链接
            var url = WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function () {
                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT + '/customer/CustomerListSelectClass.js', function () {
                    var obj = new CustomerWithdrawClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token, obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
    }

    //查询客户事件
    function onClickCheckCustomer() {
        $('#btnCheckCustomer' + token).bind('click', function () {
            //获取客户列表的链接
            var url = WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function () {

                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT + '/customer/CustomerListSelectClass.js', function () {
                    var obj = new CustomerWithdrawClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token, obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
    };


    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }, loadCustomerInfo: function (customerId, customerName, attribute, accountId, bankName) {
            $("#customerId" + token).val(customerId);
            $("#customerName" + token).val(customerName);
        }
    };
}