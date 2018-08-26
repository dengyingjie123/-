/**
 * OA_任务
 * @param token
 * @returns {{initModule: initModule}}
 * @constructor
 */
var TaskClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickTaskSearch();
        // 初始化查询重置事件
        onClickTaskSearchReset();
        //初始化下拉列表
        initStatusTree(null,"-2");
        // 初始化表格
        initTaskTaskTable();
    }

    /**
     * 初始化下拉列表项
     */
    function initStatusTree(combotreeId,selectIndexId) {
        if(combotreeId==null){
            combotreeId="search_status" + token;
        }
        var URL = WEB_ROOT + "/oa/task/Task_StatusTree.action";
        fw.combotreeLoad(combotreeId,URL,selectIndexId);
    }

    /**
     * 初始换表格
     */
    function initTaskTaskTable() {
        var strTaskTableId = 'TaskTable' + token;
        var url = WEB_ROOT + "/oa/task/Task_list.action";
        $('#' + strTaskTableId).datagrid({
            title: 'OA_任务',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            loadFilter: function (data) {
                try {
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
                    { field: 'sid', title: 'sid', hidden: true, width: 30 },
                    { field: 'id', title: 'id', hidden: true, width: 30 },
                    { field: 'name', title: '名称', width: 30 },
                    { field: 'description', title: '描述', width: 30 },
                    { field: 'process', title: '完成进度', width: 30 },
                    { field: 'statusName', title: '状态', width: 30 },
                    { field: 'startTime', title: '开始时间', width: 30 },
                    { field: 'stopTime', title: '结束时间', width: 30 },
                    { field: 'creatorName', title: '创建者', width: 30 },
                    { field: 'createTime', title: '创建时间', width: 30 },
                    { field: 'executorName', title: '执行人', width: 30 },
                    { field: 'executeTime', title: '执行时间', width: 30 },
                    { field: 'checkerName', title: '检查人', width: 30 },
                    { field: 'checkTime', title: '检查时间', width: 30 }
                ]
            ],
            toolbar: [
                {
                    id: 'btnTaskAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnTaskEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnTaskDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onclickTaskAdd();
                onClickTaskEdit();
                onClickTaskDelete();

            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initTaskWindow(data) {

        data["task.operatorId"] = loginUser.getId();
        data["task.operatorName"] = loginUser.getName();

        var url = WEB_ROOT + "/modules/oa/task/Task_Save.jsp?token=" + token;
        var windowId = "TaskWindow" + token;
        fw.window(windowId, 'OA_任务列表', 600, 450, url, function () {
            initStatusTree("status"+token,data["task.status"]);

            onClickTaskSubmit();
            fw.formLoad('formTask' + token, data);
        }, null);
    }

    /**
     * 提交事件
     */
    function onClickTaskSubmit() {
        var buttonId = "btnTaskSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formTask" + token;
            var url = WEB_ROOT + "/oa/task/Task_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("TaskTable" + token);
                fw.windowClose('TaskWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 添加OA_任务
     */
    function onclickTaskAdd() {
        var buttonId = "btnTaskAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            initTaskWindow({});
            process.afterClick();
        })
    }

    /**
     * 修改OA_任务数据
     */
    function onClickTaskEdit() {
        var buttonId = "btnTaskEdit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('TaskTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/oa/task/Task_load.action?task.id=" + selected.id;
                fw.post(url, null, function (data) {
                    data['taskVO.creatorName'] = selected.creatorName;
                    data['taskVO.executorName'] = selected.executorName;
                    data['taskVO.checkerName'] = selected.checkerName;
                    initTaskWindow(data);
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
    function onClickTaskDelete() {
        var buttonId = "btnTaskDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            fw.datagridGetSelected('TaskTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {

                    var url = WEB_ROOT + "/oa/task/Task_delete.action?task.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('TaskTable' + token);
                        process.afterClick();
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickTaskSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "TaskTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            //获取是减控件的值
            params["taskVO_startTime_Start"] = fw.getFormValue('search_StartTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_startTime_End"] = fw.getFormValue('search_StartTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_stopTime_Start"] = fw.getFormValue('search_StopTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_stopTime_End"] = fw.getFormValue('search_StopTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_createTime_Start"] = fw.getFormValue('search_CreateTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_createTime_End"] = fw.getFormValue('search_CreateTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_executeTime_Start"] = fw.getFormValue('search_ExecuteTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_executeTime_End"] = fw.getFormValue('search_ExecuteTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_checkTime_Start"] = fw.getFormValue('search_CheckTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["taskVO_checkTime_End"] = fw.getFormValue('search_CheckTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            //文本框中的值
            params['taskVO.name'] = $('#search_name' + token).val();
            params['taskVO.executorName'] = $('#search_executorName' + token).val();
            params['taskVO.creatorName'] = $('#search_creatorName' + token).val();

            var ids=$("#search_status"+token).combotree("getValue");// = fw.combotreeGetCheckedIds('search_status' + token, ',', "'");

             params['taskVO.status'] = ids;

            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickTaskSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            //清空时间控件
            $('#search_StartTime_Start' + token).datebox("setValue", '');
            $('#search_StartTime_End' + token).datebox("setValue", '');
            $('#search_StopTime_Start' + token).datebox("setValue", '');
            $('#search_StopTime_End' + token).datebox("setValue", '');
            $('#search_CreateTime_Start' + token).datebox("setValue", '');
            $('#search_CreateTime_End' + token).datebox("setValue", '');
            $('#search_ExecuteTime_Start' + token).datebox("setValue", '');
            $('#search_ExecuteTime_End' + token).datebox("setValue", '');
            $('#search_CheckTime_Start' + token).datebox("setValue", '');
            $('#search_CheckTime_End' + token).datebox("setValue", '');
            $('#search_name' + token).val('');
            $('#search_executorName' + token).val('');
            $('#search_creatorName' + token).val('');
            //下拉列表选中第一个
            fw.combotreeClear("search_status"+token);
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
