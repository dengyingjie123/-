/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/10/14
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
var CustomerSelectClass = function(token) {
    var customerId=null;
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickCustomerPersonalSearch();
        // 初始化查询重置事件
        onClickCustomerPersonalSearchReset();

        // 初始化表格
        initTableCustomerPersonalTable();

        //初始化选择客户按钮
        onClickSelectedCustomer()
    }
    /**
     * 初始化表格
     */
    function initTableCustomerPersonalTable() {
        var strTableId = 'CustomerTable'+token;
        var url = WEB_ROOT+"/customer/CustomerPersonal_listPagerCustomerVO.action";

        $('#'+strTableId).datagrid({
            title: '个人客户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[10],
            pageSize: 10,
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
                { field: 'sid', title: '序号', hidden:true },
                { field: 'id', title: '编号', hidden:true},
                { field: 'personalNumber', title: '客户号'},
                { field: 'name', title: '姓名'},
                { field: 'sex', title: '性别',hidden:true},
                { field: 'birthday', title: '出生日期',hidden:true},
                { field: 'nation', title: '国籍',hidden:true},
                { field: 'workAddress', title: '工作地址',hidden:true},
                { field: 'homeAddress', title: '家庭地址',hidden:true},
                { field: 'identityCardAddress', title: '身份证地址',hidden:true},
                { field: 'mobile', title: '移动电话'},
                { field: 'phone', title: '固定电话',hidden:true},
                { field: 'email', title: '电子邮件',hidden:true},
                { field: 'postNo', title: '邮编',hidden:true},
                { field: 'remark', title: '备注'}
            ]]
        });
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 查询事件
     */
    function onClickCustomerPersonalSearch() {
        var buttonId = "btnSearchCustomer" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "CustomerTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["customerName"] = $("#search_Name"+token).val();
            params["customerMobile"] = $("#search_Mobile"+token).val();
            $( '#' + strTableId).datagrid('load');                         //加载第一页的行
            // alert(ids);
            fw.treeClear()
        });

    }


    /**
     * 查询重置事件
     */
    function onClickCustomerPersonalSearchReset() {
        var buttonId = "btnResetCustomer" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_Name"+token).val('');
            $('#search_Mobile'+token).val('');
        });
    }


    /**
     * 初始化选择客户按钮
     */
    function onClickSelectedCustomer(callback) {
        var buttonId = "btnSelectedCustomer" + token;
        fw.bindOnClick(buttonId, function(){

            fw.datagridGetSelected('CustomerTable'+token, function(selected){
                var customer = {'customerId':selected.id, 'customerName':selected.name};

                if (fw.checkIsFunction(callback)) {
                    callback(customer);
                }

                fwCloseWindow('CustomerSelectWindow'+token);
            });
        });
    }



    ///  事件定义 结束  /////////////////////////////////////////////////////////////////

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        },
        initOnClickSelected:function(callback){

            initTableCustomerPersonalTable();
            onClickSelectedCustomer(callback);


            onClickCustomerPersonalSearchReset();
            onClickCustomerPersonalSearch();

        }
    };
}
