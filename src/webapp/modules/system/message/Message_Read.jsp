<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/17/14
  Time: 9:51 AM
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formArticle<%=token %>" name="formArticle" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td width="60" align="right">标题</td>
                    <td><input  type="text" id="title<%=token %>" disabled="disabled" class="easyui-validatebox" name="message.title"  required="true" missingmessage="必须填写"  style="width:690px"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top">内容</td>
                    <td colspan="3"><textarea id="content<%=token%>" disabled="disabled" name="message.content" style="width:600px;height:400px"></textarea></td>
                </tr>

            </table>
            <input  type="hidden" id="sid<%=token %>" name="message.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="message.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="message.state" style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="message.operatorId" style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="message.operateTime"  style="width:200px"/>
            <input  type="hidden" id="uIds<%=token %>" name="uIdsJSON" style="width:200px"/>
            <input  type="hidden" id="type<%=token %>" name="message.type" style="width:200px"/>
            <input  type="hidden" id="status<%=token %>" name="message.status" style="width:200px"/>
            <input  type="hidden" id="sendRange<%=token %>" name="message.sendRange" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnMessageReadSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >回执</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ArticleWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>