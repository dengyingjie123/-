<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/15/14
  Time: 1:01 PM
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
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/production/ProductionstageClass.js"></script>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formProductionstage<%=token %>" name="formProductionstage" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">名称</td>
                    <td><input  type="text" id="name<%=token %>" class="easyui-validatebox" name="productionstage.name"  required="true" missingmessage="必须填写" onblur="DataLength(this)" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">配额</td>
                    <td><input class="easyui-validatebox"  type="text" id="size<%=token %>" name="productionstage.size"  required="true" missingmessage="必须填写" onblur="TextSize(this)"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">开始时间</td>
                    <td><input type="text" class="easyui-datebox"  editable="false" id="startTime<%=token %>" name="productionstage.startTime" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">结束时间</td>
                    <td><input class="easyui-datebox"  editable="false" type="text" id="stopTime<%=token %>" name="productionstage.stopTime" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">起息日</td>
                    <td><input type="text" id="valueDate<%=token %>"  class="easyui-datebox"  required="true" missingmessage="必须填写"  editable="false" name="productionstage.valueDate"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">到期日</td>
                    <td><input type="text" id="expiringDate<%=token %>"  class="easyui-datebox"  required="true" missingmessage="必须填写"  editable="false" name="productionstage.expiringDate" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">付息日</td>
                    <td><input type="text" id="interestDate<%=token %>"  class="easyui-datebox"  required="true" missingmessage="必须填写"  editable="false" name="productionstage.interestDate" style="width:200px"/></td>
                </tr>
                <tr><input  type="hidden" id="productionId<%=token %>" name="productionstage.productionId"    style="width:200px"/></tr>
            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="productionstage.operatorId" style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="productionstage.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="productionstage.id"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="productionstage.operateTime"  style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="productionstage.state" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProductionstageSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProductionstageWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>