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
        <form id="formCustomerMoney<%=token %>" name="formCustomerMoney" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">客户名称</td>
                    <td><input  type="text" id="customerName<%=token %>" name="customerName"    style="width:250px"/></td>
                </tr>


                <tr>
                    <td align="right">冻结资金</td>
                    <td><input  type="text" id="frozenMoney<%=token %>" validType="FloatOrCurrency" name="customerMoney.frozenMoney" class="easyui-validatebox" validType="number"    style="width:250px"/></td>
                </tr>


                <tr>
                    <td align="right">待收资金</td>
                    <td><input  type="text" id="expectedMoney<%=token %>" validType="FloatOrCurrency" name="customerMoney.expectedMoney" class="easyui-validatebox" validType="number"    style="width:250px"/></td>
                </tr>


                <tr>
                    <td align="right">可用资金</td>
                    <td><input  type="text" id="availableMoney<%=token %>" validType="FloatOrCurrency" name="customerMoney.availableMoney" class="easyui-validatebox" validType="number"    style="width:250px"/></td>
                </tr>


                <tr>
                    <td align="right">已投资资金</td>
                    <td><input  type="text" id="investedMoney<%=token %>" validType="FloatOrCurrency" name="customerMoney.investedMoney" class="easyui-validatebox" validType="number"    style="width:250px"/></td>
                </tr>


                <tr>
                    <td align="right">累计收益资金</td>
                    <td><input  type="text" id="totalProfitMoney<%=token %>" validType="FloatOrCurrency" name="customerMoney.totalProfitMoney" class="easyui-validatebox" validType="number"    style="width:250px"/></td>
                </tr>

            </table>
            <input type="hidden" id="sid<%=token %>" name="customerMoney.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="customerMoney.id" style="width:200px"/>
            <input type="hidden" id="state<%=token %>" name="customerMoney.state" style="width:200px"/>
            <input type="hidden" id="customerId"<%=token%>  name="customerMoney.customerId"  style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerMoneySubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('CustomerMoneyWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
