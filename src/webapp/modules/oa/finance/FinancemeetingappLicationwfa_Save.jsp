<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2015/4/29
  Time: 18:11
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
        <form id="formFinancemeetingapplicationwfa<%=token %>" name="formFinancemeetingapplicationwfa" action="" method="post">
            <table border="0" cellspacing="3" cellpadding="6">
                <tr>
                    <td align="right">组织编号</td>
                    <td><input class="easyui-validatebox" type="text" id="orgId<%=token %>" validType="number" name="financemeetingapplicationwfa.orgId"  required="true" missingmessage="必须填写"     style="width:150px"/></td>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="financemeetingapplicationwfa.name"  required="true" missingmessage="必须填写"     style="width:150px"/></td>
                    <td align="right">参与人员</td>
                    <td><input class="easyui-validatebox" type="text" id="participant<%=token %>" name="financemeetingapplicationwfa.participant"  required="true" missingmessage="必须填写"     style="width:150px"/></td>

                </tr>
                <tr>
                    <td align="right">开始时间</td>
                    <td><input   type="text" class="easyui-datebox" id="startTime<%=token %>"  editable="false" name="financemeetingapplicationwfa.startTime"  required="true" missingmessage="必须填写"     style="width:150px"/></td>
                    <td align="right">结束时间</td>
                    <td><input   class="easyui-datebox" type="text" id="endTime<%=token %>"  editable="false" name="financemeetingapplicationwfa.endTime"  required="true" missingmessage="必须填写"     style="width:150px"/></td>
                    <td align="right">会议报批金额</td>
                    <td><input class="easyui-validatebox" type="text" id="money<%=token %>" name="financemeetingapplicationwfa.money"  required="true" missingmessage="必须填写" validType="FloatOrCurrency"     style="width:150px"/></td>

                </tr>


                <tr>
                    <td align="right">申请部门负责人编号</td>
                    <td><input  type="text" id="departmentLeaderId<%=token %>" name="financemeetingapplicationwfa.departmentLeaderId"       style="width:150px"/></td>
                    <td align="right">申请部门负责人时间</td>
                    <td><input  type="text" id="departmentLeaderTime<%=token %>"  editable="false" name="financemeetingapplicationwfa.departmentLeaderTime"   class="easyui-datebox"    style="width:150px"/></td>
                    <td align="right">申请部门负责人意见</td>
                    <td><input class="easyui-combobox"  editable="false" type="text" id="departmentLeaderCommentTypeId<%=token %>" name="financemeetingapplicationwfa.departmentLeaderCommentTypeId"       style="width:150px"/></td>

                </tr>


                <tr>
                    <td align="right">所属公司总经理编号</td>
                    <td><input  type="text" id="generalManagerId<%=token %>" name="financemeetingapplicationwfa.generalManagerId"       style="width:150px"/></td>
                    <td align="right">所属公司总经理时间</td>
                    <td><input  type="text" id="generalManagerTime<%=token %>"  editable="false" name="financemeetingapplicationwfa.generalManagerTime" class="easyui-datebox"      style="width:150px"/></td>
                    <td align="right">所属公司总经理意见</td>
                    <td><input class="easyui-combobox"  editable="false" type="text" id="generalManagerCommentTypeId<%=token %>" name="financemeetingapplicationwfa.generalManagerCommentTypeId"       style="width:150px"/></td>
                </tr>
                <tr>
                    <td align="right">总公司财务总监编号</td>
                    <td><input  type="text" id="hQFinanceDirectorId<%=token %>" name="financemeetingapplicationwfa.hQFinanceDirectorId"       style="width:150px"/></td>
                    <td align="right">总公司财务总监时间</td>
                    <td><input  type="text" id="hQFinanceDirectorTime<%=token %>" name="financemeetingapplicationwfa.hQFinanceDirectorTime"editable="false"  class="easyui-datebox"     style="width:150px"/></td>
                    <td align="right">总公司财务总监意见</td>
                    <td><input class="easyui-combobox"  editable="false" type="text" id="hQFinanceDirectorCommentTypeId<%=token %>" name="financemeetingapplicationwfa.hQFinanceDirectorCommentTypeId"       style="width:150px"/></td>

                </tr>

                <tr>
                    <td align="right">总公司分管领导编号</td>
                    <td><input  type="text" id="hQLeaderId<%=token %>" name="financemeetingapplicationwfa.hQLeaderId"       style="width:150px"/></td>
                    <td align="right">总公司分管领导时间</td>
                    <td><input  type="text" id="hQLeaderTime<%=token %>"  editable="false" name="financemeetingapplicationwfa.hQLeaderTime"  class="easyui-datebox"     style="width:150px"/></td>
                    <td align="right">总公司分管领导意见</td>
                    <td><input class="easyui-combobox"  editable="false" type="text" id="hQLeaderCommentTypeId<%=token %>" name="financemeetingapplicationwfa.hQLeaderCommentTypeId"       style="width:150px"/></td>

                </tr>

                <tr>
                    <td align="right">总公司执行董事编号</td>
                    <td><input  type="text" id="hQCEOId<%=token %>" name="financemeetingapplicationwfa.hQCEOId"       style="width:150px"/></td>
                    <td align="right">总公司执行董事时间</td>
                    <td><input  type="text" class="easyui-datebox" id="hQCEOTime<%=token %>"  editable="false" name="financemeetingapplicationwfa.hQCEOTime"       style="width:150px"/></td>
                    <td align="right">总公司执行董事意见</td>
                    <td><input class="easyui-combobox"  editable="false" type="text" id="hQCEOCommentTypeId<%=token %>" name="financemeetingapplicationwfa.hQCEOCommentTypeId"       style="width:150px"/></td>

                </tr>
                <tr>
                    <td align="right">地点</td>
                    <td   colspan="5"><input class="easyui-validatebox" type="text" id="address<%=token %>" name="financemeetingapplicationwfa.address"  required="true" missingmessage="必须填写"     style="width:440px"/></td>
                </tr>
            </table>
            <input type="hidden" id="sid<%=token %>" name="financemeetingapplicationwfa.sid" style="width:450px"/>
            <input type="hidden" id="id<%=token %>" name="financemeetingapplicationwfa.id" style="width:450px"/>
            <input type="hidden" id="state<%=token %>" name="financemeetingapplicationwfa.state" style="width:450px"/>
            <input type="hidden" id="operatorId<%=token %>" name="financemeetingapplicationwfa.operatorId" style="width:450px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnFinancemeetingapplicationwfaSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('FinancemeetingapplicationwfaWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
