/**
 * Created by yux on 2016/6/12.
 */
var ProductionCommissionClass = function (token) {

    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickProductionCommissionSearch();
        // 初始化查询重置事件
        onClickProductionCommissionSearchReset();

        // 初始化表格
        initProductionCommissionTable();

        // 初始化查询表单
        initProductionCommissionSearchForm();
    }

    /**
     * 初始化查询表单
     */
    function initProductionCommissionSearchForm() {
    }

    // 构造初始化表格脚本
    function initProductionCommissionTable() {
        var strTableId = 'productionCommissionTable' + token;
        var url = WEB_ROOT + '/sale/ProductionCommission_list.action';

        $('#' + strTableId).datagrid({
            title: '列表',
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
                    { field: 'commissionType', title: '返佣类型',hidden:false, sortable:true},
                    { field: 'commissionLevel', title: '返佣等级',hidden:false, sortable:true},
                    { field: 'commissionRate', title: '返佣率',hidden:false, sortable:true},
                    { field: 'areaCode', title: '区域编号',hidden:false, sortable:true},
                    { field: 'effectieTime', title: '生效时间',hidden:false, sortable:true},
                    { field: 'commissionTime', title: '期限',hidden:false, sortable:true}
                ]
            ],
            toolbar: [
                {
                    id: 'btnProductionCommissionAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnProductionCommissionEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnProductionCommissionDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickProductionCommissionAdd();
                onClickProductionCommissionEdit();
                onClickProductionCommissionDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickProductionCommissionAdd() {
        var buttonId = 'btnProductionCommissionAdd' + token;
        fw.bindOnClick(buttonId, function(process) {
            initProductionCommissionWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickProductionCommissionEdit() {
        var buttonId = 'btnProductionCommissionEdit' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('productionCommissionTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/sale/ProductionCommission_load.action?productionCommission.id='+selected.id;
                fw.post(url, null, function(data){
                    initProductionCommissionWindow(data);
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
    function onClickProductionCommissionDelete() {
        var buttonId = 'btnProductionCommissionDelete' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('productionCommissionTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    // todo cm 修改url
                    var url = WEB_ROOT + '/sale/ProductionCommission_delete.action?productionCommission.sid='+selected.sid;
                    fw.post(url, null, function(data) {
                        process.afterClick();
                        fw.datagridReload('productionCommissionTable'+token);
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
    function initProductionCommissionWindow(data) {

        // fw.alertReturnValue(data);

        data['productionCommission.operatorId'] = loginUser.getId();
        data['productionCommission.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/sale/ProductionCommission_Save.jsp?token=' + token;
        var windowId = 'productionCommissionWindow' + token;
        fw.window(windowId, '返佣管理', 380, 350, url, function () {
            //提交事件
            onClickProductionCommissionSubmit();


            // 加载数据
            fw.formLoad('formProductionCommission' + token, data);

        });
    }

    /**
     * 数据提交事件
     */
    function onClickProductionCommissionSubmit() {
        var buttonId = 'btnProductionCommissionSubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = 'formProductionCommission' + token;
            // todo cm modify url
            var url = WEB_ROOT + '/sale/ProductionCommission_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('productionCommissionTable'+token);
                fw.windowClose('productionCommissionWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickProductionCommissionSearch() {
        var buttonId = 'btnProductionCommissionSearchSubmit' + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = 'productionCommissionTable'+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;




            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickProductionCommissionSearchReset() {
        var buttonId = 'btnProductionCommissionSearchReset' + token;
        fw.bindOnClick(buttonId, function(process) {
            // 清空时间文本框
        });
    }
    return{
        /**
         * boot.js 加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
};