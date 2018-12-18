/**
 * Created by 张舜清 on 2015/4/10.
 */
var UserClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickUserSearch();
        // 充值查询事件
        onClickUserReset();
        // 初始化列表
        initUserTable();
    }

    /**
     * 初始化列表事件
     */
    function initUserTable(){
        var strTableId = 'UserTable'+token;
        var url = WEB_ROOT + "/system/User_list.action";
        $('#'+strTableId).datagrid({
            title: '用户信息',
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
                    return data;
                }
                catch(e) {
                }
            },
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条
                {field:'ck',checkbox:true},
                { field: 'referralCode', title: '推荐码'},
                { field: 'name', title: '名称'},
                { field: 'staffStatus', title: '状态'},
                { field: 'mobile', title: '手机号'},
                { field: 'weChatId', title: '微信号'},
                { field: 'email', title: '邮箱'}
            ]],
            columns: [[
                // 隐藏属性
                { field: 'id', title: 'id',hidden:true },
                { field: 'sid', title: 'sid',hidden:true },
                { field: 'state', title: 'state',hidden:true },
                { field: 'operatorId', title: 'operatorId',hidden:true },
                { field: 'password', title: 'password',hidden:true },
                { field: 'staffCode', title: '员工编号'},
                { field: 'genderName', title: '性别'},
                { field: 'idnumber', title: '身份证'},
                { field: 'positionType', title: '岗位'},
                { field: 'address', title: '地址'},
                { field: 'birthday', title: '生日'},
                { field: 'jointime', title: '入职时间'},
                { field: 'leftTime', title: '离职时间',
                    formatter: function(value) {
                        /**
                         * 徐明煜
                         * 保留日期，除去小时时间
                         * 2018.12.18
                         */
                        return value.substr(0,10)
                    }
                },
                { field: 'operateTime', title: '操作时间'}
            ]],
            onLoadSuccess:function() {
                //添加事件跳转
                onUserAdd();
                // 删除事件跳转
                onUserDelete();
                //修改事件跳转
                onUserEdit();
                //离职时间跳转
                onUserDismiss();
            }
        });
    }

    /**
     * 添加事件
     */
    function onUserAdd(){
        var buttonId = "btnUserAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 初始化表单数据为空
            initUserWindow({},null);
        });
    }

    /**
     * 删除事件
     */
    function onUserDelete() {
        var buttonId = "btnUserDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('UserTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    // 更改url
                    var url = WEB_ROOT + "/system/User_delete.action?user.id="+selected.id;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('UserTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }

    /**
    * @description 员工离职
    * @author 徐明煜
    * @date 2018/12/13 13:58
    * @param null
    * @return
    * @throws
    */
    function onUserDismiss() {
        var buttonId = "btnUserDismiss" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('UserTable'+token, function(selected){
                fw.confirm('员工离职确认', '是否确认该员工离职？', function(){
                    // 更改url
                    var url = WEB_ROOT + "/system/User_dismiss.action?userId="+selected.id;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('UserTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 修改事件
     */
    function onUserEdit(){
        var butttonId = "btnUserEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('UserTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/system/User_load.action?user.id="+id;
                fw.post(url, null, function(data){
                    // 增加其他传入参数
                    data["name"]=selected.name;
                    data["user.staffCode"] = selected.staffCode;
                    data["user.password"]="";
                    initUserWindow(data,1);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }

    /**
     * 弹出窗体
     * 修个人:姚章鹏
     * 修改用户管理，修改的时候密码会被清空掉
     *
     */
    function initUserWindow(data,markID){
        data["operatorId"] = loginUser.getId();
        data["operatorName"] = loginUser.getName();
        // 更改url地址
        var url =  WEB_ROOT + "/modules/system/User_Save.jsp?token="+token;
        var windowId = "UserWindow" + token;
        // 更改窗口名称
        // 更改窗口大小
        fw.window(windowId, '用户信息', 550, 400, url, function() {
            // 初始化类型combotree

            // 初始化控件位置
            fw.getComboTreeFromKV('positionTypeId'+ token, 'System_PositionType','k',fw.getMemberValue(data,'user.positionTypeId'));
            fw.getComboTreeFromKV('genderName'+token, 'Sex', 'k', fw.getMemberValue(data, 'user.gender'));
            fw.getComboTreeFromKV('status'+token, 'System_UserStatus', 'k', fw.getMemberValue(data, 'user.status'));
            fw.getComboTreeFromKV('industry'+token, 'Career', 'k', fw.getMemberValue(data, 'user.industry'));
            fw.getComboTreeFromKV('userType'+token, 'sale_userType', 'k', fw.getMemberValue(data, 'user.userType'));
            // 初始化表单提交事件
           onClickUserSubmit();
            // 加载数据
            fw.formLoad('formUser'+token, data);
        }, null);
    }

    /**
     * 数据提交事件
     */
    function onClickUserSubmit(){
        var buttonId = "btnUserSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formUser" + token;

            // 更改url
            var url = WEB_ROOT + "/system/User_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("UserTable"+token);
                fw.windowClose('UserWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 初始化查询
     */
    function onClickUserSearch(){
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId,function(){
            var strTableId = "UserTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            //获取名称
            params["userVO.name"] = $('#search_user_name'+token).val();
            //获取电话
            params["userVO.mobile"] = $('#search_user_mobile'+ token).val();
            //获取身份证
            params["userVO.idnumber"] = $('#search_user_idnumber' + token).val();
            $( '#' + strTableId).datagrid('load');
        })
    }

    /**
     * 重置查询事件
     */
    function onClickUserReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $('#search_user_name' + token).val('');
            $('#search_user_mobile' + token).val('');
            $('#search_user_idnumber' + token).val('');
        });
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    }

}

