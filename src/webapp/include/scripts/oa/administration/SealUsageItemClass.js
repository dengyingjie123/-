/**
 * SealUsageItemClass.js 脚本对象\
 * 用章类型脚本对象
 * @parame btnstatus 列表按钮状态
 * @parsme token
 * @parsme applicationId 申请编号
 */
var SealUsageItemClass = function (token, btnstatus,applicationId) {


    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickSealUsageItemSearch();
        // 初始化查询重置事件
        onClickSealUsageItemSearchReset();

        // 初始化表格
        initSealUsageItemTable();

        // 初始化查询表单
        initSealUsageItemSearchForm();

    }

    /**
     * 初始化查询表单
     */
    function initSealUsageItemSearchForm() {

    }


    // 构造初始化表格脚本
    function initSealUsageItemTable() {
        var strTableId = 'SealUsageItemTable' + token;

        var url = WEB_ROOT + '/oa/administration/SealUsageItem_list.action';

        $('#' + strTableId).datagrid({
            //title: '用章类型',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
                "sealUsageItem.applicationId":applicationId
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [3, 6, 9],
            pageSize: 3,
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
                    { field: 'sid', title: 'sid', hidden: true},
                    { field: 'id', title: 'id', hidden: true},
                    { field: 'state', title: 'state', hidden: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true},
                    { field: 'applicationId', title: '申请编号', hidden: true},
                    { field: 'sealId', title: '印章编号', hidden: true},
                    { field: 'sealName', title: '印章名称', hidden: false},
                    { field: 'topies', title: '印章份数', hidden: false}
                ]
            ],
            toolbar: [
                {
                    id: 'btnSealUsageItemAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnSealUsageItemEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnSealUsageItemDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickSealUsageItemAdd();
                onClickSealUsageItemEdit();
                onClickSealUsageItemDelete();
            }

        });
        if (btnstatus == "check" || btnstatus == "upload" ||btnstatus == "applay") {
            $("#btnSealUsageItemAdd" + token).remove();
            $("#btnSealUsageItemEdit" + token).remove();
            $("#btnSealUsageItemDelete" + token).remove();
        }
    }

    /**
     * 添加事件
     */
    function onClickSealUsageItemAdd() {
        var buttonId = 'btnSealUsageItemAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            var add="add";
            initSealUsageItemWindow({},add);
        });
    }

    /**
     * 修改事件
     */
    function onClickSealUsageItemEdit() {
        var buttonId = 'btnSealUsageItemEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SealUsageItemTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/administration/SealUsageItem_load.action?sealUsageItem.id=' + selected.id;
                fw.post(url, null, function (data) {
                    var edit ='edit'
                    initSealUsageItemWindow(data,edit);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 删除事件
     */
    function onClickSealUsageItemDelete() {
        var buttonId = 'btnSealUsageItemDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('SealUsageItemTable' + token, function (selected) {
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function () {

                    var url = WEB_ROOT + '/oa/administration/SealUsageItem_delete.action?sealUsageItem.sid=' + selected.sid;
                    fw.post(url, null, function () {
                        process.afterClick();
                        fw.datagridReload('SealUsageItemTable' + token);
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
     * @param windowStatus 窗口状态
     */
    function initSealUsageItemWindow(data,windowStatus) {

        // fw.alertReturnValue(data);

        data['sealUsageItem.operatorId'] = loginUser.getId();
        data['sealUsageItem.operatorName'] = loginUser.getName();
        //用章申请ID
        data['sealUsageItem.applicationId'] = applicationId;

        var url = WEB_ROOT + '/modules/oa/administration/SealUsageItem_Save.jsp?token=' + token;
        var windowId = 'SealUsageItemWindow' + token;
        fw.window(windowId, '窗口', 400, 200, url, function () {
            //窗口状态为添加时
            if(windowStatus=='add'){
                //加载用章选择项
                fw.getComboTreeFromKV("sealId" + token, 'oa_SaleUsageWFA', 'k', "-2");
            }
            //其他情况
            else{

                //加载用章选择项
                fw.getComboTreeFromKV("sealId" + token, 'oa_SaleUsageWFA', 'k', data["sealUsageItem.sealId"]);
            }

            //提交事件
            onClickSealUsageItemSubmit();

            // 加载数据
            fw.formLoad('formSealUsageItem' + token, data);

        });
    }

    /**
     * 数据提交事件
     */
    function onClickSealUsageItemSubmit() {
        var buttonId = 'btnSealUsageItemSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            //将用章名字加入参数
            var sealText = fw.getFormValue("sealId" + token, fw.type_form_combotree, fw.type_get_text);
            $("#sealName" + token).val(sealText);
            var formId = 'formSealUsageItem' + token;
            var url = WEB_ROOT + '/oa/administration/SealUsageItem_insertOrUpdate.action';
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('SealUsageItemTable' + token);
                fw.windowClose('SealUsageItemWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickSealUsageItemSearch() {
        var buttonId = 'btnSealUsageItemSearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'SealUsageItemTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;


            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickSealUsageItemSearchReset() {
        var buttonId = 'btnSealUsageItemSearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
        });
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            //判断用章表数据是否存在
            if(applicationId == "" || applicationId == undefined || applicationId== null){
                fw.alert("警告！","数据错误 请于管理员联系");
                return ;
            }
            return initAll();
        }
    };
};
