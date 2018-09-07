<%--
  Created by IntelliJ IDEA.
  User: zsq
  Date: 4/22/2015
  Time: 11:51 AM
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
                <td>原产品编号</td>
                <td><input type="text" id="search_originalProductionNum<%=token %>" style="width:150px;"></td>
                <td>原产品持有人</td>
                <td><input type="text" id="search_originalCustumerName<%=token %>" style="width:150px;"></td>
            <tr/>
            <tr>
                <td>现产品编号</td>
                <td><input type="text" id="search_currentProductionNum<%=token %>" style="width:150px;"></td>
                <td>现产品持有人</td>
                <td><input type="text" id="search_currentCustomerName<%=token %>" style="width:150px;"></td>
                <td>转让</td>
                <td><input  id="search_transferSatues<%=token%>" class="easyui-combotree" style="width:100px;"/></td>
                    <%--<select type="text" editable="false" id="search_transferSatues<%=token %>" style="width:70px;">--%>
                        <%--<option value="">全部</option>--%>
                        <%--<option value="0">未转让</option>--%>
                        <%--<option value="1">已转让</option>--%>
                    <%--</select>--%>
                <td>
                    <a id="btnProductionTransferSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnProductionTransferSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ProductionTransferTable<%=token%>"></table>

</div>
</body>
</html>
