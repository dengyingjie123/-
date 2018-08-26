/**
 * Created by admin on 2015/4/23.
 */
var ContentTemplateClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        initFormContentSearch();
        // 初始化查询事件
        onClickContentTemplateSearch();
        // 初始化查询重置事件
        onClickContentTemplateSearchReset();
        // 初始化表格
        initContentTemplateTable();
    }
    function initFormContentSearch() {
        fw.getComboTreeFromKV('search_Type'+token, 'ContentTemplate', 'k','-2');
    }

    function initContentTemplateTable() {
        var strTableId = 'SmsTemplateTable'+token;
        var url = WEB_ROOT+"/system/ContentTemplate_list.action";

        $('#'+strTableId).datagrid({
            title: '内容模板信息',
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
                { field: 'sid', title: 'sid',hidden:true, width: 30 },
                { field: 'id', title: 'id',hidden:true, width: 30 },
                { field: 'state', title: 'state',hidden:true, width: 30 },
                { field: 'operatorId', title: 'operatorId',hidden:true, width: 30 },
                { field: 'operateTime', title: 'operateTime',hidden:true, width: 30 },
                { field: 'name', title: '名称', width: 100 },
                { field: 'typeName', title: '类型', width: 100 },
                { field: 'template', title: '模板', width: 300 }
            ]],
//            toolbar:[{
//                id:'btnSmsTemplateAdd'+token,
//                text:'添加',
//                iconCls:'icon-add'
//            },{
//                id:'btnSmsTemplateEdit'+token,
//                text:'修改',
//                iconCls:'icon-edit'
//            },{
//                id:'btnSmsTemplateDelete'+token,
//                text:'删除',
//                iconCls:'icon-cut'
//            }],
            onLoadSuccess:function() {
                // 初始化事件
                onclickContentTemplateAdd();
                onClickContentTemplateEdit();
                onClickContentTemplateDelete();
            }
        });
    }
    /**
     * 添加
     */
    function onclickContentTemplateAdd(){
        var buttonId = "btnSmsTemplateAdd"+token;
        fw.bindOnClick(buttonId,function(process){
            process.beforeClick();
            initContentTemplateWindow({});
            process.afterClick();
        })
    }
    /**
     * 初始化弹出窗口
     * @param data
     */
    function initContentTemplateWindow(data) {

        data["contentTemplate.operatorId"] = loginUser.getId();
        data["contentTemplate.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/system/ContentTemplate_Save.jsp?token="+token;
        var windowId = "SmsTemplateWindow" + token;
        fw.window(windowId, '内容模板', 500, 300, url, function() {
            // 初始化控件
            // 初始化类型combotree
             fw.getComboTreeFromKV('type'+token, 'ContentTemplate', 'k', fw.getMemberValue(data, 'contentTemplate.type'));
            // 初始化表单提交事件
            onClickContentTemplateSubmit();
            // 加载数据
            fw.formLoad('formSmsTemplate'+token, data);
        }, null);
    }

    function onClickContentTemplateSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "SmsTemplateTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["contentTemplateVO.name"] = $("#search_Name"+token).val();
            params["contentTemplateVO.template"] = $("#search_Template"+token).val();
            params["contentTemplateVO.type"] = fw.getFormValue('search_Type' + token, fw.type_form_combotree, fw.type_get_value);
            $( '#' + strTableId).datagrid('load');
        });
    }
    /**
     * 查询重置事件
     */
    function onClickContentTemplateSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            // fw.combotreeClear('search_InOrOut'+token);
            $("#search_Name"+token).val('');
            fw.combotreeClear('#search_Type'+token);
            $("#search_Template"+token).val('');
        });
    }
    /**
     * 修改事件
     */
    function onClickContentTemplateEdit() {
        var butttonId = "btnSmsTemplateEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('SmsTemplateTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/system/ContentTemplate_load.action?contentTemplate.id="+selected.id;
                fw.post(url, null, function(data){
                    initContentTemplateWindow(data,null);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }
    /**
     * 删除事件
     */
    function onClickContentTemplateDelete() {
        var buttonId = "btnSmsTemplateDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('SmsTemplateTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/system/ContentTemplate_delete.action?contentTemplate.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('SmsTemplateTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }
    /**
     * 数据提交事件
     */
    function onClickContentTemplateSubmit() {
        var buttonId = "btnSmsTemplateSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formSmsTemplate" + token;
            var url = WEB_ROOT + "/system/ContentTemplate_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("SmsTemplateTable"+token);
                fw.windowClose('SmsTemplateWindow'+token);
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
