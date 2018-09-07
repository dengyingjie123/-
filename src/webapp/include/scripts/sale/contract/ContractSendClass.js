/**
 * 描述：合同调配
 * 说明:
 */
var ContractSendClass = function (token) {
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
        var url = WEB_ROOT + "/sale/contract/Contract_listContractVOs.action";

        $('#' + strTableId).datagrid({
            title: '调配销售合同',
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
                    {field: 'orgName', title: '财富中心'},
                    {field: 'productionName', title: '产品名称'},
                    {field: 'contractNo', title: '合同号'},
                    {field: 'status', title: '合同状态',hidden:true},
                    {field: 'statusName', title: '合同状态'}
                ]
            ],
            columns: [
                [
                    {field: 'sid', title: 'sid', hidden: true},
                    {field: 'id', title: 'id', hidden: true},
                    {field: 'operatorId', title: 'operatorId', hidden: true},
                    {field: 'operateTime', title: 'operateTime', hidden: true},
                    {field: 'orgId', title: 'orgId', hidden: true},
                    {field: 'receiveUserName', title: '持有人'},
                    {field: 'signedStatus', title: '是否签收', hidden: true},
                    {field: 'signedStatusName', title: '是否签收'},
                    {field: 'salesmanName', title: '理财经理'},
                    {field: 'customerName', title: '客户'},
                    {field: 'money', title: '签约金额',formatter: function (value, row, index) {
                        return fw.formatMoney(value);
                    }},
                    {field: 'payTime', title: '签约时间'},
                    {field: 'cancelTime', title: '作废时间'},
                    {field: 'applicationUserName', title: '申请人', hidden: true},
                    {field: 'applicationTime', title: '申请时间', hidden: true},
                    {field: 'checkName', title: '审核人', hidden: true},
                    {field: 'checkTime', title: '审核时间', hidden: true}
                ]
            ],
            onLoadSuccess: function () {
                //签收销售合同
                onClickReceiveContract();
                //寄送销售合同
                //onClickDispatchContract();
                //修改财富中心
                onClickUpdateDepartment();
                //查看流转状态
                onClickContractRouteList();

                // 设置合同状态
                onClickSetContractStatus();
                ////移交归档管理员
                //onClickSendArchiveContract();

                //移交总部合同管理
                // onClickMoveTotalManager();

                //移交空白管理员
                //onClickSendBlankContract();


                onClickChangeContractNo();

                //// 修改合同流转状态
                //onClickChangeContractRouteStatus();

                onClickChangeProduction();


                onClickSendContract();
            }
        });
    }

    function onClickSetContractStatus() {
        var buttonId = "btnSetContractStatus" + token;
        fw.bindOnClick(buttonId, function (process) {

            fw.datagridGetSelected('SendContractTable'+token,function(selected){

                var url = WEB_ROOT + "/modules/sale/contract/Contract_SetStatus.jsp?token=" + token;
                var windowId = "ContractSetStatusWindow" + token;

                fw.window(windowId, '设置合同状态', 400, 250, url, function () {

                    // 初始化表单提交事件
                    fw.getComboTreeFromKV('contractStatus'+token, 'Contract_Status','Orders', '-1');

                    fw.setFormValue('contractNO'+token, selected['contractNo'], fw.type_form_text, '');

                    onClickSetContractStatusSubmit();
                }, null);
            });
        });
    }

    function onClickSetContractStatusSubmit() {
        var buttonId = "btnSetContractStatusSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            var status = fw.getComboTreeValue('contractStatus'+token);

            var comment = $('#comment'+token).val();
            if (fw.checkIsTextEmpty(comment)) {
                fw.alert('提示', '请输入备注');
                return;
            }

            // 申请作废连接
            var url = "";
            var parameters = $('#formContractStatus'+token).serialize();
            var confirmMessage = "";

            if (status == '3') {
                url = WEB_ROOT + "/sale/contract/Contract_setContractStatusCancelRequest";
                confirmMessage = "是否申请合同作废？";
            }
            else if (status == '4') {
                url = WEB_ROOT + "/sale/contract/Contract_setContractStatusCancelConfirmed";
                confirmMessage = "是否同意合同作废？";
            }
            else {
                fw.alert('提示', '操作失败，合同状态选项无效');
                return;
            }

            fw.confirm('提示',confirmMessage, function(){
                fw.post(url, parameters, function(data){

                    if (data == "1") {
                        fw.datagridReload('SendContractTable'+token);
                        fw.windowClose('ContractSetStatusWindow'+token);
                    }


                },null);
            },null);


        });
    }


    // 修改合同流转状态
    //function onClickChangeContractRouteStatus() {
    //    // 获取按钮元素 ID
    //    var buttonId = "btnChangeContractRouteStatus" + token;
    //
    //    fw.bindOnClick(buttonId, function (process) {
    //        fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
    //
    //            process.beforeClick();
    //
    //            var ids = "";
    //            for (var i = 0; i < selecteds.length; i++) {
    //                ids += selecteds[i].contractNo + ",";
    //            }
    //            //去除最后一个，
    //            var idStr = ids.substring(0, ids.length - 1);
    //            iniShowWindow("updateRouteListStatus", {"contractPO.contractNo": idStr});
    //
    //            process.afterClick();
    //        }, function () {
    //            process.afterClick();
    //        });
    //    });
    //
    //
    //}

    ////将合同根据状态移交到归档管理员
    //function onClickSendArchiveContract() {
    //    var buttonId = "btnSendArchiveContract" + token;
    //    fw.bindOnClick(buttonId, function (process) {
    //        fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
    //            process.beforeClick();
    //            var nos = "";
    //            for (var i = 0; i < selecteds.length; i++) {
    //                if (selecteds[i].actionType == 5) {
    //                    fw.alert("警告", selecteds[i].contractNo + "该合同未签收无法移交归档合同管理员，请勿选择");
    //                    process.afterClick();
    //                    return false;
    //                }
    //                nos += selecteds[i].contractNo + ",";
    //            }
    //
    //            fw.confirm("通知", "确认移交归档管理员", function () {
    //                //去除最后一个，
    //                var contractNo = nos.substring(0, nos.length - 1);
    //                var url = WEB_ROOT + "/sale/contract/Contract_setSendContractsToArchiveManager.action?contractPO.contractNo=" + contractNo;
    //                fw.post(url, null, function (data) {
    //                    process.afterClick();
    //                    if (data == true) {
    //                        fw.alert("通知", "移交成功");
    //                        fw.datagridReload("SendContractTable" + token);
    //                    } else {
    //                        fw.alert("通知", "移交失败");
    //                        fw.datagridReload("SendContractTable" + token);
    //                    }
    //                }, function () {
    //                    process.afterClick();
    //                });
    //            }, function () {
    //                process.afterClick();
    //            });
    //        }, function () {
    //            process.afterClick();
    //        });
    //    });
    //}

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

                //fw.alertReturnValue(selecteds);

                var contractNOs = "";
                $(selecteds).each(function(){
                    contractNOs += this['contractNo']+",";
                });

                //alert(contractNOs);
                contractNOs = fw.removeLastLetters(contractNOs, ",");

                fw.confirm('确认', '是否确认签收合同【'+contractNOs+'】?', function(){
                    var url = WEB_ROOT + "/sale/contract/Contract_signedContract.action?contractNOs=" + contractNOs+"&singedStatus=1";
                    fw.post(url, null, function(){
                        fw.datagridReload('SendContractTable'+token);
                    },null);
                },null);



                process.afterClick();

            }, function () {
                process.afterClick();
            });
        });
    }

    function onClickSendContract() {
        var buttonId = "btnContractSend" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selected) {
                process.beforeClick();

                var isSigned = true;
                $(selected).each(function(){

                    //alert(this['signedStatus']);

                    if (this['signedStatus'] != "1" && this['receiveUserId'] != "") {
                        isSigned = false;
                    }
                });

                if (!isSigned) {
                    fw.alert('警告', '存在未签收合同，请检查');
                    process.afterClick();
                    return;
                }

                using(SCRIPTS_ROOT + '/sale/SalesmanGroupClass.js', function () {
                    var salesmanGroupClass = new SalesmanGroupClass();
                    salesmanGroupClass.initModuleWithSelectWindow(function(data){
                        // alert('name' + data.userName);

                        //fw.alertReturnValue(data);

                        fw.confirm('提示', "是否确认调配合同给<br>【 "+data['groupName']+" "+data['userName']+" 】",function(){
                            var url = WEB_ROOT + '/sale/contract/Contract_sendContract';
                            //fw.alertReturnValue(selected);
                            var contractNOs = "";
                            $(selected).each(function(){
                                contractNOs += this["contractNo"] + ",";
                            });

                            contractNOs = fw.removeLastLetters(contractNOs, ",");

                            var parameters = 'contractNOs='+contractNOs+'&receiveUserId='+data["userId"]+"&groupId="+data['groupId'];

                            fw.post(url, parameters, function(data){
                                fw.datagridReload('SendContractTable'+token);
                            }, null);
                        },null);


                    })
                });

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
                    if (selecteds[i].actionType == 5) {
                        fw.alert("警告", selecteds[i].contractNo + "该合同未签收，无法修改财富中心，请勿选择");
                        process.afterClick();
                        return false;
                    }
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

    ////寄送销售合同
    //function onClickDispatchContract() {
    //    var buttonId = "btnDispatchContract" + token;
    //    fw.bindOnClick(buttonId, function (process) {
    //        fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
    //            process.beforeClick();
    //            var flag = false;
    //            for (var i = 0; i < selecteds.length; i++) {
    //                for (var j = 0; j < selecteds.length; j++) {
    //                    if (selecteds[i].departmentName !== selecteds[j].departmentName) {
    //                        flag = true;
    //                        break;
    //                    }
    //                }
    //            }
    //            if (flag) {
    //                fw.alert("警告", "请选择统一的财富中心");
    //                process.afterClick();
    //                return false;
    //            }
    //            var ids = "";
    //            for (var i = 0; i < selecteds.length; i++) {
    //
    //                //状态等待调配
    //                if (selecteds[i].actionType == 5) {
    //                    fw.alert("警告", selecteds[i].contractNo + "该合同已经寄出，请先签收");
    //                    process.afterClick();
    //                    return false;
    //                }
    //                ids += selecteds[i].contractNo + ",";
    //            }
    //            //去除最后一个，
    //            var idStr = ids.substring(0, ids.length - 1);
    //            iniShowWindow("send", {"contractPO.contractNo": idStr});
    //            process.afterClick();
    //        }, function () {
    //            process.afterClick();
    //        });
    //    });
    //}

    function onClickChangeProduction() {
        var buttonId = "btnChangeProduction" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                process.beforeClick();

                if (selecteds.length > 1) {
                    fw.alert('警告', '请选择一条记录');
                    process.afterClick();
                    return;
                }


                initWindowChangeProductionWindow(selecteds[0]);

                process.afterClick();
            }, function () {
                process.afterClick();
            });
        });
    }


    function initWindowChangeProductionWindow(contract) {

        var url = WEB_ROOT + "/modules/sale/contract/TotalSendContract_ChangeProduction.jsp?token=" + token;
        var windowId = "ChangeProductionWindow" + token;
        fw.window(windowId, '合同产品信息', 550, 300, url, function () {

            fw.textSetValue('contractNo'+token, contract['contractNo']);
            fw.textSetValue('productionName_old'+token, contract['productionName']);
            fw.textSetValue('comment'+token, contract['comment']);
            fw.textSetValue('id'+token, contract['id']);
            fw.textSetValue('operatorName'+token, loginUser.getName());
            fw.datetimeboxSetValue('operateTime'+token, fw.getTimeNow());
            fw.datetimeboxSetReadOnly('operateTime'+token);

            initChangeProductionCombotree();

            onClickChangeProductionSubmit();
        }, null);
    }


    function initChangeProductionCombotree(searchCondition) {

        var searchProductionURL = WEB_ROOT + "/production/Production_queryProductions4Combotree.action?";

        var combotreeId = '#productionId' + token;

        $(combotreeId).combotree({
            delay:1000,
            keyHandler:{
                up: function(){},
                down: function(){},
                enter: function(){},
                query: function(q){

                    if (!fw.checkIsTextEmpty(searchQ) && searchQ.indexOf(q) == 0 && searchQ.length > q.length) {
                        $(combotreeId).combotree('setText',"");
                        q = "";
                        searchQ = "";
                    }

                    if (fw.checkIsTextEmpty(q)) {
                        return;
                    }

                    var t = $(combotreeId).combotree('tree');
                    var root = $(t).tree('getRoot');

                    fw.post(searchProductionURL, "productId="+q + "&productionName="+q, function(data){
                        // fw.debug(data, 44);
                        $(combotreeId).combotree('clear');
                        $(combotreeId).combotree('loadData',data);
                        $(combotreeId).combotree('setText',q);
                        searchQ = q;
                    }, null);
                }
            }
        });

        if (fw.checkIsNullObject(searchCondition)) {
            return;
        }

        /**
         * 构建推荐码
         */
        var searchQ = searchCondition;

        if (!fw.checkIsTextEmpty(searchQ)) {
            fw.post(searchProductionURL, "productId="+searchQ + "&productionName="+searchQ, function(data){
                // fw.debug(data, 44);
                $(combotreeId).combotree('clear');
                $(combotreeId).combotree('loadData',data);

                var t = $(combotreeId).combotree('tree');
                var root = $(t).tree('getRoot');
                $(t).tree('select', root.target);
                var node = $(t).tree('getSelected');
                $(combotreeId).combotree('setText',node.text);
            }, null);
        }
    }


    function onClickChangeProductionSubmit() {
        var buttonId = "btnChangeProductionSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            var contractNo = fw.textGetValue('contractNo'+token);
            var productionId = fw.getComboTreeValue("productionId" + token);
            var productionName = fw.getComboTreeText("productionId" + token);

            var url = WEB_ROOT + "/sale/contract/Contract_changeProduction?productionId="+productionId+"&contractNo="+contractNo;

            fw.confirm('确认修改', '是否确认将合同产品更改为【'+productionName+'】？', function () {
                // 确认 是
                process.beforeClick();

                fw.post(url, null, function (data) {

                    fw.datagridReload("SendContractTable" + token);
                    fw.windowClose('ChangeProductionWindow' + token);

                    process.afterClick();
                }, function () {
                    process.afterClick();
                });


            }, function () {
                // 确认否
                process.afterClick();
            });
        });
    }


    function onClickChangeContractNo() {
        var buttonId = "btnChangeContractNo" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SendContractTable' + token, function (selecteds) {
                process.beforeClick();

                if (selecteds.length > 1) {
                    fw.alert('警告', '请选择一条记录');
                    process.afterClick();
                    return;
                }


                initWindowChangeContractNoWindow(selecteds[0]);

                process.afterClick();
            }, function () {
                process.afterClick();
            });
        });
    }

    function initWindowChangeContractNoWindow(contract) {

        var url = WEB_ROOT + "/modules/sale/contract/TotalSendContract_ChangeContractNo.jsp?token=" + token;
        var windowId = "ChangeContractNoWindow" + token;
        fw.window(windowId, '合同编号信息', 550, 300, url, function () {

            fw.textSetValue('contractNo_old'+token, contract['contractNo']);
            fw.textSetValue('comment'+token, contract['comment']);
            fw.textSetValue('id'+token, contract['id']);
            fw.textSetValue('operatorName'+token, loginUser.getName());
            fw.datetimeboxSetValue('operateTime'+token, fw.getTimeNow());
            fw.datetimeboxSetReadOnly('operateTime'+token);

            onClickChangeContractNoSubmit();
        }, null);
    }

    function onClickChangeContractNoSubmit() {
        var butttonId = "btnChangeContractNoSubmit" + token;
        fw.bindOnClick(butttonId, function (process) {

            var id = fw.textGetValue('id'+token);
            var contractNo = fw.textGetValue('contractNo'+token);

            var url = WEB_ROOT + "/sale/contract/Contract_changeContractNo?id="+id+"&contractNo="+contractNo;

            fw.confirm('确认修改', '是否确认将合同编号更改为【'+contractNo+'】？', function () {
                // 确认 是
                process.beforeClick();

                fw.post(url, null, function (data) {

                    fw.datagridReload("SendContractTable" + token);
                    fw.windowClose('ChangeContractNoWindow' + token);

                    process.afterClick();
                }, function () {
                    process.afterClick();
                });


            }, function () {
                // 确认否
                process.afterClick();
            });
        });
    }

    ///**
    // * 弹出详情
    // */
    //function iniShowWindow(status, data) {
    //
    //    var url = WEB_ROOT + "/modules/sale/contract/SendContract_Save.jsp?token=" + token;
    //
    //    var windowId = "ContractRouteListWindow" + token;
    //    var height = 270;
    //    if (status == "send") {
    //        height = 300;
    //    }
    //    fw.window(windowId, '调配销售合同', 550, height, url, function () {
    //
    //        fw.formLoad('formContractRouteListPO' + token, data);
    //        $("#contractNO" + token).attr("readonly", "readonly");
    //        //寄送
    //        if (status == "send") {
    //            //表单提交事件
    //            onClickContractSendSubmit();
    //            $("#btnContractRouteListSubmit" + token).linkbutton({text: '寄送'});
    //            $("#receive" + token).remove();
    //            $("#updateDepartment" + token).remove();
    //            $("#updateRouteListState" + token).remove();
    //        }
    //        //签收
    //        else if (status == "receive") {
    //            onClickContractReceiveSubmit();
    //            $("#btnContractRouteListSubmit" + token).linkbutton({text: '签收'});
    //            $("#send" + token).remove();
    //            $("#updateDepartment" + token).remove();
    //            $("#updateRouteListState" + token).remove();
    //        }
    //        //更改财富中心
    //        else if (status == "updateDepartment") {
    //            onClickContractUpdateDepartmentSubmit();
    //            var url = WEB_ROOT + "/system/Department_list.action";
    //            //部门
    //            fw.combotreeLoad("#departmentId" + token, url, "-2");
    //            $("#btnContractRouteListSubmit" + token).linkbutton({text: '更改财富中心'});
    //            $("#receive" + token).remove();
    //            $("#send" + token).remove();
    //            $("#updateRouteListState" + token).remove();
    //        }
    //        // 修改合同流转状态
    //        else if(status == "updateRouteListStatus") {
    //            onClickContractUpdateRouteListSubmit(data);
    //            var url = WEB_ROOT + "/sale/contract/Contract_getRouteListStatusTree.action";
    //            fw.combotreeLoad("#routeListStatus" + token, url, "-2");
    //            $("#btnContractRouteListSubmit" + token).linkbutton({text: '修改流转状态'});
    //            $("#receive" + token).remove();
    //            $("#send" + token).remove();
    //            $("#updateDepartment" + token).remove();
    //        }
    //    }, null);
    //}

    /**
     * 更改合同流转状态
     */
    function onClickContractUpdateRouteListSubmit(data) {
        var buttonId = "btnContractRouteListSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("通知", "<font color='red'><strong>谨慎操作</strong></font> 确认更改合同流转状态吗？", function () {
                process.beforeClick();
                // 获取状态
                var status = fw.getFormValue("routeListStatus" + token, fw.type_form_combotree, fw.type_get_value);
                var contractIds = data["contractPO.contractNo"];
                // 请求更改
                var url = WEB_ROOT + "/sale/contract/ContractRoute_updateRouteListStatus.action?status=" + status + "&contractPO.contractNo=" + contractIds;
                fw.post(url, {}, function(){
                    process.afterClick();
                    fw.datagridReload("SendContractTable" + token);
                    fw.windowClose('ContractRouteListWindow' + token);
                }, function(){
                    fw.alert("错误", "请求不成功，请稍后再试");
                    process.afterClick();
                });

            }, function () {
                process.afterClick();
            });
        });
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
            params["contractVO.receiveUserName"] = $("#search_RecieveUserName" + token).val();

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
            $("#search_RecieveUserName" + token).val('');
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