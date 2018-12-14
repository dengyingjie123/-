/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/10/14
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
var CustomerPersonalClass = function (token) {
    var remark = 0;//客户分配时标志为个人客户
    //var customerId = null;
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询条件
        initCustomerPersonalSearch();

        // 初始化查询事件
        onClickCustomerPersonalSearch();
        // 初始化查询重置事件
        onClickCustomerPersonalSearchReset();

        // 初始化表格
        initTableCustomerPersonalTable();

    }

    function initCustomerPersonalSearch() {}

    /**
     * 初始化表格
     * * 修改:姚章鹏
     * 内容：客户分配审核的列自适应，默认赋予15条记录，各个表都统一15、30、60，弹窗为 10、15、20
     * 时间：2015年6月18日09:17:53
     *
     * JIRA：HOPEWEALTH-1276
     * 内容：增加关键信息的掩盖，如果该客户的销售人员不是登录用户自己，则以"138****1234"（保留前三后四）掩盖电话号码，地址等信息。
     * 时间：2016年03月16日
     */
    function initTableCustomerPersonalTable() {
        var strTableId = 'CustomerPersonalTable' + token;
        var url = WEB_ROOT + "/customer/CustomerPersonal_list.action";
        $('#' + strTableId).datagrid({
            title: '个人客户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            rownumbers: true,
            singleSelect: false,
            pageList: [10, 30, 60],
            pageSize: 10,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            onSortColumn: function (sort, orders) {
            },
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
                    { field: 'personalNumber', title: '客户号'},
                    { field: 'name', title: '姓名', sortable: true},
                    { field: 'loginName', title: '用户名', sortable: true,
                        //HOPEWEALTH-1276
                        formatter: function(value, row, index) {
                            if (row['loginName'] == "") {
                                return row['loginName'];
                            }
                            if (row['saleManId'] != loginUser.getId()) {
                                return "***";

                            }
                            return row['loginName'];
                        }},
                    { field: 'mobile', title: '移动电话', sortable: true},
                    { field: 'customerTypeName', title: '类别'},
                    { field: 'sex', title: '性别',
                        formatter: function(value,row,index) {
                            if (row['sex'] == '0') {
                                return '女';
                            }
                            if (row['sex'] == '1') {
                                return '男';
                            }
                        }
                    },
                    { field: 'birthday', title: '出生日期'},
                    { field: 'groupName', title: '负责组', sortable: true},
                    { field: 'saleManName', title: '负责销售', sortable: true},
                    { field: 'referralCode', title: '推荐码'},
                    { field: 'distributionStatus', title: '分配状态',
                        formatter: function(value,row,index) {
                            if (row['distributionStatus'] == 0) {
                                return '未审核';
                            } if (row['distributionStatus'] == 1) {
                                return '通过';
                            } else if (row['distributionStatus'] == 2) {
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
                    { field: 'sid', title: '序号', hidden: true},
                    { field: 'id', title: '编号', hidden: true},
                    { field: 'saleManId', title: '负责编号', hidden: true, sortable: true},
                    { field: 'identityCardAddress', title: '身份证地址', sortable: true,
                        //HOPEWEALTH-1276
                        formatter: function(value, row, index) {
                            if (row['identityCardAddress'] == "") {
                                return row['identityCardAddress'];
                            }
                            if (row['saleManId'] != loginUser.getId()) {
                                return "***";
                            }
                            return row['identityCardAddress'];
                        }
                    },
                    { field: 'workAddress', title: '工作地址', sortable: true},
                    { field: 'homeAddress', title: '家庭地址', sortable: true},
                    { field: 'phone', title: '固定电话',
                        //HOPEWEALTH-1276
                        formatter: function(value, row, index) {
                            if (row['phone'] == "") {
                                return row['phone'];
                            }
                            if (row['saleManId'] != loginUser.getId()) {
                                return "***";
                            }
                            return row['phone'];
                        }
                    },
                    { field: 'createTime', title: '创建时间'},
                    { field: 'remark', title: '备注'},
                    { field: 'customerChannelTypeId', title: '渠道种类',
                        formatter: function(value,row,index) {
                            if (row['customerChannelTypeId'] == '0') {
                                return '个人客户';
                            } if (row['customerChannelTypeId'] == '1') {
                                return '渠道客户';
                            }
                        }
                    },
                    { field: 'customerCatalogId', title: '客户分类',
                        formatter: function(value,row,index) {
                            if (row['customerCatalogId'] == '0') {
                                return '未确认客户';
                            } if (row['customerCatalogId'] == '1') {
                                return '已确认客户';
                            }
                        }
                    }
                ]
            ],
            onLoadSuccess: function () {
                onClickCustomerPersonalAdd();
                onClickCustomerPersonalDelete();
                onClickCustomerPersonalEdit();
                // 初始化客户分配事件
                onClickCustomerDistribution();
                onClickPassword();
                onClickSendSms();
                // Excel导出
                onClickCustomerPersonalExport();
                onClickAllinpayCircleQueryCashShare();
                // HOPEWEALTH-1276 呼叫电话事件
                //onClickCustomerPersonalDialNew();

            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowCustomerPersonalWindow(data, obj1, customerId) {

        data["personal.OperatorId"] = loginUser.getId();
        var isMyCustomer = true;
        if (data["saleManId"] != loginUser.getId()) {
            isMyCustomer = false;
        }

        var url = WEB_ROOT + "/modules/customer/CustomerPersonal_Save.jsp?token=" + token;
        var windowId = "CustomerPersonalWindow" + token;
        fw.window(windowId, '个人客户详细信息', 1000, 575, url, function () {
            fw.getComboTreeFromKV('sex' + token, 'Sex', 'V', fw.getMemberValue(data, 'personal.sex'));
            fw.getComboTreeFromKV('customerSourceId' + token, 'CustomerSource', 'V', fw.getMemberValue(data, 'personal.customerSourceId'));
            fw.getComboTreeFromKV('customerTypeId' + token, 'CustomerType', 'V', fw.getMemberValue(data, 'personal.customerTypeId'));
            fw.getComboTreeFromKV('customerCatalogId' + token, 'CustomerCatalog', 'V', fw.getMemberValue(data, 'personal.customerCatalogId'));
            fw.getComboTreeFromKV('customerChannelTypeId' + token, 'CustomerChannelType', 'V', fw.getMemberValue(data, 'personal.customerChannelTypeId'));
            fw.getComboTreeFromKV('relationshipLevelId' + token, 'RelationshipLevel', 'V', fw.getMemberValue(data, 'personal.relationshipLevelId'));
            fw.getComboTreeFromKV('creditRateId' + token, 'CreditRate', 'V', fw.getMemberValue(data, 'personal.creditRateId'));
            fw.getComboTreeFromKV('careerId' + token, 'Career', 'V', fw.getMemberValue(data, 'personal.careerId'));

            // 初始化表单提交事件
            onClickCustomerPersonalSubmit();
            // 初始化拨打事件
            onClickCustomerPersonalDial();

            // initCallCenter();

            //HOPEWEALTH-1276
            if (!isMyCustomer) {
                //TODO
                //mask sensitive info
                //CustomerPersonal_Save.jsp
                //mobile, mobile2, phone, phone2, homeAddress, identityCardAddress
                //TODO
            }

            //开户行
            using(SCRIPTS_ROOT + '/customer/CustomerAccountClass.js', function () {
                var accunt = new CustomerAccountClass(token);
                accunt.initModule(customerId, null);
            });
            //证件
            using(SCRIPTS_ROOT + '/customer/CustomerCertificateClass.js', function () {

                var certificate = new CustomerCertificateClass(token);
                certificate.initModule(customerId, null);
            });
            // 回访日志
            using(SCRIPTS_ROOT + '/customer/CustomerFeedbackClass_Tab.js', function () {
                var feedback = new CustomerFeedbackClass_Tab(token);
                feedback.initModule(customerId, name);
            });

            // 初始化产品信息列表
            listProductionTable(customerId);

            // 加载数据
            fw.formLoad('formCustomerPersonal' + token, data);

            if (obj1 == 1) {
                $("#btnCustomerPersonalDialNew" + token).remove();
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

    /**
     * 初始化产品列表
     * @param customerId
     */
    function listProductionTable(customerId) {
        var strTableId = "CustomerProductionTable" + token;
        var url = WEB_ROOT + "/customer/CustomerProduction_list.action";
        $('#' + strTableId).datagrid({
            url: url,
            queryParams: {
                customerId: customerId,
                customerRemark: remark
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
                    { field: 'customerId', title: 'id', hidden: true},
                    { field: 'orderNum', title: '订单编号'},
                    { field: 'customerName', title: '客户姓名'},
                    { field: 'name', title: '产品名称'},
                    { field: 'projectName', title: '所属项目', hidden: true},
                    { field: 'productCompositionName', title: '产品规模', hidden: true},
                    { field: 'payTime', title: '认购时间'},
                    { field: 'money', title: '金额',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(row['money'])
                        }
                    },
                    { field: 'moneyStatusName', title: '状态'},
                    { field: 'originSalesman', title: '销售人员', hidden: true}
                ]
            ],
            toolbar: [
                //{
                //    id: 'btnShowDetail4Production' + token,
                //    text: '查看详情',
                //    iconCls: 'icon-search'
                //}
            ],
            onLoadSuccess: function () {
                // 初始化事件
                //onClickShowDetail4Production();
            }
        });
    }

    function onClickShowDetail4Production() {
        var buttonId = "btnShowDetail4Production" + token;
        fw.bindOnClick(buttonId, function () {
            fw.datagridGetSelected('CustomerProductionTable' + token, function (selected) {
                var productionId = selected.id;
//                alert(selected.projectId);
                var url = WEB_ROOT + "/customer/CustomerProduction_list.action?CustomerProduction.productionId=" + productionId;
                fw.post(url, null, function (data) {
                    data["production.projectName"] = selected.projectName;
                    data["production.name"] = selected.name;
                    data["production.size"] = selected.size;
                    data["production.startTime"] = selected.startTime;
                    data["production.stopTime"] = selected.stopTime;
                    data["production.valueDate"] = selected.valueDate;
                    data["production.expiringDate"] = selected.expiringDate;
                    data["production.interestDate"] = selected.interestDate;
                    data["production.appointmentMoney"] = selected.appointmentMoney;
                    data["production.saleMoney"] = selected.saleMoney;
                    data["production.status"] = selected.status;
                    data["production.productId"] = selected.productId;
                    data["production.paymentMoney"] = selected.paymentMoney;
                    data["production.paymentProfitMoney"] = selected.paymentProfitMoney;
                    initWindowProductionDetail(data);
                });
            })

        });
    }

    function openWindowPatchSendSms(data) {
        //alert(data["sms.id"]);
        var url = WEB_ROOT + "/modules/customer/CustomerPatchSendSms.jsp?token=" + token;
        var windowId = "CustomerSmsSendingWindow" + token;
        fw.window(windowId, "信息编辑", 800, 455, url, function () {
            onClickPatchSendSms();
            fw.formLoad("formCustomerSmsSending" + token, data);

        });
    }

    function initWindowProductionDetail(data) {

        var url = WEB_ROOT + "/modules/production/Production_Search.jsp?token=" + token;
        var windowId = "ProductionDetailWindow" + token;
        fw.window(windowId, '产品详细信息', 980, 230, url, function () {
            fw.formLoad('formProductionDetail' + token, data);
        }, null);
    }


    /**
     * 初始化密码窗口
     * @param data
     */
    function initPasswordWindow(data) {

        data["personal.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/customer/CustomerPersonal_Password.jsp?token=" + token;
        var windowId = "PasswordWindow" + token;
        fw.window(windowId, '个人客户密码管理', 400, 300, url, function () {

            // 初始化表单提交事件
            onClickPasswordSubmit();

            // 加载数据
            fw.formLoad('formPassword' + token, data);
            $("#password" + token).val("");
            $("#password2" + token).val("");

        }, null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

//    /**
//     * 呼叫电话事件
//     * HOPEWEALTH-1276
//     * 修正
//     */
//    function onClickCustomerPersonalDialNew() {
//        var buttonId = "btnCustomerPersonalDial" + token;
//
//        fw.bindOnClick(buttonId, function (process) {
//            fw.datagridGetSelected('CustomerPersonalTable' + token, function (selected) {
//                process.beforeClick();
//                // 要拨打的电话
//                var phoneNumber = selected.mobile;
//                alert(buttonId + ", phone = " + phoneNumber);
//                if (phoneNumber == null || phoneNumber == "") {
//                    fw.alert("电话为空", "该客户电话为空！");
//                    process.afterClick();
//                    return;
//                }
//                var loginName = "8001@bhcf";
//                var password = "8001";
//;
//                // 拨打
//
//                var softphoneBar = null;
//                var phone = null;
//                var monitor = null;
//                var monitorTimers = [];
//                var monitoring = null;
//
//                hojo.registerModulePath("icallcenter", "../js/icallcenter");
//                hojo.require("icallcenter.logon");
//                hojo.require("hojo.io.script");
//                icallcenter.logon.startLogon(loginName, password);
//            });
//        });
//    }

    /**
     * 呼叫电话事件
     * HOPEWEALTH-1276
     */
    function onClickCustomerPersonalDial() {
        var buttonId = "btnCustomerPersonalDialNew" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('CustomerPersonalTable' + token, function (selected) {
                process.beforeClick();

                var phoneNumber = fw.getTextValue("mobileNotMasked"+token);
                if (phoneNumber == null || phoneNumber == "") {
                    fw.alert("电话为空", "该客户电话为空！");
                    process.afterClick();
                    return;
                }
                var dialLoginName = "8001@hbcf";
                var dialPassword = "4008001";
                var url = WEB_ROOT;
                url += "/include/framework/7moor/edb_bar/phoneBar/phonebarWithNumber.html?loginName=";
                url += dialLoginName;
                url += "&password=";
                url += dialPassword;
                url += "&loginType=Local";
                url += "&phoneNumber=" + phoneNumber;
                window.open(url);

                // fw.window("ccMain" + token, "呼叫中心", 250, 250, url, null, null);

                ////确定拨打电话，输入座席账户和密码
                //var url = WEB_ROOT + "/modules/callcenter/CallCenter_Dial.jsp?token=" + token + "&phoneNumber=" + phoneNumber;
                //url += "&hidden=true";//需隐藏电话
                //var windowId = "DialWindow" + token;
                //fw.window(windowId, '拨打', 350, 200, url, function () {
                //    //绑定按钮btnDialSubmit事件
                //    onClickCustomerPersonalDialSubmit();
                //}, null);

                process.afterClick();
                //跳转页面
                //http://localhost:8080/core/include/framework/7moor/edb_bar/login.html
                //有效的账号和密码
                //http://localhost:8080/core/include/framework/7moor/edb_bar/phoneBar/phonebar.html?loginName=8001@hbcf&password=8001&loginType=Local
            });
        });
    }

    /**
     * 确认拨打事件
     * HOPEWEALTH-1276
     */
    function onClickCustomerPersonalDialSubmit() {
        var buttonId = "btnDialSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var phoneNumber = $("#phoneNumber" + token).val();
            var loginName = $("#loginName" + token).val();
            var password = $("#password" + token).val();

            var url = WEB_ROOT;
            url += "/include/framework/7moor/edb_bar/phoneBar/phonebarWithNumber.html?loginName=";
            url += loginName;
            url += "&password=";
            url += password;
            url += "&loginType=Local";
            url += "&phoneNumber=" + phoneNumber;
            window.open(url);
            //var windowId = "DialWindow" + token;
            //fw.windowClose(windowId);
        });
    }



    /**
     * 添加事件
     */
    function onClickCustomerPersonalAdd() {

        var buttonId = "btnCustomerPersonalAdd" + token;
        fw.bindOnClick(buttonId, function (process) {

            // 打开窗口，初始化表单数据为空
            initWindowCustomerPersonalWindow({}, 1, null);
        });

    }

    /**
     * 删除事件
     */
    function onClickCustomerPersonalDelete() {
        var buttonId = "btnCustomerPersonalDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('CustomerPersonalTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/customer/CustomerPersonal_delete.action?personal.id=" + selected.id;
                    //alert(url);
                    fw.post(url, null, function (data) {
                        //fw.alertReturnValue(data);
                        fw.datagridReload('CustomerPersonalTable' + token);
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickCustomerPersonalEdit() {
        var butttonId = "btnCustomerPersonalEdit" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('CustomerPersonalTable' + token, function (selected) {
                //HOPEWEALTH-1276
                //alert("loginUser id = " + loginUser.getId() + "\n" +
                //      "saleman id = " +  selected.saleManId);
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerPersonal_load.action?personal.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data["bizid"] = selected.id;
                    data["saleManId"] = selected.saleManId;//HOPEWEALTH-1276
                    initWindowCustomerPersonalWindow(data, null, id);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }


    /**
     * 短信发送事件
     */
    function onClickSendSms() {
        var buttonId = "btnSmsSending" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('CustomerPersonalTable' + token, function (selections) {
                process.beforeClick();
                var length = selections.length;
                var receiverNames = "";
                var receiverIds = "";

                for (var i = 0; i < length; i++) {
                    receiverNames += selections[i].name + "(" + selections[i].mobile + "),";
                    receiverIds += selections[i].id + ",";
                }
                var waitingTime = fw.getTimeNow();
                receiverNames = fw.removeLastLetters(receiverNames, ",");
                receiverIds = fw.removeLastLetters(receiverIds, ",");
                var data = {"sms.receiverIds": receiverIds, "sms.receiverName": receiverNames, "sms.waitingTime": waitingTime};
                openWindowPatchSendSms(data);
                process.afterClick();
            });


        });
    }


    /**
     * @description 客户分配管理
     * 批量分配客户，通过ids来分配
     * @author 胡超怡
     *
     * @date 2018/11/27 16:18
     */
    function onClickCustomerDistribution() {

        var buttonId = "btnCustomerDistribution" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("提醒", "是否进行客户分配", function () {

                fw.datagridGetSelections('CustomerPersonalTable' + token, function (selections) {

                    process.beforeClick();
                    var url = WEB_ROOT + '/customer/CustomerDistribution_load.action';
                    var params = {"customers": JSON.stringify(selections)}


                    /**
                     * @description
                     * 获得客户的所有数据的json
                     * @author 胡超怡
                     *
                     * @date 2018/12/12 17:40
                     * @throws Exception
                     */
                    fw.post(url, params, function (data) {
                        using(SCRIPTS_ROOT + '/sale/CustomerSaleClass.js', function () {

                            var customerSaleClass = new CustomerSaleClass(token, null, remark, data);
                            customerSaleClass.initModule();
                            process.afterClick();

                        }, function () {
                            process.afterClick();
                        });
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    }, null)

                });
            });
        });
    }


    /**
     * 密码管理
     */
    function onClickPassword() {
        var buttonId = "btnPassword" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('CustomerPersonalTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerPersonal_load.action?personal.id=" + id;
                fw.post(url, null, function (data) {
//                    fw.alertReturnValue(data);
                    initPasswordWindow(data);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 查询事件
     */
    function onClickCustomerPersonalSearch() {
        var buttonId = "btnSearchCustomerPersonal" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "CustomerPersonalTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["personalVO.loginName"] = $("#search_LoginName" + token).val();
            params["personalVO.name"] = $("#search_Name" + token).val();
            params["personalVO.mobile"] = $("#search_Mobile" + token).val();
            params["personalVO.workAddress"] = $("#search_WorkAddress" + token).val();
            params["personalVO.groupName"] = $("#search_GroupName" + token).val();
            params["personalVO.saleManName"] = $("#search_SaleManName" + token).val();
            $('#' + strTableId).datagrid('load');//加载第一页的行
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
            $('#search_GroupName' + token).val('');
            $('#search_SaleManName' + token).val('');
        });
    }


    function onClickAllinpayCircleQueryCashShare() {
        var btnId = 'btnAllinpayCircleQueryCashShare' + token;

        fw.bindOnClick(btnId, function (process) {
            process.beforeClick();

            try {

                fw.datagridGetSelected('CustomerPersonalTable' + token, function (selected) {
                    using(SCRIPTS_ROOT + '/allinpayCircle/AllinpayCircleQueryCashShareClass.js', function () {
                        //alert("hello");
                        var allinpayCircleQueryCashShareClass = new AllinpayCircleQueryCashShareClass(token);
                        allinpayCircleQueryCashShareClass.openWindow(selected.id);
                    });
                });


            }
            catch (e) {

            }

            process.afterClick();
        });

    }

    /**
     * 数据提交事件
     */
    function onClickCustomerPersonalSubmit() {
        var buttonId = "btnCustomerPersonalSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formCustomerPersonal" + token;
            var url = WEB_ROOT + "/customer/CustomerPersonal_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                //alert('done');
                process.afterClick();
                fw.datagridReload("CustomerPersonalTable" + token);
                fw.windowClose('CustomerPersonalWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 密码保存事件
     */
    function onClickPasswordSubmit() {
        var buttonId = "btnPasswordSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var sid = $("#sid" + token).val();
            var password = $("#beforePassword" + token).val();
            var successful;
            var url = WEB_ROOT + "/customer/CustomerPersonal_checkPassword.action?personal.sid=" + sid + "&personal.password=" + password;
            $.post(url, null, function (data) {
                var json = "[" + data + "]";
                var jsonArray = eval('(' + json + ')');
//                fw.alertReturnValue(data)
                successful = jsonArray[0].returnValue[0].successful;
                if (successful == "1") {
                    var formId = "formPassword" + token;
                    var url = WEB_ROOT + "/customer/CustomerPersonal_passwordUpdate.action";
                    fw.bindOnSubmitForm(formId, url, function () {
                        process.beforeClick();
                    }, function () {
                        //alert('done');
                        process.afterClick();
                        fw.datagridReload("CustomerPersonalTable" + token);
                        fw.windowClose('PasswordWindow' + token);
                    }, function () {
                        process.afterClick();
                    });
                } else {
//                    alert("密码错误");
                    $("#errorMessage" + token).html("原密码错误");
                }

            })

        });
    }

    function onClickPatchSendSms() {
        var buttonId = "btnCustomerFeedbackSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            var url = WEB_ROOT + "/customer/CustomerSms_push.action";
            var formId = "formCustomerSmsSending" + token;
            var params = "";
            var names = $("#receiverName" + token).val().split(",");
            var receiverId = $("#receiverIds" + token).val().split(",");
            var waitingTime = $("#waitingTime" + token).val();
            var subject = encodeURI(encodeURI($("#subject" + token).val()));
            var content = encodeURI(encodeURI($("#content" + token).val()));
            var receiverNames = new Array();
            var receiverMobiles = new Array();
            var receiverIds = new Array();
            for (var i = 0; i < names.length; i++) {
                receiverNames[i] = names[i].substr(0, names[i].lastIndexOf('('));
                receiverMobiles[i] = names[i].substr(names[i].indexOf('(') + 1, 11);
                receiverIds[i] = receiverId[i];
                params += "smsPOs[" + i + "].receiverId=" + receiverIds[i] + "&smsPOs[" + i + "].receiverName=" + encodeURI(encodeURI(receiverNames[i])) + "&smsPOs[" + i + "].receiverMobile=" + receiverMobiles[i] +
                    "&smsPOs[" + i + "].waitingTime=" + waitingTime + "&smsPOs[" + i + "].subject=" + subject + "&smsPOs[" + i + "].content=" + content + "&";
            }

            fw.post(url, params, function () {
                fw.windowClose('CustomerSmsSendingWindow' + token);
            }, null);
        });
    }

    /**
     * 导出事件
     */
    function onClickCustomerPersonalExport() {

        var buttonId = "btnCustomerPersonalExport" + token;
        fw.bindOnClick(buttonId, function (process) {

            var strTableId = 'CustomerPersonalTable' + token;
            var datagrid = $('#'+strTableId+'');  //获取datagrid对象

            //获取固定列表头信息
            var header = datagrid.datagrid('options').frozenColumns[0];
            var fields = "";
            var titles = "";
            for(var i = 0;i<header.length;i++){
                var field = header[i].field;
                var title = header[i].title;
                var hiddenFlag = header[i].hidden;
                if(!hiddenFlag && field!="ck"){
                    fields = fields + field + ",";
                    titles = titles + title + ",";
                }
            }

            //获取表头信息
            var header = datagrid.datagrid('options').columns[0];
            for(var i = 0;i<header.length;i++){
                var field = header[i].field;
                var title = header[i].title;
                var hiddenFlag = header[i].hidden;
                if(!hiddenFlag && field!="ck"){
                    if( i  == header.length-1)
                    {
                        fields = fields + field;
                        titles = titles + title;
                    }else{
                        fields = fields + field + ",";
                        titles = titles + title + ",";
                    }
                }
            }

            //向后台发送请求
            //var url = WEB_ROOT + "/customer/CustomerPersonal_export.action?personalVO.loginName="+ $("#search_LoginName" + token).val()+
            //    "&personalVO.name="+$("#search_Name" + token).val()+"&personalVO.mobile="+$("#search_Mobile" + token).val()+
            //    "&personalVO.workAddress="+$("#search_WorkAddress" + token).val();
            var url = WEB_ROOT + "/customer/CustomerPersonal_export.action";

            var form = $("<form>");//定义一个form表单
            form.attr('style','display:none');
            form.attr('target','');
            form.attr('method','post');
            form.attr('action',url);
            //添加input
            var input1 = $("<input>");
            input1.attr('type','hidden');
            input1.attr('name','fields');
            input1.attr('value',fields);

            var input2 = $("<input>");
            input2.attr('type','hidden');
            input2.attr('name','titles');
            input2.attr('value',titles);

            var input3 = $("<input>");
            input3.attr('type','hidden');
            input3.attr('name','personalVO.loginName');
            input3.attr('value',$("#search_LoginName" + token).val());

            var input4 = $("<input>");
            input4.attr('type','hidden');
            input4.attr('name','personalVO.name');
            input4.attr('value',$("#search_Name" + token).val());

            var input5 = $("<input>");
            input5.attr('type','hidden');
            input5.attr('name','personalVO.mobile');
            input5.attr('value',$("#search_Mobile" + token).val());

            var input6 = $("<input>");
            input6.attr('type','hidden');
            input6.attr('name','personalVO.workAddress');
            input6.attr('value',$("#search_WorkAddress" + token).val());

            //将表单放到body中
            $('body').append(form);
            form.append(input1);
            form.append(input2);
            form.append(input3);
            form.append(input4);
            form.append(input5);
            form.append(input6);
            form.submit();//提交表单

        });
    }

    function initCallCenter() {

        hojo.registerModulePath("icallcenter", "../../include/framework/7moor/edb_bar/js/icallcenter");
        hojo.require("icallcenter.logon");
        hojo.require("hojo.io.script");

        hojo.addOnLoad(function () {
            var loginName = "8001@hbcf";
            var password = "8001";
            var extenType = "Local";


            icallcenter.logon.startLogon(loginName, password, extenType);
        });

        hojo.addOnWindowUnload(function (){
            if(phone) {
                //phone.destroy(true);
            }
        });

        softphoneBar.dialout(hojo.byId('icallcenter.dialout.input').value)
    }


    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        setCustomerId: function (id) {
            this.customerId = id;
        },
        setSalemanId: function (salemanId) {
            this.saleManId = salemanId;
        },
        getSalemanId: function () {
            return this.saleManId;
        },
        getCustomerId: function () {
            return this.customerId;
        },
        getRemark: function () {
            return remark;
        },
        initTable: function () {
            initTableCustomerPersonalTable();
        }
    };
}
