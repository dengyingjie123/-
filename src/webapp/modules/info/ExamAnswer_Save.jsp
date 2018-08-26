<%--
  Created by IntelliJ IDEA.
  User: zsq
  Date: 4/14/2015
  Time: 2:30 PM
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
        <form id="formExamAnswer<%=token %>" name="formExamAnswer" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0" align="center">
                <tr>
                    <td align="right">客户</td>
                    <td><input class="easyui-validatebox" type="text" id="customerName<%=token %>" name="examAnswerVO.customerName" readonly="readonly" required="true" missingmessage="必须填写" style="width:450px"/></td>
                    <td><a href="javascript:void(0)" id="btnCheckCustomer<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                </tr>


                <tr>
                    <td align="right">问题</td>
                    <td><input class="easyui-validatebox" type="text" id="question<%=token %>" name="examAnswerVO.question" readonly="readonly" required="true" missingmessage="请选择客户" style="width:450px"/></td>
                    <td><a href="javascript:void(0)" id="btnCheckExamQuestionList<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                </tr>


                <tr>
                    <td align="right">选项</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="examAnswerVO.optionName" readonly="readonly" required="true" missingmessage="请选择问题" style="width:450px"/></td>
                    <td><a href="javascript:v=oid(0)" id="btnCheckExamOptionList<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                </tr>

                <tr>
                    <td align="right">描述</td>
                    <td><input  type="text" id="description<%=token %>" name="examAnswerVO.description" readonly="true" style="width:450px"/></td>
                </tr>

                <tr>
                    <td align="right">值</td>
                    <td><input  type="text" id="examOptionValue<%=token %>" name="examAnswerVO.examOptionValue" readonly="true" style="width:450px"/></td>
                </tr>

                <tr>
                    <td align="right">分数</td>
                    <td><input  type="text" id="examOptionScore<%=token %>" name="examAnswerVO.examOptionScore" readonly="true" style="width:450px"/></td>
                </tr>



                <tr>
                    <td align="right">填空题答案</td>
                    <td><input  type="text" id="answer<%=token %>" name="examAnswer.answer" style="width:450px"/></td>
                </tr>


            </table>
            <input  type="hidden" id="sid<%=token %>" name="examAnswer.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="examAnswer.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="examAnswer.state"    style="width:200px"/>
            <input  type="hidden" id="customerId<%=token %>" name="examAnswer.customerId" style="width:200px"/>
            <input  type="hidden" id="questionId<%=token %>" name="examAnswer.questionId" style="width:200px"/>
            <input  type="hidden" id="optionId<%=token %>" name="examAnswer.optionId" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnExamAnswerSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ExamAnswerWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
