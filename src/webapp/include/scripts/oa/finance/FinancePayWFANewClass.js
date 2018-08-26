// -----------------------------------------------------------------------------
// 构造JS
/**
 * FinancePayWFAClass.js 脚本对象
 * 资金支付对象
 *
 *  *  用法
 *    using(SCRIPTS_ROOT + '/oa/finance/FinancePayWFAClass.js', function () {
            var financePayWFAClass = new FinancePayWFAClass(token);
            financePayWFAClass.initModule();
        });
 *
 *  修改：周海鸿
 *  内容：完成详细注释
 *  时间：2015年 6月17日 15:00:00
 */
var FinancePayWFANewClass = function (token) {
    //设置服务访问地址
    var ActionUrl = WEB_ROOT + '/oa/finance';
    //设置野蛮访问地址
    var WebPageUrl = WEB_ROOT + '/modules/oa/finance';

    var callback4Select = undefined;

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickFinancePayWFASearch();

        // 初始化查询重置事件
        onClickFinancePayWFASearchReset();

        // 初始化tabs
        initFinancePayWFATabs();

        // 初始化查询表单
        initFinancePayWFASearchForm();

        // 初始化选择窗口按钮
        onClcikSelectDone();

    }

    function initFinancePayWFATabs() {
        var tabId = fw.getObjectFromId("FinancePayWFATabs" + token);
        $(tabId).tabs({
            onSelect:function(title, index) {

                if (title == '我的申请') {
                    // 初始化表格
                    initFinancePayWFATable();
                }

                if (title == '等待我审批') {
                    initWatFinancePayWFATable();
                }

                if (title == '已完成') {
                    initFinancePayWFAParticipantTable();
                }

            }
        });

        initFinancePayWFATable();
    }

    /**
     * 初始化查询表单
     */
    function initFinancePayWFASearchForm() {
    }


    function onClcikSelectDone() {
        fw.bindOnClick('btnSelect' + token, function (press) {
            var strTableId = 'FinancePayWFATable' + token;
            var data = $('#' + strTableId).datagrid('getSelected');
            if (fw.checkIsFunction(callback4Select)) {
                callback4Select(data);
            }
            fwCloseWindow('SelectWindow' + token);
        });
    }

    // 构造申请表单
    function initFinancePayWFATable() {
        var strTableId = 'FinancePayWFATable' + token;

        var url = ActionUrl + '/FinancePayWFA_list.action';

        $('#' + strTableId).datagrid({
//            title: '资金支付管理',
            url: url,
            queryParams: {
                "financePayWFAVO.workflowId":25
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [10, 20, 30],
            pageSize: 10,
            rownumbers: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: 'sid',//排序的列
            loadFilter: function (data) {
                try {
                    //获取数据 返回的Value
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
                    { field: 'time', title: '日期', hidden: false, formatter: function (value, row, index) {
                        //格式化显示的值
                        return value.substring(0, 10);
                    }},
/*
                    { field: 'payName', title: '付款方名称', hidden: false},*/

                    { field: 'money', title: '本次支付金额', hidden: false,
                        formatter: function (value, row, index) {
                            return value == "" ? "" : value + " 元";
                        }}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'Sid', hidden: true},
                    { field: 'id', title: 'Id', hidden: true},
                    { field: 'state', title: 'State', hidden: true},
                    { field: 'operatorId', title: 'OperatorId', hidden: true},
                    { field: 'operateTime', title: 'OperateTime', hidden: true},
                    { field: 'orgId', title: '组织编号', hidden: true},/*
                    { field: 'payBankName', title: '付款方开户行', hidden: false},
                    { field: 'payBankAccount', title: '付款方帐号', hidden: false},*/
                    { field: 'contractName', title: '合同名称', hidden: false},
                    { field: 'payeeName', title: '收款方名称', hidden: false},
                    { field: 'payeeBankName', title: '收款方开户行', hidden: false},
                    { field: 'payeeBankAccount', title: '收款方帐号', hidden: false},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'accountingContent', title: '会计审核',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }
                    },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }  },
                    { field: 'generalManagerContent', title: '副总裁审核意见',
                    formatter: function (value, row, index) {

                        return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                    }},
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {

                            return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;

                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"] == "" ? "" : row["cashierName"] + "：" + value;

                        }  },
                    { field: 'projectName', title: '资金支付项目', hidden: true},
                    { field: 'contractNo', title: '合同编号', hidden: true},
                    { field: 'contractMoney', title: '合同金额', hidden: true},
                    { field: 'paidMoney', title: '累计已支付金额', hidden: true},
                    { field: 'applicantId', title: '经办人编号', hidden: true},
                    { field: 'submitterName', title: '经办人名称', hidden: false},
                    { field: 'applicantTime', title: '经办人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true },
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinancePayWFAAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnFinancePayWFAEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFinancePayWFACheck' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnFinancePayWFAUpload' + token,
                    text: '附件上传',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFinancePayWFADelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                },
                {
                    id: 'btnFinancePayWFAPrint' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickFinancePayWFAAdd();
                onClickFinancePayWFAEdit();
                onClickFinancePayWFADelete();
                onClickFinancePayWFACheck();
                onClickFinancePayWFAPrint( 'btnFinancePayWFAPrint' + token,strTableId);
                onClickFinancePayWFAUpload();
            }
        });
    }
    //打印
    /*修改：周海鸿
    * 时间：2015-7-17
    * 内容：添加打印按钮*/
    function onClickFinancePayWFAPrint(buttonId,tableid) {
        //var buttonId = 'btnFinancePayWFAPrint' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableid, function (selected) {

                process.beforeClick();

                var url =WEB_ROOT+"/modules/oa/modelsFiles/FinancePayWFAModelsNew.jsp?id="+selected.id+"&token=" + token;
               window.open(url);

                process.afterClick();

            });
        });
    }

    // 构造审核列表
    function initWatFinancePayWFATable() {
        var strTableId = 'WaitFinancePayWFATable' + token;

        var url = ActionUrl + '/FinancePayWFA_waitList.action';

        $('#' + strTableId).datagrid({
//            title: '资金支付管理',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
                "financePayWFAVO.workflowId":25
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [10, 20, 30],
            pageSize: 10,
            rownumbers: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: 'sid',//排序的列
            loadFilter: function (data) {
                try {
                    //获取数据 返回的Value
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
                    { field: 'time', title: '日期', hidden: false, formatter: function (value, row, index) {
                        //格式化显示的值
                        return value.substring(0, 10);
                    }},

                    //{ field: 'payName', title: '付款方名称', hidden: false},
                    { field: 'money', title: '本次支付金额', hidden: false, formatter: function (value, row, index) {
                        return value == "" ? "" : value + " 元";
                    }}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'Sid', hidden: true},
                    { field: 'id', title: 'Id', hidden: true},
                    { field: 'state', title: 'State', hidden: true},
                    { field: 'operatorId', title: 'OperatorId', hidden: true},
                    { field: 'operateTime', title: 'OperateTime', hidden: true},
                    { field: 'orgId', title: '组织编号', hidden: true},
                    //{ field: 'payBankName', title: '付款方开户行', hidden: false},
                    //{ field: 'payBankAccount', title: '付款方帐号', hidden: false},

                    { field: 'contractName', title: '合同名称', hidden: false},
                    { field: 'payeeName', title: '收款方名称', hidden: false},
                    { field: 'payeeBankName', title: '收款方开户行', hidden: false},
                    { field: 'payeeBankAccount', title: '收款方帐号', hidden: false},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'accountingContent', title: '会计审核',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }
                    },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }  },
                    { field: 'generalManagerContent', title: '副总裁审核意见',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }},
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {

                            return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;

                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"] == "" ? "" : row["cashierName"] + "：" + value;

                        }  },
                    { field: 'projectName', title: '资金支付项目', hidden: true},
                    { field: 'contractNo', title: '合同编号', hidden: true},
                    { field: 'contractMoney', title: '合同金额', hidden: true},
                    { field: 'paidMoney', title: '累计已支付金额', hidden: true},
                    { field: 'applicantId', title: '经办人编号', hidden: true},
                    { field: 'submitterName', title: '经办人名称', hidden: false},
                    { field: 'applicantTime', title: '经办人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true },
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnWaitFinancePayWFACheck' + token,
                    text: '业务审批',
                    iconCls: 'icon-search'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickWaitFinancePayWFACheck();
            }
        });
    }

    // 构造参与列表
    function initFinancePayWFAParticipantTable() {
        var strTableId = 'ParticipantFinancePayWFATable' + token;

        var url = ActionUrl + '/FinancePayWFA_participantList.action';

        $('#' + strTableId).datagrid({
//            title: '资金支付管理',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
                "financePayWFAVO.workflowId":25
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [10, 20, 30],
            pageSize: 10,
            rownumbers: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: 'sid',//排序的列
            loadFilter: function (data) {
                try {
                    //获取数据 返回的Value
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
                    { field: 'time', title: '日期', hidden: false, formatter: function (value, row, index) {
                        //格式化显示的值
                        return value.substring(0, 10);
                    }},
                    //{ field: 'payName', title: '付款方名称', hidden: false},
                    { field: 'money', title: '本次支付金额', hidden: false,
                        formatter: function (value, row, index) {
                            return value == "" ? "" : value + " 元";
                        }}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'Sid', hidden: true},
                    { field: 'id', title: 'Id', hidden: true},
                    { field: 'state', title: 'State', hidden: true},
                    { field: 'operatorId', title: 'OperatorId', hidden: true},
                    { field: 'operateTime', title: 'OperateTime', hidden: true},
                    { field: 'orgId', title: '组织编号', hidden: true},
                    //{ field: 'payBankName', title: '付款方开户行', hidden: false},
                    //{ field: 'payBankAccount', title: '付款方帐号', hidden: false},
                    { field: 'contractName', title: '合同名称', hidden: false},
                    { field: 'payeeName', title: '收款方名称', hidden: false},
                    { field: 'payeeBankName', title: '收款方开户行', hidden: false},
                    { field: 'payeeBankAccount', title: '收款方帐号', hidden: false},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },

                    { field: 'accountingContent', title: '会计审核',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }
                    },

                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }
                    },
                    { field: 'generalManagerContent', title: '副总裁审核',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }},
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {

                            return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;

                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"] == "" ? "" : row["cashierName"] + "：" + value;

                        }  },
                    { field: 'projectName', title: '资金支付项目', hidden: true},
                    { field: 'contractNo', title: '合同编号', hidden: true},
                    { field: 'contractMoney', title: '合同金额', hidden: true},
                    { field: 'paidMoney', title: '累计已支付金额', hidden: true},
                    { field: 'applicantId', title: '经办人编号', hidden: true},
                    { field: 'submitterName', title: '经办人名称', hidden: false},
                    { field: 'applicantTime', title: '经办人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true },
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinancePayWFAELooK' + token,
                    text: '查看',
                    iconCls: 'icon-edit'
                },{
                    id: 'btnFinancePayWFAPrint2' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickFinancePayWFALook();
                onClickFinancePayWFAPrint( 'btnFinancePayWFAPrint2' + token,strTableId);
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickFinancePayWFAAdd() {
        var buttonId = 'btnFinancePayWFAAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initFinancePayWFAWindow({controlString1:"",controlString2:""}, null);
        });
    }

    /**
     * 修改人:周海鸿
     * 修改时间：2015-6-30
     * 修改事件:实现单一附件上传功能
     */

    function onClickFinancePayWFAUpload() {
        var butttonId = "btnFinancePayWFAUpload" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('FinancePayWFATable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var upload = "upload";
                process.beforeClick();
                var url = ActionUrl + '/FinancePayWFA_load.action?financePayWFA.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["submitterName"] = selected.submitterName;
                    data["controlString1"] = selected.controlString1Id;
                    data["financePayWFAVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initFinancePayWFAWindow(data, upload);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 修改事件
     */
    function onClickFinancePayWFAEdit() {
        var buttonId = 'btnFinancePayWFAEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FinancePayWFATable' + token, function (selected) {
                //判断当前数据是否进入业务控制状态
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
                var url = ActionUrl + '/FinancePayWFA_load.action?financePayWFA.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["submitterName"] = selected.submitterName;
                    data["controlString1"] = selected.controlString1Id;
                    data["financePayWFAVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initFinancePayWFAWindow(data,'edit');
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 查看时间
     */
    function onClickFinancePayWFALook() {
        var buttonId = 'btnFinancePayWFAELooK' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ParticipantFinancePayWFATable' + token, function (selected) {
                process.beforeClick();
                var url = ActionUrl + '/FinancePayWFA_load.action?financePayWFA.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["submitterName"] = selected.submitterName;
                    data["controlString1"] = selected.controlString1Id;
                    data["financePayWFAVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initFinancePayWFAWindow(data, "look");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 删除事件
     */
    function onClickFinancePayWFADelete() {
        var buttonId = 'btnFinancePayWFADelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FinancePayWFATable' + token, function (selected) {

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
                fw.confirm('删除确认', '是否确认删除数据？', function () {

                    var url = ActionUrl + '/FinancePayWFA_delete.action?financePayWFA.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('FinancePayWFATable' + token);
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    function initFinancePayWorkflowPanel(data) {
        // 组装URL
        var url = WEB_ROOT + "/include/wf/bizRoute/bizRouteNew_Save.jsp";
        url += "?YWID=" + data["YWID"];
        url += "&WorkflowID=" + data["WorkflowID"];
        url += "&UserID=" + loginUser.getId();
        url += "&RouteListID=" + data['RouteListID'];
        url += "&CurrNodeID=" + data['CurrentNode'];
        url += "&token=" + token;


        $('#panelFinancePayWFAWorkflow' + token).load(
            url,
            null,
            function() {
                //直接引用工作流的js代码，将按钮与功能绑定，并实现编辑控制
                using('../wf/script/BizRouteNewClass.js', function () {
                    // 组装参数，传入token, data, 三个表的ID, 窗口ID和四个按钮ID
                    var table1 = "FinancePayWFATable" + token;// 我的申请列表
                    var table2 = 'WaitFinancePayWFATable' + token;// 等待我审核列表
                    var table3 = 'ParticipantFinancePayWFATable' + token;// 已完成列表
                    var windowId = "FinancePayWFAWindow" + token;// 窗口ID
                    var btn1 = "btnFinancePayWFABizRouteSubmit";// 同意按钮
                    var btn2 = "btnFinancePayWFABizRouteOver";// 完成按钮
                    var btn3 = "btnFinancePayWFABizRouteReject";// 回退按钮
                    var btn4 = "btnFinancePayWFABizRouteCancel";// 中止按钮

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
     * 修改：周海鸿
     时间：2015-7-14
     内容：修改窗口大小
     */
    function initFinancePayWFAWindow(data, btn) {


        data['financePayWFA.operatorId'] = loginUser.getId();
        data['financePayWFA.operatorName'] = loginUser.getName();

        var url = WebPageUrl + '/FinancePayWFANew_Save.jsp?token=' + token;
        var windowId = 'FinancePayWFAWindow' + token;
        fw.window(windowId, '资金支付管理', 650, 450, url, function () {
            fw.textFormatCurrency('contractMoney' + token);
            fw.textFormatCurrency('paidMoney' + token);
            fw.textFormatCurrency('money' + token);
            //判断下拉式否使用
            var temp = false;
            //窗口为查看窗口
            if (btn == "look") {

                $("#btnFinancePayWFASubmit_Start" + token).remove();
                $("#btnFinancePayWFASubmit" + token).remove();
                $("#btnFinancePayWFASubmit_applay" + token).remove();
            } //申请
            else  if(btn == "applay")
            {
                $("#btnFinancePayWFASubmit_Start" + token).remove();
                $("#btnFinancePayWFASubmit" + token).remove();
                onClickFinancePayWFAApplaySubmit(data)
            }
            //判断当前按钮点击状态是否不为检查状态
            else if (btn != "check") {
                //去除业务按钮
                $("#btnFinancePayWFASubmit_Start" + token).remove();
                $("#btnFinancePayWFASubmit_applay" + token).remove();
                //数据提交事件
                onClickFinancePayWFASubmit();
            }
           else {
                //判断下拉式否使用
                temp = true;
                //去除数据调教按钮
                $("#btnFinancePayWFASubmit" + token).remove();
                $("#btnFinancePayWFASubmit_applay" + token).remove();
                //调用业务控制，初始化panel
                initFinancePayWorkflowPanel(data);
                fw.onClickBizSubmit(token, "btnFinancePayWFASubmit_Start" + token,
                    data,"FinancePayWFATable" + token, 'WaitFinancePayWFATable' + token, 'ParticipantFinancePayWFATable' + token, windowId);
            }
            if (btn == "check") {
                //gagaga
                // 结束节点为8
                if (data["CurrentNode"] == "8" || data["CurrentNode"] == 8) {
                    $("#btnFinancePayWFABizRouteSubmit" + token).remove();// 移除同意按钮，保留完成按钮
                } else {
                    $("#btnFinancePayWFABizRouteOver" + token).remove();// 移除完成按钮，保留同意按钮
                }
            } else {
                $("#panelFinancePayWFAWorkflow" + token).remove();
                $("#btnFinancePayWFABizRouteSubmit" + token).remove();
                $("#btnFinancePayWFABizRouteOver" + token).remove();
                $("#btnFinancePayWFABizRouteReject" + token).remove();
                $("#btnFinancePayWFABizRouteCancel" + token).remove();
            }

            if(btn=="upload") {

                $("#btnFinancePayWFASubmit_Start" + token).remove();
                $("#btnFinancePayWFASubmit_applay" + token).remove();
                //数据提交事件
                //附件上传页面按钮状态
                var btnstatus = "";
                // 初始化文件上传
                if (loginUser.getId() != data["financePayWFA.applicantId"]) {
                    btnstatus = "remove";
                }
                fw.initFileUpload(data["financePayWFA.id"], 18830, 'btnUpload' + token, btnstatus, token);
            }else{

                $("#uploadTR" + token).remove();
            }

            //下拉列表
            //获取选中的节点
            var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
            var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"];
            //公司部门二级下拉列表

            fw.doubleDepartmentTrees("controlString1"+token ,"controlString2"+token ,selectString1,selectString2,temp);
            fw.formLoad('formHRLeave' + token, data);


            // 加载数据
            fw.formLoad('formFinancePayWFA' + token, data);

        }, null);
    }
    /**
     *申请数据提交事件
     */
    function onClickFinancePayWFAApplaySubmit(data) {
        var buttonId = "btnFinancePayWFASubmit_applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"]="";
            fw.onclickworkflowApplay(data,process,"FinancePayWFATable" + token, 'WaitFinancePayWFATable' + token, 'ParticipantFinancePayWFATable' + token,'FinancePayWFAWindow' + token);
            provess.afterClick
        });
    }
    /**
     * 数据提交事件
     */
    function onClickFinancePayWFASubmit() {
        var buttonId = 'btnFinancePayWFASubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.CurrencyFormatText('contractMoney' + token);
            fw.CurrencyFormatText('paidMoney' + token);
            fw.CurrencyFormatText('money' + token);
            var formId = 'formFinancePayWFA' + token;
            var url = ActionUrl + '/FinancePayWFA_insertOrUpdate.action?financePayWFAVO.workflowId=25';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('FinancePayWFATable' + token);
                fw.windowClose('FinancePayWFAWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickFinancePayWFASearch() {
        var buttonId = 'btnFinancePayWFASearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'FinancePayWFATable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params['financePayWFAVO_time_Start'] = fw.getFormValue('Search_Time_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params['financePayWFAVO_time_End'] = fw.getFormValue('Search_Time_End' + token, fw.type_form_datebox, fw.type_get_value);
            params['financePayWFAVO.payeeName'] = $('#Search_PayeeName' + token).val();
            params['financePayWFAVO.payName'] = $('#Search_payName' + token).val();
            params['financePayWFAVO.contractName'] = $('#Search_ContractName' + token).val();
            params['financePayWFAVO.contractNo'] = $('#Search_ContractNo' + token).val();


            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickFinancePayWFASearchReset() {
        var buttonId = 'btnFinancePayWFASearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
            $('#Search_Time_Start' + token).datebox('setValue', '');
            $('#Search_Time_End' + token).datebox('setValue', '');
            $('#Search_PayeeName' + token).val('');
            $('#Search_payName' + token).val('');
            $('#Search_ContractName' + token).val('');
            $('#Search_ContractNo' + token).val('');
        });
    }

    /**
     * 业务处理
     * @returns {{initModule: initModule, initModuleWithSelect: initModuleWithSelect}}
     */
    function onClickFinancePayWFACheck() {
        var buttonId = "btnFinancePayWFACheck" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FinancePayWFATable' + token, function (selected) {
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经提交申请不得重复提交");
                    process.afterClick();
                    return;
                }
                process.beforeClick();
                var id=selected.id;
                var url = ActionUrl + '/FinancePayWFA_load.action?financePayWFA.id=' + id;
                fw.post(url, null, function (data) {

                    data["workflowID"] = 25;

                    data["nextNode"] = 2;
                    //设置业务编号
                    data["id"] = id;
                    //设置路由编号
                    data["routeListId"] = selected.routeListId;
                    //设置节点编号
                    data["currentNodeId"] = selected.currentNodeId;

                    data["controlString3"] = selected.controlString3;
                    data["controlString1"] = selected.controlString1Id;
                    data["controlString2"] = selected.controlString2Id;
                    initFinancePayWFAWindow(data,"applay");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });

        })
    }

    /**
     * 审核业务处理
     * @returns {{initModule: initModule, initModuleWithSelect: initModuleWithSelect}}
     */
    function onClickWaitFinancePayWFACheck() {
        var buttonId = "btnWaitFinancePayWFACheck" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('WaitFinancePayWFATable' + token, function (selected) {
                process.beforeClick();
                var url = ActionUrl + '/FinancePayWFA_load.action?financePayWFA.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["WorkflowID"] = 25;
                    data["bizRoute.workflowId"] = 25;
                    data["YWID"] = selected.id;
                    data["RouteListID"] = selected.routeListId;
                    data["CurrentNode"] = selected.currentNodeId;
                    data["submitterName"] = selected.submitterName;
                    data["controlString1"] = selected.controlString1Id;
                    data["controlString2"] = selected.controlString2Id;
                    data["financePayWFAVO.controlString3"] = selected.controlString3;
                    data["currentNodeTitle"] =selected.currentNodeTitle;
                    initFinancePayWFAWindow(data, "check");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });

        })
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initModuleWithSelect: function (callback4SelectIn) {
            var url = WebPageUrl + '/FinancePayWFA_Select.jsp?token=' + token;
            var selectionWindowId = 'SelectWindow' + token;
            fw.window(selectionWindowId, '选择窗口', 1000, 500, url, function () {
                callback4Select = callback4SelectIn;
                initAll();
                $('#FinancePayWFATable' + token).datagrid({toolbar: []});
            }, null);
        }
    };
};