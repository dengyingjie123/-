/**
 * Created by Administrator on 2015/4/3.
 */
/*
 * 修改:周海鸿
 * 时间：2015-7-13
 * 内容:添加按钮移除参数
 *
 * "remove" 为移除添加、删除、修改 上传按钮。其他 不操作*/
var FilesClass = function (token, moduleId, btnStatus, bizId) {

        ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
        /**
         * 初始化主页面控件
         */
        function initAll() {
            // 初始化查询事件
            onClickFilesSearch();
            // 初始化查询重置事件
            onResetFilesSearch()
            // 初始化表格
            if (moduleId == "") {
                initTableFiles();
            } else {
                initTableFilesUpload();
            }
            //初始化上传事件
            onclickFilesUpload();
            /**
             * 初始化关闭窗口事件
             */
            onCloseWindowFile();

        }

        /**
         * 初始化上传表格
         */
        function initTableFilesUpload() {
            var strTableId = 'FilesTable' + token;
            var url = WEB_ROOT + "/system/Files_list.action?filesVO.moduleId=" + moduleId + "&filesVO.bizId=" + bizId;
            $('#' + strTableId).datagrid({
                title: '文件列表',
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
                    //alert(JSON.stringify(data));
                    //alert(data.code);
                    try {
                        data = fw.dealReturnObject(data);
                        //fw.alertReturnValue(data);
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
                        {field: 'name', title: '中文名', hidden:true},
                        {field: 'fileName', title: '文件名'},
                        {field: 'extensionName', title: '扩展名'},
                        {field: 'size', title: '大小(Byte)'},
                        {field: 'path', hidden: true, title: '保存路径'},
                        {field: 'createTime', title: '创建时间'},
                        {field: 'checkCode', title: '校验码'},
                        {field: 'moduleId', hidden: true, title: '所属模块编号'},
                        {field: 'moduleDescriptionName', hidden: true, title: '所属模块描述'},
                        {field: 'operatorName', title: '操作员'},
                        {field: 'description', title: '描述'}
                    ]
                ],
                toolbar: [
                    /* {
                     id: 'btnFilesAdd' + token,
                     text: '添加',
                     iconCls: 'icon-add'
                     },*/
                    {
                        id: 'btnFilesEdit' + token,
                        text: '修改',
                        iconCls: 'icon-edit'
                    },
                    {
                        id: 'btnFilesDelete' + token,
                        text: '删除',
                        iconCls: 'icon-cut'
                    },
                    {
                        id: 'btnFilesPerview2' + token,
                        text: '预览/下载',
                        iconCls: 'icon-search'
                    }
                ],
                onLoadSuccess: function () {
                    onClickFilesAdd();
                    onClickFilesDelete();
                    onClickFilesEdit();
                    onClickFilesPreview("btnFilesPerview2" + token, strTableId);
                }

            });
            //将所有可以修改的按钮去除
            if (btnStatus == "remove") {
                //移除添加、修改、删除按钮
                //  $("#btnFilesAdd"+token).remove();
                $("#btnFilesEdit" + token).remove();
                $("#btnFilesDelete" + token).remove();
                //移除上传按钮
                $("#btnFilesUpload" + token).remove();
            }
        }

        //初始化表格
        function initTableFiles() {

            var strTableId = 'FilesTable' + token;
            var url = WEB_ROOT + "/system/Files_list.action?filesVO.moduleId=" + moduleId + "&filesVO.bizId=" + bizId;
            $('#' + strTableId).datagrid({
                title: '文件列表',
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
                    //alert(JSON.stringify(data));
                    //alert(data.code);
                    try {
                        data = fw.dealReturnObject(data);
                        //fw.alertReturnValue(data);
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
                        {field: 'name', title: '中文名', hidden:true},
                        {field: 'fileName', title: '文件名'},
                        {field: 'extensionName', title: '扩展名'},
                        {field: 'size', title: '大小(Byte)'},
                        {field: 'path', title: '保存路径'},
                        {field: 'createTime', title: '创建时间'},
                        {field: 'checkCode', title: '校验码'},
                        {field: 'moduleId', title: '所属模块编号'},
                        {field: 'moduleDescriptionName', title: '所属模块描述'},
                        {field: 'operatorName', title: '操作员'},
                        {field: 'description', title: '描述'}
                    ]
                ],
                toolbar: [
                    /*  {
                     id: 'btnFilesAdd' + token,
                     text: '添加',
                     iconCls: 'icon-add'
                     },*/
                    {
                        id: 'btnFilesEdit' + token,
                        text: '修改',
                        iconCls: 'icon-edit'
                    },
                    {
                        id: 'btnFilesDelete' + token,
                        text: '删除',
                        iconCls: 'icon-cut'
                    },
                    {
                        id: 'btnFilesPerview' + token,
                        text: '预览/下载',
                        iconCls: 'icon-search'
                    }
                ],
                onLoadSuccess: function () {
                    onClickFilesAdd();
                    onClickFilesDelete();
                    onClickFilesEdit();
                    onClickFilesPreview("btnFilesPerview" + token, strTableId);
                }
            });
        }

        /**
         * 添加事件
         */
        function onClickFilesAdd() {
            var buttonId = "btnFilesAdd" + token;
            fw.bindOnClick(buttonId, function (process) {
                // 打开窗口，初始化表单数据为空
                initWindowFiles({});
            });
        }

        /**
         * 删除事件
         */
        function onClickFilesDelete() {
            var buttonId = "btnFilesDelete" + token;

            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected("FilesTable" + token, function (selected) {
                    fw.confirm('删除确认', '是否确认删除数据？', function () {
                        var url = WEB_ROOT + "/system/Files_delete.action?files.sid=" + selected.sid;
                        fw.post(url, null, function (data) {
                            fw.datagridReload("FilesTable" + token);
                        }, null);
                    }, null);
                });
            });
        }

        //修改事件
        function onClickFilesEdit() {
            var buttonId = "btnFilesEdit" + token;
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected("FilesTable" + token, function (selected) {
                    process.beforeClick();
                    var id = selected.id;
                    var url = WEB_ROOT + "/system/Files_load.action?files.id=" + id;
                    fw.post(url, null, function (data) {
                        data["files.moduleDescription"] = selected.moduleDescriptionName
                        initWindowFiles(data);
                        process.afterClick();
                    }, function () {
                        process.afterClick();
                    });
                })

            });
        }

        /**
         * 初始化上传事件
         */
        function onclickFilesUpload() {

            var buttonId = "btnFilesUpload" + token;
            if (moduleId == "") {
                moduleId = "5458";
            }
            var url = WEB_ROOT + "/system/FileUpload_upload.action?files.moduleId=" + moduleId + "&files.bizId=" + bizId;
            var fromId = "formContractUpload" + token;
            fw.bindOnClick(buttonId, function (process) {
                if ($("#upload" + token).val() == "") {
                    fw.alert("提示", "请选择文件");
                    return;
                }

                if ($("#name" + token).val() == "") {
                    fw.alert("提示", "请输入文件名");
                    return;
                }
                // 获取文件类型
                var idxPoint = $("#upload" + token).val().lastIndexOf(".");
                var contentType = $("#upload" + token).val().substr(idxPoint + 1);
                if (contentType == "exe" ||
                    contentType == "bat" ||
                    contentType == "cmd" ||
                    contentType == "jar") {
                    fw.alert("提示", "文件类型不允许上传！");
                    return;
                }
                process.beforeClick();
                fw.bindOnSubmitForm(fromId, url, function () {
                    process.beforeClick();
                }, function () {
                    $("#name" + token).val("");
                    $("#upload" + token).val("");
                    fw.alert("提示", '提交成功！');
                    $('#FilesTable' + token).datagrid('load');
                    process.afterClick();
                })
            })
        }

        //初始化查询
        function onClickFilesSearch() {
            var buttonId = "btnSearchSubmit" + token;
            var strTableId = "FilesTable" + token;
            fw.bindOnClick(buttonId, function () {
                var params = $('#' + strTableId).datagrid('options').queryParams;

                //获取文件名
                params["filesVO.fileName"] = $('#search_FileName' + token).val();
                //获取扩展名
                params["filesVO.extensionName"] = $('#search_ExtensionName' + token).val();

                params["filesVO_createTime_Start"] = fw.getFormValue('search_CreateTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
                params["filesVO_createTime_End"] = fw.getFormValue('search_CreateTime_End' + token, fw.type_form_datebox, fw.type_get_value);
                $('#' + strTableId).datagrid('load');
            })
        }

        //初始化查询重置事件
        function onResetFilesSearch() {
            var buttonId = "btnSearchReset" + token;
            fw.bindOnClick(buttonId, function (process) {
                // 初始化文件名参数
                $('#search_FileName' + token).val("");
                // 初始化扩展名参数
                $('#search_ExtensionName' + token).val("");
                $('#search_CreateTime_Start' + token).datebox("setValue", '');
                $('#search_CreateTime_End' + token).datebox("setValue", '');
            });
        }

        //初始化弹窗
        function initWindowFiles(data) {
            data["files.operatorId"] = loginUser.getId();
            data["files.operatorName"] = loginUser.getName();
            var url = WEB_ROOT + "/modules/system/Files_Save.jsp?token=" + token;
            var windowId = "FilesWindow" + token;
            fw.window(windowId, '文件详情', 450, 380, url, function () {
                //数据提交事件
                onClickFilesSubmit();
                fw.formLoad('formFilesWindow' + token, data);
            }, null);
        }

        //数据提交事件
        function onClickFilesSubmit() {
            var buttonId = "btnFilesSubmit" + token;
            fw.bindOnClick(buttonId, function (process) {
                var formId = "formFilesWindow" + token;
                //更改url，走对应Action类的insertOrUpdate方法
                var url = WEB_ROOT + "/system/Files_insertOrUpdate.action";
                fw.bindOnSubmitForm(formId, url, function () {
                    process.beforeClick();
                }, function () {
                    process.afterClick();
                    fw.datagridReload("FilesTable" + token);
                    fw.windowClose('FilesWindow' + token);
                }, function () {
                    process.afterClick();
                });
            });
        }

        /**
         * 文件上传关闭关闭按钮事件
         */
        function onCloseWindowFile() {
            var windowId = "FilesUploadWindow" + token;
            var buttonId = "FilesUploadCloseBtn" + token;
            fw.bindOnClick(buttonId, function (num) {
                fw.windowClose(windowId);
                var buttonId = "btnUpload" + token;
                if (bizId != "") {
                    var URl = WEB_ROOT + "/system/Files_getBizIdCounts.action?moduleId=" + moduleId + "&bizId=" + bizId;
                    fw.post(URl, null, function (data) {
                        $("#" + buttonId).linkbutton({text: "已有" + data['bizIds'] + "个附件，点击上传", disabled: false});
                    }, null);
                }
            })
        }

        /**
         * 初始化文件预览与下载
         * @constructor
         */
        function onClickFilesPreview(buttonId, tableID) {
            //FileView(buttonId, tableID);
            fw.bindOnClick(buttonId, function (process) {
                fw.datagridGetSelected(tableID, function (selected) {
                    viewOrDownload(selected.path, selected.fileName + selected.extensionName);
                })
            });
        }

        function viewOrDownload(path, downloadName) {
            // 路径
            var filePath = path;

            //获取最后一个斜杠的索引
            var lastIndex = path.lastIndexOf('/');
            //获取文件名
            var filename = path.substring(lastIndex + 1);

            var lastDot = path.lastIndexOf('.');
            var contentType = path.substring(lastDot + 1);
            var pageURL = WEB_ROOT + "/system/file/FileDownload.action?filePath=" + filePath + "&fileName=" + filename + "&contentType=" + contentType + "&downloadName=" + downloadName;
            //alert(pageURL);
            window.open(pageURL, "查看附件", 'width=400px,height=400px,alwaysRaised=yes,hotkeys=yes,location=no,menubar=no,resizable=yes,scrollbars=yes,z-look=yes');

        }

        function onClickUpload(moduleId, bizId, helpArticleId) {
            var buttonId = "btnUpload" + token;
            if (bizId != "") {
                var URl = WEB_ROOT + "/system/Files_getBizIdCounts.action?moduleId=" + moduleId + "&bizId=" + bizId;
                fw.post(URl, null, function (data) {
                    if (!fw.checkIsTextEmpty(data['errorMessage'])) {
                        $("#" + buttonId).linkbutton({text: data['errorMessage'], disabled: true});
                    }
                    else {
                        $("#" + buttonId).linkbutton({text: "已有" + data['bizIds'] + "个附件，点击上传", disabled: false});
                        fw.bindOnClick(buttonId, function (process) {
                            process.beforeClick();
                            /**
                             * 知识库地址：http://c.hopewealth.net/pages/viewpage.action?pageId=27066375
                             */
                            fw.uploadFiles(token, moduleId, "", bizId, helpArticleId);
                            process.afterClick();
                        });
                    }
                }, null);
            }
        }

        return {
            /**
             * boot.js加载时调用的初始化方法
             */
            initModule: function () {
                return initAll();
            },
            initOnClickUploadButton:function(moduleId, bizId, helpArticleId){
                return onClickUpload(moduleId, bizId, helpArticleId);
            }
        };
    }
    ;