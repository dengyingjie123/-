/**
 * Created by Administrator on 2015/5/11.
 */
var MainTable4DetailClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickMainTableSearch();
        // 初始化查询重置事件
        onClickMainTableSearchReset();
        //初始化下拉
        initComboTree();
        // 初始化表格
        initMainTableTable();

   }
    function initComboTree(){
        fw.combotreeLoadFromClass('search_A8'+token, 'com.youngbook.entity.po.example.MainTablePO', 'search_A8');
        fw.getComboTreeFromKV('search_A7'+token, 'Is_Avaliable', null, null);

    }

    // 构造初始化表格脚本
    function initMainTableTable() {
        var strTableId = 'MainTableTable' + token;
        var url = WEB_ROOT + "/example/MainTable_list.action";

        $('#' + strTableId).datagrid({
            title: '列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
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
                    { field: 'sid', title: 'sid',width:100,hidden:true},
                    { field: 'id', title: 'id',width:100,hidden:true},
                    { field: 'state', title: 'state',width:100,hidden:true},
                    { field: 'operatorId', title: 'operatorId',width:100,sortable: true},
                    { field: 'operateTime', title: 'operateTime',width:100,sortable: true},
                    { field: 'a1', title: 'A1',width:100,sortable: true},
                    { field: 'a2', title: 'A2',width:100,sortable: true},
                    { field: 'a3', title: 'A3',width:100,sortable: true},
                    { field: 'a4', title: 'A4',width:100,sortable: true},
                    { field: 'a5', title: 'A5',width:100,sortable: true},
                    { field: 'a6', title: 'A6',width:100,sortable: true},
                    { field: 'a7', title: 'A7',width:100,sortable: true},
                    { field: 'a8', title: 'A8',width:100,sortable: true}
                ]
            ],
            toolbar: [
                {
                    id: 'btnMainTableAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnMainTableEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnMainTableDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickMainTableAdd();
                onClickMainTableEdit();
                onClickMainTableDelete();
            }
        });
    } /**
     * 添加事件
     */
    function onClickMainTableAdd() {
        var buttonId = "btnMainTableAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            initMainTableWindow({});
        });
    }
    /**
     * 修改事件
     */
    function onClickMainTableEdit() {
        var buttonId = "btnMainTableEdit" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('MainTableTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + "/example/MainTable_load.action?mainTable.id="+selected.id;
                fw.post(url, null, function(data){
                    initMainTableWindow(data);
                    process.afterClick();
                }, function() {
                    process.afterClick();
                });
            })
        });
    }
    /**
     * 删除事件
     */
    function onClickMainTableDelete() {
        var buttonId = "btnMainTableDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('MainTableTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                  var url = WEB_ROOT + "/example/MainTable_delete.action?mainTable.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        process.afterClick();
                        fw.datagridReload('MainTableTable'+token);
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
    function initMainTableWindow(data) {

        data["mainTable.operatorId"] = loginUser.getId();
        data["mainTable.operatorName"] = loginUser.getName();

        var url = WEB_ROOT + "/modules/example/MainTable4Detail_Save.jsp?token=" + token;
        var windowId = "MainTableWindow" + token;
        fw.window(windowId, '例子', 800, 600, url, function () {
            //提交事件
            onClickMainTableSubmit();
            fw.combotreeLoadFromClass('a8'+token, 'com.youngbook.entity.po.example.MainTablePO', 'search_A8', null);
            fw.getComboTreeFromKV('a7'+token, 'Is_Avaliable', null, data['mainTable.a7']);

            using(SCRIPTS_ROOT + '/example/DetailTableClass.js', function () {
                    var detailTableClass = new DetailTableClass(token);
                    detailTableClass.initModule();
            });

            // 加载数据
            fw.formLoad('formMainTable' + token, data);
        }, null);
    }
    /**
     * 数据提交事件
     */
    function onClickMainTableSubmit() {
        var buttonId = "btnMainTableSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formMainTable" + token;
            var url = WEB_ROOT + "/example/MainTable_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("MainTableTable"+token);
                fw.windowClose('MainTableWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }
    /**
     * 查询事件
     */
    function onClickMainTableSearch() {
        var buttonId = "btnMainTableSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "MainTableTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

           params["mainTableVO_a5_Start"]= fw.getFormValue('Search_A5_Start'+token,fw.type_form_datebox, fw.type_get_value);
           params["mainTableVO_a5_End"]=fw.getFormValue('Search_A5_End'+token,fw.type_form_datebox, fw.type_get_value);
           params["mainTableVO_a6_Start"]= fw.getFormValue('Search_A6_Start'+token,fw.type_form_datebox, fw.type_get_value);
           params["mainTableVO_a6_End"]= fw.getFormValue('Search_A6_End'+token,fw.type_form_datebox, fw.type_get_value);
           params["mainTableVO.a1"]=$("#search_A1"+token).val();
           params["mainTableVO.a2"]=$("#search_A2"+token).val();

            params["mainTableVO_a3_Start"]=$("#mainTableVO_a3_Start"+token).val();
            params["mainTableVO_a3_End"]=$("#mainTableVO_a3_End"+token).val();
            params["mainTableVO.a4"] = $("#search_A4" + token).val();


            params["mainTableVO.a7"] = fw.getFormValue('search_A7'+token, fw.type_form_combotree, fw.type_get_value);
            params["mainTableVO.a8"] = fw.getFormValue('search_A8'+token, fw.type_form_combotree, fw.type_get_value);

            $( '#' + strTableId).datagrid('load');
        });
    }
    /**
     * 查询重置事件
     */
    function onClickMainTableSearchReset() {
        var buttonId = "btnMainTableSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            //清空时间文本框
            $('#Search_A5_Start' + token).datebox("setValue", '');
            $('#Search_A5_End' + token).datebox("setValue", '');
            $('#Search_A6_Start' + token).datebox("setValue", '');
            $('#Search_A6_End' + token).datebox("setValue", '');

            $('#search_A1'+token).val('');
            $('#search_A4'+token).val('');

            $('#mainTableVO_a3_Start'+token).val('');
            $('#mainTableVO_a3_End'+token).val('');

            fw.combotreeClear('search_A7'+token);
            fw.combotreeClear('search_A8'+token);
        });
    }
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
};