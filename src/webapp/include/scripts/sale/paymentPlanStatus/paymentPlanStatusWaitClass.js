/**
 * 兑付计划管理类
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var paymentPlanStatusWaitClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //获取项目名称。
        initProductionHomeSearchForm();

        // 初始化查询事件
        onClickPaymentPlanSearch();
        // 初始化查询重置事件
        onClickPaymentPlanSearchReset();
        // 初始化表格
        initTablePaymentPlanWaitTable();
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

    /**
     * 初始化表格
     */
    function initTablePaymentPlanWaitTable() {
        var strTableId = 'PaymentPlanWaitTable' + token;
        //访问的URLAction
        var url = WEB_ROOT + "/sale/PaymentPlan_getProductionWaitlist.action";

        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
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
                    {field: 'ck', checkbox: true}
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
                    {field: 'currentNodeTitle', title: '当前状态', hidden: false}
                ]
            ],
            //设置按钮的文字与图片
            toolbar: [
                {
                    id: 'btnPaymentPlanPayDetails' + token,
                    text: '详情',
                    iconCls: 'icon-edit'
                }
            ],
            //加载成后做的事
            onLoadSuccess: function () {
                onClickPaymentPlanDetails();
            }
        });
    }



    /**
     * 申请事件
     */
    function onClickPaymentPlanDetails() {
        var buttonId = "btnPaymentPlanPayDetails" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanWaitTable'+token, function(selected){

                process.beforeClick();
                var data={'productId':selected.productId,'projectName':selected.projectName,'productName':selected.productName,
                    'totalPaymentMoney':selected.totalPaymentMoney,'paymentTime':selected.paymentTime};
                initWindowPaymentPlanWindow(data);
                process.afterClick();

            });

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
            $("#btnApplaySubmit"+token).remove();
            fw.formLoad('formPaymentPlan' + token, data);
        }, null);
    }


///  初始化部分 结束  /////////////////////////////////////////////////////////////////


///  事件定义 开始  /////////////////////////////////////////////////////////////////


    /**
     * 兑付事件
     */
    function onClickPaymentPlanPay() {
        var buttonId = "btnPaymentPlanPay" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('PaymentPlanGuanLiTable' + token, function (selected) {
                var statusName = selected.statusName;

                var paymentTime = Date.parse(selected.paymentTime);
                if(statusName == '已兑付') {
                    fw.alert('提示', '您选的记录已被兑付！');
                    return false;
                }

                var date = new Date();
                if(paymentTime > date.getMilliseconds()) {
                    fw.alert('提示', '还未到兑付时间不能兑付！');
                    return false;
                }

                fw.confirm('确认兑付', '请慎重操作，您确定兑付这项计划吗？<br />【产品：' + selected.productName + '】<br />【金额：' + selected.totalPaymentMoney + '】', function () {
                    var id = selected.id;
                    var url = WEB_ROOT + "/sale/PaymentPlan_payment.action?id=" + id;
                    fw.post(url, {}, function (data) {
                        if(data=='1'){
                            fw.alert('提示',"改客户没有绑定银行卡,不能兑付");
                        }
                        fw.datagridReload('PaymentPlanGuanLiTable' + token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickPaymentPlanSearch() {
        var buttonId = "btnSearchPaymentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "PaymentPlanWaitTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params["paymentPlanVO.productName"] = $("#Search_ProductionName"+token).val();
            params["paymentPlanVO.projectId"] = fw.getFormValue('Search_ProjectName'+token, fw.type_form_combotree, fw.type_get_value);
             $('#' + strTableId).datagrid('load');


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


    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
}