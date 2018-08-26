/**
 * 描述：签约销售合同
 * 说明:
 * Created by zhouhaihong on 2015/12/24.
 */
var SignedContractClass = function (token) {

    function initAll() {

        initSignedContactSearch();
        //查询
        onClickSignedContractSearch();
        //查询重置
        onClickSignedContractSearchReset();
        //表格
        initSignedContractTable();
    }

    /**
     * 合同列表
     */
    function initSignedContractTable() {
        var strTableId = 'SignedContractTable' + token;
        var url = WEB_ROOT + "/sale/contract/Contract_getListSignedContracts.action";

        $('#' + strTableId).datagrid({
                title: '签约销售合同',
                url: url,
                queryParams: {
                    // 此处可定义默认的查询条件
                },
                loadMsg: '数据正在加载，请稍后……',
                fitColumns: false,
                singleSelect: true, //单选
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
                        {field: 'customerName', title: '签约客户'},
                        {field: 'sigendTime', title: '签约时间'}
                    ]
                ],
                columns: [
                    [
                        {field: 'sid', title: 'sid', hidden: true},
                        {field: 'id', title: 'id', hidden: true},
                        {field: 'operatorId', title: 'operatorId', hidden: true},
                        {field: 'operateTime', title: 'operateTime', hidden: true},
                        {field: 'orgId', title: 'orgId', hidden: true},
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
                        {field: 'cancelName', title: '作废人'},
                        {field: 'cancelTime', title: '作废时间'},
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
                        }
                    ]
                ],

                onLoadSuccess: function () {
                    onCLickConfirmContract();
                    //查看流转状态
                    onClickContractRouteList();
                }
            }
        );
    }

    //查看流转状态
    function onClickContractRouteList() {

        var buttonId = "btnListContractRouteList" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SignedContractTable' + token, function (selecteds ) {

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


    /**
     * 合同状态确认
     */
    function onCLickConfirmContract() {
        var buttonId = "btnConfirmContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SignedContractTable' + token, function (selected) {
                process.beforeClick();
                var contractNo = selected.contractNo;
                iniShowWindow(contractNo);
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

        var url = WEB_ROOT + "/modules/sale/contract/ConfirmContract.jsp?token=" + token;

        var windowId = "ConfirmContractWindow" + token;
        fw.window(windowId, '销售合同状态确认', 900, 450, url, function (contractNo) {
            //销售初始化销售合同详情列表
            initDetailContract(contractNo);
            //表单提交事件
            onClickContractSubmit(contractNo);
            $("#btnConfirmContractSubmit" + token).linkbutton({text: '确认状态并移交管理员'});
        }, null, contractNo);
    }

    /**
     * 添加提交事件
     */
    function onClickContractSubmit(contractNo) {
        var buttonId = "btnConfirmContractSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("通知", "是否确认合同状态", function () {
                var url = WEB_ROOT + "/sale/contract/Contract_confirmContract.action?contractPO.contractNo=" + contractNo;
                fw.post(url, null, function (data) {
                    process.afterClick();
                    if (data == true) {
                        fw.alert("通知", "确认成功");
                        fw.datagridReload("SignedContractTable" + token);
                        fw.windowClose('ConfirmContractWindow' + token);
                    } else {
                        fw.alert("通知", "确认失败");
                        fw.datagridReload("SignedContractTable" + token);
                        fw.windowClose('ConfirmContractWindow' + token);
                    }
                }, function () {
                    process.afterClick();
                });
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 合同详情列表
     */
    function initDetailContract(contractNo) {
        var strTableId = 'ConfirmContractTable' + token;
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
                        {field: 'contractNo', title: '合同号'},
                        {field: 'contractDetailNo', title: '序号'},
                        {field: 'productionName', title: '产品名称'},
                        {field: 'customerName', title: '签约客户'},
                        {field: 'sigendTime', title: '签约时间'}
                    ]
                ],
                columns: [
                    [
                        {field: 'sid', title: 'sid', hidden: true},
                        {field: 'id', title: 'id', hidden: true},
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
                                return "遗失";
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
                toolbar: [],
                onLoadSuccess: function () {
                }
            }
        )
        ;
    }

    /**
     * 初始化查询
     */
    function initSignedContactSearch() {
    }

    /**
     * 查询事件
     */
    function onClickSignedContractSearch() {
        var buttonId = "btnSearchSignedContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "SignedContractTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["contractVO.contractNo"] = $("#search_ContractNo" + token).val();
            params["contractVO.productionName"] = $("#search_ProductionName" + token).val();
            params["contractVO.customerName"] = $("#search_CustomerName" + token).val();

            params["contractVO_sigendTime_Start"] = fw.getFormValue('search_SigendTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["contractVO_sigendTime_End"] = fw.getFormValue('search_SigendTime_End' + token, fw.type_form_datebox, fw.type_get_value);

            $('#' + strTableId).datagrid('load');
        });

    }

    /**
     * 查询重置事件
     */
    function onClickSignedContractSearchReset() {
        var buttonId = "btnResetSignedContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_ContractNo" + token).val('');
            $("#search_ProductionName" + token).val('');
            $("#search_CustomerName" + token).val('');

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