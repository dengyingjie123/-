/**
 * 记账日志处理类
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var MoneyLogReportClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        initPanelSpendRate();
    }

    /**
     * 初始化费用支出比例
     */
    function initPanelSpendRate() {

        initSpendRateDepartment();
        initSpendRateType();
        onLoadSpendRatePanel();

        onClickSpendRateSearch();
        onClickSpendRateSearchReset();
    }

    function initSpendRateDepartment() {
        var url = WEB_ROOT+"/system/Department_list.action";
        //alert($('#search_Department'+token).length);
        //var tree = $().combotree('tree');
        fw.combotreeLoadWithCheck('#search_SpendRate_Department'+token, url, null, null, null);
    }

    function initSpendRateType() {
        fw.getComboTreeFromKVWithCheck('search_SpendRate_Type'+token, 'OA_Finance_MoneyLog_Type', 'k','-2');
    }



    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////






    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    function onClickSpendRateSearch() {
        var buttonId = "btnSearchSpendRate" +token;
        fw.bindOnClick(buttonId, function() {
            var parametersJson = {};
            var departmentIds = fw.combotreeGetCheckedIds("search_SpendRate_Department"+token, ",", null);
            parametersJson = fw.getJsonParameters(departmentIds, "DepartmentIds");
            //fw.alertReturnValue(parametersJson);

            var typeIds = fw.combotreeGetCheckedIds("search_SpendRate_Type"+token, ",", null);
            //alert(typeIds);
            var typeIdsJson = fw.getJsonParameters(typeIds, "TypeIds");
            //fw.alertReturnValue(typeIdsJson);


            var start = fw.getFormValue('search_SpendRate_Start' +token, fw.type_form_datebox, fw.type_get_value);
            var end = fw.getFormValue('search_SpendRate_End' +token, fw.type_form_datebox, fw.type_get_value);


            parametersJson = fw.jsonJoin({}, [typeIdsJson, parametersJson, {"MoneyTime_Start":start}, {"MoneyTime_End":end}]);

            //fw.alertReturnValue(parametersJson);

            onLoadSpendRatePanel(parametersJson);
        });
    }

    function onClickSpendRateSearchReset() {
        var buttonId = "btnResetSpendRate" + token;
        fw.bindOnClick(buttonId, function(process) {
            $('#search_SpendRate_Start'+token).datebox("setValue", '');
            $('#search_SpendRate_End'+token).datebox("setValue", '');
            fw.combotreeClear('search_SpendRate_Department'+token);
            fw.combotreeClear('search_SpendRate_Type'+token);
        });
    }

    function onLoadSpendRatePanel(params) {
        var url = WEB_ROOT + "/system/chart/Chart_getMoneyLogSpendRatePie.action";
        fw.post(url, params, function(data) {
            fw.chartPie1('SpendRateContainer'+token, '费用支出比例', '支出比例', data.rows);
        }, null);
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