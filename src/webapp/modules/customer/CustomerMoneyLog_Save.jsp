<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2015/4/28
  Time: 9:36
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
        <form id="formCustomerMoneyLog<%=token %>" name="formCustomerMoneyLog" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">类型</td>
                    <td><input  type="text" id="type<%=token %>" name="customerMoneyLog.type"   class="easyui-combotree"   style="width:320px"/></td>
                </tr>
                <tr>
                    <td align="right">状态</td>
                    <td><input  type="text" id="status<%=token %>" name="customerMoneyLog.status"  class="easyui-combotree"   style="width:320px"/></td>
                </tr>


                <tr>
                    <td align="right">模块编号</td>
                    <td><input  type="text" id="moduleId<%=token %>" name="customerMoneyLog.moduleId" class="easyui-validatebox" validType="number"   style="width:320px"/></td>
                </tr>


                <tr>
                    <td align="right">业务编号</td>
                    <td><input  type="text" id="bizId<%=token %>" name="customerMoneyLog.bizId" class="easyui-validatebox"  validType="number"  style="width:320px"/></td>
                </tr>
                <tr>
                    <td align="right">内容</td>
                    <td><textarea type="text" id="description<%=token %>"   name="customerMoneyLog.content"    style="width:320px;height:100px;"/></td>
                </tr>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="customerMoneyLog.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="customerMoneyLog.id"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCustomerMoneyLogSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerMoneyLogWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
