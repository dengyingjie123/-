/**
 * Created by Administrator on 2015/5/22.
 */
/**
 * SaleTask4Group脚本对象
 *产品销售小组分配
 */
var SaleTask4GroupClass = function (token) {

    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickSaleTask4GroupSearch();
        // 初始化查询重置事件
        onClickSaleTask4GroupSearchReset();

        // 初始化表格
        initSaleTask4GroupTable();

        // 初始化查询表单
        initSaleTask4GroupSearchForm();
    }

    /**
     * 初始化查询表单
     */
    function initSaleTask4GroupSearchForm() {
        var groupNameurl=WEB_ROOT+"/sale/SaleTask4Group_getSalemGroupName.action";
        var productNameurl=WEB_ROOT+"/sale/SaleTask4Group_getProductName.action";
        var groupNameID="search_saleGroupName"+token;
        var productNameId="search_productionName"+token
        fw.combotreeLoad(groupNameID,groupNameurl,"-2");
        fw.combotreeLoad(productNameId,productNameurl,"-2");
    }

    // 构造初始化表格脚本
    function initSaleTask4GroupTable() {
        var strTableId = 'saleTask4GroupTable' + token;
        var url = WEB_ROOT + '/sale/SaleTask4Group_list.action';
        $('#' + strTableId).datagrid({
            title: '产品分配列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍候……',
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: 'sid',//排序的列
            loadFilter: function (data) {
                try {
                    //获取数据 返回的Value
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid',hidden:true, sortable:true},
                    { field: 'id', title: 'id',hidden:true, sortable:true},
                    { field: 'state', title: 'state',hidden:true, sortable:true},
                    { field: 'operatorId', title: 'operatorId',hidden:true, sortable:true},
                    { field: 'operateTime', title: 'operateTime',hidden:true, sortable:true},
                    { field: 'productionId', title: '产品编号',hidden:true, sortable:true},
                    { field: 'productionName', title: '产品名称',hidden:false, sortable:true,width:100},
                    { field: 'saleGroupId', title: '销售组编号',hidden:true, sortable:true,width:100},
                    { field: 'saleGroupName', title: '销售组名称',hidden:false, sortable:true,width:100},
                    { field: 'taskMoney', title: '分配金额',hidden:false, sortable:true,width:100},
                    { field: 'startTime', title: '开始时间',hidden:false, sortable:true,width:100},
                    { field: 'endTime', title: '结束时间',hidden:false, sortable:true,width:100},
                    { field: 'waitingMoney', title: '待售金额',hidden:false, sortable:true,width:100},
                    { field: 'appointmengMoney', title: '预约金额',hidden:false, sortable:true,width:100},
                    { field: 'soldMoney', title: '打款金额',hidden:false, sortable:true,width:100},
                    { field: 'totoalCancelMoney', title: '累计取消金额',hidden:false, sortable:true,width:100}
                ]
            ],
            toolbar: [
                {
                    id: 'btnSaleTask4GroupAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSaleTask4GroupEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnSaleTask4GroupDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickSaleTask4GroupAdd();
                onClickSaleTask4GroupEdit();
                onClickSaleTask4GroupDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickSaleTask4GroupAdd() {
        var buttonId = 'btnSaleTask4GroupAdd' + token;
        fw.bindOnClick(buttonId, function(process) {
            initSaleTask4GroupWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickSaleTask4GroupEdit() {
        var buttonId = 'btnSaleTask4GroupEdit' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('saleTask4GroupTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/sale/SaleTask4Group_load.action?saleTask4Group.id='+selected.id;
                fw.post(url, null, function(data){
                    data["saleTask4GroupVO.productionName"]=selected.productionName;
                    data["saleTask4GroupVO.saleGroupName"]=selected.saleGroupName;
                    initSaleTask4GroupWindow(data);
                    process.afterClick();
                }, function() {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 删除事件
     */
    function onClickSaleTask4GroupDelete() {
        var buttonId = 'btnSaleTask4GroupDelete' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('saleTask4GroupTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    // todo cm 修改url
                    var url = WEB_ROOT + '/sale/SaleTask4Group_delete.action?saleTask4Group.sid='+selected.sid;
                    fw.post(url, null, function(data) {
                        process.afterClick();
                        fw.datagridReload('saleTask4GroupTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initSaleTask4GroupWindow(data) {
        data['saleTask4Group.operatorId'] = loginUser.getId();
        data['saleTask4Group.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/sale/SaleTask4Group_Save.jsp?token=' + token;
        var windowId = 'saleTask4GroupWindow' + token;
        fw.window(windowId, '产品分配', 760, 560, url, function () {
            fw.textFormatCurrency('taskMoney'+token);
            //提交事件
            onClickSaleTask4GroupSubmit();
            //初始化选择产品
            initselectProduction();
            //初始化选择销售小组
            initselectSalemangroup();

            if(data["saleTask4Group.id"] !=undefined) {
                using(SCRIPTS_ROOT + '/sale/SaleTask4SalemanClass.js', function () {
                    var saleTask4SalemanClass = new SaleTask4SalemanClass(token,data);
                    saleTask4SalemanClass.initModule();
                });
            }

            // 加载数据
            fw.formLoad('formSaleTask4Group' + token, data);

        });
    }

    /**
     * 数据提交事件
     */
    function onClickSaleTask4GroupSubmit() {
        var buttonId = 'btnSaleTask4GroupSubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            fw.CurrencyFormatText('taskMoney'+token);
            var formId = 'formSaleTask4Group' + token;
            // todo cm modify url
            var url = WEB_ROOT + '/sale/SaleTask4Group_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('saleTask4GroupTable'+token);
                fw.windowClose('saleTask4GroupWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickSaleTask4GroupSearch() {
        var buttonId = 'btnSaleTask4GroupSearchSubmit' + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = 'saleTask4GroupTable'+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            params['saleTask4GroupVO.productionId']=$("#search_productionName"+token).combotree("getValue");
            params['saleTask4GroupVO.saleGroupId']=$("#search_saleGroupName"+token).combotree("getValue");
            params['saleTask4GroupVO.taskMoney']=$('#search_TaskMoney'+token).val();
            params['saleTask4GroupVO_startTime_Start']= fw.getFormValue('search_StartTime_Start'+token,fw.type_form_datebox, fw.type_get_value);
            params['saleTask4GroupVO_startTime_End']=fw.getFormValue('search_StartTime_End'+token,fw.type_form_datebox, fw.type_get_value);
            params['saleTask4GroupVO_endTime_Start']= fw.getFormValue('search_EndTime_Start'+token,fw.type_form_datebox, fw.type_get_value);
            params['saleTask4GroupVO_endTime_End']=fw.getFormValue('search_EndTime_End'+token,fw.type_form_datebox, fw.type_get_value);
            params['saleTask4GroupVO.waitingMoney']=$('#search_WaitingMoney'+token).val();
            params['saleTask4GroupVO.appointmengMoney']=$('#search_AppointmengMoney'+token).val();
            params['saleTask4GroupVO.soldMoney']=$('#search_SoldMoney'+token).val();
            params['saleTask4GroupVO.totoalCancelMoney']=$('#search_TotoalCancelMoney'+token).val();

            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickSaleTask4GroupSearchReset() {
        var buttonId = 'btnSaleTask4GroupSearchReset' + token;
        fw.bindOnClick(buttonId, function(process) {
            // 清空时间文本框
            $('#search_TaskMoney'+token).val('');
            $('#search_StartTime_Start' + token).datebox('setValue', '');
            $('#search_StartTime_End' + token).datebox('setValue', '');
            $('#search_EndTime_Start' + token).datebox('setValue', '');
            $('#search_EndTime_End' + token).datebox('setValue', '');
            $('#search_WaitingMoney'+token).val('');
            $('#search_AppointmengMoney'+token).val('');
            $('#search_SoldMoney'+token).val('');
            $('#search_TotoalCancelMoney'+token).val('');

            fw.combotreeClear("search_productionName"+token);
            fw.combotreeClear("search_saleGroupName"+token);
        });
    }

    /***
     *初始选择产品事件
     */
    function initselectProduction(){
        var textId="#productionName"+token;
        $(textId).bind("click",function(){
            var url =  WEB_ROOT + "/modules/production/Production_Main.jsp?token="+token;
            var swindowId = "ProductionWindow" + token;
            fw.window(swindowId,"在售产品列表",1000,550,url,function(){
                ///加载js
                using(SCRIPTS_ROOT+'/production/ProductionClass.js', function () {
                    var my=new SaleTask4GroupClass(token);
                    var production = new ProductionClass(token,my,"");
                    production.initModule();

                });
            },null);
        })
    }

    /**
     * 初始化选择销售小组
     */
    function initselectSalemangroup(){
        var textId="#saleGroupName"+token;
        $(textId).bind("click",function(){
            var url =  WEB_ROOT + "/modules/sale/SalemanGroup_Main.jsp?token="+token;
            var swindowId = "SalemanGroupSelectWindow" + token;
            fw.window(swindowId,"在售产品列表",600,400,url,function(){
                ///加载js
                using(SCRIPTS_ROOT+'/sale/SalesmanGroupClass.js', function () {
                    var my=new SaleTask4GroupClass(token);
                    var salemanGroupClass = new SalemanGroupClass(token,my);
                    salemanGroupClass.initModule();
                });
            },null);
        })
    }
    return{
        /**
         * boot.js 加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },//产品
        loadSelected:function(data){
            $("#productionName"+token).val(data["production.name"]);
            $("#productionId"+token).val(data["production.id"]);
        },
        //选中小组回调函数
        loadSelectedSaleman:function(data){
            $("#saleGroupName"+token).val(data["salemanGroup.name"]);
            $("#saleGroupId"+token).val(data["salemanGroup.id"]);
        }
    };
};
