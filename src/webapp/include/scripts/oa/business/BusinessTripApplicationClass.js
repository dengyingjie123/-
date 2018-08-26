/**
 * Created by Administrator on 2015/7/14.
 */
//出差申请
var BusinessTripApplicationClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickBusinessTripApplicationSearch();
        // 初始化查询重置事件
        onClickBusinessTripApplicationSearchReset();

        // 初始化表格
        initBusinessTripApplicationTable();
        /*修改：周海鸿
         * 时间：2015-7-15
         * 内容：添加初始化业务流控制列表模块*/
        //初始化等待审批表格
        initWaitBusinessTripApplicationTable();
        //初始化已完成表格
        initParticipantBusinessTripApplicationTable();


        // 初始化查询表单
        initBusinessTripApplicationSearchForm();


        // 初始化选择窗口按钮
        onClcikSelectDone()
    }

    /**
     * 初始化查询表单
     */
    function initBusinessTripApplicationSearchForm() {
        fw.buildCompanyCombotree('Search_department' + token, -2, function () {
        });
    }

    /**
     * 初始化下拉列表项
     */
    function initStatusTree(url, combotreeId, selectIndexId) {
        fw.combotreeLoad(combotreeId, url, selectIndexId);
    }

    function onClcikSelectDone() {
        fw.bindOnClick('btnSelect' + token, function (press) {
            var strTableId = 'BusinessTripApplicationTable' + token;
            var data = $('#' + strTableId).datagrid('getSelected');
            if (fw.checkIsFunction(callback4Select)) {
                callback4Select(data);
            }
            fwCloseWindow('SelectWindow' + token);
        });
    }

    // 构造初始化申请表格
    function initBusinessTripApplicationTable() {
        var strTableId = 'BusinessTripApplicationTable' + token;
        var url = WEB_ROOT + '/oa/business/BusinessTripApplication_list.action';
        $('#' + strTableId).datagrid({
//            title: '列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
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
                    { field: 'userName', title: '出差人', hidden: false},
                    { field: 'departmentName', title: '部门', hidden: true},
                    { field: 'applicationTime', title: '申请时间', hidden: false},
                    { field: 'planFate', title: '计划天数', hidden: false},
                    { field: 'actualFate', title: '实际天数', hidden: false},
                    { field: 'expenseBudge', title: '费用预算', hidden: false},
                    { field: 'expenseActual', title: '实际费用', hidden: false},
                    { field: 'businessAddress', title: '出差地点', hidden: false}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true},
                    { field: 'id', title: 'id', hidden: true},
                    { field: 'state', title: 'state', hidden: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true},
                    { field: 'userId', title: '出差人编号', hidden: true},
                    { field: 'departmentId', title: '部门', hidden: true},
                    { field: 'purpose', title: '出差原因', hidden: true},
                    { field: 'evections', title: '出差人员', hidden: false},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'content1', title: '人力行政',
                        formatter: function (value, row, index) {

                            return  row["name1"] == "" ? "" : row["name1"] + "：" + value;
                        } },
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }  },
                    { field: 'chargeLeaderContent', title: '分管领导审核意见',
                        formatter: function (value, row, index) {

                            return  row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;

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

                    { field: 'operatorSign', title: '经办人签字', hidden: false},
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnBusinessTripApplicationAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnBusinessTripApplicationEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnBusinessTripApplicationPrint' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnBusinessTripApplicationEditCheck' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnBusinessTripApplicationDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickBusinessTripApplicationAdd();
                onClickBusinessTripApplicationEdit();
                onClickBusinessTripApplicationCheck();
                onClickBusinessTripApplicationPrint('btnBusinessTripApplicationPrint' + token,strTableId);
                onClickBusinessTripApplicationDelete();
            }
        });
    }

    /*修改：周海鸿
     * 时间：2015-7-15
     * 内容：添加初始化业务流控制列表模块*/
    //构造初始化等待审批表格
    function initWaitBusinessTripApplicationTable() {
        var strTableId = 'WaitBusinessTripApplicationTable' + token;
        var url = WEB_ROOT + '/oa/business/BusinessTripApplication_Waitlist.action';
        $('#' + strTableId).datagrid({
//            title: '列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
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
                    { field: 'userName', title: '出差人', hidden: false},
                    { field: 'departmentName', title: '部门', hidden: true},
                    { field: 'applicationTime', title: '申请时间', hidden: false},
                    { field: 'planFate', title: '计划天数', hidden: false},
                    { field: 'actualFate', title: '实际天数', hidden: false},
                    { field: 'expenseBudge', title: '费用预算', hidden: false},
                    { field: 'expenseActual', title: '实际费用', hidden: false},
                    { field: 'businessAddress', title: '出差地点', hidden: false}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true},
                    { field: 'id', title: 'id', hidden: true},
                    { field: 'state', title: 'state', hidden: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true},
                    { field: 'userId', title: '出差人编号', hidden: true},
                    { field: 'departmentId', title: '部门', hidden: true},
                    { field: 'purpose', title: '出差原因', hidden: true},
                    { field: 'evections', title: '出差人员', hidden: false},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'content1', title: '人力行政',
                        formatter: function (value, row, index) {

                            return  row["name1"] == "" ? "" : row["name1"] + "：" + value;
                        } },
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }  },
                    { field: 'chargeLeaderContent', title: '分管领导审核意见',
                        formatter: function (value, row, index) {

                            return  row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;

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

                    { field: 'operatorSign', title: '经办人签字', hidden: false},
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnWaitBusinessTripApplicationEditCheck' + token,
                    text: '业务审批',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {

                onClickWaitBusinessTripApplicationCheck();
            }
        });
    }

    //构造初始化已完成表格
    function initParticipantBusinessTripApplicationTable() {
        var strTableId = 'ParticipantBusinessTripApplicationTable' + token;
        var url = WEB_ROOT + '/oa/business/BusinessTripApplication_Participantlist.action';
        $('#' + strTableId).datagrid({
//            title: '列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
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
                    { field: 'userName', title: '出差人', hidden: false},
                    { field: 'departmentName', title: '部门', hidden: true},
                    { field: 'applicationTime', title: '申请时间', hidden: false},
                    { field: 'planFate', title: '计划天数', hidden: false},
                    { field: 'actualFate', title: '实际天数', hidden: false},
                    { field: 'expenseBudge', title: '费用预算', hidden: false},
                    { field: 'expenseActual', title: '实际费用', hidden: false},
                    { field: 'businessAddress', title: '出差地点', hidden: false}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true},
                    { field: 'id', title: 'id', hidden: true},
                    { field: 'state', title: 'state', hidden: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true},
                    { field: 'userId', title: '出差人编号', hidden: true},
                    { field: 'departmentId', title: '部门', hidden: true},
                    { field: 'purpose', title: '出差原因', hidden: true},
                    { field: 'evections', title: '出差人员', hidden: false},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'content1', title: '人力行政',
                        formatter: function (value, row, index) {

                            return  row["name1"] == "" ? "" : row["name1"] + "：" + value;
                        } },
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
                        }  },
                    { field: 'chargeLeaderContent', title: '分管领导审核意见',
                        formatter: function (value, row, index) {

                            return  row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;

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

                    { field: 'operatorSign', title: '经办人签字', hidden: false},
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnBusinessTripApplicationLook' + token,
                    text: '查看',
                    iconCls: 'icon-edit'
                },{
                    id: 'btnBusinessTripApplicationPrint2' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickBusinessTripApplicationLook();
                onClickBusinessTripApplicationPrint('btnBusinessTripApplicationPrint2' + token,strTableId);
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickBusinessTripApplicationAdd() {
        var buttonId = 'btnBusinessTripApplicationAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initBusinessTripApplicationWindow({controlString1:"",controlString2:""}, "add");
        });
    }

    /**
     * 修改事件
     */
    function onClickBusinessTripApplicationEdit() {
        var buttonId = 'btnBusinessTripApplicationEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('BusinessTripApplicationTable' + token, function (selected) {
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
                var id = selected.id
                var url = WEB_ROOT + '/oa/business/BusinessTripApplication_load.action?businessTripApplication.id=' + selected.id;
                fw.post(url, null, function (data) {

                    data["controlString1"] = selected.controlString1Id;
                    data["businessTripApplicationVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initBusinessTripApplicationWindow(data, "edit");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 打印事件
     */

    /*修改：周海鸿
     * 时间：2015-7-20
     * 内容： 添加打印事件*/
    function onClickBusinessTripApplicationPrint(buttonId,tableid) {
        //var buttonId = 'btnBusinessTripApplicationPrint' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableid, function (selected) {

                process.beforeClick();

                var url = WEB_ROOT + "/modules/oa/modelsFiles/BusinessTripApplicationModels.jsp?id=" + selected.id + "&token=" + token;
                window.open(url);

                process.afterClick();
            });
        });
    }

    /**
     *  * 修改 :周海鸿
     * 时间：2015-7-15
     * 内容：添加查看事件
     */
    function onClickBusinessTripApplicationLook() {
        var buttonId = 'btnBusinessTripApplicationLook' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ParticipantBusinessTripApplicationTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/business/BusinessTripApplication_load.action?businessTripApplication.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["controlString1"] = selected.controlString1Id;
                    data["businessTripApplicationVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initBusinessTripApplicationWindow(data, "look");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /*
     * 修改 :周海鸿
     * 时间：2015-7-15
     * 内容：添加审核事件
     * */
    function onClickBusinessTripApplicationCheck() {
        var buttonId = 'btnBusinessTripApplicationEditCheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('BusinessTripApplicationTable' + token, function (selected) {
                process.beforeClick();
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经提交申请不得重复提交");
                    process.afterClick();
                    return;
                }
                var id = selected.id
                var url = WEB_ROOT + '/oa/business/BusinessTripApplication_load.action?businessTripApplication.id=' + selected.id;
                fw.post(url, null, function (data) {
                    //设置所有业务数据
                    data["workflowID"] =12;

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
                    data["currentNodeTitle"] =selected.currentNodeTitle;
                    //设置窗口审核状态
                    initBusinessTripApplicationWindow(data, "applay");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /*
     * 修改 :周海鸿
     * 时间：2015-7-15
     * 内容：添加审核事件
     * */
    function onClickWaitBusinessTripApplicationCheck() {
        var buttonId = 'btnWaitBusinessTripApplicationEditCheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('WaitBusinessTripApplicationTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/business/BusinessTripApplication_load.action?businessTripApplication.id=' + selected.id;
                fw.post(url, null, function (data) {

                    //设置所有业务数据
                    data["WorkflowID"] = 12;
                    data["bizRoute.workflowId"] = 12;
                    data["YWID"] = selected.id;
                    data["RouteListID"] = selected.routeListId;
                    data["CurrentNode"] = selected.currentNodeId;
                    data["controlString1"] = selected.controlString1Id;
                    data["controlString2"] = selected.controlString2Id;
                    data["businessTripApplicationVO.controlString3"] = selected.controlString3;
                    data["currentNodeTitle"] =selected.currentNodeTitle;
                    //设置窗口审核状态
                    var check = "check";
                    initBusinessTripApplicationWindow(data, check);
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
    function onClickBusinessTripApplicationDelete() {
        var buttonId = 'btnBusinessTripApplicationDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('BusinessTripApplicationTable' + token, function (selected) {
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
                    var url = WEB_ROOT + '/oa/business/BusinessTripApplication_delete.action?businessTripApplication.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('BusinessTripApplicationTable' + token);
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }


    /**
     * 初始化弹出窗口
     * @param data
     * 修改 :周海鸿
     * 时间：2015-7-15
     * 内容：添加按钮状态参数、实现审核、查看业务流程
     */
    function initBusinessTripApplicationWindow(data, BtnStatus) {


        data['businessTripApplication.operatorId'] = loginUser.getId();
        data['businessTripApplication.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/oa/business/BusinessTripApplication_Save.jsp?token=' + token;

        var windowId = 'BusinessTripApplicationWindow' + token;
        fw.window(windowId, '出差申请', 450, 550, url, function () {
            fw.textFormatCurrency('expenseBudge' + token);
            fw.textFormatCurrency('expenseActual' + token);

            //判断下列是否固定
        var temp =false;
            initUserName();
            //窗口状态为查看状态时
            if ("look" == BtnStatus) {
                $("#btnBusinessSubmit_start" + token).remove();//移除业务审批按钮
                $("#btnBusinessSubmit_applay" + token).remove();//移除业务审批按钮
                $("#btnBusinessSubmit" + token).remove();//移除提交按钮
            }
            //按钮状态为检查状态
            else if ("check" == BtnStatus) {
                temp=true;
                $("#btnBusinessSubmit" + token).remove();//移除提交按钮
                $("#btnBusinessSubmit_applay" + token).remove();//移除提交按钮
                //业务审核页面
                fw.onClickBizSubmit(token, "btnBusinessSubmit_start" + token,
                    data, "BusinessTripApplicationTable" + token,
                        'WaitBusinessTripApplicationTable' + token, 'ParticipantBusinessTripApplicationTable' + token, windowId);

            }  else if ("applay" == BtnStatus) {
                temp=true;
                $("#btnBusinessSubmit" + token).remove();//移除提交按钮
                $("#btnBusinessSubmit_start" + token).remove();//移除业务审批按钮

                onClickBusinessTripApplicationApplay(data,windowId);
            } else if ("add" == BtnStatus || "edit" == BtnStatus) {
                if ("add" == BtnStatus) {

                    data['businessTripApplication.operatorSign'] = loginUser.getName();
                }
                $("#btnBusinessSubmit_start" + token).remove();//移除业务审批按钮
                $("#btnBusinessSubmit_applay" + token).remove();//移除业务审批按钮

            }

            //提交事件
            onClickBusinessTripApplicationSubmit();
            //下拉列表
            //获取选中的节点
            var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
            var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"]
            //公司部门二级下拉列表

            fw.doubleDepartmentTrees("controlString1"+token ,"controlString2"+token ,selectString1,selectString2,temp);


            // 加载数据
            fw.formLoad('formBusinessTripApplication' + token, data);

        });
    }
    /**
     *
     * 申请
     * @param data
     * @param windowid
     */
    function onClickBusinessTripApplicationApplay(data,windowid) {
        var buttonId = "btnBusinessSubmit_applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"]="";
            fw.onclickworkflowApplay(data,process, "BusinessTripApplicationTable" + token,
                'WaitBusinessTripApplicationTable' + token, 'ParticipantBusinessTripApplicationTable' + token,windowid);
            provess.afterClick
        });
    }

    /**
     *
     初始化选择出差人
     */
    function initUserName() {
        //设置按钮ID
        var textID = "#userName" + token;
        //按钮点击事件
        $(textID).bind('click', function () {
            //加载用户选择脚本
            using(SCRIPTS_ROOT + '/system/UserClass(Discard).js', function () {
                var userClass = new UserClass(token);
                userClass.windowOpenUserSelection(true, token, function (row) {
                    //获取选择的用户名字 ，ID
                    $("#userName" + token).val(row[0].name);
                    $("#userId" + token).val(row[0].id);
                    //设置用户部门查询URL
                    var orgNameUrl = WEB_ROOT + "/system/User_getUserForDepartmentName.action?user.id=" + row[0].id;
                    //请求获取制定用户的部门信息
                    fw.post(orgNameUrl, null, function (orgData) {
                        //将部门信息设置到表单中
                        $("#departmentName" + token).val(orgData["name"]);
                        $("#departmentId" + token).val(orgData["id"]);
                    })
                });

            })
        });
    }


    /**
     * 数据提交事件
     */
    function onClickBusinessTripApplicationSubmit() {
        var buttonId = 'btnBusinessSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = 'formBusinessTripApplication' + token;
            var url = WEB_ROOT + '/oa/business/BusinessTripApplication_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                fw.CurrencyFormatText('expenseBudge' + token);
                fw.CurrencyFormatText('expenseActual' + token);
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('BusinessTripApplicationTable' + token);
                fw.windowClose('BusinessTripApplicationWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickBusinessTripApplicationSearch() {
        var buttonId = 'btnBusinessTripApplicationSearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'BusinessTripApplicationTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            //获取是减控件的值
            params["businessTripApplicationVO.userName"] = $("#Search_userId" + token).val();
            params["businessTripApplicationVO.departmentId"] = fw.getFormValue('Search_department' + token, fw.type_form_combotree, fw.type_get_value);
            params["businessTripApplicationVO.expenseBudge"] = $("#Search_expenseBudge" + token).val();
            params["businessTripApplicationVO.expenseActual"] = $("#Search_expenseActual" + token).val();
            params["businessTripApplicationVO.operatorSign"] = $("#Search_operatorSign" + token).val();
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickBusinessTripApplicationSearchReset() {
        var buttonId = 'btnBusinessTripApplicationSearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#Search_userId' + token).val('');
            $('#Search_expenseBudge' + token).val('');
            $('#Search_expenseActual' + token).val('');
            $('#Search_operatorSign' + token).val('');
            fw.combotreeClear('Search_department' + token);
        });
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
};
