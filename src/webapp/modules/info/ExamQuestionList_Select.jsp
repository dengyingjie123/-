<%--
  Created by IntelliJ IDEA.
  User: zsq
  Date: 4/14/2015
  Time: 4:11 PM
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
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:5px;background:#fff;border:1px solid #ccc;">

            <div id="examQuestion<%=token%>" title="试题" style="padding:2px;">
                <div class="easyui-panel" title="查询" iconCls="icon-search">
                    <table border="0" cellpadding="3" cellspacing="0">
                        <tr>
                            <td>问题</td>
                            <td><input class="easyui-validatebox" type="text" id="search_QuestionList<%=token %>"/></td>
                            <td>类型</td>
                            <td><input type="text" class="easyui-combotree" id="search_TypeNameList<%=token %>" style="width:80px;" editable="false" /></td>
                            <td>
                                <a id="btnExamQuestionListSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            </td>
                            <td>
                                <a id="btnExamQuestionListSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <br>
                <table id="ExamQuestionListTable<%=token%>"></table>
            </div>


    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelectedExamQuestion<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >选择</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ExamQuestionSelectWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>