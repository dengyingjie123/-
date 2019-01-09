var WEB_ROOT = "/core";
var SCRIPTS_ROOT = "../scripts";
var JEASYUI_ROOT = "jquery-easyui-1.4.5";
//var SCRIPTS_ROOT = WEB_ROOT+"/include/scripts";
var VERSION = Math.random();
var config = new ConfigClass();
var LoginUser = function () {
    var user = null;
    var positionInfo = null;

    return {
        setUser: function (json) {
            user = json;
        },
        setUserPositionInfo:function (json) {
            // alert('init positionInfo');
            positionInfo = json;
        },
        getDepartmentName: function () {
            return positionInfo.departmentName;
        },
        getDepartmentId: function () {
            return positionInfo['departmentId'];
        },
        getId: function () {
            return user.id;
        },
        getName: function () {
            return user.name;
        },
        getMobile: function () {
            return user.mobile;
        },
        getReferralCode:function(){
            return user['referralCode'];
        }
    }

};
var loginUser = new LoginUser();

function getWidth(percent){
    return $(window).width() * percent;
}

function getHeight(percent){
    return $(window).height() - percent;
}

/**
 * 初始化系统，主要是初始化菜单
 */
function initSystem() {
    //alert("initSystem2");
    // 初始化菜单
    var systemMenuId = 'systemMenu';

    // 绑定菜单点击事件
    $('#' + systemMenuId).tree({
        onClick: function (node) {
            $('#' + systemMenuId).tree('toggle', node.target);
            try {
                if ($("#" + systemMenuId).tree('isLeaf', node.target)) {
                    loadWorkSpace(node.attributes.url, node.attributes.moduleName,
                        node.attributes.permissionName, node.iconCls, node.id);
                }
            }
            catch (e) {
                // $.messager.alert('出差警告','菜单加载失败 ' + e);
            }
        }
    });

    fw.post(WEB_ROOT + "/system/Menu_listMenu.action", null, function (data) {
//        alert(JSON.stringify(data));
        $('#' + systemMenuId).tree({data: data[0].children});
        $('#' + systemMenuId).tree('collapseAll');
    }, function (message) {
        alert(message);
    });


    // 初始化登录用户
    fw.post(WEB_ROOT + "/system/Login_loadLoginUser.action", null, function (data) {
        //fw.alertReturnValue(data);
        loginUser.setUser(data);

        // 设置登录用户名称
        $('#btnLoginUser').menubutton({text: loginUser.getName()});
        //onClickLogoutUser();


        // 加载用户推荐码
        var referralCodeURL = WEB_ROOT + "/system/User_getReferralCode.action?user.id=" + loginUser.getId();
        // fw.debug(refereeCodeUrl, 333344);
        fw.post(referralCodeURL, null, function(data){
            var referralCode = data["referralCode"];
            if (fw.checkIsTextEmpty(referralCode)) {
                fw.alert("警告", "无法获得用户推荐码");
                return;
            }


            $('#referralCode').html(referralCode);

        }, function() {
            fw.alert("警告", "无法获得用户推荐码");
        });

    }, function () {
        fw.alert("警告", "获得登录用户信息失败，请重新登录");
    });

    // 初始化登录用户所在部门
    initUserDepartment();






    // 可在此处增加默认的模块 [24611604]
    // 右侧面板显示公告
    loadWorkSpace('modules/system/Calendar_Main.jsp','个人日历','个人日历管理','','5cbcd591-fbd6-4773-81b8-f05f154c8c04');
}


function initUserDepartment() {
    // 初始化登陆用户所在部门
    fw.post(WEB_ROOT + "/system/Login_loadLoginUserPositionInfo.action", null, function (data) {
        // fw.alertReturnValue(data);

        //
        $('#mb').menubutton({
            iconCls: 'icon-edit',
            menu: '#mm'
        });

        // removeMenuItem('loginDepartmentMenu');
        var userPositionInfo = data['userPositionInfo'];

        loginUser.setUserPositionInfo(data['defaultUserPosition']);

        $(userPositionInfo).each(function() {

            var iconCls = "";
            if (this['status'] == '1') {
                iconCls = 'icon-ok';
            }

            var positionId = this['positionId'];
            $('#loginDepartmentMenu').menu('appendItem', {
                text: this['departmentFullName'] + " - " + this['positionName'],
                iconCls:iconCls,
                onclick: function(){
                    onClickLoginDepartmentMenu(positionId);
                }
            });
        });

        var loginDepartmentDisplayText = data['defaultUserPosition']['departmentFullName'] + "-" + data['defaultUserPosition']['positionName'];
        $('#btnLoginDepartment').menubutton({text: loginDepartmentDisplayText});
    }, function () {
        fw.alert("警告", "获得登录用户所属部门信息失败，请重新登录");
    });
}

function onClickLoginDepartmentMenu(positionId) {
    window.location = WEB_ROOT + "/login_execute?defaultPositionId="+positionId;
}

function removeMenuItem(id) {
    id = fw.getObjectFromId(id);

    $(id).each(function() {
        var item = $(id).menu('getItem', this);
        $(id).menu('removeItem', item.target);
    });
}

function onClickLogoutUser() {

    fw.confirm('提示', '是否确定注销当前用户？', function () {
        var url = WEB_ROOT + "/system/Login_logout.action";
        fw.post(url, null, function () {
            window.location = WEB_ROOT + "/login.jsp";
        }, null);
    }, null);

}
/**
 * 加载页面到工作区
 * @param url 页面地址
 * @param moduleName 模块名称
 * @param permissionName 权限名称
 * @param icon 图标
 * @param token
 */
function loadWorkSpace(url, moduleName, permissionName, icon, token) {

    // alert("Menu:["+moduleName+"] obj:["+permissionName+"] url:["+url+"] token:["+token+"]");

    //alert("["+moduleName+"]");
    //alert("icon:["+icon+"]");
    var contentTabsId = "contentTabs";
    var index = hmMenu.get(token);
    //alert("Menu Index 1:"+index + " size:"+hmMenu.size);
    if (fw.checkIsTextEmpty(index)) {
        index = hmMenu.size;
        hmMenu.put(token, index);
    }

    // console.log($('#' + contentTabsId).tabs('getSelected'));

    $('#' + contentTabsId).tabs({
        border:false,
        onSelect:function(title){
            // alert(title+' is selected');
        }
    });

    if (!$('#' + contentTabsId).tabs('exists', index)) {
        $('#' + contentTabsId).tabs('add', {
            title: moduleName,
            loadingMessage: '正在加载，请稍后……',
            href: WEB_ROOT + "/" + url + "?token=" + token,
            cache: true,
            iconCls: icon,
            closable: false,
            onLoad: function () {
                contentTabAdd(permissionName, token);
            },
            onClose: function (title, index) {
                //hmMenu = new Map();
                //alert("onclose : " + index);
                if (index) {
                    alert("close: " + index);
                    var i = 0
                    for (i = 0; i < hmMenu.keys.length; i++) {
                        var key = hmMenu.keys[i];
                        var value = hmMenu.get(key);
                        if (value == index) {
                            alert("remove " + index + " token: " + value);
                            hmMenu.remove(key);
                        }
                    }
                }
            }
        });
    }
    else {
        $('#' + contentTabsId).tabs('select', index);
        contentTabReload(permissionName, token);
    }
}
/**
 * 加载左侧树形菜单
 * @param permissionName
 * @param token
 */
function contentTabAdd(permissionName, token) {
    //alert(permissionName);
    if (permissionName == "功能模块管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/system/MenuClass.js', function () {
            //alert("hello");
            var menuClass = new MenuClass(token);
            menuClass.initModule();
        });
    }
    else if (permissionName == "通联金融生态圈_单笔查询") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/allinpayCircle/AllinpayCircleQueryClass.js', function () {
            //alert("hello");
            var allinpayCircleQueryClass = new AllinpayCircleQueryClass(token);
            allinpayCircleQueryClass.initModule();
        });
    }
    else if (permissionName == "文章管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/cms/ArticleClass.js', function () {
            //alert("hello");
            var article = new ArticleClass(token);
            article.initModule();
        });
    }
    else if (permissionName == "财务会议申请WFA") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/oa/finance/FinancemeetingapplicationwfaClass.js', function () {
            //alert("hello");
            var financemeetingapp = new FinancemeetingapplicationwfaClass(token);
            financemeetingapp.initModule();
        });
    }
    else if (permissionName == "公告管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/system/SystemMessageClass.js', function () {
            //alert("hello");
            var article = new ArticleClass(token);
            article.initModule();
        });
    }
    else if (permissionName == "待办事项") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/system/SystemMessageClass.js', function () {
            //alert("hello");
            var article = new ArticleClass(token);
            article.initModule();
        });
    }
    else if (permissionName == "产品管理_供应商管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT+'/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Production_Supply','供应商管理');
        });
    }
    else if (permissionName == "销售管理_销售日报_查看") {
        using(SCRIPTS_ROOT + '/production/OrderClass.js', function () {
            //alert("hello");
            var orderClass = new OrderClass(token);
            orderClass.initReportWeekly();
        });
    }
    else if (permissionName == "销售管理_销售月报_查看") {
        using(SCRIPTS_ROOT + '/production/OrderClass.js', function () {
            //alert("hello");
            var orderClass = new OrderClass(token);
            orderClass.initReportMonthly();
        });
    }
    else if (permissionName == "销售产品统计报表") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/report/ProductionStatisticsClass.js', function () {
            //alert("hello");
            var pro = new ProductionStatisticsClass(token);
            pro.initModule();
        });
    }
    else if (permissionName == "销售时间统计报表") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/report/TimeStatisticsClass.js', function () {
            //alert("hello");
            var time = new TimeStatisticsClass(token);
            time.initModule();
        });
    }
    else if (permissionName == "合同申请") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/sale/ContractApplicationClass.js', function () {
            //alert("hello");
            var conApplication = new ContractApplicationClass(token);
            conApplication.initModule();
        });
    }
    else if (permissionName == "合同调配") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/sale/ContractTransferClass.js', function () {
            var contractTransferClass = new ContractTransferClass(token);
            contractTransferClass.initModule();
        });
    }
    else if (permissionName == "费用报销") {
//        alert("begin");
        using(SCRIPTS_ROOT + '/oa/finance/FinanceExpenseClass.js', function () {
//            alert("hello");
            var financeExpense = new FinanceExpenseClass(token);
            financeExpense.initModule();
        });
    }
    else if (permissionName == "费用报销_新") {
        using(SCRIPTS_ROOT + '/oa/finance/FinanceExpenseNewClass.js', function () {
            var financeExpenseNew = new FinanceExpenseNewClass(token);
            financeExpenseNew.initModule();
        });
    }
    else if (permissionName == "兑付计划月报表") {
        using(SCRIPTS_ROOT + '/sale/PaymentPlanClass.js', function () {
            var paymentPlanClass = new PaymentPlanClass(token);
            paymentPlanClass.initModulePaymentPlanReportMonth();
        });
    }
    else if (permissionName == "用章情况") {
        using(SCRIPTS_ROOT + '/oa/administration/SealUsageWFAClass.js', function () {
            var wfa = new SealUsageWFAClass(token);
            wfa.initModule();
        });
    } else if (permissionName == "印章管理") {
        using(SCRIPTS_ROOT + '/oa/administration/SealUsageWFA2Class.js', function () {
            var wfa = new SealUsageWFA2Class(token);
            wfa.initModule();
        });
    }
    else if (permissionName == "用章情况_用章类型管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('oa_SaleUsageWFA');
        });
    } else if (permissionName == "厚币钱庄每日产品销售额") {
        using(SCRIPTS_ROOT + '/sale/report/DailySalesClass.js', function () {
            var cls = new DailySalesClass(token);
            cls.initModule();
        });
    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年6月25日16:29:51
     * 内容：添加消息订阅
     */
    else if (permissionName == "消息订阅") {
        using(SCRIPTS_ROOT + '/system/MessageSubscriptionClass.js', function () {
            var messageSubscriptionClass = new MessageSubscriptionClass(token);
            messageSubscriptionClass.initModule();
        });
    }
    else if (permissionName == "产品分期管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/production/ProductionClass.js', function () {
            //alert("hello");
            var productionClass = new ProductionClass(token, null);
            productionClass.initModule();
        });
    } else if (permissionName == "产品分期管理_兑付配置") {
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('product_is_paymentplan_status');
        });
    }

    else if (permissionName == "产品分期综合查询") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/production/ProductionQueryClass.js', function () {
            //alert("hello");
            var productionQueryClass = new ProductionQueryClass(token, null);
            productionQueryClass.initModule();
        });
    }
    else if (permissionName == "产品管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/production/ProductionHomeClass.js', function () {
            //alert("hello");
            var productionHomeClass = new ProductionHomeClass(token, null);
            productionHomeClass.initModule();
        });
    }
    else if (permissionName == "客户回访管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerFeedbackClass.js', function () {
            //alert("hello");
            var customerFeedbackClass = new CustomerFeedbackClass(token);
            customerFeedbackClass.initModule();
        });
    }
    else if (permissionName == "出差申请") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/oa/business/BusinessTripApplicationClass.js', function () {
            //alert("hello");
            var businessTripApplicationClass = new BusinessTripApplicationClass(token);
            businessTripApplicationClass.initModule();
        });
    }
    else if (permissionName == "客户管理_客户回访类型管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('CRM_CustomerFeedbackType');
        });
    }
    else if (permissionName == "个人日历管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/system/CalendarClass.js', function () {
            //alert("hello");
            var calendarClass = new CalendarClass(token);
            calendarClass.initModule();
        });
    }
    else if (permissionName == "合同调配管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/sale/ContractRouteClass.js', function () {
            //alert("hello");
            var contractRouteClass = new ContractRouteClass(token);
            contractRouteClass.initModule();
        });
    }
    else if (permissionName == "订单综合查询") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/production/OrderQueryClass.js', function () {
            //alert("hello");
            var orderQueryClass = new OrderQueryClass(token, null);
            orderQueryClass.initModule();
        });
    }
    else if (permissionName == "客户资金日志") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerMoneyLogClass.js', function () {
            //alert("hello");
            var customerMoneylog = new CustomerMoneyLogClass(token, null);
            customerMoneylog.initModule();
        });
    }
    else if (permissionName == "个人客户管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerPersonalClass.js', function () {
            //alert("hello");
            var customerPersonalClass = new CustomerPersonalClass(token);
            customerPersonalClass.initModule();
        });
    }
    else if (permissionName == "机构客户管理") {
        using(SCRIPTS_ROOT + '/customer/CustomerInstitutionClass.js', function () {
            // alert("loaded...");
            var customerInstitutionClass = new CustomerInstitutionClass(token);
            customerInstitutionClass.initModule();
            // alert("finish");
        });

    }
    else if (permissionName == "记账管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/oa/finance/MoneyLogClass.js', function () {
            //alert("hello");
            var moneyLogClass = new MoneyLogClass(token);
            moneyLogClass.initModule();
        });
    }
    else if (permissionName == "合同调配") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/sale/ContractTransferClass.js', function () {
            var contractTransferClass = new ContractTransferClass(token);
            contractTransferClass.initModule();
        });
    }
    else if (permissionName == "人员信息") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/sale/SalesmanInfoClass.js', function () {
            var salesmanInfoClass = new SalesmanInfoClass(token, null, "", "", "");
            salesmanInfoClass.initModule();
        });
    }
    else if (permissionName == "客户分配审核") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerAuditClass.js', function () {
            var customerAuditClass = new CustomerAuditClass(token, null);
            customerAuditClass.initModule();
        });
    }
    else if (permissionName == "合同管理") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/sale/ContractClass.js', function () {
            var contractClass = new ContractClass(token, null);
            contractClass.initModule();
        });
    }
    else if (permissionName == "财务管理财务报表") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/oa/finance/MoneyLogReportClass.js', function () {
            //alert("hello");
            var moneyLogReportClass = new MoneyLogReportClass(token);
            moneyLogReportClass.initModule();
        });
    }
    else if (permissionName == "财务管理财务收支报表") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/oa/finance/MoneyLogReportSummaryClass.js', function () {
            //alert("hello");
            var moneyLogReportSummaryClass = new MoneyLogReportSummaryClass(token);
            moneyLogReportSummaryClass.initModule();
        });
    }
    // 运行测试菜单
    else if (permissionName == "运行测试菜单") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/test/TestClass.js', function () {
            var testClass = new TestClass();
            testClass.hello();
        });
    }
    else if (permissionName == "财务管理财务收入明细报表") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/oa/finance/MoneyLogReportDetailInOrOutClass.js', function () {
            //alert("hello");
            var moneyLogReportDetailInOrOutClass = new MoneyLogReportDetailInOrOutClass(token, '0');
            moneyLogReportDetailInOrOutClass.initModule();
        });
    }
    else if (permissionName == "财务管理财务支出明细报表") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/oa/finance/MoneyLogReportDetailInOrOutClass.js', function () {
            //alert("hello");
            var moneyLogReportDetailInOrOutClass = new MoneyLogReportDetailInOrOutClass(token, '1');
            moneyLogReportDetailInOrOutClass.initModule();
        });
    }
    else if (permissionName == "部门组织管理") {
        using(SCRIPTS_ROOT + '/system/OrgClass.js', function () {
            var orgClass = new OrgClass(token);
            orgClass.initModule();
        });
    }
    else if (permissionName == "个人信息") {
        using(SCRIPTS_ROOT + '/system/PersonalInfoClass.js', function () {
            var personalInfo = new PersonalInfoClass(token);
            personalInfo.initModule();
        });
    }
    else if (permissionName == "布局管理") {
        using(SCRIPTS_ROOT + '/cms/LayoutClass.js', function () {
            var layoutClass = new LayoutClass(token);
            layoutClass.initModule();
        });
    }
    else if (permissionName == "栏目管理") {

        using(SCRIPTS_ROOT + '/cms/ColumnClass.js', function () {
            var columnClass = new ColumnClass(token);
            columnClass.initModule();
        });
    }
    else if (permissionName == "菜单管理") {
        using(SCRIPTS_ROOT + '/system/MenuClass.js', function () {
            //alert("hello");
            var menuClass = new MenuClass(token);
            menuClass.initModule();
        });
    }
    else if (permissionName == "用户管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/UserClass.js', function () {
            //alert("loaded...");
            var userClass = new UserClass(token);
            userClass.initModule();
        });
    }
    //合同调配管理
    else if (permissionName == "客户资金") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerMoneyClass.js', function () {
            //alert("hello");
            var customerMoneyClass = new CustomerMoneyClass(token);
            customerMoneyClass.initModule();
        });
    } //兑付计划
    else if (permissionName == "兑付计划") {
        using(SCRIPTS_ROOT + '/sale/PaymentPlanClass.js', function () {
            var paymentPlanClass = new PaymentPlanClass(token, permissionName);
            paymentPlanClass.initModule();
        })
    }
    else if (permissionName == "资金准备") {
        using(SCRIPTS_ROOT+'/sale/CapitalPreClass.js', function () {
            var capitalPreClass = new CapitalPreClass(token);
            capitalPreClass.initModule(null,null);
        });
    }else if (permissionName == "兑付审核") {
        using(SCRIPTS_ROOT + '/sale/PaymentPlanClass.js', function () {
            var paymentPlanClass = new PaymentPlanClass(token, permissionName);
            paymentPlanClass.initModule();
        })
    } else if (permissionName == "兑付申请") {
        using(SCRIPTS_ROOT + '/sale/paymentPlanStatus/paymentPlanStatusApplyClass.js', function () {
            //958 KV表中的未兑付
            var paymentPlanStatusApply = new paymentPlanStatusApplyClass(token);
            paymentPlanStatusApply.initModule();
        })
    } else if (permissionName == "兑付审批") {
        using(SCRIPTS_ROOT + '/sale/paymentPlanStatus/paymentPlanStatusExamineClass.js', function () {
            var paymentPlanStatusExamine = new paymentPlanStatusExamineClass(token);
            paymentPlanStatusExamine.initModule();
        })
    } else if (permissionName == "等待兑付") {
        using(SCRIPTS_ROOT + '/sale/paymentPlanStatus/paymentPlanStatusWaitClass.js', function () {
            //958 kv表中的未兑付
            var paymentPlanStatusWait = new paymentPlanStatusWaitClass(token);
            paymentPlanStatusWait.initModule();
        })
    } else if (permissionName == "审批失败") {
        using(SCRIPTS_ROOT + '/sale/paymentPlanStatus/paymentPlanStatusExamineCancelClass.js', function () {
            //18364  KV表的兑付失败
            var paymentPlanStatusExamineCancel = new paymentPlanStatusExamineCancelClass(token);
            paymentPlanStatusExamineCancel.initModule();
        })
    } else if (permissionName == "兑付成功") {
        using(SCRIPTS_ROOT + '/sale/paymentPlanStatus/paymentPlanStatusSuccessClass.js', function () {
            // 957 KV 表的以对付
            var paymentPlanStatusSuccess = new paymentPlanStatusSuccessClass(token);
            paymentPlanStatusSuccess.initModule();
        })
    }
    //兑付计划状态管理
    else if (permissionName == "资金兑付_兑付状态管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Sale_PaymentPlan_Status');
        });
    }     //客户支付_方式管理
    else if (permissionName == "客户支付_方式管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('customer_customerPayment_Type');
        });
    }   //客户支付_方式管理
    else if (permissionName == "客户支付_状态管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('customer_customerPayment_status');
        });
    } //客户支付_方式管理
    else if (permissionName == "客户支付_支付方式管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('customer_customerPayment_PaymentType');
        });
    }
    else if (permissionName == "客户支付") {
        using(SCRIPTS_ROOT + '/customer/CustomerPaymentClass.js', function () {
            var customerPaymentClass = new CustomerPaymentClass(token);
            //调用初始化方法
            customerPaymentClass.initModule();
        })
    }
    else if (permissionName == "医师管理") {
        alert('get');
        var url = 'http://localhost:8080/shelby/test.jsp?name=youngbook';
        fw.loadScript(url, function () {
            testHello();
        });
    }
    else if (permissionName == "监管指标") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/ydhp/JianGuanZhiBiao.js', function () {
            var jianGuanZhiBiaoClass = new JianGuanZhiBiaoClass(token);
            jianGuanZhiBiaoClass.initModule();
        });
    }
    else if (permissionName == "科室监管指标") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/ydhp/KeShiJianGuanZhiBiao.js', function () {
            var keShiJianGuanZhiBiaoClass = new KeShiJianGuanZhiBiaoClass(token);
            keShiJianGuanZhiBiaoClass.initModule();
        });
    }
    else if (permissionName == "任务管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/task/Task.js', function () {
            var taskClass = new TaskClass(token);
            taskClass.initModule();
        });
    }
    else if (permissionName == "项目管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/production/ProjectClass.js', function () {
            var projectClass = new ProjectClass(token);
            projectClass.initModule();
        });
    }
    else if (permissionName == "KV管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule();
        });
    }
    else if (permissionName == "客户信用管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('CreditRate');
        });
    }
    else if (permissionName == "系统管理_文件权限模块管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('ModuleGroup');
        });
    }
    else if (permissionName == "系统管理_系统岗位类型管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('System_PositionType');
        });
    }
    else if (permissionName == "合同调配_合同状态管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('ContractState');
        });
    }
    else if (permissionName == "系统消息") {
        using(SCRIPTS_ROOT + '/oa/sms/SmsClass.js', function () {
            var smsClass = new SmsClass(token);
            smsClass.initModule();
        });
    }
    else if (permissionName == "OA_财务公司资金支付管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/oa/finance/FinancePayWFAClass.js', function () {
            var financePayWFAClass = new FinancePayWFAClass(token);
            financePayWFAClass.initModule();
        });
    }
    else if (permissionName == "资金支付管理_新") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/oa/finance/FinancePayWFANewClass.js', function () {
            var financePayWFAClass = new FinancePayWFANewClass(token);
            financePayWFAClass.initModule();
        });
    }
    else if (permissionName == "KV类别管理") {
        // alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('YDHP_KeShi_WaiKe');
        });
    }
    else if (permissionName == "权限管理") {
        using(SCRIPTS_ROOT + '/system/PermissionClass.js', function () {
            //alert("loaded...");
            var permissionClass = new PermissionClass(token);
            permissionClass.initModule();
        });
    }
    else if (permissionName == "客户信用管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('CreditRate');
        });
    }
    else if (permissionName == "人员规模管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('staffSize');
        });
    }
    else if (permissionName == "客户来源管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('CustomerSource');
        });
    }
    else if (permissionName == "客户种类管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('CustomerType');
        });
    }
    else if (permissionName == "客户职业管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Career');
        });
    }
    else if (permissionName == "客户性别管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Sex');
        });
    }
    else if (permissionName == "客户国籍管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Guoji');
        });
    }
    else if (permissionName == "短信管理") {
        //  alert(SCRIPTS_ROOT + '/oa/message/MessageClass.js');
        using(SCRIPTS_ROOT + '/oa/message/MessageClass.js', function () {
            var messageClass = new MessageClass(token);
            messageClass.initModule();
        });
    }
    else if (permissionName == "信用等级管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('CreditRate');
        });
    }
    else if (permissionName == "关系等级管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('RelationshipLevel');
        });
    }
    else if (permissionName == "投资方向管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('InvestmentDirection');
        });
    }
    else if (permissionName == "合作方管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Partner');
        });
    }
    else if (permissionName == "收支类型管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('OA_Finance_MoneyLog_InOrOut');
        });
    }
    else if (permissionName == "开户行管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Bank');
        });
    }
    else if (permissionName == "销售级别管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('sales_level');
        });
    }
    else if (permissionName == "收支类型管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('OA_Finance_MoneyLog_InOrOut');
        });
    }
    else if (permissionName == "客户关系状态") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('CRM_statusId');
        });
    }
    else if (permissionName == "证件类型管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Certificate');
        });
    }
    else if (permissionName == "状态管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Status');
        });
    }
    else if (permissionName == "收入类型管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('OA_Finance_MoneyLog_Type_In');
        });
    }
    else if (permissionName == "法律协议") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/info/LegalAgreementClass.js', function () {
            //alert("hello");
            var legalAgreementClass = new LegalAgreementClass(token);
            legalAgreementClass.initModule();
        });
    }
    else if (permissionName == "客户认证码") {
        using(SCRIPTS_ROOT + '/customer/AuthenticationCodeClass.js', function () {
            var authenticationCodeClass = new AuthenticationCodeClass(token);
            authenticationCodeClass.initModule();
        });
    }
    else if (permissionName == "差旅费报销") {
        using(SCRIPTS_ROOT + '/oa/expense/FinanceBizTripExpenseWFA4DetailClass.js', function () {
            var financeBizTripExpenseWFA4DetailClass = new FinanceBizTripExpenseWFA4DetailClass(token);
            financeBizTripExpenseWFA4DetailClass.initModule();
        });
    }
    else if (permissionName == "差旅费报销_新") {
        using(SCRIPTS_ROOT + '/oa/expense/FinanceBizTripExpenseWFA4DetailNewClass.js', function () {
            var financeBizTripExpenseWFA4DetailClass = new FinanceBizTripExpenseWFA4DetailNewClass(token);
            financeBizTripExpenseWFA4DetailClass.initModule();
        });
    }
    else if (permissionName == "固定资产申请") {
        using(SCRIPTS_ROOT + '/oa/assetFixation/AssetApplicationClass.js', function () {
            var assetApplicationClass = new AssetApplicationClass(token);
            assetApplicationClass.initModule();
        });
    }
    else if (permissionName == "客户充值") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerDepositClass.js', function () {
            //alert("hello");
            var customerDepositClass = new CustomerDepositClass(token);
            customerDepositClass.initModule();
        });
    }
    else if (permissionName == '系统统计') {
        using(SCRIPTS_ROOT + '/system/StatisticsClass.js', function () {
            var statisticsClass = new StatisticsClass(token);
            statisticsClass.initModule();
        });
    }
    // 客户提现
    else if (permissionName == "客户提现") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerWithdrawClass.js', function () {
            //alert("hello");
            var customerWithdrawClass = new CustomerWithdrawClass(token);
            customerWithdrawClass.initModule();
        });
    }
    else if (permissionName == "例子") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/example/MainTableClass.js', function () {
            //alert("hello");
            var mainTableClass = new MainTableClass(token);
            mainTableClass.initModule();
        });
    }
    else if (permissionName == "详细例子") {
        using(SCRIPTS_ROOT + '/example/MainTable4DetailClass.js', function () {
            var mainTable4DetailClass = new MainTable4DetailClass(token);
            mainTable4DetailClass.initModule();
        });
    }
    else if (permissionName == "销售管理_销售组管理") {
        using(SCRIPTS_ROOT + '/sale/SalesmanGroupClass.js', function () {
            var salesmanGroupClass = new SalesmanGroupClass(token, null);
            salesmanGroupClass.initModule();
        });
    }
    // Sale包下的投资计划
    else if (permissionName == "投资计划") {
        using(SCRIPTS_ROOT + '/sale/InvestmentPlanClass.js', function () {
            var investmentPlanClass = new InvestmentPlanClass(token);
            investmentPlanClass.initModule();
        });
    }
    else if (permissionName == "投资参与者") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/sale/InvestmentParticipantClass.js', function () {
            //alert("hello");
            var investmentParticipantClass = new InvestmentParticipantClass(token);
            investmentParticipantClass.initModule();
        });
    }
    //投资参与类型管理
    else if (permissionName == "产品管理_投资参与类型管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Sale_InvestmentParticipantJoinType');
        });
    }
    //投资参与状态管理
    else if (permissionName == "产品管理_投资参与状态管理") {
        //alert(SCRIPTS_ROOT+'/system/UserClass.js?VERSION='+VERSION);
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Sale_InvestmentParticipantJoinStatus');
        });
    }
    else if (permissionName == "系统文件") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/system/FilesClass.js', function () {
            //alert("hello");
            var filesClass = new FilesClass(token, "", "", "");
            filesClass.initModule();
        });
    }
    // 对应客户认证
    else if (permissionName == "客户认证") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerAuthenticationStatusClass.js', function () {
            //alert("hello");
            var customerAuthenticationStatusClass = new CustomerAuthenticationStatusClass(token);
            customerAuthenticationStatusClass.initModule();
        });
    }
    // 对应产品审核
    else if (permissionName == "产品审核") {

        using(SCRIPTS_ROOT + '/production/ProductionCheckClass.js', function () {

            var productionCheckClass = new ProductionCheckClass(token);
            productionCheckClass.initModule();
        });
    }
    //OA_任务
    else if (permissionName == "OA_任务") {

        using(SCRIPTS_ROOT + '/oa/task/TaskClass.js', function () {
            var taskClass = new TaskClass(token);
            taskClass.initModule();
        });
    }
    //WEB_AD
    else if (permissionName == "图片广告") {

        using(SCRIPTS_ROOT + '/web/AdImageClass.js', function () {
            var adImageClass = new AdImageClass(token);
            adImageClass.initModule();
        });
    }
    // 试题类型对应的KV管理
    else if (permissionName == "图片广告_归类管理") {
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Web_AdImageCatalog');
        });
    }
    else if (permissionName == "图片广告_使用管理") {
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('Is_Avaliable');
        });
    }
    else if (permissionName == "系统管理_日志管理") {
        using(SCRIPTS_ROOT + '/system/LogClass.js', function () {
            var logClass = new LogClass(token);
            logClass.initModule();
        });
    }
    //短信模板
    else if (permissionName == "内容模板") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/system/ContentTemplateClass.js', function () {
            var contentTemplateClass = new ContentTemplateClass(token);
            contentTemplateClass.initModule();
        });
    }
    // 对应production包的产品扩展信息模块
    else if (permissionName == "产品扩展信息") {
        using(SCRIPTS_ROOT + '/production/ProductionInfoClass.js', function () {
            var productionInfoClass = new ProductionInfoClass(token);
            productionInfoClass.initModule();
        });
    }
    // 试题
    else if (permissionName == "试题") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/info/ExamQuestionClass.js', function () {
            //alert("hello");
            var examQuestionClass = new ExamQuestionClass(token);
            examQuestionClass.initModule();
        });
    }
    // 试题类型对应的KV管理
    else if (permissionName == "培训管理_试题类型管理") {
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('INFO_ExamQuestionType');
        });
    }
    //部门类型KV
    else if (permissionName == "系统管理_部门类型管理") {
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('DepartmentType');
        });
    }
    // 试题选项
    else if (permissionName == "试题选项") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/info/ExamOptionClass.js', function () {
            //alert("hello");
            var examOptionClass = new ExamOptionClass(token, '', '', '');
            examOptionClass.initModule();
        });
    }
    else if (permissionName == "试题答案") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/info/ExamAnswerClass.js', function () {
            //alert("hello");
            var examAnswerClass = new ExamAnswerClass(token);
            examAnswerClass.initModule();
        });
    }
    else if (permissionName == "客户积分") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/customer/CustomerPointClass.js', function () {
            //alert("hello");
            var customerPointClass = new CustomerPointClass(token);
            customerPointClass.initModule();
        });
    }
    // 产品转让
    else if (permissionName == "产品转让") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/sale/ProductionTransferClass.js', function () {
            //alert("hello");
            var productionTransferClass = new ProductionTransferClass(token);
            productionTransferClass.initModule();
        });
    }
    // 产品转让
    else if (permissionName == "验证码") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/system/CodeClass.js', function () {
            //alert("hello");
            var codeClass = new CodeClass(token);
            codeClass.initModule();
        });
    }
    // 产品销售管理
    else if (permissionName == "产品销售管理") {
        using(SCRIPTS_ROOT + '/sale/SaleTask4GroupClass.js', function () {
            var saleTask4GroupClass = new SaleTask4GroupClass(token);
            saleTask4GroupClass.initModule();
        });
    }
    // 对内及对外信息报送审批
    else if (permissionName == "对外资料报送") {
        using(SCRIPTS_ROOT + '/oa/Information/InformationSubmittedClass.js', function () {
            var informationSubmittedClass = new InformationSubmittedClass(token);
            informationSubmittedClass.initModule();
        });
    } // 对内及对外信息报送审批
    else if (permissionName == "对外资料报送2") {
        using(SCRIPTS_ROOT + '/oa/Information/InformationSubmitted2Class.js', function () {
            var informationSubmitted2Class = new InformationSubmitted2Class(token);
            informationSubmitted2Class.initModule();
        });
    }
    // 固定资产信息
    else if (permissionName == "固定资产信息") {
        using(SCRIPTS_ROOT + '/oa/assetInfo/AssetInfoClass.js', function () {
            var assetInfoClass = new AssetInfoClass(token);
            assetInfoClass.initModule();
        });
    }
    // 用户部门归属
    else if (permissionName == "用户部门归属") {
        using(SCRIPTS_ROOT + '/system/PositionUserClass.js', function () {
            var positionUserClass = new PositionUserClass(token);
            positionUserClass.initModule();
        });
    } // 用户部门归属
    else if (permissionName == "收件箱") {
        using(SCRIPTS_ROOT + '/oa/email/FromEmailClass.js', function () {
            var fromEmailClass = new FromEmailClass(token);
            fromEmailClass.initModule();
        });
    }
    //厚币钱庄每日产品兑付金额
    else if (permissionName == "厚币钱庄每日产品兑付金额") {
        using(SCRIPTS_ROOT + '/report/ProductDailyPaymentReportClass.js', function () {
            var productDailyPaymentReportClass = new ProductDailyPaymentReportClass(token);
            productDailyPaymentReportClass.initModule();
        });
    }
    //厚币钱庄每日客户兑付金额
    else if (permissionName == "每日客户兑付金额") {
        using(SCRIPTS_ROOT + '/report/CustomerDailyPaymentReportClass.js', function () {
            var customerDailyPaymentReportClass = new CustomerDailyPaymentReportClass(token);
            customerDailyPaymentReportClass.initModule();
        });
    }
    // 客户退款
    else if (permissionName == "客户退款") {
        using(SCRIPTS_ROOT + '/customer/CustomerRefundClass.js', function () {
            var customerRefundClass = new CustomerRefundClass(token);
            customerRefundClass.initModule();
        });
    }
    else if (permissionName == "系统管理_消息类型模块管理") {
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule("System_MessageType");
        });
    }
    // 客户提现审查
    else if (permissionName == "客户管理_客户提现审查") {
        using(SCRIPTS_ROOT + '/customer/CustomerWithdrawVerifyClass.js', function () {
            var customerWithdrawVerifyClass = new CustomerWithdrawVerifyClass(token);
            customerWithdrawVerifyClass.initModule("System_MessageType");
        });
    }
    // 客户综合查询
    else if (permissionName == "客户综合查询") {
        using(SCRIPTS_ROOT + '/customer/CustomerPersonalQueryClass.js', function () {
            var customerPersonalQueryClass = new CustomerPersonalQueryClass(token);
            customerPersonalQueryClass.initModule(token);
        });
    }
    else if (permissionName == "日常办公_系统消息类型模块管理") {
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule("System_SmsType");
        });
    }
    else if (permissionName == "请假休假") {
        using(SCRIPTS_ROOT + '/oa/hr/HRLeaveClass.js', function () {
            var hRLeaveClass = new HRLeaveClass(token);
            hRLeaveClass.initModule();
        });
    } else if (permissionName == "借款申请") {
        using(SCRIPTS_ROOT + '/oa/borrow/BorrowMoneyClass.js', function () {
            var borrowMoneyClass = new BorrowMoneyClass(token);
            borrowMoneyClass.initModule();
        });
    }
    else if (permissionName == "日常办公_请假休假_请假休假类型管理") {
        using(SCRIPTS_ROOT + '/system/KVClass.js', function () {
            var kvClass = new KVClass(token);
            kvClass.initModule('oa_hrleave_hrleaveType');
        });
    }
    else if (permissionName == "报表_销售_产品销售报表") {
        using(SCRIPTS_ROOT + '/report/sale/ReportSaleProductionClass.js', function () {
            var reportSaleProductionClass = new ReportSaleProductionClass(token);
            reportSaleProductionClass.initModule();
        });
    }
    else if (permissionName == "报表_销售_客户销售报表") {
        using(SCRIPTS_ROOT + '/report/sale/ReportSaleCustomerClass.js', function () {
            var reportSaleCustomerClass = new ReportSaleCustomerClass(token);
            reportSaleCustomerClass.initModule();
        });
    } else if (permissionName == "销售合同申请") {
        using(SCRIPTS_ROOT + '/sale/contract/ApplicationContractClass.js', function () {
            var ApplicationContract = new ApplicationContractClass(token);
            ApplicationContract.initModule();
        });
    }
    else if (permissionName == "总部销售合同调配") {
        using(SCRIPTS_ROOT + '/sale/contract/ContractSendClass.js', function () {
            var contractSendClass = new ContractSendClass(token);
            contractSendClass.initModule();
        });
    }
    else if (permissionName == "财富中心销售合同调配") {
        using(SCRIPTS_ROOT + '/sale/contract/SendContractClass.js', function () {
            var sendContractClass = new SendContractClass(token);
            sendContractClass.initModule();
        });
    } else if (permissionName == "空白销售合同") {
        using(SCRIPTS_ROOT + '/sale/contract/BlankContractClass.js', function () {
            var blankContractClass = new BlankContractClass(token);
            blankContractClass.initModule();
        });
    } else if (permissionName == "我的销售合同") {
        using(SCRIPTS_ROOT + '/sale/contract/ContractClass.js', function () {
            var contractClass = new ContractClass(token);
            contractClass.initModule();
        });
    } else if (permissionName == "签约销售合同") {
        using(SCRIPTS_ROOT + '/sale/contract/SignedContractClass.js', function () {
            var signedContractClass = new SignedContractClass(token);
            signedContractClass.initModule();
        });
    }else if (permissionName == "异常销售合同") {
        using(SCRIPTS_ROOT + '/sale/contract/UnsaulContractClass.js', function () {
            var unsaulContractClass = new UnsaulContractClass(token);
            unsaulContractClass.initModule();
        });
    } else if (permissionName == "归档销售合同") {
        using(SCRIPTS_ROOT + '/sale/contract/ArchiveContractClass.js', function () {
            var archiveContractClass = new ArchiveContractClass(token);
            archiveContractClass.initModule();
        });
    }
    else if (permissionName == "合同综合查询") {
        using(SCRIPTS_ROOT + '/sale/contract/ContractCompositeSearchClass.js', function () {
            var contractCompositeSearchClass = new ContractCompositeSearchClass(token);
            contractCompositeSearchClass.initModule();
        });
    }
    else if (permissionName == "订单管理2") {
        //alert("begin");
        using(SCRIPTS_ROOT + '/production/OrderClass.js', function () {
            //alert("hello");
            var orderClass = new OrderClass(token);
            orderClass.initModule();
        });
    }else if (permissionName == "我的归档合同") {
        using(SCRIPTS_ROOT + '/sale/contract/HistoryContractClass.js', function () {
            var historyContractClass = new HistoryContractClass(token);
            historyContractClass.initModule();
        });
    }


    // 综合查询=》兑付综合查询
    else if (permissionName == "兑付综合查询") {
            using(SCRIPTS_ROOT + '/sale/PaymentPlanQueryClass.js', function () {
                var paymentPlanQueryClass = new PaymentPlanQueryClass(token);
                paymentPlanQueryClass.initModule();
            });
        }

    //对外销售人员管理
    else if (permissionName == "对外销售人员管理") {
            using(SCRIPTS_ROOT+'/customer/SalemanOuterClass.js', function () {
                var salemanOuterClass = new SalemanOuterClass(token);
                salemanOuterClass.initModule();
            });
        }
    else if (permissionName == "返佣管理") {
            using(SCRIPTS_ROOT+'/sale/ProductionCommissionClass.js', function () {
                var productionCommissionClass = new ProductionCommissionClass(token);
                productionCommissionClass.initModule();
            });
        }


        else {
            $.messager.alert('警告', '没有找到业务对象' + permissionName);
            var tab = $('#contentTabs').tabs('getSelected');
            var index = $('#contentTabs').tabs('getTabIndex', tab);
            $('#contentTabs').tabs('clo' + 'se', index);
            hmMenu.remove(token);
        }
}
function contentTabReload(permissionName, token) {
    if (permissionName == "系统测试") {

    }
    else {
        //$.messager.alert('警告','没有找到业务对象 ' + permissionName);

//        var tab = $('#contentTabs').tabs('getSelected');
//        var index = $('#contentTabs').tabs('getTabIndex',tab);
//        $('#contentTabs').tabs('close',index);
    }
}