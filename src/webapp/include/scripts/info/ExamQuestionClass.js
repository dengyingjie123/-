/**
 * 主表管理
 *
 * 试题管理JS
 * @author 张舜清
 * @Time 4/13/2015
 */
var ExamQuestionClass = function (token, obj) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化主表 //////////////////////////
        // 查询表单
        initExamQuestionSearchForm();

        // 初始化查询事件
        onClickExamQuestionSearch();

        // 初始化查询重置事件
        onClickExamQuestionSearchReset();

        // 初始化表格
        initExamQuestionTable();


        // 初始化附表 /////////////////////////////
        // 初始化ExamQuestionList_Select.jsp的查询事件
        onClickExamQuestionOptionSearch();

        // 初始化ExamQuestionList_Select.jsp的查询重置事件
        onClickExamQuestionOptionSearchReset();

        // 初始化ExamQuestionList_Select.jsp的按钮选择事件
        onClickSelectedExamQuestion();
    }


    /**
     * 初始化表格
     */
    function initExamQuestionTable() {
        var strTableId = 'ExamQuestionTable' + token;
        var url = WEB_ROOT + "/info/ExamQuestion_listPaperExamQuestionVO.action";
        $('#' + strTableId).datagrid({
            title: '试题列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            fitColumns: false,
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            loadFilter: function (data) {
                try {
                    data = fw.dealReturnObject(data);

                    //alert(JSON.stringify(data));

                    return data;
                }
                catch (e) {
                    fw.alert(e);
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
                    { field: 'paperId', title: '试卷', hidden:true},
                    { field: 'paperName', title: '试卷'},
                    { field: 'questionNO', title: '题号'},
                    { field: 'title', title: '标题'},
                    { field: 'question', title: '问题'},
                    { field: 'typeName', title: '类型'},
                    { field: 'time', title: '创建时间'}
                ]
            ],
//            toolbar: [
//                {
//                    id: 'btnExamQuestionAdd' + token,
//                    text: '添加',
//                    iconCls: 'icon-add'
//                },
//                {
//                    id: 'btnExamQuestionEdit' + token,
//                    text: '修改',
//                    iconCls: 'icon-edit'
//                },
//                {
//                    id: 'btnExamQuestionDelete' + token,
//                    text: '删除',
//                    iconCls: 'icon-cut'
//                }
//            ],
            onLoadSuccess: function () {
                // 初始化添加事件
                onClickExamQuestionAdd();
                // 初始化修改事件
                onClickExamQuestionEdit();
                // 初始化删除事件
                onClickExamQuestionDelete();
            }
        });
    }


    /**
     * 添加事件
     */
    function onClickExamQuestionAdd() {
        var buttonId = "btnExamQuestionAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initExamQuestionWindowAdd({});
        });
    }

    /**
     * 初始化主表增加窗口
     * @param data
     */
    function initExamQuestionWindowAdd(data) {
        data["examQuestion.operatorId"] = loginUser.getId();
        data["examQuestion.operatorName"] = loginUser.getName();


        var url = WEB_ROOT + "/modules/info/ExamQuestion_Add.jsp?token=" + token;
        var windowId = "ExamQuestionWindow" + token;


        fw.window(windowId, '添加试题', 570, 250, url, function () {

            fw.getComboTreeFromKV('type' + token, 'INFO_ExamQuestionType', 'k', fw.getMemberValue(data, 'examQuestion.type'));
            fw.getComboTreeFromKV('paperId' + token, 'INFO_ExamQuestionPaper', 'k', fw.getMemberValue(data, 'examQuestion.paperId'));


            // 初始化提交按钮
            onClickExamQuestionSubmit();

            // 加载一个空白的表单
            fw.formLoad('formExamQuestion' + token, data);
        }, null);
    }


    /**
     *  修改事件
     */
    function onClickExamQuestionEdit() {
        var buttonId = "btnExamQuestionEdit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ExamQuestionTable' + token, function (selected) {
                // 改变按钮状态
                process.beforeClick();

                // 获得选中的id
                var id = selected.id;

                var url = WEB_ROOT + "/info/ExamQuestion_load.action?examQuestion.id=" + id;
                fw.post(url, null, function (data) {

                    try {
                        // 查询数据初始化主表窗口表单
                        initExamQuestionWindow(data);
                    }
                    catch(e) {
                        fw.alert(e);
                    }
                    finally {
                        // 改变按钮状态
                        process.afterClick();
                    }

                }, function () {
                    // 改变按钮状态
                    process.afterClick();
                });
            })
        });
    }

    /**
     *  删除事件
     */
    function onClickExamQuestionDelete() {
        var buttonId = "btnExamQuestionDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ExamQuestionTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/info/ExamQuestion_delete.action?examQuestion.sid=" + selected.sid;
                    fw.post(url, null, function (data) {
                        fw.datagridReload('ExamQuestionTable' + token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickExamQuestionSearch() {
        var buttonId = "btnExamQuestionSearchSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ExamQuestionTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["examQuestionVO.question"] = $('#search_Question' + token).val();
            params["examQuestionVO.type"] = fw.getFormValue('search_TypeName' + token, fw.type_form_combotree, fw.type_get_value);
            params["examQuestionVO.paperId"] = fw.getFormValue('search_PaperId' + token, fw.type_form_combotree, fw.type_get_value);
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickExamQuestionSearchReset() {
        // btnExamQuestionSearchReset
        var buttonId = "btnExamQuestionSearchReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_Question' + token).val("");
            // 清空类型下拉菜单
            fw.combotreeClear('search_TypeName' + token);
            fw.combotreeClear('search_PaperId' + token);
        });
    }

    /**
     * 附表查询条件
     */
    function onClickExamQuestionOptionSearch() {
        var buttonId = "btnExamQuestionListSearchSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ExamQuestionListTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["examQuestionVO.question"] = $('#search_QuestionList' + token).val();
            params["examQuestionVO.type"] = fw.getFormValue('search_TypeNameList' + token, fw.type_form_combotree, fw.type_get_value);
            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * ExamQuestionList_Select.jsp页面对应的查询重置事件
     */
    function onClickExamQuestionOptionSearchReset() {
        $('#search_QuestionList' + token).val("");
        // 清空类型下拉菜单
        fw.combotreeClear('search_TypeNameList' + token);
    }


    /**
     * 打开窗口，初始化信息
     * @param data
     */
    function initExamQuestionWindow(data) {
        data["examQuestion.operatorId"] = loginUser.getId();
        data["examQuestion.operatorName"] = loginUser.getName();
        var url = WEB_ROOT + "/modules/info/ExamQuestion_Save.jsp?token=" + token;
        var windowId = "ExamQuestionWindow" + token;

        fw.window(windowId, '试题', 870, 600, url, function () {
            // 初始化提交按钮
            ///onSelectedType(data);
            fw.getComboTreeFromKV('type' + token, 'INFO_ExamQuestionType', 'k', fw.getMemberValue(data, 'examQuestion.type'));
            fw.getComboTreeFromKV('paperId' + token, 'INFO_ExamQuestionPaper', 'k', fw.getMemberValue(data, 'examQuestion.paperId'));
            onClickExamQuestionSubmit();
            using(SCRIPTS_ROOT + '/info/ExamOptionClass.js', function () {
                fw.datagridGetSelected('ExamQuestionTable' + token, function (selected) {
                    var id = selected.id;
                    var question = selected.question;
                    var option = new ExamOptionClass(token, null, null, id, question);
                    option.initModule();
                })
            });
            // 加载数据
            fw.formLoad('formExamQuestion' + token, data);
        }, null);
    }

    /**
     * 初始化查询表单
     */
    function initExamQuestionSearchForm() {
        fw.getComboTreeFromKV('search_TypeName' + token, 'INFO_ExamQuestionType', 'k', '-2');
        fw.getComboTreeFromKV('search_PaperId' + token, 'INFO_ExamQuestionPaper', 'k', '-2');
    }

    /**
     * 数据提交事件
     */
    function onClickExamQuestionSubmit() {
        var buttonId = "btnExamQuestionSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formExamQuestion" + token;
            var url = WEB_ROOT + "/info/ExamQuestion_insertOrUpdate.action";
            //alert("action is start")
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("ExamQuestionTable" + token);
                fw.windowClose('ExamQuestionWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 初始化选择客户按钮
     */
    function onClickSelectedExamQuestion() {
        var buttonId = "btnSelectedExamQuestion" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formTabsId = "selectedExamQuestion" + token;
            //取得选项卡的位置
            var questionId;
            var question;
            var tableName = "ExamQuestionListTable" + token;

            fw.datagridGetSelected(tableName, function (selected) {
                questionId = selected.id;
                question = selected.question;
                //调用加载文本框函数
                obj.loadExamQuestionInfo(questionId, question);
                fwCloseWindow('ExamQuestionSelectWindow' + token);
            });
        });
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
};
