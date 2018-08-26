//系统统计

var StatisticsClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickStatisticsSearch();
        // 初始化查询重置事件
        onClickStatisticsSearchReset();
        // 初始化表格
        initStatisticsTable();
    }
    //初始化查询事件
  function  onClickStatisticsSearch(){
      var buttonId = "btnStatisticsSearchSubmit" + token;
      fw.bindOnClick(buttonId, function(process) {
          var strTableId = "statisticsTable"+token;
          var params = $( '#' + strTableId).datagrid('options').queryParams;
           //名称
          params['statisticsVO.name']=$("#search_name"+token).val();
           //表示
          params['statisticsVO.tag']=$("#search_tag"+token).val();
          //数值
          params['statisticsVO.v']=$("#search_v"+token).val();
          $( '#' + strTableId).datagrid('load');

      });
  }
    /**
     * 查询重置事件
     */
    function onClickStatisticsSearchReset() {
        var buttonId = "btnStatisticsSearchReset" + token;
        fw.bindOnClick(buttonId, function(process) {
            //清空数据
            $("#search_name"+token).val('');
            $("#search_tag"+token).val('');
            $("#search_v"+token).val('');
        });
    }
    // -----------------------------------------------------------------------------
// 构造初始化表格脚本
    function initStatisticsTable() {
        var strTableId = 'statisticsTable'+token;

        var url = WEB_ROOT+"/system/Statistics_list.action";

        $('#'+strTableId).datagrid({
            title: '系统统计',
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
                { field: 'id', title: 'id',hidden:true, width: 30 },
                { field: 'name', title: '名称', width: 30 },
                { field: 'tag', title: '标识', width: 30 },
                { field: 'v', title: '数值', width: 30 },
                { field:'operatorName',title:'操作员',width:30},
                { field:'operateTime',title:'操作时间',width:30}
            ]],
            toolbar:[{
                id:'btnStatisticsAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnStatisticsEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnStatisticsDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                //添加数据
                onclickStatisticsAdd();
                //初始化修改事件
                onClickStatisticsEdit()
                //初始化删除事件
                onClickStatisticsDelete();
            }
        });
    }

    /**
     * 添加事件
     */
   function onclickStatisticsAdd(){
        //获取按钮ID
        var buttonId = 'btnStatisticsAdd'+token;

        //按钮点击事件
        fw.bindOnClick(buttonId,function(process){
            process.beforeClick();
            initStatisticsWindow({});
            process.afterClick();
        })

    }
    /**
     * 初始化表单提交数据
     */
    function onClickStatisticsSubmit() {
        var buttonId = "btnStatisticsSubmit" + token;
        fw.bindOnClick(buttonId, function(process){

            var formId = "formStatistics" + token;

            var url = WEB_ROOT + "/system/Statistics_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                //改变按钮的文本
                process.beforeClick();

            }, function() {
                //改变按钮文本
                process.afterClick();
                //刷新列表
                fw.datagridReload("statisticsTable"+token);
                //关闭窗口
                fw.windowClose('StatisticsWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }
    /**
     * 删除事件
     */
    function onClickStatisticsDelete() {
        var buttonId = "btnStatisticsDelete" + token;
       //按钮是事件
        fw.bindOnClick(buttonId, function(process) {
            //获取列表选中的数据行
            fw.datagridGetSelected('statisticsTable'+token, function(selected){
                process.beforeClick();
                //提示用户是都删除
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    //修改按钮文本
                    process.beforeClick();
                    var url = WEB_ROOT + "/system/Statistics_delete.action?statistics.sid="+selected.sid;
                   //请求删除数据
                    fw.post(url, null, function(data) {
                        //重新加载数据
                        fw.datagridReload('statisticsTable'+token);
                        //修改按钮文本
                    }, null);
                }, function(){
                    process.afterClick();
                });
            });
        });
    }
    /**
     * 修改事件
     */
    function onClickStatisticsEdit() {
        //获取修改按钮对象
        var buttonId = "btnStatisticsEdit" + token;
        //绑定事件
        fw.bindOnClick(buttonId, function(process) {
            //获取列表选中额数据
            fw.datagridGetSelected('statisticsTable'+token, function(selected){
                  //改变按钮文字
                process.beforeClick();
                //获取ID
                var id = selected.id;
                //设置URL
                var url = WEB_ROOT + "/system/Statistics_load.action?statistics.id="+selected.id;
                //请求获取数据
                fw.post(url, null, function(data){
                    initStatisticsWindow(data);
                    process.afterClick();
                }, null);
            })
        });
    }
    //初始化弹出窗口事件
    function initStatisticsWindow(data) {

        //设置操作员数据
        data["statistics.operatorId"] = loginUser.getId();
        data["statistics.operatorName"] = loginUser.getName();

        //显示的页面数据
        var url =  WEB_ROOT + "/modules/system/Statistics_Save.jsp?token="+token;
        var windowId = "StatisticsWindow" + token;
        //弹出窗口
        fw.window(windowId, '系统统计', 300, 250, url, function() {

            //按钮 提交事件
                onClickStatisticsSubmit();

            // 加载数据
            fw.formLoad('formStatistics'+token, data);
        }, null);
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