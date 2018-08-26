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
                    <td align="right">发送到</td>
                    <td colspan="2"><textarea id="sendTo<%=token%>" name="message.sendTo" style="width:212px;height:30px"></textarea></td>
                    <td align="right"><a id="btnMessageSendTo<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >发送到</a></td>

                </tr>
                <tr>
                    <td align="right">标题</td>
                    <td><input  type="text" id="title<%=token %>" class="easyui-validatebox" name="message.title"  required="true" missingmessage="必须填写"  style="width:270px"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top">内容</td>
                    <td colspan="3"><textarea id="content<%=token%>" name="message.content" style="width:600px;height:300px"></textarea></td>
                </tr>


                <tr>
                    <td align="right">定时发送</td>
                    <td><input type="text" class="easyui-datetimebox"  editable="false" id="publishedTime<%=token %>" name="message.publishedTime" style="width:270px"/></td>

                    <td align="right">优先级</td>
                    <td><input type="text" class="easyui-combotree" editable="false" id="level<%=token %>" required="true" missingmessage="必须填写"  name="message.level" style="width:270px"/></td>

                </tr>

                <tr>
                    <td align="right" valign="top">是否置顶</td>
                    <td><input type="text" class="easyui-combotree" id="isTop<%=token%>" name="message.isTop" style="width:270px;" editable="false" required="true"  /></td>
                    <td align="right" valign="top">是否回执</td>
                    <td><input type="text" class="easyui-combotree" id="isReceipt<%=token%>" name="message.isReceipt" style="width:270px;" editable="false" required="true"  /></td>
                </tr>

                <!--
                <td align="right">排序</td>
                <td></td>
                -->

            </table>
            <input  type="hidden" class="easyui-validatebox" validType="number"   id="orders<%=token %>" required="true" missingmessage="必须填写"  name="message.orders" style="width:270px"/>
            <input  type="hidden" id="sid<%=token %>" name="message.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="message.id"    style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="message.state" style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="message.operatorId" style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="message.operateTime"  style="width:200px"/>
            <input  type="hidden" id="type<%=token %>" name="message.type" style="width:200px"/>
            <input  type="hidden" id="status<%=token %>" name="message.status" style="width:200px"/>
            <input  type="hidden" id="sendRange<%=token %>" name="message.sendRange" style="width:200px"/>
            <input  type="hidden" id="departmentIds<%=token %>" name="message.departmentIds" style="width:200px"/>
            <input  type="hidden" id="staffIds<%=token %>" name="message.staffIds" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnMessageSave<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >保存草稿</a>
        <a id="btnMessageSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >发送</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ArticleWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>