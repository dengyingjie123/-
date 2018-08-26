/**
 * 描述： 签约销售合同
 * 说明:
 * Created by zhouhaihong on 2015/12/24.
 */
var BlankContractClass = function (token) {
    function initAll() {
        initContactBlankSerrch();
        //查询
        onClickContractBlankSearch();
        //查询重置
        onClickContractBlankSearchReset();
        //表格
        initContractBlankTable();
    }

    /**
     * 空白合同列表
     */
    function initContractBlankTable() {
        var strTableId = 'BlankContractTable' + token;
        var url = WEB_ROOT + "/sale/contract/Contract_getListBlankContracts.action";

        $('#' + strTableId).datagrid({
            title: '空白销售合同',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: false, //多选
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
                [
                    // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},

                    {field: 'departmentName', title: '财富中心'},
                    {field: 'contractNo', title: '合同号'},
                    {field: 'productionName', title: '产品名称'},
                    {field: 'applicationUserName', title: '签收人'},
                    {field: 'sigTime', title: '签收时间'}

                ]
            ],
            columns: [
                [
                    {field: 'sid', title: 'sid', hidden: true},
                    {field: 'id', title: 'id', hidden: true},
                    {field: 'operatorId', title: 'operatorId', hidden: true},
                    {field: 'operateTime', title: 'operateTime', hidden: true},
                    {field: 'orgId', title: 'orgId', hidden: true},
                    {field: 'receiveUserName', title: '销售员'},
                    {
                        field: 'status', title: '合同状态',
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "签约";
                            } else if (value == 1) {
                                return "未签约";
                            } else if (value == 2) {
                                return "合同异常";
                            } else {
                                return "";
                            }

                        }
                    },
                ]
            ],
            onLoadSuccess: function () {
                //分配销售合同
                onClickDistributeContract();
                //移交管理员
                onClickSendContract();
                //查看流转状态
                onClickContractRouteList();
            }
        });
    }

    //将合同根据状态移交到指定管理员
    function onClickSendContract() {
        var buttonId = "btnSendContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('BlankContractTable' + token, function (selecteds) {
                process.beforeClick();
                var nos = "";
                for (var i = 0; i < selecteds.length; i++) {
                    nos += selecteds[i].contractNo + ",";
                }

                fw.confirm("通知", "是否确认移交管理员", function () {
                    //去除最后一个，
                    var contractNo = nos.substring(0, nos.length - 1);
                    var url = WEB_ROOT + "/sale/contract/Contract_setSendContractsManager.action?contractPO.contractNo=" + contractNo;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        if (data == true) {
                            fw.alert("通知", "移交成功");
                            fw.datagridReload("BlankContractTable" + token);
                        } else {
                            fw.alert("通知", "移交失败");
                            fw.datagridReload("BlankContractTable" + token);
                        }
                    }, function () {
                        process.afterClick();
                    });
                }, function () {
                    process.afterClick();
                });
            }, function () {
                process.afterClick();
            });
        });
    }


    //查看流转状态
    function onClickContractRouteList() {

        var buttonId = "btnListContractRouteList" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('BlankContractTable' + token, function (selected) {
                if (selected.length > 1) {
                    fw.alert("警告", "请选择一条数据");
                    return false;
                }
                var selecteds = selected[0];
                process.beforeClick();
                var contractNo = selecteds.contractNo;
                var status = selecteds.status;
                var orgId = selecteds.orgId;
                var contractId = selecteds.id;

                var dataUrl =
                    "?token=" + token +
                    "&contractNo=" + contractNo +
                    "&status=" + status +
                    "&orgId=" + orgId +
                    "&contractId=" + contractId

                iniShowWindowRouteList(dataUrl);
                process.afterClick();
            });
        });
    }


    /**
     * 弹出流转
     */
    function iniShowWindowRouteList(dataUrl) {
        var url = WEB_ROOT + "/modules/sale/contract/ContractRouteListDetail.jsp" + dataUrl;
        var windowId = "ContractRouteListWindow" + token;
        fw.window(windowId, '销售合同流转详情', 800, 400, url, function () {

        }, null);
    }

    //分配销售合同
    function onClickDistributeContract() {
        var buttonId = "btnDistributeContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('BlankContractTable' + token, function (selecteds) {
                process.beforeClick();
                var ids = "";
                var flag = false;
                for (var i = 0; i < selecteds.length; i++) {
                    if (selecteds[i].status != 1) {
                        fw.alert("警告", "合同状态不为未签约,不允许分配");
                        return false;
                    }
                    //是否有销售员
                    if (selecteds[i].receiveUserName != "") {
                        flag = true;
                    }
                    ids += selecteds[i].contractNo + ",";
                }
                if (flag) {
                    fw.confirm("警告", "选中的数据中有合同已经分配销售员，确认重新分配", function () {
                        //去除最后一个，
                        var idStr = ids.substring(0, ids.length - 1);
                        iniShowWindow({"contractPO.contractNo": idStr});
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    })
                } else {
                    //去除最后一个，
                    var idStr = ids.substring(0, ids.length - 1);
                    iniShowWindow({"contractPO.contractNo": idStr});
                    process.afterClick();
                }
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 弹出详情
     */
    function iniShowWindow(data) {

        var url = WEB_ROOT + "/modules/sale/contract/BlankContract_Save.jsp?token=" + token;

        var windowId = "BlankContractWindow" + token;
        fw.window(windowId, '分配销售员', 300, 200, url, function () {

            fw.formLoad('formContractPO' + token, data);
            /**
             * 选中销售成员
             */
            onClickReceiveUserAdd();
            //表单提交事件
            onClickContractBlankSubmit();
        }, null);
    }

    /**
     * 选中销售成员
     */
    function onClickReceiveUserAdd() {
        $('#receiveUserName' + token).bind('click', function () {
            using(SCRIPTS_ROOT + '/system/UserClass(Discard).js', function () {
                try {
                    var userClass = new UserClass(token, "/sale/contract/Contract_getSaleUser.action");
                    userClass.windowOpenUserSelection(true, token, function (row) {
                        $("#receiveUserName" + token).val(row[0].name);
                        $("#receiveUserId" + token).val(row[0].id);
                    });
                }
                catch (e) {
                    fw.dealException(e);
                }
            });
        });
    }

    /**
     * 添加提交事件
     */
    function onClickContractBlankSubmit() {
        var buttonId = "btnBlankContractSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("通知", "确认给配给该销售员", function () {
                var formId = "formContractPO" + token;
                var url = WEB_ROOT + "/sale/contract/Contract_distributeContract.action";
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("BlankContractTable" + token);
                    fw.windowClose('BlankContractWindow' + token);
                }, function () {
                    process.afterClick();
                });
            }, function () {
                process.afterClick();
            });

        });
    }

    /**
     * 初始化查询
     */
    function initContactBlankSerrch() {

    }

    /**
     * 查询事件
     */
    function onClickContractBlankSearch() {
        var buttonId = "btnSearchBlankContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "BlankContractTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["contractVO.contractNo"] = $("#search_ContractNo" + token).val();
            params["contractVO.productionName"] = $("#search_ProductionName" + token).val();

            params["contractVO_sigTime_Start"] = fw.getFormValue('search_SigTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["contractVO_sigTime_End"] = fw.getFormValue('search_SigTime_End' + token, fw.type_form_datebox, fw.type_get_value);

            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickContractBlankSearchReset() {
        var buttonId = "btnResetBlankContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_ContractNo" + token).val('');
            $("#search_ProductionName" + token).val('');
            $('#search_SigTime_Start' + token).datebox("setValue", '');
            $('#search_SigTime_End' + token).datebox("setValue", '');
        });
    }

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    }
}