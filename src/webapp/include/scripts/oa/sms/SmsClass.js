/**
 * Created by Jepson on 2015/7/3.
 */
var SmsClass = function (token) {

    /**
     * 初始化主页面控件
     */
    function initAll() {

        initSearchFrom();
//        // 初始化查询事件
        onClickSmsSearch();
//        // 初始化查询重置事件
        onClickSmsSearchReset();

        // 初始化表格
        initSmsTable();
    }
function initSearchFrom(){
    fw.getComboTreeFromKV('search_type'+token, 'System_SmsType', 'k','-2');
}
    var receiverType=0;
    var senderType=1;
    /**
     * 初始化查询表单
     */
    function onClickSmsSearch(){
        var buttonId = "btnSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var tab = $('#smsTable'+token).tabs('getSelected');
            var index = $('#smsTable'+token).tabs('getTabIndex',tab);
            //收件箱的选项卡
            if(index==receiverType){
                initSearchTable('SmsReceiverTable'+token);

            }
            //发件箱的选项卡
            else if(index==senderType){
                initSearchTable('SmsSenderTable'+token);
            }

        });
    }

    function initSearchTable(TableId){
        var params = $( '#' + TableId).datagrid('options').queryParams;
        params["smsVO.subject"] = $("#search_subject"+token).val();
        params["smsVO.content"] = $("#search_content"+token).val();
        params["smsVO.type"] = fw.getFormValue('search_type' + token, fw.type_form_combotree, fw.type_get_value);
        $( '#' + TableId).datagrid('load');
    }

    function onClickSmsSearchReset(){
        var buttonId = 'btnSearchReset' + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_subject"+token).val('');
            $("#search_content"+token).val('');
            fw.combotreeClear('#search_type'+token);

        });
    }

    // 构造初始化表格脚本
    function initSmsTable() {
        using(SCRIPTS_ROOT + '/oa/sms/SenderSmsClass.js', function () {
            var senderSmsClass = new SenderSmsClass(token);
            senderSmsClass.initModule();
        });
        using(SCRIPTS_ROOT + '/oa/sms/ReceiverSmsClass.js', function () {

            var receiverSmsClass = new ReceiverSmsClass(token);
            receiverSmsClass.initModule();
        });

    }


    return{
        /**
         * boot.js 加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
};
