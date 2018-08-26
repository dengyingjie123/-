<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>

<%
    String token = request.getParameter("token");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<%
    UserPO loginUser = (UserPO)session.getAttribute(Config.SESSION_ACTION_LOGINUSER_STRING);
%>

<body>
<div class="easyui-layout" fit="true">

    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">

        <form id="formPaymentPlanAuditStatus<%=token %>" name="formPaymentPlanAuditStatus" action="" method="post">

            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">审核状态</td>
                    <td><input type="radio" name="status" value="8" id="pass"/>通过</td>
                    <td><input type="radio" name="status" value="9" id="notPass"/>不通过</td>
                </tr>
                <tr>
                    <td align="right">审核人</td>
                    <td colspan="2"><input style="width: 180px;" type="text" id="id<%=token %>" value="<%=loginUser.getName()%>" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="3"><span style="color:red;">请谨慎审核后方可操作！！！</span></td>
                </tr>
            </table>
        </form>

    </div>

    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnPaymentPlanAuditStatusSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('PaymentPlanAuditStatusWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
