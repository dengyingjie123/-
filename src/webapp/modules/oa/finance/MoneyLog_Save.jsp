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
        <form id="formMoneyLog<%=token %>" name="formMoneyLog" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">部门</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="departmentId<%=token %>" name="moneyLog.departmentId"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="moneyLog.name"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">收支</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="inOrOut<%=token %>" name="moneyLog.inOrOut"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
                
                <tr>
                    <td align="right">类型</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="type<%=token %>" name="moneyLog.type"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
                
                <tr>
                    <td align="right">金额</td>
                    <td><input class="easyui-validatebox" type="text" id="money<%=token %>" name="moneyLog.money"  required="true" missingmessage="必须填写"  validType="intOrFloat" style="width:200px"/></td>
                </tr>
                
                <tr>
                    <td align="right">备注</td>
                    <td><input  type="text" id="comment<%=token %>" name="moneyLog.comment" style="width:200px"/></td>
                </tr>
                
                <tr>
                    <td align="right">账务时间</td>
                    <td> <input  class="easyui-datetimebox" type="text" id="moneyTime<%=token %>" editable="false" name="moneyLog.moneyTime" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
                
                <tr>
                    <td align="right">操作人</td>
                    <td><input type="text" id="operatorName<%=token %>" name="moneyLog.operatorName"  readonly="readonly" style="width:200px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="moneyLog.operatorId" style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="moneyLog.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="moneyLog.id"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="moneyLog.operateTime"  style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="moneyLog.state" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnMoneyLogSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('MoneyLogWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>