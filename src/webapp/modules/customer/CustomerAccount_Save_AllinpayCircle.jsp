<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/18/14
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
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
        <form id="formCustomerAccountMobileCodeCheck<%=token %>" name="formCustomerAccountMobileCodeCheck" action="" method="post">

            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">org_processing_code</td>
                    <td><input  type="text" id="org_processing_code<%=token %>" class="easyui-validatebox" name="org_processing_code"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">org_trans_date</td>
                    <td><input  type="text" id="org_trans_date<%=token %>" class="easyui-validatebox" name="org_trans_date"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">org_req_trace_num</td>
                    <td><input  type="text" id="org_req_trace_num<%=token %>" class="easyui-validatebox" name="org_req_trace_num"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">verify_code</td>
                    <td><input  type="text" id="verify_code<%=token %>" class="easyui-validatebox" name="verify_code"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
            </table>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerAccountSubmit_AllinpayCircle_MobileCodeCheck<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('MobileCodeCheckWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>