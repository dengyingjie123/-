/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/18/14
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
var CustomerCertificateClass = function (token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll(obj, calendar) {
        // 初始化表格
        initTableCustomerCertificateTable(obj);
        initArea(obj, calendar);
    }

    function initArea(obj, calendar) {
        if (obj == null && calendar == null) {
            $("#btnCustomerCertificateAdd" + token).remove();
            $("#btnCustomerCertificateEdit" + token).remove();
            $("#btnCustomerCertificateDelete" + token).remove();
        } else if (obj != null && calendar != null) {
            $("#btnCustomerCertificateAdd" + token).remove();
            $("#btnCustomerCertificateEdit" + token).remove();
            $("#btnCustomerCertificateDelete" + token).remove();
        }
    }

    /**
     * 初始化表格
     */
    function initTableCustomerCertificateTable(obj) {
        var id = obj;
        var strTableId = 'CustomerCertificateTable' + token;
        var url = WEB_ROOT + "/customer/CustomerCertificate_list.action?customerCertificate.customerId=" + id;

        $('#' + strTableId).datagrid({
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
                    { field: 'sid', title: '序号', hidden: true, width: 30 },
                    { field: 'id', title: '编号', hidden: true, width: 30 },
                    { field: 'customerId', title: '客户编号', hidden: true, width: 30 },
                    { field: 'name', title: '名称', width: 30 },
                    { field: 'number', title: '号码', width: 30 },
                    { field: 'validityDate', title: '有效期', width: 30 },
                    { field: 'validityDateStart', title: '有效期开始时间', hidden: true, width: 30 },
                    { field: 'validityDateEnd', title: '有效期结束时间', hidden: true, width: 30 },
                    { field: 'authenticationInstitution', title: '发证机构', hidden: true, width: 30 }
                ]
            ],
            toolbar: [
                {
                    id: 'btnCustomerCertificateAdd' + token,
                    text: '添加',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnCustomerCertificateEdit' + token,
                    text: '修改',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnCustomerCertificateDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                }
            ],
            onLoadSuccess: function () {
                onClickCustomerCertificateAdd(obj);
                onClickCustomerCertificateDelete();
                onClickCustomerCertificateEdit(obj);
            }
        });
    }

    /**修改人:姚章鹏
     * 时间：2015年6月18日13:55:49
     * 内容：自定义customerWindow窗口，添加帮助按钮
     * 初始化弹出窗口
     * @param data
     */
    function initWindowCustomerCertificateWindow(data, obj) {
        var customerId = obj;

        data["customerCertificate.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/customer/CustomerCertificate_Save.jsp?token=" + token;
        var url2 = WEB_ROOT + "/modules/customer/CustomHelpWindow.jsp?token=" + token;
        var windowId = "CustomerCertificateWindow" + token;
        var helpWindowId = "helpWindowId" + token;
        fw.customerWindow(windowId,helpWindowId ,'证件信息', 380, 380, url,url2,function () {

            // 初始化长期下拉菜单
            $('#isLongValidityDate' + token).combotree('loadData', [
                {id: 0, text: '非长期'},
                {id: 1, text: '长期'}
            ]);
            //产期选择改变事件
            $('#isLongValidityDate' + token).combo({
                onChange: function (newValue, oldValue) {
                    if (newValue == 1) {
                        $('#validityDate' + token).combo('disable');
                        $('#validityDate' + token).combo({required: false});
                        $('#validityDateEnd' + token).combo('disable');
                    }
                    else {
                        // enable
                        $('#validityDate' + token).combo('enable');
                        $('#validityDate' + token).combo({required: true});
                        $('#validityDateEnd' + token).combo('enable');
                    }
                }
            });

            //初始化上传
            // onclickIDCardUpload(customerId);

            //初始化文本框离开焦点事件
            initNumberText();
            // 初始化表单提交事件
            onClickCustomerCertificateSubmit();
            fw.getComboTreeFromKV('name' + token + 1, 'Certificate', 'V', fw.getMemberValue(data, 'customerCertificate.name'));

            // 加载数据
            fw.formLoad('formCustomerCertificate' + token, data);
            //获取产品id
            $("#customerId" + token).val(obj);
        }, null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     *初始化身份证检查事件
     */
    function initNumberText() {
        var textId = "number" + token;
        //文本框获取焦点事件
        $("#" + textId).bind("focus", function () {
            var selectNameText = fw.getFormValue("name" + token + 1, fw.type_form_combotree, fw.type_get_text);
            if (selectNameText == "身份证") {
                $('#' + textId).validatebox({required: true});
                //$('#' + textId).validatebox({validType: 'idcard'});
            }
             else if(selectNameText == "营业执照"){
                $('#'+textId).validatebox({required:true});
            }else if(selectNameText == "驾驶证")
            {
                $('#' + textId).validatebox({required: true});
            }
        });
//        //文本框焦点事件
//        $("#"+textId).bind("blur",function(){
//            var value=$("#"+textId).val();
//            var id=$("#id"+token).val();
//            if(value != "") {
//                var url = WEB_ROOT + "/customer/CustomerCertificate_CheckIdCore.action?customerCertificate.id="+id+"&customerCertificate.number=" + value;
//                fw.post(url, null, function (date) {
//                    var text=date["istrue"];
//                     $('#' + textId).validatebox({validType:'TextIsTrue['+text+']'});
//
//                }, null)
//            }
//        });
    }

    /**
     * 添加事件
     */
    function onClickCustomerCertificateAdd(obj) {
        var buttonId = "btnCustomerCertificateAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initWindowCustomerCertificateWindow({}, obj);
        });

    }

    /**
     * 删除事件
     */
    function onClickCustomerCertificateDelete() {
        var buttonId = "btnCustomerCertificateDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('CustomerCertificateTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/customer/CustomerCertificate_delete.action?customerCertificate.sid=" + selected.sid;
                    //alert(url);
                    fw.post(url, null, function (data) {
                        //fw.alertReturnValue(data);
                        fw.datagridReload('CustomerCertificateTable' + token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickCustomerCertificateEdit(obj) {
        var butttonId = "btnCustomerCertificateEdit" + token;
        fw.bindOnClick(butttonId, function (process) {
            fw.datagridGetSelected('CustomerCertificateTable' + token, function (selected) {
                var sid = selected.sid;
                var url = WEB_ROOT + "/customer/CustomerCertificate_load.action?customerCertificate.sid=" + sid;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    initWindowCustomerCertificateWindow(data, obj);
                }, null);
            })

        });
    }

    /**
     * 数据提交事件
     */
    function onClickCustomerCertificateSubmit() {
        var buttonId = "btnCustomerCertificateSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formCustomerCertificate" + token;
            var url = WEB_ROOT + "/customer/CustomerCertificate_insertOrUpdate.action";
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    //alert('done');
                    process.afterClick();
                    fw.datagridReload("CustomerCertificateTable" + token);
                    fw.windowClose('CustomerCertificateWindow' + token);
                }, function () {
                    process.afterClick();
                });
        });
    }

    /**
     * 初始化上传事件
     */
    function onclickIDCardUpload(bizid) {
        var buttonId = "btnUpload" + token;
        var moduleId = "5222";
        if (bizid != "") {
            var URl = WEB_ROOT + "/system/Files_getBiZidCounts.action?files.moduleId=" + moduleId + "&files.bizid=" + bizid;
            fw.post(URl, null, function (data) {
                $("#" + buttonId).linkbutton({text: "已有" + data['bizids'] + "个附件，点击上传", disabled: false});
            }, null);
        }
        fw.bindOnClick(buttonId, function (process) {
            process.beforeClick();
            /**
             * 知识库地址：http://c.hopewealth.net/pages/viewpage.action?pageId=27066375
             */
            fw.uploadFiles(token, moduleId, "", bizid);
            process.afterClick();
        })
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function (obj, calendar) {
            return initAll(obj, calendar);
        }
    };
}
