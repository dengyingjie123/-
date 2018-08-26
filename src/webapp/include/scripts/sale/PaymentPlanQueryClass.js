/**
 * Created by 张舜清 on 2015/9/15.
 */
var PaymentPlanQueryClass = function (token) {
    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化兑付状态
        initFormPaymentPlanSearch();
        // 初始化兑付类型
        interestTypeTree("search_Type", '-2');

        // 初始化查询事件
        onClickPaymentPlanSearch();
        // 初始化查询重置事件
        onClickPaymentPlanSearchReset();
        // 初始化表格
        initTablePaymentPlanTable();
    }

    // 初始化兑付状态
    function initFormPaymentPlanSearch() {
        fw.getComboTreeFromKV('search_status' + token, 'Sale_PaymentPlan_Status', 'k', '-2');
    }

    // 初始化兑付类型
    function interestTypeTree(typeId, selectIndexId) {
        if (typeId == null) {
            typeId == 'search_Type';
        }
        var URL = WEB_ROOT + "/production/Production_interestTypeTree.action";
        //付息类型
        fw.combotreeLoad(typeId + token, URL, selectIndexId);
    }

    /**
     * 初始化表格
     */
    function initTablePaymentPlanTable() {
        var strTableId = 'PaymentPlanQueryTable' + token;//表格ID

        //访问的URLAction
        var url = WEB_ROOT + "/sale/PaymentPlan_list.action";

        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '兑付综合查询',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,//行是否适应屏幕
            singleSelect: true,//是否只能选择一行
            pageList: [10, 20, 30],//设置每页显示多少条
            pageSize: 10,//初始化每页显示多少
            rownumbers: true,//是否显示行号
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);// 解析从数据库返回的数据
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,//是否分頁
            frozenColumns: [
                [  // 固定列，没有滚动条
                    { field: 'ck', checkbox: true},
                    { field: 'loginName', title: '用户名'},
                    { field: 'customerName', title: '客户姓名'},
                    { field: 'productName', title: '产品名称'}
                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    { field: 'sid', title: '序号', hidden: true },
                    { field: 'id', title: '编号', hidden: true},
                    { field: 'customerId', title: '客户ID', hidden: true},
                    { field: 'type', title: '兑付类型' },
                    { field: 'orderName', title: '订单名称'},
                    { field: 'paymentTime', title: '兑付日期'},
                    { field: 'totalInstallment', title: '兑付总期数'},
                    { field: 'currentInstallment', title: '当前兑付期数'},
                    { field: 'statusName', title: '兑付状态'},
                    { field: 'totalPaymentMoney', title: '应兑付总金额'},
                    { field: 'totalPaymentPrincipalMoney', title: '应兑付本金总金额'},
                    { field: 'totalProfitMoney', title: '应兑付收益总金额'},
                    { field: 'paiedPrincipalMoney', title: '已兑付本金金额'},
                    { field: 'paiedProfitMoney', title: '已兑付收益金额'},
                    { field: 'comment', title: '备注'},
                    { field: 'paiedPaymentTime', title: '已兑付时间'}
                ]
            ],
            //设置按钮的文字与图片
            toolbar: [
                {
                    id: 'btnCustomerPersonalInfoQuery' + token,
                    text: '客户信息',
                    iconCls: 'icon-search'
                },
                {
                    id: 'btnOrderInfoQuery' + token,
                    text: '订单信息',
                    iconCls: 'icon-search'
                },
                {
                    id: 'btnProductionInfoQuery' + token,
                    text: '产品信息',
                    iconCls: 'icon-search'
                },
                {
                    id: 'btnCustomerMoneyLogInfoQuery' + token,
                    text: '客户资金日志信息',
                    iconCls: 'icon-search'
                }
            ],
            //加载成后做的事
            onLoadSuccess: function () {
                //初始化按钮事件
                onClickCustomerPersonalInfoQuery();
                onClickOrderInfoQuery();
                onClickProductionInfoQuery();
                onClickCustomerMoneyLogInfoQuery();
            }
        });
    }

    /**
     * 点击查询对应客户信息
     */
    function onClickCustomerPersonalInfoQuery() {
        var butttonId = "btnCustomerPersonalInfoQuery" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanQueryTable' + token, function (selected) {
                process.beforeClick();
                var queryURL = WEB_ROOT + "/sale/PaymentPlan_customerPersonalQuery.action?customerId=" + selected.customerId;
                var tableURL = WEB_ROOT + "/modules/sale/Query_CustomerPersonal.jsp?token=" + token;
                var tableName = "个人客户信息";
                initQueryWindow(queryURL, tableURL, tableName);
                process.afterClick();
            });
        });
    }

    /**
     * 点击查询对应订单信息
     */
    function onClickOrderInfoQuery() {
        var butttonId = "btnOrderInfoQuery" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanQueryTable' + token, function (selected) {
                process.beforeClick();
                var queryURL = WEB_ROOT + "/sale/PaymentPlan_orderQuery.action?orderId=" + selected.orderId;
                var tableURL = WEB_ROOT + "/modules/sale/Query_Order.jsp?token=" + token;
                var tableName = "订单信息";
                initQueryWindow(queryURL, tableURL, tableName);
                process.afterClick();
            });
        });
    }

    /**
     * 点击查询对应产品信息
     */
    function onClickProductionInfoQuery() {
        var butttonId = "btnProductionInfoQuery" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanQueryTable' + token, function (selected) {
                process.beforeClick();
                var queryURL = WEB_ROOT + "/sale/PaymentPlan_productionQuery.action?productionId=" + selected.productId;
                var tableURL = WEB_ROOT + "/modules/sale/Query_Production.jsp?token=" + token;
                var tableName = "产品分期信息";
                initQueryWindow(queryURL, tableURL, tableName);
                process.afterClick();
            });
        });
    }

    /**
     * 点击查询对应客户资金日志
     */
    function onClickCustomerMoneyLogInfoQuery() {
        var butttonId = "btnCustomerMoneyLogInfoQuery" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanQueryTable' + token, function (selected) {
                process.beforeClick();
                var queryURL = WEB_ROOT + "/sale/PaymentPlan_customerMoneyLogQuery.action?customerId=" + selected.customerId;
                var tableURL = WEB_ROOT + "/modules/sale/Query_CustomerMoneyLog.jsp?token=" + token;
                var tableName = "客户资金日志信息";
                initQueryWindow(queryURL, tableURL, tableName);
                process.afterClick();
            });
        });
    }

    /**
     * 调用公共弹窗方法
     * @param queryURL 请求url
     * @param tableURL jspUrl
     * @param tableName 显示表名
     */
    function initQueryWindow(queryURL, tableURL, tableName) {
        var windowID = "queryWindow" + token;
        fw.window(windowID, tableName, 1000, 350, tableURL, function () {
            if (tableName == '个人客户信息') {
                initCustomerPersonalQueryTable(queryURL, tableName);
            }
            else if (tableName == "订单信息") {
                initOrderQueryTable(queryURL, tableName);
            }
            else if (tableName == "产品分期信息") {
                initProductionQueryTable(queryURL, tableName);
            }
            else if (tableName == "客户资金日志信息") {
                initCustomerMoneyLogQueryTable(queryURL, tableName);
            }
        }, null);
    }

    // todo 个人客户信息展示
    function initCustomerPersonalQueryTable(queryURL, tableName) {
        var strTableId = "CustomerPersonalQueryTable" + token;
        $('#' + strTableId).datagrid({
            title: tableName,
            url: queryURL,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            rownumbers: true,
            singleSelect: true,
            pageList: [10, 30, 60],
            pageSize: 10,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            onSortColumn: function (sort, orders) {
            },
            loadFilter: function (data) {
                try {
//                    fw.alertReturnValue(data);
                    data = fw.dealReturnObject(data);
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true}  ,
                    { field: 'name', title: '姓名', sortable: true},
                    { field: 'loginName', title: '用户名', sortable: true},
                    { field: 'sex', title: '性别', sortable: true},
                    { field: 'birthday', title: '出生日期'},
                    { field: 'mobile', title: '移动电话', sortable: true},
                    { field: 'groupName', title: '负责组', sortable: true},
                    { field: 'saleManName', title: '负责销售', sortable: true},
                    { field: 'distributionStatus', title: '分配状态',
                        formatter: function (value, row, index) {
                            if (row.distributionStatus == 0) {
                                return '未审核';
                            }
                            if (row.distributionStatus == 1) {
                                return '通过';
                            }
                            else if (row.distributionStatus == 2) {
                                return '不通过';
                            }
                            else {
                                return '未分配';
                            }
                        }
                    }
                ]
            ],
            columns: [
                [
                    { field: 'identityCardAddress', title: '身份证地址', sortable: true},
                    { field: 'workAddress', title: '工作地址', sortable: true},
                    { field: 'homeAddress', title: '家庭地址', sortable: true},
                    { field: 'phone', title: '固定电话'},
                    { field: 'remark', title: '备注'},
                    { field: 'sid', title: '序号', hidden: true},
                    { field: 'id', title: '编号', hidden: true},
                    { field: 'saleManId', title: '负责编号', hidden: true, sortable: true}
                ]
            ], toolbar: [
                {
                    id: 'btnCustomerInfoDetail' + token,
                    text: '显示详情',
                    iconCls: 'icon-search'
                }
            ],
            onLoadSuccess: function () {
                onClickCustomerInfoDetail();
            }
        });
    }

    // 显示详情事件
    function onClickCustomerInfoDetail() {
        var butttonId = "btnCustomerInfoDetail" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('CustomerPersonalQueryTable' + token, function (selected) {
                process.beforeClick();
                var sid = selected.sid;
                var id = selected.id;
                var name = selected.name;
                var url = WEB_ROOT + "/customer/CustomerPersonal_load.action?personal.id=" + id;
                fw.post(url, null, function (data) {
//                    fw.alertReturnValue(data);
                    data["bizid"] = selected.id;
                    initWindowCustomerPersonalWindow(data, null, id, name);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    // 初始化弹窗
    function initWindowCustomerPersonalWindow(data, obj1, obj, name) {

        data["personal.OperatorId"] = loginUser.getId();

        var url = WEB_ROOT + "/modules/sale/Query_CustomerPersonalDetail.jsp?token=" + token;
        var windowId = "CustomerPersonalWindow" + token;
        fw.window(windowId, '个人客户详细信息', 1000, 575, url, function () {

            fw.getComboTreeFromKV('sex' + token, 'Sex', 'V', fw.getMemberValue(data, 'personal.sex'));
            fw.getComboTreeFromKV('customerSourceId' + token, 'CustomerSource', 'V', fw.getMemberValue(data, 'personal.customerSourceId'));
            fw.getComboTreeFromKV('customerTypeId' + token, 'CustomerType', 'V', fw.getMemberValue(data, 'personal.customerTypeId'));
            fw.getComboTreeFromKV('relationshipLevelId' + token, 'RelationshipLevel', 'V', fw.getMemberValue(data, 'personal.relationshipLevelId'));
            fw.getComboTreeFromKV('creditRateId' + token, 'CreditRate', 'V', fw.getMemberValue(data, 'personal.creditRateId'));
            fw.getComboTreeFromKV('careerId' + token, 'Career', 'V', fw.getMemberValue(data, 'personal.careerId'));
            //开户行
            using(SCRIPTS_ROOT + '/customer/CustomerAccountClass.js', function () {
                var accunt = new CustomerAccountClass(token);
                accunt.initModule(obj, "notButton");
            });
            //证件
            using(SCRIPTS_ROOT + '/customer/CustomerCertificateClass.js', function () {

                var certificate = new CustomerCertificateClass(token);
                certificate.initModule(obj, "notButton");
            });
            //会访日志
            using(SCRIPTS_ROOT + '/customer/CustomerFeedbackClass_Tab.js', function () {
                var feedback = new CustomerFeedbackClass_Tab(token);
                feedback.initModule(obj, "notButton");
            });
            // 加载产品列表
            ListProductionTable(name);
            // 加载数据
            fw.formLoad('formCustomerPersonal' + token, data);

            if (obj1 == 1) {
                var url = WEB_ROOT + "/customer/CustomerPersonal_add.action";
                $.post(url, null, function (data) {
                    var json = "[" + data + "]";
                    var jsonArray = eval('(' + json + ')');
                    $("#personalNumber" + token).val(jsonArray[0].returnValue[0].personalNumber);
                    $("#creatTime" + token).val(jsonArray[0].returnValue[0].creatTime);
                    $("#nationId" + token).val("中国");
                })
            }

        }, null);
    }

    // 初始化产品列表
    function ListProductionTable(customerId) {
        var strTableId = "CustomerProductionTable" + token;
        var url = WEB_ROOT + "/customer/CustomerProduction_list.action";
        $('#' + strTableId).datagrid({
            url: url,
            queryParams: {
                customerId: customerId
            },
            fitColumns: true,
            singleSelect: true,
            pageList: [3],
            pageSize: 3,
            rownumbers: true,
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);
                    //fw.alertReturnValue(data);
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
                    { field: 'customerId', title: 'id', hidden: true, width: 25},
                    { field: 'customerName', title: '客户姓名', width: 24},
                    { field: 'name', title: '产品名称', width: 80 },
                    { field: 'projectName', title: '所属项目', width: 40},
                    { field: 'productCompositionName', title: '产品规模', width: 50 },
                    { field: 'createTime', title: '认购时间', width: 22},
                    { field: 'money', title: '金额', width: 25 },
                    { field: 'moneyStatus', title: '状态'},
                    { field: 'originSalesman', title: '销售人员'}
                ]
            ],
            onLoadSuccess: function () {
            }
        });
    }


    // todo 订单信息展示
    function initOrderQueryTable(queryURL, tableName) {
        var strTableId = "OrderQueryTable" + token;
        $('#' + strTableId).datagrid({
            title: tableName,
            url: queryURL,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: [10, 15, 20],
            pageSize: 10,
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
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'orderNum', title: '订单号'},
                    {field: 'loginName', title: '用户名'},
                    {field: 'customerName', title: '客户'},
                    {field: 'productionName', title: '产品'},
                    {field: 'money', title: '购买金额(元)'},
                    {field: 'status', title: '当前状态',
                        formatter: function (value, row, index) {
                            return  value == "0" ? "预约" : "已售馨";
                        }
                    },
                    {field: 'customerAttribute', title: '客户属性'},
                    {field: 'referralCode', title: '推荐人'},
                    {field: 'createTime', title: '订单时间'},
                    {field: 'productionCompositionName', title: '产品构成'}
                ]
            ],
            toolbar: [
                {
                    id: 'btnOrderInfoDetail' + token,
                    text: '显示详情',
                    iconCls: 'icon-search'
                }
            ],
            onLoadSuccess: function () {
                onClickOrderInfoDetail();
            }
        });
    }

    // 初始化弹出按钮事件
    function onClickOrderInfoDetail() {
        var butttonId = "btnOrderInfoDetail" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('OrderQueryTable' + token, function (selected) {
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/production/Order_load.action?order.sid=" + sid;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["loginName"] = selected.loginName;
                    data["customerName"] = selected.customerName;
                    data["productionName"] = selected.productionName;
                    initWindowOrderWindow(data, sid);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    // 初始化弹出窗口
    function initWindowOrderWindow(data, sid) {

        data["order.OperatorId"] = loginUser.getId();

        var url = WEB_ROOT + "/modules/sale/Query_OrderDetail.jsp?token=" + token;
        var windowId = "OrderWindow" + token;
        fw.window(windowId, '订单信息', 470, 430, url, function () {
            fw.textFormatCurrency('orderMoney' + token);

            var treeID = $('#status' + token).combotree('tree');
            var orderStatus = data["order.status"];

            if (sid != null) {
                $('#production' + token).attr("readonly", true);
                $('#productionCompositionId' + token).combotree({readonly: true});
                $('#orderMoney' + token).attr("readonly", true);
//                onClickCheckCustomer();
                productionCompositionMenu(data["order.productionId"]);
                if (orderStatus == 0) {
                    var statusData = [
                        {'id': 0, 'text': "预约"},
                        {'id': 1, 'text': "已打款"}
                    ];
                } else if (orderStatus == 1) {
                    var statusData = [
                        {'id': 1, 'text': "已打款"},
                        {'id': 2, 'text': "申请退款"}
                    ];
                } else if (orderStatus == 2) {
                    var statusData = [
                        {'id': 2, 'text': "申请退款"},
                        {'id': 3, 'text': "已退款"}
                    ];
                }
            }
            statusMenu(treeID, statusData);

            // 加载数据
            fw.formLoad('formOrder' + token, data);
        }, null);
    }

    function statusMenu(treeID, statusData) {
        var tree = fw.getObjectFromId(treeID);

        $(tree).tree({
            data: statusData
        });
    }

    //根据已选择的产品加载产品构成
    function productionCompositionMenu(productId) {
        var tree = $('#productionCompositionId' + token).combotree('tree');
        fw.combotreeClear('productionCompositionId' + token);
        var url = WEB_ROOT + "/production/Order_getProductionCompositionMenu.action?productId=" + productId;
        fw.treeLoad(tree, url, null, null, null);
    }


    // todo 产品详细
    function initProductionQueryTable(queryURL, tableName) {
        var strTableId = 'ProductionQueryTable' + token;
        $('#' + strTableId).datagrid({
            title: tableName,
            url: queryURL,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: [10, 30, 60],
            pageSize: 10,
            remoteSort: true,
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
                    { field: 'name', title: '分期名称'},
                    { field: 'productId', title: '分期编号' }
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: '序号', hidden: true},
                    { field: 'id', title: '编号', hidden: true},
                    { field: 'projectId', title: '项目编号', hidden: true},
                    { field: 'contractCopies', title: '合同一式份'},
                    { field: 'startTime', title: '开始时间', hidden: true},
                    { field: 'stopTime', title: '结束时间', hidden: true},
                    { field: 'valueDate', title: '起息日'},
                    { field: 'expiringDate', title: '到期日'},
                    { field: 'interestDate', title: '付息日'},
                    { field: 'interestCycle', title: '付息周期'},
                    { field: 'interestUnit', title: '付息单位'},
                    { field: 'interestTimes', title: '付息期数'},
                    { field: 'interestType', title: '付息类型'},
                    { field: 'appointmentMoney', title: '预约金额' },
                    { field: 'saleMoney', title: '销售金额'},
                    { field: 'status', title: '状态' },
                    { field: 'productionName', title: '产品' },
                    { field: 'websiteDisplayName', title: '网站显示名称', hidden: true },
                    { field: 'investTermView', title: '网站显示投资期限', hidden: true },
                    { field: 'investTerm', title: '网站显示期限查询', hidden: true }
                ]
            ],
            toolbar: [
                {
                    id: 'btnProductionInfoDetail' + token,
                    text: '显示详情',
                    iconCls: 'icon-search'
                }
            ],
            onLoadSuccess: function () {
                onClickProductionDetail();
            }
        });
    }

    // 显示详情
    function onClickProductionDetail() {
        var butttonId = "btnProductionInfoDetail" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('ProductionQueryTable' + token, function (selected) {
                process.beforeClick();
                var sid = selected.sid;
                var id = selected.id;
                var url = WEB_ROOT + "/production/Production_load.action?production.sid=" + sid;
                fw.post(url, null, function (data) {
//                    fw.alertReturnValue(data);
                    var url = WEB_ROOT + "/production/ProductionInfo_load.action?productionInfo.productionId=" + data["production.id"];
                    fw.post(url, null, function (data1) {
                        //fw.alertReturnValue(data1);
                        data["productionInfo.sid"] = data1["productionInfo.sid"];
                        data["productionInfo.id"] = data1["productionInfo.id"];
                        data["productionInfo.state"] = data1["productionInfo.state"];
                        data["productionInfo.operatorId"] = data1["productionInfo.operatorId"];
                        data["productionInfo.operateTime"] = data1["productionInfo.operateTime"];
                        data["productionInfo.productionId"] = data1["productionInfo.productionId"];
                        data["productionInfo.description"] = data1["productionInfo.description"];
                        initWindowProductionWindow(data, id);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                }, null);
            })

        });
    }

    // 初始化弹窗
    function initWindowProductionWindow(data, obj) {
        data["production.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/sale/Query_ProductionDetail.jsp?token=" + token;
        var windowId = "ProductionWindow" + token;
        fw.window(windowId, '产品分期信息', 970, 650, url, function () {
            fw.textFormatCurrency('size' + token);
            var vc = $("#interestCycle").val();
            var vt = $("#interestTimes").val();
            $('#interestType' + token).combotree({
                onSelect: function (node) {
                    if (node.id == "一次性本息兑付") {
                        $("#interestCycle" + token).val('1');
                        $("#interestCycle" + token).validatebox('validate');
                        $("#interestTimes" + token).val('1');
                        $("#interestTimes" + token).validatebox('validate');
                        $('#interestCycle' + token).attr('readonly', true);
                        $('#interestTimes' + token).attr('readonly', true);
                    }
                    if (node.id == "按月本息兑付") {
                        $('#interestCycle' + token).attr('readonly', false);
                        $('#interestTimes' + token).attr('readonly', false);
                    }
                }
            });
            fw.initCKEditor("description" + token);
            // 得到下拉的id
            var tree = $('#productHomeId' + token).combotree('tree');
            // 初始化产品头的列表
            productionHome(tree, function () {
            });
            //初始化下拉列表
            initStatusTree("status" + token, data["production.status"]);
            initPaymentPlanTypeTree(data["production.interestType"]);
            interestUnitTree(data["production.interestUnit"]);

            //加载构成
            using(SCRIPTS_ROOT + '/production/ProductionCompositionClass.js', function () {
                var productionComposition = new ProductionCompositionClass(token);
                productionComposition.initModule(obj);
            });
            // 加载数据
            fw.formLoad('formProduction' + token, data);
        }, null);
    }

    // 初始化兑付类型
    function initPaymentPlanTypeTree(selectIndexId) {
        var URL = WEB_ROOT + "/production/Production_interestTypeTree.action";
        //付息类型
        fw.combotreeLoad("interestType" + token, URL, selectIndexId);
    }

    // 初始化产品头
    function productionHome(treeId, success) {
        var url = WEB_ROOT + "/production/ProductionHome_productionHomeList.action";
        fw.treeLoad(treeId, url, null, success, null);
    }

    // 初始化下拉列表
    function initStatusTree(combotreeId, selectIndexId) {
        if (combotreeId == null) {
            combotreeId = "search_status" + token;
        }
        var URL = WEB_ROOT + "/oa/task/Task_StatusTree.action";
        fw.combotreeLoad(combotreeId, URL, selectIndexId);
    }

    function interestUnitTree(selectIndexId) {
        var URL = WEB_ROOT + "/production/Production_interestUnitTree.action";
        //付息单位
        fw.combotreeLoad("interestUnit" + token, URL, selectIndexId);
    }


    // todo 客户资金日志详细
    function initCustomerMoneyLogQueryTable(queryURL, tableName) {
        var strTableId = 'CustomerMoneyLogQueryTable' + token;
        $('#' + strTableId).datagrid({
            title: tableName,
            url: queryURL,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [10, 30, 60],
            pageSize: 10,
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
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true, width: 30 },
                    { field: 'id', title: 'id', hidden: true, width: 30 },
                    { field: 'state', title: 'state', hidden: true, width: 30 },
                    { field: 'operatorId', title: 'operatorId', hidden: true, width: 30 },
                    { field: 'operateTime', title: 'operateTime', hidden: true, width: 30 },
                    { field: 'customerName', title: '客户名称', width: 30 },
                    { field: 'type', title: '类型', width: 30 },
                    { field: 'content', title: '内容', width: 30 },
                    { field: 'status', title: '状态', width: 30 },
                    { field: 'moduleId', title: '模块编号', width: 30 },
                    { field: 'bizId', title: '业务编号', width: 30 }
                ]
            ],
            onLoadSuccess: function () {
            }
        });
    }

    /**
     * 查询事件
     */
    function onClickPaymentPlanSearch() {
        var buttonId = "btnSearchPaymentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "PaymentPlanGuanLiTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;


            //获取作为查询条件的oredrId条件
            params['paymentPlanVO.orderId'] = $("#search_orderId" + token).val();

            //产品名称
            params['paymentPlanVO.productName'] = $("#search_productName" + token).val()
            //客户名称
            params['paymentPlanVO.customerName'] = $("#search_customerName" + token).val();

            params["paymentPlanVO.search_status"] = fw.getFormValue('search_status' + token, fw.type_form_combotree, fw.type_get_value);
            //兑付日期 时间段查询
            params["paymentPlanVO_paymentTime_Start"] = fw.getFormValue('search_PaymentTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["paymentPlanVO_paymentTime_End"] = fw.getFormValue('search_PaymentTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            //兑付类型
            params["paymentPlanVO.type"] = fw.getFormValue('search_Type' + token, fw.type_form_combotree, fw.type_get_value);
            $('#' + strTableId).datagrid('load');


        });
    }

    /**
     * 查询重置事件
     */
    function onClickPaymentPlanSearchReset() {
        var buttonId = "btnResetPaymentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_PaymentTime_Start' + token).datebox("setValue", '');
            $('#search_PaymentTime_End' + token).datebox("setValue", '');
            fw.combotreeClear('search_Type' + token);
            fw.combotreeClear('search_status' + token);
            //清空文本框的值
            $('#search_orderId' + token).val('');
            $('#search_productName' + token).val('');
            $('#search_customerName' + token).val('');
        });
    }


///  事件定义 结束  /////////////////////////////////////////////////////////////////


    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
}
