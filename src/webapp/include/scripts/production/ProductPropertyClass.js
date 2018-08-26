// -----------------------------------------------------------------------------
// 构造JS
/**
 * ProductPropertyClass.js 脚本对象
 *
 *
 *
 * 示例：弹出选择窗口调用示例
 * function onCleckSelectSomething() {
 *     $('#somethingName'+token).bind('click',function(){
 *         using(SCRIPTS_ROOT+'/production/ProductPropertyClass.js', function () {
 *             var windowToken = token + "-window";
 *             var somethingClass = new SomethingClass(windowToken);
 *             somethingClass.initModuleWithSelect(function(data) {
 *                 // fw.alertReturnValue(data);
 *                 $('#somethingName'+token).val(data.name);
 *                 $('#somethingId'+token).val(data.id);
 *             });
 *         });
 *     });
 * }
 */
var ProductPropertyClass = function (token) {


    var ActionUrl = WEB_ROOT  + '/production';
    var WebPageUrl = WEB_ROOT + '/modules/production';
    var callback4Select = undefined;


    var _mainIdAsDetail = undefined;
    var _mainNameAsDetail = undefined;


    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickProductPropertySearch();
        // 初始化查询重置事件
        onClickProductPropertySearchReset();

        // 初始化表格
        initProductPropertyTable();

        // 初始化查询表单
        initProductPropertySearchForm();

        // 初始化选择窗口按钮
        onClcikSelectDone()
    }

    /**
     * 初始化查询表单
     */
    function initProductPropertySearchForm() {
    }


    function onClcikSelectDone() {
        fw.bindOnClick('btnSelect'+token, function(press) {
            var strTableId = 'ProductPropertyTable' + token;
            var data = $('#' + strTableId).datagrid('getSelected');
            if (fw.checkIsFunction(callback4Select)) {
                callback4Select(data);
            }
            fwCloseWindow('SelectWindow'+token);
        });
    }

    // 构造初始化表格脚本
    function initProductPropertyTable(productId) {
        var strTableId = 'ProductPropertyTable' + token;
        var tableTitle = '产品属性';
        if (!fw.checkIsTextEmpty(_mainIdAsDetail)) {
            tableTitle = undefined;
        }

        var url = ActionUrl + '/ProductProperty_getPropertiesByProductId.action';

        var queryParams = {};
        // queryParams
        queryParams = fw.jsonJoin(queryParams,{'productProperty.productId':productId}, true);

        $('#' + strTableId).datagrid({
            title:tableTitle,
            url: url,
            queryParams: queryParams,
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
                    { field: 'sid', title: 'sid',hidden:true},
                    { field: 'id', title: 'id',hidden:true},
                    { field: 'state', title: 'state',hidden:true},
                    { field: 'operatorId', title: 'operatorId',hidden:true},
                    { field: 'operateTime', title: 'operateTime',hidden:true},
                    { field: 'productId', title: '产品编号',hidden:true},
                    { field: 'typeId', title: '类型',hidden:true},
                    { field: 'typeName', title: '类型名称',hidden:false},
                    { field: 'value', title: '值',formatter: function(value,row,index) {
                        return fw.stringSubString(row['value'], 0, 100);
                    }},
                    { field: 'valueType', title: '值类型',hidden:true}
                ]
            ],
            toolbar: [
                {
                    id: 'btnProductPropertyAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnProductPropertyEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnProductPropertyDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickProductPropertyAdd();
                onClickProductPropertyEdit();
                onClickProductPropertyDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickProductPropertyAdd() {


        var buttonId = 'btnProductPropertyAdd' + token;
        fw.bindOnClick(buttonId, function(process) {

            var productId = fw.getTextValue('id' + token);
            var productionName = fw.getTextValue('productionName' + token);
            if (fw.checkIsTextEmpty(productId)) {
                fw.alert('警告','请先保存产品信息');
                return;
            }


            var data = {'productProperty.productName':productionName,'productProperty.productId':productId};
            initProductPropertyWindow(data);
        });
    }

    /**
     * 修改事件
     */
    function onClickProductPropertyEdit() {
        var buttonId = 'btnProductPropertyEdit' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProductPropertyTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id

                var url = ActionUrl + '/ProductProperty_load.action?productProperty.id='+selected.id;
                fw.post(url, null, function(data){

                    var productionName = fw.getTextValue('productionName' + token);
                    data['productProperty.productName'] = productionName;

                    initProductPropertyWindow(data);
                    process.afterClick();
                }, function() {
                    process.afterClick();
                });
            });
        });
    }

    function onClickbtnProductPropertyCKEditor() {
        //
        var buttonId = "btnProductPropertyCKEditor" + token;
        fw.bindOnClick(buttonId, function(process) {
            // fw.initCKEditor("value" + token);

            using(SCRIPTS_ROOT + '/system/CKEditorClass.js', function () {
                var ckeditorClass = new CKEditorClass(token);
                ckeditorClass.init('ckeditor', 'This is content', function(data) {
                    // alert('callback-function: ' + data);
                    fw.setFormValue('value'+token, data, fw.type_form_text, data);
                });
            });

        });
    }

    /**
     * 删除事件
     */
    function onClickProductPropertyDelete() {
        var buttonId = 'btnProductPropertyDelete' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProductPropertyTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){

                    var url = ActionUrl + '/ProductProperty_delete.action?productProperty.sid='+selected.sid;
                    fw.post(url, null, function(data) {
                        process.afterClick();
                        fw.datagridReload('ProductPropertyTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initProductPropertyWindow(data) {

        // fw.alertReturnValue(data);


        /**
         * 默认尺寸
         */
        var defaultWidth = 410;
        var defaultHeight = 200;

        // fw.alertReturnValue(data);


        data['productProperty.operatorId'] = loginUser.getId();
        data['productProperty.operatorName'] = loginUser.getName();

        var url = WebPageUrl + '/ProductProperty_Save.jsp?token=' + token;
        var windowId = 'ProductPropertyWindow' + token;
        fw.window(windowId, '窗口', defaultWidth, defaultHeight, url, function () {
            //提交事件
            onClickProductPropertySubmit(token);

            fw.getComboTreeFromKV('typeId'+token, 'Production_ProductPropertyType', null, data['productProperty.typeId']);

            onClickbtnProductPropertyCKEditor();

            // 加载数据
            fw.formLoad('formProductProperty' + token, data);

            // 一对多的实现 结束  ////////////////////////////////
        });
    }

    /**
     * 数据提交事件
     */
    function onClickProductPropertySubmit() {
        var buttonId = 'btnProductPropertySubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = 'formProductProperty' + token;

            var url = ActionUrl + '/ProductProperty_newProperty.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('ProductPropertyTable'+token);
                fw.windowClose('ProductPropertyWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickProductPropertySearch() {
        var buttonId = 'btnProductPropertySearchSubmit' + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = 'ProductPropertyTable'+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;




            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickProductPropertySearchReset() {
        var buttonId = 'btnProductPropertySearchReset' + token;
        fw.bindOnClick(buttonId, function(process) {
            // 清空时间文本框
        });
    }
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initProductPropertyTable:function(productId) {
            return initProductPropertyTable(productId);
        },
        initModuleWithMainInfo:function(id, name) {
            _mainIdAsDetail = id;
            _mainNameAsDetail = name;
            return initAll();
        },
        initModuleWithSelect:function(callback4SelectIn) {
            var url =  WebPageUrl + '/ProductProperty_Select.jsp?token='+token;
            var selectionWindowId = 'SelectWindow' + token;
            fw.window(selectionWindowId, '选择窗口',1000, 500, url, function() {
                callback4Select = callback4SelectIn;
                initAll();
                $('#ProductPropertyTable'+token).datagrid({toolbar:[]});
            }, null);
        }
    };
};

