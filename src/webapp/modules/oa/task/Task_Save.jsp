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
        <form id="formTask<%=token %>" name="formTask" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0" width="100%">
                <tr>
                    <td align="center">名称</td>
                    <td colspan="3"><input class="easyui-validatebox" type="text" id="name<%=token %>" name="task.name"
                               required="true" missingmessage="必须填写" style="width:450px"/></td>
                </tr>
                <tr>
                    <td align="center">分类编号</td>
                    <td colspan="3"><input class="easyui-validatebox" type="text" id="catalogId<%=token %>" name="task.catalogId"
                               required="true" missingmessage="必须填写" style="width:450px"/></td>
                </tr>
                <tr>
                    <td align="center">完成进度</td>
                    <td colspan="3"><input type="text" id="process<%=token %>" name="task.process" style="width:450px"/></td>
                </tr>
                <tr>
                    <td align="center">状态</td>
                    <td colspan="3">

                     <input  id="status<%=token%>" class="easyui-combotree" style="width:450px;" name="task.status"/>
                      <%--<select id="status<%=token%>" name="task.status"  style="width:450px"  required="true" missingmessage="必须选择" >--%>
                        <%--<option value="1" selected="selected">状态1</option>--%>
                        <%--<option value="2">状态2</option>--%>
                        <%--</select>--%>
                       <%--</td>--%>
                </tr>
                <tr>
                    <td align="center">开始时间</td>
                    <td><input type="text" class="easyui-datetimebox" editable="false" id="startTime<%=token %>" name="task.startTime" /></td>

                    <td align="center">结束时间</td>
                    <td><input type="text"  class="easyui-datetimebox" editable="false" id="stopTime<%=token %>" name="task.stopTime" /></td>
                </tr>
                <tr>
                    <td align="center">创建者</td>
                    <td><input class="easyui-validatebox" type="text" id="creatorId<%=token %>" name="task.creatorId"
                               required="true" missingmessage="必须填写" /></td>

                    <td align="center">创建时间</td>
                    <td><input type="text" class="easyui-datetimebox" editable="false" id="createTime<%=token %>" name="task.createTime" /></td>
                </tr>
                <tr>
                    <td align="center">执行人</td>
                    <td><input class="easyui-validatebox" type="text" id="executorId<%=token %>" name="task.executorId"
                               required="true" missingmessage="必须填写" /></td>

                    <td align="center">执行时间</td>
                    <td><input type="text"class="easyui-datetimebox" editable="false"id="executeTime<%=token %>" name="task.executeTime" />
                    </td>
                </tr>
                <tr>
                    <td align="center">检查人</td>
                    <td><input type="text" id="checkerId<%=token %>" name="task.checkerId" /></td>

                    <td align="center">检查时间</td>
                    <td><input type="text"class="easyui-datetimebox" editable="false" id="checkTime<%=token %>" name="task.checkTime" /></td>
                </tr>
                <tr>
                    <td valign="top" align="center">描述</td>
                    <td colspan="3"><textarea type="text" id="description<%=token %>" name="task.description" style="width:450px;height:100px;"></textarea>
                    </td>
                </tr>


            </table>
            <input type="hidden" id="sid<%=token %>" name="task.sid" style="width:450px"/>
            <input type="hidden" id="id<%=token %>" name="task.id" style="width:450px"/>
            <input type="hidden" id="state<%=token %>" name="task.state" style="width:450px"/>
            <input type="hidden" id="operatorId<%=token %>" name="task.operatorId" style="width:450px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnTaskSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('TaskWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
