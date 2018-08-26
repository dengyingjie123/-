<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
        <form id="formOrder<%=token %>" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right" width="80">订单号</td>
                    <td><input class="easyui-validatebox" readonly="true" type="text" id="orderNum<%=token %>" name="order.orderNum" editable="false" style="width:245px"/></td>
                </tr>
                <tr>
                    <td align="right">用户名</td>
                    <td><input type="text" readonly="true"  class="easyui-validatebox"  id="loginName<%=token %>" name="loginName" editable="false" required="true"  style="width:245px"/></td>
                </tr>
                <tr>
                    <td align="right">客户</td>
                    <td><input type="text" readonly="true"  class="easyui-validatebox"  id="customerName<%=token %>" name="customerName" editable="false" required="true"  style="width:245px"/></td>
                </tr>
                <tr>
                    <td align="right">产品名</td>
                    <td style="width: 200px"><input id="production<%=token %>" class="easyui-validatebox" name="productionName" editable="false" required="true"  style="width:245px"/></td>
                </tr>
                <tr>
                    <td align="right">产品构成</td>
                    <td><input class="easyui-combotree"  id="productionCompositionId<%=token %>" name="order.productionCompositionId" required="true"  editable="false" style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">购买金额</td>
                    <td><input class="easyui-validatebox" validType="FloatOrCurrency"  data-options="required:true" type="text" id="orderMoney<%=token %>" name="order.money"  required="true" missingmessage="必须填写" editable="true" style="width:205px"/>
                        &nbsp;&nbsp;元
                    </td>
                </tr>
                <tr>
                    <td align="right">状态</td>
                    <td><input class="easyui-combotree" id="status<%=token %>" name="order.status"  required="true" missingmessage="必须填写" editable="false" style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">描述</td>
                    <td><textarea  type="text"id="description<%=token %>" name="order.description" style="width:245px;height: 70px;resize: none"/></td>
                </tr>
                <tr>
                    <td align="right">推荐人</td>
                    <td><input class="easyui-validatebox" readonly="true" type="text" id="referralCode<%=token %>" name="order.referralCode" editable="false" style="width:245px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="ID<%=token %>" name="order.id" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="SID<%=token %>" name="order.sid" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="createTime<%=token %>" name="order.createTime"  style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="order.customerId" readonly="true"   style="width:200px"/>
            <input  type="hidden"  id="productionId<%=token %>" name="order.productionId" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="attribute<%=token %>" name="order.customerAttribute"  style="width:200px"/>
            <input  type="hidden" id="accountId<%=token %>" name="order.accountId"  style="width:200px"/>
            <input  type="hidden" id="salemanId<%=token %>" name="order.salemanId"  style="width:200px"/>
            <input  type="hidden" id="payTime<%=token %>" name="order.payTime"  style="width:200px"/>
            <input  type="hidden" id="applyDrawbackTime<%=token %>" name="order.applyDrawbackTime"  style="width:200px"/>
            <input  type="hidden" id="drawbackTime<%=token %>" name="order.drawbackTime"  style="width:200px"/>
            <input  type="hidden" id="appointmentTime<%=token %>" name="order.appointmentTime"  style="width:200px"/>
            <input  type="hidden" id="cancelTime<%=token %>" name="order.cancelTime"  style="width:200px"/>
            <input type="hidden" id="paybackTime<%=token%>" name="order.paybackTime" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('OrderWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>