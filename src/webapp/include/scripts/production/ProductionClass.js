/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/15/14
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 * SaleTask4GroupClass:销售小组管理 获取
 * User: 姚章鹏
 * Date: 9/6/12
 * Time: 11:13 AM
 * 添加了  { field: 'contractCopies', title: '合同一式份'},
 * 修改弹出窗口宽高
 */
var ProductionClass = function (token, my, obj) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

        //初始化查询区域
        initFormProductTable();

        // 初始化查询事件
        onClickProductionSearch();
        // 初始化查询重置事件
        onClickProductionSearchReset();
        // 初始化表格
        initTableProductionTable();
        //初始化下拉列表
        initStatusTree(null, "-2");
        fw.combotreeClear('#search_Status' + token);
        //初始化选择区域
        initSelectArea();
    }

    /**
     * 初始化下拉列表项
     */
    function initStatusTree(combotreeId, selectIndexId) {
        if (combotreeId == null) {
            combotreeId = "search_status" + token;
        }
        var URL = WEB_ROOT + "/oa/task/Task_StatusTree.action";
        fw.combotreeLoad(combotreeId, URL, selectIndexId);
    }

    function interestTypeTree(selectIndexId) {
        var URL = WEB_ROOT + "/production/Production_interestTypeTree.action";
        //付息类型
        fw.combotreeLoad("interestType" + token, URL, selectIndexId);
    }

    function interestUnitTree(selectIndexId) {
        var URL = WEB_ROOT + "/production/Production_interestUnitTree.action";
        //付息单位
        fw.combotreeLoad("interestUnit" + token, URL, selectIndexId);
    }

    function initFormProductTable() {
        fw.getComboTreeFromKV('search_ProductionName' + token, null, 'k', '-2');

        var tree = $('#search_ProductionName' + token).combotree('tree');
        productionHome(tree, function () {});
    }

    /**
     * 初始化表格
     */
    function initTableProductionTable() {
        fw.combotreeClear('#search_Status' + token);
        var strTableId = 'ProductionTable' + token;
        var pageSize = 10;
        var pageList = [10, 20, 30]
        var url = WEB_ROOT + "/production/Production_list.action";
        if (obj != null) {
            url = WEB_ROOT + "/production/Production_list.action?productionStatus='2'";
            pageSize = 10;
            pageList = [10]
        }
        //alert(pageSize);
        $('#' + strTableId).datagrid({
            title: '产品分期信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: pageList,
            pageSize: pageSize,
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
                    { field: 'statusName', title: '状态' },
                    { field: 'name', title: '分期名称'}
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: '序号', hidden: true},
                    { field: 'id', title: '编号', hidden: true},
                    { field: 'projectId', title: '项目编号', hidden: true},
                    { field: 'valueDate', title: '起息日'},
                    { field: 'saleMoney', title: '销售金额',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(row['saleMoney'])
                        }
                    },
                    { field: 'size', title: '产品总额',
                        formatter: function(value,row,index) {
                            return fw.formatMoney(row['size'])
                        }
                    },
                    { field: 'percent', title: '募集比例',
                        formatter: function(value,row,index) {
                            return fw.numberFloatFormat(row['saleMoney']/row['size'] * 100, 2) + '%' ;
                        }
                    },
                    { field: 'productName', title: '所属产品' },
                    { field: 'websiteDisplayName', title: '网站显示名称' },
                    { field: 'investTermView', title: '网站显示投资期限' },
                    { field: 'operateTime', title: '操作时间',hidden:true},
                    { field: 'operatorName', title: '操作人',hidden:true},
                    { field: 'displayType', title: '展示类型'},
                    { field: 'totalCost', title: '总成本', formatter: function (value, row, index) {
                        return  value == "" ? "" : value + "%";
                        }
                    },
                    { field: 'discountRate', title: '折标费率'},
                    { field: 'orders', title: '排序'},
                    { field: 'productHomeId', title: '主项目编号'}

                ]
            ],
            onLoadSuccess: function () {
                onClickProductionAdd();
                onClickProductionDelete();
                onClickProductionEdit();
                onClickProductionView();
                //审批事件
                onClickProductionCheck();
                onClickProductionCustomerList();
            }
        });

    }

    function initWindowProductionCustomerWindow(id) {
        //alert(id);
        var url = WEB_ROOT + "/modules/production/ProductionCustomer.jsp?token=" + token;
        var windowId = "ProductionCustomerWindow" + token;
        fw.window(windowId, '客户信息', 950, 500, url, function () {


            /**
             * 加载统计数据
             *
             * Date: 2016-05-19 21:20:04
             * Author: leevits
             */
            var productionStatisticsURL = WEB_ROOT + "/production/ProductionSaleStatistics_getProductionSaleStatisticsByProductionId?productionId=" + id;

            fw.post(productionStatisticsURL, null, function (data) {
                // fw.debug(data);

                var totalSaleMoney = data['totalSaleMoney'];
                var totalTransferMoney = data['totalTransferMoney'];
                var totalPaybackMoney = data['totalPaybackMoney'];
                var totalRemainMoney = data['totalRemainMoney'];

                // var totalSaleMoney = totalRemainMoney + totalPaybackMoney + totalTransferMoney;

                $('#totalSaleMoney' + token).text(fw.formatMoney(totalSaleMoney));
                $('#totalTransferMoney' + token).text(fw.formatMoney(totalTransferMoney));
                $('#totalPaybackMoney' + token).text(fw.formatMoney(totalPaybackMoney));
                $('#totalRemainMoney' + token).text(fw.formatMoney(totalRemainMoney));

            }, null);


            /**
             * 加载明细表单
             *
             * Date: 2016-05-19 21:19:52
             * Author: leevits
             */
            var strTableId = "ProductionCustomer" + token;
            var url = WEB_ROOT + "/production/Production_listCustomer.action?productId=" + id;
            $("#" + strTableId).datagrid({
                title: '客户信息',
                url: url,
                queryParams: {},
                loadMsg: '正在加载，请稍后...',
                fitColumns: true,
                singleSelect: true,
                rownumbers: true,
                loadFilter: function (data) {
                    try {
                        data = fw.dealReturnObject(data);
                        return data;
                    }
                    catch (e) {
                    }
                },
                toolbar:[
                    //{
                    //    id:'btnShowActiveOrders'+token,
                    //    iconCls: 'icon-edit',
                    //    text:'显示有效订单'
                    //}
                ],
                pagination: true,
                frozenColumns: [
                    [  // 固定列，没有滚动条
                        {field: 'ck', checkbox: true}
                    ]
                ],
                columns: [
                    [
                        { field: 'customerName', title: '客户姓名'},
                        { field: 'mobile', title: '手机号'},
                        { field: 'money', title: '金额',
                            formatter: function(value,row,index) {
                                return fw.formatMoney(row['money'])
                            }
                        },
                        { field: 'orderNum', title: '合同号' },
                        { field: 'payTime', title: '打款时间' },
                        { field: 'paybackTime', title: '兑付时间' },
                        { field: 'orderStatusName', title: '订单状态' }
                    ]
                ],
                onLoadSuccess: function () {
                    // onClickShowAcitveOrders();
                }
            });
        });
    }

    function onClickShowAcitveOrders(productId) {
        var buttonId = "btnShowActiveOrders" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ProductionCustomer" + token;
            var url = WEB_ROOT + "/production/Production_listCustomer.action?productId=" + productId;

            fw.post(url,null, function(data){
                $("#" + strTableId).datagrid('load',data);
            },null);

        });
    }

    function onClickProductionDescription(productionId, buttonId, index) {

        fw.bindOnClick(buttonId, function (process) {

            var url = WEB_ROOT + "/production/ProductionInfo_load.action?productionInfo.productionId=" + productionId;
            fw.post(url, null, function (productionInofData) {
                // fw.alertReturnValue(productionInofData);

                var title = productionInofData["productionInfo.title" + index];
                var content = productionInofData["productionInfo.content" + index];
                var isDisplay = productionInofData["productionInfo.isDisplay" + index];
                initWindowProductionDesWindow(productionInofData["productionInfo.id"], productionId, title, content, isDisplay, index);

                process.afterClick();
            }, function () {
                process.afterClick();
            });
            // 打开窗口，初始化表单数据为空

        });

    }

    /**
     * 清空起息日
     */
    function onClickRemoveValueDate() {
        fw.bindOnClick4Any('btnRemoveValueDate'+token, function(){
            fw.databoxClear('valueDate'+token)
        });
    }


    /**
     * 修改:姚章鹏
     * 内容：修改时的datagrid没有了
     * 时间:2015年6月18日09:05:44
     *
     * 初始化弹出窗口
     * 姚章鹏
     * @param  windowType 判断是添加还窗口是弹出
     * 内容:判断添加和编辑弹出的窗口宽高
     *
     * 修改：leevits
     * 时间：2015年6月24日 15:09:34
     * 内容：将金额输入框初始化代码封装到fw里
     *
     *
     * * 实现资金输入框
     * 姚章鹏
     * date 6/17/15
     * 内容:判断添加和编辑弹出的窗口宽高
     * 修改一次性本息兑付时默认值初始化为1，按月本息兑付为可编辑，添加资金格式为货币
     * 产品信息，付息周期的框长度缩为2个字符即可，后面的"周期"追加
     * 添加初始化文本编辑框
     */
    var WindowType_Add = 1;
    var WindowType_Edit = 2;
    var WindowType_Check = 3; // 审核状态

    function initWindowProductionWindow(data, obj, windowType) {
        var height;
        if (windowType == WindowType_Add) {
            height = 370;
        }
        if (windowType == WindowType_Edit || windowType == WindowType_Check) {
            height = 640;
        }


        data["production.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/production/Production_Save.jsp?token=" + token;
        var windowId = "ProductionWindow" + token;

        fw.window(windowId, '产品分期信息', 1150, height, url, function () {
            fw.textFormatCurrency('size' + token);
            if (windowType == WindowType_Add) {
                data["production.incomeType"]=0;
                //隐藏产品信息
                $("#productionTable" + token).remove();

                $("#scheduleId" + token).hide();
                $("#appointmentId" + token).hide();
            }

            if (windowType == WindowType_Edit || windowType == WindowType_Add) {
                fw.combotreeSetReadOnly('status'+token);
            }
            //产品介绍
            //onClickProductionDes1(data);
            //onClickProductionDes2(data);
            //onClickProductionDes3(data);
            //onClickProductionDes4(data);
            //onClickProductionDes5(data);

            var vc = $("#interestCycle").val();
            var vt = $("#interestTimes").val();
            $('#interestType' + token).combotree({
                onSelect: function (node) {
                    if (node.id == "一次性本息兑付") {
                        // $("#interestCycle" + token).val('1');
                        $("#interestCycle" + token).validatebox('validate');
                        $("#interestTimes" + token).val('1');
                        $("#interestTimes" + token).validatebox('validate');
                        // $('#interestCycle' + token).attr('readonly', true);
                        $('#interestTimes' + token).attr('readonly', true);
                    }

                    if (node.id == "按月本息兑付") {
                        $('#interestCycle' + token).attr('readonly', false);
                        $('#interestTimes' + token).attr('readonly', false);
                    }
                }
            });


            // 初始化表单提交事件
            // fw.initCKEditor("description" + token);
            onClickProductionSubmit();
            // 得到下拉的id
            var tree = $('#productHomeId' + token).combotree('tree');
            // 初始化产品头的列表
            productionHome(tree, function () {});

            //初始化融资机构列表
            var institutionTree = $('#financeInstitutionId' + token).combotree('tree');
            institution(institutionTree, function () {});

            //初始化下拉列表
            initStatusTree("status" + token, data["production.status"]);
            interestTypeTree(data["production.interestType"]);
            interestUnitTree(data["production.interestUnit"]);


            // 清空起息日事件
            onClickRemoveValueDate();

            // 初始化产品描述事件
            for (var i = 1; i <=10; i++) {
                onClickProductionDescription(data["production.id"],'btnProductionDes' + i + token, i);
            }


            console.log(data);
            console.log(data["production.id"]);

            //加载构成
            using(SCRIPTS_ROOT + '/production/ProductionCompositionClass.js', function () {
                var productionComposition = new ProductionCompositionClass(token);
                productionComposition.initModule(obj);
            });
            // 加载数据
            fw.formLoad('formProduction' + token, data);
            $("#incomeType"+token).find("option[value='"+data["production.incomeType"]+"").attr("selected",true);
        }, null);
    }


    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////
    /**
     * 查询事件
     */
    function onClickProductionSearch() {
        var buttonId = "btnSearchProduction" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ProductionTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["productionVO.name"] = $("#search_Name" + token).val();
            params["productionVO.status"] = fw.getFormValue('search_Status' + token, fw.type_form_combotree, fw.type_get_value);
            params["productionOrder"] = fw.getFormValue('search_Order' + token, fw.type_form_combotree, fw.type_get_text);

            params["productionVO.productHomeId"] = fw.getFormValue('search_ProductionName' + token, fw.type_form_combotree, fw.type_get_value);
            $('#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
        });

    }

    /**
     * 查询重置事件
     */
    function onClickProductionSearchReset() {
        var buttonId = "btnResetProduction" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_Name" + token).val('');
            fw.combotreeClear('#search_ProductionName' + token);
            fw.combotreeClear('#search_Status' + token);
            fw.combotreeClear('#search_Order' + token);
        });
    }

    /**
     * 添加事件
     */
    function onClickProductionAdd() {

        var buttonId = "btnProductionAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initWindowProductionWindow({}, obj, WindowType_Add);
        });

    }

    /**
     * 删除事件
     */
    function onClickProductionDelete() {
        var buttonId = "btnProductionDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {

                    var url = WEB_ROOT + "/production/Production_del.action?production.id=" + selected.id;
                    $.post(url, null, function (data) {
//                        console.log(data);
                        var json = "[" + data + "]";
                        var jsonArray = eval('(' + json + ')');
                        if (jsonArray[0].returnValue[0].state == 1) {
                            fw.alert("提示", "该产品还有子构成信息，请在修改中删除其构成信息后再进行删除！");
                        } else {
                            var url = WEB_ROOT + "/production/Production_delete.action?production.sid=" + selected.sid;
                            var productionInfoURL = WEB_ROOT + "/production/ProductionInfo_loadDescription.action?productionInfo.productionId=" + selected.id;

                            fw.post(productionInfoURL, null, function (ProductionInfoData) {
                                //fw.alertReturnValue(ProductionInfoData);
                                fw.post(url, null, function () {
                                    var url = WEB_ROOT + "/production/ProductionInfo_delete.action?productionInfo.sid=" + ProductionInfoData["productionInfo.sid"];
                                    fw.post(url, null, function () {
                                        fw.datagridReload('ProductionTable' + token);
                                    }, null);
                                }, null);
                            }, null);
                        }
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickProductionEdit() {
        var buttonId = "btnProductionEdit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;

                if(selected.status == 0){
                    var url = WEB_ROOT + "/production/Production_load.action?production.id=" + id;
                    fw.post(url, null, function (data) {
//                    fw.alertReturnValue(data);
                        initWindowProductionWindow(data, id, WindowType_Edit);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                }else{
                    alert("当前状态订单无法修改，请审核为草稿再进行修改操作");
                    initTableProductionTable();
                }

            })

        });
    }

    function onClickProductionView() {
        var buttonId = "btnProductionView" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/production/Production_load.action?production.id=" + id;
                fw.post(url, null, function (data) {
//                    fw.alertReturnValue(data);
                    initWindowProductionWindow(data, id, WindowType_Edit);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }
    /**
     * 审核事件
     *
     * 原有实现代码：5CE41511
     */
    function onClickProductionCheck() {
        var buttonId = "btnProductionCheck" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/production/Production_load.action?production.id=" + id;
                fw.post(url, null, function (data) {
//                    fw.alertReturnValue(data);
                    var url = WEB_ROOT + "/production/ProductionInfo_load.action?productionInfo.productionId=" + data["production.id"];
                    fw.post(url, null, function (data1) {
                        //fw.alertReturnValue(data1);
                        data["productionInfo.sid"] = data1["productionInfo.sid"];
                        data["productionInfo.id"] = data1["productionInfo.id"];
                        data["productionInfo.state"] = data1["productionInfo.state"];
                        data["productionInfo.operatorId"] = data1["productionInfo.operatorId"];
                        data["productionInfo.operateTime"] = data1["productionInfo.operateTime"];
                        data["productionInfo.productionId"] = data1["productionInfo.productionId"];
                        data["productionInfo.description"] = data1["productionInfo.description"];
                        initWindowProductionWindow(data, id, WindowType_Check);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                }, null);
            })

        });
    }

    /**
     * 根据产品列出客户信息
     */
    function onClickProductionCustomerList() {
        var buttonId = "btnProductionCustomerList" + token;
        fw.bindOnClick(buttonId, function () {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                var id = selected.id;
                // var id = selected.id;
                // var url = WEB_ROOT + "/production/Production_listCustomer.action";
                initWindowProductionCustomerWindow(id);
            })

        });
    }


    /**
     * 数据提交事件
     */
    function onClickProductionSubmit() {


        var buttonId = "btnProductionSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.CurrencyFormatText('size' + token);
            //判断配额是否小于或等于0
            if (fw.getCurrencyFormatValue('size' + token) <= 0) {
                fw.alert("警告", "配额不可为0");
                return;
            }
            //开始时间
            var startTime = fw.getFormValue('startTime' + token, fw.type_form_datebox, fw.type_get_value);
            // 到期日
            var stopTime = fw.getFormValue('stopTime' + token, fw.type_form_datebox, fw.type_get_value);
            //结束时间不能小于开始时间
            if (stopTime < startTime) {
                fw.alert("警告", "结束时间不能小于开始时间");
                return;
            }


            //  起息日不能大于到期日
            //起息日
            var valueDate = fw.getFormValue('valueDate' + token, fw.type_form_datebox, fw.type_get_value);
            // 到期日
            var expiringDate = fw.getFormValue('expiringDate' + token, fw.type_form_datebox, fw.type_get_value);

            if (!fw.checkIsTextEmpty(valueDate) && !fw.checkIsTextEmpty(expiringDate) && valueDate >= expiringDate) {

                fw.alert("警告", "起息日不能大于或等于到期日");
                return;
            }
            //投资期限
            var InvestTermView =   $("#InvestTermView"+token).val();
            //显示期限范围
            var InvestTerm = $("#InvestTerm"+token).val();

            //判断数据有效性
            if (!fw.checkIsNullObject(InvestTerm) && InvestTerm < 0) {
                fw.alert("警告", "网站显示期限范围数据填写正确");
                return false;
            }

            //结束时间不能小于开始时间
            var formId = "formProduction" + token;
            var url = WEB_ROOT + "/production/Production_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
//                    alert('done');
                process.afterClick();
                fw.datagridReload("ProductionTable" + token);
                fw.windowClose('ProductionWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 创建人：张舜清
     * 时间：2015年8月18日20:49:37
     *
     * @param treeId
     * @param success
     */
    function productionHome(treeId, success) {
        var url = WEB_ROOT + "/production/ProductionHome_productionHomeList.action";
        fw.treeLoad(treeId, url, null, success, null);
    }


    //初始化融资机构列表
    function institution(treeId, success){
        var url = WEB_ROOT + "/customer/CustomerInstitution_financeInstitutionList.action";
        fw.treeLoad(treeId, url, null, success, null);
    }

    function initSelectArea() {

        if (!isSelectWindow) {
            $("#ProductionSelectArea" + token).remove();
            return;
        }

        $("#btnProductionAdd" + token).remove();
        $("#btnProductionEdit" + token).remove();
        $("#btnProductionDelete" + token).remove();
        $("#search_Status" + token).remove();
        //初始化选择项目确定按钮
        onClickSelect();

    }

    function onClickSelect() {
        var buttonId = "#btnSelect" + token;

        fw.bindOnClick(buttonId, function () {
            fw.datagridGetSelected('ProductionTable' + token, function (selected) {
                if (fw.checkIsFunction(callbackfunction)) {
                    callbackfunction(selected);
                }
                fw.windowClose("ProductionWindow" + token);
            });
        });
    }


    function initWindowProductionDesWindow(productionInfoId, productionId, title, content, isDisplay, index) {
        var innerToken = token + index;
        var url = WEB_ROOT + "/modules/production/Production_Description.jsp?token=" + innerToken + "&index=" + index;
        var windowId = "ProductionDesWindow" + innerToken;


        fw.window(windowId, '产品介绍', 800, 500, url, function () {

            onClickProductionDesSubmit(innerToken);

            fw.initCKEditor("content" + innerToken);
            $("#title" + innerToken).val(title);
            $("#content" + innerToken).val(content);
            $("#productionInfoId" + innerToken).val(productionInfoId);
            $("#productionId" + innerToken).val(productionId);
            $("#index" + innerToken).val(index);

            // 初始化是否显示
            fw.getComboTreeFromKV('isDisplay'+innerToken, 'Is_Avaliable', "-2", isDisplay);
        }, null);
    }
    //定义产品介绍结束///////////////////////////////////////////////

    /**
     * 数据提交事件
     */
    function onClickProductionDesSubmit(token) {


        var buttonId = "btnProductionDesSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            //alert("开始提交");
            var formId = "formProductionDes" + token;
            // 更改url
            var url = WEB_ROOT + "/production/ProductionInfo_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.windowClose('ProductionDesWindow' + token);
            }, function() {
                process.afterClick();
            });
        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    var isSelectWindow = false;
    var callbackfunction = null;

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initModuleWithSelect: function (callback) {
            isSelectWindow = true;
            callbackfunction = callback;
            return initAll();
        }
    };
}
