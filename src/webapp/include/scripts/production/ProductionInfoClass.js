/**
 * Created by Administrator on 2015/4/9.
 */
var ProductionInfoClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {



        // 初始化查询事件
        onClickProductionInfoSearch();
        // 初始化查询重置事件
        onClickProductionInfoSearchReset();
        // 初始化表格
        initProductionInfoTable();
    }

    /**
     * 初始化表格事件
     */
    function initProductionInfoTable(){
        var strTableId = 'ProductionInfoTable'+token;
        var url = WEB_ROOT+"/production/ProductionInfo_list.action";

        $('#'+strTableId).datagrid({
            title: '产品扩展信息',
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
                { field: 'productionName', title: '产品名称', width: 30 },
                { field: 'description', title: '产品描述', width: 30 }
            ]],
            toolbar:[{
                id:'btnProductionInfoAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnProductionInfoEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnProductionInfoDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                onClickProductionInfoAdd();
                onClickProductionInfoEdit();
                onClickProductionInfoDelete();
            }
        });
    }

    /**
     * 单击删除按钮事件
     */
    function onClickProductionInfoDelete(){
        var buttonId = "btnProductionInfoDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProductionInfoTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/production/ProductionInfo_delete.action?productionInfo.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('ProductionInfoTable'+token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 单击添加按钮事件
     */
    function onClickProductionInfoAdd(){
        var buttonId = "btnProductionInfoAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initProductionInfoWindow({});
        });
    }

    /**
     * 单击修改按钮事件
     */
    function onClickProductionInfoEdit() {
        var butttonId = "btnProductionInfoEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('ProductionInfoTable'+token, function(selected){
                var id = selected.id;
                var url = WEB_ROOT + "/production/ProductionInfo_load.action?productionInfo.id="+id;
                fw.post(url, null, function(data){
                    // 增加其他传入参数
                    data['productionInfoVO.productionName']=selected.productionName;
                    // fw.alertReturnValue(data);
                    initProductionInfoWindow(data,null);
                }, null);
            })
        });
    }
    /**
     * 初始化弹出窗口
     * @param data
     */
    function initProductionInfoWindow(data) {

        data["productionInfo.operatorId"] = loginUser.getId();
        data["productionInfo.operatorName"] = loginUser.getName();

        // 更改url地址
        var url =  WEB_ROOT + "/modules/production/ProductionInfo_Save.jsp?token="+token;
        var windowId = "ProductionInfoWindow" + token;
        // 更改窗口名称
        // 更改窗口大小
        fw.window(windowId, '详细信息', 750, 450, url, function() {
            // 初始化表单提交事件
            onClickProductionInfoSubmit();
            // 初始化CKEditor
            fw.initCKEditor("productionInfo.description");
            // 加载数据
            fw.formLoad('formProductionInfo'+token, data);
        }, null);
    }

a
    /**
     * 数据提交事件
     */
    function onClickProductionInfoSubmit() {
        var buttonId = "btnProductionInfoSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            //alert("开始提交");
            var formId = "formProductionInfo" + token;
            // 更改url
            var url = WEB_ROOT + "/production/ProductionInfo_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("ProductionInfoTable"+token);
                fw.windowClose('ProductionInfoWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 单击查询按钮事件
     */
    function onClickProductionInfoSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "ProductionInfoTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params['productionInfoVO.productionName'] = $('#Search_ProductionName'+token).val();

            $( '#' + strTableId).datagrid('load');
            fw.treeClear()
        });
    }

    /**
     * 查询重置事件
     */
    function onClickProductionInfoSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $('#Search_ProductionName' + token).val('');
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
    ;