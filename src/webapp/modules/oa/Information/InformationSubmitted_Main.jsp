<%--
  Created by IntelliJ IDEA.
  User: haihong
  Date: 2015/5/29
  Time: 11:24
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
                <td>经办部门</td>
                <td>
                    <input type="text" class="easyui-combotree" name="informationSubmitted.department" id="Search_Department<%=token%>" style="width:150px;"/>
                </td>
                <td>经办人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="informationSubmitted.handlingId" id="Search_HandlingId<%=token%>" style="width:150px;"/>
                </td>
                <td>主送单位</td>
                <td>
                    <input type="text" class="easyui-combotree" name="informationSubmitted.mainOrg" id="Search_MainOrg<%=token%>" style="width:150px;"/>
                </td>
                <td>抄送单位</td>
                <td>
                    <input type="text" class="easyui-combotree" name="informationSubmitted.otherOrg" id="Search_OtherOrg<%=token%>" style="width:150px;"/>
                </td>
                </tr><tr>
                <td>报送事由</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="informationSubmitted.reason" id="Search_Reason<%=token%>" style="width:150px;"/>
                </td>
                <td>报送内容</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="informationSubmitted.content" id="Search_Content<%=token%>" style="width:150px;"/>
                </td>
                <td>
                    开始报送时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_SubmitTime_Start" id="Search_SubmitTime_Start<%=token%>" editable="false" style="width:150px;"/>

                </td>
                <td>
                    结束报送时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_SubmitTime_End" id="Search_SubmitTime_End<%=token%>" editable="false" style="width:150px;"/>
                </td>
        </tr><tr>
                <td>
                    开始原件移交时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_TransferTime_Start" id="Search_TransferTime_Start<%=token%>" editable="false" style="width:150px;"/>

                </td>
                <td>
                    结束原件移交时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_TransferTime_End" id="Search_TransferTime_End<%=token%>" editable="false" style="width:150px;"/>
                </td>
                <td>原件移交人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="informationSubmitted.transferOperatorId" id="Search_TransferOperatorId<%=token%>" style="width:150px;"/>
                </td>
                <td>原件移交接收人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="informationSubmitted.transferRecipient" id="Search_TransferRecipient<%=token%>" style="width:150px;"/>
                </td></tr><tr>
                <td>
                    开始原件归还时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_RevertTime_Start" id="Search_RevertTime_Start<%=token%>" editable="false" style="width:150px;"/>

                </td>
                <td>
                    结束原件归还时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_RevertTime_End" id="Search_RevertTime_End<%=token%>" editable="false" style="width:150px;"/>
                </td>
                <td>原件归还人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="informationSubmitted.revertOperatorId" id="Search_RevertOperatorId<%=token%>" style="width:150px;"/>
                </td>
                <td>原件归还接收人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="informationSubmitted.revertRecipientId" id="Search_RevertRecipientId<%=token%>" style="width:150px;"/>
                </td>
                </tr><tr>
                <td>
                    <a id="btnInformationSubmittedSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnInformationSubmittedSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>  <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
    <div title='我的申请' style='padding:5px'>
        <table id="InformationSubmittedTable<%=token%>"></table>
    </div>
    <div title='等待我审批' style='padding:5px'>
        <table id="WaitInformationSubmittedTable<%=token%>"></table>
    </div>
    <div title='已完成' style='padding:5px'>
        <table id="ParticipantInformationSubmittedTable<%=token%>"></table>
    </div>

</div>

</div>
</body>
</html>