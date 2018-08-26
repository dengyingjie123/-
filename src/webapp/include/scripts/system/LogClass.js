var LogClass = function (token) {

    function onClickLogSearch() {
        var buttonId = "btnSearchLog" + token;
        fw.bindOnClick(buttonId, function (process) {
            var strTableId = "LogTable" + token;
            var params = $('#' + strTableId).datagrid('options').queryParams;
            params["log.operatorName"] = $("#search_logPOOperatorName" + token).val();
            params["log.name"] = $("#search_logPOName" + token).val();
            params["log.machineMessage"] = $("#search_machineMessage" + token).val();
            params["log.peopleMessage"] = $("#search_peopleMessage" + token).val();
            params["log.url"] = $("#search_url" + token).val();
            $('#' + strTableId).datagrid('load');                         //加载第一页的行
        });
    }

    function onClickLogSearchReset() {
        var buttonId = "btnSearchLogReset" + token;
        fw.bindOnClick(buttonId, function (process) {
            $('#search_logPOOperatorName' + token).val('');
            $("#search_logPOName" + token).val('');
            $("#search_machineMessage" + token).val('');
            $("#search_url" + token).val('');
            $("#peopleMessage" + token).val('');
        });
    }

    function initWindowLogWindow(data) {

        var url = WEB_ROOT + "/modules/system/Log_Save.jsp?token=" + token;
        var windowId = "LogWindow" + token;
        fw.window(windowId, '日志信息', 600, 550, url, function () {
            //fw.alertReturnValue(data);
            fw.jsonJoin(data, {"parameters":fw.convert2String(data['parameters'])}, true);
            fw.formLoad('formLogPO' + token, data);
        }, null);
    }

    function initAll() {
        onClickLogSearch();
        onClickLogSearchReset();
        initTableLogTable();
    }

    function initTableLogTable() {
        var strTableId = 'LogTable' + token;
        var url = WEB_ROOT + "/system/Log_listPagerLogPO.action";

        $('#' + strTableId).datagrid({
            title: '产品属性',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg: '数据正在加载，请稍后……',
            rownumbers: true,
            singleSelect: true,
            pageList: [12, 30, 60],
            pageSize: 12,
            remoteSort: false,//是否从数据库排序
            sortOrder: 'desc',//排序方法 默认
            sortName: "sid",//排序的列
            onSortColumn: function (sort, orders) {
            },
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
                    {field: 'ck', checkbox: true},
                    { field: 'operateTime', title: '操作时间'},
                    { field: 'operatorId', title: '操作人ID',hidden:true},
                    { field: 'operatorName', title: '操作人'},
                    { field: 'name', title: '名称'},
                    { field: 'peopleMessage', title: 'peopleMessage',
                        formatter:function(value, row, index){
                            return fw.stringSubString(value, 0, 100);
                        }
                    }
                ]
            ],
            columns: [
                [
                    { field: 'sid', title: '序号',hidden:true},
                    { field: 'id', title: '编号',hidden:true},
                    { field: 'machineMessage', title: 'machineMessage',
                        formatter:function(value, row, index) {
                            return fw.stringSubString(fw.convert2String(value), 0, 100);
                        }
                    },
                    { field: 'url', title: 'url',
                        formatter:function(value, row, index){
                            return fw.stringSubString(value, 0, 100);
                        }
                    },
                    { field: 'IP', title: 'IP'}

                ]
            ],
            onLoadSuccess: function () {

            },
            onDblClickRow:function(rowIndex, rowData){
                initWindowLogWindow(rowData);
            }
        });
    }

    return {
        initModule: function () {
            return initAll();
        }
    }
}