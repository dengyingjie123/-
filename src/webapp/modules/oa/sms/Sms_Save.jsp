<%--
  Created by IntelliJ IDEA.
  User: Jepson
  Date: 2015/7/3
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
    <title>Untitled Document</title>
</head>
<body>

<div class='easyui-layout' fit='true'>
    <div region='center' border='false' style='padding:10px;background:#fff;border:0px solid #ccc;'>
        <form id='formSms<%=token %>' name='formSms' action='' method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>

                <tr>
                    <td align="right">主题</td>
                    <td colspan="3"><input class='easyui-validatebox'  required="true" missingmessage="必须填写" type="text" id="subject<%=token %>" name="sms.subject"       style="width:500px"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top">内容</td>
                    <td colspan="3">
                        <textarea  type="text" class='easyui-validatebox' id="content<%=token %>" name="sms.content" required="true" missingmessage="必须填写"  style="width:500px;height:100px"/>
                </tr>
                <tr>
                    <td align="right">接收者名称</td>
                    <td><input  type="text" class='easyui-validatebox'  required="true" missingmessage="必须填写" id="receiverName<%=token %>" name="sms.receiverName"       style="width:200px"/></td>

                    <td align="right">类型</td>
                    <td><input   type="text"  class="easyui-combobox" id="type<%=token %>" name="sms.type"  required="true" missingmessage="必须填写"      style="width:200px"/></td>

               </tr>
                <tr>
                    <td align="right">接收者手机</td>
                    <td><input  type="text" class='easyui-validatebox' readonly="readonly" id="receiverMobile<%=token %>" name="sms.receiverMobile"       style="width:200px"/></td>
                    <td align="right">接收时间</td>
                    <td><input  type="text" class='easyui-datetimebox' id="receiveTime<%=token %>" editable="false" name="sms.receiveTime"     style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">发送时间</td>
                    <td><input  type="text" class='easyui-datetimebox' id="sendTime<%=token %>" name="sms.sendTime"   editable="false"   style="width:200px"/></td>
                    <td align="right">等待发送时间</td>
                    <td><input class='easyui-datetimebox' type="text" id="waitingTime<%=token %>" name="sms.waitingTime"   editable="false"    style="width:200px"/></td>

                </tr>


            </table>
            <input  type='hidden' id='sid<%=token %>' name='sms.sid' style='width:200px'/>
            <input  type='hidden' id='id<%=token %>' name='sms.id' style='width:200px'/>
            <input  type='hidden' id='state<%=token %>' name='sms.state' style='width:200px'/>
            <input  type='hidden' id='operatorId<%=token %>' name='sms.operatorId' style='width:200px'/>
            <input  type='hidden' id='senderId<%=token %>' name='sms.senderId' style='width:200px'/>
            <input  type='hidden' id='senderName<%=token %>' name='sms.senderName' style='width:200px'/>
            <input  type='hidden' id='receiverId<%=token %>' name='sms.receiverId' style='width:200px'/>
        </form>
    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnSmsSubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok' href='javascript:void(0)'>确定</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)' onClick="fwCloseWindow('smsWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>