/**
 * Created with IntelliJ IDEA.
 * User: Jayden
 * Date: 14-4-29
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
var DepartmentClass = function(token) {

    function initAll() {
        // 添加按钮事件
        $('#btnOrgAdd'+token).bind('click', function(){
            var data = {};
            var node = $('#systemOrg'+token).tree('getSelected');
            if (node != null) {
                data = {"org.parentId":node.id};
            }
            //alert("ddd: "+data);
            windowOpenDetail([data]);
        });

        // 修改按钮
        $('#btnOrgEdit'+token).bind('click', function(){
            //alert("edit");
            //var tree = $('#systemOrg'+token).combotree('tree');	// get the tree object
            var node = $('#systemOrg'+token).tree('getSelected');
            if (node != null) {
                //alert(node.id);
                $.ajax({
                    type: "POST",
                    url: WEB_ROOT+"/system/org_load.action?org.id="+node.id,
                    processData:true,
                    data:'',
                    dataType:"json",
                    success: function(data){
                        //alert("value: " + JSON.stringify(data.returnValue));
                        try {
                            if (data.code == 100) {
                                // 操作成功
                                windowOpenDetail([data.returnValue]);
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


            }
            else {
                fw.alert('提示','请选择组织项');
            }

        });
        // 删除按钮
        $('#btnOrgDelete'+token).bind('click', function() {
            //var tree = $('#systemOrg'+token).combotree('tree');	// get the tree object

            var node = $('#systemOrg'+token).tree('getSelected');
            if (node != null) {
                $.messager.confirm('删除确认', '是否确认删除组织？', function(r){
                    if (r){
                        //alert(node.id);
                        $('#btnOrgDelete'+token).linkbutton({text:'正在删除',disabled:true});
                        fw.treeClear('#systemOrgPosition'+token);
                        $.ajax({
                            type: "POST",
                            url: WEB_ROOT+"/system/org_delete.action?org.id="+node.id,
                            processData:true,
                            data:'',
                            success: function(data){
                                // 清空option树
                                // loadData


                                var tree = '#systemOrg'+token;
                                initOrgTree(tree);
                                $('#btnOrgDelete'+token).linkbutton({text:'删除',disabled:false});
                            },
                            error:function(XMLHttpRequest, textStatus, errorThrown){
                                $('#btnOrgDelete'+token).linkbutton({text:'删除',disabled:false});
                            }
                        });
                    }
                });
            }
            else {
                fw.alert('提示','请选择组织项');
            }

        });


        // 添加组织岗位项
        $('#btnAddOrgPosition'+token).bind('click', function(){
            var data = {};
            var node = $('#systemOrg'+token).tree('getSelected');
            if (node != null) {
                data = {"orgPosition.orgId":node.id};
                //alert("ddd: "+data);
                windowsOpenOrgPositionDetail([data]);
            }
            else {
                fw.alert('警告','请先选择左边组织项');
            }

        });

        // 修改组织岗位项
        $('#btnEditOrgPosition'+token).bind('click', function(){
            //var tree = $('#systemOrg'+token).combotree('tree');	// get the tree object
            var node = $('#systemOrgPosition'+token).tree('getSelected');
            if (node != null) {

                $.ajax({
                    type: "POST",
                    url: WEB_ROOT+"/system/orgPosition_load.action?orgPosition.id="+node.id,
                    processData:true,
                    data:'',
                    dataType:"json",
                    success: function(data){
                        //alert(JSON.stringify(data.returnValue));
                        try {
                            if (data.code == 100) {
                                // 操作成功
                                windowsOpenOrgPositionDetail([data.returnValue]);
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
            }
            else {
                fw.alert('提示','请选择岗位细项');
            }

        });

        // 删除组织岗位项
        $('#btnDeleteOrgPosition'+token).bind('click', function(){
            //var tree = $('#systemOrg'+token).combotree('tree');	// get the tree object

            var node = $('#systemOrgPosition'+token).tree('getSelected');
            if (node != null) {

                $.messager.confirm('删除确认', '是否确认删除？', function(r){
                    if (r){
                        $('#btnDeleteOrgPosition'+token).linkbutton({text:'正在删除',disabled:true});
                        $.ajax({
                            type: "POST",
                            url: WEB_ROOT+"/system/orgPosition_delete.action?orgPosition.id="+node.id,
                            processData:true,
                            data:'',
                            success: function(data){
                                var tree = "#systemOrgPosition"+token;
                                var node = $('#systemOrg'+token).tree('getSelected');
                                initOrgPositionTree(tree, node.id);

                                $('#btnDeleteOrgPosition'+token).linkbutton({text:'删除',disabled:false});
                            },
                            error:function(XMLHttpRequest, textStatus, errorThrown){
                                $('#btnDeleteOrgPosition'+token).linkbutton({text:'删除',disabled:false});
                            }
                        });
                    }
                });
            }
            else {
                fw.alert('提示','请选择岗位细项');
            }

        });


        // 点击组织项事件，初始化组织岗位项树
        var tree = '#systemOrg'+token;
        //alert("TT: " + tree);
        initOrgTree(tree);
        $(tree).tree({
            onClick: function (node) {
                // 收起非叶子项
                $(tree).tree('toggle', node.target);

                // 检查是否是叶子
                if ($(tree).tree('isLeaf', node.target)) {
                    // 刷新组织岗位项
                    var optionTreeId = "#systemOrgPosition"+token;
                    //alert(optionTreeId);
                    initOrgPositionTree(optionTreeId, node.id);
                }
            }
        });
    }

    function windowsOpenOrgPositionDetail(formData) {
        // 初始化组织详细内容页面
        if ($("#orgPositionWindow"+token).length==0) {
            $("#windowsArea").append("<div id='orgPositionWindow"+token+"'></div> ");
        }

        $('#orgPositionWindow'+token).window({
            title:'组织岗位细项管理',
            width:400,
            height:230,
            modal:true,
            cache:false,
            href:WEB_ROOT+"/contents/system/org_position.jsp?token="+token,
            closed:false,
            onLoad:function() {
                //alert(JSON.stringify(formData));
                //superId
                // 初始化表单
                //alert(formData[0]);
                formInitOrgPosition('formOrgPosition'+token, formData[0]);

                // 绑定事件
                $('#btnSubmitOrgPosition'+token).bind('click',function() {
                    //alert('begin submit');
                    var formId = 'formOrgPosition'+token;
                    //alert(formId);
                    $("#"+formId).form('submit', {
                        url:WEB_ROOT+'/system/orgPosition_save.action',
                        onSubmit: function(){
                            // do some check
                            // return false to prevent submit;
                            $('#btnSubmitOrgPosition'+token).linkbutton({text:'正在提交...',disabled:true});
                            if (!$(this).form('validate')) {
                                $('#btnSubmitOrgPosition'+token).linkbutton({text:'确定',disabled:false});
                                return false;
                            }
                        },
                        success:function(data){
                            //alert(JSON.stringify(data));

                            var tree = "#systemOrgPosition"+token;
                            var node = $('#systemOrg'+token).tree('getSelected');
                            initOrgPositionTree(tree, node.id);

                            $("#orgPositionWindow"+token).window("close");
                            $('#btnSubmitOrgPosition'+token).linkbutton({text:'确定',disabled:false});
                        }
                    });
                });
            },
            onClose:function() {

            }
        });
    }

    /**
     * 打开详细窗口
     * @param formData JSON数组
     */
    function windowOpenDetail(formData) {
        //alert(JSON.stringify(formData));
        // 初始化组织详细内容页面
        if ($("#orgWindow"+token).length==0) {
            $("#windowsArea").append("<div id='orgWindow"+token+"'></div> ");
        }
        //$('#orgWindow'+token).load(WEB_ROOT+"/contents/system/org_new.jsp?token="+token,{},function() {});
        $('#orgWindow'+token).window({
            title:'组织管理',
            width:400,
            height:230,
            modal:true,
            cache:false,
            collapsible:false,
            minimizable:false,
            maximizable:false,
            resizable:false,
            href:WEB_ROOT+"/contents/system/org_new.jsp?token="+token,
            closed:false,
            onLoad:function() {
                //superId
                var tree = $('#parentId'+token).combotree('tree');
                initOrgTree(tree);
                // 初始化表单
                //alert(formData[0]);
                formInitDetail('formOrg'+token, formData[0]);

                // 绑定事件
                $('#submit'+token).bind('click',function() {
                    //alert('begin submit');
                    var formId = "formOrg"+token;
                    $("#"+formId).form('submit', {
                        url:WEB_ROOT+'/system/org_save.action',
                        onSubmit: function(){
                            // do some check
                            // return false to prevent submit;
                            $('#submit'+token).linkbutton({text:'正在提交...',disabled:true});
                            if (!$(this).form('validate')) {
                                $('#submit'+token).linkbutton({text:'确定',disabled:false});
                                return false;
                            }
                        },
                        success:function(data){
                            //alert(JSON.stringify(data));
                            // 刷新组织树
                            var tree = '#systemOrg'+token;
                            initOrgTree(tree);

                            $("#orgWindow"+token).window("close");
                            $('#submit'+token).linkbutton({text:'确定',disabled:false});
                        }
                    });
                });
            },
            onClose:function() {

            }
        });
    }

    /**
     * 初始化组织岗位项
     * @param tree
     * @param orgId
     */
    function initOrgPositionTree(tree, orgId) {
        //alert("here " + tree);
        $(tree).tree({
            url: WEB_ROOT+"/system/orgPosition_list.action?orgPosition.orgId="+orgId,
            loadFilter: function(data){
                //alert(data.returnValue);
//                return fw.treeDataConvert(rows);
                try {
                    if (data.code == 100) {
                        return fw.convert2Json(data.returnValue);
                    }
                    else {
                        fw.alert("错误",data.message + " 代码: " + data.code);
                    }
                }
                catch(e) {
                    fw.alert("错误","未知错误，信息："+e);
                }
            }
        });
    }

    function initOrgTree(tree) {
        //alert(tree);
        $(tree).tree({
            url: WEB_ROOT+"/system/org_list.action",
            loadFilter: function(data){
                //alert(data);
//                return fw.treeDataConvert(rows);
                try {
                    if (data.code == 100) {
                        var rows = fw.convert2Json(data.returnValue);
                        return rows;
                    }
                    else {
                        fw.alert("错误",data.message + " 代码: " + data.code);
                    }
                }
                catch(e) {
                    fw.alert("错误","未知错误，信息："+e);
                }
            }
        });
    }

    /**
     * 初始化详细内容表单
     * @param formId
     * @param formData
     */
    function formInitDetail(formId, formData) {
        $('#'+formId).form('clear');
        $('#submit'+token).linkbutton({text:'正在加载...',disabled:true});
        $('#'+formId).form({
            onLoadSuccess:function(data) {
                //$("#message"+token).html(JSON.stringify(data));
                $('#submit'+token).linkbutton({text:'确定',disabled:false});
            },
            onLoadError:function() {
                fw.alert('警告','数据加载失败，请与管理员联系');
            }
        });

        $('#'+formId).form('load',formData);
    }

    function formInitOrgPosition(formId, formData) {
        //alert(formId + ":"+JSON.stringify(formData));
        $('#'+formId).form('clear');
        $('#btnSubmitOrgPosition'+token).linkbutton({text:'正在加载...',disabled:true});
        $('#'+formId).form({
            onLoadSuccess:function(data) {
                //$("#message"+token).html(JSON.stringify(data));
                $('#btnSubmitOrgPosition'+token).linkbutton({text:'确定',disabled:false});
            },
            onLoadError:function() {
                fw.alert('警告','数据加载失败，请与管理员联系');
            }
        });
        $('#'+formId).form('load',formData);
    }

    return {
        initModule:function() {
            // 初始化token
            return initAll();
        },
        build4TreeOrg:function(treeId) {
            //alert('OrgClass.js build4Tree');
            return initOrgTree(treeId);
        },
        build4ComboTreeOrg:function(comboTreeId) {
            var tree = $("#"+comboTreeId).combotree('tree');
            return initOrgTree(tree);
        },
        build4TreeOrgPosition:function(treeId, orgIdValue) {
            return initOrgPositionTree(treeId, orgIdValue);
        }
    };
};

