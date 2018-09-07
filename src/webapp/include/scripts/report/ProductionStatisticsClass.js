/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/29/14
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */

var ProductionStatisticsClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化查询区域
        initSearchArea();
        // 初始化查询事件
        onClickProductionStatisticsSearch();
        // 初始化查询重置事件
        onClickProductionStatisticsSearchReset();

        // 初始化表格
        initTableProductionStatisticsTable();
        //初始化产品选择
        initProductionSelest();
    }

    /**
     * 初始化产品选择
    */
    function initProductionSelest(){
        $('#btnProduction' + token).bind('click', function () {
            var url =  WEB_ROOT + "/modules/production/Production_Main.jsp?token="+token;
            var windowId = "ProductionWindow" + token;
            fw.window(windowId, '选择产品', 900, 500, url, function() {
                //加载js
                using(SCRIPTS_ROOT+'/production/ProductionClass.js', function () {
                    //alert("loaded...");
                    var my=new ProductionStatisticsClass(token);
                    var production = new ProductionClass(token,my);
                    production.initModule();

                });
            }, null);
        })

    }
    /**
     * 初始化查询区域
     */
    function initSearchArea(){
        var url = WEB_ROOT+"/system/SaleMenu_list.action";
        fw.combotreeLoadWithCheck('#search_SaleMan'+token, url, null, null, null);
    }

    /**
     * 初始化表格
     */
    function initTableProductionStatisticsTable() {
        var strTableId = 'ProductionStatisticsTable'+token;
        var url = WEB_ROOT+"/report/ProductionStatistics_list.action";

        $('#'+strTableId).datagrid({
            title: '产品统计信息',
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
                { field: 'createTime', title: '创建时间', hidden:true, width: 30 },
                { field: 'userId', title: '销售人员id', hidden:true, width: 30 },
                {field: 'production',title:'产品名称',width: 30 },
                { field: 'name', title: '销售人员', width: 30 },
                { field: 'count', title: '客户数', width: 30 },
                { field: 'money', title: '销售金额', width: 30 },
                { field: 'proportion', title: '销售金额比例(%)', width: 30 }
            ]]
        });
    }


    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 查询事件
     */
    function onClickProductionStatisticsSearch() {
        var buttonId = "btnSearch" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "ProductionStatisticsTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["proStatistics.production"] = $("#productionId"+token).val();
            params["proStatistics_createTime_Start"] = fw.getFormValue('search_Production_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["proStatistics_createTime_End"] = fw.getFormValue('search_Production_End'+token, fw.type_form_datebox, fw.type_get_value);
            var ids = fw.combotreeGetCheckedIds('search_SaleMan' + token, ',', "'");
            // alert(ids);
            params["SaleMan"] = ids;
            $( '#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
            fw.treeClear()
        });

    }

    /**
     * 查询重置事件
     */
    function onClickProductionStatisticsSearchReset() {
        var buttonId = "btnReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_Production"+token).val('');
            $("#productionId"+token).val('');
            $('#search_Production_Start'+token).datebox("setValue", '');
            $('#search_Production_End'+token).datebox("setValue", '');
            fw.combotreeClear('#search_SaleMan'+token);
        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        },
        loadSelected:function(data){
            $("#search_Production"+token).val(data["production.name"]);
            $("#productionId"+token).val(data["production.id"]);
        }
    };
}