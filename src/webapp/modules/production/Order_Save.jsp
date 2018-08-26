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
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">订单号</td>
                    <td><input class="easyui-validatebox" readonly="true" type="text" id="orderNum<%=token %>" name="order.orderNum" editable="false" style="width:245px"/></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">客户</td>
                    <td><input type="text" readonly="true"  class="easyui-validatebox"  id="customerName<%=token %>" name="order.customerName" required="true" style="width:245px"/></td>
                    <td align="right">客户账号</td>
                    <td><input type="text" readonly="true"  class="easyui-validatebox"  id="accountDisplayName<%=token %>" name="accountDisplayName" style="width:245px"/></td>
                </tr>
                <tr>
                    <td align="right">产品名</td>
                    <td style="width: 200px"><input id="production<%=token %>" class="easyui-validatebox" name="order.productionName" editable="false" required="true"  style="width:245px"/></td>
                    <td align="right">产品构成</td>
                    <td><input class="easyui-combotree"  id="productionCompositionId<%=token %>" name="order.productionCompositionId" required="true"  editable="false" style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">合同</td>
                    <td>
                        <input class="easyui-combotree"  id="contractNo<%=token %>" name="order.contractNo" editable="false" style="width:250px"/>
                    </td>
                    <td align="right">推荐码</td>
                    <td><input class="easyui-combotree" type="text" id="referralCode<%=token %>" name="order.referralCode" editable="true" style="width:150px"/></td>
                </tr>
                <tr>
                    <td align="right">预约时间</td>
                    <td><input class="easyui-datebox" id="appointmentTime<%=token %>" data-options="editable:false" name="order.appointmentTime" style="width:150px" /></td>
                    <td align="right">到账时间</td>
                    <td><input class="easyui-datebox" id="payTime<%=token %>" data-options="editable:false" name="order.payTime" style="width:150px"/></td>
                </tr>
                <tr id="tr_pay<%=token%>">
                    <td align="right">操作名称</td>
                    <td><input class="easyui-combotree"  id="status<%=token %>" name="order.status" data-options="required:true"  editable="false" missingmessage="必须填写" style="width:250px"/>
                        <input type="hidden" id="statusNumber<%=token %>" name="statusNumber" readonly="readonly" style="width:250px"/></td>
                    <td align="right">操作金额</td>
                    <td><input class="easyui-validatebox" validType="FloatOrCurrency" data-options="required:true" type="text" id="operationMoney<%=token %>" name="operationMoney" missingmessage="必须填写" editable="true" style="width:205px"/>
                        &nbsp;&nbsp;元
                    </td>
                </tr>
                <tr>
                    <td align="right">财务信息确认人</td>
                    <td>
                        <input class="easyui-validatebox" readonly="true" type="text" id="orderConfirmUserName01<%=token %>" name="order.orderConfirmUserName01" editable="false" style="width:245px"/>
                        <input type="hidden" id="orderConfirmUserId01<%=token %>" name="order.orderConfirmUserId01"/></td>
                    </td>
                    <td align="right">财务信息确认时间</td>
                    <td><input class="easyui-datetimebox" id="orderConfirmUserTime01<%=token %>" data-options="editable:false" name="order.orderConfirmUserTime01" style="width:150px"/></td>
                </tr>
                <tr>
                    <td align="right">财务信息复核人</td>
                    <td>
                        <input class="easyui-validatebox" readonly="true" type="text" id="orderConfirmUserName02<%=token %>" name="order.orderConfirmUserName02" editable="false" style="width:245px"/>
                        <input type="hidden" id="orderConfirmUserId02<%=token %>" name="order.orderConfirmUserId02"/></td>
                    </td>
                    <td align="right">财务信息复核时间</td>
                    <td><input class="easyui-datetimebox" id="orderConfirmUserTime02<%=token %>" data-options="editable:false" name="order.orderConfirmUserTime02" style="width:150px"/></td>
                </tr>
                <tr>
                    <td align="right">支付渠道</td>
                    <td><input class="easyui-combotree" id="payChannel<%=token %>" name="order.payChannel" editable="false" style="width:250px"/></td>
                    <td align="right"></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">描述</td>
                    <td colspan="3"><textarea  type="text"id="description<%=token %>" name="order.description" style="width:630px;height: 50px;resize: none"></textarea></td>
                </tr>
            </table>
            <div style="color: red"></div>
            <!-- 支付渠道 -->

            <input type="hidden" id="loginName<%=token %>" name="loginName"/>
            <input  type="hidden" id="ID<%=token %>" name="order.id" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="SID<%=token %>" name="order.sid" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="createTime<%=token %>" name="order.createTime"  style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="order.customerId" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="productionId<%=token %>" name="order.productionId" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="attribute<%=token %>" name="order.customerAttribute"  style="width:200px"/>
            <input  type="hidden" id="accountId<%=token %>" name="order.accountId"  style="width:200px"/>
            <input  type="hidden" id="bankCode<%=token %>" name="order.bankCode"  style="width:200px"/>
            <input  type="hidden" id="bankCard<%=token %>" name="order.bankCard"  style="width:200px"/>
            <input  type="hidden" id="salemanId<%=token %>" name="order.salemanId"  style="width:200px"/>
            <input  type="hidden" id="applyDrawbackTime<%=token %>" name="order.applyDrawbackTime"  style="width:200px"/>
            <input  type="hidden" id="cancelTime<%=token %>" name="order.cancelTime"  style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnOrderSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('OrderWindow<%=token%>')" id="btnOrderCancel<%=token %>">取消</a>
    </div>
</div>
</body>
</html>