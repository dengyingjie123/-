/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 15-4-7
 * Time: 下午9:23
 * To change this template use File | Settings | File Templates.
 */
var SealUsageWFA2Class = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        $('#InformTab' + token).tabs({
            border: false,
            onSelect: function (title, index) {
                var strTableId = "";
                if (index == 0) {
                    strTableId = 'SealUsageWFA2Table' + token;
                    fw.datagridReload(strTableId);
                } else if (index == 1) {
                    //等待审核
                    // initWaitSealUsageWFA2Table();

                    strTableId = 'WaitSealUsageWFA2Table' + token;
                    var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_waitList.action";
                    $('#' + strTableId).datagrid({url: url});
                    fw.datagridReload(strTableId);
                }
                else if (index == 2) {
                    //已完成
                    // initParticipantSealUsageWFA2Table();

                    strTableId = 'ParticipantSealUsageWFA2Table' + token;
                    var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_particiPantlist.action";
                    $('#' + strTableId).datagrid({url: url});
                    fw.datagridReload(strTableId);
                }
            }
        });
        // 初始化查询事件
        onClickSealUsageWFA2Search();
        // 初始化查询重置事件
        onClickSealUsageWFA2SearchReset();
        // 初始化表格
        initTableSealUsageWFA2Table();

        //等待审核
        initWaitSealUsageWFA2Table();

        //已完成
        initParticipantSealUsageWFA2Table();
    }

    /**
     * 初始换申请列表
     */
    function initTableSealUsageWFA2Table() {
        var strTableId = 'SealUsageWFA2Table' + token;
        var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_list.action";
        $('#' + strTableId).datagrid({
            //  title: '用章情况',
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
                    {field: 'sentto', title: '发往处'},
                    {
                        field: 'isOut', title: '是否需要外带', formatter: function (value, row, index) {
                        if (value == 0) {
                            return "不需要";
                        } else if (value == 1) {
                            return "需要";
                        }
                    }
                    }, {
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
                    field: 'content1', title: '印章保管人',
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
                    id: 'btnSealUsageWFA2Add' + token,
                    text: '申请',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSealUsageItem2Add' + token,
                    text: '添加用章',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnSealUsageItem2Edit' + token,
                    text: '修改用章明细',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnSealUsageWFA2Upload' + token,
                    text: '附件上传',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnSealUsageWFA2Check' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnSealUsageWFA2Print' + token,
                    text: '打印',
                    iconCls: 'icon-print'
                },
                {
                    id: 'btnSealUsageWFA2Delete' + token,
                    text: '作废',
                    iconCls: 'icon-clear'
                }
            ],
            onLoadSuccess: function () {
                onClickSealUsageWFA2Add();
                onClickSealUsageWFA2Delete();
                onClickSealUsageWFA2Check();
                onClickSealUsageItem2Add();//添加印章
                onClickSealUsageItem2Edit();//修改印章详情
                //打印
                onClickSealUsageWFA2Print('btnSealUsageWFA2Print' + token, strTableId);
                onClickSealUsageWFA2Upload();
            }
        });
    }

    /**
     * 初始化等待审批列表
     */
    function initWaitSealUsageWFA2Table() {
        var strTableId = 'WaitSealUsageWFA2Table' + token;
        var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_waitList.action";
        $('#' + strTableId).datagrid({
            // title: '用章情况',
            // url: url,
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
                    {field: 'sentto', title: '发往处'},
                    {
                        field: 'isOut', title: '是否需要外带', formatter: function (value, row, index) {
                        if (value == 0) {
                            return "不需要";
                        } else if (value == 1) {
                            return "需要";
                        }
                    }
                    }, {
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
                    },
                    {
                        field: 'content1', title: '印章保管人',
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
                    id: 'btnSealUsageWFA2Check2' + token,
                    text: '业务审批',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                onClickSealUsageWFA2Check2();
            }
        });
    }

    /**
     * 初始化审批完成列表
     */
    function initParticipantSealUsageWFA2Table() {
        var strTableId = 'ParticipantSealUsageWFA2Table' + token;
        var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_particiPantlist.action";
        $('#' + strTableId).datagrid({
//            title: '用章情况',
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
                    {field: 'sentto', title: '发往处'},
                    {
                        field: 'isOut', title: '是否需要外带', formatter: function (value, row, index) {
                        if (value == 0) {
                            return "不需要";
                        } else if (value == 1) {
                            return "需要";
                        }
                    }
                    }, {
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
                    field: 'content1', title: '印章保管人',
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
                    id: 'btnSealUsageWFA2Look' + token,
                    text: '查看',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnSealUsageWFA2Print2' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnUpdateSealUsageItemStatus' + token,
                    text: '修改印章详情 ',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                onClickSealUsageWFA2Look();
                onClickUpdateSealUsageItem2Edit();
                onClickSealUsageWFA2Print('btnSealUsageWFA2Print2' + token, strTableId);
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
    function initWindowSealUsageWFA2Window(data, winStatus) {
        data["sealUsageWFA2.operatorId"] = loginUser.getId();
        data["sealUsageWFA2.operatorName"] = loginUser.getName();

        //窗口状态为添加时窗体高度为260
        var height;
        if (winStatus == "add") {
            height = 360;
        }
        //其他状态
        else {
            height = 560;
        }
        var url = WEB_ROOT + "/modules/oa/administration/SealUsageWFA2_Save.jsp?token=" + token;
        var windowId = "SealUsageWFA2Window" + token;
        // 弹窗
        fw.window(windowId, '用章申请', 600, height, url, function () {
            //用来判断下列列表是否可以使用
            var temp = false;
            //窗口状态为查看状态(
            if (winStatus != "look") {
                //窗口状态为添加数据状态时
                if (winStatus == "add") {
                    //上传按钮消失
                    $("#uploadTR" + token).remove();
                    //用章明细列表消失
                    $("#sealUsageItem2" + token).remove();
                    //去除两个业务无关按钮
                    $("#btnsealUsageWFA2Submit_start" + token).remove();
                    $("#btnsealUsageWFA2Submit_applay" + token).remove();

                    data["sealUsageWFA2.applicantName"] = loginUser.getName();
                    data["sealUsageWFA2.applicantId"] = loginUser.getId();
                    data["sealUsageWFA2.applicationTime"] = fw.getDateTime();

                    // 初始化表单提交事件
                    onClickSealUsageWFA2Submit();
                }
                //当前窗口状态不等于添加状态
                if (winStatus != "add") {
                    //判断是否有用章申请的ID
                    if (data["sealUsageWFA2.id"] == undefined) {
                        fw.alert("警告!", "用章申请数据出错请联系管理员");
                        return;
                    }
                    //加载用章类型脚本
                    using(SCRIPTS_ROOT + '/oa/administration/SealUsageItem2Class.js', function () {
                        var sealUsageItemClass = new SealUsageItem2Class(token, winStatus, data["sealUsageWFA2.id"]);
                        sealUsageItemClass.initModule();
                    });
                }
                //窗口状态为添加用章数据状态时
                if (winStatus == "edit") {
                    //去除两个业务无关按钮
                    $("#btnsealUsageWFA2Submit_start" + token).remove();
                    $("#btnsealUsageWFA2Submit_applay" + token).remove();
                    //上传按钮消失
                    $("#uploadTR" + token).remove();
                    // 初始化表单提交事件
                    onClickSealUsageWFA2Submit();
                }
                //窗口状态为附件上传状态时
                if (winStatus == "upload") {

                    //去除两个业务无关按钮
                    $("#btnsealUsageWFA2Submit_start" + token).remove();
                    $("#btnsealUsageWFA2Submit_applay" + token).remove();
                    //附件上传页面按钮状态
                    var btnstatus = "";
                    // 初始化文件上传
                    if (loginUser.getId() != data["sealUsageWFA2.applicantId"]) {
                        btnstatus = "remove";
                    }
                    fw.initFileUpload(data["sealUsageWFA2.id"], 9775, 'btnUpload' + token, btnstatus, token);

                    // 初始化表单提交事件
                    onClickSealUsageWFA2Submit()
                }
                //窗口状态为业务审批状态
                if (winStatus == 'check') {
                    // 初始化文件上传
                    fw.initFileUpload(data["sealUsageWFA2.id"], 9775, 'btnUpload' + token, token);
                    $("#btnsealUsageWFA2Submit" + token).remove();
                    //去除两个业务无关按钮
                    $("#btnsealUsageWFA2Submit_applay" + token).remove();

                    //设置需要其他业务的类
                    data["serviceClassName"] = "com.youngbook.service.oa.administration.SealUsageWFA2Service";
                    //业务审核页面
                    fw.onClickBizSubmit(token, "btnsealUsageWFA2Submit_start" + token,
                        data, "SealUsageWFA2Table" + token, 'WaitSealUsageWFA2Table' + token, 'ParticipantSealUsageWFA2Table' + token, windowId);
                    //用来判断下列列表是否可以使用
                    temp = true;
                }
                //申请
                if (winStatus == "applay") {

                    $("#btnsealUsageWFA2Submit_start" + token).remove();
                    $("#btnsealUsageWFA2Submit" + token).remove();

                    //附件上传页面按钮状态
                    var btnstatus = "";
                    // 初始化文件上传
                    if (loginUser.getId() != data["sealUsageWFA2.applicantId"]) {
                        btnstatus = "remove";
                    }
                    fw.initFileUpload(data["sealUsageWFA2.id"], 9775, 'btnUpload' + token, btnstatus, token);
                    onClickSealUsageWFA2ApplaySubmit(data)
                }


                //todo 业务审批后改。
                //工作流审批完成
                if (winStatus == "updateStatus") {
                    $("#btnsealUsageWFA2Submit_start" + token).remove();
                    $("#btnsealUsageWFA2Submit_applay" + token).remove();
                    $("#btnsealUsageWFA2Submit" + token).remove();
                    // 初始化表单提交事件
                    onClickSealUsageWFA2Submit();
                    //$("#uploadTR" + token).remove();
                    //加载用章类型脚本
                    //加载用章类型脚本
                    using(SCRIPTS_ROOT + '/oa/administration/SealUsageItem2Class.js', function () {
                        var sealUsageItemClass = new SealUsageItem2Class(token, winStatus, data["sealUsageWFA2.id"]);
                        sealUsageItemClass.initModule();
                    });
                    //附件上传页面按钮状态
                    var btnstatus = "";
                    // 初始化文件上传
                    if (loginUser.getId() != data["sealUsageWFA2.applicantId"]) {
                        btnstatus = "remove";
                    }
                    fw.initFileUpload(data["sealUsageWFA2.id"], 9775, 'btnUpload' + token, btnstatus, token);
                }
            }
            else {
                //去除审批、保存；
                $("#btnsealUsageWFA2Submit_applay" + token).remove();
                $("#btnsealUsageWFA2Submit_start" + token).remove();
                $("#btnsealUsageWFA2Submit" + token).remove();
                //$("#uploadTR" + token).remove();
                //加载用章类型脚本
                //加载用章类型脚本
                using(SCRIPTS_ROOT + '/oa/administration/SealUsageItem2Class.js', function () {
                    var sealUsageItemClass = new SealUsageItem2Class(token, winStatus, data["sealUsageWFA2.id"]);
                    sealUsageItemClass.initModule();
                });
                //附件上传页面按钮状态
                var btnstatus = "";
                // 初始化文件上传
                if (loginUser.getId() != data["sealUsageWFA2.applicantId"]) {
                    btnstatus = "remove";
                }
                fw.initFileUpload(data["sealUsageWFA2.id"], 9775, 'btnUpload' + token, btnstatus, token);
            }

            //下拉列表
            //获取选中的节点
            var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
            var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"]
            //公司部门二级下拉列表

            fw.doubleDepartmentTrees("controlString1" + token, "controlString2" + token, selectString1, selectString2, temp);

            // 加载数据
            fw.formLoad('formsealUsageWFA2' + token, data);

        }, null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickSealUsageWFA2Add() {
        var butttonId = "btnSealUsageWFA2Add" + token;
        fw.bindOnClick(butttonId, function (process) {
            var add = 'add';
            initWindowSealUsageWFA2Window({controlString1: "", controlString2: ""}, add);
        });
    }

    /**
     * 删除事件
     */
    function onClickSealUsageWFA2Delete() {
        var buttonId = "btnSealUsageWFA2Delete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SealUsageWFA2Table' + token, function (selected) {

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
                    var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_delete.action?SealUsageWFA2.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('SealUsageWFA2Table' + token);
                    }, null);
                }, function () {
                });
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickSealUsageWFA2Check() {
        var butttonId = "btnSealUsageWFA2Check" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('SealUsageWFA2Table' + token, function (selected) {
                var id = selected.id;
                process.beforeClick();
                //获取用章类型的列表 如果列表为空 无法审批
                var sealItemUrl = WEB_ROOT + "/oa/administration/SealUsageItem2_list.action?sealUsageItem2.applicationId=" + id;
                fw.post(sealItemUrl, null, function (sealItemData) {
                    //返回的列表对象为无法审批
                    if (sealItemData["total"] <= 0) {
                        process.afterClick();
                        fw.alert("警告", "请添加用章类型");
                        return;
                    }

                    if ("申请" != selected.currentNodeTitle) {
                        fw.alert("警告", "该数据已经提交申请不得重复提交");
                        process.afterClick();
                        return;
                    }
                    var applay = "applay";
                    var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_load.action?SealUsageWFA2.id=" + id;
                    fw.post(url, null, function (data) {

                        data["workflowID"] = 16;
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
                        //申请
                        fw.getComboTreeFromKV('statusId' + token, 'OA_SealUsageStatus', 'V', fw.getMemberValue(data, 'SealUsageWFA2.statusId'));
                        initWindowSealUsageWFA2Window(data, applay);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });

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
    function onClickSealUsageWFA2Check2() {
        var butttonId = "btnSealUsageWFA2Check2" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('WaitSealUsageWFA2Table' + token, function (selected) {
                var id = selected.id;
                process.beforeClick();
                //获取用章类型的列表 如果列表为空 无法审批
                var sealItemUrl = WEB_ROOT + "/oa/administration/SealUsageItem2_list.action?sealUsageItem2.applicationId=" + id;
                fw.post(sealItemUrl, null, function (sealItemData) {
                    //返回的列表对象为无法审批
                    if (sealItemData["total"] <= 0) {
                        process.afterClick();
                        fw.alert("警告", "请添加用章明细信息");
                        return;
                    }
                    var check = "check";
                    var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_load.action?SealUsageWFA2.id=" + id;
                    fw.post(url, null, function (data) {
                        data["WorkflowID"] = 16;
                        data["bizRoute.workflowId"] = 16;
                        data["YWID"] = selected.id;
                        data["RouteListID"] = selected.routeListId;
                        data["CurrentNode"] = selected.currentNodeId;
                        data["SealUsageWFA2VO.controlString3"] = selected.controlString3;
                        data["controlString1"] = selected.controlString1Id;
                        data["controlString2"] = selected.controlString2Id;
                        fw.getComboTreeFromKV('statusId' + token, 'OA_SealUsageStatus', 'V', fw.getMemberValue(data, 'SealUsageWFA2.statusId'));
                        initWindowSealUsageWFA2Window(data, check);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });

                });


            })

        });
    }

    /**
     * 添加用章类型
     * 修改人：周海鸿
     * 修改时间2015-7-1
     *
     */
    function onClickSealUsageItem2Add() {
        var butttonId = "btnSealUsageItem2Add" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('SealUsageWFA2Table' + token, function (selected) {

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

                var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_load.action?SealUsageWFA2.id=" + id;
                fw.post(url, null, function (data) {

                    data["controlString1"] = selected.controlString1Id;
                    data["sealUsageWFA2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    var Edit = "edit";
                    initWindowSealUsageWFA2Window(data, Edit);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 修改用章明细
     * 修改人：周海鸿
     * 修改时间2015-7-1
     *
     */
    function onClickSealUsageItem2Edit() {
        var buttonId = "btnSealUsageItem2Edit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SealUsageWFA2Table' + token, function (selected) {

                process.beforeClick();

                if ("已完成" != selected.currentNodeTitle) {
                    fw.alert("警告", "请审批完成后操作");
                    process.afterClick();
                    return;
                }


                var id = selected.id;
                var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_load.action?SealUsageWFA2.id=" + id;
                fw.post(url, null, function (data) {
                    data["controlString1"] = selected.controlString1Id;
                    data["sealUsageWFA2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    var Edit = "updateStatus";
                    initWindowSealUsageWFA2Window(data, Edit);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 修改用章明细
     * 修改人：周海鸿
     * 修改时间2015-7-1
     *
     */
    function onClickUpdateSealUsageItem2Edit() {
        var buttonId = "btnUpdateSealUsageItemStatus" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ParticipantSealUsageWFA2Table' + token, function (selected) {

                process.beforeClick();

                if ("已完成" != selected.currentNodeTitle) {
                    fw.alert("警告", "请审批完成后操作");
                    process.afterClick();
                    return;
                }


                var id = selected.id;
                var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_load.action?SealUsageWFA2.id=" + id;
                fw.post(url, null, function (data) {
                    data["controlString1"] = selected.controlString1Id;
                    data["sealUsageWFA2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    var Edit = "updateStatus";
                    initWindowSealUsageWFA2Window(data, Edit);
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
    function onClickSealUsageWFA2Print(buttonId, tableid) {
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableid, function (selected) {
                //判断选择的数据是否处在工作流状态中

                process.beforeClick();

                var url = WEB_ROOT + "/modules/oa/modelsFiles/SealUsageWFAModels2.jsp?id=" + selected.id + "&token=" + token;
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
    function onClickSealUsageWFA2Look() {
        var butttonId = "btnSealUsageWFA2Look" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('ParticipantSealUsageWFA2Table' + token, function (selected) {
                //判断选择的数据是否处在工作流状态中

                process.beforeClick();
                var id = selected.id;
                var look = "look";
                var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_load.action?SealUsageWFA2.id=" + id;
                fw.post(url, null, function (data) {
                    fw.getComboTreeFromKV('statusId' + token, 'OA_SealUsageStatus', 'V', fw.getMemberValue(data, 'SealUsageWFA2.statusId'));

                    data["controlString1"] = selected.controlString1Id;
                    data["SealUsageWFA2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initWindowSealUsageWFA2Window(data, look);
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

    function onClickSealUsageWFA2Upload() {
        var butttonId = "btnSealUsageWFA2Upload" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('SealUsageWFA2Table' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var upload = "upload";
                var url = WEB_ROOT + "/oa/administration/SealUsageWFA2_load.action?SealUsageWFA2.id=" + id;
                fw.post(url, null, function (data) {
                    fw.getComboTreeFromKV('statusId' + token, 'OA_SealUsageStatus', 'V', fw.getMemberValue(data, 'SealUsageWFA2.statusId'));

                    data["controlString1"] = selected.controlString1Id;
                    data["SealUsageWFA2VO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initWindowSealUsageWFA2Window(data, upload);
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
    function onClickSealUsageWFA2Search() {
        var buttonId = "btnSearchSealUsageWFA2" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "SealUsageWFA2Table" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params["SealUsageWFA2VO_applicationTime_Start"] = fw.getFormValue('search_ApplicationTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["SealUsageWFA2VO_applicationTime_End"] = fw.getFormValue('search_ApplicationTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["SealUsageWFA2VO.applicantName"] = $("#search_ApplicantName" + token).val();
            params["SealUsageWFA2VO.applicationPurpose"] = $("#search_ApplicationPurpose" + token).val();

            $('#' + strTableId).datagrid('load');

        });
    }

    /**
     * 查询重置事件
     */
    function onClickSealUsageWFA2SearchReset() {
        var buttonId = "btnResetSealUsageWFA2" + token;
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
    function onClickSealUsageWFA2Submit() {
        var buttonId = "btnsealUsageWFA2Submit" + token;
        var formId = "formsealUsageWFA2" + token;
        fw.bindOnClick(buttonId, function (process) {
            var url = WEB_ROOT + '/oa/administration/SealUsageWFA2_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("SealUsageWFA2Table" + token);
                fwCloseWindow('SealUsageWFA2Window' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     *申请数据提交事件
     */
    function onClickSealUsageWFA2ApplaySubmit(data) {
        var buttonId = "btnsealUsageWFA2Submit_applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"] = "com.youngbook.service.oa.administration.SealUsageWFA2Service";
            fw.onclickworkflowApplay(data, process, 'WaitSealUsageWFA2Table' + token, 'ParticipantSealUsageWFA2Table' + token, 'SealUsageWFA2Table' + token, 'SealUsageWFA2Window' + token);
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