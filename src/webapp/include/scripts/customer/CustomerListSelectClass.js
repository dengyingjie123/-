/**
 * Created by Administrator on 2015/4/10.
 */

var CustomerListSelectClass = function (token, obj) {

    //初始化事件
    function initAll() {
        // 初始化查询事件
        onClickCustomerPersonalSearch();
        onClickCustomerInstitutionSearch();

        //查询重置事件
        onClickCustomerPersonalSearchReset();
        onClickCustomerInstitutionSearchReset();
        // 初始化表格
        initTableCustomerPersonalTable();
        initTableCustomerInstitutionTable();

        //初始化按钮选择事件
        onClickSelectedCustomer();
    }

    /**
     * 初始化表格
     */
    function initTableCustomerPersonalTable() {
        var strTableId = 'CustomerPersonalTable' + token;
        var url = WEB_ROOT + "/customer/CustomerPersonal_list.action";

        $('#' + strTableId).datagrid({
            title: '个人客户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [10, 20, 40],
            pageSize: 10,
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
                    { field: 'sid', title: '序号', hidden: true, width: 30 },
                    { field: 'id', title: '编号', hidden: true, width: 30 },
                    { field: 'name', title: '姓名', width: 30 },
                    { field: 'sex', title: '性别', width: 30 },
                    { field: 'birthday', title: '出生日期', width: 30 },
                    { field: 'nation', title: '国籍', width: 30 },
                    { field: 'address', title: '地址', width: 30 },
                    { field: 'mobile', title: '移动电话', width: 30 },
                    { field: 'phone', title: '固定电话', width: 30 },
                    { field: 'email', title: '电子邮件', width: 30 },
                    { field: 'postNo', title: '邮编', width: 30 }
                ]
            ]
        });
    }


    function initTableCustomerInstitutionTable() {
        var strTableId = 'CustomerInstitutionTable' + token;
        var url = WEB_ROOT + "/customer/CustomerInstitution_list.action";

        $('#' + strTableId).datagrid({
            title: '机构客户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [10, 20, 40],
            pageSize: 10,
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
                    { field: 'sid', title: '序号', hidden: true, width: 30 },
                    { field: 'id', title: '编号', hidden: true, width: 30 },
                    { field: 'name', title: '姓名', width: 30 },
                    { field: 'type', title: '性质', width: 30 },
                    { field: 'legalPerson', title: '法人', width: 30 },
                    { field: 'registeredCapital', title: '注册资本', width: 30 },
                    { field: 'address', title: '注册地址', width: 30 },
                    { field: 'mobile', title: '移动电话', width: 30 },
                    { field: 'phone', title: '固定电话', width: 30 },
                    { field: 'email', title: '电子邮件', width: 30 },
                    { field: 'postNo', title: '邮编', width: 30 }
                ]
            ]
        });
    }

    /**
     * 查询事件
     */
    function onClickCustomerPersonalSearch() {
        var buttonId = "btnSearchCustomerPersonal" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "CustomerPersonalTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["personalVO.name"] = $("#search_Name" + token).val();
            params["personalVO.mobile"] = $("#search_Mobile" + token).val();
            params["personalVO.address"] = $("#search_Address" + token).val();
            params["certificate"] = $("#search_Certificate" + token).val();
            $('#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
            fw.treeClear()
        });

    }

    function onClickCustomerInstitutionSearch() {
        var buttonId = "btnSearchCustomerInstitution" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "CustomerInstitutionTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["customerInstitutionVO.name"] = $("#search_iName" + token).val();
            params["customerInstitutionVO.mobile"] = $("#search_iMobile" + token).val();
            params["customerInstitutionVO.address"] = $("#search_iAddress" + token).val();
            params["certificate"] = $("#search_iCertificate" + token).val();
            $('#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
            fw.treeClear()
        });

    }

    /**
     * 查询重置事件
     */
    function onClickCustomerPersonalSearchReset() {
        var buttonId = "btnResetCustomerPersonal" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_Name" + token).val('');
            $('#search_Mobile' + token).val('');
            $('#search_Address' + token).val('');
            $('#search_Certificate' + token).val('');
            // 清空数据，并查询出列表
            var strTableId = "CustomerPersonalTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["personalVO.name"] = $("#search_Name" + token).val();
            params["personalVO.mobile"] = $("#search_Mobile" + token).val();
            params["personalVO.address"] = $("#search_Address" + token).val();
            params["certificate"] = $("#search_Certificate" + token).val();
            $('#' + strTableId).datagrid('load');
        });
    }

    function onClickCustomerInstitutionSearchReset() {
        var buttonId = "btnResetCustomerInstitution" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_iName" + token).val('');
            $('#search_iMobile' + token).val('');
            $('#search_iAddress' + token).val('');
            $('#search_iCertificate' + token).val('');
            // 清空数据，并查询出列表
            var strTableId = "CustomerInstitutionTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["customerInstitutionVO.name"] = $("#search_iName" + token).val();
            params["customerInstitutionVO.mobile"] = $("#search_iMobile" + token).val();
            params["customerInstitutionVO.address"] = $("#search_iAddress" + token).val();
            params["certificate"] = $("#search_iCertificate" + token).val();
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 初始化选择客户按钮
     */
    function onClickSelectedCustomer() {
        var buttonId = "btnSelectedCustomer" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formTabsId = "selectedCustomer" + token;
            var formTab = $("#" + formTabsId).tabs("getSelected");
            //取得选项卡的位置
            var index = $("#" + formTabsId).tabs('getTabIndex', formTab);
            var tableName;
            var accountTable;
            var customerId;
            var customerName;
            var attribute;
            if (index == 0) {
                attribute = 0;
                tableName = "CustomerPersonalTable" + token;
            } else {
                attribute = 1;
                tableName = "CustomerInstitutionTable" + token;
            }

            fw.datagridGetSelected(tableName, function (selected) {
                customerId = selected.id;
                customerName = selected.name;
                var accountId = "";
                var bankName = "";
                //调用加载文本框函数
                obj.loadCustomerInfo(customerId, customerName, attribute, accountId, bankName);
                fwCloseWindow('CustomerSelectWindow' + token);
            });
        });
    }

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    }
}
