<%--
  Created by IntelliJ IDEA.
  User: zsq
  Date: 4/22/2015
  Time: 5:17 PM
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
        <form id="formProductionTransfer<%=token %>" name="formProductionTransfer" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0" align="center">
                <tr>
                    <td align="right">原订单编号</td>
                    <td><input class="easyui-validatebox" type="text" id="originalOrderId<%=token %>" name="productionTransferVO.originalOrderNum" required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td align="right">转让</td>
                    <td><input  id="transferSatues<%=token%>" name="productionTransfer.transferSatues" class="easyui-combotree" style="width:200px;"/></td>
                        <%--<select class="easyui-combobox" type="text" editable="false" id="transferSatues<%=token %>" name="productionTransfer.transferSatues" required="true" missingmessage="必须填写" style="width:200px;">--%>
                            <%--<option value="0">未转让</option>--%>
                            <%--<option value="1">已转让</option>--%>
                        <%--</select>--%>

                </tr>

                <tr>
                    <td align="right">原产品编号</td>
                    <td><input class="easyui-validatebox" type="text" id="originalProductionId<%=token %>" name="productionTransferVO.originalProductionNum" required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td align="right">现产品编号</td>
                    <td><input class="easyui-validatebox" type="text" id="currentProductionId<%=token %>" name="productionTransferVO.currentProductionNum" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">原产品持有人</td>
                    <td><input class="easyui-validatebox" type="text" id="originalCustumerId<%=token %>" name="productionTransferVO.originalCustumerName" required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td align="right">现产品持有人</td>
                    <td><input class="easyui-validatebox" type="text" id="currentCustomerId<%=token %>" name="productionTransferVO.currentCustomerName" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">原投资金额</td>
                    <td><input class="easyui-validatebox" type="text" id="money<%=token %>" name="productionTransfer.originalMoney" data-options="validType:'FloatOrCurrency'" style="width:200px"/></td>
                    <td align="right">现投资金额</td>
                    <td><input class="easyui-validatebox" type="text" id="nowMoney<%=token %>" name="productionTransfer.currentMoney" data-options="validType:'FloatOrCurrency'" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">原收益率</td>
                    <td><input class="easyui-validatebox" type="text" id="originalProfitRate<%=token %>" name="productionTransfer.originalProfitRate" data-options="validType:'number'" style="width:200px"/></td>
                    <td align="right">现收益率</td>
                    <td><input class="easyui-validatebox" type="text" id="currentProfitRate<%=token %>" name="productionTransfer.currentProfitRate" data-options="validType:'number'" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">转让金额</td>
                    <td><input class="easyui-validatebox" type="text" id="transferMoney<%=token %>" name="productionTransfer.transferMoney" data-options="validType:'FloatOrCurrency'" style="width:200px"/></td>
                    <td align="right">转让收益率</td>
                    <td><input class="easyui-validatebox" type="text" id="transferProfitRate<%=token %>" name="productionTransfer.transferProfitRate" data-options="validType:'number'" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">审核人</td>
                    <td><input class="easyui-validatebox" type="text" id="checkerId<%=token %>" name="productionTransferVO.checkerName"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                    <td align="right">审核时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="checkTime<%=token %>" name="productionTransfer.checkTime" required="true" missingmessage="必须填写"   style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">转让开始时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="transferStartTime<%=token %>" name="productionTransfer.transferStartTime"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                    <td align="right">转让结束时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="transferEndTime<%=token %>" name="productionTransfer.transferEndTime"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="productionTransfer.sid"/>
            <input type="hidden" id="id<%=token %>" name="productionTransfer.id"/>
            <input type="hidden" id="state<%=token %>" name="productionTransfer.state"/>
            <input type="hidden" id="originalProductionId<%=token %>" name="productionTransfer.originalProductionId"/>
            <input type="hidden" id="originalOrderId<%=token %>" name="productionTransfer.originalOrderId"/>
            <input type="hidden" id="originalCustumerId<%=token %>" name="productionTransfer.originalCustumerId"/>
            <input type="hidden" id="currentProductionId<%=token %>" name="productionTransfer.currentProductionId"/>
            <input type="hidden" id="currentCustomerId<%=token %>" name="productionTransfer.currentCustomerId"/>
            <input type="hidden" id="checkerId<%=token %>" name="productionTransfer.checkerId"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProductionTransferSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductionTransferWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
