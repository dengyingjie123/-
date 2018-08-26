/**
 * Created by Administrator on 2015/5/29.
 */
/**
 * InformationSubmittedClass.js 脚本对象
 */
var InformationSubmittedClass = function (token) {


    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickInformationSubmittedSearch();
        // 初始化查询重置事件
        onClickInformationSubmittedSearchReset();

        // 初始化表格
        initInformationSubmittedTable();
        //审核列列表
        initWaitInformationSubmittedTable();
        //我参与审核列表
        initParticipantInformationSubmittedTable();
        // 初始化查询表单
        initInformationSubmittedSearchForm();

        // 初始化选择窗口按钮
    }


    /**
     * 初始化查询表单
     */
    function initInformationSubmittedSearchForm() {
        var url = WEB_ROOT + "/system/Department_list.action";
        initStatusTree(url, '#Search_Department' + token, -2);//主办部门
//        initStatusTree(url, '#Search_MainOrg' + token, -2);//主送单位
//        initStatusTree(url, '#Search_OtherOrg' + token, -2);//抄送单位

//        var ur2 = WEB_ROOT + '/oa/Information/InformationSubmitted_getUsers.action';
//        initStatusTree(ur2,"#Search_HandlingId"+token,-2);//经办人
        //initStatusTree(ur2 ,"#Search_TransferOperatorId"+token,-2);//原件移交人
        //initStatusTree(ur2,"#Search_TransferRecipient"+token,-2);//原件移交接收人
    }

    /**
     * 初始化下拉列表项
     */
    function initStatusTree(url, combotreeId, selectIndexId) {
        fw.combotreeLoad(combotreeId, url, selectIndexId);
    }


    // 构造申请表格脚本
    function initInformationSubmittedTable() {
        var strTableId = 'InformationSubmittedTable' + token;
        var url = WEB_ROOT + '/oa/Information/InformationSubmitted_list.action';

        $('#' + strTableId).datagrid({
            //  title: '对外资料报送',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            fitColumns: false,
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
                    { field: 'department', title: '经办部门', hidden: true},
                    { field: 'departmentName', title: '经办部门', hidden: false, sortable: true},
                    { field: 'handlingId', title: '经办人', hidden: true},
                    { field: 'handlingName', title: '经办人', hidden: false, sortable: true},
                    { field: 'mainOrg', title: '主送单位', hidden: false, sortable: true},
                    { field: 'otherOrg', title: '抄送单位', hidden: false, sortable: true},
                    { field: 'reason', title: '报送事由', hidden: true},
                    { field: 'content', title: '报送内容', hidden: true},
                    { field: 'submitTime', title: '报送时间', hidden: false, sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'Sid', hidden: true},
                    { field: 'id', title: 'Id', hidden: true},
                    { field: 'state', title: 'State', hidden: true},
                    { field: 'operatorId', title: 'OperatorId', hidden: true},
                    { field: 'operateTime', title: 'OperateTime', hidden: true},
                    { field: 'transferTime', title: '原件移交时间', hidden: false, sortable: true},
                    { field: 'transferOperatorId', title: '原件移交人', hidden: false, sortable: true},
                    { field: 'transferRecipient', title: '原件移交接收人', hidden: false, sortable: true},
                    { field: 'revertTime', title: '原件归还时间', hidden: false, sortable: true},
                    { field: 'revertOperatorId', title: '原件归还人', hidden: false, sortable: true},
                    { field: 'revertRecipientId', title: '原件归还接收人', hidden: false, sortable: true},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }  },

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
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},
                    { field: 'currentNodeTitle', title: '当前状态' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnInformationSubmittedAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnInformationSubmittedEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnInformationSubmittedReturn' + token,
                    text: '资料归还',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnInformationSubmittedCheck' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnInformationSubmittedPrint' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnInformationSubmittedDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickInformationSubmittedAdd();
                onClickInformationSubmittedEdit();
                onClickInformationSubmittedReturn();
                onClickInformationSubmittedCheck();
                onClickInformationSubmittedDelete();
                onClickInformationSubmittedPrint( 'btnInformationSubmittedPrint' + token,strTableId);
            }
        });
    }

    // 构造审核表格脚本
    function initWaitInformationSubmittedTable() {
        var strTableId = 'WaitInformationSubmittedTable' + token;
        var url = WEB_ROOT + '/oa/Information/InformationSubmitted_Waitlist.action';

        $('#' + strTableId).datagrid({
            //  title: '对外资料报送',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            fitColumns: false,
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
                    { field: 'department', title: '经办部门', hidden: true},
                    { field: 'departmentName', title: '经办部门', hidden: false, sortable: true},
                    { field: 'handlingId', title: '经办人', hidden: true},
                    { field: 'handlingName', title: '经办人', hidden: false, sortable: true},
                    { field: 'mainOrg', title: '主送单位', hidden: false, sortable: true},
                    { field: 'otherOrg', title: '抄送单位', hidden: false, sortable: true},
                    { field: 'reason', title: '报送事由', hidden: true},
                    { field: 'content', title: '报送内容', hidden: true},
                    { field: 'submitTime', title: '报送时间', hidden: false, sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'Sid', hidden: true},
                    { field: 'id', title: 'Id', hidden: true},
                    { field: 'state', title: 'State', hidden: true},
                    { field: 'operatorId', title: 'OperatorId', hidden: true},
                    { field: 'operateTime', title: 'OperateTime', hidden: true},
                    { field: 'transferTime', title: '原件移交时间', hidden: false, sortable: true},
                    { field: 'transferOperatorId', title: '原件移交人', hidden: false, sortable: true},
                    { field: 'transferRecipient', title: '原件移交接收人', hidden: false, sortable: true},
                    { field: 'revertTime', title: '原件归还时间', hidden: false, sortable: true},
                    { field: 'revertOperatorId', title: '原件归还人', hidden: false, sortable: true},
                    { field: 'revertRecipientId', title: '原件归还接收人', hidden: false, sortable: true},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }  },

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
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},
                    { field: 'currentNodeTitle', title: '当前状态' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnInformationSubmittedCheck2' + token,
                    text: '业务审批',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickInformationSubmittedCheck2();
            }
        });
    }

    // 构造已完成表格脚本
    function initParticipantInformationSubmittedTable() {
        var strTableId = 'ParticipantInformationSubmittedTable' + token;
        var url = WEB_ROOT + '/oa/Information/InformationSubmitted_Participantlist.action';

        $('#' + strTableId).datagrid({
            //  title: '对外资料报送',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            fitColumns: false,
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
                    { field: 'department', title: '经办部门', hidden: true},
                    { field: 'departmentName', title: '经办部门', hidden: false, sortable: true},
                    { field: 'handlingId', title: '经办人', hidden: true},
                    { field: 'handlingName', title: '经办人', hidden: false, sortable: true},
                    { field: 'mainOrg', title: '主送单位', hidden: false, sortable: true},
                    { field: 'otherOrg', title: '抄送单位', hidden: false, sortable: true},
                    { field: 'reason', title: '报送事由', hidden: true},
                    { field: 'content', title: '报送内容', hidden: true},
                    { field: 'submitTime', title: '报送时间', hidden: false, sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'Sid', hidden: true},
                    { field: 'id', title: 'Id', hidden: true},
                    { field: 'state', title: 'State', hidden: true},
                    { field: 'operatorId', title: 'OperatorId', hidden: true},
                    { field: 'operateTime', title: 'OperateTime', hidden: true},
                    { field: 'transferTime', title: '原件移交时间', hidden: false, sortable: true},
                    { field: 'transferOperatorId', title: '原件移交人', hidden: false, sortable: true},
                    { field: 'transferRecipient', title: '原件移交接收人', hidden: false, sortable: true},
                    { field: 'revertTime', title: '原件归还时间', hidden: false, sortable: true},
                    { field: 'revertOperatorId', title: '原件归还人', hidden: false, sortable: true},
                    { field: 'revertRecipientId', title: '原件归还接收人', hidden: false, sortable: true},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        } },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"] == "" ? "" : row["generalManagerName"] + "：" + value;
                        }  },

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
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},
                    { field: 'currentNodeTitle', title: '当前状态' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnInformationSubmittedLook' + token,
                    text: '查看',
                    iconCls: 'icon-edit'
                } ,{
                    id: 'btnInformationSubmittedPrint2' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickInformationSubmittedLook();
                onClickInformationSubmittedPrint( 'btnInformationSubmittedPrint2' + token,strTableId);
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickInformationSubmittedAdd() {
        var buttonId = 'btnInformationSubmittedAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initInformationSubmittedWindow({controlString1:"",controlString2:""}, 'add');
        });
    }

    /**
     * 查看事件
     */
    function onClickInformationSubmittedLook() {
        var buttonId = 'btnInformationSubmittedLook' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ParticipantInformationSubmittedTable' + token, function (selected) {
                //判断当前数据是否进入业务控制状态
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/Information/InformationSubmitted_load.action?informationSubmitted.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["handlingId"] = selected.handlingId
                    data["departmentName"] = selected.departmentName;
                    data["controlString1"] = selected.controlString1Id;
                    data["informationSubmittedVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initInformationSubmittedWindow(data, 'look');
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     *打印事件
     */ /*修改：周海鸿
     * 时间2015-7-20
     * 内容：添加报表打印*/
    function onClickInformationSubmittedPrint(buttonId,tableid) {
        //var buttonId = 'btnInformationSubmittedPrint' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableid, function (selected) {
                //判断当前数据是否进入业务控制状态

                process.beforeClick();

                var url =WEB_ROOT+"/modules/oa/modelsFiles/InformationSubmittedModels.jsp?id="+selected.id+"&token=" + token;
                window.open(url);

                process.afterClick();
            });
        });
    }
    /**
     * 修改事件
     */
    function onClickInformationSubmittedEdit() {
        var buttonId = 'btnInformationSubmittedEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('InformationSubmittedTable' + token, function (selected) {
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
                var id = selected.id
                var url = WEB_ROOT + '/oa/Information/InformationSubmitted_load.action?informationSubmitted.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["handlingId"] = selected.handlingId
                    data["departmentName"] = selected.departmentName;
                    data["controlString1"] = selected.controlString1Id;
                   data["informationSubmittedVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initInformationSubmittedWindow(data, 'edit');
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 资料归还
     * 修改人：周海鸿
     * 时间：2015-7-8
     * 内容：添加资料 报送完成的数据添加
     */
    function onClickInformationSubmittedReturn() {
        var buttonId = 'btnInformationSubmittedReturn' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('InformationSubmittedTable' + token, function (selected) {
                //判断当前数据是否审批是否完成
                if ("已完成"!=selected.currentNodeTitle ) {
                    fw.alert("警告", "数据还未审批完成无法添加数据");
                    return;
                }
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/Information/InformationSubmitted_load.action?informationSubmitted.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["handlingId"] = selected.handlingId
                    data["departmentName"] = selected.departmentName;
                    data["controlString1"] = selected.controlString1Id;
                   data["informationSubmittedVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    data['informationSubmitted.revertTime']=fw.getDateTime();
                    var comeback = "comeback"
                    initInformationSubmittedWindow(data, comeback);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 检查事件
     */
    /***
     * 修改人 周海鸿
     * 修改时间2015-6-26
     * 修改事件：添加业务审批按钮
     */
    function onClickInformationSubmittedCheck() {
        var buttonId = 'btnInformationSubmittedCheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('InformationSubmittedTable' + token, function (selected) {
                process.beforeClick();
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经提交申请不得重复提交");
                    process.afterClick();
                    return;
                }
                var id = selected.id
                var url = WEB_ROOT + '/oa/Information/InformationSubmitted_load.action?informationSubmitted.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["workflowID"] =10;

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

                    data["handlingId"] = selected.handlingId
                    data["departmentName"] = selected.departmentName;

                    initInformationSubmittedWindow(data, 'applay');
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });

        });
    }

    /**
     * 检查事件
     */
    /***
     * 修改人 周海鸿
     * 修改时间2015 -7- 8
     * 修改事件：添加业务审批按钮
     */
    function onClickInformationSubmittedCheck2() {
        var buttonId = 'btnInformationSubmittedCheck2' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('WaitInformationSubmittedTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/Information/InformationSubmitted_load.action?informationSubmitted.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["WorkflowID"] = 10;
                    data["bizRoute.workflowId"] = 10;
                    data["YWID"] = selected.id;
                    data["RouteListID"] = selected.routeListId;
                    data["CurrentNode"] = selected.currentNodeId;
                    data["handlingId"] = selected.handlingId
                    data["departmentName"] = selected.departmentName;
                    data["controlString1"] = selected.controlString1Id;
                   data["informationSubmittedVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;

                    initInformationSubmittedWindow(data, 'check');
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
    function onClickInformationSubmittedDelete() {
        var buttonId = 'btnInformationSubmittedDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('InformationSubmittedTable' + token, function (selected) {
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
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + '/oa/Information/InformationSubmitted_delete.action?informationSubmitted.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('InformationSubmittedTable' + token);
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
     * @param windowStatus 窗口状态
     *
     * 修改人：周海鸿
     * 时间：2015-7-8
     * 内容：实现资料归还保存
     */
    function initInformationSubmittedWindow(data, windowStatus) {

        data['informationSubmitted.operatorId'] = loginUser.getId();
        data['informationSubmitted.handlingId'] = loginUser.getId();
        data['informationSubmitted.handlingName'] = loginUser.getName();
        data['informationSubmitted.operatorName'] = loginUser.getName();

        var url2 = WEB_ROOT + '/system/User_getUserForDepartmentName.action?user.id=' + loginUser.getId();
        fw.post(url2, null, function (data1) {
            data["departmentName"] = data1["name"];
            data["informationSubmitted.department"] = data1["id"];
            data["informationSubmitted.submitTime"] = fw.getDateTime()//设置时间


            var url = WEB_ROOT + '/modules/oa/Information/InformationSubmitted_Save.jsp?token=' + token;
            var windowId = 'InformationSubmittedWindow' + token;
            fw.window(windowId, '对外资料报送', 700, 400, url, function () {
                var temp=false;

                //判断窗口状态时否为查看状态
                if (windowStatus == 'look') {

                    $("#btnInformationSubmittedSubmit_start" + token).remove();//去除业务按钮
                    $("#btnInformationSubmittedSubmit_applay" + token).remove();//去除业务按钮

                    $("#btnInformationSubmittedSubmit" + token).remove();//去除确定按钮
                    //业务安妮处理事件
                }
                //判断数窗口是否数据数据回归状态
                if (windowStatus == "comeback") {
                    //设置部份文本框为只读
                    $('#mainOrg' + token).attr("readonly", "readonly");//主送单位
                    $('#otherOrg' + token).attr("readonly", "readonly"); // 抄送单位
                    $('#reason' + token).attr("readonly", "readonly");//报送事由
                    $('#content' + token).attr("readonly", "readonly"); //内容
                    /*修改人：周海鸿
                    * 时间：2015-7-14
                    * 内容：设置归还信息必填*/
                    //归还时间
                    $('#revertTime'+token).validatebox({
                        required:true
                    });
                    //原件归还接收人
                    $('#revertOperatorId'+token).validatebox({
                        required:true
                    });
                    $('#revertRecipientId'+token).validatebox({
                        required:true
                    });
                    $("#revertOperatorId"+token).attr("required");
                     $("#revertRecipientId"+token).attr("required");//原件归还接收人
                    $("#btnInformationSubmittedSubmit_start" + token).remove();//去除业务按钮
                    temp = true;

                }
                //判断窗口是否属于审核状态
                else if (windowStatus == 'check') {
                    $("#btnInformationSubmittedSubmit" + token).remove();//去除确定按钮
                    $("#btnInformationSubmittedSubmit_applay" + token).remove();//去除业务按钮
                    //业务安妮处理事件
                    fw.onClickBizSubmit(token, "btnInformationSubmittedSubmit_start" + token,
                        data,"InformationSubmittedTable" + token, 'WaitInformationSubmittedTable' + token, 'ParticipantInformationSubmittedTable' + token, windowId);
                    temp = true;
                }else if(windowStatus == 'applay'){

                    $("#btnInformationSubmittedSubmit_start" + token).remove();//去除业务按钮
                    $("#btnInformationSubmittedSubmit" + token).remove();//去除确定按钮
                    // 申请
                    onClickInformationSubmittedApplay(data,windowId);
                } else {
                    //窗口不属于审核状态
                    $("#btnInformationSubmittedSubmit_start" + token).remove();//去除业务按钮
                    $("#btnInformationSubmittedSubmit_applay" + token).remove();//去除业务按钮
                    //提交事件

                }
                onClickInformationSubmittedSubmit();
                /**
                 * 初始化查询表单
                 */
                var url = WEB_ROOT + "/system/Department_list.action";


                var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
                var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"]
                //公司部门二级下拉列表

                fw.doubleDepartmentTrees("controlString1"+token ,"controlString2"+token ,selectString1,selectString2,temp);

                // 加载数据
                fw.formLoad('formInformationSubmitted' + token, data);

            });

        }, null);
    }
    /**
     *
     * 申请
     * @param data
     * @param windowid
     */
    function onClickInformationSubmittedApplay(data,windowid) {
        var buttonId = "btnInformationSubmittedSubmit_applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"]="";
            fw.onclickworkflowApplay(data,process,"InformationSubmittedTable" + token, 'WaitInformationSubmittedTable' + token, 'ParticipantInformationSubmittedTable' + token,windowid);
            provess.afterClick
        });
    }
    /**
     * 数据提交事件
     */
    function onClickInformationSubmittedSubmit() {
        var buttonId = 'btnInformationSubmittedSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = 'formInformationSubmitted' + token;
            // todo cm modify url
            var url = WEB_ROOT + '/oa/Information/InformationSubmitted_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('InformationSubmittedTable' + token);
                fw.windowClose('InformationSubmittedWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickInformationSubmittedSearch() {
        var buttonId = 'btnInformationSubmittedSearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'InformationSubmittedTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params['informationSubmittedVO.department'] = fw.getFormValue('Search_Department' + token, fw.type_form_combotree, fw.type_get_value);
            params['informationSubmittedVO.handlingName'] = $("Search_HandlingId" + token).val();
            params['informationSubmittedVO.mainOrg'] = fw.getFormValue('Search_MainOrg' + token, fw.type_form_combotree, fw.type_get_value);
            params['informationSubmittedVO.otherOrg'] = fw.getFormValue('Search_OtherOrg' + token, fw.type_form_combotree, fw.type_get_value);
            params['informationSubmittedVO.reason'] = $('#Search_Reason' + token).val();
            params['informationSubmittedVO.content'] = $('#Search_Content' + token).val();
            params['informationSubmittedVO_submitTime_Start'] = fw.getFormValue('Search_SubmitTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params['informationSubmittedVO_submitTime_End'] = fw.getFormValue('Search_SubmitTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params['informationSubmittedVO_transferTime_Start'] = fw.getFormValue('Search_TransferTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params['informationSubmittedVO_transferTime_End'] = fw.getFormValue('Search_TransferTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params['informationSubmittedVO.transferOperatorId'] = $('#Search_TransferOperatorId' + token).val();
            params['informationSubmittedVO.transferRecipient'] = $('#Search_TransferRecipient' + token).val();
            params['informationSubmittedVO_revertTime_Start'] = fw.getFormValue('Search_RevertTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params['informationSubmittedVO_revertTime_End'] = fw.getFormValue('Search_RevertTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params['informationSubmittedVO.revertOperatorId'] = $('#Search_RevertOperatorId' + token).val();
            params['informationSubmittedVO.revertRecipientId'] = $('#Search_RevertRecipientId' + token).val();
            params['informationSubmittedVO.departmentLeaderOpinion'] = $('#Search_DepartmentLeaderOpinion' + token).val();
            params['informationSubmittedVO.leaderShipOpinion'] = $('#Search_LeaderShipOpinion' + token).val();
            params['informationSubmittedVO.directorOpinion'] = $('#Search_DirectorOpinion' + token).val();


            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickInformationSubmittedSearchReset() {
        var buttonId = 'btnInformationSubmittedSearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {

            fw.combotreeClear('Search_Department' + token);
            // fw.combotreeClear('Search_HandlingId'+token);
            fw.combotreeClear('Search_MainOrg' + token);
            fw.combotreeClear('Search_OtherOrg' + token);
//            // 清空时间文本框
//            $('#Search_Department'+token).val('');
            $('#Search_HandlingId' + token).val('');
//            $('#Search_MainOrg'+token).val('');
//            $('#Search_OtherOrg'+token).val('');
            $('#Search_Reason' + token).val('');
            $('#Search_Content' + token).val('');
            $('#Search_SubmitTime_Start' + token).datebox('setValue', '');
            $('#Search_SubmitTime_End' + token).datebox('setValue', '');
            $('#Search_TransferTime_Start' + token).datebox('setValue', '');
            $('#Search_TransferTime_End' + token).datebox('setValue', '');
            $('#Search_TransferOperatorId' + token).val('');
            $('#Search_TransferRecipient' + token).val('');
            $('#Search_RevertTime_Start' + token).datebox('setValue', '');
            $('#Search_RevertTime_End' + token).datebox('setValue', '');
            $('#Search_RevertOperatorId' + token).val('');
            $('#Search_RevertRecipientId' + token).val('');
            $('#Search_DepartmentLeaderOpinion' + token).val('');
            $('#Search_LeaderShipOpinion' + token).val('');
            $('#Search_DirectorOpinion' + token).val('');
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
