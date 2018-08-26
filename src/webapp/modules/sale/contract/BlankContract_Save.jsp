<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
 空白销售合同分配资料页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>空白销售合同分配资料</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formContractPO<%=token %>" name="formContractPO" action="" method="post">

            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">销售员</td>
                    <td><input type="text" id="receiveUserName<%=token %>"
                               name="contractPO.receiveUserName" readonly="readonly"
                               style="width:200px"/></td>
                <tr>
            </table>
            <input type="hidden" id="receiveUserId<%=token %>" name="contractPO.receiveUserId" style="width:200px"/>
            <input type="hidden" id="ContractNo<%=token %>" name="contractPO.contractNo" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnBlankContractSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('BlankContractWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
