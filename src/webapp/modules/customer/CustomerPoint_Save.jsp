<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/4/20
  Time: 10:26
  To change this template use File | Settings | File Templates.
  客户积分
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
        <form id="formCustomerPoint<%=token %>" name="formCustomerPoint" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">客户名称</td>
                    <td><input  type="text" class="easyui-validatebox" id="name<%=token %>" name="customerPoint.customerName" required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td><a href="javascript:void(0)" id="btnCheckCustomer<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>

                </tr>
                <tr>
                    <td align="right">可用积分</td>
                    <td><input    class="easyui-validatebox"  data-options="validType:'number'"  id="availablePoint<%=token %>" name="customerPoint.availablePoint"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">已使用积分</td>
                    <td><input  type="text" class ="easyui-validatebox" data-options="validType:'number'"   id="usedPoint<%=token %>" name="customerPoint.usedPoint"    style="width:200px"/></td>
                </tr>
                <tr>
              <td align="right">操作员</td>
                <td><input  readonly="readonly"  type="text" id="operatorName<%=token %>" name="customerPoint.operatorName"    style="width:200px"/></td>
              </tr>


            </table>
            <input  type="hidden" id="sid<%=token %>" name="customerPoint.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerPoint.id"    style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="customerPoint.operatorId"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="customerPoint.state"    style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="customerPoint.customerId"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerPointSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerPointWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>