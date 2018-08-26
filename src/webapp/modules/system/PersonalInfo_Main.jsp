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

<div style="padding:10px;">

    <div class="easyui-panel" title="基本信息" style="width:400px;height:220px;padding:10px;background:#fafafa;">
        <form id="baseEditForm<%=token%>" action="" method="post">
            <input name="user.id" type="hidden" id="uid2<%=token %>" readonly="readonly" style="width:200px"/>
            <table width="100%" cellpadding="5">
                <tr>
                    <td align="right" width="100px">姓名:</td>
                    <td>
                        <input name="user.name" type="text" id="uname<%=token %>" readonly="readonly" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">手机号码:</td>
                    <td>
                        <input name="user.mobile"  class="easyui-validatebox" maxlength="11"  invalidMessage="请输入11位手机号" validType="mobile" onBlur="checkMobile(value)" type="text" id="umobile<%=token %>" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">企业邮箱:</td>
                    <td>
                        <input name="user.emailAccountName" type="text" id="emailAccountName<%=token %>"readonly="readonly"  style="width:200px"/></td>
                </tr>
            </table>
        </form>
    </div>

    <br>

    <div class="easyui-panel" title="系统密码" style="width:400px;height:150px;padding:10px;background:#fafafa;">
        <form id="passwordEditForm<%=token%>" action="" method="post">
            <table width="100%" cellpadding="5">
                <tr>
                    <td align="right" width="100px">新的密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="upassword<%=token %>" name="user.password"
                               validType="equalTo['upassword2<%=token %>']" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">重复密码:</td>
                    <td><input class="easyui-validatebox" type="password" id="upassword2<%=token %>"
                               validType="equalTo['upassword<%=token %>']" style="width:200px"/></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><a id="btnPasswordEditSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a></td>
                </tr>
            </table>
        </form>
    </div>

</div>
</body>
</html>