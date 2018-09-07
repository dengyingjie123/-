/**
 * Created by Jiangwandong on 2015/4/1.
 */
var LegalAgreementClass = function(token){

    function initAll(){
        //初始化查询框
        initFormLegalAgreementSearch();
        //绑定查询按钮点击事件
        onClickLegalAgreementSearch();
        //绑定查询重置按钮点击事件
        onClickLegalAgreementSearchReset();
        //初始化表格
        initTableLegalAgreement();
    }

    /**
     * 初始化查询框
     */
    function initFormLegalAgreementSearch() {
        fw.getComboTreeFromKV('search_InOrOut' + token, 'OA_Finance_MoneyLog_InOrOut', 'k','-2');
    }

    /**
     * 绑定查询按钮点击事件
     */
    function onClickLegalAgreementSearch() {
        var buttonId = "btnSearchAgreement" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "LegalAgreementTable" + token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            // 此处的下标应是VO
            params["legalAgreementVO.name"] = $("#search_Name" + token).val();
            params["legalAgreementVO.content"] = $("#search_Content" + token).val();
            //当所查询的条件是在一个时间段,就要使用'实例名_字段属性_Start',结束的就要'_End'这种方式，如果不是查询一个时间段就用'实例名称.字段属性'。
            params["legalAgreementVO_startTime_Start"] = fw.getFormValue("search_StartTime_Start" + token, fw.type_form_datebox, fw.type_get_value);
            params["legalAgreementVO_endTime_End"] = fw.getFormValue("search_EndTime_End" + token, fw.type_form_datebox, fw.type_get_value);
            $( '#' + strTableId).datagrid('load');
            fw.treeClear();
        });
    }

    /**
     * 绑定查询重置按钮点击事件
     */
    function onClickLegalAgreementSearchReset() {
        var buttonId = "btnResetAgreement" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_Name" + token).val('');
            $("#search_Content" + token).val('');
            $('#search_StartTime_Start' + token).datebox("setValue", '');
            $('#search_EndTime_End' + token).datebox("setValue", '');
            fw.combotreeClear('search_InOrOut' + token);
        });
    }

    /**
     * 初始化表格和触发事件
     */
    function initTableLegalAgreement(){
        // 关联到相应jsp页面中的datagrid
        var strTableId = "LegalAgreementTable" + token;
        var url = WEB_ROOT + "/info/LegalAgreement_list.action";

        $('#'+strTableId).datagrid({
            title: '法律协议信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍候……',
            fitColumns:true,
            singleSelect:true,
            pageList:[15,30,60],
            pageSize: 15,
            rownumbers:true,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch(e) {
                }
            },
            pagination:true,
            columns: [[
                { field: 'sid', title: '序号', hidden:true, width: 30 },
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'name', title: '协议名称', width: 80 },
                { field: 'content', title: '协议内容', width: 30, hidden:true },
                { field: 'username', title: '操作者', width: 20 },
                { field: 'operateTime', title: '操作时间', width: 20 },
                { field: 'startTime', title: '开始时间', width: 20 },
                { field: 'endTime', title: '结束时间', width: 20 }
            ]],
//            toolbar:[{
//                id:'btnAgreementAdd'+token,
//                text:'添加',
//                iconCls:'icon-add'
//            },{
//                id:'btnAgreementEdit'+token,
//                text:'修改',
//                iconCls:'icon-edit'
//            },{
//                id:'btnAgreementDelete'+token,
//                text:'删除',
//                iconCls:'icon-cut'
//            }],
            frozenColumns:[[ //固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            onLoadSuccess:function() {
                // 如果初始化成功，也添加ToolBar的各项事件
                onClickAgreementAdd();
                onClickAgreementDelete();
                onClickAgreementEdit();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickAgreementAdd() {
        var buttonId = "btnAgreementAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowAgreementWindow({},0, true);
        });
    }

    /**
     * 删除事件
     */
    function onClickAgreementDelete() {
        var buttonId = "btnAgreementDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected("LegalAgreementTable"+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/info/LegalAgreement_delete.action?legalAgreement.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload("LegalAgreementTable" + token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickAgreementEdit() {
        var butttonId = "btnAgreementEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            process.beforeClick();
            fw.datagridGetSelected("LegalAgreementTable" + token, function(selected){
                var id = selected.id;
                var url = WEB_ROOT + "/info/LegalAgreement_load.action?legalAgreement.id=" + selected.id;
                fw.post(url, null, function(data){
                    //fw.alertReturnValue(data);
                    initWindowAgreementWindow(data, null, false);
                    process.afterClick();
                }, null);
            })
        });
    }

    /**
     * 打开窗口，初始化信息
     * @param data
     * @param obj
     */
    function initWindowAgreementWindow(data, obj, isInsert) {
        var url =  WEB_ROOT + "/modules/info/LegalAgreement_Save.jsp?token="+token;
        var windowId = "AgreementWindow" + token;
        fw.window(windowId, isInsert ? "添加法律协议" : "修改法律协议", 880, 530, url, function() {
            data["legalAgreement.operatorId"] = loginUser.getId();
            // 初始化表单提交事件
            onClickAgreementSubmit();
            // 初始化CKEditor
            fw.initCKEditor("legalAgreement.content");
            // 加载数据
            fw.formLoad('formAgreement'+token, data);
        }, null);
    }

    /**
     * 保存事件
     */
    function onClickAgreementSubmit() {
        var buttonId = "btnAgreementSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formAgreement" + token;
            var url = WEB_ROOT + "/info/LegalAgreement_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("LegalAgreementTable" + token);
                fw.windowClose('AgreementWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    return{
        initModule:function(){
            return initAll();
        }
    };
}