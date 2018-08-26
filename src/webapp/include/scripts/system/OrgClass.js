var OrgClass = function(token) {

    function onClickPositionAdd() {
        //alert('Position click');
        fw.bindOnClick('btnPositionAdd'+token, function(process) {
            var department = $('#departmentOption'+token).tree('getSelected');
            //alert(JSON.stringify(departmentId));
            if (department != null) {
                process.beforeClick();
                try {
                    var outerToken = token + "_1";
                    var url = WEB_ROOT + "/modules/system/department_position.jsp?token="+outerToken;
                    fw.window('DepartmentPositionWindow'+outerToken, '岗位管理', 500, 400, url, function() {
                        //alert(department.text);
                        onClickDepertmentPositionSubmit(outerToken);

                        fw.formLoad('formDepertmentPosition'+outerToken, {'position.departmentName':department.text,'position.departmentId':department.id});
                    }, function() {});
                }
                catch (e) {
                    process.errorClick();
                }
                process.afterClick();
            }
            else {
                fw.alert('提示','请选择部门');
            }

        });
    }

    function onClickPositionEdit() {
        fw.bindOnClick('btnPositionEdit' + token, function(process) {
            fw.treeGetSelected('positionTree'+token,function(position){
                var positionId = position.id;
                var url = WEB_ROOT + "/system/Position_load.action?position.id="+positionId;
                process.beforeClick();
                fw.post(url, null, function(data) {

                    var outerToken =  token + "_1";
                    var windowUrl = WEB_ROOT + "/modules/system/department_position.jsp?token="+outerToken;
                    fw.window('DepartmentPositionWindow'+outerToken, '岗位管理', 400, 300, windowUrl, function() {
                        //alert(department.text);
                        onClickDepertmentPositionSubmit(outerToken);
                        fw.formLoad('formDepertmentPosition'+outerToken, data);
                    }, function() {});


                    //fw.alertReturnValue(data);
                    fw.formLoad('formDepertmentPosition'+token, data);
                    process.afterClick();
                }, function() {
                    process.errorClick();
                });
            }, '请选择岗位');
        });
    }

    function onClickPositionDelete() {
        var buttonId = "btnPositionDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.treeGetSelected('positionTree'+token,function(position) {
                fw.confirm('确认删除','是否确认删除？', function() {
                    var url = WEB_ROOT + "/system/Position_delete.action?position.id="+position.id;
                    process.beforeClick();
                    fw.post(url, null, function(data) {

                        fw.treeGetSelected('departmentOption'+token, function(department) {
                            treeReloadPositionTree(department.id);
                        } , '请选择部门');

                        process.afterClick();
                    }, function() {
                        process.errorClick();
                    });
                },null);
            }, '请选择岗位');
        });
    }

    function initTablePermissionTable(positionId) {
        var table = '#permissionTable' + token;
    }

    function initTableUserTable(positionId) {
        var table = '#userTable' + token;
        var url = WEB_ROOT + "/system/PositionUser_listUsers.action?positionUser.positionId=" + positionId;
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
                { field: 'name', title: '名字', width: 30 }
            ]],
            toolbar:[{
                id:'btnUserAdd' + token,
                text:'添加',
                iconCls: 'icon-add'
            },{
                id:'btnUserDelete' + token,
                text:'删除',
                iconCls: 'icon-remove'
            }],
            onLoadSuccess:function(data) {
                onClickUserAdd();
                onClickUserDelete();
            }
        });
    }

    function onClickUserDelete() {
        fw.bindOnClick('btnUserDelete'+token, function(process) {

            var rows = $('#userTable'+token).datagrid('getSelections');
            if (rows != null && rows.length > 0) {
                fw.confirm('提示','是否确定删除用户？', function(){
                    fw.treeGetSelected('positionTree'+token,function(position){
                        var parameters = {
                            'positionUser.userId':rows[0].id,
                            'positionUser.positionId':position.id
                        };
                        fw.post(WEB_ROOT+'/system/PositionUser_deleteByIds.action', parameters, function(data) {
                            initTableUserTable(position.id);
                        },null);
                    }, '请选择岗位');
                },null);

            }
            else {
                fw.alert('提示','请选择需要删除的用户');
            }
        });
    }

    function onClickUserAdd() {
        fw.bindOnClick('btnUserAdd' + token, function(process) {
            using(SCRIPTS_ROOT+'/system/UserClass(Discard).js', function() {
                //alert("loaded...");
                process.beforeClick();
                try {
                    var userClass = new UserClass(token);
                    var token1 = token + '1';
                    userClass.windowOpenUserSelection(true, token1, function(row) {
                        fw.treeGetSelected('positionTree'+token, function(position) {
                            var parameters = {
                                'positionUser.positionId':position.id,
                                'positionUser.userId':row[0].id
                            };
                            fw.post(WEB_ROOT+'/system/PositionUser_insertOrUpdate.action', parameters, function(data) {
                                initTableUserTable(position.id);
                            }, null);
                        }, '请选择岗位');
                    });
                }
                catch (e) {
                    fw.dealException(e);
                    process.errorClick();
                }
                process.afterClick();

            });
        });
    }
    function onClickDepartmentDelete(departmentTreeId) {
        fw.bindOnClick('departmentDelete'+token, function(process){
            fw.treeGetSelected(departmentTreeId, function(selected) {
                fw.confirm('确认','是否确认删除数据？', function() {
                    process.beforeClick();
                    fw.post(WEB_ROOT+"/system/Department_delete.action?department.id="+selected.id, null, function(data) {
                        process.afterClick();
                        initDepartmentTree(departmentTreeId);
                    },function(){
                        process.afterClick();
                        fw.alert("提示信息","删除失败");
                        initDepartmentTree(departmentTreeId);
                    })
                }, null);
            }, '请选择需要删除的数据');
        });
    }

    function onClickDepartmentEdit(departmentTreeId) {
        //alert(departmentTreeId);
        var buttonId = "departmentEdit"+token;
        fw.bindOnClick(buttonId, function(process) {
            fw.treeGetSelected(departmentTreeId, function(selected) {
                //fw.alertReturnValue(selected);
                $.ajax({
                    type: "POST",
                    url: WEB_ROOT+"/system/Department_load.action?department.id="+selected.id,
                    processData:true,
                    data:'',
                    dataType:"json",
                    success: function(data){
                        //alert("value: " + JSON.stringify(data.returnValue));
                        try {
                            if (data.code == 100) {
                                // 操作成功
                                //alert(fw.convert2Json(data.returnValue));
//                                fw.alertReturnValue(data);
                                var DepartmentTypeSelecteID = data["returnValue"]["department.typeID"];
                                windowOpenDetail(data.returnValue,DepartmentTypeSelecteID);
                            }
                            else {
                                fw.alert('错误','加载数据失败');
                            }
                        }
                        catch(e) {
                            fw.alert('错误','加载数据失败 ' + e);
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                    }
                });
            }, '请选择要修改的数据');
        })
    }

    function onClickDepartmentOption() {
        $('#departmentOption'+token).tree({onSelect:function(node) {
            treeReloadPositionTree(node.id);
        }});
    }

    function onClickPositionTree() {
        $('#positionTree' + token).tree({
            onSelect:function(node) {
                // 初始化用户表
                initTableUserTable(node.id);

                // 初始化权限树
                initTreePermission(node.id);
            }
        });
    }

    function initTreePermission(positionId) {
        //alert(positionId);
        var tree = "permissionTree"+token;
        var url = WEB_ROOT+"/system/Menu_list.action";
        //alert(url);
        fw.treeLoad(tree,url,null, function(data) {
            $('#'+ tree).tree({
                checkbox:true,
                data:data
            });

            //fw.alertReturnValue(data);

            var urlPositionPermission = WEB_ROOT + '/system/PositionPermission_listPermissions.action';
            var parameter = {'positionPermission.positionId':positionId};

            fw.post(urlPositionPermission, parameter, function(d) {
                //fw.alertReturnValue(d);
                $(d).each(function() {
                    //alert(this.id);
                    var node = $('#'+ tree).tree('find', this.id);
                    // 如果是叶子节点，才选中
                    var isLeaf = $('#' + tree).tree('isLeaf', node.target);


//                    if (!isLeaf) {
//                        var t = JSON.stringify(node);
//                        fw.debug(t);
//                    }


                    if (isLeaf) {
                        $('#'+tree).tree('check', node.target);
                    }

                });
            },null);

        }, null);
    }

    function onClickPermissionSubmitSubmit() {
        var buttonId = 'btnPermission'+token;
        fw.bindOnClick(buttonId, function(process){
            process.beforeClick();
            try {
                var tree = '#permissionTree'+token;
                var nodes = $(tree).tree('getChecked',['checked','indeterminate']);

                var paramPermissions = "";
                $(nodes).each(function() {
                    //alert(this.id);
//                    if ($(tree).tree('isLeaf', this.target)) {
//                        paramPermissions +=  this.id + config.splitLetter;
//                    }

                    paramPermissions +=  this.id + config.splitLetter;
                });
                paramPermissions = fw.removeLastLetters(paramPermissions, config.splitLetter);
                //alert(paramPermissions);

                fw.treeGetSelected('positionTree'+token, function(position){
                    var positionId = position.id;

                    var parameter = {
                        'positionPermission.positionId':positionId,
                        'permissionIds':paramPermissions
                    };

                    //alert(parameter);
                    var urlPositionPermissionSubmit = WEB_ROOT+'/system/PositionPermission_saveAll.action';
                    fw.post(urlPositionPermissionSubmit, parameter, function(data) {
                        process.afterClick();
                    }, function () {
                        process.errorClick();
                    });
                }, '请选择岗位');

            }
            catch (e) {
                process.errorClick();
            }
        });
    }

    function onClickDepertmentPositionSubmit(token) {
        var formId = "formDepertmentPosition" + token;
        var buttonId = "btnSubmitDepertmentPosition" + token;
        fw.bindOnClick(buttonId, function(process) {
            var url = WEB_ROOT + "/system/Position_insertOrUpdate.action";
            fw.formSubmit(formId, url, buttonId, null, function() {

                // 刷新岗位树
//                alert(token);
//                alert($('#departmentId'+token).length);
                var departmentId = $('#departmentId'+token).val();
                fw.windowClose('DepartmentPositionWindow' + token);
                //alert(departmentId);
                treeReloadPositionTree(departmentId);

            });
        });
    }

    function treeReloadPositionTree(departmentId) {
        var loadPositionUrl = WEB_ROOT + "/system/Position_list.action?position.DepartmentId="+departmentId;

        fw.treeLoad('positionTree'+token, loadPositionUrl, null,null,null);
    }

    function windowOpenDetail(formData,departmentTypeSelecteID){
        if($("#DeparmentWindow"+token).length == 0){
            $("#windowsArea").append("<div id='DeparmentWindow"+token+"'></div> ");
        }
        $("#DeparmentWindow"+token).window({
            title:'部门管理',
            width:400,
            height:330,
            modal:true,
            cache:false,
            collapsible:false,
            minimizable:false,
            maximizable:false,
            resizable:false,
            href:WEB_ROOT+"/modules/system/department_save.jsp?token="+token,
            closed:false,
            onLoad:function() {
                var combotreeId = "Search_Type"+token;
                var URL = WEB_ROOT + "/system/Department_departmentTypeTree.action";
                fw.combotreeLoad(combotreeId,URL,departmentTypeSelecteID);

                var tree = $("#parentId"+token).combotree("tree");
                //formInitDetail("formDepartment"+token,formData);
                var formId = "formDepartment"+token;
                initDepartmentTree(tree);//formDepartment
                //formSave(tree,formData);
                formInitDetail(formId,formData);
                //提交按钮绑定
                $("#btnSubmitDepartment"+token).bind("click",function(){

                    $("#"+formId).form("submit",{
                        url:WEB_ROOT+'/system/Department_insertOrUpdate.action',
                        onSubmit: function(){
                            $('#submit'+token).linkbutton({text:'正在提交...',disabled:true});
                            if (!$(this).form('validate')) {
                                $('#submit'+token).linkbutton({text:'确定',disabled:false});
                                return false;
                            }
                        },
                        success:function(data){
                            //alert(JSON.stringify(data));
                            // 刷新菜单树
                            var tree = '#departmentOption'+token;
                            initDepartmentTree(tree);

                            $("#DeparmentWindow"+token).window("close");
                            $('#btnSubmitDepartment'+token).linkbutton({text:'确定',disabled:false});
                        }
                    });
                });
            },
            onClose:function() {

            }
        });
    }

    function initAll(){
        onClickDepartmentOption();
        onClickPositionAdd();
//        onClickPositionEdit();
        onClickPositionEdit();
        onClickPositionDelete();
        onClickPositionTree();
        onClickPermissionSubmitSubmit();
        var departmentTreeId = "departmentOption"+token;
        initDepartmentTree(departmentTreeId);


        //添加按钮绑定
        $("#departmentAdd"+token).bind("click",function(){
            var data = {};
            var treeNode = $("#departmentOption"+token).tree("getSelected");
            if(treeNode != null){
                data = {"department.parentId":treeNode.id}
            }
            windowOpenDetail(data,-2);
        });
        //修改按钮绑定
        onClickDepartmentEdit(departmentTreeId);

        //删除按钮绑定
        onClickDepartmentDelete(departmentTreeId);
    }

    function formSave(treeId, formData){
        var treeValue = formData["department.parentId"];
        //alert(treeValue);
        fw.setFormValue(treeId, treeValue, fw.type_form_combotree, treeValue);
    }

    function formInitDetail(formId, formData) {
        $('#'+formId).form('clear');
        $('#'+formId).form('load',formData);
    }
    function initDepartmentTree(treeId){
        var url = WEB_ROOT+"/system/Department_list.action";
        fw.treeLoad(treeId,url,null, null, null);
    }
    return{
        initModule:function(){
            return initAll();
        },
        buildTreeDepartment:function(treeId, success) {
            var url = WEB_ROOT+"/system/Department_list.action";
            fw.treeLoad(treeId,url,null, success, null);
        }
    };
};

