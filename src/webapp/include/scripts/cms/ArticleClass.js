/**
 * Created by 邓超
 * Date 2015-4-16
 */

var ArticleClass = function (token) {

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化查询区域
        initSearchArea();
        // 初始化查询事件
        onClickArticleSearch();
        // 初始化查询重置事件
        onClickArticleSearchReset();
        // 初始化表格
        initTableArticleTable();
    }

    /**
     * 初始化查询区域
     */
    function initSearchArea() {
        using(SCRIPTS_ROOT + '/cms/ColumnClass.js', function () {
            var tree = $('#search_ColumnId' + token).combotree('tree');
            var columnClass = new ColumnClass(token);
            columnClass.buildColumnTree(tree);
        });
        //var url = WEB_ROOT + "/cms/Article_listMenuData.action";
        //fw.combotreeLoadWithCheck('#search_ColumnId'+token, url, null, null, null);
    }

    /**
     * 初始化表格
     */
    function initTableArticleTable() {
        var strTableId = 'ArticleTable' + token;
        var url = WEB_ROOT + "/cms/Article_list.action";
        $('#' + strTableId).datagrid({
            title: '文章信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍候......',
            fitColumns: true,
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
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
            frozenColumns: [[
                {field: 'ck', checkbox: true}
            ]],
            columns: [[
                {field: 'sid', title: '序号', hidden: true},
                {field: 'id', title: '编号', hidden: true},
                { field: 'state', title: 'state',hidden:true },

                {field: 'title', title: '标题'},
                {field: 'columnName', title: '栏目'},
                {field: 'publishedTime', title: '发布时间'},
                {field: 'content', title: '内容', hidden: true},
                {field: 'orders', title: '排序'},
                { field: 'operatorId', title: 'operatorId',hidden:true },
                { field: 'operateTime', title: '操作时间'}
            ]],
//            toolbar: [{
//                id: 'btnArticleAdd' + token,
//                text: '添加',
//                iconCls: 'icon-add'
//            }, {
//                id: 'btnArticleEdit' + token,
//                text: '修改',
//                iconCls: 'icon-edit'
//            }, {
//                id: 'btnArticleDelete' + token,
//                text: '删除',
//                iconCls: 'icon-cut'
//            }],
            onLoadSuccess: function () {
                onClickArticleAdd();
                onClickArticleDelete();
                onClickArticleEdit();
                onClickArticleContentEdit();
            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowArticleWindow(data) {
        data["article.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/cms/Article_Save.jsp?token=" + token;
        var windowId = "ArticleWindow" + token;
        fw.window(windowId, '文章内容', 780, 500, url, function () {
            // 构建栏目树
            using(SCRIPTS_ROOT + '/cms/ColumnClass.js', function () {
                var columnClass = new ColumnClass(token);
                var tree = $('#columnId' + token).combotree('tree');
                columnClass.buildColumnTree(tree);
            });
            // 初始化ckeditor
            initCKEditor();
            // 初始化表单提交事件
            onClickArticleSubmit();
            // 加载数据
            fw.formLoad('formArticle' + token, data);
            fw.getComboTreeFromKV('isDisplay'+token, 'Is_Avaliable', null, data["article.isDisplay"]);
        }, null);
    }


    function initWindowArticleContentWindow(data) {
        data["article.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/cms/Article_Content_Save.jsp?token=" + token;
        var windowId = "ArticleContentWindow" + token;
        fw.window(windowId, '文章内容', 780, 500, url, function () {
            // 初始化ckeditor
            initCKEditor();
            // 初始化表单提交事件
            onClickArticleContentSubmit();
            // 加载数据
            fw.formLoad('formArticle' + token, data);
        }, null);
    }

    /**
     * 添加事件
     */
    function onClickArticleAdd() {

        var buttonId = "btnArticleAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initWindowArticleWindow({});
        });

    }

    /**
     * 删除事件
     */
    function onClickArticleDelete() {
        var buttonId = "btnArticleDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ArticleTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/cms/Article_delete.action?article.sid=" + selected.sid;
                    //alert(url);
                    fw.post(url, null, function (data) {
                        //fw.alertReturnValue(data);
                        fw.datagridReload('ArticleTable' + token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickArticleEdit() {
        var buttonId = "btnArticleEdit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ArticleTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/cms/Article_load.action?article.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    initWindowArticleWindow(data);
                }, null);
            })

        });
    }

    function onClickArticleContentEdit() {
        var buttonId = "btnArticleContentEdit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ArticleTable' + token, function (selected) {
                var id = selected.id;
                var url = WEB_ROOT + "/cms/Article_load.action?article.id=" + id;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    initWindowArticleContentWindow(data);
                }, null);
            })

        });
    }

    /**
     * 查询事件
     */
    function onClickArticleSearch() {
        var buttonId = "btnSearchArticle" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "ArticleTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["articleVO.title"] = $("#search_Title" + token).val();
            params["articleVO.columnId"] = $('#search_ColumnId' + token).combotree('getValue');
            $('#' + strTableId).datagrid('load');
            fw.treeClear()
        });
    }

    /**
     * 查询重置事件
     */
    function onClickArticleSearchReset() {
        var buttonId = "btnResetArticle" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_Title" + token).val('');
            fw.combotreeClear('#search_ColumnId' + token);
        });
    }

    /**
     * 数据提交事件
     */
    function onClickArticleSubmit() {
        var buttonId = "btnArticleSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formArticle" + token;
            var url = WEB_ROOT + "/cms/Article_saveProperties.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                //alert('done');
                process.afterClick();
                fw.datagridReload("ArticleTable" + token);
                fw.windowClose('ArticleWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    function onClickArticleContentSubmit() {
        var buttonId = "btnArticleContentSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formArticle" + token;
            var url = WEB_ROOT + "/cms/Article_saveContent.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                //alert('done');
                process.afterClick();
                fw.datagridReload("ArticleTable" + token);
                fw.windowClose('ArticleContentWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    function initCKEditor() {
        using(SCRIPTS_ROOT + '/ckeditor/4.4.3/ckeditor.js', function () {
            CKEDITOR.replace('article.content');
        });
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