var ColumnClass = function(token) {

    function initAll(){

        var departmentTreeId = "departmentOption"+token;
        initDepartmentTree(departmentTreeId);
        onClickDepartmentOption();

    }

    //加载DepartmentTree机构列表
    function initDepartmentTree(treeId){
        var url = WEB_ROOT+"/system/Department_list.action";
        fw.treeLoad(treeId,url,null, null, null);
    }

    function initColumnTree(treeId) {
        var url = WEB_ROOT+"/cms/Column_getTree.action";
        //alert(treeId);
        fw.treeLoad(treeId,url,null, null, null);
    }

    //点击DepartmentTreeNode事件，加载columnTable
    function onClickDepartmentOption() {
        $('#departmentOption'+token).tree({onSelect:function(node) {
           // initTableColumnTable(node.id);
            initTalbeSummaryTreeTable(node.id);
        }});
    }

    //初始化栏目信息表treegrid


    function initTalbeSummaryTreeTable(departmentId) {
        var strTableId = '#columnTable' + token;
        var dept = $('#departmentOption'+token).tree('getSelected');
        var departmentIda=departmentId;
        if(dept!=null){
            departmentIda=dept.id;
        }
        var url = WEB_ROOT + "/cms/Column_getColumnTreeList.action?&departmentId="+departmentIda;

        //alert(url);

        $(strTableId).treegrid({
            url:url,
            idField:'id',
            treeField:'name',
            loadFilter:function(data){
                //alert(JSON.stringify(data));
                //alert(data.code);
                try {
                    data = fw.dealReturnObject(data);
                    //fw.alertReturnValue(data);
                    return data;
                }
                catch(e) {
                }
            },
            columns:[[
                {title:'id',field:'id',hidden:true},
                { field: 'sid', title: '序号', hidden:true, width: 30 },
                { field: 'name', title: '名称', width: 300 },
                { field: 'description', title: '描述', width: 130 }
            ]],
            toolbar:[{
                id:'btnColumnAdd' + token,
                text:'添加',
                iconCls: 'icon-add'
            },{
                id:'btnColumnEdit' + token,
                text:'修改',
                iconCls: 'icon-edit'
            },{
                id:'btnColumnDelete' + token,
                text:'删除',
                iconCls: 'icon-remove'
            }],
            onLoadSuccess:function(data) {
                onClickColumnAdd(departmentId);
                onClickColumnEdit(departmentId);
                onClickColumnDelete(departmentId);
            }
        });

    }


    //初始化栏目信息列表
    function initTableColumnTable(departmentId) {
        var table = '#columnTable' + token;
        var url = WEB_ROOT + "/cms/Column_list.action?departmentId="+departmentId;
        var parameters = {};
        $(table).datagrid({
            url: url,
            queryParams:parameters,
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[10],
            pageSize:10,
            rownumbers:true,
            loadFilter:function(data){
                //alert(JSON.stringify(data));
                //alert(data.code);
                try {
                    return fw.dealReturnObject(data);
                }
                catch(e) { }
            },
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            columns: [[
                { field: 'id', title: '编号',hidden:true, width: 20 },
                { field: 'sid', title: '序号',hidden:true, width: 20 },
                { field: 'name', title: '名字', width: 30 },
                { field: 'description', title: '描述', width: 30 }
            ]],
            toolbar:[{
                id:'btnColumnAdd' + token,
                text:'添加',
                iconCls: 'icon-add'
            },{
                id:'btnColumnEdit' + token,
                text:'修改',
                iconCls: 'icon-edit'
            },{
                id:'btnColumnDelete' + token,
                text:'删除',
                iconCls: 'icon-remove'
            }],
            onLoadSuccess:function(data) {
                onClickColumnAdd(departmentId);
                onClickColumnEdit();
                onClickColumnDelete(departmentId);
            }
        });
    }

    //添加栏目button事件
    function onClickColumnAdd(departmentId){
        fw.bindOnClick('btnColumnAdd'+token, function(process) {
            windowOpenDetail([],departmentId);
        });
    }

    //修改栏目button事件
    function onClickColumnEdit(departmentId){
        fw.bindOnClick('btnColumnEdit'+token, function(process) {
            fw.datagridGetSelected("columnTable"+token, function(selected){
                process.beforeClick();
                var sid = selected.sid;
                var url = WEB_ROOT + "/cms/Column_load.action?column.sid="+sid;
                fw.post(url, null, function(data){
                    //fw.alertReturnValue(data);
                    windowOpenDetail(data,departmentId);
                    process.afterClick();
                }, function() {
                    process.afterClick();
                });
            })
        });
    }

    function onClickColumnDelete(departmentId) {
        fw.bindOnClick('btnColumnDelete'+token, function(process) {
            fw.datagridGetSelected("columnTable"+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/cms/Column_delete.action?column.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        $( '#columnTable'+token).treegrid('load');
                       // initTalbeSummaryTreeTable(departmentId);
                    }, null);
                }, null);
            });

        });
    }


    function windowOpenDetail(formData,departmentId){
        if($("#ColumnWindow"+token).length == 0){
            $("#windowsArea").append("<div id='ColumnWindow"+token+"'></div> ");
        }
        $("#ColumnWindow"+token).window({
            title:'栏目信息编辑',
            width:400,
            height:330,
            modal:true,
            cache:false,
            collapsible:false,
            minimizable:false,
            maximizable:false,
            resizable:false,
            href:WEB_ROOT+"/modules/cms/Column_save.jsp?token="+token,
            closed:false,
            onLoad:function() {
                var tree = $("#parentId"+token).combotree("tree");
                //formInitDetail("formDepartment"+token,formData);
                var formId = "formColumn"+token;
                $('#'+formId).form('clear');
                $('#'+formId).form('load',formData);
                //加载权限
                var columnId=formData["column.parentId"];
               if(columnId!=null){
                   formData["column.parentId"]="";
               }
                treeLoadPositionTree(departmentId);
                treeLoadColumnParentIdTree(departmentId);
               // initDepartmentTree(tree);//formDepartment
                //提交按钮绑定
                $("#btnSubmitColumn"+token).bind("click",function(){
                   // var permissionIds = $("#permissionId"+token).combotree('getValues');
                    var ids = fw.combotreeGetCheckedIds('permissionId' + token, ',', "");
                   // alert(ids);
                    $("#"+formId).form("submit",{
                        url:WEB_ROOT+'/cms/Column_insertOrUpdate.action?positionIds='+ids
                                     +'&columnBelong.departmentId='+departmentId,
                        onSubmit: function(){
                            $('#btnSubmitColumn'+token).linkbutton({text:'正在提交...',disabled:true});
                            if (!$(this).form('validate')) {
                                $('#btnSubmitColumn'+token).linkbutton({text:'确定',disabled:false});
                                return false;
                            }
                        },
                        success:function(data){
                            // 刷新列表
                            $("#ColumnWindow"+token).window("close");
                            $('#btnSubmitColumn'+token).linkbutton({text:'确定',disabled:false});
                            //initTableColumnTable(departmentId);
                            $( '#columnTable'+token).treegrid('load');

                        }
                    });
                });
            },
            onClose:function() {

            }
        });
    }

    function treeLoadPositionTree(departmentId) {
        var loadPositionUrl = WEB_ROOT + "/system/Position_list.action?position.DepartmentId="+departmentId;
        fw.combotreeLoadWithCheck("permissionId"+token,loadPositionUrl,null,null,null);
    }

    function treeLoadColumnParentIdTree(departmentId){
        var tree = $('#parentId'+token).combotree('tree');
        var url = WEB_ROOT+"/cms/Column_getTreelist.action?departmentId="+departmentId;
        //fw.combotreeLoadWithCheck("parentId"+token,url,null, null, null);
        fw.treeLoad(tree,url,null, null, null);

    }

    return{
        initModule:function(){
            return initAll();
        },
        buildColumnTree:function(treeId) {
            return initColumnTree(treeId);
        }
    };
};

