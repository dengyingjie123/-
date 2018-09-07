/**
 * PositionUserClass.js 脚本对象
 */
var PositionUserClass = function (token) {


    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickPositionUserSearch();
        // 初始化查询重置事件
        onClickPositionUserSearchReset();

        // 初始化表格
        initPositionUserTable();

        // 初始化查询表单
        initPositionUserSearchForm();

        // 初始化选择窗口按钮
        onClcikSelectDone()
    }

    /**
     * 初始化查询表单
     */
    function initPositionUserSearchForm() {
    }


    function onClcikSelectDone() {
        fw.bindOnClick('btnSelect'+token, function(press) {
            var strTableId = 'PositionUserTable' + token;
            var data = $('#' + strTableId).datagrid('getSelected');
            if (fw.checkIsFunction(callback4Select)) {
                callback4Select(data);
            }
            fwCloseWindow('SelectWindow'+token);
        });
    }

    // 构造初始化表格脚本
    function initPositionUserTable() {
        var strTableId = 'PositionUserTable' + token;

        var url = WEB_ROOT + '/system/PositionUser_showList.action';

        $('#' + strTableId).datagrid({
            title: '用户部门归属列表',
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
//            sortOrder: 'desc',//排序方法 默认
//            sortName: 'sid',//排序的列
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
                    { field: 'id', title: 'ID',hidden:true},
                    { field: 'positionId', title: 'positionId',hidden:true},
                    { field: 'userId', title: 'userId',hidden:true},
                    { field: 'states', title: 'states',hidden:true},
                    { field: 'userName', title: '用户名称',hidden:false},
                    { field: 'departmentName', title: '部门名称',hidden:false},
                    { field: 'positionName', title: '岗位名称',hidden:false},
                    { field: 'statesName', title: '默认归属',hidden:false}
                ]
            ],
            toolbar: [
//                {
//                    id: 'btnPositionUserAdd' + token,
//                    text: '添加',
//                    iconCls: 'icon-add'
//                },
                {
                    id: 'btnPositionUserEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                }
//                {
//                    id: 'btnPositionUserDelete' + token,
//                    text: '删除',
//                    iconCls: 'icon-cut'
//                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
//                onClickPositionUserAdd();
                onClickPositionUserEdit();
//                onClickPositionUserDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickPositionUserAdd() {
        var buttonId = 'btnPositionUserAdd' + token;
        fw.bindOnClick(buttonId, function(process) {
            initPositionUserWindow({});
        });
    }

    /**
     * 修改事件
     */
    function onClickPositionUserEdit() {
        var buttonId = 'btnPositionUserEdit' + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('PositionUserTable'+token, function(selected){
                process.beforeClick();
                    var id = selected.id;
                    var url = WEB_ROOT + '/system/PositionUser_loadPositionUser.action?positionUserPO.id=' + id;
                    fw.post(url, null, function (data) {
                        data['positionUserPO.id'] = selected.id;
                        data['positionUserPO.userId'] = selected.userId;
                        data['positionUserVO.departmentId'] = selected.departmentId;
                        data['positionUserPO.positionId'] = selected.positionId;
                        data['positionUserPO.states'] = selected.states;
                        data['positionUserVO.departmentName'] = selected.departmentName;
                        data['positionUserVO.positionName'] = selected.positionName;
                        data['positionUserVO.statesName'] = selected.statesName;
                        data['positionUserVO.userName'] = selected.userName;
                        initPositionUserWindow(data);
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
//    function onClickPositionUserDelete() {
//        var buttonId = 'btnPositionUserDelete' + token;
//        fw.bindOnClick(buttonId, function(process) {
//            fw.datagridGetSelected('PositionUserTable'+token, function(selected){
//                process.beforeClick();
//                fw.confirm('删除确认', '是否确认删除数据？', function(){
//
//                    var url = ActionUrl + '/PositionUser_delete.action?positionUser.sid='+selected.sid;
//                    fw.post(url, null, function(data) {
//                        process.afterClick();
//                        fw.datagridReload('PositionUserTable'+token);
//                    }, null);
//                }, function(){
//                    process.afterClick();
//                });
//            });
//        });
//    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initPositionUserWindow(data) {

        // fw.alertReturnValue(data);

        data['positionUserPO.operatorId'] = loginUser.getId();
        data['positionUserPO.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/system/PositionUser_Save.jsp?token=' + token;
        var windowId = 'PositionUserWindow' + token;
        fw.window(windowId, '设置默认归属', 320, 230, url, function () {

            //提交事件
            onClickPositionUserSubmit();
            fw.combotreeLoadFromClass('statesName'+token, 'com.youngbook.entity.po.system.PositionUserPO', 'search_States');
            // 加载数据
            fw.formLoad('formPositionUser' + token, data);

        });
    }

    /**
     * 数据提交事件
     */
    function onClickPositionUserSubmit() {
        var buttonId = 'btnPositionUserSubmit' + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = 'formPositionUser' + token;
            fw.confirm('更改提示', '归属状态已改变，是否更改？', function() {
            var url = WEB_ROOT + '/system/PositionUser_updatePositionUser.action';
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload('PositionUserTable'+token);
                fw.windowClose('PositionUserWindow'+token);
            }, function() {
                process.afterClick();
            });
            }, function(){
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickPositionUserSearch() {
        var buttonId = 'btnPositionUserSearchSubmit' + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = 'PositionUserTable'+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            params["PositionUserVO.UserName"] = $('#Search_UserName'+token).val();
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickPositionUserSearchReset() {
        var buttonId = 'btnPositionUserSearchReset' + token;
        fw.bindOnClick(buttonId, function(process) {
            // 清空事件文本框
            $('#Search_UserName'+ token).val('');
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