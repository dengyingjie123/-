<%--系统统计--%>
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
        <form id="formStatistics<%=token %>" name="formStatistics" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">

                <tr>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="statistics.name" required="true" missingmessage="必须填写"   style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">标识</td>
                    <td><input class="easyui-validatebox" type="text" id="tag<%=token %>" name="statistics.tag"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">数值</td>
                    <td><input class="easyui-validatebox" type="text" id="v<%=token %>" name="statistics.v"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">操作员</td>
                    <td><input  type="text"  id="operatorName<%=token %>" name="statistics.operatorName"  readonly="true"  style="width:200px;" /></td>
                </tr>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="statistics.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="statistics.id"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="statistics.operateTime"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;">
        <a id="btnStatisticsSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('StatisticsWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
