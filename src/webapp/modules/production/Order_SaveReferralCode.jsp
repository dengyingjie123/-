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
                    <td align="right">订单号</td>
                    <td><input class="easyui-validatebox" readonly="true" type="text" id="orderNum<%=token %>" name="order.orderNum" editable="false" style="width:245px"/></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">客户</td>
                    <td><input type="text" readonly="true"  class="easyui-validatebox"  id="customerName<%=token %>" name="order.customerName"   required="true"  style="width:245px"/></td>
                    <td align="right">合同</td>
                    <td><input class="easyui-combotree"  id="contractNo<%=token %>" name="order.contractNo" editable="false" style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">产品名</td>
                    <td style="width: 200px"><input id="production<%=token %>" class="easyui-validatebox" name="order.productionName" editable="false" required="true"  style="width:245px"/></td>
                    <td align="right">产品构成</td>
                    <td><input class="easyui-combotree"  id="productionCompositionId<%=token %>" name="order.productionCompositionId" required="true"  editable="false" style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">推荐码</td>
                    <td><input class="easyui-combotree" type="text" id="referralCode<%=token %>" name="order.referralCode" editable="true" style="width:245px"/></td><br>
					<td align="right">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
<input type="hidden" id="loginName<%=token %>" name="loginName"/>
            <input  type="hidden" id="ID<%=token %>" name="order.id" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="SID<%=token %>" name="order.sid" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="createTime<%=token %>" name="order.createTime"  style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="order.customerId" readonly="true"   style="width:200px"/>
            <input  type="hidden"  id="productionId<%=token %>" name="order.productionId" readonly="true"   style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnOrderSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('OrderSaveReferralCodeWindow<%=token%>')" id="btnOrderCancel<%=token %>">取消</a>
    </div>
</div>
</body>
</html>