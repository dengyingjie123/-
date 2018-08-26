<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/29
  Time: 11:34
  To change this template use File | Settings | File Templates.--%>
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
        <form id="formInformationSubmitted<%=token %>" name="formInformationSubmitted" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">公司名称：</td>
                    <td colspan="3"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                                           name="informationSubmittedVO.controlString1" style="width:450px"/></td>
                </tr>
                <tr>
                    <td align="right">部门名称：</td>

                    <td colspan="3"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                                           name="informationSubmittedVO.controlString2" style="width:450px"/></td>
                </tr>
                <tr>
                    <td align="right">经办部门</td>
                    <td><input type="text" readonly="readonly" id="departmentId<%=token %>" name="departmentName"
                               style="width:200px"/></td>
                    <td align="right">经办人</td>
                    <td><input type="text" readonly="readonly" id="handlingName<%=token %>" name="informationSubmitted.handlingName"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">主送单位</td>
                    <td><input class="easyui-validatebox"  data-options="required:true" type="text" id="mainOrg<%=token %>" required="true" missingmessage="必须填写" name="informationSubmitted.mainOrg"
                               style="width:200px"/></td>
                    <td align="right">抄送单位</td>
                    <td><input class="easyui-validatebox"   type="text" id="otherOrg<%=token %>"  name="informationSubmitted.otherOrg"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">报送事由</td>
                    <td colspan="3"><input type="text"   id="reason<%=token %>" name="informationSubmitted.reason"
                               style="width:500px"/></td>
             </tr>
                <tr >
                    <td align="right">报送内容</td>
                    <td colspan="3"><textarea type="text"   id="content<%=token %>" name="informationSubmitted.content"
                                              style="resize: none;width:500px;height:50px;"></textarea></td>
                </tr>
                <tr>
                    <td align="right">报送时间</td>
                    <td><input type="text" readonly="readonly"   id="submitTime<%=token %>" name="informationSubmitted.submitTime"
                               style="width:200px"/></td>

                    <td align="right">原件移交时间</td>
                    <td><input type="text"   class="easyui-datetimebox" editable="false" id="transferTime<%=token %>" name="informationSubmitted.transferTime"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">原件移交人</td>
                    <td><input type="text"  id="transferOperatorId<%=token %>"
                               name="informationSubmitted.transferOperatorId" style="width:200px"/></td>
                    <td align="right">原件移交接收人</td>
                    <td><input type="text"  id="transferRecipient<%=token %>"
                               name="informationSubmitted.transferRecipient" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">原件归还时间</td>
                    <td><input type="text"  class="easyui-datetimebox"   ditable="false" id="revertTime<%=token %>" name="informationSubmitted.revertTime"
                               style="width:200px"/></td>
                    <td align="right">原件归还人</td>
                    <td><input type="text"  class="easyui-validatebox"   id="revertOperatorId<%=token %>" name="informationSubmitted.revertOperatorId"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">原件归还接收人</td>
                    <td colspan="3"><input type="text" 
                                           class="easyui-validatebox"   id="revertRecipientId<%=token %>"
                               name="informationSubmitted.revertRecipientId" style="width:500px"/></td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="informationSubmitted.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="informationSubmitted.id" style="width:200px"/>
            <input type="hidden" id="state<%=token %>" name="informationSubmitted.state" style="width:200px"/>
            <input type="hidden" id="operatorId<%=token %>" name="informationSubmitted.operatorId"
                   style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="informationSubmitted.operateTime"
                   style="width:200px"/>
            <input type="hidden" id="handlingId<%=token %>" name="informationSubmitted.handlingId"
                                               style="width:200px"/>
            <input type="hidden" id="departments<%=token %>" name="informationSubmitted.department"
                   style="width:200px"/>
            <input type="hidden" id="controlString3<%=token %>"
                   name="informationSubmittedVO.controlString3" style="width:450px"/>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnInformationSubmittedSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a id="btnInformationSubmittedSubmit_start<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">业务审批</a>
        <a id="btnInformationSubmittedSubmit_applay<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">业务申请</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('InformationSubmittedWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>

