/**
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var IstitutionClientClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

       // initFormMoneyLogSearch();

        // 初始化按钮事件

        // 初始化查询事件
       // onClickMoneyLogSearch();
        // 初始化查询重置事件
        //onClickMoneyLogSearchReset();


        // 初始化表格

        // 初始化表格
        initTableClientTable();


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
    function initTableClientTable() {
        var strTableId = 'ClientGuanLiTable'+token;
        var url = WEB_ROOT+"/system/Client_list.action";


        $('#'+strTableId).datagrid({
            title: '客户信息',
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
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            columns: [[
                { field: 'sid', title: '序号', hidden:true, width: 30 },
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'Name', title: '姓名', width: 30 },
                { field: 'Sex', title: '性别', width: 30 },
                { field: 'Mobile', title: '电话', width: 15 },
                { field: 'Email', title: '邮箱', width: 30 },
                { field: 'Birthday', title: '生日', width: 30 }
            ]],
            toolbar:[{
                id:'btnClientAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnClientEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnClientDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                onClickClientAdd();
                onClickClientDelete();
                onClickClientEdit();
            }
        });

    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowClientWindow(data) {



        var url =  WEB_ROOT + "/modules/system/client_update.jsp?token="+token;
        var windowId = "ClientWindow" + token;
        fw.window(windowId, '客户信息窗口', 450, 380, url, function() {
            //alert($('#save_type'+token).length);
            //fw.alertReturnValue(data);
            //  data['moneyLog.type']

            // 初始化控件


            // 初始化类型combotree
            //alert($('#inOrOut'+token).length);
           // onSelectedInOrOut(data);
           // fw.getComboTreeFromKV('inOrOut'+token, 'OA_Finance_MoneyLog_InOrOut', 'k', fw.getMemberValue(data, 'moneyLog.inOrOut'));


            // 初始化表单提交事件
            onClickClientSubmit();

           /* using(SCRIPTS_ROOT+'/system/OrgClass.js', function () {
                var orgClass = new OrgClass(token);
                var tree = $('#departmentId'+token).combotree('tree');
                orgClass.buildTreeDepartment(tree, function() {

                });
            });*/

            // 加载数据
            fw.formLoad('formClient'+token, data);
        }, null);
    }



    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////






    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickClientAdd() {

        var buttonId = "btnClientAdd" + token;

        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowClientWindow({});
        });

    }

    /**
     * 删除事件
     */
    function onClickClientDelete() {
        var buttonId = "btnClientDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ClientGuanLiTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/system/Client_delete.action?clientPO.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('ClientGuanLiTable'+token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickClientEdit() {
        var butttonId = "btnClientEdit" + token;
        fw.bindOnClick(butttonId, function(process) {

            fw.datagridGetSelected('ClientGuanLiTable'+token, function(selected){
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/system/Client_load.action?clientPO.sid="+sid;

                fw.post(url, null, function(data){

                   // fw.alertReturnValue(data);
                    initWindowClientWindow(data);
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
    function onClickClientSubmit() {
        var buttonId = "btnClientSubmit" + token;
        //alert(buttonId);
        fw.bindOnClick(buttonId, function(process){
            var formId = "formClient" + token;

            var url = WEB_ROOT + "/system/Client_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){

                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("ClientGuanLiTable"+token);
                fw.windowClose('ClientWindow'+token);
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