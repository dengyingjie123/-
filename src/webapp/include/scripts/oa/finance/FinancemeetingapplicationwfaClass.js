/**
 * Created by admin on 2015/4/29.
 */
var FinancemeetingapplicationwfaClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        initFormFinancemeetingapplicationwfaSearch();
        onClickFinancemeetingapplicationwfaSearch();
        // 初始化查询重置事件
        onClickFinancemeetingapplicationwfaSearchReset();
        // 初始化表格
        initFinancemeetingapplicationwfaTable();
    }
    function initFormFinancemeetingapplicationwfaSearch() {
//        fw.getComboTreeFromKV('search_OrgId'+token, 'OA_WFAPassType', 'k','-2');


    }
    /**
     * 查询事件
     */
    function onClickFinancemeetingapplicationwfaSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "FinancemeetingTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["financemeetingapplicationwfaVO.orgId"] = $("#search_OrgId"+token).val();
            params["financemeetingapplicationwfaVO.name"] = $("#search_Name"+token).val();
            params["financemeetingapplicationwfaVO_startTime_Start"] = fw.getFormValue('search_StartTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["financemeetingapplicationwfaVO_startTime_End"] = fw.getFormValue('search_StartTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["financemeetingapplicationwfaVO.address"] = $("#search_Address"+token).val();
            params["financemeetingapplicationwfaVO_endTime_Start"] = fw.getFormValue('search_EndTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["financemeetingapplicationwfaVO_endTime_End"] = fw.getFormValue('search_EndTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["financemeetingapplicationwfaVO.participant"] = $("#search_Participant"+token).val();
            $( '#' + strTableId).datagrid('load');
            fw.treeClear()
        });
    }
    /**
     * 查询重置事件
     */
    function onClickFinancemeetingapplicationwfaSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_OrgId"+token).val('');
            $("#search_Name"+token).val('');
            $('#search_StartTime_Start'+token).datebox("setValue", '');
            $('#search_StartTime_End'+token).datebox("setValue", '');
            $('#search_EndTime_Start'+token).datebox("setValue", '');
            $('#search_EndTime_End'+token).datebox("setValue", '');
            $("#search_Address"+token).val('');
            $("#search_Participant"+token).val('');


        });
    }



    // 构造初始化表格脚本
    function initFinancemeetingapplicationwfaTable() {
        var strTableId = 'FinancemeetingTable'+token;
        var url = WEB_ROOT+"/oa/finance/Financemeetingapplicationwfa_list.action";

        $('#'+strTableId).datagrid({
            title: '财务会议申请WFA',
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

//                    alert(JSON.stringify(data));
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
                { field: 'operateTime', title: 'operateTime', hidden:true,width: 30 },
                { field: 'orgId', title: '组织编号', width: 30 },
                { field: 'name', title: '名称', width: 30 },
                { field: 'startTime', title: '开始时间', width: 30 },
                { field: 'endTime', title: '结束时间', width: 30 },
                { field: 'address', title: '地点', width: 30 },
                { field: 'participant', title: '参与人员', width: 30 },
                { field: 'money', title: '会议报批金额', width: 30 },
                { field: 'departmentLeaderId', title: '申请部门负责人编号', width: 30 },
                { field: 'departmentLeaderTime', title: '申请部门负责人时间', width: 30 },
                { field: 'DepartmentLeaderCommentTypeName', title: '申请部门负责人意见', width: 30 },
                { field: 'generalManagerId', title: '所属公司总经理编号', width: 30 },
                { field: 'generalManagerTime', title: '所属公司总经理时间', width: 30 },
                { field: 'GeneralManagerCommentTypeName', title: '所属公司总经理意见', width: 30 },
                { field: 'hQFinanceDirectorId', title: '总公司财务总监编号', width: 30 },
                { field: 'hQFinanceDirectorTime', title: '总公司财务总监时间', width: 30 },
                { field: 'HQFinanceDirectorCommentTypeName', title: '总公司财务总监意见', width: 30 },
                { field: 'hQLeaderId', title: '总公司分管领导编号', width: 30 },
                { field: 'hQLeaderTime', title: '总公司分管领导时间', width: 30 },
                { field: 'HQLeaderCommentTypeIdName', title: '总公司分管领导意见', width: 30 },
                { field: 'hQCEOId', title: '总公司执行董事编号', width: 30 },
                { field: 'hQCEOTime', title: '总公司执行董事时间', width: 30 },
                { field: 'HQCEOCommentTypeName', title: '总公司执行董事意见', width: 30 }
            ]],
            toolbar:[{
                id:'btnFinancemeetingAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnFinancemeetingEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnFinancemeetingDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                onclickFinancemeetingAdd();
                onClickFinancemeetingEdit();
                onClickFinancemeetingapplicationwfaDelete();
            }
        });
    }



    /**
     * 添加数据
     */
    function onclickFinancemeetingAdd() {
        var buttonId = "btnFinancemeetingAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            initFinancemeetingWindow({});
            process.afterClick();
        })
    }
    function  onClickFinancemeetingEdit(){
        var buttonId = "btnFinancemeetingEdit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FinancemeetingTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/oa/finance/Financemeetingapplicationwfa_load.action?financemeetingapplicationwfa.id=" + selected.id
                fw.post(url, null, function (data) {
//                    data['financemeeting.creatorName'] = selected.creatorName;
//                    data['financemeeting.executorName'] = selected.executorName;
//                    data['financemeeting.checkerName'] = selected.checkerName;
                    initFinancemeetingWindow(data);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }
    /**
     * 初始化弹出窗口
     * @param data
     */
    function initFinancemeetingWindow(data) {

        data["financemeetingapplicationwfa.operatorId"] = loginUser.getId();
        data["financemeetingapplicationwfa.operatorName"] = loginUser.getName();
        var url = WEB_ROOT + "/modules/oa/finance/FinancemeetingappLicationwfa_Save.jsp?token=" + token;
        var windowId = "FinancemeetingapplicationwfaWindow" + token;
        fw.window(windowId, '财务会议申请WFA', 950, 420, url, function () {
            fw.textFormatCurrency('money'+token);
            fw.getComboTreeFromKV('departmentLeaderCommentTypeId'+token, 'OA_WFAPassType', 'k', fw.getMemberValue(data, 'financemeetingapplicationwfa.departmentLeaderCommentTypeId'));
            fw.getComboTreeFromKV('generalManagerCommentTypeId'+token, 'OA_WFAPassType', 'k', fw.getMemberValue(data, 'financemeetingapplicationwfa.generalManagerCommentTypeId'));
            fw.getComboTreeFromKV('hQFinanceDirectorCommentTypeId'+token, 'OA_WFAPassType', 'k', fw.getMemberValue(data, 'financemeetingapplicationwfa.hQFinanceDirectorCommentTypeId'));
            fw.getComboTreeFromKV('hQLeaderCommentTypeId'+token, 'OA_WFAPassType', 'k', fw.getMemberValue(data, 'financemeetingapplicationwfa.hQLeaderCommentTypeId'));
            fw.getComboTreeFromKV('hQCEOCommentTypeId'+token, 'OA_WFAPassType', 'k', fw.getMemberValue(data, 'financemeetingapplicationwfa.hQCEOCommentTypeId'));
            onClickFinancemeetingapplicationwfaSubmit();
            fw.formLoad('formFinancemeetingapplicationwfa' + token, data);
        }, null);
    }
    /**
     * 数据提交事件
     */
    function onClickFinancemeetingapplicationwfaSubmit() {
        var buttonId = "btnFinancemeetingapplicationwfaSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            fw.CurrencyFormatText('money'+token);
            var formId = "formFinancemeetingapplicationwfa" + token;
            var url = WEB_ROOT + "/oa/finance/Financemeetingapplicationwfa_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("FinancemeetingTable"+token);
                fw.windowClose('FinancemeetingapplicationwfaWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }
    /**
     * 删除事件
     */
    function onClickFinancemeetingapplicationwfaDelete() {
        var buttonId = "btnFinancemeetingDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('FinancemeetingTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/oa/finance/Financemeetingapplicationwfa_delete.action?financemeetingapplicationwfa.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('FinancemeetingTable'+token);
                    }, null);
                }, null);
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
