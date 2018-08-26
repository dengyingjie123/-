
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <form id="formCustomerSmsSending<%=token %>" name="formCustomerSmsSending" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">客户名称</td>
                    <td><textarea id="receiverName<%=token %>" name="sms.receiverName"  rows="5"  style="width:600px" readonly="true"/></td>
                </tr>
                <tr>
                    <td align="right">发送主题</td>
                    <td><input type="text" id="subject<%=token%>" name="sms.subject" required="true" missingmessage="主题不能为空" style="width:600px" /></td>
                </tr>
                <tr>
                    <td align="right">发送内容</td>
                    <td><textarea  id="content<%=token %>" name="sms.content"  required="true" missingmessage="内容不能为空" rows="5" style="width:600px"/></td>
                </tr>
                <tr>
                    <td align="right">发送时间</td>
                   <td><input class="easyui-datetimebox" type="text"  id="waitingTime<%=token%>" name="sms.waitingTime" required="true" missingmessage="内容不能为空" style="width:200px" /></td>
                </tr>
            </table>
            <input type="hidden" id="id<%=token %>" name="sms.id"    style="width:200px"/>
            <input type="hidden" id = "receiverIds<%=token%>" name = "sms.receiverIds" style="width:200px" />
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerFeedbackSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >发送</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerSmsSendingWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
