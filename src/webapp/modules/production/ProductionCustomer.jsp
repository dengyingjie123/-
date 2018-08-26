<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/2/4
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
    String root=Config.getWebRoot();
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
          <table border="0" cellspacing="5" cellpadding="0">
            <tr>
              <td width="100" align="right">总销量</td>
              <td width="100" align="left" id="totalSaleMoney<%=token%>">&nbsp;</td>
              <td width="100" align="right">累计已转让</td>
              <td width="100" align="left" id="totalTransferMoney<%=token%>">&nbsp;</td>
              <td width="100" align="right">累计已兑付</td>
              <td width="100" align="left" id="totalPaybackMoney<%=token%>">&nbsp;</td>
              <td width="100" align="right">总余额</td>
              <td width="100" align="left" id="totalRemainMoney<%=token%>">&nbsp;</td>
            </tr>
          </table>
        <table id="ProductionCustomer<%=token%>" style="height: 365px"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductionCustomerWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
