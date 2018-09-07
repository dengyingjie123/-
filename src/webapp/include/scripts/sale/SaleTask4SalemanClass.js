/**
 * Created by Administrator on 2015/5/22.
 * 销售成品产品分配
 */
// 附表的 JS 代码
var SaleTask4SalemanClass = function (token,SalemanGroupData) {

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化表格
        initSaleTask4SalemanTable();
    }

    /**
     * 初始化表格
     */
    function initSaleTask4SalemanTable() {
        var strTableId = 'saleTask4SalemanTable' + token;
        var url = WEB_ROOT + '/sale/SaleTask4Saleman_list.action';
        $('#' + strTableId).datagrid({
            url: url,
            queryParams: {
                // mainId：主表的 关联ID
            },
            loadMsg: '数据正在加载，请稍候……',
            fitColumns: false,
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
                    { field: 'sid', title: 'sid', hidden: true, sortable: true},
                    { field: 'id', title: 'id', hidden: true, sortable: true},
                    { field: 'state', title: 'state', hidden: true, sortable: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true, sortable: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true, sortable: true},
                    { field: 'productionId', title: '产品编号', hidden: true, sortable: true},
                    { field: 'saleGroupId', title: '销售组编号', hidden: true, sortable: true},
                    { field: 'salemanId', title: '销售员编号', hidden: true, sortable: true},
                    { field: 'salemanName', title: '销售员', hidden: false, sortable: true},
                    { field: 'taskMoney', title: '分配金额', hidden: false, sortable: true},
                    { field: 'startTime', title: '开始时间', hidden: false, sortable: true},
                    { field: 'endTime', title: '结束时间', hidden: false, sortable: true},
                    { field: 'waitingMoney', title: '待售金额', hidden: false, sortable: true},
                    { field: 'appointmengMoney', title: '预约金额', hidden: false, sortable: true},
                    { field: 'soldMoney', title: '打款金额', hidden: false, sortable: true},
                    { field: 'totoalCancelMoney', title: '累计取消金额', hidden: false, sortable: true}
                ]
            ],
            toolbar: [
                {
                    id: 'btnSaleTask4SalemanAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSaleTask4SalemanEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnSaleTask4SalemanDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickSaleTask4SalemanAdd();
                onClickSaleTask4SalemanEdit();
                onClickSaleTask4SalemanDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickSaleTask4SalemanAdd() {
        var buttonId = 'btnSaleTask4SalemanAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initWindowSaleTask4Saleman({});
        });
    }

    /**
     * 修改事件
     */
    function onClickSaleTask4SalemanEdit() {
        var butttonId = 'btnSaleTask4SalemanEdit' + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('saleTask4SalemanTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + '/sale/SaleTask4Saleman_load.action?saleTask4Saleman.id=' + selected.id;
                fw.post(url, null, function (data) {
                    data["saleTask4SalemanVO.salemanName"]=selected.salemanName;
                    initWindowSaleTask4Saleman(data);
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
    function onClickSaleTask4SalemanDelete() {
        var buttonId = 'btnSaleTask4SalemanDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('saleTask4SalemanTable' + token, function (selected) {
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + '/sale/SaleTask4Saleman_delete.action?saleTask4Saleman.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('saleTask4SalemanTable' + token);
                        process.afterClick();
                    }, function(){
                        process.afterClick();
                    });
                }, null);
            });
        });
    }

    /**
     * 弹窗事件
     * @param data
     */
    /**
     * 修改人周海鸿
     * 修改时间：2015-6-29
     * 修改时间：添加项目productionId 参数 用来作为条件查询小组
     */
    function initWindowSaleTask4Saleman(data) {
        data['saleTask4Saleman.operatorId'] = loginUser.getId();
        data['saleTask4Saleman.operatorName'] = loginUser.getName();
        var url = WEB_ROOT + '/modules/sale/SaleTask4Saleman_Save.jsp?token=' + token;
        var windowId = 'saleTask4SalemanWindow' + token;
        fw.window(windowId, '销售人员分配', 350, 350, url, function () {
          var saleManGroupId=  SalemanGroupData["saleTask4Group.saleGroupId"];
          var productionId=  SalemanGroupData["saleTask4Group.productionId"];
            $("#salemanName"+token).val(data["salemanName"]);
            //初始化文本框
            initSelecetSaleman(saleManGroupId,productionId);
            //提交事件
            onClickSaleTask4SalemanSubmit();
            // 加载数据
            fw.formLoad('formSaleTask4Saleman' + token, data);
        }, null);
    }


    /**
     * 数据提交事件
     */
    function onClickSaleTask4SalemanSubmit() {
        var buttonId = 'btnSaleTask4SalemanSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            //人员分配金额不能大于小组分配金额
            var taskMoney=$("#SalemantaskMoney"+token).val();
            if(taskMoney>SalemanGroupData["saleTask4Group.taskMoney"]){
                fw.alert("警告！","人员分配金额不能大于小组分配金额");
                return;
            }
            var formId = 'formSaleTask4Saleman' + token;
            var url = WEB_ROOT + '/sale/SaleTask4Saleman_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('saleTask4SalemanTable' + token);
                fw.windowClose('saleTask4SalemanWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 初始化文本框选择事件
     */
    /**
     * 修改人周海鸿
     * 修改时间：2015-6-29
     * 修改时间：添加项目productionId 参数 用来作为条件查询小组
     */
    function initSelecetSaleman(saleManGroupId,productionId){
        var textId="#salemanName"+token;
        $(textId).bind("click",function() {
            initWindowdutySaleMan(saleManGroupId,productionId);
        });
    }



    /**
     * 初始化弹出选中销售列表
     * @param saleManGroupId
     */
    /**
     * 修改人周海鸿
     * 修改时间：2015-6-29
     * 修改时间：添加项目productionId 参数 用来作为条件查询小组
     */
    function initWindowdutySaleMan(saleManGroupId ,productionId) {
        var windowId = "SelectSaleManWindow" + token;
        var url = WEB_ROOT + "/modules/sale/Saleman_Select.jsp?token=" + token;
        fw.window(windowId, "销售人员列表", 800, 500, url, function (saleManGroupId) {
            /**
             * 选中销售成员
             */
            using(SCRIPTS_ROOT + "/sale/SalesmanInfoClass.js", function () {
                var salesmanInfoClass = new SalesmanInfoClass(token, null, saleManGroupId, '3',productionId);
                salesmanInfoClass.initModule();
            })
            OnSelectdutySaleman();
        }, null, saleManGroupId);
    }

    /**
     * 选中销售人员列表信息
     * @param saleManGroupId
     * @constructor
     */
    function OnSelectdutySaleman(){
        var buttonId = "btnSelectSalesmanSelection" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SelectsalesManTable' + token, function (selected) {
                process.beforeClick();
                $("#salemanName"+token).val(selected.userName);
                $("#salemanId"+token).val(selected.id);
                var productionId=SalemanGroupData["saleTask4Group.productionId"];
                var salegroupId=SalemanGroupData["saleTask4Group.saleGroupId"];
                $("#production"+token).val(productionId);
                $("#saleGroup"+token).val(salegroupId);
                fw.datagridReload('saleTask4SalemanTable' + token);
                fw.windowClose('SelectSaleManWindow' + token);
            });
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