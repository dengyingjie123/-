/**
 * 描述：我的销售合同脚本
 * 说明:
 * Created by zhouhaihong on 2015/12/24.
 */
var ContractClass = function (token) {
    function initAll() {
        initContactSearch();
        //查询
        onClickContractSearch();
        //查询重置
        onClickContractSearchReset();
        //表格
        initContractTable();
    }


    /**
     * 合同列表
     */
    function initContractTable() {
        var strTableId = 'ContractTable' + token;
        var url = WEB_ROOT + "/sale/contract/Contract_getListContracts.action";

        $('#' + strTableId).datagrid({
                title: '销售合同',
                url: url,
                queryParams: {
                    // 此处可定义默认的查询条件
                },
                loadMsg: '数据正在加载，请稍后……',
                fitColumns: false,
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
                    [
                        // 固定列，没有滚动条
                        {field: 'ck', checkbox: true},

                        {field: 'contractNo', title: '合同号'},
                        {field: 'productionName', title: '产品名称'},
                        {field: 'customerName', title: '签约客户'},
                        {field: 'sigendTime', title: '签约时间'}

                    ]
                ],
                columns: [
                    [
                        {field: 'sid', title: 'sid', hidden: true},
                        {field: 'id', title: 'id', hidden: true},
                        {field: 'orgId', title: 'orgId', hidden: true},
                        {field: 'operatorId', title: 'operatorId', hidden: true},
                        {field: 'operateTime', title: 'operateTime', hidden: true},
                        {
                            field: 'money', title: '签约金额',
                            formatter: function (value, row, index) {
                                if (row.status == 1) {
                                    return "";
                                } else {
                                    return value;
                                }
                            }
                        },
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
                        }, {
                        field: 'actionType', title: '流转状态',
                        formatter: function (value, row, index) {

                            //合同流转状态
                            return fw.ContractRouteStatus(value);

                        }
                    }

                    ]
                ],  toolbar: [
                    {
                        id: 'btnDetailContract' + token,
                        text: '查看合同详情',
                        iconCls: 'icon-edit'
                    }, {
                        id: 'btnSendContract' + token,
                        text: '移交合同岗',
                        iconCls: 'icon-edit'
                    }, {
                        id: 'btnListContractRouteList' + token,
                        text: '查看流转状态',
                        iconCls: 'icon-edit'
                    }
                ],
                onLoadSuccess: function () {
                    //查看合同详情
                    onClickDetailContract();
                    //移交管理员
                    onClickSendContract();

                    //查看流转状态
                    onClickContractRouteList();
                }
            }
        )
        ;
    }

    //查看流转状态
    function onClickContractRouteList() {

        var buttonId = "btnListContractRouteList" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ContractTable' + token, function (selecteds) {


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

    //将合同根据状态移交到管理员
    function onClickSendContract() {
        var buttonId = "btnSendContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ContractTable' + token, function (selecteds) {
                process.beforeClick();

                fw.confirm("通知", "确认移交管理员", function () {

                    var url = WEB_ROOT + "/sale/contract/Contract_setSendContracts.action?contractPO.contractNo=" + selecteds.contractNo;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        if (data == true) {
                            fw.alert("通知", "移交成功");
                            fw.datagridReload("ContractTable" + token);
                        } else {
                            fw.alert("通知", "移交失败");
                            fw.datagridReload("ContractTable" + token);
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

    //显示合同详情
    function onClickDetailContract() {
        var buttonId = "btnDetailContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ContractTable' + token, function (selecteds) {

                process.beforeClick();
                var idStr = selecteds.contractNo;
                iniShowWindow(idStr);
                process.afterClick();
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 弹出详情
     */
    function iniShowWindow(contractNo) {

        var url = WEB_ROOT + "/modules/sale/contract/ContractDetail_Main.jsp?token=" + token;

        var windowId = "ContractWindow" + token;
        fw.window(windowId, '销售合同详情', 900, 450, url, function () {
            //销售初始化销售合同详情列表
            initDetailContract(contractNo);
            //表单提交事件
            onClickContractSubmit();
        }, null, contractNo);
    }


    /**
     * 添加提交事件
     */
    function onClickContractSubmit() {
        var buttonId = "btnContractSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridReload("ContractTable" + token);
            fw.windowClose('ContractWindow' + token);
        });
    }


    /**
     * 合同详情列表
     */
    function initDetailContract(contractNo) {
        var strTableId = 'ContractDetailTable' + token;
        var url = WEB_ROOT + "/sale/contract/Contract_getListDetailContracts.action";

        $('#' + strTableId).datagrid({
                title: '销售合同详情',
                url: url,
                queryParams: {
                    // 此处可定义默认的查询条件
                    "contractPO.contractNo": contractNo
                },
                loadMsg: '数据正在加载，请稍后……',
                fitColumns: false,
                singleSelect: true, //单选
                pageList: [8],
                pageSize: 8,
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


                    ]
                ],
                columns: [
                    [
                        {field: 'sid', title: 'sid', hidden: true},
                        {field: 'id', title: 'id', hidden: true},
                        {field: 'operatorId', title: 'operatorId', hidden: true},
                        {field: 'operateTime', title: 'operateTime', hidden: true},
                        {field: 'contractNo', title: '合同号'},
                        {field: 'contractDetailNo', title: '序号'},
                        {field: 'productionName', title: '产品名称'},
                        {field: 'customerName', title: '签约客户'},
                        {field: 'sigendTime', title: '签约时间'},
                        {
                            field: 'money', title: '签约金额',
                            formatter: function (value, row, index) {
                                if (row.status == 1) {
                                    return "";
                                } else {
                                    return value;
                                }
                            }
                        }, {
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
                    }, {
                        field: 'detailStatus', title: '单本合同状态',
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "正常";
                            } else if (value == 1) {
                                return "作废";
                            } else {
                                return "";
                            }

                        }
                    },

                        {
                            field: 'comment', title: '备注'
                        }

                    ]
                ],
                toolbar: [
                    {
                        id: 'btnDetailContracts' + token,
                        text: '设置单本合同状态',
                        iconCls: 'icon-edit'
                    }
                ],
                onLoadSuccess: function () {
                    //设置单本合同状态
                    onClickDetailContracts();

                }
            }
        )
        ;
    }

    //设置合同状态
    function onClickDetailContracts() {
        var buttonId = "btnDetailContracts" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ContractDetailTable' + token, function (selecteds) {
                process.beforeClick();
                var idStr = selecteds.id;
                var detailStatus = selecteds.detailStatus;
                var contractNo = selecteds.contractNo;
                var comment = selecteds.comment;
                var data = {"id": idStr, "detailStatus": detailStatus, "comment": comment, "contractNo": contractNo};
                iniShowWindows(data);
                process.afterClick();
            }, function () {
                process.afterClick();
            });
        });
    }


    /**
     * 设置合同状态
     */

    var detailStatus = [{"id": "0", "text": "正常"}, {"id": "1", "text": "作废"}];

    function iniShowWindows(data) {

        var url = WEB_ROOT + "/modules/sale/contract/ContractDetail_Save.jsp?token=" + token;

        var windowId = "ContractDetailWindow" + token;
        fw.window(windowId, '设置销售合同状态', 300, 300, url, function () {
            //表单提交事件
            onClickContractDetailSubmit();
            //备注
            $("#comment" + token).val(data["comment"]);
            $("#ContractNo" + token).val(data["contractNo"]);
            $("#id" + token).val(data["id"]);
            //加载状态
            $("#detailStatus" + token).combotree('loadData', detailStatus);
            //选中节点
            fw.combotreeSetSelected("#detailStatus" + token, data["detailStatus"]);
        }, null);
    }

    /**
     * 设置合同状态
     */
    function onClickContractDetailSubmit() {
        var buttonId = "btnContractDetailSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            fw.confirm("通知", "确认修改合同的状态", function () {
                var formId = "formContractDetailPO" + token;
                var url = WEB_ROOT + "/sale/contract/Contract_setContractStatus.action";
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("ContractDetailTable" + token);
                    fw.windowClose('ContractDetailWindow' + token);
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
    function initContactSearch() {

        $("#search_Status" + token).combotree('loadData', ContractStatus);
        $("#search_RouteActionType" + token).combotree('loadData', fw.getContractRouteCombotreeDate());
    }

    /**
     * 查询事件
     */
    function onClickContractSearch() {
        var buttonId = "btnSearchContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ContractTable" + token;
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
    function onClickContractSearchReset() {
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