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
        <form id="formcustomerPayment<%=token %>" name="formcustomerPayment" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">



                <tr>
                    <td align="right">操作员姓名</td>
                    <td><input  type="text" id="operatorName<%=token %>" name="customerPayment.operatorName"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">操作时间</td>
                    <td><input  type="text" id="operateTime<%=token %>" name="customerPayment.operateTime"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">金额</td>
                    <td><input  type="text" id="money<%=token %>" name="customerPayment.money"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">费率</td>
                    <td><input  type="text" id="feeRate<%=token %>" name="customerPayment.feeRate"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">方式</td>
                    <td><input  type="text" id="typeName<%=token %>" name="customerPayment.typeName"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">状态</td>
                    <td><input  type="text" id="statusName<%=token %>" name="customerPayment.statusName"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">添加时间</td>
                    <td><input  type="text" id="time<%=token %>" name="customerPayment.time"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">IP</td>
                    <td><input  type="text" id="iP<%=token %>" name="customerPayment.iP"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">传输业务编号</td>
                    <td><input  type="text" id="bizId<%=token %>" name="customerPayment.bizId"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">支付银行</td>
                    <td><input  type="text" id="paymentAccount<%=token %>" name="customerPayment.paymentAccount"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">支付方式</td>
                    <td><input  type="text" id="paymentTypeName<%=token %>" name="customerPayment.paymentTypeName"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">凭证信息</td>
                    <td><input  type="text" id="paymentInfo<%=token %>" name="customerPayment.paymentInfo"    style="width:200px"/></td>
                </tr>

            </table>
            <input  type="hidden" id="sid<%=token %>" name="contract.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="contract.id"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btncustomerPaymentSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerPaymentWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>