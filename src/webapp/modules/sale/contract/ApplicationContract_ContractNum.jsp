<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
 销售合同申请资料填写页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>合同号列表</title>
</head>
<body>
<div class="easyui-layout" fit="true">
        <textarea  type="text" id="contractNum<%=token %>"  readonly="readonly"  style="width:580px;height: 374px ;resize: none"></textarea>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ContractNumWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
