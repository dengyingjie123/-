/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/14/14
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
var ProjectClass = function(token) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {
        //初始化查询区域
        initSearchArea();
        // 初始化查询事件
        onClickProjectSearch();
        // 初始化查询重置事件
        onClickProjectSearchReset();

        // 初始化表格
        initTableProjectTable();
    }

    /**
     * 初始化查询区域
     */
      function initSearchArea(){
        fw.getComboTreeFromKV('search_Status'+token, 'Status', 'V',null);
        fw.getComboTreeFromKV('search_Type'+token, 'Type', 'V',null);
    }

    /**
     * 初始化表格
     */
    function initTableProjectTable() {
        var strTableId = 'ProjectTable'+token;
        var url = WEB_ROOT+"/production/Project_list.action";

        $('#'+strTableId).datagrid({
            title: '项目信息',
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
                { field: 'name', title: '名称', width: 30 },
                { field: 'size', title: '规模', width: 30 },
                { field: 'startTime', title: '开始时间', width: 30 },
                { field: 'endTime', title: '结束时间', width: 30 },
                { field: 'status', title: '状态', width: 30 },
                { field: 'type', title: '类型', width: 30 },
                { field: 'investmentDirection', title: '投资方向', width:30 },
                { field: 'partner', title: '合作方', width: 30 },
                { field: 'description', title: '描述', width: 30 },
                { field: 'industry', title: '所属行业', width: 30 }
            ]],
            onLoadSuccess:function() {
                onClickProjectAdd();
                onClickProjectDelete();
                onClickProjectEdit();
            }
        });
    }

    /**
     * 初始化弹出窗口
     * @param data
     */
    function initWindowProjectWindow(data) {

        data["project.OperatorId"] = loginUser.getId();

        var url =  WEB_ROOT + "/modules/production/Project_Save.jsp?token="+token;
        var windowId = "ProjectWindow" + token;
        fw.window(windowId, '项目信息', 580, 340, url, function() {

            fw.getComboTreeFromKV('statusId'+token, 'Project_Status', 'V', fw.getMemberValue(data, 'project.statusId'));
            fw.getComboTreeFromKV('typeId'+token, 'Project_Type', 'V', fw.getMemberValue(data, 'project.typeId'));
            fw.getComboTreeFromKV('investmentDirectionId'+token, 'InvestmentDirection', 'V', fw.getMemberValue(data, 'project.investmentDirectionId'));
            fw.getComboTreeFromKV('partnerId'+token, 'Partner', 'V', fw.getMemberValue(data, 'project.partnerId'));
            fw.getComboTreeFromKV('industry'+token, 'Industry', 'V', fw.getMemberValue(data, 'project.industry'));


            // 初始化表单提交事件
            onClickProjectSubmit();

            // 加载数据
            fw.formLoad('formProject'+token, data);

        }, null);
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 添加事件
     */
    function onClickProjectAdd() {

        var buttonId = "btnProjectAdd" + token;
        fw.bindOnClick(buttonId, function(process) {
            // 打开窗口，初始化表单数据为空
            initWindowProjectWindow({});
        });

    }

    /**
     * 删除事件
     */
    function onClickProjectDelete() {
        var buttonId = "btnProjectDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('ProjectTable'+token, function(selected){
                process.beforeClick();
                fw.confirm('删除确认', '是否确认删除数据？', function(){

                    var url = WEB_ROOT + "/production/Project_del.action?project.sid="+selected.sid;
                    $.post(url, null, function(data) {
//                        console.log(data);
                        var json="["+data+"]";
                        var jsonArray = eval('(' + json + ')');
                        if(jsonArray[0].returnValue[0].sta==1 ){
                            fw.alert("提示","该项目还有子产品，请在删除其产品后再进行删除！");
                        } else{

                            var url = WEB_ROOT + "/production/Project_delete.action?project.sid="+selected.sid;
                            //alert(url);
                            fw.post(url, null, function(data) {
                                //fw.alertReturnValue(data);
                                fw.datagridReload('ProjectTable'+token);
                                process.afterClick();
                            }, function () {
                                process.afterClick();
                            });
                        }
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
    function onClickProjectEdit() {
        var butttonId = "btnProjectEdit" + token;
        fw.bindOnClick(butttonId, function(process) {
            fw.datagridGetSelected('ProjectTable'+token, function(selected){
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/production/Project_load.action?project.sid="+sid;
                fw.post(url, null, function(data){
                    //fw.alertReturnValue(data);
                    initWindowProjectWindow(data);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            })

        });
    }

    /**
     * 查询事件
     */
    function onClickProjectSearch() {
        var buttonId = "btnSearchProject" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "ProjectTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["projectVO.name"] = $("#search_Name"+token).val();
            params["projectVO.status"] = fw.getFormValue('search_Status'+token, fw.type_form_combotree, fw.type_get_text);
            params["projectVO.type"] = fw.getFormValue('search_Type'+token, fw.type_form_combotree, fw.type_get_text);
            $( '#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
            fw.treeClear()
        });

    }

    /**
     * 查询重置事件
     */
    function onClickProjectSearchReset() {
        var buttonId = "btnResetProject" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_Name"+token).val('');
            fw.combotreeClear('#search_Status'+token);
            fw.combotreeClear('#search_Type'+token);
        });
    }

    /**
     * 数据提交事件
     */
    function onClickProjectSubmit() {
        var buttonId = "btnProjectSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var text=$("#size"+token).val();
            var exp = /^([1-9][\d]{0,14}|0)(\.[\d]{1,4})?$/;
            if(exp.test(text)){
                var formId = "formProject" + token;
                var url = WEB_ROOT + "/production/Project_insertOrUpdate.action";
                fw.bindOnSubmitForm(formId, url, function(){
                    process.beforeClick();
                }, function() {
                    //alert('done');
                    process.afterClick();
                    fw.datagridReload("ProjectTable"+token);
                    fw.windowClose('ProjectWindow'+token);
                }, function() {
                    process.afterClick();
                });

            } else{
                alert("规模处请输入正确的数字类型！");

            }
        });
    }

    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        }
    };
}