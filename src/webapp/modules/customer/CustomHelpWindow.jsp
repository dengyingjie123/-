<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/18/14
  Time: 5:23 PM
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">

    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="CustomHelpWindow<%=token %>" name="formCustomerCertificate" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0" >
                <tr>
                    <td>
                    1.身份证,驾驶证及营业执照编号均是唯一的，各不相同。<br/>
                        </td>
                </tr>
                <tr>
                    <td>
                    2.身份证编号：为18位号码。<br/>
                    </td>
                </tr>
                <tr>
                    <td>
                    3.驾驶证编号：档案编号为12位，驾驶证号为18位数。<br/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div region="south" border="false" style="text-align:center;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)"
           onClick="fwCloseWindow('helpWindowId<%=token%>')">确定</a>
    </div>
</div>
</body>
</html>