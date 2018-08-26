/**
 * Created by ThinkPad on 5/11/2015.
 */
var DetailTableClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件

        // 初始化查询重置事件

        // 初始化表格
        initDetailTableTable();
    }

    /**
     * 初始化表格
     */
    function initDetailTableTable() {
        var strTableId = 'DetailTable'+token;
        var url = WEB_ROOT+"/example/DetailTable_list.action";

        $('#'+strTableId).datagrid({
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:false,
            singleSelect:true,
            pageList:[15,30,60],
            pageSize: 15,
            rownumbers:true,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);

                    //alert(JSON.stringify(data));
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
                { field: 'sid', title: '序号', hidden:true},
                { field: 'id', title: '编号', hidden:true},
                { field: 'state', title: 'state', hidden:true},
                { field: 'operatorId', title: 'operatorId'},
                { field: 'operateTime', title: 'operateTime'},
                { field: 'mainId', title: 'MainId'},
                { field: 'b1', title: 'B1'},
                { field: 'b2', title: 'B2'},
                { field: 'b3', title: 'B3'},
                { field: 'b4', title: 'B4'},
                { field: 'b5', title: 'B5'},
                { field: 'b6', title: 'B6'},
                { field: 'b7', title: 'B7'},
                { field: 'b8', title: 'B8'}
            ]],
            toolbar:[{
                id:'btnDetailTableAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnDetailTableEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnDetailTableDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                onClickDetailTableAdd();
                onClickDetailTableEdit();
                onClickDetailTableDelete();
            }
        });
    }
    /**
     * 添加事件
     */
    function onClickDetailTableAdd() {
        var buttonId = "btnDetailTableAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            initWindowDetailTable({});
        });
    }

    /**
     * 修改事件
     */
    function onClickDetailTableEdit() {
        var butttonId = "btnDetailTableEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('DetailTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/example/DetailTable_load.action?detailTable.id="+selected.id;
                fw.post(url, null, function(data){
                    initWindowDetailTable(data,null);
                    process.afterClick();
                }, function(){
                    process.afterClick();
                });
            })
        });
    }

    /**
     * 删除事件
     */
    function onClickDetailTableDelete() {
        var buttonId = "btnDetailTableDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('DetailTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/example/DetailTable_delete.action?detailTable.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('DetailTable'+token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 弹窗事件
     * @param data
     */
    function initWindowDetailTable(data){
        data["detailTable.operatorId"] = loginUser.getId();
        data["detailTable.operatorName"] = loginUser.getName();

        var url = WEB_ROOT + "/modules/example/DetailTable_Save.jsp?token=" + token;
        var windowId = "DetailTableWindow" + token;
        fw.window(windowId, '详细例子', 350, 450, url, function () {

            fw.combotreeLoadFromClass('b8'+token, 'com.youngbook.entity.po.example.DetailTablePO', 'search_B8');
            fw.getComboTreeFromKV('b7'+token, 'Is_DetailExample', null, data['detailTable.b7']);
            //提交事件
            onClickDetailTableSubmit();
            // 加载数据
            fw.formLoad('formDetailTable' + token, data);
        }, null);
    }


    /**
     * 数据提交事件
     */
    function onClickDetailTableSubmit() {
        var buttonId = "btnDetailTableSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formDetailTable" + token;
            var url = WEB_ROOT + "/example/DetailTable_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("DetailTable"+token);
                fw.windowClose('DetailTableWindow'+token);
            }, function() {
                process.afterClick();
            });
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
};