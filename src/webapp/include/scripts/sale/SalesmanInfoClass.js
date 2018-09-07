var idChange = null;    //修改添加和删除操作用的
var idChange2 = null;                //修改添加和删除操作用的

/**
 * 修改人周海鸿
 * 修改时间：2015-6-29
 * 修改时间：添加productionId 参数 用来作为条件查询小组
*/
var SalesmanInfoClass = function (token, obj, salemangroupid, SaleManStatus,productionId) {
    var tableName = "salesManTable";
    var btnAdd = "btnAddSalesman" + token;
    var btnUpdate = "btnUpdateSalesman" + token;
    var btnDelete = "btnDeleteSalesman" + token;
    var addSuccess = false;
    var returnData = [];

    function initAll() {

        //初始化查询事件
        onClickSearch();
        // 初始化查询重置事件
        onClickSearchReset();
        //初始化查询事件
        onClickSearch2();
        // 初始化查询重置事件
        onClickSearchReset2();
        if (SaleManStatus != "") {
            if (SaleManStatus == '3') {
                var url = WEB_ROOT + "/sale/SaleTask4Group_getNotAllocationSaleMans.action?saleTask4Group.saleGroupId=" + salemangroupid+"&saleTask4Group.productionId="+productionId;
                //显示销售小组的人员列表
                initSelectTable(url);
            } else {
                var url = WEB_ROOT + "/sale/SalemanGroup_getSaleMans.action?salemanGroup.id=" + salemangroupid;
                //显示销售小组的人员列表
                initSelectTable(url);
            }
        }
        else if (salemangroupid != "") {
            var url = WEB_ROOT + "/sale/SalemanGroup_getSaleManNoInList.action?salemanGroup.id=" + salemangroupid;
            initSelectTable(url);
        } else {
            // 初始化项目信息选择界面表格
            initTable();
        }

        //根据管理界面，还是查询窗口，初始化选择区域
        initSelectArea();

    }

    /**
     * 显示本销售销售列表
     * @param salemangroupid
     * @param SaleManStatus
     */
    function initSelectTable(url) {
        var strTableId = "SelectsalesManTable" + token;
        $('#' + strTableId).datagrid({
            title: '销售人员信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [10, 15, 20],
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
                    { field: 'userName', title: '名字' }

                ]
            ],
            columns: [
                [
                    { field: 'sid', title: '序号', hidden: true },
                    { field: 'id', title: '编号', hidden: true },
                    {field: 'userId', title: "用户编号", hidden: true, width: 20},
                    { field: 'gender', title: '性别' },
                    { field: 'mobile', title: '电话' },
                    { field: 'email', title: '邮箱'},
                    { field: 'birthday', title: '生日'},
                    { field: 'salesLevel', title: '销售级别'}
                ]
            ]
        });
    }

    //-----------初始化部分--------------//
    /**
     * 初始化表格
     */
    function initTable() {
        var strTableId = tableName + token;
        var url = WEB_ROOT + "/sale/Salesman_list.action";
        var rows = 10, list1 = 10, list2 = 30, list3 = 60;
        $('#' + strTableId).datagrid({
            title: '销售人员信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [list1, list2, list3],
            pageSize: rows,
            rownumbers: true,
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);
                    //fw.alertReturnValue(data);
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    { field: 'userName', title: '名字' }
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: 'sid', hidden: true },
                    { field: 'id', title: 'id', hidden: true },
                    { field: 'state', title: 'state', hidden: true },
                    { field: 'operatorId', title: 'operatorId', hidden: true },
                    { field: 'operateTime', title: 'operateTime', hidden: true },
                    { field: 'genderName', title: '性别', width: 30 },
                    { field: 'idnumber', title: '身份号码', hidden: true},
                    { field: 'staffCode', title: '员工编号', width: 30 },
                    { field: 'mobile', title: '移动电话', width: 40},
                    { field: 'salesLevel', title: '销售级别', width: 40}
                ]
            ],
            toolbar:[
//                {
//                    id: 'btnAddSalesman' + token,
//                    text: '添加',
//                    iconCls: 'icon-add'
//                },
                {
                    id: 'btnUpdateSalesman' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                }
//                {
//                    id: 'btnDeleteSalesman' + token,
//                    text: '删除',
//                    iconCls: 'icon-cut'
//                }
            ],
            onLoadSuccess: function () {
//                onClickSalesmanAdd();
//                onClickSalesmanDelete();
                onClickSalesmanUpdate();
            }
        });

    }

    /**
     * 选择销售员区域初始化
     */
    function initSelectArea() {
        if (obj == null) {
            $("#salesmanSelectionArea" + token).remove();
        } else {
            //初始化选择项目确定按钮
            $("#" + btnAdd).hide();
            $("#" + btnDelete).hide();
            $("#" + btnUpdate).hide();
            var customerId = obj.getCustomerId();
            var operatorId = loginUser.getId();
            var remark = obj.getRemark();
            var butttonId = "btnSelectSalesmanSelection" + token;
            fw.bindOnClick(butttonId, function (process) {
                fw.datagridGetSelected(tableName + token, function (selected) {
                    process.beforeClick();
                    var salesmanId = selected.id;
                    var url = WEB_ROOT + "/customer/CustomerDistribution_saveCustomerDistribution.action?customerDistribution.customerId=" + customerId +
                        "&customerDistribution.saleManId=" + salesmanId + "&customerDistribution.remark=" + remark+"&originalSalemanId="+obj.getSalemanId();
                    fw.post(url, null, function (data) {
                        obj.initTable();
                        fw.windowClose("SalesmanSelectWindow" + token);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                })

            });
            fw.bindOnClick("btnCancelCustomerDistribution" + token, function (process) {
                var url = WEB_ROOT + "/customer/CustomerDistribution_cancelDistribution.action?customerDistribution.customerId=" + customerId +
                    "&customerDistribution.remark=" + remark;
                fw.post(url, null, function (data) {
                    alert("该客户所有分配取消，请重新分配");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        }
    }

    function windowOpen(data) {
        if ($("#userWindow" + token).length == 0) {
            $("#windowsArea").append("<div id='userWindow" + token + "'></div> ");
        }
        $('#userWindow' + token).window({
            title: '人员信息信息管理',
            width: 585,
            height: 350,
            modal: true,
            cache: false,
            collapsible: false,
            minimizable: false,
            maximizable: false,
            closable: false,
            resizable: false,
            href: WEB_ROOT + "/modules/sale/SalesmanInfo_Save.jsp?token=" + token + "&idChange2=" + idChange2,    // "/modules/system/user_save.jsp?token=?idChange"
            closed: false,
            onLoad: function () {
                // 绑定表单提交事件
                fw.getComboTreeFromKV('gender' + token, 'Sex', 'V', null);
                var formId = "baseInfoEidt" + token;
                var buttonId = "salesmanSubmit" + token;
                $("salesmanPassword" + token).remove();
                $("salesmanLevel" + token).remove();
                $("#" + buttonId).bind('click', function () {
                    var formTabsId = "updateTabs" + token;
                    var formTab = $("#" + formTabsId).tabs("getSelected");
                    //取得选项卡的位置
                    var index = $("#" + formTabsId).tabs('getTabIndex', formTab);
                    if (index == 0) {
                        //提交基本信息
                        var url = WEB_ROOT + '/sale/Salesman_insert.action';
                        formSubmitDetail(buttonId, formId, "userWindow" + token, url, index);
                    } else if (index == 1) {
                        fw.alert("提示信息", "请先保存基本信息！");
                        //保存密码
                    } else if (index = 2) {
                        fw.alert("提示信息", "请先保存基本信息！");
                    }
                });
            },
            onClose: function () {

            }
        });
    }


    function formSubmitDetail(buttonId, formId, windowId, url, index) {
        $("#" + formId).form('submit', {
            url: url,
            onSubmit: function () {
                $('#' + buttonId).linkbutton({text: '正在提交...', disabled: true});

            },
            success: function (data) {
                if (windowId == "userWindow" + token) {
                    fwCloseWindow(windowId);
                    $('#' + tableName + token).datagrid('load');
                    $('#' + buttonId).linkbutton({text: '确定', disabled: false});
                } else {
                    if (index == 2) {
                        fwCloseWindow('userUpdateWindow' + token)
                        $('#' + tableName + token).datagrid('load');
                        $('#' + buttonId).linkbutton({text: '确定', disabled: false});
                    } else {
                        var formTabsId = "updateTabs" + token;
                        // addSuccess=true;
                        $("#" + formTabsId).tabs("select", "销售人员属性");
                        // returnData=data;
                        $('#' + buttonId).linkbutton({text: '确定', disabled: false});
                    }
                }

            }
        });
    }

    function fwCloseWindow(windowId) {
        var ids = fw.getObjectStartWith(windowId);
        var i = 0;
        try {
            for (i = 0; i < ids.length; i++) {
                var obj = ids[i];
                //alert("Reload " + i);
                $(obj).window("close");
                $(obj).remove();
            }
        }
        catch (e) {

        }
    }

    //-----------事件定义部分-----------//

    /**
     * 查询事件
     */
    function onClickSearch() {
        var buttonId = "btnSearchSalesman" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = tableName + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["salesManVO.userName"] = $("#search_userName" + token).val();
            params["salesManVO.staffCode"] = $("#search_salesman_staffCode" + token).val();
            params["salesManVO.mobile"] = $("#search_salesman_mobile" + token).val();
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickSearchReset() {
        var buttonId = "btnSearchResetSalesman" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_userName" + token).val("");
            $("#search_salesman_staffCode" + token).val("");
            $("#search_salesman_mobile" + token).val("");
        });
    }

    /**
     * 查询事件
     */
    function onClickSearch2() {
        var buttonId = "btnSearchSalesman_select" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "SelectsalesManTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["salemanVO.userName"] = $("#search_salesman_name" + token).val();
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickSearchReset2() {
        var buttonId = "btnSearchResetSalesman_select" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_salesman_name" + token).val("");
        });
    }

    /**
     * 数据编辑窗口提交事件
     */
    function onClickEditWindowSubmit() {

        var buttonId = "salesmanSubmit" + token;
//        alert('2');
        fw.bindOnClick(buttonId, function (process) {
//            alert('3');
            var formId = "baseInfoEidt" + token;
            // 更改url
            var url = WEB_ROOT + "/sale/Salesman_update.action";
            fw.bindOnSubmitForm(formId, url, function () {
//                alert('4');
                process.beforeClick();
            }, function () {
//                alert('success');
                process.afterClick();
                fw.datagridReload("salesManTable" + token);
                fw.windowClose('userUpdateWindow' + token);
            }, function () {
                alert('error');
                process.afterClick();
            });
        });


//        var windowId = 'userUpdateWindow' + token;
//        var formId = "baseInfoEidt" + token;
//        var buttonId = "salesmanSubmit" + token;
//        $("#" + buttonId).bind('click', function (process) {
//
//            var formTabsId = "updateTabs" + token;
////            var formTab = $("#" + formTabsId).tabs("getSelected");
////            var index = $("#" + formTabsId).tabs('getTabIndex', formTab);
//            if (index == 0) {
//                //提交基本信息
//                var url = WEB_ROOT + '/system/User_update.action';
//                formSubmitDetail(buttonId, "baseInfoEidt" + token, windowId, url, index);
//            }
////            else if (index == 1) {
//                var url = WEB_ROOT + '/system/User_insertOrUpdate.action';
//                if ($("#password" + token).val() == $("#password2" + token).val()) {
//                    formSubmitDetail(buttonId, "userUpdatePwd" + token, windowId, url, index);
//                } else {
//                    //alert("p1:"+$("#password"+token).val()+" p2:"+$("#password2"+token).val());
//                    fw.alert("密码提示", "两次输入的密码必须一致!");
//                }
//
//
//            } else if (index = 2) {
//                var url = WEB_ROOT + '/sale/Salesman_insertOrUpdate.action';
//                // $("#userId"+token).val( $("#id"+token).val());
//                formSubmitDetail(buttonId, "salesmanLevelEdit" + token, windowId + token, url, index);
//            }
//        });
    }

    //-------------数据操作事件定义------------------//
    /**
     * 添加事件
     */
//    function onClickSalesmanAdd() {
//        fw.bindOnClick(btnAdd, function (process) {
//            idChange = 'submitBaseInfo';
//            idChange2 = 'userWindow';
//            var data = [];
//            windowOpen(data);
//        });
//
//    }

    /**
     * 删除事件
     */
//    function onClickSalesmanDelete() {
//        fw.bindOnClick(btnDelete, function (process) {
//            fw.datagridGetSelected(tableName + token, function (selected) {
//                fw.confirm('删除确认', '是否确认删除数据？', function () {
//                    var url = WEB_ROOT + "/system/User_delete.action?user.sid=" + selected.sid;
//                    fw.post(url, null, function (data) {
//                        $('#' + tableName + token).datagrid('load');
//                    }, null);
//                }, null);
//            });
//        });
//    }

    /**
     * 修改事件
     */
    function onClickSalesmanUpdate() {

        idChange2 = 'userUpdateWindow';
        fw.bindOnClick(btnUpdate, function (process) {
            fw.datagridGetSelected(tableName + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/sale/Salesman_loadUser.action?salesmanPO.id=" + id;
                fw.post(url, null, function (data) {
                    data['salesmanVO.userName'] = selected.userName;
//                    alert(data['salesman.username']);
                    data['salesmanVO.gender'] = selected.gender;
                    initWindowEditWindow(data);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    function initWindowEditWindow(data) {

        data["salesman.operatorId"] = loginUser.getId();
        data["salesman.operatorName"] = loginUser.getName();

        var url = WEB_ROOT + "/modules/sale/SalesmanInfo_Save.jsp?token=" + token + "&idChange=" + idChange + "&idChange2=" + idChange2;
        var windowId = "userUpdateWindow" + token;
        fw.window(windowId, '销售人员等级', 350, 200, url, function () {
//            fw.alertReturnValue(data);
//            $('#name'+token).input({disabled:true});
//            $('#gender'+token).combotree({disabled:true});
            fw.getComboTreeFromKV('Sales_level' + token, 'sales_level', 'k', fw.getMemberValue(data, 'salesman.sales_levelId'));
            fw.getComboTreeFromKV('gender' + token, 'Sex', 'k', fw.getMemberValue(data, 'salesmanVO.gender'));
//            $('#baseInfoEidt' + token).form('load', data[0]);
//            $("#userUpdatePwd" + token).form('load', data[0]);
//            $("#salesmanLevel" + token).form('load', data[1]);
            // 初始化表单提交事件
            onClickEditWindowSubmit();
            fw.formLoad('baseInfoEidt' + token, data);
        }, null);
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
