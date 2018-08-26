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
    <div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
        <table>
            <tr>
                <td>订单附件资料</td>
                <td><a id="btnUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">上传</a></td>
            </tr>
        </table>
        <br>
        <table id="OrderDetailTable<%=token%>"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnOrderDetailSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('OrderDetailWindow<%=token%>')" id="btnOrderCancel<%=token %>">取消</a>
    </div>
</div>
</body>
</html>