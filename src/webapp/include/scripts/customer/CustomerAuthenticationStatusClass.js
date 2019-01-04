/**
 * Created by Administrator on 2015/4/7.
 */
var CustomerAuthenticationStatusClass = function (token, obj) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化下拉列表
        initMobileStatusTree(null, "-2");
        initEmailStatusTree(null, "-2");
        initQAStatusTree(null, "-2");
        initAccountStatusTree(null, "-2");

        // 初始化查询事件
        onClickCustomerAuthenticationStatusSearch();
        // 初始化查询重置事件
        onClickCustomerAuthenticationStatusSearchReset();
        // 初始化表格
        initCustomerAuthenticationStatusTable();
    }


    //初始化表格
    function initCustomerAuthenticationStatusTable() {
        var strTableId = 'CustomerAuthenticationStatusTable' + token;
        var url = WEB_ROOT + "/customer/CustomerAuthenticationStatus_list.action";
        $('#' + strTableId).datagrid({
            title: '客户认证信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            loadFilter: function (data) {
                //alert(JSON.stringify(data));
                //alert(data.code);
                try {
                    data = fw.dealReturnObject(data);
                    //fw.alertReturnValue(data);
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    { field: 'customerName', title: '客户名称' }
                ]
            ],
            columns: [
                [
                    { field: 'mobile_Status', title: '手机' },
                    { field: 'mobileTime', title: '时间' },
                    { field: 'email_Status', title: '邮箱' },
                    { field: 'emailTime', title: '时间' },
                    { field: 'account_Status', title: '账户' },
                    { field: 'accountTime', title: '时间' },
                    { field: 'qa_Status', title: '安全问题' },
                    { field: 'qaTime', title: '时间' },
                    { field: 'video_Status', title: '视频' },
                    { field: 'videoTime', title: '时间' },
                    { field: 'face_Status', title: '现场' },
                    { field: 'faceTime', title: '时间' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnCustomerAuthenticationStatusAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnCustomerAuthenticationStatusEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnCustomerAuthenticationStatusDelete' + token,
                    text: '删除',
                    iconCls: 'icon-clear'
                }
            ],
            onLoadSuccess: function () {
                //添加事件跳转
                onClickCustomerAuthenticationStatusAdd();
                // 删除事件跳转
                onClickCustomerAuthenticationStatusDelete();
                //修改事件跳转
                onClickCustomerAuthenticationStatusEdit();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickCustomerAuthenticationStatusAdd() {
        var buttonId = "btnCustomerAuthenticationStatusAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initCustomerAuthenticationStatusWindow({});
        });
    }

    /**
     * 删除事件
     */
    function onClickCustomerAuthenticationStatusDelete() {
        var buttonId = "btnCustomerAuthenticationStatusDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('CustomerAuthenticationStatusTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/customer/CustomerAuthenticationStatus_delete.action?customerAuthenticationStatus.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('CustomerAuthenticationStatusTable' + token);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                }, null);
            });
        });
    }

    //修改事件
    function onClickCustomerAuthenticationStatusEdit() {
        var butttonId = "btnCustomerAuthenticationStatusEdit" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('CustomerAuthenticationStatusTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerAuthenticationStatus_load.action?customerAuthenticationStatus.id=" + id;
                fw.post(url, null, function (data) {
                    // 增加其他传入参数
                    data["customerAuthenticationStatusVO.customerName"] = selected.customerName;
                    data["customerAuthenticationStatus.mobileStatus"] = selected.mobileStatus;
                    data["customerAuthenticationStatus.emailStatus"] = selected.emailStatus;
                    data["customerAuthenticationStatus.accountStatus"] = selected.accountStatus;
                    data["customerAuthenticationStatus.qaStatus"] = selected.qaStatus;
                    data["customerAuthenticationStatus.videoStatus"] = selected.videoStatus;
                    data["customerAuthenticationStatus.faceStatus"] = selected.faceStatus;
                    //fw.alertReturnValue(data);
                    initCustomerAuthenticationStatusWindow(data);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }

    //初始化弹出窗体
    function initCustomerAuthenticationStatusWindow(data) {

        data["customerAuthenticationStatus.operatorId"] = loginUser.getId();
        data["operatorName"] = loginUser.getName();

        // 更改url地址
        var url = WEB_ROOT + "/modules/customer/CustomerAuthenticationStatus_Save.jsp?token=" + token;
        var windowId = "CustomerAuthenticationStatusWindow" + token;
        // 更改窗口名称
        // 更改窗口大小
        fw.window(windowId, '客户状态信息', 400, 320, url, function () {
            onClickCheckCustomer();
            onClickInputCustomer();
            // 初始化控件

            // 初始化类型combotree
            // 初始化控件位置
            // fw.getComboTreeFromKV('typeId'+token, 'CRM_CustomerFeedbackType', 'k', fw.getMemberValue(data, 'customerFeedback.typeId'));

            // 初始化表单提交事件
            onClickCustomerAuthenticationStatusSubmit();
            // 加载数据
            fw.formLoad('formCustomerAuthenticationStatus' + token, data);
        }, null);
    }

    //客户名称文本框点击弹出客户列表事件
    function onClickInputCustomer() {
        $('#customerName' + token).bind('click', function () {
            //获取客户列表的链接
            var url = WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function () {
                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT + '/customer/CustomerListSelectClass.js', function () {
                    var obj = new CustomerAuthenticationStatusClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token, obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
    }

    //查询客户事件
    function onClickCheckCustomer() {
        $('#btnCheckCustomer' + token).bind('click', function () {
            //获取客户列表的链接
            var url = WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function () {

                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT + '/customer/CustomerListSelectClass.js', function () {
                    var obj = new CustomerAuthenticationStatusClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token, obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
    }

    //数据提交事件
    function onClickCustomerAuthenticationStatusSubmit() {
        var buttonId = "btnCustomerAuthenticationStatusSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formCustomerAuthenticationStatus" + token;
            // 更改url
            var url = WEB_ROOT + "/customer/CustomerAuthenticationStatus_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function () {
                //alert($("#mobile_Status"+token).val());
                process.beforeClick();
            }, function () {
                //alert('done');
                process.afterClick();
                fw.datagridReload("CustomerAuthenticationStatusTable" + token);
                fw.windowClose('CustomerAuthenticationStatusWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    //查询事件
    function onClickCustomerAuthenticationStatusSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function () {
            var strTableId = "CustomerAuthenticationStatusTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            //获取客户名称
            params["customerAuthenticationStatusVO.customerName"] = $('#search_CustomerName' + token).val();
            //获取手机验证状态
            params["customerAuthenticationStatusVO.mobileStatus"] = $('#search_Mobile_Status' + token).combotree("getValue");
            //获取邮箱验证状态
            params["customerAuthenticationStatusVO.emailStatus"] = $('#search_Email_Status' + token).combotree("getValue");
            //获取安全问题验证状态
            params['customerAuthenticationStatusVO.qaStatus'] = $('#search_QA_Status' + token).combotree("getValue");
            //获取账户认证状态
            params['customerAuthenticationStatusVO.accountStatus'] = $('#search_Account_Status' + token).combotree("getValue");
            $('#' + strTableId).datagrid('load');
        })
    }

    //查询重置事件
    function onClickCustomerAuthenticationStatusSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            //重置客户名称
            $('#search_CustomerName' + token).val('');
            //重置手机验证状态
            fw.combotreeClear("search_Mobile_Status" + token);
            //重置邮箱验证状态
            fw.combotreeClear("search_Email_Status" + token);
            //重置安全问题验证状态
            fw.combotreeClear("search_QA_Status" + token);
            //重置账户验证状态
            fw.combotreeClear("search_Account_Status" + token);
        });
    }

    /**
     * 初始化下拉列表项
     */
    function initMobileStatusTree(combotreeId, selectIndexId) {
        if (combotreeId == null) {
            combotreeId = "search_Mobile_Status" + token;
        }
        var URL = WEB_ROOT + "/customer/CustomerAuthenticationStatus_StatusTree.action";
        fw.combotreeOnload(combotreeId, URL, function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {
            }
            return treeData;
        }, selectIndexId);
    }

    function initEmailStatusTree(combotreeId, selectIndexId) {
        if (combotreeId == null) {
            combotreeId = "search_Email_Status" + token;
        }
        var URL = WEB_ROOT + "/customer/CustomerAuthenticationStatus_StatusTree.action";
        fw.combotreeOnload(combotreeId, URL, function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {
            }
            return treeData;
        }, selectIndexId);
    }

    function initQAStatusTree(combotreeId, selectIndexId) {
        if (combotreeId == null) {
            combotreeId = "search_QA_Status" + token;
        }
        var URL = WEB_ROOT + "/customer/CustomerAuthenticationStatus_StatusTree.action";
        fw.combotreeOnload(combotreeId, URL, function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {
            }
            return treeData;
        }, selectIndexId);
    }

    function initAccountStatusTree(combotreeId, selectIndexId) {
        if (combotreeId == null) {
            combotreeId = "search_Account_Status" + token;
        }
        var URL = WEB_ROOT + "/customer/CustomerAuthenticationStatus_StatusTree.action";
        fw.combotreeOnload(combotreeId, URL, function (data) {
            var treeData = [];
            try {
                data = fw.dealReturnObject(data);
                treeData = data;
            }
            catch (e) {
            }
            return treeData;
        }, selectIndexId);
    }


    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }, loadCustomerInfo: function (customerId, customerName, attribute, accountId, bankName) {
            $("#customerId" + token).val(customerId);
            $("#customerName" + token).val(customerName);
        }
    };
}
