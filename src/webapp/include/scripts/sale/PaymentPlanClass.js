/**
 * 兑付计划管理类
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var PaymentPlanClass = function (token, permissionName) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化列表状态选择查询。
        initFormPaymentPlanSearch();
        if (permissionName == '兑付审核') {
            // 初始化表格
            initTablePaymentPlanTable("");
            $("#window" + token).remove();
        } else {
            // 初始化表格
            initTablePaymentPlanTable2();

        }

        onClickPaymentPlanSearch();
    }

    //初始化兑付状态
    function initFormPaymentPlanSearch() {
        fw.getComboTreeFromKV('search_status' + token, 'Sale_PaymentPlan_Status', 'k', '-2');
    }

    function initPaymentPlanDetailTable(orderId,confirmorId) {

        var url = WEB_ROOT + "/sale/PaymentPlan_listPaymentPlanVOByOrderId?orderId="+orderId+"&confirmorId="+confirmorId;
        var strTableId = 'PaymentPlanDetailTable' + token;
        $('#' + strTableId).datagrid({
            title: '兑付计划',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            rownumbers: true,
            singleSelect: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            showFooter:true,
            onSortColumn: function (sort, orders) {
            },
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);
                    //fw.alertReturnValue(data);
                    return data;
                }
                catch (e) {
                }
            },
            pagination: false,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [

                    { field: 'sid', title: '序号',hidden:true},
                    { field: 'id', title: '编号',hidden:true},
                    { field: 'type', title: '类型'},
                    { field: 'paymentTime', title: '兑付时间'},
                    { field: 'totalInstallment', title: '总期数'},
                    { field: 'currentInstallment', title: '当前期数',sortable:true},
                    { field: 'totalPaymentPrincipalMoney', title: '兑付本金',
                        formatter: function(value,row,index){
                        return fw.formatMoney(value);
                    }},
                    { field: 'totalProfitMoney', title: '兑付收益',
                        formatter: function(value,row,index){
                        return fw.formatMoney(value);
                    }},
                    { field: 'totalPaymentMoney', title: '兑付总金额',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(value);
                        }
                    },
                    { field: 'paiedPrincipalMoney', title: '已兑付本金',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(value);
                        }
                    },
                    { field: 'paiedProfitMoney', title: '已兑付收益',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(value);
                        }
                    },
                    { field: 'paiedPaymentTime', title: '兑付时间'},
                    { field: 'confirmorName', title: '确认人'},
                    { field: 'confirmTime', title: '确认时间'}
                ]
            ],
            toolbar: [{
                id:'btnPaymentPlanEdit'+token,
                iconCls: 'icon-edit',
                text:'修改'
            },{
                id:'btnDoPaymentPlanDone'+token,
                iconCls: 'icon-edit',
                text:'兑付'
            }],
            onLoadSuccess: function () {
                onClickPaymentPlanEdit();
                onClickDoPaymentPlanDoneSubmit();
            }
        });
    }

    function initPaymentPlanDetailWindow(orderId, confirmorId) {
        var windowId = "PaymentPlanWindowDetail" + token;
        var url = WEB_ROOT + "/modules/sale/PaymentPlan_Detail.jsp?orderId="+orderId+"&token="+token;

        fw.window(windowId,'兑付计划详情', 950, 400, url, function(){
            initPaymentPlanDetailTable(orderId, confirmorId);

            onClickPaymentPlanSubmit();
        }, null);
    }

    /**
     * 附件所示
     */
    function initTablePaymentPlanTable2() {
        var strTableId = 'PaymentPlanGuanLiTable2' + token;
        var url = WEB_ROOT + "/sale/PaymentPlan_getList2.action";

        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '兑付计划',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,//行是否适应屏幕
            singleSelect: false,//是否只能选择一行
            pageList: [40],//设置每页显示多少条
            pageSize: 40,//初始化每页显示多少
            rownumbers: true,//是否显示行号
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            showFooter:true,
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
                    {field: 'ck', checkbox: true},
                    {field: 'paymentTime', title: '兑付日期'},
                    {field: 'productionName', title: '产品名称', hidden:true},
                    {field: 'productId', title: '产品编号', hidden: true}
                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    {field: 'totalRecord', title: '总兑付条数'},
                    {field: 'totalPaymentPrincipalMoney', title: '预期兑付本金',
                        formatter:function(value,row,index){
                            return fw.formatMoney(row['totalPaymentPrincipalMoney'])
                        }
                    },
                    {field: 'totalProfitMoney', title: '预期兑付收益',
                        formatter:function(value,row,index){
                            return fw.formatMoney(row['totalProfitMoney'])
                        }
                    },
                    {field: 'paiedPrincipalMoney', title: '实际兑付本金',
                        formatter:function(value,row,index){
                            return fw.formatMoney(row['paiedPrincipalMoney'])
                        }
                    },
                    {field: 'paiedProfitMoney', title: '实际兑付收益',
                        formatter:function(value,row,index){
                            return fw.formatMoney(row['paiedProfitMoney'])
                        }
                    },
                    {field: 'checkName', title: '申请人'},
                    {field: 'checkTime', title: '申请时间'},
                    {field: 'checkName2', title: '审核人'},
                    {field: 'checkTime2', title: '审核时间'}
                ]
            ],
            //设置按钮的文字与图片
            toolbar: [
                {
                    id: 'btnPaymentPlanDetail' + token,
                    text: '查询明细',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnPaymentPlanCheck' + token,
                    text: '提交整月兑付计划申请',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnPaymentPlanCheck2' + token,
                    text: '审核整月兑付计划',
                    iconCls: 'icon-edit'
                }
            ],
            //加载成后做的事
            onLoadSuccess: function () {
                //查看明细
                onClickPaymentPlanDetail();

                onClickPaymentPlanCheck();
                onClickPaymentPlanCheck2();
            }
        });

    }


    function onClickPaymentPlanCheck(){
        var buttonId = "btnPaymentPlanCheck" + token;

        // alert($("#"+buttonId).length);

        fw.bindOnClick(buttonId, function(process){
            process.beforeClick();


            fw.datagridGetSelections('PaymentPlanGuanLiTable2'+token, function(selections){

                var paymentPlanDate = fw.removeLastLettersCount(selections[0]["paymentTime"],3);

                fw.confirm("提交申请", "是否提交【 "+paymentPlanDate+" 】的兑付计划申请？", function(){

                    var url = WEB_ROOT + "/sale/PaymentPlan_check?paymentPlanDate=" + paymentPlanDate;

                    fw.post(url, null, function(data){
                        if (data == "1") {
                            fw.alert('提示', '操作成功');
                            fw.datagridReload('PaymentPlanGuanLiTable2'+token);
                        }
                    },null);

                },null);
            });



            process.afterClick();
        })
    }

    function onClickPaymentPlanCheck2(){
        var buttonId = "btnPaymentPlanCheck2" + token;

        fw.bindOnClick(buttonId, function(process){
            process.beforeClick();

            fw.datagridGetSelections('PaymentPlanGuanLiTable2'+token, function(selections){

                var paymentPlanDate = fw.removeLastLettersCount(selections[0]["paymentTime"],3);

                fw.confirm("提交申请", "是否通过【 "+paymentPlanDate+" 】的兑付计划申请？", function(){

                    var url = WEB_ROOT + "/sale/PaymentPlan_check2?paymentPlanDate=" + paymentPlanDate;

                    fw.post(url, null, function(data){
                        if (data == "1") {
                            fw.alert('提示', '操作成功');
                            fw.datagridReload('PaymentPlanGuanLiTable2'+token);
                        }
                    },null);

                },null);
            });

            process.afterClick();
        })
    }

    function onClickPaymentPlanDetail() {
        var buttonId = "btnPaymentPlanDetail" + token;
        fw.bindOnClick(buttonId, function (process) {

            process.beforeClick();
            fw.datagridGetSelected('PaymentPlanGuanLiTable2' + token, function (selected) {
                var url = WEB_ROOT + "/modules/sale/PaymentPlan_Main2.jsp?token=" + token;
                var windowId = "PaymentPlanWindowDetail" + token;
                //弹出窗口将数据带到指定jsp页面
                fw.window(windowId, '兑付明细', 1000, 500, url, function (selected) {
                    initTablePaymentPlanTable(selected);
                    // 初始化查询事件
                    onClickPaymentPlanSearch();
                    // 初始化查询重置事件
                    initFormPaymentPlanSearch();
                },function(){}, selected);
                process.afterClick();
            })

        });
    }

    /**
     * 初始化表格
     */
    function initTablePaymentPlanTable(selected) {
        var strTableId = 'PaymentPlanGuanLiTable' + token;//表格ID

        //访问的Action，如果菜单权限是兑付审核，则请求兑付审核的 list，其他都是正常的 list
        if (permissionName == '兑付审核') {
            var url = WEB_ROOT + "/sale/PaymentPlan_list4AuditSubmitted.action";
        } else {
            var url = WEB_ROOT + "/sale/PaymentPlan_list.action";
        }

        var productId = selected.productId;
        var paymentTime = selected.paymentTime;
        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '兑付计划',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
                "paymentPlanVO.productId":productId,"paymentPlanVO.paymentTime":paymentTime
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,//行是否适应屏幕
            singleSelect: true,//是否只能选择一行
            pageList: [40],//设置每页显示多少条
            pageSize: 40,//初始化每页显示多少
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
                    {field: 'ck', checkbox: true},
                    {field: 'loginName', title: '用户名', hidden:true},
                    {field: 'customerName', title: '客户姓名'},
                    {field: 'productionName', title: '产品名称'}
                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'type', title: '兑付类型'},
                    {field: 'orderName', title: '订单名称'},
                    {field: 'money', title: '订单金额',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(row['money'])
                        }
                    },
                    {field: 'bankName', title: '银行'},
                    {field: 'bankNumber', title: '银行账号'},
                    {field: 'bankBranchName', title: '开户行'},
                    //{ field: 'paymentMoney', title: '兑付金额'},
                    {field: 'paymentTime', title: '兑付日期'},
                    {field: 'totalInstallment', title: '兑付总期数'},
                    {field: 'currentInstallment', title: '当前兑付期数'},
                    {field: 'statusName', title: '兑付状态'},
                    {field: 'paiedPaymentTime', title: '已兑付时间'},
                    {field: 'totalPaymentMoney', title: '应兑付总金额'},
                    {field: 'totalPaymentPrincipalMoney', title: '应兑付本金总金额'},
                    {field: 'totalProfitMoney', title: '应兑付收益总金额'},
                    {field: 'paiedPrincipalMoney', title: '已兑付本金金额'},
                    {field: 'paiedProfitMoney', title: '已兑付收益金额'},
                    {field: 'confirmorName', title: '核对人'},
                    {field: 'auditExecutorId', title: '审核人',hidden:true},
                    {field: 'comment', title: '备注'}

                ]
            ],
            //加载成后做的事
            onLoadSuccess: function () {
                onClickPaymentPlanEdit();
                onClickPaymentPlanPay();
                //审批
                onClickPaymentPlanCheck();
                //提交审核
                onClickPaymentPlanRequestAudit();
                // 执行审核
                onClickPaymentPlanAuditStatusSubmit();

                onClickSendSms();
            }
        });

        //if (permissionName == '兑付审核') {
        //    $('#btnPaymentPlanRequestAudit' + token).hide();
        //    $('#btnPaymentPlanPay' + token).hide();
        //} else {
        //    $('#btnPaymentPlanDoAudit' + token).hide();
        //}

    }


    function initTablePaymentPlanReportMonthTable(paymentTimeMonth) {
        var strTableId = 'PaymentPlanTable' + token;
        var url = WEB_ROOT + "/sale/PaymentPlan_reportMonth.action?paymentTime="+paymentTimeMonth;

        $('#' + strTableId).datagrid({
            title: '产品属性',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            rownumbers: true,
            singleSelect: true,
            remoteSort: false,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            onSortColumn: function (sort, orders) {
            },
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);
                    data = {'rows': data};
                    return data;
                }
                catch (e) {
                }
            },
            pagination: false,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: '序号', hidden: true},
                    { field: 'id', title: '编号', hidden: true},
                    { field: 'customerName', title: '客户姓名'},
                    { field: 'productionName', title: '产品名称'},
                    { field: 'payTime', title: '认购时间'},
                    { field: 'money', title: '认购金额',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(row['money']);
                        }
                    },
                    { field: 'interestDescription', title: '兑付频率及次数'},
                    { field: 'paymentTime', title: '兑付日'},
                    { field: 'weekOfDay', title: '兑付星期'},
                    { field: 'totalPaymentPrincipalMoney', title: '兑付本金',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(row['totalPaymentPrincipalMoney']);
                        }
                    },
                    { field: 'totalProfitMoney', title: '兑付利息',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(row['totalProfitMoney']);
                        }
                    },
                    { field: 'totalPaymentMoney', title: '本息合计',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(row['totalPaymentMoney']);
                        }
                    }
                ]
            ],
            toolbar: [{
                iconCls: 'icon-print', id:'btnExport'+token, text:'导出'
            }],
            onLoadSuccess: function () {
                onClickReportMonthExport();
            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowPaymentPlanWindow(data) {

        data["paymentPlan.operatorId"] = loginUser.getId();
        data["paymentPlan.operatorName"] = loginUser.getName();

        var url = WEB_ROOT + "/modules/sale/PaymentPlan_Save.jsp?token=" + token;
        var windowId = "PaymentPlanWindow" + token;
        //弹出窗口将数据带到指定jsp页面
        fw.window(windowId, '兑付计划明细', 800, 350, url, function () {
            fw.textFormatCurrency('paymentMoney' + token);
            fw.textFormatCurrency('totalPaymentMoney' + token);
            fw.textFormatCurrency('totalPaymentPrincipalMoney' + token);
            fw.textFormatCurrency('totalProfitMoney' + token);
            fw.textFormatCurrency('paiedPrincipalMoney' + token);
            fw.textFormatCurrency('paiedProfitMoney' + token);
            // 加载数据ForPaymentPlan是弹出页面的FORM表单
            // onPaymentPlanSubmit();
            // onSelectedIStatusName(data);

            fw.getComboTreeFromKV('statusName' + token, 'Sale_PaymentPlan_Status', 'k', fw.getMemberValue(data, 'paymentPlan.status'));
            fw.formLoad('formPaymentPlan' + token, data);

            onPaymentPlanSubmit();
        }, null);
    }

    /**
     * 初始化弹出兑付审核窗口
     */
    function initPaymentPlanAuditWindow(recordId) {
        var url = WEB_ROOT + "/modules/sale/PaymentPlanAudit_Save.jsp?token=" + token;
        var windowId = "PaymentPlanAuditStatusWindow" + token;

        fw.window(windowId, '兑付审核', 350, 200, url, function () {

            // 通过 KV 来给下拉菜单赋值
            fw.getComboTreeFromKV('auditStatus' + token, 'Sale_PaymentPlan_Status', 'k', fw.getMemberValue('', 'paymentPlan.status'));

            // 绑定窗口确定事件
            var buttonId = "btnPaymentPlanAuditStatusSubmit" + token;
            fw.bindOnClick(buttonId, function (process) {
                process.beforeClick();

                var status;
                var pass = document.getElementById("pass").checked;
                if (pass) status = 8;
                var notPass = document.getElementById("notPass").checked;
                if (notPass) status = 9;

                var url2AuditExe = WEB_ROOT + "/sale/PaymentPlan_updateAuditExecuteStatus?paymentPlan.id=" + recordId + "&status=" + status;
                fw.post(url2AuditExe, null, function (data) {
                    if (data == 0) {
                        fw.alert('错误', '没有查询到该记录');
                    } else if (data == 1) {
                        fw.alert('错误', '您选择的状态不正确');
                    } else if (data == 2) {
                        fw.alert('错误', '缺少参数');
                    } else {
                        fw.alert('提示', '审核状态修改成功');
                    }
                    fw.datagridReload('PaymentPlanGuanLiTable' + token);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
                fwCloseWindow('PaymentPlanAuditStatusWindow' + token);
            });

        }, null);
    }

///  初始化部分 结束  /////////////////////////////////////////////////////////////////


///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickPaymentPlanAdd() {

        var buttonId = "btnPaymentPlanAdd" + token;

        fw.bindOnClick(buttonId, function (process) {


            // 打开窗口，初始化表单数据为空
            initWindowPaymentPlanWindow({});

            // fw.getComboTreeFromKV('type' + token, "OA_Finance_PaymentPlan_Status", 'k', '-2');
        });

    }

    /**
     * 删除事件
     */
    function onClickPaymentPlanDelete() {
        var buttonId = "btnPaymentPlanDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('MoneyLogGuanLiTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/sale/MoneyLog_delete.action?moneyLog.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('MoneyLogGuanLiTable' + token);
                    }, null);
                }, null);
            });
        });
    }


    /**
     * 修改事件
     */
    function onClickPaymentPlanEdit() {
        var buttonId = "btnPaymentPlanEdit" + token;
        fw.bindOnClick(buttonId, function (process) {

            fw.datagridGetSelected('PaymentPlanDetailTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/sale/PaymentPlan_load.action?paymentPlan.id=" + id;
                fw.post(url, null, function (data) {

                    //从表格获取数据
                    data['loginName'] = selected.loginName;
                    //客户名称
                    // data["paymentPlan.customerId"] = selected.customerName;
                    // //产品名称
                    // data["paymentPlan.productId"] = selected.productName;

                    data["paymentPlan.statusName"] = selected.statusName;

                    // fw.alertReturnValue(data);

                    //弹出窗口
                    initWindowPaymentPlanWindow(data);

                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 审核事件
     */
    function onClickPaymentPlanAuditStatusSubmit() {
        var butttonId = "btnPaymentPlanDoAudit" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanGuanLiTable' + token, function (selected) {
                process.beforeClick();

                var recordId = selected.id;

                // 弹出窗口
                initPaymentPlanAuditWindow(recordId);

                process.afterClick();
            })
        });
    }

    /**
     * 修改事件
     */
    // function onClickPaymentPlanCheck() {
    //     var butttonId = "btnPaymentPlanPayCheck" + token;
    //     fw.bindOnClick(butttonId, function (process) {
    //         fw.datagridGetSelected('PaymentPlanGuanLiTable' + token, function (selected) {
    //             process.beforeClick();
    //             var id = selected.id;
    //             var url = WEB_ROOT + "/sale/PaymentPlan_getPaymentPlanYW.action?paymentPlan.id=" + id;
    //             fw.post(url, null, function (data) {
    //                 data["bizRoute.workflowId"] = 14;
    //                 data["WorkflowID"] = 14;
    //                 data["YWID"] = data["id"];
    //                 data["RouteListID"] = data["routeListId"];
    //                 data["CurrentNode"] = data["currentNodeId"];
    //                 data["serviceClassName"] = "com.youngbook.service.sale.PaymentPlanService";
    //                 using('../wf/script/BizRouteClass.js', function () {
    //                     var bizRoute = new BizRouteClass(token, data, 'PaymentPlanGuanLiTable' + token, "", "", "");
    //                     bizRoute.initModule();
    //                     process.afterClick();
    //                 });
    //             }, function () {
    //                 process.afterClick();
    //             });
    //         })
    //     });
    // }

    function onClickSendSms(){
        var buttonId = "btnSendSms" + token;
        fw.bindOnClick(buttonId, function(){

            fw.datagridGetSelected("PaymentPlanGuanLiTable"+token, function(selectd){
                var url = WEB_ROOT + "/system/Sms_sendSms4PaymentPlan?paymentPlanId="+selectd.id;

                fw.post(url, null, function(data){
                    if (data == "1") {
                        fw.alert("提示","发送成功");
                    }
                }, null);
            });


        });
    }

    /**
     * 兑付事件
     */
    function onClickPaymentPlanPay() {
        var buttonId = "btnPaymentPlanPay" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanGuanLiTable' + token, function (selected) {

                var statusName = selected.statusName;

                if (statusName == '已兑付' || statusName == '等待银行处理') {
                    fw.alert('提示', '该计划已被兑付过，不能再次兑付！');
                    return false;
                }


                if (statusName != '审核成功') {
                    fw.alert('提示', '请先成功审核兑付计划');
                    return false;
                }

                var paymentTime = Date.parse(selected.paymentTime);
                var date = new Date();
                if (paymentTime > date.getTime()) {
                    fw.alert('提示', '还未到兑付时间不能兑付！');
                    return false;
                }

                fw.confirm('确认兑付', '<span style="color:red">请慎重操作！</span>您确定兑付这项计划吗？<br />【产品：' + selected.productName + '】<br />【金额：' + selected.totalPaymentMoney + '】', function () {
                    var id = selected.id;
                    var url = WEB_ROOT + "/sale/PaymentPlan_payment.action?id=" + id;
                    fw.post(url, {}, function (data) {
                        if (data == '1') {
                            fw.alert('提示', "改客户没有绑定银行卡，不能兑付");
                        }
                        else {
                            alert('已提交至银行处理，请在几分钟后刷新表格以查看兑付结果');
                            fw.datagridReload('PaymentPlanGuanLiTable' + token);
                        }
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 提交兑付审核事件
     */
    function onClickPaymentPlanRequestAudit() {
        var buttonId = "btnPaymentPlanRequestAudit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanGuanLiTable' + token, function (selected) {
                // 检测状态是否可以兑付
                var statusName = selected.statusName;
                var paymentTime = Date.parse(selected.paymentTime);
                if (statusName == '已兑付' || statusName == '等待银行处理') {
                    fw.alert('提示', '该计划已被兑付，无需再次操作');
                    return false;
                }
                // 确认提交审核
                fw.confirm('确认', '您确定提交审核这项计划吗？<br />【订单编号：' + selected["orderName"] + '】', function () {
                    var id = selected.id;
                    var url = WEB_ROOT + "/sale/PaymentPlan_updateAuditSubmitted.action?paymentPlan.id=" + id;
                    // post 请求
                    fw.post(url, {}, function (data) {
                        if (data == 0) {
                            fw.alert('错误', "没有查询到该记录");
                        } else if (data == 1) {
                            fw.alert('错误', "当前状态不能提交审核");
                        } else {
                            fw.alert('提示', "提交成功");
                            fw.datagridReload('PaymentPlanGuanLiTable' + token);
                        }
                    }, null);
                }, null);
            });
        })
    }

    function onClickPaymentPlanSubmit(){
        var buttonId = "btnPaymentPlanConfirm" + token;
        fw.bindOnClick(buttonId, function (process) {

            var orderId = fw.getTextValue("orderId"+token);
            var confirmorId = fw.getTextValue("confirmorId"+token);
            var url = WEB_ROOT + "/sale/PaymentPlan_confirm.action?orderId="+orderId;


            fw.confirm('提示', '是否确定此批兑付计划？', function(){

                //if (!fw.checkIsTextEmpty(confirmorId)) {
                //    fw.confirm('提示', '此兑付计划已经确认，是否需要重复确认？',null, function(){
                //        return;
                //    })
                //}

                fw.post(url, null, function(data){
                    if (data == "1") {
                        fw.alert('提示', '兑付计划已确认');
                        fw.datagridReload('OrderTable'+token);
                        fw.windowClose('PaymentPlanWindowDetail'+token);
                    }
                },null);
            },null);

        });
    }

    /**
     * 查询事件
     */
    function onClickPaymentPlanSearch() {
        var buttonId = "btnSearchPaymentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "PaymentPlanGuanLiTable2" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;


            //获取作为查询条件的oredrId条件
            //产品名称
            params['paymentPlanVO.productionName'] = $("#search_productName" + token).val()
            params["paymentPlanVO.search_status"] = fw.getFormValue('search_status' + token, fw.type_form_combotree, fw.type_get_value);
            //兑付日期 时间段查询
            var date = fw.getFormValue('Search_PaymentPlan_Year' + token, fw.type_form_datebox, fw.type_get_value) + "-" + fw.getFormValue('Search_PaymentPlan_Month' + token, fw.type_form_datebox, fw.type_get_value);
            params["paymentPlanVO_paymentTime"] = date;
            //兑付类型
            params["paymentPlanVO.type"] = fw.getFormValue('search_Type' + token, fw.type_form_combotree, fw.type_get_value);
            $('#' + strTableId).datagrid('load');
        });
    }


    function onClickDoPaymentPlanDoneSubmit() {
        var buttonId = "btnDoPaymentPlanDone" + token;



        fw.bindOnClick(buttonId, function(){

            fw.datagridGetSelected('PaymentPlanDetailTable'+token, function(selected){
                fw.confirm('提示', '是否确定兑付此笔兑付计划？', function() {

                    var url = WEB_ROOT + "/sale/PaymentPlan_doPaymnetDone.action?paymentPlanId="+selected.id;

                    fw.post(url, null, function(data){
                        if (data == "1") {

                            fw.alert('提示','手动兑付成功');

                            fw.datagridReload("PaymentPlanDetailTable" + token);
                            fw.windowClose('PaymentPlanWindow' + token);
                        }
                    },null);

                }, null);
            });
        });
    }


    /**
     * 数据提交事件
     */
    function onPaymentPlanSubmit() {

        var buttonId = "btnPaymentPlanSubmit" + token;

        fw.bindOnClick(buttonId, function (process) {
            var formId = "formPaymentPlan" + token;
            var url = WEB_ROOT + "/sale/PaymentPlan_savePaymentPlan.action";
            //var url = "http://localhost:8080/core/sale/PaymentPlan_load.action?";



            // var data = $('#'+formId).serialize();
            // // var data = "";
            //
            // fw.alert('url', url + " " + data);

            // return ;
            //
            // fw.post(url, data, function(){
            //     alert(1234);
            // },null);

            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("PaymentPlanDetailTable" + token);
                fw.windowClose('PaymentPlanWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }


    function onClickReportMonthSearch() {
        var buttonId = "btnSearchPaymentPlan" + token;

        fw.bindOnClick(buttonId, function (process) {
            var paymentTimeMonth = fw.getFormValue('Search_PaymentPlan_Year' + token, fw.type_form_combobox, fw.type_get_value) + "-" + fw.getFormValue('Search_PaymentPlan_Month' + token, fw.type_form_combobox, fw.type_get_value);
            initTablePaymentPlanReportMonthTable(paymentTimeMonth);
        });
    }

    function onClickReportMonthExport() {
        // exportReportMonth
        var buttonId = "btnExport" + token;
        fw.bindOnClick(buttonId, function (process) {
            var paymentTimeMonth = fw.getFormValue('Search_PaymentPlan_Year' + token, fw.type_form_combobox, fw.type_get_value) + "-" + fw.getFormValue('Search_PaymentPlan_Month' + token, fw.type_form_combobox, fw.type_get_value);
            window.open(WEB_ROOT + "/sale/PaymentPlan_exportReportMonth?paymentTimeMonth="+paymentTimeMonth);
        });
    }

///  事件定义 结束  /////////////////////////////////////////////////////////////////


    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initModulePaymentPlanDetail:function(orderId, confirmorId){
            return initPaymentPlanDetailWindow(orderId, confirmorId);
        },
        initModulePaymentPlanReportMonth:function(){
            var year = fw.getTimeYearString()
            var month = fw.getTimeMonthString()
            initTablePaymentPlanReportMonthTable(year + '-' + month);


            // 初始化查询日期
            $('#Search_PaymentPlan_Year'+token).combobox('setValue', year);
            $('#Search_PaymentPlan_Month'+token).combobox('setValue', month);


            onClickReportMonthSearch();

        }
    };
}