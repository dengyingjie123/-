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
        <form id="formContractTake<%=token %>" name="formContractTake" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">合同号</td>
                    <td colspan="3"><textarea name="contractRoute.contractNo" rows="2" class="easyui-validatebox"
                                              id="contractNo<%=token %>" style="width:450px" required="true"
                                              missingmessage="必须填写"></textarea></td>
                </tr>

                <tr id="takeTRID<%=token%>">
                    <td align="right">领用日期</td>
                    <td><input class="easyui-datebox" editable="false" type="text" id="takeTime<%=token %>"
                               name="contractRoute.takeTime" style="width:200px"/></td>
                    <td align="right">领用人</td>
                    <td><input class="easyui-validatebox" readonly="true" type="text" id="takeUserName<%=token %>"
                               name="contractRoute.takeUserName" editable="false" style="width:195px"/></td>
                </tr>
                <tr id="retakeTRID<%=token%>">
                    <td align="right">重领日期</td>
                    <td><input class="easyui-datebox" editable="false" type="text" id="retakeTime<%=token %>"
                               name="contractRoute.retakeTime" style="width:200px"/></td>
                    <td align="right">重领人</td>
                    <td><input class="easyui-validatebox" readonly="true" type="text" id="retakeUserName<%=token %>"
                               name="contractRoute.retakeUserName" editable="false" style="width:195px"/></td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="contractRoute.sid"/>
            <input type="hidden" id="id<%=token %>" name="contractRoute.id"/>
            <input type="hidden" id="takeId<%=token %>" name="contractRoute.takeId"/>
            <input type="hidden" id="retakeId<%=token %>" name="contractRoute.retakeId"/>
            <input type="hidden" id="opName<%=token %>" name="opName"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnContractTakeSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ContractTakeWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
