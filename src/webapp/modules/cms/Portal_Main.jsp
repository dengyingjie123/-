<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Portal - jQuery EasyUI</title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/default.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/buttons.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/portal.css">
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/system/ConfigClass.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/boot.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/treeplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/validator.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/highcharts4/js/highcharts.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/cms/jquery.portal.js"></script>

    <style type="text/css">
        .title{
            font-size:16px;
            font-weight:bold;
            padding:20px 10px;
            background:#eee;
            overflow:hidden;
            border-bottom:1px solid #ccc;
        }
        .t-list{
            padding:5px;
        }
    </style>
    <script>
        $(function(){

            $('#pp').portal({
                border:false,
                fit:true
            });
            add();
        });
        function add(){
            var leftPanel = document.getElementById('leftPanel');
            var centerPanel = document.getElementById('centerPanel');
            var rightPanel = document.getElementById('rightPanel');
            for(var i=0; i<3; i++){
                var p=$('<div/>');
                if(i==0){
                    p.appendTo(rightPanel);
                }else if(i==1){
                    p.appendTo(centerPanel);
                }else{
                    p.appendTo(leftPanel);
                }
                p.panel({
                    title:'Title'+i,
                    content:'<div style="padding:5px;">Content'+(i+1)+'</div>',
                    height:100,
                    closable:true,
                    collapsible:true
                });
                $('#pp').portal('add', {
                    panel:p,
                    columnIndex:i
                });
            }
            $('#pp').portal('resize');
        }
        function remove(){
            $('#pp').portal('remove',$('#pgrid'));
            $('#pp').portal('resize');
        }

        function getDivPosition(){
            var obj = document.getElementById("clock");
            var parentPanelID= $("#clock").parent().parent().attr("id");
            var parentIndex=$("#clock").parent().index();
            alert("所在div:"+parentPanelID+" 的第 "+parentIndex+" 位置");
        }

    </script>
</head>
<body class="easyui-layout">
<div region="north" class="title" border="false" style="height:60px;">
    门户网站
</div>
<div region="center" border="false">
    <div id="pp" style="position:relative">
        <div id="leftPanel" style="width:30%;">
            <div id="clock" title="Clock" style="text-align:center;background:#f3eeaf;height:150px;padding:5px;">
                <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="100" height="100">
                    <param name="movie" value="http://www.respectsoft.com/onlineclock/analog.swf">
                    <param name=quality value=high>
                    <param name="wmode" value="transparent">
                </object>
            </div>
            <div title="Tutorials" collapsible="true" closable="false" style="height:200px;padding:5px;">
                <div class="t-list"><a href="http://www.jeasyui.com/tutorial/datagrid/datagrid1.php">Build border layout for Web Pages</a></div>
                <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/panel.php">Complex layout on Panel</a></div>
                <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/accordion.php">Create Accordion</a></div>
                <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/tabs.php">Create Tabs</a></div>
                <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/tabs2.php">Dynamically add tabs</a></div>
                <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/panel2.php">Create XP style left panel</a></div>
            </div>
        </div>
        <div id="centerPanel" style="width:40%;">
            <div id="pgrid" title="DataGrid" closable="false" style="height:200px;">
                <table class="easyui-datagrid" style="width:650px;height:auto"
                       fit="true" border="false"
                       singleSelect="true"
                       idField="itemid" url="datagrid_data.json">
                    <thead>
                    <tr>
                        <th field="itemid" width="60">Item ID</th>
                        <th field="productid" width="60">Product ID</th>
                        <th field="listprice" width="80" align="right">List Price</th>
                        <th field="unitcost" width="80" align="right">Unit Cost</th>
                        <th field="attr1" width="120">Attribute</th>
                        <th field="status" width="50" align="center">Status</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
        <div id="rightPanel" style="width:30%;">
            <div title="Searching" iconCls="icon-search" closable="true" style="height:80px;padding:10px;">
                <input class="easyui-searchbox">
            </div>
            <div title="Graph" closable="true" style="height:200px;text-align:center;">
                <img height="160" src="http://knol.google.com/k/-/-/3mudqpof935ww/ip4n5y/web-graph.png"/>
            </div>
            <div title="Graph" closable="true" style="height:200px;text-align:center;">
                <input type="button" value="check" onclick="getDivPosition()"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>