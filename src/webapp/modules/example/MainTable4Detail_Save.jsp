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
        <form id="formMainTable<%=token %>" name="formMainTable" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">A1</td>
                    <td><input class="easyui-validatebox" type="text" id="a1<%=token %>" name="mainTable.a1"
                               data-options="required:true,validType:'chinese'" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">A2</td>
                    <td><textarea  class="easyui-validatebox" data-options="required:true,validType:'unnormal'" id="a2<%=token %>"
                                   name="mainTable.a2" style="height:100px;width:200px;resize: none;"></textarea></td>
                </tr>

                <tr>
                    <td align="right">A3</td>
                    <td><input class="easyui-validatebox" type="text" id="a3<%=token %>" name="mainTable.a3"
                               data-options="required:true,validType:'integer'" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">A4</td>
                    <td><input type="text" class="easyui-validatebox" id="a4<%=token %>" name="mainTable.a4"
                               data-options="required:true,validType:'intOrFloat'" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">A5</td>
                    <td><input class="easyui-datetimebox" required="true" type="text" id="a5<%=token %>"
                               editable="false" name="mainTable.a5" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">A6</td>
                    <td><input class="easyui-datebox" required="true" type="text" id="a6<%=token %>" editable="false"
                               name="mainTable.a6" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">A7</td>
                    <td><input class="easyui-combotree" required="true" type="text" id="a7<%=token %>" name="mainTable.a7" style="width:200px"/></td>

                </tr>

                <tr>
                    <td align="right">A8</td>
                    <td><input class="easyui-combotree" type="text" id="a8<%=token %>" name="mainTable.a8" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">操作人</td>
                    <td><input type="text" id="operatorName<%=token %>" name="mainTable.operatorName"
                               disabled="disabled" style="width:200px"/></td>
                </tr>

                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="mainTable.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="mainTable.id" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="mainTable.operatorId" style="width:200px"/>
        </form>
        <div style="height: 250px">
            <div class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
                <div title="详细示例"  style="padding:5px">
                    <table id="DetailTable<%=token%>" ></table>
                </div>
            </div>
        </div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnMainTableSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('MainTableWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>