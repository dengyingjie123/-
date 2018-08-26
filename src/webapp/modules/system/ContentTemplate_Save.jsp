<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2015/4/23
  Time: 13:14
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
        <form id="formSmsTemplate<%=token %>" name="formSmsTemplate" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="contentTemplate.name"  required="true" missingmessage="必须填写"  style="width:380px"/></td>
                </tr>
                <tr>
                    <td align="right">类型</td>
                    <td><input class="easyui-combotree" type="text" id="type<%=token %>" name="contentTemplate.type"  required="true" missingmessage="必须填写"  style="width:380px"/></td>
                </tr>
                <tr>
                    <td align="right">模板</td>
                    <td>
                        <textarea type="text" class="easyui-validatebox"  id="template<%=token %>" name="contentTemplate.template"  required="true" missingmessage="必须填写"  style="width:380px;height:100px;"></textarea>
                     </td>
                </tr>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="contentTemplate.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="contentTemplate.id"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSmsTemplateSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('SmsTemplateWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
