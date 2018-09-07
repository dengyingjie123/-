/**
 * Created by 张舜清 on 2015/4/10.
 */
var SalemanOuterClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickSalemanOuterSearch();
        // 充值查询事件
        onClickSalemanOuterReset();
        // 初始化列表
        ininSalemanOuterTable();
    }

    /**
     * 初始化列表事件
     */
    function ininSalemanOuterTable(){
        var strTableId = 'SalemanOuterTable'+token;
        var url = WEB_ROOT + "/system/User_list.action?user.userType=1";
        $('#'+strTableId).datagrid({
            title: '用户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
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
                {field:'ck',checkbox:true}
            ]],
            columns: [[
                // 隐藏属性
                { field: 'id', title: 'id',hidden:true },
                { field: 'sid', title: 'sid',hidden:true },
                { field: 'state', title: 'state',hidden:true },
                { field: 'operatorId', title: 'operatorId',hidden:true },
                { field: 'operateTime', title: 'operateTime',hidden:true },
                { field: 'password', title: 'password',hidden:true },
                { field: 'staffCode', title: '员工编号/推荐码'},
                { field: 'name', title: '名称'},
                { field: 'genderName', title: '性别'},
                { field: 'idnumber', title: '身份证'},
                { field: 'positionType', title: '岗位'},
                { field: 'staffStatus', title: '状态'},
                { field: 'mobile', title: '手机号'},
                { field: 'email', title: '邮箱'},
                { field: 'address', title: '地址'},
                { field: 'birthday', title: '生日'},
                { field: 'jointime', title: '入职时间'},
                { field: 'leftTime', title: '离职时间'},
                { field: 'referralCode', title: '推荐码'},
                { field: 'industryType', title: '行业'},
                { field: 'UserTypeStr', title: '销售类别'},
                { field: 'saleLevel', title: '销售级别',hidden:true}
            ]],
            onLoadSuccess:function() {
                //添加事件跳转
                onSalemanOuterAdd();
                // 删除事件跳转
                onSalemanOuterDelete();
                //修改事件跳转
                onSalemanOuterEdit();
            }
        });
    }

    /**
     * 添加事件
     */
    function onSalemanOuterAdd(){
        var buttonId = "btnSalemanOuterAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 初始化表单数据为空
            initSalemanOuterWindow({},null);
        });
    }

    /**
     * 删除事件
     */
    function onSalemanOuterDelete() {
        var buttonId = "btnSalemanOuterDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('SalemanOuterTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    // 更改url
                    var url = WEB_ROOT + "/system/User_delete.action?user.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('SalemanOuterTable'+token);
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
    function onSalemanOuterEdit(){
        var butttonId = "btnSalemanOuterEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('SalemanOuterTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/system/User_load.action?user.id="+id;
                fw.post(url, null, function(data){
                    // 增加其他传入参数
                    data["name"]=selected.name;
                    data["user.staffCode"] = selected.staffCode;
                    data["user.password"]="";
                    initSalemanOuterWindow(data,1);
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
    function initSalemanOuterWindow(data,markID){
        data["operatorId"] = loginUser.getId();
        data["operatorName"] = loginUser.getName();
        // 更改url地址
        var url =  WEB_ROOT + "/modules/customer/SalemanOuter_Save.jsp?token="+token;
        var windowId = "SalemanOuterWindow" + token;
        // 更改窗口名称
        // 更改窗口大小
        fw.window(windowId, '用户信息', 550, 380, url, function() {
            // 初始化类型combotree

            if(markID == 1) {
                var company =  data['user.staffCode'];
                var num = company.substring(0,2);
                fw.combotreeLoadFromClass('company'+token, 'com.youngbook.common.config.Config', 'Search_Company',num);
                $("#company" + token).combotree({disabled: true});
                //data["editType"]="editType"

            }else{
                fw.combotreeLoadFromClass('company'+token, 'com.youngbook.common.config.Config', 'Search_Company');
            }
            // 初始化控件位置
            fw.getComboTreeFromKV('positionTypeId'+ token, 'System_PositionType','k',fw.getMemberValue(data,'user.positionTypeId'));
            fw.getComboTreeFromKV('genderName'+token, 'Sex', 'k', fw.getMemberValue(data, 'user.gender'));
            fw.getComboTreeFromKV('status'+token, 'System_UserStatus', 'k', fw.getMemberValue(data, 'user.status'));
            fw.getComboTreeFromKV('industry'+token, 'Career', 'k', fw.getMemberValue(data, 'user.industry'));
            fw.getComboTreeFromKV('userType'+token, 'sale_userType', 'k', fw.getMemberValue(data, 'user.userType'));
            // 初始化表单提交事件
            onClickSalemanOuterSubmit();
            // 加载数据
            fw.formLoad('formSalemanOuter'+token, data);
        }, null);
    }

    /**
     * 数据提交事件
     */
    function onClickSalemanOuterSubmit(){
        var buttonId = "btnSalemanOuterSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formSalemanOuter" + token;

            // 更改url
            var url = WEB_ROOT + "/system/User_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("SalemanOuterTable"+token);
                fw.windowClose('SalemanOuterWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 初始化查询
     */
    function onClickSalemanOuterSearch(){
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId,function(){
            var strTableId = "SalemanOuterTable"+token;
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
    function onClickSalemanOuterReset() {
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

