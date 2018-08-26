<%--

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String token = request.getParameter("token");
%>
<html>
<head>
  <title>标题</title>
</head>
<body>
<div class="easyui-layout" fit="true">
  <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
    <form id="formOrderContractSigned<%=token %>" name="formContractRouteListPO" action="" method="post">
      <table border="0" cellspacing="5" cellpadding="0">
        <tr>
          <td>订单编号</td>
          <td><input type="text" name="orderNum" id="orderNum<%=token%>"  style="width:400px" /></td>
        </tr>
        <tr>
          <td>客户名称</td>
          <td><input type="text" name="customerName" id="customerName<%=token%>"  style="width:400px" /></td>
        </tr>
        <tr>
          <td>产品名称</td>
          <td><input type="text" name="productionName" id="productionName<%=token%>"  style="width:400px" /></td>
        </tr>
        <tr>
          <td>打款金额</td>
          <td><input type="text" name="money" id="money<%=token%>"  style="width:400px" /></td>
        </tr>
        <tr>
          <td>打款时间</td>
          <td><input type="text" name="payTime" id="payTime<%=token%>"  style="width:400px" /></td>
        </tr>
        <tr>
          <td>合同编号</td>
          <td><input class="easyui-combotree" type="text" id="contractNo<%=token %>" name="contractNo" editable="true" style="width:400px"/></td>
        </tr>
      </table>
      <input type="hidden" name="id" id="id<%=token%>"  style="width:400px" />
    </form>
  </div>
  <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
    <a id="btnOrderContractSignedSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('OrderContractSigned<%=token%>')">取消</a>
  </div>
</div>
</body>
</html>