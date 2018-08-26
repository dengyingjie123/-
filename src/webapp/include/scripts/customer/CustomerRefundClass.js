/**
 * Created by 张舜清 on 2015/8/26.
 */
var CustomerRefundClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 查询事件
        onClickCustomerRefundSearch();

        // 初始化查询重置事件
        initCustomerRefundSearchReset();

        // 初始化表格
        initCustomerRefundTable();
    }

    // 构造初始化表格脚本
    function initCustomerRefundTable() {
        var strTableId = 'customerRefundTable' + token;
        //设置URL
        var url = WEB_ROOT + "/customer/CustomerRefund_list.action";

        $('#' + strTableId).datagrid({
            title: '客户退款',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
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
                    {field: 'ck', checkbox: true},
                    { field: 'customerName', title: '客户名称' },
                    { field: 'money', title: '退款金额' },
                    { field: 'createdDatetime', title: '生成时间' },
                    { field: 'states', title: '退款状态' },
                    { field: 'refundDatetime', title: '退款时间' },
                    { field: 'refundAmount', title: '实际退款金额' }
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: '序号', hidden: true },
                    { field: 'id', title: '编号', hidden: true },
                    { field: 'State', title: 'State', hidden: true },
                    { field: 'operatorId', title: 'State', hidden: true },
                    { field: 'operateTime', title: 'State', hidden: true },
                    { field: 'reason', title: '退款原因' },
                    { field: 'customerIP', title: '客户IP' },
                    { field: 'orderId', title: '订单编号' },
                    { field: 'backId', title: '接口返回ID' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnRefund' + token,
                    text: '退款',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化退款事件
                onClickCustomerRefund();
            }
        });
    }

    // 初始化退款事件
    function onClickCustomerRefund() {
        var buttonId = "btnRefund" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('customerRefundTable' + token, function (selected) {
                var states = selected.states;
                if (states == '已退款') {
                    fw.alert('提示', '您选的记录已退款！');
                    return false;
                }

                fw.confirm('确认退款', '请确认退款【金额：' + selected.money + '】', function () {
                    var id = selected.id;
                    var url = WEB_ROOT + "/customer/CustomerRefund_refund.action?id=" + id;
                    fw.post(url, null, function (data) {
                        if (data == '0') {
                            fw.alert('提示', "操作失败");
                            return;
                        }
                        else if (data == '2') {
                            fw.alert('提示', '找不到退款记录');
                            return;
                        }
                        else if (data == '3') {
                            fw.alert('提示', '改客户没有绑定银行卡,不能退款');
                            return;
                        }
                        else {
                            fw.alert('提示', '退款成功，将在2至3个工作日到账');
                            fw.datagridReload('customerRefundTable' + token);
                        }
                    }, null);
                }, null);
            });
        });
    }

    //初始化查询事件
    function onClickCustomerRefundSearch() {
        //获取查询按钮
        var buttonId = "btnSearchCustomerMoney" + token;

        //绑定点击事件
        fw.bindOnClick(buttonId, function (process) {

            //获取datagrid表格对象
            var strTableId = "customerMoneyTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            //获取查询条件
            params['customerMoneyVO.operateTime'] = fw.getFormValue('search_operateTime' + token, fw.type_form_datebox, fw.type_get_value);
            //获取客户名称
            params['customerMoneyVO.customerName'] = $("#search_customerName" + token).val();
            //获取操作员名称
            params['customerMoneyVO.operatorName'] = $("#search_operatorName" + token).val();

            //装载到datagrid中

            $('#' + strTableId).datagrid('load');
        });

    }

    // 初始化查询重置事件
    function initCustomerRefundSearchReset() {
        var buttonId = "btnSearchCustomerMoneyReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            //清空时间文本框
            $('#search_operateTime' + token).datebox("setValue", '');

            //清空文本框
            $('#search_customerName' + token).val('');
            $('#search_operatorName' + token).val('');
        });
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
