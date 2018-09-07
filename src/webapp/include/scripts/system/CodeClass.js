/**
 * Created by Administrator on 2015/4/22.
 * 系统管理
 */
var CodeClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickCodeSearch();
        // 初始化查询重置事件
        onClickCodeSearchReset();
        // 初始化表格
        initCodeCodeTable();
    }
    /*
    *构造初始化表格脚本
     */
    function initCodeCodeTable() {
        var strCodeTableId = 'CodeTable'+token;
        var url = WEB_ROOT+"/system/Code_list.action";

        $('#'+strCodeTableId).datagrid({
            title:'验证码管理',
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
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'code', title: 'code', width: 30 },
                { field: 'createTime', title: '创建时间', width: 30 },
                { field: 'typeName', title: '类型', width: 30 },
                { field: 'availableTime', title: '有效时间', width: 30 },
                { field: 'expiredTime', title: '失效时间', width: 30 },
                { field: 'usedTime', title: '使用时间', width: 30 },
                { field: 'userName', title: '使用用户名称', width: 30 },
                { field: 'iP', title: '使用者IP', width: 30 }
            ]],
            onLoadSuccess:function() {
                // 初始化事件
                onclickCodeAdd();
                onClickCodeEdit();
                onClickCodeDelete();
            }
        });
    }

    /**
     * 删除事件
     */
    function onclickCodeAdd(){
         var buttonId="btnCodeAdd"+token
        fw.bindOnClick(buttonId,function(process){
            process.beforeClick();
            initCodeWindow({});
            process.afterClick()
        })
    }
    /**
     * 修改事件
     */
    function onClickCodeEdit() {
        var buttonId = "btnCodeEdit" + token;
        fw.bindOnClick(buttonId, function(process) {

            fw.datagridGetSelected('CodeTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/system/Code_load.action?code.id="+selected.id;
                fw.post(url, null, function(data){
                    data['userName']=selected.userName;
                    initCodeWindow(data);
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
    function onClickCodeDelete() {
        var buttonId = "btnCodeDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            process.beforeClick();
            fw.datagridGetSelected('CodeTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/system/Code_delete.action?code.id="+selected.id;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('CodeTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }
    /**
     * 查询事件
     */
    function onClickCodeSearch() {
        var buttonId = "btnCodeSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strCodeTableId = "CodeTable"+token;
            var params = $( '#' + strCodeTableId).datagrid('options').queryParams;
            params["codeVO_expiredTime_Start"] = fw.getFormValue('search_ExpiredTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["codeVO_expiredTime_End"] = fw.getFormValue('search_ExpiredTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["codeVO_availableTime_Start"] = fw.getFormValue('search_AvailableTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["codeVO_availableTime_End"] = fw.getFormValue('search_AvailableTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["codeVO_usedTime_Start"] = fw.getFormValue('search_UsedTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["codeVO_usedTime_End"] = fw.getFormValue('search_UsedTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["codeVO.userName"] = $("#search_userName"+token).val();
            params["codeVO.iP"] = $("#search_IP"+token).val();
            params["codeVO.type"]=$("#search_Type"+token).val();
            $( '#' + strCodeTableId).datagrid('load');
        });
    }
    /**
     * 查询重置事件
     */
    function onClickCodeSearchReset() {
        var buttonId = "btnCodeSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $('#search_ExpiredTime_Start'+token).datebox("setValue", '');
            $('#search_ExpiredTime_End'+token).datebox("setValue", '');
            $('#search_AvailableTime_Start'+token).datebox("setValue", '');
            $('#search_AvailableTime_End'+token).datebox("setValue", '');
            $('#search_UsedTime_Start'+token).datebox("setValue", '');
            $('#search_UsedTime_End'+token).datebox("setValue", '');
            $("#search_userName"+token).val('');
            $("#search_IP"+token).val('');
            $('#search_Type'+token+':first option:first').attr("selected","selected");
        });
    }
    /**
     * 初始化弹出窗口
     * @param data
     */
    function initCodeWindow(data) {
//
//        data["code.operatorId"] = loginUser.getId();
//        data["codeVO.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/system/Code_Save.jsp?token="+token;
        var windowId = "CodeWindow" + token;
        fw.window(windowId, '验证码管理', 350, 350, url, function() {
            onClickCodeSubmit();
            fw.formLoad('formCode'+token, data);
        }, null);
    }
    /**
     * 数据提交事件
     */
    function onClickCodeSubmit() {
        var buttonId = "btnCodeSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formCode" + token;
            var url = WEB_ROOT + "/system/Code_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("CodeTable"+token);
                fw.windowClose('CodeWindow'+token);
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
