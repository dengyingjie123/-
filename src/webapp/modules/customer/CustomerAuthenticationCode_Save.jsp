<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015-4-7
  Time: 下午 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formCustomerAuthCode<%=token %>" name="formCustomerAuthCode" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <input type="hidden" id="sid<%=token %>" name="customerAuthenticationCodePO.sid" readonly="true" style="width:200px"/>
                <input type="hidden" id="id<%=token %>" name="customerAuthenticationCodePO.id" readonly="true" style="width:200px"/>
                <input type="hidden" id="state<%=token %>" name="customerAuthenticationCodePO.state" style="width:200px"/>
                <input type="hidden" id="customerId<%=token %>" name="customerAuthenticationCodePO.customerId" style="width:200px"/>
                <input  type="hidden" id="operatorId<%=token %>" style="background-color: #ebebeb" name="customerAuthenticationCodePO.operatorId" readonly="true" style="width:200px"/>
                <tr>
                    <td align="right">客户名称</td>
                    <td><input  type="text" class="easyui-validatebox" id="customerName<%=token %>" name="name" required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td><a href="javascript:void(0)" id="btnCheckCustomer<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                </tr>
                <tr>
                    <td align="right">信息</td>
                    <td><input  type="easyui-validatebox" id="info<%=token %>" name="customerAuthenticationCodePO.info" style="width:200px"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">认证码</td>
                    <td><input type="text" class="easyui-validatebox" id="code<%=token %>" name="customerAuthenticationCodePO.code" style="width:200px"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">操作人</td>
                    <td><input  type="text" id="operatorName<%=token %>" class="easyui-validatebox"   name="operatorName" readonly="true" style="width:200px"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">操作时间</td>
                    <td><input  type="text" class="easyui-datetimebox" editable="false"  id="operateTime<%=token %>" name="customerAuthenticationCodePO.operateTime" style="width:200px"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">发送时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="sendTime<%=token %>" name="customerAuthenticationCodePO.sendTime" style="width:200px"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">过期时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="expiredTime<%=token %>" name="customerAuthenticationCodePO.expiredTime" style="width:200px"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">认证时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="authenticationTime<%=token %>" name="customerAuthenticationCodePO.authenticationTime" style="width:200px"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">发送类型</td>
                    <td><input class="easyui-combobox"  editable="false" type="text" id="sendType<%=token %>" name="customerAuthenticationCodePO.sendType" style="width:200px"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td align="right">状态</td>
                    <td><input class="easyui-combobox"  editable="false" type="text" id="status<%=token %>" name="customerAuthenticationCodePO.status" style="width:200px"/></td>
                    <td></td>
                </tr>
            </table>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnAuthCodeSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerAuthCodeWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
