/**
 * Created by Administrator on 2014/12/3.
 */
var MessageClass = function(token){

    function initAll(){
        initTableMessageTable();
        //初始化查询表单
        initFormMessageSearch();
        //初始化查询事件
        onClickMessageSearch();
        //初始化重置事件
        onClickMessageReset();
        //初始化详细信息表单


    }

    function initFormMessageSearch(){
        //alert('messageSearch');
        fw.getComboTreeFromKV('search_IsRead'+token , 'OA_Message_IsRead','k','-2');

        fw.getComboTreeFromKV('search_Type'+token , 'OA_Message_Type' , 'k' , '-2');

    }

    function initTableMessageTable(){

        var strTableId = 'messageShowTable'+token;

        var url = WEB_ROOT + "/customer/CustomerSms_list.action";
       // alert(url);
        $('#'+strTableId).datagrid({
            title:"发送信息",
            url:url,
            fitColumns:true,
            queryParams:{},
            loadMsg:'数据正在加载，请稍后...',
            singleSelect:true,
            pageList:[15,30,60],
            pageSize:15,
            rownumbers:true,
            loadFilter:function(data){
                try{
                    data = fw.dealReturnObject(data);
                    return data;
                }catch(e){

                }
            },
            pagination:true,
            frozenColumns:[[
                {field: 'ck' , checkbox:true}
            ]],
            columns:[[
                { field:'sid' , title:'序号' ,hidden:true , width:30},
                { field:'id' , title:'编号',hidden:true , width:30},
                { field:'receiverName' , title:'收信人' , width:30},
                { field:'receiverMobile' , title:'收信人号码' , width:30},
                { field:'waitingTime' , title:'待发送时间' , width:30},
                { field:'subject' , title:'主题', width:30},
                { field:'content' , title:'内容', width:30},
                { field:'senderName',title:'发送者', width:30},
                { field:'sendTime' , title:'发送时间',width:30},
                { field:'feedbackStatus', title:'发送状态', width:30}
            ]]
        });
    }

    /**
     * 初始化toolbar按钮
     */
    function onClickMessageAdd(){
        var buttonId = "btnMessageAdd"+token;

        fw.bindOnClick(buttonId , function(process){
            //打开窗口，初始化表单数据为空
            initWindowMessageWindow({});
        });
    }
       //回复窗口后期定义
    function onClickMessageReply(){
        var buttonId = "btnMessageReply"+token;

        fw.bindOnClick(buttonId , function(process){

        });
    }
    //标记为已读
    function onClickMessageMark(){
        var buttonId = "btnMessageMark"+token;

        fw.bindOnClick(buttonId , function(process){
            fw.datagridGetSelected('messageShowTable'+token , function(selected){
                var url = WEB_ROOT + '/oa/finance/Message_mark.action?message.sid='+selected.sid;

                fw.post(url , null , function(data){
                    fw.datagridReload('messageShowTable'+token);

                } , null);
            });
        });
    }


    function onClickMessageSearch(){
        var buttonId = "btn_SearchMessage"+token;
        fw.bindOnClick(buttonId , function(process){
            var strTableId  = "messageShowTable"+token;
            var params = $('#'+strTableId).datagrid('options').queryParams;

            params["messageVO.SenderId"] = $("#search_SenderId"+token).val();
            params["messageVO.Subject"] = $("#search_Subject"+token).val();
            params["messageVO.Content"] =$("#search_Content"+token).val();
            params["messageVO.IsRead"] = fw.getFormValue('search_IsRead'+token , fw.type_form_combotree , fw.type_get_text);
            params["messageVO.Type"] = fw.getFormValue('search_Type'+token , fw.type_form_combotree , fw.type_get_text);
            params["messageVO.FromTime"] = fw.getFormValue('search_FromTime'+token , fw.type_form_datebox , fw.type_get_value);
            params["messageVO.ReceiveTime"] = fw.getFormValue('search_ReceiveTime'+token , fw.type_form_datebox , fw.type_get_value);
            alert(JSON.stringify(params));
            $('#'+strTableId).datagrid('load');

            fw.treeClear();
        });
    }

    function onClickMessageReset(){
        var buttonId = "btn_ResetSearchMessage"+token;

        fw.bindOnClick(buttonId , function(process){
            $('#search_SenderId'+token).val('');
            $('#search_Subject'+token).val('');
            $('#search_Content'+token).val('');
            fw.combotreeClear('search_IsRead'+token);
            fw.combotreeClear('search_Type'+token);
            $('#search_FromTime'+token).datebox("setValue" , '');
            $('#search_ReceiveTime'+token).datebox("setValue" , '');

        });
    }

    function initWindowMessageWindow(data){
        var url=WEB_ROOT+'/modules/oa/message/Message_Save.jsp?token='+token;
        var windowId = "MessageWindow"+token;
        //初始化表单提交

        fw.window(windowId , '收到的消息', 620, 460 , url ,function(){
            fw.getComboTreeFromKV('Type'+token , 'OA_Message_Type','k','-2');
            fw.getComboTreeFromKV('Priority'+token , 'Message_Priority','k','-2');
             onClickMessageSubmit();
        },null);
    }

    function onClickMessageSubmit(){
        var buttonId = "btnMessageSubmit"+token;
        //alert(buttonId);
        fw.bindOnClick(buttonId , function(process){
            var formId = "formMessageSubmit"+token;
            var url = WEB_ROOT+"/oa/finance/Message_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId , url , function(){
                process.beforeClick();
            },function(){

                process.afterClick();
                fw.datagridReload("messageShowTable"+token);
                fw.windowClose("MessageWindow"+token);
            },function(){
                process.afterClick();
            });
        });
    }

    function onClickMessageDelete() {
        var buttonId = "btnMessageDelete" + token;

        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('messageShowTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/oa/finance/Message_delete.action?message.sid="+selected.sid;

                    fw.post(url, null, function(data) {
                        fw.datagridReload('messageShowTable'+token);
                    }, null);
                }, null);
            });
        });
    }

    return{
        initModule:function(){
            return initAll();
        }
    };
}