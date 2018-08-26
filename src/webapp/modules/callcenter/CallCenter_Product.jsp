<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-1-20
  Time: 上午2:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*" language="java" %>
<html>
<head>
    <title>产品</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/callcenter/callcenter.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/callcenter/style.css">
    <script type="text/javascript">
        var WEB_ROOT = "/core";

        function bindingProjectDataGrid() {
            $("#product_datagrid_project").datagrid({
                method: 'post',
                url: '',
                loadMsg: '正在加载数据，请稍等...',
                striped: true,
                autoRowHeight: true,
                singleSelect: true,
                nowrap: true,
                fitColumns: false,
                pagination: true,
                columns: [[
                    { field: 'ID', width: 50, align: 'center', hidden: true, title: '编号' },
                    { field: 'SID', width: 50, align: 'center', hidden: true, title: '序号' },
                    { field: 'Name', width: 200, align: 'center', title: '名称' },
                    { field: 'Size', width: 100, align: 'center', title: '规模' },
                    { field: 'StartTime', width: 120, align: 'center', title: '开始时间' },
                    { field: 'EndTime', width: 120, align: 'center', title: '结束时间' },
                    { field: 'StatusId', width: 80, align: 'center', title: '状态' },
                    { field: 'TypeId', width: 80, align: 'center', title: '类型' },
                    { field: 'Description', width: 300, align: 'center', title: '描述' }
                ]],
                onClickRow:function(rowIndex, rowData){
                    var row = rowData;
                    if(row != null){
                        bindingProductDataGrid(row.ID);
                    }
                }
            });
        }

        function bindingProductDataGrid(projectid) {
            $("#product_datagrid_product").datagrid({
                method: 'post',
                url: '',
                loadMsg: '正在加载数据，请稍等...',
                striped: true,
                autoRowHeight: true,
                singleSelect: true,
                nowrap: true,
                fitColumns: false,
                pagination: true,
                columns: [[
                    { field: 'ID', width: 50, align: 'center', hidden: true, title: '编号' },
                    { field: 'SID', width: 50, align: 'center', hidden: true, title: '序号' },
                    { field: 'Name', width: 200, align: 'center', title: '名称' },
                    { field: 'Size', width: 100, align: 'center', title: '配额' },
                    { field: 'StartTime', width: 120, align: 'center', title: '开始时间' },
                    { field: 'StopTime', width: 120, align: 'center', title: '结束时间' },
                    { field: 'ValueDate', width: 120, align: 'center', title: '起息日' },
                    { field: 'ExpiringDate', width: 120, align: 'center', title: '到期日' },
                    { field: 'InterestDate', width: 120, align: 'center', title: '付息日' },
                    { field: 'AppointmentMoney', width: 80, align: 'center', title: '预约金额' },
                    { field: 'Status', width: 80, align: 'center', title: '状态' }
                ]],
                onClickRow:function(rowIndex, rowData){
                    var row = rowData;
                    if(row != null){
                        bindingCompositionDataGrid(row.ID);
                    }
                }
            });
        }

        function bindingCompositionDataGrid(productid) {
            $("#product_datagrid_composition").datagrid({
                method: 'post',
                url: '',
                loadMsg: '正在加载数据，请稍等...',
                striped: true,
                autoRowHeight: true,
                singleSelect: true,
                nowrap: true,
                fitColumns: false,
                pagination: true,
                columns: [[
                    { field: 'ID', width: 50, align: 'center', hidden: true, title: '编号' },
                    { field: 'SID', width: 50, align: 'center', hidden: true, title: '序号' },
                    { field: 'Name', width: 200, align: 'center', title: '名称' },
                    { field: 'SizeStart', width: 100, align: 'center', title: '范围开始' },
                    { field: 'SizeStop', width: 120, align: 'center', title: '范围结束' },
                    { field: 'ExpectedYield', width: 120, align: 'center', title: '预期收益率' },
                    { field: 'FloatingRate', width: 120, align: 'center', title: '浮动收益率' },
                    { field: 'BuyingRate', width: 120, align: 'center', title: '购买费率' },
                    { field: 'PayRate', width: 120, align: 'center', title: '支付率' }
                ]],
                onClickRow:function(rowIndex, rowData){

                }
            });
        }

        function RetrunValue() {
            var row = $("#product_datagrid_product").datagrid("getSelected");
            if (row != null) {
                var returnvalue = "{\"ID\":\"" + row.ID + "\",\"Text\":\"" + row.F_Name + "\"}";
                window.returnValue = returnvalue;
                window.close();
            }
            else {
                alert("没有选中任何信息");
                return;
            }
        }
    </script>
</head>
<body>
    <div class="easyui-panel" title="查询">
        <table>
            <tr>
                <td>产品名称</td>
                <td>
                    <input type="text" id="product_txt_searchproductname" />
                </td>
                <td>预约金额</td>
                <td>
                    <input type="text" id="product_txt_searchAppointmentMoney" />
                </td>
            </tr>
        </table>
    </div>
    <table id="product_datagrid_project"></table>
    <table id="product_datagrid_product"></table>
    <table id="product_datagrid_composition"></table>
</body>
</html>