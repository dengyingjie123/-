/**
 * Created by 章鹏
 * Date 2015-6-5
 */
var AssetApplicationClass = function (token) {

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询表单
        initAssetApplicationSearchForm();

        // 初始化查询事件
        onClickAssetApplicationSearch();
        // 初始化查询重置事件
        onClickAssetApplicationSearchReset();

        // 初始化表格
        initAssetApplicationTable();
        //初始化等待审批
        initWaitAssetApplicationTable();
        //初始换已完成
        initParticipantAssetApplicationTable();

    }

    /**
     * 初始化查询表单
     */
    function initAssetApplicationSearchForm() {
        var url = WEB_ROOT + "/system/Department_list.action";
        initStatusTree(url, '#search_applicationDepartmentId' + token, -2);//主办部门
        fw.getComboTreeFromKV('search_AssetTypeId' + token, 'OA_AssetApplicationType', 'k', '-2');

    }

    /**
     * 初始化下拉列表项
     */
    function initStatusTree(url, combotreeId, selectIndexId) {
        fw.combotreeLoad(combotreeId, url, selectIndexId);
    }

    // 构造初始化申请表格脚本
    function initAssetApplicationTable() {
        var strTableId = 'assetApplicationTable' + token;
        var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_list.action';

        $('#' + strTableId).datagrid({
//            title: '固定资产申请',
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
                    { field: 'productName', title: '名称', hidden: false, sortable: true},
                    { field: 'assetTypeId', title: '资产类型', hidden: true, sortable: true},
                    { field: 'assetTypeName', title: '资产类型', hidden: false, sortable: true},
                    { field: 'departmentId', title: '部门', hidden: true, sortable: true},
                    { field: 'applicationDepartmentId', title: '部门名称', hidden: true, sortable: true},
                    { field: 'applicationDepartmentName', title: '部门名称', hidden: true, sortable: true},
                    { field: 'applicantId', title: '请购人', hidden: true, sortable: true},
                    { field: 'applicantName', title: '请购人', hidden: false, sortable: true},
                    { field: 'moneys', title: '总金额(元)', sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true, sortable: true},
                    { field: 'id', title: 'id', hidden: true, sortable: true},
                    { field: 'state', title: 'state', hidden: true, sortable: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'purpose', title: '申请原因', hidden: true, sortable: true},

                    { field: 'content1', title: '使用部门意见',
                        formatter: function (value, row, index) {

                            return  row["name1"] == "" ? "" : row["name1"] + "：" + value;

                        }  },
                    { field: 'content2', title: '采购归口部门意见',
                        formatter: function (value, row, index) {

                            return  row["name2"] == "" ? "" : row["name2"] + "：" + value;

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

                    { field: 'currentNodeTitle', title: '当前状态'}
                ]
            ],
            toolbar: [
                {
                    id: 'btnAssetApplicationAdd' + token,
                    text: '申请资产',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnAssetApplicationEdit' + token,
                    text: '添加资产项目',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnAssetApplicationCheck' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                },{
                    id: 'btnAssetApplicationPrint' + token,
                    text: '打印',
                    iconCls: 'icon-print'
                },
                {
                    id: 'btnAssetApplicationDelete' + token,
                    text: '删除',
                    iconCls: 'icon-clear'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickAssetApplicationAdd();
                onClickAssetApplicationEdit();
                onClickAssetApplicationPrint('btnAssetApplicationPrint' + token,strTableId);
                onClickAssetApplicationCheck();
                onClickAssetApplicationDelete();
            }
        });
    }

    // 构造初始化等待审核表格脚本
    function initWaitAssetApplicationTable() {
        var strTableId = 'WaitassetApplicationTable' + token;
        var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_Waitlist.action';

        $('#' + strTableId).datagrid({
//            title: '固定资产申请',
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
                    { field: 'productName', title: '名称', hidden: false, sortable: true},
                    { field: 'assetTypeId', title: '资产类型', hidden: true, sortable: true},
                    { field: 'assetTypeName', title: '资产类型', hidden: false, sortable: true},
                    { field: 'departmentId', title: '部门', hidden: true, sortable: true},
                    { field: 'applicationDepartmentId', title: '部门名称', hidden: true, sortable: true},
                    { field: 'applicationDepartmentName', title: '部门名称', hidden: true, sortable: true},
                    { field: 'applicantId', title: '请购人', hidden: true, sortable: true},
                    { field: 'applicantName', title: '请购人', hidden: false, sortable: true},
                    { field: 'moneys', title: '总金额(元)', sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true, sortable: true},
                    { field: 'id', title: 'id', hidden: true, sortable: true},
                    { field: 'state', title: 'state', hidden: true, sortable: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'purpose', title: '申请原因', hidden: true, sortable: true},

                    { field: 'content1', title: '使用部门意见',
                        formatter: function (value, row, index) {

                            return  row["name1"] == "" ? "" : row["name1"] + "：" + value;

                        }  },
                    { field: 'content2', title: '采购归口部门意见',
                        formatter: function (value, row, index) {

                            return  row["name2"] == "" ? "" : row["name2"] + "：" + value;

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

                    { field: 'currentNodeTitle', title: '当前状态'}
                ]
            ],
            toolbar: [
                {
                    id: 'btnAssetApplicationWaitCheck' + token,
                    text: '业务审批',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickAssetApplicationWaitCheck();
            }
        });
    }

    // 构造初始化已完成表格脚本
    function initParticipantAssetApplicationTable() {
        var strTableId = 'articipantassetApplicationTable' + token;
        var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_Participantlist.action';

        $('#' + strTableId).datagrid({
//            title: '固定资产申请',
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
                    { field: 'productName', title: '名称', hidden: false, sortable: true},
                    { field: 'assetTypeId', title: '资产类型', hidden: true, sortable: true},
                    { field: 'assetTypeName', title: '资产类型', hidden: false, sortable: true},
                    { field: 'departmentId', title: '部门', hidden: true, sortable: true},
                    { field: 'applicationDepartmentId', title: '部门名称', hidden: true, sortable: true},
                    { field: 'applicationDepartmentName', title: '部门名称', hidden: true, sortable: true},
                    { field: 'applicantId', title: '请购人', hidden: true, sortable: true},
                    { field: 'applicantName', title: '请购人', hidden: false, sortable: true},
                    { field: 'moneys', title: '总金额(元)', sortable: true}

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true, sortable: true},
                    { field: 'id', title: 'id', hidden: true, sortable: true},
                    { field: 'state', title: 'state', hidden: true, sortable: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'purpose', title: '申请原因', hidden: true, sortable: true},

                    { field: 'content1', title: '使用部门意见',
                        formatter: function (value, row, index) {

                            return  row["name1"] == "" ? "" : row["name1"] + "：" + value;

                        }  },
                    { field: 'content2', title: '采购归口部门意见',
                        formatter: function (value, row, index) {

                            return  row["name2"] == "" ? "" : row["name2"] + "：" + value;

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
                    { field: 'currentNodeTitle', title: '当前状态'}
                ]
            ],
            toolbar: [
                {
                    id: 'btnAssetApplicationLook' + token,
                    text: '查看',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnAssetApplicationPrint2' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickAssetApplicationLook();
                onClickAssetApplicationPrint('btnAssetApplicationPrint2' + token,strTableId);
            }
        });
    }

    function onClickAssetApplicationWaitCheck() {
        var buttonId = 'btnAssetApplicationWaitCheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('WaitassetApplicationTable' + token, function (selected) {

                process.beforeClick();
                if (selected.moneys <= 0) {
                    fw.alert("警告", "请添加项目")
                    process.afterClick();
                    return;
                }
                var id = selected.id
                var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_load.action?assetApplication.id=' + selected.id;
                fw.post(url, null, function (data) {

                    //设置工作流编号
                    data["WorkflowID"] = 9;
                    data["bizRoute.workflowId"] = 9;
                    //设置业务编号
                    data["YWID"] = selected.id;
                    //设置路由编号
                    data["RouteListID"] = selected.routeListId;
                    //设置节点编号
                    data["CurrentNode"] = selected.currentNodeId;

                    data["controlString1"] = selected.controlString1Id;
                    data["controlString2"] = selected.controlString2Id;
                    data["assetApplicationVO.controlString3"] = selected.controlString3;
                    
                    data["applicantName"] = selected.applicantName;
                    data["departmentName"] = selected.departmentName;
                    data["assetApplication.moneys"] = selected.moneys;
                    initAssetApplicationWindow(data, WindowType_Check);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    function onClickAssetApplicationCheck() {
        var buttonId = 'btnAssetApplicationCheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('assetApplicationTable' + token, function (selected) {

                process.beforeClick();
                if (selected.moneys <= 0) {
                    fw.alert("警告", "请添加项目")
                    process.afterClick();
                    return;
                }
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经提交申请不得重复提交");
                    process.afterClick();
                    return;
                }
                var id = selected.id
                var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_load.action?assetApplication.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["workflowID"] = 9;

                    data["nextNode"] = 9;
                    //设置业务编号
                    data["id"] = id;
                    //设置路由编号
                    data["routeListId"] = selected.routeListId;
                    //设置节点编号
                    data["currentNodeId"] = selected.currentNodeId;

                    data["controlString1"] = selected.controlString1Id;
                    data["controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;

                    data["applicantName"] = selected.applicantName;
                    data["departmentName"] = selected.departmentName;
                    data["assetApplication.moneys"] = selected.moneys;
                    initAssetApplicationWindow(data, WindowType_applay);
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
    function onClickAssetApplicationAdd() {
        var buttonId = 'btnAssetApplicationAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initAssetApplicationWindow({controlString1:"",controlString2:""}, WindowType_Add);
        });

    }

    /**
     * 修改事件
     */
    function onClickAssetApplicationEdit() {
        var buttonId = 'btnAssetApplicationEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('assetApplicationTable' + token, function (selected) {
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
                var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_load.action?assetApplication.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["applicantName"] = selected.applicantName;
                    data["departmentName"] = selected.departmentName;
                    data["assetApplication.moneys"] = selected.moneys;
                    data["controlString1"] = selected.controlString1Id;
                    data["assetApplicationVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initAssetApplicationWindow(data, WindowType_Edit);
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
    function onClickAssetApplicationPrint(buttonId,tableId) {
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableId, function (selected) {
                process.beforeClick();

                var url =WEB_ROOT+"/modules/oa/modelsFiles/AssetApplicationModels.jsp?id="+selected.id+"&token=" + token;
                window.open(url);

                process.afterClick();  });
        });
    }

    /**
     * 查看事件
     */
    function onClickAssetApplicationLook() {
        var buttonId = 'btnAssetApplicationLook' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('articipantassetApplicationTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_load.action?assetApplication.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["applicantName"] = selected.applicantName;
                    data["departmentName"] = selected.departmentName;
                    data["assetApplication.moneys"] = selected.moneys;
                    data["controlString1"] = selected.controlString1Id;
                    data["assetApplicationVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    initAssetApplicationWindow(data, WindowType_look);
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
    function onClickAssetApplicationDelete() {
        var buttonId = 'btnAssetApplicationDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('assetApplicationTable' + token, function (selected) {
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
                    var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_delete.action?assetApplication.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('assetApplicationTable' + token);
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
    var WindowType_Add = 1;
    var WindowType_Edit = 2;
    var WindowType_Check = 3;
    var WindowType_look = 4;//查看
    var WindowType_applay = 5;//申请

    function initAssetApplicationWindow(data, windowType) {
        var height;
        var btnStatus = "btnStatus";
        if (windowType == WindowType_Add) {
            height = 300;
            //设置请购时间
            data['assetApplication.assApplicantTime'] = fw.getDate();
        }
        if (windowType == WindowType_Edit) {
            height = 500;
        }
        if (windowType == WindowType_Check || windowType == WindowType_look|| windowType == WindowType_applay) {
            height = 500;
        }


        // fw.alertReturnValue(data);

        data['assetApplication.operatorId'] = loginUser.getId();

        var url = WEB_ROOT + '/modules/oa/assetFixation/AssetApplication_Save.jsp?token=' + token;
        var windowId = 'assetApplicationWindow' + token;
        fw.window(windowId, '固定资产申请', 650, height, url, function () {
            //判断下拉是否禁用
            var temp=false;

            ////加载部门列表
            //var url = WEB_ROOT + "/system/Department_list.action";
            //fw.combotreeLoad("applicationDepartmentId" + token, url, data["assetApplication.applicationDepartmentId"]);


            $("#assetTableCommon" + token).remove();//隐藏暂时不需要的页面
            //窗口为查看状态
            if (windowType == WindowType_look) {
                $("#btnAssetApplicationSubmit_start" + token).remove();//去除业务审批按钮

                $("#btnAssetApplicationSubmit" + token).remove();//去除提交按钮
                //调用业务控制方法

                btnStatus = null;
            }
            //窗口为添加
            if (windowType == WindowType_Add) {

                $("#btnAssetApplicationSubmit_start" + token).remove();//去除业务审批按钮
                $("#assetTablePrj" + token).hide();

                //设置用户部门查询URL
                var orgNameUrl = WEB_ROOT + "/system/User_getUserForDepartmentName.action?user.id=" + loginUser.getId()
                //请求获取制定用户的部门信息
                fw.post(orgNameUrl, null, function (orgData) {
                    //将部门信息设置到表单中
                    $("#departmentId" + token).val(orgData["id"]);
                    //初始换设置请购人
                    $("#applicantId" + token).val(loginUser.getId());
                    $("#applicant" + token).val(loginUser.getName())

                })
            }
            //窗口为休息
            if (windowType == WindowType_Edit) {
                $("#btnAssetApplicationSubmit_start" + token).remove();//去除业务审批按钮
            }
            //窗口为检查状态
            if (windowType == WindowType_Check) {
                $("#btnAssetApplicationSubmit" + token).remove();//去除提交按钮
                //调用业务控制方法
                fw.onClickBizSubmit(token, "btnAssetApplicationSubmit_start" + token,
                    data, "assetApplicationTable" + token, 'WaitassetApplicationTable' + token,'articipantassetApplicationTable' + token,windowId);
                btnStatus = null;temp=true;
            }
            //申请
            if(windowType==WindowType_applay){

                $("#btnAssetApplicationSubmit_start" + token).remove();//去除业务审批按钮
                $("#btnAssetApplicationSubmit" + token).remove();//去除提交按钮
                //提交事件
                onClickAssetApplicationApplay(data,windowId);btnStatus = null;
            }else{

                $("#btnAssetApplicationSubmit_applay" + token).remove();
            }
            fw.getComboTreeFromKV('assetTypeId' + token, 'OA_AssetApplicationType', 'k', fw.getMemberValue(data, 'assetApplication.assetTypeId'));
            //提交事件
            onClickAssetApplicationSubmit();

            using(SCRIPTS_ROOT + '/oa/assetFixation/AssetItemClass.js', function () {
                var assetItemClass = new AssetItemClass(token, btnStatus);
                assetItemClass.initModule();
            });
            //下拉列表
            //获取选中的节点
            var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
            var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"]
            //公司部门二级下拉列表

            fw.doubleDepartmentTrees("controlString1"+token ,"controlString2"+token ,selectString1,selectString2,temp);


            // 加载数据
            fw.formLoad('formAssetApplication' + token, data);

        });
    }
    /**
     *
     * 申请
     * @param data
     * @param windowid
     */
    function onClickAssetApplicationApplay(data,windowid) {
        var buttonId = "btnAssetApplicationSubmit_applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"]="";
            fw.onclickworkflowApplay(data,process,"assetApplicationTable" + token, 'WaitassetApplicationTable' + token,'articipantassetApplicationTable' + token,windowid);
            provess.afterClick
        });
    }

    /**
     * 数据提交事件
     */
    function onClickAssetApplicationSubmit() {
        var buttonId = 'btnAssetApplicationSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = 'formAssetApplication' + token;
            var url = WEB_ROOT + '/oa/assetFixation/AssetApplication_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('assetApplicationTable' + token);
                fw.windowClose('assetApplicationWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickAssetApplicationSearch() {
        var buttonId = 'btnAssetApplicationSearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'assetApplicationTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params['assetApplicationVO.productName'] = $('#search_productName' + token).val();
            params['assetApplicationVO.applicantId'] = $('#search_ApplicantId' + token).val();

            params['assetApplicationVO.purpose'] = $('#search_Purpose' + token).val();
            params['assetApplicationVO.departmentHeadrId'] = $('#search_DepartmentHeadrId' + token).val();
            params['assetApplicationVO.administrativeDepartmentHeadId'] = $('#search_administrativeDepartmentHeadId' + token).val();

            params['assetApplicationVO.departmentLeaderId'] = $('#search_DepartmentLeaderId' + token).val();
            params['assetApplicationVO.financeDepartmentHeadId'] = $('#search_financeDepartmentHeadId' + token).val();

            params['assetApplication_operateTime_Start'] = fw.getFormValue('search_time_start' + token, fw.type_form_datebox, fw.type_get_value);
            params['assetApplication_operateTime_End'] = fw.getFormValue('search_time_end' + token, fw.type_form_datebox, fw.type_get_value);

            params['assetApplication_technicalDepartmentHeadTime_Start'] = fw.getFormValue('search_reimburseTime_start' + token, fw.type_form_datebox, fw.type_get_value);
            params['assetApplication_technicalDepartmentHeadTime_End'] = fw.getFormValue('search_reimburseTime_end' + token, fw.type_form_datebox, fw.type_get_value);
            //部门名称
            params['assetApplicationVO.controlString2Id'] = fw.getFormValue('search_applicationDepartmentId' + token, fw.type_form_combotree, fw.type_get_value);
            params['assetApplicationVO.assetTypeId'] = fw.getFormValue('search_AssetTypeId' + token, fw.type_form_combotree, fw.type_get_value);
            var ids = fw.combotreeGetCheckedIds('search_applicationDepartmentId' + token, ',', "'");

            params["controlString2Id"] = ids;
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickAssetApplicationSearchReset() {
        var buttonId = 'btnAssetApplicationSearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
            $('#search_productName' + token).val('');
            $('#search_ApplicantId' + token).val('');
            $('#search_Purpose' + token).val('');
            $('#search_DepartmentHeadrId' + token).val('');
            $('#search_DepartmentLeaderId' + token).val('');
            $('#search_financeDepartmentHeadId' + token).val('');
            $('#search_administrativeDepartmentHeadId' + token).val('');

            $('#search_time_start' + token).datebox('setValue', '');
            $('#search_time_end' + token).datebox('setValue', '');
            $('#search_reimburseTime_start' + token).datebox('setValue', '');
            $('#search_reimburseTime_end' + token).datebox('setValue', '');
            fw.combotreeClear('search_applicationDepartmentId' + token);
            fw.combotreeClear('search_AssetTypeId' + token);
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
};