var MenuClass = function(token) {

    function initWindowMenu(formData) {
        //fw.alertReturnValue(formData);
        var token1 = token + "_1";
        var winMenuId = 'winMenu'+token1;
        var urlMenu = WEB_ROOT+"/modules/system/menu_save.jsp?token="+token1
        fw.window(winMenuId, '菜单管理', 430, 350, urlMenu, function() {
            var tree = $('#parentId'+token1).combotree('tree');
            initMenuTree(tree);
            formInitDetail('formMenu'+token1, formData[0]);
            onClickMenuSubmit(token1);
        }, null);
    }

    function onClickMenuSubmit(token1) {
        var buttonId = 'submit'+token1;

        fw.bindOnClick(buttonId, function(process){
            var formId = "formMenu" + token1;
            var urlForm = WEB_ROOT+'/system/Menu_save.action';

            fw.formSubmit(formId, urlForm, buttonId, null, function() {
                var tree = '#systemMenu' + token;
                initMenuTree(tree);
                $("#winMenu"+token1).window("close");
            });
        });
    }

    /**
     * 初始化详细内容表单
     * @param formId
     * @param formData
     */
    function formInitDetail(formId, formData) {
       $('#'+formId).form('clear');
        $('#'+formId).form('load',formData);
    }

    function onClickMenuAdd() {
        var buttonId = 'btnMenuAdd' + token;
        fw.bindOnClick(buttonId, function(process){
            var data = {};
            var menu = $('#systemMenu'+token).tree('getSelected');
            if(menu != null){
                //alert(node.id);
                data = {"menu.parentId":menu.id};
            }
            initWindowMenu([data]);
        });
    }

	function initAll() {
        var treeId = "systemMenu"+token;
        initMenuTree(treeId);

        onClickMenuAdd();


        fw.bindOnClick('btnMenuEdit'+token, function(process) {
            fw.treeGetSelected('systemMenu'+token, function(selectd){
                process.beforeClick();
                fw.post(WEB_ROOT+"/system/Menu_load.action?menu.id="+selectd.id, null, function(data) {
                    try {
                        //process.beforeClick();
                        initWindowMenu([data]);
                    }
                    catch (e) {

                        process.afterClick()
                    }

                }, function() {
                    process.errorClick();
                })
            }, '请选择需要修改的数据');
        });


        //删除按钮
        fw.bindOnClick('btnMenuDelete'+token, function(process) {
            var node = $('#systemMenu'+token).tree('getSelected');
            if (node != null) {
                fw.confirm('删除确认','是否确认删除菜单？', function() {
                    process.beforeClick();
                    fw.post(WEB_ROOT+"/system/Menu_delete.action?menu.id="+node.id,null, function(data) {
                        try {
                            var tree = '#systemMenu'+token;
                            initMenuTree(tree);
                        }
                        catch (e) {
                            process.afterClick();
                        }
                    }, function() {
                        process.errorClick();
                    })
                },null);
            }
            else {
                fw.alert('提示','请选择需要删除的菜单项');
            }
        });
    }

    function initMenuTree(treeId) {
        //alert(treeId);
        var url = WEB_ROOT+"/system/Menu_list.action";
        fw.treeLoad(treeId,url,null, function() {
            // 关闭展开的叶子
            treeId = fw.getObjectFromId(treeId);
            //alert(treeId);
            var root = $(treeId).tree('getRoot');
            $(treeId).tree('collapseAll', root.target);
            $(treeId).tree('expand', root.target);
        }, null);


    }

	return {
		initModule:function() {
            // 初始化token
			return initAll();
		},
        build4TreeMenu:function(treeId) {
            //alert('MenuClass.js build4Tree');
            return initMenuTree(treeId);
        }
	};
};

