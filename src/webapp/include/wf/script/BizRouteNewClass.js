
/**
 * 该js主要用于控制工作流业务流程。
 * @param token
 * @param YWData
 * @param TableId 我的申请列表
 * @param TableId2 等待我审核列表
 * @param TableId3 已完成列表
 * @param windowID 窗口
 * @param btn1 同意按钮
 * @param btn2 完成按钮
 * @param btn3 回退按钮
 * @param btn4 中止按钮
 * @returns {{initModule: initModule}}
 * @constructor
 */
var BizRouteNewClass = function (token, YWData,
                                 TableId, TableId2, TableId3,
                                 windowID,
                                 btn1, btn2, btn3, btn4) {
    function initAll() {
        //初始获取业务数据的方法
        onClickBizRoute(YWData, btn1, btn2, btn3, btn4);
    }

    /**
     * 根据业务编号 获取业务数据
     */
    function onClickBizRoute(YWData, btn1, btn2, btn3, btn4) {

        //获取当前业务编号的业务流数据
        var url = WEB_ROOT + "/wf/BizRoute_load.action?bizRoute.id_ywid=" + YWData['YWID'];

        //请求数据
        fw.post(url, null, function (data) {
            //当路由届满编号为0
            if (YWData['RouteListID'] == '0') {
                //设置申请人
                data["applicationName"] = loginUser.getName;
                //设置申请时间
                data["bizRoute.applicationTime"] = fw.getDateTime();
                //设置申请编号
                data["bizRoute.applicationId"] = loginUser.getId;
            }

            //将业务数据封装到数据数组中
            data["YWID"] = YWData['YWID'];
            data["WorkflowID"] = YWData['WorkflowID'];
            data["bizRoute.workflowId"] = YWData['WorkflowID'];
            data["RouteListID"] = YWData['RouteListID'];
            data["CurrentNode"] = YWData['CurrentNode'];
            data["controlString1"] = YWData['controlString1'];
            data["controlString2"] = YWData['controlString2'];
            data["bizRoute.serviceClassName"] = YWData['serviceClassName'];
            data["Participant"] = loginUser.getId();

            //初始化业务数据控制内容
            initBizRoutePanel(data, btn1, btn2, btn3, btn4);

        }, function () {

        });
    }

    /**
     * 初始化工作流Panel
     */
    function initBizRoutePanel(data, btn1, btn2, btn3, btn4) {
        // 加载数据
        fw.formLoad('formBizRoute' + token, data);
        //将数据ID设置到表单中
        $("#yWID" + token).val(data["YWID"]);

        //用来控制业务节点可编辑的项 返回状态文本框对象
        var statusobj = returnObj();
        checkFromEditable();
        // 业务转发提交事件(同意)
        onClickBizRouteSubmit(statusobj, btn1);
        // 业务结束事件(完成)
        onClickBinRouteOver(statusobj, btn2);
        // 业务回退事件(回退)
        onClickBinRouteReject(statusobj, btn3);
        // 业务中止事件(中止)
        onClickBinRouteCancel(statusobj, btn4);
    }

    /***
     * 业务提交事件
     *
     * 修改：周海鸿
     * 内容：完成详细注释
     * 时间：2015年 6月17日 09:24:00
     *
     */
    function onClickBizRouteSubmit(statusobj, btn) {
        //设置提交按钮
        var buttonId = btn + token;

        //设置提交表单编号
        var formId = "formBizRoute" + token;

        //设置业务提交URL
        var url = WEB_ROOT + "/wf/Workflow_service.action";

        //初始化点击事件
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("提示", "是否同意提交业务", function () {
                //设置表单提交数据 跟业务有关
                //表单完成处理转向的URL
                $('#TargetURL' + token).val('/wf/Done.jsp');
                //业务服务
                $('#ServiceType' + token).val('AUTOFORWARD');
                //流业务数据操作对象的类名
                $('#BizDaoName' + token).val('com.youngbook.entity.po.wf.BizRoutePO');
                //流业务数据操作对象的类名前缀
                $('#JsonPrefix' + token).val('bizRoute');

                //提交表达
                fw.formSubmit(formId, url, buttonId, function () {
                    if (!fw.checkIsNullObject(statusobj)) {
                        statusobj.val("1");
                    }
                }, function (data) {
                    //判断数据源是否符合要求
                    data = fw.convert2Json(data);

                    //数据码不等于100 报错
                    if (data.code != 100) {
                        process.afterClick();
                        fw.alert('错误', data.message);
                    }
                    else {

                        //重新重新刷星指定列表
                        fw.datagridReload(TableId);
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId2);
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId3);
                        fw.confirm("提示", "业务已提交", function () {
                            //关闭业务窗口
                            fw.windowClose('bizRouteWindow' + token);
                            fw.windowClose(windowID);

                        })
                    }
                });
            });
        });
    }

    /**
     * 业务流转结束事件
     *
     * 修改：周海鸿
     * 内容：完成详细注释
     * 时间：2015年 6月17日 09:24:00
     *
     *
     */
    function onClickBinRouteOver(statusobj, btn) {
            //设置结束按钮id
            var buttonId = btn + token;

            //设置表单id
            var formId = "formBizRoute" + token;

            //设置业务结束URL
            var url = WEB_ROOT + "/wf/Workflow_service.action";

            //点击事件
            fw.bindOnClick(buttonId, function (process) {

                fw.confirm("提示", "是否同意结束业务", function () {
                //设置表单提交数据 跟业务有关
                //表单完成处理转向的URL
                $('#TargetURL' + token).val('/wf/Done.jsp');
                //业务服务
                $('#ServiceType' + token).val('Over');
                //流业务数据操作对象的类名
                $('#BizDaoName' + token).val('com.youngbook.entity.po.wf.BizRoutePO');
                //流业务数据操作对象的类名前缀
                $('#JsonPrefix' + token).val('bizRoute');

                //提交表单
                fw.formSubmit(formId, url, buttonId, function () {
                    if (!fw.checkIsNullObject(statusobj)) {
                        statusobj.val("1");
                    }
                }, function (data) {

                    //判断数据源是否符合要求
                    data = fw.convert2Json(data);

                    //数据码不等于100 报错
                    if (data.code != 100) {
                        process.afterClick();
                        fw.alert('错误', data.message);
                    }
                    else {
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId);
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId2);
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId3);

                        fw.confirm("提示", "业务已完成", function () {
                            //关闭业务窗口
                            fw.windowClose('bizRouteWindow' + token);
                            fw.windowClose(windowID);

                        })
                    }

                });
            });
        });
    }

    /**
     * 业务不同意返回上一个节点
     * @param statusobj
     */
    function onClickBinRouteReject(statusobj, btn) {
        //设置结束按钮id
        var buttonId = btn + token;

        //点击事件
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            var param = "?&WorkflowID=" + YWData['WorkflowID'] + "&CurrNodeID=" + YWData['CurrentNode'] + "&token=" + token;
            //弹出选择
            var windowurl = WEB_ROOT + "/include/wf/bizRoute/bizRouteSeelctRejectNextNote.jsp" + param;
            fw.window("RouteRejectWindow" + token, "选择回退", 500, 200, windowurl, function (statusobj) {
                process.afterClick();
                onClickBinRouteRejectSubmit(statusobj);
            }, function () {
                process.afterClick();
            }, statusobj)
        });
    }

    /**
     *回退提交时间
     */
    function onClickBinRouteRejectSubmit(statusobj) {

        //设置结束按钮id
        var buttonId = "btnok" + token;

        //点击事件
        fw.bindOnClick(buttonId, function (process) {
            fw.confirm("提示", "是否回退业务", function () {
                process.beforeClick();

                var NextNode = $("#NextNode" + token).val();
                if(NextNode == "" || NextNode ==  null){
                    process.afterClick();
                    fw.alert("警告","请选择回退节点");
                    return ;
                }
                //设置表单id
                var formId = "formBizRoute" + token;

                //设置表单提交数据 跟业务有关
                //获取选中的下个节点的值
                //表单完成处理转向的URL
                $('#TargetURL' + token).val('/wf/Done.jsp');
                //业务服务
                $('#ServiceType' + token).val('SAVEFORWARD');
                //流业务数据操作对象的类名
                $('#BizDaoName' + token).val('com.youngbook.entity.po.wf.BizRoutePO');
                //流业务数据操作对象的类名前缀
                $('#JsonPrefix' + token).val('bizRoute');

                //设置业务结束URL
                var url = WEB_ROOT + "/wf/Workflow_service.action?NextNode=" + NextNode;
                //提交表单
                fw.formSubmit(formId, url, buttonId, function () {
                    if (!fw.checkIsNullObject(statusobj)) {
                        statusobj.val("2");
                    }
                }, function (data) {

                    //判断数据源是否符合要求
                    data = fw.convert2Json(data);

                    //数据码不等于100 报错
                    if (data.code != 100) {
                        process.afterClick();
                        fw.alert('错误', data.message);
                    }
                    else {


                        //重新重新刷星指定列表
                        fw.datagridReload(TableId);
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId2);
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId3);

                        fw.confirm("提示", "业务已返回", function () {
                            //关闭业务窗口
                            fw.windowClose('bizRouteWindow' + token);
                            fw.windowClose(windowID);
                            fw.windowClose("RouteRejectWindow" + token);

                        })
                    }
                });
            });
        });
    }

    /**
     * 业务中止事件
     * @param statusobj 需要改变下拉值得对象
     */
    function onClickBinRouteCancel(statusobj, btn) {
            //设置结束按钮id
            var buttonId = btn + token;

            //设置表单id
            var formId = "formBizRoute" + token;

            //设置业务结束URL
            var url = WEB_ROOT + "/wf/Workflow_service.action";

            //点击事件
            fw.bindOnClick(buttonId, function (process) {

                fw.confirm("提示", "是否中止业务", function () {
                //设置表单提交数据 跟业务有关
                //表单完成处理转向的URL
                $('#TargetURL' + token).val('/wf/Done.jsp');
                //业务服务
                $('#ServiceType' + token).val('CANCEL');
                //流业务数据操作对象的类名
                $('#BizDaoName' + token).val('com.youngbook.entity.po.wf.BizRoutePO');
                //流业务数据操作对象的类名前缀
                $('#JsonPrefix' + token).val('bizRoute');

                //提交表单
                fw.formSubmit(formId, url, buttonId, function () {
                    if (!fw.checkIsNullObject(statusobj)) {
                        statusobj.val("2");
                    }
                }, function (data) {

                    //判断数据源是否符合要求
                    data = fw.convert2Json(data);

                    //数据码不等于100 报错
                    if (data.code != 100) {
                        process.afterClick();
                        fw.alert('错误', data.message);
                    }
                    else {


                        //重新重新刷星指定列表
                        fw.datagridReload(TableId);
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId2);
                        //重新重新刷星指定列表
                        fw.datagridReload(TableId3);

                        fw.confirm("提示", "业务已中止", function () {
                            //关闭业务窗口
                            fw.windowClose('bizRouteWindow' + token);
                            fw.windowClose(windowID);

                        })
                    }

                });
            });
        });
    }

    /**
     * 初始化检查当前人物是否有权限编辑
     */
    function checkFromEditable() {
        //根据业务流判断当前角色否拥有会计的编辑权限
        if (!fw.checkWfEditable('accountingContent' + token)) {
            $('#accountingContent' + token).attr('readonly', 'readonly');
            $('#accountingName' + token).attr('readonly', 'readonly');
            $('#accountingTime' + token).attr('readonly', 'readonly');
            $('#accountingStatus' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#accountingTime' + token).val(fw.getDateTime());
            $('#accountingName' + token).val(loginUser.getName());
            $("#accountingId" + token).val(loginUser.getId());
            $('#accountingName' + token).attr('readonly', 'readonly');
            $('#accountingTime' + token).attr('readonly', 'readonly');
        }
        //根据业务流判断当前角色是否拥有部门负责人的编辑权限
        if (!fw.checkWfEditable('departmentLeaderContent' + token)) {
            $('#departmentLeaderContent' + token).attr('readonly', 'readonly');
            $('#departmentLeaderName' + token).attr('readonly', 'readonly');
            $('#departmentLeaderTime' + token).attr('readonly', 'readonly');
            $('#departmentLeaderStatus' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#departmentLeaderTime' + token).val(fw.getDateTime());
            $('#departmentLeaderName' + token).val(loginUser.getName());
            $("#departmentLeaderId" + token).val(loginUser.getId());
            $('#departmentLeaderName' + token).attr('readonly', 'readonly');
            $('#departmentLeaderTime' + token).attr('readonly', 'readonly');
        }
        //根据业务流判断当觉是否拥有分管领导的编辑权限
        if (!fw.checkWfEditable('chargeLeaderContent' + token)) {
            $('#chargeLeaderContent' + token).attr('readonly', 'readonly');
            $('#chargeLeaderName' + token).attr('readonly', 'readonly');
            $('#chargeLeaderTime' + token).attr('readonly', 'readonly');
            $('#chargeLeaderStatus' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#chargeLeaderTime' + token).val(fw.getDateTime());
            $('#chargeLeaderName' + token).val(loginUser.getName());
            $("#chargeLeaderId" + token).val(loginUser.getId());
            $('#chargeLeaderName' + token).attr('readonly', 'readonly');
            $('#chargeLeaderTime' + token).attr('readonly', 'readonly');
        }
        //根据业务流判断当前是否拥有财务总监的编辑权限
        if (!fw.checkWfEditable('financeDirectorContent' + token)) {
            $('#financeDirectorContent' + token).attr('readonly', 'readonly');
            $('#financeDirectorName' + token).attr('readonly', 'readonly');
            $('#financeDirectorTime' + token).attr('readonly', 'readonly');
            $('#financeDirectorStatus' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#financeDirectorTime' + token).val(fw.getDateTime());
            $('#financeDirectorName' + token).val(loginUser.getName());
            $("#financeDirectorId" + token).val(loginUser.getId());
            $('#financeDirectorName' + token).attr('readonly', 'readonly');
            $('#financeDirectorTime' + token).attr('readonly', 'readonly');
        }
        //根据业务流判断当前是否拥有所属总经理的编辑权限
        if (!fw.checkWfEditable('generalManagerContent' + token)) {
            $('#generalManagerContent' + token).attr('readonly', 'readonly');
            $('#generalManagerName' + token).attr('readonly', 'readonly');
            $('#generalManagerTime' + token).attr('readonly', 'readonly');
            $('#generalManagerStatus' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#generalManagerTime' + token).val(fw.getDateTime());
            $('#generalManagerName' + token).val(loginUser.getName());
            $("#generalManagerId" + token).val(loginUser.getId());
            $('#generalManagerName' + token).attr('readonly', 'readonly');
            $('#generalManagerTime' + token).attr('readonly', 'readonly');
        }
        //根据业务流判断当前是否拥有执行董事的编辑权限
        if (!fw.checkWfEditable('executiveDirectorContent' + token)) {
            $('#executiveDirectorContent' + token).attr('readonly', 'readonly');
            $('#executiveDirectorName' + token).attr('readonly', 'readonly');
            $('#executiveDirectorTime' + token).attr('readonly', 'readonly');
            $('#executiveDirectorStatus' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#executiveDirectorTime' + token).val(fw.getDateTime());
            $('#executiveDirectorName' + token).val(loginUser.getName());
            $("#executiveDirectorId" + token).val(loginUser.getId());
            $('#executiveDirectorName' + token).attr('readonly', 'readonly');
            $('#executiveDirectorTime' + token).attr('readonly', 'readonly');
        }
        //根据业务流判断当前是否拥有出纳的编辑权限
        if (!fw.checkWfEditable('cashierContent' + token)) {
            $('#cashierContent' + token).attr('readonly', 'readonly');
            $('#cashierName' + token).attr('readonly', 'readonly');
            $('#cashierTime' + token).attr('readonly', 'readonly');
            $('#cashierStatus' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#cashierTime' + token).val(fw.getDateTime());
            $('#cashierName' + token).val(loginUser.getName());
            $("#cashierId" + token).val(loginUser.getId());
            $('#cashierName' + token).attr('readonly', 'readonly');
            $('#cashierTime' + token).attr('readonly', 'readonly');
        }


        /**
         * 修改人周海鸿
         * 修改时间 2015-6-30
         * 修改事件 实现特殊字段的可编辑性
         */
        //根据业务流判断当前是否拥有ID1的编辑权限
        if (!fw.checkWfEditable('content1' + token)) {
            $('#content1' + token).attr('readonly', 'readonly');
            $('#name1' + token).attr('readonly', 'readonly');
            $('#time1' + token).attr('readonly', 'readonly');
            $('#status1' + token).attr('readonly', 'readonly');

        } else {
            //将当前人信息绑定到文本框中
            $('#time1' + token).val(fw.getDateTime());
            $('#name1' + token).val(loginUser.getName());
            $("#id1" + token).val(loginUser.getId());
            $('#name1' + token).attr('readonly', 'readonly');
            $('#time1' + token).attr('readonly', 'readonly');
        }
        //根据业务流判断当前是否拥有ID2的编辑权限
        if (!fw.checkWfEditable('content2' + token)) {
            $('#content2' + token).attr('readonly', 'readonly');
            $('#name2' + token).attr('readonly', 'readonly');
            $('#time2' + token).attr('readonly', 'readonly');
            $('#status2' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#time2' + token).val(fw.getDateTime());
            $('#name2' + token).val(loginUser.getName());
            $("#id2" + token).val(loginUser.getId());
            $('#name2' + token).attr('readonly', 'readonly');
            $('#time2' + token).attr('readonly', 'readonly');
        }
        //根据业务流判断当前是否拥有ID3的编辑权限
        if (!fw.checkWfEditable('content3' + token)) {
            $('#content3' + token).attr('readonly', 'readonly');
            $('#name3' + token).attr('readonly', 'readonly');
            $('#time3' + token).attr('readonly', 'readonly');
            $('#status3' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#time3' + token).val(fw.getDateTime());
            $('#name3' + token).val(loginUser.getName());
            $("#id3" + token).val(loginUser.getId());
            $('#name3' + token).attr('readonly', 'readonly');
            $('#time3' + token).attr('readonly', 'readonly');
        }

        //根据业务流判断当前是否拥有ID4的编辑权限
        if (!fw.checkWfEditable('content4' + token)) {
            $('#content4' + token).attr('readonly', 'readonly');
            $('#name4' + token).attr('readonly', 'readonly');
            $('#time4' + token).attr('readonly', 'readonly');
            $('#status4' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#time4' + token).val(fw.getDateTime());
            $('#name4' + token).val(loginUser.getName());
            $("#id4" + token).val(loginUser.getId());
            $('#name4' + token).attr('readonly', 'readonly');
            $('#time4' + token).attr('readonly', 'readonly');
        }

        //根据业务流判断当前是否拥有ID5的编辑权限
        if (!fw.checkWfEditable('content5' + token)) {
            $('#content5' + token).attr('readonly', 'readonly');
            $('#name5' + token).attr('readonly', 'readonly');
            $('#time5' + token).attr('readonly', 'readonly');
            $('#status5' + token).attr('readonly', 'readonly');
        } else {
            //将当前人信息绑定到文本框中
            $('#time5' + token).val(fw.getDateTime());
            $('#name5' + token).val(loginUser.getName());
            $("#id5" + token).val(loginUser.getId());
            $('#name5' + token).attr('readonly', 'readonly');
            $('#time5' + token).attr('readonly', 'readonly');

        }

    }

    function returnObj() {
        //根据业务流判断当前角色否拥有会计的编辑权限
        if (fw.checkWfEditable('accountingContent' + token)) {
            return $("#accountingStatus" + token);
        }
        //根据业务流判断当前角色是否拥有部门负责人的编辑权限
        if (fw.checkWfEditable('departmentLeaderContent' + token)) {
            return $("#departmentLeaderStatus" + token);
        }
        //根据业务流判断当觉是否拥有分管领导的编辑权限
        if (fw.checkWfEditable('chargeLeaderContent' + token)) {
            return $("#chargeLeaderStatus" + token);
        }
        //根据业务流判断当前是否拥有财务总监的编辑权限
        if (fw.checkWfEditable('financeDirectorContent' + token)) {
            return $("#financeDirectorStatus" + token);
        }
        //根据业务流判断当前是否拥有所属总经理的编辑权限
        if (fw.checkWfEditable('generalManagerContent' + token)) {
            return $("#generalManagerStatus" + token);
        }
        //根据业务流判断当前是否拥有执行董事的编辑权限
        if (fw.checkWfEditable('executiveDirectorContent' + token)) {
            return $("#executiveDirectorStatus" + token);
        }
        //根据业务流判断当前是否拥有出纳的编辑权限
        if (fw.checkWfEditable('cashierContent' + token)) {
            return $("#cashierStatus" + token);
        }
        /**
         * 修改人周海鸿
         * 修改时间 2015-6-30
         * 修改事件 实现特殊字段的可编辑性
         */
        //根据业务流判断当前是否拥有ID1的编辑权限
        if (fw.checkWfEditable('content1' + token)) {
            return $("#status1" + token);
        }
        //根据业务流判断当前是否拥有ID2的编辑权限
        if (fw.checkWfEditable('content2' + token)) {
            return $("#status2" + token);
        }
        //根据业务流判断当前是否拥有ID3的编辑权限
        if (fw.checkWfEditable('content3' + token)) {
            return $("#status3" + token);
        }

        //根据业务流判断当前是否拥有ID4的编辑权限
        if (fw.checkWfEditable('content4' + token)) {
            return $("#status4" + token);
        }
        //根据业务流判断当前是否拥有ID5的编辑权限
        if (fw.checkWfEditable('content5' + token)) {
            return $("#status5" + token);
        }
    }

    return {
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
}
