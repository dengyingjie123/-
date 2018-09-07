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
        <form id="formCustomerAccount<%=token %>" name="formCustomerAccount" action="" method="post">

            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">开户行</td>
                    <td><input  type="text" id="bank<%=token %>" class="easyui-combotree" name="customerAccount.bank"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">账户名称</td>
                    <td><input type="text" class="easyui-validatebox"  id="name<%=token %>" name="customerAccount.name" style="width:200px" required="true" missingmessage="必须填写"/></td>
                </tr>

                <tr>
                    <td align="right">账号</td>
                    <td><input type="text" id="number<%=token %>" class="easyui-validatebox" data-options="required:true,validType:'integer'"  name="customerAccount.number"  style="width:200px" required="true" missingmessage="必须填写"/></td>
                </tr>
                <tr>
                    <td align="right">开户支行名称</td>
                    <td><input type="text" id="bankBranchName<%=token %>"  class="easyui-validatebox"   name="customerAccount.bankBranchName"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">城市代码</td>
                    <td><input type="text" id="cityCode<%=token %>"  class="easyui-validatebox"   name="customerAccount.cityCode"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">银行预留手机号</td>
                    <td><input type="text" id="mobile<%=token %>"  class="easyui-validatebox"   name="customerAccount.mobile"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">通联金融圈交易子账号</td>
                    <td><input type="text" id="allinpayCircle_AcctSubNo<%=token %>"  class="easyui-validatebox"   name="customerAccount.allinpayCircle_AcctSubNo"  style="width:200px" readonly/></td>
                </tr>
            </table>
            <input type="hidden" id="bankCode<%=token %>"  class="easyui-validatebox"  name="customerAccount.bankCode"  style="width:200px"/>
            <input type="hidden" id="customerId<%=token %>"  class="easyui-validatebox" name="customerAccount.customerId"  style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="customerAccount.operatorId" style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="customerAccount.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerAccount.id"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="customerAccount.operateTime"  style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="customerAccount.state" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerAccountSubmit_AllinpayCircle<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >通联金融生态圈换卡</a>
        <a id="btnCustomerAccountSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >正常保存</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerAccountWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>