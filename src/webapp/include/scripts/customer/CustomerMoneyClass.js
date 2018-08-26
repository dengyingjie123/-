/**
 * Created by Administrator on 2015/4/1.
 */
/***
 * 客户资金
 */
var CustomerMoneyClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 查询事件
        onClickCustomerMoneySearch();

        // 初始化查询重置事件
        initCustomerMoneySearchReset();

        // 初始化表格
        initCustomerMoneyTable();
    }
    // -----------------------------------------------------------------------------
// 构造初始化表格脚本
    function initCustomerMoneyTable() {
        var strTableId = 'customerMoneyTable'+token;
        //设置URL
        var url = WEB_ROOT+"/customer/CustomerMoney_list.action";

        $('#'+strTableId).datagrid({
            title: '客户资金',
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
                { field: 'sid', title: '序号', hidden:true, width: 30 },
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'State', title: 'State', hidden:true,width: 30 },
                { field: 'customerName', title: '客户名称', width: 30 },
                { field: 'frozenMoney', title: '冻结资金', width: 30 },
                { field: 'expectedMoney', title: '待收资金', width: 30 },
                { field: 'availableMoney', title: '可用资金', width: 30 },
                { field: 'investedMoney', title: '已投资资金', width: 30 },
                { field: 'totalProfitMoney', title: '累计收益资金', width: 30 }
            ]],
            toolbar:[{
                id:'btnAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },
                {
                    id: 'btnEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                }],
            onLoadSuccess:function() {
                // 初始化事件
                onClickCustomerMoneyEdit();
                onClickCustomerMoneyAdd();
//               initCustomerMoneyEdit();
            }
        });
    }
    function onClickCustomerMoneyAdd(){
        var buttonId = "btnAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowCustomerMoneyWindow({});
        });
    }
    /**
     * 修改事件
     */
    function onClickCustomerMoneyEdit() {
        var butttonId = "btnEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('customerMoneyTable'+token, function(selected){
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerMoney_load.action?customerMoney.id="+selected.id;
                fw.post(url, null, function(data){
                     //data["customerMoney.customerName"]=selected.customerName;\
                    data["customerName"]=selected.customerName;
//                     fw.alertReturnValue(data);
                    initWindowCustomerMoneyWindow(data,null);
                }, null);
            })
        });
    }
    function initWindowCustomerMoneyWindow(data) {

        data["customerMoney.operatorId"] = loginUser.getId();
        data["customerMoney.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/customer/CustomerMoney_Save.jsp?token="+token;
        var windowId = "CustomerMoneyWindow" + token;
        fw.window(windowId, '客户资金', 400, 300, url, function() {
            fw.textFormatCurrency('frozenMoney'+token);
            fw.textFormatCurrency('expectedMoney'+token);
            fw.textFormatCurrency('availableMoney'+token);
            fw.textFormatCurrency('investedMoney'+token);
            fw.textFormatCurrency('totalProfitMoney'+token);
            // 初始化控件

            // 初始化类型combotree
            // fw.getComboTreeFromKV('typeId'+token, 'CRM_CustomerFeedbackType', 'k', fw.getMemberValue(data, 'customerFeedback.typeId'));

            // 初始化表单提交事件
            onClickCustomerMoneySubmit();

            // 加载数据
            fw.formLoad('formCustomerMoney'+token, data);
        }, null);
    }
    /**
     * 数据提交事件
     */
    function onClickCustomerMoneySubmit() {
        var buttonId = "btnCustomerMoneySubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            fw.CurrencyFormatText('frozenMoney'+token);
            fw.CurrencyFormatText('expectedMoney'+token);
            fw.CurrencyFormatText('availableMoney'+token);
            fw.CurrencyFormatText('investedMoney'+token);
            fw.CurrencyFormatText('totalProfitMoney'+token);
            var formId = "formCustomerMoney" + token;
            var url = WEB_ROOT + "/customer/CustomerMoney_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("customerMoneyTable"+token);
                fw.windowClose('CustomerMoneyWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }
    //按钮修改时间判定
    function initCustomerMoneyEdit(){
        //获取按钮ID
        var butttonId = "btnCustomerMoneyEdit" + token;

        //绑定按钮事件

        fw.bindOnClick(butttonId, function (process) {

            fw.datagridGetSelected('customerMoneyTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT+"/customer/CustomerMoney_load.action?customerMoney.id=" + id;
                fw.post(url, null, function(data) {

                    //从表格获取数据
                    //客户名称
                    //data["customerMoney.customerName"] = selected.customerName;


                    //弹出窗口
                    initCustomerMoneyWindow(data);

                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    //初始化查询事件
    function onClickCustomerMoneySearch(){
        //获取查询按钮
        var buttonId = "btnSearchCustomerMoney" + token;

        //绑定点击事件
          fw.bindOnClick(buttonId,function(process){

              //获取datagrid表格对象
              var strTableId = "customerMoneyTable" + token;
              var params= $('#'+strTableId).datagrid('options').queryParams;

              //获取查询条件
              params['customerMoneyVO.operateTime'] = fw.getFormValue('search_operateTime'+token,fw.type_form_datebox, fw.type_get_value);
              //获取客户名称
              params['customerMoneyVO.customerName'] = $("#search_customerName" + token).val();
              //获取操作员名称
              params['customerMoneyVO.operatorName'] = $("#search_operatorName" + token).val();

              //装载到datagrid中

              $('#' + strTableId).datagrid('load');
          });

    }

    /**
     * 初始化查询重置事件
     */
    function initCustomerMoneySearchReset(){
        var buttonId = "btnSearchCustomerMoneyReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            //清空时间文本框
            $('#search_operateTime' + token).datebox("setValue", '');

            //清空文本框
            $('#search_customerName' + token).val('');
            $('#search_operatorName' + token).val('');
        });
    }
    // 编写窗口初始化JS
    /**
     * 初始化弹出窗口
     * @param data
     */
    function initCustomerMoneyWindow(data) {

        //绑定当前操作员信息
        data["customerMoney.operatorId"] = loginUser.getId();
        data["customerMoney.operatorName"] = loginUser.getName();

        // URL地址
        var url =  WEB_ROOT + "/modules/customer/CustomerMoney_Save.jsp?token="+token;
        var windowId = "CustomerMoneyWindow" + token;

        //设置窗口
        fw.window(windowId, '客户资金管理', 400,350, url, function() {

            // 加载数据
            fw.formLoad('formCustomerMoney'+token, data);
        }, null);
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
