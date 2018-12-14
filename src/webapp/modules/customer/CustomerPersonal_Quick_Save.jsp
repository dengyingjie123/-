<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>

<body>
<div class="easyui-layout" fit="true" >
    <div region="center"  border="false" style="padding:10px;background:#fafafa;border:0px solid #ccc;">
            <form id="formCustomerPersonalQuick<%=token %>" name="formCustomerPersonal" action="" method="post">
                <table width="100%" border="0" cellpadding="5">
                    <tr>
                        <td align="right">姓名:</td>
                        <td><input type="text" id="name<%=token %>" class="easyui-validatebox" name="name" required="true" missingmessage="必须填写" /></td>
                    </tr>
                    <tr>
                        <td align="right">移动电话:</td>
                        <td><input type="text" id="mobile<%=token %>" class="easyui-validatebox" name="mobile" /></td>
                    </tr>
                    <tr>
                        <td align="right">身份证号:</td>
                        <td><input  type="text" id="idCardNumber<%=token %>" name="idCardNumber"/></td>
                    </tr>
                </table>
            </form>
        </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
                <td>
                    <a id="btnQuickAddSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
                       href="javascript:void(0)">确定</a>
                    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
                       onClick="fwCloseWindow('customerPersonalQuickWindow<%=token%>')" >取消</a>
                </td>
            </tr>
    </div>
</div>
</body>
</html>