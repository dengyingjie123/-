<!-- FinancePayWFA_Main.jsp /////////////////////////////////////////// -->
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
  String token = request.getParameter("token");
%>
<html>
<head>
  <title></title>
</head>
<body>
<div style="padding:5px;">

  <div class="easyui-panel" title="查询" iconCls="icon-search">
    <table border="0" cellpadding="3" cellspacing="0">
      <tr>
        <td>
          开始日期
        </td>
        <td>
          <input type="text" class="easyui-datebox" name="Search_Time_Start" id="Search_Time_Start<%=token%>" editable="false" style="width:100px;"/>

        </td>
        <td>
          结束日期
        </td>
        <td>
          <input type="text" class="easyui-datebox" name="Search_Time_End" id="Search_Time_End<%=token%>" editable="false" style="width:100px;"/>
        </td>
        <td>收款方名称</td>
        <td>
          <input type="text" class="easyui-validatebox" name="financePayWFA.payeeName" id="Search_PayeeName<%=token%>" style="width:100px;"/>
        </td>
          <td>付款方名称</td>
        <td>
          <input type="text" class="easyui-validatebox" name="financePayWFA.payName" id="Search_payName<%=token%>" style="width:100px;"/>
        </td>
          </tr>
        <tr>
        <td>合同名称</td>
        <td>
          <input type="text" class="easyui-validatebox" name="financePayWFA.contractName" id="Search_ContractName<%=token%>" style="width:100px;"/>
        </td>
        <td>合同编号</td>
        <td>
          <input type="text" class="easyui-validatebox" name="financePayWFA.contractNo" id="Search_ContractNo<%=token%>" style="width:100px;"/>
        </td>
        <td>
          <a id="btnFinancePayWFASearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
             iconCls="icon-search">查询</a>
        </td>
        <td>
          <a id="btnFinancePayWFASearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
             iconCls="icon-cut">重置</a>
        </td>
      </tr>
    </table>
  </div>
  <br>
    <div style="height:400px;">
    <div id="FinancePayWFATabs<%=token%>" class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
        <div title='我的申请' style='padding:5px'>
            <table id="FinancePayWFATable<%=token%>"></table>
        </div>
        <div title='等待我审批' style='padding:5px'>
            <table id="WaitFinancePayWFATable<%=token%>"></table>
        </div>
        <div title='已完成' style='padding:5px'>
            <table id="ParticipantFinancePayWFATable<%=token%>"></table>
        </div>
    </div>
    </div>

</div>
</body>
</html>