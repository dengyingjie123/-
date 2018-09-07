<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/2
  Time: 18:15
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formAssetInfo<%=token %>" name="formAssetInfo" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">

                <tr>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="assetInfo.name"
                               required="true" missingmessage="必须填写" style="width:200px"/></td>

                    <td align="right">所属部门</td>
                    <td><input class="easyui-combotree" type="text" id="departmentId<%=token %>"
                               name="assetInfo.departmentId" required="true" missingmessage="必须填写" style="width:200px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">规格型号</td>
                    <td  colspan="3"><input type="text" id="specification<%=token %>" name="assetInfo.specification"
                               style="width:460px"/></td>
                </tr>
                <tr>
                    <td align="right">申购用途</td>
                    <td colspan="3"><textarea type="text" id="purpose<%=token %>" name="assetInfo.purpose"
                                  style="width:460px ;height:100px;resize:none;"></textarea></td>
                </tr>

                <tr>
                    <td align="right">数量</td>
                    <td><input class="easyui-validatebox" type="text" id="quanity<%=token %>" name="assetInfo.quanity"
                               validType="number" invalidMessage="请输入整数" style="width:200px"/></td>

                    <td align="right">单价</td>
                    <td><input type="text" class="easyui-validatebox" data-options="validType:'FloatOrCurrency'" id="unitPrice<%=token %>" name="assetInfo.unitPrice" style="width:200px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">金额</td>
                    <td><input type="text" readonly="readonly" id="money<%=token %>" name="assetInfo.money" validType="FloatOrCurrency"  style="width:200px"/></td>

                    <td align="right">采购时间</td>
                    <td><input class="easyui-datebox" editable="false" type="text" id="buyTime<%=token %>"
                               name="assetInfo.buyTime" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
                <tr>
                <td align="right">保管人</td>
                <td colspan="3"><input type="text" class="easyui-validatebox" id="keeperId<%=token %>" name="assetInfo.keeperId"
                           style="width:460px"/></td>
             </tr>
                <tr>
                    <td align="right">存放地点</td>
                    <td colspan="3"><textarea class="easyui-validatebox" type="text" id="storagePlace<%=token %>"
                               name="assetInfo.storagePlace" required="true" missingmessage="必须填写" style="width:460px;height:100px;resize:none;"></textarea>
                    </td>
                </tr>

            </table>
            <input type="hidden" id="sid<%=token %>" name="assetInfo.sid" style="width:360px"/>

            <input type="hidden" id="id<%=token %>" name="assetInfo.id" style="width:360px"/>

            <input type="hidden" id="state<%=token %>" name="assetInfo.state" style="width:360px"/>

            <input type="hidden" id="operatorId<%=token %>" name="assetInfo.operatorId" style="width:360px"/>
            </tr>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnAssetInfoSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('AssetInfoWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>

