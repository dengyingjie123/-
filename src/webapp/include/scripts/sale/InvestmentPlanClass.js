/**
 * Created by Administrator on 2015/4/2.
 */
var InvestmentPlanClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickInvestmentPlanSearch()
        // 初始化查询重置事件
        onResetInvestmentPlanSearch()

        // 初始化表格
        initTableInvestmentPlan();
    }


    /**
     * 初始化表格
     */
    function initTableInvestmentPlan() {
        var strTableId = 'InvestmentPlanTable' + token;
        var url = WEB_ROOT + "/sale/InvestmentPlan_list.action";
        $('#' + strTableId).datagrid({
            title: '投资计划',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            loadFilter: function (data) {
                //alert(JSON.stringify(data));
                //alert(data.code);
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
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    { field: 'name', title: '名称' },
                    { field: 'description', title: '描述' },
                    { field: 'typeId', title: '类型编号' },
                    { field: 'investMoneyMin', title: '最小投资额' },
                    { field: 'investMoneyMax', title: '最大投资额' },
                    { field: 'investTimeMin', title: '最短投资期限' },
                    { field: 'investTimeMax', title: '最长投资期限' },
                    { field: 'returnRateMin', title: '最小回报率' },
                    { field: 'returnRateMax', title: '最大回报率' },
                    { field: 'planStartTime', title: '计划开始时间' },
                    { field: 'planTimeStop', title: '计划结束时间' },
                    { field: 'investTermView', title: '网站显示期限' },
                    { field: 'investTerm', title: '网站期限范围' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnInvestmentPlanAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnInvestmentPlanEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnInvestmentPlanDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                onClickInvestmentPlanAdd();
                onClickInvestmentPlanDelete();
                onClickInvestmentPlanEdit();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickInvestmentPlanAdd() {
        var buttonId = "btnInvestmentPlanAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initWindowInvestmentPlan({}, WindowType_Add);
        });
    }

    /**
     * 删除事件
     */
    function onClickInvestmentPlanDelete() {
        var buttonId = "btnInvestmentPlanDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('InvestmentPlanTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/sale/InvestmentPlan_delete.action?investmentPlan.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('InvestmentPlanTable' + token);
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
    function onClickInvestmentPlanEdit() {
        var butttonId = "btnInvestmentPlanEdit" + token;
        fw.bindOnClick(butttonId, function (process) {

            fw.datagridGetSelected('InvestmentPlanTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/sale/InvestmentPlan_load.action?investmentPlan.id=" + id;
                fw.post(url, null, function (data) {

                    initWindowInvestmentPlan(data, WindowType_Edit);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });

    }

    /**
     * 数据提交事件
     */
    /*修改：周海鸿
     * 时间：2015-7-13
     * 内容：添加数据有效性验证
     * */
    function onClickInvestmentPlanSubmit() {
        var buttonId = "btnInvestmentPlanSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.CurrencyFormatText('investMoneyMin' + token);
            fw.CurrencyFormatText('investMoneyMax' + token);

            var investMoneyMax = parseInt(fw.getCurrencyFormatValue("investMoneyMax" + token));//最大投资额
            var investMoneyMin =  parseInt(fw.getCurrencyFormatValue("investMoneyMin" + token)); //最小投资额

            var returnRateMax =  parseInt($("#returnRateMax" + token).val());//最大回报率
            var returnRateMin =  parseInt($("#returnRateMin" + token).val()); //最小回报率

            //最大投资额 不能小于最小投资额
            if (investMoneyMax<=investMoneyMin){
                fw.alert("警告","最大投资额必须大于最小投资额");
                return;
            }
            //回报率不能为负数
            if(returnRateMax<0){
                fw.alert("警告","回报率不能为负数");
                return;
            } //回报率不能为负数
            if(returnRateMin<0){
                fw.alert("警告","回报率不能为负数");
                return;
            }
            //最大回报率不能小于最小回报率
            if (returnRateMax<=returnRateMin){
                fw.alert("警告","最大回报率必须大于最小回报率");
                return;
            }

           var formId = "formInvestmentPlan" + token;
            //更改url，走对应Action类的insertOrUpdate方法
            var url = WEB_ROOT + "/sale/InvestmentPlan_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("InvestmentPlanTable" + token);
                fw.windowClose('InvestmentPlanWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 初始化查询事件
     */
    function onClickInvestmentPlanSearch() {
        var buttonId = "btnSearchInvestmentPlan" + token;
        fw.bindOnClick(buttonId, function () {
            var strTableId = "InvestmentPlanTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["investmentPlanVO.name"] = $('#search_Name' + token).val();
            $('#' + strTableId).datagrid('load');
        })
    }

    /**
     * 初始化重置事件
     */
    function onResetInvestmentPlanSearch() {
        var buttonId = "btnResetInvestmentPlan" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_Name' + token).val("");
        });

    }

    /**
     * 初始化窗口事件
     * 修改人：姚章鹏
     * 添加文本插件
     * 审核时间变成只读
     *重置删除加载列表
     */
    var WindowType_Add = 1;
    var WindowType_Edit = 2;

    function initWindowInvestmentPlan(data, windowType) {
        data["investmentPlan.operatorId"] = loginUser.getId();
        data["investmentPlan.operatorName"] = loginUser.getName();
        var url = WEB_ROOT + "/modules/sale/InvestmentPlan_Save.jsp?token=" + token;
        var windowId = "InvestmentPlanWindow" + token;
        fw.window(windowId, '投资计划', 800, 570, url, function () {
            //数据提交事件

            if (windowType == WindowType_Edit) {
                $("#checkTime" + token).datebox({
                    readonly: true
                });
            }
            fw.textFormatCurrency('investMoneyMin' + token);

            fw.textFormatCurrency('investMoneyMax' + token);


            // 初始化ckeditor
            initCKEditor();
            onClickInvestmentPlanSubmit();
            fw.formLoad('formInvestmentPlan' + token, data);
        }, null);

    }


    function initCKEditor() {
        using(SCRIPTS_ROOT + '/ckeditor/4.4.3/ckeditor.js', function () {
            CKEDITOR.replace('investmentPlan.description');
        });
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