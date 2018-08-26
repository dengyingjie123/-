/**
 * Created by 张舜清 on 2015/9/7.
 */

var CustomerDailyPaymentReportClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickCustomerDailyPaymentReportSearch();
        // 初始化查询重置事件
        onClickCustomerDailyPaymentReportSearchReset();

        // 初始化表格
        initCustomerDailyPaymentReportTable();
    }

    /**
     * 初始化表格
     */
    function initCustomerDailyPaymentReportTable() {
        var strTableId = 'CustomerDailyPaymentReportTable' + token;
        var url = WEB_ROOT + "/report/CustomerDailyPaymentReport_list.action";

        $('#' + strTableId).datagrid({
            title: '厚币通宝每日客户兑付金额',
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
//                {field:'ck',checkbox:true}
                ]
            ],
            columns: [
                [
                    { field: 'customerName', title: '客户名称' },
                    { field: 'paymentTime', title: '兑付日期' },
                    { field: 'totalProfitMoney', title: '应兑付利息' },
                    { field: 'totalPaymentMoney', title: '应兑付本金' },
                    { field: 'totalPaymentPrincipalMoney', title: '应兑付收益' },
                    { field: 'currentInstallment', title: '当前期数' },
                    { field: 'totalInstallment', title: '总期数' },
                    { field: 'surplusPaymentMoney', title: '剩余未兑付本金' },
                    { field: 'surplusPaymentPrincipalMoney', title: '剩余未兑付收益' },
                    { field: 'status', title: '状态',
                        formatter: function (value, row, index) {
                            return  value == "958" ? "未兑付" : "已兑付";
                        } }
                ]
            ],
            toolbar: [
                {
                    id: 'btnCustomerDailyPaymentReportPrint' + token,
                    text: '打印报表',
                    iconCls: 'icon-edit'
                } ,{
                    id: 'btnOut' + token,
                    text: '导出',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickCustomerDailyPaymentReportPrint();
                onClickOut();
            }
        });
    }
    /*
        导出
     */
    function onClickOut(){

        var buttonId = "btnOut" + token;
        fw.bindOnClick(buttonId, function(process) {
            var url=WEB_ROOT+"/sale/PaymentPlan_getPaymentExcel";
           window.open(url);

        });
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 打印选择日期的报表
     */
    function onClickCustomerDailyPaymentReportPrint() {
        var buttonId = "btnCustomerDailyPaymentReportPrint" + token;
        fw.bindOnClick(buttonId, function(process) {
            var time = fw.getFormValue('search_CustomerDailyPaymentReport_Time' + token, fw.type_form_datebox, fw.type_get_value);
            if (time == null || time == "" || time == undefined) {
                fw.alert("提示","请选择日期查询后再打印当天报表！")
            }
            else{
                var url =WEB_ROOT+"/modules/report/CustomerDailyPaymentReportPrint.jsp?time="+time;
                window.open(url);
            }
        });
    }

    /**
     * 查询事件
     */
    function onClickCustomerDailyPaymentReportSearch() {
        var buttonId = "btnCustomerDailyPaymentReportSearch" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "CustomerDailyPaymentReportTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["CustomerDailyPaymentReportVO.paymentTime"] = fw.getFormValue('search_CustomerDailyPaymentReport_Time' + token, fw.type_form_datebox, fw.type_get_value);
            $('#' + strTableId).datagrid('load');
            fw.treeClear()
        });

    }

    /**
     * 查询重置事件
     */
    function onClickCustomerDailyPaymentReportSearchReset() {
        var buttonId = "btnCustomerDailyPaymentReportReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_CustomerDailyPaymentReport_Time' + token).datebox("setValue", '');
        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
}