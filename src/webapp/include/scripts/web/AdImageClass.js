var AdImageClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        initFormAdImageSearch();
        onClickAdImageSearch();
        // 初始化查询重置事件
        onClickAdImageSearchReset();
        // 初始化表格
        initAdImageTable();
    }

    // 构造初始化表格脚本
    function initAdImageTable() {
        var strTableId = 'AdImageTable'+token;
        var url = WEB_ROOT+"/web/AdImage_list.action";

        $('#'+strTableId).datagrid({
            title: '图片广告信息',
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
                { field: 'sid', title: '序号', hidden:true, width: 30 },
                { field: 'id', title: '编号', hidden:true, width: 30 },
                { field: 'state', title: 'state',hidden:true, width: 30 },
                { field: 'operatorId', title: 'operatorId',hidden:true, width: 30 },
                { field: 'operateTime', title: 'operateTime', hidden:true, width: 30 },
                { field: 'catalogType', title: '归类编号', width: 30 },
                { field: 'name', title: '名称', width: 30 },
                { field: 'description', title: '描述', width: 30 },
                { field: 'url', title: 'url', width: 30 },
                { field: 'size', title: '大小', width: 30 },
                { field: 'width', title: '宽', width: 30 },
                { field: 'height', title: '高', width: 30 },
                { field: 'responseURL', title: '响应地址', width: 30 },
                { field: 'orders', title: '排序', width: 30 },
                { field: 'IsAvaliableType', title: '是否使用', width: 30 },
                { field: 'startTime', title: '启用时间', width: 30 },
                { field: 'endTime', title: '停用时间', width: 30 }
            ]],
//            toolbar:[{
//                id:'btnAdImageAdd'+token,
//                text:'添加',
//                iconCls:'icon-add'
//            },{
//                id:'btnAdImageEdit'+token,
//                text:'修改',
//                iconCls:'icon-edit'
//            },{
//                id:'btnAdImageDelete'+token,
//                text:'删除',
//                iconCls:'icon-cut'
//            }],
            onLoadSuccess:function() {
                // 初始化事件
                onclickAdImageAdd();
                onClickAdImageEdit();
                onClickAdImageDelete();
            }
        });
    }
    /**
     * 添加OA_任务
     */
    function onclickAdImageAdd(){
        var buttonId = "btnAdImageAdd"+token;
        fw.bindOnClick(buttonId,function(process){
            process.beforeClick();
            initAdImageWindow({});
            process.afterClick();
        })
    }
    /**
     * 查询事件
     */
    function onClickAdImageSearch() {
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "AdImageTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            //alert(JSON.stringify(params));
            //alert($("#search_user_name"+token).val());
            //alert(JSON.stringify(params));
// 查询部分
            //获取是减控件的值
            params["adImageVO_startTime_Start"] = fw.getFormValue('search_StartTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["adImageVO_startTime_End"] = fw.getFormValue('search_StartTime_End'+token, fw.type_form_datebox, fw.type_get_value);
            params["adImageVO_endTime_Start"] = fw.getFormValue('search_EndTime_Start'+token, fw.type_form_datebox, fw.type_get_value);
            params["adImageVO_endTime_End"] = fw.getFormValue('search_EndTime_End'+token, fw.type_form_datebox, fw.type_get_value);

            params["adImageVO.catalogId"] = fw.getFormValue('search_CatalogId' + token, fw.type_form_combotree, fw.type_get_value);
            params["adImageVO.isAvaliable"] = fw.getFormValue('search_isAvaliable' + token, fw.type_form_combotree, fw.type_get_value);
            params["adImageVO.name"] = $("#search_Name"+token).val();
            params["adImageVO.description"] = $("#search_Description"+token).val();
            $( '#' + strTableId).datagrid('load');
        });
    }

    /**
     * 查询重置事件
     */
    function onClickAdImageSearchReset() {
        var buttonId = "btnSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 重置部分
            fw.combotreeClear('#search_CatalogId'+token);
            fw.combotreeClear('#search_isAvaliable'+token);
            $("#search_Name"+token).val('');
            $("#search_Description"+token).val('');

            $('#search_StartTime_Start'+token).datebox("setValue", '');
            $('#search_StartTime_End'+token).datebox("setValue", '');
            $('#search_EndTime_Start'+token).datebox("setValue", '');
            $('#search_EndTime_End'+token).datebox("setValue", '');
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initAdImageWindow(data) {

        data["adImage.operatorId"] = loginUser.getId();
        data["adImage.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/web/AdImage_Save.jsp?token="+token;
        var windowId = "AdImageWindow" + token;
        fw.window(windowId, '图片广告', 500, 530, url, function() {

            // 初始化控件

            // 初始化类型combotree
             fw.getComboTreeFromKV('catalogId'+token, 'Web_AdImageCatalog', 'k', fw.getMemberValue(data, 'adImage.catalogId'));
             fw.getComboTreeFromKV('isAvaliable'+token, 'Is_Avaliable', 'k', fw.getMemberValue(data, 'adImage.isAvaliable'));

            // 初始化表单提交事件
             onClickAdImageSubmit();

            // 加载数据
            fw.formLoad('formAdImage'+token, data);
        }, null);
    }
    /**
     * 数据提交事件
     */
    function onClickAdImageSubmit() {
        var buttonId = "btnAdImageSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formAdImage" + token;
            var url = WEB_ROOT + "/web/AdImage_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                //alert('done');
                process.afterClick();
                fw.datagridReload("AdImageTable"+token);
                fw.windowClose('AdImageWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }
    /**
     * 修改事件
     */
    function onClickAdImageEdit() {
        var butttonId = "btnAdImageEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('AdImageTable'+token, function(selected){
                process.beforeClick();
                var id = selected.id;
                var url = WEB_ROOT + "/web/AdImage_load.action?adImage.id="+selected.id;
                fw.post(url, null, function(data){
                    // data["oc.orderNum"]=selected.orderNum;
                    // fw.alertReturnValue(data);
                    initAdImageWindow(data,null);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })
        });
    }
    /**
     * 删除事件
     */
    function onClickAdImageDelete() {
        var buttonId = "btnAdImageDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('AdImageTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/web/AdImage_delete.action?adImage.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('AdImageTable'+token);
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }

    function initFormAdImageSearch() {
          fw.getComboTreeFromKV('search_CatalogId'+token, 'Web_AdImageCatalog', 'k','-2');
          fw.getComboTreeFromKV('search_isAvaliable'+token, 'Is_Avaliable', 'k','-2');
    }
    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        }
    };
};
