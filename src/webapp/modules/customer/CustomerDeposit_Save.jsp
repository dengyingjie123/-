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
        <form id="formCustomerDeposit<%=token %>" name="formCustomerDeposit" action="" method="post">
            <table align="center" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">客户名称</td>
                    <td colspan="2"><input class="easyui-validatebox" editable="false" type="text" id="customerName<%=token %>" name="customerDepositVO.customerName"  readonly="readonly" required="true"  missingmessage="必须填写"  style="width:200px"/></td>
                    <td><a href="javascript:void(0)" id="btnCheckCustomer<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                </tr>

                <tr>
                    <td align="right">金额</td>
                    <td><input class="easyui-validatebox" editable="false" type="text" id="money<%=token %>" validType="FloatOrCurrency"  name="customerDeposit.money"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">费率</td>
                    <td><input class="easyui-validatebox" editable="false" type="text" id="rate<%=token %>" name="customerDeposit.rate"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">时间</td>
                    <td><input class="easyui-datetimebox" type="text" id="time<%=token %>" name="customerDeposit.time" editable="false" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">客户IP</td>
                    <td><input class="easyui-validatebox" type="text" id="customerIP<%=token %>" name="customerDeposit.customerIP"  required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">充值手续费</td>
                    <td><input class="easyui-validatebox" type="text" editable="false" validType="FloatOrCurrency" id="fee<%=token %>" name="customerDeposit.fee" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">实际到账金额</td>
                    <td> <input class="easyui-validatebox" editable="false" validType="FloatOrCurrency" type="text" id="moneyTransfer<%=token %>" editable="false" name="customerDeposit.moneyTransfer" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">充值来源客户</td>
                    <td><input class="easyui-validatebox"   type="text" id="fromAccountId<%=token %>" name="customerDeposit.fromAccountId"  readonly="readonly" style="width:200px"/></td>
                </tr>

                <%--<tr>--%>
                <%--<td align="right">操作人</td>--%>
                <%--<td><input class="easyui-validatebox" type="text" id="operatorName<%=token %>" name="customerDepositVO.operatorName"  readonly="readonly" style="width:200px"/></td>--%>
                <%--</tr>--%>
            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="customerDeposit.operatorId" style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="customerDeposit.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerDeposit.id"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="customerDeposit.operateTime"  style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="customerDeposit.state" style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="customerDeposit.customerId" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerDepositSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerDepositWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>