<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 14-12-27
  Time: 下午3:21
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
        <form id="formContractRoute<%=token %>" name="formContractRoute" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
            	<tr>
                    <td align="right">合同</td>
                    <td colspan="3"><input type="text" id="counts<%=token %>" name="contractApplication.counts" disabled="disabled" style="width:50px" readonly="readonly"/>套</td>
                </tr>
                <tr>
                    <td align="right"  valign="top">合同编号</td>
                    <td colspan="3"><textarea type="text" id="contractIdList<%=token %>"  name="contractRouteVO.contractIdList" disabled="disabled" style="width:340px;height: 100px;resize: none"/></td>
                </tr>
                <tr>
                    <td align="right">调配人</td>
                    <td><input  editable="false" readonly="true"  type="text" id="senderUserName<%=token %>" disabled="disabled" name="contractApplicationVO.senderUserName" style="width:130px"/></td>
                    <td align="right">调配时间</td>
                    <td><input class="easyui-datebox" type="text" id="sendTime<%=token %>" name="contractApplication.sendTime" disabled="disabled"  readonly="true" style="width:140px"/></td>
                </tr>
                <tr>
                    <td align="right">快递公司</td>
                    <td colspan="3"><input type="text" id="sendExpress<%=token %>" name="contractRoute.sendExpress" style="width:340px"/></td>
                </tr>
                <tr>
                    <td align="right">快递单号</td>
                    <td colspan="3"><input type="text" id="sendExpressId<%=token %>" name="contractRoute.sendExpressId" style="width:340px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="sid<%=token %>" name="contractApplication.sid" style="width:200px"/>
            <input  type="hidden" id="id<%=token %>" name="contractApplication.id" style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="contractApplication.operatorId" style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="contractApplication.operateTime"  style="width:200px"/>
            <input  type="hidden" id="state<%=token %>" name="contractApplication.state" style="width:200px"/>
            <input  type="hidden" id="senderId<%=token %>" name="contractApplication.senderId" style="width:200px"/>
        <%--<input  type="text" id="orgId<%=token %>" name="conApplication.orgId" style="width:200px"/>--%>
            <%--<input  type="text" id="applicationUerId<%=token %>" name="conApplication.applicationUerId"  style="width:200px"/>--%>
            <%--<input  type="text" id="productionId<%=token %>" name="conApplication.productionId" style="width:200px"/>--%>
            <%--<input  type="text" id="checkeId<%=token %>" name="conApplication.checkeId" style="width:200px"/>--%>
            <%--<input  type="text" id="applicationTime<%=token %>" name="conApplication.applicationTime" style="width:250px"/>--%>
            <%--<input  type="text" id="counts<%=token %>" name="conApplication.counts" style="width:250px"/>--%>
            <%--<input  type="text" id="checkState<%=token %>" name="conApplication.checkState" style="width:250px"/>--%>
            <%--<input  type="text" id="checkTime<%=token %>" name="conApplication.checkTime"style="width:250px"/>--%>
            <%--<input  type="text" id="checkComment<%=token %>" name="conApplication.checkComment" style="width:250px"/>--%>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnContractRouteSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ContractRouteWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
