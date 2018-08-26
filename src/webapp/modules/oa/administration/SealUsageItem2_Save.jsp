<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/7/1
  Time: 17:32
  To change this template use File | Settings | File Templates.
  用章类型保存页面
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formSealUsageItem2<%=token %>" name="formSealUsageItem2" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">

                <tr>
                    <td align="right">印章名称</td>
                    <td colspan="3"><input type="text" class="easyui-combotree" required ="true"  id="sealId<%=token %>"
                                           name="sealUsageItem2.sealId"
                                           style="width:505px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">印章份数</td>
                    <td><input type="text" class="easyui-validatebox" data-options="required:true,validType:'number'"
                               id="topies<%=token %>" name="sealUsageItem2.topies" style="width:200px"/>
                    </td>
                    <td align="right">是否外带</td>
                    <td>
                        <select name="sealUsageItem2.status" id="status<%=token%>" style="width:205px;">
                            <option value="0" selected="selected">内部使用</option>
                            <option value="1">外带使用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">外带地点</td>
                    <td colspan="3"><input type="text"   readonly="false"  id="sentToAddress<%=token %>"
                                           name="sealUsageItem2.sentToAddress" style="width:500px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">印章接收人</td>
                    <td><input type="text"
                               id="receiveName<%=token %>" readonly="false" name="sealUsageItem2.receiveName"
                               style="width:200px"/>
                    </td>
                   <%-- <td align="right">印章归还接收人</td>
                    <td><input type="text"
                               id="outBackName<%=token %>" readonly="false" name="sealUsageItem2.outBackName"
                               style="width:200px"/>
                    </td>--%>
                </tr>

            </table>
            <%--<td align="right">印章编号</td>--%>
            <input type="hidden" id="sealName<%=token %>" name="sealUsageItem2.sealName" style="width:200px"/>
            <%--id--%>
            <input type="hidden" id="id<%=token %>" name="sealUsageItem2.id" style="width:200px"/>
            <input type="hidden" id="sid<%=token %>" name="sealUsageItem2.sid" style="width:200px"/>
            <%--operatorId--%>
            <input type="hidden" id="operatorId<%=token %>" name="sealUsageItem2.operatorId" style="width:200px"/>
            <%--operateTime--%>
            <input type="hidden" id="operateTime<%=token %>" name="sealUsageItem2.operateTime" style="width:200px"/>
            <%--申请编号--%>
            <input type="hidden" id="applicationId<%=token %>" name="sealUsageItem2.applicationId" style="width:200px"/>
            <%--接收人--%>
            <input type="hidden" id="receiveId<%=token %>" name="sealUsageItem2.receiveId" style="width:200px"/>
            <input type="hidden" id="receiveTime<%=token %>" name="sealUsageItem2.receiveTime" style="width:200px"/>
            <%--归还接收人--%>
            <input type="hidden" id="outBackId<%=token %>" name="sealUsageItem2.outBackId" style="width:200px"/>
            <input type="hidden" id="outBackTime<%=token %>" name="sealUsageItem2.outBackTime" style="width:200px"/>
            <input type="hidden" id="receiveIsConfirm<%=token %>" name="sealUsageItem2.receiveIsConfirm" style="width:200px"/>
            <input type="hidden" id="outBackIsConfirm<%=token %>" name="sealUsageItem2.outBackIsConfirm" style="width:200px"/>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSealUsageItem2Submit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('SealUsageItem2Window<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>

