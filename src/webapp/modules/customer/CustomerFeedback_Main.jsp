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
                <td align="right">日志类型</td>
                <td><input type="text"  class="easyui-combotree" id="search_typeId<%=token %>" name="customerFeedback.typeId" style="width:100px;" editable="false" /></td>
                <td align="right">日志内容</td>
                <td><input type="text" id="search_content<%=token %>" name="customerFeedback.content" style="width:100px;" /></td>
                <td align="right">客户姓名</td>
                <td><input type="text" id="search_customerName<%=token %>" name="customerFeedback.customerName" style="width:100px;" />                </td>
                <td><a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
            </tr>
            <tr>
              <td>日志时间（开始）</td>
              <td><input type="text" class="easyui-datebox" id="search_time_Start<%=token %>" style="width:100px;" editable="false" /></td>
              <td>日志时间（结束）</td>
              <td><input type="text" class="easyui-datebox" id="search_time_End<%=token %>" style="width:100px;" editable="false" /></td>
              <td align="right">理财师
              </td>
              <td><input type="text" id="search_saleManName<%=token %>" name="customerFeedback.saleManName" style="width:100px;" /></td>
              <td><a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a></td>
            </tr>
        </table>
</div>
    <br>
    <table id="Table<%=token%>"></table>

</div>
</body>
</html>