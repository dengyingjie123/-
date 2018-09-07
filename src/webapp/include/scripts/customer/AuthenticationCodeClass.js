/**
 * Created by Administrator on 2015-4-7.
 */
var AuthenticationCodeClass = function(token){

    function initAll(){
        //初始化查询框
        initFormLegalAgreementSearch();
        //绑定查询按钮点击事件
        onClickAuthCodeSearch();
        //绑定查询重置按钮点击事件
        onClickAuthCodeSearchReset();
        //初始化表格
        initTableAuthCode();
    }

    /**
     * 初始化查询框
     */
    function initFormLegalAgreementSearch() {
        fw.getComboTreeFromKV('search_InOrOut'+token, 'OA_Finance_MoneyLog_InOrOut', 'k','-2');
        fw.getComboTreeFromKV('search_sendType'+token, 'CRM_CustomerAuthenticationSendType', 'k', '-2');
        fw.getComboTreeFromKV('search_status'+token, 'CRM_CustomerAuthenticationStatus', 'k', '-2');
    }

    /**
     * 绑定查询按钮点击事件
     */
    function onClickAuthCodeSearch() {
        var buttonId = "btnSearchAuthCode" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "AuthCodeTable" + token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["sendTimeStart"] = fw.getFormValue('search_SendTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["sendTimeEnd"] = fw.getFormValue('search_SendTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["expiredTimeStart"] = fw.getFormValue('search_ExpiredTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["expiredTimeEnd"] = fw.getFormValue('search_ExpiredTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["authenticationTimeStart"] = fw.getFormValue('search_AuthenticationTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["authenticationTimeEnd"] = fw.getFormValue('search_AuthenticationTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["info"] = $("#search_info" + token).val();
            var status = $('#search_status' + token).combotree('getText');
            var sendType = $('#search_sendType' + token).combotree('getText');
            params["status"] = status;
            params["sendType"] = sendType;
            params["name"] = $('#search_name' + token).val();
            $( '#' + strTableId).datagrid('load');
            fw.treeClear()
        });
    }

    /**
     * 绑定查询重置按钮点击事件
     */
    function onClickAuthCodeSearchReset() {
        var buttonId = "btnResetAuthCode" + token;
        fw.bindOnClick(buttonId, function(process) {
            $('#search_SendTime_Start'+token).datebox("setValue", '');
            $('#search_SendTime_End'+token).datebox("setValue", '');
            $('#search_ExpiredTime_Start'+token).datebox("setValue", '');
            $('#search_ExpiredTime_End'+token).datebox("setValue", '');
            $('#search_AuthenticationTime_Start'+token).datebox("setValue", '');
            $('#search_AuthenticationTime_End'+token).datebox("setValue", '');
            $("#search_info"+token).val('');
            $("#search_name"+token).val('');
            // 重置KV
            fw.combotreeClear('#search_sendType'+token);
            fw.combotreeClear('#search_status'+token);

            fw.combotreeClear('search_InOrOut' + token);
        });
    }

    /**
     * 初始化表格和触发事件
     */
    function initTableAuthCode(){
        // 关联到相应jsp页面中的datagrid
        var strTableId = "AuthCodeTable" + token;
        var url = WEB_ROOT + "/customer/CustomerAuthenticationCode_list.action";
        $('#'+strTableId).datagrid({
            title: '客户认证码',
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
                { field: 'id', title: '客户编号', hidden:true, width: 30 },
                { field: 'name', title: '客户名称', width: 30 },
                { field: 'sendType', title: '发送类型', width: 30 },
                { field: 'status', title: '状态', width: 30 },
                { field: 'code', title: '认证码', width: 30 },
                { field: 'sendTime', title: '发送时间', width: 30 },
                { field: 'info', title: '信息', width: 30 }
            ]],
//            toolbar:[{
//                id:'btnAuthCodeAdd'+token,
//                text:'添加',
//                iconCls:'icon-add'
//            },{
//                id:'btnAuthCodeEdit'+token,
//                text:'修改',
//                iconCls:'icon-edit'
//            },{
//                id:'btnAuthCodeDelete'+token,
//                text:'删除',
//                iconCls:'icon-cut'
//            }],
            frozenColumns:[[ //固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            onLoadSuccess:function() {
                // 如果初始化成功，也添加ToolBar的各项事件
                onClickAuthCodeAdd();
                onClickAuthCodeDelete();
                onClickAuthCodeEdit();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickAuthCodeAdd() {
        var buttonId = "btnAuthCodeAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initAuthCodeWindow({},0, true);
        });
    }

    /**
     * 删除事件
     */
    function onClickAuthCodeDelete() {
        var buttonId = "btnAuthCodeDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected("AuthCodeTable" + token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/customer/CustomerAuthenticationCode_delete.action?customerAuthenticationCodePO.sid=" + selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload("AuthCodeTable" + token);
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
    function onClickAuthCodeEdit() {
        var butttonId = "btnAuthCodeEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            process.beforeClick();
            fw.datagridGetSelected("AuthCodeTable" + token, function(selected){
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerAuthenticationCode_load.action?customerAuthenticationCodePO.id=" + selected.id;
                fw.post(url, null, function(data){
                    data['name'] = selected.name;
                    initAuthCodeWindow(data, null, false);
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
    function initAuthCodeWindow(data, obj, isInsert) {
        var url =  WEB_ROOT + "/modules/customer/CustomerAuthenticationCode_Save.jsp?token="+token;
        var windowId = "CustomerAuthCodeWindow" + token;
        fw.window(windowId, isInsert ? "添加客户认证信息" : "修改客户认证信息", 380, 380, url, function() {
            // 初始化查询客户事件
            onClickCheckCustomer();
            // 初始化表单提交事件
            onClickAuthCodeSubmit();
            // 通过KV获取ComboTree的值
            fw.getComboTreeFromKV('sendType'+token, 'CRM_CustomerAuthenticationSendType', 'k', fw.getMemberValue(data, 'customerAuthenticationCodePO.sendType'));
            fw.getComboTreeFromKV('status'+token, 'CRM_CustomerAuthenticationStatus', 'k', fw.getMemberValue(data, 'customerAuthenticationCodePO.status'));
            // 加载数据
            fw.formLoad('formCustomerAuthCode' + token, data);
            $("#operatorName" + token).val(loginUser.getName());
        }, null);
    }

    /**
     * 查询客户事件
     */
    function onClickCheckCustomer(){
        $('#btnCheckCustomer' + token).bind('click', function () {
            var url =  WEB_ROOT + "/modules/production/Select_Customer.jsp?token=" + token;
            var selectionWindowId = "CustomerSelectWindow" + token;
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function() {
                //加载js
                using(SCRIPTS_ROOT+'/production/CustomerSelectedClass.js', function () {
                    var obj = new AuthenticationCodeClass(token);
                    var customerSelected = new CustomerSelectedClass(token, obj);
                    customerSelected.initModule();
                });
            }, null);
        })
    }

    /**
     * 保存事件
     */
    function onClickAuthCodeSubmit() {
        var buttonId = "btnAuthCodeSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formCustomerAuthCode" + token;
            var url = WEB_ROOT + "/customer/CustomerAuthenticationCode_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("AuthCodeTable" + token);
                fw.windowClose('CustomerAuthCodeWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    return{
        initModule:function(){
            return initAll();
        },
        loadCustomerInfo:function(customerId,customerName,attribute,accountId,bankName) {
            //加载选择客户的id，name，attribute
            //var customerNameAndBank;
            //if(bankName!=""){
                //customerNameAndBank=customerName+"(开户行："+bankName+")"
            //}else{
                //customerNameAndBank=customerName;
            //}
            //$("#customerName"+token).val(customerNameAndBank);
            //$("#attribute"+token).val(attribute);
            $("#customerId" + token).val(customerId);
            $("#customerName" + token).val(customerName);
            //$("#accountId"+token).val(accountId);
        }
    };
}