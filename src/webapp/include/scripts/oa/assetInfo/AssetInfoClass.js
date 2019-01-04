/**
 * Created by Administrator on 2015/6/2.
 */
/**姚章鹏
 *
 * AssetInfoClass.js 脚本对象
 */
var AssetInfoClass = function (token) {


    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickAssetInfoSearch();
        // 初始化查询重置事件
        onClickAssetInfoSearchReset();

        // 初始化表格
        initAssetInfoTable();

        // 初始化查询表单
        initAssetInfoSearchForm();

//        // 初始化选择窗口按钮
//        onClcikSelectDone()
    }

    /**
     * 初始化查询表单
     */
    function initAssetInfoSearchForm() {

        var url = WEB_ROOT + "/system/Department_list.action";
        initStatusTree(url, '#Search_DepartmentId' + token, -2);//主办部门
    }

    /**
     * 初始化下拉列表项
     */
        function initStatusTree(url, combotreeId, selectIndexId) {
        fw.combotreeLoad(combotreeId, url, selectIndexId);
    }


    // 构造初始化表格脚本
    function initAssetInfoTable() {
        var strTableId = 'AssetInfoTable' + token;
        // todo cm 更改url
        var url = WEB_ROOT + '/oa/assetInfo/AssetInfo_list.action';

        $('#' + strTableId).datagrid({
            title: '固定资产信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: 'sid',//排序的列
            loadFilter: function (data) {
                try {
                    //获取数据 返回的Value
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
                    { field: 'sid', title: 'sid', hidden: true, width: 100},
                    { field: 'id', title: 'id', hidden: true},
                    { field: 'state', title: 'state', hidden: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true},
                    { field: 'departmentId', title: '所属部门', hidden: true},
                    { field: 'departmentName', title: '所属部门', hidden: false, width: 100, sortable: true},
                    { field: 'name', title: '名称', hidden: false, width: 100, sortable: true},
                    { field: 'keeperId', title: '保管人', hidden: false, width: 100, sortable: true},
                    { field: 'purpose', title: '申购用途', hidden: true},
                    { field: 'specification', title: '规格型号', hidden: false, width: 100, sortable: true},
                    { field: 'quanity', title: '数量', hidden: false, width: 100, sortable: true},
                    { field: 'unitPrice', title: '单价', hidden: false, width: 100, sortable: true},
                    { field: 'money', title: '金额', hidden: false, width: 100, sortable: true},
                    { field: 'buyTime', title: '采购时间', hidden: false, width: 100, sortable: true},
                    { field: 'storagePlace', title: '存放地点', hidden: false, width: 100, sortable: true}

                ]
            ],
            toolbar: [
                {
                    id: 'btnAssetInfoAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnAssetInfoEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                }, {
                    id: 'btnAssetInfoPrint' + token,
                    text: '打印',
                    iconCls: 'icon-print'
                },
                {
                    id: 'btnAssetInfoDelete' + token,
                    text: '删除',
                    iconCls: 'icon-clear'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickAssetInfoAdd();
                onClickAssetInfoEdit();
                onClickAssetInfoPrint();
                onClickAssetInfoDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickAssetInfoAdd() {
        var buttonId = 'btnAssetInfoAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initAssetInfoWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickAssetInfoEdit() {
        var buttonId = 'btnAssetInfoEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('AssetInfoTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                // todo cm 修改url
                var url = WEB_ROOT + '/oa/assetInfo/AssetInfo_load.action?assetInfo.id=' + selected.id;
                fw.post(url, null, function (data) {
                    initAssetInfoWindow(data);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }
    /*
    * 修改：周海鸿
    * 时间：2015-7-20
    * 内容：添加打印*/
    function onClickAssetInfoPrint() {
        var buttonId = 'btnAssetInfoPrint' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('AssetInfoTable' + token, function (selected) {
                //判断数据是否进入业务流状态

                process.beforeClick();

                var url =WEB_ROOT+"/modules/oa/modelsFiles/AssetInfoModels.jsp?id="+selected.id+"&token=" + token;
                window.open(url);

                process.afterClick();
            });
        });
    }

    /**
     * 删除事件
     */
    function onClickAssetInfoDelete() {
        var buttonId = 'btnAssetInfoDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('AssetInfoTable' + token, function (selected) {
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    // todo cm 修改url
                    var url = WEB_ROOT + '/oa/assetInfo/AssetInfo_delete.action?assetInfo.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('AssetInfoTable' + token);
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initAssetInfoWindow(data) {
        data['assetInfo.operatorId'] = loginUser.getId();
        data['assetInfo.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/oa/assetInfo/AssetInfo_Save.jsp?token=' + token;
        var windowId = 'AssetInfoWindow' + token;
        fw.window(windowId, '固定资产信息', 610, 480, url, function () {
            fw.textFormatCurrency('unitPrice'+token);

            var url = WEB_ROOT + "/system/Department_list.action";
            initStatusTree(url, '#departmentId' + token, data["assetInfo.departmentId"]);//主办部门
            //提交事件
            onClickAssetInfoSubmit();
            //初始化文本框离开焦点事件
            initmoney();


            // 加载数据
            fw.formLoad('formAssetInfo' + token, data);

        });
    }

    /**
     * 数据提交事件
     */
    function onClickAssetInfoSubmit() {
        var buttonId = 'btnAssetInfoSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.CurrencyFormatText('unitPrice'+token);
            fw.CurrencyFormatText('money'+token);
            var formId = 'formAssetInfo' + token;
            var url = WEB_ROOT + '/oa/assetInfo/AssetInfo_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('AssetInfoTable' + token);
                fw.windowClose('AssetInfoWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickAssetInfoSearch() {
        var buttonId = 'btnAssetInfoSearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'AssetInfoTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params['assetInfoVO.name'] = $('#Search_Name' + token).val();
            params['assetInfoVO.specification'] = $('#Search_Specification' + token).val();
           // params['assetInfoVO.unitPrice'] = $('#Search_UnitPrice' + token).val();
            params['assetInfoVO_unitPrice_Start'] = $('#Search_UnitPrice_Start' + token).val();
            params['assetInfoVO_unitPrice_End'] = $('#Search_UnitPrice_End' + token).val();
          //  params['assetInfoVO.money'] = $('#Search_Money' + token).val();
            params['assetInfoVO_money_Start'] = $('#Search_Money_Start' + token).val();
            params['assetInfoVO_money_End'] = $('#Search_Money_End' + token).val();
            params['assetInfoVO_buyTime_Start'] = fw.getFormValue('Search_BuyTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params['assetInfoVO_buyTime_End'] = fw.getFormValue('Search_BuyTime_End' + token, fw.type_form_datebox, fw.type_get_value);
           // params['assetInfoVO.buyTime'] = $('#Search_BuyTime' + token).val();
            params['assetInfoVO.storagePlace'] = $('#Search_StoragePlace' + token).val();
            params['assetInfoVO.keeperId'] = $('#Search_KeeperId' + token).val();

            params['assetInfoVO.departmentId'] = fw.getFormValue('Search_DepartmentId' + token,fw.type_form_combotree,fw.type_get_value);

            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickAssetInfoSearchReset() {
        var buttonId = 'btnAssetInfoSearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
            $('#Search_Name' + token).val('');
//            $('#Search_DepartmentId' + token).val('');
            $('#Search_Specification' + token).val('');
            $('#Search_UnitPrice' + token).val('');
            $('#Search_UnitPrice_Start' + token).val('');
            $('#Search_UnitPrice_End' + token).val('');
            $('#Search_Money' + token).val('');
            $('#Search_Money_Start' + token).val('');
            $('#Search_Money_End' + token).val('');
            $('#Search_BuyTime_Start' + token).datebox('setValue', '');
            $('#Search_BuyTime_End' + token).datebox('setValue', '');
            $('#Search_BuyTime' + token).val('');
            $('#Search_StoragePlace' + token).val('');
            $('#Search_KeeperId' + token).val('');

            fw.combotreeClear('Search_DepartmentId'+token);
        });
    }

    /**
     * 初始计算金额
     */
    function initmoney() {
        var textID = "#unitPrice" + token;
        var moneyID="#money"+token;
        $(textID).bind("blur", function () {
            var unitPrice = fw.getCurrencyFormatValue("unitPrice" + token);
            var quanity = $("#quanity" + token).val();
            $(moneyID).val(unitPrice * quanity);
            fw.textFormatCurrency('money'+token);
        });
        var textID2 = "#quanity" + token;
        $(textID2).bind("blur", function () {
            var unitPrice = fw.getCurrencyFormatValue("unitPrice" + token);
            var quanity = $("#quanity" + token).val();
            $(moneyID).val(unitPrice * quanity);
            fw.textFormatCurrency('money'+token);
        });
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
//        initModuleWithSelect:function(callback4SelectIn) {
//            var url =  WEB_ROOT + '/modules/example/AssetInfo_Select.jsp?token='+token;
//            var selectionWindowId = 'SelectWindow' + token;
//            fw.window(selectionWindowId, '选择窗口',1000, 500, url, function() {
//                callback4Select = callback4SelectIn;
//                initAll();
//                $('#AssetInfoTable'+token).datagrid({toolbar:[]});
//            }, null);
//        }
    };
};
