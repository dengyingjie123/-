/**
 * Created by Administrator on 2015/6/5.
 */
var FromEmailClass = function (token) {

    var callback4Select = undefined;

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickFromEmailSearch();
        // 初始化查询重置事件
        onClickFromEmailSearchReset();

        // 初始化表格
        initFromEmailTable();

        // 初始化查询表单
        initFromEmailSearchForm();

    }

    /**
     * 初始化查询表单
     */
    function initFromEmailSearchForm() {
    }


    // 构造初始化表格脚本
    function initFromEmailTable() {
        var strTableId = 'FromEmailTable' + token;
        var url = WEB_ROOT + '/oa/email/FromEmail_list.action';

        $('#' + strTableId).datagrid({
            title: '邮箱列表',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            singleSelect: true,
            pageList: [15, 30, 60],
            pageSize: 15,
            rownumbers: true,
            remoteSort: true,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: 'sid',//排序的列
            loadFilter: function (data) {
                try {
                    //获取数据 返回的Value
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
                    { field: 'sid', title: 'sid', hidden: true},
                    { field: 'id', title: 'id', hidden: true},
                    { field: 'state', title: 'state', hidden: true},
                    { field: 'operatorId', title: 'operatorId', hidden: true},
                    { field: 'operateTime', title: 'operateTime', hidden: true},
                    { field: 'fromName', title: '发送人', hidden: false, sortable: true},
                    { field: 'fromEmail', title: '发送人邮件', hidden: false, sortable: true},
                    { field: 'toName', title: '收件人', hidden: false, sortable: true},
                    { field: 'toEmail', title: '收件人邮件', hidden: false, sortable: true},
                    { field: 'emaioTitle', title: '标题', hidden: false, sortable: true},
                    { field: 'toTime', title: '发送时间', hidden: false, sortable: true}
                ]
            ],
            toolbar: [
                {
                    id: 'btnFromEmailAdd' + token,
                    text: '发送',
                    iconCls: 'icon-add'
                },
                {
                    id: 'btnFromEmailEdit' + token,
                    text: '详情',
                    iconCls: 'icon-edit'
                },
                {
                    id: 'btnFromEmailDelete' + token,
                    text: '删除',
                    iconCls: 'icon-cut'
                } ,
                {
                    id: 'btnFromEmailRefresh' + token,
                    text: '刷新',
                    iconCls: 'icon-search'
                },
                {
                    id:'btnDownloadAttachment'+token,
                    text:'下载全部附件',
                    iconCls:'icon-search'
                }
            ],
            onLoadSuccess: function () {
                // 初始化事件
                onClickFromEmailAdd();
                onClickFromEmailRefresh();
                onClickFromEmailEdit();
                onClickFromEmailDelete();
                onClickFromEmailDownload();
            }
        });
    }

    /**
     * 修改人：张舜清
     * 修改时间：6/15/15
     * 描述：添加事件
     */
    function onClickFromEmailAdd() {
        var buttonId = "btnFromEmailAdd" + token;
        fw.bindOnClick(buttonId, function (process) {
            var url = WEB_ROOT + '/oa/email/FromEmail_loadEmailAccount.action';
            fw.post(url, null, function (data) {
                initFromEmailWindow(data, null);
//                fw.alertReturnValue(data);
                data["fromEmail.fromName"] = loginUser.getName();
                data["fromEmail.fromEmail"] = data[0].Email;
            }, function () {
            });
        });
    }

    /**
     * 刷新事件
     */
    function onClickFromEmailRefresh() {
        var buttonId = 'btnFromEmailRefresh' + token;
        fw.bindOnClick(buttonId, function (process) {
            var url = WEB_ROOT + "/oa/email/FromEmail_refresh.action";
            process.beforeClick();
            fw.post(url, null, function () {
                $('#FromEmailTable' + token).datagrid('load');
                process.afterClick();
            }, function () {
                process.afterClick();
            })
        });
    }

    /**
     * 修改事件
     */
    function onClickFromEmailEdit() {
        var buttonId = 'btnFromEmailEdit' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FromEmailTable' + token, function (selected) {
                process.beforeClick();
                var id = selected.id
                var url = WEB_ROOT + '/oa/email/FromEmail_load.action?fromEmail.id=' + selected.id;
                fw.post(url, null, function (data) {
                    initFromEmailWindow(data, "");
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 删除事件
     */
    function onClickFromEmailDelete() {
        var buttonId = 'btnFromEmailDelete' + token;
        fw.bindOnClick(buttonId, function (process) {
            fw.datagridGetSelected('FromEmailTable' + token, function (selected) {
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function () {
                    var url = WEB_ROOT + '/oa/email/FromEmail_delete.action?fromEmail.sid=' + selected.sid;
                    fw.post(url, null, function (data) {
                        process.afterClick();
                        fw.datagridReload('FromEmailTable' + token);
                    }, null);
                }, function () {
                    process.afterClick();
                });
            });
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initFromEmailWindow(data, sid) {

        // fw.alertReturnValue(data);

        data['fromEmail.operatorId'] = loginUser.getId();
        data['fromEmail.operatorName'] = loginUser.getName();

        var url = WEB_ROOT + '/modules/oa/email/FromEmail_Save.jsp?token=' + token;
        var windowId = 'FromEmailWindow' + token;
        fw.window(windowId, '窗口', 800, 500, url, function () {
            fw.initCKEditor("fromEmail.emailContent");

            // 初始化提交事件
            onClickFromEmailSubmit();
            // 加载数据
            fw.formLoad('formFromEmail' + token, data);

            $("#emailContent" + token).val(data["fromEmail.emailContent"]);
            //提交事件
            if (sid == "") {
                $("#btnFromEmailSubmit" + token).remove();
            }
            if (sid == null) {
                $("#toTime" + token).val(fw.getDateTime());
            }

        });
    }

    /**
     * 数据提交事件
     */
    function onClickFromEmailSubmit() {
        var buttonId = 'btnFromEmailSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = 'formFromEmail' + token;
            var attachmentPath = $('#attachment'+token).val();
            if(attachmentPath.length == 0){
                var url = WEB_ROOT + '/oa/email/FromEmail_FromToEmail.action';
            }else{
                var url = WEB_ROOT + '/oa/email/FromEmail_uploadAttachment.action';
            }

            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                process.afterClick();
                fw.datagridReload('FromEmailTable' + token);
                fw.windowClose('FromEmailWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }

    /**
     * 查询事件
     */
    function onClickFromEmailSearch() {
        var buttonId = 'btnFromEmailSearchSubmit' + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = 'FromEmailTable' + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;

            params['fromEmail.fromName'] = $('#Search_FromName' + token).val();
            params['fromEmail.fromEmail'] = $('#Search_FromEmail' + token).val();
            params['fromEmail.toName'] = $('#Search_ToName' + token).val();
            params['fromEmail.toEmail'] = $('#Search_ToEmail' + token).val();
            params['fromEmail.emaioTitle'] = $('#Search_EmaioTitle' + token).val();
            params['fromEmail.emailContent'] = $('#Search_EmailContent' + token).val();
            params['fromEmail_toTime_Start'] = fw.getFormValue('Search_ToTime_Start' + token, fw.type_form_datebox, fw.type_get_value);
            params['fromEmail_toTime_End'] = fw.getFormValue('Search_ToTime_End' + token, fw.type_form_datebox, fw.type_get_value);
            params['fromEmail.toTime'] = $('#Search_ToTime' + token).val();


            $('#' + strTableId).datagrid('load');
        });
    }

    /**
     * jiangwandong 15/6/16
     * 添加下载全部附件方法
     */

    function onClickFromEmailDownload(){
        var buttonId = "btnDownloadAttachment"+token;
        var url = WEB_ROOT + '/oa/email/FromEmail_downloadAll.action';
        fw.bindOnClick(buttonId , function(process){
            process.beforeClick();
            fw.post(url  , function(){
                alert('下载成功！');
                process.afterClick();
            },function(){
                process.afterClick();
            });
        })
    }

    /**
     * 查询重置事件
     */
    function onClickFromEmailSearchReset() {
        var buttonId = 'btnFromEmailSearchReset' + token;
        fw.bindOnClick(buttonId, function (process) {
            // 清空时间文本框
            $('#Search_FromName' + token).val('');
            $('#Search_FromEmail' + token).val('');
            $('#Search_ToName' + token).val('');
            $('#Search_ToEmail' + token).val('');
            $('#Search_EmaioTitle' + token).val('');
            $('#Search_EmailContent' + token).val('');
            $('#Search_ToTime_Start' + token).datebox('setValue', '');
            $('#Search_ToTime_End' + token).datebox('setValue', '');
            $('#Search_ToTime' + token).val('');
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
