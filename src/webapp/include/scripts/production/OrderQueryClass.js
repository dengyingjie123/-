/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/15/14
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */

var OrderQueryClass = function (token) {
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化查询区域
        initSearchArea();
        // 初始化表格
        initTableOrderTable();

        //初始化选择区域
        //初始化选择订单
        inirSelectOrder();

        initSelect();
    }

    function initSearchArea() {
        //var treeID = $('#search_status'+token).combotree('tree');
        //statusMenu(treeID)
        $('#search_status' + token).combotree('loadData', [
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
     * 初始化表格
     */
    function initTableOrderTable() {
        var strTableId = 'OrderTable' + token;
        var url = WEB_ROOT + "/production/Order_list.action";

        $('#' + strTableId).datagrid({
            title: '订单信息综合查询',
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
                {field: 'customerId', title: '客户编号',hidden: true},
                {field: 'productionId', title: '产品编号',hidden: true},
                {field: 'productionName', title: '产品'},
                {field: 'money', title: '购买金额(元)'},
                {field: 'status', title: '当前状态'},
                {field: 'customerAttribute', title: '客户属性'},
                {field: 'referralCode', title: '推荐人'},
                {field: 'createTime', title: '订单时间'},
                {field: 'productionCompositionName', title: '产品构成'}
            ]],
            onLoadSuccess: function () {
                onClickPaymentInfo();
                onClickCustomerInfo();
                onClickProductInfo();
                onClickCustomerMoneyLogInfo();
            }
        });
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
            // var status = $("#search_status"+token).combotree('getValues');
            var statuss = $('#search_status' + token).combotree('getText');
            //alert(statuss);
            params["status"] = statuss;
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
            fw.combotreeClear('search_status' + token);
        });
    }

    /**
     * 兑付信息
     */
    function onClickPaymentInfo() {
        var buttonId = "btnPaymentInfo" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                //获取客户列表的链接
                var url = WEB_ROOT + "/modules/sale/PaymentPlanInfo.jsp?token=" + token;
                //弹出窗口的ID
                var selectionWindowId = "PaymentInfoWindow" + token;
                //弹出窗口
                fw.window(selectionWindowId, '兑付综合查询', 1100, 500, url, function () {
                    //初始化列表状态选择查询。
                    initFormPaymentPlanSearch();

                    interestTypeTree("search_Type", '-2')
                    // 初始化查询事件
                    onClickPaymentPlanSearch();

                    // 初始化查询重置事件
                    onClickPaymentPlanSearchReset();
                    initPaymentPlanTable(selected);

                }, null);
            })
        });

    }

    /**
     * 客户资金日志
     */
    function onClickCustomerMoneyLogInfo() {
        var buttonId = "btnCustomerLogInfo" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                //获取客户列表的链接
                var url = WEB_ROOT + "/modules/customer/CustomerMoneyLogInfo.jsp?token=" + token;
                //弹出窗口的ID
                var selectionWindowId = "CustomerMoneyLogInfoWindow" + token;
                //弹出窗口
                fw.window(selectionWindowId, '客户资金综合查询', 1100, 500, url, function () {

                    // 初始化查询事件
                    initTypeTree(null,"-2");
                    initStatusTreeMoneyLog(null,"-2");
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
        var strTableId = 'CustomerMoneyLogInfoTable'+token;
        var url = WEB_ROOT+"/customer/CustomerMoneyLog_loadCustomerLog.action?customerId="+obj.customerId;

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
     *客户信息
     */
    function onClickCustomerInfo() {

        var butttonId = "btnCustomerInfo" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                //获取客户列表的链接
                var url = WEB_ROOT + "/modules/customer/CustomerPersonInfo.jsp?token=" + token;
                //弹出窗口的ID
                var selectionWindowId = "CustomerPersonalDataWindow" + token;
                //弹出窗口
                fw.window(selectionWindowId, '客户综合查询', 930, 500, url, function () {

                    initCustomerPersonInfo(selected);
                }, null);
            })
        });

    }

    /**
     * 查询重置事件
     */
    function onClickCustomerMoneyLogSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 重置部分
            fw.combotreeClear("search_statusInfo" + token);
            fw.combotreeClear("search_type" + token);
            $('#search_StartTime_Start'+token).datebox("setValue", '');
            $('#search_StartTime_End'+token).datebox("setValue", '');
            $('#search_content'+token).val('');

        });
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
     * 初始化下拉列表项
     * selectIndexId 为选中的项 -2 为什么都选
     */
    function initStatusTreeMoneyLog(combotreeId,selectIndexId) {
        if(combotreeId==null){
            combotreeId="search_statusInfo" + token;
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


    function onClickCustomerMoneyLogSearch(){
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "CustomerMoneyLogInfoTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["customerMoneyLogVO.content"] = $("#search_content"+token).val();
            params["customerMoneyLogVO.type"]=$('#search_type'+token).combotree('getText');
            params["customerMoneyLogVO.status"]=$('#search_statusInfo'+token).combotree('getText');


            //获取是减控件的值
            params["customerMoneyLogVO_operateTime_Start"] = fw.getFormValue('search_StartTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["customerMoneyLogVO_operateTime_End"] = fw.getFormValue('search_StartTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            $( '#' + strTableId).datagrid('load');
        });
    }



    /**
     * 初始化兑付信息
     */
    function initPaymentPlanTable(obj) {
        var strTableId = 'PaymentInfoTable' + token;//表格ID
        var url = WEB_ROOT + "/sale/PaymentPlan_loadPaymentInfo.action?orderId="+obj.id;
        //设置datagrid
        $('#' + strTableId).datagrid({
            title: "兑付信息",
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件{kdk:''}
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
            //加载成后做的事
            onLoadSuccess: function () {}
        });


    }


    //初始化兑付状态

    function initFormPaymentPlanSearch() {
        fw.getComboTreeFromKV('search_statusInfo' + token, 'Sale_PaymentPlan_Status', 'k', '-2');
    }
    /**
     * 查询事件
     */
    function onClickPaymentPlanSearch() {
        var buttonId = "btnSearchPaymentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "PaymentInfoTable" + token;
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
            fw.combotreeClear('search_statusInfo' + token);
            //清空文本框的值
            $('#search_orderId' + token).val('');
            $('#search_productName' + token).val('');
            $('#search_customerName' + token).val('');
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



    function initCustomerPersonInfo(obj) {
            var strTableId = 'CustomerPersonalTable' + token;
            var url = WEB_ROOT + "/production/Order_loadCustomer.action?customerPersonalVO.id=" +obj.customerId;
            $('#' + strTableId).datagrid({
                title: '个人客户信息',
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
                        { field: 'loginName', title: '用户名', sortable: true},
                        { field: 'sex', title: '性别', sortable: true},
                        { field: 'birthday', title: '出生日期'},
                        { field: 'mobile', title: '移动电话', sortable: true},
                        { field: 'groupName', title: '负责组', sortable: true},
                        { field: 'saleManName', title: '负责销售', sortable: true},
                        { field: 'distributionStatus', title: '分配状态',formatter: function(value,row,index) {
                            if (row.distributionStatus == 0) {
                                return '未审核';
                            } if (row.distributionStatus == 1) {
                                return '通过';
                            } else if (row.distributionStatus == 2) {
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
                ],
                toolbar: [
                    {
                        id: 'btnCustomerData' + token,
                        text: '查看',
                        iconCls: 'icon-search'
                    }

                ],
                onLoadSuccess:function() {
                    onClickCustomerPersonalInfo();
                }
            });
        }


    /**
     * 查看个人事件
     */
    function onClickCustomerPersonalInfo() {
        var butttonId = "btnCustomerData" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('CustomerPersonalTable' + token, function (selected) {
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
        var windowId = "CustomerPersonalWindowTable" + token;
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
     * 产品信息
     */
    function onClickProductInfo() {
        var butttonId = "btnProductInfo" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('OrderTable' + token, function (selected) {
                //获取客户列表的链接
                var url = WEB_ROOT + "/modules/sale/CustomerProductionInfo.jsp?token=" + token;
                //弹出窗口的ID
                var selectionWindowId = "CustomerProductionWindow" + token;
                //弹出窗口
                fw.window(selectionWindowId, '产品综合查询', 930, 500, url, function () {
                    initCustomerProductionInfo(selected);
                }, null);
            })
        });

    }


    //初始化产品信息
    function initCustomerProductionInfo(obj){
        var strTableId = 'CustomerProductionDataTable' + token;
        var url = WEB_ROOT + "/production/Production_loadProduction.action?productionVO.id=" +obj.productionId;
        $('#' + strTableId).datagrid({
            title: '产品信息',
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
                    {field: 'ck', checkbox: true}
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
            toolbar: [
                {
                    id: 'btnProductData' + token,
                    text: '查看',
                    iconCls: 'icon-search'
                }

            ],
            onLoadSuccess:function() {
                onClickProductionDetail();

            }
        });
    }

    // 显示详情
    function onClickProductionDetail() {
        var butttonId = "btnProductData" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('CustomerProductionDataTable' + token, function (selected) {
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
        var url = WEB_ROOT + "/modules/sale/ProductionDetailInfo.jsp?token=" + token;
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
            productionHome(tree, function () {});
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

    /**
     * 数据提交事件
     */
    function onClickOrderSubmit() {
        var buttonId = "btnOrderSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
        fw.windowClose('OrderWindow' + token);
        });
    }

    //查询客户事件
    function onClickCheckCustomer() {

        $('#btnCheckCustomer' + token).bind('click', function () {
            var url = WEB_ROOT + "/modules/production/Select_Customer.jsp?token=" + token;
            var selectionWindowId = "CustomerSelectWindow" + token;
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function () {
                //加载js
                using(SCRIPTS_ROOT + '/production/CustomerSelectedClass.js', function () {
                    //alert("loaded...");
                    var obj = new OrderClass(token);
                    var customerSelected = new CustomerSelectedClass(token, obj);
                    customerSelected.initModule();
                });
            }, null);
        })
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
        var url = WEB_ROOT + "/production/Order_getReferralCode.action";
        $.post(url, null, function (data) {
            var json = eval('(' + data + ')');
            $("#referralCode" + token).val(json.returnValue.staffCode);
        })
        return false;
    }

    //产品选择
    function productionMenu() {

        $('#btnProduction' + token).bind('click', function () {
            var outerToken = token + "outer";
            var url = WEB_ROOT + "/modules/production/Production_Main.jsp?token=" + outerToken;
            var swindowId = "ProductionWindow" + outerToken;
            fw.window(swindowId, '选择产品', 930, 650, url, function () {
                //加载js
                using(SCRIPTS_ROOT + '/production/ProductionClass.js', function () {
                    //alert("loaded...");
                    var production = new ProductionClass(outerToken,123,"order");
                    production.initModuleWithSelect(function(data) {

                        //fw.alertReturnValue(data);

                        $("#production" + token).val(data["name"]);
                        $("#productionId" + token).val(data["id"]);
                        $("#paybackTime" + token).val(data["interestDate"]);
                        productionCompositionMenu(data["id"]);
                    });

                });
            }, null);
        })
    }

    //根据已选择的产品加载产品构成
    function productionCompositionMenu(productId) {
        var tree = $('#productionCompositionId' + token).combotree('tree');
        fw.combotreeClear('productionCompositionId' + token);
        var url = WEB_ROOT + "/production/Order_getProductionCompositionMenu.action?productId=" + productId;
        fw.treeLoad(tree, url, null, null, null);
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

    function inirSelectOrder() {

    }

    //客户名称文本框点击弹出客户列表事件
    function onClickCustomer() {
        $('#customerName' + token).bind('click', function () {
            //获取客户列表的链接
            var url = WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function () {
                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT + '/customer/CustomerListSelectClass.js', function () {
                    var obj = new CustomerDepositClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token, obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
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
        initModuleWithSelect: function(callback) {
            callbackfunction = callback;
            isSelectWindow = true;
            return initAll();
        },
        loadCustomerInfo: function (customerId, loginName,customerName, attribute, accountId, bankName) {
            //加载选择客户的id，name，attribute
            var customerNameAndBank;
            if (bankName != "") {
                customerNameAndBank = customerName + "(开户行：" + bankName + ")"
            } else {
                customerNameAndBank = customerName;
            }
            $("#customerName" + token).val(customerNameAndBank);
            $('#loginName'+token).val(loginName);
            $("#attribute" + token).val(attribute);
            $("#customerId" + token).val(customerId);
            $("#accountId" + token).val(accountId);
        }
    };
}
