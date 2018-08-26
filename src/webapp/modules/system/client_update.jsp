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
        <form id="formClient<%=token %>" name="formClient" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">姓名</td>
                    <td><input class="easyui-validatebox"  type="text" id="Name<%=token %>" name="clientPO.Name"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">性别</td>
                    <td><input class="easyui-validatebox"  type="text" id="Sex<%=token %>" name="clientPO.Sex"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">电话</td>
                    <td><input class="easyui-validatebox"  type="text" id="Mobile<%=token %>" name="clientPO.Mobile"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">邮箱</td>
                    <td><input   class="easyui-validatebox"  type="text" id="Email<%=token %>" name="clientPO.Email" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">生日</td>
                    <td> <input  class="easyui-datebox" type="text" id="Birthday<%=token %>"  name="clientPO.Birthday" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>


            </table>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnClientSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ClientWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>