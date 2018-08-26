/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/30/14
 * Time: 9:10 AM
 * To change this template use File | Settings | File Templates.
 */

var TimeStatisticsClass = function(token) {
     var timeStr="";
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化查询区域
        initSearchArea();
        // 初始化查询事件
        onClickTimeStatisticsSearch();
        // 初始化查询重置事件
        onClickTimeStatisticsSearchReset();

        // 初始化表格
        initTableTimeStatisticsTable();

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
    function initTableTimeStatisticsTable() {
        var strTableId = 'TimeStatisticsTable'+token;
        var url = WEB_ROOT+"/report/TimeStatistics_list.action";

        $('#'+strTableId).datagrid({
            title: '销售时间统计信息',
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
                    //fw.alertReturnValue(data);
                    var obj=eval(data);
                    var obj1=eval(obj.returnValue);
                    timeStr="统计日期："+obj1["start"].substring(0,10)+"至"+obj1["stop"].substring(0,10);
                    return obj1["pager"];
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
                { field: 'name', title: '销售人员', width: 30 },
                { field: 'count', title: '客户数', width: 30 },
                { field: 'money', title: '销售金额', width: 30 },
                { field: 'proportion', title: '销售金额比例(%)', width: 30 }
            ]],
            onLoadSuccess:function(){
                //加载时间
                $("#timeBox"+token).html(timeStr);
            }
        });

    }


    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 查询事件
     */
    function onClickTimeStatisticsSearch() {
        var buttonId = "btnSearch" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "TimeStatisticsTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["timeStatistics.saleStart"] = fw.getFormValue('search_SaleStart'+token, fw.type_form_datebox, fw.type_get_value);
            params["timeStatistics.saleEnd"] = fw.getFormValue('search_SaleEnd'+token, fw.type_form_datebox, fw.type_get_value);
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
    function onClickTimeStatisticsSearchReset() {
        var buttonId = "btnReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $('#search_SaleStart'+token).datebox("setValue", '');
            $('#search_SaleEnd'+token).datebox("setValue", '');
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
        }
    };
}