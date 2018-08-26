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
<div class="easyui-layout" fit="true">
	<div region="center" border="false" style="padding:10px;background:#fff">
    <table id="CustomerAccountTable<%=token%>"></table>
    </div>
	<div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelectedCustomerAccount<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >选择</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('customerAccountSelectWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>