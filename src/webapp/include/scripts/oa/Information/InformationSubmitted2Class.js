/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 15-4-7
 * Time: 下午9:23
 * To change this template use File | Settings | File Templates.
 */
var InformationSubmitted2Class = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        $('#InformTab' + token).tabs({
            border: false,
            onSelect: function (title, index) {
                var strTableId = ""

                if (index == 0) {
                    strTableId = 'InformationSubmitted2Table' + token;
                    fw.datagridReload(strTableId);
                } else if (index == 1) {
                    //等待审核
                    // initWaitInformationSubmitted2Table();{
                    strTableId = 'WaitInformationSubmitted2Table' + token;
                    var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_waitList.action";
                    $('#' + strTableId).datagrid({url: url});
                    fw.datagridReload(strTableId);
                }
                else if (index == 2) {

                    var strTableId = 'ParticipantInformationSubmitted2Table' + token;
                    var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_particiPantList.action";
                    $('#' + strTableId).datagrid({url: url});
                    fw.datagridReload(strTableId)
                }
            }
        });

        // 初始化查询事件
        onClickInformationSubmitted2Search();
        // 初始化查询重置事件
        onClickInformationSubmitted2SearchReset();
        initTableInformationSubmitted2Table();

        //等待审核
        initWaitInformationSubmitted2Table();
        //已完成
        initParticipantInformationSubmitted2Table();

    }


    /**
     * 初始换申请列表
     */
    function initTableInformationSubmitted2Table() {
        var strTableId = 'InformationSubmitted2Table' + token;
        var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_list.action";
        $('#' + strTableId).datagrid({
            //  title: '资料情况',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [10, 20, 30],
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
                    {field: 'ck', checkbox: true},
                    {field: 'controlString3', title: '数据标识号', hidden: false},
                    {field: 'controlString1', title: '公司名称', hidden: false},
                    {field: 'controlString2', title: '部门名称', hidden: false},
                    {field: 'controlString1Id', title: '公司名称', hidden: true},
                    {field: 'controlString2Id', title: '部门名称', hidden: true}
                ]
            ],
            columns: [
                [
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'applicantName', title: '申请人'},
                    {field: 'applicationTime', title: '申请时间'},
                    {field: 'applicationPurpose', title: '申请用途'},
                    {field: 'sentto', title: '报送地址'},
                    {
                    field: 'isAllReceive', title: '是否全部接收', formatter: function (value, row, index) {
                        if (value == "") {
                            return "";
                        } else if (value == 0) {
                            return "是";
                        } else if (value == 1) {
                            return "否";
                        }
                    }
                }, {
                    field: 'isAllOutBack', title: '是否全部归还', formatter: function (value, row, index) {
                        if (value == "") {
                            return "";
                        } else if (value == 0) {
                            return "是";
                        } else if (value == 1) {
                            return "否";
                        }
                    }
                },
                    {
                        field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {
                            return row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        }
                    },

                    {
                        field: 'chargeLeaderContent', title: '分管领导审核意见',
                        formatter: function (value, row, index) {
                            return row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;


                        }
                    }, {
                    field: 'content1', title: '资料保管人',
                    formatter: function (value, row, index) {
                        return row["name1"] == "" ? "" : row["name1"] + "：" + value;
                    }
                },
                    {field: 'status', title: '是否完成', hidden: true},
                    {field: 'statusId', title: '状态', hidden: true},
                    {field: 'currentNodeId', title: '当前节点', hidden: true},
                    {field: 'routeListId', title: '路由节点', hidden: true},
                    {field: 'currentNodeTitle', title: '当前状态', hidden: false},
                    {
                        field: 'currentStatus', title: '当前状态', hidden: true, formatter: function (value, row, index) {
                        if (value < '1') {
                            return '等待流转';
                        }
                        else if (value == '1') {
                            return '流转中';
                        }
                        else if (value == '5') {
                            return '已完成';
                        }
                    }
                    }
                ]
            ],
            toolbar: [
                {
                    id: 'btnInformationSubmitted2Add' + token,
                    text: '申请',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnInformationSubmittedItem2Add' + token,
                    text: '添加资料',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnInformationSubmittedItem2Edit' + token,
                    text: '修改资料明细',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnInformationSubmitted2Upload' + token,
                    text: '附件上传',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnInformationSubmitted2Check' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnInformationSubmitted2Print' + token,
                    text: '打印',
                    iconCls: 'icon-print'
                },
                {
                    id: 'btnInformationSubmitted2Delete' + token,
                    text: '作废',
                    iconCls: 'icon-clear'
                }
            ],
            onLoadSuccess: function () {
                onClickInformationSubmitted2Add();
                onClickInformationSubmitted2Delete();
                onClickInformationSubmitted2Check();
                onClickInformationSubmittedItem2Add();//添加资料
                onClickInformationSubmittedItem2Edit();//修改资料详情
                //打印
                onClickInformationSubmitted2Print('btnInformationSubmitted2Print' + token, strTableId);
                onClickInformationSubmitted2Upload();
            }
        });
    }

    /**
     * 初始化等待审批列表
     */
    function initWaitInformationSubmitted2Table() {
        var strTableId = 'WaitInformationSubmitted2Table' + token;
        var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_waitList.action";
        $('#' + strTableId).datagrid({
            // title: '资料情况',
            //url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [10, 20, 30],
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
                    {field: 'ck', checkbox: true},
                    {field: 'controlString3', title: '数据标识号', hidden: false},
                    {field: 'controlString1', title: '公司名称', hidden: false},
                    {field: 'controlString2', title: '部门名称', hidden: false},
                    {field: 'controlString1Id', title: '公司名称', hidden: true},
                    {field: 'controlString2Id', title: '部门名称', hidden: true}
                ]
            ],
            columns: [
                [
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'applicantName', title: '申请人'},
                    {field: 'applicationTime', title: '申请时间'},
                    {field: 'applicationPurpose', title: '申请用途'},
                    {field: 'sentto', title: '报送地址'},{
                    field: 'isAllReceive', title: '是否全部接收', formatter: function (value, row, index) {
                        if (value == "") {
                            return "";
                        } else if (value == 0) {
                            return "是";
                        } else if (value == 1) {
                            return "否";
                        }
                    }
                }, {
                    field: 'isAllOutBack', title: '是否全部归还', formatter: function (value, row, index) {
                        if (value == "") {
                            return "";
                        } else if (value == 0) {
                            return "是";
                        } else if (value == 1) {
                            return "否";
                        }
                    }
                },
                    {
                        field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {
                            return row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        }
                    },

                    {
                        field: 'chargeLeaderContent', title: '分管领导审核意见',
                        formatter: function (value, row, index) {
                            return row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;


                        }
                    }, {
                    field: 'content1', title: '资料保管人',
                    formatter: function (value, row, index) {
                        return row["name1"] == "" ? "" : row["name1"] + "：" + value;
                    }
                },
                    {field: 'status', title: '是否完成', hidden: true},
                    {field: 'statusId', title: '状态', hidden: true},
                    {field: 'currentNodeId', title: '当前节点', hidden: true},
                    {field: 'routeListId', title: '路由节点', hidden: true},
                    {field: 'currentNodeTitle', title: '当前状态', hidden: false},
                    {
                        field: 'currentStatus', title: '当前状态', hidden: true, formatter: function (value, row, index) {
                        if (value < '1') {
                            return '等待流转';
                        }
                        else if (value == '1') {
                            return '流转中';
                        }
                        else if (value == '5') {
                            return '已完成';
                        }
                    }
                    }
                ]
            ],
            toolbar: [
                {
                    id: 'btnInformationSubmitted2Check2' + token,
                    text: '业务审批',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                onClickInformationSubmitted2Check2();
            }
        });
    }

    /**
     * 初始化审批完成列表
     */
    function initParticipantInformationSubmitted2Table() {
        var strTableId = 'ParticipantInformationSubmitted2Table' + token;
        var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_particiPantList.action";
        $('#' + strTableId).datagrid({
//            title: '资料情况',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [10, 20, 30],
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
                    {field: 'ck', checkbox: true},
                    {field: 'controlString3', title: '数据标识号', hidden: false},
                    {field: 'controlString1', title: '公司名称', hidden: false},
                    {field: 'controlString2', title: '部门名称', hidden: false},
                    {field: 'controlString1Id', title: '公司名称', hidden: true},
                    {field: 'controlString2Id', title: '部门名称', hidden: true}
                ]
            ],
            columns: [
                [
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'applicantName', title: '申请人'},
                    {field: 'applicationTime', title: '申请时间'},
                    {field: 'applicationPurpose', title: '申请用途'},
                    {field: 'sentto', title: '报送地址'},
                    {
                    field: 'isAllReceive', title: '是否全部接收', formatter: function (value, row, index) {
                        if (value == "") {
                            return "";
                        } else if (value == 0) {
                            return "是";
                        } else if (value == 1) {
                            return "否";
                        }
                    }
                }, {
                    field: 'isAllOutBack', title: '是否全部归还', formatter: function (value, row, index) {
                        if (value == "") {
                            return "";
                        } else if (value == 0) {
                            return "是";
                        } else if (value == 1) {
                            return "否";
                        }
                    }
                },
                    {
                        field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {
                            return row["departmentLeaderName"] == "" ? "" : row["departmentLeaderName"] + "：" + value;
                        }
                    },

                    {
                        field: 'chargeLeaderContent', title: '分管领导审核意见',
                        formatter: function (value, row, index) {
                            return row["chargeLeaderName"] == "" ? "" : row["chargeLeaderName"] + "：" + value;


                        }
                    }, {
                    field: 'content1', title: '资料保管人',
                    formatter: function (value, row, index) {
                        return row["name1"] == "" ? "" : row["name1"] + "：" + value;
                    }
                },
                    {field: 'status', title: '是否完成', hidden: true},
                    {field: 'statusId', title: '状态', hidden: true},
                    {field: 'currentNodeId', title: '当前节点', hidden: true},
                    {field: 'routeListId', title: '路由节点', hidden: true},
                    {field: 'currentNodeTitle', title: '当前状态', hidden: false},
                    {
                        field: 'currentStatus', title: '当前状态', hidden: true, formatter: function (value, row, index) {
                        if (value < '1') {
                            return '等待流转';
                        }
                        else if (value == '1') {
                            return '流转中';
                        }
                        else if (value == '5') {
                            return '已完成';
                        }
                    }
                    }
                ]
            ],
            toolbar: [
                {
                    id: 'btnInformationSubmitted2Look' + token,
                    text: '查看',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnInformationSubmitted2Print2' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnUpdateSealUsageItemStatus' + token,
                    text: '修改资料详情 ',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                onClickInformationSubmitted2Look();
                onClickUpdateSealUsageItem2Edit();
                onClickInformationSubmitted2Print('btnInformationSubmitted2Print2' + token, strTableId);
            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     * @history 2015-6-10 | 邓超：添加文件上传，这里的 id 通过前台生成 uuid，不通过后台
     * 修改人周海鸿
     * 修改时间 20115-6-29
     * 修改事件：集合通用业务控制
     * @param winStatus 窗口的状态
     */
    function initWindowInformationSubmitted2Window(data, winStatus) {
        data["informationSubmitted2.operatorId"] = loginUser.getId();
        data["informationSubmitted2.operatorName"] = loginUser.getName();

        //窗口状态为添加时窗体高度为260
        var height;
        if (winStatus == "add") {
            height = 360;
        }
        //其他状态
        else {
            height = 560;
        }
        var url = WEB_ROOT + "/modules/oa/Information/InformationSubmitted2_Save.jsp?token=" + token;
        var windowId = "InformationSubmitted2Window" + token;
        // 弹窗
        fw.window(windowId, '资料申请', 600, height, url, function () {
            //用来判断下列列表是否可以使用
            var temp = false;
            //窗口状态为查看状态(
            if (winStatus != "look") {
                //窗口状态为添加数据状态时
                if (winStatus == "add") {
                    //上传按钮消失
                    $("#uploadTR" + token).remove();
                    //资料明细列表消失
                    $("#infromationSubmittedItem2" + token).remove();
                    //去除两个业务无关按钮
                    $("#btnInformationSubmitted2Submit_start" + token).remove();
                    $("#btnInformationSubmitted2Submit_applay" + token).remove();

                    data["informationSubmitted2.applicantName"] = loginUser.getName();
                    data["informationSubmitted2.applicantId"] = loginUser.getId();
                    data["informationSubmitted2.applicationTime"] = fw.getDateTime();

                    // 初始化表单提交事件
                    onClickInformationSubmitted2Submit();
                }
                //当前窗口状态不等于添加状态
                if (winStatus != "add") {
                    //判断是否有资料申请的ID
                    if (data["informationSubmitted2.id"] == undefined) {
                        fw.alert("警告!", "资料申请数据出错请联系管理员");
                        return;
                    }
                    //加载资料脚本
                    using(SCRIPTS_ROOT + '/oa/Information/InformationSubmittedItem2Class.js', function () {
                        var informationSubmittedItem2Class = new InformationSubmittedItem2Class(token, winStatus, data["informationSubmitted2.id"]);
                        informationSubmittedItem2Class.initModule();
                    });
                }
                //窗口状态为添加资料数据状态时
                if (winStatus == "edit") {
                    //去除两个业务无关按钮
                    $("#btnInformationSubmitted2Submit_start" + token).remove();
                    $("#btnInformationSubmitted2Submit_applay" + token).remove();
                    //上传按钮消失
                    $("#uploadTR" + token).remove();
                    // 初始化表单提交事件
                    onClickInformationSubmitted2Submit();
                }
                //窗口状态为附件上传状态时
                if (winStatus == "upload") {

                    //去除两个业务无关按钮
                    $("#btnInformationSubmitted2Submit_start" + token).remove();
                    $("#btnInformationSubmitted2Submit_applay" + token).remove();
                    //附件上传页面按钮状态
                    var btnstatus = "";
                    // 初始化文件上传
                    if (loginUser.getId() != data["informationSubmitted2.applicantId"]) {
                        btnstatus = "remove";
                    }
                    fw.initFileUpload(data["informationSubmitted2.id"], 9775, 'btnUpload' + token, btnstatus, token);

                    // 初始化表单提交事件
                    onClickInformationSubmitted2Submit()
                }
                //窗口状态为业务审批状态
                if (winStatus == 'check') {
                    // 初始化文件上传
                    fw.initFileUpload(data["informationSubmitted2.id"], 9775, 'btnUpload' + token, token);
                    $("#btnInformationSubmitted2Submit" + token).remove();
                    //去除两个业务无关按钮
                    $("#btnInformationSubmitted2Submit_applay" + token).remove();

                    //设置需要其他业务的类
                    data["serviceClassName"] = "com.youngbook.service.oa.Information.InformationSubmitted2Service";
                    //业务审核页面
                    fw.onClickBizSubmit(token, "btnInformationSubmitted2Submit_start" + token,
                        data, "InformationSubmitted2Table" + token, 'WaitInformationSubmitted2Table' + token, 'ParticipantInformationSubmitted2Table' + token, windowId);
                    //用来判断下列列表是否可以使用
                    temp = true;
                }
                if (winStatus == "applay") {

                    $("#btnInformationSubmitted2Submit_start" + token).remove();
                    $("#btnInformationSubmitted2Submit" + token).remove();

                    //附件上传页面按钮状态
                    var btnstatus = "";
                    // 初始化文件上传
                    if (loginUser.getId() != data["informationSubmitted2.applicantId"]) {
                        btnstatus = "remove";
                    }
                    fw.initFileUpload(data["informationSubmitted2.id"], 9775, 'btnUpload' + token, btnstatus, token);
                    onClickInformationSubmitted2ApplaySubmit(data)
                }


                //todo 业务审批后改。
                //工作流审批完成
                if (winStatus == "updateStatus") {
                    $("#btnInformationSubmitted2Submit_start" + token).remove();
                    $("#btnInformationSubmitted2Submit_applay" + token).remove();
                    $("#btnInformationSubmitted2Submit" + token).remove();
                    // 初始化表单提交事件
                    onClickInformationSubmitted2Submit();
                    //$("#uploadTR" + token).remove();
                    //加载资料脚本
                    using(SCRIPTS_ROOT + '/oa/Information/InformationSubmittedItem2Class.js', function () {
                        var informationSubmittedItem2Class = new InformationSubmittedItem2Class(token, winStatus, data["informationSubmitted2.id"]);
                        informationSubmittedItem2Class.initModule();
                    });
                    //附件上传页面按钮状态
                    var btnstatus = "";
                    // 初始化文件上传
                    if (loginUser.getId() != data["informationSubmitted2.applicantId"]) {
                        btnstatus = "remove";
                    }
                    fw.initFileUpload(data["informationSubmitted2.id"], 9775, 'btnUpload' + token, btnstatus, token);
                }
            }
            else {
                //去除审批、保存；
                $("#btnInformationSubmitted2Submit_applay" + token).remove();
                $("#btnInformationSubmitted2Submit_start" + token).remove();
                $("#btnInformationSubmitted2Submit" + token).remove();
                //$("#uploadTR" + token).remove();
                //加载资料脚本
                using(SCRIPTS_ROOT + '/oa/Information/InformationSubmittedItem2Class.js', function () {
                    var informationSubmittedItem2Class = new InformationSubmittedItem2Class(token, winStatus, data["informationSubmitted2.id"]);
                    informationSubmittedItem2Class.initModule();
                });
                //附件上传页面按钮状态
                var btnstatus = "";
                // 初始化文件上传
                if (loginUser.getId() != data["informationSubmitted2.applicantId"]) {
                    btnstatus = "remove";
                }
                fw.initFileUpload(data["informationSubmitted2.id"], 9775, 'btnUpload' + token, btnstatus, token);
            }

            //下拉列表
            //获取选中的节点
            var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
            var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"]
            //公司部门二级下拉列表
            fw.doubleDepartmentTrees("controlString1" + token, "controlString2" + token, selectString1, selectString2, temp);

            // 加载数据
            fw.formLoad('formInformationSubmitted2' + token, data);

        }, null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickInformationSubmitted2Add() {
        var butttonId = "btnInformationSubmitted2Add" + token;
        fw.bindOnClick(butttonId, function (process) {
            var add = 'add';
            initWindowInformationSubmitted2Window({controlString1: "", controlString2: ""}, add);
        });
    }

    /**
     * 删除事件
     */
    function onClickInformationSubmitted2Delete() {
        var buttonId = "btnInformationSubmitted2Delete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('InformationSubmitted2Table' + token, function (selected) {

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
                    var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_delete.action?InformationSubmitted2.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('InformationSubmitted2Table' + token);
                    }, null);
                }, function () {
                });
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickInformationSubmitted2Check() {
        var butttonId = "btnInformationSubmitted2Check" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('InformationSubmitted2Table' + token, function (selected) {
                var id = selected.id;
                //获取资料的列表 如果列表为空 无法审批
                var sealItemUrl = WEB_ROOT + "/oa/Information/InformationSubmittedItem2_list.action?informationSubmittedItem2.applicationId=" + id;

                process.beforeClick();
                fw.post(sealItemUrl, null, function (sealItemData) {
                    //返回的列表对象为无法审批
                    if (sealItemData["total"] <= 0) {
                        process.afterClick();
                        fw.alert("警告", "请添加资料");
                        return;
                    }

                    if ("申请" != selected.currentNodeTitle) {
                        fw.alert("警告", "该数据已经提交申请不得重复提交");
                        process.afterClick();
                        return;
                    }
                    var applay = "applay";
                    var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_load.action?InformationSubmitted2.id=" + id;
                    fw.post(url, null, function (data) {

                        data["workflowID"] = 17;
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
                        initWindowInformationSubmitted2Window(data, applay);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });

                }, function () {
                    process.afterClick();
                });


            })

        });
    }

    /**
     * 业务审核事件
     * 修改人：周海鸿
     * 时间：2015-7-8
     * 内容：添加时间
     */
    function onClickInformationSubmitted2Check2() {
        var butttonId = "btnInformationSubmitted2Check2" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('WaitInformationSubmitted2Table' + token, function (selected) {
                var id = selected.id;
                process.beforeClick();
                //获取资料的列表 如果列表为空 无法审批
                var sealItemUrl = WEB_ROOT + "/oa/Information/InformationSubmittedItem2_list.action?informationSubmittedItem2.applicationId=" + id;
                fw.post(sealItemUrl, null, function (sealItemData) {
                    //返回的列表对象为无法审批
                    if (sealItemData["total"] <= 0) {
                        process.afterClick();
                        fw.alert("警告", "请添加资料明细信息");
                        return;
                    }
                    var check = "check";
                    var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_load.action?InformationSubmitted2.id=" + id;
                    fw.post(url, null, function (data) {
                        data["WorkflowID"] = 17;
                        data["bizRoute.workflowId"] = 17;
                        data["YWID"] = selected.id;
                        data["RouteListID"] = selected.routeListId;
                        data["CurrentNode"] = selected.currentNodeId;
                        data["informationSubmitted2VO.controlString3"] = selected.controlString3;
                        data["controlString1"] = selected.controlString1Id;
                        data["controlString2"] = selected.controlString2Id;
                        fw.getComboTreeFromKV('statusId' + token, 'OA_SealUsageStatus', 'V', fw.getMemberValue(data, 'informationSubmitted2.statusId'));
                        initWindowInformationSubmitted2Window(data, check);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });

                });


            })

        });
    }

    /**
     * 添加资料
     * 修改人：周海鸿
     * 修改时间2015-7-1
     *
     */
    function onClickInformationSubmittedItem2Add() {
        var buttonId = "btnInformationSubmittedItem2Add" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('InformationSubmitted2Table' + token, function (selected) {

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
                var id = selected.id;

                var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_load.action?InformationSubmitted2.id=" + id;
                fw.post(url, null, function (data) {

                    data["controlString1"] = selected.controlString1Id;
                    data["informationSubmitted2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    var Edit = "edit";
                    initWindowInformationSubmitted2Window(data, Edit);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 修改资料明细
     * 修改人：周海鸿
     * 修改时间2015-7-1
     *
     */
    function onClickInformationSubmittedItem2Edit() {
        var buttonId = "btnInformationSubmittedItem2Edit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('InformationSubmitted2Table' + token, function (selected) {

                process.beforeClick();

                if ("已完成" != selected.currentNodeTitle) {
                    fw.alert("警告", "请审批完成后操作");
                    process.afterClick();
                    return;
                }


                var id = selected.id;
                var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_load.action?InformationSubmitted2.id=" + id;
                fw.post(url, null, function (data) {
                    data["controlString1"] = selected.controlString1Id;
                    data["informationSubmitted2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    var Edit = "updateStatus";
                    initWindowInformationSubmitted2Window(data, Edit);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 修改资料明细
     * 修改人：周海鸿
     * 修改时间2015-7-1
     *
     */
    function onClickUpdateSealUsageItem2Edit() {
        var buttonId = "btnUpdateSealUsageItemStatus" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ParticipantInformationSubmitted2Table' + token, function (selected) {

                process.beforeClick();

                if ("已完成" != selected.currentNodeTitle) {
                    fw.alert("警告", "请审批完成后操作");
                    process.afterClick();
                    return;
                }


                var id = selected.id;
                var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_load.action?InformationSubmitted2.id=" + id;
                fw.post(url, null, function (data) {
                    data["controlString1"] = selected.controlString1Id;
                    data["informationSubmitted2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    var Edit = "updateStatus";
                    initWindowInformationSubmitted2Window(data, Edit);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /*修改：周海鸿
     * 时间2015-7-20
     * 内容：添加报表打印*/
    function onClickInformationSubmitted2Print(buttonId, tableid) {
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableid, function (selected) {
                //判断选择的数据是否处在工作流状态中

                process.beforeClick();

                var url = WEB_ROOT + "/modules/oa/modelsFiles/InformationSubmittedModels2.jsp?id=" + selected.id + "&token=" + token;
                window.open(url);

                process.afterClick();
            })

        });
    }

    /**
     *
     * 修改人：周海鸿
     * 修改时间2015-7-1
     *内容：添加查看事件；
     */
    function onClickInformationSubmitted2Look() {
        var butttonId = "btnInformationSubmitted2Look" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('ParticipantInformationSubmitted2Table' + token, function (selected) {
                //判断选择的数据是否处在工作流状态中

                process.beforeClick();
                var id = selected.id;
                var look = "look";
                var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_load.action?InformationSubmitted2.id=" + id;
                fw.post(url, null, function (data) {
                    fw.getComboTreeFromKV('statusId' + token, 'OA_SealUsageStatus', 'V', fw.getMemberValue(data, 'informationSubmitted2.statusId'));

                    data["controlString1"] = selected.controlString1Id;
                    data["informationSubmitted2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initWindowInformationSubmitted2Window(data, look);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 修改人:周海鸿
     * 修改时间：2015-6-30
     * 修改事件:实现单一附件上传功能
     */

    function onClickInformationSubmitted2Upload() {
        var butttonId = "btnInformationSubmitted2Upload" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('InformationSubmitted2Table' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var upload = "upload";
                var url = WEB_ROOT + "/oa/Information/InformationSubmitted2_load.action?InformationSubmitted2.id=" + id;
                fw.post(url, null, function (data) {
                    fw.getComboTreeFromKV('statusId' + token, 'OA_SealUsageStatus', 'V', fw.getMemberValue(data, 'informationSubmitted2.statusId'));

                    data["controlString1"] = selected.controlString1Id;
                    data["informationSubmitted2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initWindowInformationSubmitted2Window(data, upload);
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
    function onClickInformationSubmitted2Search() {
        var buttonId = "btnSearchInformationSubmitted2" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "InformationSubmitted2Table" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params["informationSubmitted2VO_applicationTime_Start"] = fw.getFormValue('search_ApplicationTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["informationSubmitted2VO_applicationTime_End"] = fw.getFormValue('search_ApplicationTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["informationSubmitted2VO.applicantName"] = $("#search_ApplicantName" + token).val();
            params["informationSubmitted2VO.applicationPurpose"] = $("#search_ApplicationPurpose" + token).val();

            $('#' + strTableId).datagrid('load');

        });
    }

    /**
     * 查询重置事件
     */
    function onClickInformationSubmitted2SearchReset() {
        var buttonId = "btnResetInformationSubmitted2" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_ApplicationTime_Start' + token).datebox("setValue", '');
            $('#search_ApplicationTime_End' + token).datebox("setValue", '');
            $('#search_CheckTime_Start' + token).datebox("setValue", '');
            $('#search_CheckTime_End' + token).datebox("setValue", '');
            $('#search_ApplicantName' + token).val("");
            $('#search_CheckerName' + token).val("");
            $('#search_ApplicationPurpose' + token).val("");
        });
    }

    /**
     * 数据提交事件
     */
    function onClickInformationSubmitted2Submit() {
        var buttonId = "btnInformationSubmitted2Submit" + token;
        var formId = "formInformationSubmitted2" + token;
        fw.bindOnClick(buttonId, function (process) {
            var url = WEB_ROOT + '/oa/Information/InformationSubmitted2_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("InformationSubmitted2Table" + token);
                fwCloseWindow('InformationSubmitted2Window' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     *申请数据提交事件
     */
    function onClickInformationSubmitted2ApplaySubmit(data) {
        var buttonId = "btnInformationSubmitted2Submit_applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"] = "com.youngbook.service.oa.Information.InformationSubmitted2Service";
            fw.onclickworkflowApplay(data, process, 'WaitInformationSubmitted2Table' + token, 'ParticipantInformationSubmitted2Table' + token, 'InformationSubmitted2Table' + token, 'InformationSubmitted2Window' + token);
            provess.afterClick
        });
    }


    ///  事件定义 结束  /////////////////////////////////////////////////////////////////


    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
}