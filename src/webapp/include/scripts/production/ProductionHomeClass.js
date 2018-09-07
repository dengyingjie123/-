/**
 * Created by jepson-pc on 2015/8/17.
 */
var ProductionHomeClass = function (token) {

    var callback4Select = undefined;

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickProductionHomeSearch();
        // 初始化查询重置事件
        onClickProductionHomeSearchReset();

        // 初始化表格
        initProductionHomeTable();

        // 初始化查询表单
        initProductionHomeSearchForm();

        // 初始化选择窗口按钮
        onClcikSelectDone();

    }

    /**
     * 初始化查询表单
     */
    function initProductionHomeSearchForm() {
        var tree = $('#Search_ProjectName'+token).combotree('tree');
        projectMenu(tree, function(){});
    }
    function projectMenu(treeId, success) {
        var url = WEB_ROOT+"/system/ProjectMenu_list.action";
        fw.treeLoad(treeId,url,null, success, null);
    }

    function onClcikSelectDone() {
        fw.bindOnClick('btnSelect'+token, function(press) {
            var strTableId = 'ProductionHomeTable' + token;
            var data = $('#' + strTableId).datagrid('getSelected');
            if (fw.checkIsFunction(callback4Select)) {
                callback4Select(data);
            }
            fwCloseWindow('SelectWindow'+token);
        });
    }

    // 构造初始化表格脚本
    function initProductionHomeTable() {
        var strTableId = 'ProductionHomeTable' + token;
        var url = WEB_ROOT + '/production/ProductionHome_list.action';

        $('#' + strTableId).datagrid({
            title: '产品管理',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
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
                    { field: 'sid', title: 'Sid',hidden:true},
                    { field: 'id', title: 'Id',hidden:true},
                    { field: 'state', title: 'State',hidden:true},
                    { field: 'operatorId', title: 'OperatorId',hidden:true},
                    { field: 'operateTime', title: 'OperateTime',hidden:true},
                    { field: 'productionId', title: '产品编号',hidden:false},
                    { field: 'productionName', title: '产品名称',hidden:false},
                    { field: 'description', title: '产品描述',hidden:false},
                    { field: 'name', title: '所属项目',hidden:false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnProductionHomeAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnProductionHomeEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnProductionHomeDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickProductionHomeAdd();
                onClickProductionHomeEdit();
                onClickProductionHomeDelete();
            }
        });
    }



    /**
     * 添加事件
     */
    function onClickProductionHomeAdd() {
        var buttonId = 'btnProductionHomeAdd' + token;
        fw.bindOnClick(buttonId, function(process) {
            initProductionHomeWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickProductionHomeEdit() {
        var buttonId = 'btnProductionHomeEdit' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProductionHomeTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/production/ProductionHome_load.action?productionHome.id='+selected.id;
                fw.post(url, null, function(data){
                    initProductionHomeWindow(data);
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
    function onClickProductionHomeDelete() {
        var buttonId = 'btnProductionHomeDelete' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProductionHomeTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + '/production//ProductionHome_delete.action?productionHome.sid='+selected.sid;
                    fw.post(url, null, function(data) {
                        process.afterClick();
                        fw.datagridReload('ProductionHomeTable'+token);
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
    function initProductionHomeWindow(data) {

        // fw.alertReturnValue(data);

        data['productionHome.operatorId'] = loginUser.getId();
        data['productionHome.operatorName'] = loginUser.getName();
        var url = WEB_ROOT + '/modules/production/ProductionHome_Save.jsp?token=' + token;
        var windowId = 'ProductionHomeWindow' + token;
        fw.window(windowId, '产品管理',  700,550, url, function () {
            //提交事件
            onClickProductionHomeSubmit();
            var tree = $('#projectId'+token).combotree('tree');
            projectMenu(tree, function(){});

            // 加载数据
            fw.formLoad('formProductionHome' + token, data);

            // 初始化产品属性表格

            using(SCRIPTS_ROOT+'/production/ProductPropertyClass.js', function () {
                var productPropertyClass = new ProductPropertyClass(token);
                productPropertyClass.initProductPropertyTable(data['productionHome.id']);
            });
        });
    }

    /**
     * 数据提交事件
     */
    function onClickProductionHomeSubmit() {
        var buttonId = 'btnProductionHomeSubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = 'formProductionHome' + token;
            var url = WEB_ROOT + '/production/ProductionHome_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('ProductionHomeTable'+token);
                fw.windowClose('ProductionHomeWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickProductionHomeSearch() {
        var buttonId = 'btnProductionHomeSearchSubmit' + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = 'ProductionHomeTable'+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            params["productionHomeVO.productionName"] = $("#Search_ProductionName"+token).val();
            params["productionHomeVO.productionId"] = $("#Search_ProductioId"+token).val();
            params["productionHomeVO.projectId"] = fw.getFormValue('Search_ProjectName'+token, fw.type_form_combotree, fw.type_get_value);

            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickProductionHomeSearchReset() {
        var buttonId = 'btnProductionHomeSearchReset' + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#Search_ProductionName"+token).val('');
            $("#Search_ProductioId"+token).val('');
            fw.combotreeClear('#Search_ProjectName'+token);
        });
    }
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initModuleWithSelect:function(callback4SelectIn) {
            var url =  WEB_ROOT + '/modules/production/ProductionHome_Select.jsp?token='+token;
            var selectionWindowId = 'SelectWindow' + token;
            fw.window(selectionWindowId, '选择窗口',1000, 500, url, function() {
                callback4Select = callback4SelectIn;
                initAll();
                $('#ProductionHomeTable'+token).datagrid({toolbar:[]});
            }, null);
        }
    };
};