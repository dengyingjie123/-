var PermissionClass = function(token) {
    var menuOptionsMap = new Map();
    function initAll() {

        menuOptionsMap.removeAll();



        // 构建组织
        using(SCRIPTS_ROOT+'/system/OrgClass.js', function () {
            //alert("loaded...");
            //orgFuns.initModule(token);
            var tree = '#systemOrg'+token;
            var orgClass = new OrgClass(token);
            orgClass.build4TreeOrg(tree);


            var orgPositionId = "#systemOrgPosition"+token;

            $(tree).tree({
                onSelect: function (node) {
                    // 清除岗位项
                    fw.treeClear("#systemOrgPosition"+token);

                    // 清除菜单选中图标
                    var tree = '#systemMenu'+token;
                    var menuClass = new MenuClass(token);
                    menuClass.build4TreeMenu(tree);

                    // 清除菜单控制项
                    fw.treeClear('#systemMenuOption'+token);
                    menuOptionsMap.removeAll();

                    // 收起非叶子项
                    $(tree).tree('toggle', node.target);

                    // 检查是否是叶子
                    if ($(tree).tree('isLeaf', node.target)) {
                        // 刷新菜单控制项

                        //alert(optionTreeId);

                        orgClass.build4TreeOrgPosition(orgPositionId, node.id);
                    }
                }
            });

            $(orgPositionId).tree({
                onSelect:function(node) {
                    $.ajax({
                        type: "POST",
                        url: WEB_ROOT+"/system/orgPositionPermission_load.action?position.id="+node.id,
                        processData:true,
                        data:'',
                        dataType:"json",
                        success: function(data){
                            //alert(JSON.stringify(data));
                            try {
                                if (data.code == 100) {
                                    // 操作成功
                                    //alert(data.returnValue);
                                    var menusAndOptions = fw.convert2Json(data.returnValue);
                                    // 初始化菜单项
                                    var menusData = menusAndOptions.menus;
                                    $(menusData).each(function() {
                                        var menuTreeId = "#systemMenu"+token;
                                        var node = $(menuTreeId).tree('find', this.id);
                                        $(menuTreeId).tree('update', {
                                            target:node.target,
                                            iconCls:config.iconClsAdded
                                        });

                                    });

                                    // 初始化菜单控制项
                                    var menuOptionsData = menusAndOptions.menuOptions;
                                    $(menuOptionsData).each(function() {
                                        menuOptionsMap.put(this.id,this.id);
                                    });

                                    // fw.convert2Json(data.returnValue)
                                    //windowOpenDetail([fw.convert2Json(data.returnValue)]);
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
            });

        });

        // 构建菜单树
        using(SCRIPTS_ROOT+'/system/MenuClass.js', function () {
            //menufuns.initModule(token);
            //alert(token);
            var tree = '#systemMenu'+token;
            var menuClass = new MenuClass(token);
            menuClass.build4TreeMenu(tree);
            $(tree).tree('options').cascadeCheck=true;

            $(tree).tree({
                onSelect: function (node) {

                    var orgPositionId = $('#systemOrgPosition'+token).tree('getSelected');
                    if (fw.checkIsNullObject(orgPositionId)) {
                        fw.alert('警告','请选择岗位');
                        return
                    }

                    // 清空菜单控制项
                    fw.treeClear('#systemMenuOption'+token);

                    // 收起非叶子项
                    $(tree).tree('toggle', node.target);

                    // 检查是否是叶子
                    if ($(tree).tree('isLeaf', node.target)) {
                        // 刷新菜单控制项
                        var optionTreeId = "#systemMenuOption"+token;
                        //alert(optionTreeId);

                        //alert("menu id: "+node.id);
                        menuClass.build4TreeMenuOption(optionTreeId, node.id);
                    }
                }
            });

            var optionTreeId = "#systemMenuOption"+token;
            //alert(optionTreeId);


            $(optionTreeId).tree({
                checkbox:true,
                cascadeCheck:true,
                onCheck:function(node, checked) {
                    var optionsChecked = $(optionTreeId).tree('getChecked');

                    if (optionsChecked.length>0) {
                        var menuSelectedNode = $(tree).tree('getSelected');

                        $(tree).tree('update', {
                            target:menuSelectedNode.target,
                            iconCls:ConfigClass.iconClsAdded
                        });
                    }
                    else {
                        var menuSelectedNode = $(tree).tree('getSelected');
                        $(tree).tree('update', {
                            target:menuSelectedNode.target,
                            iconCls:ConfigClass.iconClsDefault
                        });
                    }

                    if (checked) {
                        menuOptionsMap.put(node.id, node.id);
                    }
                    else {
                        menuOptionsMap.remove(node.id);
                    }
                },
                onLoadSuccess:function(node, data) {
                    //alert(JSON.stringify(data));
//                                alert(menuOptionsMap.size);
                    for(var i = 0; i++ < menuOptionsMap.size; menuOptionsMap.next()) {
                        //alert("find: " + menuOptionsMap.key());
                        var optionChecked = $(optionTreeId).tree('find', menuOptionsMap.key());
                        if (!fw.checkIsNullObject(optionChecked)) {
                            $(optionTreeId).tree('check', optionChecked.target);
                        }

                    }
                }
            });
        });


        $('#btnSubmitPermission'+token).bind('click', function(){
            //alert("search");
            var orgPositionNode = $('#systemOrgPosition'+token).tree('getSelected');
            if (!fw.checkIsNullObject(orgPositionNode)) {
                //alert(menuOptionsMap.size);
                var params = "";
                for(var i = 0; i < menuOptionsMap.size; menuOptionsMap.next(),i++) {
                    params+="&menuOptions["+i+"].id="+menuOptionsMap.key();
                }
//                alert(params);
                $.ajax({
                    type: "POST",
                    url: WEB_ROOT+"/system/orgPositionPermission_save.action?position.id="+orgPositionNode.id,
                    processData:true,
                    data:params,
                    dataType:"json",
                    success: function(data){
                        //alert(JSON.stringify(data));
                        try {
                            if (data.code == 100) {
                                // 操作成功
                                //windowOpenDetail([fw.convert2Json(data.returnValue)]);
                                fw.alert('消息','保存成功');
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
                fw.alert('警告','请选择岗位');
            }
        });

    }

    return {
        initModule:function() {
            return initAll();
        }
    }
};