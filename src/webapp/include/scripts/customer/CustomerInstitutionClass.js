
var CustomerInstitutionClass = function(token) {
    var remark=1;//客户分配时标志为机构客户
    //var customerId=null;
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickCustomerInstitutionSearch();
        // 初始化查询重置事件
        onClickCustomerInstitutionSearchReset();

        // 初始化表格

        initTableCustomerInstitutionTable();
    }
    /**
     * 初始化表格
     */
    function initTableCustomerInstitutionTable() {
        var strTableId = 'CustomerInstitutionTable'+token;
        var url = WEB_ROOT+"/customer/CustomerInstitution_list.action";

        $('#'+strTableId).datagrid({
            title: '机构客户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:false,
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
                { field: 'sid', title: '序号', hidden:true},
                { field: 'id', title: '编号', hidden:true },
                { field: 'name', title: '姓名'},
                { field: 'type', title: '性质',hidden:true },
                { field: 'legalPerson', title: '法人' },
                { field: 'registeredCapital', title: '注册资本' },
                { field: 'address', title: '注册地址' },
                { field: 'mobile', title: '移动电话'
                    //HOPEWEALTH-1276
                    //formatter: function(value, row, index) {
                    //    if (row['mobile'] == "") {
                    //        return row['mobile'];
                    //    }
                    //    if (row['saleManId'] != loginUser.getId()) {
                    //        return "***";
                    //    }
                    //    return row['mobile'];
                    //}
                },
                { field: 'phone', title: '固定电话'
                    //HOPEWEALTH-1276
                    //formatter: function(value, row, index) {
                    //    if (row['phone'] == "") {
                    //        return row['phone'];
                    //    }
                    //    if (row['saleManId'] != loginUser.getId()) {
                    //        return "***";
                    //    }
                    //    return row['phone'];
                    //}
                },
                { field: 'email', title: '电子邮件' },
                { field: 'distributionStatus', title: '审批状态', formatter: function (value, row, index) {
                    if(value == 0){
                        return "未审核";
                    }else if(value == 1){
                        return "审核通过";
                    }else if(value == 2){
                        return "审核未通过";
                    }
                }},
                { field: 'saleManName', title: '负责销售员',formatter: function (value, row, index){
                    if(value == ""){
                        return "未分配";
                    }
                    return value;
                }},
                { field: 'groupName', title: '销售组', hidden: false}, //HOPEWEALTH-1276
                { field: 'saleManId', title: '销售员编号', hidden: true} //HOPEWEALTH-1276
            ]],
            onLoadSuccess:function() {
                onClickCustomerInstitutionAdd();
                onClickCustomerInstitutionDelete();
                onClickCustomerInstitutionEdit();
                onClickCustomerInstitutionDistribution();
                onClickPassword();
                // HOPEWEALTH-1276 呼叫电话事件
                onClickCustomerInstitutionDial();
            }
        });
    }

    /**
     * 初始化弹出窗口
     * TODO
     * @param data
     */
    function initWindowCustomerInstitutionWindow(data, obj1, obj, customerId) {

        data["institution.OperatorId"] = loginUser.getId();

        var url =  WEB_ROOT + "/modules/customer/CustomerInstitution_Save.jsp?token="+token;
        var windowId = "CustomerInstitutionWindow" + token;

        fw.window(windowId, '机构客户详细信息',850, 575, url, function() {

            fw.getComboTreeFromKV('type'+token, 'CustomerInstitutionType', 'V', fw.getMemberValue(data, 'institution.type'));
            fw.getComboTreeFromKV('customerSourceId'+token, 'CustomerSource', 'V', fw.getMemberValue(data, 'institution.customerSourceId'));
            fw.getComboTreeFromKV('customerTypeId'+token, 'CustomerType', 'V', fw.getMemberValue(data, 'institution.customerTypeId'));
            fw.getComboTreeFromKV('relationshipLevelId'+token, 'RelationshipLevel', 'V', fw.getMemberValue(data, 'institution.relationshipLevelId'));
            fw.getComboTreeFromKV('creditRateId'+token, 'CreditRate', 'V', fw.getMemberValue(data, 'institution.creditRateId'));
            fw.getComboTreeFromKV('careerId'+token, 'Career', 'V', fw.getMemberValue(data, 'institution.careerId'));
            fw.getComboTreeFromKV('staffSizeId'+token, 'staffSize', 'V', fw.getMemberValue(data, 'institution.staffSizeId'));
            // 初始化表单提交事件
            onClickCustomerInstitutionSubmit();
            using(SCRIPTS_ROOT+'/customer/CustomerAccountClass.js', function () {
                var accunt = new CustomerAccountClass(token);
                accunt.initModule(obj);
            });
            using(SCRIPTS_ROOT+'/customer/CustomerCertificateClass.js', function () {
                var certificate = new CustomerCertificateClass(token);
                certificate.initModule(obj);
            });
            // HOPEWEALTH-1224 初始化产品信息列表
            listProductionTable(customerId);

            // 加载数据
            fw.formLoad('formCustomerInstitution'+token, data);

            if(obj1==1){
                var url = WEB_ROOT + "/customer/CustomerInstitution_add.action";
                $.post(url,null,function(data){
                    var json="["+data+"]";
                    var jsonArray = eval('(' + json + ')');
                    $("#institutionNumber"+token).val(jsonArray[0].returnValue[0].institutionNumber );
                    $("#creatTime"+token).val(jsonArray[0].returnValue[0].creatTime);
                })
            }
        }, null);
    }

    /**
     * HOPEWEALTH-1224 初始化产品列表
     * @param customerId
     */
    function listProductionTable(customerId) {
        var strTableId = "CustomerProductionTable" + token;
        var url = WEB_ROOT + "/customer/CustomerProduction_list.action";
        $('#' + strTableId).datagrid({
            url: url,
            queryParams: {
                customerId: customerId,
                customerRemark: remark
            },
            fitColumns: true,
            singleSelect: true,
            pageList: [3],
            pageSize: 3,
            rownumbers: true,
            loadFilter: function (data) {
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
                    {field: 'ck', checkbox: true}
                ]
            ],
            columns: [
                [
                    { field: 'customerId', title: 'id', hidden: true, width: 25},
                    { field: 'customerName', title: '客户姓名', width: 24},
                    { field: 'name', title: '产品名称', width: 80 },
                    { field: 'projectName', title: '所属项目', width: 40, hidden: true},
                    { field: 'productCompositionName', title: '产品规模', width: 50 , hidden: true},
                    { field: 'createTime', title: '认购时间', width: 22},
                    { field: 'money', title: '金额', width: 25 },
                    { field: 'moneyStatus', title: '状态',
                        formatter:function(value,row,index){
                            if (value == "0") {
                                return "预约";
                            }
                            else if (value == "1") {
                                return "已打款";
                            }
                            else if (value == "4") {
                                return "作废";
                            }
                            else {
                                return "其他";
                            }
                        }
                    },
                    { field: 'originSalesman', title: '销售人员', hidden: true}
                ]
            ],
            toolbar: [
                {
                    id: 'btnShowDetail4Production' + token,
                    text: '查看详情',
                    iconCls: 'icon-search'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickShowDetail4Production();
            }
        });
    }

    //响应
    function onClickShowDetail4Production() {
        var buttonId = "btnShowDetail4Production" + token;
        fw.bindOnClick(buttonId, function () {
            fw.datagridGetSelected('CustomerProductionTable' + token, function (selected) {
                var productionId = selected.id;
//                alert(selected.projectId);
                var url = WEB_ROOT + "/customer/CustomerProduction_list.action?CustomerProduction.productionId=" + productionId;
                fw.post(url, null, function (data) {
                    data["production.projectName"] = selected.projectName;
                    data["production.name"] = selected.name;
                    data["production.size"] = selected.size;
                    data["production.startTime"] = selected.startTime;
                    data["production.stopTime"] = selected.stopTime;
                    data["production.valueDate"] = selected.valueDate;
                    data["production.expiringDate"] = selected.expiringDate;
                    data["production.interestDate"] = selected.interestDate;
                    data["production.appointmentMoney"] = selected.appointmentMoney;
                    data["production.saleMoney"] = selected.saleMoney;
                    data["production.status"] = selected.status;
                    data["production.productId"] = selected.productId;
                    data["production.paymentMoney"] = selected.paymentMoney;
                    data["production.paymentProfitMoney"] = selected.paymentProfitMoney;
                    initWindowProductionDetail(data);
                });
            })

        });
    }

    /**
     * 初始化密码窗口
     * @param data
     */
    function initPasswordWindow(data) {

        data["personal.OperatorId"] = loginUser.getId();
        var url =  WEB_ROOT + "/modules/customer/CustomerInstitution_Password.jsp?token="+token;
        var windowId = "PasswordWindow" + token;
        fw.window(windowId, '机构客户密码管理', 400, 300, url, function() {

            // 初始化表单提交事件
            onClickPasswordSubmit();

            // 加载数据
            fw.formLoad('formPassword'+token, data);
            $("#password"+token).val("");

        }, null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 呼叫电话事件
     * HOPEWEALTH-1276
     */
    function onClickCustomerInstitutionDial() {
        var buttonId  = "btnCustomerInstitutionDial" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerInstitutionTable' + token, function (selected) {
                process.beforeClick();

                var phoneNumber = selected.mobile;
                if (phoneNumber == null || phoneNumber == "") {
                    fw.alert("电话为空", "该客户电话为空！");
                    process.afterClick();
                    return;
                }

                //确定拨打电话，输入座席账户和密码
                var url = WEB_ROOT + "/modules/callcenter/CallCenter_Dial.jsp?token=" + token + "&phoneNumber=" + phoneNumber;

                var windowId = "DialWindow" + token;
                fw.window(windowId, '拨打', 350, 200, url, function () {
                    //绑定按钮btnDialSubmit事件
                    onClickCustomerInstitutionDialSubmit();
                }, null);

                process.afterClick();
            });
        });
    }

    /**
     * 确认拨打事件
     * HOPEWEALTH-1276
     */
    function onClickCustomerInstitutionDialSubmit() {
        var buttonId = "btnDialSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            //TODO
            var phoneNumber = $("#phoneNumber" + token).val();
            var loginName = $("#loginName" + token).val();
            var password = $("#password" + token).val();

            var url = WEB_ROOT;//TODO
            url += "/include/framework/7moor/edb_bar/phoneBar/phonebarWithNumber.html?loginName=";
            url += loginName;
            url += "&password=";
            url += password;
            url += "&loginType=Local";
            url += "&phoneNumber=" + phoneNumber;
            window.open(url);
            //var windowId = "DialWindow" + token;
            //fw.windowClose(windowId);
        });
    }

    /**
     * 添加事件
     */
    function onClickCustomerInstitutionAdd() {

        var buttonId = "btnCustomerInstitutionAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowCustomerInstitutionWindow({},1,null);
        });

    }

    /**
     * 删除事件
     */
    function onClickCustomerInstitutionDelete() {
        var buttonId = "btnCustomerInstitutionDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerInstitutionTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/customer/CustomerInstitution_delete.action?institution.sid="+selected.sid;
                    //alert(url);
                    fw.post(url, null, function(data) {
                        //fw.alertReturnValue(data);
                        fw.datagridReload('CustomerInstitutionTable'+token);
                        process.afterClick();
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
    function onClickCustomerInstitutionEdit() {
        var butttonId = "btnCustomerInstitutionEdit" + token;
        fw.bindOnClick(butttonId, function(process) {

            fw.datagridGetSelected('CustomerInstitutionTable'+token, function(selected){
                //HOPEWEALTH-1276
                //alert("loginUser id = " + loginUser.getId() + "\n" +
                //      "saleman id = " +  selected.saleManId);
                process.beforeClick();
                var sid = selected.sid;
                var id = selected.id;//customerId
                var url = WEB_ROOT + "/customer/CustomerInstitution_load.action?institution.sid="+sid;
                fw.post(url, null, function(data){
                    //fw.alertReturnValue(data);
                    initWindowCustomerInstitutionWindow(data, null, id, id);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 机构客户分配
     */
    function onClickCustomerInstitutionDistribution(){
        var buttonId = "btnInstitutionDistribution" + token;
        fw.bindOnClick(buttonId,function(process){
            fw.datagridGetSelections('CustomerInstitutionTable'+token, function(selections){
                process.beforeClick();
                var url = WEB_ROOT + '/customer/CustomerDistribution_load.action';

                var params = {"customers": JSON.stringify(selections)}




                /**
                 * @description
                 * 获得客户的所有数据的json
                 * @author 胡超怡
                 *
                 * @date 2018/12/12 17:52
                 * @throws Exception
                 */
                using(SCRIPTS_ROOT + '/sale/CustomerSaleClass.js', function () {

                    fw.post(url,params, function (data) {

                            var customerSaleClass = new CustomerSaleClass(token, null, remark, data);
                            customerSaleClass.initModule();
                            process.afterClick();

                        }, function () {
                            process.afterClick();

                        }, function () {
                            process.afterClick();
                        }, buttonId);

                });
            });
        });
    }

    /**
     * 密码管理
     */
    function onClickPassword(){
        var butttonId = "btnPassword" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('CustomerInstitutionTable'+token, function(selected){
                var sid = selected.sid;
                var url = WEB_ROOT + "/customer/CustomerInstitution_load.action?institution.sid="+sid;
                fw.post(url, null, function(data){
                    //fw.alertReturnValue(data);
                    initPasswordWindow(data,1);
                }, null);
            })

        });
    }
    /**
     * 密码管理
     */
    function onClickPassword(){
        var butttonId = "btnPassword" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('CustomerInstitutionTable'+token, function(selected){
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/customer/CustomerInstitution_load.action?institution.sid="+sid;
                fw.post(url, null, function(data){
//                    fw.alertReturnValue(data);
                    initPasswordWindow(data);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }
    /**
     * 查询事件
     */
    function onClickCustomerInstitutionSearch() {
        var buttonId = "btnSearchCustomerInstitution" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "CustomerInstitutionTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["customerInstitutionVO.name"] = $("#search_Name"+token).val();
            params["customerInstitutionVO.mobile"] = $("#search_Mobile"+token).val();
            params["customerInstitutionVO.address"] = $("#search_Address"+token).val();
            $( '#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
            fw.treeClear()
        });

    }

    /**
     * 查询重置事件
     */
    function onClickCustomerInstitutionSearchReset() {
        var buttonId = "btnResetCustomerInstitution" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_Name"+token).val('');
            $('#search_Mobile'+token).val('');
            $('#search_Address'+token).val('');
        });
    }

    /**
     * 数据提交事件
     */
    function onClickCustomerInstitutionSubmit() {
        var buttonId = "btnCustomerInstitutionSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formCustomerInstitution" + token;
            var url = WEB_ROOT + "/customer/CustomerInstitution_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("CustomerInstitutionTable"+token);
                fw.windowClose('CustomerInstitutionWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 密码保存事件
     */
    function onClickPasswordSubmit() {
        var buttonId = "btnPasswordSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var sid=$("#sid"+token).val();
            var password=$("#beforePassword"+token).val();
            var successful;
            var url = WEB_ROOT + "/customer/CustomerInstitution_checkPassword.action?institution.sid="+sid+"&institution.password="+password;
            $.post(url,null,function(data){
                var json="["+data+"]";
                var jsonArray = eval('(' + json + ')');
//                fw.alertReturnValue(data)
                successful=jsonArray[0].returnValue[0].successful;
                if(successful=="1"){
                    var formId = "formPassword" + token;
                    var url = WEB_ROOT + "/customer/CustomerInstitution_passwordUpdate.action";
                    fw.bindOnSubmitForm(formId, url, function(){
                        process.beforeClick();
                    }, function() {
                        //alert('done');
                        process.afterClick();
                        fw.datagridReload("CustomerInstitutionTable"+token);
                        fw.windowClose('PasswordWindow'+token);
                    }, function() {
                        process.afterClick();
                    });
                }else{
                    $("#errorMessage"+token).html("原密码错误");
                }

            })

        });
    }
    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        },
        setCustomerId:function(id){
            this.customerId=id;
        },
        getCustomerId:function(){
            return this.customerId;
        },
        getRemark:function(){
            return remark;
        },
        initTable:function(){
            initTableCustomerInstitutionTable();
        }
    };
}
