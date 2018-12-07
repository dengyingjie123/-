/**
 * 记账日志处理类
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var CalendarClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,basicWeek,basicDay,agendaWeek'
            },
            lang:'zh-cn',
            defaultDate: fw.getTimeToday(),
            editable: true,
            eventLimit: true, // allow "more" link when too many events
            eventClick: function(event, jsEvent, view) {
//                    if (event.id) {
//                        alert(event.start);
//                    }
                var customerName = event.title;
                initCostomerPersonal_ListProductionWindow(customerName);
            },
            events:function(start, end, timezone, callback) {
//                alert(start.unix() + " :" + end.unix());
//
//                var now = new Date(parseInt(start.unix()) * 1000);
//                var s = now.toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
//                alert(s);
                var view = $('#calendar').fullCalendar('getView');
                // alert("The view's title is " + view.intervalStart.format() + " --- " + view.end.format());
                fw.post(WEB_ROOT + "/system/Calendar_listCustomerBirthdays.action", {"intervalStart":view.intervalStart.format()}, function(data) {
                    callback(data);
                }, null);
            },
        });

        iniTableRaiseInfoTable();

    }

    /**
     * 初始化用户购买产品信息的窗口
     * @param customerName
     */
    function initCostomerPersonal_ListProductionWindow(customerName){
        var url =  WEB_ROOT + "/modules/customer/CustomerPersonal_ListProduction.jsp?token="+token;
        var windowId = "CustomerPersonal_ListProductionWindow"+token;
        var customerName = customerName;

        fw.window(windowId,'客户产品列表',900,400,url,function(){
            initSearchBar();
            initCostomerPersonal_ListProductionTable(customerName);

        },null);


    }

    /**
     * 初始化用户购买产品列表
     * @param customerName
     */
    function initCostomerPersonal_ListProductionTable(customerId){
        var strTableId = "CustomerPersonal_ListTable"+token;
        var url = WEB_ROOT+"/customer/CustomerProduction_list.action";

        $('#'+strTableId).datagrid({
            title:'客户产品列表',
            url:url,
            queryParams:{
                customerId:customerId
            },
            fitColumns:true,
            singleSelect:true,
            pageList:[15,30,60],
            pageSize: 15,
            rownumbers:true,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);
                    //fw.alertReturnValue(data);
                    return data;
                }
                catch(e) {
                }
            },
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条

            ]],
            columns: [[
                { field:'customerId',title:'id',hidden:true,width:25},
                { field: 'customerName', title: '客户姓名', width: 24},
                { field: 'productName', title: '产品名称', width: 80 },
                { field: 'projectName',title:'所属项目', width:40},
                { field: 'productCompositionName', title: '产品规模', width: 50 },
                { field: 'createTime', title: '认购时间', width: 22},
                { field: 'money', title: '金额(万元)', width: 25 },
                { field: 'moneyStatus', title: '状态', width: 30 },
                { field: 'originSalesman', title:'销售人员',width:30}
            ]]
        });
    }

    /**
     * 初始化搜索框
     */
    function initSearchBar(){
        onClickInitSearch();
        onClickResetSearch();
        var url = WEB_ROOT+"/system/ProjectMenu_list.action";
        fw.combotreeLoadWithCheck('#search_ProjectName'+token, url, null, null, null);
    }
    function onClickResetSearch(){
        var buttonId = "btnResetCostomerProduction"+token;
        fw.bindOnClick(buttonId , function(process){
            fw.combotreeClear('#search_ProjectName'+token);
            $('#search_ProjectName'+token).val('');
        });
    }
    function onClickInitSearch(){
        var buttonId = "btnSearchCostomerProduction"+token;
        fw.bindOnClick(buttonId , function(process){
            var strTableId = "CustomerPersonal_ListTable"+token;
            var params = $('#'+strTableId).datagrid('options').queryParams;
           // alert(JSON.stringify(params));
           // alert($("#search_user_name"+token).val());

            params["CustomerProductionVO.projectName"]=fw.getFormValue('search_ProjectName'+token,fw.type_form_combotree,fw.type_get_text);
            //alert(JSON.stringify(params));

            $( '#' + strTableId).datagrid('load');


            fw.treeClear()

        });
    }
    /**
     * @description 初始化募集资金信息显示
     * 
     * @author 苟熙霖 
     * 
     * @date 2018/12/4 10:13
     * @param null
     * @return 
     * @throws Exception
     */
    function iniTableRaiseInfoTable() {
        var today = fw.getTimeToday();
        var strTableId = "raiseInfoTable" + token;
        var url = WEB_ROOT+"/system/Calendar_getCurrentMonthRaise.action?today="+today;

        $('#'+strTableId).datagrid({
            url:url,
            rownumbers:false,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch(e) {
                }
            },
            frozenColumns:[[  // 固定列，没有滚动条

            ]],
            columns: [[
                { field:'money',title:'当月募集资金总额'}
            ]]
        });
    }




    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        }
    };
}