/**
 * 描述：销售合同申请
 * 说明:
 * Created by zhouhaihong on 2015/12/24.
 */
var ApplicationContractClass = function (token) {
    function initAll() {
        //初始化查询
        initContactapplicationSerrch();
        //查询
        onClickContractApplicationSearch();
        //重置
        onClickContractApplicationSearchReset();
        // 初始化表格
        initContractApplicationTable();


        /**
         * 生成合同号
         */
        onClickCreateContractNum();
    }

    //审核状态
    var ContractStatus = [{"id": "0", "text": "未审批"}, {"id": "1", "text": "审批通过"}, {"id": "2", "text": "审批失败 "}];


    /**
     * 初始化查询
     */
    function initContactapplicationSerrch() {

        $("#search_CheckState" + token).combotree('loadData', ContractStatus);
    }

    /**
     * 初始审申请表格
     */
    function initContractApplicationTable() {
        var strTableId = 'ContractApplicationTable' + token;
        var url = WEB_ROOT + "/sale/contract/ContractApplication_listApplications.action";

        $('#' + strTableId).datagrid({
            title: '申请销售合同',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true, //单选
            pageList: [15, 30, 60],
            pageSize: 15,
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
                    {field: 'ck', checkbox: true},
                    {field: 'departmentName', title: '财富中心'},
                    {field: 'productionName', title: '产品名称'},
                    {field: 'counts', title: '申请套数'},
                    {field: 'applicationUserName', title: '申请人'},
                    {field: 'applicationTime', title: '申请时间'},

                ]
            ],
            columns: [
                [
                    {field: 'sid', title: 'sid', hidden: true},
                    {field: 'id', title: 'id', hidden: true},
                    {field: 'operatorId', title: 'operatorId', hidden: true},
                    {field: 'operateTime', title: 'operateTime', hidden: true},
                    {field: 'checkName', title: '审核人'},
                    {field: 'checkTime', title: '审核时间'},
                    {
                        field: 'checkState', title: '审核状态',
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "未审核";
                            } else if (value == 1) {
                                return "审核通过";
                            } else if (value == 2) {
                                return "审核失败";
                            } else {
                                return "";
                            }

                        }
                    },
                    {field: 'contracts', title: '合同范围'}
                ]
            ],
            onLoadSuccess: function () {
                // 初始化事件
                //添加
                onClickContractApplicationAdd();
                onClickContractApplicationUpdate();
                onClickContractApplicationDelete();
                //审批
                onClickContractApplicationCheck();

            }
        });
    }


    /**
     * 删除销售合同申请
     */
    function onClickContractApplicationDelete() {
        var buttonId = "btnDeleteApplication" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ContractApplicationTable' + token, function (selected) {
                //不等于未审批
                if (selected.checkState != 0) {
                    fw.alert("警告", "该申请已经审批了,无法修改");
                    return false;
                }
                fw.confirm("警告", "是否确认删除", function () {
                    //获取销售合同申请的编号
                    var id = selected.id;
                    //获取销售合同数据
                    var url = WEB_ROOT + "/sale/contract/ContractApplication_DeleteApplicationContract.action?contractApplicationPO.id=" + id;
                    fw.post(url, null, function (data) {
                        if (data == true) {
                            fw.alert("通知", "删除成功")
                        } else {

                            fw.alert("通知", "删除失败");
                        }
                        fw.datagridReload("ContractApplicationTable" + token);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                }, function () {
                    process.afterClick();
                });
            });
        });
    }


    /**
     * 添加销售合同申请
     */
    function onClickContractApplicationUpdate() {
        var buttonId = "btnUpdateApplication" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ContractApplicationTable' + token, function (selected) {
                //不等于未审批
                if (selected.checkState != 0) {
                    fw.alert("警告", "该申请已经审批了,无法修改");
                    return false;
                }
                //获取销售合同申请的编号
                var id = selected.id;
                //获取销售合同数据
                var url = WEB_ROOT + "/sale/contract/ContractApplication_loadApplicationContact.action?contractApplicationPO.id=" + id;
                fw.post(url, null, function (data) {
                    data['applicationUserName'] = selected.applicationUserName;
                    data['productionName'] = selected.productionName;

                    iniShowWindow(data, "update");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }


    /**
     * 添加销售合同申请
     */
    function onClickContractApplicationAdd() {
        var buttonId = "btnNewApplication" + token;
        fw.bindOnClick(buttonId, function (process) {
            iniShowWindow({}, "add");
        });
    }

    /**
     * 审批销售合同
     */
    function onClickContractApplicationCheck() {
        var buttonId = "btnCheckApplication" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ContractApplicationTable' + token, function (selected) {
                //不等于未审批
                if (selected.checkState != 0) {
                    fw.alert("警告", "该申请已经审批了,无须审批");
                    return false;
                }
                process.beforeClick();
                //获取销售合同申请的编号
                var id = selected.id;
                //获取销售合同数据
                var url = WEB_ROOT + "/sale/contract/ContractApplication_loadApplicationContact.action?contractApplicationPO.id=" + id;
                fw.post(url, null, function (data) {
                    data['applicationUserName'] = selected.applicationUserName;
                    data['productionName'] = selected.productionName;

                    // fw.debug(data);
                    iniShowWindow(data, "check");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 弹出详情
     */
    function iniShowWindow(data, WindowStatus) {

        data["contractApplicationPO.operatorId"] = loginUser.getId();
        data["contractApplicationPO.operatorName"] = loginUser.getName();

        var url = WEB_ROOT + "/modules/sale/contract/ApplicationContract_Save.jsp?token=" + token;

        var windowId = "ContractApplicationWindow" + token;
        var height = 300;
        if (WindowStatus == "check") {
            height = 400;
        }
        fw.window(windowId, '销售合同申请', 550, height, url, function () {


            fw.formLoad('formContractApplicationPO' + token, data);
            var url = WEB_ROOT + "/system/Department_list.action";

            if (WindowStatus == "add") {
                $("#applicationUserName" + token).val(loginUser.getName());
                $("#applicationUserId" + token).val(loginUser.getId());
                $("#operateTime" + token).val(fw.getDateTime());
                $('#applicationTime' + token).datebox("setValue", fw.getDateTime());

                $("#checkNameTR" + token).remove();
                $("#checkStateTR" + token).remove();
                $("#checkCommentTR" + token).remove();

                //添加事件
                onClickContractApplicationAddSubmit();

                // 绑定产品查询事件
                productionMenu();
                //部门
                fw.combotreeLoad("#departmentId" + token, url, loginUser.getDepartmentId());

            } else if (WindowStatus == "update") {
                $("#applicationUserName" + token).val(loginUser.getName());
                $("#applicationUserId" + token).val(loginUser.getId());
                $("#operateTime" + token).val(fw.getDateTime());
                $('#applicationTime' + token).datebox("setValue", fw.getDateTime());

                $("#checkNameTR" + token).remove();
                $("#checkStateTR" + token).remove();
                $("#checkCommentTR" + token).remove();

                //添加事件
                onClickContractApplicationUpdateSubmit();

                // 绑定产品查询事件
                productionMenu();
                //部门
                fw.combotreeLoad("#departmentId" + token, url, data["contractApplicationPO.departmentId"]);
            } else if (WindowStatus == "check") {
                $("#productionName" + token).attr("readonly", "readonly");
                $("#counts" + token).attr("readonly", "readonly");

                $("#checkName" + token).val(loginUser.getName());
                $("#checkId" + token).val(loginUser.getId());
                $('#checkTime' + token).datebox("setValue", fw.getDateTime());
                //检查事件
                onClickContractApplicationCheckSubmit(data["contractApplicationPO.id"]);

                fw.combotreeLoad("#departmentId" + token, url, data["contractApplicationPO.departmentId"]);

                // alert($('#departmentId' + token).length);
                //fw.debug(data,2131);
                //fw.debug(data["contractApplicationPO.departmentId"],34342);
                //审核状态
                $("#checkState" + token).combotree('loadData', ContractStatus);
            }
            //$("#departmentId" + token).combotree({
            //    onSelect: function (node) {
            //        $("#departmentId" + token).combotree('disable');
            //    }
            //});
        }, null);
    }


    //产品选择
    function productionMenu() {

        $('#productionName' + token).bind('click', function () {
            var outerToken = token + "outer";
            var url = WEB_ROOT + "/modules/production/Production_Main.jsp?token=" + outerToken;
            var swindowId = "ProductionWindow" + outerToken;
            fw.window(swindowId, '选择产品', 930, 550, url, function () {
                //加载js
                using(SCRIPTS_ROOT + '/production/ProductionClass.js', function () {
                    //alert("loaded...");
                    var production = new ProductionClass(outerToken, 123, "order");
                    production.initModuleWithSelect(function (data) {
                        $("#productionName" + token).val(data["name"]);
                        $("#productionId" + token).val(data["id"]);
                    });

                });
            }, null);
        })
    }


    /**
     * @description 绑定生成合同号事件
     * 
     * @author 苟熙霖 
     * 
     * @date 2018/12/12 16:51
     * @param null
     * @return 
     * @throws Exception
     */
    function onClickCreateContractNum(){
        var buttonId = "btnCreateContractNum" + token;
        fw.bindOnClick(buttonId,function () {




            fw.datagridGetSelected('ContractApplicationTable'+token, function(selected){
                var contracts = selected.contracts;
                var productionId = selected.productionId;
                var counts = selected.counts;
                var url =  WEB_ROOT +"/modules/sale/contract/ApplicationContract_ContractNum.jsp?token="+token;



                /*
                * 合同申请未经过审批时，没有合同范围，无法生成合同
                * */
                if(contracts == 0){
                    fw.alert("提示", "无法获取合同范围，生成合同号失败，请审批该申请生成合同范围");
                    return false;
                }

                


                fw.window('ContractNumWindow'+token,'合同号列表',600,450,url,function () {
                    initContractNumWindow(contracts , productionId , counts);
                });
            });
        });
    }


    /**
     * @description 初始化合同号
     * 
     * @author 苟熙霖 
     * 
     * @date 2018/12/12 17:15
     * @param null
     * @return 
     * @throws Exception
     */
    function initContractNumWindow (contracts , productionId , counts) {

        var url = WEB_ROOT + "/sale/contract/ContractApplication_createContractNum.action?contracts=" + contracts + "&productionId=" + productionId + "&counts=" + counts;





        /*
        * 请求并返回数据，渲染到testArea
        * */
        fw.post(url,null,function (data) {
            $("#contractNum"+token).val(JSON.parse(data).contractNum.split(' ').join('\r\n'));
        });
    }


    /**
     * 修改提交事件
     */
    function onClickContractApplicationUpdateSubmit() {
        var buttonId = "btnContractApplicationSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var departmentName = fw.getFormValue("departmentId" + token, fw.type_form_combotree, fw.type_get_text);
            $("#departmentName" + token).val(departmentName);

            fw.confirm("通知", "是否确认修改合同申请数据", function () {
                var formId = "formContractApplicationPO" + token;
                var url = WEB_ROOT + "/sale/contract/ContractApplication_UpdateApplicationContract.action";
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("ContractApplicationTable" + token);
                    fw.windowClose('ContractApplicationWindow' + token);
                }, function () {
                    process.afterClick();
                });
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 添加提交事件
     */
    function onClickContractApplicationAddSubmit() {
        var buttonId = "btnContractApplicationSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {

            var departmentName = fw.getFormValue("departmentId" + token, fw.type_form_combotree, fw.type_get_text);
            $("#departmentName" + token).val(departmentName);


            $('#applicationContractType'+token).val(0);
            if (fw.checkboxGetChecked('cbContractNos'+token)) {
                $('#applicationContractType'+token).val(1);
            }

            fw.confirm("通知", "是否确认添加合同申请数据", function () {
                var formId = "formContractApplicationPO" + token;
                var url = WEB_ROOT + "/sale/contract/ContractApplication_newApplicationContract.action";
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("ContractApplicationTable" + token);
                    fw.windowClose('ContractApplicationWindow' + token);
                }, function () {
                    process.afterClick();
                });
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 检查提交事件
     */
    function onClickContractApplicationCheckSubmit(id) {
        var buttonId = "btnContractApplicationSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var checkState = fw.getFormValue("checkState" + token, fw.type_form_combotree, fw.type_get_value);
            if (checkState == 0) {
                fw.alert("警告", "选择审批状态:通过或者失败");
                return false;
            }
            fw.confirm("通知", "是否确认审批该申请", function () {
                var departmentName = fw.getFormValue("departmentId" + token, fw.type_form_combotree, fw.type_get_text);
                $("#departmentName" + token).val(departmentName);
                process.afterClick();
                var url = WEB_ROOT + "/sale/contract/ContractApplication_checkApplication.action;";
                var formId = "formContractApplicationPO" + token;
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("ContractApplicationTable" + token);
                    fw.windowClose('ContractApplicationWindow' + token);
                }, function () {
                    process.afterClick();
                });
            }, function () {
                process.afterClick();
            })

        });
    }

    /**
     * 查询事件
     */
    function onClickContractApplicationSearch() {
        var buttonId = "btnSearchContractApplication" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ContractApplicationTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["contractApplicationVO.productionName"] = $("#search_ProductionName" + token).val();
            params["contractApplicationVO.applicationUserName"] = $("#search_ApplicationUserName" + token).val();
            params["contractApplicationVO.checkState"] = fw.getFormValue("search_CheckState" + token, fw.type_form_combotree, fw.type_get_value);

            params["contractApplicationVO_applicationTime_Start"] = fw.getFormValue('search_ApplicationTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["contractApplicationVO_applicationTime_End"] = fw.getFormValue('search_ApplicationTime_End' + token, fw.type_form_datebox, fw.type_get_value);

            $('#' + strTableId).datagrid('load');
            fw.treeClear()
        });

    }

    /**
     * 查询重置事件
     */
    function onClickContractApplicationSearchReset() {
        var buttonId = "btnResetContractApplication" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_ProductionName" + token).val('');
            $("#search_ApplicationUserName" + token).val('');
            fw.combotreeClear('search_CheckState' + token);
            $('#search_ApplicationTime_Start' + token).datebox("setValue", '');
            $('#search_ApplicationTime_End' + token).datebox("setValue", '');
        });
    }

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    }
}