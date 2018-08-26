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
        <form id="formCustomer<%=token %>" name="formCustomer" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">

                <tr>
                    <td align="right">公司名</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="departmentId<%=token %>" name="customerDistribution.departmentId"  required="true" missingmessage="必须填写"  style="width:380px"/></td>
                </tr>
                <tr>
                    <td align="right">销售小组</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="salemanGroupId<%=token %>" name="customerDistribution.saleGroupId"  required="true" missingmessage="必须填写"  style="width:380px"/></td>
                </tr>
                <tr>
                    <td align="right">销售员</td>
                    <td><input class="easyui-combotree"  editable="false" type="text" id="saleman<%=token %>" name="customerDistribution.saleManId"  required="true" missingmessage="必须填写"  style="width:380px"/></td>
                </tr>

            </table>

            <input type="hidden" name="customerDistribution.customerId"  id="customerId<%=token %>" readonly="readonly" style="width:200px"/>
            <input type="hidden" name="customerDistribution.remark"  id="remark<%=token %>" readonly="readonly" style="width:200px"/>
            <input type="hidden" name="customerDistribution.id"  id="id<%=token %>" readonly="readonly" style="width:200px"/>
       </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerSaleDelete<%=token %>" class="easyui-linkbutton" iconCls="icon-no" href="javascript:void(0)" >删除</a>
        <a id="btnCustomerSaleSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerSaleWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>