<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/30/14
  Time: 11:47 AM
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
                <td>产品</td>
                <td><input type="text"  id="search_Production<%=token %>" style="width:150px;" readonly="true"/><input type="hidden"  id="productionId<%=token %>" style="width:150px;" /></td>
                <td><a href="javascript:void(0)" id="btnProduction<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
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
            <td><span id="name<%=token%>"></span></td>
            <td><span id="size<%=token%>"></span></td>
            <td><span id="sumMoney<%=token%>"></span></td>
            <td><span id="average<%=token%>"></span></td>
        </tr>
    </table>
    <table id="DetailStatisticsTable<%=token%>"></table>

</div>
</body>
</html>