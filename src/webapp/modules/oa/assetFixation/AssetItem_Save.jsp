<%--
  Created by IntelliJ IDEA.
  User: Jepson
  Date: 2015/6/8
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
    <title>Untitled Document</title>
</head>
<body>
<div class='easyui-layout' fit='true'>
    <div region='center' border='false' style='padding:10px;background:#fff;border:0px solid #ccc;'>
        <form id='formAssetItem<%=token %>' name='formAssetItem' action='' method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>
                <tr>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="assetItem.name"  required="true" missingmessage="必须填写"     style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">申购用途</td>
                    <td><textarea class="easyui-validatebox"  type="text" id="usage<%=token %>" name="assetItem.tusage"     required="true" missingmessage="必须填写"   style="resize:none;width:250px;height: 50px"/></td>
                </tr>
                <tr>
                    <td align="right">规格型号</td>
                    <td><input  type="text" id="specification<%=token %>" name="assetItem.specification"       style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">数量</td>
                    <td><input class="easyui-validatebox" type="text" id="quanity<%=token %>" name="assetItem.quanity" required="true"   validType="number"  invalidMessage="请输入整数"   style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">预计单价</td>

                    <td><input  type="text" class="easyui-validatebox" id="expectedUnitPrice<%=token %>" name="assetItem.expectedUnitPrice"    validType="FloatOrCurrency"      style="width:250px"/>元</td>

                </tr>
                <tr>
                    <td align="right">预计金额</td>

                    <td><input readonly="readonly" type="text"class="easyui-validatebox" id="expectedMoney<%=token %>" name="assetItem.expectedMoney"  validType="FloatOrCurrency"          style="width:250px"/>元</td>

                </tr>
                <tr>
                    <td align="right">单价</td>

                    <td><input  type="text" class="easyui-validatebox" id="unitPrice<%=token %>" name="assetItem.unitPrice"  required="true"  validType="FloatOrCurrency"       style="width:250px"/>元</td>

                </tr>
                <tr>
                    <td align="right">金额</td>

                    <td><input readonly="readonly" type="text" class="easyui-validatebox" id="money<%=token %>" name="assetItem.money" required="true"    validType="FloatOrCurrency"       style="width:250px"/>元</td>

                </tr>
                <tr>
                    <td align="right">采购时间</td>
                    <td><input class="easyui-datebox"  readonly="readonly"  editable="false" type="text" id="buyTime<%=token %>" name="assetItem.buyTime"       style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">存放地点</td>
                    <td><input class="easyui-validatebox"  readonly="readonly"  type="text" id="storagePlace<%=token %>" name="assetItem.storagePlace"      style="width:250px"/></td>
                </tr>
                <tr>
                    <td align="right">保管人姓名</td>
                    <td><input  type="text" id="keeperId<%=token %>"   readonly="readonly"  name="assetItem.keeperId"       style="width:250px"/></td>
                </tr>
            </table>
            <input  type='hidden' id='sid<%=token %>' name='assetItem.sid' style='width:250px'/>
            <input  type='hidden' id='id<%=token %>' name='assetItem.id' style='width:250px'/>
            <input  type='hidden' id='state<%=token %>' name='assetItem.state' style='width:250px'/>
            <input  type='hidden' id='operatorId<%=token %>' name='assetItem.operatorId' style='width:250px'/>
            <input  type='hidden' id='applicationId<%=token %>' name='assetItem.applicationId' style='width:250px'/>
        </form>
    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnAssetItemSubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok' href='javascript:void(0)' >确定</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)' onClick="fwCloseWindow('assetItemWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
