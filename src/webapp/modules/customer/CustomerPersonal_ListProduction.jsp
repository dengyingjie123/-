<%--
  Created by Jiangwandong.
  User: Administrator
  Date: 2014/11/26
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>CostomerPersonal_ListProduction</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
</head>
<body>
<div class="easyui-layout" fit="true">
    <div class = "easyui-panel" title="查询" iconCls="icon-search">
        <table>
            <tr>
                <%--<td>产品名称</td>--%>
                <%--<td><input type="text" class="easyui-combotree" id="Search_Productions" style="width:150px" editable="false"></td>--%>
                <td>所属项目</td>
                <td><input type="text" id="search_ProjectName<%=token %>" class="easyui-combotree"  style="width:150px" editable="false"></td>

                <td><a id="btnSearchCostomerProduction<%=token %>" class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-search">查询</a></td>
                <td><a id="btnResetCostomerProduction<%=token %>" class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-cut">重置</a></td>
            </tr>
        </table>
    </div>
    <table id = "CustomerPersonal_ListTable<%=token%>"></table>
    <div region="south"  border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <table>
            <tr>
                <td ><a class="easyui-linkbutton" href="javascript:void(0)" onClick="fwCloseWindow('CustomerPersonal_ListProductionWindow<%=token%>')">确定</a></td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>
