/**
 * SealUsageItem2Class.js 脚本对象\
 * 用章类型脚本对象
 * @parame btnstatus 列表按钮状态
 * @parsme token
 * @parsme applicationId 申请编号
 */
var SealUsageItem2Class = function (token, btnstatus, applicationId) {


    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickSealUsageItem2Search();
        // 初始化查询重置事件
        onClickSealUsageItem2SearchReset();

        // 初始化表格
        initSealUsageItem2Table();

        // 初始化查询表单
        initSealUsageItem2SearchForm();

    }

    /**
     * 初始化查询表单
     */
    function initSealUsageItem2SearchForm() {

    }


    // 构造初始化表格脚本
    function initSealUsageItem2Table() {
        var strTableId = 'SealUsageItem2Table' + token;

        var url = WEB_ROOT + '/oa/administration/SealUsageItem2_list.action';

        $('#' + strTableId).datagrid({
            //title: '用章类型',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
                "sealUsageItem2.applicationId": applicationId
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: false,
            pageList: [3, 6, 9],
            pageSize: 3,
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
                    {field: 'sid', title: 'sid', hidden: true},
                    {field: 'id', title: 'id', hidden: true},
                    {field: 'state', title: 'state', hidden: true},
                    {field: 'operatorId', title: 'operatorId', hidden: true},
                    {field: 'operateTime', title: 'operateTime', hidden: true},
                    {field: 'applicationId', title: '申请编号', hidden: true},
                    {field: 'sealId', title: '印章编号', hidden: true},
                    {field: 'sealName', title: '印章名称'},
                    {field: 'topies', title: '印章份数'},
                    {
                        field: 'status', title: '是否需要外带', formatter: function (value, row, index) {
                        if (value == 0) {
                            return "不需要";
                        } else if (value == 1) {
                            return "需要";
                        }
                    }
                    },
                    {field: 'sentToAddress', title: '外带地址'},
                    {field: 'receiveName', title: '接收人'},
                    {
                        field: 'receiveIsConfirm', title: '确认接收', formatter: function (value, row, index) {
                        if (value == "") {
                            return "";
                        } else if (value == 1) {
                            return "已接收";
                        } else if (value == 0) {
                            return "未接收";
                        }
                    }
                    },
                    {field: 'receiveTime', title: '接收时间'},
                    {field: 'outBackName', title: '归还接收人'},
                    {field: 'outBackId', title: '归还接收人', hidden: true},
                    {
                        field: 'outBackIsConfirm', title: '确认归还',
                        formatter: function (value, row, index) {

                            if (value == "") {
                                return "";
                            } else if (value == 1) {
                                return "已归还";
                            } else if (value == 0) {
                                return "未归还";
                            }
                        }
                    },
                    {field: 'outBackTime', title: '归还时间'}
                ]
            ],
            toolbar: [
                {
                    id: 'btnItemAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSealUsageItem2Edits' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnSealUsageItem2Delete' + token,
                    text: '删除',
                    iconCls: 'icon-clear'
                },
                {
                    id: 'btnSealUsageItem2ReceiveIsConfirm' + token,
                    text: '确认接收印章',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnSealUsageItem2OutBackIsConfirm' + token,
                    text: '确认印章归还',
                    iconCls: 'icon-edit'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickSealUsageItem2Add();
                onClickSealUsageItem2Edit();
                onClickSealUsageItem2Delete();
                onClickSealUsageItem2ReceiveIsConfirm();
                onClickSealUsageItem2OutBackIsConfirm();
            }

        });
        if (btnstatus == "updateStatus") {
            $("#btnItemAdd" + token).remove();
            $("#btnSealUsageItem2Edits" + token).remove();
            $("#btnSealUsageItem2Delete" + token).remove();
        } else if (btnstatus == "edit") {
            //移除接收与归还按钮
            $("#btnSealUsageItem2ReceiveIsConfirm" + token).remove();
            $("#btnSealUsageItem2OutBackIsConfirm" + token).remove();
        }
        if (btnstatus == "check" || btnstatus == "upload" || btnstatus == "applay" || btnstatus == "look") {
            $("#btnItemAdd" + token).remove();
            $("#btnSealUsageItem2Edits" + token).remove();
            $("#btnSealUsageItem2Delete" + token).remove();
            //移除接收与归还按钮
            $("#btnSealUsageItem2ReceiveIsConfirm" + token).remove();
            $("#btnSealUsageItem2OutBackIsConfirm" + token).remove();
        }
    }


    /**
     * 批量确定接收印章
     */
    function onClickSealUsageItem2ReceiveIsConfirm() {
        var buttonId = 'btnSealUsageItem2ReceiveIsConfirm' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SealUsageItem2Table' + token, function (selecteds) {
                process.beforeClick();
                //批量选中的编号
                var itemid = "";
                for (var i = 0; i < selecteds.length; i++) {
                    //不需要外带
                    if (selecteds[i].status == 0) {
                        process.afterClick();
                        fw.alert("警告", selecteds[i].sealName + ",不需要外带；请勿选择");
                        return false;
                    }
                    //已接收
                    if (selecteds[i].receiveIsConfirm == 1) {
                        process.afterClick();
                        fw.alert("警告", selecteds[i].sealName + ",已经接收，请勿选择");
                        return false;
                    }
                    //已归还
                    if (selecteds[i].outBackIsConfirm == 1) {
                        process.afterClick();
                        fw.alert("警告", selecteds[i].sealName + ",已经归还，请勿选择");
                        return false;
                    }

                    itemid = itemid + selecteds[i].id + ',';
                }

                if (itemid == "") {
                    process.afterClick();
                    fw.alert("警告", "请选择数据");
                    return false;
                }
                //去除最后一个，号；
                itemid = itemid.substring(0, itemid.length - 1);

                fw.confirm("通知", "是否确认接收所选中的印章", function () {
                    var url = WEB_ROOT + '/oa/administration/SealUsageItem2_updateReceive.action?sealUsageItem2.applicationId=' + selecteds[0].applicationId + '&sealUsageItem2.id=' + itemid
                    fw.post(url, null, function (data) {
                        var message = "";
                        if (data == 1) {
                            message = "接收成功"
                        } else {
                            message = "接收失败"
                        }
                        fw.alert("通知", message);
                        fw.datagridReload('SealUsageItem2Table' + token);
                        fw.datagridReload("SealUsageWFA2Table" + token);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                }, function () {
                    process.afterClick();
                })

            });
        });
    }

    /**
     * 批量确认印章归还
     */
    function onClickSealUsageItem2OutBackIsConfirm() {
        var buttonId = 'btnSealUsageItem2OutBackIsConfirm' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SealUsageItem2Table' + token, function (selecteds) {

                process.beforeClick();
                //批量选中的id编号
                var itemid = "";
                for (var i = 0; i < selecteds.length; i++) {
                    //判断当前登录员是否归还接收人
                    if (selecteds[i].outBackId != loginUser.getId()) {
                        process.afterClick();
                        fw.alert("警告", "您不是印章归还接收人，请勿操作");
                        return false;
                    }
                    //不需要外带
                    if (selecteds[i].status == 0) {
                        process.afterClick();
                        fw.alert("警告", selecteds[i].sealName + ",不需要外带，请勿选择");
                        return false;
                    }
                    //以接收
                    if (selecteds[i].receiveIsConfirm == 0) {
                        process.afterClick();
                        fw.alert("警告", selecteds[i].sealName + ",印章未接收，请勿选择");
                        return false;
                    }
                    //已归还
                    if (selecteds[i].outBackIsConfirm == 1) {
                        process.afterClick();
                        fw.alert("警告", selecteds[i].sealName + ",已经归还，请勿选择");
                        return false;
                    }

                    itemid = itemid + selecteds[i].id + ',';
                }

                if (itemid == "") {
                    process.afterClick();
                    fw.alert("警告", "请选择数据");
                    return false;
                }
                //去除最后一个，号；
                itemid = itemid.substring(0, itemid.length - 1);

                fw.confirm("通知", "是否确认归还所选中的印章", function () {
                    var url = WEB_ROOT + '/oa/administration/SealUsageItem2_updateOutBack.action?sealUsageItem2.applicationId=' + selecteds[0].applicationId + '&sealUsageItem2.id=' + itemid
                    fw.post(url, null, function (data) {
                        var message = "";
                        if (data == 1) {
                            message = "归还成功"
                        } else {
                            message = "归还失败"
                        }
                        fw.alert("通知", message);
                        fw.datagridReload('SealUsageItem2Table' + token);
                        fw.datagridReload("SealUsageWFA2Table" + token);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                }, function () {
                    process.afterClick();
                })


            });
        });
    }


    /**
     * 添加事件
     */
    function onClickSealUsageItem2Add() {
        var buttonId = 'btnItemAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            var add = "add";
            initSealUsageItem2Window({}, add);
        });
    }

    /**
     * 修改事件
     */
    function onClickSealUsageItem2Edit() {
        var buttonId = 'btnSealUsageItem2Edits' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('SealUsageItem2Table' + token, function (selected) {
                process.beforeClick();
                var id = selected[0].id;
                var url = WEB_ROOT + '/oa/administration/SealUsageItem2_load.action?sealUsageItem2.id=' + id;
                fw.post(url, null, function (data) {
                    var edit = 'edit'
                    initSealUsageItem2Window(data, edit);
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
    function onClickSealUsageItem2Delete() {
        var buttonId = 'btnSealUsageItem2Delete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SealUsageItem2Table' + token, function (selected) {
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function () {

                    var url = WEB_ROOT + '/oa/administration/SealUsageItem2_delete.action?sealUsageItem2.sid=' + selected.sid;
                    fw.post(url, null, function () {
                        process.afterClick();
                        fw.datagridReload('SealUsageItem2Table' + token);
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
     */
    function initSealUsageItem2Window(data, windowStatus) {

        // fw.alertReturnValue(data);

        data['sealUsageItem2.operatorId'] = loginUser.getId();
        data['sealUsageItem2.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/oa/administration/SealUsageItem2_Save.jsp?token=' + token;
        var windowId = 'SealUsageItem2Window' + token;
        fw.window(windowId, '印章管理', 700, 240, url, function () {

            // 加载数据
            fw.formLoad('formSealUsageItem2' + token, data);
            onChangeStatus();
            //窗口状态为添加时
            if (windowStatus == 'add') {
                //加载用章选择项
                fw.getComboTreeFromKV("sealId" + token, 'oa_SaleUsageWFA', 'k', "-2");

                $('#receiveName' + token).attr('readonly', "true");
                $('#outBackName' + token).attr('readonly', "true");
                $("#status" + token).find("option[value=0]").attr("selected", true);
            }
            //其他情况
            else {
                $("#topies" + token).val(data["sealUsageItem2.topies"]);
                $("#sentToAddress" + token).val(data["sealUsageItem2.sentToAddress"]);
                $("#receiveName" + token).val(data["sealUsageItem2.receiveName"]);

                $("#status" + token).find("option[value=" + data["sealUsageItem2.status"] + "]").attr("selected", true);
                //加载用章选择项
                fw.getComboTreeFromKV("sealId" + token, 'oa_SaleUsageWFA', 'k', data["sealUsageItem2.sealId"]);

                if (data["sealUsageItem2.status"] == 1) {
                    $('#sentToAddress' + token).removeAttr('readonly');
                }
            }

            //提交事件
            onClickSealUsageItem2Submit();


        });
    }

    /**
     * 下拉选中事件
     * @param obj
     */
    function onChangeStatus() {
        $("#status" + token).bind("change", function () {
            var value = $(this).val();
            if (value == 0) {
                $("#sentToAddress" + token).val("");
                $("#receiveName" + token).val("");
                $("#receiveId" + token).val("");
                $('#sentToAddress' + token).attr('readonly', "true");
                $('#sentToAddress' + token).removeAttr('class');
            } else if (value == 1) {
                $('#sentToAddress' + token).removeAttr('readonly');
                $('#receiveName' + token).val(loginUser.getName());
                $('#receiveId' + token).val(loginUser.getId());
            }
        });

    }


    /**
     * 数据提交事件
     */
    function onClickSealUsageItem2Submit() {
        var buttonId = 'btnSealUsageItem2Submit' + token;
        fw.bindOnClick(buttonId, function (process) {
            if ($('#sentToAddress' + token).val() == "" && $("#status" + token).val() == 1) {
                fw.alert("警告", "请填写外带地址");
                process.afterClick();
                return false;
            }
            //将用章名字加入参数
            var sealText = fw.getFormValue("sealId" + token, fw.type_form_combotree, fw.type_get_text);
            $("#sealName" + token).val(sealText);
            $("#applicationId" + token).val(applicationId);
            var formId = 'formSealUsageItem2' + token;
            var url = WEB_ROOT + '/oa/administration/SealUsageItem2_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('SealUsageItem2Table' + token);
                fw.windowClose('SealUsageItem2Window' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickSealUsageItem2Search() {
        var buttonId = 'btnSealUsageItem2SearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'SealUsageItem2Table' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;


            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickSealUsageItem2SearchReset() {
        var buttonId = 'btnSealUsageItem2SearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
        });
    }

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            //判断用章表数据是否存在
            if (applicationId == "" || applicationId == undefined || applicationId == null) {
                fw.alert("警告！", "数据错误 请于管理员联系");
                return;
            }
            return initAll();
        }
    };
}
