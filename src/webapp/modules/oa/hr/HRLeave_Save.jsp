<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/26
  Time: 17:45
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
        <form id="formHRLeave<%=token %>" name="formHRLeave" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">公司名称：</td>
                    <td><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                               name="hrleaveVO.controlString1" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">部门名称：</td>

                    <td><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                               name="hrleaveVO.controlString2" style="width:300px"/></td>
                </tr>

                <tr>
                    <td align="right">申请人</td>
                    <td><input type="text" readonly="readonly" id="oh_applicationName<%=token %>"
                               name="oh_applicationName" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">请假类别</td>
                    <td><input class="easyui-combotree" editable="false" type="text" id="leaveTypeId<%=token %>"
                               name="hrleave.leaveTypeId" required="true" missingmessage="必须填写" style="width:300px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">其他类别描述</td>
                    <td><textarea type="text" id="otherTypeDescription<%=token %>" name="hrleave.otherTypeDescription"
                                  style="width:300px;height:30px;resize:none;"></textarea></td>
                </tr>
                <tr>
                    <td align="right">天数</td>
                    <td><input class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"  type="text" id="days<%=token %>" name="hrleave.days"
                                style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">请假去处</td>
                    <td><input type="text" id="whereToLeave<%=token %>" name="hrleave.whereToLeave"
                               style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">开始时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="startTime<%=token %>"
                               name="hrleave.startTime" required="true" missingmessage="必须填写" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">结束时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="endTime<%=token %>" name="hrleave.endTime"
                               required="true" missingmessage="必须填写" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">工作交接人</td>
                    <td><input type="text" id="handoverName<%=token %>" name="hrleave.handoverName"
                               style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">请假原因</td>
                    <td><textarea class="easyui-validatebox" type="text" id="reason<%=token %>" name="hrleave.reason"
                                  required="true" missingmessage="必须填写"
                                  style="width:300px;height:30px;resize:none"></textarea>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="hrleave.sid" style="width:300px"/>
            <input type="hidden" id="id<%=token %>" name="hrleave.id" style="width:300px"/>
            <input type="hidden" id="state<%=token %>" name="hrleave.state" style="width:300px"/>
            <input type="hidden" id="operatorId<%=token %>" name="hrleave.operatorId"
                   style="width:300px"/>
            <input type="hidden" id="operateTime<%=token %>" name="hrleave.operateTime"
                   style="width:300px"/>
            <%--申请人编号--%>
            <input type="hidden" id="applicantId<%=token %>" name="hrleave.applicantId" style="width:300px"/>
            <input type="hidden"  id="controlString3<%=token %>"
                   name="hrleaveVO.controlString3" style="width:300px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnHRLeaveSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a id="btnHRLeaveSubmit_start<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">业务审批</a>
        <a id="btnHRLeaveSubmit_applay<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">业务申请</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('HRLeaveWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
