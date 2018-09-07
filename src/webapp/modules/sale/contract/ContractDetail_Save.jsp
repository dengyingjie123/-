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
        <form id="formContractDetailPO<%=token %>" name="formContractDetailPO" action="" method="post">

            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">状态</td>
                    <td><input type="text" class="easyui-combotree" id="detailStatus<%=token %>"
                               name="contractPO.detailStatus"
                               style="width:200px"/>
                    </td>
                <tr>
                <tr>
                    <td align="right">
                        备注
                    </td>
                    <td>
                      <textarea id="comment<%=token%>" name="contractPO.comment" style="width:200px;height:100px;">

                      </textarea>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="ContractNo<%=token %>" name="contractPO.contractNo" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="contractPO.id" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnContractDetailSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ContractDetailWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
