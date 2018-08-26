<%--
  Created by IntelliJ IDEA.
  User: Jepson
  Date: 2015/6/5
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style='padding:5px;'>
    <div class='easyui-panel' title='查询' iconCls='icon-search'>
        <table border='0' cellpadding='3' cellspacing='0'>
            <tr>
                <td align="right">申请人</td>
                <td>
                    <input type="text"  name="borrnowMoney.applicantName" id="search_applicantName<%=token%>" style="width:200px;"/>
                </td>
                <td align="right">申请金额</td>
                <td>
                    <input type="text" name="borrnowMoney.money" editable="false" id="search_money<%=token%>" style="width:200px;"/>
                </td>
                <td align="right">申请公司</td>
                <td>
                    <input type="text" class="easyui-combotree" name="borrnowMoney.controlString1Id" id="search_controlString1Id<%=token%>" style="width:300px;"/>
                </td>
              </tr>
            <tr>
                <td align="right">申请时间</td>
                <td><input type='text' class='easyui-datetimebox' name='search_applicationTime_start' id='search_applicationTime_start<%=token%>' style='width:200px;' editable='false'/></td>
                <td align="right">至</td>
                <td><input type='text' class='easyui-datetimebox' name='search_applicationTime_end' id='search_applicationTime_end<%=token%>' style='width:200px;' editable='false'/></td>

            </tr>
            <tr>
                <td>
                    <a id='btnBorrnowMoneySearchSubmit<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                       iconCls='icon-search'>查询</a>
                </td>
                <td>
                    <a id='btnBorrnowMoneySearchReset<%=token %>' href='javascript:void(0)' class='easyui-linkbutton'
                       iconCls='icon-cut'>重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <div style="height:400px;">
    <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
        <div title='我的申请' style='padding:5px'>
            <table id='BorrowMoneyTable<%=token%>'></table>
        </div>
        <div title='等待我审批' style='padding:5px'>
            <table id="WaitborrnowMoneyTable<%=token%>"></table>
        </div>
        <div title='已完成' style='padding:5px'>
            <table id="ParticipantborrnowMoneyTable<%=token%>"></table>
        </div>
    </div>
    </div>
</div>
</body>
</html>