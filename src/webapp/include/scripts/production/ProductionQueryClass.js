/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * User: 姚章鹏
 */
var ProductionQueryClass = function (token, my, obj) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

        //初始化查询区域
        initFormProductTable();

        // 初始化查询事件
        onClickProductionSearch();
        // 初始化查询重置事件
        onClickProductionSearchReset();
        // 初始化表格
        initTableProductionTable();
        //初始化下拉列表
        initStatusTree(null, "-2");
        //初始化选择区域
        initSelectArea();
    }

    /**
     * 初始化下拉列表项
     */
    function initStatusTree(combotreeId, selectIndexId) {
        if (combotreeId == null) {
            combotreeId = "search_status" + token;
        }
        var URL = WEB_ROOT + "/oa/task/Task_StatusTree.action";
        fw.combotreeLoad(combotreeId, URL, selectIndexId);
    }

    function interestTypeTree(selectIndexId) {
        var URL = WEB_ROOT + "/production/Production_interestTypeTree.action";
        //付息类型
        fw.combotreeLoad("interestType" + token, URL, selectIndexId);
    }

    function interestUnitTree(selectIndexId) {
        var URL = WEB_ROOT + "/production/Production_interestUnitTree.action";
        //付息单位
        fw.combotreeLoad("interestUnit" + token, URL, selectIndexId);
    }

    function initFormProductTable() {
        fw.getComboTreeFromKV('search_ProductionName' + token, null, 'k', '-2');

        var tree = $('#search_ProductionName' + token).combotree('tree');
        productionHome(tree, function () {});
    }

    /**
     * 初始化表格
     */
    function initTableProductionTable() {
        fw.combotreeClear('#search_Status' + token);
        var strTableId = 'ProductionTable' + token;
        var pageSize = 10;
        var pageList = [10, 20, 30]
        var url = WEB_ROOT + "/production/Production_list.action";
        if (obj != null) {
            url = WEB_ROOT + "/production/Production_list.action?productionStatus='2'";
            pageSize = 10;
            pageList = [10]
        }
        //alert(pageSize);
        $('#' + strTableId).datagrid({
            title: '产品分期综合查询',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: pageList,
            pageSize: pageSize,
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
                    { field: 'interestUnit', title : '付息单位'},
                    { field: 'interestTimes', title: '付息期数'},
                    { field: 'interestType', title: '付息类型'},
                    { field: 'appointmentMoney', title: '预约金额' },
                    { field: 'saleMoney', title: '销售金额'},
                    { field: 'status', title: '状态' },
//                { field: 'projectName', title: '所属项目' },
                    { field: 'productionName', title: '产品' },
                    { field: 'websiteDisplayName', title: '网站显示名称', hidden: true },
                    { field: 'investTermView', title: '网站显示投资期限', hidden: true },
                    { field: 'investTerm', title: '网站显示期限查询', hidden: true }
                ]
            ],
            onLoadSuccess: function () {
                onClickCustomerInfo();
                onClickPaymentInfo();
                onClickOrderInfo();
                onClickCustomerLog();
            }
        });

    }
    ///  事件定义 开始  /////////////////////////////////////////////////////////////////
    /**
     * 查询事件
     */
    function onClickProductionSearch() {
        var buttonId = "btnSearchProduction" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ProductionTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["productionVO.name"] = $("#search_Name" + token).val();
            params["productionVO.status"] = fw.getFormValue('search_Status' + token, fw.type_form_combotree, fw.type_get_text);
            params["productionOrder"] = fw.getFormValue('search_Order' + token, fw.type_form_combotree, fw.type_get_text);

            params["productionVO.productHomeId"] = fw.getFormValue('search_ProductionName' + token, fw.type_form_combotree, fw.type_get_value);
            $('#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
        });

    }

    /**
     * 查询重置事件
     */
    function onClickProductionSearchReset() {
        var buttonId = "btnResetProduction" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_Name" + token).val('');
            fw.combotreeClear('#search_ProductionName' + token);
            fw.combotreeClear('#search_Status' + token);
            fw.combotreeClear('#search_Order' + token);
        });
    }

    /**
     * 客户信息
     */
    function onClickCustomerInfo() {

        var buttonId = "btnCustomerInfo" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                //获取客户列表的链接
                var url = WEB_ROOT + "/modules/customer/CustomerPersonQuery.jsp?token=" + token;
                //弹出窗口的ID
                var selectionWindowId = "CustomerTableWindow" + token;
                //弹出窗口
                fw.window(selectionWindowId, '客户综合查询', 930, 500, url, function () {
                    // 初始化查询事件
                    onClickCustomerPersonalSearch();
                    // 初始化查询重置事件
                    onClickCustomerPersonalSearchReset();

                    initCustomerPersonInfo(selected);
                }, null);
            })
        });

    }


    /**
     * 查询事件
     */
    function onClickCustomerPersonalSearch() {
        var buttonId = "btnSearchCustomerPersonal" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "CustomerPersonalQueryTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["personalVO.loginName"] = $("#search_LoginName" + token).val();
            params["personalVO.mobile"] = $("#search_Mobile" + token).val();
            params["personalVO.workAddress"] = $("#search_WorkAddress" + token).val();
            $('#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
            fw.treeClear()
        });

    }
    /**
     * 查询重置事件
     *
     * 修改人：李昕骏
     * 时间：2015年8月18日 15:33:16
     * 内容： 重置不进行搜索操作
     */
    function onClickCustomerPersonalSearchReset() {
        var buttonId = "btnResetCustomerPersonal" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_LoginName' + token).val('');
            $("#search_Name" + token).val('');
            $('#search_Mobile' + token).val('');
            $('#search_WorkAddress' + token).val('');
            // 清空文本框数据，并查询列表
            //var strTableId = "CustomerPersonalTable" + token;
            //var params = $('#' + strTableId).datagrid('options').queryParams;
            //params["personalVO.name"] = $("#search_Name" + token).val();
            //params["personalVO.loginName"] = $("#search_LoginName" + token).val();
            //params["personalVO.mobile"] = $("#search_Mobile" + token).val();
            //params["personalVO.workAddress"] = $("#search_WorkAddress" + token).val();
            //$('#' + strTableId).datagrid('load');
        });
    }

    function initPaymentInfo(obj) {
        var strTableId = 'PaymentPlanDataTable' + token;
        var url = WEB_ROOT + "/sale/PaymentPlan_loadPaymentData.action?productionId=" +obj.id;
        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '兑付信息',
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
                    { field: 'type', title: '兑付类型' },
                    { field: 'orderName', title: '订单名称'},
                    //{ field: 'paymentMoney', title: '兑付金额'},
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
            ]
        });
    }
    function initCustomerPersonInfo(obj) {
        var strTableId = 'CustomerPersonalQueryTable' + token;
        var url = WEB_ROOT + "/customer/CustomerPersonal_loadCustomer.action?productionId=" +obj.id;
        $('#' + strTableId).datagrid({
            title: '客户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [10, 20, 40],
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
                    {field: 'ck', checkbox: true}  ,
                    { field: 'name', title: '姓名', sortable: true},
                    { field: 'id', title: '编号',hidden: true, sortable: true},
                    { field: 'sid', title: '客户sid',hidden: true, sortable: true},
                    { field: 'loginName', title: '用户名', sortable: true},
                    { field: 'sex', title: '性别', formatter: function(value,row,index) {
                        if (row.sex == '1') {
                            return '男';
                        }else{
                            return '女';
                        }

                    }},
                    { field: 'mobile', title: '移动电话', sortable: true}
                ]
            ],
            columns: [
                [
                    { field: 'identityCardAddress', title: '身份证地址', sortable: true},
                    { field: 'workAddress', title: '工作地址', sortable: true},
                    { field: 'homeAddress', title: '家庭地址', sortable: true},
                    { field: 'phone', title: '固定电话'},
                    { field: 'saleManId', title: '负责编号', hidden: true, sortable: true}
                ]
            ],
           toolbar:[{
                id:'btnCustomerPersonalData'+token,
                text:'查看',
                iconCls:'icon-search'
            } ],
            onLoadSuccess: function () {
                onClickCustomerPersonalInfo();

            }

        });

    }

    /**
     * 兑付信息
     */
    function onClickPaymentInfo() {
        var buttonId = "btnPaymentInfo" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                //获取客户列表的链接
                var url = WEB_ROOT + "/modules/sale/PaymentPlanData.jsp?token=" + token;
                //弹出窗口的ID
                var selectionWindowId = "PaymentTableWindow" + token;
                //弹出窗口
                fw.window(selectionWindowId, '兑付综合查询', 1100, 500, url, function () {
                    //初始化列表状态选择查询。
                    initFormPaymentPlanSearch();

                    // 初始化查询事件
                    onClickPaymentPlanSearch();
                    // 初始化查询重置事件
                    onClickPaymentPlanSearchReset();
                    interestTypeTree("search_Type", '-2')
                    initPaymentInfo(selected);

                }, null);
            })
        });
    }

    //初始化兑付类型
    function interestTypeTree(typeId, selectIndexId) {
        if (typeId == null) {
            typeId == 'search_Type';
        }

        var URL = WEB_ROOT + "/production/Production_interestTypeTree.action";
        //付息类型
        fw.combotreeLoad(typeId + token, URL, selectIndexId);
    }
    //初始化兑付状态
    function initFormPaymentPlanSearch() {
        fw.getComboTreeFromKV('search_status' + token, 'Sale_PaymentPlan_Status', 'k', '-2');
    }
    /**
     * 查询事件
     */
    function onClickPaymentPlanSearch() {
        var buttonId = "btnSearchPaymentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "PaymentPlanDataTable" + token;
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
    /**
     * 订单信息
     */
    function onClickOrderInfo() {
        var butttonId = "btnOrderInfo" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                //获取客户列表的链接
                var url = WEB_ROOT + "/modules/production/OrderQueryInfo.jsp?token=" + token;
                //弹出窗口的ID
                var selectionWindowId = "orderTableWindow" + token;
                //弹出窗口
                fw.window(selectionWindowId, '订单综合查询', 930, 500, url, function () {

                    //初始化查询区域
                    initSearchArea();
                    // 初始化表格
                    initOrderInfo(selected);
                }, null);
            })
        });
    }

    function initSearchArea() {
        //var treeID = $('#search_status'+token).combotree('tree');
        //statusMenu(treeID)
        $('#search_statusInfo' + token).combotree('loadData', [
            {'id': 1, 'text': "预约"},
            {'id': 2, 'text': "已打款"},
            {'id': 3, 'text': "申请退款"},
            {'id': 4, 'text': "已退款"},
            {'id': 5, 'text': "已兑付"},
            {'id': 6, 'text': "作废"}
        ]);


        // 初始化查询事件
        onClickOrderSearch();
        // 初始化查询重置事件
        onClickOrderSearchReset();

    }

    /**
     * 订单查看事件
     */
    function onClickOrder() {
        var butttonId = "btnOrderSearchEvent" + token;
        fw.bindOnClick(butttonId, function (process) {

            fw.datagridGetSelected('OrderQueryInfoTable' + token, function (selected) {
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


    /**
     * 初始化弹出窗口
     */
    function initWindowOrderWindow(data, sid) {

        data["order.OperatorId"] = loginUser.getId();

        var url = WEB_ROOT + "/modules/production/Order_Save.jsp?token=" + token;
        var windowId = "OrderWindow" + token;
        fw.window(windowId, '订单信息', 430, 430, url, function () {
            fw.textFormatCurrency('orderMoney'+token);

              $('#btnCheckCustomer'+token).remove();
              $('#btnProduction'+token).remove();
            $('#btnOrderSubmit'+token).remove();
            $('#btnOrderCancel'+token).remove();
            var orderStatus = data["order.status"];

            productionCompositionMenu(data["order.productionId"]);

            if (sid != null) {
                $('#production' + token).attr("readonly", true);
                $('#productionCompositionId' + token).combotree({readonly: true});
                $('#orderMoney' + token).attr("readonly", true);
                if (orderStatus == 0) {
                    var statusData = [{'id': 0, 'text': "预约"}, {'id': 1, 'text': "已打款"}];
                } else if (orderStatus == 1) {
                    var statusData = [{'id': 1, 'text': "已打款"}, {'id': 2, 'text': "申请退款"}];
                } else if (orderStatus == 2) {
                    var statusData = [{'id': 2, 'text': "申请退款"}, {'id': 3, 'text': "已退款"}];
                }
            } else {
                var statusData = [{'id': 0, 'text': "预约"}, {'id': 1, 'text': "已打款"}];
                //新增客户获取客户编号
            }


            // 加载数据
            fw.formLoad('formOrder' + token, data);
            // 初始化表单提交事件
        }, null);
    }

    //根据已选择的产品加载产品构成
    function productionCompositionMenu(productId) {
        var tree = $('#productionCompositionId' + token).combotree('tree');
        fw.combotreeClear('productionCompositionId' + token);
        var url = WEB_ROOT + "/production/Order_getProductionCompositionMenu.action?productId=" + productId;
        fw.treeLoad(tree, url, null, null, null);
    }

    /**
     * 查询事件
     */
    function onClickOrderSearch() {
        var buttonId = "btnSearchOrder" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "OrderQueryInfoTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["orderVO.orderNum"] = $("#search_OrderNum" + token).val();
            params["orderVO.loginName"] = $("#search_Customer" + token).val();
            params["orderVO.productionName"] = $("#search_Product" + token).val();
            // var status = $("#search_status"+token).combotree('getValues');
            var statuss = $('#search_statusInfo' + token).combotree('getValue');
            //alert(statuss);
            params["orderVO.status"] = statuss-1;
            $('#' + strTableId).datagrid('load');                         //加载第一页的行

            fw.treeClear()
        });

    }

    /**
     * 查看个人事件
     */
    function onClickCustomerPersonalInfo() {
        var butttonId = "btnCustomerPersonalData" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('CustomerPersonalQueryTable' + token, function (selected) {
                process.beforeClick();
                var sid = selected.sid;
                var id = selected.id;
                var name = selected.name;
                var url = WEB_ROOT + "/customer/CustomerPersonal_load.action?personal.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["bizid"] = selected.id;
                    initWindowCustomerPersonalWindow(data, null, id, name);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowCustomerPersonalWindow(data, obj1, obj, name) {

        data["personal.OperatorId"] = loginUser.getId();

        var url = WEB_ROOT + "/modules/customer/CustomerPersonal_Save.jsp?token=" + token;
        var windowId = "CustomerPersonalWindow" + token;
        fw.window(windowId, '个人客户详细信息', 1000, 575, url, function () {
             $('#btnCustomerPersonalSubmit'+token).remove();
             $('#btnCustomerPersonalSubmit'+token).remove();
            fw.getComboTreeFromKV('sex' + token, 'Sex', 'V', fw.getMemberValue(data, 'personal.sex'));
            fw.getComboTreeFromKV('customerSourceId' + token, 'CustomerSource', 'V', fw.getMemberValue(data, 'personal.customerSourceId'));
            fw.getComboTreeFromKV('customerTypeId' + token, 'CustomerType', 'V', fw.getMemberValue(data, 'personal.customerTypeId'));
            fw.getComboTreeFromKV('relationshipLevelId' + token, 'RelationshipLevel', 'V', fw.getMemberValue(data, 'personal.relationshipLevelId'));
            fw.getComboTreeFromKV('creditRateId' + token, 'CreditRate', 'V', fw.getMemberValue(data, 'personal.creditRateId'));
            fw.getComboTreeFromKV('careerId' + token, 'Career', 'V', fw.getMemberValue(data, 'personal.careerId'));
            // 初始化表单提交事件
            //开户行
            using(SCRIPTS_ROOT + '/customer/CustomerAccountClass.js', function () {
                var accunt = new CustomerAccountClass(token);
                accunt.initModule(obj, "not have btn");
            });
            //证件
            using(SCRIPTS_ROOT + '/customer/CustomerCertificateClass.js', function () {

                var certificate = new CustomerCertificateClass(token);
                certificate.initModule(obj, "not have btn");
            });
            //会访日志
            using(SCRIPTS_ROOT + '/customer/CustomerFeedbackClass_Tab.js', function () {
                var feedback = new CustomerFeedbackClass_Tab(token);
                feedback.initModule(obj, "not have btn");
            });

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


    /**
     * 查询重置事件
     */
    function onClickOrderSearchReset() {
        var buttonId = "btnResetOrder" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_OrderNum" + token).val('');
            $("#search_Customer" + token).val('');
            $("#search_Product" + token).val('');
            fw.combotreeClear('search_statusInfo' + token);
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
                    var sid = selected.sid;
                    var url = WEB_ROOT + "/production/Order_load.action?order.sid=" + sid;
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

    function initOrderInfo(obj) {
        var strTableId = 'OrderQueryInfoTable' + token;
        var url = WEB_ROOT + "/production/Order_loadOrder.action?productionId=" +obj.id;
        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '订单信息',
            url: url,
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
            frozenColumns: [[  // 固定列，没有滚动条
                {field: 'ck', checkbox: true}
            ]],
            columns: [[
                {field: 'sid', title: '序号', hidden: true},
                {field: 'id', title: '编号', hidden: true},
                {field: 'orderNum', title: '订单号'},
                {field: 'loginName', title: '用户名'},
                {field: 'customerName', title: '客户'},
                {field: 'productionName', title: '产品'},
                {field: 'money', title: '购买金额(元)'},
                {field: 'status', title: '当前状态',formatter: function(value,row,index) {
                    if (row.status == '0') {
                        return '预约';
                    }else if(row.status=='1'){
                        return '已打款';

                    }else if(row.status=='2'){
                        return '申请退款';

                    }else if(row.status=='3'){
                        return '已退款';

                    }else if(row.status=='4'){
                        return '已兑付';

                    }else if(row.status=='5'){
                        return '作废';

                    }
                }},
                {field: 'customerAttribute', title: '客户属性'},
                {field: 'referralCode', title: '推荐人'},
                {field: 'createTime', title: '订单时间'},
                {field: 'productionCompositionName', title: '产品构成'}
            ]],

            toolbar: [
                {
                    id: 'btnOrderSearchEvent' + token,
                    text: '查看',
                    iconCls: 'icon-search'
                }
            ],
            onLoadSuccess: function () {
                onClickOrder();
            }
        });
    }




    /**
     * 客户资金日志
     */
    function onClickCustomerLog() {
        var buttonId = "btnCustomerMoneyLogInfo" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                //获取客户列表的链接
                var url = WEB_ROOT + "/modules/customer/CustomerMoneyLogData.jsp?token=" + token;
                //弹出窗口的ID
                var selectionWindowId = "CustomerMoneyLogInfoWindow" + token;
                //弹出窗口
                fw.window(selectionWindowId, '客户资金综合查询', 1100, 500, url, function () {

                    // 初始化查询事件
                    initTypeTree(null,"-2");
                    initStatusTree(null,"-2");
                    onClickCustomerMoneyLogSearch();
                    // 初始化查询重置事件
                    onClickCustomerMoneyLogSearchReset();

                    initCustomerMoneyLogTable(selected);

                }, null);
            })
        });

    }

    // 构造初始化表格脚本
    function initCustomerMoneyLogTable(obj) {
        var strTableId = 'CustomerMoneyLogDataTable'+token;
        var url = WEB_ROOT+"/customer/CustomerMoneyLog_loadCustomerLogData.action?productionId="+obj.id;

        $('#'+strTableId).datagrid({
            title: '资金日志',
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
                try {
                    data = fw.dealReturnObject(data);

                    //alert(JSON.stringify(data));
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
                { field: 'sid', title: 'sid',hidden:true, width: 30 },
                { field: 'id', title: 'id',hidden:true, width: 30 },
                { field: 'state', title: 'state',hidden:true, width: 30 },
                { field: 'operatorId', title: 'operatorId',hidden:true, width: 30 },
                { field: 'operateTime', title: 'operateTime',hidden:true, width: 30 },
                { field: 'customerName', title: '客户名称', width: 30 },
                { field: 'type', title: '类型', width: 30 },
                { field: 'content', title: '内容', width: 30 },
                { field: 'status', title: '状态', width: 30 },
                { field: 'moduleId', title: '模块编号', width: 30 },
                { field: 'bizId', title: '业务编号', width: 30 }
            ]],

            onLoadSuccess:function() {

            }
        });
    }


    /**
     * 查询重置事件
     */
    function onClickCustomerMoneyLogSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 重置部分
            fw.combotreeClear("search_status" + token);
            fw.combotreeClear("search_type" + token);
            $('#search_StartTime_Start'+token).datebox("setValue", '');
            $('#search_StartTime_End'+token).datebox("setValue", '');
            $('#search_content'+token).val('');

        });
    }

    function onClickCustomerMoneyLogSearch(){
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "CustomerMoneyLogDataTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["customerMoneyLogVO.content"] = $("#search_content"+token).val();
            params["customerMoneyLogVO.type"]=$('#search_type'+token).combotree('getText');
            params["customerMoneyLogVO.status"]=$('#search_status'+token).combotree('getText');


            //获取是减控件的值
            params["customerMoneyLogVO_operateTime_Start"] = fw.getFormValue('search_StartTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["customerMoneyLogVO_operateTime_End"] = fw.getFormValue('search_StartTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 初始化下拉列表项
     * selectIndexId 为选中的项 -2 为什么都选
     */
    function initStatusTree(combotreeId,selectIndexId) {
        if(combotreeId==null){
            combotreeId="search_status" + token;
        }
        var URL = WEB_ROOT + "/customer/CustomerMoneyLog_StatusTree.action";
        fw.combotreeOnload(combotreeId,URL,function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {}
            return treeData;
        },selectIndexId);
    }

    /**
     * 初始化下拉列表项
     * selectIndexId 为选中的项 -2 为什么都选
     */
    function initTypeTree(combotreeId,selectIndexId) {
        if(combotreeId==null){
            combotreeId="search_type" + token;
        }
        var URL = WEB_ROOT + "/customer/CustomerMoneyLog_TypeTree.action";
        fw.combotreeOnload(combotreeId,URL,function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {}
            return treeData;
        },selectIndexId);
    }



    /**
     * 数据提交事件
     */
    function onClickProductionSubmit() {


        var buttonId = "btnProductionSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.CurrencyFormatText('size' + token);
            //判断配额是否小于或等于0
            if (fw.getCurrencyFormatValue('size' + token) <= 0) {
                fw.alert("警告", "配额不可为0");
                return;
            }
            //开始时间
            var startTime = fw.getFormValue('startTime' + token, fw.type_form_datebox, fw.type_get_value);
            // 到期日
            var stopTime = fw.getFormValue('stopTime' + token, fw.type_form_datebox, fw.type_get_value);
            //结束时间不能小于开始时间
            if (stopTime < startTime) {
                fw.alert("警告", "结束时间不能小于开始时间");
                return;
            }


            //  起息日不能大于到期日
            //起息日
            var valueDate = fw.getFormValue('valueDate' + token, fw.type_form_datebox, fw.type_get_value);
            // 到期日
            var expiringDate = fw.getFormValue('expiringDate' + token, fw.type_form_datebox, fw.type_get_value);
            if (valueDate >= expiringDate) {

                fw.alert("警告", "起息日不能大于或等于到期日");
                return;
            }

            //结束时间不能小于开始时间
            var formId = "formProduction" + token;
            var url = WEB_ROOT + "/production/Production_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
//                    alert('done');
                process.afterClick();
                fw.datagridReload("ProductionTable" + token);
                fw.windowClose('ProductionWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 创建人：张舜清
     * 时间：2015年8月18日20:49:37
     *
     * @param treeId
     * @param success
     */
    function productionHome(treeId, success) {
        var url = WEB_ROOT + "/production/ProductionHome_productionHomeList.action";
        fw.treeLoad(treeId, url, null, success, null);
    }

    function initSelectArea() {

        if (!isSelectWindow) {
            $("#ProductionSelectArea" + token).remove();
            return;
        }

        $("#btnProductionAdd" + token).remove();
        $("#btnProductionEdit" + token).remove();
        $("#btnProductionDelete" + token).remove();
        $("#search_Status" + token).remove();
        //初始化选择项目确定按钮
        onClickSelect();

    }

    function onClickSelect() {
        var buttonId = "#btnSelect" + token;

        fw.bindOnClick(buttonId, function () {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                if (fw.checkIsFunction(callbackfunction)) {
                    callbackfunction(selected);
                }
                fw.windowClose("ProductionWindow" + token);
            });
        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    var isSelectWindow = false;
    var callbackfunction = null;

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initModuleWithSelect: function (callback) {
            isSelectWindow = true;
            callbackfunction = callback;
            return initAll();
        }
    };
}
