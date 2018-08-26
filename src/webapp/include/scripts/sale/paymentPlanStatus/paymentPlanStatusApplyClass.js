/**
 * 兑付申请脚本
 * 根据传入的状态定义不同的操作
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var paymentPlanStatusApplyClass = function (token, status) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化表格
        //initTablePaymentPlanTable();
        initTableProductionPaymentPlanTable();
        //初始化列表状态选择查询。
        //项目名称
        initProductionHomeSearchForm();

        // 初始化查询事件
        onClickPaymentPlanSearch();
        // 初始化查询重置事件
        onClickPaymentPlanSearchReset();
        interestTypeTree("search_Type", '-2')


    }
    /**
     * 获取项目名称
     */
    function initProductionHomeSearchForm() {
        var tree = $('#Search_ProjectName'+token).combotree('tree');
        projectMenu(tree, function(){});
    }
    function projectMenu(treeId, success) {
        var url = WEB_ROOT+"/system/ProjectMenu_list.action";
        fw.treeLoad(treeId,url,null, success, null);
    }

    //初始化兑付类型
    function interestTypeTree(typeId, selectIndexId) {
        var URL = WEB_ROOT + "/production/Production_interestTypeTree.action";
        //付息类型
        fw.combotreeLoad(typeId + token, URL, selectIndexId);
    }

    /**
     * 初始化产品兑付表格
     */
    function initTableProductionPaymentPlanTable() {
        var strTableId = 'ProductionPaymentPlanApplyTable' + token;//表格ID

        //访问的URLAction
        var url = WEB_ROOT + "/sale/PaymentPlan_getProductionlist.action";

        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '',
            url: url,
            queryParams: {
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,//行是否适应屏幕
            singleSelect: true,//是否只能选择一行
            pageList: [10, 20, 30],//设置每页显示多少条
            pageSize: 10,//初始化每页显示多少
            rownumbers: true,//是否显示行号
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);// 解析从数据库返回的数据
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,//是否分頁
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'productId', title: '产品编号',hidden: true},
                    {field: 'projectName', title: '项目名称'},
                    {field: 'productName', title: '产品名称'},
                    {field: 'totalPaymentMoney', title: '应兑付总金额'},
                    {field: 'paymentTime', title: '兑付日期'},
                    {field: 'statusId', title: '状态', hidden: true},

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
                    {field: 'currentNodeId', title: '当前节点', hidden: true},
                    {field: 'routeListId', title: '路由节点', hidden: true},

                    {field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            //设置按钮的文字与图片
            toolbar: [{
                id: 'btnPaymentPlanPayApply' + token,
                text: '申请',
                iconCls: 'icon-edit'
            }
            ],
            //加载成后做的事
            onLoadSuccess: function () {
                onClickPaymentPlanApply();
            }
        });

    }


    /**
     * 初始化兑付详情表格
     */
    function initTablePaymentPlanTable(productId,paymentTime) {
        var strTableId = 'PaymentPlanApplyTable' + token;//表格ID

        //访问的URLAction
        var url = WEB_ROOT + "/sale/PaymentPlan_getPaymentPlanPagerIsFoApply.action?paymentPlan.paymentTime="+paymentTime+"&paymentPlan.productId="+productId;

        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '',
            url: url,
            queryParams: {
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,//行是否适应屏幕
            singleSelect: true,//是否只能选择一行
            pageList: [5, 10, 150],//设置每页显示多少条
            pageSize: 5,//初始化每页显示多少
            rownumbers: true,//是否显示行号
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);// 解析从数据库返回的数据
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,//是否分頁
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    {field: 'loginName', title: '用户名'},
                    {field: 'customerName', title: '客户姓名'},
                    {field: 'productName', title: '产品名称'}
                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    {field: 'sid', title: '序号', hidden: true},
                    {field: 'id', title: '编号', hidden: true},
                    {field: 'type', title: '兑付类型'},
                    {field: 'orderName', title: '订单名称'},
                    //{ field: 'paymentMoney', title: '兑付金额'},
                    {field: 'paymentTime', title: '兑付日期'},
                    {field: 'totalInstallment', title: '兑付总期数'},
                    {field: 'currentInstallment', title: '当前兑付期数'},
                    {field: 'statusName', title: '兑付状态'},
                    {field: 'totalPaymentMoney', title: '应兑付总金额'},
                    {field: 'totalPaymentPrincipalMoney', title: '应兑付本金总金额'},
                    {field: 'totalProfitMoney', title: '应兑付收益总金额'},
                    {field: 'paiedPrincipalMoney', title: '已兑付本金金额'},
                    {field: 'paiedProfitMoney', title: '已兑付收益金额'},
                    {field: 'comment', title: '备注'}

                ]
            ],
            //设置按钮的文字与图片
            toolbar: [{

            }
            ],
            //加载成后做的事
            onLoadSuccess: function () {
            }
        });

    }


///  初始化部分 结束  /////////////////////////////////////////////////////////////////


///  事件定义 开始  /////////////////////////////////////////////////////////////////


    /**
     * 申请事件
     */
    function onClickPaymentPlanApply() {
        var buttonId = "btnPaymentPlanPayApply" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionPaymentPlanApplyTable'+token, function(selected){
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经进入流程转正无法修改数据");
                    process.afterClick();
                    return;
                }
                process.beforeClick();
                var data={'productId':selected.productId,'projectName':selected.projectName,'productName':selected.productName,
                    'totalPaymentMoney':selected.totalPaymentMoney,'paymentTime':selected.paymentTime,'currentNodeId':selected.currentNodeId,'routeListId':selected.routeListId};
                initWindowPaymentPlanWindow(data);
                    process.afterClick();

            });


        });
    }


    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowPaymentPlanWindow(data) {

        var url = WEB_ROOT + "/modules/sale/paymentPlanStatus/paymentPlanStatus_Apply_save.jsp?token=" + token;
        var windowId = "PaymentPlanWindow" + token;
        //弹出窗口将数据带到指定jsp页面
        fw.window(windowId, '兑付详情', 800, 550, url, function () {

            //兑付详情列表
            initTablePaymentPlanTable(data.productId,data.paymentTime);
            //申请提交事件
            onclickSubmit(data.productId,data.paymentTime,data.currentNodeId,data.routeListId);
            fw.formLoad('formPaymentPlan' + token, data);
        }, null);
    }

    /**
     * 提交工作流审批事件
     * @param productId 产品id
     * @param paymentTime 兑付时间
     * @param currentNodeId 当前节点号
     * @param routeListId 当前路由节点号
     */
    function onclickSubmit(productId,paymentTime,currentNodeId,routeListId){


        var butonId = "btnApplaySubmit"+token;
        fw.bindOnClick(butonId,function(process){
            if(!paymentTime ){

                fw.alert("警告","数据出错请联系管理员，【兑付时间错误】");
                return false
            }
            if(!productId){
                fw.alert("警告","数据出错请联系管理员，【产品编号错误】");
                return false
            } if(!currentNodeId && ! routeListId){
                fw.alert("警告","数据出错请联系管理员，【工作流数据出错误】");

                return false
            }
            process.beforeClick();
            var URl=WEB_ROOT+"/sale/PaymentPlan_getInssertApplayWorlkflowId.action?paymentPlanVO.paymentTime="+paymentTime+"&paymentPlanVO.productId="+productId;
            fw.post(URl, null, function (data) {

                if(!data.id){
                    fw.alert("警告","数据出错请联系管理员，【产品工作流编号出错】");
                    return false
                }
                var datas={'workflowID':'14','id':data.id,'routeListId':routeListId,'currentNodeId':currentNodeId,'nextNode':'2','serviceClassName':'com.youngbook.service.sale.PaymentPlanService'};
                fw.onclickworkflowApplay(datas,process,"ProductionPaymentPlanApplyTable" + token,'', '','PaymentPlanWindow'+token);
                process.afterClick
            }, null);
        });
    }


    /**
     * 查询事件
     */
    function onClickPaymentPlanSearch() {
        var buttonId = "btnSearchPaymentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "PaymentPlanApplyTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["paymentPlanVO.productName"] = $("#Search_ProductionName"+token).val();
            params["paymentPlanVO.projectId"] = fw.getFormValue('Search_ProjectName'+token, fw.type_form_combotree, fw.type_get_value);

            $( '#' + strTableId).datagrid('load');


        });
    }

    /**
     * 查询重置事件
     */
    function onClickPaymentPlanSearchReset() {
        var buttonId = "btnResetPaymentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.combotreeClear('Search_ProjectName' + token);
            //清空文本框的值
            $('#Search_ProductionName' + token).val('');
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

