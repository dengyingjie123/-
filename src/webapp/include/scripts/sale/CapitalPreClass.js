/**
 * 兑付计划管理类
 * @see 此处保存Confluence URL
 * @param token
 * @returns {{initModule: Function}}
 * @constructor
 */
var CapitalPreClass = function (token) {


    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickCapitalPreSearch();
        // 初始化查询重置事件
        onClickCapitalPreSearchReset();

        // 初始化表格
        initCapitalPreTable();

        // 初始化查询表单
        initCapitalPreSearchForm();



    }





    /**
     * 初始化查询表单
     */
    function initCapitalPreSearchForm() {
    }

    // 构造初始化表格脚本
    function initCapitalPreTable() {
        var strTableId = 'CapitalPreTable' + token;
        var url = WEB_ROOT + "/sale/PaymentPlanMoneyPrepare_list.action";

        //设置datagrid
        $('#' + strTableId).datagrid({
            title: '资金准备',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,//行是否适应屏幕
            singleSelect: false,//是否只能选择一行
            pageList: [10, 20, 30],//设置每页显示多少条
            pageSize: 10,//初始化每页显示多少
            rownumbers: true,//是否显示行号
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);// 解析从数据库返回的数据
                    return data;
                }
                catch (e) {
                }
            },
            pagination: true,//是否分頁
            frozenColumns: [
                [  // 固定列，没有滚动条
                    {field: 'ck', checkbox: true},
                    {field: 'paymentTime', title: '兑付日期'},
                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    {field: 'totalRecord', title: '总兑付条数'},
                    {field: 'totalPaymentPrincipalMoney', title: '预期兑付本金',
                        formatter: function (value, row, index) {
                            return fw.formatMoney(row['totalPaymentPrincipalMoney']);
                        }
                    },
                    {field: 'totalProfitMoney', title: '预期兑付收益',
                        formatter: function (value, row, index) {
                            return fw.formatMoney(row['totalProfitMoney']);
                        }
                    }
                ]
            ],
            //设置按钮的文字与图片
            toolbar: [

                {
                    id: 'btnCreateURL' + token,
                    text: '资金准备申请',
                    iconCls: 'icon-add'
                }

            ],
            //加载成后做的事
            onLoadSuccess: function () {
                 //资金准备申请
                createURL();
            }
        });
    }
    
    
    function createURL() {
        var buttonId = "btnCreateURL" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelections('CapitalPreTable' + token, function (sections) {
                var startTime=sections[0].paymentTime;
                var endTime=sections[sections.length-1].paymentTime;
                var url= "http://" + document.location.host+WEB_ROOT + '/modules/sale/PaymentPlanMoneyPrepare_Save.jsp?token=1905CF72';
                url+="&startTime="+startTime;
                url+="&endTime="+endTime;

                fw.alert("URL",url);


            });
        });
    }

    function initCapitalPreDetailTable(data) {
        var strTableId = 'CapitalPreDetailTable' + token;
        //设置datagrid

        var total = 0;
        $('#' + strTableId).datagrid({
            title: '资金准备',
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            url: null,
            fitColumns: false,//行是否适应屏幕
            singleSelect: true,//是否只能选择一行
            rownumbers: true,//是否显示行号
            pagination: false,//是否分頁
            frozenColumns: [
                [  // 固定列，没有滚动条

                ]
            ],
            //表格显示的列
            //列名对应数据库字段。
            columns: [
                [
                    {field: 'customerName', title: '客户姓名'},
                    {field: 'productName', title: '产品名称'},
                    {field: 'paymentTime', title: '兑付日期'},
                    {field: 'totalPaymentPrincipalMoney', title: '预期兑付本金',
                        formatter: function (value, row, index) {
                            total += row['totalPaymentPrincipalMoney'];
                            return fw.formatMoney(row['totalPaymentPrincipalMoney']);
                        }
                    },
                    {field: 'totalProfitMoney', title: '预期兑付收益',
                        formatter: function (value, row, index) {
                            total += row['totalProfitMoney'];
                            return fw.formatMoney(row['totalProfitMoney']);
                        }
                    }
                ]
            ],
            //加载成后做的事
            onLoadSuccess: function () {
                $('.datagrid-cell').css('font-size','18px');
            }
        });

        $('#' + strTableId).datagrid('loadData',data);

        var m = fw.formatMoney(total);
        fw.textSetValue('money'+token, m);
    }

    /* /!**
     * 初始化弹出窗口
     * @param data
     *!/
     function initProductionCommissionWindow(data) {

     // fw.alertReturnValue(data);

     data['productionCommission.operatorId'] = loginUser.getId();
     data['productionCommission.operatorName'] = loginUser.getName();

     // todo cm 修改url
     var url = WEB_ROOT + '/modules/sale/ProductionCommission_Save.jsp?token=' + token;
     var windowId = 'productionCommissionWindow' + token;
     fw.window(windowId, '窗口', 650, 550, url, function () {
     //提交事件
     onClickProductionCommissionSubmit();


     // 加载数据
     fw.formLoad('formProductionCommission' + token, data);

     });
     }*/

    /**
     * 数据提交事件
     */
    /*  function onClickProductionCommissionSubmit() {
     var buttonId = 'btnProductionCommissionSubmit' + token;
     fw.bindOnClick(buttonId, function(process){
     var formId = 'formProductionCommission' + token;
     // todo cm modify url
     var url = WEB_ROOT + '/sale/ProductionCommission_insertOrUpdate.action';
     fw.bindOnSubmitForm(formId, url, function(){
     process.beforeClick();
     }, function() {
     process.afterClick();
     fw.datagridReload('productionCommissionTable'+token);
     fw.windowClose('productionCommissionWindow'+token);
     }, function() {
     process.afterClick();
     });
     });
     }*/

    function onClickBusinessTripApplicationAdd() {
        var buttonId = 'btnBusinessTripApplicationAdd' + token;
        fw.bindOnClick(buttonId, function (process) {
            initBusinessTripApplicationWindow({controlString1:"",controlString2:""}, "add");
        });
    }







    function onClickbtnCapitalPreApply() {
        var buttonId = 'btnBusinessTripApplicationEditCheck' + token;
        fw.bindOnClick(buttonId, function (process) {




            /*fw.datagridGetSelected('BusinessTripApplicationTable' + token, function (selected) {
                process.beforeClick();
                if ("申请" != selected.currentNodeTitle) {
                    fw.alert("警告", "该数据已经提交申请不得重复提交");
                    process.afterClick();
                    return;
                }
                var id = selected.id
                var url = WEB_ROOT + '/oa/business/BusinessTripApplication_load.action?businessTripApplication.id=' + selected.id;
                fw.post(url, null, function (data) {
                    //设置所有业务数据
                    data["workflowID"] =12;

                    data["nextNode"] = 2;
                    //设置业务编号
                    data["id"] = id;
                    //设置路由编号
                    data["routeListId"] = selected.routeListId;
                    //设置节点编号
                    data["currentNodeId"] = selected.currentNodeId;

                    data["controlString1"] = selected.controlString1Id;
                    data["controlString3"] = selected.controlString3;
                    data["controlString2"] = selected.controlString2Id;
                    data["currentNodeTitle"] =selected.currentNodeTitle;
                    //设置窗口审核状态
                    initBusinessTripApplicationWindow(data, "applay");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });*/
        });
    }


    /**
     * 修改事件
     */
    function onClickBusinessTripApplicationEdit(startTime,endTime) {
        var url = WEB_ROOT + '/sale/PaymentPlanMoneyPrepare_getCapitalPreDetail.action';
        url+="?startTime="+startTime;
        url+="&endTime="+endTime;
        fw.post(url, null, function (data) {
            initBusinessTripApplicationWindow2(data, "edit");
        }, function () {
        });
    }




    function initBusinessTripApplicationWindow(data, BtnStatus) {



        var url = WEB_ROOT + '/modules/sale/PaymentPlanMoneyPrepare_Save.jsp?token=' + token;

        var windowId = 'BusinessTripApplicationWindow' + token;
        fw.window(windowId, '资金准备', 600, 550, url, function () {
            fw.textFormatCurrency('expenseBudge' + token);
            fw.textFormatCurrency('expenseActual' + token);

            //判断下列是否固定
            var temp =false;
            //initUserName();
            //窗口状态为查看状态时
            if ("look" == BtnStatus) {
                $("#btnBusinessSubmit_start" + token).remove();//移除业务审批按钮
                $("#btnBusinessSubmit_applay" + token).remove();//移除业务审批按钮
                $("#btnBusinessSubmit" + token).remove();//移除提交按钮
            }
            //按钮状态为检查状态
            else if ("check" == BtnStatus) {
                temp=true;
                $("#btnBusinessSubmit" + token).remove();//移除提交按钮
                $("#btnBusinessSubmit_applay" + token).remove();//移除提交按钮
                //业务审核页面
                fw.onClickBizSubmit(token, "btnBusinessSubmit_start" + token,
                    data, "BusinessTripApplicationTable" + token,
                    'WaitBusinessTripApplicationTable' + token, 'ParticipantBusinessTripApplicationTable' + token, windowId);

            }  else if ("applay" == BtnStatus) {
                temp=true;
                $("#btnBusinessSubmit" + token).remove();//移除提交按钮
                $("#btnBusinessSubmit_start" + token).remove();//移除业务审批按钮

                onClickBusinessTripApplicationApplay(data,windowId);
            } else if ("add" == BtnStatus || "edit" == BtnStatus) {
                if ("add" == BtnStatus) {

                    data['businessTripApplication.operatorSign'] = loginUser.getName();
                }
              
                $("#btnBusinessSubmit_start" + token).remove();//移除业务审批按钮
                $("#btnBusinessSubmit_applay" + token).remove();//移除业务审批按钮
               initCapitalPreDetailTable(data[0]['paymentPlanVOs']);


            }

            // 加载数据
            fw.formLoad('formBusinessTripApplication' + token, data[0]['capitalPreVO']);

        });
    }


    function initBusinessTripApplicationWindow2(data, BtnStatus) {

        // 加载数据
        fw.formLoad('formBusinessTripApplication' + token, data[0]['capitalPreVO']);

        if ("add" == BtnStatus || "edit" == BtnStatus) {
            initCapitalPreDetailTable(data[0]['paymentPlanVOs']);
        }

    }





    /**
     * 查询事件
     */
    function onClickCapitalPreSearch() {
        var buttonId = 'btnCapitalPreSearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'CapitalPreTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;


            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickCapitalPreSearchReset() {
        var buttonId = 'btnCapitalPreSearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
        });
    }

    return {
        /**
         * boot.js 加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        },
        initModuleForJsp:function (startTime,endTime) {
            //加载数据
            onClickBusinessTripApplicationEdit(startTime,endTime);
        }
    };
}




















