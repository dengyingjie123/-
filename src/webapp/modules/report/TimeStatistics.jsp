<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/30/14
  Time: 9:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token") ;
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SaleStart<%=token %>" style="width:100px;" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_SaleEnd<%=token %>" style="width:100px;" /></td>
                <td>销售人员</td>
                <td><input type="text" class="easyui-combotree" id="search_SaleMan<%=token %>" style="width:100px;" /></td>
                <td>
                    <a id="btnSearch<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table>
        <tr>
            <td><span id="timeBox<%=token%>"></span></td>
        </tr>
    </table>
    <table id="TimeStatisticsTable<%=token%>"></table>

</div>
</body>
</html>