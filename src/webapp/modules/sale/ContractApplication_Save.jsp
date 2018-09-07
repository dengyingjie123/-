<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 14-12-24
  Time: 下午4:26
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
        <form id="formContractApplication<%=token %>" name="formContractApplication" action="" method="post">



            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">申请人</td>
                    <td><input class="easyui-validatebox" type="text" id="applicationUserName<%=token %>" name="applicationUserName" readonly="readonly" style="width:180px"/></td>
                    <td>申请时间</td>
                    <td><input class="easyui-datebox" type="text" id="applicationTime<%=token %>" name="contractApplication.applicationTime" editable="false"readonly="true" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">产品</td>
                    <td colspan="3">
                        <input class="easyui-validatebox" readonly="true"  type="text" id="production<%=token %>" name="contractApplicationVO.productionName" style="width:430px" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">合同</td>
                    <td><input class="easyui-validatebox" type="text" id="counts<%=token %>" name="contractApplication.counts" style="width:50px" required="true" validType="integer"/>套</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td align="right">审核人</td>
                    <td><input class="easyui-validatebox" type="text" id="checkerUserName<%=token %>" name="checkerUserName" readonly="readonly" style="width:180px"/></td>
                    <td>审核时间</td>
                    <td><input class="easyui-datebox" type="text" id="checkTime<%=token %>" name="contractApplication.checkTime" editable="false" readonly="true" style="width:180px"/></td>
                </tr>
                <tr>
                    <td align="right">审核状态</td>
                    <td><input class="easyui-combotree" type="text" id="checkState<%=token %>" name="contractApplication.checkState"   style="width:180px"/></td>
                    <td>审核意见</td>
                    <td><input class="easyui-validatebox" type="text" id="checkComment<%=token%>" name="contractApplication.checkComment" style="width:180px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="id<%=token %>" name="contractApplication.id" style="width:200px"/>
            <input  type="hidden" id="operatorId<%=token %>" name="contractApplication.operatorId" style="width:200px"/>
            <input  type="hidden" id="operateTime<%=token %>" name="contractApplication.operateTime"  style="width:200px"/>
            <input  type="hidden" id="applicationUserId<%=token %>" name="contractApplication.applicationUserId"  style="width:200px"/>
            <input  type="hidden" id="productionId<%=token %>" name="contractApplication.productionId" style="width:200px"/>
            <input  type="hidden" id="checkId<%=token %>" name="contractApplication.checkId" style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnContractApplicationSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ContractApplicationWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
