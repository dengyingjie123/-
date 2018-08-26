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
        <form id="formCustomerFeedback<%=token %>" name="formCustomerFeedback" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
            	<tr>
                    <td align="right">客户名称</td>
                    <td><input  type="text"  id="cname<%=token %>" name="customerFeedback.customerName"    style="width:320px"/></td>
                </tr>
                <tr>
                    <td align="right">日志类型</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="typeId<%=token %>" name="customerFeedback.typeId"  required="true" missingmessage="必须填写"  style="width:320px"/></td>
                </tr>
                
                <tr>
                    <td align="right" valign="top">日志内容</td>
                    <td><textarea name="customerFeedback.content" rows="5" class="easyui-validatebox" id="content<%=token %>" style="width:320px" required="true" missingmessage="必须填写"></textarea></td>
                </tr>
                
                <tr>
                    <td align="right">日志时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="time<%=token %>" name="customerFeedback.time"  required="true" missingmessage="必须填写"  style="width:320px"/></td>
                </tr>
                
                <tr>
                    <td align="right">理财师</td>
                    <td><input  type="text" id="feedbackManName<%=token %>" name="customerFeedback.feedbackManName" required="true" missingmessage="必须填写" readonly="readonly" style="width:320px"/></td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="customerFeedback.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerFeedback.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="customerFeedback.state"    style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="customerFeedback.operatorId"  style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="customerFeedback.operateTime"    style="width:200px"/>
            <input  type="hidden" id="feedbackManId<%=token %>" name="customerFeedback.feedbackManId" style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="customerFeedback.customerId" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerFeedbackSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerFeedbackWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>