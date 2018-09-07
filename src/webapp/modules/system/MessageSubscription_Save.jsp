<%--
  Created by IntelliJ IDEA.
  User: Jepson
  Date: 2015/6/25
  Time: 16:24
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
        <form id="formMessageSubscription<%=token %>" name="formMessageSubscription" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">用户编号</td>
                    <td><input  type="text" class="easyui-validatebox" id="userId<%=token %>" name="messageSubscription.userId"    required="true"    style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">消息类型</td>
                    <td><input  class="easyui-combotree"  type="text" id="messageTypeId<%=token %>" name="messageSubscription.messageTypeId" required="true"      style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">邮件提醒</td>
                    <td><input  type="text" id="isEmail<%=token %>" name="messageSubscription.isEmail" class="easyui-combotree"      style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">短信提醒</td>
                    <td><input  type="text" id="isSms<%=token %>" name="messageSubscription.isSms"   class="easyui-combotree"    style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">系统代办提醒</td>
                    <td><input  type="text" id="isTodoList<%=token %>" name="messageSubscription.isTodoList"  class="easyui-combotree"     style="width:200px"/></td>
                </tr>
                <input  type="hidden" id="sid<%=token %>" name="messageSubscription.sid"    style="width:200px"/>
                <input  type="hidden" id="id<%=token %>" name="messageSubscription.id"    style="width:200px"/>
            </table>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnMessageSubscriptionSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('MessageSubscriptionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
