/**
 *
 * 创建一个公共客户分配的方法
 * @parame success 一个回调方法
 */
var CustomerSaleClass = function (token, customerId, remark, data) {

    ///  初始化部分 开始  /////////////////////////////////////////////////////////////////
    //创建临时变量用来判断下拉的状态

    /**
     * 初始化主页面控件
     */
    function initAll() {

        initCustomerDistributeWindow(data);

    }


    //销售小组事件
    function selectSalemanTree(selectIndexId) {
        $('#salemanGroupId' + token).combotree({
            onSelect: function (node) {
                var url = WEB_ROOT + "/sale/SalemanGroup_getDepartmentSaleman.action?salemanGroup.id=" + node["id"];
                fw.combotreeLoad("saleman" + token, url, selectIndexId);
            }
        });
    }


    function initCustomerDistributeWindow(data) {

        var url = WEB_ROOT + "/modules/sale/CustomerSale_Save.jsp?token=" + token;
        var windowId = "CustomerSaleWindow" + token;
        fw.window(windowId, '销售分配管理', 500, 200, url, function () {

            fw.jsonJoin(data, {'departmentId':'-2'}, false);
            fw.jsonJoin(data, {'saleGroupId':'-2'}, false);
            fw.jsonJoin(data, {'saleManId':'-2'}, false);
            //fw.jsonJoin(data, {'id':''},false);

            var selectIndexDepartmentId = data["departmentId"];
            var selectIndexSaleManGroupId = data["saleGroupId"];
            var selectIndexSaleManId = data["saleManId"];
            //var uid=data["id"];


            // 初始化查询事件
            fw.combotreeBuild4FortuneCenter("departmentId" + token, selectIndexDepartmentId, function (node) {

                var url = WEB_ROOT + "/sale/SalemanGroup_getDepartmentgroup.action?salemanGroupVO.departmentId=" + node["id"];

                fw.combotreeLoad("salemanGroupId" + token, url, selectIndexSaleManGroupId);

                if (!fw.checkIsJsonObject(selectIndexSaleManId)) {
                    selectSalemanTree(selectIndexSaleManId);
                }

            });

            onClickCustomerPersonalSubmit();
            onClickCustomerPersonalDelete();
            $('#customerId' + token).val(customerId);
            $('#remark' + token).val(remark);
            //$('#uid' + token).val(uid);
        }, null);
    }

    /**
     * 数据提交事件
     */
    function onClickCustomerPersonalSubmit() {
        var buttonId = "btnCustomerSaleSubmit" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formCustomer" + token;
            var url = WEB_ROOT + "/customer/CustomerDistribution_saveCustomerDistribution.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                //alert('done');
                process.afterClick();
                fw.datagridReload("CustomerPersonalTable" + token);
                fw.datagridReload("CustomerInstitutionTable" + token);
                fw.windowClose('CustomerSaleWindow' + token);
            }, function () {
                process.afterClick();
            });
        });
    }


    /**
     * 删除事件
     */
    function onClickCustomerPersonalDelete() {
        var buttonId = "btnCustomerSaleDelete" + token;
            fw.bindOnClick(buttonId, function(process) {
                fw.confirm('删除确认', '是否确认删除数据？', function(){
                        var formId = "formCustomer" + token;
                        var url = WEB_ROOT + "/customer/CustomerDistribution_remove.action";
                        fw.bindOnSubmitForm(formId, url, function () {
                            process.beforeClick();
                        }, function () {
                            //alert('done');
                            process.afterClick();
                            fw.datagridReload("CustomerPersonalTable" + token);
                            fw.windowClose('CustomerSaleWindow' + token);
                        }, function () {
                            process.afterClick();
                        });
                }, function(){
                    process.afterClick();
                });
            });

      /*  var buttonId = "btnCustomerSaleDelete" + token;
        fw.bindOnClick(buttonId, function (process) {
            var formId = "formCustomer" + token;
            var url = WEB_ROOT + "/customer/CustomerDistribution_delete.action";
            fw.bindOnSubmitForm(formId, url, function () {
                process.beforeClick();
            }, function () {
                //alert('done');
                process.afterClick();
                fw.datagridReload("CustomerPersonalTable" + token);
                fw.windowClose('CustomerSaleWindow' + token);
            }, function () {
                process.afterClick();
            });
        });*/
    }



    return{
        /**
         * boot.js加载时调用的初始化方法
         */
        initModule: function () {
            return initAll();
        }
    };
};