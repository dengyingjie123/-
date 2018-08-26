/**
 * Created by Administrator on 2015/4/10.
 * 客户账号选择
 */

var CustomerAccountListSelectClass = function (token, obj, customerId) {

    function initAll() {

        //初始化列表
        initTableCustomerPersonalOnclick(customerId);

        //初始化选择账户
        onClickSelectedCustomerAccount();
    }

    //初始化表格事件
    function initTableCustomerPersonalOnclick(customerId) {

        //获取用户的ID
        var strTableId = 'CustomerAccountTable' + token;

        var url = WEB_ROOT + "/customer/CustomerAccount_list.action?customerAccount.customerId=" + customerId;
        $('#' + strTableId).datagrid({
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [5, 10, 20],
            pageSize: 5,
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
                    { field: 'sid', title: '序号', hidden: true, width: 30 },
                    { field: 'id', title: '编号', hidden: true, width: 30 },
                    { field: 'customerId', title: '客户编号', hidden: true, width: 30 },
                    { field: 'bank', title: '开户行', width: 30 },
                    { field: 'name', title: '账户名称', width: 30 },
                    { field: 'number', title: '账号', width: 30 },
                    { field: 'bankBranchName', title: '开户行', hidden: true, width: 30 },
                    { field: 'bankCode', title: '银行代码', hidden: true, width: 30 }
                ]
            ]
        });
    }


    /**
     * 初始化选择客户按钮
     */
function onClickSelectedCustomerAccount() {
    var buttonId = "btnSelectedCustomerAccount" + token;
    fw.bindOnClick(buttonId, function (process) {

        var tableName = "CustomerAccountTable" + token;
        fw.datagridGetSelected(tableName, function (selected) {
           //var accountId = selected.id;
            var accountId = selected.name;
            //调用加载文本框函数
            obj.loadCustomerInfo('', '', '', accountId, '');
            fwCloseWindow('CustomerAccountListSelectWindow' + token);
        });
    });
}

    return{
        initModule: function () {

            return initAll();
        }
    }
}
