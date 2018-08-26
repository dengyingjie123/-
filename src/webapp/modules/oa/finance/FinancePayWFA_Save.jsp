<!-- // FinancePayWFA_Save.jsp /////////////////////////////////////////// -->
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
    <form id="formFinancePayWFA<%=token %>" name="formFinancePayWFA" action="" method="post">
      <table border="0" cellspacing="5" cellpadding="0">
        <tr style="display: none">
          <td align="right">Sid</td>
          <td><input  type="text" id="sid<%=token %>" name="financePayWFA.sid"       style="width:350px"/></td>
        </tr>
        <tr style="display: none">
          <td align="right">Id</td>
          <td><input  type="text" id="id<%=token %>" name="financePayWFA.id"       style="width:350px"/></td>
        </tr>
        <tr style="display: none">
          <td align="right">State</td>
          <td><input  type="text" id="state<%=token %>" name="financePayWFA.state"       style="width:350px"/></td>
        </tr>
        <tr style="display: none">
          <td align="right">OperatorId</td>
          <td><input  type="text" id="operatorId<%=token %>" name="financePayWFA.operatorId"       style="width:350px"/></td>
        </tr>
        <tr style="display: none">
          <td align="right">OperateTime</td>
          <td><input  type="text" id="operateTime<%=token %>" name="financePayWFA.operateTime"       style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">公司名称：</td>
          <td><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                     name="financePayWFAVO.controlString1" style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">部门名称：</td>

          <td><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                     name="financePayWFAVO.controlString2" style="width:350px"/></td>
        </tr>
        <tr style="display: none">
          <td align="right">组织编号</td>
          <td><input  type="text" id="orgId<%=token %>" name="financePayWFA.orgId"       style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">日期</td>
          <td><input class="easyui-datebox" editable="false" type="text" id="time<%=token %>" name="financePayWFA.time"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
          <%--修改：周海鸿
               时间：2015-7-14
               内容：添加付款方模块信息--%>
      <%--  <tr>
          <td align="right">付款方名称</td>
          <td><input class="easyui-validatebox" type="text" id="payName<%=token %>" name="financePayWFA.payName"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">付款方开户行</td>
          <td><input class="easyui-validatebox" type="text" id="payBankName<%=token %>" name="financePayWFA.payBankName"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">付款方帐号</td>
          <td><input class="easyui-validatebox" type="text" id="payBankAccount<%=token %>" name="financePayWFA.payBankAccount"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>--%>

          <tr>
          <td align="right">收款方名称</td>
          <td><input class="easyui-validatebox" type="text" id="payeeName<%=token %>" name="financePayWFA.payeeName"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">收款方开户行</td>
          <td><input class="easyui-validatebox" type="text" id="payeeBankName<%=token %>" name="financePayWFA.payeeBankName"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">收款方帐号</td>
          <td><input class="easyui-validatebox" type="text" id="payeeBankAccount<%=token %>" name="financePayWFA.payeeBankAccount"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">资金支付项目</td>
          <td><input class="easyui-validatebox" type="text" id="projectName<%=token %>" name="financePayWFA.projectName"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">合同名称(项目名称)</td>
          <td><input class="easyui-validatebox" type="text" id="contractName<%=token %>" name="financePayWFA.contractName"  required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">合同编号</td>
          <td><input  type="text" id="contractNo<%=token %>" name="financePayWFA.contractNo"       style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">合同金额</td>
          <td><input class="easyui-validatebox" type="text" id="contractMoney<%=token %>" name="financePayWFA.contractMoney" validType="FloatOrCurrency" required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">累计已支付金额</td>
          <td><input  type="text" id="paidMoney<%=token %>" name="financePayWFA.paidMoney"   validType="FloatOrCurrency"    style="width:350px"/></td>
        </tr>
        <tr>
          <td align="right">本次支付金额</td>
          <td><input class="easyui-validatebox" type="text" id="money<%=token %>" name="financePayWFA.money" validType="FloatOrCurrency" required="true" missingmessage="必须填写"     style="width:350px"/></td>
        </tr>
        <!-- 附件上传开始 -->
        <tr id="uploadTR<%=token%>">
          <td align="right">附件上传</td>
          <td><a id="btnUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
                 href="javascript:void(0)">正在查询上传记录...</a></td>
        </tr>
        <tr>
          <td align="right">经办人姓名</td>
          <td><input  class="easyui-validatebox"  type="text" id="submitterName<%=token %>" name="submitterName"  data-options="required:'true'"     style="width:350px"/></td>

            <input  type="hidden" id="applicantId<%=token %>" name="financePayWFA.applicantId"       style="width:350px"/>
        </tr>
        <tr>
          <td align="right">经办人时间</td>
          <td><input  type="text" class="easyui-datetimebox" editable="false" data-options="required:'true'"  id="applicantTime<%=token %>" name="financePayWFA.applicantTime"       style="width:350px"/></td>
        </tr>
      </table>
     <input type="hidden"  id="controlString3<%=token %>"
      name="financePayWFAVO.controlString3" style="width:350px"/>
    </form>
  </div>
  <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
    <a id="btnFinancePayWFASubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
       href="javascript:void(0)">确定</a>

      <a id="btnFinancePayWFASubmit_Start<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
         href="javascript:void(0)">业务审批</a>
    <a id="btnFinancePayWFASubmit_applay<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
         href="javascript:void(0)">业务申请</a>

    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
       onClick="fwCloseWindow('FinancePayWFAWindow<%=token%>')">取消</a>
  </div>
</div>
</body>
</html>
