/***
 *  差旅费报销逻辑控制js类
 *
 *  用法
 *    using(SCRIPTS_ROOT + '/oa/expense/FinanceBizTripExpenseWFA4DetailClass.js', function () {
            var financeBizTripExpenseWFA4DetailClass = new FinanceBizTripExpenseWFA4DetailClass(token);
            financeBizTripExpenseWFA4DetailClass.initModule();
        });
 *
 *  修改：周海鸿
 *  内容：完成详细注释
 *  时间：2015年 6月17日 09:24:00
 * @param token
 * @returns {{initModule: initModule}}
 * @constructor
 */


var FinanceBizTripExpenseWFA4DetailClass = function (token)
{

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickFinanceBizTripExpenseWFASearch();
        // 初始化查询重置事件
        onClickFinanceBizTripExpenseWFASearchReset();
        //初始化下拉
        initComboTree();
        // 初始化表格
        initFinanceBizTripExpenseWFATable();
        //初始化等待列表
        initWaitFinanceBizTripExpenseWFATable();
        //初始化以完成列表
        initParticipantFinanceBizTripExpenseWFATable();
    }


    /**
     * 初始化下拉
     */
    function initComboTree() {
        //初始化创建部门下拉列表
        var url = WEB_ROOT + "/system/Department_list.action";
        fw.combotreeLoadWithCheck('#search_orgName' + token, url, null, null, null);
    }

    // 构造申请初始化表格脚本
    function initFinanceBizTripExpenseWFATable() {
        //表格id设置
        var strTableId = 'financeBizTripExpenseWFATable' + token;

        //设置数据请求URL
        var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_list.action';

        //请求获取数据
        $('#' + strTableId).datagrid({
//            title: '差旅报销',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
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
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    {field: 'id', title: 'Id', hidden: true},
                    { field: 'controlString3', title: '数据标识号', hidden: false},
                    { field: 'controlString1', title: '公司名称', hidden: false},
                    { field: 'controlString2', title: '部门名称', hidden: false},
                    { field: 'controlString1Id', title: '公司名称', hidden: true},
                    { field: 'controlString2Id', title: '部门名称', hidden: true},
                    {field: 'orgId', title: '组织编号', hidden: true},
                    
                    {field: 'userId', title: '出差人编号', hidden: true},
                    {field: 'userNames', title: '出差人' },
                    {field: 'reimburseName', title: '报销人' },
                    {field: 'status', title: '状态', hidden: true},
                    {field: 'time', title: '报销日期'},
                    {field: 'money', title: '金额（元）'},
                    {field: 'accessoryNumber', title: '附件张数'},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"]== "" ?"":row["departmentLeaderName"]+"："+value;
                        } },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"]== "" ?"":row["generalManagerName"]+"："+value;
                        }  },
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"]== "" ?"":row["accountingName"]+"："+value;
                        }  },

                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"]== "" ?"":row["financeDirectorName"]+"："+value;
                        }  },
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {

                            return  row["executiveDirectorName"]== "" ?"":row["executiveDirectorName"]+"："+value;

                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"]== "" ?"":row["cashierName"]+"："+value;

                        }  },
                    { field: 'status', title: '是否完成', hidden: true },
                    { field: 'statusId', title: '状态', hidden: true  },
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},
                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinanceBizTripExpenseWFAAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnFinanceBizTripExpenseWFAEdit' + token,
                    text: '添加报销项目',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFinanceBizTripExpenseWFAcheck' + token,
                    text: '业务申请',
                    iconCls: 'icon-edit'
                },{
                    id: 'btnFinanceBizTripExpenseWFAUpload' + token,
                    text: '附件上传',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFinanceBizTripExpenseWFADelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                },
                {
                    id: 'financeBizTripExpenseWFAPrint' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                //添加
                onClickFinanceBizTripExpenseWFAAdd();
                //添加报销项目
                onClickFinanceBizTripExpenseWFAEdit();
                //业务审批
                onClickFinanceBizTripExpenseWFACheck();
                //删除事件
                onClickFinanceBizTripExpenseWFADelete();
                onClickFinanceBizTripExpenseWFAPrint('financeBizTripExpenseWFAPrint' + token,strTableId);

                onClickFinanceBizTripExpenseUpload();
            }
        });
    }

    /**
     * 修改人:周海鸿
     * 修改时间：2015-6-30
     * 修改事件:实现单一附件上传功能
     */

    function onClickFinanceBizTripExpenseUpload() {
        var butttonId = "btnFinanceBizTripExpenseWFAUpload" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('financeBizTripExpenseWFATable' + token, function (selected) {
                process.beforeClick();
                var upload = "upload";
                var id = selected.id
                //根据选择的用户ID 获取指定的数据
                //设置URL
                var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_load.action?FinanceBizTripExpenseWFA.id=' + selected.id;
                fw.post(url, {}, function (data) {
                    //设置工作流编号
                    data["WorkflowID"] = 8;
                    //设置业务编号
                    data["YWID"] = selected.id;
                    //设置路由编号
                    data["RouteListID"] = selected.routeListId;
                    //设置节点编号
                    data["CurrentNode"] = selected.currentNodeId;
                    data["controlString1"] = selected.controlString1Id;
                    data["financeBizTripExpenseWFAVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;

                    //设获取列表数据 设置到数据集中
                    data["orgName"] = selected.orgName;
                    data["userName"] = selected.userName;
                    data["reimburseName"] = selected.reimburseName;
                    initFinanceBizTripExpenseWFAWindow(data, upload,"3");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }
    // 构造等待审批初始化表格脚本
    function initWaitFinanceBizTripExpenseWFATable() {
        //表格id设置
        var strTableId = 'WaiitfinanceBizTripExpenseWFATable' + token;

        //设置数据请求URL
        var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_Waitlist.action';

        //请求获取数据
        $('#' + strTableId).datagrid({
//            title: '差旅报销',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
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
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                 [
                    {field: 'id', title: 'Id', hidden: true},
                     { field: 'controlString3', title: '数据标识号', hidden: false},
                     { field: 'controlString1', title: '公司名称', hidden: false},
                     { field: 'controlString2', title: '部门名称', hidden: false},
                     { field: 'controlString1Id', title: '公司名称', hidden: true},
                     { field: 'controlString2Id', title: '部门名称', hidden: true},
                    {field: 'orgId', title: '组织编号', hidden: true},
                    
                    {field: 'userId', title: '出差人编号', hidden: true},
                    {field: 'userNames', title: '出差人' },
                    {field: 'reimburseName', title: '报销人' },
                    {field: 'status', title: '状态', hidden: true},
                    {field: 'time', title: '报销日期'},
                    {field: 'money', title: '金额（元）'},
                     {field: 'accessoryNumber', title: '附件张数'},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"]== "" ?"":row["departmentLeaderName"]+"："+value;
                        } },
                     { field: 'generalManagerContent', title: '总经理审核内容',
                         formatter: function (value, row, index) {

                             return  row["generalManagerName"]== "" ?"":row["generalManagerName"]+"："+value;
                         }  },
                     { field: 'accountingContent', title: '会计审核内容',
                         formatter: function (value, row, index) {

                             return  row["accountingName"]== "" ?"":row["accountingName"]+"："+value;
                         }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"]== "" ?"":row["financeDirectorName"]+"："+value;
                        }  },
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {

                            return  row["executiveDirectorName"]== "" ?"":row["executiveDirectorName"]+"："+value;

                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"]== "" ?"":row["cashierName"]+"："+value;

                        }  },
                    { field: 'status', title: '是否完成', hidden: true },
                    { field: 'statusId', title: '状态', hidden: true  },
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},

                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}]
            ],
            toolbar: [
                {
                    id: 'btnWaitFinanceBizTripExpenseWFAcheck' + token,
                    text: '业务审批',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                //业务审批
                onClickWaitFinanceBizTripExpenseWFACheck();
            }
        });
    }
    // 构造初已完成始化表格脚本
    function initParticipantFinanceBizTripExpenseWFATable() {
        //表格id设置
        var strTableId = 'ParticipantfinanceBizTripExpenseWFATable' + token;

        //设置数据请求URL
        var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_Participantlist.action';

        //请求获取数据
        $('#' + strTableId).datagrid({
//            title: '差旅报销',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
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
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    {field: 'id', title: 'Id', hidden: true},
                    { field: 'controlString3', title: '数据标识号', hidden: false},
                    { field: 'controlString1', title: '公司名称', hidden: false},
                    { field: 'controlString2', title: '部门名称', hidden: false},
                    { field: 'controlString1Id', title: '公司名称', hidden: true},
                    { field: 'controlString2Id', title: '部门名称', hidden: true},
                    {field: 'orgId', title: '组织编号', hidden: true},
                    
                    {field: 'userId', title: '出差人编号', hidden: true},
                    {field: 'userNames', title: '出差人' },
                    {field: 'reimburseName', title: '报销人' },
                    {field: 'status', title: '状态', hidden: true},
                    {field: 'time', title: '报销日期'},
                    {field: 'money', title: '金额(元)'},
                    {field: 'accessoryNumber', title: '附件张数'},
                    { field: 'departmentLeaderContent', title: '部门负责人审核意见',
                        formatter: function (value, row, index) {

                            return  row["departmentLeaderName"]== "" ?"":row["departmentLeaderName"]+"："+value;
                        } },
                    { field: 'generalManagerContent', title: '总经理审核内容',
                        formatter: function (value, row, index) {

                            return  row["generalManagerName"]== "" ?"":row["generalManagerName"]+"："+value;
                        }  },
                    { field: 'accountingContent', title: '会计审核内容',
                        formatter: function (value, row, index) {

                            return  row["accountingName"]== "" ?"":row["accountingName"]+"："+value;
                        }  },
                    { field: 'financeDirectorContent', title: '财务总监审核意见',
                        formatter: function (value, row, index) {

                            return  row["financeDirectorName"]== "" ?"":row["financeDirectorName"]+"："+value;
                        }  },
                    { field: 'executiveDirectorContent', title: '执行董事审核意见',
                        formatter: function (value, row, index) {

                            return  row["executiveDirectorName"]== "" ?"":row["executiveDirectorName"]+"："+value;

                        }  },
                    { field: 'cashierContent', title: '出纳意见',
                        formatter: function (value, row, index) {

                            return  row["cashierName"]== "" ?"":row["cashierName"]+"："+value;

                        }  },
                    { field: 'status', title: '是否完成', hidden: true },
                    { field: 'statusId', title: '状态', hidden: true  },
                    {field: 'reimburseId', title: '报销人编号', hidden: true},
                    {field: 'reimburseTime', title: '报销人时间', hidden: true},
                    { field: 'currentNodeId', title: '当前节点', hidden: true },
                    { field: 'routeListId', title: '路由节点', hidden: true},

                    { field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinanceBizTripExpenseWFALook' + token,
                    text: '查看',
                    iconCls: 'icon-edit'
                },{
                    id: 'financeBizTripExpenseWFAPrint2' + token,
                    text: '打印',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                //添加
                onClickFinanceBizTripExpenseWFALook();
                onClickFinanceBizTripExpenseWFAPrint('financeBizTripExpenseWFAPrint2' + token,strTableId);
            }
        });
    }
    /*修改：周海鸿
     * 时间：2015-7-17
     * 内容：添加打印按钮*/
    function onClickFinanceBizTripExpenseWFAPrint(buttonId,tableid) {
        //var buttonId = 'financeBizTripExpenseWFAPrint' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected(tableid, function (selected) {

                process.beforeClick();

                var url =WEB_ROOT+"/modules/oa/modelsFiles/FinanceBizTripExpenseltemModels.jsp?id="+selected.id+"&token=" + token;
                window.open(url);

                process.afterClick();

            });
        });
    }

    /**
     * 添加事件
     */
    function onClickFinanceBizTripExpenseWFAAdd() {
        //设置添加安妮编号
        var buttonId = 'btnFinanceBizTripExpenseWFAAdd' + token;
        //按钮点击事件
        fw.bindOnClick(buttonId, function (process) {
            //用来识别页面如何显示
            var add = "add";
            //调用初始化窗口 弹出窗口界面
            initFinanceBizTripExpenseWFAWindow({controlString1:"",controlString2:""}, add, '1');
        });
    }

    /**
     * 修改事件
     */
    function onClickFinanceBizTripExpenseWFAEdit() {
        var buttonId = 'btnFinanceBizTripExpenseWFAEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('financeBizTripExpenseWFATable' + token, function (selected) {
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
                var id = selected.id
                //根据选择的用户ID 获取指定的数据
                //设置URL
                var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_load.action?FinanceBizTripExpenseWFA.id=' + selected.id;
                fw.post(url, {}, function (data) {
                   //将列表中的数据添加到请求数据中
                    data["orgName"] = selected.orgName
                    data["userName"] = selected.userName
                    data["reimburseName"] = selected.reimburseName

                    data["controlString1"] = selected.controlString1Id;
                    data["financeBizTripExpenseWFAVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    //调用窗口
                    initFinanceBizTripExpenseWFAWindow(data, "",'2');
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }
    /**
     * 查看事件
     */
    function onClickFinanceBizTripExpenseWFALook() {
        var buttonId = 'btnFinanceBizTripExpenseWFALook' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ParticipantfinanceBizTripExpenseWFATable' + token, function (selected) {
                process.beforeClick();

                var id = selected.id
                //根据选择的用户ID 获取指定的数据
                //设置URL
                var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_load.action?FinanceBizTripExpenseWFA.id=' + selected.id;
                fw.post(url, {}, function (data) {
                   //将列表中的数据添加到请求数据中
                    data["orgName"] = selected.orgName
                    data["userName"] = selected.userName
                    data["reimburseName"] = selected.reimburseName

                    data["controlString1"] = selected.controlString1Id;
                    data["financeBizTripExpenseWFAVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    //调用窗口
                    initFinanceBizTripExpenseWFAWindow(data, "look", '2');
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }

    /**
     * 业务审批
     */
    function onClickFinanceBizTripExpenseWFACheck() {
        var buttonId = 'btnFinanceBizTripExpenseWFAcheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('financeBizTripExpenseWFATable' + token, function (selected) {

              //判断数据是否满足条件进去业务流程
                if (selected.money <= 0) {

                    fw.alert("警告", "请添加报销项目！")
                    return;
                }
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经提交申请不得重复提交");
                    process.afterClick();
                    return;
                }
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_load.action?FinanceBizTripExpenseWFA.id=' + selected.id;
                fw.post(url, {}, function (data) {
                    data["workflowID"] = 8;

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


                   //设获取列表数据 设置到数据集中
                    data["orgName"] = selected.orgName
                    data["userName"] = selected.userName
                    data["reimburseName"] = selected.reimburseName

                    //当前窗口状态为审批状态
                    //调用窗口
                    initFinanceBizTripExpenseWFAWindow(data, "applay", '3');

                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }
    /**
     * 业务审批
     */
    function onClickWaitFinanceBizTripExpenseWFACheck() {
        var buttonId = 'btnWaitFinanceBizTripExpenseWFAcheck' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('WaiitfinanceBizTripExpenseWFATable' + token, function (selected) {

              //判断数据是否满足条件进去业务流程
                if (selected.money <= 0) {

                    fw.alert("警告", "请添加报销项目！")
                    return;
                }
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_load.action?FinanceBizTripExpenseWFA.id=' + selected.id;
                fw.post(url, {}, function (data) {
                    //设置工作流编号
                    data["WorkflowID"] = 8;
                    //设置业务编号
                    data["YWID"] = selected.id;
                    //设置路由编号
                    data["RouteListID"] = selected.routeListId;
                    //设置节点编号
                    data["CurrentNode"] = selected.currentNodeId;
                    data["controlString1"] = selected.controlString1Id;
                    data["financeBizTripExpenseWFAVO.controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;

                   //设获取列表数据 设置到数据集中
                    data["orgName"] = selected.orgName
                    data["userName"] = selected.userName
                    data["reimburseName"] = selected.reimburseName

                    //当前窗口状态为审批状态
                    var check = "check";

                    //调用窗口
                    initFinanceBizTripExpenseWFAWindow(data, check, '3');

                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }

    /**
     * 删除事件
     */
    function onClickFinanceBizTripExpenseWFADelete() {
        var buttonId = 'btnFinanceBizTripExpenseWFADelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('financeBizTripExpenseWFATable' + token, function (selected) {
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
                //提示是否确认删除
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    //修改数据状态URL
                    var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_delete.action?FinanceBizTripExpenseWFA.sid=' + selected.sid;
                    fw.post(url, {}, function (data) {
                        process.afterClick();
                        //刷新列表
                        fw.datagridReload('financeBizTripExpenseWFATable' + token);
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
     * @param temp 用来识别页面如何显示
     * @param windowStatus 用来判断窗口是属于什么状态，控制窗口的大小 1：添加： 其他：修改，审批
     */
    function initFinanceBizTripExpenseWFAWindow(data, temp, windowStatus) {

        //设置窗口大小
        var height = 0
        var width = 0;
        //窗口为添加状态的大小 窗口状态为添加状态
        if (windowStatus == '1') {
            height = 400;
            width = 600;
            //设置操作人、操作时间
            data['financeBizTripExpenseWFA.operatorId'] = loginUser.getId();
            data['financeBizTripExpenseWFA.operatorName'] = loginUser.getName();
        }//其他事件窗口大小
        else {
            width = 700;
            height = 600;
        }
        //设置窗口数据请求URL
        var url = WEB_ROOT + '/modules/oa/expense/FinanceBizTripExpenseWFA4Detail_Save.jsp?token=' + token;
        //设置窗口ID
        var windowId = 'financeBizTripExpenseWFAWindow' + token;
        //汤出窗口
        fw.window(windowId, '差旅报销', width, height, url, function () {
            fw.textFormatCurrency('money'+token);

            //初始化选择出差人
            initUserName();

            //初始化选择报销人
            initReimburseName();
            var temps=false;
            //窗口为差可能时
            if(temp == "look"){
                $("#btnFinanceBizTripExpenseWFASubmit" + token).remove();
                $("#btnFinanceBizTripExpenseWFASubmit_start" + token).remove();
                $("#btnFinanceBizTripExpenseWFASubmit_applay" + token).remove();
                // 加载附表列表数据
                using(SCRIPTS_ROOT + '/oa/expense/FinanceBizTripExpenseItemClass.js', function () {
                    var financeBizTripExpenseItemClass = new FinanceBizTripExpenseItemClass(token, "check", data["financeBizTripExpenseWFA.id"]);
                    financeBizTripExpenseItemClass.initModule();
                });

            }

            //窗口为添加时的
            else if (temp == "add") {
                //去除与主表没关系的元素
                $("#financeBizTripDIV" + token).remove();
                $("#btnFinanceBizTripExpenseWFASubmit_start" + token).remove();
                $("#btnFinanceBizTripExpenseWFASubmit_applay" + token).remove();


            }
            //窗口为检查状态
            else if (temp == "check") {
                //去除提交按钮
                $("#btnFinanceBizTripExpenseWFASubmit" + token).remove();
                $("#btnFinanceBizTripExpenseWFASubmit_applay" + token).remove();
                //使报销人无法修改
                $("#reimburseName" + token).attr("readonly", true)
                // 加载附表列表数据
                using(SCRIPTS_ROOT + '/oa/expense/FinanceBizTripExpenseItemClass.js', function () {
                    var financeBizTripExpenseItemClass = new FinanceBizTripExpenseItemClass(token, temp, data["financeBizTripExpenseWFA.id"]);
                    financeBizTripExpenseItemClass.initModule();
                });
                //调用业务控制方法
                fw.onClickBizSubmit(token, "btnFinanceBizTripExpenseWFASubmit_start" + token,
                    data,"financeBizTripExpenseWFATable" + token,"WaiitfinanceBizTripExpenseWFATable" + token,"ParticipantfinanceBizTripExpenseWFATable" + token, windowId);
                temps=true;
            }
            // 申请
            else if(temp=="applay"){

                $("#btnFinanceBizTripExpenseWFASubmit" + token).remove();
                $("#btnFinanceBizTripExpenseWFASubmit_start" + token).remove();
                //使报销人无法修改
                $("#reimburseName" + token).attr("readonly", true)
                // 加载附表列表数据
                using(SCRIPTS_ROOT + '/oa/expense/FinanceBizTripExpenseItemClass.js', function () {
                    var financeBizTripExpenseItemClass = new FinanceBizTripExpenseItemClass(token, temp, data["financeBizTripExpenseWFA.id"]);
                    financeBizTripExpenseItemClass.initModule();
                });

                onClickFinanceBizTripExpenseWFApplaySubmit(data,windowId);
            }
            //其他状态
            else {
                //去除业务发送按钮
                $("#btnFinanceBizTripExpenseWFASubmit_start" + token).remove();
                $("#btnFinanceBizTripExpenseWFASubmit_applay" + token).remove();
                //使包报销人无法修改
                $("#reimburseName" + token).attr("readonly", true)
                // 调用附表
                using(SCRIPTS_ROOT + '/oa/expense/FinanceBizTripExpenseItemClass.js', function () {
                    var financeBizTripExpenseItemClass = new FinanceBizTripExpenseItemClass(token, temp, data["financeBizTripExpenseWFA.id"]);
                    financeBizTripExpenseItemClass.initModule();
                });
            }
            if(temp=="upload" || temp=="check") {
                //附件上传页面按钮状态
                var btnstatus = "";
                if(temp=="check"){
                    btnstatus = "remove";
                }

                fw.initFileUpload(data["financeExpense.id"], 18832, 'btnUpload' + token, btnstatus, token);
            }else{

                $("#uploadTR" + token).remove();
            }
            //下拉列表
            //获取选中的节点
            var selectString1 = data["controlString1"] == "" ? "-2" : data["controlString1"];
            var selectString2 = data["controlString2"] == "" ? "-2" : data["controlString2"];
            //公司部门二级下拉列表

            fw.doubleDepartmentTrees("controlString1"+token ,"controlString2"+token ,selectString1,selectString2,temps);


            //主表提交保存事件
            onClickFinanceBizTripExpenseWFASubmit();

            // 加载数据表单中
            fw.formLoad('formFinanceBizTripExpenseWFATable' + token, data);

        }, null);
    }
    /**
     *
     * 申请
     * @param data
     * @param windowid
     */
    function onClickFinanceBizTripExpenseWFApplaySubmit(data,windowid) {
        var buttonId = "btnFinanceBizTripExpenseWFASubmit_applay" + token;
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            data["serviceClassName"]="";
            fw.onclickworkflowApplay(data,process,"financeBizTripExpenseWFATable" + token,"WaiitfinanceBizTripExpenseWFATable" + token,"ParticipantfinanceBizTripExpenseWFATable" + token,windowid);
            provess.afterClick
        });
    }
    /**
     * 主表数据提交事件
     */
    function onClickFinanceBizTripExpenseWFASubmit() {
        //设置按钮ID
        var buttonId = 'btnFinanceBizTripExpenseWFASubmit' + token;
        //点击事件
        fw.bindOnClick(buttonId, function (process) {
            //设置表单ID
            fw.CurrencyFormatText('money'+token);
            var formId = 'formFinanceBizTripExpenseWFATable' + token;

            //设置请求URL
            var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseWFA_insertOrUpdate.action';

            //提交表单事件
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                //刷新列表
                fw.datagridReload('financeBizTripExpenseWFATable' + token);
                //关闭窗口
                fw.windowClose('financeBizTripExpenseWFAWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 初始化点击查询事件
     */
    function onClickFinanceBizTripExpenseWFASearch() {
        //设置按钮ID
        var buttonId = 'btnFinanceBizTripExpenseWFASearchSubmit' + token;

        fw.bindOnClick(buttonId, function (process) {
        //设置列表ID
            var strTableId = 'financeBizTripExpenseWFATable' + token;
            //获取列表参数集合对象
            var params = $('#' + strTableId).datagrid('options').queryParams;

            //将需要查询的数据添加到列表参数集合对象中
            params["financeBizTripExpenseWFAVO.controlString2"] = fw.getFormValue("search_orgName" + token, fw.type_form_combotree, fw.type_get_text);
            params["financeBizTripExpenseWFAVO.userNames"] = $("#search_userName" + token).val();


            params["financeBizTripExpenseWFAVO.money"] = $("#search_money" + token).val();
            params["financeBizTripExpenseWFAVO.reimburseName"] = $("#search_reimburseName" + token).val();

            params["financeBizTripExpenseWFA_reimburseTime_Start"] = fw.getFormValue('search_reimburseTime_start' + token, fw.type_form_datebox, fw.type_get_value);
            params["financeBizTripExpenseWFA_reimburseTime_End"] = fw.getFormValue('search_reimburseTime_end' + token, fw.type_form_datebox, fw.type_get_value);
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 初始化查询重置事件
     */
    function onClickFinanceBizTripExpenseWFASearchReset() {
        //设置按钮ID
        var buttonId = 'btnFinanceBizTripExpenseWFASearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            //将文本框喝树形节点的文本框清空
            $("#search_userName" + token).val("");
            $('#search_time_start' + token).datebox("setValue", '');
            $('#search_time_end' + token).datebox("setValue", '');
            $("#search_money" + token).val("");
            $("#search_reimburseName" + token).val("");
            $("#search_reimburseId" + token).val("");
            $('#search_reimburseTime_start' + token).datebox("setValue", '');
            $('#search_reimburseTime_end' + token).datebox("setValue", '');
            fw.combotreeClear('search_orgName' + token);
        });
    }

    /**
     *
     初始化选择出差人
     */

    function initUserName() {
        //设置按钮ID
        var textID = "#userNames" + token;
        //按钮点击事件
        $(textID).bind('click', function () {
            //加载用户选择脚本
            using(SCRIPTS_ROOT + '/system/UserClass(Discard).js', function () {
                var userClass = new UserClass(token);
                userClass.windowOpenUserSelection(true, token, function (row) {
                    //获取选择的用户名字 ，ID
                    var names= $("#userNames" + token).val();
                    var userids= $("#userId" + token).val();
                    if(names ==""){
                        $("#userId" + token).val("");
                    }
                    $("#userNames" + token).val((names == ""?"":names+",")+row[0].name);
                    $("#userId" + token).val((userids== ""?"":userids+",")+row[0].id);
                    //设置用户部门查询URL
                    var orgNameUrl = WEB_ROOT + "/system/User_getUserForDepartmentName.action?user.id=" + row[0].id;
                    //请求获取制定用户的部门信息
                    fw.post(orgNameUrl, null, function (orgData) {
                        //获取选择的用户名字 ，ID
                        var deparname= $("#departmentName" + token).val();
                        var deparid= $("#departmentId" + token).val();
                        if(deparname ==""){
                            $("#departmentId" + token).val("");
                        }
                        //将部门信息设置到表单中
                        $("#departmentName" + token).val((deparname == ""?"":deparname+",")+orgData["name"]);
                        $("#departmentId" + token).val((deparid == ""?"":deparid+",")+orgData["id"]);
                    })
                });

            })
        });
    }
    /**
     *
     初始化选择报销人
     *
     */
    function initReimburseName() {
        //设置按钮ID
        var textID = "#reimburseName" + token;
        //按钮点击事件
        $(textID).bind('click', function () {
            //加载用户选择脚本
            using(SCRIPTS_ROOT + '/system/UserClass(Discard).js', function () {
                var userClass = new UserClass(token);
                userClass.windowOpenUserSelection(true, token + 1, function (row) {
                    //获取选择的用户名字 ，ID
                    $("#reimburseName" + token).val(row[0].name);
                    $("#reimburseId" + token).val(row[0].id);
                    $("#reimburseTime" + token).val(fw.getDateTime());
                });

            })
        })
    }

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
};