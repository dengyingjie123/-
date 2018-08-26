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
        <form id="formMessageSubmit<%=token %>" name="formMessageSubmit" action="" method="post">
            <table width="100%" border="0" cellspacing="10" cellpadding="0">
                <tr>
                    <td align="right">发送者</td>
                    <td><input  type="text" id="SenderId<%=token %>" name="message.SenderId"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                    <td align="right">主题</td>
                    <td><input type="text" id="Subject<%=token%>" name="message.Subject" style="width:200px" /></td>
                </tr>

                <tr>
                    <td align="right">接收者</td>
                    <td colspan="2"><input  type="text" id="ReceiverId<%=token %>" name="message.ReceiverId"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                    <td><a class="easyui-linkbutton" id="btnReceiverIdSelect<%=token%>" iconCls="icon-search">选择</a></td>
                </tr>

                <tr>
                    <td align="right">类型</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="Type<%=token %>" name="message.Type"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                    <td align="right">优先级</td>
                    <td><input class="easyui-combotree" editable="false" type="text" id="Priority<%=token%>" name="message.Priority" required="true" missingmessage="必须填写" style="width:200px" /></td>
                </tr>
                <tr>
                    <td align="right">内容</td>
                   <td colspan="4"><textarea style="width:447px;height:238px" id="Content" name="message.Content"/></td>
                </tr>
            </table>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnMessageSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >提交</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('MessageWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>