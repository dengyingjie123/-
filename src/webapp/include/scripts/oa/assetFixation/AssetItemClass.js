/**
 * Created by Jepson on 2015/6/8.
 * 修改人：周海鸿
 * 修改时间2015-6-24
 * 修改事件：修改附表列表。
 */
var AssetItemClass = function(token,btnStatus){

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询表单
        // 初始化表格
        initAssetItemTable();
    }



    /**
     * 初始化表格
     */
    function initAssetItemTable() {
        var assetItemId = document.getElementsByName('assetApplication.id')[0].value;
        var strTableId = 'assetItem'+token;
        var url = WEB_ROOT+'/oa/assetFixation/AssetItem_list.action';

        $('#'+strTableId).datagrid({

            url: url,
            queryParams: {
                // mainId：主表的 关联ID                'assetItem.mainId': assetItemId
                'assetItem.applicationId': assetItemId
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
                        { field: 'applicationId', title: '申请编号', hidden: true, sortable: true},
                        { field: 'name', title: '名称', hidden: false, sortable: true},
                        { field: 'tusage', title: '申购用途', hidden: false, sortable: true},
                        { field: 'specification', title: '规格型号', hidden: false, sortable: true},
                        { field: 'quanity', title: '数量', hidden: false, sortable: true},
                        { field: 'expectedUnitPrice', title: '预计单价（元）', hidden: false, sortable: true},
                        { field: 'expectedMoney', title: '预计金额（元）', hidden: false, sortable: true},
                        { field: 'unitPrice', title: '单价（元)', hidden: false, sortable: true},
                        { field: 'money', title: '金额(元）', hidden: false, sortable: true},
                        { field: 'buyTime', title: '采购时间', hidden: true, sortable: true},
                        { field: 'storagePlace', title: '存放地点', hidden: true, sortable: true},
                        { field: 'keeperId', title: '保管人姓名', hidden: true, sortable: true}
                    ]
                ],
                toolbar: [
                    {
                        id: 'btnAssetItemAdd' + token,
                        text: '添加',
                        iconCls: 'icon-add'
                    },
                    {
                        id: 'btnAssetItemEdit' + token,
                        text: '修改',
                        iconCls: 'icon-edit'
                    },
                    {
                        id: 'btnAssetItemDelete' + token,
                        text: '删除',
                        iconCls: 'icon-cut'
                    }
                ],
                onLoadSuccess: function () {
                    // 初始化事件
                    onClickAssetItemAdd();
                    onClickAssetItemEdit();
                    onClickAssetItemDelete();
                }
            });
        if(btnStatus == null){
            $("#btnAssetItemAdd"+token).remove();
            $("#btnAssetItemEdit"+token).remove();
            $("#btnAssetItemDelete"+token).remove();
        }
    }
    /**
     * 添加事件
     */
    function onClickAssetItemAdd() {
        var buttonId = 'btnAssetItemAdd' + token;
        fw.bindOnClick(buttonId, function(process) {
            initWindowAssetItem({});
        });
    }

    /**
     * 修改事件
     */
    function onClickAssetItemEdit() {
        var butttonId = 'btnAssetItemEdit' + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('assetItem'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + '/oa/assetFixation/AssetItem_load.action?assetItem.id='+selected.id;
                fw.post(url, null, function(data){
                    initWindowAssetItem(data,null);
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
    function onClickAssetItemDelete() {
        var buttonId = 'btnAssetItemDelete' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('assetItem'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + '/oa/assetFixation/AssetItem_delete.action?assetItem.sid='+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('assetItem'+token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 弹窗事件
     * @param data
     */
    function initWindowAssetItem(data){
        var assetApplicationId = document.getElementsByName('assetApplication.id')[0].value;

        data['assetItem.operatorId'] = loginUser.getId();
        data['assetItem.operatorName'] = loginUser.getName();
        data['assetItem.applicationId'] = assetApplicationId;

        var url = WEB_ROOT + '/modules/oa/assetFixation/AssetItem_Save.jsp?token=' + token;
        var windowId = 'assetItemWindow' + token;
        fw.window(windowId, '资产项目',  400, 480, url, function () {
            initMOney("quanity"+token);//数量
            initMOney("unitPrice"+token); //单价

            InitexpectedMoney("quanity"+token);//数量
            InitexpectedMoney("expectedUnitPrice"+token);

            fw.textFormatCurrency('expectedUnitPrice'+token);
            fw.textFormatCurrency('expectedMoney'+token);
            fw.textFormatCurrency('unitPrice'+token);
            fw.textFormatCurrency('money'+token);
            //提交事件
            onClickAssetItemSubmit(assetApplicationId);
            // 加载数据
            fw.formLoad('formAssetItem' + token, data);
        }, null);
    }


    /**
     * 数据提交事件
     */
    function onClickAssetItemSubmit(assetApplicationId) {
        var buttonId = 'btnAssetItemSubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            fw.CurrencyFormatText('expectedUnitPrice'+token);
            fw.CurrencyFormatText('expectedMoney'+token);
            fw.CurrencyFormatText('unitPrice'+token);
            fw.CurrencyFormatText('money'+token);
            var formId = 'formAssetItem' + token;
            var url = WEB_ROOT + '/oa/assetFixation/AssetItem_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('assetItem'+token);
                fw.windowClose('assetItemWindow'+token);
                var moneyURL= WEB_ROOT+"/oa/assetFixation/AssetItem_getMoneys.action?assetItem.applicationId=" + assetApplicationId
                fw.post(moneyURL,null,function(da){
                    $("#moneys"+token).val(da);
                });
            }, function() {
                process.afterClick();
            });
        });
    }
    /**
     * 2017-6-17
     * 设置文本框离开时 计算总金额
     * @param textid
     */
    function initMOney(textid) {
        textid = fw.getObjectFromId(textid);
        $(textid).bind('blur', function () {
            //数量
            var quanity = $("#quanity" + token).val()==""?0:$("#quanity" + token).val();
           //单价

            var unitPrice = fw.getCurrencyFormatValue('unitPrice' + token)==""?0:fw.getCurrencyFormatValue('unitPrice' + token);
            //金额
            $("#money" + token).val(quanity*unitPrice);
        });
    }

    /**
     * 计算预计金额
     * @param textid
     */
    function InitexpectedMoney(textid) {
        textid = fw.getObjectFromId(textid);
        $(textid).bind('blur', function () {
            //预售单价
            var expectedUnitPrice = fw.getCurrencyFormatValue('expectedUnitPrice' + token)==""?0:fw.getCurrencyFormatValue('expectedUnitPrice' + token)
             //数量
            var quanity = $("#quanity" + token).val()==""?0:$("#quanity" + token).val();
             //预售金额
            $("#expectedMoney" + token).val(expectedUnitPrice*quanity);

        });
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
