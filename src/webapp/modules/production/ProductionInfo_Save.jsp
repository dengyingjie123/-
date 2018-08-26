<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/4/9
  Time: 15:08
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
        <form id="formProductionInfo<%=token %>" name="formProductionInfo" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0" align="center">
                <tr>
                    <td>产品名称</td>
                    <td><input class="easyui-validatebox" type="text" id="productionName<%=token %>" name="productionInfoVO.productionName"/></td>
                </tr>

                <tr>
                    <td>产品描述</td>
                    <td><textarea id="description<%=token %>" name="productionInfo.description"></textarea></td>
                </tr>

            </table>
            <input  type="hidden" id="sid<%=token %>" name="productionInfo.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="productionInfo.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="productionInfo.state"    style="width:200px"/>
            <input  type="hidden" id="productionId<%=token %>" name="productionInfo.productionId"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProductionInfoSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductionInfoWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
