/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/30/14
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */

var DetailStatisticsClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    var size;
    var sum;
    var expectedYield;
    var name;
    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化查询区域
        initSearchArea();
        // 初始化查询事件
        onClickDetailStatisticsSearch();
        // 初始化查询重置事件
        onClickDetailStatisticsSearchReset();
        // 初始化表格
        initTableDetailStatisticsTable();

        //初始化总信息
//        initDetail();
    }

    /**
     * 初始化总信息
     */
    function initDetail(){
        var url = WEB_ROOT + "/report/DetailStatistics_detail.action";
        $.post(url, null, function(data) {

        })
    }
    /**
     * 初始化查询区域
     */
    function initSearchArea(){
        var url = WEB_ROOT+"/system/SaleMenu_list.action";
        fw.combotreeLoadWithCheck('#search_SaleMan'+token, url, null, null, null);
        $('#btnProduction' + token).bind('click', function () {
            var url =  WEB_ROOT + "/modules/production/Production_Main.jsp?token="+token;
            var windowId = "ProductionWindow" + token;
            fw.window(windowId, '选择产品', 900, 500, url, function() {
                //加载js
                using(SCRIPTS_ROOT+'/production/ProductionClass.js', function () {
                    //alert("loaded...");
                    var my=new DetailStatisticsClass(token);
                    var production = new ProductionClass(token,my);
                    production.initModule();

                });
            }, null);
        })

    }

    /**
     * 初始化表格
     */
    function initTableDetailStatisticsTable() {
        var strTableId = 'DetailStatisticsTable'+token;
        var url = WEB_ROOT+"/report/DetailStatistics_list.action";

        $('#'+strTableId).datagrid({
            title: '销售明细信息',
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
//                   fw.alertReturnValue(data);
                    var obj=eval(data);
                    var obj1=eval(obj.returnValue);
//                    fw.alertReturnValue(obj1["pager"]);
//                    fw.alertReturnValue(obj1["pager1"]);
                    var ob=obj1["pager1"].rows;
                    if(ob[0].sumMoney=="" || ob[0].expectedYield==""||ob[0].productionName==""||ob[0].size==""){
                        sum=0;
                        expectedYield=0;
                        size=0;
                        name="";
                    }else{
                        sum=ob[0].sumMoney;
                        expectedYield=ob[0].expectedYield;
                        size=ob[0].size;
                        name=ob[0].productionName;
                }
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
                { field: 'userId', title: '销售人员id', hidden:true, width: 30 },
                { field: 'customerName', title: '客户名称', width: 30 },
                { field: 'time', title: '打款时间', width: 30 },
                { field: 'money', title: '购买金额', width: 30 },
                { field: 'proportion', title: '收益率(%)', width: 30 },
                { field: 'saleName', title: '销售人员', width: 30}
            ]],
            onLoadSuccess:function(){
                $("#size"+token).html("&nbsp&nbsp&nbsp产品配额："+size+"万");
                $("#average"+token).html("&nbsp&nbsp&nbsp平均收益："+expectedYield+"%");
                $("#sumMoney"+token).html("&nbsp&nbsp&nbsp购买总额："+sum+"万");
                if(name==""){
                   name= $("#search_Production"+token).val();
                }
                $("#name"+token).html("产品名称："+name);
        }
        });
    }


    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 查询事件
     */
    function onClickDetailStatisticsSearch() {
        var buttonId = "btnSearch" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "DetailStatisticsTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["detail.production"] = $("#productionId"+token).val();
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
    function onClickDetailStatisticsSearchReset() {
        var buttonId = "btnReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_Production"+token).val('');
            $("#productionId"+token).val('');
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