<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formPaymentPlan<%=token %>" name="formPaymentPlan" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">兑付日期</td>
                    <td><input type="text" class="easyui-datetimebox" id="paymentTime<%=token %>"  name="paymentPlan.paymentTime" style="width:200px"  editable="true" /></td>
                    <td align="right">兑付状态</td>
                  <td><input class="easyui-combotree" type="text" editable="true" id="statusName<%=token %>" name="paymentPlan.status" style="width:200px" /></td>
                </tr>
                <tr>
                    <td align="right">兑付总期数</td>
                    <td><input  type="text" id="comment<%=token %>"  name="paymentPlan.totalInstallment"
                               style="width:200px"  /></td>
                    <td align="right">当前兑付期数</td>
                    <td><input  type="text" id="currentInstallment<%=token %>"  name="paymentPlan.currentInstallment"
                               style="width:200px" /></td>
                </tr>
                <tr>
                    <td align="right">应兑付总金额</td>
                    <td><input  type="text" id="totalPaymentMoney<%=token %>" name="paymentPlan.totalPaymentMoney" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',width:'200px'"/></td>
                    <td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td align="right">应兑付本金总金额</td>
                    <td><input  type="text" id="totalPaymentPrincipalMoney<%=token %>" name="paymentPlan.totalPaymentPrincipalMoney" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',width:'200px'"/></td>
                    <td align="right">应兑付收益总金额</td>
                  	<td><input type="text" id="totalProfitMoney<%=token %>" name="paymentPlan.totalProfitMoney" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',width:'200px'"/></td>
                </tr>
                <tr>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                    <td align="right">已兑付本金金额</td>
                    <td><input  type="text" id="paiedPrincipalMoney<%=token %>" name="paymentPlan.paiedPrincipalMoney" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',width:'200px'"/></td>
                    <td align="right">已兑付收益金额</td>
                  <td><input  type="text" id="paiedProfitMoney<%=token %>" name="paymentPlan.paiedProfitMoney"   class="easyui-numberbox" data-options="precision:2,groupSeparator:',',width:'200px'"/></td>
                </tr>
                <tr>
                	<td align="right">实际兑付时间</td>
                  <td><input type="text" class="easyui-datetimebox" id="paiedPaymentTime<%=token %>"  name="paymentPlan.paiedPaymentTime" style="width:200px"  editable="true" /></td>
                    <td align="right">备注</td>
                    <td><input  type="text" id="comment<%=token %>" name="paymentPlan.comment" style="width:200px" /></td>
                </tr>
            </table>
            <input type="hidden" id="loginName<%=token %>" name="loginName" style="width:200px"/>
			<input type="hidden" id="paymentMoney<%=token %>" class="easyui-validatebox" validType="FloatOrCurrency"  missingmessage="必须填写" name="paymentPlan.paymentMoney" style="width:200px" />
          	<input type="hidden" id="type<%=token %>" name="paymentPlan.type" readonly="true"  style="width:200px"/>
   	    	<input type="hidden" id="operatorId<%=token %>" name="paymentPlan.operatorId" style="width:200px"/>
            <input type="hidden" id="sid<%=token %>" name="paymentPlan.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="paymentPlan.id" style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="paymentPlan.operateTime" style="width:200px"/>
            <input type="hidden" id="state<%=token %>" name="paymentPlan.state" style="width:200px"/>
            <input type="hidden" id="orderId<%=token %>" name="paymentPlan.orderId" />
            <input type="hidden" id="productId<%=token %>" name="paymentPlan.productId" />
            <input type="hidden" id="productCompositionId<%=token %>" name="paymentPlan.productCompositionId" />
            <input type="hidden" id="customerId<%=token %>" name="paymentPlan.customerId" />
            <input type="hidden" id="confirmorId<%=token %>" name="paymentPlan.confirmorId" />
            <input type="hidden" id="confirmTime<%=token %>" name="paymentPlan.confirmTime" />
            <input type="hidden" id="auditExecutorId<%=token %>" name="paymentPlan.auditExecutorId" />
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnPaymentPlanSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('PaymentPlanWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>