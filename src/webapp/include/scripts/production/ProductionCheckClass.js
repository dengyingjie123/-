/**
 * Created by Administrator on 2015/4/9.
 */
/**
 * 产品审核
 * @param token
 * @returns {{initModule: initModule}}
 * @constructor
 */
var ProductionCheckClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickProductionCheckSearch();
        // 初始化查询重置事件
        onClickProductionCheckSearchReset();
        // 初始化表格
        initProductionCheckTable();
    }
    // 构造初始化表格脚本
    function initProductionCheckTable() {
        var strTableId = 'ProductionCheckTable'+token;
        var url = WEB_ROOT+"/production/ProductionCheck_list.action";

        $('#'+strTableId).datagrid({
            title: '产品审核',
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
                { field: 'productionName', title: '产品名称', width: 30 },
                { field: 'checker1Name', title: '审核人1', width: 30 },
                { field: 'checker1Time', title: '审核时间1', width: 30 },
                { field: 'checker1TagName', title: '审核标识1', width: 30 },
                { field: 'checker1Content', title: '审核内容1', width: 30 },
                { field: 'checker2Name', title: '审核人2', width: 30 },
                { field: 'checker2Time', title: '审核时间2', width: 30 },
                { field: 'checker2TagName', title: '审核标识2', width: 30 },
                { field: 'checker2Content', title: '审核内容2', width: 30 },
                { field: 'checker3Name', title: '审核人3', width: 30 },
                { field: 'checker3Time', title: '审核时间3', width: 30 },
                { field: 'checker3TagName', title: '审核标识3', width: 30 },
                { field: 'checker3Content', title: '审核内容3', width: 30 }
            ]],
            toolbar:[{
                id:'btnProductionCheckAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnProductionCheckEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnProductionCheckDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                onClickProductionCheckAdd();
                onClickProductionCheckEdit();
                onClickProductionCheckDelete();
            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initProductionCheckWindow(data) {

        data["productionCheck.operatorId"] = loginUser.getId();
        data["productionCheck.operatorName"] = loginUser.getName();


        var url =  WEB_ROOT + "/modules/production/ProductionCheck_Save.jsp?token="+token;
        var windowId = "ProductionCheckWindow" + token;

        fw.window(windowId, '产品审核', 610, 510, url, function() {

            // 初始化表单提交事件
            onClickProductionCheckSubmit();

            // 加载数据
            fw.formLoad('formProductionCheck'+token, data);
        }, null);
    }
    /**
     * 数据提交事件
     */
    function onClickProductionCheckSubmit() {
        var buttonId = "btnProductionCheckSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formProductionCheck" + token;
            var url = WEB_ROOT + "/production/ProductionCheck_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {

                process.afterClick();
                fw.datagridReload("ProductionCheckTable"+token);
                fw.windowClose('ProductionCheckWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    /**
     * 添加事件
     */
    function onClickProductionCheckAdd(){

        var buttonId = 'btnProductionCheckAdd'+token;
          fw.bindOnClick(buttonId,function(process){
              process.beforeClick();
              initProductionCheckWindow({});
              process.afterClick();
          })

    }
    /**
     * 修改事件
     */
    function onClickProductionCheckEdit() {
        var buttonId = "btnProductionCheckEdit" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProductionCheckTable'+token, function(selected){
                process.beforeClick()
                var id = selected.id;
                var url = WEB_ROOT + "/production/ProductionCheck_load.action?productionCheck.id="+selected.id;
                fw.post(url, null, function(data){


                    data["productionCheck.checker3Name"]=selected.checker3Name;
                    data["productionCheck.checker2Name"]=selected.checker2Name;
                    data["productionCheck.checker1Name"]=selected.checker1Name;
                    //产品名
                    data["productionCheck.productionName"]=selected.productionName;
                    //下拉列表选值
                    $('#checker1Tag'+token).selectedIndex=data["productionCheck.checker1Tag"];
                    $('#checker2Tag'+token).selectedIndex=data["productionCheck.checker2Tag"];
                    $('#checker3Tag'+token).selectedIndex=data["productionCheck.checker3Tag"];

                    //判断权限
                    //判断审核人一是有人审核
//                    if(data['productionCheck.checker1Id'] != '' || data['productionCheck.checker1Id'] != null ){
//                        $("#checker1Tag"+token).attr("readonly","readonly");
//                        $("#checker1Time"+token).attr("readonly","readonly");
//                        $("#checker1Content"+token).attr("readonly","readonly");
//                        $("#checker1Id"+token).attr("readonly","readonly");
//                    }
                    initProductionCheckWindow(data);
                    process.afterClick();
                },function(){
                    process.afterClick();
                });
            });
        });
    }
    /**
     * 删除事件
     */
    function onClickProductionCheckDelete() {
        var buttonId = "btnProductionCheckDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            process.beforeClick()
            fw.datagridGetSelected('ProductionCheckTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/production/ProductionCheck_delete.action?productionCheck.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('ProductionCheckTable'+token);
                        process.afterClick();
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }
    /**
     * 查询事件
     */
    function onClickProductionCheckSearch() {
        var buttonId = "btnProductionCheckSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "ProductionCheckTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            //查询
            params["productionCheckVO.productionName"] = $("#search_productionName"+token).val();
            $( '#' + strTableId).datagrid('load');


            fw.treeClear()
        });
    }
    /**
     * 查询重置事件
     */
    function onClickProductionCheckSearchReset() {
        var buttonId = "btnProductionCheckSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_productionName"+token).val('');
        });
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

