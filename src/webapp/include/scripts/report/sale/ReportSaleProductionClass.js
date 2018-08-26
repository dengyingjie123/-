/**
 *
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var ReportSaleProductionClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //alert('init all');
        initReportSaleProductionTable();

        onClickReportSaleProductionSearch();
        onClickReportSaleProductionSearchReset();
    }


    function initReportSaleProductionTable() {
        var strTableId = 'ReportSaleProductionTable' + token;
        var url = WEB_ROOT + "/report/sale/ReportSaleProduction_list.action";

        $('#' + strTableId).datagrid({
            title: '厚币通宝产品报表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [10, 30, 60],
            pageSize: 10,
            showFooter:true,
            rownumbers: true,
            loadFilter: function (data) {
                try {
                    //fw.alertReturnValue(data);
                    data = fw.dealReturnObject(data);

                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                {field:'ck',checkbox:true}
                ]
            ],
            columns: [
                [
                    { field: 'productionId', title: '产品编号', hidden:true },
                    { field: 'productionName', title: '产品名称' },
                    { field: 'money', title: '产品销售额',
                        formatter:function(value,row,index){
                            return fw.numberFloatFormat(row['money'], 2);
                        }
                    },
                    { field: 'expectedYield', title: '预期收益率（年化）',
                        formatter:function(value,row,index){
                            if (fw.checkIsNullObject(row['expectedYield'])) {
                                return "";
                            }
                            return row['expectedYield'] + "%";
                        }
                    },
                    { field: 'payTime', title: '认购日期' },
                    { field: 'valueDate', title: '产品起息日' },
                    { field: 'paymentTime', title: '产品兑付日' },
                    { field: 'totalPaymentPrincipalMoney', title: '兑付本金',
                        formatter:function(value,row,index){
                            return fw.numberFloatFormat(row['totalPaymentPrincipalMoney'], 2);
                        }
                    },
                    { field: 'totalProfitMoney', title: '兑付利息',
                        formatter:function(value,row,index){
                            return fw.numberFloatFormat(row['totalProfitMoney'], 2);
                        }
                    },
                    { field: 'paymentPlanStatus', title: '状态' }
                ]
            ],
            toolbar: [
            ],
            onLoadSuccess: function (data) {

                var moeny_all = 0;
                var totalPaymentPrincipalMoney_all = 0;
                var totalProfitMoney_all = 0;
                $(data.rows).each(function() {
                    moeny_all += parseFloat(this['money']);
                    totalPaymentPrincipalMoney_all += parseFloat(this['totalPaymentPrincipalMoney']);
                    totalProfitMoney_all += parseFloat(this['totalProfitMoney']);
                });

                var footerRow = {};
                footerRow = fw.jsonJoin(footerRow, {'productionName': '合计'}, true);
                footerRow = fw.jsonJoin(footerRow, {'money': moeny_all}, true);
                footerRow = fw.jsonJoin(footerRow, {'totalPaymentPrincipalMoney': totalPaymentPrincipalMoney_all}, true);
                footerRow = fw.jsonJoin(footerRow, {'totalProfitMoney': totalProfitMoney_all}, true);


                $('#'+strTableId).datagrid('reloadFooter',[
                    footerRow
                ]);

                // 初始化事件

            }
        });
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////



    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    function onClickReportSaleProductionSearch() {
        var buttonId = "btnReportSaleProductionSearch" +token;
        var strTableId = 'ReportSaleProductionTable' + token;
        fw.bindOnClick(buttonId, function() {
            var parametersJson = $('#' + strTableId).datagrid('options').queryParams;

            var start = fw.getFormValue('Search_ReportSaleProduction_Time' +token, fw.type_form_datebox, fw.type_get_value);
            parametersJson = fw.jsonJoin(parametersJson, {'Search_ReportSaleProduction_Time':start}, true);

            //fw.alertReturnValue(parametersJson);

            $('#' + strTableId).datagrid('load');

        });
    }

    function onClickReportSaleProductionSearchReset() {
        var buttonId = "btnReportSaleProductionSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $('#Search_ReportSaleProduction_Time'+token).datebox("setValue", '');
        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////


    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        }
    };
}