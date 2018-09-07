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
        <form id="formAudit<%=token %>" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">客户名</td>
                    <td><input class="easyui-validatebox" readonly="true" type="text" id="customerName<%=token %>" name="customerName" editable="false" style="width:120px"/></td>

                    <td align="right">销售员</td>
                    <td><input type="text" readonly="true"  class="easyui-validatebox"  id="salesmanName<%=token %>" name="salesmanName" editable="false" style="width:120px"/></td>

                    <td align="right">审核</td>
                    <td><input class="easyui-combotree" id="status<%=token %>" name="customerDistribution.status"  required="true" missingmessage="必须填写" editable="false" style="width:125px"/></td>
                </tr>
                <tr>
                    <td align="right">描述</td>
                    <td colspan="6"><textarea  type="text"id="reason<%=token %>" name="customerDistribution.reason" style="width:500px;height: 50px;resize: none"/></td>
                </tr>
            </table>
            <input  type="hidden" id="customerId<%=token %>" name="customerDistribution.customerId" style="width:200px"/>
            <input  type="hidden" id="saleManId<%=token %>" name="customerDistribution.saleManId" style="width:200px"/>
            <input  type="hidden" id="remark<%=token %>" name="customerDistribution.remark" style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerDistribution.id" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnAuditSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('customerAuditWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>