<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2015/4/28
  Time: 9:11
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
            <tr>
                <td>类型</td>
                <td><input type="text" class="easyui-combotree" id="search_type<%=token %>" style="width:100px;"   /></td>
                <td>状态</td>
                <td><input type="text" class="easyui-combotree"  id="search_status<%=token %>" style="width:100px;"   /></td>
                  <td>内容</td>
                 <td><input type="text"   class="easyui-validatebox" id="search_content<%=token %>" style="width:100px;"  /></td>
                <td>开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_StartTime_Start<%=token %>"  editable="false" style="width:100px;"  /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_StartTime_End<%=token %>"  editable="false" style="width:100px;"  /></td>
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
    <table id="CustomerMoneyLogDataTable<%=token%>"></table>

</div>
</body>
</html>
