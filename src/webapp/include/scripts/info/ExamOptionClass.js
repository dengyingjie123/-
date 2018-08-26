/**
 * 描述：这是培训管理->试题选项对应的JS。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 修改描述：
 * 修改时间：
 * 修改人：
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
var ExamOptionClass = function(token,obj,examOptionObj,questionId,question){
    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickExamOptionListSearch();
        // 初始化查询重置事件
        onClickExamOptionListSearchReset();
        // 初始化表格
        initExamOptionTable(questionId);
        // 初始化试题弹出选项的列表
        initExamOptionListTable(questionId,question);
        // 初始化ExamOptionList_Select的按钮选择事件
        onClickSelectedExamOptionList();
    }

    /**
     * 初始化表格
     */
    function initExamOptionTable(questionId) {
        var strTableId = 'ExamOptionTable'+token;
        var url = WEB_ROOT+"/info/ExamOption_list.action?examOption.questionId=" + questionId;

        $('#'+strTableId).datagrid({
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
                { field: 'question', title: '问题', width: 30 },
                { field: 'description', title: '描述', width: 30 },
                { field: 'name', title: '选项', width: 30 },
                { field: 'value', title: '值', width: 30 },
                { field: 'score', title: '分数', width: 30 },
                { field: 'orders', title: '排序', width: 30 }
            ]],
            toolbar:[{
                id:'btnExamOptionAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnExamOptionEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnExamOptionDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化修改事件
                onClickExamOptionEdit();
                // 初始化添加事件
                onClickExamOptionAdd();
                // 初始化删除事件
                onClickExamOptionDelete();
            }
        });
    }

    /**
     * 初始化试题弹出选项的列表
     * @param questionId
     * @param question
     */
    function initExamOptionListTable(questionId,question) {
        var strTableId = 'ExamOptionListTable'+token;
        var url = WEB_ROOT+"/info/ExamOption_list.action?examOption.questionId=" + questionId;

        $('#'+strTableId).datagrid({
            title: '试题选项',
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
                { field: 'question', title: '问题', width: 30 },
                { field: 'description', title: '描述', width: 30 },
                { field: 'name', title: '选项', width: 30 },
                { field: 'value', title: '值', width: 30 },
                { field: 'score', title: '分数', width: 30 },
                { field: 'orders', title: '排序', width: 30 }
            ]]
        });
    }



    /**
     * 添加事件
     */
    function onClickExamOptionAdd() {
        var buttonId = "btnExamOptionAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            initExamOptionWindow({});
        });
    }

    /**
     * 删除事件
     */
    function onClickExamOptionDelete() {
        var buttonId = "btnExamOptionDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ExamOptionTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/info/ExamOption_delete.action?examOption.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('ExamOptionTable'+token);
                    }, null);
                },null);
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickExamOptionEdit() {
        var butttonId = "btnExamOptionEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('ExamOptionTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/info/ExamOption_load.action?examOption.id="+selected.id;
                fw.post(url, null, function(data){
                    data['examOptionVO.question'] = selected.question;
                    initExamOptionWindow(data,null);
                    process.afterClick();
                }, function(){
                    process.afterClick();
                });
            })
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initExamOptionWindow(data) {
        data["examOption.operatorId"] = loginUser.getId();
        data["examOption.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/info/ExamOption_Save.jsp?token="+token;
        var windowId = "ExamOptionWindow" + token;
        fw.window(windowId, '试题选项', 520, 250, url, function() {

            if(questionId!=''){
                // 隐藏查询客户列表按钮
                $('#btnCheckExamQuestionList' + token).hide();
                // 把questionId和question放到隐藏域
                data['examOption.questionId'] = questionId;
                data['examOptionVO.question'] = question;
            }
            // 初始化试题列表事件
            //onClickCheckExamQuestion();
            // 初始化试题列表事件
            //onClickInputExamQuestion();
            // 初始化表单提交事件
            onClickExamOptionSubmit();

            // 加载数据
            fw.formLoad('formExamOption'+token, data);
        },null);
    }

    /**
     * 数据提交事件
     */
    function onClickExamOptionSubmit() {
        var buttonId = "btnExamOptionSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formExamOption" + token;
            var url = WEB_ROOT + "/info/ExamOption_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("ExamOptionTable"+token);
                fw.windowClose('ExamOptionWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * ExamOptionList_Select.jsp的查询事件
     */
    function onClickExamOptionListSearch() {
        var buttonId = "btnExamOptionListSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "ExamOptionListTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params['examOptionVO.name'] =  $('#Search_NameList' + token).val();;
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * ExamOptionList_Select.jsp的重置事件
     */
    function onClickExamOptionListSearchReset(){
        var buttonId = "btnExamOptionListSearchReset" + token;
        fw.bindOnClick(buttonId,function(process){
            // 清空文本框
            $('#Search_NameList' + token).val('');
            // 查询出列表
            var strTableId = "ExamOptionListTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params['examOptionVO.name'] =  $('#Search_NameList' + token).val();
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 初始化按钮事件
     */
    function onClickSelectedExamOptionList() {
        var buttonId = "btnSelectedExamOption" + token;
        fw.bindOnClick(buttonId,function(process){
            var optionId;
            var description;
            var name;
            var value;
            var score;
            var tableName = "ExamOptionListTable" + token;

            fw.datagridGetSelected(tableName, function (selected) {
                optionId = selected.id;
                description = selected.description;
                name = selected.name;
                value = selected.value;
                score = selected.score;
                //调用加载文本框函数
                examOptionObj.loadExamOptionInfo(optionId,description,name,value,score);
                fwCloseWindow('ExamOptionSelectWindow' + token);
            });
        });
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        },loadExamQuestionInfo:function(questionId, question){
            $("#questionId" + token).val(questionId);
            $("#question" + token).val(question);
        }
    };
};