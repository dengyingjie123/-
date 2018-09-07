<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/14/14
  Time: 10:08 AM
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
        <form id="formProject<%=token %>" name="formProject" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">名称</td>
                    <td><input  type="text" id="name<%=token %>" class="easyui-validatebox" name="project.name" validType="maxLength[30]" required="true" missingmessage="必须填写"  style="width:200px"/></td>
                    <td align="right">规模</td>
                    <td><input  type="text" class="easyui-validatebox" id="size<%=token %>" name="project.size"  validType="intOrFloat"   required="true" missingmessage="必须填写" style="width:170px"/></td>
                </tr>
                <tr>
                    <td align="right">开始时间</td>
                    <td><input type="text" class="easyui-datebox"  editable="false" id="startTime<%=token %>" name="project.startTime" style="width:200px"/></td>
                    <td align="right">结束时间</td>
                    <td><input class="easyui-datebox"  editable="false" type="text" id="endTime<%=token %>" name="project.endTime" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">状态</td>
                    <td><input type="text" id="statusId<%=token %>" class="easyui-combotree" name="project.statusId"  required="true" missingmessage="必须填写"  editable="false" style="width:200px"/></td>
                    <td align="right">类型</td>
                    <td><input type="text" id="typeId<%=token %>" class="easyui-combotree" name="project.typeId" required="true" missingmessage="必须填写"  editable="false" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">投资方向</td>
                    <td><input type="text" id="investmentDirectionId<%=token %>"  class="easyui-combotree"   editable="false" name="project.investmentDirectionId"  style="width:200px"/></td>
                    <td align="right">合作方向</td>
                    <td><input type="text" id="partnerId<%=token %>"  class="easyui-combotree"  editable="false" name="project.partnerId" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">所属行业</td>
                    <td><input type="text" id="industry<%=token %>"  class="easyui-combotree"   editable="false" name="project.industryId"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top" >描述</td>
                    <td colspan="3"><textarea  type="text"id="description<%=token %>" name="project.description" style="width:460px;height: 70px;resize: none"/></td>
                </tr>

            </table>
            <input  type="hidden" id="operatorId<%=token %>" name="project.operatorId" style="width:200px"/>
            <input  type="hidden" id="sid<%=token %>" name="project.sid"    style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="project.id"    style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="project.operateTime"  style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="project.state" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnProjectSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ProjectWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>