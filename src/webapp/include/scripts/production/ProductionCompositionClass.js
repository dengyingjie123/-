/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/16/14
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
var ProductionCompositionClass = function (token) {


    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll(obj) {
        // 初始化表格
        initTableProductionCompositionTable(obj);
        initArea(obj);
    }

    function initArea(obj) {
        if (obj == null) {
            $("#btnProductionCompositionAdd" + token).remove();
            $("#btnProductionCompositionEdit" + token).remove();
            $("#btnProductionCompositionDelete" + token).remove();
        }
    }

    /**
     * 初始化表格
     */
    function initTableProductionCompositionTable(obj) {
        var id = obj;
        var strTableId = 'ProductionCompositionTable' + token;
        var url = WEB_ROOT + "/production/ProductionComposition_list.action?productionComposition.productionId=" + id;

        $('#' + strTableId).datagrid({
            title: '构成信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: true,
            singleSelect: true,
            pageList: [3],
            pageSize: 3,
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
                    { field: 'sid', title: '序号', hidden: true },
                    { field: 'id', title: '编号', hidden: true },
                    { field: 'productionId', title: '产品编号', hidden: true },
                    { field: 'name', title: '名称' },
                    { field: 'sizeStart', title: '范围开始' , formatter: function (value, row, index) {
                        value=fw.formatMoney(row['sizeStart']);
                        return  value == "" ? "" : value + "元";
                    }   },
                    { field: 'sizeStop', title: '范围结束', formatter: function (value, row, index) {
                        value=fw.formatMoney(row['sizeStop']);
                        return  value == "" ? "" : value + "元";
                    }  },
                    { field: 'expectedYield', title: '预期收益率', formatter: function (value, row, index) {
                            return  value == "" ? "" : value + "%";
                        }
                    },
                    { field: 'floatingRate', title: '浮动收益率', formatter: function (value, row, index) {
                            return  value == "" ? "" : value + "%";
                        }
                    },
                    { field: 'buyingRate', title: '购买费率', formatter: function (value, row, index) {
                            return  value == "" ? "" : value + "%";
                        }
                    },
                    { field: 'payRate', title: '支付费率', formatter: function (value, row, index) {
                        return  value == "" ? "" : value + "%";
                        }
                    },
                    { field: 'directSellingRate', title: '直销费率', formatter: function (value, row, index) {
                        return  value == "" ? "" : value + "%";
                        }
                    },
                    { field: 'channelSellingRate', title: '渠道费率', formatter: function (value, row, index) {
                        return  value == "" ? "" : value + "%";
                        }
                    },
                    { field: 'bankingRate', title: '银行托管费率', formatter: function (value, row, index) {
                        return  value == "" ? "" : value + "%";
                        }
                    },
                    { field: 'commissionLevel', title: '返佣等级' },
                    { field: 'needCommissionCorrectionValue', title: '是否开启返佣修正' },
                    { field: 'needCustomerTypeCommissionCorrectionValue', title: '是否开启客户类型返佣修正' }
                ]
            ],
            toolbar: [
                {
                    id: 'btnProductionCompositionAdd' + token,
                    text: '新建构成',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnProductionCompositionEdit' + token,
                    text: '修改构成',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnProductionCompositionDelete' + token,
                    text: '删除构成',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                onClickProductionCompositionAdd(obj);
                onClickProductionCompositionDelete();
                onClickProductionCompositionEdit(obj);
            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowProductionCompositionWindow(data, obj, obj1) {

        data["productionComposition.OperatorId"] = loginUser.getId();

        var url = WEB_ROOT + "/modules/production/ProductionComposition_Save.jsp?token=" + token;
        var windowId = "ProductionCompositionWindow" + token;
        fw.window(windowId, '产品构成信息', 420, 470, url, function (obj) {


            fw.getComboTreeFromKV('needCommissionCorrection'+token, 'Is_Avaliable', 'k', fw.getMemberValue(data, 'productionComposition.needCommissionCorrection'));
            fw.getComboTreeFromKV('needCustomerTypeCommissionCorrection'+token, 'Is_Avaliable', 'k', fw.getMemberValue(data, 'productionComposition.needCustomerTypeCommissionCorrection'));

            data['productionComposition.productionId'] = obj;

            // 初始化表单提交事件
            onClickProductionCompositionSubmit();

            // 加载数据
            fw.formLoad('formProductionComposition' + token, data);
            if (obj1 == 1) {
                $("#floatingRate" + token).val("0");
                $("#buyingRate" + token).val("0");
                $("#payRate" + token).val("0");
            }
            //获取产品id
            //alert($("#productionId"+token).val(obj));
        }, null, obj);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickProductionCompositionAdd(obj) {

        var buttonId = "btnProductionCompositionAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空

            initWindowProductionCompositionWindow({}, obj, 1);
        });

    }

    /**
     * 删除事件
     */
    function onClickProductionCompositionDelete() {
        var buttonId = "btnProductionCompositionDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ProductionCompositionTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/production/ProductionComposition_delete.action?productionComposition.sid=" + selected.sid;
                    //alert(url);
                    fw.post(url, null, function (data) {
                        //fw.alertReturnValue(data);
                        fw.datagridReload('ProductionCompositionTable' + token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickProductionCompositionEdit(obj) {
        var butttonId = "btnProductionCompositionEdit" + token;
        fw.bindOnClick(butttonId, function (process) {

            fw.datagridGetSelected('ProductionCompositionTable' + token, function (selected) {
                var sid = selected.sid;
                var url = WEB_ROOT + "/production/ProductionComposition_load.action?productionComposition.sid=" + sid;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    data['productionComposition.name'] = selected.name;
                    initWindowProductionCompositionWindow(data, obj, 0);
                }, null);
            })

        });
    }

    /**
     * 数据提交事件
     */
    /*修改人：周海鸿
    * 时间：2015-7-9
    * 内容：判断数据有效性*/
    function onClickProductionCompositionSubmit() {
        var buttonId = "btnProductionCompositionSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            //获取范围开始范围结束
            var sizeStart  = $("#sizeStart"+token).val();
            var sizeStop  = $("#sizeStop"+token).val();
            //预期收益率
            var expectedYield  = $("#expectedYield"+token).val();

            //判断范围开始不能为负数
            if(sizeStart<=0){
                fw.alert("警告","范围开始不能小于1");
                return ;
            }
            //判断范围结束不能为负数
            if(sizeStop<=0){
                fw.alert("警告","范围结束不能小于1");
                return ;
            }
            //开始范围不能大于结束范围
            if(parseFloat(sizeStart)>=parseFloat(sizeStop)){
                fw.alert("警告","开始范围不能大于或等于结束范围");
                return ;
            }
            //判断范围开始不能为负数
           /* if (expectedYield <=0) {
                fw.alert("警告", "预期收益率不能小于1");
                return;
            }*/

            var formId = "formProductionComposition" + token;
            var url = WEB_ROOT + "/production/ProductionComposition_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                //alert('done');
                process.afterClick();
                fw.datagridReload("ProductionCompositionTable" + token);
                fw.windowClose('ProductionCompositionWindow' + token);
            }, function () {
                process.afterClick();
            });

        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function (obj) {
            return initAll(obj);
        }
    };
}
