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
        <form id="formPositionUser<%=token %>" name="formPositionUser" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">用户名称</td>
                    <td><input  type="text" id="userName<%=token %>" name="positionUserVO.userName" readonly="readonly" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">部门名称</td>
                    <td><input  type="text" id="departmentName<%=token %>" name="positionUserVO.departmentName" readonly="readonly" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">岗位名称</td>
                    <td><input  type="text" id="positionName<%=token %>" name="positionUserVO.positionName" readonly="readonly" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">默认归属</td>
                    <td><input class="easyui-combotree" type="text" id="statesName<%=token %>" name="positionUserPO.states"  style="width:200px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="id<%=token %>" name="positionUserPO.id" readonly="readonly" style="width:200px"/>
            <input  type="hidden" id="userId<%=token %>" name="positionUserPO.userId" readonly="readonly" style="width:200px"/>
            <input  type="hidden" id="positionId<%=token %>" name="positionUserPO.positionId" readonly="readonly" style="width:200px"/>
            <%--<input  type="text" id="states<%=token %>" name="positionUserPO.states" readonly="readonly" style="width:200px"/>--%>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnPositionUserSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('PositionUserWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>