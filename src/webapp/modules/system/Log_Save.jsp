<%--

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>标题</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formLogPO<%=token %>" name="formLogPO" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">时间</td>
                    <td><input type="text" class="eastui-validatebox" name="operateTime" id="operateTime<%=token%>5"  style="width:500px" /></td>
                </tr>
                <tr>
                    <td align="right">操作人</td>
                    <td><input type="text" class="eastui-validatebox" name="operatorName" id="operatorName<%=token%>5"  style="width:200px" /><input type="text" class="eastui-validatebox" name="operatorId" id="operatorId<%=token%>5"  style="width:250px" /></td>
                </tr>
                <tr>
                    <td align="right">名称</td>
                    <td><input type="text" class="eastui-validatebox" name="name" id="name<%=token%>"  style="width:500px" /></td>
                </tr>
                <tr>
                    <td align="right">People Message</td>
                    <td>
                        <textarea id="peopleMessage<%=token%>" name="peopleMessage" style="width:500px;height:100px;"></textarea>
                    </td>
                </tr>
                <tr>
                    <td align="right">Machine Message</td>
                    <td>
                        <textarea id="machineMessage<%=token%>" name="machineMessage" style="width:500px;height:100px;"></textarea>
                    </td>
                </tr>
                <tr>
                    <td align="right">URL</td>
                    <td><textarea id="url<%=token%>" name="url" style="width:500px;height:50px;"></textarea></td>
                </tr>
                <tr>
                    <td align="right">参数</td>
                    <td><textarea id="parameters<%=token%>" name="parameters" style="width:500px;height:50px;"></textarea></td>
                </tr>
                <tr>
                    <td align="right">IP</td>
                    <td><input type="text" class="eastui-validatebox" name="logVO.IP" id="IP<%=token%>"  style="width:500px" /></td>
                </tr>
            </table>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('LogWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>