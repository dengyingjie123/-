/**
 * 描述：这是培训管理->试题答案对应的JS。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
var ExamAnswerClass = function(token,obj,examQuestionObj){
    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickExamAnswerSearch();
        // 初始化查询重置事件
        onClickExamAnswerSearchReset();
        // 初始化表格
        initExamAnswerTable();
    }

    /**
     * 初始化表格
     */
    function initExamAnswerTable() {
        var strTableId = 'ExamAnswerTable'+token;
        var url = WEB_ROOT+"/info/ExamAnswer_list.action";

        $('#'+strTableId).datagrid({
            title: '试题答案',
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
                { field: 'customerName', title: '客户', width: 20 },
                { field: 'question', title: '试题' ,width:50 },
                { field: 'optionName', title: '选项' ,width:10 },
                { field: 'description', title: '描述' ,width:20 },
                { field: 'examOptionValue', title: '值' ,width:10 },
                { field: 'examOptionScore', title: '分数' ,width:10 },
                { field: 'answer', title: '填空题答案' ,width:20 }
            ]],
//            toolbar:[{
//                id:'btnExamAnswerAdd'+token,
//                text:'添加',
//                iconCls:'icon-add'
//            },{
//                id:'btnExamAnswerEdit'+token,
//                text:'修改',
//                iconCls:'icon-edit'
//            },{
//                id:'btnExamAnswerDelete'+token,
//                text:'删除',
//                iconCls:'icon-cut'
//            }],
            onLoadSuccess:function() {
                // 初始化修改事件
                onClickExamAnswerEdit();
                // 初始化添加事件
                onClickExamAnswerAdd();
                // 初始化删除事件
                onClickExamAnswerDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickExamAnswerAdd() {
        var buttonId = "btnExamAnswerAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            process.beforeClick();
            initExamAnswerWindow({});
            process.afterClick();
        });
    }

    /**
     * 删除事件
     */
    function onClickExamAnswerDelete() {
        var buttonId = "btnExamAnswerDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ExamAnswerTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/info/ExamAnswer_delete.action?examAnswer.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('ExamAnswerTable'+token);
                    },null);
                },null);
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickExamAnswerEdit() {
        var butttonId = "btnExamAnswerEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('ExamAnswerTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/info/ExamAnswer_load.action?examAnswer.id="+selected.id;
                fw.post(url, null, function(data){
                    data['examAnswerVO.customerName'] = selected.customerName;
                    data['examAnswerVO.question'] = selected.question;
                    data['examAnswerVO.optionName'] = selected.optionName;
                    data['examAnswerVO.description'] = selected.description;
                    data['examAnswerVO.examOptionValue'] = selected.examOptionValue;
                    data['examAnswerVO.examOptionScore'] = selected.examOptionScore;
                    initExamAnswerWindow(data,null);
                    process.afterClick();
                }, function (){
                    process.afterClick();
                });
            })
        });
    }

    /**
     * 弹出窗口
     */
    function initExamAnswerWindow(data) {

        data["examAnswer.operatorId"] = loginUser.getId();
        data["examAnswer.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/info/ExamAnswer_Save.jsp?token="+token;
        var windowId = "ExamAnswerWindow" + token;

        fw.window(windowId, '试题答案', 630, 340, url, function() {
            // 初始化按钮和事件
            onClickCheckCustomer();
            onClickInputCustomer();
            onClickCheckExamQuestion();
            onClickInputExamQuestion();
            onClickCheckExamOption();
            onClickInputExamOption();

            // 初始化表单提交事件
            onClickExamAnswerSubmit();

            // 加载数据
            fw.formLoad('formExamAnswer'+token, data);
        }, null);
    }

    /**
     * 数据提交事件
     */
    function onClickExamAnswerSubmit() {
        var buttonId = "btnExamAnswerSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formExamAnswer" + token;
            var url = WEB_ROOT + "/info/ExamAnswer_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("ExamAnswerTable"+token);
                fw.windowClose('ExamAnswerWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickExamAnswerSearch() {
        var buttonId = "btnExamAnswerSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "ExamAnswerTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params['examAnswerVO.customerName'] = $('#search_customerName' + token).val();
            params['examAnswerVO.examOptionScore'] = $('#search_score' + token).val();
            $( '#' + strTableId).datagrid('load');
            fw.treeClear()
        });
    }

    /**
     * 查询重置事件
     */
    function onClickExamAnswerSearchReset() {
        var buttonId = "btnExamAnswerSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 清空对应条件
            $('#search_customerName' + token).val('');
            $('#search_score' + token).val('');
            // 重新加载列表
            var strTableId = "ExamAnswerTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params['examAnswerVO.customerName'] = $('#search_customerName' + token).val();
            params['examAnswerVO.examOptionScore'] = $('#search_score' + token).val();
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 单击查询客户列表按钮事件
     */
     function onClickCheckCustomer(){
        $('#btnCheckCustomer' + token).bind('click', function () {
            //获取客户列表的链接
            var url =  WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 550, url, function() {
                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT+'/customer/CustomerListSelectClass.js', function () {
                    var obj = new ExamAnswerClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token, obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
    }

    /**
     * 单击文本框弹出客户列表事件
     */
    function onClickInputCustomer() {
        $('#customerName' + token).bind('click', function () {
            //获取客户列表的链接
            var url =  WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 550, url, function() {
                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT+'/customer/CustomerListSelectClass.js', function () {
                    var obj = new ExamAnswerClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token,obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
    }

    /**
     * 单击查询试题列表事件
     */
    function onClickCheckExamQuestion(){
        $('#btnCheckExamQuestionList' + token).bind('click', function () {
            //客户为空时不能能添加
            var customerId=  $("#customerId" + token).val();
            if(customerId =='' || customerId == null) {
                fw.showMessage('提示','请选择客户！');
                return;
            }
            //获取试题列表的链接
            var url =  WEB_ROOT + "/modules/info/ExamQuestionList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "ExamQuestionSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '试题列表', 930, 500, url, function() {
                //将选择试题的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT+'/info/ExamQuestionClass.js', function () {
                    var examQuestionObj = new ExamAnswerClass(token);
                    var examQuestionSelectClass = new ExamQuestionClass(token, examQuestionObj);
                    examQuestionSelectClass.initModule();
                });
            }, null);
        })
    }

    /**
     * 单击文本框弹出试题列表事件
     */
    function onClickInputExamQuestion(){
        $('#question' + token).bind('click', function () {
            //客户为空时不能能添加
            var customerId=  $("#customerId" + token).val();
            if(customerId =='' || customerId == null) {
                fw.showMessage('提示','请选择客户！');
                return;
            }
            //获取试题列表的链接
            var url =  WEB_ROOT + "/modules/info/ExamQuestionList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "ExamQuestionSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '试题列表', 930, 500, url, function() {
                //将选择试题的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT+'/info/ExamQuestionClass.js', function () {

                    var examQuestionObj = new ExamAnswerClass(token);
                    var examQuestionSelectClass = new ExamQuestionClass(token, examQuestionObj);
                    examQuestionSelectClass.initModule();
                });
            }, null);
        })
    }

    /**
     * 单击查询选项列表事件
     */
    function onClickCheckExamOption(){
        $('#btnCheckExamOptionList' + token).bind('click', function () {
            var questionId=  $("#questionId" + token).val();
            if(questionId =='' || questionId ==null) {
                fw.showMessage('提示','请选择问题！');
                return;
            }
            //获取试题选项列表的链接
            var url =  WEB_ROOT + "/modules/info/ExamOptionList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "ExamOptionSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '试题选项列表', 930, 500, url, function(questionId) {
                //将选择试题选项的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT+'/info/ExamOptionClass.js', function () {
                    var examOptionObj = new ExamAnswerClass(token);
                    var examOptionSelectClass = new ExamOptionClass(token,'', examOptionObj,questionId);
                    examOptionSelectClass.initModule();
                });
            }, null,questionId);
        })
    }

    /**
     * 单击文本框弹出试题选项列表事件
     */
    function onClickInputExamOption(){
        $('#name' + token).bind('click', function () {
            var questionId=  $("#questionId" + token).val();
            if(questionId =='' || questionId ==null) {
                fw.showMessage('提示','请选择问题！');
                return;
            }
            //获取试题选项列表的链接
            var url =  WEB_ROOT + "/modules/info/ExamOptionList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "ExamOptionSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '试题选项列表', 930, 500, url, function(questionId) {
                //将选择试题选项的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT+'/info/ExamOptionClass.js', function () {
                    var examOptionObj = new ExamAnswerClass(token);
                    var examOptionSelectClass = new ExamOptionClass(token,null,examOptionObj,questionId);
                    examOptionSelectClass.initModule();
                });
            }, null,questionId);
        })
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        },loadCustomerInfo:function(customerId, customerName){
            $("#customerId" + token).val(customerId);
            $("#customerName" + token).val(customerName);
        },loadExamQuestionInfo:function(questionId, question){
            $("#questionId" + token).val(questionId);
            $("#question" + token).val(question);
            $('#description' + token).val('');
            $('#name' + token).val('');
            $('#examOptionValue' + token).val('');
            $('#examOptionScore' + token).val('');
        },loadExamOptionInfo:function(optionId,description,name,value,score){
            $('#optionId' + token).val(optionId);
            $('#description' + token).val(description);
            $('#name' + token).val(name);
            $('#examOptionValue' + token).val(value);
            $('#examOptionScore' + token).val(score);
        }
    };
};