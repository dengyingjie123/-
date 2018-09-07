<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formContractStateSet<%=token %>" name="formContractStateSet" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">合同编号</td>
                    <td><input name="contractRoute.contractNo" class="easyui-validatebox"
                               id="contractNo<%=token %>" style="width:200px" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td align="right">合同状态</td>
                    <td><input class="easyui-combotree" type="text" id="ContractStateId<%=token %>"
                               name="contractRoute.ContractStateId" editable="false" style="width:200px"/></td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="contractRoute.sid"/>
            <input type="hidden" id="id<%=token %>" name="contractRoute.id"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnContractStateSetSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ContractStateSetWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>

