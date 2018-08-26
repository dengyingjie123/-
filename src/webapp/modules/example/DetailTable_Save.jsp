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
        <form id="formDetailTable<%=token %>" name="formDetailTable" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">

                <tr>
                    <td align="right">MainId</td>
                    <td><input  type="text" id="mainId<%=token %>" name="detailTable.mainId"       style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">B1</td>
                    <td><input  type="text" id="b1<%=token %>" name="detailTable.b1"       style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">B2</td>
                    <td><input  type="text" id="b2<%=token %>" name="detailTable.b2"       style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">B3</td>
                    <td><input class="easyui-validatebox" type="text" id="b3<%=token %>" name="detailTable.b3" data-options="validType:'number'"   style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">B4</td>
                    <td><input class="easyui-validatebox" type="text" id="b4<%=token %>" name="detailTable.b4" data-options="validType:'intOrFloat'"   style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">B5</td>
                    <td><input class="easyui-datetimebox" id="b5<%=token %>" name="detailTable.b5" required="true" editable="false" style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">B6</td>
                    <td><input class="easyui-datebox" id="b6<%=token %>" name="detailTable.b6" required="true" editable="false" style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">B7</td>
                    <td><input class="easyui-combotree" type="text" id="b7<%=token %>" name="detailTable.b7" style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">B8</td>
                    <td><input class="easyui-combotree" type="text" id="b8<%=token %>" name="detailTable.b8" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">操作员</td>
                    <td><input  type="text" id="operatorName<%=token %>" name="detailTable.operatorName" disabled="disabled"  style="width:200px"/></td>
                </tr>

            </table>
            <input  type="hidden" id="sid<%=token %>" name="detailTable.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="detailTable.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="detailTable.state"       style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="detailTable.operatorId"       style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnDetailTableSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('DetailTableWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>