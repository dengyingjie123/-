/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/10/14
 * Time: 1:24 PM
 * 客户分配审核界面.
 */
var CustomerAuditClass = function(token) {
    var customerId=null;
    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////

    /**
     * 初始化主页面控件
     */
    function initAll() {

        //初始化查询区域
        initSearchArea()
        onClickCustomerInstitutionSearch();onClickCustomerPersonalSearchReset();
        // 初始化表格
        initTableCustomerPersonalTable();
        initTableCustomerInstitutionTable();

    }

    function initSearchArea(){

        var dataStatus=[
            {'id':0,'text':"未审核"},
            {'id':1,'text':"审核通过"},
            {'id':2,'text':"未通过"}
        ];
        var tok=token+1;
        $('#search_personal_AuditStatus'+tok).combotree('loadData', [
            {'id':0,'text':"未审核"},
            {'id':1,'text':"审核通过"},
            {'id':2,'text':"未通过"}
        ]);
        //$('#search_personal_AuditStatus'+tok).combotree('loadData',dataStatus);
        $('#search_institution_AuditStatus'+token).combotree('loadData',dataStatus);

        // 初始化查询事件
        onClickCustomerPersonalSearch();
        onClickCustomerInstitutionSearch();
        // 初始化查询重置事件
        onClickCustomerPersonalSearchReset();
        onClickCustomerInstitutionSearchReset();

    }
    /**
    * 修改:姚章鹏
    * 内容：客户分配审核的列自适应，默认赋予15条记录，各个表都统一15、30、60，弹窗为 10、15、20
     * 时间:2015年6月18日09:42:51
     */
    function initTableCustomerPersonalTable() {
        var strTableId = 'CustomerPersonalTable'+token;
        var url = WEB_ROOT+"/customer/CustomerDistribution_listPersonalCustomer.action";

        $('#'+strTableId).datagrid({
            title: '个人客户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
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
                { field: 'sid', title: '序号', hidden:true },
                { field: 'id', title: '编号', hidden:true},
                { field: 'status', title: '审核', hidden:true},
                { field: 'saleManId', title: '销售员ID', hidden:true},
                { field: 'personalNumber', title: '客户号' },
                { field: 'name', title: '姓名' },
                { field: 'sex', title: '性别', hidden:true },
                { field: 'birthday', title: '出生日期', hidden:true },
                { field: 'identityCardAddress', title: '地址', hidden:true},
                { field: 'mobile', title: '移动电话'  },
                { field: 'phone', title: '固定电话' , hidden:true},
                { field: 'email', title: '电子邮件' , hidden:true},
                { field: 'createTime', title: '创建时间'  },
                { field: 'salesmanName', title: '销售员'  },
                { field: 'auditStatus', title: '审核状态',formatter:function(value,row,index){
                    if(value=='0'){
                        return "未审核";
                    }else if(value=='1'){
                        return "审核通过";
                    }else if(value=='2'){
                        return "未通过";
                    }
                }}
            ]],
            onLoadSuccess:function(){
                var btnId='btnPersonalDistribution'+token;
                onClickPersonalDistributionAudit(btnId,strTableId,0);
            }
        });
    }


    function initTableCustomerInstitutionTable() {
        var strTableId = 'CustomerInstitutionTable'+token;
        var url = WEB_ROOT+"/customer/CustomerDistribution_listInstitutionCustomer.action";

        $('#'+strTableId).datagrid({
            title: '机构客户信息',
            url: url,
            queryParams: {
                // 此处可定义默认的查询条件
            },
            loadMsg:'数据正在加载，请稍后……',
            singleSelect:true,
            pageList:[10,20,40],
            pageSize: 10,
            rownumbers:true,
            loadFilter:function(data){
                try {
                    data = fw.dealReturnObject(data);

                    //alert(JSON.stringify(data));
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
                { field: 'sid', title: '序号', hidden:true  },
                { field: 'id', title: '编号', hidden:true },
                { field: 'status', title: '审核', hidden:true },
                { field: 'saleManId', title: '销售员ID', hidden:true  },
                { field: 'name', title: '机构名' },
                { field: 'legalPerson', title: '法人'  },
                { field: 'registeredCapital', title: '注册资本'  },
                { field: 'address', title: '注册地址' },
                { field: 'mobile', title: '移动电话'  },
                { field: 'phone', title: '固定电话' },
                { field: 'email', title: '电子邮件' },
                { field: 'salesmanName', title: '销售员'  },
                { field: 'auditStatus', title: '审核状态', formatter:function(value,row,index){
                    if(value=='0'){
                        return "未审核";
                    }else if(value=='1'){
                        return "审核通过";
                    }else if(value=='2'){
                        return "未通过";
                    }
                }}
            ]],
            onLoadSuccess:function(){
                var btnId='btnInstitutionDistribution'+token;
                onClickPersonalDistributionAudit(btnId,strTableId,1);
            }
        });
    }

    ///  初始化部分 结束  /////////////////////////////////////////////////////////////////

    ///  事件定义 开始  /////////////////////////////////////////////////////////////////

    /**
     * 查询事件
     */
    function onClickCustomerPersonalSearch() {
        var buttonId = "btnSearchCustomerPersonal" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "CustomerPersonalTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["customerName"] = $("#search_personal_Name"+token).val();
            // var status = $("#search_status"+token).combotree('getValues');
            var tok=token+1;
            var statusS=$('#search_personal_AuditStatus'+tok).combotree('getValue');
            params["customerStatus"] = statusS;
            $( '#' + strTableId).datagrid('load');
        });

    }

    function onClickCustomerInstitutionSearch() {
        var buttonId = "btnSearchCustomerInstitution" + token;
        fw.bindOnClick(buttonId, function(process) {
            var strTableId = "CustomerInstitutionTable"+token;
            var params = $( '#' + strTableId).datagrid('options').queryParams;
            params["customerName"] = $("#search_institution_Name"+token).val();
            params["customerMobile"] = $("#search_institution_Mobile"+token).val();
            // var status = $("#search_status"+token).combotree('getValues');
            var statusS=$('#search_institution_AuditStatus'+token).combotree('getText');
            params["customerStatus"] = statusS;
            $( '#' + strTableId).datagrid('load');
        });

    }

    /**
     * 查询重置事件
     */
    function onClickCustomerPersonalSearchReset() {
        var buttonId = "btnResetCustomerPersonal" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_personal_Name"+token).val('');
            fw.combotreeClear('search_personal_AuditStatus'+token+1);

        });
    }

    function onClickCustomerInstitutionSearchReset() {
        var buttonId = "btnResetCustomerInstitution" + token;
        fw.bindOnClick(buttonId, function(process) {
            $("#search_institution_Name"+token).val('');
            $('#search_institution_Mobile'+token).val('');
            fw.combotreeClear('search_institution_AuditStatus'+token);
        });
    }

    //分配审核事件
    /**
     * 修改:姚章鹏
     * 内容：添加文本编辑器
     * 时间：2015年6月18日09:44:05
     *
     */

    function onClickPersonalDistributionAudit(buttonId,tableId,remark){
        fw.bindOnClick(buttonId,function(process){
            fw.datagridGetSelected(tableId, function(selected){
                var id=selected.id;

                var customerName=selected.name;
                var salesmanName=selected.salesmanName;
                var status = selected.status;
                var customerId=selected.id;
                var saleManId=selected.saleManId;
//                if(status==0  ){
                    var url =  WEB_ROOT + "/modules/customer/CustomerAudit.jsp?token="+token;
                    var windowId = "customerAuditWindow" + token;
                    fw.window(windowId, '审核', 650, 200, url, function() {
                        //fw.initCKEditor("reason" + token);
                        // 初始化表单数据
                        $("#customerName"+token).val(customerName);
                        $("#salesmanName"+token).val(salesmanName);
                        $("#customerId"+token).val(customerId);
                        $("#saleManId"+token).val(saleManId);
                        $("#remark"+token).val(remark);
                        $("#id"+token).val(id);
                        $('#status'+token).combotree('loadData',[
                            {'id':0,'text':"未审核"},
                            {'id':1,'text':"审核通过"},
                            {'id':2,'text':"未通过"}
                        ]);

                        fw.bindOnClick("btnAuditSubmit"+token, function(process){
                            var url = WEB_ROOT +"/customer/CustomerDistribution_auditDistribution.action?"
                            fw.bindOnSubmitForm("formAudit"+token, url, function(){
                                process.beforeClick();
                            }, function() {
                                //alert('done');
                                process.afterClick();
                                fw.datagridReload(tableId);
                                fw.windowClose(windowId);
                            }, function() {
                                process.afterClick();
                            });
                        });

                    }, null);
//                }else{
//                    fw.alert("提示","该客户分配已审核！");
//                }
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
        }
    };
}
