/**
 * 记账日志处理类
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var MoneyLogReportDetailInOrOutClass = function(token, inOrOut) {

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
        initTalbeDetailTreeTable();
    }

    function initFormMoneyLogSearch() {
//        var url = WEB_ROOT+"/system/Department_list.action";
//        //alert($('#search_Department'+token).length);
//        //var tree = $().combotree('tree');
//        fw.combotreeLoadWithCheck('#search_Department'+token, url, null, null, null);
//
//        fw.getComboTreeFromKV('search_InOrOut'+token, 'OA_Finance_MoneyLog_InOrOut', 'k','-2');
    }

    function initTalbeDetailTreeTable() {

        var columnUrl = WEB_ROOT + "/oa/finance/MoneyLogReportDetailInOrOut_listColumn.action?InOrOut="+inOrOut;
        fw.post(columnUrl, null, function(data) {

            var strTableId = "MoneyLogReportDetailInOrOutTreeTable" + token;
            var url = WEB_ROOT+"/oa/finance/MoneyLogReportDetailInOrOut_listCompany.action?InOrOut="+inOrOut;
            $('#'+strTableId).treegrid({
                title: '公司收支表',
                url:url,
                idField:'id',
                treeField:'departmentName',
                loadFilter:function(data){
                    //alert(JSON.stringify(data));
                    //alert(data.code);
                    try {
                        data = fw.dealReturnObject(data);
                        //fw.alertReturnValue(data);
                        return data;
                    }
                    catch(e) {
                    }
                },
                columns:data

            });

        }, null);



//        var strTableId = "MoneyLogReportDetailInTreeTable" + token;
//        var url = WEB_ROOT+"/oa/finance/MoneyLogReportDetailIn_listCompany.action";
//        $('#'+strTableId).treegrid({
//            title: '公司收支表',
//            url:url,
//            idField:'id',
//            treeField:'departmentName',
//            loadFilter:function(data){
//                //alert(JSON.stringify(data));
//                //alert(data.code);
//                try {
//                    data = fw.dealReturnObject(data);
//                    //fw.alertReturnValue(data);
//                    return data;
//                }
//                catch(e) {
//                }
//            },
//            columns:[[
//                {title:'部门',field:'departmentName',hidden:false,width:250},
//                {title:'产品利润',field:'40',hidden:false,width:130},
//                {title:'银行利息',field:'39',hidden:false,width:130},
//                {title:'合计',field:'total',hidden:false,width:130}
//            ]]
//        });


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
            fw.getComboTreeFromKV('type'+token, 'OA_Finance_MoneyLog_Type', 'k', fw.getMemberValue(data, 'moneyLog.type'));
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
                var sid = selected.sid;
                var url = WEB_ROOT + "/oa/finance/MoneyLog_load.action?moneyLog.sid="+sid;
                fw.post(url, null, function(data){
                    //fw.alertReturnValue(data);
                    initWindowMoneyLogWindow(data);
                }, null);
            })

        });
    }

    /**
     * 查询事件
     */
    function onClickMoneyLogSearch() {
        var buttonId = "btnSearchMoneyLog" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "MoneyLogReportDetailInOrOutTreeTable"+token;
            var params = $( '#' + strTableId).treegrid('options').queryParams;

            //alert(JSON.stringify(params));
            //alert($("#search_user_name"+token).val());
            params["moneyTime_Start"] = fw.getFormValue('search_MoneyTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["moneyTime_End"] = fw.getFormValue('search_MoneyTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            //params["moneyLogVO.inOrOutName"] = fw.getFormValue('search_InOrOut'+token, fw.type_form_combotree, fw.type_get_text);
            //alert(JSON.stringify(params));

//            var ids = fw.combotreeGetCheckedIds('search_Department' + token, ',', "'");
//            // alert(ids);
//            params["Departments"] = ids;

            $( '#' + strTableId).treegrid('load');


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
//            fw.combotreeClear('search_InOrOut'+token);
//            fw.combotreeClear('search_Department'+token);
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