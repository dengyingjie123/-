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
        <form id="formArticle<%=token %>" name="formArticle" action="" method="post">
            <table width="100%" border="0">
                <tr>
                    <td><input type="text" id="title<%=token %>" class="easyui-validatebox" name="article.title" readonly="readonly" style="width:450px"/></td>
                <tr>
                    <td colspan="3"><textarea id="content<%=token%>" name="article.content" style="width:400px;height:300px"></textarea></td>
                </tr>

            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="article.operatorId" style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="article.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="article.id"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="article.operateTime"  style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="article.state" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnArticleContentSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ArticleContentWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>