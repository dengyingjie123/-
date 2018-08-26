<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 15-4-2
  Time: 下午1:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
       %>
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
    <form id="formFinanceExpense<%=token %>" name="formFinanceExpense" action="" method="post">
        <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
                <td align="right">公司名称：</td>
                <td colspan="4"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                                       name="expenseVO.controlString1" style="width:450px"/></td>
            </tr>
            <tr>
                <td align="right">部门名称：</td>

                <td colspan="4"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                                       name="expenseVO.controlString2" style="width:450px"/></td>
            </tr>
            <tr>
                <td align="right">报销人</td>
                <td><input type="text" id="submitter<%=token %>" name="financeExpense.submitter"
                           style="width:200px"/></td>
                <td></td>
                <td align="right">附件张数</td>
                <td>  <input type="text" id="accessoryNumber<%=token %>" name="financeExpense.accessoryNumber"   style="width:200px"/>张
                </td>
            </tr>
            <tr>
                <td align="right">费用产生时间</td>
                <td><input type="text" data-options="required:true" class="easyui-datebox" editable="false"
                           id="time<%=token %>"
                           name="financeExpense.time" style="width:200px"/></td>
                <td></td>
                <td align="right">报销月份</td>
                <td>
                    <input type="text" data-options="required:true" class="easyui-datebox" editable="false"
                           id="month<%=token %>"
                           name="financeExpense.month" style="width:200px"/>
                </td>
            </tr>
            <tr>
                <td align="right">总金额</td>
                <td>  <input type="text" id="money<%=token %>" name="financeExpense.money" readonly="readonly" validType="FloatOrCurrency" style="width:200px"/>
                </td>
                <td colspan="3" style="color: red;">总金额由系统自动计算，无需手动填写</td>
            </tr>
            <!-- 附件上传开始 -->
            <tr id="uploadTR<%=token%>">
                <td align="right">附件上传</td>
                <td><a id="btnUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
                       href="javascript:void(0)">正在查询上传记录...</a></td>
            </tr>
        </table>
        <table id="FinanceExpenseDetailTable<%=token%>" style="height: 250px"></table>


        <input type="hidden" id="expenseId<%=token %>" name="financeExpense.expenseId" style="width:200px"/>
        <input type="hidden" id="department<%=token %>" name="departmentName" style="width:200px"/>
        <input type="hidden" id="submitterId<%=token %>" name="financeExpense.submitterId" style="width:200px"/>
        <input type="hidden" id="submitterTime<%=token %>" name="financeExpense.submitterTime" style="width:150px"/>
        <input type="hidden" id="dapertmentId<%=token %>" name="financeExpense.dapertmentId" style="width:150px"/>
        <input type="hidden" id="operatorId<%=token %>" name="financeExpense.operatorId" style="width:200px"/>
        <input type="hidden" id="sid<%=token %>" name="financeExpense.sid" style="width:200px"/>
        <input type="hidden" id="id<%=token %>" name="financeExpense.id" style="width:200px"/>
        <input type="hidden" id="operateTime<%=token %>" name="financeExpense.operateTime" style="width:200px"/>
        <input type="hidden" id="state<%=token %>" name="financeExpense.state" style="width:200px"/>
        <input type="hidden" id="status<%=token %>" name="financeExpense.status" style="width:200px"/>
        <input type="hidden" id="operatorName<%=token %>" name="financeExpense.operatorName" style="width:200px"/>

        <input type="hidden"  id="controlString3<%=token %>"
               name="expenseVO.controlString3" style="width:450px"/>

    </form>
</div>
<div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">

    <a id="btnFinanceExpenseSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
       href="javascript:void(0)">确定</a>

    <a id="btnFinanceExpenseSubmit_Start<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
       href="javascript:void(0)">业务审批</a>
    <a id="btnFinanceExpenseSubmit_Applay<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
       href="javascript:void(0)">业务申请</a>

    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
       onClick="fwCloseWindow('FinanceExpenseWindow<%=token%>')">取消</a>
</div>
</div>
</body>
</html>