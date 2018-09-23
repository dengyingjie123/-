
/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/15/14
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 * 用来配合销售合同 2.0 版本的订单脚本文件
 *
 */
var OrderClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化查询区域
        initSearchArea();
        // 初始化表格

        initTableOrderTable();

        initSelect();
    }

    function initSearchArea() {
        //var treeID = $('#search_status'+token).combotree('tree');
        //statusMenu(treeID)
        $('#search_status' + token).combotree('loadData', [
            {'id': 0, 'text': "预约"},
            {'id': 7, 'text': "打款"},
            {'id': 25, 'text': "财务第一次核对"},
            {'id': 1, 'text': "已支付"},
            {'id': 5, 'text': "已兑付"},
            {'id': 11, 'text': "已转让"},
            {'id': 23, 'text': "第一次回访"},
            {'id': 24, 'text': "第二次回访"}
        ]);
        fw.combotreeClear('search_status'+token);


        // 初始化查询事件
        onClickOrderSearch();
        // 初始化查询重置事件
        onClickOrderSearchReset();

        onClickOrderSaleAndWaitingSearch();
    }

    function onClickOrderShowPaymentPlan(orderId, confirmorId){
        //alert(index);
        using(SCRIPTS_ROOT + '/sale/PaymentPlanClass.js', function () {
            //alert("loaded...");
            var paymentPlanClass = new PaymentPlanClass(token, null);
            paymentPlanClass.initModulePaymentPlanDetail(orderId, confirmorId);
        });
    }

    function initOrderReportWeeklySearch() {
        var dataId = '#searchDate' + token;
        $(dataId).datebox({
            editable:false
        });

        var c = $(dataId).datebox('calendar');
        c.calendar({
            firstDay: 1
        });

    }

    function onClickOrderReportWeeklySearchSubmit() {
        var buttonId = "btnSearch" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "orderReportWeeklyTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["selectedDate"] = $('#searchDate' + token).datebox('getValue');
            $('#' + strTableId).datagrid('load');

            fw.treeClear()
        });
    }

    function initTableOrderReportWeeklyTable() {

        fw.datagrid({
            id:'orderReportWeeklyTable'+token,
            url:WEB_ROOT + "/production/Order_getReportWeekly.action",
            loadMsg: '数据正在加载，请稍后……',
            rownumbers: true,
            singleSelect: true,
            pageList: [100],
            pageSize: 100,
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch (e) {
                }
            },
            frozenColumns:[
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'groupName', title: '财富中心'},
                    {field: 'name', title: '销售'}
                ]
            ],
            columns: [
                [
                    {field: 'money_open', title: '存量金额',
                        formatter: function(value,row,index){
                            return fw.formatMoney(row['money_open']);
                        }
                    },
                    {field: 'money_open_discountRate', title: '存量金额折标',
                        formatter: function(value,row,index){
                            return fw.formatMoney(row['money_open_discountRate']);
                        }
                    },
                    {field: 'money_open_add', title: '新增金额',
                        formatter: function(value,row,index){
                            return fw.formatMoney(row['money_open_add']);
                        }
                    },
                    {field: 'money_open_discountRate_add', title: '新增金额折标',
                        formatter: function(value,row,index){
                            return fw.formatMoney(row['money_open_discountRate_add']);
                        }
                    },
                    {field: 'customer_count', title: '客户数'},
                    {field: 'customer_count_add', title: '客户新增数'}
                ]
            ],
            onLoadSuccess: function () {

            }
        });
    }


    /**
     * 初始化表格
     */
    function initTableOrderTable() {

        fw.datagrid({
            id:'OrderTable'+token,
            url:WEB_ROOT + "/production/Order_list.action",
            usedHeight:250,
            frozenColumns:[
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'orderNum', title: '订单号',
                        styler: function(value,row,index){
                            if (row['statusName']=='打款' && row['financeMoneyConfirm'] != '1') {
                                // #79D3FF
                                return 'background-color:#B9E9FF;';
                            }
                            else if (row['statusName']=='打款' && row['financeMoneyConfirm'] == '1') {
                                return 'background-color:#00B22D;';
                            }
                            else if (row['statusName']=='预约') {
                                return 'background-color:#FFD24D;';
                            }
                        }
                    },
                    {field: 'customerName', title: '客户'},
                    {field: 'mobile', title: '电话'},
                    {field: 'productionName', title: '产品'}
                ]
            ],
            columns: [
                [
                    {field: 'money', title: '购买金额(元)',
                        formatter: function(value,row,index){
                            return fw.formatMoney(row['money']);
                        }
                    },
                    {field: 'bankName', title: '银行'},
                    {field: 'bankNumber', title: '账号'},
                    {field: 'bankBranchName', title: '开户行'},
                    {field: 'statusName', title: '当前状态'},
                    {field: 'customerAttribute', title: '客户属性',hidden:true},
                    {field: 'referralCode', title: '推荐码'},
                    {field: 'salesmanName', title: '负责销售'},
                    {field: 'appointmentTime', title: '预约时间'},
                    {field: 'payTime', title: '到账时间'},
                    {field: 'createTime', title: '创建时间'},
                    {field: 'paybackTime', title: '兑付计划',formatter:function(value,row,index){
                        return "<a href='#'>查看</a>";
                    }},
                    {field: 'confirmorName', title: '兑付计划确认人'},
                    {field: 'productionCompositionName', title: '产品构成'},
                    {field: 'contractNo', title: '合同编号'},
                    {field: 'financeMoneyConfirm', title: '日终扎帐确认',hidden:true},
                    {field: 'financeMoneyConfirmUserName', title: '日终扎帐确认人'},
                    {field: 'financeMoneyConfirmTime', title: '日终扎帐确认时间'},
                    {field: 'payChannelName', title: '支付渠道',hidden:true},
                    {field: 'downloadFiles', title: '下载附件',formatter:function(value,row,index){
                        var zipName = "订单附件_" + row['customerName'] + '_' + row['productionName'] + '.zip';
                        var url = "down.jsp?zipName="+zipName+"&moduleId=18833&bizId=" + row['id'];
                        return "<a href='"+url+"' target='_blank'>下载</a>";
                    }},
                    {field: 'allinpayCircle_deposit_status', title: '通联-充值状态',
                        formatter: function(value,row,index){
                            if (row['allinpayCircle_deposit_status']=='0') {
                                return '未充值';
                            }
                            else if (row['allinpayCircle_deposit_status']=='1') {
                                return '充值成功';
                            }
                            else if (row['allinpayCircle_deposit_status']=='2') {
                                return '充值已受理';
                            }
                            else if (row['allinpayCircle_deposit_status']=='3') {
                                return '充值失败';
                            }
                        }
                    },
                    {field: 'allinpayCircle_payByShare_status', title: '通联-份额支付状态',
                        formatter: function(value,row,index){
                            if (row['allinpayCircle_payByShare_status']=='0') {
                                return '未支付';
                            }
                            else if (row['allinpayCircle_payByShare_status']=='1') {
                                return '已支付';
                            }
                            else if (row['allinpayCircle_payByShare_status']=='2') {
                                return '支付受理';
                            }
                            else if (row['allinpayCircle_payByShare_status']=='3') {
                                return '支付失败';
                            }
                        }
                    },
                    {field: 'allinpayCircle_payByShare_time', title: '通联-份额支付时间'}
                ]
            ],
            onLoadSuccess: function () {
                onClickOrderSaleAndWaiting();
                onClickOrderSaleAndWaitingCancel();
                onClickOrderAppointment();
                onClickOrderAppointmentCancel();

                /**
                 * 财务一次核对
                 */
                onClickOrderFinanceConfirm01();

                onClickOrderFinanceConfirm02();
                onClickOrderTransfer();
                onClickOrderPayback();
                onClickMoneyTransfer2Gongda();
                onClickOrderFeedback1();
                onClickOrderFeedback2();
                onClickOrderDetailShow();

                onClickOrderEdit();

                onClickOrderGeneratePaymentPlan();

                onClickOrderContractSigned();
                onClickOrderSaveReferralCode();

                onClickOrderContractCancelSign();

                onClickOrderFinanceMoneyConfirm();
                onClickOrderFinanceMoneyConfirmCancel();

                onClickExportExcel();

                onClickOrder_AllinpayCircle_DepositByInstitution();
                onClickOrder_AllinpayCircle_payByShare();
            },
            onClickCell:function(index,field,value){
                var data = $('#OrderTable'+token).datagrid('getData');
                // fw.alertReturnValue(data);

                // 查看兑付计划
                if (field == 'paybackTime') {
                    var orderId = data['rows'][index]['id'];
                    var confirmorId = data['rows'][index]['confirmorId'];
                    onClickOrderShowPaymentPlan(orderId, confirmorId);
                }

            }
        });
    }


    function onClickOrderContractSigned() {
        var buttonId = "btnOrderContractSigned" + token;

        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {

                initWindowOrderContractSignedWindow(selected);

            });
        });

    }

    function onClickOrderContractSignedSubmit() {
        var buttonId = "btnOrderContractSignedSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            var orderId = fw.textGetValue("id" + token);
            var contracNo = fw.getComboTreeValue("contractNo" + token);

            fw.confirm('确认', '确认关联此订单合同？', function () {
                // 确认 是
                var url = WEB_ROOT + "/production/Order_signContract?contracNo="+contracNo+"&orderId="+orderId;
                fw.post(url, function(data){
                    if (data != 1) {
                        fw.alert('警告', '订单合同关联失败');
                        return;
                    }

                    fw.datagridReload("OrderTable"+token);
                    fw.windowClose("OrderContractSigned"+token);
                },null);

            }, function () {
                // 确认否
            });

        });
    }



    function onClickOrderContractCancelSign() {
        var buttonId = "btnOrderContractCancelSign" + token;

        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var orderNum = selected['orderNum'];
                var contractNo = selected['contractNo'];
                var orderId = selected['id'];
                fw.confirm('确认', '是否取消编号为【'+orderNum+'】的订单与编号为【'+contractNo+'】的合同签约关系？', function () {


                    var url = WEB_ROOT + "/production/Order_cancelSignContract?orderId="+orderId;
                    fw.post(url, function(data){
                        if (data != 1) {
                            fw.alert('警告', '订单合同取消关联失败');
                            return;
                        }

                        fw.datagridReload("OrderTable"+token);
                    },null);


                }, null);

            });
        });

    }


    function onClickOrder_AllinpayCircle_DepositByInstitution() {
        var buttonId = "btnAllinpayCircle_DepositByInstitution" + token;

        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var customerName = selected['customerName'];
                var orderId = selected['id'];
                var moneyString = fw.formatMoney(selected['money']);
                fw.confirm('确认', '是否确认给<br>客户【'+customerName+'】<br>充值【' + moneyString + '】', function () {
                    var url = WEB_ROOT + '/pay/AllinpayCircle_depositByInstitution?orderId=' + orderId;
                    fw.post(url, null, function(data){

                        // fw.alertReturnValue(data);
                        var message = "充值已受理";
                        if (data == "1") {
                            message = "充值申请已受理";
                        }
                        else {
                            message = "充值申请失败：" + data['message'];
                        }
                        fw.alert('提示', message);

                    }, null);

                }, null);

            });
        });

    }


    /**
     * 份额支付
     */
    function onClickOrder_AllinpayCircle_payByShare() {
        var buttonId = "btnAllinpayCircle_payByShare" + token;

        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var customerName = selected['customerName'];
                var orderId = selected['id'];
                var moneyString = fw.formatMoney(selected['money']);
                fw.confirm('确认', '是否确认使用<br>客户【'+customerName+'】<br>的份额进行支付，<br>支付金额为【' + moneyString + '】', function () {
                    var url = WEB_ROOT + '/pay/AllinpayCircle_payByShare?orderId=' + orderId;
                    fw.post(url, null, function(data){

                        if (!fw.checkIsTextEmpty(data)) {
                            fw.alert('提示', data);
                        }
                    }, null);

                }, null);

            });
        });

    }



    function initWindowOrderContractSignedWindow(data) {

        var url = WEB_ROOT + "/modules/production/Order_OrderContractSigned.jsp?token=" + token;
        var windowId = "OrderContractSigned" + token;
        fw.window(windowId, '订单信息', 550, 280, url, function () {
            fw.formLoad("formOrderContractSigned"+token ,data);

            initContractNoCombotree("", data['productionId']);

            onClickOrderContractSignedSubmit();
        }, null);
    }



    function initContractNoCombotree(contractNo, productionId) {

        var searchContractNoURL = WEB_ROOT + "/sale/contract/Contract_queryContractNo.action?";

        var combotreeId = '#contractNo' + token;

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

                    fw.post(searchContractNoURL, "contractNo="+q + "&productionId="+productionId, function(data){
                        // fw.debug(data, 44);
                        $(combotreeId).combotree('clear');
                        $(combotreeId).combotree('loadData',data);
                        $(combotreeId).combotree('setText',q);
                        searchQ = q;
                    }, null);
                }
            }
        });

        if (fw.checkIsNullObject(contractNo)) {
            return;
        }

        /**
         * 构建推荐码
         */
        var searchQ = contractNo;

        if (!fw.checkIsTextEmpty(searchQ)) {
            fw.post(searchContractNoURL, "contractNo="+searchQ + "&productionId="+productionId, function(data){
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


    function initTableOrderDetailTable(orderId) {
        var strTableId = 'OrderDetailTable' + token;
        var url = WEB_ROOT + "/production/Order_listPagerOrderDetails.action?orderId="+orderId;

        $('#' + strTableId).datagrid({
            title: '订单详情信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: [5],
            pageSize: 5,
            rownumbers: true,
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    {field: 'orderLine', title: '行号'},
                    {field: 'orderNum', title: '订单号'},
                    {field: 'money', title: '购买金额(元)',
                        formatter: function(value,row,index){
                            return fw.formatMoney(row['money']);
                        }
                    },
                    {field: 'statusName', title: '当前状态'}
                ]
            ],
            columns: [
                [
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'customerName', title: '客户'},
                    {field: 'description', title: '描述'},
                    {field: 'createTime', title: '创建时间'},
                    {field: 'operatorName', title: '操作员'}
                ]
            ],
            onLoadSuccess: function () {

                // onClickOrderEdit();
            }
        });
    }



    //支付渠道（用于下单和财务核对时选择）
    var payChannelStatus = [{"id": "1", "text": "通联支付"}, {"id": "2", "text": "富友支付"}, {"id": "3", "text": "线下支付 "}];

    function initWindowOrderWindowForm_FinanceConfirm02(data) {
        //$('#production' + token).attr("readonly", true);
        //$('#productionCompositionId' + token).combotree({readonly: true});
        //$('#orderMoney' + token).attr("readonly", true);

        // fw.debug(data, 1233);
        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);

        $('#production' + token).attr("readonly", true);
        $('#productionCompositionId' + token).combotree({readonly: true});
        $('#contractNo' + token).combotree({readonly: true});



        /**
         * 操作金额信息 - 可编辑
         */
        $('#operationMoney' + token).attr("readonly", false);


        // 编辑推荐人信息 - 可编辑
        $('#referralCode' + token).attr("readonly", false);

        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: false});



        /**
         * 初始化核对操作金额和操作时间
         *
         * Date: 2016-05-20 10:37:49
         * Author: leevits
         */
        data['operationMoney'] = data['order.money'];


        fw.formLoad('formOrder' + token, data);

        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=FinanceConfirm", function (data) {
            // fw.debug(data, "订单状态");
            // status
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);


        initRefreeCodeCombotree(data["order.referralCode"]);

        fw.setFormValue('orderConfirmUserName02' + token, loginUser.getName(), fw.type_form_text, '');
        fw.setFormValue('orderConfirmUserId02' + token, loginUser.getId(), fw.type_form_text, '');
        fw.datetimeboxSetValue('orderConfirmUserTime02'+token, fw.getTimeNow());

    }


    function initWindowOrderWindowForm_SetCustomerEnable() {
        initOrderNum();
        //获取推荐人
        getReferralCode();
        //选择客户
        onClickCheckCustomer();
        //产品查询事件
        productionMenu();

        $('#productionCompositionId' + token).combotree({readonly: false});
    }

    function initWindowOrderWindowForm_SetFormsReadOnly() {
        $('#production' + token).attr("readonly", true);
        $('#productionCompositionId' + token).combotree({readonly: true});
        $('#contractNo' + token).combotree({readonly: true});


        /**
         * 操作金额信息
         */
        $('#operationMoney' + token).attr("readonly", true);

        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});




        // 编辑推荐人信息
        $('#referralCode' + token).attr("readonly", true);
    }



    /**
     * 初始化订单转让窗口
     *
     * Date: 2016-05-17 11:02:54
     * Author: leevits
     */
    function initWindowOrderWindowForm_Transfer(data) {
        //$('#production' + token).attr("readonly", true);
        //$('#productionCompositionId' + token).combotree({readonly: true});
        //$('#orderMoney' + token).attr("readonly", true);

        // fw.debug(data, 1233);
        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);


        /**
         * 操作金额信息 - 可编辑
         */
        $('#operationMoney' + token).attr("readonly", false);

        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});


        fw.formLoad('formOrder' + token, data);

        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=Transfer", function (data) {
            // fw.debug(data, "订单状态");
            // status
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);



        initRefreeCodeCombotree(data["order.referralCode"]);

    }


    function initWindowOrderWindowForm_Feedback1(data) {
        //$('#production' + token).attr("readonly", true);
        //$('#productionCompositionId' + token).combotree({readonly: true});
        //$('#orderMoney' + token).attr("readonly", true);

        // fw.debug(data, 1233);
        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);

        data['operationMoney'] = data['order.money'];

        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});

        fw.formLoad('formOrder' + token, data);

        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=Feedback1", function (data) {
            // fw.debug(data, "订单状态");
            // status
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);



        initRefreeCodeCombotree(data["order.referralCode"]);
        fw.combotreeSetReadOnly('referralCode'+token);

    }

    function initWindowOrderWindowForm_Feedback2(data) {
        //$('#production' + token).attr("readonly", true);
        //$('#productionCompositionId' + token).combotree({readonly: true});
        //$('#orderMoney' + token).attr("readonly", true);

        // fw.debug(data, 1233);
        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);

        data['operationMoney'] = data['order.money'];

        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});

        fw.formLoad('formOrder' + token, data);

        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=Feedback2", function (data) {
            // fw.debug(data, "订单状态");
            // status
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);



        initRefreeCodeCombotree(data["order.referralCode"]);
        fw.combotreeSetReadOnly('referralCode'+token);

    }





    function initWindowOrderWindowForm_Appointment(data) {

        initWindowOrderWindowForm_SetCustomerEnable();

        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);


        /**
         * 操作金额信息
         */
        $('#operationMoney' + token).attr("readonly", false);

        fw.combotreeSetReadOnly('referralCode' + token);

        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});


        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=Appointment", function (data) {
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);

        fw.formLoad('formOrder' + token, data);


        var referralCode = loginUser.getReferralCode();
        if (!fw.checkIsTextEmpty(data["order.referralCode"])) {
            referralCode = data["order.referralCode"];
        }

        initRefreeCodeCombotree(referralCode);

    }


    /**
     * 初始化 销售订单
     *
     * 状态为：打款待确认
     *
     * Date: 2016-05-19 0:34:52
     * Author: leevits
     */
    function initWindowOrderWindowForm_SaleAndWaiting(data) {

        initWindowOrderWindowForm_SetCustomerEnable();

        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);

        fw.combotreeSetEditable('contractNo'+token);


        /**
         * 操作金额信息 - 可编辑
         */
        $('#operationMoney' + token).attr("readonly", false);


        // 编辑推荐人信息 - 可编辑
        $('#referralCode' + token).attr("readonly", false);


        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});

        fw.formLoad('formOrder' + token, data);


        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=SaleAndWaiting", function (data) {
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);


        var referralCode = loginUser.getReferralCode();
        if (!fw.checkIsTextEmpty(data["order.referralCode"])) {
            referralCode = data["order.referralCode"];
        }

        initRefreeCodeCombotree(referralCode);

    }


    function initWindowOrderWindowForm_FinanceConfirm01(data) {

        initWindowOrderWindowForm_SetCustomerEnable();

        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);

        fw.combotreeSetEditable('contractNo'+token);


        /**
         * 操作金额信息 - 可编辑
         */
        $('#operationMoney' + token).attr("readonly", false);


        // 编辑推荐人信息 - 可编辑
        $('#referralCode' + token).attr("readonly", false);

        // 操作金额
        data['operationMoney'] = data['order.money'];

        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});

        fw.formLoad('formOrder' + token, data);


        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=FinanceConfirm01", function (data) {
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);


        var referralCode = loginUser.getReferralCode();
        if (!fw.checkIsTextEmpty(data["order.referralCode"])) {
            referralCode = data["order.referralCode"];
        }

        initRefreeCodeCombotree(referralCode);

        fw.setFormValue('orderConfirmUserName01' + token, loginUser.getName(), fw.type_form_text, '');
        fw.setFormValue('orderConfirmUserId01' + token, loginUser.getId(), fw.type_form_text, '');
        fw.datetimeboxSetValue('orderConfirmUserTime01'+token, fw.getTimeNow());

    }


    function initWindowOrderWindowForm_Edit(data) {

        // fw.alertReturnValue(data);

        getReferralCode();

        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);

        fw.combotreeSetEditable('contractNo'+token);


        /**
         * 操作金额信息 - 可编辑
         */
        $('#operationMoney' + token).attr("readonly", false);


        // 编辑推荐人信息 - 可编辑
        $('#referralCode' + token).attr("readonly", false);


        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});

        data['operationMoney'] = data['order.money'];

        //fw.alertReturnValue(data);
        //fw.setFormValue('accountDisplayName'+token, data[''], fw.type_form_text, '');

        fw.formLoad('formOrder' + token, data);


        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=SaleAndWaiting", function (data) {
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);


        var referralCode = loginUser.getReferralCode();
        if (!fw.checkIsTextEmpty(data["order.referralCode"])) {
            referralCode = data["order.referralCode"];
        }

        initRefreeCodeCombotree(referralCode);

        // console.log(data['order.contractNo']);
        // console.log(fw.checkIsTextEmpty(data['order.contractNo']));
        // console.log(data['order.productionId']);
        // console.log(fw.checkIsTextEmpty(data['order.productionId']));

        // 加载对应合同编号
        if (!fw.checkIsTextEmpty(data["order.productionId"]) && fw.checkIsTextEmpty(data['order.contractNo'])) {
            // console.log("加载合同编号")
            contractsMenu(data["order.productionId"]);
        }

    }


    /**
     * 产品兑付
     *
     * Date: 2016-05-18 10:55:19
     * Author: leevits
     */
    function initWindowOrderWindowForm_Payback(data) {
        //$('#production' + token).attr("readonly", true);
        //$('#productionCompositionId' + token).combotree({readonly: true});
        //$('#orderMoney' + token).attr("readonly", true);

        // fw.debug(data, 1233);
        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);


        /**
         * 操作金额信息 - 可编辑
         */
        $('#operationMoney' + token).attr("readonly", false);


        //初始化支付渠道
        $("#payChannel" + token).combotree('loadData', payChannelStatus);
        $('#payChannel' + token).combotree({readonly: true});


        fw.formLoad('formOrder' + token, data);


        // 初始化订单状态
        var url = WEB_ROOT + "/production/Order_getJsonOfOrderStatus.action";
        fw.post(url, "name=Payback", function (data) {
            // fw.debug(data, "订单状态");
            // status
            fw.combotreeClear('status'+token);
            $('#status'+token).combotree('loadData', data);
        }, null);



        initRefreeCodeCombotree(data["order.referralCode"]);

    }

    var searchURL = WEB_ROOT + "/system/User_searchReferralCode.action?";
    function initRefreeCodeCombotree(code) {

        $('#referralCode'+token).combotree({keyHandler:{
            delay:1000,
            up: function(){},
            down: function(){},
            enter: function(){},
            query: function(q){

                if (!fw.checkIsTextEmpty(searchQ) && searchQ.indexOf(q) == 0 && searchQ.length > q.length) {
                    $('#referralCode'+token).combotree('setText',"");
                    q = "";
                    searchQ = "";
                }

                if (fw.checkIsTextEmpty(q)) {
                    return;
                }


                fw.post(searchURL, "code="+q, function(data){
                    // fw.debug(data, 44);
                    $('#referralCode'+token).combotree('clear');
                    $('#referralCode'+token).combotree('loadData',data);
                    $('#referralCode'+token).combotree('setText',q);
                    searchQ = q;
                }, null);
            }
        }});

        if (fw.checkIsNullObject(code)) {
            return;
        }

        if (code.indexOf("S") == 0) {
            code = code.substr(1);
        }

        /**
         * 构建推荐码
         */
        var searchQ = code;

        if (!fw.checkIsTextEmpty(searchQ)) {
            fw.post(searchURL, "code="+searchQ, function(data){
                // fw.debug(data, 44);
                $('#referralCode'+token).combotree('clear');
                $('#referralCode'+token).combotree('loadData',data);

                var t = $('#referralCode'+token).combotree('tree');
                var root = $(t).tree('getRoot');
                $(t).tree('select', root.target);
                var node = $(t).tree('getSelected');
                $('#referralCode'+token).combotree('setText',node.text);
                $('#referralCode'+token).combotree('setValue',node.id);
            }, null);
        }
    }

    /**
     * 初始化弹出窗口
     */
    function initWindowOrderWindow(data, actionName) {

        if (data['order.status'] != '7' && actionName == Action_FinanceConfirm01) {

            fw.alert('错误','只有【支付待确认】的订单可以进行财务第一次审核');

            return;
        }

        if (data['order.status'] != '25' && actionName == Action_FinanceConfirm02) {

            fw.alert('错误','只有【财务第一次审核】的订单可以进行财务第二次审核');

            return;
        }

        // console.log("initWindowOrderWindow");
        // console.log(data);
        // console.log(actionName);

        data["order.OperatorId"] = loginUser.getId();

        var url = WEB_ROOT + "/modules/production/Order_Save.jsp?token=" + token;
        var windowId = "OrderWindow" + token;


        fw.window(windowId, '订单信息', 800, 450, url, function () {

            initWindowOrderWindowForm_SetFormsReadOnly();

            fw.textFormatCurrency('orderMoney' + token);
            // 加载数据
            fw.formLoad('formOrder' + token, data);


            if (actionName == Action_FinanceConfirm02) {
                initWindowOrderWindowForm_FinanceConfirm02(data);
            }


            /**
             * 第一次回访
             *
             * Date: 2016-05-19 0:39:16
             * Author: leevits
             */
            else if (actionName == Action_Feedback1) {
                initWindowOrderWindowForm_Feedback1(data);
            }


            /**
             * 第二次回访
             *
             * Date: 2016-05-19 0:39:16
             * Author: leevits
             */
            else if (actionName == Action_Feedback2) {
                initWindowOrderWindowForm_Feedback2(data);
            }


            /**
             * 打款待确认
             *
             * Date: 2016-05-19 0:39:16
             * Author: leevits
             */
            else if (actionName == Action_SaleAndWaiting) {
                initWindowOrderWindowForm_SaleAndWaiting(data);
            }


            else if (actionName == Action_FinanceConfirm01) {

                initWindowOrderWindowForm_FinanceConfirm01(data);
            }

            /**
             * 编辑订单
             */
            else if (actionName == Action_Edit) {
                initWindowOrderWindowForm_Edit(data);
            }



            /**
             * 订单转让
             *
             * Date: 2016-05-17 11:02:28
             * Author: leevits
             */
            else if (actionName == Action_Transfer) {
                initWindowOrderWindowForm_Transfer(data);
            }




            /**
             * 订单兑付
             *
             * Date: 2016-05-18 11:01:48
             * Author: leevits
             */
            else if (actionName == Action_Payback) {
                initWindowOrderWindowForm_Payback(data)
            }

            //预约
            else if (actionName == Action_Appointment) {

                initWindowOrderWindowForm_Appointment(data);

            }
            // 初始化表单提交事件
            onClickOrderSubmit();

            ocClickCustomerAccount();

        }, null);
    }


    /**
     * 初始化弹出窗口
     */
    function initWindowOrderSaveReferralCodeWindow(data) {

        data["order.OperatorId"] = loginUser.getId();

        var url = WEB_ROOT + "/modules/production/Order_SaveReferralCode.jsp?token=" + token;
        var windowId = "OrderSaveReferralCodeWindow" + token;
        fw.window(windowId, '订单信息', 780, 430, url, function () {

            initWindowOrderWindowForm_SetFormsReadOnly();


            $('#referralCode' + token).attr("readonly", false);

            initWindowOrderWindowForm_ChangeReferralCode(data);


            onClickOrderSaveReferralCodeSubmit();

        }, null);
    }


    /**
     * 修改订单推荐码
     *
     * Date: 2016-05-17 11:02:54
     * Author: leevits
     */
    function initWindowOrderWindowForm_ChangeReferralCode(data) {
        // fw.debug(data, 1233);
        productionCompositionMenu(data["order.productionId"], data["order.productionCompositionId"]);

        fw.combotreeClear('contractNo' + token);
        $('#contractNo'+token).combotree('setValue', data["order.contractNo"]);


        /**
         * 操作金额信息 - 可编辑
         */
        $('#operationMoney' + token).attr("readonly", false);


        fw.formLoad('formOrder' + token, data);

        initRefreeCodeCombotree(data["order.referralCode"]);
    }

    function initWindowOrderDetailWindow(orderId) {

        var url = WEB_ROOT + "/modules/production/OrderDetail_Save.jsp?token=" + token;
        var windowId = "OrderDetailWindow" + token;
        fw.window(windowId, '订单明细信息', 780, 430, url, function () {

            initTableOrderDetailTable(orderId);

            using(SCRIPTS_ROOT+'/system/FilesClass.js', function () {
                var filesClass = new FilesClass(token);
                filesClass.initOnClickUploadButton("18833", orderId, "D209999D076045238C1C9ED327934703");
            });

            // 初始化表单提交事件
            // onClickOrderSubmit();
        }, null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////
    /**
     * 查询事件
     */
    function onClickOrderSearch() {
        var buttonId = "btnSearchOrder" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "OrderTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["OrderNum"] = $("#search_OrderNum" + token).val();
            params["CustomerName"] = $("#search_Customer" + token).val();
            params["ProductName"] = $("#search_Product" + token).val();
            params['ReferralCode'] = $('#search_ReferralCode' + token).val();
            params['salesmanName'] = $('#search_salesman' + token).val();

            params['PayTime_Start'] = fw.getFormValue('search_PayTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params['PayTime_End'] = fw.getFormValue('search_PayTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            // var status = $("#search_status"+token).combotree('getValues');
            var status = $('#search_status' + token).combotree('getValue');
            //alert(statuss);
            params["status"] = status;
            $('#' + strTableId).datagrid('load');                         //加载第一页的行

            fw.treeClear()
        });

    }


    /**
     * 日终扎帐查询
     */
    function onClickOrderSaleAndWaitingSearch() {
        var buttonId = "btnSearchOrderSaleAndWaiting" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "OrderTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["OrderNum"] = $("#search_OrderNum" + token).val();
            params["CustomerName"] = $("#search_Customer" + token).val();
            params["ProductName"] = $("#search_Product" + token).val();
            params['ReferralCode'] = $('#search_ReferralCode' + token).val();
            params['salesmanName'] = $('#search_salesman' + token).val();

            params['PayTime_Start'] = fw.getFormValue('search_PayTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params['PayTime_End'] = fw.getFormValue('search_PayTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            // var status = $("#search_status"+token).combotree('getValues');
            var status = '7';
            //alert(statuss);
            params["status"] = status;
            $('#' + strTableId).datagrid('load');                         //加载第一页的行

            fw.treeClear()
        });

    }

    /**
     * 查询重置事件
     */
    function onClickOrderSearchReset() {
        var buttonId = "btnResetOrder" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_OrderNum" + token).val('');
            $("#search_Customer" + token).val('');
            $("#search_Product" + token).val('');
            $('#search_ReferralCode' + token).val('');
            $('#search_salesman' + token).val('');
            fw.databoxClear("search_PayTime_Start"+token);
            fw.databoxClear("search_PayTime_End"+token);
            fw.combotreeClear('search_status' + token);
        });
    }


    /**
     *作废订单事件
     */
    function onClickOrderInvalid() {
        var buttonId = "btnOrderInvalid" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                process.beforeClick();
                if(selected.status =="预约"){
                    fw.alert("警告","该订单不属于可以作废订单");
                    process.afterClick();
                    return false;
                }
                fw.confirm('确认作废', '是否确认作废该订单？', function () {
                    var url = WEB_ROOT + "/production/Order2_delete.action?order.id=" + selected.id;
                    //alert(url);
                    fw.post(url, null, function (data) {

                        fw.datagridReload('OrderTable' + token);
                        process.afterClick();
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }


    // /**
    //  * 修改事件
    //  */
    // function onClickOrderEdit() {
    //     var butttonId = "btnOrderEdit" + token;
    //     alert(butttonId);
    //     fw.bindOnClick(butttonId, function (process) {
    //
    //         fw.datagridGetSelected('OrderTable' + token, function (selected) {
    //             process.beforeClick();
    //             var sid = selected.sid;
    //             var url = WEB_ROOT + "/production/Order2_load.action?order.sid=" + sid;
    //             fw.post(url, null, function (data) {
    //                 //fw.alertReturnValue(data);
    //                 data["loginName"] = selected.loginName;
    //                 data["customerName"] = selected.customerName;
    //                 data["productionName"] = selected.productionName;
    //                 initWindowOrderWindow(data, "update");
    //                 process.afterClick();
    //             }, function () {
    //                 process.afterClick();
    //             });
    //         })
    //
    //     });
    // }


    /**
     * 修改事件
     */
    function onClickOrderSaveReferralCode() {
        var butttonId = "btnOrderSaveReferralCode" + token;
        fw.bindOnClick4Any(butttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {

                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderSaveReferralCodeWindow(data, "update");

                }, function () {

                });
            })

        });
    }


    var Action_Edit = "Action_Edit";

    /**
     * 订单修改
     */
    function onClickOrderEdit() {
        var buttonId = "btnOrderEdit" + token;
        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderWindow(data, Action_Edit);
                },null);
            })

        });
    }


    function onClickOrderFinanceMoneyConfirm() {
        var buttonId = "btnFinanceMoneyConfirm" + token;
        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_financeMoneyConfirm.action?orderId=" + id;

                fw.confirm('提示','是否确认此笔订单已到帐<br>客户【'+selected.customerName+'】，到账【'+fw.formatMoney(selected.money) +'】？', function(){
                    fw.post(url, null, function (data) {
                        //fw.alertReturnValue(data);
                        if (data == "1") {
                            fw.datagridReload("OrderTable" + token);
                            fw.alert('提示', '日终扎帐已确认');
                        }
                    },null);
                }, null);
            })

        });
    }


    function onClickOrderFinanceMoneyConfirmCancel() {
        var buttonId = "btnFinanceMoneyConfirmCancel" + token;
        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_financeMoneyConfirmCancel.action?orderId=" + id;

                fw.confirm('提示','是否取消此笔订单已到帐的信息？', function(){
                    fw.post(url, null, function (data) {
                        //fw.alertReturnValue(data);
                        if (data == "1") {
                            fw.datagridReload("OrderTable" + token);
                            fw.alert('提示', '日终扎帐已取消');
                        }
                    },null);
                }, null);
            })

        });
    }


    /**
     * 财务二次复核
     */
    var Action_FinanceConfirm02 = "Action_FinanceConfirm02";
    function onClickOrderFinanceConfirm02() {
        var buttonId = "btnFinanceConfirm02" + token;
        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderWindow(data, Action_FinanceConfirm02);
                },null);
            })

        });
    }


    function onClickExportExcel() {
        var buttonId = "btnExportExcel" + token;
        fw.bindOnClick4Any(buttonId, function () {

            var parameter = {};
            parameter['order.orderNum'] = fw.getTextValue('search_OrderNum' + token);
            parameter['order.customerName'] = fw.getTextValue('search_Customer' + token);
            parameter['order.productionName'] = fw.getTextValue('search_Product' + token);
            parameter['order.salesmanName'] = fw.getTextValue('search_salesman' + token);
            parameter['search_PayTime_Start'] = fw.getFormValue('search_PayTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            parameter['search_PayTime_End'] = fw.getFormValue('search_PayTime_End'+token, fw.type_form_datebox, fw.type_get_value);

            parameter['order.status'] = $('#search_status' + token).combotree('getValue');

            // fw.alertReturnValue(parameter);

            p = fw.buildUrlParameters(parameter);

            // fw.alertReturnValue(p);

            window.open(WEB_ROOT + "/production/Order_exportExcel?" + p);
        });
    }

    /**
     * 财务一次复核
     */
    var Action_FinanceConfirm01 = "Action_FinanceConfirm01";
    function onClickOrderFinanceConfirm01() {
        var buttonId = "btnFinanceConfirm01" + token;
        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderWindow(data, Action_FinanceConfirm01);
                },null);
            })

        });
    }


    var Action_Feedback1 = "Action_Feedback1";

    /**
     * 第一次回访
     */
    function onClickOrderFeedback1() {
        var butttonId = "btnOrderFeedback1" + token;
        fw.bindOnClick4Any(butttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderWindow(data, Action_Feedback1);
                },null);
            })

        });
    }



    var Action_Feedback2 = "Action_Feedback2";

    /**
     * 第二次回访
     */
    function onClickOrderFeedback2() {
        var butttonId = "btnOrderFeedback2" + token;
        fw.bindOnClick4Any(butttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderWindow(data, Action_Feedback2);
                },null);
            })

        });
    }


    function onClickMoneyTransfer2Gongda() {
        var butttonId = "btnMoneyTransfer2Gongda" + token;
        fw.bindOnClick4Any(butttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                fw.confirm('确认信息', '是否确认执行此订单转账到公达？<br><br><div style="color: red">注意此操作无法撤销！点击操作后请耐心等待，切勿重复点击！</div><br>如有疑问，请联系系统维护人员。', function () {
                    // 确认 是
                    var orderId = selected.id;
                    var url = WEB_ROOT + "/production/Order_moneyTransfer2Gongda.action?orderId=" + orderId;
                    fw.post(url, null, function (data) {

                        if (!fw.checkIsNullObject(data)) {
                            //fw.alertReturnValue(data);
                            fw.alert('请求结果', data['message']);

                            fw.datagridReload("OrderTable" + token);
                        }

                    },null);

                },null);
            });

        });
    }




    /**
     * 订单取消
     *
     * 被财务确认的订单无法取消
     *
     * Date: 2016-05-19 19:06:33
     * Author: leevits
     */
    function onClickOrderSaleAndWaitingCancel() {
        var buttonId = "btnOrderSaleAndWaitingCancel" + token;
        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {

                fw.confirm('确认取消订单', '是否确认取消该订单？', function () {
                    var url = WEB_ROOT + "/production/Order_saleAndWaitingOrderCancel.action?order.id=" + selected.id;
                    fw.post(url, null, function (data) {
                        fw.datagridReload("OrderTable" + token);
                    }, null);

                }, null);


            });

        });
    }


    /**
     * 订单转让
     *
     * Date: 2016-05-17 11:00:55
     * Author: leevits
     */
    var Action_Transfer = "Action_Transfer";
    function onClickOrderTransfer() {
        var butttonId = "btnOrderTransfer" + token;
        fw.bindOnClick4Any(butttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderWindow(data, Action_Transfer);
                },null);
            });

        });
    }

    function onClickOrderDetailShow() {
        var butttonId = "btnOrderDetailShow" + token;
        fw.bindOnClick4Any(butttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                initWindowOrderDetailWindow(selected.id);

            });
        });
    }

    function onClickOrderGeneratePaymentPlan() {
        var buttonId = "btnOrderGeneratePaymentPlan" + token;
        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                var orderId = selected.id;

                fw.confirm('提示', '是否生成【'+selected['orderNum']+'】订单的兑付计划？', function(){
                    var url = WEB_ROOT + "/production/Order_generatePaymentPlan?orderId="+orderId;
                    fw.post(url, null, function(data){
                        fw.alert('提示', data.message);

                        fw.datagridReload("OrderTable" + token);
                    },null);
                },null);
            });
        });
    }

    var Action_Appointment = "Action_Appointment";
    function onClickOrderAppointment() {
        var butttonId = "btnOrderAppointment" + token;
        fw.bindOnClick4Any(butttonId, function () {

            initWindowOrderWindow({}, Action_Appointment);

        });
    }

    var Action_SaleAndWaiting = "Action_SaleAndWaiting";
    function onClickOrderSaleAndWaiting() {
        var buttonId = "btnOrderSaleAndWaiting" + token;
        fw.bindOnClick4Any(buttonId, function () {

            initWindowOrderWindow({}, Action_SaleAndWaiting);

        });
    }

    function onClickOrderAppointmentCancel() {
        var buttonId = "btnOrderAppointmentCancel" + token;
        fw.bindOnClick4Any(buttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {

                fw.confirm('确认取消订单', '是否确认取消预约该订单？', function () {

                    var url = WEB_ROOT + "/production/Order_appointmentOrderCancel.action?order.id=" + selected.id;
                    fw.post(url, null, function (data) {
                        fw.datagridReload("OrderTable" + token);
                    },null);
                },null);
            });

        });
    }


    /**
     * 产品兑付
     *
     * Date: 2016-05-18 10:59:58
     * Author: leevits
     */
    var Action_Payback = "Action_Payback";
    function onClickOrderPayback() {
        var butttonId = "btnOrderPayback" + token;
        fw.bindOnClick4Any(butttonId, function () {

            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderWindow(data, Action_Payback);
                },null);
            })

        });
    }

    /**
     * 数据提交事件
     */
    function onClickOrderSubmit() {
        var buttonId = "btnOrderSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            fw.confirm("通知", "是否确认该订单", function () {


                /**
                 * 转化所有金额为浮点型
                 *
                 * Date: 2016-05-19 17:30:06
                 * Author: leevits
                 */
                fw.CurrencyFormatText('operationMoney' + token);

                // 设置推荐码
                var t = $('#referralCode'+token).combotree('tree');
                if (t) {
                    var node = $(t).tree('getSelected');
                    if (node) {
                        $('#referralCode'+token).val(node.id);
                    }
                }


                var formId = "formOrder" + token;
                var appointmentURL = WEB_ROOT + "/production/Order_appointmentOrder.action";
                var saleWaitingURL = WEB_ROOT + "/production/Order_saleAndWaitingOrder.action";
                var financeConfirm01URL = WEB_ROOT + "/production/Order_financeConfirm01.action";
                var saleURL = WEB_ROOT + "/production/Order_saleOrder.action";
                var transferOrder = WEB_ROOT + "/production/Order_transferOrder.action";
                var paybackOrder = WEB_ROOT + "/production/Order_paybackOrder.action";
                var feedback1 = WEB_ROOT + "/production/Order_feedback1.action";
                var feedback2 = WEB_ROOT + "/production/Order_feedback2.action";


                var url = "";
                var orderStatus = fw.getComboTreeValue("status"+token);


                /**
                 * 预约订单
                 *
                 * Date: 2016-05-19 17:41:15
                 * Author: leevits
                 */
                if (orderStatus == '0') {

                    url = appointmentURL;
                }



                /**
                 * 支付待确认订单
                 *
                 * Date: 2016-05-19 17:41:29
                 * Author: leevits
                 */
                else if (orderStatus == '7') {

                    url = saleWaitingURL;
                }


                else if (orderStatus == '25') {

                    url = financeConfirm01URL;
                }



                /**
                 * 财务审核设置为已支付
                 *
                 * Date: 2016-05-19 17:58:58
                 * Author: leevits
                 */
                else if (orderStatus == '1') {
                    url = saleURL;
                }
                else if (orderStatus == '11' || orderStatus == '20') {
                    url = transferOrder;
                }
                else if (orderStatus == '5' || orderStatus == '8') {
                    url = paybackOrder;
                }
                else if (orderStatus == '23') {
                    url = feedback1;
                }
                else if (orderStatus == '24') {
                    url = feedback2;
                }


                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function (data) {
                    //alert('done');
                    process.afterClick();

                    // fw.alertReturnValue(data);
                    fw.datagridReload("OrderTable" + token);

                    if (orderStatus != '0') {
                        fw.alert('重要提示','您的订单以保存，请在【订单管理】模块里，【基本操作】-【订单明细】功能上传订单证明附件，否则财务无法确认。');
                    }

                    fw.windowClose('OrderWindow' + token);
                }, function () {
                    process.afterClick();
                });

            }, function () {
                process.afterClick();
            })

        });
    }


    /**
     * 数据提交事件
     */
    function onClickOrderSaveReferralCodeSubmit() {
        var buttonId = "btnOrderSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            fw.confirm("通知", "是否确认修改该订单推荐码", function () {

                // 设置推荐码
                var t = $('#referralCode'+token).combotree('tree');
                if (t) {
                    var node = $(t).tree('getSelected');
                    if (node) {
                        $('#referralCode'+token).val(node.id);
                    }
                }

                var orderId = $('#ID'+token).val();
                var referralCode = $('#referralCode'+token).val();


                var formId = "formOrder" + token;
                var url = WEB_ROOT + "/production/Order_saveReferralCode.action?orderId="+orderId+"&referralCode="+referralCode;

                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    //alert('done');
                    process.afterClick();
                    fw.datagridReload("OrderTable" + token);
                    fw.windowClose('OrderSaveReferralCodeWindow' + token);
                }, function () {
                    process.afterClick();
                });

            }, function () {
                process.afterClick();
            })

        });
    }

    //查询客户事件
    function onClickCheckCustomer() {

        $('#customerName' + token).bind('click', function () {
            // var url = WEB_ROOT + "/modules/production/Select_Customer.jsp?token=" + token;
            var url = WEB_ROOT + "/modules/customer/Customer_Select.jsp?token=" + token;
            var selectionWindowId = "CustomerSelectWindow" + token;
            fw.window(selectionWindowId, '客户列表', 800, 500, url, function () {
                //加载js
                using(SCRIPTS_ROOT + '/customer/CustomerSelectClass.js', function () {
                    var customerSelect = new CustomerSelectClass(token);
                    customerSelect.initOnClickSelected(function(customer){
                        //fw.alertReturnValue(customer);

                        fw.setFormValue('customerName'+token, customer['customerName'], fw.type_form_text, customer['customerName']);
                        fw.setFormValue('customerId'+token, customer['customerId'], fw.type_form_text, customer['customerId']);

                        fw.setFormValue('accountId'+token, '', fw.type_form_text, '');
                        fw.setFormValue('accountDisplayName'+token, '', fw.type_form_text, '');

                    })
                });
            }, null);
        })
    }

    function ocClickCustomerAccount() {

        fw.bindOnClick4Any('accountDisplayName'+token, function(){

            var customerId = fw.getFormValue('customerId'+token, fw.type_form_text, fw.type_get_value);

            if (!fw.checkIsTextEmpty(customerId)) {
                using(SCRIPTS_ROOT + "/customer/CustomerAccountClass.js", function(){
                    var customerAccountClass = new CustomerAccountClass(token);
                    customerAccountClass.initModuleWithSelect(customerId, function(customerAccount){
                        //fw.alertReturnValue(data);
                        fw.setFormValue('accountId'+token, customerAccount['id'], fw.type_form_text, '');
                        fw.setFormValue('accountDisplayName'+token, customerAccount['bank'] + ' ' + customerAccount['number'], fw.type_form_text, '');
                    });
                });
            }

        });

        // $('#account'+token).bind('click',function(){
        //     // 初始化账户信息
        //     fw.combotreeRemove('account' + token);
        //
        //     var customerId = fw.getFormValue('customerId'+token,fw.type_form_text, fw.type_get_value);
        //     alert(customerId);
        //     if (!fw.checkIsTextEmpty(customerId)) {
        //         var urlAccount = WEB_ROOT + "/customer/CustomerAccount_getCustomerAccount4Tree.action?customerId=" + customerId;
        //         // fw.debug(selectedCompositionId, "选中的构成编号");
        //         fw.combotreeLoad('account' + token, urlAccount, -2);
        //     }
        //
        //
        // });
    }

    //获取订单编号
    function initOrderNum() {
        var url = WEB_ROOT + "/production/Order_getOrderNum.action";
        $.post(url, null, function (data) {
            var json = "[" + data + "]";
            var jsonArray = eval('(' + json + ')');
            $("#orderNum" + token).val(jsonArray[0].returnValue[0].orderNum);
        })
        return null;
    }

    //获取推荐人
    function getReferralCode() {
        var url = WEB_ROOT + "/system/User_getReferralCode.action";
        fw.post(url, null, function(data){
            var referralCode = data["referralCode"];
            $("#referralCode" + token).val(referralCode);
        }, function(){
            fw.alert("警告", "获取用户推荐码失败");
        });

        return false;
    }

    //产品选择
    function productionMenu() {

        $('#production' + token).bind('click', function () {
            var outerToken = token + "outer";
            var url = WEB_ROOT + "/modules/production/Production_Main.jsp?token=" + outerToken;
            var swindowId = "ProductionWindow" + outerToken;
            fw.window(swindowId, '选择产品', 930, 560, url, function () {
                //加载js
                using(SCRIPTS_ROOT + '/production/ProductionClass.js', function () {
                    //alert("loaded...");
                    var production = new ProductionClass(outerToken, 123, "order");
                    production.initModuleWithSelect(function (data) {

                        //fw.alertReturnValue(data);

                        $("#production" + token).val(data["name"]);
                        $("#productionId" + token).val(data["id"]);
                        $("#paybackTime" + token).val(data["interestDate"]);
                        // 根据产品选择产品构成
                        productionCompositionMenu(data["id"]);
                        // 根据产品选择合同
                        contractsMenu(data["id"]);
                    });

                });
            }, null);
        })
    }

    //根据已选择的产品加载产品构成
    function productionCompositionMenu(productId, selectedCompositionId) {

        fw.combotreeClear('productionCompositionId' + token);
        var url = WEB_ROOT + "/production/Order_getProductionCompositionMenu.action?productId=" + productId;
        // fw.debug(selectedCompositionId, "选中的构成编号");
        fw.combotreeLoad('#productionCompositionId' + token, url, selectedCompositionId);
    }

    //根据合同选择的产品加载对应合同
    function contractsMenu(productId) {
        if ($('#contractNo' + token).length>0) {
            var treeId = $('#contractNo' + token).combotree('tree');
            fw.combotreeClear('contractNo' + token);
            var url = WEB_ROOT + "/production/Order_getContractNoMenu.action?productId=" + productId;
            fw.treeLoad(treeId, url, null, null, null);
        }
    }

    function statusMenu(treeID, statusData) {
        var tree = fw.getObjectFromId(treeID);

        $(tree).tree({
            data: statusData
        });
    }


    /**
     *
     */
    function initSelect() {
        if (isSelectWindow) {
            $("#btnOrderAdd" + token).hide();
            $("#btnOrderEdit" + token).hide();
            $("#btnOrderInvalid" + token).hide();
            //初始化选择项目确定按钮
            var butttonId = "btnSelect" + token;
            fw.bindOnClick(butttonId, function (process) {
                fw.datagridGetSelected('OrderTable' + token, function (selected) {
                    process.beforeClick();
                    var id = selected.id;
                    var url = WEB_ROOT + "/production/Order_load.action?order.id=" + id;
                    fw.post(url, null, function (data) {
                        //fw.alertReturnValue(data);

                        if (fw.checkIsFunction(callbackfunction)) {
                            callbackfunction(data);
                        }

                        fw.windowClose("OrderWindow" + token);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                })

            });
        }
    }


    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    // 弹出选择窗口后，回调函数
    var callbackfunction = null;
    // 是否是备其他功能弹出窗口选择订单
    var isSelectWindow = false;

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initReportWeekly:function(){
            initOrderReportWeeklySearch();
            initTableOrderReportWeeklyTable();

            onClickOrderReportWeeklySearchSubmit();
        },
        initModuleWithSelect: function (callback) {
            callbackfunction = callback;
            isSelectWindow = true;
            return initAll();
        },
        loadCustomerInfo: function (customerId, loginName, customerName, attribute, accountId, bankName, bankCode, bankNumber) {
            //加载选择客户的id，name，attribute
            var customerNameAndBank;
            if (bankName != "") {
                customerNameAndBank = customerName + "(开户行：" + bankName + ")"
            } else {
                customerNameAndBank = customerName;
            }
            $("#customerName" + token).val(customerNameAndBank);
            $('#loginName' + token).val(loginName);
            $("#attribute" + token).val(attribute);
            $("#customerId" + token).val(customerId);
            $("#accountId" + token).val(accountId);
            $('#bankCode' + token).val(bankCode);
            $('#bankCard' + token).val(bankNumber);

            $('#bankCardDisplay' + token).html("（选择的银行卡尾数为：" + bankNumber.substring(bankNumber.length - 4, bankNumber.length) + "）");

        }
    };
}
