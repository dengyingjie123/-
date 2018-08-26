/**
 * Created by 邓超
 * Date 2015-5-18
 */
var FinanceBizTripExpenseItemClass = function (token, obj, expenseId) {

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        // 初始化查询重置事件
        // 初始化表格
        initFinanceBizTripExpenseItemTable();
    }

    /**
     * 初始化表格
     */
    function initFinanceBizTripExpenseItemTable() {
        var strTableId = 'financeBizTripExpenseItemTable' + token;
        var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseItem_list.action';
        $('#' + strTableId).datagrid({
            url: url,
            queryParams: {
                "financeBizTripExpenseItemVO.expenseId": expenseId
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: [5],
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
                    {field: 'id', title: 'Id', hidden: true},
                    {field: 'expenseId', title: 'ExpenseId', hidden: true},
                    {field: 'startTime', title: '开始时间' },
                    {field: 'endTime', title: '结束时间' },
                    {field: 'startAddress', title: '起始地' },
                    {field: 'endAddress', title: '结束地' },
                    {field: 'roadFee', title: '过路费' },
                    {field: 'airplaneFee', title: '飞机票费' },
                    {field: 'trainFee', title: '火车票费' },
                    {field: 'busFee', title: '汽车票费' },
                    {field: 'foodFee', title: '伙食补贴' },
                    {field: 'liveFee', title: '住宿费' },
                    {field: 'otherFee', title: '其他' },
                    {field: 'totalFee', title: '合计' },
                    {field: 'comment', title: '备注' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnFinanceBizTripExpenseItemAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnFinanceBizTripExpenseItemEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFinanceBizTripExpenseItemDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {

                // 初始化事件
                onClickFinanceBizTripExpenseItemAdd();
                onClickFinanceBizTripExpenseItemEdit();
                onClickFinanceBizTripExpenseItemDelete();
            }
        });
        if (obj == "check" || obj=="upload"|| obj=="applay") {
            $('#btnFinanceBizTripExpenseItemAdd' + token).remove();
            $('#btnFinanceBizTripExpenseItemEdit' + token).remove();
            $('#btnFinanceBizTripExpenseItemDelete' + token).remove();
        }
    }

    /**
     * 添加事件
     */
    function onClickFinanceBizTripExpenseItemAdd() {
        var buttonId = 'btnFinanceBizTripExpenseItemAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initWindowFinanceBizTripExpenseItem({});
        });
    }

    /**
     * 修改事件
     */
    function onClickFinanceBizTripExpenseItemEdit() {
        var butttonId = 'btnFinanceBizTripExpenseItemEdit' + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('financeBizTripExpenseItemTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseItem_load.action?financeBizTripExpenseItem.id=' + selected.id;
                fw.post(url, null, function (data) {
                    initWindowFinanceBizTripExpenseItem(data, null);
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
    function onClickFinanceBizTripExpenseItemDelete() {
        var buttonId = 'btnFinanceBizTripExpenseItemDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('financeBizTripExpenseItemTable' + token, function (selected) {

                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    process.beforeClick();
                    var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseItem_delete.action?financeBizTripExpenseItem.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('financeBizTripExpenseItemTable' + token);
                        process.afterClick();
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 弹窗事件
     * @param data
     */
    function initWindowFinanceBizTripExpenseItem(data) {
        data['financeBizTripExpenseItem.operatorId'] = loginUser.getId();
        data['financeBizTripExpenseItem.operatorName'] = loginUser.getName();
        data['financeBizTripExpenseItem.expenseId'] = expenseId;
        var url = WEB_ROOT + '/modules/oa/expense/FinanceBizTripExpenseItem_Save.jsp?token=' + token + '&financeBizTripExpenseItemVO.expenseId=' + expenseId;
        var windowId = 'financeBizTripExpenseItemWindow' + token;
        fw.window(windowId, '报销项目', 400, 450, url, function () {
            //过路费合计
            initcalculateMOneys("#roadFee" + token);
            //飞机
            initcalculateMOneys("#airplaneFee" + token);
            //火车
            initcalculateMOneys("#trainFee" + token);
            //汽车
            initcalculateMOneys("#busFee" + token);
            //补贴
            initcalculateMOneys("#foodFee" + token);
            //住宿
            initcalculateMOneys("#liveFee" + token);
            //其他
            initcalculateMOneys("#otherFee" + token);




            //提交事件
            onClickFinanceBizTripExpenseItemSubmit();
            // 加载数据
            fw.formLoad('formFinanceBizTripExpenseItem' + token, data);
        }, null);
    }


    /**
     * 数据提交事件
     */
    function onClickFinanceBizTripExpenseItemSubmit() {
        var buttonId = 'btnFinanceBizTripExpenseItemSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = 'formFinanceBizTripExpenseItem' + token;
            var url = WEB_ROOT + '/oa/expense/FinanceBizTripExpenseItem_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                var moneyURL= WEB_ROOT+"/oa/expense/FinanceBizTripExpenseItem_getMoneys.action?financeBizTripExpenseItem.expenseId=" + expenseId
                fw.post(moneyURL,null,function(da){
                    $("#money"+token).val(da);
                    fw.datagridReload('financeBizTripExpenseItemTable' + token);
                    fw.windowClose('financeBizTripExpenseItemWindow' + token);
                });
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 2017-6-17
     * 设置文本框离开时 计算总金额
     * @param textid
     */
    function initcalculateMOneys(textid) {
        textid = fw.getObjectFromId(textid);
        $(textid).bind('blur', function () {
            //过路费
            var roadFee =$("#roadFee" + token).val()==""?0:$("#roadFee" + token).val();
            //飞机
            var airplaneFee = $("#airplaneFee" + token).val()==""?0:$("#airplaneFee" + token).val();
            //火车
            var trainFee = $("#trainFee" + token).val()==""?0:$("#trainFee" + token).val();
            //汽车
            var busFee = $("#busFee" + token).val()==""?0:$("#busFee" + token).val();
            //补贴
            var foodFee = $("#foodFee" + token).val()==""?0:$("#foodFee" + token).val();
            //住宿
            var liveFee = $("#liveFee" + token).val()==""?0:$("#liveFee" + token).val();
            //其他
            var otherFee = $("#otherFee" + token).val()==""?0:$("#otherFee" + token).val();
            //合计
            var otherFee = parseFloat(roadFee)+parseFloat(airplaneFee)+parseFloat(trainFee)+parseFloat(busFee)+parseFloat(foodFee)+parseFloat(liveFee)+parseFloat(otherFee);

            $("#totalFee" + token).val(otherFee);

        });
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