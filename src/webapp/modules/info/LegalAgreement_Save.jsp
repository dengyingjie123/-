<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/18/14
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" import="com.youngbook.common.config.*" %>
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
        <form id="formAgreement<%=token %>" name="formAgreement" action="" method="post">

            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">协议名称</td>
                    <td><input  type="text" id="name<%=token %>" class="easyui-validatebox" name="legalAgreement.name" required="true" missingmessage="必须填写"   style="width:700px" /></td>
                </tr>
                <tr>
                    <td align="right" >开始时间</td>
                    <td><input type="text" id="startTime<%=token %>"  class="easyui-datetimebox" name="legalAgreement.startTime" editable="false" required="true" missingmessage="必须填写" style="width:700px"/></td>
                </tr>
                <tr>
                    <td align="right" >结束时间</td>
                    <td><input type="text" id="endTime<%=token %>"  class="easyui-datetimebox" name="legalAgreement.endTime" editable="false" required="true" missingmessage="必须填写" style="width:700px"/></td>
                </tr>
                <tr>
                    <td align="right" style="vertical-align:top;">协议内容</td>
                    <td><textarea  id="name<%=token %>" name="legalAgreement.content" style="width:700px;height:300px;" ></textarea></td>
                </tr>
            </table>
            <input type="hidden" id="operateTime<%=token %>"  class="easyui-validatebox"   name="legalAgreement.operateTime" missingmessage="必须填写" style="width:600px"/>
            <input type="hidden" id="operatorId<%=token %>" class="easyui-validatebox" name="legalAgreement.operatorId"  style="width:570px" required="true" missingmessage="必须填写"/>
            <input  type="hidden" id="sid<%=token %>" name="legalAgreement.sid" style="width:570px"/>
            <input  type="hidden" id="id<%=token %>" name="legalAgreement.id" style="width:570px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnAgreementSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('AgreementWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>