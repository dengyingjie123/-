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
        <form id="formCustomerWithdraw<%=token %>" name="formCustomerWithdraw" action="" method="post">
            <table align="center" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">客户名称</td>
                    <td><input class="easyui-validatebox"  type="text" id="customerName<%=token %>" name="customerWithdraw.customerName" readonly="readonly" required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td><a href="javascript:void(0)" id="btnCheckCustomer<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                </tr>

                <tr>
                    <td align="right">金额</td>
                    <td><input class="easyui-validatebox" type="text"  validType="FloatOrCurrency"  id="money<%=token %>" name="customerWithdraw.money"  required="true" missingmessage="必须填写" validType="intOrFloat" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">时间</td>
                    <td><input  class="easyui-datetimebox" type="text" id="time<%=token %>" editable="false" name="customerWithdraw.time" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">客户IP</td>
                    <td><input class="easyui-validatebox"  editable="false" type="text" id="customerIP<%=token %>" name="customerWithdraw.customerIP" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">费率</td>
                    <td><input class="easyui-validatebox" type="text" id="rate<%=token %>"  name="customerWithdraw.rate"  required="true" missingmessage="必须填写"  validType="intOrFloat" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">提现手续费</td>
                    <td><input class="easyui-validatebox" type="text" id="fee<%=token %>" validType="FloatOrCurrency" name="customerWithdraw.fee" required="true" missingmessage="必须填写" validType="intOrFloat" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">修改后的提现手续费</td>
                    <td><input class="easyui-validatebox" type="text" id="feeModifed<%=token %>" validType="FloatOrCurrency" name="customerWithdraw.feeModifed" required="true" missingmessage="必须填写" validType="intOrFloat" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">实际到账金额</td>
                    <td><input class="easyui-validatebox" type="text" id="moneyTransfer<%=token %>" validType="FloatOrCurrency" name="customerWithdraw.moneyTransfer" required="true" missingmessage="必须填写" validType="intOrFloat" style="width:200px"/></td>
                </tr>


                <%--<tr>--%>
                    <%--<td align="right">操作人</td>--%>
                    <%--<td><input type="text" id="operatorName<%=token %>" name="customerWithdraw.operatorName" disabled="disabled" style="width:200px"/></td>--%>
                <%--</tr>--%>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="customerWithdraw.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerWithdraw.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="customerWithdraw.state" style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="customerWithdraw.operatorId" style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="customerWithdraw.operateTime"  style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="customerWithdraw.customerId"  style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerWithdrawSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerWithdrawWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>