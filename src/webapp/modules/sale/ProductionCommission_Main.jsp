<%--
  Created by IntelliJ IDEA.
  User: yux
  Date: 2016/6/12
  Time: 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style='padding:5px;'>
    <div class='easyui-panel' title='查询' iconCls='icon-search'>
        <table border='0' cellpadding='3' cellspacing='0'>
            <tr>
            </tr>
            <tr>
                <td>
                    <a id='btnProductionCommissionSearchSubmit<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                       iconCls='icon-search'>查询</a>
                </td>
                <td>
                    <a id='btnProductionCommissionSearchReset<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                       iconCls='icon-cut'>重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id='productionCommissionTable<%=token%>'></table>
</div>
</body>
</html>
