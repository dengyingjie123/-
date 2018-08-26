<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
 销售合同申请资料填写页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title>销售合同申请</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formContractApplicationPO<%=token %>" name="formContractApplicationPO" action="" method="post">

            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">申请部门</td>
                    <td colspan="3"><input class="easyui-combotree" type="text" id="departmentId<%=token %>"
                                           name="contractApplicationPO.departmentId"
                                           style="width:400px" readonly="readonly"/></td>

                </tr>
                <tr>
                    <td align="right">申请人</td>
                    <td><input class="easyui-validatebox" type="text" id="applicationUserName<%=token %>"
                               name="applicationUserName" readonly="readonly"
                               style="width:163px"/></td>
                    <td align="right">申请时间</td>
                    <td><input class="easyui-datebox" type="text" id="applicationTime<%=token %>"
                               name="contractApplicationPO.applicationTime" editable="false" readonly="readonly"
                               style="width:163px"/></td>
                </tr>
                <tr>
                    <td align="right">产品</td>
                    <td colspan="3">
                        <input class="easyui-validatebox" readonly="true" type="text" id="productionName<%=token %>"
                               name="productionName" style="width:398px" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">合同套数</td>
                                <td colspan="3"><input class="easyui-validatebox" type="text" id="counts<%=token %>" name="contractApplicationPO.counts" style="width:100px" required="true" validType="integer"/>&nbsp;&nbsp;套
                    </td>
                </tr>
                <tr>
                    <td align="right">开始编号</td>
                    <td colspan="3"><input class="easyui-validatebox" type="text" id="beginNumber<%=token %>" name="beginNumber" style="width:100px" required="true" validType="integer"/>
                    </td>
                </tr>
                <tr id="checkNameTR<%=token%>">
                    <td align="right">审核人</td>
                    <td><input class="easyui-validatebox" type="text" id="checkName<%=token %>"
                               name="checkName" readonly="readonly" style="width:163px"/></td>
                    <td align="right">审核时间</td>
                    <td><input class="easyui-datetimebox" type="text" id="checkTime<%=token %>" name="contractApplicationPO.checkTime" editable="false" readonly="true" style="width:163px"/></td>
                </tr>
                <tr id="checkStateTR<%=token%>">
                    <td align="right">审核状态</td>
                    <td colspan="3"><input class="easyui-combotree" type="text" id="checkState<%=token %>"
                                           name="contractApplicationPO.checkState" style="width:400px"/></td>
                </tr>
                <tr id="checkCommentTR<%=token%>">

                    <td align="right">审核意见</td>
                    <td colspan="3"><textarea class="easyui-validatebox" type="text" id="checkComment<%=token%>"
                                              name="contractApplicationPO.checkComment"
                                              style="width:398px;height:100px;;"></textarea></td>
                </tr>
            </table>
          <input type="hidden" id="id<%=token %>" name="contractApplicationPO.id" style="width:200px"/>
            <input type="hidden" id="operatorId<%=token %>" name="contractApplicationPO.operatorId"
                   style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="contractApplicationPO.operateTime"
                   style="width:200px"/>
            <input type="hidden" id="applicationUserId<%=token %>" name="contractApplicationPO.applicationUserId"
                   style="width:200px"/>
            <input type="hidden" id="productionId<%=token %>" name="contractApplicationPO.productionId"
                   style="width:200px"/>
            <input type="hidden" id="checkId<%=token %>" name="contractApplicationPO.checkId" style="width:200px"/>
            <input type="hidden" id="departmentName<%=token %>" name="contractApplicationPO.departmentName"
                   style="width:200px"/>
            <input type="hidden" id="orgId<%=token %>" name="contractApplicationPO.orgId"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnContractApplicationSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('ContractApplicationWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
