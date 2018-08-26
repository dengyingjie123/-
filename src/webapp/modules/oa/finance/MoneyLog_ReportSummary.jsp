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
                <td>账务开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_MoneyTime_Start<%=token %>" style="width:90px;" editable="false" /></td>
                <td>账务结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_MoneyTime_End<%=token %>" style="width:90px;" editable="false" /></td>
                <td>
                    <a id="btnSearchMoneyLog<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetMoneyLog<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="MoneyLogReportParentCompanyTreeTable<%=token%>"></table>

</div>
</body>
</html>