<%--
  JIRA: HOPEWEALTH-1276
  呼叫客户电话确认界面
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
    String phoneNumber = request.getParameter("phoneNumber");
    String phoneNumberMask = phoneNumber;
    String hiddenStatus = request.getParameter("hidden");
    if (hiddenStatus != null && hiddenStatus.equals("true")) {
        phoneNumberMask = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <table border="0" cellspacing="5" cellpadding="0">
            <tr>
                <td align="right" hidden="true" >呼叫电话</td>
                <td><input editable="false" hidden="true" class="easyui-validatebox" type="text" value="<%=phoneNumber%>" id="phoneNumber<%=token %>" name="callCenter.phoneNumber" required="true" style=" width:200px;"/></td>
            </tr>
            <tr>
                <td align="right" >呼叫电话</td>
                <td><input disabled="true" editable="false" class="easyui-validatebox" type="text" value="<%=phoneNumberMask%>" id="phoneNumberMask<%=token %>" name="callCenter.phoneNumberMask" required="true" style=" width:200px;"/></td>
            </tr>
            <tr>
                <td align="right">座席用户名</td>
                <td><input class="easyui-validatebox" type="text" id="loginName<%=token%>" name="callCenter.loginName" style="width:200px"/></td>

            </tr>
            <tr>
                <td align="right">密码</td>
                <td><input class="easyui-validatebox" type="password" id="password<%=token %>" name="callCenter.password" style="width:200px"/></td>
            </tr>
        </table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnDialSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('DialWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
