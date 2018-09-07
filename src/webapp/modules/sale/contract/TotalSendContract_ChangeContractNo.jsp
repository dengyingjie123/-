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
        <form id="formContractRouteListPO<%=token %>" name="formContractRouteListPO" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td>原合同编号</td>
                    <td>
                        <input type="text" name="contract.contractNo_old" id="contractNo_old<%=token%>"  style="width:400px" />
                    </td>
                </tr>
                <tr>
                  <td>新合同编号</td>
                  <td><input type="text" name="contract.contractNo" id="contractNo<%=token%>"  style="width:400px" /></td>
                </tr>
                <tr>
                    <td align="right">变更原因</td>
                    <td>
                        <textarea id="comment<%=token%>" name="contract.comment" style="width:400px;height:50px;"></textarea>
                    </td>
                </tr>
                <tr>
                  <td align="right">操作人</td>
                  <td><input type="text" class="eastui-combotree" name="contract.operatorName" id="operatorName<%=token%>" style="width:200px" readonly /></td>
                </tr>
                <tr>
                  <td align="right">操作时间</td>
                  <td><input class="easyui-datetimebox" type="text" id="operateTime<%=token %>" name="contract.operateTime" editable="false" style="width:200px"/></td>
                </tr>
            </table>
            
            <input type="hidden" name="contract.id" id="id<%=token%>" style="width:200px" />
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnChangeContractNoSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ChangeContractNoWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>