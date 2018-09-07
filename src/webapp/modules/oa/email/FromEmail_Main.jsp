<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/5
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>收件人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="fromEmail.fromName" id="Search_FromName<%=token%>" style="width:100px;"/>
                </td>
                <td>收件人邮件</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="fromEmail.fromEmail" id="Search_FromEmail<%=token%>" style="width:100px;"/>
                </td>
                <td>发送人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="fromEmail.toName" id="Search_ToName<%=token%>" style="width:100px;"/>
                </td>
                <td>发送人邮件</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="fromEmail.toEmail" id="Search_ToEmail<%=token%>" style="width:100px;"/>
                </td>
                </tr><tr>
                <td>标题</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="fromEmail.emaioTitle" id="Search_EmaioTitle<%=token%>" style="width:100px;"/>
                </td>

                <td>
                    开始发送时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_ToTime_Start" id="Search_ToTime_Start<%=token%>" editable="false" style="width:100px;"/>

                </td>
                <td>
                    结束发送时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_ToTime_End" id="Search_ToTime_End<%=token%>" editable="false" style="width:100px;"/>
                </td>

                <td>
                    <a id="btnFromEmailSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnFromEmailSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="FromEmailTable<%=token%>"></table>

</div>
</body>
</html>
