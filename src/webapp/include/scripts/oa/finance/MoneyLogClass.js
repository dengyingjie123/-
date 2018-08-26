/**
 * 记账日志处理类
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var MoneyLogClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

        initFormMoneyLogSearch();

        // 初始化按钮事件

        // 初始化查询事件
        onClickMoneyLogSearch();
        // 初始化查询重置事件
        onClickMoneyLogSearchReset();


        // 初始化表格

        // 初始化表格
        initTableMonelyLogTable();


    }

    function initFormMoneyLogSearch() {
        var url = WEB_ROOT+"/system/Department_list.action";
        //alert($('#search_Department'+token).length);
        //var tree = $().combotree('tree');
        fw.combotreeLoadWithCheck('#search_Department'+token, url, null, null, null);

        fw.getComboTreeFromKV('search_InOrOut'+token, 'OA_Finance_MoneyLog_InOrOut', 'k','-2');
    }

    /**
     * 初始化表格
     */
    function initTableMonelyLogTable() {
        var strTableId = 'MoneyLogGuanLiTable'+token;
        var url = WEB_ROOT+"/oa/finance/MoneyLog_list.action";
       // alert(url);

        $('#'+strTableId).datagrid({
            title: '记账日志',
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
                    //fw.alertReturnValue(data);
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
                { field: 'sid', title: '序号', hidden:true, width: 30 },
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'departmentName', title: '部门', width: 30 },
                { field: 'typeName', title: '类型', width: 30 },
                { field: 'inOrOutName', title: '收支', width: 15 },
                { field: 'name', title: '名称', width: 30 },
                { field: 'money', title: '金额', width: 30 },
                { field: 'moneyTime', title: '账务时间', width: 30 },
                { field: 'comment', title: '备注', width: 30 },
                { field: 'operatorName', title: '操作人', width: 30 },
                { field: 'operateTime', title: '操作时间', width: 30 }
            ]],
            onLoadSuccess:function() {
                onClickMoneyLogAdd();
                onClickMoneyLogDelete();
                onClickMoneyLogEdit();
            }
        });

    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowMoneyLogWindow(data) {

        data["moneyLog.operatorId"] = loginUser.getId();
        data["moneyLog.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/oa/finance/MoneyLog_Save.jsp?token="+token;
        var windowId = "MoneyLogWindow" + token;
        fw.window(windowId, '记账窗口', 450, 380, url, function() {
            //alert($('#save_type'+token).length);
            //fw.alertReturnValue(data);
            //  data['moneyLog.type']

            // 初始化控件


            // 初始化类型combotree
            //alert($('#inOrOut'+token).length);
            onSelectedInOrOut(data);
            fw.getComboTreeFromKV('inOrOut'+token, 'OA_Finance_MoneyLog_InOrOut', 'k', fw.getMemberValue(data, 'moneyLog.inOrOut'));


            // 初始化表单提交事件
            onClickMoneyLogSubmit();

            using(SCRIPTS_ROOT+'/system/OrgClass.js', function () {
                var orgClass = new OrgClass(token);
                var tree = $('#departmentId'+token).combotree('tree');
                orgClass.buildTreeDepartment(tree, function() {

                });
            });

            // 加载数据
            fw.formLoad('formMoneyLog'+token, data);
        }, null);
    }



    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////






    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickMoneyLogAdd() {

        var buttonId = "btnMoneyLogAdd" + token;

        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowMoneyLogWindow({});
        });

    }

    /**
     * 删除事件
     */
    function onClickMoneyLogDelete() {
        var buttonId = "btnMoneyLogDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('MoneyLogGuanLiTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/oa/finance/MoneyLog_delete.action?moneyLog.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('MoneyLogGuanLiTable'+token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickMoneyLogEdit() {
        var butttonId = "btnMoneyLogEdit" + token;
        fw.bindOnClick(butttonId, function(process) {

            fw.datagridGetSelected('MoneyLogGuanLiTable'+token, function(selected){
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/oa/finance/MoneyLog_load.action?moneyLog.sid="+sid;
                fw.post(url, null, function(data){
                    //fw.alertReturnValue(data);
                    initWindowMoneyLogWindow(data);
                    process.afterClick();
                }, function() {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 查询事件
     */
    function onClickMoneyLogSearch() {
        var buttonId = "btnSearchMoneyLog" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "MoneyLogGuanLiTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            //alert(JSON.stringify(params));
            //alert($("#search_user_name"+token).val());
            params["moneyLogVO_moneyTime_Start"] = fw.getFormValue('search_MoneyTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["moneyLogVO_moneyTime_End"] = fw.getFormValue('search_MoneyTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["moneyLogVO.inOrOutName"] = fw.getFormValue('search_InOrOut'+token, fw.type_form_combotree, fw.type_get_text);
            //alert(JSON.stringify(params));

            var ids = fw.combotreeGetCheckedIds('search_Department' + token, ',', "'");
            // alert(ids);
            params["Departments"] = ids;

            $( '#' + strTableId).datagrid('load');


            fw.treeClear()
        });
    }

    /**
     * 查询重置事件
     */
    function onClickMoneyLogSearchReset() {
        var buttonId = "btnResetMoneyLog" + token;
        fw.bindOnClick(buttonId, function(process) {
            $('#search_MoneyTime_Start'+token).datebox("setValue", '');
            $('#search_MoneyTime_End'+token).datebox("setValue", '');
            fw.combotreeClear('search_InOrOut'+token);
            fw.combotreeClear('search_Department'+token);
        });
    }

    /**
     * 数据提交事件
     */
    function onClickMoneyLogSubmit() {
        var buttonId = "btnMoneyLogSubmit" + token;
        //alert(buttonId);
        fw.bindOnClick(buttonId, function(process){
            var formId = "formMoneyLog" + token;
            var url = WEB_ROOT + "/oa/finance/MoneyLog_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("MoneyLogGuanLiTable"+token);
                fw.windowClose('MoneyLogWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    function onSelectedInOrOut(selectedId) {

        //alert('InorOut:' + selectedId);
        if (fw.checkIsNullObject(selectedId)) {
            selectedId = '-2';
        }
        else {
            selectedId = selectedId["moneyLog.type"];
        }

        $('#inOrOut'+token).combotree({
            onSelect:function(node) {

//                alert(selectedId);

                // 收
                if (node.id == '0') {
                    fw.getComboTreeFromKV('type'+token, 'OA_Finance_MoneyLog_Type_In', 'k', selectedId);
                }
                // 支
                else if (node.id == '1') {
                    fw.getComboTreeFromKV('type'+token, 'OA_Finance_MoneyLog_Type', 'k', selectedId);
                }
            }
        });

//        var tree = $('#inOrOut'+token).combotree('tree');
//        alert(tree);
//        $(tree).tree({
//            onSelect:function(node) {
//                alert(node.id);
//                // 收
//                if (node.id == '0') {
//                    fw.getComboTreeFromKV('type'+token, 'OA_Finance_MoneyLog_Type', 'k', '-2');
//                }
//                // 支
//                else if (node.id == '1') {
//                    fw.getComboTreeFromKV('type'+token, 'OA_Finance_MoneyLog_Type_In', 'k', '-2');
//                }
//            }
//        });
    }


    ///  事件定义 结束  /////////////////////////////////////////////////////////////////


    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        }
    };
}