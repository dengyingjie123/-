<%--
  Created by IntelliJ IDEA.
  User: zhang
  Date: 2015/9/16
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;" class="easyui-layout" fit="true">
    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>订单号</td>
                <td><input type="text" id="search_OrderNum<%=token %>" style="width:150px;" /></td>
                <td>客户</td>
                <td><input type="text" id="search_Customer<%=token %>" style="width:150px;" /></td>
                <td>产品</td>
                <td><input type="text" id="search_Product<%=token %>" style="width:150px;" /></td>
                <td align="right">状态</td>
                <td><input  class="easyui-combotree" id="search_status<%=token %>" editable="false" style="width:100px"/></td>
                <td>
                    <a id="btnSearchOrder<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetOrder<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>

    <div>
        <table id="OrderQueryTable<%=token%>"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('queryWindow<%=token%>')">取消</a>
    </div>

</div>
</body>
</html>

