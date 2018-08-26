/**
 * Created by Administrator on 2015/6/26.
 */
/**
 * HRLeaveClass.js 脚本对象
 * 创建一个休假申请审批脚本
 */
var HRLeaveClass = function (token) {


        ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
        /**
         * 初始化主页面控件
         */
        function initAll() {

            // 初始化查询事件
            onClickHRLeaveSearch();
            // 初始化查询重置事件
            onClickHRLeaveSearchReset();

            // 初始化申请表格
            initHRLeaveTable();
            //初始化审核参与
            initHRLeaveTableParticipant();
            //初始化等待审核
            initHRLeavewaitTable();

            // 初始化查询表单
            initHRLeaveSearchForm("Search_LeaveTypeId" + token, '-2');

        }

        /**
         * 初始化查询表单
         */
        function initHRLeaveSearchForm(textid, selectedId) {

            fw.getComboTreeFromKV(textid, "oa_hrleave_hrleaveType", "k", selectedId);
        }


        // 构造初始化申请表格脚本
        function initHRLeaveTable() {
            var strTableId = 'HRLeaveTable' + token;

            var url = WEB_ROOT + '/oa/hr/HRLeave_listHRLeave.action';

            $('#' + strTableId).datagrid({
                //  title: '请假休假申请列表',
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
                        { field: 'operatorId', title: 'operatorId', hidden: true},
                        { field: 'operateTime', title: 'operateTime', hidden: true},
                        { field: 'applicantId', title: '申请人编号', hidden: true},
                        { field: 'oh_applicationName', title: '申请人', hidden: false},
                        { field: 'leaveTypeId', title: '请假类别', hidden: true},
                        { field: 'leaveTypeName', title: '请假类别', hidden: false},
                        { field: 'otherTypeDescription', title: '其他类别描述', hidden: true},
                        { field: 'days', title: '天数',width:50, hidden: false}
                    ]
                ],
                columns: [
                    [
                        { field: 'sid', title: 'sid', hidden: true},
                        { field: 'id', title: 'id', hidden: true},
                        { field: 'state', title: 'state', hidden: true},

                        { field: 'whereToLeave', title: '请假去处', hidden: false},
                        { field: 'startTime', title: '开始时间', hidden: false},
                        { field: 'endTime', title: '结束时间', hidden: false},
                        { field: 'handoverName', title: '工作交接人', hidden: false},
                        { field: 'reason', title: '请假原因', hidden: false},
                        { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                            formatter: function (value, row, index) {
                                return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                            } },
                        { field: 'content1', title: '人力行政',
                            formatter: function (value, row, index) {

                                return  row["name1"] == "" ? "" : row["name1"] + "：" + value;
                            } },
                        { field: 'generalManagerContent', title: '总经理审核内容',
                            formatter: function (value, row, index) {
                                return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;

                            }
                        },
                        { field: 'chargeLeaderContent', title: '分管领导审核意见',
                            formatter: function (value, row, index) {
                                return  row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;


                            }  },
                        { field: 'executiveDirectorContent', title: '执行董事审核意见',
                            formatter: function (value, row, index) {

                                return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;

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
                        id: 'btnHRLeaveAdd' + token,
                        text: '添加',
                        iconCls: 'icon-add'
                    },
                    {
                        id: 'btnHRLeaveEdit' + token,
                        text: '修改',
                        iconCls: 'icon-edit'
                    },
                    {
                        id: 'btnHRLeaveCheck' + token,
                        text: '业务申请',
                        iconCls: 'icon-edit'
                    },{
                        id: 'btnHRLeavePrint' + token,
                        text: '打印',
                        iconCls: 'icon-edit'
                    },
                    {
                        id: 'btnHRLeaveDelete' + token,
                        text: '删除',
                        iconCls: 'icon-cut'
                    }
                ],
                onLoadSuccess: function () {
                    // 初始化事件
                    onClickHRLeaveAdd();
                    onClickHRLeaveEdit();
                    onClickHRLeaveCheck();
                    onClickHRLeavePrint( 'btnHRLeavePrint' + token,strTableId);
                    onClickHRLeaveDelete();
                }
            });
        }

        // 构造初始化审核参与表格脚本
        function initHRLeaveTableParticipant() {
            var strTableId = 'HRLeaveTableParticipant' + token;

            var url = WEB_ROOT + '/oa/hr/HRLeave_listHRLeaveParticipant.action';

            $('#' + strTableId).datagrid({
                //   title: '请假休假审核参与列表',
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
                        { field: 'operatorId', title: 'operatorId', hidden: true},
                        { field: 'operateTime', title: 'operateTime', hidden: true},
                        { field: 'applicantId', title: '申请人编号', hidden: true},
                        { field: 'oh_applicationName', title: '申请人', hidden: false},
                        { field: 'leaveTypeId', title: '请假类别', hidden: true},
                        { field: 'leaveTypeName', title: '请假类别', hidden: false},
                        { field: 'otherTypeDescription', title: '其他类别描述', hidden: true},
                        { field: 'days', title: '天数',width:50, hidden: false}
                    ]
                ],
                columns: [
                    [
                        { field: 'sid', title: 'sid', hidden: true},
                        { field: 'id', title: 'id', hidden: true},
                        { field: 'state', title: 'state', hidden: true},
                        { field: 'whereToLeave', title: '请假去处', hidden: false},
                        { field: 'startTime', title: '开始时间', hidden: false},
                        { field: 'endTime', title: '结束时间', hidden: false},
                        { field: 'handoverName', title: '工作交接人', hidden: false},
                        { field: 'reason', title: '请假原因', hidden: false},
                        { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                            formatter: function (value, row, index) {
                                return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                            } },
                        { field: 'content1', title: '人力行政',
                            formatter: function (value, row, index) {

                                return  row["name1"] == "" ? "" : row["name1"] + "：" + value;
                            } },
                        { field: 'generalManagerContent', title: '总经理审核内容',
                            formatter: function (value, row, index) {
                                return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;

                            }
                        },
                        { field: 'chargeLeaderContent', title: '分管领导审核意见',
                            formatter: function (value, row, index) {
                                return  row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;


                            }  },
                        { field: 'executiveDirectorContent', title: '执行董事审核意见',
                            formatter: function (value, row, index) {

                                return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;

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
                        id: 'btnHRLeaveLook' + token,
                        text: '查看',
                        iconCls: 'icon-edit'
                    } ,{
                        id: 'btnHRLeavePrint2' + token,
                        text: '打印',
                        iconCls: 'icon-edit'
                    }
                ],
                onLoadSuccess: function () {
                    // 初始化事件
                    onClickHRLeaveLook();
                    onClickHRLeavePrint( 'btnHRLeavePrint2' + token,strTableId);
                }
            });
        }

        // 构造初始化等待审核表格脚本
        function initHRLeavewaitTable() {
            var strTableId = 'HRLeavewaitTable' + token;

            var url = WEB_ROOT + '/oa/hr/HRLeave_listHRLeavewait.action';

            $('#' + strTableId).datagrid({
                //title: '请假休假等待审核列表',
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
                        { field: 'operatorId', title: 'operatorId', hidden: true},
                        { field: 'operateTime', title: 'operateTime', hidden: true},
                        { field: 'applicantId', title: '申请人编号', hidden: true},
                        { field: 'oh_applicationName', title: '申请人', hidden: false},
                        { field: 'leaveTypeId', title: '请假类别', hidden: true},
                        { field: 'leaveTypeName', title: '请假类别', hidden: false},
                        { field: 'otherTypeDescription', title: '其他类别描述', hidden: true},
                        { field: 'days', title: '天数',width:50, hidden: false}
                    ]
                ],
                columns: [
                    [
                        { field: 'sid', title: 'sid', hidden: true},
                        { field: 'id', title: 'id', hidden: true},
                        { field: 'state', title: 'state', hidden: true},
                        { field: 'whereToLeave', title: '请假去处', hidden: false},
                        { field: 'startTime', title: '开始时间', hidden: false},
                        { field: 'endTime', title: '结束时间', hidden: false},
                        { field: 'handoverName', title: '工作交接人', hidden: false},
                        { field: 'reason', title: '请假原因', hidden: false},
                        { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                            formatter: function (value, row, index) {
                                return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                            } },
                        { field: 'content1', title: '人力行政',
                            formatter: function (value, row, index) {

                                return  row["name1"] == "" ? "" : row["name1"] + "：" + value;
                            } },
                        { field: 'generalManagerContent', title: '总经理审核内容',
                            formatter: function (value, row, index) {
                                return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;

                            }
                        },
                        { field: 'chargeLeaderContent', title: '分管领导审核意见',
                            formatter: function (value, row, index) {
                                return  row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;


                            }  },
                        { field: 'executiveDirectorContent', title: '执行董事审核意见',
                            formatter: function (value, row, index) {

                                return  row["executiveDirectorName"] == "" ? "" : row["executiveDirectorName"] + "：" + value;

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
                        id: 'btnwaitHRLeaveCheck' + token,
                        text: '业务审批',
                        iconCls: 'icon-edit'
                    }
                ],
                onLoadSuccess: function () {
                    // 初始化事件
                    onClickwaitHRLeaveCheck();
                }
            });
        }

        /**
         * 添加事件
         */
        function onClickHRLeaveAdd() {
            var buttonId = 'btnHRLeaveAdd' + token;
            fw.bindOnClick(buttonId, function (process) {
                var add = "add";
                initHRLeaveWindow({controlString1:"",controlString2:""}, add);
            });
        }

        /**
         * 打印事件、
         */
        /*修改：周海鸿
        * 时间：2015-7-20
        * 内容： 添加打印事件*/
        function onClickHRLeavePrint(buttonId,tableid) {
            //var buttonId = 'btnHRLeavePrint' + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected(tableid, function (selected) {
                    //判断数据是否进入业务流状态

                    process.beforeClick();

                    var url =WEB_ROOT+"/modules/oa/modelsFiles/HRLeaceModels.jsp?id="+selected.id+"&token=" + token;
                    window.open(url);

                    process.afterClick();
                } );
            });
        }
        /**
         * 修改事件
         */
        function onClickHRLeaveEdit() {
            var buttonId = 'btnHRLeaveEdit' + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected('HRLeaveTable' + token, function (selected) {
                    //判断数据是否进入业务流状态
                    //判断选择的数据是否处在工作流状态中
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

                    var id = selected.id
                    var url = WEB_ROOT + '/oa/hr/HRLeave_load.action?hrleave.id=' + selected.id;
                    fw.post(url, null, function (data) {
                        var edit = "edit";
                        data["oh_applicationName"] = selected.oh_applicationName;

                      data["controlString1"] = selected.controlString1Id;
                        data["hrleaveVO.controlString3"] = selected.controlString3;
                        data["controlString2"] = selected.controlString2Id;
                        initHRLeaveWindow(data, edit);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                });
            });
        }

        /**
         * 查看事件
         */
        function onClickHRLeaveLook() {
            var buttonId = 'btnHRLeaveLook' + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected('HRLeaveTableParticipant' + token, function (selected) {
                    //判断数据是否进入业务流状态
                    process.beforeClick();

                    var id = selected.id
                    var url = WEB_ROOT + '/oa/hr/HRLeave_load.action?hrleave.id=' + selected.id;
                    fw.post(url, null, function (data) {
                        var look = "look";
                        data["oh_applicationName"] = selected.oh_applicationName;

                      data["controlString1"] = selected.controlString1Id;
                        data["hrleaveVO.controlString3"] = selected.controlString3;
                        data["controlString2"] = selected.controlString2Id;
                        initHRLeaveWindow(data, look);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                });
            });
        }

        /**
         * 业务审批事件
         */
        function onClickwaitHRLeaveCheck() {
            var buttonId = 'btnwaitHRLeaveCheck' + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected('HRLeavewaitTable' + token, function (selected) {
                    process.beforeClick();
                    var id = selected.id
                    var url = WEB_ROOT + '/oa/hr/HRLeave_load.action?hrleave.id=' + selected.id;
                    fw.post(url, null, function (data) {

                        data["oh_applicationName"] = selected.oh_applicationName;
                        //设置窗口审核状态
                        var check = 'check';
                        //设置所有业务数据
                        data["WorkflowID"] = 11;
                        data["bizRoute.workflowId"] = 11;
                        data["YWID"] = selected.id;
                        data["RouteListID"] = selected.routeListId;
                        data["CurrentNode"] = selected.currentNodeId;
                      data["controlString1"] = selected.controlString1Id;
                        data["hrleaveVO.controlString3"] = selected.controlString3;
                        data["controlString2"] = selected.controlString2Id;
                        var check = "check";
                        initHRLeaveWindow(data, check);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                });
            });
        }

        /**
         * 业务审批事件
         */
        function onClickHRLeaveCheck() {
            var buttonId = 'btnHRLeaveCheck' + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected('HRLeaveTable' + token, function (selected) {
                    process.beforeClick();
                    if ("申请" != selected.currentNodeTitle) {
                        fw.alert("警告", "该数据已经提交申请不得重复提交");
                        process.afterClick();
                        return;
                    }
                    var id = selected.id
                    var url = WEB_ROOT + '/oa/hr/HRLeave_load.action?hrleave.id=' + selected.id;
                    fw.post(url, null, function (data) {

                        data["oh_applicationName"] = selected.oh_applicationName;
                        data["workflowID"] =11;

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


                        initHRLeaveWindow(data, "applay");
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
        function onClickHRLeaveDelete() {
            var buttonId = 'btnHRLeaveDelete' + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected('HRLeaveTable' + token, function (selected) {  //判断数据是否进入业务流状态
                    //判断选择的数据是否处在工作流状态中
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

                        var url = WEB_ROOT + '/oa/hr/HRLeave_delete.action?hrleave.sid=' + selected.sid;
                        fw.post(url, null, function (data) {
                            process.afterClick();
                            fw.datagridReload('HRLeaveTable' + token);
                        }, null);
                    }, function () {
                        process.afterClick();
                    });
                });
            });
        }

        /**
         * 初始化弹出窗口
         * @param data  数据列表
         * @param windosWtstua 窗口状态
         */
        function initHRLeaveWindow(data, windowStatus) {

            // fw.alertReturnValue(data);

            data['hrleave.operatorId'] = loginUser.getId();
            data['hrleave.operatorName'] = loginUser.getName();

            //窗口状态为添加
            if (windowStatus == "add") {
                data['oh_applicationName'] = loginUser.getName();
                data['hrleave.applicantId'] = loginUser.getId();
            }

            var url = WEB_ROOT + '/modules/oa/hr/HRLeave_Save.jsp?token=' + token;
            var windowId = 'HRLeaveWindow' + token;

            fw.window(windowId, '请假休假', 450, 500, url, function () {
                //根据窗口用来控制下拉框是否禁用
                 var temp = false;
                //窗口状态为查看状态时
                if (windowStatus == "look") {
                    //移除所有可以看的按钮
                    $("#btnHRLeaveSubmit_start" + token).remove();
                    $("#btnHRLeaveSubmit_applay" + token).remove();
                    $("#btnHRLeaveSubmit" + token).remove();
                }

                // 加载类型数据
                initHRLeaveSearchForm('leaveTypeId' + token, data['hrleave.leaveTypeId']);
                //窗口状态为check时
                if (windowStatus == 'check') {
                    $("#btnHRLeaveSubmit" + token).remove();
                    $("#btnHRLeaveSubmit_applay" + token).remove();
                    //业务审核页面
                    fw.onClickBizSubmit(token, "btnHRLeaveSubmit_start" + token,
                        data, "HRLeaveTable" + token,'HRLeaveTableParticipant' + token, 'HRLeavewaitTable' + token, windowId);
                    temp = true;
                }
                else if (windowStatus == 'applay'){

                    $("#btnHRLeaveSubmit_start" + token).remove();
                    $("#btnHRLeaveSubmit" + token).remove();    // 申请
                    onClickInformationSubmittedApplay(data,windowId);
                }
                //窗口状态不为check时
                else if (windowStatus != 'check') {
                    $("#btnHRLeaveSubmit_start" + token).remove();
                    $("#btnHRLeaveSubmit_applay" + token).remove();
                    //初始化选择任务接收人
                    initReimburseName();
                    //提交事件
                    onClickHRLeaveSubmit();
                    temp = false;
                }

                //下拉列表
                //获取选中的节点
                var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
                var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"]
                //公司部门二级下拉列表

                fw.doubleDepartmentTrees("controlString1"+token ,"controlString2"+token ,selectString1,selectString2,temp);
                fw.formLoad('formHRLeave' + token, data);

            });
        }
    /**
     *
     * 申请
     * @param data
     * @param windowid
     */
    function onClickInformationSubmittedApplay(data,windowid) {
        var buttonId = "btnHRLeaveSubmit_applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"]="";
            fw.onclickworkflowApplay(data,process,"HRLeaveTable" + token,'HRLeaveTableParticipant' + token, 'HRLeavewaitTable' + token,windowid);
            provess.afterClick
        });
    }
        /**
         * 数据提交事件
         */
        function onClickHRLeaveSubmit() {
            var buttonId = 'btnHRLeaveSubmit' + token;
            fw.bindOnClick(buttonId, function (process) {
                var formId = 'formHRLeave' + token;
                var url = WEB_ROOT + '/oa/hr/HRLeave_insertOrUpdate.action';
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload('HRLeaveTable' + token);
                    fw.windowClose('HRLeaveWindow' + token);
                }, function () {
                    process.afterClick();
                });
            });
        }

        /**
         * 查询事件
         */
        function onClickHRLeaveSearch() {
            var buttonId = 'btnHRLeaveSearchSubmit' + token;
            fw.bindOnClick(buttonId, function (process) {
                var strTableId = 'HRLeaveTable' + token;
                var params = $('#' + strTableId).datagrid('options').queryParams;

                params['hrleaveVO.applicantId'] = $('#Search_ApplicantId' + token).val();
                params['hrleaveVO.leaveTypeId'] = fw.getFormValue('Search_LeaveTypeId' + token, fw.type_form_combotree, fw.type_get_value);
                params['hrleaveVO_days_Start'] = $('#Search_Days_Start' + token).val();
                params['hrleaveVO_days_End'] = $('#Search_Days_End' + token).val();
                params['hrleaveVO_startTime_Start'] = fw.getFormValue('Search_StartTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
                params['hrleaveVO_startTime_End'] = fw.getFormValue('Search_StartTime_End' + token, fw.type_form_datebox, fw.type_get_value);
                params['hrleaveVO.reason'] = $('#Search_Reason' + token).val();


                $('#' + strTableId).datagrid('load');
            });
        }

        /**
         *初始化选择人物接收人
         *
         */
        function initReimburseName() {
            //设置按钮ID
            var textID = "#handoverName" + token;
            //按钮点击事件
            $(textID).bind('click', function () {
                //加载用户选择脚本
                using(SCRIPTS_ROOT + '/system/UserClass(Discard).js', function () {
                    var userClass = new UserClass(token);
                    userClass.windowOpenUserSelection(true, token + 1, function (row) {
                        //获取选择的用户名字 ，ID
                        $("#handoverName" + token).val(row[0].name);
                    });

                })
            })
        }

        /**
         * 查询重置事件
         */
        function onClickHRLeaveSearchReset() {
            var buttonId = 'btnHRLeaveSearchReset' + token;
            fw.bindOnClick(buttonId, function (process) {
                // 清空时间文本框
                $('#Search_ApplicantId' + token).val('');
                $('#Search_LeaveTypeId' + token).val('');
                $('#Search_Days_Start' + token).val('');
                $('#Search_Days_End' + token).val('');
                $('#Search_StartTime_Start' + token).datebox('setValue', '');
                $('#Search_StartTime_End' + token).datebox('setValue', '');
                $('#Search_Reason' + token).val('');
                fw.combotreeClear('Search_LeaveTypeId' + token);
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
