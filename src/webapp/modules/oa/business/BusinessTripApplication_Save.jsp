<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/7/14
  Time: 16:25
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
        <form id="formBusinessTripApplication<%=token %>" name="formBusinessTripApplication" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">公司名称：</td>
                    <td colspan="3"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                                           name="businessTripApplicationVO.controlString1" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">部门名称：</td>

                    <td colspan="3"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                                           name="businessTripApplicationVO.controlString2" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">出差人</td>
                    <td><input type="text" class="easyui-validatebox" id="userName<%=token %>"
                               name="businessTripApplication.userName" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">申请时间</td>
                    <td><input type="text" class="easyui-datetimebox" editable="false" id="applicationTime<%=token %>"
                               name="businessTripApplication.applicationTime" style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">计划天数</td>
                    <td><input type="text" id="planFate<%=token %>" name="businessTripApplication.planFate"
                               style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">实际天数</td>
                    <td><input type="text" id="actualFate<%=token %>" name="businessTripApplication.actualFate"
                               style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">费用预算</td>
                    <td><input type="text" class="easyui-validatebox" id="expenseBudge<%=token %>"
                               validType="FloatOrCurrency" name="businessTripApplication.expenseBudge"
                               style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">实际费用</td>
                    <td><input type="text" class="easyui-validatebox" id="expenseActual<%=token %>"
                               validType="FloatOrCurrency" name="businessTripApplication.expenseActual"
                               style="width:300px"/></td>
                </tr>
                <tr>
                    <td align="right">出差人员</td>
                    <td><textarea type="text" id="evections<%=token %>" name="businessTripApplication.evections"
                                  style="width:300px;height:30px;resize:none"></textarea></td>
                </tr>
                <tr>
                    <td align="right">出差原因</td>
                    <td>
                        <textarea class="easyui-validatebox" type="text" id="purpose<%=token %>"
                                  name="businessTripApplication.purpose"
                                  required="true" missingmessage="必须填写"
                                  style="width:300px;height:30px;resize:none"></textarea>
                </tr>
                <tr>
                    <td align="right">出差地点</td>
                    <td>
                        <textarea class="easyui-validatebox" type="text" id="businessAddress<%=token %>"
                                  name="businessTripApplication.businessAddress"
                                  required="true" missingmessage="必须填写"
                                  style="width:300px;height:30px;resize:none"></textarea>
                </tr>
                <tr>
                    <td align="right">经办人签字</td>
                    <td><input type="text" id="operatorSign<%=token %>" name="businessTripApplication.operatorSign"
                               readonly="readonly" style="width:300px"/></td>
                </tr>
            </table>
            <input type="hidden" id="userId<%=token %>" readonly="readonly" name="businessTripApplication.userId"
                   style="width:300px"/>
            <input type="hidden" id="departmentId<%=token %>" name="businessTripApplication.departmentId"
                   style="width:300px"/>
            <input type="hidden" id="sid<%=token %>" name="businessTripApplication.sid" style="width:300px"/>
            <input type="hidden" id="id<%=token %>" name="businessTripApplication.id" style="width:300px"/>
            <input type="hidden" id="state<%=token %>" name="businessTripApplication.state" style="width:300px"/>
            <input type="hidden" id="operatorId<%=token %>" name="businessTripApplication.operatorId"
                   style="width:300px"/>
            <input type="hidden" id="operateTime<%=token %>" name="businessTripApplication.operateTime"
                   style="width:300px"/>
            <input type="hidden" class="easyui-validatebox" id="departmentName<%=token %>"
                   name="businessTripApplication.departmentName" readonly="readonly" style="width:300px"/>
            <input type="hidden"  id="controlString3<%=token %>"
                   name="businessTripApplicationVO.controlString3" style="width:300px"/></td>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnBusinessSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a id="btnBusinessSubmit_start<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">业务审批</a>
        <a id="btnBusinessSubmit_applay<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">业务申请</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('BusinessTripApplicationWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>