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
            <tr>
                <td>类型</td>
                <td><input type="text" class="easyui-combotree" id="search_type<%=token %>" style="width:100px;"   /></td>
                <td>状态</td>
                <td><input type="text" class="easyui-combotree"  id="search_status<%=token %>" style="width:100px;"   /></td>
                <td>内容</td>
                <td><input type="text" id="search_content<%=token%>" style="width:100px;"  /></td>
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

    <div>
        <table id="CustomerMoneyLogQueryTable<%=token%>"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('queryWindow<%=token%>')">取消</a>
    </div>

</div>
</body>
</html>

