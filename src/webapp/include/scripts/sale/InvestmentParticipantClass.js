/**
 * Created by Administrator on 2015/4/7.
 * 投资参与者
 */

var InvestmentParticipantClass = function (token) {
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        initFormInvestmentParticipantSearch();
        //查询事件
        onClickInvestmentParticipantSearch();
        // 初始化查询重置事件
        onClickInvestmentParticipantSearchReset();
        // 初始化表格
        initInvestmentParticipantTable();
    }

    //查询初始化方法
    function initFormInvestmentParticipantSearch() {
        //1402 ,1403 组名 Sale_InvestmentParticipantJoinStatus
        //1405、1406 Sale_InvestmentParticipantJoinType
        //参与状态
        fw.getComboTreeFromKV('search_statusName' + token, 'Sale_InvestmentParticipantJoinStatus', 'k', '-2');
        //参与类型
        fw.getComboTreeFromKV('search_typeName' + token, 'Sale_InvestmentParticipantJoinType', 'k', '-2');
    }

    //查询事件
    function onClickInvestmentParticipantSearch() {
        var buttonId = "btninvestmentParticipantSearchSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "InvestmentParticipantTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            //客户名称
            params["investmentParticipantVO.customerName"] = $("#search_customerName" + token).val();
            params["investmentParticipantVO.investmentplanName"]=$("#search_investmentplanName"+token).val();
           //树形节点查询
            params["investmentParticipantVO.joinStatus"]=fw.getFormValue('search_statusName'+token,fw.type_form_combotree,fw.type_get_value);
            params["investmentParticipantVO.joinType"]=fw.getFormValue('search_typeName'+token,fw.type_form_combotree,fw.type_get_value);
            //根据时间段查询
            params["investmentParticipantVO_joinTime_Start"] = fw.getFormValue('search_JoinTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["investmentParticipantVO_joinTime_End"] = fw.getFormValue('search_JoinTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["investmentParticipantVO_appointmentTime_Start"] = fw.getFormValue('search_AppointmentTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["investmentParticipantVO_appointmentTime_End"] = fw.getFormValue('search_AppointmentTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["investmentParticipantVO_quitTime_Start"] = fw.getFormValue('search_QuitTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["investmentParticipantVO_quitTime_End"] = fw.getFormValue('search_QuitTime_End' + token, fw.type_form_datebox, fw.type_get_value);

            $('#' + strTableId).datagrid('load');

            fw.treeClear()
        });
    }
    //查询重置设置
    function onClickInvestmentParticipantSearchReset() {
        var buttonId = "btninvestmentParticipantSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            //清空文本框
            $("#search_customerName"+token).val('');
            $("#search_investmentplanName"+token).val('');
            //清空时间控件
            $('#search_JoinTime_Start'+token).datebox("setValue", '');
            $('#search_JoinTime_End'+token).datebox("setValue", '');
            $('#search_AppointmentTime_Start'+token).datebox("setValue", '');
            $('#search_AppointmentTime_End'+token).datebox("setValue", '');
            $('#search_QuitTime_Start'+token).datebox("setValue", '');
            $('#search_QuitTime_End'+token).datebox("setValue", '');
            //清空树形节点
            fw.combotreeClear('search_statusName'+token);
            fw.combotreeClear('search_typeName'+token);
        });
    }

    // 构造初始化表格脚本
    function initInvestmentParticipantTable() {
        var strTableId = 'InvestmentParticipantTable' + token;
        var url = WEB_ROOT + "/sale/InvestmentParticipant_list.action";

        $('#' + strTableId).datagrid({
            title: '投资参与者',
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
                    //alert(JSON.stringify(data));
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
                    { field: 'Sid', title: 'sid', hidden: true, width: 30 },
                    { field: 'Id', title: 'id', hidden: true, width: 30 },
                    { field: 'investmentplanName', title: '投资计划名称', width: 30 },
                    { field: 'customerName', title: '客户名称', width: 30 },
                    { field: 'kv_StatusName', title: '参与状态', width: 30 },
                    { field: 'kv_TypeName', title: '参与类型', width: 30 },
                    { field: 'joinMoney', title: '参与金额', width: 30 },
                    { field: 'joinTime', title: '参与时间', width: 30 },
                    { field: 'appointmentTime', title: '预约时间', width: 30 },
                    { field: 'quitTime', title: '退出时间', width: 30 }
                ]
            ],
            toolbar: [
                {
                    id: 'btnInvestmentplan' + token,
                    text: '弹出详情',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                //获取详情
                onClickInvestmentParticipantBtn();
            }
        });
    }
    //弹出详情
    function onClickInvestmentParticipantBtn() {
        var buttonId = "btnInvestmentplan" + token;
        //弹出详情
        fw.bindOnClick(buttonId, function(process) {

            fw.datagridGetSelected('InvestmentParticipantTable'+token, function(selected){
                process.beforeClick();

                var id = selected.id;
                var url = WEB_ROOT + "/sale/InvestmentParticipant_load.action?investmentParticipant.id="+selected.id;
                fw.post(url, null, function(data){
                    //记载其他数据从列表获得
                    data["investmentParticipant.investmentplanName"]=selected.investmentplanName;
                    data["investmentParticipant.customerName"] = selected.customerName;
                    data["investmentParticipant.kv_StatusName"]=selected.kv_StatusName;
                    data["investmentParticipant.kv_TypeName"]=selected.kv_TypeName;

                    //弹出窗口
                    initWindowInvestmentParticipantWindow(data);
                    process.afterClick();
                },function(){
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 弹出详情
     */
    function initWindowInvestmentParticipantWindow(data) {

        data["investmentParticipant.operatorId"] = loginUser.getId();
        data["investmentParticipant.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/sale/InvestmentParticipant_Save.jsp?token="+token;

        var windowId = "InvestmentParticipantWindow" + token;

        fw.window(windowId, '投资参与者', 350, 400, url, function() {
            // 加载数据
            fw.textFormatCurrency('joinMoney'+token);
            fw.formLoad('formInvestmentParticipant'+token, data);
        }, null);
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