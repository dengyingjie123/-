/**
 * 客户积分脚本类
 * @param token
 * @returns {{initModule: initModule}}
 * @constructor
 */
var CustomerPointClass = function(token){

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    /**
     * 初始化主页面控件
     */
    function initAll() {

        // 初始化查询事件
        onClickCustomerPointSearch();
        // 初始化查询重置事件
        onClickCustomerPointSearchReset();

        // 初始化表格
        initCustomerPointTable();
    }
    /**
     * 查询事件
     */
    function onClickCustomerPointSearch() {
        var buttonId = "btnCustomerPointSearchSubmit" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "CustomerPointTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            //查询参数

            params["customerPointVO.customerName"]=$("#customerName"+token).val();
//            if ($("#availablePoint"+token).val() != '') {
//                params["customerPointVO.availablePoint"]=$("#availablePoint"+token).val();
//            }
//            params["customerPointVO.usedPoint"]=$("#usedPoint"+token).val();

            $( '#' + strTableId).datagrid('load');

        });
    }
    /**
     * 查询重置事件
     */
    function onClickCustomerPointSearchReset() {
        var buttonId = "btnCustomerPintSearchReset" + token;
        fw.bindOnClick(buttonId, function(process)
        {
            $('#customerName'+token).val('');
            $("#availablePoint"+token).val('');
            $("#usedPoint"+token).val('');
        });
    }

    /**
     * 初始化表格
     */
    function initCustomerPointTable() {
        var strTableId = 'CustomerPointTable'+token;
        var url = WEB_ROOT+"/customer/CustomerPoint_list.action";

        $('#'+strTableId).datagrid({
            title: '客户积分信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            fitColumns:true,
            singleSelect:true,
            pageList:[15,30,60],
            pageSize: 15,
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
                { field: 'sid', title: 'sid',hidden:true, width: 30 },
                { field: 'id', title: 'id', hidden:true,width: 30 },
                { field: 'state', title: 'state',hidden:true, width: 30 },
                { field: 'customerName', title: '客户名称', width: 30 },
                { field: 'availablePoint', title: '可用积分', width: 30 },
                { field: 'usedPoint', title: '已使用积分', width: 30 }
            ]],
            toolbar:[{
                id:'btnCustomerPointAdd'+token,
                text:'添加',
                iconCls:'icon-add'
            },{
                id:'btnCustomerPointEdit'+token,
                text:'修改',
                iconCls:'icon-edit'
            },{
                id:'btnCustomerPointDelete'+token,
                text:'删除',
                iconCls:'icon-cut'
            }],
            onLoadSuccess:function() {
                // 初始化事件
                //添加
                onClickCustomerPointAdd();
                //修改事件
                onClickCustomerPointEdit();
                //删除事件
                onClickCustomerPointDelete();
            }
        });
    }

    /**
     * 添加事件
     */
    function onClickCustomerPointAdd() {
        var buttonId ="btnCustomerPointAdd"+token;
        fw.bindOnClick(buttonId,function(process){
            process.beforeClick();
            initCustomerPointWindow({});
            process.afterClick();
        })
    }
    /**
     * 修改事件
     */
    function onClickCustomerPointEdit() {
        var buttonId = "btnCustomerPointEdit" + token;
        fw.bindOnClick(buttonId, function(process) {
            process.beforeClick();
            fw.datagridGetSelected('CustomerPointTable'+token, function(selected){
                var id = selected.id;
                var url = WEB_ROOT + "/customer/CustomerPoint_load.action?customerPoint.id="+selected.id;
                fw.post(url, null, function(data){
                    data["customerPoint.customerName"]=selected.customerName;
                    initCustomerPointWindow(data);
                    process.afterClick();
                }, function () {
                    process.afterClick();
                });
            });
            process.afterClick();
        });
    }
    /**
     * 删除事件
     */
    function onClickCustomerPointDelete() {
        var buttonId = "btnCustomerPointDelete" + token;
        fw.bindOnClick(buttonId, function(process) {
            fw.datagridGetSelected('CustomerPointTable'+token, function(selected){
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                    var url = WEB_ROOT + "/customer/CustomerPoint_delete.action?customerPoint.sid="+selected.sid;
                    fw.post(url, null, function(data) {
                        fw.datagridReload('CustomerPointTable'+token);
                    }, null);
                }, null);
            });
        });
    }
    /**
     * 初始化弹出窗口
     * @param data
     */
    function initCustomerPointWindow(data) {

        data["customerPoint.operatorId"] = loginUser.getId();
        data["customerPoint.operatorName"] = loginUser.getName();

        var url =  WEB_ROOT + "/modules/customer/CustomerPoint_Save.jsp?token="+token;
        var windowId = "CustomerPointWindow" + token;
        fw.window(windowId, '客户积分', 370, 250, url, function() {

            //文本框绑定事件
            onClickCheckCustomer("name");
            //放大镜
            onClickCheckCustomer("btnCheckCustomer");
            //提交事件
            onClickCustomerPointSubmit();
            // 加载数据
            fw.formLoad('formCustomerPoint'+token, data);
        }, null);
    }
    /**
     * 文本框绑定事件
     */
    function onClickCheckCustomer(name){
        //根据ID绑定点击事件
        $('#' +name+ token).bind('click', function () {
            //获取客户列表的链接
            var url =  WEB_ROOT + "/modules/customer/CustomerList_Select.jsp?token=" + token;
            //弹出窗口的ID
            var selectionWindowId = "CustomerSelectWindow" + token;
            //弹出窗口
            fw.window(selectionWindowId, '客户列表', 930, 500, url, function() {
                //将选择客户的脚本脚在到弹出的页面中
                using(SCRIPTS_ROOT+'/customer/CustomerListSelectClass.js', function () {
                    var obj = new CustomerPointClass(token);
                    var customerListSelectClass = new CustomerListSelectClass(token, obj);
                    customerListSelectClass.initModule();
                });
            }, null);
        })
    }
    /**
     * 数据提交事件
     */
    function onClickCustomerPointSubmit() {
        var buttonId = "btnCustomerPointSubmit" + token;
        fw.bindOnClick(buttonId, function(process){
            var formId = "formCustomerPoint" + token;
            var url = WEB_ROOT + "/customer/CustomerPoint_insertOrUpdate.action";
            fw.bindOnSubmitForm(formId, url, function(){
                process.beforeClick();
            }, function() {
                process.afterClick();
                fw.datagridReload("CustomerPointTable"+token);
                fw.windowClose('CustomerPointWindow'+token);
            }, function() {
                process.afterClick();
            });
        });
    }

    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule:function(){
            return initAll();
        },loadCustomerInfo:function(customerId,customerName,accountId,accountName){
            //绑定文本框数据
            if(customerId!=''){
                $('#customerId'+token).val(customerId);
                $('#name'+token).val(customerName);
            }
            if(accountId != ''){
                $('#accountId'+token).val(accountId);
                $('#accountName'+token).val(accountName);
            }
        }

    };
};
