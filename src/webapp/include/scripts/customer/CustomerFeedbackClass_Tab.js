/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/13/15
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
var CustomerFeedbackClass_Tab = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll(obj,name) {

        // 初始化表格
        initTable(obj,name);
        initArea(obj,name);

    }

    function initArea(obj,calendar){
        if(obj==null&&calendar==null){
            $("#btnAdd"+token).remove();
            $("#btnEdit"+token).remove();
            $("#btnDelete"+token).remove();
        }else if(obj!=null&&calendar!=null){
//            $("#btnAdd"+token).remove();
//            $("#btnEdit"+token).remove();
//            $("#btnDelete"+token).remove();
        }
    }

    var OPNAME_ADD = 0;
    /**
     * 修改事件
     */
    function onClickCustomerFeedbackAdd(obj ,name) {
        var butttonId = "btnAdd" + token;
        fw.bindOnClick(butttonId, function(process) {
            // alert(butttonId);
            var data = {};
            initWindowCustomerFeedbackWindow(data , obj, name);
        });
    }

    var OPNAME_EDIT = 0;
    /**
     * 修改事件
     */
    function onClickCustomerFeedbackEdit(obj , name) {
        var butttonId = "btnEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('CustomerFeedbackTable'+token , function(selected){

                    //TODO 更改访问url 更改传入ID
                    var url = WEB_ROOT + "/customer/CustomerFeedback_load.action?customerFeedback.id="+selected.id;
                    fw.post(url, null, function(data) {
                        // TODO 增加其他传入参数
                        // data["oc.orderNum"]=selected.orderNum;
                        initWindowCustomerFeedbackWindow(data,obj,name);
                    }, null);
                }
            )

        });
    }


    /**
     * 数据提交事件
     */
    function onClickCustomerFeedbackSubmit() {
        var buttonId = "btnCustomerFeedbackSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            // alert("开始提交");
            var formId = "formCustomerFeedback" + token;
            // TODO 更改url
            var url = WEB_ROOT + "/customer/CustomerFeedback_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("CustomerFeedbackTable"+token);
                fw.windowClose('CustomerFeedbackWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 删除事件
     */
    function onClickCustomerFeedbackDelete() {
        var buttonId = "btnDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerFeedbackTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    // TODO 更改url
                    var url = WEB_ROOT + "/customer/CustomerFeedback_delete.action?customerFeedback.id="+selected.id;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('CustomerFeedbackTable'+token);
                    }, null);
                }, null);
            });
        });
    }


    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowCustomerFeedbackWindow(data ,obj, name) {

        data["customerFeedback.operatorId"] = loginUser.getId();
        data["customerFeedback.operatorName"] = loginUser.getName();
        data["customerFeedback.feedbackManName"] = loginUser.getName();
        data["customerFeedback.customerName"] = name;
        data["customerFeedback.customerId"] = obj;



        // 初始化其他表单
        data = fw.setDefaultValue(data, "customerFeedback.time", fw.getTimeToday());
        data = fw.setDefaultValue(data, "customerFeedback.feedbackManId", loginUser.getId());

        var url =  WEB_ROOT + "/modules/customer/CustomerFeedback_Save.jsp?token="+token;
        var windowId = "CustomerFeedbackWindow" + token;

        fw.window(windowId, '回访日志', 500, 400, url, function() {

            // 初始化控件

            // 初始化类型combotree
            fw.getComboTreeFromKV('typeId'+token, 'CRM_CustomerFeedbackType', 'k', fw.getMemberValue(data, 'customerFeedback.typeId'));


            // 初始化表单提交事件
            onClickCustomerFeedbackSubmit();

            // 加载数据
            fw.formLoad('formCustomerFeedback'+token, data);
        }, null);
    }

    function initTable(obj,name) {
        var strTableId = 'CustomerFeedbackTable'+token;
//        var customerName = encodeURI(encodeURI(name));
        var url = WEB_ROOT+"/customer/CustomerFeedback_list.action?customerFeedback.customerId="+obj;
        $('#'+strTableId).datagrid({
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[3],
            pageSize:3,
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
                { field:'sid' , title:'sid' , hidden:true , width:30},
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'typeName', title: '日志类型', width: 30 },
                { field: 'customerName', title: '客户', width: 30 },
                { field: 'content', title: '内容', width: 80 },
                { field: 'feedbackManName', title: '回访人', width: 30 },
                { field: 'time', title: '时间', width: 30 }
            ]],
            toolbar:[{
                id:'btnAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                onClickCustomerFeedbackAdd(obj , name);
                onClickCustomerFeedbackEdit(obj , name);
                onClickCustomerFeedbackDelete();
            }
        });
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(obj,name){
            return initAll(obj,name);
        }

    };
};/**
 * Created by Administrator on 2015/2/3.
 */
