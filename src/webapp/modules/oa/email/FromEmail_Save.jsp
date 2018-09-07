<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/5
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
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
        <form id="formFromEmail<%=token %>" name="formFromEmail" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">发送人</td>
                    <td><input type="text" readonly="readonly" id="fromName<%=token %>" name="fromEmail.fromName" style="width:200px"/></td>

                    <td align="right">发送人邮件</td>
                    <td><input type="text" readonly="readonly" id="fromEmail<%=token %>" name="fromEmail.fromEmail" style="width:200px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">收件人</td>
                    <td><input type="text" id="toName<%=token %>" name="fromEmail.toName" style="width:200px"/></td>

                    <td align="right">收件人邮件</td>
                    <td><input type="text" class="easyui-validatebox" data-options="required:true,validType:'email'" id="toEmail<%=token %>" name="fromEmail.toEmail" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">标题</td>
                    <td colspan="3"><input type="text" class="easyui-validatebox" data-options="required:true" id="emaioTitle<%=token %>" name="fromEmail.emaioTitle"
                                           style="width:400px"/></td>
                </tr>
                <tr>
                    <td align="right">发送时间</td>
                    <td colspan="3"><input readonly="readonly" type="text" id="toTime<%=token %>" name="fromEmail.toTime"
                                           style="width:400px"/></td>
                </tr>
                <tr>
                    <td align="right">上传附件</td>
                    <td colspan="3"><input type="file" id="attachment<%=token %>" name="attachmentPath"
                                           style="width:400px"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top">内容</td>
                    <td colspan="3"><textarea id="emailContent<%=token %>" name="fromEmail.emailContent"
                                              style="width:400px;height: 300px;resize:none;"></textarea></td>
                </tr>

            </table>

            <input type="hidden" id="sid<%=token %>" name="fromEmail.sid" style="width:200px"/>
            <input type="hidden" id="emailId<%=token %>" name="fromEmail.emailId" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="fromEmail.id" style="width:200px"/>
            <input type="hidden" id="state<%=token %>" name="fromEmail.state" style="width:200px"/>
            <input type="hidden" id="operatorId<%=token %>" name="fromEmail.operatorId" style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="fromEmail.operateTime" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnFromEmailSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
        href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('FromEmailWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
