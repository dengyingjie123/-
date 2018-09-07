<%--
  Created by IntelliJ IDEA.
  User: 张舜清
  Date: 4/13/2015
  Time: 3:16 PM
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
        <form id="formExamQuestion<%=token %>" name="formExamQuestion" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
            	<tr>
                    <td align="right">试卷</td>
                    <td colspan="5"><input class="easyui-combotree" type="text" id="paperId<%=token %>" name="examQuestion.paperId" editable="false" required="true" missingmessage="必须填写" style="width:750px"/></td>
                </tr>
                <tr>
                    <td align="right">标题</td>
                    <td><input class="easyui-validatebox" type="text" id="title<%=token %>" name="examQuestion.title" required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td align="right">类型</td>
                    <td><input class="easyui-combotree" type="text" id="type<%=token %>" name="examQuestion.type" editable="false" required="true" missingmessage="必须填写" style="width:200px"/></td>
                    <td align="right">创建时间</td>
                    <td><input class="easyui-datetimebox" type="text" id="time<%=token %>" name="examQuestion.time"  readonly="true" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">问题</td>
                    <td colspan="5"><input class="easyui-validatebox" type="text" id="question<%=token %>" name="examQuestion.question" required="true" missingmessage="必须填写" style="width:750px"/></td>
                </tr>
                <tr>
                    <td align="right">题号</td>
                    <td colspan="5"><input class="easyui-validatebox" type="text" id="questionNO<%=token %>" name="examQuestion.questionNO" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="examQuestion.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="examQuestion.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="examQuestion.state"    style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="examQuestion.operatorId"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="examQuestion.operateTime"    style="width:200px"/>
        </form>
        <div style="height: 400px">
            <div id="" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
                <div  title="试题选项"  style="padding:5px ;" >
                    <table id="ExamOptionTable<%=token%>" ></table>
                </div>
            </div>
        </div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnExamQuestionSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ExamQuestionWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>