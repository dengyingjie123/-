
var idChange = null ;    //修改添加和删除操作用的
var idChange2 = null;                //修改添加和删除操作用的

var UserClass=function(token,saleurl) {

    function initAll() {

        initFormSearch(token);
    }

    function initFormSearch(token) {
        //查询事件
        $('#btnSearchUser'+token).bind('click', function(){
            //alert("search");
            var strTableId = "userTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;

            params["userVO.name"] = $("#search_user_name"+token).val();
            params["userVO.mobile"] = $("#search_user_mobile"+token).val();
            params["userVO.idnumber"] = $("#search_user_idnumber"+token).val();
//                fw.alertReturnValue(params);
            $( '#' + strTableId).datagrid('load');
        });

        $('#btnResetUser'+token).bind('click', function(){
            //alert("search");
            $("#search_user_name"+token).val('');
            $("#search_user_mobile"+token).val('');
            $("#search_user_idnumber"+token).val('');
        });
    }
    function formInitDetail(formId, formData) {
         $('#'+formId).form('load',formData);
    }
  function getDatagridToolbar(strTableId,token) {

        return [{
            id:'btnadd',
            text:'添加',
            iconCls:'icon-add',
            handler:function(){
                idChange = 'submitUser' ;
                idChange2 = 'userWindow';
                var data = [];
                windowOpen(data);

            }
        },{
            id:'btnModifyUser'+token,
            text:'修改',
            iconCls:'icon-edit',
            handler:function(){

                //alert('xiugai');
                idChange = 'updateUserPwd' ;
                idChange2 = 'userUpdateWindow';
                var selected = $('#' + strTableId).datagrid('getSelected');

                if (selected != null) {
                    $('#btnModifyUser'+token).linkbutton({text:'正在加载',disabled:true});

                    $.ajax({
                        type: "POST",
                        url: WEB_ROOT+"/system/User_load.action?user.id="+selected.id,
                        processData:true,
                        data:'',
                        dataType:'json',
                        success: function(data){
                            try {
                                if (data.code==100) {
                                    //alert(data);
                                    var userData = fw.convert2Json(data.returnValue);
                                    windowOpenUpdateDetail([userData]);
                                }
                                else if (data.code==200) {
                                    fw.alert('错误',data.message);
                                }
                            }
                            catch(e) {
                                fw.alert("错误","加载用户失败"+e);
                            }
                            $('#btnModifyUser'+token).linkbutton({text:'修改',disabled:false});
                        },
                        error:function(XMLHttpRequest, textStatus, errorThrown){
                            $('#btnModifyUser'+token).linkbutton({text:'修改',disabled:false});
                            fw.alert('错误',textStatus);
                        }
                    });
                }
                else {
                    fw.alert('警告','请选择需要修改的记录');
                }
                var data = {};
            }
        },'-',{
            id:'btnDeleteUser'+token,
            text:'删除',
            iconCls:'icon-cut',
            handler:function(){

                fw.datagridGetSelected(strTableId, function(selected){
                    fw.confirm('确认删除', '是否确认删除？', function(){
                        var url =  WEB_ROOT+"/system/User_delete.action?user.id="+selected.id;
                        fw.post(url, null, function(data){
                            doReload();
                            $('#btnDeleteUser'+token).linkbutton({text:'删除',disabled:false});
                        }, null);
                    }, function(){
                        $('#btnDeleteUser'+token).linkbutton({text:'删除',disabled:false});
                    });
                });
            }
        }]

    }


  /*  $("#icon-add").onclick = function(){
        alert('132');
    }
    $("#btnadd").onclick = function(){
        alert('132');
    }*/


    /**
     *
     * @param 姚章鹏
     * @param 更改窗口列表自动适应
     * @param token
     */
    function buildTable(type,singleSelect,token) {
        //alert("UserClass.buildTable():"+token);
        if (fw.checkIsNullObject(singleSelect)) {
            singleSelect = true;
        }

        var strTableId = "userTable"+token;
        var url =WEB_ROOT ;
        if(saleurl ==  null || saleurl == "" ||  typeof(saleurl)==="undefined") {
             url=url+"/system/User_list.action";
        }else{
            url=url+ saleurl;
        }
        var toolbar = null;

        if (type == config.buildTableTypeWithManagement) {
            toolbar = getDatagridToolbar(strTableId, token);
            $('#userSelectionArea'+token).hide();
        }

        var datagridPageListType = config.datagridPageListType4Default;
        if (type == config.buildTableTypeSelection) {
            datagridPageListType = config.datagridPageListType4Window;
        }

        //alert(config.getDatagridPageList(datagridPageListType));

        //alert(datagridPageListType);
        $('#'+strTableId).datagrid({
            title: '用户信息',
            url: url,
            queryParams: {
                "user.name":"", "user.mobile":""
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:false,
            singleSelect:singleSelect,
            pageList:[10],
            pageSize: 10,
            rownumbers:true,
            loadFilter:function(data){
                //alert(JSON.stringify(data));
                //alert(data.code);
                try {
                    return fw.dealReturnObject(data);
                }
                catch(e) {
                }
            },
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条
                {field:'ck',checkbox:true},
                { field: 'name', title: '名字'},
                { field: 'gender', title: '性别',hidden:true }
            ]],
            columns: [[
                { field: 'id', title: '编号',hidden:true },

                { field: 'genderName', title: '性别' },
                { field: 'mobile', title: '电话' },
                { field: 'address', title: '地址' },
                { field: 'email', title: '邮箱'},
                { field: 'birthday', title: '生日' },
                { field: 'jointime', title: '入职日期' },
                { field: 'idnumber', title: '身份号码' }
            ]],
            toolbar:toolbar
        });
    }
    function windowOpen(data){

        if ($("#userWindow"+token).length==0) {
            $("#windowsArea").append("<div id='userWindow"+token+"'></div> ");
        }
        $('#userWindow'+token).window({
            title:'用户信息管理',
            width:585,
            height:350,
            modal:true,
            cache:false,
            collapsible:false,
            minimizable:false,
            maximizable:false,
            closable:false,
            resizable:false,
            href:WEB_ROOT+"/modules/system/user_update.jsp?token="+ token +"&idChange="+ idChange +"&idChange2=" +idChange2,    // "/modules/system/user_save.jsp?token=?idChange"
            closed:false,
            onLoad:function() {
                // 绑定表单提交事件
                fw.getComboTreeFromKV('gender'+token,'Sex','V',null);
                var formId = "formUser"+token;
                var buttonId = "submitUser"+token;
                $("#"+buttonId).bind('click',function() {
                    var formTabsId = "formTable"+token;
                    var formTab = $("#"+formTabsId).tabs("getSelected");
                    //取得选项卡的位置
                    var index = $("#"+formTabsId).tabs('getTabIndex',formTab);
                    if(index == 0){
                        //提交基本信息
                        formSubmitDetail(buttonId,formId,"userWindow"+token);
                    }else if(index == 1){
                        //保存密码
                        var pwdFormId = "id"+token;
                        if($("#"+pwdFormId).val() == ""){
                            fw.alert("提示信息","请先保存用户基本信息！");
                        }
                    }
                });
            },
            onClose:function() {

            }
        });
    }
    function formSubmitDetail(buttonId,formId,windowId){
        $("#"+formId).form('submit',{
            url:WEB_ROOT+'/system/User_insertOrUpdate.action',
            onSubmit: function(){
                $('#'+buttonId).linkbutton({text:'正在提交...',disabled:true});
                if (!$(this).form('validate')) {
                    $('#'+buttonId).linkbutton({text:'确定',disabled:false});
                    return false;
                }else {
                    var password = $('#password'+token).val();
                    if (!fw.checkIsTextEmpty(password)) {
                        var passwordMD5 = fw.getMD5(password);
                        $('#password'+token).val(passwordMD5);
                        $('#password2'+token).val(passwordMD5);
                    }

                }
            },
            success:function(data){
                $("#"+windowId).window("close");
                doReload();
                $('#'+buttonId).linkbutton({text:'确定',disabled:false});
            }
        });
    }
    function windowOpenUpdateDetail(formData){
        console.info("打开窗口");
        if($("#userUpdateWindow"+token).length == 0){
            $("#windowsArea").append("<div id='userUpdateWindow"+token+"'></div> ");
        }
        $('#userUpdateWindow'+token).window({
            title:'用户岗位管理',
            width:585,
            height:350,
            modal:true,
            cache:false,
            collapsible:false,
            closable:false,
            minimizable:false,
            maximizable:false,
            resizable:false,
            href:WEB_ROOT+"/modules/system/user_update.jsp?token="+ token +"&idChange="+ idChange +"&idChange2=" +idChange2,           //  更新  调用update
            closed:false,
            closable:true,
            onLoad:function() {
                fw.getComboTreeFromKV('gender'+token, 'Sex', 'V', fw.getMemberValue(formData[0], 'user.gender'));
                console.info("加载窗口");
                //为表单赋值
                var formInfo = "userUpdate"+token;
                var formPwd = "userUpdatePwd"+token;
                var treeId = "#department"+token;
                var url = WEB_ROOT+"/system/Department_list.action";
                fw.treeLoad(treeId,url,null, null,null);
                formInitDetail(formInfo,formData[0]);
                formInitDetail(formPwd,formData[0]);
                $("#password"+token).val('');
                //绑定表单提交
                var buttonId = "updateUserPwd"+token;
                $("#"+buttonId).bind("click",function(){
                    var formTabsId = "updateTabs"+token;
                    var formTab = $("#"+formTabsId).tabs("getSelected");
                    var index = $("#"+formTabsId).tabs('getTabIndex',formTab);
                    // TODO CodeReview 增加注释，标明index取值所代表的内容
                    if(index == 0){
                        formSubmitDetail(buttonId,formInfo,"userUpdateWindow"+token);
                    }else if(index == 1){
                        //$("#password"+token).val('');
                        formInitDetail(formInfo,formData[0]);
                        formSubmitDetail(buttonId,formPwd,"userUpdateWindow"+token);
                    }
                });
                //绑定部门信息菜单树点击事件
                var departmentTreeId = "department"+token;
                $("#"+departmentTreeId).tree({onClick:function(node){ //TODO 完成树的显示

                    var jobOptionId = "jobOption"+token;
                    console.info(jobOptionId);
                    //清空树、
                    //fw.treeClear(jobOptionId);

                    $.ajax({
                        type: "POST",
                        url: WEB_ROOT+"/Job_list.action?job.departmentId="+node.id,
                        processData:true,
                        data:'',
                        dataType:"json",
                        success: function(data){
                            try {
                                if (data.code == 100) {
                                    var row =  fw.convert2Json(data.returnValue).children;
                                    $(jobOptionId).tree({
                                        data:data.returnValue
                                    });
                                }
                                else {
                                    //fw.alert('提示信息','该菜单下还没有菜单项');
                                    fw.treeClear(jobOptionId);
                                    $(jobOptionId).tree("reload");
                                }
                            }
                            catch(e) {
                                fw.alert('错误','加载数据失败 ' + e);
                            }
                        },
                        error:function(XMLHttpRequest, textStatus, errorThrown){
                        }
                    });
                }});
            },
            onClose:function() {

            }
        });
    }
    function formInitDetail(formId, formData) {
        $('#'+formId).form('load',formData);
    }
    function doReload() {
        var strTableId = "userTable"+token;
        $('#'+strTableId).datagrid('reload');
    }

    return {
        initModule:function() {

            try {
                initAll();
                getDatagridToolbar(config.buildTableTypeWithManagement, true, token);
            } catch (e) {
                fw.alert('错误','加载数据失败 ' + e);
            }

        },
        windowOpenUserSelection:function(singleSelect,tokenOut, callback) {
            if ($("#windowUserSelection"+tokenOut).length==0) {
                $("#windowsArea").append("<div id='windowUserSelection"+tokenOut+"'></div> ");
            }

            $('#windowUserSelection'+tokenOut).window({
                title:'用户选择窗口',
                width:750,
                height:550,
                modal:true,
                cache:false,
                collapsible:false,
                minimizable:false,
                maximizable:false,
                closable:false,
                resizable:false,
                href:WEB_ROOT+"/modules/system/user(Discard).jsp?token="+tokenOut,
                closed:false,
                onLoad:function() {
                    //formInitDetail('formUser'+token, formData[0]);
                    // 绑定关闭窗口事件
                    $('#btnCancelUserSelection'+tokenOut).bind('click', function(){
                        fw.windowClose('windowUserSelection'+tokenOut);
                    });

                    // 绑定选择事件
                    $('#btnSelectUserSelection'+tokenOut).bind('click', function(){
                        var selectedRows = $('#userTable' + tokenOut).datagrid('getSelections');
                        callback(selectedRows);
                        fw.windowClose('windowUserSelection'+tokenOut);
                    });

                    // 初始化查询表单
                    initFormSearch(tokenOut);

                    buildTable(config.buildTableTypeSelection,singleSelect, tokenOut);
                },
                onClose:function() {

                }
            });
        }
    }
};