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
                <td>客户名称</td>
                <td><input type="text"  name="customerName" id="customerName<%=token%>"/></td>
                <%--<td>可用积分</td>--%>
                <%--<td><input type="text" class="easyui-validatebox" name="availablePoint" data-options="validType:'number' "id="availablePoint<%=token%>"/></td>--%>
                <%--<td>已使用积分</td>--%>
                <%--<td><input type="text" class="easyui-validatebox"  name="usedPoint" data-options="validType:'number'" id="usedPoint<%=token%>"/></td>--%>
                <td>
                    <a id="btnCustomerPointSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnCustomerPintSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="CustomerPointTable<%=token%>"></table>

</div>
</body>
</html>
