/**
 * Created by zsq on 4/22/2015.
 */
var ProductionTransferClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化属性菜单
        initStatusTree(null,"-2");

        // 初始化查询事件
        onClickProductionTransferSearch();
        // 初始化查询重置事件
        onClickProductionTransferSearchReset();
        // 初始化表格
        initProductionTransferTable()
    }

    /**
     *  初始化表格事件
     */
    function initProductionTransferTable() {
        var strTableId = 'ProductionTransferTable'+token;
        var url = WEB_ROOT+"/sale/ProductionTransfer_list.action";

        $('#'+strTableId).datagrid({
            title: '产品转让',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:false,
            singleSelect:true,
            pageList:[15,30,60],
            pageSize: 15,
            rownumbers:true,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);

                    //alert(JSON.stringify(data));
                    return data;
                }
                catch(e) {
                }
            },
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            columns: [[
                { field: 'originalProductionNum', title: '原产品编号'},
                { field: 'originalOrderNum', title: '原订单编号'},
                { field: 'originalCustumerName', title: '原产品持有人'},
                { field: 'originalMoney', title: '原投资金额'},
                { field: 'originalProfitRate', title: '原收益率'},
                { field: 'currentProductionNum', title: '现产品编号'},
                { field: 'currentCustomerName', title: '现产品持有人'},
                { field: 'currentMoney', title: '现投资金额'},
                { field: 'currentProfitRate', title: '现收益率'},
                { field: 'transferMoney', title: '转让金额'},
                { field: 'transferProfitRate', title: '转让收益率'},
                { field: 'transfer_Satues', title: '转让状态'},
                { field: 'checkerName', title: '审核人'},
                { field: 'checkTime', title: '审核时间'},
                { field: 'transferStartTime', title: '转让开始时间'},
                { field: 'transferEndTime', title: '转让结束时间'}
            ]],
            toolbar:[{
                id:'btnProductionTransferAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnProductionTransferEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnProductionTransferDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                onClickProductionTransferAdd();
                onClickProductionTransferEdit();
                onClickProductionTransferDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickProductionTransferAdd() {
        var buttonId = "btnProductionTransferAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            //var s = $('#transferSatues' + token).combobox('getValue');
            initProductionTransferWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickProductionTransferEdit() {
        var butttonId = "btnProductionTransferEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('ProductionTransferTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/sale/ProductionTransfer_load.action?productionTransfer.id="+selected.id;
                fw.post(url, null, function(data){
                    data['productionTransferVO.originalProductionNum'] = selected.originalProductionNum;
                    data['productionTransferVO.originalOrderNum'] = selected.originalOrderNum;
                    data['productionTransferVO.originalCustumerName'] = selected.originalCustumerName;
                    data['productionTransferVO.currentProductionNum'] = selected.currentProductionNum;
                    data['productionTransferVO.currentCustomerName'] = selected.currentCustomerName;
                    data['productionTransferVO.checkerName'] = selected.checkerName;

                    initProductionTransferWindow(data,null);
                    process.afterClick();
                }, function(){
                    process.afterClick();
                });
            })
        });
    }

    /**
     * 删除事件
     */
    function onClickProductionTransferDelete() {
        var buttonId = "btnProductionTransferDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProductionTransferTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/sale/ProductionTransfer_delete.action?productionTransfer.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('ProductionTransferTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 弹窗事件
     */
    function initProductionTransferWindow(data){
        data["productionTransfer.operatorId"] = loginUser.getId();
        data["productionTransfer.operatorName"] = loginUser.getName();
        var url =  WEB_ROOT + "/modules/sale/ProductionTransfer_Save.jsp?token="+token;
        var windowId = "ProductionTransferWindow" + token;
        fw.window(windowId, '产品转让', 630, 330, url, function() {
            fw.textFormatCurrency('money'+token);
            fw.textFormatCurrency('nowMoney'+token);
            fw.textFormatCurrency('transferMoney'+token);
            initStatusTree("transferSatues"+token,data["productionTransfer.transferSatues"]);
            //数据提交事件
            onClickProductionTransferSubmit();
            fw.formLoad('formProductionTransfer'+token, data);
        }, null);
    }

    /**
     * 数据提交事件
     */
    function onClickProductionTransferSubmit() {
        var buttonId = "btnProductionTransferSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            fw.CurrencyFormatText('money'+token);
            fw.CurrencyFormatText('nowMoney'+token);
            fw.CurrencyFormatText('transferMoney'+token);
            //alert("开始提交");
            var formId = "formProductionTransfer" + token;
            var url = WEB_ROOT + "/sale/ProductionTransfer_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("ProductionTransferTable"+token);
                fw.windowClose('ProductionTransferWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickProductionTransferSearch() {
        var buttonId = "btnProductionTransferSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "ProductionTransferTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params['productionTransferVO.originalProductionNum'] = $('#search_originalProductionNum' + token).val();
            params['productionTransferVO.originalCustumerName'] = $('#search_originalCustumerName' + token).val();
            params['productionTransferVO.currentProductionNum'] = $('#search_currentProductionNum' + token).val();
            params['productionTransferVO.currentCustomerName'] = $('#search_currentCustomerName' + token).val();
            params['productionTransferVO.transferSatues'] = $('#search_transferSatues' + token).combotree("getValue");
            //alert(JSON.stringify(params));
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickProductionTransferSearchReset() {
        var buttonId = "btnProductionTransferSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 清空对应查询
            $('#search_originalProductionNum' + token).val('');
            $('#search_originalCustumerName' + token).val('');
            $('#search_currentProductionNum' + token).val('');
            $('#search_currentCustomerName' + token).val('');
            fw.combotreeClear("search_transferSatues" + token);
            // 清空查询后重新加载列表
            var strTableId = "ProductionTransferTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params['productionTransferVO.originalProductionNum'] = $('#search_originalProductionNum' + token).val();
            params['productionTransferVO.originalCustumerName'] = $('#search_originalCustumerName' + token).val();
            params['productionTransferVO.currentProductionNum'] = $('#search_currentProductionNum' + token).val();
            params['productionTransferVO.currentCustomerName'] = $('#search_currentCustomerName' + token).val();
            params['productionTransferVO.transferSatues'] = $('#search_transferSatues' + token).combotree("getValue");
            //alert(JSON.stringify(params));
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 初始化下拉列表项
     * selectIndexId 为选中的项 -2 为什么都选
     */
    function initStatusTree(combotreeId,selectIndexId) {
        if(combotreeId==null){
            combotreeId="search_transferSatues" + token;
        }
        var URL = WEB_ROOT + "/sale/ProductionTransfer_StatusTree.action";
        fw.combotreeOnload(combotreeId,URL,function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {}
            return treeData;
        },selectIndexId);
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        }
    };
};