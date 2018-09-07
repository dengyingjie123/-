/**
 * SalemanGroup 脚本对象
 */
var SalesmanGroupClass = function (token, my) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickSalemanGroupSearch();
        // 初始化查询重置事件
        onClickSalemanGroupSearchReset();

        // 初始化表格
        initSalemanGroupTable();

        // 初始化查询表单
        initSalemanGroupSearchForm();
        //初始化选中区域
        initSelectArea();
    }

    /**
     * 初始化查询表单
     */
    function initSalemanGroupSearchForm() {         //初始化部门下拉列表
        var url = WEB_ROOT + "/system/Department_getDepartments4FortuneCenter.action";
        fw.combotreeLoad("Search_departmentId" + token, url, "-2");

    }

    // 构造初始化表格脚本
    function initSalemanGroupTable() {
        var li1 = 15;
        var li2 = 30;
        var li3 = 45;
        var size = 15;
        if (my != null) {
            li1 = 10;
            li2 = 15;
            li3 = 2;
            size = 10;
        }
        var strTableId = 'SalemanGroupTable' + token;
        var url = WEB_ROOT + '/sale/SalemanGroup_list.action';

        $('#' + strTableId).datagrid({
            title: '销售小组列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [li1, li2, li3],
            pageSize: size,
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
                    { field: 'sid', title: 'sid', hidden: true},
                    { field: 'id', title: 'id', hidden: true},
                    { field: 'state', title: 'state', hidden: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'departmentName', title: '公司', sortable: true},
                    { field: 'departmentId', title: '公司',hidden: true, sortable: false},
                    { field: 'name', title: '组名', sortable: true},
                    { field: 'description', title: '描述', sortable: true},
                    { field: 'groupArea', title: '区域', sortable: true}
                ]
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickSalemanGroupAdd();
                onClickSalemanGroupEdit();
                onClickSalemanGroupDelete();
            }
        });
    }
    // todo  调用用来天机小组分配
//    function onClickSalemanGroupcs(){
//        var buttonId = 'btnSalemanGroupcs' + token;
//        fw.bindOnClick(buttonId, function (process) {
//            using(SCRIPTS_ROOT + '/sale/CustomerSaleClass.js', function () {
//                var customerSaleClass = new CustomerSaleClass(token, null);
//               customerSaleClass.initModule();
//            });
//        });
//    }

    /**
     * 添加事件
     */
    function onClickSalemanGroupAdd() {
        var buttonId = 'btnSalemanGroupAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initSalemanGroupWindow({});
        });
    }

    /**
     * 初始化显示本小组销售人员列表
     */
    function initSaleManTable(saleManGroupId) {
        var strTableId = "SaleManTable" + token;
        var url = WEB_ROOT + "/sale/SalemanGroup_getSaleMans.action?salemanGroup.id=" + saleManGroupId;
        $('#' + strTableId).datagrid({
            title: '销售人员信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [5, 10, 15],
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
                    { field: 'sid', title: '序号', hidden: true, width: 20 },
                    { field: 'id', title: '编号', hidden: true, width: 20 },
                    { field: 'saleManStatus', title: '岗位', width: 30, formatter: function (value, row, index) {
                        //如果返回的值等于1的时候
                        if (value == 2) {
                            return "负责人";
                        } else {
                            return "销售员";
                        }

                    } },
                    { field: 'defaultGroup', title: '默认销售组', width: 30, formatter: function (value, row, index) {
                        //如果返回的值等于1的时候
                        if (value == 1) {
                            return "是";
                        } else {
                            return "否";
                        }

                    } },
                    { field: 'userName', title: '名字', width: 30 },
                    { field: 'gender', title: '性别', width: 10 },
                    { field: 'mobile', title: '电话', width: 20 },
                    { field: 'email', title: '邮箱', width: 30 },
                    { field: 'jointime', title: '入职日期', width: 30 },
                    { field: 'salesLevel', title: '销售级别', width: 30 },
                    { field: 'position', title: '公司职位', width: 30 }
                ]
            ],
            toolbar: [
                {
                    id: 'btnSalemanAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSalemanduty' + token,
                    text: '修改为负责人',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSalemans' + token,
                    text: '修改为销售人员',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSalemanDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                },
                {
                    id: 'btnDefaultGroup' + token,
                    text: '设置默认销售组',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                onclickSalemanAdd(saleManGroupId);
                OnclickSalemanDelete(saleManGroupId);
                OnClickdutySaleman(saleManGroupId);
                OnClickSalemans(saleManGroupId);
                OnclickSalemanDelete(saleManGroupId);
                OnclickDefaultGroup(saleManGroupId);
            }
        });
    }


    /**
     * 删除销售人员
     * @param saleManGroupId
     */
    function OnclickSalemanDelete(saleManGroupId) {
        var buttonId = 'btnSalemanDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SaleManTable' + token, function (selected) {
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url2 = WEB_ROOT + "/sale/SalemanSalemangroup_load?"
                    var s = "saleManSaleManGroup.saleManId=" + selected.id + "&saleManSaleManGroup.saleManGroupId=" + saleManGroupId;
                    var saleManSaleManGroupId = "";
                    fw.post(url2 + s, null, function (data) {
                        saleManSaleManGroupId = data["id"];
                        if (saleManSaleManGroupId == "") {
                            fw.alert("提示", "系统数据错误！请联系管理员");
                            return;
                        }
                        var url = WEB_ROOT + '/sale/SalemanSalemangroup_delete.action?saleManSaleManGroup.id=' + saleManSaleManGroupId;
                        fw.post(url, null, function (data) {
                            process.afterClick();
                            fw.datagridReload('SaleManTable' + token);
                            fw.datagridReload('dutyTable' + token);
                        }, null);
                    }, null);

                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 添加销售人员
     */
    function onclickSalemanAdd(saleManGroupId) {
        var buttonId = "btnSalemanAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            initWindowSaleMan(saleManGroupId);
        });
    }


    /***
     * 设置销售人员的默认销售组
     */
    function OnclickDefaultGroup(saleManGroupId) {
        var buttonId = "btnDefaultGroup" + token;
        fw.bindOnClick(buttonId,function (process) {
            fw.datagridGetSelected('SaleManTable' + token, function (selected) {
                process.beforeClick();
                var url2 = WEB_ROOT + "/sale/SalemanSalemangroup_load?"
                var s = "saleManSaleManGroup.saleManId=" + selected.id + "&saleManSaleManGroup.saleManGroupId=" + saleManGroupId;
                var saleManSaleManGroupId = "";
                fw.post(url2 + s, null, function (data) {
                    if (data["id"] == "") {
                        fw.alert("提示", "系统数据错误！请联系管理员");
                        return;
                    }
                    var ss = "saleManSaleManGroup.id=" + data["id"] + "&saleManSaleManGroup.saleManId=" + data["saleManId"] + "&saleManSaleManGroup.saleManGroupId=" + data["saleManGroupId"] + "&saleManSaleManGroup.saleManStatus=" + data["saleManStatus"];
                    var url = WEB_ROOT + '/sale/SalemanSalemangroup_UpdateDefaultGroup.action?';
                    fw.post(url + ss, null, function () {
                        process.afterClick();
                        fw.datagridReload('SaleManTable' + token);

                    }, function () {
                        process.afterClick();
                    });
                }, function () {
                    process.afterClick();
                });
            });
        });
    }


    /***
     *初始化弹出选择人员列表
     */
    function initWindowSaleMan(saleManGroupId) {
        var windowId = "SelectSaleManWindow" + token;
        var url = WEB_ROOT + "/modules/sale/Saleman_Select.jsp?token=" + token;
        fw.window(windowId, "销售人员列表", 420, 550, url, function (saleManGroupId) {
            /**
             * 选中销售成员
             */
            OnSelectSaleman(saleManGroupId);
            using(SCRIPTS_ROOT + "/sale/SalesmanInfoClass.js", function () {
                var salesmanInfoClass = new SalesmanInfoClass(token, null, saleManGroupId, "");
                salesmanInfoClass.initModule();
            })
        }, null, saleManGroupId);
    }


    /**
     * 初始化添加销售小组负责人
     * @param saleManGroupId
     * @constructor
     */
    function OnClickdutySaleman(saleManGroupId) {
        var buttonId = "btnSalemanduty" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SaleManTable' + token, function (selected) {
                process.beforeClick();
                var url2 = WEB_ROOT + "/sale/SalemanSalemangroup_load?"
                var s = "saleManSaleManGroup.saleManId=" + selected.id + "&saleManSaleManGroup.saleManGroupId=" + saleManGroupId;
                var saleManSaleManGroupId = "";
                fw.post(url2 + s, null, function (data) {
                    if (data["id"] == "") {
                        fw.alert("提示", "系统数据错误！请联系管理员");
                        return;
                    }
                    var ss = "saleManSaleManGroup.id=" + data["id"] + "&saleManSaleManGroup.saleManId=" + data["saleManId"] + "&saleManSaleManGroup.saleManGroupId=" + data["saleManGroupId"] + "&saleManSaleManGroup.defaultGroup=" + data["defaultGroup"];
                    var url = WEB_ROOT + '/sale/SalemanSalemangroup_UpdateSaleManStatusFor2.action?';
                    fw.post(url + ss, null, function () {
                        process.afterClick();
                        fw.datagridReload('SaleManTable' + token);

                    }, function () {
                        process.afterClick();
                    });
                }, function () {
                    process.afterClick();
                });
            });
        })
    }

    /**
     * 初始化修改销售成员事件
     * @constructor
     */
    function OnClickSalemans(saleManGroupId) {
        var buttonId = "btnSalemans" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SaleManTable' + token, function (selected) {
                process.beforeClick();
                var url2 = WEB_ROOT + "/sale/SalemanSalemangroup_load?"
                var s = "saleManSaleManGroup.saleManId=" + selected.id + "&saleManSaleManGroup.saleManGroupId=" + saleManGroupId;
                var saleManSaleManGroupId = "";
                fw.post(url2 + s, null, function (data) {
                    if (data["id"] == "") {
                        fw.alert("提示", "系统数据错误！请联系管理员");
                        return;
                    }
                    var ss = "saleManSaleManGroup.id=" + data["id"] + "&saleManSaleManGroup.saleManId=" + data["saleManId"] + "&saleManSaleManGroup.saleManGroupId=" + data["saleManGroupId"] + "&saleManSaleManGroup.defaultGroup=" + data["defaultGroup"];
                    var url = WEB_ROOT + '/sale/SalemanSalemangroup_UpdateSaleManStatusFor1.action?';
                    fw.post(url + ss, null, function () {
                        process.afterClick();
                        fw.datagridReload('SaleManTable' + token);
                    }, function () {
                        process.afterClick();
                    });
                }, function () {
                    process.afterClick();
                });
            });
        })
    }

    /**
     * 初始化选中销售成员事件
     * @constructor
     */
    function OnSelectSaleman(saleManGroupId) {
        var buttonId = "btnSelectSalesmanSelection" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SelectsalesManTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.userId;//获取列表的用户编号
                var ss = "saleManSaleManGroup.saleManId=" + id + "&saleManSaleManGroup.saleManGroupId=" + saleManGroupId;
                var url = WEB_ROOT + '/sale/SalemanSalemangroup_Insert.action?' + ss;
                fw.post(url, null, function () {
                    fw.datagridReload('SaleManTable' + token);
                    fw.windowClose('SelectSaleManWindow' + token);
                }, function () {
                    process.afterClick();
                });
            });
        })
    }

    /**
     * 修改事件
     */
    function onClickSalemanGroupEdit() {
        var buttonId = 'btnSalemanGroupEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SalemanGroupTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/sale/SalemanGroup_load.action?salemanGroup.id=' + selected.id;
                fw.post(url, null, function (data) {
                    initSalemanGroupWindow(data);
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
    function onClickSalemanGroupDelete() {
        var buttonId = 'btnSalemanGroupDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SalemanGroupTable' + token, function (selected) {
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + '/sale/SalemanGroup_delete.action?salemanGroup.departmentId='+selected.departmentId+'&salemanGroup.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('SalemanGroupTable' + token);
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
    function initSalemanGroupWindow(data) {

        // fw.alertReturnValue(data);

        data['salemanGroup.operatorId'] = loginUser.getId();
        data['salemanGroup.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/sale/SalemanGroup_Save.jsp?token=' + token;
        var windowId = 'SalemanGroupWindow' + token;
        fw.window(windowId, '销售小组', 650, 550, url, function () {


            fw.getComboTreeFromKV('areaId'+token, 'salesgroup_area', 'V', fw.getMemberValue(data, 'salemanGroup.areaId'));

            //提交事件
            onClickSalemanGroupSubmit();
            //初始化部门下拉列表

            var url = WEB_ROOT + "/system/Department_getDepartments4FortuneCenter.action";
            fw.combotreeLoad("departmentId" + token, url, data["salemanGroup.departmentId"]);



            if (data["salemanGroup.id"] != undefined) {
                initSaleManTable(data["salemanGroup.id"]);
            }
            // 加载数据
            fw.formLoad('formSalemanGroup' + token, data);

        });
    }




    /**
     * 数据提交事件
     */
    function onClickSalemanGroupSubmit() {
        var buttonId = 'btnSalemanGroupSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            //创建访问服务器判断是否可以添加销售小组
            var url = WEB_ROOT + "/sale/SalemanGroup_isInstalSalemanGroup.action?department.id=" + fw.getFormValue("departmentId" + token, fw.type_form_combotree, fw.type_get_value);
            //请求服务器
            fw.post(url, null, function (date) {
                //判断服务费返回的值是否允许添加
                if (date != true) {
                    fw.alert("警告", "只有财富中心和渠道可以增加销售小组");
                }
                //提交事件
                else {
                    var formId = 'formSalemanGroup' + token;
                    var url = WEB_ROOT + '/sale/SalemanGroup_insertOrUpdate.action';
                    fw.bindOnSubmitForm(formId, url, function () {
                        process.beforeClick();
                    }, function () {
                        process.afterClick();
                        fw.datagridReload('SalemanGroupTable' + token);
                        fw.windowClose('SalemanGroupWindow' + token);
                    }, function () {
                        process.afterClick();
                    });
                }
            });

        });
    }

    /**
     * 查询事件
     */
    function onClickSalemanGroupSearch() {
        var buttonId = 'btnSalemanGroupSearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'SalemanGroupTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params['salemanGroupVO.name'] = $('#Search_Name' + token).val();
            params['salemanGroupVO.description'] = $('#Search_Description' + token).val();
            params['salemanGroupVO.departmentId'] = fw.getFormValue("Search_departmentId" + token, fw.type_form_combotree, fw.type_get_value);

            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickSalemanGroupSearchReset() {
        var buttonId = 'btnSalemanGroupSearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
            $('#Search_Name' + token).val('');
            $('#Search_Description' + token).val('');
            fw.treeClear("Search_departmentId" + token);
        });
    }

    /**
     * 初始化选中的区域
     */
    function initSelectArea() {
        if (my == null) {
            $("#SalemanGroupSelectArea" + token).remove();
        } else {
            $("#btnSalemanGroupAdd" + token).remove();
            $("#btnSalemanGroupEdit" + token).remove();
            $("#btnSalemanGroupDelete" + token).remove();
            //初始化选择项目确定按钮
            var buttonId = "btnSelect" + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected('SalemanGroupTable' + token, function (selected) {
                    process.beforeClick();
                    var id = selected.id;
                    var url = WEB_ROOT + "/sale/SalemanGroup_load.action?salemanGroup.id=" + id;
                    fw.post(url, null, function (data) {
                        my.loadSelectedSaleman(data);
                        fw.windowClose("SalemanGroupSelectWindow" + token);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                })

            });
        }
    }

    function initSalesmanGroupTable() {
        var strTableId = 'SalesmanGroupTable' + token;
        //alert(strTableId);
        var url = WEB_ROOT + "/sale/Salesman_listPagerSalesmanGroup.action";

        $('#' + strTableId).datagrid({
            title: '理财师列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true, //多选
            pageList: [10],
            pageSize: 10,
            rownumbers: true,
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);

                    //alert(JSON.stringify(data));
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [
                    // 固定列，没有滚动条
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    {field: 'sid', title: 'sid', hidden: true},
                    {field: 'id', title: 'id', hidden: true},
                    {field: 'operatorId', title: 'operatorId', hidden: true},
                    {field: 'operateTime', title: 'operateTime', hidden: true},
                    {field: 'groupId', title: 'groupId', hidden: true},
                    {field: 'userName', title: '销售人'},
                    {field: 'groupName', title: '销售组'},
                    {field: 'groupAreaName', title: '区域'}
                ]
            ],
            onLoadSuccess: function () {

                // 初始化查询
                fw.bindOnClick('btnSalemanGroupSearchSubmit'+token, function (process) {
                    var strTableId = 'SalesmanGroupTable' + token;
                    var params = $('#' + strTableId).datagrid('options').queryParams;

                    params['groupName'] = $('#Search_GroupName' + token).val();
                    params['userName'] = $('#Search_UserName' + token).val();

                    $('#' + strTableId).datagrid('load');
                });

                fw.bindOnClick('btnSalemanGroupSearchReset'+token, function (process) {
                    // 清空时间文本框
                    $('#Search_GroupName' + token).val('');
                    $('#Search_UserName' + token).val('');
                });
            }
        });
    }

    function onClickSelectSalesman(callback) {
        fw.bindOnClick('btnSelect'+token, function(){
            fw.datagridGetSelected('SalesmanGroupTable'+token, function(selected){
                //alert(selected.userName);

                if (fw.checkIsFunction(callback)) {
                    callback(selected);
                }

                fw.windowClose('SalesmanGroupSelectWindow'+token);
            })
        })
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initModuleWithSelectWindow:function(callback){
            var url = WEB_ROOT + "/modules/sale/SalemanGroup_Select.jsp?token=" + token;
            var windowId = "SalesmanGroupSelectWindow" + token;
            fw.window(windowId, '选择销售人员', 530, 520, url, function () {

                initSalesmanGroupTable();
                onClickSelectSalesman(callback);

            }, null);
        }
    };
};
