<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
设置销售合同状态资料页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>销售合同状态</title>
</head>
<body>

<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formContractStatus<%=token %>" name="formContractStatus" action="" method="post">

            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">状态</td>
                    <td><input type="text" class="easyui-combotree" id="contractStatus<%=token %>" name="status" style="width:320px"/>
                    </td>
                <tr>
                <tr>
                    <td align="right">
                        备注
                    </td>
                    <td>
                      <textarea id="comment<%=token%>" name="comment" style="width:320px;height:100px;"></textarea>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="contractNO<%=token %>" name="contractNO" />
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSetContractStatusSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ContractSetStatusWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
