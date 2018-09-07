<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 15-4-2
  Time: 下午4:09
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
        <form id="formFinanceExpenseDetail<%=token %>" name="formFinanceExpenseDetail" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">项目名称</td>
                    <td><input  type="text" class="easyui-validatebox" id="itemName<%=token %>" data-options="required:true" name="expenseDetail.itemName" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">项目时间</td>
                    <td><input  type="text" editable="false"  data-options="required:true" class="easyui-datebox" id="itemTime<%=token %>" name="expenseDetail.itemTime" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">金额</td>
                    <td><input  class="easyui-validatebox" data-options="required:true" type="text" id="money<%=token %>" name="expenseDetail.money"  validType="currency" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">用途</td>
                    <td><textarea id="purpose<%=token %>" name="expenseDetail.purpose" style="width:200px;height: 40px"/></td>
                </tr>
                <tr>
                    <td align="right">备注</td>
                    <td><textarea id="comment<%=token %>" name="expenseDetail.comment" style="width:200px;height: 40px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="expenseId<%=token+1 %>" name="expenseDetail.expenseId" style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="expenseDetail.operatorId" style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="expenseDetail.sid" style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="expenseDetail.id" style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="expenseDetail.operateTime" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnFinanceExpenseDetailSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('FinanceExpenseDetailWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>