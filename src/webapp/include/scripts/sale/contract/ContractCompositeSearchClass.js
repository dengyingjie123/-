/**
 * 描述：合同综合查询
 * Created by yux on 2016/6/28.
 */
var ContractCompositeSearchClass = function (token) {
    function initAll() {
        //初始化查询
        initContractCompositeSearch();
        //查询
        onClickContractCompositeSearch();
        //查询重置
        onClickContractCompositeSearchReset();
        //初始化表格
        initContractCompositeSearchTable();
    }


    /**
     * 初始化合同综合查询表格
     */
    function initContractCompositeSearchTable() {
        var strTableId = 'ContractCompositeSearchTable' + token;
        var url = WEB_ROOT + "/sale/contract/Contract_getContractCompositeSearchList.action";

        $('#' + strTableId).datagrid({
            title: '销售合同',
            url: url,
            usedHeight:450,
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
                    {field: 'productionName', title: '产品分期名称'},
                    {field: 'contractNo', title: '合同号'},
                    {
                        field: 'status', title: '合同状态',
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "签约";
                            } else if (value == 1) {
                                return "未签约";
                            } else if (value == 2) {
                                return "合同异常";
                            } else {
                                return "";
                            }

                        }
                    }
                ]
            ],
            columns: [
                [
                    {field: 'sid', title: 'sid', hidden: true},
                    {field: 'id', title: 'id', hidden: true},
                    {field: 'operatorId', title: 'operatorId', hidden: true},
                    {field: 'operateTime', title: 'operateTime', hidden: true},
                    {field: 'orgId', title: 'orgId', hidden: true},
                    {field: 'projectId', title: 'projectId', hidden: true},
                    {field: 'productionId', title: 'productionId', hidden: true},
                    {field: 'productionHomeId', title: 'productionHomeId', hidden: true},
                    {field: 'salemanName', title: '销售人'},
                    {field: 'sigendTime', title: '签约时间'},
                    {field: 'productionHomeName', title: '产品名称'},
                    {field: 'projectName', title: '项目名称'},
                    {field: 'customerName', title: '客户名称'},
                    {field: 'customerId', title: '客户ID', hidden: true},
                    {field: 'money', title: '订单金额',
                        formatter: function (value, row, index) {
                            return fw.formatMoney(row['money']);
                        }
                    }
                ]
            ],
            onLoadSuccess: function () {
                //查看流转状态
                onClickContractRouteList();
                //查看产品分期-合同摘要
                onClickShowContractAbstractByProduction();
                //查看客户-合同摘要
                onClickShowContractAbstractByCustomer();
                //查看产品-合同摘要
                onClickShowContractAbstractByProductionHome();
            }
        });
    }



    //查看流转状态
    function onClickContractRouteList() {
        var buttonId = "btnListContractRouteList" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('ContractCompositeSearchTable' + token, function (selecteds) {
                if (selecteds.length > 1) {
                    fw.alert("警告", "请选择一条数据");
                    return false;
                }
                process.beforeClick();
                var contractNo = selecteds[0].contractNo;
                var status = selecteds[0].status;
                var orgId = selecteds[0].orgId;
                var contractId = selecteds[0].id;

                var dataUrl =
                    "?token=" + token +
                    "&contractNo=" + contractNo +
                    "&status=" + status +
                    "&orgId=" + orgId +
                    "&contractId=" + contractId

                iniShowWindowRouteList(dataUrl);
                process.afterClick();
            });
        });
    }


    /**
     * 弹出流转
     */
    function iniShowWindowRouteList(dataUrl) {
        var url = WEB_ROOT + "/modules/sale/contract/ContractRouteListDetail.jsp" + dataUrl;
        var windowId = "ContractRouteListWindow" + token;
        fw.window(windowId, '销售合同流转详情', 800, 400, url, function () {

        }, null);
    }


    /***
     * 获取产品分期ID
     */
    function onClickShowContractAbstractByProduction(){

            var buttonId = "btnShowContractAbstractByProduction" + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelections('ContractCompositeSearchTable' + token, function (selecteds) {
                    if (selecteds.length > 1) {
                        fw.alert("警告", "请选择一条数据");
                        return false;
                    }
                    process.beforeClick();
                    //初始化产品摘要信息界面，获取选择的数据的产品ID，获取改产品的摘要信息
                    var productionId;
                    productionId = selecteds[0].productionId;
                    getContractAbstractDataByProduction(productionId);
                    process.afterClick();
                });
            });


    }

    //根据产品分期ID获取对应合同摘要信息
    function getContractAbstractDataByProduction(productionId){
        var url =  WEB_ROOT + "/sale/contract/Contract_getContractAbstractByProduction.action?productionId="+productionId;
        //通过productionId获取合同对应产品的摘要信息
        fw.post(url, null, function (data) {
            initContractAbstractByProductionWindow(data);
        },null);
    }

    /***
     * 显示产品对应的合同的摘要信息
     */
    function onClickShowContractAbstractByProductionHome(){
        var buttonId = "btnShowContractAbstractByProductionHome" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('ContractCompositeSearchTable' + token, function (selecteds) {
                if (selecteds.length > 1) {
                    fw.alert("警告", "请选择一条数据");
                    return false;
                }
                process.beforeClick();
                //初始化产品摘要信息界面，获取选择的数据的产品ID，获取改产品的摘要信息
                var productionHomeId = selecteds[0].productionHomeId;
                var url =  WEB_ROOT + "/sale/contract/Contract_getContractAbstractByProductionHome.action?productionHomeId="+productionHomeId;
                //通过productionId获取合同对应产品的摘要信息
                fw.post(url, null, function (data) {
                    initContractAbstractByProductionHomeWindow(data);
                    process.afterClick();
                },function () {
                    process.afterClick();
                });
            });
        });

    }



    /***
     * 显示客户对应的合同的摘要信息
     */
    function onClickShowContractAbstractByCustomer(){
        var buttonId = "btnShowContractAbstractByCustomer" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('ContractCompositeSearchTable' + token, function (selecteds) {
                if (selecteds.length > 1) {
                    fw.alert("警告", "请选择一条数据");
                    return false;
                }
                process.beforeClick();
                //初始化产品摘要信息界面，获取选择的数据的产品ID，获取改产品的摘要信息
                var customerId = selecteds[0].customerId;
                var url =  WEB_ROOT + "/sale/contract/Contract_getContractAbstractByCustomer.action?customerId="+customerId;
                //通过productionId获取合同对应产品的摘要信息
                fw.post(url, null, function (data) {
                    initContractAbstractByProductionHomeWindow(data);
                    process.afterClick();
                },function () {
                    process.afterClick();
                });
            });
        });

    }




    /***
     * 初始化产品分期对应合同摘要的弹出窗口
     * @param data
     */
    function initContractAbstractByProductionWindow(data) {
        var windowId = "ContractAbstractByProductionWindow" + token;
        var url = WEB_ROOT + "/modules/sale/contract/ContractCompositeSearch_ContractAbstract.jsp?token="+token;
        fw.window(windowId, "产品分期-合同摘要", 600, 450, url, function () {
            initContractAbstractByProductionTable(data);
        },null);
    }

    /***
     * 初始化产品对应合同摘要的弹出窗口
     * @param data
     */
    function initContractAbstractByProductionHomeWindow(data) {
        var windowId = "ContractAbstractByProductionWindow" + token;
        var url = WEB_ROOT + "/modules/sale/contract/ContractCompositeSearch_ContractAbstract.jsp?token="+token;
        fw.window(windowId, "产品-合同摘要", 600, 450, url, function () {
            initContractAbstractByProductionHomeTable(data);
        },null);
    }


    /***
     * 初始化产品分期对应合同摘要的弹出窗口中的表格、数据
     * @param data
     */
    function initContractAbstractByProductionTable(data) {
        var strTableId = 'ContractAbstractTable' + token;
        //设置datagrid
        var projectName;
        var productionHomeName;
        var productName;
        var size=0;
        var saleMoney=0;
        var salePercent=0;
        var totalContract=0;
        var totalSignedContract=0;
        var totalUnsignContract=0;
        var totalCanceledContract=0;
        $('#' + strTableId).datagrid({
            title: '产品分期-合同摘要列表',
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            url: null,
            fitColumns: false,//行是否适应屏幕
            singleSelect: true,//是否只能选择一行
            rownumbers: true,//是否显示行号
            pagination: false,//是否分頁
            frozenColumns: [
                [  // 固定列，没有滚动条

                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    {field: 'productName', title: '产品分期名称', hidden: true,
                        formatter: function (value, row, index) {
                            productName = row['productName'];
                            return row['productName'];
                        }},
                    {field: 'orgName', title: '财富中心'},
                    {field: 'allContract', title: '总合同数', hidden: true,
                        formatter: function (value, row, index) {
                            totalContract += row['allContract'];
                            return row['allContract'];
                        }
                    },
                    {field: 'allUnsignConract', title: '空白',
                        formatter: function (value, row, index) {
                            totalUnsignContract += row['allUnsignConract'];
                            return row['allUnsignConract'];
                        }
                    },
                    {field: 'allSignedConract', title: '已签约',
                        formatter: function (value, row, index) {
                            totalSignedContract += row['allSignedConract'];
                            return row['allSignedConract'];
                        }
                    },
                    {field: 'allCanceledConract', title: '作废',
                        formatter: function (value, row, index) {
                            totalCanceledContract += row['allCanceledConract'];
                            return row['allCanceledConract'];
                        }
                    },
                    {field: 'projectName', title: '项目名称', hidden: true,
                        formatter: function (value, row, index) {
                            projectName = row['projectName'];
                            return row['projectName'];
                        }
                    },

                    {field: 'productionHomeName', title: '产品名称', hidden: true,
                        formatter: function (value, row, index) {
                            productionHomeName = row['productionHomeName'];
                            return row['productionHomeName'];
                        }
                    },
                    {field: 'size', title: '产品额度', hidden: true,
                        formatter: function (value, row, index) {
                            size = row['size'];
                            return fw.formatMoney(row['size']);
                        }
                    },
                    {field: 'saleMoney', title: '已签约额度', hidden: true,
                        formatter: function (value, row, index) {
                            saleMoney = row['saleMoney'];
                            return fw.formatMoney(row['saleMoney']);
                        }
                    }
                ]
            ],
            //加载成后做的事
            onLoadSuccess: function () {

            }
        });

        $('#' + strTableId).datagrid('loadData',data);
        //初始化数据
        if(size!=0){
            salePercent = (saleMoney/size*100).toFixed(2);
        }
        salePercent += "%";
        size = fw.formatMoney(size);
        saleMoney = fw.formatMoney(saleMoney);
        $("#projectName" + token).text(projectName);
        $("#productName" + token).text(productName);
        $("#productionHomeName" + token).text(productionHomeName);
        $("#size" + token).text(size);
        $("#saleMoney" + token).text(saleMoney);
        $("#totalSignedContract" + token).text(totalSignedContract);
        $("#totalUnsignContract" + token).text(totalUnsignContract);
        $("#totalCanceledContract" + token).text(totalCanceledContract);
        $("#salePercent" + token).text(salePercent);
    }



    /***
     * 初始化产品对应合同摘要的弹出窗口中的表格、数据
     * @param data
     */
    function initContractAbstractByProductionHomeTable(data) {
        var strTableId = 'ContractAbstractTable' + token;
        //设置datagrid
        var projectName;
        var productionHomeName;
        var size=0;
        var saleMoney=0;
        var salePercent=0;
        var totalContract=0;
        var totalSignedContract=0;
        var totalUnsignContract=0;
        var totalCanceledContract=0;
        $('#' + strTableId).datagrid({
            title: '产品-合同摘要列表',
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            url: null,
            fitColumns: false,//行是否适应屏幕
            singleSelect: true,//是否只能选择一行
            rownumbers: true,//是否显示行号
            pagination: false,//是否分頁
            frozenColumns: [
                [  // 固定列，没有滚动条

                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    {field: 'productName', title: '产品分期名称'},
                    {field: 'orgName', title: '财富中心'},
                    {field: 'allContract', title: '总合同数', hidden: true,
                        formatter: function (value, row, index) {
                            totalContract += row['allContract'];
                            return row['allContract'];
                        }
                    },
                    {field: 'allUnsignConract', title: '空白',
                        formatter: function (value, row, index) {
                            totalUnsignContract += row['allUnsignConract'];
                            return row['allUnsignConract'];
                        }
                    },
                    {field: 'allSignedConract', title: '已签约',
                        formatter: function (value, row, index) {
                            totalSignedContract += row['allSignedConract'];
                            return row['allSignedConract'];
                        }
                    },
                    {field: 'allCanceledConract', title: '作废',
                        formatter: function (value, row, index) {
                            totalCanceledContract += row['allCanceledConract'];
                            return row['allCanceledConract'];
                        }
                    },
                    {field: 'projectName', title: '项目名称', hidden: true,
                        formatter: function (value, row, index) {
                            projectName = row['projectName'];
                            return row['projectName'];
                        }
                    },

                    {field: 'productionHomeName', title: '产品名称', hidden: true,
                        formatter: function (value, row, index) {
                            productionHomeName = row['productionHomeName'];
                            return row['productionHomeName'];
                        }
                    },
                    {field: 'size', title: '产品额度', hidden: true,
                        formatter: function (value, row, index) {
                            size += row['size'];
                            return fw.formatMoney(row['size']);
                        }
                    },
                    {field: 'saleMoney', title: '已签约额度', hidden: true,
                        formatter: function (value, row, index) {
                            saleMoney += row['saleMoney'];
                            return fw.formatMoney(row['saleMoney']);
                        }
                    }
                ]
            ],
            //加载成后做的事
            onLoadSuccess: function () {

            }
        });

        $('#' + strTableId).datagrid('loadData',data);
        //初始化数据
        if(size!=0){
            salePercent = (saleMoney/size*100).toFixed(2);
        }
        salePercent += "%";
        size = fw.formatMoney(size);
        saleMoney = fw.formatMoney(saleMoney);
        $("#production" + token).hide();
        $("#projectName" + token).text(projectName);
        $("#productionHomeName" + token).text(productionHomeName);
        $("#size" + token).text(size);
        $("#saleMoney" + token).text(saleMoney);
        $("#totalSignedContract" + token).text(totalSignedContract);
        $("#totalUnsignContract" + token).text(totalUnsignContract);
        $("#totalCanceledContract" + token).text(totalCanceledContract);
        $("#salePercent" + token).text(salePercent);
    }



    //合同状态（用于初始化查询列表）
    var ContractStatus = [{"id": "0", "text": "签约"}, {"id": "1", "text": "未签约"}, {"id": "2", "text": "合同异常 "}];


    /**
     * 初始化查询
     */
    function initContractCompositeSearch() {

        $("#search_Status" + token).combotree('loadData', ContractStatus);
        var ProjectNameTree = $('#search_Project'+token).combotree('tree');
        projectMenu(ProjectNameTree, function(){});
        var DepartmentTree = $('#search_OrgName'+token).combotree('tree');
        departmentMenu(DepartmentTree, function(){});
    }

    /***
     * 项目名称列表(用于初始化查询的下拉列表)
     * @param treeId
     * @param success
     */
    function projectMenu(treeId, success) {
        var url = WEB_ROOT+"/system/ProjectMenu_list.action";
        fw.treeLoad(treeId,url,null, success, null);
    }

    /***
     * 财富中心列表（用于初始化查询的下拉列表）
     * @param treeId
     * @param success
     */
    function departmentMenu(treeId, success) {
        var url = WEB_ROOT+"/system/Department_listDepartmentsForSearch.action";
        fw.treeLoad(treeId,url,null, success, null);
    }


    /**
     * 查询事件
     */
    function onClickContractCompositeSearch() {
        var buttonId = "btnSearchContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ContractCompositeSearchTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["contractVO.contractNo"] = $("#search_ContractNo" + token).val();
            params["contractVO.productionName"] = $("#search_ProductionName" + token).val();
            params["contractVO.customerName"] = $("#search_CustomerName" + token).val();
            params["contractVO.salemanName"] = $("#search_SalemanName" + token).val();
            params["contractVO.projectId"] = fw.getFormValue("#search_Project" + token, fw.type_form_combotree, fw.type_get_value);
            params["contractVO.orgId"] = fw.getFormValue("#search_OrgName" + token, fw.type_form_combotree, fw.type_get_value);
           /* params["contractVO.actionType"] = fw.getFormValue("search_RouteActionType" + token, fw.type_form_combotree, fw.type_get_value);*/
            params["contractVO.status"] = fw.getFormValue("search_Status" + token, fw.type_form_combotree, fw.type_get_value);

            params["contractVO_sigendTime_Start"] = fw.getFormValue('search_SigendTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["contractVO_sigendTime_End"] = fw.getFormValue('search_SigendTime_End' + token, fw.type_form_datebox, fw.type_get_value);

            $('#' + strTableId).datagrid('load');
        });

    }

    /**
     * 查询重置事件
     */
    function onClickContractCompositeSearchReset() {
        var buttonId = "btnResetContract" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_ContractNo" + token).val('');
            $("#search_ProductionName" + token).val('');
            $("#search_CustomerName" + token).val('');
            $("#search_SalemanName" + token).val('');
            fw.combotreeClear('#search_RouteActionType' + token);
            fw.combotreeClear('#search_Status' + token);
            fw.combotreeClear('#search_Project' + token);
            fw.combotreeClear('#search_OrgName' + token);
            $('#search_SigendTime_Start' + token).datebox("setValue", '');
            $('#search_SigendTime_End' + token).datebox("setValue", '');
        });
    }

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initContractAbstractModule:function () {
           return getContractAbstractDataByProduction(productionId);
        }
    }
}
