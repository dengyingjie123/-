<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="keywords" content="jquery,ui,easy,easyui,web">
    <meta name="description" content="easyui help you build your web page easily!">
    <title>jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
    <script>
        var products = [
            {productid:'FI-SW-01',name:'Koi'},
            {productid:'K9-DL-01',name:'Dalmation'},
            {productid:'RP-SN-01',name:'Rattlesnake'},
            {productid:'RP-LI-02',name:'Iguana'},
            {productid:'FL-DSH-01',name:'Manx'},
            {productid:'FL-DLH-02',name:'Persian'},
            {productid:'AV-CB-01',name:'Amazon Parrot'}
        ];
        function formatProduct(value){
            for(var i=0; i<products.length; i++){
                if (products[i].productid == value) return products[i].name;
            }
            return value;
        }
        $(function(){
            $('#tt').datagrid({
                onLoadSuccess:function(){
                    var merges = [{
                        index:2,
                        rowspan:2
                    },{
                        index:5,
                        rowspan:2
                    },{
                        index:7,
                        rowspan:2
                    },{
                        index:9,
                        colspan:2
                    }];
                    for(var i=0; i<merges.length; i++) {
                        $('#tt').datagrid('mergeCells',{
                            index:merges[i].index,
                            field:'productid',
                            rowspan:merges[i].rowspan
                        });
                    }

                    $('#tt').datagrid('mergeCells',{
                        index:9,
                        field:'productid',
                        colspan:2
                    });
                }
            });
        });
    </script>
</head>
<body>
<table id="tt" title="Merge Cells" style="width:100%;height:550px"
       url="<%=Config.getWebRoot() %>/datagrid_data.json"
       singleSelect="true" iconCls="icon-save" rownumbers="true"
       idField="itemid" pagination="true">
    <thead frozen="true">
    <tr>
        <th field="productid" width="80" formatter="formatProduct">Product ID</th>
        <th field="itemid" width="100">Item ID</th>
    </tr>
    </thead>
    <thead>
    <tr>
        <th colspan="2">Price</th>
        <th rowspan="2" field="attr1" width="150">Attribute</th>
        <th rowspan="2" field="status" width="60" align="center">Stauts</th>
    </tr>
    <tr>
        <th field="listprice" width="80" align="right">List Price</th>
        <th field="unitcost" width="80" align="right">Unit Cost</th>
    </tr>
    </thead>
</table>

</body>
</html>