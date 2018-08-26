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
<div style="padding:10px; ">
    <table border="0" cellspacing="5" cellpadding="0">
        <tr>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td valign="top">
                <div id="departmentListPanel" class="easyui-tabs" style="width:280px;height:450px">
                    <div id="department<%=token%>" title="机构列表" style="padding:2px;">
                        <div id="departmentOptionPanel" class="easyui-panel" fit="true">
                            <ul id="departmentOption<%=token%>" class="easyui-tree"></ul>
                        </div>
                    </div>
                </div>
            </td>
            <td valign="top">
            	<div id="columnListPanel" class="easyui-tabs" style="width:700px;height:450px">
                    <div id="column<%=token%>" title="栏目信息" style="padding:2px;">
                    	<table id="columnTable<%=token%>"></table>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>
</body>
</html>