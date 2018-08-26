<%--
  Created by IntelliJ IDEA.
  User: zsq
  Date: 4/14/2015
  Time: 9:54 AM
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
        <form id="formExamOption<%=token %>" name="formExamOption" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0" align="center">
                <tr>
                    <td>问题</td>
                    <td><input class="easyui-validatebox" type="text" id="question<%=token %>" name="examOptionVO.question" readonly="readonly" required="true" missingmessage="必须填写" style="width:400px"/></td>
                    <td><a href="javascript:void(0)" id="btnCheckExamQuestionList<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                </tr>

                <tr>
                    <td>选项</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="examOption.name" required="true" missingmessage="必须填写" style="width:180px"/>
                    &nbsp;&nbsp;描述
                    <input class="easyui-validatebox" type="text" id="description<%=token %>" name="examOption.description" required="true" missingmessage="必须填写" style="width:180px"/></td>
                </tr>

                <tr>
                    <td align="right">值</td>
                    <td><input class="easyui-validatebox" type="text" id="value<%=token %>" name="examOption.value" required="true" missingmessage="必须填写" style="width:180px"/>
                    &nbsp;&nbsp;分数
                    <input class="easyui-validatebox" type="text" id="score<%=token %>" name="examOption.score" required="true" missingmessage="必须填写" style="width:180px"/></td>
                </tr>

                <tr>
                    <td>排序</td>
                    <td><input class="easyui-validatebox" type="text" id="orders<%=token %>" name="examOption.orders" required="true" missingmessage="必须填写" style="width:400px"/></td>
                </tr>

            </table>
            <input  type="hidden" id="sid<%=token %>" name="examOption.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="examOption.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="examOption.state"    style="width:200px"/>
            <input  type="hidden" id="questionId<%=token %>" name="examOption.questionId"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnExamOptionSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ExamOptionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
