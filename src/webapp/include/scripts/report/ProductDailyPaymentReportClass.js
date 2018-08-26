/**
 * Created by 张舜清 on 2015/9/7.
 */

var ProductDailyPaymentReportClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickProductDailyPaymentReportSearch();
        // 初始化查询重置事件
        onClickProductDailyPaymentReportSearchReset();

        // 初始化表格
        initProductDailyPaymentReportTable();
    }

    /**
     * 初始化表格
     */
    function initProductDailyPaymentReportTable() {
        var strTableId = 'ProductDailyPaymentReportTable' + token;
        var url = WEB_ROOT + "/report/ProductDailyPaymentReport_list.action";

        $('#' + strTableId).datagrid({
            title: '厚币通宝每日产品兑付金额',
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
                    { field: 'productionName', title: '产品名称' },
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
                    id: 'btnProductDailyPaymentReportPrint' + token,
                    text: '打印报表',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickProductDailyPaymentReportPrint();
            }
        });
    }


    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 打印选择日期的报表
     */
    function onClickProductDailyPaymentReportPrint() {
        var buttonId = "btnProductDailyPaymentReportPrint" + token;
        fw.bindOnClick(buttonId, function(process) {
            var time = fw.getFormValue('search_ProductionReport_Time' + token, fw.type_form_datebox, fw.type_get_value);
            if (time == null || time == "" || time == undefined) {
                fw.alert("提示","请选择日期查询后再打印当天报表！")
            }
            else{
                var url =WEB_ROOT+"/modules/report/ProductDailyPaymentReportPrint.jsp?time="+time;
                window.open(url);
            }
        });
    }

    /**
     * 查询事件
     */
    function onClickProductDailyPaymentReportSearch() {
        var buttonId = "btnProductDailyPaymentReportSearch" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ProductDailyPaymentReportTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["productDailyPaymentReportVO.paymentTime"] = fw.getFormValue('search_ProductionReport_Time' + token, fw.type_form_datebox, fw.type_get_value);
            $('#' + strTableId).datagrid('load');
            fw.treeClear()
        });

    }

    /**
     * 查询重置事件
     */
    function onClickProductDailyPaymentReportSearchReset() {
        var buttonId = "btnProductDailyPaymentReportReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_ProductionReport_Time' + token).datebox("setValue", '');
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