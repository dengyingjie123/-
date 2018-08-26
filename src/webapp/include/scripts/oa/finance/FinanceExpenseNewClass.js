/**
 *
 */

var FinanceExpenseNewClass = function (token) {
    var expenseId;
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

//        初始化部门与下了列表
        initFormFinanceExpenseSearch();

        // 初始化查询事件
        onClickFinanceExpenseSearch();
        // 初始化查询重置事件
        onClickFinanceExpenseSearchReset();
        // 初始化表格
        initFinanceExpenseTable();
        //初始化等待审核
        initWaitFinanceExpenseTable();
        //初始化已完成列表
        initParticipantFinanceExpenseTable();


    }

    //初始化加载部门与状态我下了列表
    function initFormFinanceExpenseSearch() {
        var url = WEB_ROOT + "/system/Department_list.action";
        fw.combotreeLoadWithCheck('#search_Department' + token, url, null, null, null);
        fw.getComboTreeFromKV('search_Status' + token, 'OA_FinanceExpenseStatus', 'k', null);
    }

    /**
     * 申请列表
     */
    function initFinanceExpenseTable() {
        var strTableId = 'FinanceExpenseTable' + token;
        var url = WEB_ROOT + "/oa/finance/FinanceExpense_list.action?isNewWorkflow=1";
        $('#' + strTableId).datagrid({
//            title: '费用报销',
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
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    { field: 'controlString3', title: '数据标识号', hidden: false},
                    { field: 'controlString1', title: '公司名称', hidden: false},
                    { field: 'controlString2', title: '部门名称', hidden: false},
                    { field: 'controlString1Id', title: '公司名称', hidden: true},
                    { field: 'controlString2Id', title: '部门名称', hidden: true},
                    { field: 'submitterId', title: '报销人ID', hidden: true },
                    { field: 'dapertmentId', title: '部门ID', hidden: true },
                     
                    { field: 'submitterName', title: '报销人' },
                    { field: 'month', title: '报销月份' },
                    {field: 'accessoryNumber', title: '附件张数'},
                    { field: 'money', title: '金额' }
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'SID', hidden: true },
                    { field: 'id', title: 'ID', hidden: true },
                    { field: 'expenseId', title: 'expenseId', hidden: true },

                    { field: 'time', title: '创建时间' },
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }  },
                    { field: 'generalManagerContent', title: '副总裁审核意见',
                    formatter: function (value, row, index) {
                        return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                    }  },
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {
                            return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;
                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"] == "" ? "" : row["cashierName"] + "：" + value;

                        }  },

                    { field: 'status', title: '是否完成', hidden: true },
                    { field: 'statusId', title: '状态', hidden: true  },
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},

                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinanceExpenseAdd' + token,
                    text: '新增申报',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnFinanceExpenseAdd2' + token,
                    text: '增加报销项目',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFinanceExpenseCheck' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnFinanceExpenseUpload' + token,
                    text: '附件上传',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFinanceExpensePrint' + token,
                    text: '打印',
                    iconCls: 'icon-search'
                },

                {
                    id: 'btnFinanceExpenseDelete' + token,
                    text: '作废',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                onClickFinanceExpenseAdd();
                onClickFinanceExpenseAdd2();
                onClickFinanceExpenseDelete();
                onClickFinanceExpenseCheck();
                onClickFinanceExpensePrint('btnFinanceExpensePrint' + token,strTableId);
                onClickFinanceExpenxe();
                onClickFinanceExpenseUpload();
            }
        });
    }

    /**
     * 修改人:周海鸿
     * 修改时间：2015-6-30
     * 修改事件:实现单一附件上传功能
     */

    function onClickFinanceExpenseUpload() {
        var butttonId = "btnFinanceExpenseUpload" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('FinanceExpenseTable' + token, function (selected) {
                process.beforeClick();
                var sid = selected.sid;
                var upload = "upload";
                var url = WEB_ROOT + "/oa/finance/FinanceExpense_load.action?financeExpense.sid=" + sid;

                fw.post(url, null, function (data) {

                    //设置工作流编号
                    data["WorkflowID"] = 23;
                    //设置业务编号
                    data["YWID"] = selected.id;
                    //设置路由编号
                    data["RouteListID"] = selected.routeListId;
                    //设置节点编号
                    data["CurrentNode"] = selected.currentNodeId;
                    //设置项目组名
                    data["departmentName"] = selected.departmentName;
                    //设置申请人
                    data["financeExpense.submitter"] = selected.submitterName;
                    //设置参与人
                    data["Participant"] = loginUser.getName();

                    data["controlString1"] = selected.controlString1Id;
                    data["expenseVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initWindowFinanceExpenseWindow(data, upload,"3");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 等待审核列表
     */
    function initWaitFinanceExpenseTable() {
        var strTableId = 'WaitFinanceExpenseTable' + token;
        var url = WEB_ROOT + "/oa/finance/FinanceExpense_waitlist.action?isNewWorkflow=1";
        $('#' + strTableId).datagrid({
//            title: '费用报销',
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
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    { field: 'controlString3', title: '数据标识号', hidden: false},
                    { field: 'controlString1', title: '公司名称', hidden: false},
                    { field: 'controlString2', title: '部门名称', hidden: false},
                    { field: 'controlString1Id', title: '公司名称', hidden: true},
                    { field: 'controlString2Id', title: '部门名称', hidden: true},
                    { field: 'submitterId', title: '报销人ID', hidden: true },
                    { field: 'dapertmentId', title: '部门ID', hidden: true },
                     
                    { field: 'submitterName', title: '报销人' },
                    { field: 'month', title: '报销月份' },
                    {field: 'accessoryNumber', title: '附件张数'},
                    { field: 'money', title: '金额' }
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'SID', hidden: true },
                    { field: 'id', title: 'ID', hidden: true },
                    { field: 'expenseId', title: 'expenseId', hidden: true },

                    { field: 'time', title: '创建时间' },
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }  },
                    { field: 'generalManagerContent', title: '副总裁审核意见',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }  },
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {

                            return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;

                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"] == "" ? "" : row["cashierName"] + "：" + value;

                        }  },
                    { field: 'status', title: '是否完成', hidden: true },
                    { field: 'statusId', title: '状态', hidden: true  },
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinanceExpenseCheck2' + token,
                    text: '业务审批',
                    iconCls: 'icon-search'
                }

            ],
            onLoadSuccess: function () {
                onClickFinanceExpenseCheck2();
            }
        });

    }

    /**
     *
     * 已完成列表
     */
    function initParticipantFinanceExpenseTable() {
        var strTableId = 'ParticipantFinanceExpenseTable' + token;
        var url = WEB_ROOT + "/oa/finance/FinanceExpense_participantlist.action?isNewWorkflow=1";
        $('#' + strTableId).datagrid({
//            title: '费用报销',
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
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    { field: 'controlString3', title: '数据标识号', hidden: false},
                    { field: 'controlString1', title: '公司名称', hidden: false},
                    { field: 'controlString2', title: '部门名称', hidden: false},
                    { field: 'controlString1Id', title: '公司名称', hidden: true},
                    { field: 'controlString2Id', title: '部门名称', hidden: true},
                    { field: 'submitterId', title: '报销人ID', hidden: true },
                    { field: 'dapertmentId', title: '部门ID', hidden: true },
                     
                    { field: 'submitterName', title: '报销人' },
                    { field: 'month', title: '报销月份' },
                    {field: 'accessoryNumber', title: '附件张数'},
                    { field: 'money', title: '金额' }
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'SID', hidden: true },
                    { field: 'id', title: 'ID', hidden: true },
                    { field: 'expenseId', title: 'expenseId', hidden: true },

                    { field: 'time', title: '创建时间' },
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }  },
                    { field: 'generalManagerContent', title: '副总裁审核意见',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }  },
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {

                            return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;

                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"] == "" ? "" : row["cashierName"] + "：" + value;

                        }  },
                    { field: 'status', title: '是否完成', hidden: true },
                    { field: 'statusId', title: '状态', hidden: true  },
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinanceExpenseLook' + token,
                    text: '查看',
                    iconCls: 'icon-search'
                } ,{
                    id: 'btnFinanceExpensePrint2' + token,
                    text: '打印',
                    iconCls: 'icon-search'
                }
            ],
            onLoadSuccess: function () {
                onClickFinanceExpenseLook();
                onClickFinanceExpensePrint('btnFinanceExpensePrint2' + token,strTableId);
            }
        });

    }

    /**
     * HOPEWEALTH-1360
     * 初始化窗口中的workflow panel内容，使其直接显示审批内容
     * @param data
     */
    function initFinanceExpenseWorkflowPanel(data) {
        // 组装URL
        var url = WEB_ROOT + "/include/wf/bizRoute/bizRouteNew_Save.jsp";
        url += "?YWID=" + data["YWID"];
        url += "&WorkflowID=" + data["WorkflowID"];
        url += "&UserID=" + loginUser.getId();
        url += "&RouteListID=" + data['RouteListID'];
        url += "&CurrNodeID=" + data['CurrentNode'];
        url += "&token=" + token;

        $('#panelFinanceExpenseWorkflow' + token).load(
            url,
            null,
            function() {
                //直接引用工作流的js代码，将按钮与功能绑定，并实现编辑控制
                using('../wf/script/BizRouteNewClass.js', function () {
                    // 组装参数，传入token, data, 三个表的ID, 窗口ID和四个按钮ID
                    var table1 = "FinanceExpenseTable" + token;// 我的申请列表
                    var table2 = 'WaitFinanceExpenseTable' + token;// 等待我审核列表
                    var table3 = 'ParticipantFinanceExpenseTable' + token;// 已完成列表
                    var windowId = "FinanceExpenseWindow" + token;// 窗口ID
                    var btn1 = "btnFinanceExpenseBizRouteSubmit";// 同意按钮
                    var btn2 = "btnFinanceExpenseBizRouteOver";// 完成按钮
                    var btn3 = "btnFinanceExpenseBizRouteReject";// 回退按钮
                    var btn4 = "btnFinanceExpenseBizRouteCancel";// 中止按钮

                    var bizRoute = new BizRouteNewClass(token, data,
                        table1, table2, table3,
                        windowId,
                        btn1, btn2, btn3, btn4);
                    bizRoute.initModule();
                });
            }
        );
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowFinanceExpenseWindow(data, obj, windostatus) {
        var window = "";
        var height = "";
        if (windostatus == "1") {
            //新增申报窗口大小
            window = "655";
            height = "300";
        } else {
            //其他情况窗口大小
            window = "655";
            height = "600";
        }

        var url = WEB_ROOT + "/modules/oa/finance/FinanceExpenseNew_Save.jsp?&token=" + token;
        var windowId = "FinanceExpenseWindow" + token;

        fw.window(windowId, '申请', window, height, url, function () {
            fw.textFormatCurrency('size' + token);
            //初始化选择用户
            initUserName();
            var temp=false;
            //报销日期的格式为：0000-00
            $("#month" + token).datebox({
                formatter: function (date) {
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    if (m < 10) {
                        m = "0" + m;
                    }
                    return y + "-" + m;
                }
            });

            // 初始化表单提交事件
            var userid = "";
            userid = data["financeExpense.submitterId"];

            //窗口状态为添加状态
            if (windostatus == "1") {
                //去除不属于添加状态的元素
                //移除醒目列表
                $("#FinanceExpenseDetailTable" + token).remove();
                //移除业务审核按钮
                $("#btnFinanceExpenseSubmit_Start" + token).remove();
                userid = loginUser.getId();

                data["financeExpense.operatorId"] = loginUser.getId();
                data["financeExpense.operatorName"] = loginUser.getName();

            }
            //窗口状态为添加项目状态
            else if (windostatus == "2") {

                $("#btnFinanceExpenseSubmit_Start" + token).remove();
                $("#time" + token).datebox({
                    readonly: true
                });
            }
            //窗口德惠状态为审核状态
            else if (windostatus == "3") {
                $("#time" + token).datebox({
                    readonly: true
                });
                //移除添加按钮
                $("#btnFinanceExpenseSubmit" + token).remove();
                $("#month" + token).datebox({
                    readonly: true
                });
            }
            //窗口状态为审核状态
            if (windostatus == "3") {
                //业务审核页面
                var newWorkflow = 1;
                fw.onClickBizSubmit(token, "btnFinanceExpenseSubmit_Start" + token,
                    data , "FinanceExpenseTable" + token, 'WaitFinanceExpenseTable' + token, 'ParticipantFinanceExpenseTable' + token, windowId, newWorkflow);
                temp = true;
            } else {
                //添加数据提交事件
                onClickFinanceExpenseSubmit2();
            }

            // HOPEWEALTH-1360 begin
            if (windostatus == "look") {
                // 查看状态，提供panel，但无功能按钮
                initFinanceExpenseWorkflowPanel(data);
                $("#btnFinanceExpenseBizRouteSubmit" + token).remove();
                $("#btnFinanceExpenseBizRouteOver" + token).remove();
                $("#btnFinanceExpenseBizRouteReject" + token).remove();
                $("#btnFinanceExpenseBizRouteCancel" + token).remove();
            } else if (windostatus == "3" && obj == "check") {
                // 业务审核状态，需要初始化panel相关内容和功能
                initFinanceExpenseWorkflowPanel(data);

                // 结束节点为7
                if (data["CurrentNode"] == "7" || data["CurrentNode"] == 7) {
                    $("#btnFinanceExpenseBizRouteSubmit" + token).remove();// 移除同意按钮，保留完成按钮
                } else {
                    $("#btnFinanceExpenseBizRouteOver" + token).remove();// 移除完成按钮，保留同意按钮
                }
            } else {
                // 其他情况，舍弃无关按钮
                $("#panelFinanceExpenseWorkflow" + token).remove();
                $("#btnFinanceExpenseBizRouteSubmit" + token).remove();
                $("#btnFinanceExpenseBizRouteOver" + token).remove();
                $("#btnFinanceExpenseBizRouteReject" + token).remove();
                $("#btnFinanceExpenseBizRouteCancel" + token).remove();
            }
            // HOPEWEALTH-1360 end

            if(obj=="upload" || obj=="check") {
                //附件上传页面按钮状态
                var btnstatus = "";
                if(obj=="check"){
                    btnstatus = "remove";
                }
                fw.initFileUpload(data["financeExpense.id"], 18831, 'btnUpload' + token, btnstatus, token);
            }else{

                $("#uploadTR" + token).remove();
            }
        //申请
            if(obj == "applay"){

                $("#btnFinanceExpenseSubmit" + token).remove();
                $("#btnFinanceExpenseSubmit_Start" + token).remove();
                onClickFinanceExpenseSubmit(data,windowId);
            }else{

                $("#btnFinanceExpenseSubmit_Applay" + token).remove();
            }


            var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
            var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"]
            //公司部门二级下拉列表

            fw.doubleDepartmentTrees("controlString1"+token ,"controlString2"+token ,selectString1,selectString2,temp);



            // 加载数据
            fw.formLoad('formFinanceExpense' + token, data);
            /**、
             *向服务器预先获取一个标识列作为当前数据的ID值，
             * 设置表单数据的初始化值
             *
             * @type {string}
             */
            var url = WEB_ROOT + "/oa/finance/FinanceExpense_add.action?financeExpense.submitterId=" + userid;
            $.post(url, null, function (data2) {
                var json = "[" + data2 + "]";
                var jsonArray = eval('(' + json + ')');
                if (obj == 'add') {
                    //初始化数据ID
                    expenseId = jsonArray[0].returnValue[0].expenseId;
                    //初始化申请人
                    $("#submitterId" + token).val(loginUser.getId());
                    //设置数据ID
                    $("#expenseId" + token).val(expenseId)
                    //设置显示申请人
                    $("#submitter" + token).val(loginUser.getName());
                    //设置显示申请人部门
                    $("#department" + token).val(jsonArray[0].returnValue[0].departmentName);
                    //设置生申请时间
                    $("#submitterTime" + token).val(jsonArray[0].returnValue[0].time);
                    //设置开始时间
                    $("#time" + token).datebox("setValue", jsonArray[0].returnValue[0].time);

                    //将数据设置成只读模式
                    $('#department' + token).attr('readonly', 'readonly');
                    $('#submitter' + token).attr('readonly', 'readonly')
                    $('#time' + token).attr('readonly', true);
                } else {
                    expenseId = $('#expenseId' + token).val();
                    $('#submitter' + token).attr('readonly', 'readonly');
                    $('#department' + token).attr('readonly', 'readonly');
                    //设置报销月份
                    $("#month" + token).datebox("setValue", data["financeExpense.month"]);
                }
                //初始化调用项目
                initFinanceExpenseDetail(expenseId, obj);
            })

            //初始换文本框不能编辑

            if (obj == 'add') {
                $('#submitter' + token).val(loginUser.getName());
                $('#submitterTime' + token).val(fw.getDateTime());
                $("#submitterId" + token).val(loginUser.getId());
                $('#submitter' + token).attr('readonly', 'readonly');
                $('#department' + token).attr('readonly', 'readonly');
            }
            if (windostatus == 'look') {

                //移除添加按钮
                $("#btnFinanceExpenseSubmit" + token).remove();
                $("#btnFinanceExpenseSubmit_Start" + token).remove();
            }

        }, null);
    }

    /**
     *
     * 申请
     * @param data
     * @param windowid
     */
    function onClickFinanceExpenseSubmit(data,windowid) {
        var buttonId = "btnFinanceExpenseSubmit_Applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"]="";
            fw.onclickworkflowApplay(data,process, "FinanceExpenseTable" + token, 'WaitFinanceExpenseTable' + token, 'ParticipantFinanceExpenseTable' + token,windowid);
            provess.afterClick
        });
    }

    /**
     * 初始化项目详细窗口
     * @param data
     */
    function initWindowFinanceExpenseDetail(data, expenseId) {
        var url = WEB_ROOT + "/modules/oa/finance/FinanceExpense_Detail.jsp?token=" + token;
        var windowId = "FinanceExpenseDetailWindow" + token;
        fw.window(windowId, '项目申报', 330, 300, url, function () {
            // 初始化项目表单提交事件
            onClickFinanceExpenseDetailSubmit();
            // 加载数据
            fw.formLoad('formFinanceExpenseDetail' + token, data);
            $("#expenseId" + token + 1).val(expenseId);

        }, null);
    }

//    function onClickinanceExpenseOver() {
//        var buttonId = "btnFinanceExpenseOver" + token;
//        var formId = "formFinanceExpense" + token;
//        var url = "/core/wf/Workflow_service.action";
//        fw.bindOnClick(buttonId, function (process) {
//            $('#TargetURL' + token).val('/wf/Done.jsp');
//            $('#ServiceType' + token).val('Over');
//            $('#BizDaoName' + token).val('com.youngbook.entity.po.wf.FinanceExpensePO');
//            $('#JsonPrefix' + token).val('financeExpense');
//
//            fw.formSubmit(formId, url, buttonId, function () {
//                var data = $('#' + formId).serialize();
//                //fw.alertReturnValue(data);
//            }, function (data) {
//                // fw.alertReturnValue(data);
//                fw.datagridReload("SealUsageWFATable" + token);
//                fwCloseWindow('FinanceExpenseWindo' + token);
//            });
//        });
//    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////


    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 加载项目数据
     */
    function initFinanceExpenseDetail(expenseId, obj) {
        var strTableId = 'FinanceExpenseDetailTable' + token;
        //获取指定数据的项目列表
        var url = WEB_ROOT + "/oa/finance/FinanceExpense_listDetail.action?expenseDetail.expenseId=" + expenseId;
        $('#' + strTableId).datagrid({
            title: '费用申报项目',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [5, 10, 20],
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
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'SID', hidden: true },
                    { field: 'itemName', title: '项目名称' },
                    { field: 'itemTime', title: '项目时间' },
                    { field: 'money', title: '金额' },
                    { field: 'purpose', title: '用途' },
                    { field: 'comment', title: '备注' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinanceExpenseDetailAdd' + token,
                    text: '新增费用项目',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnFinanceExpenseDetailEdit' + token,
                    text: '修改费用项目',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFinanceExpenseDetailDelete' + token,
                    text: '删除费用项目',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                onClickFinanceExpenseDetaileAdd(expenseId);
                onClickFinanceExpenseDetaileEdit(expenseId);
                onClickFinanceExpenseDetailDelete(expenseId);
            }
        });
        //当窗口的状态为检查时候  所有按钮去除
        if (obj == "check" || obj=="upload" || obj=="applay")  {
            $('#btnFinanceExpenseDetailDelete' + token).remove();
            $('#btnFinanceExpenseDetailEdit' + token).remove();
            $('#btnFinanceExpenseDetailAdd' + token).remove();
        }
    }


    /**
     * 添加事件
     */
    function onClickFinanceExpenseAdd2() {
        var buttonId = "btnFinanceExpenseAdd2" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FinanceExpenseTable' + token, function (selected) {

                //判断数据是否进入业务流状态
                if ("已完成" == selected.currentNodeTitle) {
                    fw.alert("警告", "该数据的工作流已经完成无法修改数据");
                    process.afterClick();
                    return;
                }

                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经进入流程转正无法修改数据");
                    process.afterClick();
                    return;
                }
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/oa/finance/FinanceExpense_load.action?financeExpense.sid=" + sid;
                fw.post(url, null, function (data) {
                    //设置窗口状态
                    var project = 'project';
                    //设置工作流编号
                    data["WorkflowID"] = 23;
                    //设置业务编号
                    data["YWID"] = selected.id;
                    //设置路由编号
                    data["RouteListID"] = selected.routeListId;
                    //设置节点编号
                    data["CurrentNode"] = selected.currentNodeId;
                    //设置项目组名
                    data["departmentName"] = selected.departmentName;
                    //设置申请人
                    data["financeExpense.submitter"] = selected.submitterName;
                    //设置参与人
                    data["Participant"] = loginUser.getName();

                    data["controlString1"] = selected.controlString1Id;
                    data["expenseVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    //钓鱼窗口
                    initWindowFinanceExpenseWindow(data, project, "2");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 查看
     */
    function onClickFinanceExpenseLook() {
        var buttonId = "btnFinanceExpenseLook" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ParticipantFinanceExpenseTable' + token, function (selected) {


                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/oa/finance/FinanceExpense_load.action?financeExpense.sid=" + sid;
                fw.post(url, null, function (data) {
                    //设置窗口状态
                    var project = 'check';
                    //设置工作流编号
                    data["WorkflowID"] = 23;
                    //设置业务编号
                    data["YWID"] = selected.id;
                    //设置路由编号
                    data["RouteListID"] = selected.routeListId;
                    //设置节点编号
                    data["CurrentNode"] = selected.currentNodeId;
                    //设置项目组名
                    data["departmentName"] = selected.departmentName;
                    //设置申请人
                    data["financeExpense.submitter"] = selected.submitterName;
                    //设置参与人
                    data["Participant"] = loginUser.getName();

                    data["controlString1"] = selected.controlString1Id;
                  data["expenseVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    //钓鱼窗口
                    initWindowFinanceExpenseWindow(data, project, "look");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    function onClickFinanceExpenseAdd() {

        var buttonId = "btnFinanceExpenseAdd" + token;

        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            var add = 'add';

            initWindowFinanceExpenseWindow({controlString1:"",controlString2:""}, add, "1");
        });

    }

    /**
     * 作废事件
     */
    function onClickFinanceExpenseDelete() {
        var buttonId = "btnFinanceExpenseDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FinanceExpenseTable' + token, function (selected) {
                process.beforeClick();
                if ("已完成" == selected.currentNodeTitle) {
                    fw.alert("警告", "该数据的工作流已经完成无法修改数据");
                    process.afterClick();
                    return;
                }

                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经进入流程转正无法修改数据");
                    process.afterClick();
                    return;
                }
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/oa/finance/FinanceExpense_delete.action?financeExpense.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('FinanceExpenseTable' + token);
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }
    /*修改：周海鸿
     * 时间：2015-7-17
     * 内容：添加打印按钮*/
    function onClickFinanceExpensePrint(buttonId,tableid) {
        //var buttonId = 'btnFinanceExpensePrint' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableid, function (selected) {

                process.beforeClick();

                var url =WEB_ROOT+"/modules/oa/modelsFiles/FinanceExpenseModels.jsp?expenseId="+selected.expenseId+"&id="+selected.id+"&token=" + token;
                window.open(url);

                process.afterClick();

            });
        });
    }

    /**
     * 业务申请事件（开始进入工作流）
     */
    function onClickFinanceExpenseCheck() {
        var butttonId = "btnFinanceExpenseCheck" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('FinanceExpenseTable' + token, function (selected) {
                if (selected.money <= 0 || selected.money == "") {
                    fw.alert("警告", "请添加报销项目！");
                    return
                }
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经提交申请不得重复提交");
                    process.afterClick();
                    return;
                }
                process.beforeClick();
                var sid = selected.sid;
                var id = selected.id;
                var url = WEB_ROOT + "/oa/finance/FinanceExpense_load.action?financeExpense.sid=" + sid;
                fw.post(url, null, function (data) {

                    data["workflowID"] = 23;

                    data["nextNode"] = 2;
                    //设置业务编号
                    data["id"] = id;
                    //设置路由编号
                    data["routeListId"] = selected.routeListId;
                    //设置节点编号
                    data["currentNodeId"] = selected.currentNodeId;

                    data["controlString1"] = selected.controlString1Id;
                    data["controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;

                    data["departmentName"] = selected.departmentName;
                    data["financeExpense.submitter"] = selected.submitterName;

                    initWindowFinanceExpenseWindow(data, "applay", "3");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });

        })
    }

    /**
     * 等待我审批->业务审批
     */
    function onClickFinanceExpenseCheck2() {
        var butttonId = "btnFinanceExpenseCheck2" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('WaitFinanceExpenseTable' + token, function (selected) {
                if (selected.money <= 0 || selected.money == "") {
                    fw.alert("警告", "请添加报销项目！");
                    return
                }
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/oa/finance/FinanceExpense_load.action?financeExpense.sid=" + sid;
                fw.post(url, null, function (data) {

                    //设置窗口审核状态
                    var check = 'check';
                    //设置所有业务数据
                    data["WorkflowID"] = 23;
                    data["bizRoute.workflowId"] = 23;
                    data["YWID"] = selected.id;
                    data["RouteListID"] = selected.routeListId;
                    data["CurrentNode"] = selected.currentNodeId;
                    data["controlString1"] = selected.controlString1Id;
                    data["controlString2"] = selected.controlString2Id;
                    data["expenseVO.controlString3"] = selected.controlString3;
                    data["departmentName"] = selected.departmentName;
                    data["financeExpense.submitter"] = selected.submitterName;

                    initWindowFinanceExpenseWindow(data, check, "3");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });

        })
    }

    /**
     * 报销事件
     */

    function onClickFinanceExpenxe() {

    }

    /**
     * 查询事件
     */
    function onClickFinanceExpenseSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "FinanceExpenseTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params["expenseVO_submitterTime_Start"] = fw.getFormValue('search_SubmitterTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["expenseVO_submitterTime_End"] = fw.getFormValue('search_SubmitterTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["expenseVO.status"] = fw.getFormValue('search_Status' + token, fw.type_form_combotree, fw.type_get_text);
            params["financeExpense.money"] = $("#search_Money" + token).val();
            params["expenseVO.submitterName"] = $("#search_ExpenseName" + token).val();
            var ids = fw.combotreeGetCheckedIds('search_Department' + token, ',', "");

            params["Departments"] = ids.trim();

            $('#' + strTableId).datagrid('load');
            fw.treeClear()
        });
    }

    /**
     * 查询重置事件
     */
    function onClickFinanceExpenseSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_SubmitterTime_Start' + token).datebox("setValue", '');
            $('#search_SubmitterTime_End' + token).datebox("setValue", '');
            $('#search_Money' + token).val('');
            $('#search_ExpenseName' + token).val('');
            fw.combotreeClear('search_Department' + token);
            fw.combotreeClear('search_Status' + token);
        });
    }

//    /**
//     * 数据提交事件
//     */
//    /**
//     * 数据提交事件
//     */
//    function onClickFinanceExpenseSubmit() {
//        var buttonId = "btnFinanceExpenseSubmit" + token;
//        var formId = "formFinanceExpense" + token;
//        var url = "/core/wf/Workflow_service.action";
//        fw.bindOnClick(buttonId, function (process) {
//            //  $("#month"+token).val(fw.getFormValue('month2' + token, fw.type_form_combotree, fw.type_get_text));
//            var money = $("#money" + token).val();
//            if (money <= 0 || money == "") {
//                fw.alert("警告", "请添加报销项目！");
//                return
//            }
//            ;
//            if (fw.checkIsNullObject($('#NextNode' + token).val())) {
//                fw.alert('警告', '请选择业务目的地！');
//                return;
//            }
//            $('#TargetURL' + token).val('/wf/Done.jsp');
//            $('#ServiceType' + token).val('SaveForward');
//            $('#BizDaoName' + token).val('com.youngbook.entity.po.wf.FinanceExpensePO');
//            $('#JsonPrefix' + token).val('financeExpense');
//            //$('#CurrentNode'+token).val('1');
//            //$('#WorkflowID'+token).val('4');
//            //$('#RouteListID'+token).val('0');
//            //$('#Participant'+token).val('admin');
//
//            fw.formSubmit(formId, url, buttonId, function () {
//                var data = $('#' + formId).serialize();
//                //fw.alertReturnValue(data);
//
//            }, function (data) {
//                //fw.alertReturnValue(data);
//                data = fw.convert2Json(data);
//
//                if (data.code != 100) {
//                    fw.alert('错误', data.message);
//                }
//                else {
//                    fw.datagridReload("FinanceExpenseTable" + token);
//                    fw.windowClose('FinanceExpenseWindow' + token);
//                }
//            });
//        });
//
//    }
//
//    /**
////     *弹出添加审核界面
////     */
////    function onclickFinanceExpenseSubmit3(YWID,routeListId,currentNodeId){
////        var buttonId = "btnFinanceExpenseSubmit_Start" + token;
////        //alert(buttonId);
////        fw.bindOnClick(buttonId, function (process) {
////            process.beforeClick();
////                using('../wf/script/BizRouteClass.js', function () {
////                    var bizRoute = new BizRouteClass(token, YWID, 6, routeListId, currentNodeId,"FinanceExpenseTable" + token
////                        ,'FinanceExpenseWindow'+token)
////                        ;
////                    bizRoute.initModule();
////                });
////            process.afterClick();
////            });
////    }

    /**
     * 添加项目提交按钮，新增整个申报项目也是这里
     */
    function onClickFinanceExpenseSubmit2() {
        var buttonId = "btnFinanceExpenseSubmit" + token;
        //alert(buttonId);
        fw.bindOnClick(buttonId, function (process) {
            fw.CurrencyFormatText('money' + token);
            var formId = "formFinanceExpense" + token;
            var url = WEB_ROOT + "/oa/finance/FinanceExpense_insertOrUpdate.action?isNewWorkflow=1";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                //alert('done');
                process.afterClick();
                fw.datagridReload("FinanceExpenseTable" + token);
                fw.windowClose('FinanceExpenseWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 添加项目详细事件
     */
    function onClickFinanceExpenseDetaileAdd(expenseId) {

        var buttonId = "btnFinanceExpenseDetailAdd" + token;

        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initWindowFinanceExpenseDetail({controlString1:"",controlString2:""}, expenseId);
        });

    }

    /**
     * 删除项目详细事件
     */
    function onClickFinanceExpenseDetailDelete(expenseId) {
        var buttonId = "btnFinanceExpenseDetailDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FinanceExpenseDetailTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/oa/finance/FinanceExpense_deleteDetail.action?expenseDetail.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('FinanceExpenseDetailTable' + token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 修改项目详细事件
     */
    function onClickFinanceExpenseDetaileEdit(expenseId) {
        var butttonId = "btnFinanceExpenseDetailEdit" + token;
        fw.bindOnClick(butttonId, function (process) {

            fw.datagridGetSelected('FinanceExpenseDetailTable' + token, function (selected) {
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/oa/finance/FinanceExpense_loadDetail.action?expenseDetail.sid=" + sid;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    initWindowFinanceExpenseDetail(data, expenseId);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 项目详细数据提交事件
     */
    function onClickFinanceExpenseDetailSubmit() {
        var buttonId = "btnFinanceExpenseDetailSubmit" + token;
        //alert(buttonId);
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formFinanceExpenseDetail" + token;
            var url = WEB_ROOT + "/oa/finance/FinanceExpense_insertOrUpdateDetail.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                //alert('done');

                var getExpenseId = $("#expenseId" + token).val();
                var url2 = WEB_ROOT + "/oa/finance/FinanceExpense_getFinanceExpenseMoney.action?financeExpense.expenseId=" + getExpenseId;
                fw.post(url2, null, function (dates) {
                    $("#money" + token).val(dates);
                });
                process.afterClick();
                fw.datagridReload("FinanceExpenseDetailTable" + token);
                fw.windowClose('FinanceExpenseDetailWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }
    /**
     *
     初始化选择出差人
     */
    function initUserName() {
        //设置按钮ID
        var textID = "#submitter" + token;
        //按钮点击事件
        $(textID).bind('click', function () {
            //加载用户选择脚本
            using(SCRIPTS_ROOT + '/system/UserClass(Discard).js', function () {
                var userClass = new UserClass(token);
                userClass.windowOpenUserSelection(true, token, function (row) {
                    //获取选择的用户名字 ，ID
                    $("#submitter" + token).val(row[0].name);
                    $("#submitterId" + token).val(row[0].id);
                    //设置用户部门查询URL
                    var orgNameUrl = WEB_ROOT + "/system/User_getUserForDepartmentName.action?user.id=" + row[0].id;
                    //请求获取制定用户的部门信息
                    fw.post(orgNameUrl, null, function (orgData) {
                        //将部门信息设置到表单中
                        $("#department" + token).val(orgData["name"]);
                        $("#dapertmentId" + token).val(orgData["id"]);
                    })
                });

            })
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