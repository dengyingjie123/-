<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formKV<%=token %>" name="formKV" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">K 键</td>
                    <td><input class="easyui-validatebox" data-options="required:true" type="text" id="K<%=token %>" name="kv.K"  required="true" missingmessage="必须填写" style="width:400px"/></td>
                </tr>

                <tr>
                    <td align="right">V 值</td>
                    <td><textarea id="V<%=token %>" name="kv.V" style="width: 400px;height: 50px;"></textarea></td>
                </tr>

                <tr>
                    <td align="right">Parameter</td>
                    <td><textarea id="parameter<%=token %>" name="kv.parameter" style="width: 400px;height: 50px;"></textarea></td>
                </tr>

                <tr>
                    <td align="right">组名</td>
                    <td><input class="easyui-validate"  type="text" id="GroupNameForm<%=token %>" name="kv.GroupName"  required="true" missingmessage="必须填写" editable="true" style="width:400px"/></td>
                </tr>

                <tr>
                    <td align="right">排序</td>
                    <td><input class="easyui-validatebox"  type="text" id="Orders<%=token %>" name="kv.Orders"  required="true" missingmessage="必须填写" validType="integer" editable="true" style="width:400px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="ID<%=token %>" name="kv.ID" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="kv.sid" readonly="true"   style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSubmitKV<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('KVWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>