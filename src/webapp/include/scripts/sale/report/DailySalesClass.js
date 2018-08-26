var DailySalesClass = function (token) {

    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickContractSearch();

        // 初始化查询重置事件
        onClickContractSearchReset();

        // 初始化表格
        initTableDailySalesTable();
    }

    /**
     * 初始化表格
     */
    function initTableDailySalesTable() {

        var strTableId = 'DailySalesTable' + token;
        var url = WEB_ROOT + "/sale/report/DailySales_list.action";

        $('#' + strTableId).datagrid({
            title: '当日产品销售金额信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍候……',
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
            frozenColumns: [[  // 固定列，没有滚动条
                {field: 'ck', checkbox: true}
            ]],
            columns: [[
                {field: 'productionName', title: '产品名称'},
                {field: 'saleMoney', title: '当日销售额'},
                {field: 'size', title: '配额'},
                {field: 'daySalesRatio', title: '当天销售比例'},
                {field: 'totalSalesRatio', title: '累计销售比例'}
            ]],
            toolbar: [{
                id: 'btnDailySalesPrint' + token,
                text: '打印',
                iconCls: 'icon-print'
            }],
            onLoadSuccess: function () {
                onClickDailySalesPrint();
            }
        });
    }

    /**
     * 打印事件
     */
    function onClickDailySalesPrint() {
        var buttonId = "btnDailySalesPrint" + token;
        fw.bindOnClick(buttonId, function (process) {
            var date = fw.getFormValue('btnDailySalesDate' + token, fw.type_form_datebox, fw.type_get_value);
            window.open(WEB_ROOT + "/modules/sale/report/PrintDailySales.jsp?beginDate=" + date + "&endDate=" + date + "");
        });
    }

    /**
     * 查询事件
     */
    function onClickContractSearch() {
        var buttonId = "btnSearchDailySales" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "DailySalesTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["beginDate"] = fw.getFormValue('btnDailySalesDate' + token, fw.type_form_datebox, fw.type_get_value);
            params["endDate"] = fw.getFormValue('btnDailySalesDate' + token, fw.type_form_datebox, fw.type_get_value);
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickContractSearchReset() {
        //var buttonId = "btnResetContract" + token;
        //fw.bindOnClick(buttonId, function (process) {
        //    $("#search_Name" + token).val('');
        //    $('#search_SignDate' + token).val('');
        //
        //});
    }

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };

}

