<%--
  Created by IntelliJ IDEA.
  User: 张舜清
  Date: 2015/4/1
  Time: 10:07
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
                <td>客户名称</td>
                <td><input type="text" id="search_CustomerName<%=token %>" style="width:150px;" editable="false" /></td>
                <td>提现开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_Time_Start<%=token %>" style="width:90px;" editable="false" /></td>
                <td>提现结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_Time_End<%=token %>" style="width:90px;" editable="false" /></td>
                <td>
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="CustomerWithdrawTable<%=token%>"></table>

</div>
</body>
</html>