/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/15/14
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
var ProductionstageClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll(obj) {
        // 初始化表格
        initTableProductionstageTable(obj);
        initArea(obj);
    }
    function initArea(obj){
        if(obj==null){
            $("#btnProductionstageAdd"+token).remove();
            $("#btnProductionstageEdit"+token).remove();
            $("#btnProductionstageDelete"+token).remove();
        }
    }
    /**
     * 初始化表格
     */
    function initTableProductionstageTable(obj) {
        var id=obj;
        var strTableId = 'ProductionstageTable'+token;
        var url = WEB_ROOT+"/production/Productionstage_list.action?productionstage.productionId="+id;

        $('#'+strTableId).datagrid({
            title: '分期信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[10,20,30],
            pageSize: 10,
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
                { field: 'productionId', title: '编号', hidden:true, width: 30 },
                { field: 'name', title: '名称', width: 30 },
                { field: 'startTime', title: '开始时间', width: 30 },
                { field: 'stopTime', title: '结束时间', width: 30 },
                { field: 'valueDate', title: '起息日', width: 30 },
                { field: 'expiringDate', title: '到期日', width: 30 },
                { field: 'interestDate', title: '付息日', width:30 }
            ]],
            toolbar:[{
                id:'btnProductionstageAdd'+token,
                text:'新建分期',
                iconCls:'icon-add'
            },{
                id:'btnProductionstageEdit'+token,
                text:'修改分期',
                iconCls:'icon-edit'
            },{
                id:'btnProductionstageDelete'+token,
                text:'删除分期',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                onClickProductionstageAdd(obj);
                onClickProductionstageDelete();
                onClickProductionstageEdit(obj);
            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowProductionstageWindow(data,obj) {

        data["productionstage.OperatorId"] = loginUser.getId();

        var url =  WEB_ROOT + "/modules/production/Productionstage_Save.jsp?token="+token;
        var windowId = "ProductionstageWindow" + token;
        fw.window(windowId, '分期信息', 300, 350, url, function() {

            // 初始化表单提交事件
            onClickProductionstageSubmit();

            // 加载数据
            fw.formLoad('formProductionstage'+token, data);
            //获取产品id
            $("#productionId"+token).val(obj);
        }, null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickProductionstageAdd(obj) {

        var buttonId = "btnProductionstageAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowProductionstageWindow({},obj);
        });

    }

    /**
     * 删除事件
     */
    function onClickProductionstageDelete() {
        var buttonId = "btnProductionstageDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProductionstageTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/production/Productionstage_delete.action?productionstage.sid="+selected.sid;
                    //alert(url);
                    fw.post(url, null, function(data) {
                        //fw.alertReturnValue(data);
                        fw.datagridReload('ProductionstageTable'+token);
                    }, null);
                }, null);
            });
        });
    }
    /**
     * 修改事件
     */
    function onClickProductionstageEdit(obj) {
        var butttonId = "btnProductionstageEdit" + token;
        fw.bindOnClick(butttonId, function(process) {

            fw.datagridGetSelected('ProductionstageTable'+token, function(selected){
                var sid = selected.sid;
                var url = WEB_ROOT + "/production/Productionstage_load.action?productionstage.sid="+sid;
                fw.post(url, null, function(data){
                    //fw.alertReturnValue(data);
                    initWindowProductionstageWindow(data,obj);
                }, null);
            })

        });
    }

    /**
     * 数据提交事件
     */
    function onClickProductionstageSubmit() {
        var buttonId = "btnProductionstageSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
//            var text=$("size"+token).val();                 alert($("#size"+token).val())  ;
//            var exp = /^([1-9][\d]{0,14}|0)(\.[\d]{1,4})?$/;
//            if(exp.test(text)){
//              var leng= DataLength($("#name"+token).val());            alert($("#name"+token).val());
//            if(leng<40){
                var formId = "formProductionstage" + token;
                var url = WEB_ROOT + "/production/Productionstage_insertOrUpdate.action";
                fw.bindOnSubmitForm(formId, url, function(){
                    process.beforeClick();
                }, function() {
                    //alert('done');
                    process.afterClick();
                    fw.datagridReload("ProductionstageTable"+token);
                    fw.windowClose('ProductionstageWindow'+token);
                }, function() {
                    process.afterClick();
                });
//
//            } else{
//                alert("请输入正确的数字类型！");
//
//            }
        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(obj){
            return initAll(obj);
        }
    };
}
function DataLength(name)
{
    var fData=name.value;
    var intLength=0
    for (var i=0;i<fData.length;i++)
    {
        if ((fData.charCodeAt(i) < 0) || (fData.charCodeAt(i) > 255))
            intLength=intLength+2
        else
            intLength=intLength+1
    }
    if(intLength>80){
        alert("分期名称字数不得大于40个汉字！");
        name.value='';
    }
}
function TextSize(size){
      var text=size.value;
      var exp = /^([1-9][\d]{0,14}|0)(\.[\d]{1,4})?$/;
      if(!exp.test(text)){
        alert("请输入正确的数字类型！");
        size.value='';
    }
}