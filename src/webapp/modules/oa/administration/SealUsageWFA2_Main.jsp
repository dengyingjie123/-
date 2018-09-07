<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 15-4-7
  Time: 下午9:09
  To change this template use File | Settings | File Templates.
--%>
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
                <td>申请人</td>
                <td><input type="text" id="search_ApplicantName<%=token %>" style="width:150px;"/></td>
                <td>申请时间</td>
                <td><input type="text" class="easyui-datebox" id="search_ApplicationTime_Start<%=token %>"
                           style="width:90px;" editable="false"/></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_ApplicationTime_End<%=token %>"
                           style="width:90px;" editable="false"/></td>
                <td>申请用途</td>
                <td><input type="text" id="search_ApplicationPurpose<%=token %>" style="width:150px;"/></td>
                <td>
                    <a id="btnSearchSealUsageWFA2<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetSealUsageWFA2<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>

        </table>
        <br>
    </div>

    <div  style='height: 500px;width:auto'  >
        <div id="InformTab<%=token%>" class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
            <div title='我的申请' style='padding:5px'>
                <table id="SealUsageWFA2Table<%=token%>"></table>
            </div>
            <div title='等待我审批' style='padding:5px'>
                <table id="WaitSealUsageWFA2Table<%=token%>"></table>
            </div>
            <div title='已完成' style='padding:5px'>
                <table id="ParticipantSealUsageWFA2Table<%=token%>"></table>
            </div>
        </div>
    </div>
</div>
</body>
</html>