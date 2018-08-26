/**
 * 描述：调配销售合同
 * 说明:
 * Created by zhouhaihong on 2015/12/24.
 */
var SendContractClass = function (token) {
    function initAll() {
        //初始化查询
        initContactSendSerrch();
        //查询
        onClickContractSendSearch();
        //查询重置
        onClickContractSendSearchReset();
        //表格
        initContractSendTable();
    }


    /**
     * 初始审申请表格
     */
    function initContractSendTable() {
        var strTableId = 'SendContractTable' + token;
        var url = WEB_ROOT + "/sale/contract/Contract_getListSendContracts.action";

        $('#' + strTableId).datagrid({
            title: '财富中心调配销售合同',
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
                    {field: 'productionName', title: '产品名称'},
                    {field: 'contractNo', title: '合同号'},
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
                    {
                        field: 'actionType', title: '流转状态',
                        formatter: function (value, row, index) {
                            //合同流转状态
                            return fw.ContractRouteStatus(value);
                        }
                    },
                    {field: 'applicationUserName', title: '申请人'},
                    {field: 'applicationTime', title: '申请时间'},
                    {field: 'checkName', title: '审核人'},
                    {field: 'checkTime', title: '审核时间'},


                ]
            ],
            columns: [
                [
                    {field: 'sid', title: 'sid', hidden: true},
                    {field: 'id', title: 'id', hidden: true},
                    {field: 'operatorId', title: 'operatorId', hidden: true},
                    {field: 'operateTime', title: 'operateTime', hidden: true},
                    {field: 'orgId', title: 'orgId', hidden: true},

                    {field: 'receiveUserName', title: '销售人'},
                    {field: 'sigendTime', title: '签约时间'},
                    {field: 'cancelTime', title: '作废时间'},
                    {field: 'sendExpress', title: '快递公司'},
                    {field: 'sendExpressId', title: '快递编号'},

                ]
            ],
            onLoadSuccess: function () {
                //签收销售合同
                onClickReceiveContract();
                //寄送销售合同
                onClickDispatchContract();
                //移交总部修改财富中心
                onClickMoveTotalManager();
                //查看流转状态
                onClickContractRouteList();
                //移交空白管理员
                onClickSendBlankContract();
                //移交归档管理员
                // onClickSendArchiveContract();
                //修改财富中心
                // onClickUpdateDepartment();


                onClickUpdateDepartment();
            }
        });
    }

    //将合同根据状态移交到归档管理员
    function onClickSendArchiveContract() {
        var buttonId = "btnSendArchiveContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                process.beforeClick();
                var nos = "";
                for (var i = 0; i < selecteds.length; i++) {
                    if (selecteds[i].actionType == 6) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同未签收无法移交归档合同管理员，请勿选择");
                        process.afterClick();
                        return false;
                    }
                    nos += selecteds[i].contractNo + ",";
                }

                fw.confirm("通知", "确认移交归档管理员", function () {
                    //去除最后一个，
                    var contractNo = nos.substring(0, nos.length - 1);
                    var url = WEB_ROOT + "/sale/contract/Contract_setSendContractsToArchiveManager.action?contractPO.contractNo=" + contractNo;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        if (data == true) {
                            fw.alert("通知", "移交成功");
                            fw.datagridReload("SendContractTable" + token);
                        } else {
                            fw.alert("通知", "移交失败");
                            fw.datagridReload("SendContractTable" + token);
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

    //将合同根据状态移交到空白管理员
    function onClickSendBlankContract() {
        var buttonId = "btnSendBlankContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                process.beforeClick();
                var nos = "";
                for (var i = 0; i < selecteds.length; i++) {
                    if (selecteds[i].actionType == 4) {
                        fw.alert("警告", selecteds[i].contractNo + "总部未寄出该合同，请勿选择");
                        process.afterClick();
                        return false;
                    }
                    if (selecteds[i].actionType == 5) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同未签收，无法移交空白管理员，请勿选择");
                        process.afterClick();
                        return false;
                    }
                    if (selecteds[i].status != 1) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同状态不等于未签约，无法移交空白管理员");
                        process.afterClick();
                        return false;
                    }
                    nos += selecteds[i].contractNo + ",";
                }
                fw.confirm("通知", "确认移交空白管理员", function () {
                    //去除最后一个，
                    var contractNo = nos.substring(0, nos.length - 1);
                    var url = WEB_ROOT + "/sale/contract/Contract_setSendContractsToBlankManager.action?contractPO.contractNo=" + contractNo;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        if (data == true) {
                            fw.alert("通知", "移交成功");
                            fw.datagridReload("SendContractTable" + token);
                        } else {
                            fw.alert("通知", "移交失败");
                            fw.datagridReload("SendContractTable" + token);
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
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                if (selecteds.length > 1) {
                    fw.alert("警告", "请选择一条数据");
                    return false;
                }
                process.beforeClick();
                var contractNo = selecteds[0].contractNo;
                var status = selecteds[0].status;
                var orgId = selecteds[0].orgId;
                var contractId = selecteds[0].id;

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

    //签收销售合同
    function onClickReceiveContract() {
        var buttonId = "btnReceiveContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                process.beforeClick();
                var ids = "";
                for (var i = 0; i < selecteds.length; i++) {
                    if (selecteds[i].actionType == 20) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同已移交总部管理员，无法签收，请勿选择");
                        process.afterClick();
                        return false;
                    }
                    if (selecteds[i].actionType == 6) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同已经签收，请勿选择");
                        process.afterClick();
                        return false;
                    }
                    if (selecteds[i].actionType != 5) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同尚未寄送，请勿选择");
                        process.afterClick();
                        return false;
                    }
                    ids += selecteds[i].contractNo + ",";
                }

                //去除最后一个，
                var idStr = ids.substring(0, ids.length - 1);

                iniShowWindow("receive", {"contractPO.contractNo": idStr});
                process.afterClick();

            }, function () {
                process.afterClick();
            });
        });
    }

    //移交总部管理员
    function onClickMoveTotalManager() {
        var buttonId = "btnMoveTotalMangerContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                process.beforeClick();
                var ids = "";
                for (var i = 0; i < selecteds.length; i++) {
                    //签约、异常、的合同否无法移交到总部管理
                    if (selecteds[i].status != 1) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同状态无法更改财富中心，请勿选择");
                        process.afterClick();
                        return false;
                    }
                    ids += selecteds[i].contractNo + ",";
                }
                fw.confirm("通知", "确认移交总部管理员", function () {
                    //去除最后一个，
                    var contractNo = ids.substring(0, ids.length - 1);
                    var url = WEB_ROOT + "/sale/contract/Contract_moveTotalManager.action?contractPO.contractNo=" + contractNo;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        if (data == true) {
                            fw.alert("通知", "移交成功");
                            fw.datagridReload("SendContractTable" + token);
                        } else {
                            fw.alert("通知", "移交失败");
                            fw.datagridReload("SendContractTable" + token);
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

    //修改财富中心
    function onClickUpdateDepartment() {
        var buttonId = "btnUpdateDepartment" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                process.beforeClick();
                var ids = "";
                for (var i = 0; i < selecteds.length; i++) {
                    //if (selecteds[i].actionType != 20) {
                    //    fw.alert("警告", selecteds[i].contractNo + "该合同不需要修改财富中心，请勿选择");
                    //    process.afterClick();
                    //    return false;
                    //}
                    ids += selecteds[i].contractNo + ",";
                }
                //去除最后一个，
                var idStr = ids.substring(0, ids.length - 1);
                iniShowWindow("updateDepartment", {"contractPO.contractNo": idStr});
                process.afterClick();
            }, function () {
                process.afterClick();
            });
        });
    }

    //寄送销售合同
    function onClickDispatchContract() {
        var buttonId = "btnDispatchContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                process.beforeClick();
                var ids = "";
                for (var i = 0; i < selecteds.length; i++) {
                    if (selecteds[i].actionType == 20) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同已移交总部管理员，无法寄送，请勿选择");
                        process.afterClick();
                        return false;
                    }
                    //状态等待调配
                    if (selecteds[i].actionType == 5) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同已经寄出，请先签收");
                        process.afterClick();
                        return false;
                    }
                    ids += selecteds[i].contractNo + ",";
                }
                //去除最后一个，
                var idStr = ids.substring(0, ids.length - 1);
                iniShowWindow("send", {"contractPO.contractNo": idStr});
                process.afterClick();
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 弹出详情
     */
    function iniShowWindow(status, data) {

        var url = WEB_ROOT + "/modules/sale/contract/SendContract_Save.jsp?token=" + token;

        var windowId = "ContractRouteListWindow" + token;
        var height = 270;
        if (status == "send") {
            height = 300;
        }
        fw.window(windowId, '调配销售合同', 550, height, url, function () {

            fw.formLoad('formContractRouteListPO' + token, data);
            $("#contractNO" + token).attr("readonly", "readonly");
            //寄送
            if (status == "send") {
                //表单提交事件
                onClickContractSendSubmit();
                $("#btnContractRouteListSubmit" + token).linkbutton({text: '寄送'});
                $("#receive" + token).remove();
                $("#updateDepartment" + token).remove();
                $("#updateRouteListState" + token).remove();
            }//签收
            else if (status == "receive") {
                onClickContractReceiveSubmit();
                $("#btnContractRouteListSubmit" + token).linkbutton({text: '签收'});
                $("#send" + token).remove();
                $("#updateDepartment" + token).remove();
                $("#updateRouteListState" + token).remove();
            } else if (status == "updateDepartment") {
                //更改财富中心
                onClickContractUpdateDepartmentSubmit();

                var url = WEB_ROOT + "/system/Department_list.action";
                //部门
                fw.combotreeLoad("#departmentId" + token, url, "-2");
                $("#btnContractRouteListSubmit" + token).linkbutton({text: '更改财富中心'});
                $("#receive" + token).remove();
                $("#send" + token).remove();
                $("#updateRouteListState" + token).remove();
            }
        }, null);
    }

    /**
     *q寄送事件
     */
    function onClickContractSendSubmit() {
        var buttonId = "btnContractRouteListSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("通知", "确认寄送", function () {
                var formId = "formContractRouteListPO" + token;
                var url = WEB_ROOT + "/sale/contract/ContractRoute_dispatchContracts.action";
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("SendContractTable" + token);
                    fw.windowClose('ContractRouteListWindow' + token);
                }, function () {
                    process.afterClick();
                });
            })
        });
    }

    /**
     * 更改财富中心
     */
    function onClickContractUpdateDepartmentSubmit() {
        var buttonId = "btnContractRouteListSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("通知", "确认更改财富中心", function () {
                $("#departmentName" + token).val(fw.getFormValue("departmentId" + token, fw.type_form_combotree, fw.type_get_text));
                var url = WEB_ROOT + "/sale/contract/ContractRoute_updateContractsDepartment.action";
                var formId = "formContractRouteListPO" + token;
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("SendContractTable" + token);
                    fw.windowClose('ContractRouteListWindow' + token);
                }, function () {
                    process.afterClick();
                });
            }, function () {
                process.afterClick();

            });
        });
    }

    /**
     * 签收事件
     */
    function onClickContractReceiveSubmit() {
        var buttonId = "btnContractRouteListSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("通知", "确认签收", function () {
                var url = WEB_ROOT + "/sale/contract/ContractRoute_receiveContracts.action";
                var formId = "formContractRouteListPO" + token;
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("SendContractTable" + token);
                    fw.windowClose('ContractRouteListWindow' + token);
                }, function () {
                    process.afterClick();
                });
            }, function () {
                process.afterClick();

            });
        });
    }

    //合同状态
    var ContractStatus = [{"id": "0", "text": "签约"}, {"id": "1", "text": "未签约"}, {"id": "2", "text": "合同异常 "}];


    /**
     * 初始化查询
     */
    function initContactSendSerrch() {

        $("#search_Status" + token).combotree('loadData', ContractStatus);
        $("#search_RouteActionType" + token).combotree('loadData', fw.getContractRouteCombotreeDate());
    }

    /**
     * 查询事件
     */
    function onClickContractSendSearch() {
        var buttonId = "btnSearchContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "SendContractTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["contractVO.contractNo"] = $("#search_ContractNo" + token).val();
            params["contractVO.productionName"] = $("#search_ProductionName" + token).val();
            params["contractVO.customerName"] = $("#search_CustomerName" + token).val();

            params["contractVO.actionType"] = fw.getFormValue("search_RouteActionType" + token, fw.type_form_combotree, fw.type_get_value);
            params["contractVO.status"] = fw.getFormValue("search_Status" + token, fw.type_form_combotree, fw.type_get_value);

            params["contractVO_sigendTime_Start"] = fw.getFormValue('search_SigendTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["contractVO_sigendTime_End"] = fw.getFormValue('search_SigendTime_End' + token, fw.type_form_datebox, fw.type_get_value);

            $('#' + strTableId).datagrid('load');
        });

    }

    /**
     * 查询重置事件
     */
    function onClickContractSendSearchReset() {
        var buttonId = "btnResetContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_ContractNo" + token).val('');
            $("#search_ProductionName" + token).val('');
            $("#search_CustomerName" + token).val('');
            fw.combotreeClear('search_RouteActionType' + token);
            fw.combotreeClear('search_Status' + token);
            $('#search_SigendTime_Start' + token).datebox("setValue", '');
            $('#search_SigendTime_End' + token).datebox("setValue", '');
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