/**
 * Created with IntelliJ IDEA.
 * User: Jayden
 * Date: 14-4-22
 * Time: 下午1:47
 * To change this template use File | Settings | File Templates.
 */
var KVClass  = function(token){

    var initGroupName;
  function initAll(){
      initFormSearch(token);
  }
    function doReload() {
        var strTableId = "KVGuanLiTable"+token;
        $('#'+strTableId).datagrid('reload');
    }
  function formSubmitDetail(){
      var formId = 'formKV'+token;
      $('#'+formId).form('submit',{
          url:WEB_ROOT+"/system/KV_insertOrUpdate.action" ,
          onSubmit:function(param){
              $('#btnSubmitKV'+token).linkbutton({text:'正在提交...',disabled:true});
              var  v = $('#'+formId).serialize();
              //alert(v);
              if(!$(this).form('validate')){
                  $('#btnSubmitKV'+token).linkbutton({text:'确定.',disabled:true});
                  return false;
              }
          } ,
          success:function(data){

              data = eval('(' + data + ")");

              if(data['code'] != '100') {
                  fw.alert('错误', data['message']);
              }

              $("#KVWindow"+token).window("close");
              doReload();
              $('#btnSubmitKV'+token).linkbutton({text:'确定',disabled:false});
              fw.showMessageDone();
          }
      });
  }
  function windowOpenDetail(formData){
      fw.window('KVWindow'+token,'KV 键管理',550,300,WEB_ROOT+"/modules/system/KV_save.jsp?token="+token,function(){

          var v = formData['kv.V'];
          if (fw.checkIsObject(v)) {
              formData = fw.jsonJoin(formData, {"kv.V": fw.convert2String(v)}, true);    
          }
          
          

          $('#formKV'+token).form('load',formData);
          //绑定确定按钮点击事件
          $('#btnSubmitKV'+token).bind('click',function(){
              formSubmitDetail();
          });

          // 隐藏组名
          if (!fw.checkIsTextEmpty(initGroupName)) {
              // $('input').attr("readonly","readonly")
             // $("#GroupNameForm"+token).val("CreditRate");
              $('#GroupNameForm'+token).attr("readonly",true);
          }
      },null);
  }
  function initFormSearch(token){
      //点击查询按钮执行查询事件
      fw.bindOnClick('btnSearchKV'+token, function() {
          //alert(3);
          var strTableId = 'KVGuanLiTable'+token;
          var params =$('#'+strTableId).datagrid('options').queryParams;
          params['kv.K'] = $('#search_K'+token).val();
          params['kv.V'] = $('#search_V'+token).val();
          params['kv.parameter'] = $('#search_parameter'+token).val();
          params['kv.GroupName'] = $('#search_GroupName'+token).val();
          $('#'+strTableId).datagrid('load');
      });

      //点击重置按钮执行清空事件
      fw.bindOnClick('btnResetKV'+token, function() {
          //alert(3);
          //alert('token:'+token);
          $('#search_K'+token).val("");
          $('#search_V'+token).val("");
          $('#search_paramter'+token).val("");
          if (fw.checkIsTextEmpty(initGroupName)) {
              $('#search_GroupName'+token).val("");
          }
          else {
              $('#search_GroupName'+token).val(initGroupName);
          }
          //alert(4);
      });
  }
    //构建toolbar
    function getDatagridToolbar(strTableId,token) {

        return [{
            id:'btnadd',
            text:'添加',
            iconCls:'icon-add',
            handler:function(){

                var url = WEB_ROOT + "/system/Config_getSystemSequence.action";
                fw.post(url, null, function(data){
                    var sequence = data.sequence;
                    var data = {"kv.K":sequence, "kv.GroupName":initGroupName};
                    windowOpenDetail(data);
                }, null);
            }
        },{
            id:'btnModifyKV'+token,
            text:'修改',
            iconCls:'icon-edit',
            handler:function(){
                var selected = $('#'+strTableId).datagrid('getSelected');
                //alert(selected.ID);
                if(selected != null){
                    $('#btnModifyKV' + token).linkbutton({text: '正在加载', disabled: true});

                    fw.post(WEB_ROOT+"/system/KV_load.action?kv.ID="+selected.ID, null, function(data) {
                        try {
                            windowOpenDetail(data);
                        }
                        catch (e) {

                        }
                        $('#btnModifyKV'+token).linkbutton({text:'修改',disabled:false});
                    }, function() {
                        $('#btnModifyKV'+token).linkbutton({text:'修改',disabled:false});
                        fw.alert('错误',textStatus);
                    });

                } else {
                    fw.alert('警告','请选择需要修改的记录');
                }

            }
        },'-',{
            id:'btnDeleteKV'+token,
            text:'删除',
            iconCls:'icon-cut',
            handler:function(){
                var selected = $('#'+strTableId).datagrid('getSelected');
                if(selected != null){
                    fw.confirm('删除确认', '是否确认删除？', function() {
                        $('#btnDeleteKV'+token).linkbutton({text:'正在删除',disabled:true});
                        fw.post(WEB_ROOT+"/system/KV_delete.action?kv.ID="+selected.ID, null, function(data) {
                            doReload();
                            $('#btnDeleteKV'+token).linkbutton({text:'删除',disabled:false});
                        }, function() {
                            $('#btnDeleteKV'+token).linkbutton({text:'删除',disabled:false});
                        })
                    }, function() {
                        $('#btnDeleteKV'+token).linkbutton({text:'删除',disabled:false});
                    });
                }
                else {
                    fw.alert('信息','请选择需要删除的数据');
                }
            }
        },{
            id:'btnReload'+token,
            text:'刷新系统配置',
            iconCls:'icon-edit',
            handler:function(){
                var url = WEB_ROOT + "/system/KV_reloadSystemConfig";
                fw.post(url, null, function(data){
                    if (data == "1") {
                        fw.alert("提示", "刷新成功");
                    }
                },null);
            }
        }]

    }
    //构建表显示数据
    function buildTable(type,singleSelect,initGroupName,token){
        if(fw.checkIsNullObject(singleSelect))   {
            singleSelect = true;
        }
        var strTableId = 'KVGuanLiTable'+token;
        var url=WEB_ROOT+"/system/KV_list.action";
        var toolbar = null;
        if(type == config.buildTableTypeWithManagement){
            toolbar = getDatagridToolbar(strTableId,token);
        }
        var datagridPageListType = config.datagridPageListType4Default;
        if (type == config.buildTableTypeSelection) {
            datagridPageListType = config.datagridPageListType4Window;
        }
        $('#'+strTableId).datagrid({
            title: 'KV管理',
            url:url,
            queryParams: {   'kv.GroupName':initGroupName       },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:singleSelect,
            pageList:config.datagridPageList,
            pageSize:15,
            rownumbers:true,
            loadFilter:function(data){
                //alert(JSON.stringify(data));
                //alert(data.code);
                try {
                    return fw.dealReturnObject(data);;
                }
                catch(e) {
                }
            },
            pagination:true,
            frozenColumns:[[  // 固定列，没有滚动条
                {field:'ck',checkbox:true}
            ]],
            columns: [[
                { field: 'sid', title: '编号', hidden:true },
                { field: 'ID', title: '编号', hidden:true },
                { field: 'K', title: 'K键',hidden:false },
                { field: 'V', title: 'V键', formatter: function(value,row,index){
                    //alert(JSON.stringify(value));

                    if (!fw.checkIsTextEmpty(value) && value.indexOf("<") != - 1) {
                        return "包含特殊字符，请在【修改】里查看";
                    }

                    if (fw.checkIsObject(value)) {
                        // alert(JSON.stringify(value));
                        return fw.convert2String(value);
                    }
                    return value;
                }},
                { field: 'parameter', title: 'parameter', formatter: function(value,row,index){
                    //alert(JSON.stringify(value));

                    if (!fw.checkIsTextEmpty(value) && value.indexOf("<") != - 1) {
                        return "包含特殊字符，请在【修改】里查看";
                    }

                    if (fw.checkIsObject(value)) {
                        // alert(JSON.stringify(value));
                        return fw.convert2String(value);
                    }
                    return value;
                }},
                { field: 'GroupName', title: '组名称' },
                { field: 'Orders', title: '排序'}
            ]],
            toolbar:toolbar
        });
    }
    return {
        initModule:function(KVGroupName){

            initGroupName = KVGroupName;
            initAll();
            buildTable(config.buildTableTypeWithManagement,true,initGroupName,token);

            // 隐藏组名
            if (!fw.checkIsTextEmpty(initGroupName)) {
                fw.getObjectStartWith('search_GroupName_Text').hide();
                fw.getObjectStartWith('search_GroupName_Form').hide();
            }

        }
    }
};
