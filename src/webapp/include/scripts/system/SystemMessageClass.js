/**
 * Created by 邓超
 * Date 2015-4-16
 */



var ArticleClass = function (token) {

    var uIds = {};
    var dId = null;
    var tIds = [];

    var aIds = {};

    /**
     * 初始化主页面控件
     */
    function initAll() {
        // 初始化查询事件
        onClickArticleSearch();
        // 初始化查询重置事件
        onClickArticleSearchReset();
        // 初始化表格
        initTableArticleTable();
        // 初始化表格
        initPublishedTable();
        // 初始化草稿
        initDraftTable();
    }

    //草稿箱
    function initDraftTable() {
        var strTableId = 'DraftTable' + token;
        var url = WEB_ROOT + "/system/Message_listDraft.action";
        $('#' + strTableId).datagrid({
            title: '公告信息',
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
                {field: 'ck', checkbox: true},
                {field: 'sid', title: '序号', hidden: true},
                {field: 'id', title: '编号', hidden: true},
                {field: 'publishedTime', title: '发布时间'},
                {field: 'title', title: '主题'},
                {field: 'isTop', title: '状态'},
                {field: 'level', title: '紧急度'}
            ]],
            columns: [[
                {field: 'senderName', title: '发布者'},
                {field: 'senderDepartmentName', title: '发布者部门'},
                {field: 'orders', title: '排序', hidden: true}
            ]],
            toolbar: [{
                id: 'btnSystemMessageEdit' + token,
                text: '编辑',
                iconCls: 'icon-edit'
            }, {
                id: 'btnSystemMessageDelete' + token,
                text: '删除',
                iconCls: 'icon-cut'
            }],
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
                //onClickArticleAdd();
                //onClickArticleRead();
                onClickMessageEdit();
                onClickArticleDelete();
                //onClickArticleEdit();
            },
            onDblClickRow: function (index, row) {  //easyui封装好的时间（被单机行的索引，被单击行的值）
                onClickArticleView(row.id);
            }
        });
    }

    //初始化已发布的表格
    function initPublishedTable() {
        var strTableId = 'PublishedTable' + token;
        var url = WEB_ROOT + "/system/Message_listPublished.action";
        $('#' + strTableId).datagrid({
            title: '公告信息',
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
                {field: 'ck', checkbox: true},
                {field: 'sid', title: '序号', hidden: true},
                {field: 'id', title: '编号', hidden: true},
                {field: 'publishedTime', title: '发布时间'},
                {field: 'title', title: '主题'},
                {field: 'isTop', title: '状态'},
                {field: 'level', title: '紧急度'}
            ]],
            columns: [[
                {field: 'senderName', title: '发布者'},
                {field: 'senderDepartmentName', title: '发布者部门'},
                {field: 'orders', title: '排序', hidden: true}
            ]],
            toolbar: [
                //{
                //id: 'btnSystemMessageAdd' + token,
                //text: '新建',
                //iconCls: 'icon-add'
               //},
                {
                    id: 'btnPublishedMessageRead' + token,
                    text: '查看',
                    iconCls: 'icon-add'
                }
            ],
//              ,
//              {
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
                onClickPublishedRead();
                //onClickArticleDelete();
                //onClickArticleEdit();
            },
            onDblClickRow: function (index, row) {  //easyui封装好的时间（被单机行的索引，被单击行的值）
                onClickArticleView(row.id);
            }
        });
    }

    /**
     * 初始化表格，已收到
     */
    function initTableArticleTable() {
        var strTableId = 'ArticleTable' + token;
        var url = WEB_ROOT + "/system/Message_listReceived.action";
        $('#' + strTableId).datagrid({
            title: '公告信息',
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
                {field: 'ck', checkbox: true},
                {field: 'sid', title: '序号', hidden: true},
                {field: 'id', title: '编号', hidden: true},
                {field: 'publishedTime', title: '发布时间'},
                {field: 'title', title: '主题'},
                {field: 'isTop', title: '状态'},
                {field: 'level', title: '紧急度'}
            ]],
            columns: [[
                {field: 'senderName', title: '发布2者'},
                {field: 'senderDepartmentName', title: '发布者部门'},
                {field: 'orders', title: '排序', hidden: true},
                {field: 'isRead', title: '是否已阅'}
            ]],
            toolbar: [{
                id: 'btnSystemMessageRead' + token,
                text: '查看',
                iconCls: 'icon-add'
            }],
//                id: 'btnArticleEdit' + token,
//                text: '修改',
//                iconCls: 'icon-edit'
//            }, {
//                id: 'btnArticleDelete' + token,
//                text: '删除',
//                iconCls: 'icon-cut'
//            }],
            onLoadSuccess: function () {
                //onClickArticleAdd();
                onClickArticleRead();
                //onClickArticleDelete();
                //onClickArticleEdit();
            },
            onDblClickRow: function (index, row) {  //easyui封装好的时间（被单机行的索引，被单击行的值）
                onClickArticleView(row.id);
            }
        });
    }

    /**
     * 初始化信息发送到弹出窗口
     */
    function initWindowMessageSendTo(data) {
        data["message.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/system/message/Message_SendTo.jsp?token=" + token;
        var windowId = "DepartmentWindow" + token;
        fw.window(windowId, '选择人员', 700, 500, url, function () {
            var departmentTreeId = "departmentStaffOption" + token;
            initDepartmentStaffTree(departmentTreeId);

            //绑定按钮事件
            onClickMessageSendSubmit();
            // 加载数据
            //fw.formLoad('formArticle' + token, data);

        }, null);

    }

    /**
     * 数据提交事件
     */
    function onClickMessageSendSubmit() {
        var buttonId = "btnMessageSendSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var sendToStr = [];

            var mNames = [];
            var tNames = {};
            var dID = [];

            var mids = [];
            var tids = [];

            var uNodes = $('#departmentStaffOption' + token).tree('getChecked');

            var dFlag = false;
            var children = "";

            for (var i = 0; i < uNodes.length; i++) {
                dFlag = true;
                if (uNodes[i].department != undefined) {
                    if (children.length == 0) {
                        children = JSON.stringify(uNodes[i].children);

                        tids.push(uNodes[i].fromName + '-' + uNodes[i].text);

                        dID = [];
                        dID.push(uNodes[i].id);
                        tNames[dID] = dID;
                    } else {
                        if (children.indexOf(uNodes[i].id) == -1) {
                            tids.push(uNodes[i].fromName + '-' + uNodes[i].text);

                            dID = [];
                            dID.push(uNodes[i].id);
                            tNames[dID] = dID;

                            children = JSON.stringify(uNodes[i].children);
                        }
                    }

                }

                if (uNodes[i].user != undefined) {
                    if (children.indexOf(uNodes[i].id) == -1) {
                        dFlag = false;
                        dID = null;
                    }

                    if (!dFlag) {
                        mids.push(uNodes[i].text);
                        mNames.push(uNodes[i].id);
                    }
                }
            }

            //tids = uniqueArray(tids);
            mids = uniqueArray(mids);

            for (var prop in tids) {
                sendToStr += tids[prop] + ";";
            }

            for (var prop in mids) {
                sendToStr += mids[prop] + ";";
            }
            fw.windowClose('DepartmentWindow' + token);

            //var allSelected = JSON.stringify(tNames);
            var md5AllS = fw.getMD5(sendToStr);
            //alert(md5AllS);
            if (md5AllS == '9a7bc54d93cf05c085a6ffd33e3c320d') {
                $("#sendRange" + token).val("32079");
                $("#sendTo" + token).val("全体人员");
            } else {
                $("#sendRange" + token).val("32080");
                $("#sendTo" + token).val(sendToStr);
            }

            $("#departmentIds" + token).val('#' + JSON.stringify(tNames));
            $("#orders" + token).val('1');

            var staffO = {};
            staffO['staffIds'] = mNames;
            $("#staffIds" + token).val('#' + JSON.stringify(staffO));

            ////部门
            //$("#uIds" + token).val(uIdsStr);


            $("#type" + token).val("32206");//公告

            var formId = "formArticle" + token;
            var url = WEB_ROOT + "/system/Message_insertOrUpdate.action";

            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("ArticleTable" + token);
                fw.datagridReload("PublishedTable" + token);
                //fw.datagridReload("DraftTable" + token);
                //fw.windowClose('ArticleWindow' + token);
            }, function () {
                process.afterClick();
            });
        });


    }


    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowMessageEditWindow(data) {
        data["message.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/system/message/Message_Save.jsp?token=" + token;
        var windowId = "ArticleWindow" + token;
        fw.window(windowId, '编辑公告', 700, 500, url, function () {
            // 初始化ckeditor
            initCKEditor();
            // 初始化表单提交事件
            onClickArticleSubmit();
            //初始化草稿保存事件
            onClickMessageSave();
            //发送到
            onClickArticleSendTo();
            // 初始化部门
            //onClickDepartmentOption();

            //var departmentTreeId = "departmentOption" + token;
            //initDepartmentTree(departmentTreeId);


            // 加载数据
            fw.formLoad('formArticle' + token, data);


            fw.getComboTreeFromKV('isTop' + token, 'Is_Avaliable', null, data["message.isTop"]);
            fw.getComboTreeFromKV('isReceipt' + token, 'Is_Avaliable', null, data["message.isReceipt"]);
            fw.getComboTreeFromKV('level' + token, 'Message_Level', null, data["message.level"]);

        }, null);
    }


    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowArticleWindow(data) {
        data["message.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/system/message/Message_Save.jsp?token=" + token;
        var windowId = "ArticleWindow" + token;
        fw.window(windowId, '添加公告', 750, 550, url, function () {
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
            //初始化草稿保存事件
            onClickMessageSave();
            //发送到
            onClickArticleSendTo();
            // 初始化部门
            //onClickDepartmentOption();

            //var departmentTreeId = "departmentOption" + token;
            //initDepartmentTree(departmentTreeId);


            // 加载数据
            fw.formLoad('formArticle' + token, data);


            fw.jsonJoin(data,{'message.isTop':3949},false);
            fw.jsonJoin(data,{'message.isReceipt':3949},false);
            fw.jsonJoin(data,{'message.level':32084},false);
            fw.getComboTreeFromKV('isTop' + token, 'Is_Avaliable', null, data["message.isTop"]);
            fw.getComboTreeFromKV('isReceipt' + token, 'Is_Avaliable', null, data["message.isReceipt"]);
            fw.getComboTreeFromKV('level' + token, 'Message_Level', null, data["message.level"]);

        }, null);
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowMessageReadWindow(data) {
        data["message.OperatorId"] = loginUser.getId();
        var url = WEB_ROOT + "/modules/system/message/Message_Read.jsp?token=" + token;
        var windowId = "ArticleWindow" + token;
        fw.window(windowId, '公告信息', 800, 450, url, function () {
            // 初始化ckeditor
            initCKEditor();
            //初始化草稿保存事件
            // onClickArticleSave();

            // 加载数据
            fw.formLoad('formArticle' + token, data);

        }, null);
    }

    function initDepartmentTree(treeId) {
        var url = WEB_ROOT + "/system/Department_list.action";
        fw.treeLoad(treeId, url, null, null, null);
    }

    function initDepartmentStaffTree(treeId) {
        var url = WEB_ROOT + "/system/Position_listAllStaff.action";
        fw.treeLoad(treeId, url, null, null, null);
    }

    /**
     * 添加事件
     */
    function onClickArticleAdd() {

        var buttonId = "btnSystemMessageAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initWindowArticleWindow({});
        });

    }

    /**
     * 添加事件
     */
    function onClickArticleSendTo() {
        var buttonId = "btnMessageSendTo" + token;
        fw.bindOnClick(buttonId, function (process) {
            // 打开窗口，初始化表单数据为空
            initWindowMessageSendTo({});
        });

    }

    /**
     * 查看事件
     */
    function onClickArticleRead() {

        var buttonId = "btnSystemMessageRead" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ArticleTable' + token, function (selected) {
                var mid = selected.id;
                var url = WEB_ROOT + "/system/Message_load.action?message.id=" + mid;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    initWindowMessageReadWindow(data);
                }, null);
            })

        });

    }

    function onClickPublishedRead() {

        var buttonId = "btnPublishedMessageRead" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('PublishedTable' + token, function (selected) {
                var mid = selected.id;
                var url = WEB_ROOT + "/system/Message_load.action?message.id=" + mid;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    initWindowMessageReadWindow(data);
                }, null);
            })

        });

    }

    function onClickArticleEdit() {

        var buttonId = "btnSystemMessageEdit" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('ArticleTable' + token, function (selected) {
                var mid = selected.id;
                var url = WEB_ROOT + "/system/Message_load.action?message.id=" + mid;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    initWindowMessageEditWindow(data);
                }, null);
            })

        });

    }

    /**
     * 查看事件
     */
    function onClickArticleView(mid) {
        var url = WEB_ROOT + "/system/Message_load.action?message.id=" + mid;
        fw.post(url, null, function (data) {
            //fw.alertReturnValue(data);
            initWindowMessageReadWindow(data);
        }, null);
    }

    /**
     * 删除事件
     */
    function onClickArticleDelete() {
        var buttonId = "btnSystemMessageDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('DraftTable' + token, function (selected) {
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + "/system/Message_delete.action?message.id=" + selected.id;
                    //alert(url);
                    fw.post(url, null, function (data) {
                        //fw.alertReturnValue(data);
                        fw.datagridReload('DraftTable' + token);
                    }, null);
                }, null);
            });
        });
    }

    /**
     * 修改事件
     */
    function onClickMessageEdit() {
        var butttonId = "btnSystemMessageEdit" + token;
        fw.bindOnClick(butttonId, function (process) {

            fw.datagridGetSelected('DraftTable' + token, function (selected) {
                var sid = selected.id;
                var url = WEB_ROOT + "/system/Message_loadEdit.action?message.id=" + sid;
                fw.post(url, null, function (data) {
                    //fw.alertReturnValue(data);
                    initWindowMessageEditWindow(data);
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
            params["message_publishedTime_Start"] = fw.getFormValue('search_SubmitterTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params["message_publishedTime_End"] = fw.getFormValue('search_SubmitterTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params["messageVO.senderName"] = $("#search_SenderName" + token).val();
            params["messageVO.title"] = $("#search_Title" + token).val();
            $('#' + strTableId).datagrid('load');
            fw.treeClear();
        });
    }

    /**
     * 查询重置事件
     */
    function onClickArticleSearchReset() {
        var buttonId = "btnResetArticle" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#search_Title" + token).val('');
            //fw.combotreeClear('#search_ColumnId' + token);
        });
    }

    function initCKEditor() {
        using(SCRIPTS_ROOT + '/ckeditor/4.4.3/ckeditor.js', function () {
            CKEDITOR.replace('message.content', {
                toolbar: [
                    {name: 'document', items: ['Source', '-', 'NewPage', 'Preview', '-', 'Templates']},	// Defines toolbar group with name (used to create voice label) and items in 3 subgroups.
                    ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo'],			// Defines toolbar group without name.
                    {name: 'basicstyles', items: ['Bold', 'Italic']}
                ]
            });
        });
    }

    function onClickDepartmentOption() {
        $('#departmentOption' + token).tree({
            onBeforeCheck: function (node) {
                var len = node.id.toString().length;
                if (len > 1) {
                    treeReloadPositionTree(node);
                }
            }
        });
    }

    /**
     * 数据提交事件
     */
    function onClickArticleSubmit() {
        var buttonId = "btnMessageSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#status" + token).val('32077');//发送
            var formId = "formArticle" + token;
            var url = WEB_ROOT + "/system/Message_insertOrUpdate.action";

            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("PublishedTable" + token);
                fw.datagridReload("ArticleTable" + token);
                fw.windowClose('ArticleWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }


    /**
     * 数据提交事件
     */
    function onClickMessageSave() {
        var buttonId = "btnMessageSave" + token;
        fw.bindOnClick(buttonId, function (process) {
            $("#status" + token).val('32076');//草稿

            var formId = "formArticle" + token;
            var url = WEB_ROOT + "/system/Message_insertOrUpdate.action";

            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload("DraftTable" + token);
                fw.datagridReload("ArticleTable" + token);
                fw.datagridReload("PublishedTable" + token);
                fw.windowClose('ArticleWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    function treeReloadPositionTree(node) {
        var departmentId = node.id;
        var loadPositionUrl = WEB_ROOT + "/system/Position_listRecipient.action?position.DepartmentId=" + departmentId;
        if (node.checked == false) {
            if (dId != null && tIds.length > 0) {
                uIds[dId] = uniqueArray(tIds);
            }
            dId = node.text;

            fw.treeLoad('positionTree' + token, loadPositionUrl, null, null, null);

            //var dNodes = $('#departmentOption'+token).tree('getChecked');
            //for(var i=0; i<dNodes.length; i++){
            //alert(dNodes[i].parentId+"--"+dNodes[i].text);
            //dIds.push(dNodes[i].text);
            //}
            //dIds.push(node.text);


            $('#positionTree' + token).tree({
                onCheck: function (leafNode) {
                    treeReloadUserTree(leafNode);
                }
            });
        } else {
            delete uIds[node.text];
            tIds = [];

            var tree = fw.getObjectFromId('positionTree' + token);
            fw.treeClear(tree);
        }
    }

    function treeReloadUserTree(node) {
        tIds = [];
        var uNodes = $('#positionTree' + token).tree('getChecked');

        for (var i = 0; i < uNodes.length; i++) {
            if (uNodes[i].position == undefined) {
                tIds.push(uNodes[i].id);
            }
        }
    }

    /**

     * 去除数组重复元素

     */

    function uniqueArray(data) {
        data = data || [];
        var a = {};
        for (var i = 0; i < data.length; i++) {
            var v = data[i];
            if (typeof(a[v]) == 'undefined') {
                a[v] = 1;
            }
        }
        ;
        data.length = 0;
        for (var i in a) {
            data[data.length] = i;
        }
        return data;
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


