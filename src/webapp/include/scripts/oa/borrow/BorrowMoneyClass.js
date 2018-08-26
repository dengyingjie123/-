/**
 * Created by 章鹏
 * Date 2015-6-5
 */
var BorrowMoneyClass = function (token) {

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询表单
        initBorrowmoneyearchForm();

        // 初始化查询事件
        onClickBorrowmoneyearch();
        // 初始化查询重置事件
        onClickBorrowmoneyearchReset();

        // 初始化表格
        initBorrowMoneyTable();
        //初始化等待审批
        initWaitBorrowMoneyTable();
        //初始换已完成
        initPParticipantborrnowMoneyTable();

    }

    /**
     * 初始化查询表单
     */
    function initBorrowmoneyearchForm() {
        var controlString1Url = WEB_ROOT + "/system/Department_getDepartmentTrees.action";//公司的URL
        fw.combotreeLoad("search_controlString1Id"+token, controlString1Url, "-2");
    }


    // 构造初始化申请表格脚本
    function initBorrowMoneyTable() {
        var strTableId = 'BorrowMoneyTable' + token;
        var url = WEB_ROOT + '/oa/borrow/BorrowMoney_list.action';

        $('#' + strTableId).datagrid({
  //          title: '借款申请',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍候……',
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
                    { field: 'applicantId', title: '申请人', hidden: true, sortable: true},
                    { field: 'applicantName', title: '申请人', hidden: false, sortable: true},
                    { field: 'money', title: '申请金额', sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true, sortable: true},
                    { field: 'id', title: 'id', hidden: true, sortable: true},
                    { field: 'state', title: 'state', hidden: true, sortable: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'applicationPurpose', title: '申请原因', hidden: true, sortable: true},

                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }},
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
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
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},

                    { field: 'currentNodeTitle', title: '当前状态'}
                ]
            ],
            toolbar: [
                {
                    id: 'btnBorrowMoneyAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnBorrowMoneyEdit' + token,
                    text: '修改',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnBorrowMoneyCheck' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                },{
                    id: 'btnBorrowMoneyPrint' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnBorrowMoneyDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickBorrowMoneyAdd();
                onClickBorrowMoneyEdit();
                onClickBorrowMoneyPrint('btnBorrowMoneyPrint' + token,strTableId);
                onClickBorrowMoneyCheck();
                onClickBorrowMoneyDelete();
            }
        });
    }

    // 构造初始化等待审核表格脚本
    function initWaitBorrowMoneyTable() {
        var strTableId = 'WaitborrnowMoneyTable' + token;
        var url = WEB_ROOT + '/oa/borrow/BorrowMoney_Waitlist.action';

        $('#' + strTableId).datagrid({
  //         title: '借款审批',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍候……',
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
                    { field: 'applicantId', title: '申请人', hidden: true, sortable: true},
                    { field: 'applicantName', title: '申请人', hidden: false, sortable: true},
                    { field: 'money', title: '申请金额', sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true, sortable: true},
                    { field: 'id', title: 'id', hidden: true, sortable: true},
                    { field: 'state', title: 'state', hidden: true, sortable: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'applicationPurpose', title: '申请原因', hidden: true, sortable: true},

                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }},
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
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
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},

                    { field: 'currentNodeTitle', title: '当前状态'}
                ]
            ],
            toolbar: [
                {
                    id: 'btnBorrowMoneyWaitCheck' + token,
                    text: '业务审批',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickBorrowMoneyWaitCheck();
            }
        });
    }

    // 构造初始化已完成表格脚本
    function initPParticipantborrnowMoneyTable() {
        var strTableId = 'ParticipantborrnowMoneyTable' + token;
        var url = WEB_ROOT + '/oa/borrow/BorrowMoney_Participantlist.action';

        $('#' + strTableId).datagrid({
 //          title: '借款申请完成',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍候……',
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
                    { field: 'applicantId', title: '申请人', hidden: true, sortable: true},
                    { field: 'applicantName', title: '申请人', hidden: false, sortable: true},
                    { field: 'money', title: '申请金额', sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true, sortable: true},
                    { field: 'id', title: 'id', hidden: true, sortable: true},
                    { field: 'state', title: 'state', hidden: true, sortable: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'applicationPurpose', title: '申请原因', hidden: true, sortable: true},

                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }},
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"] == "" ? "" : row["accountingName"] + "：" + value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"] == "" ? "" : row["financeDirectorName"] + "：" + value;
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
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},

                    { field: 'currentNodeTitle', title: '当前状态'}
                ]
            ],
            toolbar: [
                {
                    id: 'btnBorrowMoneyLook' + token,
                    text: '查看',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnBorrowMoneyPrint2' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickBorrowMoneyLook();
                onClickBorrowMoneyPrint('btnBorrowMoneyPrint2' + token,strTableId);
            }
        });
    }

    function onClickBorrowMoneyWaitCheck() {
        var buttonId = 'btnBorrowMoneyWaitCheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('WaitborrnowMoneyTable' + token, function (selected) {

                process.beforeClick();
              
                var id = selected.id
                var url = WEB_ROOT + '/oa/borrow/BorrowMoney_load.action?BorrowMoney.id=' + id;
                fw.post(url, null, function (data) {

                    //设置工作流编号
                    data["WorkflowID"] = 15;
                    data["bizRoute.workflowId"] = 15;
                    //设置业务编号
                    data["YWID"] =id;
                    //设置路由编号
                    data["RouteListID"] = selected.routeListId;
                    //设置节点编号
                    data["CurrentNode"] = selected.currentNodeId;

                    data["controlString1"] = selected.controlString1Id;
                    data["controlString2"] = selected.controlString2Id;
                    data["BorrowMoneyVO.controlString3"] = selected.controlString3;

                    initBorrowMoneyWindow(data, "check");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    function onClickBorrowMoneyCheck() {
        var buttonId = 'btnBorrowMoneyCheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('BorrowMoneyTable' + token, function (selected) {
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经提交申请不得重复提交");
                    process.afterClick();
                    return;
                }
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/borrow/BorrowMoney_load.action?BorrowMoney.id=' + selected.id;
                fw.post(url, null, function (data) {

                    data["nextNode"] = 2;//下一个节点
                    //设置工作流编号
                    data["workflowID"] = 15;
                    //设置业务编号
                    data["id"] = id;
                    //设置路由编号
                    data["routeListId"] = selected.routeListId;
                    //设置节点编号
                    data["currentNodeId"] = selected.currentNodeId;

                    data["controlString1"] = selected.controlString1Id;
                    data["controlString2"] = selected.controlString2Id;
                    data["controlString3"] = selected.controlString3;
                    initBorrowMoneyWindow(data, "applay");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 添加事件
     */
    function onClickBorrowMoneyAdd() {
        var buttonId = 'btnBorrowMoneyAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initBorrowMoneyWindow({controlString1:"",controlString2:""}, "add");
        });

    }

    /**
     * 修改事件
     */
    function    onClickBorrowMoneyEdit() {
        var buttonId = 'btnBorrowMoneyEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('BorrowMoneyTable' + token, function (selected) {
                process.beforeClick();
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
                var id = selected.id
                var url = WEB_ROOT + '/oa/borrow/BorrowMoney_load.action?BorrowMoney.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["BorrowMoney.money"] = selected.money;
                    data["controlString1"] = selected.controlString1Id;
                    data["borrowMoneyVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initBorrowMoneyWindow(data, "edit");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }
    /*修改：周海鸿
     * 时间2015-7-20
     * 内容：添加报表打印*/
    function onClickBorrowMoneyPrint(buttonId,tableId) {
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableId, function (selected) {
                process.beforeClick();

                var url =WEB_ROOT+"/modules/oa/modelsFiles/BorrowMoneyModels.jsp?id="+selected.id+"&token=" + token;
                window.open(url);

                process.afterClick();  });
        });
    }

    /**
     * 查看事件
     */
    function onClickBorrowMoneyLook() {
        var buttonId = 'btnBorrowMoneyLook' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ParticipantborrnowMoneyTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/borrow/BorrowMoney_load.action?BorrowMoney.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["applicantName"] = selected.applicantName;
                    data["departmentName"] = selected.departmentName;
                    data["BorrowMoney.money"] = selected.money;
                    data["controlString1"] = selected.controlString1Id;
                    data["BorrowMoneyVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initBorrowMoneyWindow(data, "look");
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
    function onClickBorrowMoneyDelete() {
        var buttonId = 'btnBorrowMoneyDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('BorrowMoneyTable' + token, function (selected) {
                process.beforeClick();
                //判断数据是否进入业务控制状态
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
                    var url = WEB_ROOT + '/oa/borrow/BorrowMoney_delete.action?BorrowMoney.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('BorrowMoneyTable' + token);
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
     */
    /**
     * 修改人：周海鴻
     * 修改時間：2015-6-24
     * 修改時間:添加业务流程控制
     * @type {number}
     */

    function initBorrowMoneyWindow(data, windowType) {
        data['borrowMoney.operatorId'] = loginUser.getId();

        var url = WEB_ROOT + '/modules/oa/borrow/BorrowMoney_Save.jsp?token=' + token;
        var windowId = 'borrowMoneyWindow' + token;
        fw.window(windowId, '借款申请', 500, 350, url, function () {
            //判断下拉是否禁用
            var temp=false;

            //窗口为添加
            if (windowType == "add") {
                $("#btnBorrowMoneySubmit_start" + token).remove();//去除业务审批按钮
                $("#btnBorrowMoneySubmit_applay" + token).remove();//去除业务申请按钮
                data["borrowMoney.applicationTime"]=fw.getDateTime();
                data["borrowMoney.applicantName"]=loginUser.getName();
                data['borrowMoney.applicantId'] = loginUser.getId();
            }
            //修改状态
            if(windowType=="edit" ){

                $("#btnBorrowMoneySubmit_start" + token).remove();//去除业务审批按钮
                $("#btnBorrowMoneySubmit_applay" + token).remove();//去除提交按钮
            }
            //申请状态
            if(windowType =="applay"){
                $("#btnBorrowMoneySubmit_start" + token).remove();//去除业务审批按钮
                $("#btnBorrowMoneySubmit" + token).remove();//去除提交按钮
            }
            //窗口为审批状态
            if (windowType == "check") {
                $("#btnBorrowMoneySubmit" + token).remove();//去除提交按钮
                $("#btnBorrowMoneySubmit_applay" + token).remove();//去除申请按钮
                //调用业务控制方法
                fw.onClickBizSubmit(token, "btnBorrowMoneySubmit_start" + token,
                    data, "BorrowMoneyTable" + token, 'WaitborrnowMoneyTable' + token,'ParticipantborrnowMoneyTable' + token,windowId);
               temp=true;
            }
            if(windowType=="look"){

                $("#btnBorrowMoneySubmit_start" + token).remove();//去除业务审批按钮
                $("#btnBorrowMoneySubmit" + token).remove();//去除提交按钮
                $("#btnBorrowMoneySubmit_applay" + token).remove();//去除申请按钮
                //调用业务控制方法
            }
             //提交事件
            onClickBorrowmoneyubmit();
            onclickBorrowMoneyApplay(data);///申请事件

            //下拉列表
            //获取选中的节点
            var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
            var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"]
            //公司部门二级下拉列表

            fw.doubleDepartmentTrees("controlString1"+token ,"controlString2"+token ,selectString1,selectString2,temp);


            // 加载数据
            fw.formLoad('formBorrowMoney' + token, data);

        });
    }

    /**
     * 数据提交事件
     */
    function onClickBorrowmoneyubmit() {
        var buttonId = 'btnBorrowMoneySubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = 'formBorrowMoney' + token;
            var url = WEB_ROOT + '/oa/borrow/BorrowMoney_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('BorrowMoneyTable' + token);
                fw.windowClose('borrowMoneyWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }
    /**
     * 数据申请事件
     */
    function onclickBorrowMoneyApplay(data) {
        var buttonId = 'btnBorrowMoneySubmit_applay' + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"]="";
          fw.onclickworkflowApplay(data,process,"BorrowMoneyTable" + token, 'WaitborrnowMoneyTable' + token,'ParticipantborrnowMoneyTable' + token,'borrowMoneyWindow' + token);
            provess.afterClick
        });
    }

    /**
     * 查询事件
     */
    function onClickBorrowmoneyearch() {
        var buttonId = 'btnBorrnowMoneySearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'BorrowMoneyTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params['borrowMoneyVO.applicantName'] = $('#search_applicantName' + token).val();
            params['borrowMoneyVO.money'] = $('#search_money' + token).val();


            params['borrowMoneyVO_applicationTime_Start'] = fw.getFormValue('search_applicationTime_start' + token, fw.type_form_datebox, fw.type_get_value);
            params['borrowMoneyVO_applicationTime_End'] = fw.getFormValue('search_applicationTime_end' + token, fw.type_form_datebox, fw.type_get_value);
            //部门名称
            params['BorrowMoneyVO.controlString1Id'] = fw.getFormValue('search_controlString1Id' + token, fw.type_form_combotree, fw.type_get_value);

            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickBorrowmoneyearchReset() {
        var buttonId = 'btnBorrnowMoneySearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
            $('#search_applicantName' + token).val('');
            $('#search_money' + token).val('');

            $('#search_applicationTime_start' + token).datebox('setValue', '');
            $('#search_applicationTime_end' + token).datebox('setValue', '');

            fw.combotreeClear('search_controlString1Id' + token);
        });
    }

    return{
        /**
         * boot.js 加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
}