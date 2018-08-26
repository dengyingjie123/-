<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/16/14
  Time: 2:59 PM
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
        <form id="formProductionComposition<%=token %>" name="formProductionComposition" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">名称</td>
                    <td>
                        <input  type="text" id="name<%=token %>" class="easyui-validatebox" name="productionComposition.name"  required="true" missingmessage="必须填写"  style="width:200px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">范围开始</td>
                    <td><input type="text" class="easyui-validatebox"  id="sizeStart<%=token %>" name="productionComposition.sizeStart" data-options="required:true,validType:'intOrFloat'"  style="width:200px"/>元</td>
                </tr>

                <tr>
                    <td align="right">范围结束</td>
                    <td><input type="text" id="sizeStop<%=token %>" class="easyui-validatebox" name="productionComposition.sizeStop"  required="true" missingmessage="必须填写"  validType="intOrFloat" style="width:200px"/>元</td>
                </tr>
                <tr>
                    <td align="right">预期收益率</td>
                    <td><input type="text" id="expectedYield<%=token %>"  class="easyui-validatebox"  validType="intOrFloat"  required="true" missingmessage="必须填写"  name="productionComposition.expectedYield"  style="width:180px"/>
                    %</td>

                </tr>
                <tr>
                    <td colspan="3" align="right" style="color: red">(预期收益填写0表示浮动收益)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <tr>
                    <td align="right" >浮动收益率</td>
                    <td><input type="text" id="floatingRate<%=token %>"  class="easyui-validatebox" validType="intOrFloat" name="productionComposition.floatingRate"  style="width:180px"/>
                    %</td>
                </tr>
                <tr>
                    <td align="right">购买费率</td>
                    <td><input type="text" id="buyingRate<%=token %>"  class="easyui-validatebox"   validType="intOrFloat"  name="productionComposition.buyingRate"  style="width:180px"/>
                    %</td>
                </tr>
                <tr>
                    <td align="right" >支付费率</td>
                    <td><input type="text" id="payRate<%=token %>"  class="easyui-validatebox" name="productionComposition.payRate" validType="intOrFloat" style="width:180px"/>
                        %</td>
                </tr>
                <tr>
                    <td align="right">直销费率</td>
                    <td>
                        <input type="text" id="directSellingRate<%=token %>"  class="easyui-validatebox" name="productionComposition.directSellingRate" validType="intOrFloat" style="width:180px"/>%
                    </td>
                </tr>
                <tr>
                    <td align="right">渠道费率</td>
                    <td>
                        <input type="text" id="channelSellingRate<%=token %>"  class="easyui-validatebox" name="productionComposition.channelSellingRate" validType="intOrFloat" style="width:180px"/>%
                    </td>
                </tr>
                <tr>
                    <td align="right">银行托管费率</td>
                    <td>
                        <input type="text" id="bankingRate<%=token %>"  class="easyui-validatebox" name="productionComposition.bankingRate" validType="intOrFloat" style="width:180px"/>%
                    </td>
                </tr>
                <tr>
                    <td align="right" >返佣等级</td>
                    <td>
                        <input  type="text" id="commissionLevel<%=token %>" class="easyui-validatebox" name="productionComposition.commissionLevel"  style="width:195px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right"> 开启返佣修正</td>
                    <td><input class="easyui-combotree" type="text" id="needCommissionCorrection<%=token %>" name="productionComposition.needCommissionCorrection" editable="false" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">开启客户类型返佣修正</td>
                    <td><input class="easyui-combotree" type="text" id="needCustomerTypeCommissionCorrection<%=token %>" name="productionComposition.needCustomerTypeCommissionCorrection" editable="false" style="width:200px"/></td>
                </tr>
                <td></td>
            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="productionComposition.operatorId" style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="productionComposition.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="productionComposition.id"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="productionComposition.operateTime"  style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="productionComposition.state" style="width:200px"/>
            <input  type="hidden" id="productionId<%=token %>"  name="productionComposition.productionId"  style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProductionCompositionSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductionCompositionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>