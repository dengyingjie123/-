<%--
  Created by IntelliJ IDEA.
  User: 张舜清
  Date: 7/19/2015
  Time: 1:37 PM
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
        <form id="formCustomerWithdrawVerify<%=token %>" name="formCustomerWithdrawVerify" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">客户用户名</td>
                    <td><input  type="text" id="customerId<%=token %>" name="customerLoginName"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">客户姓名</td>
                    <td><input  type="text" id="customerName<%=token %>" name="customerName"     style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">提现金额</td>
                    <td><input  type="text" id="withdrawMoney<%=token %>" name="customerWithdrawVerify.withdrawMoney"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">审核人一</td>
                    <td><input  type="text" id="examineUserId1<%=token %>" name="customerWithdrawVerify.examineUserId1"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">审核人二</td>
                    <td><input  type="text" id="examineUserId2<%=token %>" name="customerWithdrawVerify.examineUserId2"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">审核人三</td>
                    <td><input  type="text" id="examineUserId3<%=token %>" name="customerWithdrawVerify.examineUserId3"       style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">打款人</td>
                    <td><input  type="text" id="remittanceUserId<%=token %>" name="customerWithdrawVerify.remittanceUserId"       style="width:200px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="customerWithdrawVerify.sid"       style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerWithdrawVerify.id"       style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="customerWithdrawVerify.state"       style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="customerWithdrawVerify.operatorId"       style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="customerWithdrawVerify.operateTime"       style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="customerWithdrawVerify.customerId"       style="width:200px"/>
            <input  type="hidden" id="examineUserId1<%=token %>" name="customerWithdrawVerify.examineUserId1"       style="width:200px"/>
            <input  type="hidden" id="examineUserId2<%=token %>" name="customerWithdrawVerify.examineUserId2"       style="width:200px"/>
            <input  type="hidden" id="examineUserId3<%=token %>" name="customerWithdrawVerify.examineUserId3"       style="width:200px"/>
            <input  type="hidden" id="remittanceUserId<%=token %>" name="customerWithdrawVerify.remittanceUserId"       style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerWithdrawVerifySubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('CustomerWithdrawVerifyWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>