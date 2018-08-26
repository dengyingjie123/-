<%--
  Created by IntelliJ IDEA.
  User: jepson-pc
  Date: 2015/8/17
  Time: 14:53
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
        <form id="formProductionHome<%=token %>" name="formProductionHome" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">所属项目</td>
                    <td colspan="3"><input type="text" id="projectId<%=token %>" class="easyui-combotree" required="true" missingmessage="必须填写" editable="false" name="productionHome.projectId"  style="width:465px"/>
                    </td>
                </tr>
                <tr>
                    <%--<td align="right">产品编号</td>--%>
                    <%--<td></td>--%>

                    <td align="right">产品名称</td>
                    <td><input  type="text" id="productionName<%=token %>" name="productionHome.productionName"       style="width:460px"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top" >描述</td>
                    <td><textarea  type="text" id="description<%=token %>" name="productionHome.description"   style="width:460px;height: 50px;resize: none"/></td>
                </tr>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="productionHome.sid"    style="width:300px"/>
            <input  type="hidden" id="id<%=token %>" name="productionHome.id"    style="width:300px"/>
            <input  type="hidden" id="state<%=token %>" name="productionHome.state"    style="width:300px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="productionHome.operateTime"  style="width:300px"/>
            <input  type="hidden" id="productionId<%=token %>" name="productionHome.productionId"       style="width:200px"/>
        </form>
        <br>
        <table id="ProductPropertyTable<%=token%>"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProductionHomeSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ProductionHomeWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>