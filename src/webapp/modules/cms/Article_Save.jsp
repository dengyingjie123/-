<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/17/14
  Time: 9:51 AM
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
        <form id="formArticle<%=token %>" name="formArticle" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">标题</td>
                    <td><input  type="text" id="title<%=token %>" class="easyui-validatebox" name="article.title"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                    <td align="right">栏目</td>
                    <td><input class="easyui-combotree"  type="text" id="columnId<%=token %>" name="article.columnId"  required="true" missingmessage="必须填写"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">发布时间</td>
                    <td><input type="text" class="easyui-datetimebox"  editable="false" id="publishedTime<%=token %>" name="article.publishedTime" style="width:200px"/></td>
                    <td align="right">排序</td>
                    <td><input type="text" class="easyui-validatebox"
                               validType="number"   id="orders<%=token %>" required="true" missingmessage="必须填写"  name="article.orders" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top">是否显示</td>
                    <td><input type="text" class="easyui-combotree" id="isDisplay<%=token%>" name="article.isDisplay" style="width:200px;" editable="false" required="true"  /></td>
                    <td align="right">关联业务</td>
                    <td><input  type="text" id="bizId<%=token %>" class="easyui-validatebox" name="article.bizId"  style="width:200px"/></td>
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
        <a id="btnArticleSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ArticleWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>