var CustomerDepositClass = function(token,obj){
    /**
     * 初始化主页面控件
     */
    function initALL(){
        // 初始化查询事件
        onClickCustomerDepositSearch();
        // 初始化查询重置事件
        onClickCustomerDepositResst()
        // 初始化表格
        initTableCustomerDepositTable()
    }

    /**
     * 加载列表事件
     */
    function initTableCustomerDepositTable(){
        //alert('begin');
        var strTableId = 'CustomerDepositTable'+token;
        var url = WEB_ROOT+"/customer/CustomerDeposit_list.action";
        $('#' + strTableId).datagrid({
            title:'客户充值',
            url:url,
            queryParams:{
                //此处可以定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[15,30,60],
            pageSize:15,
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
            frozenColumns:[[ //固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            columns:[[
                { field: 'customerName', title: '客户名称', width: 30 },
                { field: 'money', title: '金额', width: 30 },
                { field: 'rate', title: '费率', width: 30 },
                { field: 'time', title: '时间'},
                { field: 'status', title: '状态', formatter: function (value, row, index) {
                        //判断数据的格式
                        //未充值
                        if("0" ==  value){
                            return "未充值";
                        }else if("1" == value){
                            return "充值成功";
                        }else if("2" == value){
                            return "充值失败";
                        }
                }},
                { field: 'customerIP', title: '客户IP', width: 30 },
                { field: 'fee', title: '充值手续费', width: 30 },
                { field: 'moneyTransfer', title: '实际到账金额', width: 30 },
                { field: 'fromAccountId', title: '充值来源账户', width: 30 },
                { field: 'operateTime', title: '操作时间'}
            ]],
            toolbar:[{
                id:'btnCustomerDepositAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },
//                {
//                id:'btnCustomerDepositEdit'+token,
//                text:'修改',
//                iconCls:'icon-edit'
//            },
                {
                id:'btnCustomerDepositDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化添加事件
                onClickCustomerDepositAdd();
                // 初始化删除事件
                onClickCustomerDepositDelete();
                // 初始化修改事件
//                onClickCustomerDepositEdit();
            }
        });
    }

    /**
     * 查询事件
     */
    function onClickCustomerDepositSearch(){
        var buttonId = "btnSearchCustomerDeposit" + token;
        //alert("begin")
        fw.bindOnClick(buttonId,function(process){
            var strTableId = "CustomerDepositTable" + token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params ["customerDepositVO.customerName"] = $('#search_CustomerName' + token).val();
            //alert(JSON.stringify(params));
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickCustomerDepositResst(){
        var buttonId = "btnResetCustomerDeposit" + token;
        fw.bindOnClick(buttonId,function(process){
            $('#search_CustomerName'+token).val("");
        });
    }

    /**
     * 添加事件
     */
    function onClickCustomerDepositAdd(){
        var buttonId = "btnCustomerDepositAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initCustomerDepositWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickCustomerDepositEdit(){
        //alert("修改按钮单击了")
        var butttonId = "btnCustomerDepositEdit" + token;
        fw.bindOnClick(butttonId, function(process){
            fw.datagridGetSelected('CustomerDepositTable' + token,function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerDeposit_load.action?customerDeposit.id="+id;
                fw.post(url,null,function(data){

                    data["customerDepositVO.customerName"] = selected.customerName;
                    data["customerDepositVO.operatorName"] = selected.operatorName;
                    data['customerDeposit.customerId'] = selected.customerId;
                    initCustomerDepositWindow(data);
                    process.afterClick();
                },function(){
                    process.afterClick();
                });

            })
        });
    }

    /**
     * 删除事件
     */
    function onClickCustomerDepositDelete() {
        var buttonId = "btnCustomerDepositDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerDepositTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/customer/CustomerDeposit_delete.action?customerDeposit.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('CustomerDepositTable'+token);
                    }, null);
                }, null);
            });
        });
    }


    /**
     * 弹出窗口
     * @param data
     */
    function initCustomerDepositWindow(data){
        data["customerDeposit.operatorId"] = loginUser.getId();
        data["customerDeposit.operatorName"] = loginUser.getName();

        var url = WEB_ROOT + "/modules/customer/CustomerDeposit_Save.jsp?token=" + token;
        var windowId =  "CustomerDepositWindow" + token;
        fw.window(windowId,'客户充值',450, 380, url,function(){
            fw.textFormatCurrency('money'+token);
            fw.textFormatCurrency('fee'+token);
            fw.textFormatCurrency('moneyTransfer'+token);

            // 初始化客户名称窗口
            onClickCheckCustomer();
            onClickInputCustomer();

            // 初始化提交事件
            onClickCustomerDepositSubmit();
            fw.formLoad('formCustomerDeposit'+token,data);
        },null);
    }

    /**
     *  提交事件
     */
    function onClickCustomerDepositSubmit(){
        var buttonId = "btnCustomerDepositSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            fw.CurrencyFormatText('money'+token);
            fw.CurrencyFormatText('fee'+token);
            fw.CurrencyFormatText('moneyTransfer'+token);
            var formId = "formCustomerDeposit" + token;
            var url = WEB_ROOT + "/customer/CustomerDeposit_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("CustomerDepositTable"+token);
                fw.windowClose('CustomerDepositWindow'+token);
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
                    var obj = new CustomerDepositClass(token);
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
                    var obj = new CustomerDepositClass(token);
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
            return initALL();
        }, loadCustomerInfo: function (customerId, customerName, attribute, accountId, bankName) {
            $("#customerId" + token).val(customerId);
            $("#customerName" + token).val(customerName);
        }
    };
};